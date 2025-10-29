# ATP_Re Stage 2 Implementation Guide

## Overview

Stage 2 implements the core data models and database infrastructure for the ATP (Automatic Train Protection) system. This Python reimplementation is based on the original Java codebase analysis and modernizes the architecture with best practices.

## Architecture

### Data Models

The system uses Python dataclasses for type-safe, validated data models:

```
ATPMission       - Core mission identification
  ├── Record     - Time-series data (dynamic, status, VDX)
  ├── Event      - Discrete events (buttons, messages, failures)
  ├── Balise     - Track-side equipment data (BTM)
  └── Station    - Railway station information
```

### Database Schema

PostgreSQL database with optimized schema:

- **6 core tables**: stations, atp_missions, records, events, balises, btm_fragments
- **JSONB support**: Flexible data storage for varying record types
- **Efficient indexing**: GIN indexes for JSONB, composite indexes for queries
- **Data integrity**: Foreign keys, constraints, cascading deletes
- **Automatic timestamps**: Triggers for created_at/updated_at

## Quick Start

### 1. Installation

```bash
# Clone repository
git clone https://github.com/Lawliet0813/ATP_Re.git
cd ATP_Re

# Install dependencies
pip install -r requirements.txt

# Install in development mode
pip install -e .
```

### 2. Database Setup

```bash
# Create PostgreSQL database
createdb atp_re

# Set environment variables
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=atp_re
export DB_USER=postgres
export DB_PASSWORD=your_password
```

```python
# Initialize schema
from atp_re.database import DatabaseManager

db = DatabaseManager.from_env()
db.initialize_schema("src/atp_re/database/schema.sql")
```

### 3. Running Tests

```bash
# Run unit tests
pytest tests/unit/ -v

# Run with coverage
pytest tests/unit/ --cov=atp_re --cov-report=html

# Run integration tests (requires database)
export TEST_DB_URL=postgresql://user:pass@localhost/atp_re_test
pytest tests/integration/ -v
```

## Usage Examples

### Working with Missions

```python
from datetime import datetime
from atp_re.models import ATPMission
from atp_re.database import DatabaseManager

# Create database connection
db = DatabaseManager.from_env()

# Create a mission from database parameters
mission = ATPMission.from_database(
    mission_date=datetime(2024, 1, 15, 10, 30),
    work_shift="A001",
    train_running="T123",
    driver_id="D456",
    vehicle_id="V789"
)

# Insert into database
mission_id = db.insert("atp_missions", {
    "mission_date": mission.mission_date,
    "work_shift": mission.work_shift,
    "train_running": mission.train_running,
    "driver_id": mission.driver_id,
    "vehicle_id": mission.vehicle_id,
    "data_source": mission.data_source
})

# Query missions
missions = db.select(
    "atp_missions",
    where={"driver_id": "D456"},
    order_by=["mission_date"],
    limit=10
)
```

### Working with Records

```python
from atp_re.models.record import DynamicRecord, StatusRecord

# Create dynamic record (speed, position, etc.)
record = DynamicRecord(
    mission_id=mission_id,
    timestamp=datetime.now(),
    speed=100.5,
    position=1234.5,
    acceleration=2.3,
    target_speed=120.0
)

# Insert record
record_id = db.insert("records", {
    "mission_id": record.mission_id,
    "record_type": record.record_type.value,
    "timestamp": record.timestamp,
    "data": record.data
})

# Query records for a mission
records = db.execute_query("""
    SELECT * FROM records 
    WHERE mission_id = %s 
    ORDER BY timestamp
""", (mission_id,))
```

### Working with Events

