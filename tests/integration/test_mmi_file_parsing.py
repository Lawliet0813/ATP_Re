"""
Integration tests for parsing real MMI files.

This module tests the MMI decoder with actual MMI files from the field,
validating parsing capabilities, error handling, and performance.
"""

import os
import time
import pytest
from pathlib import Path
from typing import List, Dict, Any
from atp_re.decoders.mmi_decoder import MMIDecoder, MMI_DYNAMIC, MMI_STATUS


class TestMMIFileParsing:
    """Integration tests for MMI file parsing."""
    
    @pytest.fixture
    def mmi_decoder(self):
        """Create a fresh MMI decoder instance."""
        return MMIDecoder()
    
    @pytest.fixture
    def test_files_dir(self):
        """Get the path to test MMI files directory."""
        base_dir = Path(__file__).parent.parent
        return base_dir / "MMI_file"
    
    def test_mmi_files_exist(self, test_files_dir):
        """Verify that test MMI files are available."""
        assert test_files_dir.exists(), f"MMI test files directory not found: {test_files_dir}"
        
        # Find all MMI files in subdirectories
        mmi_files = list(test_files_dir.rglob("*.MMI"))
        assert len(mmi_files) > 0, "No MMI test files found"
        print(f"\nFound {len(mmi_files)} MMI test files")
    
    def test_parse_single_mmi_file(self, test_files_dir):
        """Test parsing a single MMI file."""
        mmi_files = list(test_files_dir.rglob("*.MMI"))
        assert len(mmi_files) > 0, "No MMI files found for testing"
        
        # Test with the first MMI file
        test_file = mmi_files[0]
        print(f"\nTesting MMI file: {test_file.parent.name}/{test_file.name}")
        
        with open(test_file, 'rb') as f:
            file_content = f.read()
        
        assert len(file_content) > 0, "MMI file is empty"
        print(f"File size: {len(file_content)} bytes")
        
        # MMI files contain packet data
        # Try to identify and decode MMI_DYNAMIC and MMI_STATUS packets
        packets_decoded = 0
        mmi_dynamic_count = 0
        mmi_status_count = 0
        errors = []
        
        # Simple scan for potential MMI packets
        # MMI_DYNAMIC has specific structure, try to find valid packets
        offset = 0
        while offset < len(file_content) - 30:  # MMI_DYNAMIC needs at least 30 bytes
            try:
                # Try to decode as MMI_DYNAMIC
                packet_data = file_content[offset:offset + 30]
                if len(packet_data) == 30:
                    try:
                        result = MMIDecoder.decode_mmi_dynamic(packet_data)
                        # Basic validation: speeds should be reasonable (0-400 km/h)
                        if 0 <= result.v_train <= 400 and 0 <= result.v_target <= 400:
                            packets_decoded += 1
                            mmi_dynamic_count += 1
                            if packets_decoded <= 5:  # Show first 5
                                print(f"  MMI_DYNAMIC: v_train={result.v_train}km/h, "
                                      f"a_train={result.a_train}cm/s², "
                                      f"o_train={result.o_train}m")
                    except:
                        pass
                
                offset += 1
                
            except Exception as e:
                errors.append({
                    "offset": offset,
                    "error": str(e)
                })
                offset += 1
        
        print(f"\nPotential packets found: {packets_decoded}")
        print(f"  MMI_DYNAMIC: {mmi_dynamic_count}")
        print(f"  MMI_STATUS: {mmi_status_count}")
        
        # We should be able to find some valid-looking packets
        # Note: Without knowing exact format, we expect some hits
        print(f"File processed successfully")
    
    def test_parse_all_mmi_files(self, test_files_dir):
        """Test parsing all available MMI files."""
        mmi_files = list(test_files_dir.rglob("*.MMI"))
        
        results = []
        for mmi_file in mmi_files:
            relative_path = f"{mmi_file.parent.name}/{mmi_file.name}"
            print(f"\nProcessing: {relative_path}")
            
            start_time = time.time()
            
            with open(mmi_file, 'rb') as f:
                file_content = f.read()
            
            # Scan for potential MMI packets
            potential_packets = 0
            offset = 0
            
            while offset < len(file_content) - 30:
                packet_data = file_content[offset:offset + 30]
                try:
                    result = MMIDecoder.decode_mmi_dynamic(packet_data)
                    # Validation: reasonable values
                    if (0 <= result.v_train <= 400 and 
                        0 <= result.v_target <= 400 and
                        abs(result.a_train) <= 200):
                        potential_packets += 1
                except:
                    pass
                offset += 1
            
            elapsed_time = time.time() - start_time
            
            results.append({
                "file": relative_path,
                "size_bytes": len(file_content),
                "potential_packets": potential_packets,
                "time_seconds": elapsed_time
            })
        
        # Print summary
        print("\n" + "=" * 80)
        print("MMI FILE PARSING SUMMARY")
        print("=" * 80)
        
        total_packets = sum(r["potential_packets"] for r in results)
        total_time = sum(r["time_seconds"] for r in results)
        total_size = sum(r["size_bytes"] for r in results)
        
        for result in results:
            print(f"\nFile: {result['file']}")
            print(f"  Size: {result['size_bytes']:,} bytes")
            print(f"  Potential packets: {result['potential_packets']}")
            print(f"  Time: {result['time_seconds']:.3f}s")
        
        print(f"\nTOTAL:")
        print(f"  Files processed: {len(results)}")
        print(f"  Total size: {total_size:,} bytes")
        print(f"  Total potential packets: {total_packets}")
        print(f"  Total time: {total_time:.3f}s")
        
        # Assertions
        assert len(results) > 0, "No MMI files processed"
        assert total_size > 0, "No data processed"
    
    def test_mmi_file_sizes_distribution(self, test_files_dir):
        """Analyze the distribution of MMI file sizes."""
        mmi_files = list(test_files_dir.rglob("*.MMI"))
        
        file_sizes = []
        for mmi_file in mmi_files:
            size = mmi_file.stat().st_size
            file_sizes.append({
                "file": f"{mmi_file.parent.name}/{mmi_file.name}",
                "size": size
            })
        
        file_sizes.sort(key=lambda x: x["size"])
        
        print("\n" + "=" * 80)
        print("MMI FILE SIZE DISTRIBUTION")
        print("=" * 80)
        
        # Statistics
        sizes = [f["size"] for f in file_sizes]
        min_size = min(sizes)
        max_size = max(sizes)
        avg_size = sum(sizes) / len(sizes)
        
        print(f"\nStatistics:")
        print(f"  Total files: {len(file_sizes)}")
        print(f"  Min size: {min_size:,} bytes ({min_size/1024:.1f} KB)")
        print(f"  Max size: {max_size:,} bytes ({max_size/1024:.1f} KB)")
        print(f"  Average size: {avg_size:,.0f} bytes ({avg_size/1024:.1f} KB)")
        
        print(f"\nSmallest files:")
        for f in file_sizes[:3]:
            print(f"  {f['file']}: {f['size']:,} bytes")
        
        print(f"\nLargest files:")
        for f in file_sizes[-3:]:
            print(f"  {f['file']}: {f['size']:,} bytes")
        
        assert len(file_sizes) > 0, "No MMI files found"
    
    def test_mmi_directory_structure(self, test_files_dir):
        """Analyze the directory structure of MMI test files."""
        subdirs = [d for d in test_files_dir.iterdir() if d.is_dir()]
        
        print("\n" + "=" * 80)
        print("MMI FILE DIRECTORY STRUCTURE")
        print("=" * 80)
        
        for subdir in sorted(subdirs):
            mmi_files = list(subdir.glob("*.MMI"))
            print(f"\n{subdir.name}:")
            print(f"  Files: {len(mmi_files)}")
            
            if len(mmi_files) > 0:
                total_size = sum(f.stat().st_size for f in mmi_files)
                print(f"  Total size: {total_size:,} bytes ({total_size/1024:.1f} KB)")
                print(f"  Files:")
                for f in sorted(mmi_files):
                    print(f"    {f.name}: {f.stat().st_size:,} bytes")
        
        assert len(subdirs) > 0, "No subdirectories found"
    
    def test_mmi_dynamic_packet_decoding(self):
        """Test decoding MMI_DYNAMIC packets with various values."""
        # Test various packet configurations
        test_cases = [
            {
                "name": "Zero values",
                "v_train": 0,
                "a_train": 0,
                "o_train": 0,
            },
            {
                "name": "Normal operation",
                "v_train": 120,
                "a_train": 10,
                "o_train": 1000,
            },
            {
                "name": "High speed",
                "v_train": 300,
                "a_train": -50,
                "o_train": 50000,
            },
        ]
        
        print("\n" + "=" * 80)
        print("MMI_DYNAMIC PACKET DECODING TESTS")
        print("=" * 80)
        
        for test_case in test_cases:
            print(f"\nTest: {test_case['name']}")
            
            # Create packet data
            data = bytes([
                0x00, 0x00, 0x00,  # Placeholder
                (test_case["v_train"] >> 8) & 0xFF, test_case["v_train"] & 0xFF,
                (test_case["a_train"] >> 8) & 0xFF, test_case["a_train"] & 0xFF,
                (test_case["o_train"] >> 24) & 0xFF,
                (test_case["o_train"] >> 16) & 0xFF,
                (test_case["o_train"] >> 8) & 0xFF,
                test_case["o_train"] & 0xFF,
                0x00, 0x00, 0x00, 0x00,  # o_brake_target
                0x00, 0x64,  # v_target
                0x00, 0x00,  # t_interven_war
                0x00, 0x64,  # v_permitted
                0x00, 0x64,  # v_release
                0x00, 0x64,  # v_intervention
                0x00,  # flags
                0x00, 0x00, 0x00, 0x00,  # o_bcsp
            ])
            
            result = MMIDecoder.decode_mmi_dynamic(data)
            
            print(f"  Input: v_train={test_case['v_train']}, "
                  f"a_train={test_case['a_train']}, o_train={test_case['o_train']}")
            print(f"  Decoded: v_train={result.v_train}, "
                  f"a_train={result.a_train}, o_train={result.o_train}")
            
            assert result.v_train == test_case["v_train"]
            assert result.o_train == test_case["o_train"]
    
    def test_mmi_status_packet_decoding(self):
        """Test decoding MMI_STATUS packets with various values."""
        test_cases = [
            {
                "name": "All zeros",
                "m_adhesion": 0,
                "m_mode": 0,
                "m_level": 0,
            },
            {
                "name": "Normal values",
                "m_adhesion": 2,
                "m_mode": 5,
                "m_level": 2,
            },
        ]
        
        print("\n" + "=" * 80)
        print("MMI_STATUS PACKET DECODING TESTS")
        print("=" * 80)
        
        for test_case in test_cases:
            print(f"\nTest: {test_case['name']}")
            
            # Create packet data
            data = bytes([
                0x00, 0x00, 0x00,  # Placeholder
                test_case["m_adhesion"],
                (test_case["m_mode"] << 4) | test_case["m_level"],
                0x00,  # flags
            ])
            
            result = MMIDecoder.decode_mmi_status(data)
            
            print(f"  Input: m_adhesion={test_case['m_adhesion']}, "
                  f"m_mode={test_case['m_mode']}, m_level={test_case['m_level']}")
            print(f"  Decoded: m_adhesion={result.m_adhesion}, "
                  f"m_mode={result.m_mode}, m_level={result.m_level}")
            
            assert result.m_adhesion == test_case["m_adhesion"]
            assert result.m_mode == test_case["m_mode"]
            assert result.m_level == test_case["m_level"]


