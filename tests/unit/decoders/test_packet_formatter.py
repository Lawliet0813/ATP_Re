"""
Tests for packet to_dict() methods and PacketFormatter.

This module tests the to_dict() conversion functionality for all decoder
data classes and the PacketFormatter utility.
"""

import pytest
from datetime import datetime

from atp_re.decoders.ru_decoder import RUDecoder, RUPacket
from atp_re.decoders.btm_decoder import BTMFragment, BTMTelegram
from atp_re.decoders.packet_header_parser import PacketHeader
from atp_re.decoders.packet_formatter import PacketFormatter
from atp_re.decoders.mmi_decoder import MMIDynamicData, MMIStatusData


class TestPacketHeaderToDict:
    """Tests for PacketHeader.to_dict()"""
    
    def test_packet_header_to_dict(self):
        """Test PacketHeader to_dict conversion"""
        timestamp = datetime(2024, 1, 1, 12, 0, 0)
        header = PacketHeader(
            packet_no=1,
            timestamp=timestamp,
            location=1000,
            speed=100
        )
        
        result = header.to_dict()
        
        assert result["packet_no"] == 1
        assert result["timestamp"] == "2024-01-01T12:00:00"
        assert result["location"] == 1000
        assert result["speed"] == 100
    
    def test_packet_header_to_dict_none_timestamp(self):
        """Test PacketHeader to_dict with None timestamp"""
        header = PacketHeader(
            packet_no=1,
            timestamp=None,
            location=1000,
            speed=100
        )
        
        result = header.to_dict()
        
        assert result["timestamp"] is None


class TestBTMToDict:
    """Tests for BTM to_dict() methods"""
    
    def test_btm_fragment_to_dict(self):
        """Test BTMFragment to_dict conversion"""
        fragment = BTMFragment(
            sequence_number=42,
            telegram_number=3,
            data=b'\x01\x02\x03\x04\x05'
        )
        
        result = fragment.to_dict()
        
        assert result["sequence_number"] == 42
        assert result["telegram_number"] == 3
        assert result["data_length"] == 5
        assert result["data_hex"] == "0102030405"
    
    def test_btm_telegram_to_dict(self):
        """Test BTMTelegram to_dict conversion"""
        telegram = BTMTelegram(
            sequence_number=100,
            data=b'\x00' * 104,
            nid_bg=12345,
            m_count=10
        )
        
        result = telegram.to_dict()
        
        assert result["sequence_number"] == 100
        assert result["data_length"] == 104
        assert result["nid_bg"] == 12345
        assert result["m_count"] == 10
        assert len(result["data_hex"]) == 208  # 104 bytes * 2 hex chars
    
    def test_btm_telegram_to_dict_no_metadata(self):
        """Test BTMTelegram to_dict without nid_bg and m_count"""
        telegram = BTMTelegram(
            sequence_number=100,
            data=b'\x00' * 104
        )
        
        result = telegram.to_dict()
        
        assert result["nid_bg"] is None
        assert result["m_count"] is None


class TestRUPacketToDict:
    """Tests for RUPacket.to_dict()"""
    
    def test_ru_packet_to_dict_with_mmi_data(self):
        """Test RUPacket to_dict with MMI data"""
        decoder = RUDecoder()
        
        # Create a test MMI_DYNAMIC packet
        test_data = bytes([
            1, 24, 1, 1, 12, 0, 0,
            0, 0, 0, 100,
            0, 0, 50, 0,
            30,
            1, 0, 0,
            0, 50, 0, 0,
            0, 0, 0, 100,
            0, 0, 0, 200,
            0, 45, 0, 10,
            0, 55, 0, 40,
            0, 60, 0x10,
            0, 0, 0, 150
        ])
        
        result = decoder.decode(test_data)
        packet_dict = result.to_dict()
        
        # Check structure
        assert "packet_type" in packet_dict
        assert "description" in packet_dict
        assert "header" in packet_dict
        assert "data" in packet_dict
        
        # Check values
        assert packet_dict["packet_type"] == 1
        assert packet_dict["description"] == "MMI_DYNAMIC"
        
        # Check header
        assert isinstance(packet_dict["header"], dict)
        assert "timestamp" in packet_dict["header"]
        
        # Check data
        assert isinstance(packet_dict["data"], dict)
        assert "v_train" in packet_dict["data"]
        assert packet_dict["data"]["v_train"] == 50
    
    def test_ru_packet_to_dict_with_none_data(self):
        """Test RUPacket to_dict with None data"""
        header = PacketHeader(
            packet_no=31,
            timestamp=datetime(2024, 1, 1),
            location=100,
            speed=50
        )
        
        packet = RUPacket(
            header=header,
            packet_type=31,
            description="Unknown packet",
            data=None
        )
        
        result = packet.to_dict()
        
        assert result["data"] is None
    
    def test_ru_packet_to_dict_with_dict_data(self):
        """Test RUPacket to_dict with dict data"""
        header = PacketHeader(
            packet_no=216,
            timestamp=datetime(2024, 1, 1),
            location=100,
            speed=50
        )
        
        packet = RUPacket(
            header=header,
            packet_type=216,
            description="Button event",
            data={"button": 5}
        )
        
        result = packet.to_dict()
        
        assert result["data"]["button"] == 5


