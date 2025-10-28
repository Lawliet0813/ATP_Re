from fastapi import FastAPI, Response
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import JSONResponse
import uvicorn

from config.settings import settings
from api.app.routers import tasks, data, events, upload, reports, websocket

# Import logging and monitoring
from core.logging_config import configure_logging, get_logger
from core.monitoring import MetricsCollector, get_metrics, get_metrics_content_type
from api.app.middleware import LoggingMiddleware, MetricsMiddleware, PerformanceMiddleware

# Configure structured logging
configure_logging(
    log_level=settings.LOG_LEVEL,
    json_logs=getattr(settings, 'JSON_LOGS', True)
)
logger = get_logger(__name__)

# Create FastAPI app
app = FastAPI(
    title=settings.API_TITLE,
    version=settings.API_VERSION,
    description="REST API for ATP_Re - Automatic Train Protection System"
)

# Set application info for monitoring
MetricsCollector.set_app_info(
    version=settings.API_VERSION,
    environment=getattr(settings, 'ENVIRONMENT', 'development')
)

# Configure CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=settings.CORS_ORIGINS,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Add monitoring and logging middleware
app.add_middleware(PerformanceMiddleware, slow_request_threshold=1.0)
app.add_middleware(MetricsMiddleware)
app.add_middleware(LoggingMiddleware)


# Health check endpoint
@app.get("/")
async def root():
    """Root endpoint - API information"""
    logger.info("root_endpoint_accessed")
    return {
        "name": settings.API_TITLE,
        "version": settings.API_VERSION,
        "status": "running",
        "docs": "/docs",
        "redoc": "/redoc",
        "metrics": "/metrics"
    }


@app.get("/health")
async def health_check():
    """Health check endpoint"""
    logger.debug("health_check_accessed")
    return {"status": "healthy"}


@app.get("/metrics")
async def metrics():
    """Prometheus metrics endpoint"""
    return Response(
        content=get_metrics(),
        media_type=get_metrics_content_type()
    )


# Include routers
app.include_router(tasks.router, prefix=settings.API_PREFIX)
app.include_router(data.router, prefix=settings.API_PREFIX)
app.include_router(events.router, prefix=settings.API_PREFIX)
app.include_router(upload.router, prefix=settings.API_PREFIX)
app.include_router(reports.router, prefix=settings.API_PREFIX)
app.include_router(websocket.router, prefix=settings.API_PREFIX)


# Exception handlers
@app.exception_handler(Exception)
async def general_exception_handler(request, exc):
    """Handle general exceptions"""
    logger.error(
        "unhandled_exception",
        path=request.url.path,
        method=request.method,
        error=str(exc),
        exc_info=True
    )
    return JSONResponse(
        status_code=500,
        content={
            "detail": "Internal server error",
            "message": str(exc)
        }
    )


if __name__ == "__main__":
    logger.info(
        "starting_server",
        host=settings.HOST,
        port=settings.PORT,
        log_level=settings.LOG_LEVEL
    )
    uvicorn.run(
        "main:app",
        host=settings.HOST,
        port=settings.PORT,
        reload=True,
        log_level=settings.LOG_LEVEL.lower()
    )
