# Stage 5 Quick Start Guide

This guide helps you quickly get started with the Stage 5 performance optimization and deployment features.

## Quick Start: 5 Minutes

### 1. Update Dependencies

```bash
cd /home/runner/work/ATP_Re/ATP_Re
pip install -r requirements.txt
```

### 2. Start Redis (Optional but Recommended)

```bash
# Ubuntu/Debian
sudo apt-get install redis-server
sudo systemctl start redis

# macOS
brew install redis
brew services start redis

# Docker
docker run -d -p 6379:6379 redis:7-alpine
```

### 3. Configure Environment

Add to your `.env` file:

```bash
# Redis Cache (optional)
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_DB=0
REDIS_ENABLED=true

# Logging
LOG_LEVEL=INFO
JSON_LOGS=true

# Monitoring
ENVIRONMENT=development
```

### 4. Start the API

```bash
cd api
# Development mode
python main.py

# Production mode (recommended)
uvicorn api.main:app --host 0.0.0.0 --port 8000 --workers 4

# Or use the systemd service (see production deployment)
```

### 5. Access Features

- **API Docs:** http://localhost:8000/docs
- **Health Check:** http://localhost:8000/health
- **Metrics:** http://localhost:8000/metrics

## Using Docker Compose (Recommended for Production)

### 1. One-Command Deployment

```bash
# Start all services (API, UI, PostgreSQL, Redis, Prometheus, Grafana)
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

### 2. Access Services

| Service | URL | Credentials |
|---------|-----|-------------|
| API | http://localhost:8000 | - |
| Web UI | http://localhost:8501 | - |
| Prometheus | http://localhost:9090 | - |
| Grafana | http://localhost:3000 | admin/admin |

## Feature Highlights

### 1. Caching

Automatically cache decoded file results:

```python
from core.performance import cached

@cached(ttl=3600)  # Cache for 1 hour
def decode_file(data: bytes) -> dict:
    # Expensive operation
    return result
```

### 2. Parallel Processing

Process multiple files simultaneously:

```python
from core.performance import ParallelProcessor

processor = ParallelProcessor(max_workers=4)
results = processor.process_files(
    files=['file1.dat', 'file2.dat', 'file3.dat'],
    process_func=process_single_file
)
```

### 3. Structured Logging

JSON logs with context:

```python
from core.logging_config import get_logger, LogContext

logger = get_logger(__name__)

with LogContext(request_id="abc123"):
    logger.info("processing_file", filename="data.dat", size=1024)
    # Log output: {"timestamp": "...", "request_id": "abc123", "event": "processing_file", ...}
```

### 4. Prometheus Metrics

Automatic metrics collection:

```bash
# View metrics
curl http://localhost:8000/metrics

# Key metrics
atp_api_requests_total{method="GET",endpoint="/api/v1/missions",status="200"} 42.0
atp_file_decode_duration_seconds_bucket{file_type="btm",le="1.0"} 15.0
atp_cache_hit_ratio 0.85
```

## Production Deployment

### Option 1: Automated Installation (Linux)

```bash
# Download and run installation script
sudo ./scripts/install.sh

# Configure database and settings
sudo nano /opt/atpre/app/.env

# Start services
sudo systemctl start atpre-api
sudo systemctl start atpre-ui

# Enable auto-start on boot
sudo systemctl enable atpre-api
sudo systemctl enable atpre-ui
```

### Option 2: Docker Compose

```bash
# Copy and configure
cp .env.example .env
nano .env

# Start all services
docker-compose up -d

# Check status
docker-compose ps
```

### Option 3: Manual Deployment

See [DEPLOYMENT_GUIDE.md](DEPLOYMENT_GUIDE.md) for detailed instructions.

## Monitoring Setup

### 1. View Metrics in Prometheus

```bash
# Start Prometheus (if not using Docker Compose)
docker run -d -p 9090:9090 \
  -v $(pwd)/config/prometheus.yml:/etc/prometheus/prometheus.yml \
  prom/prometheus

# Access UI
open http://localhost:9090
```

### 2. Grafana Dashboard

```bash
# Start Grafana (if not using Docker Compose)
docker run -d -p 3000:3000 grafana/grafana

# Login: admin/admin
# Add Prometheus data source: http://prometheus:9090
# Create dashboard or import from config/grafana/
```

### 3. Key Metrics to Monitor

- **Request Rate:** `rate(atp_api_requests_total[5m])`
- **Average Response Time:** `rate(atp_api_request_duration_seconds_sum[5m]) / rate(atp_api_request_duration_seconds_count[5m])`
- **Error Rate:** `rate(atp_api_requests_total{status=~"5.."}[5m])`
- **Cache Hit Ratio:** `atp_cache_hit_ratio`

## Backup and Restore

### Quick Backup

```bash
# Run backup script
sudo ./scripts/backup.sh

