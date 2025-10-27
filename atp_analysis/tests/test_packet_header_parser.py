"""
Unit tests for PacketHeaderParser.

Tests verify 100% compatibility with Java HeadDecoder implementation.
"""

import pytest
from datetime import datetime

from src.decoder.packet_header_parser import PacketHeaderParser, PacketHeaderParseError
from src.models.atp_records import RecordHeader


class TestPacketHeaderParser:
    """Test suite for PacketHeaderParser."""
    
    def setup_method(self):
        """Set up test fixtures."""
        self.parser = PacketHeaderParser()
    
    def test_parse_valid_header(self):
        """Test parsing a valid 15-byte header."""
        # Test data: 2025-10-27 14:30:40, speed=30.0 km/h, position=20000m, len=100, type=0xA0
        header_bytes = bytes([
            0x19,  # Year: 25 (2025)
            0x0A,  # Month: 10
            0x1B,  # Day: 27
            0x0E,  # Hour: 14
            0x1E,  # Minute: 30
            0x28,  # Second: 40
            0x01, 0x2C,  # Speed: 300 * 0.1 = 30.0 km/h (Big-Endian)
            0x00, 0x00, 0x4E, 0x20,  # Position: 20000 meters (Big-Endian)
            0x00, 0x64,  # Packet length: 100 bytes (Big-Endian)
            0xA0,  # Packet type: MMI (0xA0)
        ])
        
        header = self.parser.parse(header_bytes)
        
        assert header.timestamp == datetime(2025, 10, 27, 14, 30, 40)
        assert header.speed == 30.0
        assert header.position == 20000
        assert header.packet_length == 100
        assert header.packet_type == 0xA0
        assert header.is_mmi_packet is True
        assert header.is_atp_packet is False
    
    def test_parse_atp_packet_type(self):
        """Test parsing header with ATP packet type."""
        header_bytes = bytes([
            0x19, 0x01, 0x01, 0x00, 0x00, 0x00,  # Timestamp
            0x00, 0x00,  # Speed: 0
            0x00, 0x00, 0x00, 0x00,  # Position: 0
            0x00, 0x50,  # Length: 80
            0x50,  # Type: ATP (0x50)
        ])
        
        header = self.parser.parse(header_bytes)
        
        assert header.packet_type == 0x50
        assert header.is_atp_packet is True
        assert header.is_mmi_packet is False
    
    def test_parse_high_speed(self):
        """Test parsing header with high speed value."""
        # Test speed: 350 km/h = 3500 * 0.1 km/h = 0x0DAC in hex
        header_bytes = bytes([
            0x19, 0x01, 0x01, 0x00, 0x00, 0x00,  # Timestamp
            0x0D, 0xAC,  # Speed: 3500 * 0.1 = 350.0 km/h
            0x00, 0x01, 0x86, 0xA0,  # Position: 100000m
            0x00, 0x64,  # Length: 100
            0xA0,  # Type: MMI
        ])
        
        header = self.parser.parse(header_bytes)
        
        assert header.speed == 350.0
        assert header.position == 100000
    
    def test_parse_negative_speed(self):
        """Test parsing header with negative speed (signed value)."""
        # Negative speed: -10.0 km/h = -100 * 0.1 = 0xFF9C (two's complement)
        header_bytes = bytes([
            0x19, 0x01, 0x01, 0x00, 0x00, 0x00,  # Timestamp
            0xFF, 0x9C,  # Speed: -100 * 0.1 = -10.0 km/h (signed)
            0x00, 0x00, 0x00, 0x00,  # Position: 0
            0x00, 0x64,  # Length: 100
            0xA0,  # Type: MMI
        ])
        
        header = self.parser.parse(header_bytes)
        
        assert header.speed == -10.0
    
    def test_parse_position_wraparound(self):
        """Test position wraparound correction (Java logic: if >= 1000000000, subtract 1000000000)."""
        # Position: 1000000100 should become 100 after correction
        # 1000000100 = 0x3B9ACA64
        header_bytes = bytes([
            0x19, 0x01, 0x01, 0x00, 0x00, 0x00,  # Timestamp
            0x00, 0x00,  # Speed: 0
            0x3B, 0x9A, 0xCA, 0x64,  # Position: 1000000100 (Big-Endian)
            0x00, 0x64,  # Length: 100
            0xA0,  # Type: MMI
        ])
        
        header = self.parser.parse(header_bytes)
        
        # Java correction: location >= 1000000000 ? location - 1000000000 : location
        assert header.position == 100  # 1000000100 - 1000000000 = 100
    
    def test_parse_position_below_wraparound(self):
        """Test position below wraparound threshold (no correction applied)."""
        # Position: 999999999 should remain unchanged
        # 999999999 = 0x3B9AC9FF
        header_bytes = bytes([
            0x19, 0x01, 0x01, 0x00, 0x00, 0x00,  # Timestamp
            0x00, 0x00,  # Speed: 0
            0x3B, 0x9A, 0xC9, 0xFF,  # Position: 999999999 (Big-Endian)
            0x00, 0x64,  # Length: 100
            0xA0,  # Type: MMI
        ])
        
        header = self.parser.parse(header_bytes)
        
        assert header.position == 999999999  # No correction
    
    def test_parse_year_2000(self):
        """Test parsing year at boundary (2000)."""
        header_bytes = bytes([
            0x00, 0x01, 0x01, 0x00, 0x00, 0x00,  # Timestamp: 2000-01-01 00:00:00
            0x00, 0x00,  # Speed
            0x00, 0x00, 0x00, 0x00,  # Position
            0x00, 0x64,  # Length
            0xA0,  # Type
        ])
        
        header = self.parser.parse(header_bytes)
        
        assert header.timestamp.year == 2000
    
    def test_parse_year_2099(self):
        """Test parsing year at upper boundary (2099)."""
        header_bytes = bytes([
            0x63, 0x0C, 0x1F, 0x17, 0x3B, 0x3B,  # Timestamp: 2099-12-31 23:59:59
            0x00, 0x00,  # Speed
            0x00, 0x00, 0x00, 0x00,  # Position
            0x00, 0x64,  # Length
            0xA0,  # Type
        ])
        
        header = self.parser.parse(header_bytes)
        
        assert header.timestamp == datetime(2099, 12, 31, 23, 59, 59)
    
    def test_parse_header_too_short(self):
        """Test parsing fails with insufficient data."""
        short_data = bytes([0x19, 0x01, 0x01])  # Only 3 bytes
        
        with pytest.raises(PacketHeaderParseError, match="Header data too short"):
            self.parser.parse(short_data)
    
    def test_parse_invalid_month(self):
        """Test parsing fails with invalid month."""
        header_bytes = bytes([
            0x19, 0x0D, 0x01, 0x00, 0x00, 0x00,  # Month: 13 (invalid)
            0x00, 0x00,  # Speed
            0x00, 0x00, 0x00, 0x00,  # Position
            0x00, 0x64,  # Length
            0xA0,  # Type
        ])
        
        with pytest.raises(PacketHeaderParseError, match="Invalid month"):
            self.parser.parse(header_bytes)
    
    def test_parse_invalid_day(self):
        """Test parsing fails with invalid day."""
        header_bytes = bytes([
            0x19, 0x01, 0x20, 0x00, 0x00, 0x00,  # Day: 32 (invalid)
            0x00, 0x00,  # Speed
            0x00, 0x00, 0x00, 0x00,  # Position
            0x00, 0x64,  # Length
            0xA0,  # Type
        ])
        
        with pytest.raises(PacketHeaderParseError, match="Invalid day"):
            self.parser.parse(header_bytes)
    
    def test_parse_invalid_hour(self):
        """Test parsing fails with invalid hour."""
        header_bytes = bytes([
            0x19, 0x01, 0x01, 0x18, 0x00, 0x00,  # Hour: 24 (invalid)
            0x00, 0x00,  # Speed
            0x00, 0x00, 0x00, 0x00,  # Position
            0x00, 0x64,  # Length
            0xA0,  # Type
        ])
        
        with pytest.raises(PacketHeaderParseError, match="Invalid hour"):
            self.parser.parse(header_bytes)
    
    def test_parse_boundary_values(self):
        """Test parsing with boundary time values."""
        # Min values: 2000-01-01 00:00:00
        min_bytes = bytes([
            0x00, 0x01, 0x01, 0x00, 0x00, 0x00,
            0x00, 0x00,
            0x00, 0x00, 0x00, 0x00,
            0x00, 0x0F,  # Min packet length: 15
            0xA0,
        ])
        
        min_header = self.parser.parse(min_bytes)
        assert min_header.timestamp == datetime(2000, 1, 1, 0, 0, 0)
        assert min_header.packet_length == 15
        
        # Max time values: 23:59:59
        max_bytes = bytes([
            0x19, 0x01, 0x01, 0x17, 0x3B, 0x3B,  # 23:59:59
            0x00, 0x00,
            0x00, 0x00, 0x00, 0x00,
            0xFF, 0xFF,  # Max packet length: 65535
            0xA0,
        ])
        
        max_header = self.parser.parse(max_bytes)
        assert max_header.timestamp.hour == 23
        assert max_header.timestamp.minute == 59
        assert max_header.timestamp.second == 59
        assert max_header.packet_length == 65535
    
    def test_get_unsigned_byte(self):
        """Test unsigned byte conversion utility."""
        assert PacketHeaderParser.get_unsigned_byte(0) == 0
        assert PacketHeaderParser.get_unsigned_byte(127) == 127
        assert PacketHeaderParser.get_unsigned_byte(128) == 128
        assert PacketHeaderParser.get_unsigned_byte(255) == 255
        assert PacketHeaderParser.get_unsigned_byte(-1) == 255
        assert PacketHeaderParser.get_unsigned_byte(-128) == 128


class TestRecordHeaderModel:
    """Test RecordHeader dataclass properties."""
    
    def test_is_atp_packet(self):
        """Test ATP packet type detection."""
        header = RecordHeader(
            timestamp=datetime(2025, 1, 1),
            speed=0.0,
            position=0,
            packet_length=50,
            packet_type=0x50
        )
        
        assert header.is_atp_packet is True
        assert header.is_mmi_packet is False
    
    def test_is_mmi_packet(self):
        """Test MMI packet type detection."""
        header = RecordHeader(
            timestamp=datetime(2025, 1, 1),
            speed=0.0,
            position=0,
            packet_length=50,
            packet_type=0xA0
        )
        
        assert header.is_mmi_packet is True
        assert header.is_atp_packet is False
    
    def test_unknown_packet_type(self):
        """Test with unknown packet type."""
        header = RecordHeader(
            timestamp=datetime(2025, 1, 1),
            speed=0.0,
            position=0,
            packet_length=50,
            packet_type=0xFF
        )
        
        assert header.is_atp_packet is False
        assert header.is_mmi_packet is False
