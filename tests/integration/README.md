# Integration Tests for ATP MMI & RU File Parsing

This directory contains comprehensive integration tests for the ATP driving record analysis system's MMI and RU file parsing capabilities.

## Quick Start

Run all integration tests with automated reporting:

```bash
python run_integration_tests.py
```

Or run specific test suites:

```bash
# RU file tests
pytest test_ru_file_parsing.py -v

# MMI file tests  
pytest test_mmi_file_parsing.py -v
```

## Test Files

### Test Suites

- **test_ru_file_parsing.py** (7 tests)
  - Single file parsing
  - Batch file parsing
  - Packet type analysis
  - Error handling
  - Performance testing

- **test_mmi_file_parsing.py** (8 tests)
  - Single file parsing
  - Batch file parsing
  - File analysis
  - Packet decoding
  - Performance testing

- **run_integration_tests.py**
  - Automated test runner
  - Report generation (TXT, JSON, MD)

## Test Data

- **RU files**: `../RU_file/` (1 file, ~2KB)
- **MMI files**: `../MMI_file/` (18 files across 8 directories, 12KB-310KB)

## Test Results

Latest run: ✅ 15/15 tests passed (7.55s)

- RU file tests: ✅ 7/7 passed (0.02s)
- MMI file tests: ✅ 8/8 passed (7.09s)

## Generated Reports

After running `run_integration_tests.py`, reports are generated in `test_results_[timestamp]/`:

- `test_summary.txt` - Text summary
- `test_results.json` - Machine-readable results
- `TEST_REPORT.md` - Detailed markdown report
- `*.xml` - JUnit XML reports for CI/CD

## Documentation

See [INTEGRATION_TEST_GUIDE.md](INTEGRATION_TEST_GUIDE.md) for comprehensive documentation including:

- Detailed test descriptions
- Adding new test data
- Troubleshooting
- CI/CD integration
- Maintenance guidelines

## Requirements

- Python 3.12+
- pytest 7.4+
- atp_re package installed (`pip install -e .`)

## Examples

View detailed output for a specific test:

```bash
pytest test_ru_file_parsing.py::TestRUFileParsing::test_parse_single_ru_file -v -s
```

Run with coverage:

```bash
pytest test_ru_file_parsing.py --cov=atp_re.decoders
```

Generate HTML coverage report:

```bash
pytest test_ru_file_parsing.py --cov=atp_re.decoders --cov-report=html
```

## Related

- Issue: ATP 行車紀錄分析系統：MMI 與 RU 檔案實測規劃
- Parent Issue: #12 (總覽議題)

---

For questions or issues, please refer to the main project issue tracker.