```python
from atp_re.models.event import FailureEvent, BrakeEvent

# Create a failure event
failure = FailureEvent(
    mission_id=mission_id,
    timestamp=datetime.now(),
    failure_type="BALISE_ERROR",
    failure_code="BE001",
    location="KM_123.5",
    is_wayside_failure=True
)

# Insert event
event_id = db.insert("events", {
    "mission_id": failure.mission_id,
    "event_type": failure.event_type.value,
    "severity": failure.severity.value,
    "timestamp": failure.timestamp,
    "message": failure.message,
    "data": failure.data
})

# Query critical events
critical_events = db.execute_query("""
    SELECT * FROM events 
    WHERE severity = 'critical' 
    AND timestamp > NOW() - INTERVAL '7 days'
    ORDER BY timestamp DESC
""")
```

### Working with Stations

```python
from atp_re.models import Station

# Load stations from database
station_data = db.select("stations")

# Create Station objects
stations = [
    Station(
        station_id=s["station_id"],
        name_chinese=s["name_chinese"],
        name_english=s["name_english"],
        line=s["line"],
        kilometer=s["kilometer"]
    )
    for s in station_data
]

# Initialize cache for fast lookups
Station.initialize_cache(stations)

# Fast O(log n) lookups
station = Station.get_by_id(1001)
print(f"Station: {station.name_chinese} ({station.name_english})")

# Get station names
chinese_name = Station.get_chinese_name(1001)
english_name = Station.get_english_name(1001)

# Search by name
results = Station.search_by_name("台北", language="chinese")
```

### Working with Balise Data

```python
from atp_re.models.balise import Balise, BaliseType, BTMFragment, BTMAssembler

# Create balise record
balise = Balise(
    mission_id=mission_id,
    timestamp=datetime.now(),
    balise_id="B001",
    balise_type=BaliseType.FIXED,
    position=123.5,
    telegram_data=b'\x01\x02\x03\x04\x05',
    telegram_type=TelegramType.BALISE
)

# Insert balise
balise_id = db.insert("balises", {
    "mission_id": balise.mission_id,
    "timestamp": balise.timestamp,
    "balise_id": balise.balise_id,
    "balise_type": balise.balise_type.value,
    "telegram_type": balise.telegram_type.value,
    "position": balise.position,
    "telegram_data": balise.telegram_data,
    "is_valid": balise.is_valid
})

# Handle BTM fragments
assembler = BTMAssembler()

# Add fragments as they arrive
fragment1 = BTMFragment(
    mission_id=mission_id,
    timestamp=datetime.now(),
    fragment_number=1,
    total_fragments=3,
    balise_id="B001",
    fragment_data=b'\x01\x02'
)

fragment2 = BTMFragment(
    mission_id=mission_id,
    timestamp=datetime.now(),
    fragment_number=2,
    total_fragments=3,
    balise_id="B001",
    fragment_data=b'\x03\x04'
)

fragment3 = BTMFragment(
    mission_id=mission_id,
    timestamp=datetime.now(),
    fragment_number=3,
    total_fragments=3,
    balise_id="B001",
    fragment_data=b'\x05\x06'
)

# Assemble complete telegram
assembler.add_fragment(fragment1)
assembler.add_fragment(fragment2)
complete_telegram = assembler.add_fragment(fragment3)

print(f"Complete telegram: {complete_telegram.hex()}")
```

## Database Operations

### Connection Management

```python
# Using context manager (recommended)
with db.get_connection() as conn:
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM stations")
    results = cursor.fetchall()

# Using cursor context manager
with db.get_cursor() as cursor:
    cursor.execute("INSERT INTO stations (...) VALUES (...)")
    # Automatically commits on success, rolls back on error
```

### Batch Operations

```python
# Insert multiple rows efficiently
missions = [
    {
        "mission_date": datetime(2024, 1, i),
        "work_shift": f"A{i:03d}",
        "train_running": f"T{i:03d}",
        "driver_id": "D001",
        "vehicle_id": "V001",
        "data_source": "database"
    }
    for i in range(1, 11)
]

count = db.insert_many("atp_missions", missions)
print(f"Inserted {count} missions")
```

### Transaction Management

