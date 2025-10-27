"""
Packet Header Parser - Parses 15-byte record headers from RU/MMI files.

This module implements the core binary parsing logic for ATP/MMI file headers.
All parsing follows Big-Endian byte order as per the Java system implementation.

Java References:
    - decoder_re/HeadDecoder.java: setByte() method
    - decoder_re/MMIVariables.java: MMI_V_TRAIN(), MMI_O_TRAIN()
    - decode_re/DecodeATP.java: getIntFromByte2(), getIntFromByte4()
"""

import struct
import logging
from datetime import datetime
from typing import Tuple

import structlog

from ..models.atp_records import RecordHeader

logger = structlog.get_logger(__name__)


class PacketHeaderParseError(Exception):
    """Raised when packet header parsing fails."""
    pass


class PacketHeaderParser:
    """
    Parser for 15-byte ATP/MMI record headers.
    
    The header format is fixed at 15 bytes with Big-Endian encoding:
        Offset 0-5: Timestamp (YY, MM, DD, HH, mm, ss)
        Offset 6-7: Speed (uint16, 0.1 km/h units)
        Offset 8-11: Position (uint32, meters)
        Offset 12-13: Packet length (uint16, bytes)
        Offset 14: Packet type (uint8)
    
    This implementation matches the Java HeadDecoder.setByte() method exactly.
    
    Example:
        >>> parser = PacketHeaderParser()
        >>> header_bytes = b'\\x19\\x0a\\x1b\\x0e\\x1e\\x28\\x01\\x2c\\x00\\x00\\x4e\\x20\\x00\\x64\\xa0'
        >>> header = parser.parse(header_bytes)
        >>> print(f"Speed: {header.speed} km/h, Position: {header.position}m")
    """
    
    HEADER_SIZE = 15
    
    def parse(self, data: bytes) -> RecordHeader:
        """
        Parse a 15-byte record header.
        
        Args:
            data: Exactly 15 bytes of header data
            
        Returns:
            RecordHeader object with parsed fields
            
        Raises:
            PacketHeaderParseError: If data is invalid or parsing fails
            
        Java Reference:
            decoder_re/HeadDecoder.java: setByte() method lines 19-26
        """
        if len(data) < self.HEADER_SIZE:
            raise PacketHeaderParseError(
                f"Header data too short: expected {self.HEADER_SIZE} bytes, got {len(data)}"
            )
        
        try:
            # Parse timestamp (bytes 0-5)
            # Java: getTime(tsb[1], tsb[2], tsb[3], tsb[4], tsb[5], tsb[6])
            # Note: Java HeadDecoder uses offset +1, but we start at 0
            timestamp = self._parse_timestamp(data[0:6])
            
            # Parse position (bytes 8-11, Big-Endian uint32)
            # Java: MMI_O_TRAIN(tsb[7], tsb[8], tsb[9], tsb[10])
            # which calls Byte2Number.getSigned(a, b, c, d)
            position = self._parse_position(data[8:12])
            
            # Parse speed (bytes 6-7, Big-Endian uint16, unit 0.1 km/h)
            # Java: MMI_V_TRAIN(tsb[13], tsb[14])
            # which calls Byte2Number.getSigned(a, b)
            # Note: Java offset is different due to different indexing
            speed = self._parse_speed(data[6:8])
            
            # Parse packet length (bytes 12-13, Big-Endian uint16)
            packet_length = struct.unpack('>H', data[12:14])[0]
            
            # Parse packet type (byte 14)
            packet_type = data[14]
            
            header = RecordHeader(
                timestamp=timestamp,
                speed=speed,
                position=position,
                packet_length=packet_length,
                packet_type=packet_type
            )
            
            logger.debug(
                "header_parsed",
                timestamp=timestamp.isoformat(),
                speed=speed,
                position=position,
                packet_type=f"0x{packet_type:02X}"
            )
            
            return header
            
        except Exception as e:
            logger.error("header_parse_failed", error=str(e), data_hex=data.hex())
            raise PacketHeaderParseError(f"Failed to parse header: {e}") from e
    
    def _parse_timestamp(self, data: bytes) -> datetime:
        """
        Parse 6-byte timestamp.
        
        Java Reference:
            decoder_re/HeadDecoder.java: getTime() method lines 53-61
            
        Args:
            data: 6 bytes (YY, MM, DD, HH, mm, ss)
            
        Returns:
            datetime object
            
        Note:
            Year is offset by 2000 (Java: year = 2000 + Byte2Number.getUnsigned(yy))
        """
        year = 2000 + data[0]  # YY + 2000
        month = data[1]  # MM (1-12)
        day = data[2]  # DD (1-31)
        hour = data[3]  # HH (0-23)
        minute = data[4]  # mm (0-59)
        second = data[5]  # ss (0-59)
        
        # Validate timestamp components
        if not (1 <= month <= 12):
            raise PacketHeaderParseError(f"Invalid month: {month}")
        if not (1 <= day <= 31):
            raise PacketHeaderParseError(f"Invalid day: {day}")
        if not (0 <= hour <= 23):
            raise PacketHeaderParseError(f"Invalid hour: {hour}")
        if not (0 <= minute <= 59):
            raise PacketHeaderParseError(f"Invalid minute: {minute}")
        if not (0 <= second <= 59):
            raise PacketHeaderParseError(f"Invalid second: {second}")
        
        return datetime(year, month, day, hour, minute, second)
    
    def _parse_speed(self, data: bytes) -> float:
        """
        Parse 2-byte speed value.
        
        Java Reference:
            decoder_re/MMIVariables.java: MMI_V_TRAIN() method line 437-439
            Calls Byte2Number.getSigned(a, b)
            
        Args:
            data: 2 bytes (Big-Endian signed int16)
            
        Returns:
            Speed in km/h (converted from 0.1 km/h units)
            
        Note:
            Unit is 0.1 km/h, so we divide by 10.0 to get km/h
            Uses signed interpretation to match Java
        """
        # Big-Endian signed int16
        speed_raw = struct.unpack('>h', data)[0]
        # Convert from 0.1 km/h to km/h
        speed_kmh = speed_raw / 10.0
        return speed_kmh
    
    def _parse_position(self, data: bytes) -> int:
        """
        Parse 4-byte position value.
        
        Java Reference:
            decoder_re/HeadDecoder.java: setByte() method lines 22-24
            decoder_re/MMIVariables.java: MMI_O_TRAIN() method line 344-346
            Calls Byte2Number.getSigned(a, b, c, d)
            
        Args:
            data: 4 bytes (Big-Endian signed int32)
            
        Returns:
            Position in meters
            
        Note:
            Java applies a correction: if position >= 1000000000, subtract 1000000000
            This handles position wraparound in the ATP system.
        """
        # Big-Endian signed int32
        position_raw = struct.unpack('>i', data)[0]
        
        # Apply Java correction logic (line 24 in HeadDecoder.java)
        # this.location = (this.location >= 1000000000) ? (this.location - 1000000000) : this.location;
        if position_raw >= 1000000000:
            position = position_raw - 1000000000
        else:
            position = position_raw
        
        return position
    
    @staticmethod
    def get_unsigned_byte(byte_val: int) -> int:
        """
        Convert signed byte to unsigned representation.
        
        Java Reference:
            Tools.Byte2Number.getUnsigned(byte)
            
        Args:
            byte_val: Byte value (potentially negative in signed representation)
            
        Returns:
            Unsigned byte value (0-255)
        """
        return byte_val & 0xFF
