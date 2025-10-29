"""
Unit tests for MMIDecoder.
"""

import pytest
from atp_re.decoders.mmi_decoder import (
    MMIDecoder, MMIDynamicData, MMIStatusData,
    MMI_DYNAMIC, MMI_STATUS
)


class TestMMIDecoder:
    """Test cases for MMIDecoder."""
    
    def test_decode_mmi_dynamic_basic(self):
        """Test decoding basic MMI_DYNAMIC packet."""
        # Create test MMI_DYNAMIC packet data
        data = bytes([
            0x00, 0x00, 0x00,  # Placeholder for bytes 0-2
            0x00, 0x78,  # v_train = 120 km/h
            0x00, 0x0A,  # a_train = 10 cm/s² (positive acceleration)
            0x00, 0x00, 0x03, 0xE8,  # o_train = 1000 meters
            0x00, 0x00, 0x07, 0xD0,  # o_brake_target = 2000 meters
            0x00, 0x64,  # v_target = 100 km/h
            0x00, 0x1E,  # t_interven_war = 30 seconds
            0x00, 0x82,  # v_permitted = 130 km/h
            0x00, 0x6E,  # v_release = 110 km/h
            0x00, 0x8C,  # v_intervention = 140 km/h
            0x50,  # m_warning = 5, m_slip = 0, m_slide = 0
            0x00, 0x00, 0x0B, 0xB8,  # o_bcsp = 3000 meters
        ])
        
        result = MMIDecoder.decode_mmi_dynamic(data)
        
        assert isinstance(result, MMIDynamicData)
        assert result.v_train == 120
        assert result.a_train == 10
        assert result.o_train == 1000
        assert result.o_brake_target == 2000
        assert result.v_target == 100
        assert result.t_interven_war == 30
        assert result.v_permitted == 130
        assert result.v_release == 110
        assert result.v_intervention == 140
        assert result.m_warning == 5
        assert result.m_slip == 0
        assert result.m_slide == 0
        assert result.o_bcsp == 3000
    
    def test_decode_mmi_dynamic_negative_acceleration(self):
        """Test MMI_DYNAMIC with negative acceleration (braking)."""
        data = bytes([
            0x00, 0x00, 0x00,  # Placeholder
            0x00, 0x78,  # v_train = 120
            0xFF, 0xF6,  # a_train = -10 cm/s² (braking, signed)
            0x00, 0x00, 0x03, 0xE8,  # o_train = 1000
            0x00, 0x00, 0x07, 0xD0,  # o_brake_target = 2000
            0x00, 0x64,  # v_target = 100
            0x00, 0x1E,  # t_interven_war = 30
            0x00, 0x82,  # v_permitted = 130
            0x00, 0x6E,  # v_release = 110
            0x00, 0x8C,  # v_intervention = 140
            0x00,  # All flags = 0
            0x00, 0x00, 0x0B, 0xB8,  # o_bcsp = 3000
        ])
        
        result = MMIDecoder.decode_mmi_dynamic(data)
        
        assert result.a_train == -10  # Negative acceleration
    
    def test_decode_mmi_dynamic_large_position(self):
        """Test MMI_DYNAMIC with position >= 1 billion."""
        data = bytes([
            0x00, 0x00, 0x00,  # Placeholder
            0x00, 0x50,  # v_train = 80
            0x00, 0x00,  # a_train = 0
            0x3B, 0x9A, 0xCB, 0xF4,  # o_train = 1000000500 -> should adjust to 500
            0x3B, 0x9A, 0xCC, 0x58,  # o_brake_target = 1000000600 -> should adjust to 600
            0x00, 0x50,  # v_target = 80
            0x00, 0x00,  # t_interven_war = 0
            0x00, 0x50,  # v_permitted = 80
            0x00, 0x50,  # v_release = 80
            0x00, 0x50,  # v_intervention = 80
            0x00,  # flags = 0
            0x00, 0x00, 0x00, 0x00,  # o_bcsp = 0
        ])
        
        result = MMIDecoder.decode_mmi_dynamic(data)
        
        assert result.o_train == 500  # Adjusted from 1000000500
        assert result.o_brake_target == 600  # Adjusted from 1000000600
    
    def test_decode_mmi_dynamic_bit_flags(self):
        """Test MMI_DYNAMIC bit field decoding."""
        data = bytes([
            0x00, 0x00, 0x00,  # Placeholder
            0x00, 0x50,  # v_train = 80
            0x00, 0x00,  # a_train = 0
            0x00, 0x00, 0x00, 0x00,  # o_train = 0
            0x00, 0x00, 0x00, 0x00,  # o_brake_target = 0
            0x00, 0x50,  # v_target = 80
            0x00, 0x00,  # t_interven_war = 0
            0x00, 0x50,  # v_permitted = 80
            0x00, 0x50,  # v_release = 80
            0x00, 0x50,  # v_intervention = 80
            0xAC,  # m_warning = 10 (0xA), m_slip = 1 (bit 3), m_slide = 1 (bit 2)
            0x00, 0x00, 0x00, 0x00,  # o_bcsp = 0
        ])
        
        result = MMIDecoder.decode_mmi_dynamic(data)
        
        assert result.m_warning == 10  # 0xA from high nibble
        assert result.m_slip == 1  # Bit 3 set
        assert result.m_slide == 1  # Bit 2 set
    
    def test_decode_mmi_dynamic_too_short(self):
        """Test that short data raises error."""
        data = bytes([0x00, 0x00, 0x00, 0x00, 0x50])  # Only 5 bytes
        
        with pytest.raises(ValueError, match="MMI_DYNAMIC data too short"):
            MMIDecoder.decode_mmi_dynamic(data)
    
    def test_decode_mmi_dynamic_to_dict(self):
        """Test converting MMIDynamicData to dictionary."""
        data = bytes([
            0x00, 0x00, 0x00,  # Placeholder
            0x00, 0x64,  # v_train = 100
            0x00, 0x05,  # a_train = 5
            0x00, 0x00, 0x00, 0x64,  # o_train = 100
            0x00, 0x00, 0x00, 0xC8,  # o_brake_target = 200
            0x00, 0x50,  # v_target = 80
            0x00, 0x0A,  # t_interven_war = 10
            0x00, 0x6E,  # v_permitted = 110
            0x00, 0x64,  # v_release = 100
            0x00, 0x78,  # v_intervention = 120
            0x00,  # flags = 0
            0x00, 0x00, 0x01, 0x2C,  # o_bcsp = 300
        ])
        
        result = MMIDecoder.decode_mmi_dynamic(data)
        result_dict = result.to_dict()
        
        assert isinstance(result_dict, dict)
        assert result_dict["v_train"] == 100
        assert result_dict["a_train"] == 5
        assert result_dict["o_train"] == 100


