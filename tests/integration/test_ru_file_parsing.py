"""
Integration tests for parsing real RU files.

This module tests the RU decoder with actual RU files from the field,
validating parsing capabilities, error handling, and performance.
"""

import os
import time
import pytest
from pathlib import Path
from typing import List, Dict, Any
from atp_re.decoders.ru_decoder import RUDecoder


class TestRUFileParsing:
    """Integration tests for RU file parsing."""
    
    @pytest.fixture
    def ru_decoder(self):
        """Create a fresh RU decoder instance."""
        return RUDecoder()
    
    @pytest.fixture
    def test_files_dir(self):
        """Get the path to test RU files directory."""
        base_dir = Path(__file__).parent.parent
        return base_dir / "RU_file"
    
    def test_ru_files_exist(self, test_files_dir):
        """Verify that test RU files are available."""
        assert test_files_dir.exists(), f"RU test files directory not found: {test_files_dir}"
        ru_files = list(test_files_dir.glob("*.RU"))
        assert len(ru_files) > 0, "No RU test files found"
    
    def test_parse_single_ru_file(self, ru_decoder, test_files_dir):
        """Test parsing a single RU file."""
        ru_files = list(test_files_dir.glob("*.RU"))
        assert len(ru_files) > 0, "No RU files found for testing"
        
        # Test with the first RU file
        test_file = ru_files[0]
        print(f"\nTesting RU file: {test_file.name}")
        
        with open(test_file, 'rb') as f:
            file_content = f.read()
        
        assert len(file_content) > 0, "RU file is empty"
        print(f"File size: {len(file_content)} bytes")
        
        # Parse packets from the file
        packets_decoded = 0
        errors = []
        offset = 0
        
        while offset < len(file_content):
            # RU packets have a minimum header size of 15 bytes
            if offset + 15 > len(file_content):
                break
            
            try:
                # Try to decode packet
                # First get packet length (byte 15 contains body length)
                if offset + 16 > len(file_content):
                    break
                
                body_length = file_content[offset + 15]
                packet_length = 16 + body_length
                
                if offset + packet_length > len(file_content):
                    break
                
                packet_data = file_content[offset:offset + packet_length]
                result = ru_decoder.decode(packet_data)
                
                packets_decoded += 1
                print(f"Packet {packets_decoded}: Type={result.packet_type}, "
                      f"Description={result.description}, "
                      f"Location={result.header.location}m, "
                      f"Speed={result.header.speed}km/h")
                
                offset += packet_length
                
            except Exception as e:
                errors.append({
                    "offset": offset,
                    "error": str(e)
                })
                # Try to skip to next potential packet
                offset += 1
        
        print(f"\nTotal packets decoded: {packets_decoded}")
        print(f"Errors encountered: {len(errors)}")
        
        # We should be able to decode at least some packets
        assert packets_decoded > 0, "Failed to decode any packets from RU file"
        
        # Error rate should be reasonable (less than 50%)
        if packets_decoded > 0:
            error_rate = len(errors) / (packets_decoded + len(errors)) * 100
            print(f"Error rate: {error_rate:.2f}%")
            assert error_rate < 50, f"Error rate too high: {error_rate:.2f}%"
    
    def test_parse_all_ru_files(self, ru_decoder, test_files_dir):
        """Test parsing all available RU files."""
        ru_files = list(test_files_dir.glob("*.RU"))
        
        results = []
        for ru_file in ru_files:
            print(f"\nProcessing: {ru_file.name}")
            
            start_time = time.time()
            
            with open(ru_file, 'rb') as f:
                file_content = f.read()
            
            packets_decoded = 0
            errors = 0
            offset = 0
            
            while offset < len(file_content):
                if offset + 16 > len(file_content):
                    break
                
                try:
                    body_length = file_content[offset + 15]
                    packet_length = 16 + body_length
                    
                    if offset + packet_length > len(file_content):
                        break
                    
                    packet_data = file_content[offset:offset + packet_length]
                    ru_decoder.decode(packet_data)
                    packets_decoded += 1
                    offset += packet_length
                    
                except Exception:
                    errors += 1
                    offset += 1
            
            elapsed_time = time.time() - start_time
            
            results.append({
                "file": ru_file.name,
                "size_bytes": len(file_content),
                "packets_decoded": packets_decoded,
                "errors": errors,
                "time_seconds": elapsed_time,
                "packets_per_second": packets_decoded / elapsed_time if elapsed_time > 0 else 0
            })
        
        # Print summary
        print("\n" + "=" * 80)
        print("RU FILE PARSING SUMMARY")
        print("=" * 80)
        
        total_packets = sum(r["packets_decoded"] for r in results)
        total_errors = sum(r["errors"] for r in results)
        total_time = sum(r["time_seconds"] for r in results)
        
        for result in results:
            print(f"\nFile: {result['file']}")
            print(f"  Size: {result['size_bytes']} bytes")
            print(f"  Packets decoded: {result['packets_decoded']}")
            print(f"  Errors: {result['errors']}")
            print(f"  Time: {result['time_seconds']:.3f}s")
            print(f"  Speed: {result['packets_per_second']:.1f} packets/sec")
        
        print(f"\nTOTAL:")
        print(f"  Files processed: {len(results)}")
        print(f"  Total packets: {total_packets}")
        print(f"  Total errors: {total_errors}")
        print(f"  Total time: {total_time:.3f}s")
        print(f"  Average speed: {total_packets/total_time:.1f} packets/sec")
        
        # Assertions
        assert len(results) > 0, "No RU files processed"
        assert total_packets > 0, "No packets decoded from any file"
    
    def test_ru_packet_types_coverage(self, ru_decoder, test_files_dir):
        """Test that we can decode various packet types from RU files."""
        ru_files = list(test_files_dir.glob("*.RU"))
        
        packet_types_found = set()
        packet_type_counts = {}
        
        for ru_file in ru_files:
            with open(ru_file, 'rb') as f:
                file_content = f.read()
            
            offset = 0
            while offset < len(file_content):
                if offset + 16 > len(file_content):
                    break
                
                try:
                    body_length = file_content[offset + 15]
                    packet_length = 16 + body_length
                    
                    if offset + packet_length > len(file_content):
                        break
                    
                    packet_data = file_content[offset:offset + packet_length]
                    result = ru_decoder.decode(packet_data)
                    
                    packet_types_found.add(result.packet_type)
                    packet_type_counts[result.packet_type] = \
                        packet_type_counts.get(result.packet_type, 0) + 1
                    
                    offset += packet_length
                    
                except Exception:
                    offset += 1
        
        print("\nPacket Types Found:")
        for packet_type in sorted(packet_types_found):
            count = packet_type_counts[packet_type]
            print(f"  Type {packet_type}: {count} packets")
        
        # We should find at least a few different packet types
        assert len(packet_types_found) > 0, "No packet types found"
    
    def test_ru_error_handling_truncated_file(self, ru_decoder):
        """Test error handling with truncated RU data."""
        # Create truncated packet data
        truncated_data = bytes([0x01, 0x18, 0x09, 0x10])  # Only 4 bytes
        
        with pytest.raises(ValueError):
            ru_decoder.decode(truncated_data)
    
    def test_ru_error_handling_invalid_data(self, ru_decoder):
        """Test error handling with invalid RU data."""
        # Create packet with valid header but invalid body
        invalid_data = bytes([0xFF] * 16) + bytes([0x01, 0x00])  # Invalid packet
        
        # Should not crash, but might return error info
        try:
            result = ru_decoder.decode(invalid_data)
            # If it decodes, it should mark it as unknown or error
            assert result is not None
        except ValueError:
            # ValueError is acceptable for truly invalid data
            pass


