#!/bin/bash
#
# ATP_Re Installation Script
# Automated installation and setup for ATP_Re system
#

set -e  # Exit on error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configuration
APP_NAME="ATP_Re"
APP_USER="atpre"
APP_DIR="/opt/atpre"
VENV_DIR="$APP_DIR/venv"
LOG_DIR="/var/log/atpre"
DATA_DIR="/var/lib/atpre"
PYTHON_VERSION="3.9"

# Functions
log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

check_root() {
    if [ "$EUID" -ne 0 ]; then
        log_error "This script must be run as root"
        exit 1
    fi
}

check_os() {
    if [ -f /etc/os-release ]; then
        . /etc/os-release
        OS=$ID
        VER=$VERSION_ID
        log_info "Detected OS: $OS $VER"
    else
        log_error "Cannot detect OS"
        exit 1
    fi
}

install_dependencies() {
    log_info "Installing system dependencies..."
    
    if [ "$OS" = "ubuntu" ] || [ "$OS" = "debian" ]; then
        apt-get update
        apt-get install -y \
            python3 \
            python3-pip \
            python3-venv \
            postgresql-client \
            redis-tools \
            git \
            curl \
            supervisor \
            nginx
    elif [ "$OS" = "centos" ] || [ "$OS" = "rhel" ]; then
        yum install -y \
            python3 \
            python3-pip \
            postgresql \
            redis \
            git \
            curl \
            supervisor \
            nginx
    else
        log_error "Unsupported OS: $OS"
        exit 1
    fi
    
    log_info "System dependencies installed"
}

create_user() {
    if id "$APP_USER" &>/dev/null; then
        log_warn "User $APP_USER already exists"
    else
        log_info "Creating application user: $APP_USER"
        useradd -r -m -d "$APP_DIR" -s /bin/bash "$APP_USER"
        log_info "User created"
    fi
}

create_directories() {
    log_info "Creating application directories..."
    
    mkdir -p "$APP_DIR"
    mkdir -p "$LOG_DIR"
    mkdir -p "$DATA_DIR/uploads"
    mkdir -p "$DATA_DIR/reports"
    mkdir -p "$DATA_DIR/backups"
    
    # Set ownership
    chown -R "$APP_USER:$APP_USER" "$APP_DIR"
    chown -R "$APP_USER:$APP_USER" "$LOG_DIR"
    chown -R "$APP_USER:$APP_USER" "$DATA_DIR"
    
    # Set permissions
    chmod 755 "$APP_DIR"
    chmod 755 "$LOG_DIR"
    chmod 755 "$DATA_DIR"
    
    log_info "Directories created"
}

install_application() {
    log_info "Installing ATP_Re application..."
    
    # Clone repository if not exists
    if [ ! -d "$APP_DIR/app" ]; then
        log_info "Cloning repository..."
        sudo -u "$APP_USER" git clone https://github.com/Lawliet0813/ATP_Re.git "$APP_DIR/app"
    else
        log_warn "Application directory already exists, skipping clone"
    fi
    
    # Create virtual environment
    log_info "Creating Python virtual environment..."
    sudo -u "$APP_USER" python3 -m venv "$VENV_DIR"
    
    # Install Python dependencies
    log_info "Installing Python dependencies..."
    sudo -u "$APP_USER" "$VENV_DIR/bin/pip" install --upgrade pip
    sudo -u "$APP_USER" "$VENV_DIR/bin/pip" install -r "$APP_DIR/app/requirements.txt"
    sudo -u "$APP_USER" "$VENV_DIR/bin/pip" install gunicorn
    
    log_info "Application installed"
}

configure_environment() {
    log_info "Configuring environment..."
    
    if [ ! -f "$APP_DIR/app/.env" ]; then
        log_info "Creating .env file..."
        cat > "$APP_DIR/app/.env" << EOF
# ATP_Re Configuration
API_TITLE=ATP_Re API
API_VERSION=1.0.0
API_PREFIX=/api/v1

HOST=0.0.0.0
PORT=8000

# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=atp_re
DB_USER=atp_user
DB_PASSWORD=change_me

# Redis Cache
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_DB=0
REDIS_ENABLED=true

# File Upload
UPLOAD_DIR=$DATA_DIR/uploads
MAX_UPLOAD_SIZE=104857600

# Logging
LOG_LEVEL=INFO
JSON_LOGS=true

# Monitoring
ENVIRONMENT=production

# CORS
CORS_ORIGINS=["http://localhost:8501"]
EOF
        chown "$APP_USER:$APP_USER" "$APP_DIR/app/.env"
        log_warn "Please edit $APP_DIR/app/.env with your configuration"
    else
        log_warn ".env file already exists, skipping"
    fi
}

install_services() {
    log_info "Installing systemd services..."
    
    # API service
    cat > /etc/systemd/system/atpre-api.service << EOF
[Unit]
Description=ATP_Re API Service
After=network.target postgresql.service redis.service

[Service]
Type=exec
User=$APP_USER
Group=$APP_USER
WorkingDirectory=$APP_DIR/app
Environment="PATH=$VENV_DIR/bin"
ExecStart=$VENV_DIR/bin/gunicorn api.main:app \\
    --workers 4 \\
    --worker-class uvicorn.workers.UvicornWorker \\
    --bind 0.0.0.0:8000 \\
    --access-logfile $LOG_DIR/api-access.log \\
    --error-logfile $LOG_DIR/api-error.log \\
    --log-level info
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
EOF

    # UI service
    cat > /etc/systemd/system/atpre-ui.service << EOF
[Unit]
Description=ATP_Re Web UI Service
After=network.target atpre-api.service

[Service]
Type=exec
User=$APP_USER
Group=$APP_USER
WorkingDirectory=$APP_DIR/app/streamlit_ui
Environment="PATH=$VENV_DIR/bin"
ExecStart=$VENV_DIR/bin/streamlit run app.py \\
    --server.port 8501 \\
    --server.address 0.0.0.0 \\
    --server.headless true
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
EOF

    # Reload systemd
    systemctl daemon-reload
    
    log_info "Services installed"
}

setup_redis() {
    log_info "Setting up Redis..."
    
    if systemctl is-active --quiet redis-server || systemctl is-active --quiet redis; then
        log_info "Redis is already running"
    else
        log_info "Starting Redis..."
        systemctl enable redis-server 2>/dev/null || systemctl enable redis 2>/dev/null || true
        systemctl start redis-server 2>/dev/null || systemctl start redis 2>/dev/null || true
    fi
}

print_summary() {
    echo ""
    echo "======================================"
    echo "  ATP_Re Installation Complete!"
    echo "======================================"
    echo ""
    echo "Next steps:"
    echo "1. Configure database connection in: $APP_DIR/app/.env"
    echo "2. Initialize database schema"
    echo "3. Start services:"
    echo "   sudo systemctl start atpre-api"
    echo "   sudo systemctl start atpre-ui"
    echo "4. Enable services to start on boot:"
    echo "   sudo systemctl enable atpre-api"
    echo "   sudo systemctl enable atpre-ui"
    echo ""
    echo "Service endpoints:"
    echo "  API:     http://localhost:8000"
    echo "  Web UI:  http://localhost:8501"
    echo "  Metrics: http://localhost:8000/metrics"
    echo ""
    echo "Logs location: $LOG_DIR"
    echo "Data location: $DATA_DIR"
    echo ""
}

# Main installation flow
main() {
    log_info "Starting ATP_Re installation..."
    
    check_root
    check_os
    install_dependencies
    create_user
    create_directories
    install_application
    configure_environment
    setup_redis
    install_services
    
    print_summary
}

# Run main function
main
