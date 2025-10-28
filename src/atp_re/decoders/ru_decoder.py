"""
RU (Recording Unit) Decoder for ATP packets.

This module provides functionality to decode RU packets, which are the main
packet format recorded by the ATP system. It acts as a dispatcher to route
packets to appropriate decoders based on packet type.
"""

from dataclasses import dataclass
from typing import Any, Optional, Dict
from .packet_header_parser import PacketHeaderParser, PacketHeader
from .mmi_decoder import MMIDecoder, MMI_DYNAMIC, MMI_STATUS
from .btm_decoder import BTMDecoder, BTMTelegram


# RU Packet Type Constants (from Java RUDecoder)
PACKET_ATP = 1
PACKET_MMI = 4
PACKET_STATUS_ATP = 2
PACKET_STATUS_MMI = 3
PACKET_VDX_IN_STATUS_1 = 21
PACKET_VDX_OUT_1 = 22
PACKET_VDX_OUT_2 = 23
PACKET_VDX_OUT_3 = 24
PACKET_BTM_COMMAND_1 = 41
PACKET_BTM_STATUS_1 = 42
PACKET_BTM_TGM_1 = 43
PACKET_BTM_TGM_2 = 44
PACKET_BTM_TGM_3 = 45
PACKET_BTM_TGM_4 = 46
PACKET_BTM_TGM_5 = 47
PACKET_ATP_DOWN = 201
PACKET_BUTTON_EVENT = 216


@dataclass
class RUPacket:
    """
    Decoded RU packet.
    
    Attributes:
        header: Parsed packet header
        packet_type: RU packet type identifier
        description: Human-readable packet description
        data: Decoded packet data (type varies by packet_type)
    """
    header: PacketHeader
    packet_type: int
    description: str
    data: Any
    
    def to_dict(self) -> Dict[str, Any]:
        """
        Convert packet to dictionary with all decoded values.
        
        Returns:
            Dictionary containing all packet information including header and decoded data
        """
        result = {
            "packet_type": self.packet_type,
            "description": self.description,
            "header": self.header.to_dict(),
            "data": None
        }
        
        # Convert data to dict if it has to_dict method
        if self.data is not None:
            if hasattr(self.data, 'to_dict'):
                result["data"] = self.data.to_dict()
            elif isinstance(self.data, dict):
                result["data"] = self.data
            else:
                # For other types, convert to string representation
                result["data"] = str(self.data)
        
        return result


