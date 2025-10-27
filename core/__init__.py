"""
Core module for ATP_Re.

This module provides:
- Performance optimization (caching, parallel processing, streaming)
- Structured logging
- Prometheus monitoring and metrics
"""

from core.performance import (
    CacheManager,
    ParallelProcessor,
    StreamProcessor,
    cached,
)

from core.logging_config import (
    configure_logging,
    get_logger,
    LogContext,
    api_logger,
    decoder_logger,
    database_logger,
    performance_logger,
)

from core.monitoring import (
    MetricsCollector,
    monitor_execution_time,
    get_metrics,
    get_metrics_content_type,
)

__all__ = [
    # Performance
    'CacheManager',
    'ParallelProcessor',
    'StreamProcessor',
    'cached',
    # Logging
    'configure_logging',
    'get_logger',
    'LogContext',
    'api_logger',
    'decoder_logger',
    'database_logger',
    'performance_logger',
    # Monitoring
    'MetricsCollector',
    'monitor_execution_time',
    'get_metrics',
    'get_metrics_content_type',
]