# Backup created in: /var/lib/atpre/backups/backup_YYYYMMDD_HHMMSS/
```

### Automated Backups

```bash
# Schedule daily backups at 2 AM
sudo crontab -e

# Add line:
0 2 * * * /opt/atpre/app/scripts/backup.sh >> /var/log/atpre/backup.log 2>&1
```

### Quick Restore

```bash
# Find latest backup
ls -lt /var/lib/atpre/backups/

# Stop services
sudo systemctl stop atpre-api

# Restore using .pgpass for security (recommended)
# Create ~/.pgpass file with: localhost:5432:atp_re:atp_user:password
# chmod 600 ~/.pgpass
pg_restore -h localhost -U atp_user -d atp_re --clean \
  /var/lib/atpre/backups/backup_YYYYMMDD_HHMMSS/database.backup

# Start services
sudo systemctl start atpre-api
```

See [BACKUP_RESTORE.md](BACKUP_RESTORE.md) for complete procedures.

## Performance Tuning

### 1. Enable Caching

```bash
# In .env
REDIS_ENABLED=true
CACHE_TTL=3600
```

### 2. Adjust Worker Count

```python
# For CPU-bound tasks
workers = CPU_cores

# For I/O-bound tasks  
# Use 2x to 4x the number of CPU cores
workers = CPU_cores * 2  # Conservative
workers = CPU_cores * 4  # Aggressive
```

### 3. Optimize Database

```sql
-- Add indexes for frequently queried columns
CREATE INDEX idx_missions_date ON atp_missions(mission_date);
CREATE INDEX idx_records_mission ON records(mission_id);

-- Run vacuum regularly
VACUUM ANALYZE;
```

### 4. Monitor Performance

```bash
# Check metrics
curl http://localhost:8000/metrics | grep -E "(duration|cache_hit)"

# Check logs for slow requests
journalctl -u atpre-api | grep "slow_request"
```

## Troubleshooting

### Redis Not Available

The system gracefully handles Redis unavailability:
- Caching is automatically disabled
- Application continues to function normally
- Logs show: "redis_unavailable"

### High Memory Usage

```bash
# Reduce worker count in service file
sudo nano /etc/systemd/system/atpre-api.service
# Change: --workers 2

# Add swap memory
sudo fallocate -l 2G /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile
```

### Slow Performance

```bash
# Check if Redis is running
redis-cli ping

# Check cache hit ratio
curl http://localhost:8000/metrics | grep cache_hit_ratio

# Increase worker count
# Edit service file or docker-compose.yml
```

For more issues, see [TROUBLESHOOTING.md](TROUBLESHOOTING.md).

## Testing Your Setup

### 1. Health Check

```bash
curl http://localhost:8000/health
# Expected: {"status":"healthy"}
```

### 2. Metrics Check

```bash
curl http://localhost:8000/metrics
# Expected: Prometheus-format metrics output
```

### 3. Cache Test

```python
from core.performance import CacheManager

cache = CacheManager()
cache.set("test", {"data": "value"})
result = cache.get("test")
print(result)  # {'data': 'value'}
```

### 4. Logging Test

```python
from core.logging_config import configure_logging, get_logger

configure_logging(log_level="INFO", json_logs=True)
logger = get_logger("test")
logger.info("test_event", key="value")
# Expected: JSON log output
```

## Next Steps

1. **Production Checklist:**
   - [ ] Configure database credentials
   - [ ] Enable HTTPS/SSL
   - [ ] Set up automated backups
   - [ ] Configure monitoring alerts
   - [ ] Test disaster recovery
   - [ ] Set up log rotation

2. **Optimization:**
   - Monitor metrics for bottlenecks
   - Adjust cache TTL based on usage
   - Tune worker count for your workload
   - Add database indexes as needed

3. **Monitoring:**
   - Set up Grafana dashboards
   - Configure alert rules in Prometheus
   - Set up email notifications
   - Monitor backup status

4. **Documentation:**
   - Read [DEPLOYMENT_GUIDE.md](DEPLOYMENT_GUIDE.md)
   - Review [TROUBLESHOOTING.md](TROUBLESHOOTING.md)
   - Check [BACKUP_RESTORE.md](BACKUP_RESTORE.md)
   - Explore [core/README.md](core/README.md)

## Support

- **Documentation:** See guides in this repository
- **Issues:** Create issues in your repository's issue tracker
- **Logs:** Check `/var/log/atpre/` for detailed logs

## Summary of Changes

This Stage 5 implementation adds:

✅ **Performance:** Redis caching, parallel processing, streaming
✅ **Monitoring:** Prometheus metrics, Grafana dashboards
✅ **Logging:** Structured JSON logs with context
✅ **Deployment:** Docker Compose, installation scripts, systemd services
✅ **Operations:** Automated backups, troubleshooting guides, recovery procedures

Your ATP_Re system is now production-ready with enterprise-grade features!
