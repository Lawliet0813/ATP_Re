"""
Unit tests for MMIDecoder.

Tests verify 100% compatibility with Java PacketMMI implementation.
Phase 1 focuses on MMI_DYNAMIC (Type 1) packet.
"""

import pytest
from datetime import datetime

from src.decoder.mmi_decoder import MMIDecoder, MMIDecodeError
from src.models.atp_records import (
    RecordHeader,
    MMIDynamicPacket,
    MMIStatusPacket,
    MMIDriverMessagePacket,
    MMIFailureReportPacket,
    MMIPacket,
)


class TestMMIDecoder:
    """Test suite for MMIDecoder."""
    
    def setup_method(self):
        """Set up test fixtures."""
        self.decoder = MMIDecoder()
        self.sample_header = RecordHeader(
            timestamp=datetime(2025, 10, 27, 14, 30, 0),
            speed=50.0,
            position=10000,
            packet_length=46,  # 15-byte header + 31-byte MMI_DYNAMIC
            packet_type=0xA0
        )
    
    def test_decode_mmi_dynamic_basic(self):
        """Test decoding a basic MMI_DYNAMIC packet."""
        # Construct MMI_DYNAMIC packet (Type 1, 31 bytes)
        packet_data = bytes([
            0x01,  # Packet type: MMI_DYNAMIC
            0x00, 0x00, 0x00,  # Reserved
            0x01, 0xF4,  # V_TRAIN: 500 (50.0 km/h in 0.1 km/h units)
            0x00, 0x0A,  # A_TRAIN: 10 (acceleration)
            0x00, 0x00, 0x27, 0x10,  # O_TRAIN: 10000 meters
            0x00, 0x00, 0x4E, 0x20,  # O_BRAKETARGET: 20000 meters
            0x00, 0x64,  # V_TARGET: 100 (target speed)
            0x00, 0x05,  # T_INTERVENWAR: 5 seconds
            0x01, 0x90,  # V_PERMITTED: 400 (40.0 km/h)
            0x01, 0xC2,  # V_RELEASE: 450 (45.0 km/h)
            0x01, 0xF4,  # V_INTERVENTION: 500 (50.0 km/h)
            0x10,  # Byte 26: M_WARNING=1, M_SLIP=0, M_SLIDE=0
            0x00, 0x00, 0x61, 0xA8,  # O_BCSP: 25000 meters
        ])
        
        packet = self.decoder.decode(self.sample_header, packet_data)
        
        assert isinstance(packet, MMIDynamicPacket)
        assert packet.packet_type == 1
        assert packet.header == self.sample_header
        assert packet.v_train == 500
        assert packet.a_train == 10
        assert packet.o_train == 10000
        assert packet.o_braketarget == 20000
        assert packet.v_target == 100
        assert packet.v_permitted == 400
        assert packet.m_warning == 1
        assert packet.m_slip == 0
        assert packet.m_slide == 0
    
    def test_decode_mmi_dynamic_negative_acceleration(self):
        """Test MMI_DYNAMIC with negative acceleration (braking)."""
        packet_data = bytes([
            0x01,  # Packet type
            0x00, 0x00, 0x00,  # Reserved
            0x01, 0xF4,  # V_TRAIN: 500
            0xFF, 0xF6,  # A_TRAIN: -10 (braking, two's complement)
            0x00, 0x00, 0x27, 0x10,  # O_TRAIN: 10000
            0x00, 0x00, 0x4E, 0x20,  # O_BRAKETARGET: 20000
            0x00, 0x64,  # V_TARGET: 100
            0x00, 0x05,  # T_INTERVENWAR: 5
            0x01, 0x90,  # V_PERMITTED: 400
            0x01, 0xC2,  # V_RELEASE: 450
            0x01, 0xF4,  # V_INTERVENTION: 500
            0x00,  # Byte 26: all zeros
            0x00, 0x00, 0x61, 0xA8,  # O_BCSP: 25000
        ])
        
        packet = self.decoder.decode(self.sample_header, packet_data)
        
        assert isinstance(packet, MMIDynamicPacket)
        assert packet.a_train == -10  # Negative acceleration (braking)
    
    def test_decode_mmi_dynamic_warning_flags(self):
        """Test MMI_DYNAMIC with various warning flags set."""
        packet_data = bytes([
            0x01,  # Packet type
            0x00, 0x00, 0x00,  # Reserved
            0x01, 0xF4,  # V_TRAIN: 500
            0x00, 0x0A,  # A_TRAIN: 10
            0x00, 0x00, 0x27, 0x10,  # O_TRAIN: 10000
            0x00, 0x00, 0x4E, 0x20,  # O_BRAKETARGET: 20000
            0x00, 0x64,  # V_TARGET: 100
            0x00, 0x05,  # T_INTERVENWAR: 5
            0x01, 0x90,  # V_PERMITTED: 400
            0x01, 0xC2,  # V_RELEASE: 450
            0x01, 0xF4,  # V_INTERVENTION: 500
            0xFC,  # Byte 26: M_WARNING=15 (0xF), M_SLIP=1, M_SLIDE=1
            0x00, 0x00, 0x61, 0xA8,  # O_BCSP: 25000
        ])
        
        packet = self.decoder.decode(self.sample_header, packet_data)
        
        assert packet.m_warning == 15  # All warning bits set
        assert packet.m_slip == 1
        assert packet.m_slide == 1
    
    def test_decode_mmi_dynamic_zero_values(self):
        """Test MMI_DYNAMIC with all zero values (stationary train)."""
        packet_data = bytes([
            0x01,  # Packet type
            0x00, 0x00, 0x00,  # Reserved
            0x00, 0x00,  # V_TRAIN: 0
            0x00, 0x00,  # A_TRAIN: 0
            0x00, 0x00, 0x00, 0x00,  # O_TRAIN: 0
            0x00, 0x00, 0x00, 0x00,  # O_BRAKETARGET: 0
            0x00, 0x00,  # V_TARGET: 0
            0x00, 0x00,  # T_INTERVENWAR: 0
            0x00, 0x00,  # V_PERMITTED: 0
            0x00, 0x00,  # V_RELEASE: 0
            0x00, 0x00,  # V_INTERVENTION: 0
            0x00,  # Byte 26: all zeros
            0x00, 0x00, 0x00, 0x00,  # O_BCSP: 0
        ])
        
        packet = self.decoder.decode(self.sample_header, packet_data)
        
        assert isinstance(packet, MMIDynamicPacket)
        assert packet.v_train == 0
        assert packet.a_train == 0
        assert packet.o_train == 0
    
    def test_decode_mmi_dynamic_too_short(self):
        """Test that decoding fails with insufficient data."""
        short_data = bytes([0x01, 0x00, 0x00])  # Only 3 bytes
        
        with pytest.raises(MMIDecodeError, match="MMI_DYNAMIC packet too short"):
            self.decoder.decode(self.sample_header, short_data)
    
    def test_decode_mmi_status(self):
        """Test decoding MMI_STATUS packet (Type 2)."""
        packet_data = bytes([
            0x02,  # Packet type: MMI_STATUS
            0x00, 0x00, 0x00,  # Reserved
            0x05,  # Status value
        ])
        
        packet = self.decoder.decode(self.sample_header, packet_data)
        
        assert isinstance(packet, MMIStatusPacket)
        assert packet.packet_type == 2
        assert packet.status == 5
    
    def test_decode_mmi_driver_message(self):
        """Test decoding MMI_DRIVER_MESSAGE packet (Type 8)."""
        packet_data = bytes([
            0x08,  # Packet type: MMI_DRIVER_MESSAGE
            0x00, 0x00, 0x00,  # Reserved
            0x0A,  # Message ID: 10
        ])
        
        packet = self.decoder.decode(self.sample_header, packet_data)
        
        assert isinstance(packet, MMIDriverMessagePacket)
        assert packet.packet_type == 8
        assert packet.message_id == 10
    
    def test_decode_mmi_failure_report(self):
        """Test decoding MMI_FAILURE_REPORT_ATP packet (Type 9)."""
        packet_data = bytes([
            0x09,  # Packet type: MMI_FAILURE_REPORT_ATP
            0x00, 0x00, 0x00,  # Reserved
            0x00, 0x64,  # Failure number: 100 (Big-Endian)
        ])
        
        packet = self.decoder.decode(self.sample_header, packet_data)
        
        assert isinstance(packet, MMIFailureReportPacket)
        assert packet.packet_type == 9
        assert packet.failure_number == 100
    
    def test_decode_unknown_packet_type(self):
        """Test decoding unknown packet type returns base MMIPacket."""
        packet_data = bytes([
            0x99,  # Unknown packet type
            0x00, 0x00, 0x00,  # Some data
        ])
        
        packet = self.decoder.decode(self.sample_header, packet_data)
        
        assert isinstance(packet, MMIPacket)
        assert packet.packet_type == 0x99
        assert packet.raw_data == packet_data
    
    def test_decode_empty_packet_data(self):
        """Test that decoding fails with empty packet data."""
        with pytest.raises(MMIDecodeError, match="Packet data too short"):
            self.decoder.decode(self.sample_header, bytes())
    
    def test_get_signed_int16_positive(self):
        """Test signed int16 extraction utility (positive value)."""
        data = bytes([0x01, 0xF4, 0x00, 0x00])  # 500 in Big-Endian
        value = MMIDecoder.get_signed_int16(data, 0)
        assert value == 500
    
    def test_get_signed_int16_negative(self):
        """Test signed int16 extraction utility (negative value)."""
        data = bytes([0xFF, 0x9C, 0x00, 0x00])  # -100 in Big-Endian (two's complement)
        value = MMIDecoder.get_signed_int16(data, 0)
        assert value == -100
    
    def test_get_signed_int32_positive(self):
        """Test signed int32 extraction utility (positive value)."""
        data = bytes([0x00, 0x00, 0x27, 0x10])  # 10000 in Big-Endian
        value = MMIDecoder.get_signed_int32(data, 0)
        assert value == 10000
    
    def test_get_signed_int32_negative(self):
        """Test signed int32 extraction utility (negative value)."""
        data = bytes([0xFF, 0xFF, 0xD8, 0xF0])  # -10000 in Big-Endian (two's complement)
        value = MMIDecoder.get_signed_int32(data, 0)
        assert value == -10000
    
    def test_mmi_packet_type_constants(self):
        """Test that MMI packet type constants match Java definitions."""
        # From decode_re/PacketMMI.java lines 7-81
        assert MMIDecoder.MMI_START_ATP == 0
        assert MMIDecoder.MMI_DYNAMIC == 1
        assert MMIDecoder.MMI_STATUS == 2
        assert MMIDecoder.MMI_SET_TIME_ATP == 3
        assert MMIDecoder.MMI_DRIVER_MESSAGE == 8
        assert MMIDecoder.MMI_FAILURE_REPORT_ATP == 9
        assert MMIDecoder.MMI_CURRENT_DRIVER_DATA == 14