class TestPacketFormatter:
    """Tests for PacketFormatter"""
    
    def test_format_packet_text(self):
        """Test formatting packet as text"""
        packet_dict = {
            "packet_type": 1,
            "description": "MMI_DYNAMIC",
            "header": {
                "packet_no": 1,
                "timestamp": "2024-01-01T12:00:00",
                "location": 100,
                "speed": 50
            },
            "data": {
                "v_train": 50,
                "a_train": 0
            }
        }
        
        formatter = PacketFormatter()
        result = formatter.format_packet(packet_dict)
        
        assert "Packet Type: 1" in result
        assert "Description: MMI_DYNAMIC" in result
        assert "Header:" in result
        assert "Decoded Data:" in result
    
    def test_format_packet_json(self):
        """Test formatting packet as JSON"""
        packet_dict = {
            "packet_type": 1,
            "description": "Test"
        }
        
        formatter = PacketFormatter()
        result = formatter.format_packet_json(packet_dict)
        
        assert '"packet_type": 1' in result
        assert '"description": "Test"' in result
    
    def test_format_packet_list(self):
        """Test formatting list of packets"""
        packets = [
            {"packet_type": 1, "description": "Packet 1"},
            {"packet_type": 2, "description": "Packet 2"}
        ]
        
        formatter = PacketFormatter()
        result = formatter.format_packet_list(packets, format_type="text")
        
        assert "Decoded Packets (2 total)" in result
        assert "Packet #1" in result
        assert "Packet #2" in result
    
    def test_format_packet_list_json(self):
        """Test formatting list of packets as JSON"""
        packets = [
            {"packet_type": 1},
            {"packet_type": 2}
        ]
        
        formatter = PacketFormatter()
        result = formatter.format_packet_list(packets, format_type="json")
        
        assert result.startswith("[")
        assert result.endswith("]")
        assert '"packet_type": 1' in result
        assert '"packet_type": 2' in result
    
    def test_get_field_description(self):
        """Test getting field descriptions"""
        formatter = PacketFormatter()
        
        assert formatter.get_field_description("v_train") == "Train Speed (km/h)"
        assert formatter.get_field_description("m_warning") == "Warning Mode (0-15)"
        
        # Test unknown field
        desc = formatter.get_field_description("unknown_field")
        assert "unknown" in desc.lower() or "Unknown" in desc


class TestMMIDataToDict:
    """Tests for MMI data classes to_dict()"""
    
    def test_mmi_dynamic_to_dict(self):
        """Test MMIDynamicData to_dict"""
        data = MMIDynamicData(
            v_train=100,
            a_train=5,
            o_train=1000,
            o_brake_target=2000,
            v_target=90,
            t_interven_war=15,
            v_permitted=110,
            v_release=80,
            v_intervention=120,
            m_warning=2,
            m_slip=0,
            m_slide=1,
            o_bcsp=1500
        )
        
        result = data.to_dict()
        
        assert result["v_train"] == 100
        assert result["a_train"] == 5
        assert result["o_train"] == 1000
        assert result["m_warning"] == 2
        assert result["m_slip"] == 0
        assert result["m_slide"] == 1
    
    def test_mmi_status_to_dict(self):
        """Test MMIStatusData to_dict"""
        data = MMIStatusData(
            m_adhesion=1,
            m_mode=2,
            m_level=3,
            m_emer_brake=0,
            m_service_brake=1,
            m_override_eoa=0,
            m_trip=0,
            m_active_cabin=1
        )
        
        result = data.to_dict()
        
        assert result["m_adhesion"] == 1
        assert result["m_mode"] == 2
        assert result["m_level"] == 3
        assert result["m_active_cabin"] == 1
