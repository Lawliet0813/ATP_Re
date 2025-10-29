"""
Unit tests for RUDecoder.
"""

import pytest
from datetime import datetime
from atp_re.decoders.ru_decoder import (
    RUDecoder, RUPacket,
    PACKET_ATP, PACKET_MMI, PACKET_STATUS_ATP, PACKET_STATUS_MMI,
    PACKET_BTM_TGM_1, PACKET_BTM_TGM_5, PACKET_BUTTON_EVENT
)
from atp_re.decoders.mmi_decoder import MMIDynamicData, MMI_DYNAMIC


class TestRUDecoder:
    """Test cases for RUDecoder."""
    
    def test_decode_basic_packet(self):
        """Test decoding a basic RU packet with status."""
        decoder = RUDecoder()
        
        # Create packet: header + length + body
        header = bytes([
            0x02,  # packet_no = 2 (STATUS_ATP)
            0x17, 0x0A, 0x0F, 0x0E, 0x1E, 0x2D,  # timestamp
            0x00, 0x00, 0x03, 0xE8,  # location = 1000
            0x00, 0x00,  # reserved
            0x00, 0x78,  # speed = 120
        ])
        body_length = bytes([0x01])
        body = bytes([0x05])  # status byte
        
        packet_data = header + body_length + body
        
        result = decoder.decode(packet_data)
        
        assert isinstance(result, RUPacket)
        assert result.header.packet_no == 2
        assert result.header.timestamp == datetime(2023, 10, 15, 14, 30, 45)
        assert result.header.location == 1000
        assert result.header.speed == 120
        assert result.packet_type == 2
        assert result.description == "STATUS ATP"
        assert result.data == {"status": 5}
    
    def test_decode_mmi_dynamic_packet(self):
        """Test decoding MMI_DYNAMIC packet."""
        decoder = RUDecoder()
        
        # Create MMI packet (type 1 = ATP, subtype 1 = MMI_DYNAMIC)
        header = bytes([
            0x01,  # packet_no = 1 (ATP/MMI)
            0x17, 0x0A, 0x0F, 0x0E, 0x1E, 0x2D,  # timestamp
            0x00, 0x00, 0x03, 0xE8,  # location = 1000
            0x00, 0x00,  # reserved
            0x00, 0x78,  # speed = 120
        ])
        
        # MMI_DYNAMIC body
        mmi_body = bytes([
            MMI_DYNAMIC,  # subpacket type
            0x00, 0x00,  # placeholder
            0x00, 0x78,  # v_train = 120
            0x00, 0x0A,  # a_train = 10
            0x00, 0x00, 0x03, 0xE8,  # o_train = 1000
            0x00, 0x00, 0x07, 0xD0,  # o_brake_target = 2000
            0x00, 0x64,  # v_target = 100
            0x00, 0x1E,  # t_interven_war = 30
            0x00, 0x82,  # v_permitted = 130
            0x00, 0x6E,  # v_release = 110
            0x00, 0x8C,  # v_intervention = 140
            0x00,  # flags
            0x00, 0x00, 0x0B, 0xB8,  # o_bcsp = 3000
        ])
        
        body_length = bytes([len(mmi_body)])
        packet_data = header + body_length + mmi_body
        
        result = decoder.decode(packet_data)
        
        assert result.packet_type == 1
        assert result.description == "MMI_DYNAMIC"
        assert isinstance(result.data, MMIDynamicData)
        assert result.data.v_train == 120
        assert result.data.a_train == 10
    
    def test_decode_btm_fragment_incomplete(self):
        """Test decoding BTM fragment (incomplete reassembly)."""
        decoder = RUDecoder()
        
        # Create BTM TGM_1 packet
        header = bytes([
            PACKET_BTM_TGM_1,  # packet_no = 43
            0x17, 0x0A, 0x0F, 0x0E, 0x1E, 0x2D,  # timestamp
            0x00, 0x00, 0x03, 0xE8,  # location
            0x00, 0x00,  # reserved
            0x00, 0x78,  # speed
        ])
        
        # BTM fragment (sequence + data)
        btm_body = bytes([42] + [0x11] * 25)  # sequence 42, fragment 1
        body_length = bytes([len(btm_body)])
        packet_data = header + body_length + btm_body
        
        result = decoder.decode(packet_data)
        
        assert result.packet_type == PACKET_BTM_TGM_1
        assert result.description == "MVB_LOG_BTM_TGM_1"
        assert result.data is None  # Incomplete reassembly
    
    def test_decode_btm_complete_reassembly(self):
        """Test decoding complete BTM telegram reassembly."""
        decoder = RUDecoder()
        sequence_no = 42
        
        # Send all 5 fragments
        for telegram_no in range(1, 6):
            header = bytes([
                PACKET_BTM_TGM_1 + telegram_no - 1,  # 43-47
                0x17, 0x0A, 0x0F, 0x0E, 0x1E, 0x2D,
                0x00, 0x00, 0x03, 0xE8,
                0x00, 0x00,
                0x00, 0x78,
            ])
            
            btm_body = bytes([sequence_no] + [telegram_no] * 25)
            body_length = bytes([len(btm_body)])
            packet_data = header + body_length + btm_body
            
            result = decoder.decode(packet_data)
            
            if telegram_no < 5:
                # Incomplete
                assert result.data is None
            else:
                # Complete on 5th fragment
                assert result.data is not None
                assert result.data["sequence_number"] == sequence_no
                assert result.data["telegram_size"] == 104
    
    def test_decode_button_event(self):
        """Test decoding button event packet."""
        decoder = RUDecoder()
        
        header = bytes([
            PACKET_BUTTON_EVENT,  # packet_no = 216
            0x17, 0x0A, 0x0F, 0x0E, 0x1E, 0x2D,
            0x00, 0x00, 0x03, 0xE8,
            0x00, 0x00,
            0x00, 0x78,
        ])
        
        button_body = bytes([0x05])  # button value
        body_length = bytes([len(button_body)])
        packet_data = header + body_length + button_body
        
        result = decoder.decode(packet_data)
        
        assert result.packet_type == PACKET_BUTTON_EVENT
        assert result.description == "MVB LOG TYPE BUTTON EVENT"
        assert result.data == {"button": 5}
    
    def test_decode_unknown_packet_type(self):
        """Test decoding packet with unknown type."""
        decoder = RUDecoder()
        
        header = bytes([
            0xFF,  # unknown packet type
            0x17, 0x0A, 0x0F, 0x0E, 0x1E, 0x2D,
            0x00, 0x00, 0x03, 0xE8,
            0x00, 0x00,
            0x00, 0x78,
        ])
        
        body_length = bytes([0x00])
        packet_data = header + body_length
        
        result = decoder.decode(packet_data)
        
        assert result.packet_type == 0xFF
        assert "no handle Record Type" in result.description
        assert result.data is None
    
    def test_decode_vdx_packets(self):
        """Test decoding VDX packets (no detailed decoding)."""
        decoder = RUDecoder()
        
        vdx_types = [21, 22, 23, 24]  # VDX packet types
        
        for packet_type in vdx_types:
            header = bytes([
                packet_type,
                0x17, 0x0A, 0x0F, 0x0E, 0x1E, 0x2D,
                0x00, 0x00, 0x03, 0xE8,
                0x00, 0x00,
                0x00, 0x78,
            ])
            
            body_length = bytes([0x00])
            packet_data = header + body_length
            
            result = decoder.decode(packet_data)
            
            assert result.packet_type == packet_type
            assert "VDX" in result.description
            assert result.data is None
    
    def test_decoder_reset(self):
        """Test resetting decoder state."""
        decoder = RUDecoder()
        
        # Add some BTM fragments
        for telegram_no in range(1, 4):
            header = bytes([
                PACKET_BTM_TGM_1 + telegram_no - 1,
                0x17, 0x0A, 0x0F, 0x0E, 0x1E, 0x2D,
                0x00, 0x00, 0x03, 0xE8,
                0x00, 0x00,
                0x00, 0x78,
            ])
            
            btm_body = bytes([42] + [telegram_no] * 25)
            body_length = bytes([len(btm_body)])
            packet_data = header + body_length + btm_body
            
            decoder.decode(packet_data)
        
        # Reset
        decoder.reset()
        
        # BTM decoder should be clear
        btm = decoder.get_btm_decoder()
        assert btm.get_last_telegram() is None
        assert btm.get_pending_sequences() == []
    
    def test_get_btm_decoder(self):
        """Test accessing BTM decoder instance."""
        decoder = RUDecoder()
        
        btm = decoder.get_btm_decoder()
        
        assert btm is not None
        assert hasattr(btm, 'add_fragment')
        assert hasattr(btm, 'get_last_telegram')
    
    def test_decode_invalid_packet(self):
        """Test decoding with invalid packet data."""
        decoder = RUDecoder()
        
        # Too short packet
        short_packet = bytes([0x01, 0x02, 0x03])
        
        with pytest.raises(ValueError):
            decoder.decode(short_packet)
    
    def test_decode_mmi_packet_type_4(self):
        """Test that packet type 4 (MMI) also decodes MMI data."""
        decoder = RUDecoder()
        
        # Create MMI packet with type 4
        header = bytes([
            0x04,  # packet_no = 4 (MMI)
            0x17, 0x0A, 0x0F, 0x0E, 0x1E, 0x2D,
            0x00, 0x00, 0x03, 0xE8,
            0x00, 0x00,
            0x00, 0x78,
        ])
        
        # MMI_DYNAMIC body (needs 30 bytes minimum)
        mmi_body = bytes([
            MMI_DYNAMIC,
            0x00, 0x00,  # bytes 1-2
            0x00, 0x50,  # v_train = 80
            0x00, 0x00,  # a_train
            0x00, 0x00, 0x00, 0x00,  # o_train
            0x00, 0x00, 0x00, 0x00,  # o_brake_target
            0x00, 0x00,  # v_target
            0x00, 0x00,  # t_interven_war
            0x00, 0x00,  # v_permitted
            0x00, 0x00,  # v_release
            0x00, 0x00,  # v_intervention
            0x00,  # flags
            0x00, 0x00, 0x00, 0x00,  # o_bcsp
        ])
        
        body_length = bytes([len(mmi_body)])
        packet_data = header + body_length + mmi_body
        
        result = decoder.decode(packet_data)
        
        assert result.packet_type == 4
        assert result.description == "MMI_DYNAMIC"
