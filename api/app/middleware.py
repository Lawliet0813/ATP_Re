"""
FastAPI middleware for logging and monitoring.

This module provides:
- Request/response logging middleware
- Prometheus metrics middleware
- Performance tracking
"""

import time
from typing import Callable
from fastapi import Request, Response
from starlette.middleware.base import BaseHTTPMiddleware
from starlette.types import ASGIApp
import structlog

from core.monitoring import MetricsCollector, api_requests_in_progress

logger = structlog.get_logger(__name__)


class LoggingMiddleware(BaseHTTPMiddleware):
    """
    Middleware for structured logging of requests and responses.
    
    Logs:
    - Request method, path, headers
    - Response status, duration
    - Client IP address
    - Request ID for tracing
    """
    
    async def dispatch(self, request: Request, call_next: Callable) -> Response:
        """Process request and log details."""
        import uuid
        
        # Generate request ID
        request_id = str(uuid.uuid4())
        
        # Get client info
        client_host = request.client.host if request.client else "unknown"
        
        # Start timing
        start_time = time.time()
        
        # Log request
        logger.info(
            "http_request_received",
            request_id=request_id,
            method=request.method,
            path=request.url.path,
            query=str(request.url.query) if request.url.query else None,
            client=client_host,
            user_agent=request.headers.get("user-agent"),
        )
        
        # Process request
        try:
            response = await call_next(request)
            duration = time.time() - start_time
            
            # Log response
            logger.info(
                "http_request_completed",
                request_id=request_id,
                method=request.method,
                path=request.url.path,
                status=response.status_code,
                duration=duration,
                client=client_host,
            )
            
            # Add headers
            response.headers["X-Request-ID"] = request_id
            response.headers["X-Response-Time"] = f"{duration:.3f}s"
            
            return response
            
        except Exception as e:
            duration = time.time() - start_time
            
            logger.error(
                "http_request_error",
                request_id=request_id,
                method=request.method,
                path=request.url.path,
                duration=duration,
                error=str(e),
                client=client_host,
            )
            raise


class MetricsMiddleware(BaseHTTPMiddleware):
    """
    Middleware for collecting Prometheus metrics.
    
    Tracks:
    - Request count by method, endpoint, status
    - Request duration
    - Requests in progress
    """
    
    async def dispatch(self, request: Request, call_next: Callable) -> Response:
        """Process request and record metrics."""
        
        # Get endpoint (remove path parameters)
        endpoint = request.url.path
        method = request.method
        
        # Track in-progress requests
        in_progress = api_requests_in_progress.labels(
            method=method,
            endpoint=endpoint
        )
        in_progress.inc()
        
        # Start timing
        start_time = time.time()
        
        try:
            # Process request
            response = await call_next(request)
            duration = time.time() - start_time
            
            # Record metrics
            MetricsCollector.record_api_request(
                method=method,
                endpoint=endpoint,
                status=response.status_code,
                duration=duration
            )
            
            return response
            
        except Exception as e:
            duration = time.time() - start_time
            
            # Record error metrics
            MetricsCollector.record_api_request(
                method=method,
                endpoint=endpoint,
                status=500,
                duration=duration
            )
            raise
            
        finally:
            # Decrease in-progress counter
            in_progress.dec()


class PerformanceMiddleware(BaseHTTPMiddleware):
    """
    Middleware for tracking performance and slow requests.
    
    Features:
    - Logs slow requests (>1s)
    - Tracks memory usage for large requests
    """
    
    def __init__(self, app: ASGIApp, slow_request_threshold: float = 1.0):
        """
        Initialize performance middleware.
        
        Args:
            app: ASGI application
            slow_request_threshold: Threshold in seconds for slow request logging
        """
        super().__init__(app)
        self.slow_request_threshold = slow_request_threshold
    
    async def dispatch(self, request: Request, call_next: Callable) -> Response:
        """Process request and track performance."""
        
        start_time = time.time()
        
        # Process request
        response = await call_next(request)
        
        duration = time.time() - start_time
        
        # Log slow requests
        if duration > self.slow_request_threshold:
            logger.warning(
                "slow_request_detected",
                method=request.method,
                path=request.url.path,
                duration=duration,
                threshold=self.slow_request_threshold
            )
        
        return response
