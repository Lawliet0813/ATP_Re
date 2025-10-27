# ATP_Re - Python Implementation

Python re-implementation of the Automatic Train Protection (ATP) system with PostgreSQL database.

## Project Structure

```
ATP_Re/
├── src/
│   └── atp_re/
│       ├── models/          # Data models
│       │   ├── atp_mission.py
│       │   ├── record.py
│       │   ├── event.py
│       │   ├── station.py
│       │   └── balise.py
│       ├── database/        # Database management
│       │   ├── manager.py
│       │   └── schema.sql
│       └── utils/           # Utility functions
├── tests/
│   ├── unit/               # Unit tests
│   └── integration/        # Integration tests
├── requirements.txt
├── pyproject.toml
└── README.md
```

## Stage 2: Data Models and Database

This stage implements:

### ✅ Core Data Models
- **ATPMission**: Core mission identification and metadata
- **Record**: Various record types (dynamic, status, VDX, etc.)
- **Event**: Events (buttons, driver messages, failures, brakes)
- **Station**: Railway station information with caching
- **Balise**: BTM (Balise Transmission Module) data with fragment assembly

### ✅ Database Schema
- PostgreSQL schema with tables for all core models
- Indexes for performance optimization
- Foreign key relationships
- Triggers for automatic timestamp updates
- Views for common queries

### ✅ DatabaseManager
- Connection pool management
- CRUD operations
- Transaction support
- Parameterized queries for security
- Context managers for safe resource handling

### ✅ Unit Tests
- 41 unit tests covering all models
- Test data validation
- Test model behavior
- 100% test pass rate

## Installation

```bash
# Install dependencies
pip install -r requirements.txt

# Install in development mode
pip install -e .
```

## Running Tests

```bash
# Run all tests
pytest

# Run with coverage
pytest --cov=atp_re

# Run specific test file
pytest tests/unit/test_atp_mission.py
```

## Database Setup

1. Install PostgreSQL
2. Create database:
   ```sql
   CREATE DATABASE atp_re;
   ```

3. Initialize schema:
   ```python
   from atp_re.database import DatabaseManager
   
   db = DatabaseManager(
       host="localhost",
       port=5432,
       database="atp_re",
       user="postgres",
       password="your_password"
   )
   
   db.initialize_schema("src/atp_re/database/schema.sql")
   ```

## Usage Example

```python
from datetime import datetime
from atp_re.models import ATPMission, Station
from atp_re.database import DatabaseManager

# Create database connection
db = DatabaseManager.from_env()

# Create a mission
mission = ATPMission.from_database(
    mission_date=datetime.now(),
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

print(f"Mission created with ID: {mission_id}")
```

## Features

### Data Models
- Type-safe dataclasses with validation
- Enums for type safety
- Factory methods for different creation scenarios
- Efficient caching for station lookups
- BTM fragment assembly for multi-part telegrams

### Database
- Connection pooling for performance
- Thread-safe operations
- Automatic transaction management
- SQL injection prevention
- Flexible query builders

### Testing
- Comprehensive unit test coverage
- Validation testing
- Edge case testing
- Clear test organization

## Next Steps

Future stages will include:
- Data decoders (ATP, MMI, BTM, VDX)
- File import/export functionality
- Data analysis and reporting
- Web API interface
- User interface

## License

[To be determined]

## Contributors

ATP_Re Contributors
