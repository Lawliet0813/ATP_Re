"""
Structured logging configuration for ATP_Re.

This module provides:
- JSON structured logging using structlog
- Request/response logging middleware
- Performance metrics logging
- Configurable log levels and outputs
"""

import logging
import sys
from typing import Any, Dict
import structlog
from structlog.stdlib import LoggerFactory


def configure_logging(
    log_level: str = "INFO",
    json_logs: bool = True,
    include_timestamp: bool = True
) -> None:
    """
    Configure structured logging for the application.
    
    Args:
        log_level: Logging level (DEBUG, INFO, WARNING, ERROR, CRITICAL)
        json_logs: Whether to output logs as JSON
        include_timestamp: Whether to include timestamps in logs
    """
    # Configure standard logging
    logging.basicConfig(
        format="%(message)s",
        stream=sys.stdout,
        level=getattr(logging, log_level.upper())
    )
    
    # Build processor chain
    processors = [
        structlog.stdlib.filter_by_level,
        structlog.stdlib.add_logger_name,
        structlog.stdlib.add_log_level,
        structlog.stdlib.PositionalArgumentsFormatter(),
        structlog.processors.StackInfoRenderer(),
        structlog.processors.format_exc_info,
        structlog.processors.UnicodeDecoder(),
    ]
    
    if include_timestamp:
        processors.insert(0, structlog.processors.TimeStamper(fmt="iso"))
    
    # Add JSON or console renderer
    if json_logs:
        processors.append(structlog.processors.JSONRenderer())
    else:
        processors.append(structlog.dev.ConsoleRenderer())
    
    # Configure structlog
    structlog.configure(
        processors=processors,
        context_class=dict,
        logger_factory=LoggerFactory(),
        wrapper_class=structlog.stdlib.BoundLogger,
        cache_logger_on_first_use=True,
    )


def get_logger(name: str = None) -> structlog.stdlib.BoundLogger:
    """
    Get a structured logger instance.
    
    Args:
        name: Logger name (defaults to calling module)
        
    Returns:
        Structured logger instance
    """
    return structlog.get_logger(name)


class LogContext:
    """
    Context manager for adding contextual information to logs.
    
    Usage:
        with LogContext(request_id="abc123", user_id=42):
            logger.info("processing_request")
            # All logs within this context will include request_id and user_id
    """
    
    def __init__(self, **kwargs):
        """
        Initialize log context.
        
        Args:
            **kwargs: Context key-value pairs to add to logs
        """
        self.context = kwargs
        self.token = None
    
    def __enter__(self):
        self.token = structlog.contextvars.bind_contextvars(**self.context)
        return self
    
    def __exit__(self, exc_type, exc_val, exc_tb):
        structlog.contextvars.unbind_contextvars(*self.context.keys())
        return False


# Pre-configured logger instances
api_logger = get_logger("api")
decoder_logger = get_logger("decoder")
database_logger = get_logger("database")
performance_logger = get_logger("performance")
