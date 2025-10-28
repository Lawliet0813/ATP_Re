#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
ATP Packet Decoder CLI Tool

This tool decodes ATP packet files and displays all decoded values.
Usage: python decode_packets.py <input_file> [options]
"""

import argparse
import sys
from pathlib import Path
from typing import List, Dict, Any

# Add parent directory to path for imports
sys.path.insert(0, str(Path(__file__).parent.parent))

from src.atp_re.decoders import RUDecoder, PacketFormatter


def decode_ru_file(file_path: Path, max_packets: int = None) -> List[Dict[str, Any]]:
    """
    Decode all packets from an RU file.
    
    Args:
        file_path: Path to the RU file
        max_packets: Maximum number of packets to decode (None = all)
        
    Returns:
        List of decoded packet dictionaries
    """
    decoder = RUDecoder()
    packets = []
    
    with open(file_path, 'rb') as f:
        file_content = f.read()
    
    print(f"File size: {len(file_content)} bytes")
    print(f"Decoding packets...\n")
    
    offset = 0
    packet_count = 0
    errors = []
    
    while offset < len(file_content):
        # Check if we've reached max_packets
        if max_packets and packet_count >= max_packets:
            break
        
        # RU packets have a minimum header size of 15 bytes
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
            
            packet_count += 1
            offset += packet_length
            
        except Exception as e:
            errors.append({
                "offset": offset,
                "error": str(e)
            })
            # Try to skip this packet and continue
            offset += 1
    
    print(f"Successfully decoded {packet_count} packets")
    if errors:
        print(f"Encountered {len(errors)} errors")
    
    return packets


def main():
    """Main function."""
    parser = argparse.ArgumentParser(
        description="Decode ATP packet files and display all decoded values"
    )
    parser.add_argument(
        "input_file",
        type=str,
        help="Path to the input RU file"
    )
    parser.add_argument(
        "-n", "--max-packets",
        type=int,
        default=None,
        help="Maximum number of packets to decode (default: all)"
    )
    parser.add_argument(
        "-f", "--format",
        choices=["text", "json"],
        default="text",
        help="Output format (default: text)"
    )
    parser.add_argument(
        "-o", "--output",
        type=str,
        default=None,
        help="Output file (default: stdout)"
    )
    parser.add_argument(
        "-v", "--verbose",
        action="store_true",
        help="Verbose output"
    )
    
    args = parser.parse_args()
    
    # Check if input file exists
    input_path = Path(args.input_file)
    if not input_path.exists():
        print(f"Error: File not found: {input_path}", file=sys.stderr)
        return 1
    
    # Decode packets
    try:
        packets = decode_ru_file(input_path, args.max_packets)
        
        if not packets:
            print("No packets decoded", file=sys.stderr)
            return 1
        
        # Format output
        formatter = PacketFormatter()
        if args.format == "json":
            output = formatter.format_packet_json(packets)
        else:
            output = formatter.format_packet_list(packets, format_type="text")
        
        # Write output
        if args.output:
            output_path = Path(args.output)
            with open(output_path, 'w', encoding='utf-8') as f:
                f.write(output)
            print(f"\nOutput written to: {output_path}")
        else:
            print("\n" + "="*80)
            print(output)
            print("="*80)
        
        return 0
        
    except Exception as e:
        print(f"Error: {e}", file=sys.stderr)
        if args.verbose:
            import traceback
            traceback.print_exc()
        return 1


if __name__ == "__main__":
    sys.exit(main())
