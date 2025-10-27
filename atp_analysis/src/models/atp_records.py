"""
Data models for ATP record parsing.

This module contains dataclasses representing the structure of ATP/MMI binary records.
All models follow the exact structure defined in the Java system for 100% compatibility.
"""

from dataclasses import dataclass
from datetime import datetime
from typing import Optional


@dataclass
class RecordHeader:
    """
    15-byte record header from RU/MMI files.
    
    This corresponds to the header structure decoded in Java's HeadDecoder.java.
    
    Byte Layout (Big-Endian):
        0: Year (YY, add 2000)
        1: Month (MM, 1-12)
        2: Day (DD, 1-31)
        3: Hour (HH, 0-23)
        4: Minute (mm, 0-59)
        5: Second (ss, 0-59)
        6-7: Speed (uint16, unit: 0.1 km/h) - converted to float km/h
        8-11: Position (uint32, unit: meters)
        12-13: Packet Length (uint16, total length including header)
        14: Packet Type (uint8, 0x50=ATP, 0xA0=MMI)
    
    Attributes:
        timestamp: Record timestamp
        speed: Train speed in km/h (converted from 0.1 km/h units)
        position: Train position in meters
        packet_length: Total packet length including header
        packet_type: Packet type identifier (0x50 or 0xA0)
    
    Java Reference:
        - decoder_re/HeadDecoder.java: setByte() method
        - decoder_re/MMIVariables.java: MMI_V_TRAIN(), MMI_O_TRAIN()
    """
    
    timestamp: datetime
    speed: float  # km/h
    position: int  # meters
    packet_length: int  # bytes
    packet_type: int  # 0x50=ATP, 0xA0=MMI
    
    @property
    def is_atp_packet(self) -> bool:
        """Check if this is an ATP packet (0x50)."""
        return self.packet_type == 0x50
    
    @property
    def is_mmi_packet(self) -> bool:
        """Check if this is an MMI packet (0xA0)."""
        return self.packet_type == 0xA0


@dataclass
class MMIPacket:
    """
    Base class for all MMI packet types.
    
    Corresponds to PacketMMI types in decode_re/PacketMMI.java.
    
    Attributes:
        packet_type: MMI packet type number (1-40+)
        header: Associated record header
        raw_data: Raw packet bytes (excluding header)
    """
    
    packet_type: int
    header: RecordHeader
    raw_data: bytes


class MMIDynamicPacket(MMIPacket):
    """
    MMI_DYNAMIC packet (Type 1) - Dynamic train data.
    
    This is the highest priority packet containing real-time train state.
    
    Java Reference:
        - decode_re/PacketMMI.java: MMI_DYMANIC() method [Note: typo in original]
        - decode_re/DecodeATP.java: case 1 handling
    
    Attributes:
        v_train: Train speed (km/h)
        o_train: Train position (meters)
        a_train: Train acceleration (m/s²)
        mode: ATP mode
        level: ATP level
    """
    
    def __init__(
        self,
        header: RecordHeader,
        raw_data: bytes,
        v_train: Optional[int] = None,
        o_train: Optional[int] = None,
        a_train: Optional[int] = None,
        mode: Optional[int] = None,
        level: Optional[int] = None,
    ):
        super().__init__(packet_type=1, header=header, raw_data=raw_data)
        self.v_train = v_train
        self.o_train = o_train
        self.a_train = a_train
        self.mode = mode
        self.level = level


class MMIStatusPacket(MMIPacket):
    """
    MMI_STATUS packet (Type 2) - System status information.
    
    Java Reference:
        - decode_re/PacketMMI.java: MMI_STATUS() method
        - decode_re/DecodeATP.java: case 2 handling
    """
    
    def __init__(self, header: RecordHeader, raw_data: bytes, status: Optional[int] = None):
        super().__init__(packet_type=2, header=header, raw_data=raw_data)
        self.status = status


class MMIDriverMessagePacket(MMIPacket):
    """
    MMI_DRIVER_MESSAGE packet (Type 8) - Messages displayed to driver.
    
    Java Reference:
        - decode_re/PacketMMI.java: MMI_DRIVER_MESSAGE() method
        - decode_re/DecodeATP.java: case 8 handling
    """
    
    def __init__(
        self,
        header: RecordHeader,
        raw_data: bytes,
        message_id: Optional[int] = None,
        message_text: Optional[str] = None,
    ):
        super().__init__(packet_type=8, header=header, raw_data=raw_data)
        self.message_id = message_id
        self.message_text = message_text


class MMIFailureReportPacket(MMIPacket):
    """
    MMI_FAILURE_REPORT_ATP packet (Type 9) - ATP failure reports.
    
    Java Reference:
        - decode_re/PacketMMI.java: MMI_FAILURE_REPORT_ATP() method
        - decode_re/DecodeATP.java: case 9 handling
    """
    
    def __init__(
        self,
        header: RecordHeader,
        raw_data: bytes,
        failure_number: Optional[int] = None,
        failure_description: Optional[str] = None,
    ):
        super().__init__(packet_type=9, header=header, raw_data=raw_data)
        self.failure_number = failure_number
        self.failure_description = failure_description
