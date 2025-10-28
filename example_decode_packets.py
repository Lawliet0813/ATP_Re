#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Example: Displaying All Decoded Packet Values
請將解出的封包數值都列出來

This example demonstrates how to decode ATP packets and display
all decoded values in a clear, structured format.
"""

import sys
from pathlib import Path

# Add parent directory to path
sys.path.insert(0, str(Path(__file__).parent))

from src.atp_re.decoders import RUDecoder, PacketFormatter


def example_decode_single_packet():
    """Example 1: Decode a single packet and display all values"""
    print("=" * 80)
    print("Example 1: Decode Single Packet")
    print("=" * 80)
    
    decoder = RUDecoder()
    
    # Create a sample MMI_DYNAMIC packet
    test_data = bytes([
        1, 24, 1, 1, 12, 0, 0,     # Header: packet_no=1, timestamp
        0, 0, 0, 100,              # location=100m
        0, 0, 50, 0,               # speed=12800 (byte order)
        30,                        # body_length=30
        1, 0, 0,                   # MMI_DYNAMIC header
        0, 50,                     # v_train=50
        0, 0,                      # a_train=0
        0, 0, 0, 100,             # o_train=100
        0, 0, 0, 200,             # o_brake_target=200
        0, 45,                     # v_target=45
        0, 10,                     # t_interven_war=10
        0, 55,                     # v_permitted=55
        0, 40,                     # v_release=40
        0, 60,                     # v_intervention=60
        0x10,                      # warning/slip/slide
        0, 0, 0, 150              # o_bcsp=150
    ])
    
    # Decode the packet
    result = decoder.decode(test_data)
    
    # Display all decoded values in text format
    formatter = PacketFormatter()
    print("\n--- Text Format (Human Readable) ---")
    print(formatter.format_packet(result.to_dict()))
    
    # Display in JSON format
    print("\n--- JSON Format (Machine Readable) ---")
    print(formatter.format_packet_json(result.to_dict()))
    print()


def example_decode_file():
    """Example 2: Decode multiple packets from a file"""
    print("=" * 80)
    print("Example 2: Decode Packets from File")
    print("=" * 80)
    
    # Check if test file exists
    test_file = Path("tests/RU_file/024423.RU")
    if not test_file.exists():
        print(f"Test file not found: {test_file}")
        print("Skipping this example.")
        return
    
    decoder = RUDecoder()
    formatter = PacketFormatter()
    
    # Read and decode first 5 packets
    with open(test_file, 'rb') as f:
        file_content = f.read()
    
    print(f"File size: {len(file_content)} bytes")
    print("Decoding first 5 packets...\n")
    
    packets = []
    offset = 0
    
    while offset < len(file_content) and len(packets) < 5:
        if offset + 16 > len(file_content):
            break
        
        try:
            body_length = file_content[offset + 15]
            packet_length = 16 + body_length
            
            if offset + packet_length > len(file_content):
                break
            
            packet_data = file_content[offset:offset + packet_length]
            result = decoder.decode(packet_data)
            packets.append(result.to_dict())
            
            offset += packet_length
        except Exception as e:
            print(f"Error at offset {offset}: {e}")
            offset += 1
    
    # Display all decoded packets
    print(formatter.format_packet_list(packets, format_type="text"))


def example_access_decoded_values():
    """Example 3: Access individual decoded values programmatically"""
    print("=" * 80)
    print("Example 3: Access Decoded Values Programmatically")
    print("=" * 80)
    
    decoder = RUDecoder()
    
    # Create a sample packet
    test_data = bytes([
        1, 24, 1, 1, 12, 0, 0,
        0, 0, 0, 100,
        0, 0, 50, 0,
        30,
        1, 0, 0,
        0, 75,    # v_train=75 km/h
        0, 5,     # a_train=5 cm/s²
        0, 0, 1, 0,     # o_train=256m
        0, 0, 2, 0,     # o_brake_target=512m
        0, 80,    # v_target=80
        0, 15,    # t_interven_war=15
        0, 90,    # v_permitted=90
        0, 70,    # v_release=70
        0, 95,    # v_intervention=95
        0x20,     # warning mode 2
        0, 0, 1, 100    # o_bcsp=356
    ])
    
    # Decode
    result = decoder.decode(test_data)
    
    # Access header values
    print("\n--- Header Information ---")
    print(f"Packet Type: {result.packet_type}")
    print(f"Description: {result.description}")
    print(f"Timestamp: {result.header.timestamp}")
    print(f"Location: {result.header.location} meters")
    print(f"Speed (from header): {result.header.speed} km/h")
    
    # Access decoded data values
    if result.data:
        print("\n--- Decoded Data Values ---")
        print(f"Train Speed: {result.data.v_train} km/h")
        print(f"Train Acceleration: {result.data.a_train} cm/s²")
        print(f"Train Position: {result.data.o_train} meters")
        print(f"Brake Target: {result.data.o_brake_target} meters")
        print(f"Target Speed: {result.data.v_target} km/h")
        print(f"Permitted Speed: {result.data.v_permitted} km/h")
        print(f"Warning Mode: {result.data.m_warning}")
        print(f"Slip Indication: {'Yes' if result.data.m_slip else 'No'}")
        print(f"Slide Indication: {'Yes' if result.data.m_slide else 'No'}")
    
    # Convert to dictionary for other uses
    print("\n--- As Dictionary ---")
    packet_dict = result.to_dict()
    print(f"Keys available: {list(packet_dict.keys())}")
    if packet_dict['data']:
        print(f"Data fields: {list(packet_dict['data'].keys())}")
    print()


def main():
    """Run all examples"""
    print("\n" + "=" * 80)
    print("ATP Packet Decoder Examples")
    print("Demonstrating: 請將解出的封包數值都列出來")
    print("(Display All Decoded Packet Values)")
    print("=" * 80 + "\n")
    
    # Run examples
    example_decode_single_packet()
    print("\n")
    
    example_decode_file()
    print("\n")
    
    example_access_decoded_values()
    
    print("\n" + "=" * 80)
    print("Examples completed!")
    print("=" * 80 + "\n")
    
    print("For more information:")
    print("  - See DECODE_PACKETS_USAGE.md for CLI tool documentation")
    print("  - Use: python decode_packets.py --help")
    print("  - Check src/atp_re/decoders/ for decoder implementation")


if __name__ == "__main__":
    main()
