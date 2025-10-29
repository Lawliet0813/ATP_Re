"""
Performance optimization utilities for ATP_Re.

This module provides:
- Redis caching for large file decoding results
- Multi-threading support for parallel file processing
- Streaming utilities for handling large files
"""

import asyncio
import hashlib
import json
from concurrent.futures import ThreadPoolExecutor, as_completed
from typing import Any, Callable, List, Optional, Dict
from functools import wraps
import structlog

try:
    import redis
    from redis.asyncio import Redis as AsyncRedis
    REDIS_AVAILABLE = True
except ImportError:
    REDIS_AVAILABLE = False
    redis = None
    AsyncRedis = None

logger = structlog.get_logger(__name__)


class CacheManager:
    """
    Redis cache manager for storing decoded file results.
    
    Features:
    - Automatic key generation from file content
    - Configurable TTL (time-to-live)
    - JSON serialization for complex objects
    - Graceful fallback when Redis is unavailable
    """
    
    def __init__(
        self, 
        host: str = "localhost", 
        port: int = 6379, 
        db: int = 0,
        default_ttl: int = 3600,
        enabled: bool = True
    ):
        """
        Initialize cache manager.
        
        Args:
            host: Redis server host
            port: Redis server port
            db: Redis database number
            default_ttl: Default cache TTL in seconds (default: 1 hour)
            enabled: Whether caching is enabled
        """
        self.enabled = enabled and REDIS_AVAILABLE
        self.default_ttl = default_ttl
        self._client: Optional[redis.Redis] = None
        self._async_client: Optional[AsyncRedis] = None
        
        if self.enabled:
            try:
                self._client = redis.Redis(
                    host=host, 
                    port=port, 
                    db=db,
                    decode_responses=True
                )
                # Test connection
                self._client.ping()
                logger.info("cache_manager_initialized", host=host, port=port, db=db)
            except Exception as e:
                logger.warning("redis_unavailable", error=str(e))
                self.enabled = False
                self._client = None
    
    @staticmethod
    def generate_key(data: bytes, prefix: str = "atp_re") -> str:
        """
        Generate cache key from file data.
        
        Args:
            data: File data bytes
            prefix: Key prefix
            
        Returns:
            Cache key string
        """
        content_hash = hashlib.sha256(data).hexdigest()
        return f"{prefix}:file:{content_hash}"
    
    def get(self, key: str) -> Optional[Any]:
        """
        Get value from cache.
        
        Args:
            key: Cache key
            
        Returns:
            Cached value or None if not found
        """
        if not self.enabled or not self._client:
            return None
        
        try:
            value = self._client.get(key)
            if value:
                logger.debug("cache_hit", key=key)
                return json.loads(value)
            logger.debug("cache_miss", key=key)
            return None
        except Exception as e:
            logger.error("cache_get_error", key=key, error=str(e))
            return None
    
    def set(self, key: str, value: Any, ttl: Optional[int] = None) -> bool:
        """
        Set value in cache.
        
        Args:
            key: Cache key
            value: Value to cache (must be JSON serializable)
            ttl: Time-to-live in seconds (None for default)
            
        Returns:
            True if successful, False otherwise
        """
        if not self.enabled or not self._client:
            return False
        
        try:
            serialized = json.dumps(value)
            ttl = ttl or self.default_ttl
            self._client.setex(key, ttl, serialized)
            logger.debug("cache_set", key=key, ttl=ttl)
            return True
        except Exception as e:
            logger.error("cache_set_error", key=key, error=str(e))
            return False
    
    def delete(self, key: str) -> bool:
        """
        Delete value from cache.
        
        Args:
            key: Cache key
            
        Returns:
            True if successful, False otherwise
        """
        if not self.enabled or not self._client:
            return False
        
        try:
            self._client.delete(key)
            logger.debug("cache_delete", key=key)
            return True
        except Exception as e:
            logger.error("cache_delete_error", key=key, error=str(e))
            return False
    
    def clear_pattern(self, pattern: str) -> int:
        """
        Clear all keys matching pattern.
        
        Args:
            pattern: Key pattern (e.g., "atp_re:*")
            
        Returns:
            Number of keys deleted
        """
        if not self.enabled or not self._client:
            return 0
        
        try:
            keys = self._client.keys(pattern)
            if keys:
                count = self._client.delete(*keys)
                logger.info("cache_cleared", pattern=pattern, count=count)
                return count
            return 0
        except Exception as e:
            logger.error("cache_clear_error", pattern=pattern, error=str(e))
            return 0


def cached(ttl: int = 3600, key_prefix: str = "atp_re"):
    """
    Decorator for caching function results.
    
    Args:
        ttl: Cache TTL in seconds
        key_prefix: Cache key prefix
        
    Usage:
        @cached(ttl=1800)
        def decode_file(data: bytes) -> dict:
            # expensive decoding operation
            return result
    """
    def decorator(func: Callable) -> Callable:
        @wraps(func)
        def wrapper(*args, **kwargs):
            # Try to get from cache
            if args and isinstance(args[0], bytes):
                cache_mgr = CacheManager()
                cache_key = CacheManager.generate_key(args[0], key_prefix)
                
                cached_result = cache_mgr.get(cache_key)
                if cached_result is not None:
                    return cached_result
                
                # Execute function
                result = func(*args, **kwargs)
                
                # Store in cache
                cache_mgr.set(cache_key, result, ttl)
                return result
            
            # No caching for non-bytes first argument
            return func(*args, **kwargs)
        
        return wrapper
    return decorator


