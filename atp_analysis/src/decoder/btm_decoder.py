"""
BTM Decoder - Decodes BTM (Balise Transmission Module) telegrams.

BTM telegrams are transmitted by trackside balises (beacons) and received by
the train's BTM antenna. Each complete telegram may be split across up to 5
fragments that must be reassembled.

Java References:
    - decoder_re/BTMDecoder.java: Main BTM decoding logic with 5-fragment reassembly
    - decoder_re/waySidePacket/*.java: 19 ETCS packet decoders for balise content
"""

import struct
import logging
from typing import List, Optional, Dict, Tuple
from dataclasses import dataclass

import structlog

logger = structlog.get_logger(__name__)


class BTMDecodeError(Exception):
    """Raised when BTM telegram decoding fails."""
    pass


@dataclass
class BTMFragment:
    """
    Single fragment of a BTM telegram.
    
    BTM telegrams can be split across up to 5 fragments. Each fragment contains
    a portion of the complete telegram data along with sequencing information.
    
    Attributes:
        fragment_number: Fragment sequence number (1-5)
        total_fragments: Total number of fragments for this telegram
        data: Raw fragment data
    """
    fragment_number: int
    total_fragments: int
    data: bytes


@dataclass
class BTMTelegram:
    """
    Complete reassembled BTM telegram.
    
    After reassembling all fragments, the telegram contains ETCS packets
    that describe track information, movement authorities, and speed profiles.
    
    Attributes:
        telegram_id: Unique identifier for this telegram
        fragments: List of fragments that compose this telegram
        data: Complete reassembled telegram data
        packets: Decoded ETCS packets (to be implemented in Phase 2)
    """
    telegram_id: int
    fragments: List[BTMFragment]
    data: bytes
    packets: Optional[List] = None  # ETCS packets (Phase 2)


class BTMDecoder:
    """
    Decoder for BTM (Balise Transmission Module) telegrams.
    
    BTM telegrams are received from trackside balises and contain critical
    information about:
    - Movement authorities (where the train is allowed to go)
    - Speed restrictions
    - Track gradients
    - Signaling information
    
    The decoding process involves:
    1. Fragment collection: Gather up to 5 fragments per telegram
    2. Reassembly: Combine fragments into complete telegram
    3. Packet parsing: Decode 19+ types of ETCS wayside packets
    
    Java Reference:
        decoder_re/BTMDecoder.java - Implements 5-fragment reassembly logic
        decoder_re/waySidePacket/*.java - 19 packet type decoders
    
    Phase 1 Status:
        - Basic structure and interface defined
        - Fragment handling prepared
        - Full implementation in Phase 2 (requires extensive packet decoders)
    
    Example (Phase 2):
        >>> decoder = BTMDecoder()
        >>> fragments = [fragment1, fragment2, fragment3]  # Collected fragments
        >>> telegram = decoder.reassemble(fragments)
        >>> for packet in telegram.packets:
        ...     print(f"Packet type: {packet.type}")
    """
    
    def __init__(self):
        """Initialize BTM decoder with empty fragment buffer."""
        self.fragment_buffer: Dict[int, List[BTMFragment]] = {}
        logger.info("btm_decoder_initialized")
    
    def add_fragment(self, fragment: BTMFragment, telegram_id: int) -> Optional[BTMTelegram]:
        """
        Add a fragment to the buffer and attempt reassembly.
        
        Fragments are buffered by telegram ID. When all fragments for a telegram
        are received, they are reassembled into a complete BTMTelegram.
        
        Args:
            fragment: BTM fragment to add
            telegram_id: Unique identifier for the telegram
            
        Returns:
            Complete BTMTelegram if all fragments received, None otherwise
            
        Java Reference:
            decoder_re/BTMDecoder.java: Fragment collection and reassembly logic
        """
        # Initialize fragment list for this telegram if needed
        if telegram_id not in self.fragment_buffer:
            self.fragment_buffer[telegram_id] = []
        
        # Add fragment to buffer
        self.fragment_buffer[telegram_id].append(fragment)
        
        # Check if all fragments received
        fragments = self.fragment_buffer[telegram_id]
        if len(fragments) == fragment.total_fragments:
            # All fragments collected - reassemble
            telegram = self._reassemble_telegram(telegram_id, fragments)
            # Clean up buffer
            del self.fragment_buffer[telegram_id]
            return telegram
        
        return None
    
    def _reassemble_telegram(self, telegram_id: int, fragments: List[BTMFragment]) -> BTMTelegram:
        """
        Reassemble fragments into complete telegram.
        
        Sorts fragments by sequence number and concatenates their data.
        
        Args:
            telegram_id: Telegram identifier
            fragments: List of all fragments for this telegram
            
        Returns:
            Complete reassembled BTMTelegram
            
        Raises:
            BTMDecodeError: If fragment reassembly fails
            
        Java Reference:
            decoder_re/BTMDecoder.java: 5-fragment reassembly implementation
        """
        try:
            # Sort fragments by fragment number
            sorted_fragments = sorted(fragments, key=lambda f: f.fragment_number)
            
            # Validate fragment sequence
            expected_numbers = list(range(1, len(fragments) + 1))
            actual_numbers = [f.fragment_number for f in sorted_fragments]
            if actual_numbers != expected_numbers:
                raise BTMDecodeError(
                    f"Invalid fragment sequence for telegram {telegram_id}: "
                    f"expected {expected_numbers}, got {actual_numbers}"
                )
            
            # Concatenate fragment data
            complete_data = b''.join(f.data for f in sorted_fragments)
            
            telegram = BTMTelegram(
                telegram_id=telegram_id,
                fragments=sorted_fragments,
                data=complete_data,
                packets=None  # Packet parsing in Phase 2
            )
            
            logger.info(
                "btm_telegram_reassembled",
                telegram_id=telegram_id,
                fragment_count=len(fragments),
                data_length=len(complete_data)
            )
            
            return telegram
            
        except Exception as e:
            logger.error("btm_reassembly_failed", telegram_id=telegram_id, error=str(e))
            raise BTMDecodeError(f"Failed to reassemble telegram {telegram_id}: {e}") from e
    
    def decode_packets(self, telegram: BTMTelegram) -> List:
        """
        Decode ETCS packets from reassembled telegram.
        
        Phase 2 Implementation:
        This will parse 19+ types of ETCS wayside packets:
        - P3: Movement Authority
        - P12: Level 1 Movement Authority
        - P15: Level 2/3 Movement Authority
        - P21: Gradient Profile
        - P27: International Static Speed Profile
        - P41: Level Transition Order
        - And 13 more packet types...
        
        Args:
            telegram: Reassembled BTM telegram
            
        Returns:
            List of decoded ETCS packets
            
        Java Reference:
            decoder_re/waySidePacket/WaySideTelegramPacketDecoder.java: Main router
            decoder_re/waySidePacket/Packet*.java: Individual packet decoders (19 files)
        """
        raise NotImplementedError(
            "ETCS packet decoding will be implemented in Phase 2. "
            "Requires implementation of 19 wayside packet decoders. "
            "See decoder_re/waySidePacket/*.java for reference."
        )
    
    def clear_buffer(self):
        """Clear the fragment buffer (useful for cleanup or reset)."""
        self.fragment_buffer.clear()
        logger.info("btm_buffer_cleared")
    
    def get_pending_telegrams(self) -> List[int]:
        """
        Get list of telegram IDs with incomplete fragments.
        
        Returns:
            List of telegram IDs awaiting more fragments
        """
        return list(self.fragment_buffer.keys())


