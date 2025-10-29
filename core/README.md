# Core Module - Performance, Logging, and Monitoring

This module provides performance optimization, structured logging, and monitoring capabilities for ATP_Re.

## Components

### 1. Performance (`performance.py`)

#### CacheManager
Redis-based caching for decoded file results.

**Features:**
- Automatic cache key generation from file content
- Configurable TTL (time-to-live)
- JSON serialization
- Graceful fallback when Redis unavailable

**Usage:**
```python
from core.performance import CacheManager, cached

# Initialize cache manager
cache = CacheManager(host="localhost", port=6379, default_ttl=3600)

# Manual caching
cache_key = CacheManager.generate_key(file_data)
result = cache.get(cache_key)
if result is None:
    result = expensive_operation(file_data)
    cache.set(cache_key, result)

# Decorator-based caching
@cached(ttl=1800)
def decode_file(data: bytes) -> dict:
    # expensive decoding operation
    return result
```

#### ParallelProcessor
Multi-threaded parallel file processing.

**Features:**
- Thread pool management
- Progress tracking
- Error handling per file
- Configurable concurrency

**Usage:**
```python
from core.performance import ParallelProcessor

processor = ParallelProcessor(max_workers=4)

def process_file(file_path):
    # process single file
    return result

results = processor.process_files(
    files=['file1.dat', 'file2.dat'],
    process_func=process_file,
    progress_callback=lambda done, total: print(f"{done}/{total}")
)
```

#### StreamProcessor
Memory-efficient streaming for large files.

**Features:**
- Chunk-based processing
- Memory-efficient
- Progress tracking
- Async support

**Usage:**
```python
from core.performance import StreamProcessor

processor = StreamProcessor(chunk_size=8192)

def process_chunk(chunk: bytes):
    # process chunk
    pass

# Synchronous
processor.process_stream(
    'large_file.dat',
    process_chunk_func=process_chunk
)

# Asynchronous
await processor.process_stream_async(
    'large_file.dat',
    process_chunk_func=async_process_chunk
)
```

### 2. Logging (`logging_config.py`)

Structured JSON logging using structlog.

**Features:**
- JSON or console output
- Automatic timestamp inclusion
- Context management
- Pre-configured loggers

**Usage:**
```python
from core.logging_config import configure_logging, get_logger, LogContext

# Configure at application startup
configure_logging(log_level="INFO", json_logs=True)

# Get logger
logger = get_logger(__name__)

# Log events
logger.info("user_login", user_id=123, ip="192.168.1.1")
logger.error("processing_failed", file="data.dat", error="invalid format")

# Use context for additional fields
with LogContext(request_id="abc123", user_id=42):
    logger.info("processing_request")
    # All logs include request_id and user_id
```

**Pre-configured Loggers:**
- `api_logger` - For API operations
- `decoder_logger` - For decoding operations
- `database_logger` - For database operations
- `performance_logger` - For performance metrics

### 3. Monitoring (`monitoring.py`)

Prometheus metrics collection and exposure.

**Available Metrics:**

**API Metrics:**
- `atp_api_requests_total` - Total API requests
- `atp_api_request_duration_seconds` - Request duration histogram
- `atp_api_requests_in_progress` - Current in-progress requests

**File Processing:**
- `atp_files_processed_total` - Total files processed
- `atp_file_decode_duration_seconds` - Decode duration histogram
- `atp_file_size_bytes` - File size histogram

**Database:**
- `atp_database_queries_total` - Total queries
- `atp_database_query_duration_seconds` - Query duration
- `atp_database_connections_active` - Active connections

**Cache:**
- `atp_cache_operations_total` - Cache operations
- `atp_cache_hit_ratio` - Cache hit ratio

**Usage:**
```python
from core.monitoring import MetricsCollector, monitor_execution_time

# Manual metric recording
MetricsCollector.record_api_request(
    method="GET",
    endpoint="/api/v1/missions",
    status=200,
    duration=0.123
)

MetricsCollector.record_file_processing(
    file_type="btm",
    status="success",
    duration=2.5,
    size=1024000
)

# Decorator-based monitoring
@monitor_execution_time('file_decode', {'file_type': 'btm'})
def decode_btm_file(data):
    # decoding logic
    pass
```

**Accessing Metrics:**
```bash
# Metrics endpoint
curl http://localhost:8000/metrics

# Prometheus format output
# HELP atp_api_requests_total Total number of API requests
# TYPE atp_api_requests_total counter
atp_api_requests_total{method="GET",endpoint="/api/v1/missions",status="200"} 42.0
```

### 4. Middleware (`api/app/middleware.py`)

FastAPI middleware for automatic logging and monitoring.

**Components:**

#### LoggingMiddleware
Logs all HTTP requests and responses.

**Logs:**
- Request method, path, query params
- Response status, duration
- Client IP and user agent
- Request ID for tracing

#### MetricsMiddleware
Automatically records Prometheus metrics for all requests.

#### PerformanceMiddleware
Tracks and logs slow requests.

**Usage in FastAPI:**
```python
from api.app.middleware import LoggingMiddleware, MetricsMiddleware, PerformanceMiddleware

app.add_middleware(PerformanceMiddleware, slow_request_threshold=1.0)
app.add_middleware(MetricsMiddleware)
app.add_middleware(LoggingMiddleware)
```

## Configuration

### Environment Variables

Add to `.env`:

