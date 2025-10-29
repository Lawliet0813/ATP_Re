"""
Validation script for comparing Python decoder output with Java reference.

This script helps validate that the Python decoders produce the same results
as the Java reference implementation.
"""

import json
from typing import Dict, Any
from atp_re.decoders import RUDecoder, PacketHeaderParser, MMIDecoder, BTMDecoder


class DecoderValidator:
    """Validate Python decoder output against expected results."""
    
    def __init__(self):
        self.ru_decoder = RUDecoder()
        self.results = []
    
    def validate_mmi_dynamic_packet(self, test_case: Dict) -> Dict:
        """
        Validate MMI_DYNAMIC packet decoding.
        
        Args:
            test_case: Test case data from test_data_generator
            
        Returns:
            Validation result dictionary
        """
        # Get packet data
        packet_bytes = bytes(test_case["packet_bytes"])
        expected = test_case["expected"]
        
        # Decode with Python decoder
        result = self.ru_decoder.decode(packet_bytes)
        
        # Compare results
        header_match = (
            result.header.packet_no == expected["header"]["packet_no"] and
            result.header.location == expected["header"]["location"] and
            result.header.speed == expected["header"]["speed"]
        )
        
        mmi_match = True
        mismatches = []
        
        if hasattr(result.data, 'to_dict'):
            decoded = result.data.to_dict()
            for key, expected_value in expected["mmi_dynamic"].items():
                actual_value = decoded.get(key)
                if actual_value != expected_value:
                    mmi_match = False
                    mismatches.append({
                        "field": key,
                        "expected": expected_value,
                        "actual": actual_value
                    })
        
        return {
            "test_name": test_case["name"],
            "passed": header_match and mmi_match,
            "header_match": header_match,
            "mmi_match": mmi_match,
            "mismatches": mismatches
        }
    
    def validate_btm_reassembly(self, test_case: Dict) -> Dict:
        """
        Validate BTM fragment reassembly.
        
        Args:
            test_case: Test case data from test_data_generator
            
        Returns:
            Validation result dictionary
        """
        btm_decoder = BTMDecoder()
        
        # Process all fragments
        telegram = None
        for fragment in test_case["fragments"]:
            fragment_bytes = bytes(fragment["packet_bytes"])
            telegram_no = fragment["telegram_number"]
            result = btm_decoder.add_fragment(fragment_bytes, telegram_no)
            if result is not None:
                telegram = result
        
        # Validate
        if telegram is None:
            return {
                "test_name": test_case["name"],
                "passed": False,
                "error": "Failed to reassemble telegram"
            }
        
        expected_size = test_case["expected_telegram_size"]
        actual_size = len(telegram.data)
        size_match = actual_size == expected_size
        
        sequence_match = telegram.sequence_number == test_case["sequence_number"]
        
        return {
            "test_name": test_case["name"],
            "passed": size_match and sequence_match,
            "size_match": size_match,
            "sequence_match": sequence_match,
            "expected_size": expected_size,
            "actual_size": actual_size
        }
    
    def validate_test_file(self, filename: str) -> Dict:
        """
        Validate all test cases from a JSON file.
        
        Args:
            filename: Path to test data JSON file
            
        Returns:
            Summary of validation results
        """
        with open(filename, 'r') as f:
            test_data = json.load(f)
        
        results = []
        
        for test_case in test_data["test_cases"]:
            if test_case["name"].startswith("MMI_DYNAMIC"):
                result = self.validate_mmi_dynamic_packet(test_case)
                results.append(result)
            elif test_case["name"].startswith("BTM_Reassembly"):
                result = self.validate_btm_reassembly(test_case)
                results.append(result)
        
        # Calculate summary
        total = len(results)
        passed = sum(1 for r in results if r["passed"])
        
        return {
            "total_tests": total,
            "passed": passed,
            "failed": total - passed,
            "results": results
        }
    
    def print_validation_report(self, summary: Dict) -> None:
        """
        Print a formatted validation report.
        
        Args:
            summary: Validation summary from validate_test_file
        """
        print("=" * 70)
        print("DECODER VALIDATION REPORT")
        print("=" * 70)
        print(f"\nTotal Tests: {summary['total_tests']}")
        print(f"Passed: {summary['passed']}")
        print(f"Failed: {summary['failed']}")
        print(f"Success Rate: {summary['passed']/summary['total_tests']*100:.1f}%")
        
        print("\n" + "=" * 70)
        print("DETAILED RESULTS")
        print("=" * 70)
        
        for result in summary["results"]:
            status = "✓ PASS" if result["passed"] else "✗ FAIL"
            print(f"\n{status} - {result['test_name']}")
            
            if not result["passed"]:
                if "mismatches" in result and result["mismatches"]:
                    print("  Mismatched fields:")
                    for mismatch in result["mismatches"]:
                        print(f"    {mismatch['field']}: "
                              f"expected={mismatch['expected']}, "
                              f"actual={mismatch['actual']}")
                
                if "error" in result:
                    print(f"  Error: {result['error']}")


if __name__ == "__main__":
    import sys
    
    # Check if test data file is provided
    if len(sys.argv) > 1:
        test_file = sys.argv[1]
    else:
        test_file = "/tmp/decoder_test_data.json"
    
    print(f"Validating decoders with test data: {test_file}")
    print()
    
    validator = DecoderValidator()
    summary = validator.validate_test_file(test_file)
    validator.print_validation_report(summary)
