"""
MMI (Man-Machine Interface) Decoder for ATP packets.

This module provides functionality to decode MMI packets,
matching the Java PacketMMI functionality with priority on MMI_DYNAMIC.
"""

from dataclasses import dataclass
from typing import Dict, Any, List
from .byte_utils import Byte2Number


# MMI Packet Type Constants (from Java PacketMMI)
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
MMI_RU_DATA = 19


@dataclass
class MMIDynamicData:
    """
    Decoded MMI_DYNAMIC packet data.
    
    This represents the dynamic train state information including speed,
    acceleration, position, and various operational parameters.
    
    Attributes:
        v_train: Train speed (km/h)
        a_train: Train acceleration (cm/s²)
        o_train: Train position (meters)
        o_brake_target: Brake target position (meters)
        v_target: Target speed (km/h)
        t_interven_war: Intervention warning time (seconds)
        v_permitted: Permitted speed (km/h)
        v_release: Release speed (km/h)
        v_intervention: Intervention speed (km/h)
        m_warning: Warning mode (0-15)
        m_slip: Slip indication (0-1)
        m_slide: Slide indication (0-1)
        o_bcsp: BCSP position (meters)
    """
    v_train: int
    a_train: int
    o_train: int
    o_brake_target: int
    v_target: int
    t_interven_war: int
    v_permitted: int
    v_release: int
    v_intervention: int
    m_warning: int
    m_slip: int
    m_slide: int
    o_bcsp: int
    
    def to_dict(self) -> Dict[str, int]:
        """Convert to dictionary for compatibility."""
        return {
            "v_train": self.v_train,
            "a_train": self.a_train,
            "o_train": self.o_train,
            "o_brake_target": self.o_brake_target,
            "v_target": self.v_target,
            "t_interven_war": self.t_interven_war,
            "v_permitted": self.v_permitted,
            "v_release": self.v_release,
            "v_intervention": self.v_intervention,
            "m_warning": self.m_warning,
            "m_slip": self.m_slip,
            "m_slide": self.m_slide,
            "o_bcsp": self.o_bcsp,
        }


@dataclass
class MMIStatusData:
    """
    Decoded MMI_STATUS packet data.
    
    Attributes:
        m_adhesion: Adhesion mode
        m_mode: Operating mode
        m_level: ATP level
        m_emer_brake: Emergency brake status
        m_service_brake: Service brake status
        m_override_eoa: Override EOA status
        m_trip: Trip status
        m_active_cabin: Active cabin identifier
    """
    m_adhesion: int
    m_mode: int
    m_level: int
    m_emer_brake: int
    m_service_brake: int
    m_override_eoa: int
    m_trip: int
    m_active_cabin: int
    
    def to_dict(self) -> Dict[str, int]:
        """Convert to dictionary for compatibility."""
        return {
            "m_adhesion": self.m_adhesion,
            "m_mode": self.m_mode,
            "m_level": self.m_level,
            "m_emer_brake": self.m_emer_brake,
            "m_service_brake": self.m_service_brake,
            "m_override_eoa": self.m_override_eoa,
            "m_trip": self.m_trip,
            "m_active_cabin": self.m_active_cabin,
        }