class RUDecoder:
    """
    Decoder for RU (Recording Unit) packets.
    
    RU packets have the following structure:
    - Bytes 0-14: Packet header (timestamp, location, speed, packet_no)
    - Byte 15: Body length
    - Bytes 16+: Body data
    
    The decoder routes packets to appropriate sub-decoders based on packet type.
    """
    
    def __init__(self):
        """Initialize RU decoder with sub-decoders."""
        self._btm_decoder = BTMDecoder()
        self._mmi_decoder = MMIDecoder()
    
    def decode(self, data: bytes) -> RUPacket:
        """
        Decode a complete RU packet.
        
        Args:
            data: Raw RU packet data
            
        Returns:
            RUPacket with decoded header and body
            
        Raises:
            ValueError: If data is invalid or packet type is unknown
        """
        # Parse header and body
        header, body = PacketHeaderParser.parse_header_and_body(data)
        
        # Decode body based on packet type
        packet_type = header.packet_no
        description, decoded_data = self._decode_body(packet_type, body)
        
        return RUPacket(
            header=header,
            packet_type=packet_type,
            description=description,
            data=decoded_data
        )
    
    def _decode_body(self, packet_type: int, body: bytes) -> tuple[str, Any]:
        """
        Decode packet body based on packet type.
        
        Args:
            packet_type: RU packet type identifier
            body: Raw body data
            
        Returns:
            Tuple of (description, decoded_data)
        """
        # ATP/MMI packets (types 1 and 4)
        if packet_type == PACKET_ATP or packet_type == PACKET_MMI:
            return self._decode_atp_mmi_packet(body)
        
        # BTM telegram fragments (types 43-47)
        elif PACKET_BTM_TGM_1 <= packet_type <= PACKET_BTM_TGM_5:
            telegram_number = packet_type - PACKET_BTM_TGM_1 + 1
            return self._decode_btm_fragment(body, telegram_number)
        
        # Status packets
        elif packet_type == PACKET_STATUS_ATP:
            return "STATUS ATP", {"status": body[0] if len(body) > 0 else 0}
        
        elif packet_type == PACKET_STATUS_MMI:
            return "STATUS MMI", {"status": body[0] if len(body) > 0 else 0}
        
        # VDX packets (no detailed decoding yet)
        elif packet_type == PACKET_VDX_IN_STATUS_1:
            return "MVB_LOG_TYPE_VDX_IN_STATUS_1: no packet format to decode", None
        
        elif packet_type == PACKET_VDX_OUT_1:
            return "MVB_LOG_TYPE_VDX_OUT_1: no packet format to decode", None
        
        elif packet_type == PACKET_VDX_OUT_2:
            return "MVB_LOG_TYPE_VDX_OUT_2: no packet format to decode", None
        
        elif packet_type == PACKET_VDX_OUT_3:
            return "MVB_LOG_TYPE_VDX_OUT_3: no packet format to decode", None
        
        # BTM command/status (no detailed decoding yet)
        elif packet_type == PACKET_BTM_COMMAND_1:
            return "MVB_LOG_BTM_COMMAND_1: no packet format to decode", None
        
        elif packet_type == PACKET_BTM_STATUS_1:
            return "MVB_LOG_BTM_STATUS_1: no packet format to decode", None
        
        # Button event
        elif packet_type == PACKET_BUTTON_EVENT:
            button_value = body[0] if len(body) > 0 else 0
            return "MVB LOG TYPE BUTTON EVENT", {"button": button_value}
        
        # ATP down event
        elif packet_type == PACKET_ATP_DOWN:
            return "ATP DOWN", None
        
        # Unknown packet type
        else:
            return f"no handle Record Type:{packet_type}", None
    
    def _decode_atp_mmi_packet(self, body: bytes) -> tuple[str, Any]:
        """
        Decode ATP/MMI packet body.
        
        The first 3 bytes of ATP/MMI packets contain:
        - Byte 0: Sub-packet type
        - Bytes 1-2: Length or other parameters
        
        Args:
            body: Raw ATP/MMI packet body
            
        Returns:
            Tuple of (description, decoded_data)
        """
        if len(body) < 3:
            return "ATP/MMI packet too short", None
        
        # Get sub-packet type from first byte
        sub_packet_type = body[0]
        
        # Try to decode as MMI packet
        try:
            if sub_packet_type == MMI_DYNAMIC:
                decoded = self._mmi_decoder.decode_mmi_dynamic(body)
                return "MMI_DYNAMIC", decoded
            elif sub_packet_type == MMI_STATUS:
                decoded = self._mmi_decoder.decode_mmi_status(body)
                return "MMI_STATUS", decoded
            else:
                return f"ATP/MMI Packet Type {sub_packet_type}", None
        except ValueError:
            return f"ATP/MMI Packet Type {sub_packet_type} (decode error)", None
    
    def _decode_btm_fragment(self, body: bytes, telegram_number: int) -> tuple[str, Any]:
        """
        Decode BTM telegram fragment and attempt reassembly.
        
        Args:
            body: Raw BTM fragment data
            telegram_number: Fragment number (1-5)
            
        Returns:
            Tuple of (description, decoded_data)
        """
        description = f"MVB_LOG_BTM_TGM_{telegram_number}"
        
        try:
            telegram = self._btm_decoder.add_fragment(body, telegram_number)
            
            if telegram is not None:
                # Successfully reassembled complete telegram
                # Convert telegram to dict for JSON serialization
                telegram_dict = telegram.to_dict()
                return description, {
                    "sequence_number": telegram.sequence_number,
                    "telegram_size": len(telegram.data),
                    "telegram": telegram_dict
                }
            else:
                # Fragment received but reassembly not complete yet
                return description, None
        
        except ValueError as e:
            return f"{description} (error: {e})", None
    
    def get_btm_decoder(self) -> BTMDecoder:
        """
        Get the BTM decoder instance.
        
        Returns:
            BTMDecoder instance
        """
        return self._btm_decoder
    
    def reset(self) -> None:
        """Reset all decoder state."""
        self._btm_decoder.reset()