class ParallelProcessor:
    """
    Multi-threaded parallel processor for handling multiple files.
    
    Features:
    - Thread pool management
    - Progress tracking
    - Error handling per file
    - Configurable concurrency
    """
    
    def __init__(self, max_workers: Optional[int] = None):
        """
        Initialize parallel processor.
        
        Args:
            max_workers: Maximum number of worker threads (None for auto)
        """
        self.max_workers = max_workers
        logger.info("parallel_processor_initialized", max_workers=max_workers or "auto")
    
    def process_files(
        self, 
        files: List[Any], 
        process_func: Callable,
        progress_callback: Optional[Callable] = None
    ) -> Dict[str, Any]:
        """
        Process multiple files in parallel.
        
        Args:
            files: List of file objects or paths to process
            process_func: Function to process each file
            progress_callback: Optional callback for progress updates
            
        Returns:
            Dictionary with results and errors
        """
        results = []
        errors = []
        
        with ThreadPoolExecutor(max_workers=self.max_workers) as executor:
            # Submit all tasks
            future_to_file = {
                executor.submit(process_func, file): file 
                for file in files
            }
            
            # Collect results
            completed = 0
            total = len(files)
            
            for future in as_completed(future_to_file):
                file = future_to_file[future]
                completed += 1
                
                try:
                    result = future.result()
                    results.append({
                        "file": str(file),
                        "result": result,
                        "success": True
                    })
                    logger.debug(
                        "file_processed", 
                        file=str(file), 
                        progress=f"{completed}/{total}"
                    )
                except Exception as e:
                    errors.append({
                        "file": str(file),
                        "error": str(e),
                        "success": False
                    })
                    logger.error("file_processing_error", file=str(file), error=str(e))
                
                if progress_callback:
                    progress_callback(completed, total)
        
        logger.info(
            "parallel_processing_complete", 
            total=total, 
            successful=len(results), 
            errors=len(errors)
        )
        
        return {
            "results": results,
            "errors": errors,
            "total": total,
            "successful": len(results),
            "failed": len(errors)
        }


class StreamProcessor:
    """
    Streaming processor for handling large files without loading into memory.
    
    Features:
    - Chunk-based processing
    - Memory-efficient
    - Progress tracking
    """
    
    def __init__(self, chunk_size: int = 8192):
        """
        Initialize stream processor.
        
        Args:
            chunk_size: Size of chunks to read (default: 8KB)
        """
        self.chunk_size = chunk_size
        logger.info("stream_processor_initialized", chunk_size=chunk_size)
    
    def process_stream(
        self,
        file_path: str,
        process_chunk_func: Callable,
        progress_callback: Optional[Callable] = None
    ) -> Any:
        """
        Process file in streaming fashion.
        
        Args:
            file_path: Path to file
            process_chunk_func: Function to process each chunk
            progress_callback: Optional callback for progress updates
            
        Returns:
            Processing result
        """
        import os
        
        file_size = os.path.getsize(file_path)
        bytes_processed = 0
        
        logger.info("stream_processing_start", file=file_path, size=file_size)
        
        with open(file_path, 'rb') as f:
            while True:
                chunk = f.read(self.chunk_size)
                if not chunk:
                    break
                
                process_chunk_func(chunk)
                bytes_processed += len(chunk)
                
                if progress_callback:
                    progress = (bytes_processed / file_size) * 100
                    progress_callback(bytes_processed, file_size, progress)
        
        logger.info(
            "stream_processing_complete", 
            file=file_path, 
            bytes_processed=bytes_processed
        )
        
        return bytes_processed
    
    async def process_stream_async(
        self,
        file_path: str,
        process_chunk_func: Callable,
        progress_callback: Optional[Callable] = None
    ) -> Any:
        """
        Process file in streaming fashion asynchronously.
        
        Args:
            file_path: Path to file
            process_chunk_func: Async function to process each chunk
            progress_callback: Optional callback for progress updates
            
        Returns:
            Processing result
        """
        import os
        import aiofiles
        
        file_size = os.path.getsize(file_path)
        bytes_processed = 0
        
        logger.info("async_stream_processing_start", file=file_path, size=file_size)
        
        async with aiofiles.open(file_path, 'rb') as f:
            while True:
                chunk = await f.read(self.chunk_size)
                if not chunk:
                    break
                
                if asyncio.iscoroutinefunction(process_chunk_func):
                    await process_chunk_func(chunk)
                else:
                    process_chunk_func(chunk)
                    
                bytes_processed += len(chunk)
                
                if progress_callback:
                    progress = (bytes_processed / file_size) * 100
                    if asyncio.iscoroutinefunction(progress_callback):
                        await progress_callback(bytes_processed, file_size, progress)
                    else:
                        progress_callback(bytes_processed, file_size, progress)
        
        logger.info(
            "async_stream_processing_complete", 
            file=file_path, 
            bytes_processed=bytes_processed
        )
        
        return bytes_processed
