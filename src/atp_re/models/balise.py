"""
Balise data model.

Represents Balise Transmission Module (BTM) data, which provides
track-to-train information through balises (transponders) placed along the track.
"""

from dataclasses import dataclass, field
from datetime import datetime
from typing import Optional, List
from enum import Enum


class BaliseType(Enum):
    """Types of balises in the ATP system."""
    FIXED = "fixed"  # Fixed balise
    SWITCHABLE = "switchable"  # Switchable balise
    TRANSPARENT = "transparent"  # Transparent balise


class TelegramType(Enum):
    """Types of telegrams from wayside equipment."""
    BALISE = "balise"
    LOOP = "loop"
    EUROBALISE = "eurobalise"


@dataclass
class Balise:
    """
    Balise data model representing BTM (Balise Transmission Module) information.
    
    Attributes:
        mission_id: Foreign key to ATPMission
        timestamp: Time when balise was read
        balise_id: Unique identifier for the balise
        balise_type: Type of balise
        position: Position/location of the balise (kilometer or coordinate)
        telegram_data: Raw telegram data from the balise
        telegram_type: Type of telegram
        sequence: Sequence number (for multi-part telegrams)
        is_valid: Whether the balise reading is valid
        error_code: Optional error code if reading failed
    """
    
    mission_id: int
    timestamp: datetime
    balise_id: str
    balise_type: BaliseType
    position: float
    telegram_data: bytes
    telegram_type: TelegramType = TelegramType.BALISE
    sequence: Optional[int] = None
    is_valid: bool = True
    error_code: Optional[str] = None
    
    # Internal ID for database operations
    id: Optional[int] = field(default=None, repr=False)
    
    def __post_init__(self):
        """Validate balise data after initialization."""
        if isinstance(self.balise_type, str):
            self.balise_type = BaliseType(self.balise_type)
        
        if isinstance(self.telegram_type, str):
            self.telegram_type = TelegramType(self.telegram_type)
        
        if not isinstance(self.telegram_data, bytes):
            raise ValueError("telegram_data must be bytes")


@dataclass
class BaliseInfo:
    """
    Decoded balise information.
    
    This class represents parsed/decoded information from a balise telegram,
    typically containing speed limits, gradient information, and other
    track-related data.
    """
    
    balise_id: int
    position: float
    speed_limit: Optional[float] = None
    gradient: Optional[float] = None
    track_condition: Optional[str] = None
    signal_aspect: Optional[str] = None
    distance_to_signal: Optional[float] = None
    
    # Additional wayside information
    temporary_speed_limit: Optional[float] = None
    temporary_speed_limit_distance: Optional[float] = None
    
    def __repr__(self) -> str:
        """String representation of balise info."""
        return (
            f"BaliseInfo(id={self.balise_id}, pos={self.position}, "
            f"speed={self.speed_limit}, gradient={self.gradient})"
        )


@dataclass
class BTMFragment:
    """
    BTM (Balise Transmission Module) data fragment.
    
    BTM data may be split into multiple fragments (typically up to 5)
    that need to be reassembled. This class represents a single fragment.
    """
    
    mission_id: int
    timestamp: datetime
    fragment_number: int  # 1-5 typically
    total_fragments: int
    balise_id: str
    fragment_data: bytes
    is_complete: bool = False
    
    # Internal ID for database operations
    id: Optional[int] = field(default=None, repr=False)
    
    def __post_init__(self):
        """Validate fragment data."""
        if self.fragment_number < 1 or self.fragment_number > self.total_fragments:
            raise ValueError(
                f"fragment_number must be between 1 and {self.total_fragments}"
            )
        
        if not isinstance(self.fragment_data, bytes):
            raise ValueError("fragment_data must be bytes")


class BTMAssembler:
    """
    Assembler for BTM fragments.
    
    Handles the reassembly of BTM data fragments into complete telegrams,
    similar to the Java BTMDecoder functionality.
    """
    
    def __init__(self):
        """Initialize the assembler."""
        self._fragments: dict[str, List[BTMFragment]] = {}
    
    def add_fragment(self, fragment: BTMFragment) -> Optional[bytes]:
        """
        Add a fragment and attempt to assemble complete telegram.
        
        Args:
            fragment: BTM fragment to add
            
        Returns:
            Complete telegram data if all fragments received, None otherwise
        """
        key = f"{fragment.mission_id}_{fragment.balise_id}_{fragment.timestamp}"
        
        if key not in self._fragments:
            self._fragments[key] = []
        
        self._fragments[key].append(fragment)
        
        # Check if we have all fragments
        if len(self._fragments[key]) == fragment.total_fragments:
            # Sort by fragment number and reassemble
            sorted_fragments = sorted(
                self._fragments[key],
                key=lambda f: f.fragment_number
            )
            
            complete_data = b''.join(f.fragment_data for f in sorted_fragments)
            
            # Clean up
            del self._fragments[key]
            
            return complete_data
        
        return None
    
    def clear_old_fragments(self, older_than: datetime) -> None:
        """
        Clear fragments older than specified time.
        
        Args:
            older_than: Timestamp threshold for cleanup
        """
        keys_to_remove = [
            key for key, fragments in self._fragments.items()
            if fragments[0].timestamp < older_than
        ]
        
        for key in keys_to_remove:
            del self._fragments[key]