# Placeholder for ETCS packet types (Phase 2)
# These correspond to the 19 packet decoders in decoder_re/waySidePacket/

class ETCSPacketType:
    """
    ETCS packet type constants.
    
    These are the standard ETCS packet types found in BTM telegrams.
    Full implementation in Phase 2.
    
    Java Reference:
        decoder_re/waySidePacket/Packet*.java (19 files)
    """
    P3_MOVEMENT_AUTHORITY = 3
    P12_LEVEL_1_MOVEMENT_AUTHORITY = 12
    P15_LEVEL_23_MOVEMENT_AUTHORITY = 15
    P21_GRADIENT_PROFILE = 21
    P27_INTL_STATIC_SPEED_PROFILE = 27
    P41_LEVEL_TRANSITION_ORDER = 41
    P42_SESSION_MANAGEMENT = 42
    P44_DATA_USED_BY_APP_OUTSIDE_ERTMS_ETCS = 44
    P45_RADIO_NETWORK_REGISTRATION = 45
    P46_CONDITIONAL_LEVEL_TRANSITION_ORDER = 46
    P49_LIST_OF_BALISES_FOR_SH_AREA = 49
    P51_AXLE_LOAD_SPEED_PROFILE = 51
    P52_PERMITTED_BRAKING_DISTANCE_INFO = 52
    P57_MOVEMENT_AUTHORITY_REQ_PARAM = 57
    P58_POSITION_REPORT_PARAM = 58
    P63_LIST_OF_BALISES_IN_SR_AUTHORITY = 63
    P64_INHIBITION_OF_REVOCABLE_TSR = 64
    P65_TEMPORARY_SPEED_RESTRICTION = 65
    P66_TEMPORARY_SPEED_RESTRICTION_REVOCATION = 66
    P67_TRACK_CONDITION_BIG_METAL_MASSES = 67
