"""
Prometheus monitoring and metrics for ATP_Re.

This module provides:
- API endpoint metrics (request count, latency, errors)
- File processing metrics (files processed, decode time, file size)
- Database metrics (query count, connection pool)
- Custom business metrics
"""

from typing import Optional
from prometheus_client import (
    Counter, 
    Histogram, 
    Gauge, 
    Info,
    CollectorRegistry,
    generate_latest,
    CONTENT_TYPE_LATEST
)
from functools import wraps
import time
import structlog

logger = structlog.get_logger(__name__)

# Create default registry
registry = CollectorRegistry()

# API Metrics
api_requests_total = Counter(
    'atp_api_requests_total',
    'Total number of API requests',
    ['method', 'endpoint', 'status'],
    registry=registry
)

api_request_duration_seconds = Histogram(
    'atp_api_request_duration_seconds',
    'API request duration in seconds',
    ['method', 'endpoint'],
    registry=registry,
    buckets=(0.01, 0.05, 0.1, 0.5, 1.0, 2.5, 5.0, 10.0)
)

api_requests_in_progress = Gauge(
    'atp_api_requests_in_progress',
    'Number of API requests in progress',
    ['method', 'endpoint'],
    registry=registry
)

# File Processing Metrics
files_processed_total = Counter(
    'atp_files_processed_total',
    'Total number of files processed',
    ['file_type', 'status'],
    registry=registry
)

file_decode_duration_seconds = Histogram(
    'atp_file_decode_duration_seconds',
    'File decode duration in seconds',
    ['file_type'],
    registry=registry,
    buckets=(0.1, 0.5, 1.0, 5.0, 10.0, 30.0, 60.0, 120.0, 300.0)
)

file_size_bytes = Histogram(
    'atp_file_size_bytes',
    'Size of processed files in bytes',
    ['file_type'],
    registry=registry,
    buckets=(1024, 10240, 102400, 1048576, 10485760, 104857600)
)

# Database Metrics
database_queries_total = Counter(
    'atp_database_queries_total',
    'Total number of database queries',
    ['query_type', 'status'],
    registry=registry
)

database_query_duration_seconds = Histogram(
    'atp_database_query_duration_seconds',
    'Database query duration in seconds',
    ['query_type'],
    registry=registry,
    buckets=(0.001, 0.005, 0.01, 0.05, 0.1, 0.5, 1.0, 5.0)
)

database_connections_active = Gauge(
    'atp_database_connections_active',
    'Number of active database connections',
    registry=registry
)

# Cache Metrics
cache_operations_total = Counter(
    'atp_cache_operations_total',
    'Total number of cache operations',
    ['operation', 'status'],
    registry=registry
)

cache_hit_ratio = Gauge(
    'atp_cache_hit_ratio',
    'Cache hit ratio (0-1)',
    registry=registry
)

# Application Info
app_info = Info(
    'atp_application',
    'Application information',
    registry=registry
)

# System Metrics
system_memory_bytes = Gauge(
    'atp_system_memory_bytes',
    'System memory usage in bytes',
    ['type'],
    registry=registry
)

active_workers = Gauge(
    'atp_active_workers',
    'Number of active worker threads',
    registry=registry
)


