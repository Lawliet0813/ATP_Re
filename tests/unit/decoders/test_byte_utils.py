"""
Unit tests for Byte2Number utility class.
"""

import pytest
from atp_re.decoders.byte_utils import Byte2Number


class TestByte2Number:
    """Test cases for Byte2Number utility class."""
    
    def test_get_unsigned_single_byte(self):
        """Test unsigned conversion of single byte."""
        assert Byte2Number.get_unsigned(0x00) == 0
        assert Byte2Number.get_unsigned(0x7F) == 127
        assert Byte2Number.get_unsigned(0x80) == 128
        assert Byte2Number.get_unsigned(0xFF) == 255
        
    def test_get_unsigned_negative_byte(self):
        """Test unsigned conversion handles negative byte values."""
        # In Python, bytes are 0-255, but if we pass -1, it should handle it
        assert Byte2Number.get_unsigned(-1) == 255
        assert Byte2Number.get_unsigned(-128) == 128
    
    def test_get_unsigned_2_bytes(self):
        """Test unsigned 16-bit conversion."""
        assert Byte2Number.get_unsigned_2(0x00, 0x00) == 0
        assert Byte2Number.get_unsigned_2(0x01, 0x00) == 256
        assert Byte2Number.get_unsigned_2(0x00, 0x01) == 1
        assert Byte2Number.get_unsigned_2(0xFF, 0xFF) == 65535
        assert Byte2Number.get_unsigned_2(0x12, 0x34) == 4660  # 0x1234
    
    def test_get_unsigned_3_bytes(self):
        """Test unsigned 24-bit conversion."""
        assert Byte2Number.get_unsigned_3(0x00, 0x00, 0x00) == 0
        assert Byte2Number.get_unsigned_3(0x01, 0x00, 0x00) == 65536
        assert Byte2Number.get_unsigned_3(0x00, 0x01, 0x00) == 256
        assert Byte2Number.get_unsigned_3(0x00, 0x00, 0x01) == 1
        assert Byte2Number.get_unsigned_3(0xFF, 0xFF, 0xFF) == 16777215
        assert Byte2Number.get_unsigned_3(0x12, 0x34, 0x56) == 1193046  # 0x123456
    
    def test_get_unsigned_4_bytes(self):
        """Test unsigned 32-bit conversion."""
        assert Byte2Number.get_unsigned_4(0x00, 0x00, 0x00, 0x00) == 0
        assert Byte2Number.get_unsigned_4(0x01, 0x00, 0x00, 0x00) == 16777216
        assert Byte2Number.get_unsigned_4(0xFF, 0xFF, 0xFF, 0xFF) == 4294967295
        assert Byte2Number.get_unsigned_4(0x12, 0x34, 0x56, 0x78) == 305419896  # 0x12345678
    
    def test_get_signed_2_bytes_positive(self):
        """Test signed 16-bit conversion with positive values."""
        assert Byte2Number.get_signed_2(0x00, 0x00) == 0
        assert Byte2Number.get_signed_2(0x00, 0x01) == 1
        assert Byte2Number.get_signed_2(0x7F, 0xFF) == 32767  # Max positive
    
    def test_get_signed_2_bytes_negative(self):
        """Test signed 16-bit conversion with negative values."""
        assert Byte2Number.get_signed_2(0x80, 0x00) == -32768  # Min negative
        assert Byte2Number.get_signed_2(0xFF, 0xFF) == -1
        assert Byte2Number.get_signed_2(0xFF, 0xFE) == -2
    
    def test_get_signed_3_bytes_positive(self):
        """Test signed 24-bit conversion with positive values."""
        assert Byte2Number.get_signed_3(0x00, 0x00, 0x00) == 0
        assert Byte2Number.get_signed_3(0x00, 0x00, 0x01) == 1
        assert Byte2Number.get_signed_3(0x7F, 0xFF, 0xFF) == 8388607  # Max positive
    
    def test_get_signed_3_bytes_negative(self):
        """Test signed 24-bit conversion with negative values."""
        assert Byte2Number.get_signed_3(0x80, 0x00, 0x00) == -8388608  # Min negative
        assert Byte2Number.get_signed_3(0xFF, 0xFF, 0xFF) == -1
        assert Byte2Number.get_signed_3(0xFF, 0xFF, 0xFE) == -2
    
    def test_get_signed_4_bytes_positive(self):
        """Test signed 32-bit conversion with positive values."""
        assert Byte2Number.get_signed_4(0x00, 0x00, 0x00, 0x00) == 0
        assert Byte2Number.get_signed_4(0x00, 0x00, 0x00, 0x01) == 1
        assert Byte2Number.get_signed_4(0x7F, 0xFF, 0xFF, 0xFF) == 2147483647  # Max positive
    
    def test_get_signed_4_bytes_negative(self):
        """Test signed 32-bit conversion with negative values."""
        assert Byte2Number.get_signed_4(0x80, 0x00, 0x00, 0x00) == -2147483648  # Min negative
        assert Byte2Number.get_signed_4(0xFF, 0xFF, 0xFF, 0xFF) == -1
        assert Byte2Number.get_signed_4(0xFF, 0xFF, 0xFF, 0xFE) == -2
    
    def test_bytes_object_input(self):
        """Test that bytes objects work as input."""
        data = b'\x12\x34\x56\x78'
        assert Byte2Number.get_unsigned(data[0:1]) == 0x12
        assert Byte2Number.get_unsigned_2(data[0:1], data[1:2]) == 0x1234
        assert Byte2Number.get_unsigned_4(data[0:1], data[1:2], data[2:3], data[3:4]) == 0x12345678