```python
# Automatic transaction with context manager
with db.get_cursor() as cursor:
    cursor.execute("INSERT INTO atp_missions (...) VALUES (...)")
    cursor.execute("INSERT INTO records (...) VALUES (...)")
    # Both operations committed together
```

### Complex Queries

```python
# Using views
mission_summaries = db.execute_query("""
    SELECT * FROM mission_summary
    WHERE mission_date >= %s
    ORDER BY mission_date DESC
""", (datetime(2024, 1, 1),))

# JSON queries
high_speed_records = db.execute_query("""
    SELECT * FROM records
    WHERE record_type = 'dynamic'
    AND data->>'speed' IS NOT NULL
    AND (data->>'speed')::float > 100
    ORDER BY timestamp
""")

# Aggregation
failure_stats = db.execute_query("""
    SELECT 
        event_type,
        COUNT(*) as count,
        DATE(timestamp) as date
    FROM events
    WHERE event_type LIKE '%failure%'
    GROUP BY event_type, DATE(timestamp)
    ORDER BY date DESC, count DESC
""")
```

## Performance Considerations

### Indexing

The schema includes indexes for common query patterns:

- Mission lookups: `mission_date`, `driver_id`, `vehicle_id`
- Time-series queries: `timestamp`, compound `(mission_id, timestamp)`
- JSON queries: GIN indexes on JSONB columns
- Station lookups: `station_id`

### Caching

Station data is cached in memory for O(log n) lookups:

```python
# Load stations once at startup
stations = load_stations_from_db()
Station.initialize_cache(stations)

# Fast lookups throughout application lifetime
station = Station.get_by_id(1001)  # O(log n)
```

### Connection Pooling

DatabaseManager uses connection pooling for efficient database access:

```python
db = DatabaseManager(
    host="localhost",
    port=5432,
    database="atp_re",
    user="postgres",
    password="password",
    min_connections=2,
    max_connections=20
)
```

## Testing

### Unit Tests

Test individual models without database:

```bash
pytest tests/unit/ -v
```

Coverage: 97-100% for all models

### Integration Tests

Test database operations (requires PostgreSQL):

```bash
export TEST_DB_URL=postgresql://user:pass@localhost/atp_re_test
pytest tests/integration/ -v
```

### Test Structure

```
tests/
├── unit/
│   ├── test_atp_mission.py   # Mission model tests
│   ├── test_record.py         # Record model tests
│   ├── test_event.py          # Event model tests
│   ├── test_station.py        # Station model tests
│   └── test_balise.py         # Balise model tests
└── integration/
    └── test_database.py       # Database integration tests
```

## Next Steps

Future stages will implement:

1. **Stage 3: Data Decoders**
   - ATP packet decoder
   - MMI message decoder
   - BTM telegram decoder
   - VDX data decoder

2. **Stage 4: File Import/Export**
   - File format handlers
   - Batch import from files
   - Data export to various formats

3. **Stage 5: Analysis & Reporting**
   - Statistical analysis
   - Failure detection
   - Performance metrics
   - Report generation

4. **Stage 6: API & Interface**
   - REST API
   - WebSocket for real-time data
   - Web interface
   - Mobile app support

## Troubleshooting

### Database Connection Issues

```python
# Test connection
try:
    db = DatabaseManager.from_env()
    with db.get_connection() as conn:
        cursor = conn.cursor()
        cursor.execute("SELECT 1")
        print("Connection successful!")
except Exception as e:
    print(f"Connection failed: {e}")
```

### Common Errors

1. **Import Error**: Ensure package is installed: `pip install -e .`
2. **Database Error**: Check environment variables and PostgreSQL status
3. **Test Failure**: Ensure test database is clean before running tests

## Contributing

See CONTRIBUTING.md for guidelines on:
- Code style (Black, Flake8)
- Testing requirements
- Documentation standards
- Pull request process

## License

[To be determined]
