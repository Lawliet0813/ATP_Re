#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
解碼實際RU與MMI檔 (Decode Real RU and MMI Files)

This script decodes the test RU and MMI files and outputs all decoded data
to both text and JSON formats for analysis.

Files decoded:
- tests/RU_file/024423.RU
- tests/MMI_file/00000267_001170--_775495--_-9042/114011.MMI
- tests/MMI_file/00000267_001170--_775495--_-9042/120001.MMI
"""

import sys
from pathlib import Path
from typing import List, Dict, Any

# Add parent directory to path for imports
sys.path.insert(0, str(Path(__file__).parent))

from src.atp_re.decoders import RUDecoder, PacketFormatter


def decode_file(file_path: Path) -> tuple[List[Dict[str, Any]], dict]:
    """
    Decode all packets from a file.
    
    Args:
        file_path: Path to the file to decode
        
    Returns:
        Tuple of (packets list, statistics dict)
    """
    decoder = RUDecoder()
    packets = []
    
    with open(file_path, 'rb') as f:
        file_content = f.read()
    
    print(f"Decoding {file_path.name}...")
    print(f"File size: {len(file_content)} bytes")
    
    offset = 0
    packet_count = 0
    errors = []
    packet_types = {}
    
    while offset < len(file_content):
        # Check minimum header size
        if offset + 15 > len(file_content):
            break
        
        try:
            # Get body length from byte 15
            if offset + 16 > len(file_content):
                break
            
            body_length = file_content[offset + 15]
            packet_length = 16 + body_length
            
            if offset + packet_length > len(file_content):
                break
            
            # Extract and decode packet
            packet_data = file_content[offset:offset + packet_length]
            result = decoder.decode(packet_data)
            
            # Convert to dictionary
            packet_dict = result.to_dict()
            packets.append(packet_dict)
            
            # Track packet types
            pkt_type = packet_dict.get('packet_type', 'unknown')
            packet_types[pkt_type] = packet_types.get(pkt_type, 0) + 1
            
            packet_count += 1
            offset += packet_length
            
        except Exception as e:
            errors.append({
                "offset": offset,
                "error": str(e)
            })
            # Try to skip this packet and continue
            offset += 1
    
    statistics = {
        "file_name": file_path.name,
        "file_size": len(file_content),
        "total_packets": packet_count,
        "errors": len(errors),
        "packet_types": packet_types,
    }
    
    print(f"  Successfully decoded {packet_count} packets")
    if errors:
        print(f"  Encountered {len(errors)} errors")
    print()
    
    return packets, statistics


def save_output(packets: List[Dict[str, Any]], output_prefix: str):
    """
    Save decoded packets to both text and JSON formats.
    
    Args:
        packets: List of decoded packet dictionaries
        output_prefix: Prefix for output files (without extension)
    """
    formatter = PacketFormatter()
    
    # Save as text
    text_path = Path(f"{output_prefix}.txt")
    text_output = formatter.format_packet_list(packets, format_type="text")
    with open(text_path, 'w', encoding='utf-8') as f:
        f.write(text_output)
    print(f"Saved text output to: {text_path}")
    
    # Save as JSON
    json_path = Path(f"{output_prefix}.json")
    json_output = formatter.format_packet_json(packets)
    with open(json_path, 'w', encoding='utf-8') as f:
        f.write(json_output)
    print(f"Saved JSON output to: {json_path}")
    print()


def print_summary(all_statistics: List[dict]):
    """
    Print summary of all decoded files.
    
    Args:
        all_statistics: List of statistics dictionaries
    """
    print("=" * 80)
    print("SUMMARY OF DECODED FILES")
    print("=" * 80)
    print()
    
    for stats in all_statistics:
        print(f"File: {stats['file_name']}")
        print(f"  Size: {stats['file_size']:,} bytes")
        print(f"  Total Packets: {stats['total_packets']}")
        print(f"  Errors: {stats['errors']}")
        print(f"  Packet Types:")
        for pkt_type, count in sorted(stats['packet_types'].items()):
            print(f"    Type {pkt_type}: {count} packets")
        print()


def main():
    """Main function."""
    print("=" * 80)
    print("解碼實際RU與MMI檔 (Decode Real RU and MMI Files)")
    print("=" * 80)
    print()
    
    # Define files to decode
    base_path = Path(__file__).parent
    files_to_decode = [
        (
            base_path / "tests" / "RU_file" / "024423.RU",
            base_path / "tests" / "RU_file" / "024423_decoded"
        ),
        (
            base_path / "tests" / "MMI_file" / "00000267_001170--_775495--_-9042" / "114011.MMI",
            base_path / "tests" / "MMI_file" / "00000267_001170--_775495--_-9042" / "114011_decoded"
        ),
        (
            base_path / "tests" / "MMI_file" / "00000267_001170--_775495--_-9042" / "120001.MMI",
            base_path / "tests" / "MMI_file" / "00000267_001170--_775495--_-9042" / "120001_decoded"
        ),
    ]
    
    all_statistics = []
    
    # Decode each file
    for input_path, output_prefix in files_to_decode:
        if not input_path.exists():
            print(f"Warning: File not found: {input_path}")
            print()
            continue
        
        # Decode file
        packets, statistics = decode_file(input_path)
        all_statistics.append(statistics)
        
        # Save output
        save_output(packets, str(output_prefix))
    
    # Print summary
    print_summary(all_statistics)
    
    print("=" * 80)
    print("Decoding completed successfully!")
    print("=" * 80)
    print()
    print("Output files generated:")
    print("  - *.txt files: Human-readable format with field descriptions")
    print("  - *.json files: Machine-readable JSON format for further analysis")
    print()
    print("Use these files to analyze the decoded packet data.")
    
    return 0


if __name__ == "__main__":
    sys.exit(main())
