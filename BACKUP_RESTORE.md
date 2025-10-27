# Backup and Restore Guide

Complete guide for backing up and restoring ATP_Re system data.

## Table of Contents

1. [Overview](#overview)
2. [Backup Strategy](#backup-strategy)
3. [Manual Backup](#manual-backup)
4. [Automated Backup](#automated-backup)
5. [Restore Procedures](#restore-procedures)
6. [Disaster Recovery](#disaster-recovery)
7. [Testing Backups](#testing-backups)

---

## Overview

### What Gets Backed Up

ATP_Re backups include:
- **Database:** PostgreSQL database with all mission, record, and event data
- **Uploaded Files:** User-uploaded ATP data files
- **Reports:** Generated reports and analysis results
- **Configuration:** Environment configuration and settings

### Backup Types

1. **Full Backup:** Complete system backup (database + files + config)
2. **Database Only:** Just the PostgreSQL database
3. **Files Only:** Uploaded files and reports
4. **Configuration:** System configuration files

---

## Backup Strategy

### Recommended Schedule

| Type | Frequency | Retention | Storage |
|------|-----------|-----------|---------|
| Full Backup | Daily | 7 days | Local + Remote |
| Database | Hourly | 24 hours | Local |
| Files | Daily | 30 days | Remote |
| Configuration | On Change | 90 days | Version Control |

### Storage Locations

1. **Local:** `/var/lib/atpre/backups/`
2. **Remote:** Cloud storage (S3, Azure Blob, etc.)
3. **Offsite:** Secondary backup location

---

## Manual Backup

### Using Backup Script

The provided backup script handles all backup operations:

```bash
# Run full backup
sudo /opt/atpre/app/scripts/backup.sh

# Backup is created in:
# /var/lib/atpre/backups/backup_YYYYMMDD_HHMMSS/
```

### Manual Database Backup

```bash
# PostgreSQL backup
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
PGPASSWORD="your_password" pg_dump \
  -h localhost \
  -U atp_user \
  -d atp_re \
  -F c \
  -f "/var/lib/atpre/backups/db_backup_$TIMESTAMP.backup"

# Alternative: SQL dump
PGPASSWORD="your_password" pg_dump \
  -h localhost \
  -U atp_user \
  -d atp_re \
  > "/var/lib/atpre/backups/db_backup_$TIMESTAMP.sql"
```

### Manual File Backup

```bash
# Backup uploaded files
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
tar -czf "/var/lib/atpre/backups/files_$TIMESTAMP.tar.gz" \
  -C /var/lib/atpre uploads reports

# Backup configuration
cp /opt/atpre/app/.env "/var/lib/atpre/backups/env_backup_$TIMESTAMP"
```

### Verify Backup Integrity

```bash
# Check backup files exist
ls -lh /var/lib/atpre/backups/backup_*/

# Verify database backup
pg_restore --list /var/lib/atpre/backups/backup_*/database.backup | head -20

# Verify archive integrity
tar -tzf /var/lib/atpre/backups/backup_*/uploads.tar.gz | head -10
```

---

## Automated Backup

### Setup Cron Job

```bash
# Edit crontab
sudo crontab -e

# Add daily backup at 2 AM
0 2 * * * /opt/atpre/app/scripts/backup.sh >> /var/log/atpre/backup.log 2>&1

# Add hourly database backup
0 * * * * /opt/atpre/app/scripts/backup-db-only.sh >> /var/log/atpre/backup-db.log 2>&1
```

### Database-Only Backup Script

Create `/opt/atpre/app/scripts/backup-db-only.sh`:

```bash
#!/bin/bash
set -e

BACKUP_DIR="/var/lib/atpre/backups/db"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)

mkdir -p "$BACKUP_DIR"

# Load environment
if [ -f "/opt/atpre/app/.env" ]; then
    export $(grep -v '^#' /opt/atpre/app/.env | xargs)
fi

# Backup database
PGPASSWORD="$DB_PASSWORD" pg_dump \
    -h "$DB_HOST" \
    -U "$DB_USER" \
    -d "$DB_NAME" \
    -F c \
    -f "$BACKUP_DIR/db_$TIMESTAMP.backup"

# Keep only last 24 backups
find "$BACKUP_DIR" -name "db_*.backup" -type f | sort -r | tail -n +25 | xargs rm -f

echo "Database backup completed: $BACKUP_DIR/db_$TIMESTAMP.backup"
```

### Remote Backup Sync

#### To AWS S3

```bash
# Install AWS CLI
pip install awscli

# Configure AWS credentials
aws configure

# Create sync script
cat > /opt/atpre/app/scripts/sync-to-s3.sh << 'EOF'
#!/bin/bash
set -e

BACKUP_DIR="/var/lib/atpre/backups"
S3_BUCKET="s3://your-bucket-name/atp-re-backups"

# Sync backups to S3
aws s3 sync "$BACKUP_DIR" "$S3_BUCKET" \
    --storage-class STANDARD_IA \
    --exclude "*" \
    --include "backup_*/*" \
    --delete

echo "Backup synced to S3: $S3_BUCKET"
EOF

chmod +x /opt/atpre/app/scripts/sync-to-s3.sh

# Add to crontab after daily backup
0 3 * * * /opt/atpre/app/scripts/sync-to-s3.sh >> /var/log/atpre/s3-sync.log 2>&1
```

#### To Azure Blob Storage

```bash
# Install Azure CLI
curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash

# Login
az login

# Create sync script
cat > /opt/atpre/app/scripts/sync-to-azure.sh << 'EOF'
#!/bin/bash
set -e

BACKUP_DIR="/var/lib/atpre/backups"
STORAGE_ACCOUNT="yourstorageaccount"
CONTAINER="atp-re-backups"

# Sync to Azure
az storage blob upload-batch \
    --account-name "$STORAGE_ACCOUNT" \
    --destination "$CONTAINER" \
    --source "$BACKUP_DIR" \
    --pattern "backup_*/*"

echo "Backup synced to Azure: $CONTAINER"
EOF

chmod +x /opt/atpre/app/scripts/sync-to-azure.sh
```

### Backup Monitoring

Create monitoring script `/opt/atpre/app/scripts/check-backup-status.sh`:

```bash
#!/bin/bash

BACKUP_DIR="/var/lib/atpre/backups"
MAX_AGE_HOURS=26

# Find latest backup
LATEST_BACKUP=$(find "$BACKUP_DIR" -name "backup_*" -type d | sort -r | head -1)

if [ -z "$LATEST_BACKUP" ]; then
    echo "ERROR: No backups found"
    exit 1
fi

# Check backup age
BACKUP_TIME=$(stat -c %Y "$LATEST_BACKUP")
CURRENT_TIME=$(date +%s)
AGE_HOURS=$(( ($CURRENT_TIME - $BACKUP_TIME) / 3600 ))

if [ $AGE_HOURS -gt $MAX_AGE_HOURS ]; then
    echo "WARNING: Latest backup is $AGE_HOURS hours old"
    echo "Backup: $LATEST_BACKUP"
    exit 1
else
    echo "OK: Latest backup is $AGE_HOURS hours old"
    echo "Backup: $LATEST_BACKUP"
    
    # Check backup contents
    if [ -f "$LATEST_BACKUP/database.backup" ]; then
        DB_SIZE=$(du -h "$LATEST_BACKUP/database.backup" | cut -f1)
        echo "Database backup size: $DB_SIZE"
    else
        echo "WARNING: Database backup missing"
    fi
fi
```

Add to monitoring system or cron:
```bash
0 9 * * * /opt/atpre/app/scripts/check-backup-status.sh | mail -s "ATP_Re Backup Status" admin@example.com
```

---

## Restore Procedures

### Full System Restore

```bash
# 1. Stop services
sudo systemctl stop atpre-ui atpre-api

# 2. Choose backup to restore
BACKUP_DIR="/var/lib/atpre/backups/backup_20240127_020000"

# 3. Restore database
PGPASSWORD="your_password" pg_restore \
    -h localhost \
    -U atp_user \
    -d atp_re \
    --clean \
    --if-exists \
    "$BACKUP_DIR/database.backup"

# 4. Restore files
tar -xzf "$BACKUP_DIR/uploads.tar.gz" -C /var/lib/atpre/
tar -xzf "$BACKUP_DIR/reports.tar.gz" -C /var/lib/atpre/

# 5. Restore configuration
cp "$BACKUP_DIR/env.backup" /opt/atpre/app/.env

# 6. Fix permissions
sudo chown -R atpre:atpre /var/lib/atpre/uploads
sudo chown -R atpre:atpre /var/lib/atpre/reports
sudo chown atpre:atpre /opt/atpre/app/.env

# 7. Start services
sudo systemctl start atpre-api atpre-ui

# 8. Verify
curl http://localhost:8000/health
```

### Database-Only Restore

```bash
# Stop API to prevent writes
sudo systemctl stop atpre-api

# Restore database
PGPASSWORD="your_password" pg_restore \
    -h localhost \
    -U atp_user \
    -d atp_re \
    --clean \
    --if-exists \
    /var/lib/atpre/backups/db/db_20240127_140000.backup

# Restart API
sudo systemctl start atpre-api
```

### Selective Table Restore

```bash
# Restore specific table
PGPASSWORD="your_password" pg_restore \
    -h localhost \
    -U atp_user \
    -d atp_re \
    --table=atp_missions \
    /var/lib/atpre/backups/backup_*/database.backup
```

### Point-in-Time Recovery

For point-in-time recovery, PostgreSQL WAL archiving must be enabled.

```bash
# 1. Configure WAL archiving in postgresql.conf
wal_level = replica
archive_mode = on
archive_command = 'cp %p /var/lib/atpre/backups/wal/%f'

# 2. Take base backup
PGPASSWORD="your_password" pg_basebackup \
    -h localhost \
    -U atp_user \
    -D /var/lib/atpre/backups/base \
    -Ft -z -P

# 3. Restore to point in time
# Stop PostgreSQL
sudo systemctl stop postgresql

# Restore base backup
cd /var/lib/postgresql/*/main
rm -rf *
tar -xzf /var/lib/atpre/backups/base/base.tar.gz

# Create recovery.conf
cat > recovery.conf << EOF
restore_command = 'cp /var/lib/atpre/backups/wal/%f %p'
recovery_target_time = '2024-01-27 14:30:00'
recovery_target_action = 'promote'
EOF

# Start PostgreSQL
sudo systemctl start postgresql
```

---

## Disaster Recovery

### Complete System Recovery

In case of total system failure:

```bash
# 1. Provision new server
# 2. Install ATP_Re
sudo /opt/atpre/app/scripts/install.sh

# 3. Download backups from remote storage
aws s3 sync s3://your-bucket/atp-re-backups /var/lib/atpre/backups/

# 4. Restore latest backup
LATEST_BACKUP=$(ls -td /var/lib/atpre/backups/backup_* | head -1)
# Follow "Full System Restore" procedure above

# 5. Verify system
curl http://localhost:8000/health
```

### Database Recovery from Corruption

```bash
# 1. Try to start PostgreSQL in single-user mode
sudo -u postgres postgres --single -D /var/lib/postgresql/*/main atp_re

# 2. If that fails, restore from backup
sudo systemctl stop postgresql

# 3. Backup corrupted data
sudo mv /var/lib/postgresql/*/main /var/lib/postgresql/*/main.corrupt

# 4. Restore from backup
# Follow "Database-Only Restore" procedure

# 5. Verify data integrity
sudo -u postgres psql atp_re -c "SELECT count(*) FROM atp_missions;"
```

### Recovery Testing

Regularly test recovery procedures:

```bash
#!/bin/bash
# test-recovery.sh

echo "Starting recovery test..."

# 1. Create test database
sudo -u postgres psql -c "CREATE DATABASE atp_re_test;"

# 2. Restore backup to test database
LATEST_BACKUP=$(ls -td /var/lib/atpre/backups/backup_* | head -1)
PGPASSWORD="your_password" pg_restore \
    -h localhost \
    -U atp_user \
    -d atp_re_test \
    "$LATEST_BACKUP/database.backup"

# 3. Verify data
ROW_COUNT=$(PGPASSWORD="your_password" psql -h localhost -U atp_user -d atp_re_test -tAc "SELECT count(*) FROM atp_missions;")
echo "Restored $ROW_COUNT rows in atp_missions table"

# 4. Cleanup
sudo -u postgres psql -c "DROP DATABASE atp_re_test;"

echo "Recovery test completed successfully"
```

Run monthly:
```bash
0 3 1 * * /opt/atpre/app/scripts/test-recovery.sh >> /var/log/atpre/recovery-test.log 2>&1
```

---

## Testing Backups

### Backup Verification Checklist

- [ ] Backup files exist and are not zero-byte
- [ ] Database backup can be listed with pg_restore
- [ ] Archive files can be extracted without errors
- [ ] Backup is within expected size range
- [ ] Backup timestamp is recent
- [ ] Remote backup sync completed successfully
- [ ] Test restore completes without errors
- [ ] Restored data matches source data

### Automated Verification

```bash
#!/bin/bash
# verify-backup.sh

BACKUP_DIR=$1

if [ ! -d "$BACKUP_DIR" ]; then
    echo "ERROR: Backup directory not found: $BACKUP_DIR"
    exit 1
fi

echo "Verifying backup: $BACKUP_DIR"

# Check database backup
if [ -f "$BACKUP_DIR/database.backup" ]; then
    if pg_restore --list "$BACKUP_DIR/database.backup" > /dev/null 2>&1; then
        echo "✓ Database backup is valid"
    else
        echo "✗ Database backup is corrupted"
        exit 1
    fi
else
    echo "✗ Database backup not found"
    exit 1
fi

# Check file archives
for archive in uploads.tar.gz reports.tar.gz; do
    if [ -f "$BACKUP_DIR/$archive" ]; then
        if tar -tzf "$BACKUP_DIR/$archive" > /dev/null 2>&1; then
            echo "✓ $archive is valid"
        else
            echo "✗ $archive is corrupted"
            exit 1
        fi
    fi
done

# Check configuration
if [ -f "$BACKUP_DIR/env.backup" ]; then
    echo "✓ Configuration backup exists"
else
    echo "⚠ Configuration backup not found"
fi

echo "Backup verification completed successfully"
```

---

## Best Practices

### Backup Best Practices

1. **3-2-1 Rule:** Keep 3 copies of data, on 2 different media, with 1 offsite
2. **Test Regularly:** Test restores at least monthly
3. **Automate:** Use automated backup scripts and monitoring
4. **Encrypt:** Encrypt backups, especially for offsite storage
5. **Document:** Keep updated documentation of backup procedures
6. **Monitor:** Set up alerts for backup failures
7. **Verify:** Always verify backup integrity after creation

### Security Considerations

```bash
# Encrypt backups before uploading to cloud
gpg --symmetric --cipher-algo AES256 backup.tar.gz

# Set restrictive permissions on backups
chmod 600 /var/lib/atpre/backups/backup_*/database.backup

# Store passwords in secure location
# Use vault or secrets manager, not plain text
```

### Storage Management

```bash
# Monitor backup storage usage
du -sh /var/lib/atpre/backups/

# Cleanup old backups (keep last 7 days)
find /var/lib/atpre/backups -name "backup_*" -type d -mtime +7 -exec rm -rf {} \;

# Archive old backups to cheaper storage
find /var/lib/atpre/backups -name "backup_*" -type d -mtime +30 | \
while read dir; do
    tar -czf "$dir.tar.gz" -C "$(dirname $dir)" "$(basename $dir)"
    rm -rf "$dir"
done
```

---

## Troubleshooting

### Backup Failures

**Problem:** Backup script fails with "disk full" error

**Solution:**
```bash
# Check disk space
df -h /var/lib/atpre/backups

# Clean up old backups
sudo /opt/atpre/app/scripts/cleanup-old-backups.sh

# Consider moving backups to larger volume
```

**Problem:** Database backup times out

**Solution:**
```bash
# Use directory format for faster backups
pg_dump -F d -j 4 -f /path/to/backup/dir

# Or compress after backup
pg_dump -F p | gzip > backup.sql.gz
```

### Restore Failures

**Problem:** Restore fails with "permission denied"

**Solution:**
```bash
# Ensure database user has necessary privileges
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE atp_re TO atp_user;"
```

**Problem:** Restored data is incomplete

**Solution:**
```bash
# Check backup was complete
pg_restore --list backup.backup | wc -l

# Use --verbose for detailed restore log
pg_restore --verbose backup.backup 2>&1 | tee restore.log
```

---

## Emergency Contacts

In case of critical data loss:
- Database Administrator: [Contact Info]
- System Administrator: [Contact Info]
- Cloud Provider Support: [Contact Info]

## Additional Resources

- PostgreSQL Backup Documentation: https://www.postgresql.org/docs/current/backup.html
- AWS S3 Documentation: https://docs.aws.amazon.com/s3/
- Azure Backup Documentation: https://docs.microsoft.com/azure/backup/
