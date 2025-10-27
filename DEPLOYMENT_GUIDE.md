# Deployment Guide

This guide covers deploying the ATP_Re API and Web UI in production environments.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Development Deployment](#development-deployment)
3. [Production Deployment](#production-deployment)
4. [Docker Deployment](#docker-deployment)
5. [Cloud Deployment](#cloud-deployment)
6. [Monitoring and Maintenance](#monitoring-and-maintenance)

## Prerequisites

### System Requirements

- **Operating System**: Linux (Ubuntu 20.04+), Windows Server 2019+, or macOS
- **Python**: 3.8 or higher
- **Database**: SQL Server 2016 or higher
- **Memory**: Minimum 2GB RAM, recommended 4GB+
- **Storage**: 10GB+ for application and data
- **Network**: Port 8000 (API), Port 8501 (Web UI)

### Software Dependencies

```bash
# Install Python 3.8+
sudo apt-get update
sudo apt-get install python3 python3-pip python3-venv

# Install SQL Server ODBC drivers (Linux)
curl https://packages.microsoft.com/keys/microsoft.asc | apt-key add -
curl https://packages.microsoft.com/config/ubuntu/20.04/prod.list > /etc/apt/sources.list.d/mssql-release.list
apt-get update
ACCEPT_EULA=Y apt-get install -y msodbcsql18 unixodbc-dev
```

## Development Deployment

### Quick Start (Development)

1. **Clone repository:**
```bash
git clone https://github.com/Lawliet0813/ATP_Re.git
cd ATP_Re
```

2. **Create virtual environment:**
```bash
python3 -m venv venv
source venv/bin/activate  # Linux/Mac
# or
venv\Scripts\activate  # Windows
```

3. **Install dependencies:**
```bash
pip install -r requirements.txt
```

4. **Configure environment:**
```bash
cp .env.example .env
# Edit .env with your configuration
nano .env
```

5. **Initialize database:**
```bash
python3 -c "from api.app.models import init_db; from config.settings import settings; from api.app.models import get_database_url; init_db(get_database_url(settings))"
```

6. **Start services:**
```bash
# Terminal 1: Start API
cd api
python main.py

# Terminal 2: Start Web UI
cd streamlit_ui
streamlit run app.py
```

## Production Deployment

### 1. System Setup

```bash
# Create application user
sudo useradd -m -s /bin/bash atpre
sudo usermod -aG sudo atpre

# Create application directories
sudo mkdir -p /opt/atpre
sudo mkdir -p /var/log/atpre
sudo mkdir -p /var/lib/atpre/uploads
sudo mkdir -p /var/lib/atpre/reports

# Set ownership
sudo chown -R atpre:atpre /opt/atpre
sudo chown -R atpre:atpre /var/log/atpre
sudo chown -R atpre:atpre /var/lib/atpre
```

### 2. Application Setup

```bash
# Switch to application user
sudo su - atpre

# Clone repository
cd /opt/atpre
git clone https://github.com/Lawliet0813/ATP_Re.git app
cd app

# Create virtual environment
python3 -m venv venv
source venv/bin/activate

# Install dependencies
pip install -r requirements.txt
pip install gunicorn  # WSGI server for production
```

### 3. Configuration

Create production configuration file:

```bash
# /opt/atpre/app/.env
API_TITLE=ATP_Re API
API_VERSION=1.0.0
API_PREFIX=/api/v1

HOST=0.0.0.0
PORT=8000

DB_HOST=your-db-server.example.com
DB_PORT=1433
DB_NAME=ATP_DB
DB_USER=atp_production_user
DB_PASSWORD=your_secure_password_here

UPLOAD_DIR=/var/lib/atpre/uploads
MAX_UPLOAD_SIZE=104857600

CORS_ORIGINS=["https://your-domain.com", "https://www.your-domain.com"]

LOG_LEVEL=INFO
```

### 4. Systemd Service Files

Create systemd service for API:

```bash
# /etc/systemd/system/atpre-api.service
[Unit]
Description=ATP_Re FastAPI Service
After=network.target

[Service]
Type=exec
User=atpre
Group=atpre
WorkingDirectory=/opt/atpre/app
Environment="PATH=/opt/atpre/app/venv/bin"
ExecStart=/opt/atpre/app/venv/bin/gunicorn api.main:app \
    --workers 4 \
    --worker-class uvicorn.workers.UvicornWorker \
    --bind 0.0.0.0:8000 \
    --access-logfile /var/log/atpre/api-access.log \
    --error-logfile /var/log/atpre/api-error.log \
    --log-level info
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
```

Create systemd service for Web UI:

```bash
# /etc/systemd/system/atpre-ui.service
[Unit]
Description=ATP_Re Streamlit Web UI
After=network.target atpre-api.service

[Service]
Type=exec
User=atpre
Group=atpre
WorkingDirectory=/opt/atpre/app/streamlit_ui
Environment="PATH=/opt/atpre/app/venv/bin"
ExecStart=/opt/atpre/app/venv/bin/streamlit run app.py \
    --server.port 8501 \
    --server.address 0.0.0.0 \
    --server.headless true \
    --browser.serverAddress your-domain.com
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
```

### 5. Enable and Start Services

```bash
# Reload systemd
sudo systemctl daemon-reload

# Enable services to start on boot
sudo systemctl enable atpre-api
sudo systemctl enable atpre-ui

# Start services
sudo systemctl start atpre-api
sudo systemctl start atpre-ui

# Check status
sudo systemctl status atpre-api
sudo systemctl status atpre-ui

# View logs
sudo journalctl -u atpre-api -f
sudo journalctl -u atpre-ui -f
```

### 6. Nginx Reverse Proxy

Install and configure Nginx:

```bash
sudo apt-get install nginx
```

Create Nginx configuration:

```nginx
# /etc/nginx/sites-available/atpre
server {
    listen 80;
    server_name your-domain.com www.your-domain.com;

    # Redirect HTTP to HTTPS
    return 301 https://$server_name$request_uri;
}

server {
    listen 443 ssl http2;
    server_name your-domain.com www.your-domain.com;

    # SSL Configuration
    ssl_certificate /etc/letsencrypt/live/your-domain.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/your-domain.com/privkey.pem;
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;

    # Security headers
    add_header Strict-Transport-Security "max-age=31536000" always;
    add_header X-Frame-Options "SAMEORIGIN" always;
    add_header X-Content-Type-Options "nosniff" always;
    add_header X-XSS-Protection "1; mode=block" always;

    # API
    location /api/ {
        proxy_pass http://127.0.0.1:8000;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        
        # WebSocket support
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_read_timeout 86400;
    }

    # Web UI
    location / {
        proxy_pass http://127.0.0.1:8501;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        
        # WebSocket support for Streamlit
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_read_timeout 86400;
    }

    # File uploads (increase limits)
    client_max_body_size 100M;
    client_body_buffer_size 128k;
}
```

Enable the site:

```bash
sudo ln -s /etc/nginx/sites-available/atpre /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl restart nginx
```

### 7. SSL Certificate (Let's Encrypt)

```bash
# Install certbot
sudo apt-get install certbot python3-certbot-nginx

# Obtain certificate
sudo certbot --nginx -d your-domain.com -d www.your-domain.com

# Test renewal
sudo certbot renew --dry-run
```

## Docker Deployment

### 1. Dockerfile

Create Dockerfile for API:

```dockerfile
# Dockerfile.api
FROM python:3.11-slim

WORKDIR /app

# Install system dependencies
RUN apt-get update && apt-get install -y \
    curl \
    gnupg \
    unixodbc-dev \
    && rm -rf /var/lib/apt/lists/*

# Install SQL Server ODBC driver
RUN curl https://packages.microsoft.com/keys/microsoft.asc | apt-key add - && \
    curl https://packages.microsoft.com/config/debian/11/prod.list > /etc/apt/sources.list.d/mssql-release.list && \
    apt-get update && \
    ACCEPT_EULA=Y apt-get install -y msodbcsql18

# Copy requirements and install Python dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt gunicorn

# Copy application code
COPY api/ ./api/
COPY config/ ./config/

# Create uploads directory
RUN mkdir -p /app/uploads

EXPOSE 8000

CMD ["gunicorn", "api.main:app", "--workers", "4", "--worker-class", "uvicorn.workers.UvicornWorker", "--bind", "0.0.0.0:8000"]
```

Create Dockerfile for Web UI:

```dockerfile
# Dockerfile.ui
FROM python:3.11-slim

WORKDIR /app

# Install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy application code
COPY streamlit_ui/ ./streamlit_ui/

EXPOSE 8501

CMD ["streamlit", "run", "streamlit_ui/app.py", "--server.port=8501", "--server.address=0.0.0.0", "--server.headless=true"]
```

### 2. Docker Compose

```yaml
# docker-compose.yml
version: '3.8'

services:
  api:
    build:
      context: .
      dockerfile: Dockerfile.api
    ports:
      - "8000:8000"
    environment:
      - DB_HOST=${DB_HOST}
      - DB_PORT=${DB_PORT}
      - DB_NAME=${DB_NAME}
      - DB_USER=${DB_USER}
      - DB_PASSWORD=${DB_PASSWORD}
    volumes:
      - ./uploads:/app/uploads
      - ./logs:/app/logs
    restart: unless-stopped
    networks:
      - atpre-network

  ui:
    build:
      context: .
      dockerfile: Dockerfile.ui
    ports:
      - "8501:8501"
    environment:
      - API_BASE_URL=http://api:8000
    depends_on:
      - api
    restart: unless-stopped
    networks:
      - atpre-network

networks:
  atpre-network:
    driver: bridge

volumes:
  uploads:
  logs:
```

### 3. Deploy with Docker Compose

```bash
# Build and start
docker-compose up -d

# View logs
docker-compose logs -f

# Stop
docker-compose down

# Rebuild after code changes
docker-compose up -d --build
```

## Cloud Deployment

### AWS Deployment

#### Using EC2:

1. Launch EC2 instance (Ubuntu 20.04, t3.medium or larger)
2. Configure security groups (ports 22, 80, 443)
3. Follow production deployment steps above
4. Use RDS for SQL Server database
5. Use S3 for file storage

#### Using ECS (Container):

1. Build and push Docker images to ECR
2. Create ECS cluster and task definitions
3. Configure Application Load Balancer
4. Use RDS for database
5. Use EFS for shared file storage

### Azure Deployment

1. Create Azure App Service (Python 3.11)
2. Use Azure SQL Database
3. Configure Azure Storage for files
4. Set up Application Gateway for load balancing

### Google Cloud Deployment

1. Use Cloud Run for containers
2. Cloud SQL for database
3. Cloud Storage for files
4. Cloud Load Balancing

## Monitoring and Maintenance

### 1. Log Management

```bash
# Rotate logs
sudo nano /etc/logrotate.d/atpre

/var/log/atpre/*.log {
    daily
    rotate 14
    compress
    delaycompress
    notifempty
    create 0640 atpre atpre
    sharedscripts
    postrotate
        systemctl reload atpre-api atpre-ui > /dev/null 2>&1 || true
    endscript
}
```

### 2. Monitoring

Install monitoring tools:

```bash
# Prometheus + Grafana
docker run -d -p 9090:9090 prom/prometheus
docker run -d -p 3000:3000 grafana/grafana
```

Add metrics endpoint to API:

```python
# api/main.py
from prometheus_fastapi_instrumentator import Instrumentator

Instrumentator().instrument(app).expose(app)
```

### 3. Backup

```bash
# Database backup script
#!/bin/bash
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_DIR="/var/backups/atpre"

# Create backup directory
mkdir -p $BACKUP_DIR

# Backup database
sqlcmd -S your-server -U atp_user -P password \
    -Q "BACKUP DATABASE ATP_DB TO DISK='$BACKUP_DIR/ATP_DB_$DATE.bak'"

# Backup uploaded files
tar -czf $BACKUP_DIR/uploads_$DATE.tar.gz /var/lib/atpre/uploads

# Keep only last 7 days
find $BACKUP_DIR -name "*.bak" -mtime +7 -delete
find $BACKUP_DIR -name "*.tar.gz" -mtime +7 -delete
```

### 4. Updates and Maintenance

```bash
# Update application
cd /opt/atpre/app
sudo -u atpre git pull
sudo -u atpre /opt/atpre/app/venv/bin/pip install -r requirements.txt
sudo systemctl restart atpre-api atpre-ui

# Check service status
sudo systemctl status atpre-api atpre-ui
```

## Troubleshooting

### API not starting

```bash
# Check logs
sudo journalctl -u atpre-api -n 100

# Check port availability
sudo netstat -tulpn | grep 8000

# Test configuration
source /opt/atpre/app/venv/bin/activate
python -c "from config.settings import settings; print(settings)"
```

### Database connection issues

```bash
# Test connection
python3 << EOF
import pymssql
conn = pymssql.connect(
    server='your-server',
    user='atp_user',
    password='password',
    database='ATP_DB'
)
print("Connection successful")
EOF
```

### High memory usage

```bash
# Check memory usage
free -h
docker stats  # If using Docker

# Adjust worker count in service file
# /etc/systemd/system/atpre-api.service
# Reduce --workers count
```

## Security Checklist

- [ ] Use HTTPS in production
- [ ] Configure firewall (ufw/iptables)
- [ ] Use strong database passwords
- [ ] Enable SQL Server encryption
- [ ] Implement rate limiting
- [ ] Regular security updates
- [ ] Monitor access logs
- [ ] Implement authentication
- [ ] Use environment variables for secrets
- [ ] Regular backups

## Performance Tuning

1. **Database**: Add indexes on frequently queried columns
2. **API**: Adjust worker count based on CPU cores
3. **Caching**: Implement Redis for frequently accessed data
4. **CDN**: Use CDN for static assets
5. **Connection Pooling**: Configure appropriate pool sizes

## Conclusion

This deployment guide covers development, production, and containerized deployments. Choose the deployment method that best fits your infrastructure and requirements.
