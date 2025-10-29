# ATP_Re - Python Implementation

Python re-implementation of the Automatic Train Protection (ATP) system with PostgreSQL database.

## 📊 Project Status

**Current Version**: v0.1.0 (Beta)  
**Overall Progress**: 75% Complete  
**Test Pass Rate**: 100% (128/128 tests)

🟢 **Ready for Testing** - Core decoder and CLI tools are production-ready!

📖 **Detailed Status Report**: 
- [中文版本 (Chinese)](PROJECT_STATUS.md) 
- [English Version](PROJECT_STATUS_EN.md)

**Quick Links**:
- [Integration Testing Status](#integration-testing-status)
- [What's Ready to Use](#whats-ready-to-use)
- [Quick Start Guide](#quick-start)

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

## Quick Start

### Option 1: Command-Line Tool (Fastest)
```bash
# 1. Install package
pip install -e .

# 2. Decode ATP RU file
python decode_packets.py tests/RU_file/024423.RU -n 5

# 3. Save as JSON
python decode_packets.py tests/RU_file/024423.RU -f json -o output.json
```

### Option 2: Python API
```python
from atp_re.decoders import RUDecoder, PacketFormatter

# Decode packet
decoder = RUDecoder()
with open('tests/RU_file/024423.RU', 'rb') as f:
    data = f.read(50)  # Read first packet
    result = decoder.decode(data)
    
# Access decoded values
print(f"Speed: {result.data.v_train} km/h")
print(f"Position: {result.data.o_train} m")

# Format for display
formatter = PacketFormatter()
print(formatter.format_packet(result.to_dict()))
```

### Option 3: Web UI (Requires Database)
```bash
# 1. Start database
docker run -d -p 5432:5432 \
  -e POSTGRES_DB=atp_re \
  -e POSTGRES_USER=atp_user \
  -e POSTGRES_PASSWORD=password \
  postgres:15

# 2. Install and configure
pip install -r requirements.txt
cp .env.example .env  # Edit with your database credentials

# 3. Start services
cd api && python main.py &
cd streamlit_ui && streamlit run app.py

# 4. Open browser: http://localhost:8501
```

See [PROJECT_STATUS.md](PROJECT_STATUS.md) for more deployment options.

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

## Integration Testing Status

### Test Results ✅
```bash
$ pytest tests/ -v

✅ 113 unit tests passed (100% pass rate)
✅ 15 integration tests passed
⏭️ 10 database tests skipped (optional, require PostgreSQL)

Total: 138 tests | Executed: 128 | Passed: 128 | Skipped: 10 | Failed: 0
Coverage: 85%+
```

### How to Run Tests
```bash
# Install test dependencies
pip install pytest pytest-cov

# Run all tests
pytest tests/ -v

# Run with coverage report
pytest tests/ --cov=atp_re --cov-report=html
```

### Quick Verification
Run the verification script to check project health:
```bash
python verify_project_status.py
```

This will check:
- ✅ Python version compatibility
- ✅ Package installation
- ✅ Test suite execution
- ✅ Decoder functionality
- ✅ Documentation completeness
- ✅ Test files availability

See [PROJECT_STATUS.md](PROJECT_STATUS.md) for detailed testing information.

## What's Ready to Use

### ✅ Production Ready
1. **Command-Line Decoder Tool**
   ```bash
   python decode_packets.py tests/RU_file/024423.RU -n 5
   ```
   - Full RU file decoding
   - Text and JSON output formats
   - Batch processing support

2. **Python API Library**
   ```python
   from atp_re.decoders import RUDecoder
   decoder = RUDecoder()
   result = decoder.decode(packet_data)
   print(f"Speed: {result.data.v_train} km/h")
   ```

### ⚠️ Beta (Testing)
3. **Web API Service** - REST API with database integration
4. **Web UI Interface** - Streamlit-based web interface

See [Quick Start Guide](#quick-start) for usage instructions.

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

Lawliet Chen

## 📋 Interactive Charts and Automatic Analysis Planning

This project includes comprehensive planning for interactive train operation charts and automatic analysis features. See the detailed planning documents:

### Planning Documents
- **[Planning Summary](PLANNING_SUMMARY.md)** - Quick reference and overview
- **[Main Planning Document](INTERACTIVE_CHART_ANALYSIS_PLANNING.md)** - Comprehensive planning (1241 lines)
- **[API Specification](API_SPECIFICATION_INTERACTIVE_ANALYSIS.md)** - REST API design (704 lines)
- **[UI/UX Specification](UI_UX_SPECIFICATION.md)** - Interface design (794 lines)
- **[Implementation Roadmap](IMPLEMENTATION_ROADMAP.md)** - 11-week development plan (673 lines)

### Key Features Planned
- ✨ **Interactive Charts**: Zoom, pan, selection, hover tooltips, layer control
- 🔍 **Anomaly Detection**: Overspeed, harsh braking, speed fluctuations
- 📈 **Trend Analysis**: Moving averages, linear regression, predictions
- 📊 **Auto Reports**: One-click PDF/HTML report generation
- ⚡ **Performance**: < 2s load time, 1000 concurrent users

### Technology Stack
- **Frontend**: Streamlit + Plotly
- **Backend**: FastAPI + PostgreSQL + Redis
- **Analysis**: SciPy, scikit-learn, statsmodels

