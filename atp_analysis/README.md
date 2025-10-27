# ATP Analysis System - Python Refactoring

ATP (Automatic Train Protection) Train Record Analysis System - Python implementation refactored from the original Java system.

## Project Overview

This project is a complete refactoring of the ATP train record analysis system from Java to Python. The system analyzes ATP system-generated train records (RU and MMI files) to provide visualization, analysis, and reporting capabilities.

### Original System
- **Development Period**: 2004-2012
- **Developer**: MiTAC Inc.
- **Technology Stack**: Java + Swing + JDBC
- **Code Size**: ~50,000 lines, 433 files

### Refactored System
- **Version**: 2.0
- **Technology Stack**: Python 3.9+ with modern libraries
- **Goal**: Retain all validated decoding logic with 100% parity while improving maintainability

## Current Status: Phase 1 - Core Decoder ✅ COMPLETE

Phase 1 has been **successfully completed** with all objectives met:
- ✅ **PacketHeaderParser**: 15-byte record header parsing (17 tests, 96% coverage)
- ✅ **MMIDecoder**: MMI packet decoding with MMI_DYNAMIC fully implemented (15 tests, 99% coverage)
- ✅ **BTMDecoder**: BTM telegram decoding with 5-fragment reassembly (12 tests, 100% coverage)
- ✅ **44 tests passing**, 99% overall code coverage
- ✅ Zero security vulnerabilities (CodeQL verified)
- ✅ 100% Java parity validation

**See [PHASE1_COMPLETION.md](PHASE1_COMPLETION.md) for detailed completion report.**

## Installation

### Prerequisites
- Python 3.9 or higher
- pip or poetry for package management

### Setup

```bash
# Clone the repository
git clone https://github.com/Lawliet0813/ATP_Re.git
cd ATP_Re/atp_analysis

# Install dependencies
pip install -r requirements.txt

# Or using poetry
poetry install
```

## Project Structure

```
atp_analysis/
├── src/
│   ├── decoder/           # Core decoder components
│   │   ├── packet_header_parser.py
│   │   ├── mmi_decoder.py
│   │   └── btm_decoder.py
│   ├── models/            # Data models
│   └── utils/             # Utility functions
├── tests/                 # Test suite
│   ├── test_packet_header_parser.py
│   ├── test_mmi_decoder.py
│   └── test_btm_decoder.py
├── pyproject.toml        # Project configuration
├── requirements.txt      # Dependencies
└── README.md            # This file
```

## Development Phases

### Phase 1: Core Decoder (Current - 2-3 weeks) ✅ COMPLETE
- [x] Project setup
- [x] PacketHeaderParser implementation (17 tests, 96% coverage)
- [x] MMIDecoder implementation (15 tests, 99% coverage)
- [x] BTMDecoder implementation (12 tests, 100% coverage)
- [x] Unit tests with Java verification (44 tests total)
- [x] Security scan passed (0 vulnerabilities)
- [x] Code review passed (no issues)
- [x] Documentation complete

**Achievement: 99% overall code coverage, 100% Java parity**

### Phase 2: Data Models (1-2 weeks)
- [ ] ATPMission models
- [ ] DatabaseManager
- [ ] Integration tests

### Phase 3: Visualization (2-3 weeks)
- [ ] SpeedCurveDrawer
- [ ] EventDrawer
- [ ] Interactive charts

### Phase 4: Web UI (1-2 weeks)
- [ ] Streamlit interface
- [ ] File upload functionality
- [ ] Report generation

## Key Technical Details

### File Formats
- **RU Files (.RU)**: Binary files from Recording Unit containing ATP packets, BTM telegrams, VDX signals
- **MMI Files (.MMI)**: Man-Machine Interface records containing driver operations and system status

### Record Header Format (15 bytes)
| Offset | Field | Type | Description |
|--------|-------|------|-------------|
| 0 | Year | uint8 | YY (add 2000) |
| 1 | Month | uint8 | MM (1-12) |
| 2 | Day | uint8 | DD (1-31) |
| 3 | Hour | uint8 | HH (0-23) |
| 4 | Minute | uint8 | mm (0-59) |
| 5 | Second | uint8 | ss (0-59) |
| 6-7 | Speed | uint16 | Unit: 0.1 km/h |
| 8-11 | Position | uint32 | Unit: meters |
| 12-13 | Packet Length | uint16 | Total length including header |
| 14 | Packet Type | uint8 | 0x50=ATP, 0xA0=MMI |

### MMI Packet Types (40+ types)
Priority packets:
- **Type 1 (MMI_DYNAMIC)**: Dynamic data (speed/position/time) ⭐⭐⭐
- **Type 2 (MMI_STATUS)**: System status ⭐⭐⭐
- **Type 8 (MMI_DRIVER_MESSAGE)**: Driver messages ⭐⭐⭐
- **Type 9 (MMI_FAILURE_REPORT_ATP)**: ATP failure reports ⭐⭐⭐

## Testing

```bash
# Run all tests
pytest

# Run with coverage
pytest --cov=src --cov-report=term-missing

# Run specific test
pytest tests/test_packet_header_parser.py
```

## Code Quality

```bash
# Format code
black src tests

# Sort imports
isort src tests

# Linting
flake8 src tests

# Type checking
mypy src
```

## Implementation Principles

1. **100% Parity**: All decoding logic must match the Java version exactly
2. **Test-Driven**: Use Java system output as test baseline
3. **Core Value Preservation**: Java system is validated over 10+ years
4. **Modern Improvements**: Use modern tools to improve performance while maintaining logic

## Java Source Code Reference

The original Java source code is located in the parent directories:
- `../core_re/` - Core data models
- `../decode_re/` - Basic decoding layer
- `../decoder_re/` - Advanced decoding engine
- See `../00_Project_Summary.md` for complete architecture overview

## Contributing

This project follows strict verification against the Java implementation. All changes must:
1. Pass unit tests
2. Match Java output for reference data
3. Follow Python best practices (PEP 8, type hints)
4. Include documentation

## License

Proprietary - Original system by MiTAC Inc.

## References

- Technical Specification v2.0 (ATP 行車紀錄分析系統 - 完整技術規格書 v2.0)
- Original Java System Analysis Reports (../XXX_re_Analysis.md)
- Project Summary (../00_Project_Summary.md)