class MMIDecoder:
    """
    Decoder for MMI packets.
    
    Provides methods to decode various MMI packet types, with priority
    support for MMI_DYNAMIC (packet type 1).
    """
    
    @staticmethod
    def decode_mmi_dynamic(data: bytes) -> MMIDynamicData:
        """
        Decode MMI_DYNAMIC packet (packet type 1).
        
        Packet structure (starting from byte 3):
        - Bytes 3-4: V_TRAIN (train speed)
        - Bytes 5-6: A_TRAIN (train acceleration, signed)
        - Bytes 7-10: O_TRAIN (train position)
        - Bytes 11-14: O_BRAKETARGET (brake target position)
        - Bytes 15-16: V_TARGET (target speed)
        - Bytes 17-18: T_INTERVENWAR (intervention warning time)
        - Bytes 19-20: V_PERMITTED (permitted speed)
        - Bytes 21-22: V_RELEASE (release speed)
        - Bytes 23-24: V_INTERVENTION (intervention speed)
        - Byte 25 high nibble: M_WARNING (warning mode)
        - Byte 25 bit 3: M_SLIP (slip indication)
        - Byte 25 bit 2: M_SLIDE (slide indication)
        - Bytes 26-29: O_BCSP (BCSP position)
        
        Args:
            data: Raw packet data (must be at least 30 bytes)
            
        Returns:
            MMIDynamicData object with decoded values
            
        Raises:
            ValueError: If data is too short
        """
        if len(data) < 30:
            raise ValueError(f"MMI_DYNAMIC data too short: expected 30 bytes, got {len(data)}")
        
        # Parse train speed (unsigned)
        v_train = Byte2Number.get_unsigned_2(data[3], data[4])
        
        # Parse train acceleration (signed)
        a_train = Byte2Number.get_signed_2(data[5], data[6])
        
        # Parse train position (unsigned, adjust if >= 1 billion)
        o_train = Byte2Number.get_unsigned_4(data[7], data[8], data[9], data[10])
        if o_train >= 1000000000:
            o_train -= 1000000000
        
        # Parse brake target position (unsigned, adjust if >= 1 billion)
        o_brake_target = Byte2Number.get_unsigned_4(data[11], data[12], data[13], data[14])
        if o_brake_target >= 1000000000:
            o_brake_target -= 1000000000
        
        # Parse various speed and timing values
        v_target = Byte2Number.get_unsigned_2(data[15], data[16])
        t_interven_war = Byte2Number.get_unsigned_2(data[17], data[18])
        v_permitted = Byte2Number.get_unsigned_2(data[19], data[20])
        v_release = Byte2Number.get_unsigned_2(data[21], data[22])
        v_intervention = Byte2Number.get_unsigned_2(data[23], data[24])
        
        # Parse bit fields from byte 25
        byte_25 = Byte2Number.get_unsigned(data[25])
        m_warning = (byte_25 & 0xF0) >> 4  # High nibble
        m_slip = (byte_25 & 0x08) >> 3     # Bit 3
        m_slide = (byte_25 & 0x04) >> 2    # Bit 2
        
        # Parse BCSP position (unsigned)
        o_bcsp = Byte2Number.get_unsigned_4(data[26], data[27], data[28], data[29])
        
        return MMIDynamicData(
            v_train=v_train,
            a_train=a_train,
            o_train=o_train,
            o_brake_target=o_brake_target,
            v_target=v_target,
            t_interven_war=t_interven_war,
            v_permitted=v_permitted,
            v_release=v_release,
            v_intervention=v_intervention,
            m_warning=m_warning,
            m_slip=m_slip,
            m_slide=m_slide,
            o_bcsp=o_bcsp
        )
    
    @staticmethod
    def decode_mmi_status(data: bytes) -> MMIStatusData:
        """
        Decode MMI_STATUS packet (packet type 2).
        
        Packet structure (starting from byte 3):
        - Byte 3: M_ADHESION (adhesion mode)
        - Byte 4 high nibble: M_MODE (operating mode)
        - Byte 4 low nibble: M_LEVEL (ATP level)
        - Byte 5 bits 6-7: M_EMERBRAKE (emergency brake)
        - Byte 5 bits 4-5: M_SERVICEBRAKE (service brake)
        - Byte 5 bit 3: M_OVERRIDE_EOA (override EOA)
        - Byte 5 bit 2: M_TRIP (trip status)
        - Byte 5 bits 0-1: M_ACTIVE_CABIN (active cabin)
        
        Args:
            data: Raw packet data (must be at least 6 bytes)
            
        Returns:
            MMIStatusData object with decoded values
            
        Raises:
            ValueError: If data is too short
        """
        if len(data) < 6:
            raise ValueError(f"MMI_STATUS data too short: expected 6 bytes, got {len(data)}")
        
        m_adhesion = Byte2Number.get_unsigned(data[3])
        
        byte_4 = Byte2Number.get_unsigned(data[4])
        m_mode = (byte_4 & 0xF0) >> 4  # High nibble
        m_level = byte_4 & 0x0F        # Low nibble
        
        byte_5 = Byte2Number.get_unsigned(data[5])
        m_emer_brake = (byte_5 & 0xC0) >> 6     # Bits 6-7
        m_service_brake = (byte_5 & 0x30) >> 4  # Bits 4-5
        m_override_eoa = (byte_5 & 0x08) >> 3   # Bit 3
        m_trip = (byte_5 & 0x04) >> 2           # Bit 2
        m_active_cabin = byte_5 & 0x03          # Bits 0-1
        
        return MMIStatusData(
            m_adhesion=m_adhesion,
            m_mode=m_mode,
            m_level=m_level,
            m_emer_brake=m_emer_brake,
            m_service_brake=m_service_brake,
            m_override_eoa=m_override_eoa,
            m_trip=m_trip,
            m_active_cabin=m_active_cabin
        )
    
    @staticmethod
    def decode(packet_type: int, data: bytes) -> Any:
        """
        Decode MMI packet based on packet type.
        
        Args:
            packet_type: MMI packet type identifier
            data: Raw packet data
            
        Returns:
            Decoded data object (type depends on packet_type)
            
        Raises:
            ValueError: If packet type is not supported or data is invalid
        """
        if packet_type == MMI_DYNAMIC:
            return MMIDecoder.decode_mmi_dynamic(data)
        elif packet_type == MMI_STATUS:
            return MMIDecoder.decode_mmi_status(data)
        else:
            raise ValueError(f"Unsupported MMI packet type: {packet_type}")
