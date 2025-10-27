#!/bin/bash
#
# ATP_Re Backup Script
# Automated backup for database, files, and configuration
#

set -e

# Colors
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

# Configuration
BACKUP_DIR="/var/lib/atpre/backups"
DATA_DIR="/var/lib/atpre"
APP_DIR="/opt/atpre/app"
RETENTION_DAYS=7

# Database configuration (from .env)
if [ -f "$APP_DIR/.env" ]; then
    export $(grep -v '^#' "$APP_DIR/.env" | xargs)
fi

DB_HOST=${DB_HOST:-localhost}
DB_PORT=${DB_PORT:-5432}
DB_NAME=${DB_NAME:-atp_re}
DB_USER=${DB_USER:-atp_user}

log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

create_backup_dir() {
    TIMESTAMP=$(date +%Y%m%d_%H%M%S)
    BACKUP_SUBDIR="$BACKUP_DIR/backup_$TIMESTAMP"
    
    mkdir -p "$BACKUP_SUBDIR"
    log_info "Backup directory created: $BACKUP_SUBDIR"
    
    echo "$BACKUP_SUBDIR"
}

backup_database() {
    local backup_dir=$1
    log_info "Backing up database..."
    
    # PostgreSQL backup
    if command -v pg_dump &> /dev/null; then
        PGPASSWORD="$DB_PASSWORD" pg_dump \
            -h "$DB_HOST" \
            -p "$DB_PORT" \
            -U "$DB_USER" \
            -d "$DB_NAME" \
            -F c \
            -f "$backup_dir/database.backup"
        
        log_info "Database backup completed: $backup_dir/database.backup"
    else
        log_warn "pg_dump not found, skipping database backup"
    fi
}

backup_files() {
    local backup_dir=$1
    log_info "Backing up uploaded files..."
    
    if [ -d "$DATA_DIR/uploads" ]; then
        tar -czf "$backup_dir/uploads.tar.gz" -C "$DATA_DIR" uploads
        log_info "Files backup completed: $backup_dir/uploads.tar.gz"
    else
        log_warn "Upload directory not found, skipping"
    fi
}

backup_configuration() {
    local backup_dir=$1
    log_info "Backing up configuration..."
    
    # Backup .env file
    if [ -f "$APP_DIR/.env" ]; then
        cp "$APP_DIR/.env" "$backup_dir/env.backup"
        log_info "Configuration backup completed"
    else
        log_warn ".env file not found"
    fi
}

backup_reports() {
    local backup_dir=$1
    log_info "Backing up reports..."
    
    if [ -d "$DATA_DIR/reports" ]; then
        tar -czf "$backup_dir/reports.tar.gz" -C "$DATA_DIR" reports
        log_info "Reports backup completed: $backup_dir/reports.tar.gz"
    else
        log_warn "Reports directory not found, skipping"
    fi
}

create_backup_manifest() {
    local backup_dir=$1
    log_info "Creating backup manifest..."
    
    cat > "$backup_dir/manifest.txt" << EOF
ATP_Re Backup Manifest
======================
Backup Date: $(date)
Hostname: $(hostname)
Database: $DB_NAME
Database Host: $DB_HOST

Files Included:
EOF
    
    ls -lh "$backup_dir" >> "$backup_dir/manifest.txt"
    
    log_info "Manifest created"
}

cleanup_old_backups() {
    log_info "Cleaning up old backups (retention: $RETENTION_DAYS days)..."
    
    find "$BACKUP_DIR" -name "backup_*" -type d -mtime +$RETENTION_DAYS -exec rm -rf {} \; 2>/dev/null || true
    
    # Count remaining backups
    BACKUP_COUNT=$(find "$BACKUP_DIR" -name "backup_*" -type d | wc -l)
    log_info "Current backup count: $BACKUP_COUNT"
}

verify_backup() {
    local backup_dir=$1
    log_info "Verifying backup..."
    
    local errors=0
    
    # Check database backup
    if [ ! -f "$backup_dir/database.backup" ]; then
        log_warn "Database backup file not found"
        errors=$((errors + 1))
    fi
    
    # Check files backup
    if [ ! -f "$backup_dir/uploads.tar.gz" ]; then
        log_warn "Files backup not found"
    fi
    
    if [ $errors -eq 0 ]; then
        log_info "Backup verification passed"
        return 0
    else
        log_error "Backup verification failed with $errors errors"
        return 1
    fi
}

calculate_backup_size() {
    local backup_dir=$1
    local size=$(du -sh "$backup_dir" | cut -f1)
    log_info "Backup size: $size"
}

main() {
    log_info "Starting ATP_Re backup process..."
    
    # Create backup directory
    BACKUP_SUBDIR=$(create_backup_dir)
    
    # Perform backups
    backup_database "$BACKUP_SUBDIR"
    backup_files "$BACKUP_SUBDIR"
    backup_reports "$BACKUP_SUBDIR"
    backup_configuration "$BACKUP_SUBDIR"
    
    # Create manifest
    create_backup_manifest "$BACKUP_SUBDIR"
    
    # Verify backup
    if verify_backup "$BACKUP_SUBDIR"; then
        calculate_backup_size "$BACKUP_SUBDIR"
        log_info "Backup completed successfully: $BACKUP_SUBDIR"
    else
        log_error "Backup completed with warnings"
    fi
    
    # Cleanup old backups
    cleanup_old_backups
    
    echo ""
    echo "Backup Summary:"
    echo "  Location: $BACKUP_SUBDIR"
    echo "  Contents: database, uploads, reports, configuration"
    echo ""
}

# Run main function
main
