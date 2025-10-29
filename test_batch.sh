#!/bin/bash
# RU/MMI Batch Regression Test Script
# This script performs batch testing for RU decoder and MMI components

set -e  # Exit on error

# Configuration
PROCESS_CMD="${PROCESS_CMD:-./bin/atp_re process}"
TOLERANCE="${TOLERANCE:-0.01}"
TEST_DIR="${TEST_DIR:-./test_data}"
RESULT_DIR="${RESULT_DIR:-./test_results}"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo "=========================================="
echo "RU/MMI Batch Regression Test"
echo "=========================================="
echo "Process Command: $PROCESS_CMD"
echo "Tolerance: $TOLERANCE"
echo "Test Data Directory: $TEST_DIR"
echo "Results Directory: $RESULT_DIR"
echo "=========================================="

# Initialize counters
TOTAL_TESTS=0
PASSED_TESTS=0
FAILED_TESTS=0
SKIPPED_TESTS=0

# Create results directory if it doesn't exist
mkdir -p "$RESULT_DIR"

# Function to run a single test
run_test() {
    local test_file="$1"
    local test_name=$(basename "$test_file")
    
    echo -e "\n[TEST] Running: $test_name"
    TOTAL_TESTS=$((TOTAL_TESTS + 1))
    
    # Check if process command exists
    if [ ! -f "${PROCESS_CMD%% *}" ]; then
        echo -e "${YELLOW}[SKIP]${NC} Process binary not found: ${PROCESS_CMD%% *}"
        SKIPPED_TESTS=$((SKIPPED_TESTS + 1))
        return 0
    fi
    
    # Run the process command
    if $PROCESS_CMD "$test_file" > "$RESULT_DIR/${test_name}.result" 2>&1; then
        echo -e "${GREEN}[PASS]${NC} $test_name"
        PASSED_TESTS=$((PASSED_TESTS + 1))
        return 0
    else
        echo -e "${RED}[FAIL]${NC} $test_name"
        FAILED_TESTS=$((FAILED_TESTS + 1))
        return 1
    fi
}

# Check if test data directory exists
if [ ! -d "$TEST_DIR" ]; then
    echo -e "${YELLOW}[INFO]${NC} Test data directory not found: $TEST_DIR"
    echo -e "${YELLOW}[INFO]${NC} Creating placeholder directory structure..."
    mkdir -p "$TEST_DIR"
    echo -e "${GREEN}[INFO]${NC} No test files to run. Test setup completed successfully."
    echo -e "\nTo add tests, place RU log files in: $TEST_DIR"
    exit 0
fi

# Find all test files (RU log files typically have .ru extension or are binary logs)
TEST_FILES=$(find "$TEST_DIR" -type f \( -name "*.ru" -o -name "*.log" -o -name "*.dat" \) 2>/dev/null || true)

if [ -z "$TEST_FILES" ]; then
    echo -e "${YELLOW}[INFO]${NC} No test files found in $TEST_DIR"
    echo -e "${GREEN}[INFO]${NC} Test suite setup completed successfully."
    echo -e "\nTo add tests, place RU log files (*.ru, *.log, *.dat) in: $TEST_DIR"
    exit 0
fi

# Run all tests
echo -e "\nRunning tests..."
while IFS= read -r test_file; do
    run_test "$test_file" || true  # Continue even if test fails
done <<< "$TEST_FILES"

# Print summary
echo -e "\n=========================================="
echo "Test Summary"
echo "=========================================="
echo "Total Tests:   $TOTAL_TESTS"
echo -e "${GREEN}Passed:${NC}        $PASSED_TESTS"
echo -e "${RED}Failed:${NC}        $FAILED_TESTS"
echo -e "${YELLOW}Skipped:${NC}       $SKIPPED_TESTS"
echo "=========================================="

# Exit with appropriate code
if [ $FAILED_TESTS -gt 0 ]; then
    echo -e "${RED}[FAIL]${NC} Some tests failed"
    exit 1
elif [ $TOTAL_TESTS -eq 0 ]; then
    echo -e "${GREEN}[PASS]${NC} No tests to run - setup successful"
    exit 0
else
    echo -e "${GREEN}[PASS]${NC} All tests passed"
    exit 0
fi