class TestRUPerformance:
    """Performance tests for RU file parsing."""
    
    def test_large_file_performance(self, tmp_path):
        """Test performance with a large synthetic RU file."""
        decoder = RUDecoder()
        
        # Create a synthetic large RU file
        # Generate 1000 simple packets
        packets = []
        for i in range(1000):
            # Create a simple status packet
            header = bytes([
                0x02,  # packet_no
                0x18, 0x09, 0x10, 0x0b, 0x26, 0x07,  # timestamp
                0x00, 0x00, 0x03, 0xE8,  # location
                0x00, 0x00,  # reserved
                0x00, 0x50,  # speed
            ])
            body_length = bytes([0x01])
            body = bytes([0x05])
            packets.append(header + body_length + body)
        
        # Write to file
        test_file = tmp_path / "large_test.RU"
        with open(test_file, 'wb') as f:
            for packet in packets:
                f.write(packet)
        
        # Measure parsing performance
        start_time = time.time()
        
        with open(test_file, 'rb') as f:
            file_content = f.read()
        
        packets_decoded = 0
        offset = 0
        
        while offset < len(file_content):
            if offset + 16 > len(file_content):
                break
            
            body_length = file_content[offset + 15]
            packet_length = 16 + body_length
            
            if offset + packet_length > len(file_content):
                break
            
            packet_data = file_content[offset:offset + packet_length]
            decoder.decode(packet_data)
            packets_decoded += 1
            offset += packet_length
        
        elapsed_time = time.time() - start_time
        packets_per_second = packets_decoded / elapsed_time
        
        print(f"\nPerformance Test Results:")
        print(f"  Packets: {packets_decoded}")
        print(f"  Time: {elapsed_time:.3f}s")
        print(f"  Speed: {packets_per_second:.1f} packets/sec")
        
        # Should decode all packets
        assert packets_decoded == 1000
        # Should be reasonably fast (at least 1000 packets/sec)
        assert packets_per_second > 1000, \
            f"Performance too slow: {packets_per_second:.1f} packets/sec"
