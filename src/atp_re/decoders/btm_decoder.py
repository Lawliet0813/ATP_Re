"""
BTM (Balise Transmission Module) Decoder with fragment reassembly.

This module provides functionality to decode and reassemble BTM telegrams
from 5 fragments into complete 104-byte telegrams, matching the Java
BTMDecoder functionality.
"""

from dataclasses import dataclass
from typing import Optional, List, Tuple
from .byte_utils import Byte2Number


@dataclass
class BTMFragment:
    """
    Represents a single BTM telegram fragment.
    
    Attributes:
        sequence_number: Telegram sequence number (0-255)
        telegram_number: Fragment number within sequence (1-5)
        data: Raw fragment data
    """
    sequence_number: int
    telegram_number: int
    data: bytes


@dataclass
class BTMTelegram:
    """
    Represents a complete reassembled BTM telegram.
    
    Attributes:
        sequence_number: Telegram sequence number
        data: Complete 104-byte telegram data
        nid_bg: Balise group identifier
        m_count: Message count
    """
    sequence_number: int
    data: bytes
    nid_bg: Optional[int] = None
    m_count: Optional[int] = None


class BTMDecoder:
    """
    Decoder for BTM telegrams with fragment reassembly.
    
    BTM telegrams are transmitted in 5 fragments:
    - Fragment 1: Contains bytes 22-25 (4 bytes header)
    - Fragment 2: Contains bytes 1-25 at position 4-28
    - Fragment 3: Contains bytes 1-25 at position 29-53
    - Fragment 4: Contains bytes 1-25 at position 54-78
    - Fragment 5: Contains bytes 1-25 at position 79-103
    
    Total reassembled telegram: 104 bytes
    
    The decoder maintains 10 slots for tracking different sequence numbers
    simultaneously, allowing parallel reassembly of multiple telegrams.
    """
    
    NUM_SLOTS = 10
    NUM_FRAGMENTS = 5
    TELEGRAM_SIZE = 104
    
    def __init__(self):
        """Initialize BTM decoder with empty slots."""
        # Track sequence number for each slot (-1 means empty)
        self._sequence_slots: List[int] = [-1] * self.NUM_SLOTS
        
        # Track which fragments have been received for each slot
        # 0 = not received, 1 = received
        self._fragment_status: List[List[int]] = [
            [0] * self.NUM_FRAGMENTS for _ in range(self.NUM_SLOTS)
        ]
        
        # Store fragment data for each slot
        self._fragment_data: List[List[Optional[bytes]]] = [
            [None] * self.NUM_FRAGMENTS for _ in range(self.NUM_SLOTS)
        ]
        
        # Last completed telegram
        self._last_telegram: Optional[BTMTelegram] = None
    
    def add_fragment(self, fragment_data: bytes, telegram_number: int) -> Optional[BTMTelegram]:
        """
        Add a BTM fragment and attempt reassembly.
        
        Args:
            fragment_data: Raw fragment data (first byte is sequence number)
            telegram_number: Fragment number (1-5)
            
        Returns:
            BTMTelegram if reassembly is complete, None otherwise
            
        Raises:
            ValueError: If telegram_number is invalid or data is too short
        """
        if telegram_number < 1 or telegram_number > self.NUM_FRAGMENTS:
            raise ValueError(
                f"Invalid telegram number: {telegram_number} "
                f"(must be 1-{self.NUM_FRAGMENTS})"
            )
        
        if len(fragment_data) < 1:
            raise ValueError("Fragment data too short")
        
        # Extract sequence number from first byte
        sequence_number = Byte2Number.get_unsigned(fragment_data[0])
        
        # Find or allocate a slot for this sequence number
        slot_idx = self._find_or_allocate_slot(sequence_number)
        
        if slot_idx is None:
            # No available slots (all 10 are in use)
            # This shouldn't happen in normal operation
            return None
        
        # Store fragment (telegram_number is 1-based, convert to 0-based index)
        fragment_idx = telegram_number - 1
        self._fragment_status[slot_idx][fragment_idx] = 1
        self._fragment_data[slot_idx][fragment_idx] = fragment_data
        
        # Check if all 5 fragments have been received
        if all(status == 1 for status in self._fragment_status[slot_idx]):
            # Reassemble telegram
            telegram = self._reassemble_telegram(slot_idx, sequence_number)
            
            # Clear slot for reuse
            self._clear_slot(slot_idx)
            
            self._last_telegram = telegram
            return telegram
        
        return None
    
    def _find_or_allocate_slot(self, sequence_number: int) -> Optional[int]:
        """
        Find existing slot for sequence number or allocate a new one.
        
        Args:
            sequence_number: Sequence number to find or allocate
            
        Returns:
            Slot index (0-9) or None if no slots available
        """
        # First, check if sequence number already has a slot
        for i in range(self.NUM_SLOTS):
            if self._sequence_slots[i] == sequence_number:
                return i
        
        # Find an empty slot
        for i in range(self.NUM_SLOTS):
            if self._sequence_slots[i] == -1:
                self._sequence_slots[i] = sequence_number
                return i
        
        # No available slots
        return None
    
    def _clear_slot(self, slot_idx: int) -> None:
        """
        Clear a slot for reuse.
        
        Args:
            slot_idx: Slot index to clear
        """
        self._sequence_slots[slot_idx] = -1
        self._fragment_status[slot_idx] = [0] * self.NUM_FRAGMENTS
        self._fragment_data[slot_idx] = [None] * self.NUM_FRAGMENTS
    
    def _reassemble_telegram(self, slot_idx: int, sequence_number: int) -> BTMTelegram:
        """
        Reassemble complete telegram from fragments.
        
        Args:
            slot_idx: Slot index containing fragments
            sequence_number: Sequence number of telegram
            
        Returns:
            Complete BTMTelegram
        """
        # Allocate 104-byte telegram buffer
        telegram_data = bytearray(self.TELEGRAM_SIZE)
        
        # Reassemble according to BTM fragment structure
        for frag_idx in range(self.NUM_FRAGMENTS):
            fragment = self._fragment_data[slot_idx][frag_idx]
            if fragment is None:
                continue
            
            if frag_idx == 0:
                # Fragment 1: bytes 22-25 (4 bytes) -> positions 0-3
                if len(fragment) >= 26:
                    telegram_data[0:4] = fragment[22:26]
            elif frag_idx == 1:
                # Fragment 2: bytes 1-25 (25 bytes) -> positions 4-28
                if len(fragment) >= 26:
                    telegram_data[4:29] = fragment[1:26]
            elif frag_idx == 2:
                # Fragment 3: bytes 1-25 (25 bytes) -> positions 29-53
                if len(fragment) >= 26:
                    telegram_data[29:54] = fragment[1:26]
            elif frag_idx == 3:
                # Fragment 4: bytes 1-25 (25 bytes) -> positions 54-78
                if len(fragment) >= 26:
                    telegram_data[54:79] = fragment[1:26]
            elif frag_idx == 4:
                # Fragment 5: bytes 1-25 (25 bytes) -> positions 79-103
                if len(fragment) >= 26:
                    telegram_data[79:104] = fragment[1:26]
        
        # Create telegram object
        telegram = BTMTelegram(
            sequence_number=sequence_number,
            data=bytes(telegram_data)
        )
        
        # TODO: Parse NID_BG and M_COUNT from telegram data
        # This would require wayside packet decoder implementation
        
        return telegram
    
    def get_last_telegram(self) -> Optional[BTMTelegram]:
        """
        Get the last successfully reassembled telegram.
        
        Returns:
            Last BTMTelegram or None if no telegram has been reassembled
        """
        return self._last_telegram
    
    def reset(self) -> None:
        """Reset decoder state, clearing all slots."""
        self._sequence_slots = [-1] * self.NUM_SLOTS
        self._fragment_status = [
            [0] * self.NUM_FRAGMENTS for _ in range(self.NUM_SLOTS)
        ]
        self._fragment_data = [
            [None] * self.NUM_FRAGMENTS for _ in range(self.NUM_SLOTS)
        ]
        self._last_telegram = None
    
    def get_pending_sequences(self) -> List[Tuple[int, int]]:
        """
        Get list of pending (incomplete) telegram sequences.
        
        Returns:
            List of tuples (sequence_number, fragments_received)
        """
        pending = []
        for i in range(self.NUM_SLOTS):
            if self._sequence_slots[i] != -1:
                fragments_received = sum(self._fragment_status[i])
                pending.append((self._sequence_slots[i], fragments_received))
        return pending
