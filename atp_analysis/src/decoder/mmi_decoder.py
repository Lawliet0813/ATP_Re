"""
MMI Decoder - Decodes MMI (Man-Machine Interface) packets.

This module implements decoding logic for 40+ MMI packet types found in ATP/MMI files.
Phase 1 focuses on the highest priority packets, particularly MMI_DYNAMIC (Type 1).

Java References:
    - decode_re/PacketMMI.java: All MMI packet parsing methods
    - decode_re/DecodeATP.java: Packet type routing
    - decoder_re/MMIVariables.java: Variable parsing utilities
"""

import struct
import logging
from typing import Optional, Dict, Any

import structlog

from ..models.atp_records import (
    RecordHeader,
    MMIPacket,
    MMIDynamicPacket,
    MMIStatusPacket,
    MMIDriverMessagePacket,
    MMIFailureReportPacket,
)

logger = structlog.get_logger(__name__)


class MMIDecodeError(Exception):
    """Raised when MMI packet decoding fails."""
    pass


class MMIDecoder:
    """
    Decoder for MMI (Man-Machine Interface) packets.
    
    The MMI protocol defines 40+ packet types for communication between
    the ATP system and the driver's display unit. This decoder implements
    parsing for the most critical packet types used in train operation analysis.
    
    Priority Packets (Phase 1):
        - Type 1 (MMI_DYNAMIC): Real-time train dynamics (speed, position, acceleration)
        - Type 2 (MMI_STATUS): System status information
        - Type 8 (MMI_DRIVER_MESSAGE): Messages displayed to driver
        - Type 9 (MMI_FAILURE_REPORT_ATP): ATP failure reports
    
    Java Reference:
        decode_re/PacketMMI.java - Contains all MMI packet parsing logic
    
    Example:
        >>> decoder = MMIDecoder()
        >>> header = RecordHeader(...)  # 15-byte header
        >>> packet_data = b'\\x01...'  # MMI packet bytes (excluding header)
        >>> packet = decoder.decode(header, packet_data)
        >>> if isinstance(packet, MMIDynamicPacket):
        ...     print(f"Speed: {packet.v_train} km/h")
    """
    
    # MMI Packet Type Constants (from PacketMMI.java lines 7-81)
    MMI_START_ATP = 0
    MMI_DYNAMIC = 1
    MMI_STATUS = 2
    MMI_SET_TIME_ATP = 3
    MMI_TRACK_DESCRIPTION = 4
    MMI_GEO_POSITION = 5
    MMI_CURRENT_TRAIN_DATA = 6
    MMI_FORCED_DRIVER_REQUEST = 7
    MMI_DRIVER_MESSAGE = 8
    MMI_FAILURE_REPORT_ATP = 9
    MMI_ECHOED_TRAIN_DATA = 10
    MMI_CURRENT_SR_RULES = 11
    MMI_ECHOED_SR_RULES = 12
    MMI_CURRENT_DRIVER_DATA = 14
    MMI_TEST_REQUEST = 15
    MMI_SELECT_STM_REQUEST = 16
    MMI_RU_DATA = 19
    
    def decode(self, header: RecordHeader, packet_data: bytes) -> MMIPacket:
        """
        Decode an MMI packet based on its type.
        
        Args:
            header: Parsed 15-byte record header
            packet_data: Packet payload bytes (excluding the 15-byte header)
            
        Returns:
            Decoded MMI packet object (specific subtype based on packet type)
            
        Raises:
            MMIDecodeError: If packet type is unknown or decoding fails
            
        Java Reference:
            decode_re/DecodeATP.java: setData() method, switch statement lines 67-97
        """
        if len(packet_data) < 1:
            raise MMIDecodeError("Packet data too short (need at least 1 byte for type)")
        
        packet_type = packet_data[0]
        
        try:
            # Route to appropriate decoder based on packet type
            # Java: switch (Byte2Number.getUnsigned(paramArrayOfbyte[1]))
            if packet_type == self.MMI_DYNAMIC:
                return self._decode_dynamic(header, packet_data)
            elif packet_type == self.MMI_STATUS:
                return self._decode_status(header, packet_data)
            elif packet_type == self.MMI_DRIVER_MESSAGE:
                return self._decode_driver_message(header, packet_data)
            elif packet_type == self.MMI_FAILURE_REPORT_ATP:
                return self._decode_failure_report(header, packet_data)
            else:
                # For unimplemented packet types, return base MMIPacket
                logger.debug("unimplemented_packet_type", packet_type=packet_type)
                return MMIPacket(
                    packet_type=packet_type,
                    header=header,
                    raw_data=packet_data
                )
                
        except Exception as e:
            logger.error(
                "mmi_decode_failed",
                packet_type=packet_type,
                error=str(e),
                data_hex=packet_data[:32].hex()  # Log first 32 bytes
            )
            raise MMIDecodeError(f"Failed to decode MMI packet type {packet_type}: {e}") from e
    
    def _decode_dynamic(self, header: RecordHeader, data: bytes) -> MMIDynamicPacket:
        """
        Decode MMI_DYNAMIC packet (Type 1) - Real-time train dynamics.
        
        This is the highest priority packet containing speed, position, acceleration,
        and various ATP system states.
        
        Packet Structure (31 bytes total):
            Offset 0: Packet type (1)
            Offset 1-3: Reserved
            Offset 4-5: V_TRAIN (train speed, signed int16)
            Offset 6-7: A_TRAIN (train acceleration, signed int16)
            Offset 8-11: O_TRAIN (train position, signed int32)
            Offset 12-15: O_BRAKETARGET (brake target position, signed int32)
            Offset 16-17: V_TARGET (target speed, signed int16)
            Offset 18-19: T_INTERVENWAR (intervention warning time, signed int16)
            Offset 20-21: V_PERMITTED (permitted speed, signed int16)
            Offset 22-23: V_RELEASE (release speed, signed int16)
            Offset 24-25: V_INTERVENTION (intervention speed, signed int16)
            Offset 26: M_WARNING (bits 4-7), M_SLIP (bit 3), M_SLIDE (bit 2)
            Offset 27-30: O_BCSP (brake curve speed position, signed int32)
        
        Java Reference:
            decode_re/PacketMMI.java: MMI_DYMANIC() method lines 183-212
            Note: Original has typo "DYMANIC" instead of "DYNAMIC"
        
        Args:
            header: Record header
            data: Full packet bytes including type byte
            
        Returns:
            MMIDynamicPacket with parsed fields
        """
        if len(data) < 31:
            raise MMIDecodeError(f"MMI_DYNAMIC packet too short: {len(data)} bytes (need 31)")
        
        # Parse fields using Big-Endian signed integers
        # Java: MMIVariables methods call Byte2Number.getSigned()
        
        v_train = struct.unpack('>h', data[4:6])[0]  # Signed int16
        a_train = struct.unpack('>h', data[6:8])[0]  # Signed int16
        o_train = struct.unpack('>i', data[8:12])[0]  # Signed int32
        o_braketarget = struct.unpack('>i', data[12:16])[0]  # Signed int32
        v_target = struct.unpack('>h', data[16:18])[0]  # Signed int16
        t_intervenwar = struct.unpack('>h', data[18:20])[0]  # Signed int16
        v_permitted = struct.unpack('>h', data[20:22])[0]  # Signed int16
        v_release = struct.unpack('>h', data[22:24])[0]  # Signed int16
        v_intervention = struct.unpack('>h', data[24:26])[0]  # Signed int16
        
        # Parse bit fields from byte 26
        # Java lines 203-208
        byte_26 = data[26]
        m_warning = (byte_26 & 0xF0) >> 4  # Bits 4-7
        m_slip = (byte_26 & 0x08) >> 3  # Bit 3
        m_slide = (byte_26 & 0x04) >> 2  # Bit 2
        
        o_bcsp = struct.unpack('>i', data[27:31])[0]  # Signed int32
        
        packet = MMIDynamicPacket(
            header=header,
            raw_data=data,
            v_train=v_train,
            o_train=o_train,
            a_train=a_train,
        )
        
        # Store additional fields as attributes (not in simplified Phase 1 model)
        packet.o_braketarget = o_braketarget
        packet.v_target = v_target
        packet.t_intervenwar = t_intervenwar
        packet.v_permitted = v_permitted
        packet.v_release = v_release
        packet.v_intervention = v_intervention
        packet.m_warning = m_warning
        packet.m_slip = m_slip
        packet.m_slide = m_slide
        packet.o_bcsp = o_bcsp
        
        logger.debug(
            "mmi_dynamic_decoded",
            v_train=v_train,
            o_train=o_train,
            a_train=a_train,
            v_target=v_target
        )
        
        return packet
    
    def _decode_status(self, header: RecordHeader, data: bytes) -> MMIStatusPacket:
        """
        Decode MMI_STATUS packet (Type 2) - System status.
        
        Java Reference:
            decode_re/PacketMMI.java: MMI_STATUS() method
            decode_re/DecodeATP.java: case 2 handling line 82-83
        
        Args:
            header: Record header
            data: Full packet bytes
            
        Returns:
            MMIStatusPacket with status field
        """
        # Simplified implementation - full parsing to be added in Phase 2
        status = data[4] if len(data) > 4 else None
        
        return MMIStatusPacket(
            header=header,
            raw_data=data,
            status=status
        )
    
    def _decode_driver_message(self, header: RecordHeader, data: bytes) -> MMIDriverMessagePacket:
        """
        Decode MMI_DRIVER_MESSAGE packet (Type 8) - Driver messages.
        
        Java Reference:
            decode_re/PacketMMI.java: MMI_DRIVER_MESSAGE() method
            decode_re/DecodeATP.java: case 8 handling line 88-89
        
        Args:
            header: Record header
            data: Full packet bytes
            
        Returns:
            MMIDriverMessagePacket with message information
        """
        # Simplified implementation - full parsing to be added in Phase 2
        message_id = data[4] if len(data) > 4 else None
        
        return MMIDriverMessagePacket(
            header=header,
            raw_data=data,
            message_id=message_id
        )
    
    def _decode_failure_report(self, header: RecordHeader, data: bytes) -> MMIFailureReportPacket:
        """
        Decode MMI_FAILURE_REPORT_ATP packet (Type 9) - ATP failure reports.
        
        Java Reference:
            decode_re/PacketMMI.java: MMI_FAILURE_REPORT_ATP() method
            decode_re/DecodeATP.java: case 9 handling line 91-92
        
        Args:
            header: Record header
            data: Full packet bytes
            
        Returns:
            MMIFailureReportPacket with failure information
        """
        # Simplified implementation - full parsing to be added in Phase 2
        if len(data) >= 6:
            failure_number = struct.unpack('>H', data[4:6])[0]
        else:
            failure_number = None
        
        return MMIFailureReportPacket(
            header=header,
            raw_data=data,
            failure_number=failure_number
        )
    
    @staticmethod
    def get_signed_int16(data: bytes, offset: int) -> int:
        """
        Extract signed 16-bit integer (Big-Endian).
        
        Java Reference:
            Tools.Byte2Number.getSigned(byte, byte)
            decoder_re/MMIVariables.java: Various methods returning signed int16
        """
        return struct.unpack('>h', data[offset:offset+2])[0]
    
    @staticmethod
    def get_signed_int32(data: bytes, offset: int) -> int:
        """
        Extract signed 32-bit integer (Big-Endian).
        
        Java Reference:
            Tools.Byte2Number.getSigned(byte, byte, byte, byte)
            decoder_re/MMIVariables.java: MMI_O_TRAIN() and similar methods
        """
        return struct.unpack('>i', data[offset:offset+4])[0]
