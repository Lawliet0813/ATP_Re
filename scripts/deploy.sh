#!/bin/bash
#
# ATP_Re Deployment Script
# Deploy updates to production environment
#

set -e

# Colors
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

# Configuration
APP_DIR="/opt/atpre/app"
VENV_DIR="/opt/atpre/venv"
APP_USER="atpre"
BACKUP_DIR="/var/lib/atpre/backups"

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

backup_current() {
    log_info "Creating backup..."
    
    TIMESTAMP=$(date +%Y%m%d_%H%M%S)
    BACKUP_FILE="$BACKUP_DIR/deployment_backup_$TIMESTAMP.tar.gz"
    
    mkdir -p "$BACKUP_DIR"
    
    # Backup application code
    tar -czf "$BACKUP_FILE" -C "$APP_DIR" . 2>/dev/null || true
    
    log_info "Backup created: $BACKUP_FILE"
}

stop_services() {
    log_info "Stopping services..."
    
    systemctl stop atpre-ui 2>/dev/null || log_warn "UI service not running"
    systemctl stop atpre-api 2>/dev/null || log_warn "API service not running"
    
    log_info "Services stopped"
}

update_code() {
    log_info "Updating application code..."
    
    cd "$APP_DIR"
    
    # Stash any local changes
    sudo -u "$APP_USER" git stash 2>/dev/null || true
    
    # Pull latest changes
    sudo -u "$APP_USER" git pull origin main
    
    log_info "Code updated"
}

update_dependencies() {
    log_info "Updating dependencies..."
    
    sudo -u "$APP_USER" "$VENV_DIR/bin/pip" install --upgrade pip
    sudo -u "$APP_USER" "$VENV_DIR/bin/pip" install -r "$APP_DIR/requirements.txt"
    
    log_info "Dependencies updated"
}

run_migrations() {
    log_info "Running database migrations..."
    
    # Add migration commands here when available
    # sudo -u "$APP_USER" "$VENV_DIR/bin/python" manage.py migrate
    
    log_info "Migrations completed"
}

start_services() {
    log_info "Starting services..."
    
    systemctl start atpre-api
    systemctl start atpre-ui
    
    # Wait for services to start
    sleep 3
    
    # Check service status
    if systemctl is-active --quiet atpre-api; then
        log_info "API service started successfully"
    else
        log_error "API service failed to start"
        systemctl status atpre-api
        exit 1
    fi
    
    if systemctl is-active --quiet atpre-ui; then
        log_info "UI service started successfully"
    else
        log_warn "UI service failed to start"
        systemctl status atpre-ui
    fi
}

health_check() {
    log_info "Performing health check..."
    
    # Check API health endpoint
    if curl -f http://localhost:8000/health >/dev/null 2>&1; then
        log_info "API health check passed"
    else
        log_error "API health check failed"
        exit 1
    fi
}

print_summary() {
    echo ""
    echo "======================================"
    echo "  Deployment Complete!"
    echo "======================================"
    echo ""
    echo "Services:"
    echo "  API:  http://localhost:8000"
    echo "  UI:   http://localhost:8501"
    echo ""
    echo "Check logs:"
    echo "  sudo journalctl -u atpre-api -f"
    echo "  sudo journalctl -u atpre-ui -f"
    echo ""
}

rollback() {
    log_error "Deployment failed! Rolling back..."
    
    # Find latest backup
    LATEST_BACKUP=$(ls -t "$BACKUP_DIR"/deployment_backup_*.tar.gz 2>/dev/null | head -1)
    
    if [ -n "$LATEST_BACKUP" ]; then
        log_info "Restoring from backup: $LATEST_BACKUP"
        tar -xzf "$LATEST_BACKUP" -C "$APP_DIR"
        start_services
        log_info "Rollback complete"
    else
        log_error "No backup found for rollback"
    fi
}

main() {
    log_info "Starting deployment..."
    
    check_root
    backup_current
    stop_services
    
    # Try to update and start, rollback on failure
    if ! update_code; then
        rollback
        exit 1
    fi
    
    if ! update_dependencies; then
        rollback
        exit 1
    fi
    
    run_migrations
    start_services
    
    if ! health_check; then
        rollback
        exit 1
    fi
    
    print_summary
}

# Trap errors
trap 'log_error "Deployment failed"; rollback' ERR

# Run main
main
