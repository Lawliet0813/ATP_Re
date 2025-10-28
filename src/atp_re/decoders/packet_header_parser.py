"""
Packet Header Parser for ATP packets.

This module provides functionality to parse packet headers from ATP data,
matching the Java HeadDecoder functionality.
"""

from datetime import datetime
from dataclasses import dataclass
from typing import Tuple
from .byte_utils import Byte2Number


@dataclass
class PacketHeader:
    """
    Parsed packet header information.
    
    Attributes:
        packet_no: Packet number/type identifier (0-255)
        timestamp: Timestamp when packet was recorded
        location: Train location in meters (adjusted for values >= 1 billion)
        speed: Train speed in km/h
    """
    packet_no: int
    timestamp: datetime
    location: int
    speed: int


class PacketHeaderParser:
    """
    Parser for ATP packet headers.
    
    Packet header structure (15 bytes):
    - Byte 0: Packet number
    - Bytes 1-6: Timestamp (YY MM DD HH MM SS)
    - Bytes 7-10: Location (4 bytes, big-endian)
    - Bytes 11-12: Reserved
    - Bytes 13-14: Speed (2 bytes, big-endian)
    """
    
    HEADER_SIZE = 15
    
    @staticmethod
    def parse(data: bytes) -> PacketHeader:
        """
        Parse packet header from raw bytes.
        
        Args:
            data: Raw byte data (must be at least 15 bytes)
            
        Returns:
            PacketHeader object with parsed information
            
        Raises:
            ValueError: If data is too short or timestamp is invalid
        """
        if len(data) < PacketHeaderParser.HEADER_SIZE:
            raise ValueError(
                f"Data too short: expected at least {PacketHeaderParser.HEADER_SIZE} "
                f"bytes, got {len(data)}"
            )
        
        # Parse packet number
        packet_no = Byte2Number.get_unsigned(data[0])
        
        # Parse timestamp (bytes 1-6: YY MM DD HH MM SS)
        timestamp = PacketHeaderParser._parse_timestamp(
            data[1], data[2], data[3], data[4], data[5], data[6]
        )
        
        # Parse location (bytes 7-10, 4 bytes big-endian)
        location = Byte2Number.get_unsigned_4(data[7], data[8], data[9], data[10])
        # Adjust location if >= 1 billion (matching Java logic)
        if location >= 1000000000:
            location -= 1000000000
        
        # Parse speed (bytes 13-14, 2 bytes big-endian)
        speed = Byte2Number.get_unsigned_2(data[13], data[14])
        
        return PacketHeader(
            packet_no=packet_no,
            timestamp=timestamp,
            location=location,
            speed=speed
        )
    
    @staticmethod
    def _parse_timestamp(yy: int, mm: int, dd: int, 
                        hh: int, MM: int, ss: int) -> datetime:
        """
        Parse timestamp from individual byte components.
        
        Args:
            yy: Year (0-99, represents 2000-2099)
            mm: Month (1-12)
            dd: Day (1-31)
            hh: Hour (0-23)
            MM: Minute (0-59)
            ss: Second (0-59)
            
        Returns:
            datetime object
            
        Raises:
            ValueError: If timestamp components are invalid
        """
        year = 2000 + Byte2Number.get_unsigned(yy)
        month = Byte2Number.get_unsigned(mm)
        day = Byte2Number.get_unsigned(dd)
        hour = Byte2Number.get_unsigned(hh)
        minute = Byte2Number.get_unsigned(MM)
        second = Byte2Number.get_unsigned(ss)
        
        try:
            return datetime(year, month, day, hour, minute, second)
        except ValueError as e:
            raise ValueError(
                f"Invalid timestamp: {year:04d}-{month:02d}-{day:02d} "
                f"{hour:02d}:{minute:02d}:{second:02d}"
            ) from e
    
    @staticmethod
    def parse_header_and_body(data: bytes) -> Tuple[PacketHeader, bytes]:
        """
        Parse both header and body from packet data.
        
        The packet structure is:
        - Bytes 0-14: Header (15 bytes)
        - Byte 15: Body length
        - Bytes 16+: Body data
        
        Args:
            data: Raw packet data
            
        Returns:
            Tuple of (PacketHeader, body_bytes)
            
        Raises:
            ValueError: If data is too short or body length is inconsistent
        """
        if len(data) < 16:
            raise ValueError(f"Data too short for header and length: {len(data)} bytes")
        
        header = PacketHeaderParser.parse(data[:15])
        body_length = Byte2Number.get_unsigned(data[15])
        
        if len(data) < 16 + body_length:
            raise ValueError(
                f"Data too short for body: expected {16 + body_length} bytes, "
                f"got {len(data)} bytes"
            )
        
        body = data[16:16 + body_length]
        return header, body
