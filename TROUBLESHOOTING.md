# ATP_Re Troubleshooting Guide

This guide provides solutions for common issues and debugging strategies.

## Table of Contents

1. [Service Issues](#service-issues)
2. [Database Problems](#database-problems)
3. [Performance Issues](#performance-issues)
4. [Cache Issues](#cache-issues)
5. [Deployment Problems](#deployment-problems)
6. [Monitoring and Logging](#monitoring-and-logging)
7. [Common Error Messages](#common-error-messages)

---

## Service Issues

### API Service Won't Start

**Symptoms:**
- `systemctl status atpre-api` shows "failed" or "inactive"
- Port 8000 is not accessible

**Diagnosis:**
```bash
# Check service logs
sudo journalctl -u atpre-api -n 100 --no-pager

# Check if port is already in use
sudo netstat -tulpn | grep 8000

# Check configuration
cd /opt/atpre/app
source /opt/atpre/venv/bin/activate
python -c "from config.settings import settings; print(settings)"
```

**Solutions:**

1. **Port Already in Use:**
   ```bash
   # Find and kill process using port 8000
   sudo lsof -ti:8000 | xargs kill -9
   sudo systemctl restart atpre-api
   ```

2. **Configuration Error:**
   ```bash
   # Verify .env file
   cat /opt/atpre/app/.env
   # Check for missing or invalid values
   ```

3. **Permission Issues:**
   ```bash
   # Fix ownership
   sudo chown -R atpre:atpre /opt/atpre
   sudo chown -R atpre:atpre /var/log/atpre
   sudo chown -R atpre:atpre /var/lib/atpre
   ```

4. **Missing Dependencies:**
   ```bash
   cd /opt/atpre/app
   source /opt/atpre/venv/bin/activate
   pip install -r requirements.txt
   ```

### UI Service Issues

**Symptoms:**
- Streamlit UI not accessible on port 8501
- UI shows connection errors

**Diagnosis:**
```bash
# Check UI logs
sudo journalctl -u atpre-ui -n 100 --no-pager

# Check if API is accessible
curl http://localhost:8000/health
```

**Solutions:**

1. **API Not Responding:**
   ```bash
   # Ensure API is running first
   sudo systemctl start atpre-api
   sudo systemctl start atpre-ui
   ```

2. **Streamlit Config Issues:**
   ```bash
   # Check streamlit config
   cat ~/.streamlit/config.toml
   ```

---

## Database Problems

### Cannot Connect to Database

**Symptoms:**
- "Connection refused" errors
- "Authentication failed" errors

**Diagnosis:**
```bash
# Test PostgreSQL connection
psql -h localhost -U atp_user -d atp_re

# Check PostgreSQL status
sudo systemctl status postgresql

# Check if PostgreSQL is listening
sudo netstat -tulpn | grep 5432
```

**Solutions:**

1. **PostgreSQL Not Running:**
   ```bash
   sudo systemctl start postgresql
   sudo systemctl enable postgresql
   ```

2. **Wrong Credentials:**
   ```bash
   # Update .env file with correct credentials
   nano /opt/atpre/app/.env
   ```

3. **Database Doesn't Exist:**
   ```bash
   # Create database
   sudo -u postgres psql -c "CREATE DATABASE atp_re;"
   sudo -u postgres psql -c "CREATE USER atp_user WITH PASSWORD 'your_password';"
   sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE atp_re TO atp_user;"
   ```

4. **Connection Limit Reached:**
   ```bash
   # Check current connections
   sudo -u postgres psql -c "SELECT count(*) FROM pg_stat_activity;"
   
   # Increase max connections in postgresql.conf
   sudo nano /etc/postgresql/*/main/postgresql.conf
   # Set: max_connections = 200
   sudo systemctl restart postgresql
   ```

### Slow Database Queries

**Symptoms:**
- API responses are slow
- High database CPU usage

**Diagnosis:**
```bash
# Check slow queries
sudo -u postgres psql atp_re -c "
SELECT pid, now() - pg_stat_activity.query_start AS duration, query 
FROM pg_stat_activity 
WHERE state = 'active' AND now() - pg_stat_activity.query_start > interval '5 seconds';
"

# Check table sizes
sudo -u postgres psql atp_re -c "
SELECT schemaname, tablename, pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename)) AS size
FROM pg_tables 
WHERE schemaname NOT IN ('pg_catalog', 'information_schema')
ORDER BY pg_total_relation_size(schemaname||'.'||tablename) DESC;
"
```

**Solutions:**

1. **Missing Indexes:**
   ```sql
   -- Add indexes on frequently queried columns
   CREATE INDEX idx_missions_date ON atp_missions(mission_date);
   CREATE INDEX idx_records_mission ON records(mission_id);
   ```

2. **Vacuum Database:**
   ```bash
   sudo -u postgres psql atp_re -c "VACUUM ANALYZE;"
   ```

3. **Adjust Database Settings:**
   ```bash
   # Edit postgresql.conf
   sudo nano /etc/postgresql/*/main/postgresql.conf
   
   # Recommended settings:
   shared_buffers = 256MB
   effective_cache_size = 1GB
   maintenance_work_mem = 64MB
   checkpoint_completion_target = 0.9
   wal_buffers = 16MB
   default_statistics_target = 100
   random_page_cost = 1.1
   ```

---

## Performance Issues

### High Memory Usage

**Symptoms:**
- System running out of memory
- Services being killed by OOM

**Diagnosis:**
```bash
# Check memory usage
free -h
top -o %MEM

# Check service memory
systemctl status atpre-api
ps aux | grep gunicorn
```

**Solutions:**

1. **Reduce Worker Count:**
   ```bash
   # Edit service file
   sudo nano /etc/systemd/system/atpre-api.service
   # Change: --workers 2 (instead of 4)
   sudo systemctl daemon-reload
   sudo systemctl restart atpre-api
   ```

2. **Enable Swap:**
   ```bash
   # Create swap file
   sudo fallocate -l 2G /swapfile
   sudo chmod 600 /swapfile
   sudo mkswap /swapfile
   sudo swapon /swapfile
   # Make permanent
   echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
   ```

3. **Optimize Cache Settings:**
   ```bash
   # Edit .env to reduce cache size
   REDIS_MAX_MEMORY=512mb
   ```

### Slow File Processing

**Symptoms:**
- File upload/decode takes too long
- Timeout errors during processing

**Diagnosis:**
```bash
# Check metrics
curl http://localhost:8000/metrics | grep file_decode

# Check system resources
iostat -x 1
```

**Solutions:**

1. **Enable Redis Cache:**
   ```bash
   # Ensure Redis is running
   sudo systemctl start redis
   
   # Update .env
   REDIS_ENABLED=true
   ```

2. **Increase Worker Count:**
   ```bash
   # For CPU-intensive tasks
   # Edit service file to add more workers
   --workers 8
   ```

3. **Optimize Chunk Size:**
   ```python
   # In core/performance.py, adjust:
   chunk_size = 16384  # Increase for faster streaming
   ```

---

## Cache Issues

### Redis Connection Errors

**Symptoms:**
- "Connection refused to Redis"
- Cache operations failing

**Diagnosis:**
```bash
# Check Redis status
sudo systemctl status redis

# Test connection
redis-cli ping

# Check Redis logs
sudo journalctl -u redis -n 100
```

**Solutions:**

1. **Start Redis:**
   ```bash
   sudo systemctl start redis
   sudo systemctl enable redis
   ```

2. **Check Redis Configuration:**
   ```bash
   # Edit redis.conf
   sudo nano /etc/redis/redis.conf
   
   # Ensure:
   bind 127.0.0.1
   port 6379
   ```

3. **Increase Memory Limit:**
   ```bash
   # In redis.conf
   maxmemory 512mb
   maxmemory-policy allkeys-lru
   ```

### Low Cache Hit Rate

**Symptoms:**
- Cache hit ratio < 0.5
- Performance not improving with cache

**Diagnosis:**
```bash
# Check cache metrics
curl http://localhost:8000/metrics | grep cache_hit_ratio

# Check Redis stats
redis-cli info stats
```

**Solutions:**

1. **Increase Cache TTL:**
   ```python
   # In .env or code
   CACHE_TTL=7200  # 2 hours instead of 1
   ```

2. **Increase Redis Memory:**
   ```bash
   # In redis.conf
   maxmemory 1gb
   ```

3. **Check Cache Key Strategy:**
   ```bash
   # Monitor cache keys
   redis-cli KEYS "atp_re:*"
   ```

---

## Deployment Problems

### Deployment Script Fails

**Symptoms:**
- deploy.sh exits with error
- Services don't restart properly

**Diagnosis:**
```bash
# Check deployment logs
sudo journalctl -xe

# Check recent backups
ls -lh /var/lib/atpre/backups/
```

**Solutions:**

1. **Manual Rollback:**
   ```bash
   # Find latest backup
   cd /var/lib/atpre/backups
   ls -lt | head -5
   
   # Extract backup
   tar -xzf deployment_backup_YYYYMMDD_HHMMSS.tar.gz -C /opt/atpre/app
   
   # Restart services
   sudo systemctl restart atpre-api atpre-ui
   ```

2. **Fix Git Issues:**
   ```bash
   cd /opt/atpre/app
   sudo -u atpre git status
   sudo -u atpre git stash
   sudo -u atpre git pull
   ```

### Docker Deployment Issues

**Symptoms:**
- Containers won't start
- Health checks failing

**Diagnosis:**
```bash
# Check container status
docker-compose ps

# View logs
docker-compose logs api
docker-compose logs ui

# Check health
docker inspect atp_api | grep -A 10 Health
```

**Solutions:**

1. **Rebuild Containers:**
   ```bash
   docker-compose down
   docker-compose build --no-cache
   docker-compose up -d
   ```

2. **Check Environment Variables:**
   ```bash
   # View container environment
   docker exec atp_api env
   ```

3. **Network Issues:**
   ```bash
   # Recreate network
   docker-compose down
   docker network prune
   docker-compose up -d
   ```

---

## Monitoring and Logging

### Prometheus Not Scraping Metrics

**Symptoms:**
- No data in Grafana
- Prometheus targets down

**Diagnosis:**
```bash
# Check Prometheus targets
curl http://localhost:9090/api/v1/targets

# Check metrics endpoint
curl http://localhost:8000/metrics
```

**Solutions:**

1. **Verify Prometheus Config:**
   ```bash
   # Check config
   cat /opt/atpre/app/config/prometheus.yml
   
   # Reload config
   curl -X POST http://localhost:9090/-/reload
   ```

2. **Check Network Connectivity:**
   ```bash
   # From Prometheus container
   docker exec atp_prometheus curl http://api:8000/metrics
   ```

### Logs Not Appearing

**Symptoms:**
- No logs in /var/log/atpre
- journalctl shows no output

**Diagnosis:**
```bash
# Check log directory permissions
ls -la /var/log/atpre

# Check systemd logging
sudo journalctl -u atpre-api --since "1 hour ago"
```

**Solutions:**

1. **Fix Permissions:**
   ```bash
   sudo chown -R atpre:atpre /var/log/atpre
   sudo chmod 755 /var/log/atpre
   ```

2. **Enable Debug Logging:**
   ```bash
   # In .env
   LOG_LEVEL=DEBUG
   sudo systemctl restart atpre-api
   ```

3. **Configure Log Rotation:**
   ```bash
   sudo nano /etc/logrotate.d/atpre
   
   /var/log/atpre/*.log {
       daily
       rotate 14
       compress
       delaycompress
       notifempty
       create 0640 atpre atpre
   }
   ```

---

## Common Error Messages

### "ModuleNotFoundError: No module named 'X'"

**Solution:**
```bash
cd /opt/atpre/app
source /opt/atpre/venv/bin/activate
pip install -r requirements.txt
```

### "PermissionError: [Errno 13] Permission denied"

**Solution:**
```bash
# Fix file ownership
sudo chown -R atpre:atpre /opt/atpre
sudo chown -R atpre:atpre /var/log/atpre
sudo chown -R atpre:atpre /var/lib/atpre
```

### "Address already in use"

**Solution:**
```bash
# Find and kill process
sudo lsof -ti:8000 | xargs kill -9
sudo systemctl restart atpre-api
```

### "Connection pool exhausted"

**Solution:**
```bash
# Increase database connection pool
# In code or config:
DB_POOL_SIZE=20
DB_MAX_OVERFLOW=40
```

---

## Getting Help

### Collect Debug Information

When reporting issues, collect this information:

```bash
#!/bin/bash
# debug-info.sh

echo "=== System Information ==="
uname -a
cat /etc/os-release

echo -e "\n=== Service Status ==="
systemctl status atpre-api --no-pager
systemctl status atpre-ui --no-pager

echo -e "\n=== Recent Logs ==="
sudo journalctl -u atpre-api -n 50 --no-pager
sudo journalctl -u atpre-ui -n 50 --no-pager

echo -e "\n=== Resource Usage ==="
free -h
df -h
top -bn1 | head -20

echo -e "\n=== Network Status ==="
sudo netstat -tulpn | grep -E '(8000|8501|5432|6379)'

echo -e "\n=== Database Status ==="
sudo systemctl status postgresql --no-pager
sudo -u postgres psql -c "SELECT version();"

echo -e "\n=== Redis Status ==="
sudo systemctl status redis --no-pager
redis-cli ping
```

Run this script and include output when seeking help:
```bash
chmod +x debug-info.sh
./debug-info.sh > debug-output.txt 2>&1
```

### Support Resources

- **GitHub Issues:** https://github.com/Lawliet0813/ATP_Re/issues
- **Documentation:** See README.md and other guides
- **Logs:** Check /var/log/atpre/ for detailed logs

---

## Preventive Maintenance

### Regular Tasks

1. **Daily:**
   - Monitor service health
   - Check disk space
   - Review error logs

2. **Weekly:**
   - Run database vacuum
   - Review performance metrics
   - Check backup integrity

3. **Monthly:**
   - Update dependencies
   - Review and update documentation
   - Cleanup old logs and backups

### Health Check Script

```bash
#!/bin/bash
# health-check.sh

# Check services
systemctl is-active --quiet atpre-api && echo "API: OK" || echo "API: FAIL"
systemctl is-active --quiet atpre-ui && echo "UI: OK" || echo "UI: FAIL"

# Check endpoints
curl -f http://localhost:8000/health >/dev/null 2>&1 && echo "API Health: OK" || echo "API Health: FAIL"

# Check disk space
df -h / | awk 'NR==2 {if ($5+0 > 80) print "Disk: WARNING ("$5")"; else print "Disk: OK"}'

# Check memory
free | awk 'NR==2 {if ($3/$2*100 > 90) print "Memory: WARNING"; else print "Memory: OK"}'
```

Run periodically:
```bash
chmod +x health-check.sh
# Add to cron for daily checks
0 8 * * * /opt/atpre/scripts/health-check.sh | mail -s "ATP_Re Health Check" admin@example.com
```