class MetricsCollector:
    """
    Central metrics collector for ATP_Re.
    
    Provides convenience methods for recording metrics.
    """
    
    @staticmethod
    def record_api_request(method: str, endpoint: str, status: int, duration: float):
        """Record API request metrics."""
        api_requests_total.labels(
            method=method, 
            endpoint=endpoint, 
            status=str(status)
        ).inc()
        
        api_request_duration_seconds.labels(
            method=method,
            endpoint=endpoint
        ).observe(duration)
        
        logger.debug(
            "api_request_recorded",
            method=method,
            endpoint=endpoint,
            status=status,
            duration=duration
        )
    
    @staticmethod
    def record_file_processing(
        file_type: str, 
        status: str, 
        duration: float, 
        size: int
    ):
        """Record file processing metrics."""
        files_processed_total.labels(
            file_type=file_type,
            status=status
        ).inc()
        
        file_decode_duration_seconds.labels(
            file_type=file_type
        ).observe(duration)
        
        file_size_bytes.labels(
            file_type=file_type
        ).observe(size)
        
        logger.debug(
            "file_processing_recorded",
            file_type=file_type,
            status=status,
            duration=duration,
            size=size
        )
    
    @staticmethod
    def record_database_query(query_type: str, status: str, duration: float):
        """Record database query metrics."""
        database_queries_total.labels(
            query_type=query_type,
            status=status
        ).inc()
        
        database_query_duration_seconds.labels(
            query_type=query_type
        ).observe(duration)
        
        logger.debug(
            "database_query_recorded",
            query_type=query_type,
            status=status,
            duration=duration
        )
    
    @staticmethod
    def record_cache_operation(operation: str, hit: bool):
        """Record cache operation metrics."""
        status = "hit" if hit else "miss"
        cache_operations_total.labels(
            operation=operation,
            status=status
        ).inc()
        
        logger.debug(
            "cache_operation_recorded",
            operation=operation,
            status=status
        )
    
    @staticmethod
    def update_cache_hit_ratio(hits: int, total: int):
        """Update cache hit ratio."""
        if total > 0:
            ratio = hits / total
            cache_hit_ratio.set(ratio)
    
    @staticmethod
    def set_active_connections(count: int):
        """Set number of active database connections."""
        database_connections_active.set(count)
    
    @staticmethod
    def set_active_workers(count: int):
        """Set number of active worker threads."""
        active_workers.set(count)
    
    @staticmethod
    def set_app_info(version: str, environment: str):
        """Set application information."""
        app_info.info({
            'version': version,
            'environment': environment
        })


def monitor_execution_time(metric_name: str, labels: Optional[dict] = None):
    """
    Decorator to monitor function execution time.
    
    Args:
        metric_name: Name of the metric to use (e.g., 'file_decode', 'database_query')
        labels: Optional labels to add to the metric
        
    Usage:
        @monitor_execution_time('file_decode', {'file_type': 'btm'})
        def decode_btm_file(data):
            # decode logic
            pass
    """
    def decorator(func):
        @wraps(func)
        def wrapper(*args, **kwargs):
            start_time = time.time()
            try:
                result = func(*args, **kwargs)
                duration = time.time() - start_time
                
                # Record metric based on type
                if metric_name == 'file_decode':
                    file_type = labels.get('file_type', 'unknown') if labels else 'unknown'
                    MetricsCollector.record_file_processing(
                        file_type=file_type,
                        status='success',
                        duration=duration,
                        size=len(args[0]) if args and isinstance(args[0], bytes) else 0
                    )
                elif metric_name == 'database_query':
                    query_type = labels.get('query_type', 'unknown') if labels else 'unknown'
                    MetricsCollector.record_database_query(
                        query_type=query_type,
                        status='success',
                        duration=duration
                    )
                
                return result
                
            except Exception as e:
                duration = time.time() - start_time
                
                # Record failure metric
                if metric_name == 'file_decode':
                    file_type = labels.get('file_type', 'unknown') if labels else 'unknown'
                    MetricsCollector.record_file_processing(
                        file_type=file_type,
                        status='error',
                        duration=duration,
                        size=len(args[0]) if args and isinstance(args[0], bytes) else 0
                    )
                elif metric_name == 'database_query':
                    query_type = labels.get('query_type', 'unknown') if labels else 'unknown'
                    MetricsCollector.record_database_query(
                        query_type=query_type,
                        status='error',
                        duration=duration
                    )
                
                raise
        
        return wrapper
    return decorator


def get_metrics() -> bytes:
    """
    Get current metrics in Prometheus format.
    
    Returns:
        Metrics data as bytes
    """
    return generate_latest(registry)


def get_metrics_content_type() -> str:
    """
    Get the content type for Prometheus metrics.
    
    Returns:
        Content type string
    """
    return CONTENT_TYPE_LATEST