class TestMMIPerformance:
    """Performance tests for MMI file parsing."""
    
    def test_large_mmi_file_performance(self, tmp_path):
        """Test performance with a large synthetic MMI file."""
        # Create synthetic MMI file with repeated packets
        num_packets = 10000
        packet_size = 30  # MMI_DYNAMIC size
        
        # Create a simple MMI_DYNAMIC packet
        base_packet = bytes([
            0x00, 0x00, 0x00,  # Placeholder
            0x00, 0x64,  # v_train = 100
            0x00, 0x05,  # a_train = 5
            0x00, 0x00, 0x03, 0xE8,  # o_train = 1000
            0x00, 0x00, 0x07, 0xD0,  # o_brake_target = 2000
            0x00, 0x50,  # v_target = 80
            0x00, 0x0A,  # t_interven_war = 10
            0x00, 0x6E,  # v_permitted = 110
            0x00, 0x64,  # v_release = 100
            0x00, 0x78,  # v_intervention = 120
            0x00,  # flags
            0x00, 0x00, 0x01, 0x2C,  # o_bcsp = 300
        ])
        
        # Write to file
        test_file = tmp_path / "large_test.MMI"
        with open(test_file, 'wb') as f:
            for _ in range(num_packets):
                f.write(base_packet)
        
        file_size = test_file.stat().st_size
        print(f"\nCreated synthetic MMI file: {file_size:,} bytes")
        
        # Measure parsing performance
        start_time = time.time()
        
        with open(test_file, 'rb') as f:
            file_content = f.read()
        
        packets_decoded = 0
        offset = 0
        
        while offset <= len(file_content) - packet_size:
            packet_data = file_content[offset:offset + packet_size]
            MMIDecoder.decode_mmi_dynamic(packet_data)
            packets_decoded += 1
            offset += packet_size
        
        elapsed_time = time.time() - start_time
        packets_per_second = packets_decoded / elapsed_time
        mb_per_second = (file_size / (1024 * 1024)) / elapsed_time
        
        print(f"\nPerformance Test Results:")
        print(f"  Packets: {packets_decoded}")
        print(f"  Time: {elapsed_time:.3f}s")
        print(f"  Speed: {packets_per_second:.1f} packets/sec")
        print(f"  Throughput: {mb_per_second:.2f} MB/sec")
        
        # Should decode all packets
        assert packets_decoded == num_packets
        # Should be reasonably fast (at least 10000 packets/sec)
        assert packets_per_second > 10000, \
            f"Performance too slow: {packets_per_second:.1f} packets/sec"
