# RU/MMI Regression Tests

This repository includes a batch testing script for RU (Recording Unit) decoder and MMI (Man-Machine Interface) regression tests.

## Files

- **test_batch.sh**: Batch test runner script
- **.github/workflows/ru-mmi-regression.yml**: GitHub Actions workflow for automated testing

## Usage

### Running Tests Locally

```bash
# Make the script executable (if not already)
chmod +x ./test_batch.sh

# Run the tests
./test_batch.sh
```

### Configuration

The script can be configured using environment variables:

- `PROCESS_CMD`: Command to process RU log files (default: `./bin/atp_re process`)
- `TOLERANCE`: Test tolerance value (default: `0.01`)
- `TEST_DIR`: Directory containing test data files (default: `./test_data`)
- `RESULT_DIR`: Directory for test results (default: `./test_results`)

Example:

```bash
PROCESS_CMD="docker run atp_re process" TEST_DIR="./my_tests" ./test_batch.sh
```

## Adding Test Data

To add RU log files for testing:

1. Create the test data directory (if not exists):
   ```bash
   mkdir -p test_data
   ```

2. Place your RU log files in the `test_data` directory. Supported file extensions:
   - `.ru` - RU log files
   - `.log` - Log files
   - `.dat` - Data files

3. Run the test script:
   ```bash
   ./test_batch.sh
   ```

## Test Results

Test results are stored in the `test_results` directory. Each test creates a result file with the format: `<test_file_name>.result`

## GitHub Actions Workflow

The workflow automatically runs on:
- Push to `main` branch
- Pull requests to `main` branch

The workflow:
1. Checks out the code
2. Sets up Python 3.10
3. Runs the batch tests using `test_batch.sh`

## Exit Codes

- `0`: All tests passed or no tests to run
- `1`: One or more tests failed

## Notes

- The script handles missing test data gracefully
- If the process binary doesn't exist, tests are skipped
- Test data and results directories are excluded from git via `.gitignore`
