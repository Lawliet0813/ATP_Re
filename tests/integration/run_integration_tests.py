#!/usr/bin/env python3
"""
Comprehensive test runner for ATP MMI and RU file integration tests.

This script runs all integration tests and generates a detailed report.
"""

import sys
import json
import time
from pathlib import Path
from datetime import datetime
import subprocess


class TestRunner:
    """Run integration tests and generate reports."""
    
    def __init__(self, output_dir="test_results"):
        self.output_dir = Path(output_dir)
        self.output_dir.mkdir(exist_ok=True)
        self.results = {}
        self.start_time = None
        self.end_time = None
    
    def run_tests(self):
        """Run all integration tests."""
        print("=" * 80)
        print("ATP INTEGRATION TEST SUITE")
        print("MMI and RU File Parsing Tests")
        print("=" * 80)
        print()
        
        self.start_time = datetime.now()
        print(f"Test started: {self.start_time.strftime('%Y-%m-%d %H:%M:%S')}")
        print()
        
        # Run RU file tests
        print("Running RU file parsing tests...")
        print("-" * 80)
        ru_result = self._run_pytest("tests/integration/test_ru_file_parsing.py")
        self.results["ru_file_tests"] = ru_result
        print()
        
        # Run MMI file tests
        print("Running MMI file parsing tests...")
        print("-" * 80)
        mmi_result = self._run_pytest("tests/integration/test_mmi_file_parsing.py")
        self.results["mmi_file_tests"] = mmi_result
        print()
        
        self.end_time = datetime.now()
        elapsed = (self.end_time - self.start_time).total_seconds()
        
        print("=" * 80)
        print(f"Test completed: {self.end_time.strftime('%Y-%m-%d %H:%M:%S')}")
        print(f"Total time: {elapsed:.2f} seconds")
        print("=" * 80)
        print()
        
        # Generate reports
        self._generate_summary_report()
        self._generate_json_report()
        self._generate_markdown_report()
    
    def _run_pytest(self, test_path):
        """Run pytest and capture results."""
        try:
            # Run pytest with verbose output and JSON report
            cmd = [
                sys.executable, "-m", "pytest",
                test_path,
                "-v",
                "--tb=short",
                "--junit-xml=" + str(self.output_dir / f"{Path(test_path).stem}.xml")
            ]
            
            result = subprocess.run(
                cmd,
                capture_output=True,
                text=True,
                timeout=600  # 10 minute timeout
            )
            
            return {
                "returncode": result.returncode,
                "stdout": result.stdout,
                "stderr": result.stderr,
                "success": result.returncode == 0
            }
            
        except subprocess.TimeoutExpired:
            return {
                "returncode": -1,
                "stdout": "",
                "stderr": "Test timed out after 10 minutes",
                "success": False
            }
        except Exception as e:
            return {
                "returncode": -1,
                "stdout": "",
                "stderr": str(e),
                "success": False
            }
    
    def _generate_summary_report(self):
        """Generate a text summary report."""
        report_file = self.output_dir / "test_summary.txt"
        
        with open(report_file, 'w') as f:
            f.write("=" * 80 + "\n")
            f.write("ATP INTEGRATION TEST SUMMARY REPORT\n")
            f.write("=" * 80 + "\n\n")
            
            f.write(f"Test Date: {self.start_time.strftime('%Y-%m-%d %H:%M:%S')}\n")
            f.write(f"Duration: {(self.end_time - self.start_time).total_seconds():.2f} seconds\n")
            f.write("\n")
            
            # RU file tests
            f.write("RU FILE PARSING TESTS\n")
            f.write("-" * 80 + "\n")
            ru_result = self.results.get("ru_file_tests", {})
            f.write(f"Status: {'PASS' if ru_result.get('success') else 'FAIL'}\n")
            f.write(f"Return code: {ru_result.get('returncode')}\n")
            f.write("\n")
            
            # MMI file tests
            f.write("MMI FILE PARSING TESTS\n")
            f.write("-" * 80 + "\n")
            mmi_result = self.results.get("mmi_file_tests", {})
            f.write(f"Status: {'PASS' if mmi_result.get('success') else 'FAIL'}\n")
            f.write(f"Return code: {mmi_result.get('returncode')}\n")
            f.write("\n")
            
            # Overall status
            f.write("=" * 80 + "\n")
            f.write("OVERALL STATUS\n")
            f.write("=" * 80 + "\n")
            all_passed = all(r.get("success", False) for r in self.results.values())
            f.write(f"Result: {'ALL TESTS PASSED' if all_passed else 'SOME TESTS FAILED'}\n")
            f.write("\n")
            
            # Details
            f.write("DETAILED OUTPUT\n")
            f.write("=" * 80 + "\n\n")
            
            for test_name, result in self.results.items():
                f.write(f"\n{test_name.upper()}\n")
                f.write("-" * 80 + "\n")
                f.write(result.get("stdout", ""))
                if result.get("stderr"):
                    f.write("\nERRORS:\n")
                    f.write(result.get("stderr", ""))
                f.write("\n\n")
        
        print(f"Summary report generated: {report_file}")
    
    def _generate_json_report(self):
        """Generate a JSON report."""
        report_file = self.output_dir / "test_results.json"
        
        report_data = {
            "test_date": self.start_time.isoformat(),
            "start_time": self.start_time.isoformat(),
            "end_time": self.end_time.isoformat(),
            "duration_seconds": (self.end_time - self.start_time).total_seconds(),
            "results": {}
        }
        
        for test_name, result in self.results.items():
            report_data["results"][test_name] = {
                "success": result.get("success", False),
                "returncode": result.get("returncode"),
                "has_errors": bool(result.get("stderr"))
            }
        
        with open(report_file, 'w') as f:
            json.dump(report_data, f, indent=2)
        
        print(f"JSON report generated: {report_file}")
    
    def _generate_markdown_report(self):
        """Generate a markdown report."""
        report_file = self.output_dir / "TEST_REPORT.md"
        
        with open(report_file, 'w') as f:
            f.write("# ATP Integration Test Report\n\n")
            f.write("## Test Execution Summary\n\n")
            f.write(f"- **Test Date**: {self.start_time.strftime('%Y-%m-%d %H:%M:%S')}\n")
            f.write(f"- **Duration**: {(self.end_time - self.start_time).total_seconds():.2f} seconds\n")
            f.write(f"- **Test Suite**: MMI and RU File Integration Tests\n\n")
            
            # Results table
            f.write("## Test Results\n\n")
            f.write("| Test Suite | Status | Return Code |\n")
            f.write("|------------|--------|-------------|\n")
            
            for test_name, result in self.results.items():
                status = "✅ PASS" if result.get("success") else "❌ FAIL"
                returncode = result.get("returncode", "N/A")
                f.write(f"| {test_name} | {status} | {returncode} |\n")
            
            f.write("\n")
            
            # Overall status
            all_passed = all(r.get("success", False) for r in self.results.values())
            f.write("## Overall Status\n\n")
            if all_passed:
                f.write("✅ **ALL TESTS PASSED**\n\n")
            else:
                f.write("❌ **SOME TESTS FAILED**\n\n")
            
            # RU file tests details
            f.write("## RU File Parsing Tests\n\n")
            ru_result = self.results.get("ru_file_tests", {})
            if ru_result.get("success"):
                f.write("Status: ✅ PASS\n\n")
            else:
                f.write("Status: ❌ FAIL\n\n")
            
            f.write("### Test Coverage\n\n")
            f.write("- Parse single RU file\n")
            f.write("- Parse all RU files\n")
            f.write("- Packet type coverage analysis\n")
            f.write("- Error handling (truncated files)\n")
            f.write("- Error handling (invalid data)\n")
            f.write("- Performance test (large files)\n\n")
            
            # MMI file tests details
            f.write("## MMI File Parsing Tests\n\n")
            mmi_result = self.results.get("mmi_file_tests", {})
            if mmi_result.get("success"):
                f.write("Status: ✅ PASS\n\n")
            else:
                f.write("Status: ❌ FAIL\n\n")
            
            f.write("### Test Coverage\n\n")
            f.write("- Parse single MMI file\n")
            f.write("- Parse all MMI files\n")
            f.write("- File size distribution analysis\n")
            f.write("- Directory structure analysis\n")
            f.write("- MMI_DYNAMIC packet decoding\n")
            f.write("- MMI_STATUS packet decoding\n")
            f.write("- Performance test (large files)\n\n")
            
            # Test files information
            f.write("## Test Data\n\n")
            f.write("### RU Files\n\n")
            f.write("- Location: `tests/RU_file/`\n")
            f.write("- Files: 1 file (024423.RU)\n")
            f.write("- Size: ~2 KB\n\n")
            
            f.write("### MMI Files\n\n")
            f.write("- Location: `tests/MMI_file/`\n")
            f.write("- Directories: 8 subdirectories\n")
            f.write("- Files: 18 MMI files\n")
            f.write("- Size range: 12 KB - 310 KB\n\n")
            
            # Recommendations
            f.write("## Recommendations\n\n")
            f.write("1. **Test Data Expansion**: Add more diverse RU files for comprehensive testing\n")
            f.write("2. **Java Comparison**: Implement comparison with Java system output\n")
            f.write("3. **Edge Cases**: Add more corrupted/malformed file tests\n")
            f.write("4. **Performance Baseline**: Establish performance benchmarks\n")
            f.write("5. **Continuous Integration**: Integrate these tests into CI/CD pipeline\n\n")
            
            # Next steps
            f.write("## Next Steps\n\n")
            f.write("- [ ] Add Java output comparison tests\n")
            f.write("- [ ] Expand RU test file collection\n")
            f.write("- [ ] Add more error scenarios\n")
            f.write("- [ ] Document known issues and limitations\n")
            f.write("- [ ] Create performance benchmarks\n")
            f.write("- [ ] Implement automated regression testing\n")
        
        print(f"Markdown report generated: {report_file}")


def main():
    """Main entry point."""
    print()
    print("ATP Integration Test Runner")
    print("=" * 80)
    print()
    
    # Create output directory
    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    output_dir = f"test_results_{timestamp}"
    
    # Run tests
    runner = TestRunner(output_dir)
    runner.run_tests()
    
    print()
    print("Test execution completed!")
    print(f"Reports available in: {output_dir}/")
    print()


if __name__ == "__main__":
    main()