```bash
# Redis Cache
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_DB=0
REDIS_ENABLED=true

# Logging
LOG_LEVEL=INFO
JSON_LOGS=true

# Monitoring
ENVIRONMENT=production
```

### Redis Setup

```bash
# Install Redis
sudo apt-get install redis-server

# Start Redis
sudo systemctl start redis
sudo systemctl enable redis

# Test connection
redis-cli ping
```

### Prometheus Setup

Add to `config/prometheus.yml`:

```yaml
scrape_configs:
  - job_name: 'atp_api'
    static_configs:
      - targets: ['localhost:8000']
    metrics_path: '/metrics'
```

## Performance Tips

### Caching Strategy

1. **Cache Frequently Accessed Data:**
   ```python
   @cached(ttl=3600)  # 1 hour
   def get_station_info(station_id):
       return database.query(station_id)
   ```

2. **Short TTL for Dynamic Data:**
   ```python
   @cached(ttl=300)  # 5 minutes
   def get_recent_missions():
       return database.query_recent()
   ```

3. **Monitor Cache Hit Rate:**
   ```bash
   curl http://localhost:8000/metrics | grep cache_hit_ratio
   ```

### Parallel Processing

1. **Optimal Worker Count:**
   - CPU-bound: `workers = CPU_cores`
   - I/O-bound: `workers = CPU_cores * 2-4`

2. **Batch Size:**
   - Process files in batches of 10-50
   - Larger batches for small files
   - Smaller batches for large files

### Streaming

Use streaming for files larger than available memory:

```python
# Instead of loading entire file
data = open('huge_file.dat', 'rb').read()  # ❌ Memory intensive

# Use streaming
processor = StreamProcessor(chunk_size=8192)
processor.process_stream('huge_file.dat', process_chunk)  # ✅ Memory efficient
```

## Monitoring Dashboard

### Grafana Setup

1. **Install Grafana:**
   ```bash
   docker run -d -p 3000:3000 grafana/grafana
   ```

2. **Add Prometheus Data Source:**
   - URL: `http://prometheus:9090`
   - Access: `Server`

3. **Import Dashboard:**
   - Use provided dashboard JSON in `config/grafana/dashboards/`

### Key Metrics to Monitor

1. **API Performance:**
   - Request rate
   - Average response time
   - Error rate (5xx responses)

2. **File Processing:**
   - Files processed per hour
   - Average decode time
   - Processing errors

3. **System Resources:**
   - Memory usage
   - CPU usage
   - Disk I/O

4. **Cache Performance:**
   - Hit ratio (target: > 80%)
   - Operations per second
   - Memory usage

## Troubleshooting

### Redis Connection Issues

```python
# Check if Redis is available
from core.performance import CacheManager

cache = CacheManager()
if not cache.enabled:
    print("Redis is not available, caching disabled")
```

### High Memory Usage

1. **Reduce cache TTL:**
   ```python
   cache = CacheManager(default_ttl=1800)  # 30 minutes
   ```

2. **Limit Redis memory:**
   ```bash
   # In redis.conf
   maxmemory 512mb
   maxmemory-policy allkeys-lru
   ```

3. **Reduce worker count:**
   ```python
   processor = ParallelProcessor(max_workers=2)
   ```

### Slow Performance

1. **Enable caching:**
   ```bash
   REDIS_ENABLED=true
   ```

2. **Increase worker count:**
   ```python
   processor = ParallelProcessor(max_workers=8)
   ```

3. **Check metrics:**
   ```bash
   curl http://localhost:8000/metrics | grep duration
   ```

## Examples

### Complete Example: Processing Multiple Files with Caching

```python
from core.performance import CacheManager, ParallelProcessor, cached
from core.logging_config import get_logger
from core.monitoring import MetricsCollector

logger = get_logger(__name__)

@cached(ttl=3600)
def decode_file(data: bytes) -> dict:
    """Decode file with automatic caching."""
    logger.info("decoding_file", size=len(data))
    # ... decoding logic ...
    return result

def process_single_file(file_path: str) -> dict:
    """Process single file with monitoring."""
    start_time = time.time()
    
    try:
        with open(file_path, 'rb') as f:
            data = f.read()
        
        result = decode_file(data)
        
        duration = time.time() - start_time
        MetricsCollector.record_file_processing(
            file_type='dat',
            status='success',
            duration=duration,
            size=len(data)
        )
        
        return result
        
    except Exception as e:
        logger.error("file_processing_error", file=file_path, error=str(e))
        MetricsCollector.record_file_processing(
            file_type='dat',
            status='error',
            duration=time.time() - start_time,
            size=0
        )
        raise

# Process multiple files in parallel
processor = ParallelProcessor(max_workers=4)
files = ['file1.dat', 'file2.dat', 'file3.dat']

results = processor.process_files(
    files=files,
    process_func=process_single_file,
    progress_callback=lambda done, total: logger.info("progress", done=done, total=total)
)

logger.info(
    "processing_complete",
    total=results['total'],
    successful=results['successful'],
    failed=results['failed']
)
```

## Testing

Run tests for core modules:

```bash
pytest tests/test_performance.py
pytest tests/test_logging.py
pytest tests/test_monitoring.py
```

## Additional Resources

- [Structlog Documentation](https://www.structlog.org/)
- [Prometheus Python Client](https://github.com/prometheus/client_python)
- [Redis Python Client](https://redis-py.readthedocs.io/)
- [FastAPI Middleware](https://fastapi.tiangolo.com/tutorial/middleware/)
