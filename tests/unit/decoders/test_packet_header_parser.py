"""
Unit tests for PacketHeaderParser.
"""

import pytest
from datetime import datetime
from atp_re.decoders.packet_header_parser import PacketHeaderParser, PacketHeader


class TestPacketHeaderParser:
    """Test cases for PacketHeaderParser."""
    
    def test_parse_basic_header(self):
        """Test parsing a basic packet header."""
        # Create test data: packet_no=1, timestamp=2023-10-15 14:30:45, location=1000, speed=120
        data = bytes([
            0x01,  # packet_no = 1
            0x17,  # year = 23 (2023)
            0x0A,  # month = 10
            0x0F,  # day = 15
            0x0E,  # hour = 14
            0x1E,  # minute = 30
            0x2D,  # second = 45
            0x00, 0x00, 0x03, 0xE8,  # location = 1000 (big-endian)
            0x00, 0x00,  # reserved
            0x00, 0x78,  # speed = 120 (big-endian)
        ])
        
        header = PacketHeaderParser.parse(data)
        
        assert header.packet_no == 1
        assert header.timestamp == datetime(2023, 10, 15, 14, 30, 45)
        assert header.location == 1000
        assert header.speed == 120
    
    def test_parse_header_with_large_location(self):
        """Test location adjustment when >= 1 billion."""
        # location = 1,000,000,500 should be adjusted to 500
        data = bytes([
            0x05,  # packet_no = 5
            0x18, 0x01, 0x01, 0x00, 0x00, 0x00,  # timestamp
            0x3B, 0x9A, 0xCB, 0xF4,  # location = 1000000500
            0x00, 0x00,  # reserved
            0x00, 0x00,  # speed = 0
        ])
        
        header = PacketHeaderParser.parse(data)
        
        assert header.packet_no == 5
        assert header.location == 500  # Adjusted from 1000000500
    
    def test_parse_header_max_values(self):
        """Test parsing with maximum byte values."""
        data = bytes([
            0xFF,  # packet_no = 255
            0x63, 0x0C, 0x1F, 0x17, 0x3B, 0x3B,  # timestamp = 2099-12-31 23:59:59
            0xFF, 0xFF, 0xFF, 0xFF,  # location = max
            0x00, 0x00,  # reserved
            0xFF, 0xFF,  # speed = 65535
        ])
        
        header = PacketHeaderParser.parse(data)
        
        assert header.packet_no == 255
        assert header.timestamp == datetime(2099, 12, 31, 23, 59, 59)
        # location should be adjusted
        assert header.location == 4294967295 - 1000000000
        assert header.speed == 65535
    
    def test_parse_header_zero_values(self):
        """Test parsing with zero values."""
        data = bytes([
            0x00,  # packet_no = 0
            0x00, 0x01, 0x01, 0x00, 0x00, 0x00,  # timestamp = 2000-01-01 00:00:00
            0x00, 0x00, 0x00, 0x00,  # location = 0
            0x00, 0x00,  # reserved
            0x00, 0x00,  # speed = 0
        ])
        
        header = PacketHeaderParser.parse(data)
        
        assert header.packet_no == 0
        assert header.timestamp == datetime(2000, 1, 1, 0, 0, 0)
        assert header.location == 0
        assert header.speed == 0
    
    def test_parse_header_too_short(self):
        """Test that parsing fails with insufficient data."""
        data = bytes([0x01, 0x17, 0x0A])  # Only 3 bytes
        
        with pytest.raises(ValueError, match="Data too short"):
            PacketHeaderParser.parse(data)
    
    def test_parse_header_invalid_timestamp(self):
        """Test that invalid timestamp raises error."""
        data = bytes([
            0x01,  # packet_no = 1
            0x17, 0x0D, 0x20, 0x00, 0x00, 0x00,  # Invalid: month=13, day=32
            0x00, 0x00, 0x00, 0x00,  # location
            0x00, 0x00,  # reserved
            0x00, 0x00,  # speed
        ])
        
        with pytest.raises(ValueError, match="Invalid timestamp"):
            PacketHeaderParser.parse(data)
    
    def test_parse_header_and_body(self):
        """Test parsing both header and body."""
        # Create packet with header and body
        header_data = bytes([
            0x01,  # packet_no = 1
            0x17, 0x0A, 0x0F, 0x0E, 0x1E, 0x2D,  # timestamp
            0x00, 0x00, 0x03, 0xE8,  # location
            0x00, 0x00,  # reserved
            0x00, 0x78,  # speed
        ])
        body_length = bytes([0x05])  # body length = 5
        body_data = bytes([0x11, 0x22, 0x33, 0x44, 0x55])
        
        full_packet = header_data + body_length + body_data
        
        header, body = PacketHeaderParser.parse_header_and_body(full_packet)
        
        assert header.packet_no == 1
        assert header.location == 1000
        assert len(body) == 5
        assert body == body_data
    
    def test_parse_header_and_body_zero_length(self):
        """Test parsing packet with zero-length body."""
        header_data = bytes([
            0x01,  # packet_no
            0x17, 0x0A, 0x0F, 0x0E, 0x1E, 0x2D,  # timestamp
            0x00, 0x00, 0x00, 0x00,  # location
            0x00, 0x00,  # reserved
            0x00, 0x00,  # speed
        ])
        body_length = bytes([0x00])  # body length = 0
        
        full_packet = header_data + body_length
        
        header, body = PacketHeaderParser.parse_header_and_body(full_packet)
        
        assert header.packet_no == 1
        assert len(body) == 0
    
    def test_parse_header_and_body_insufficient_data(self):
        """Test that insufficient body data raises error."""
        header_data = bytes([
            0x01,  # packet_no
            0x17, 0x0A, 0x0F, 0x0E, 0x1E, 0x2D,  # timestamp
            0x00, 0x00, 0x00, 0x00,  # location
            0x00, 0x00,  # reserved
            0x00, 0x00,  # speed
        ])
        body_length = bytes([0x0A])  # claims body length = 10
        body_data = bytes([0x11, 0x22])  # but only 2 bytes
        
        full_packet = header_data + body_length + body_data
        
        with pytest.raises(ValueError, match="Data too short for body"):
            PacketHeaderParser.parse_header_and_body(full_packet)
    
    def test_parse_header_and_body_no_length_byte(self):
        """Test that missing length byte raises error."""
        header_data = bytes([
            0x01,  # packet_no
            0x17, 0x0A, 0x0F, 0x0E, 0x1E, 0x2D,  # timestamp
            0x00, 0x00, 0x00, 0x00,  # location
            0x00, 0x00,  # reserved
            0x00, 0x00,  # speed
        ])
        # No body length byte
        
        with pytest.raises(ValueError, match="Data too short for header and length"):
            PacketHeaderParser.parse_header_and_body(header_data)