class TestMMIDecoderStatus:
    """Test cases for MMI_STATUS decoding."""
    
    def test_decode_mmi_status_basic(self):
        """Test decoding basic MMI_STATUS packet."""
        data = bytes([
            0x00, 0x00, 0x00,  # Placeholder for bytes 0-2
            0x03,  # m_adhesion = 3
            0x52,  # m_mode = 5 (high nibble), m_level = 2 (low nibble)
            0xAB,  # m_emer_brake = 2, m_service_brake = 2, 
                   # m_override_eoa = 1, m_trip = 0, m_active_cabin = 3
        ])
        
        result = MMIDecoder.decode_mmi_status(data)
        
        assert isinstance(result, MMIStatusData)
        assert result.m_adhesion == 3
        assert result.m_mode == 5
        assert result.m_level == 2
        assert result.m_emer_brake == 2  # bits 6-7 of 0xAB = 10
        assert result.m_service_brake == 2  # bits 4-5 of 0xAB = 10
        assert result.m_override_eoa == 1  # bit 3
        assert result.m_trip == 0  # bit 2
        assert result.m_active_cabin == 3  # bits 0-1
    
    def test_decode_mmi_status_all_zeros(self):
        """Test MMI_STATUS with all zero values."""
        data = bytes([
            0x00, 0x00, 0x00,  # Placeholder
            0x00,  # m_adhesion = 0
            0x00,  # m_mode = 0, m_level = 0
            0x00,  # all flags = 0
        ])
        
        result = MMIDecoder.decode_mmi_status(data)
        
        assert result.m_adhesion == 0
        assert result.m_mode == 0
        assert result.m_level == 0
        assert result.m_emer_brake == 0
        assert result.m_service_brake == 0
        assert result.m_override_eoa == 0
        assert result.m_trip == 0
        assert result.m_active_cabin == 0
    
    def test_decode_mmi_status_too_short(self):
        """Test that short data raises error."""
        data = bytes([0x00, 0x00])  # Only 2 bytes
        
        with pytest.raises(ValueError, match="MMI_STATUS data too short"):
            MMIDecoder.decode_mmi_status(data)
    
    def test_decode_mmi_status_to_dict(self):
        """Test converting MMIStatusData to dictionary."""
        data = bytes([
            0x00, 0x00, 0x00,  # Placeholder
            0x02,  # m_adhesion = 2
            0x31,  # m_mode = 3, m_level = 1
            0x44,  # Various flags
        ])
        
        result = MMIDecoder.decode_mmi_status(data)
        result_dict = result.to_dict()
        
        assert isinstance(result_dict, dict)
        assert result_dict["m_adhesion"] == 2
        assert result_dict["m_mode"] == 3
        assert result_dict["m_level"] == 1


class TestMMIDecoderGeneric:
    """Test cases for generic MMI decode method."""
    
    def test_decode_by_packet_type_dynamic(self):
        """Test decoding via packet type."""
        data = bytes([0] * 30)  # Minimal valid MMI_DYNAMIC data
        
        result = MMIDecoder.decode(MMI_DYNAMIC, data)
        
        assert isinstance(result, MMIDynamicData)
    
    def test_decode_by_packet_type_status(self):
        """Test decoding STATUS via packet type."""
        data = bytes([0] * 6)  # Minimal valid MMI_STATUS data
        
        result = MMIDecoder.decode(MMI_STATUS, data)
        
        assert isinstance(result, MMIStatusData)
    
    def test_decode_unsupported_packet_type(self):
        """Test that unsupported packet type raises error."""
        data = bytes([0] * 10)
        
        with pytest.raises(ValueError, match="Unsupported MMI packet type"):
            MMIDecoder.decode(999, data)
