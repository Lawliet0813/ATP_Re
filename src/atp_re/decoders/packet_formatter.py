"""
Packet Formatter for displaying decoded packet values.

This module provides formatting utilities to display decoded packet data
in a clear, human-readable format with field descriptions.
"""

from typing import Dict, Any, Optional
import json


# Field descriptions for different packet types
FIELD_DESCRIPTIONS = {
    # Packet Header Fields
    "packet_no": "Packet Number/Type",
    "timestamp": "Recording Timestamp",
    "location": "Train Location (meters)",
    "speed": "Train Speed (km/h)",
    
    # MMI_DYNAMIC Fields
    "v_train": "Train Speed (km/h)",
    "a_train": "Train Acceleration (cm/s²)",
    "o_train": "Train Position (meters)",
    "o_brake_target": "Brake Target Position (meters)",
    "v_target": "Target Speed (km/h)",
    "t_interven_war": "Intervention Warning Time (seconds)",
    "v_permitted": "Permitted Speed (km/h)",
    "v_release": "Release Speed (km/h)",
    "v_intervention": "Intervention Speed (km/h)",
    "m_warning": "Warning Mode (0-15)",
    "m_slip": "Slip Indication (0-1)",
    "m_slide": "Slide Indication (0-1)",
    "o_bcsp": "BCSP Position (meters)",
    
    # MMI_STATUS Fields
    "m_adhesion": "Adhesion Mode",
    "m_mode": "Operating Mode",
    "m_level": "ATP Level",
    "m_emer_brake": "Emergency Brake Status",
    "m_service_brake": "Service Brake Status",
    "m_override_eoa": "Override EOA Status",
    "m_trip": "Trip Status",
    "m_active_cabin": "Active Cabin Identifier",
    
    # BTM Fields
    "sequence_number": "Telegram Sequence Number",
    "telegram_number": "Fragment Number (1-5)",
    "data_length": "Data Length (bytes)",
    "data_hex": "Raw Data (hexadecimal)",
    "nid_bg": "Balise Group Identifier",
    "m_count": "Message Count",
    
    # Other Fields
    "button": "Button Value",
    "status": "Status Value",
}


class PacketFormatter:
    """Formatter for decoded packet data."""
    
    @staticmethod
    def format_packet(packet_dict: Dict[str, Any], indent: int = 0) -> str:
        """
        Format a decoded packet dictionary into human-readable text.
        
        Args:
            packet_dict: Dictionary containing decoded packet data
            indent: Indentation level for nested structures
            
        Returns:
            Formatted string representation
        """
        lines = []
        indent_str = "  " * indent
        
        # Format packet type and description
        if "packet_type" in packet_dict:
            lines.append(f"{indent_str}Packet Type: {packet_dict['packet_type']}")
        if "description" in packet_dict:
            lines.append(f"{indent_str}Description: {packet_dict['description']}")
        
        # Format header
        if "header" in packet_dict and packet_dict["header"]:
            lines.append(f"{indent_str}Header:")
            lines.append(PacketFormatter._format_dict(
                packet_dict["header"], 
                indent + 1
            ))
        
        # Format data
        if "data" in packet_dict and packet_dict["data"]:
            lines.append(f"{indent_str}Decoded Data:")
            if isinstance(packet_dict["data"], dict):
                lines.append(PacketFormatter._format_dict(
                    packet_dict["data"], 
                    indent + 1
                ))
            else:
                lines.append(f"{indent_str}  {packet_dict['data']}")
        
        return "\n".join(lines)
    
    @staticmethod
    def _format_dict(data: Dict[str, Any], indent: int = 0) -> str:
        """Format a dictionary with field descriptions."""
        lines = []
        indent_str = "  " * indent
        
        for key, value in data.items():
            # Get field description
            description = FIELD_DESCRIPTIONS.get(key, key.replace("_", " ").title())
            
            # Format the value
            if isinstance(value, dict):
                lines.append(f"{indent_str}{description}:")
                lines.append(PacketFormatter._format_dict(value, indent + 1))
            elif isinstance(value, (list, tuple)):
                lines.append(f"{indent_str}{description}: {value}")
            else:
                lines.append(f"{indent_str}{description}: {value}")
        
        return "\n".join(lines)
    
    @staticmethod
    def format_packet_json(packet_dict: Dict[str, Any], pretty: bool = True) -> str:
        """
        Format packet as JSON.
        
        Args:
            packet_dict: Dictionary containing decoded packet data
            pretty: Whether to pretty-print the JSON
            
        Returns:
            JSON string representation
        """
        if pretty:
            return json.dumps(packet_dict, indent=2, ensure_ascii=False)
        return json.dumps(packet_dict, ensure_ascii=False)
    
    @staticmethod
    def format_packet_list(packets: list, format_type: str = "text") -> str:
        """
        Format a list of decoded packets.
        
        Args:
            packets: List of packet dictionaries
            format_type: Output format ("text" or "json")
            
        Returns:
            Formatted string representation
        """
        if format_type == "json":
            return json.dumps(packets, indent=2, ensure_ascii=False)
        
        lines = []
        lines.append(f"=== Decoded Packets ({len(packets)} total) ===\n")
        
        for i, packet in enumerate(packets, 1):
            lines.append(f"--- Packet #{i} ---")
            lines.append(PacketFormatter.format_packet(packet))
            lines.append("")  # Empty line between packets
        
        return "\n".join(lines)
    
    @staticmethod
    def get_field_description(field_name: str) -> str:
        """
        Get human-readable description for a field name.
        
        Args:
            field_name: Field name to look up
            
        Returns:
            Human-readable description
        """
        return FIELD_DESCRIPTIONS.get(
            field_name, 
            field_name.replace("_", " ").title()
        )
