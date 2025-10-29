"""
Record data model.

Represents various types of records in the ATP system including:
- Dynamic data (speed, position, etc.)
- Status data (system state)
- Category data
- Timestamp records
"""

from dataclasses import dataclass, field
from datetime import datetime
from typing import Optional
from enum import Enum


class RecordType(Enum):
    """Types of records in the ATP system."""
    DYNAMIC = "dynamic"
    STATUS = "status"
    CATEGORY = "category"
    TIMESTAMP = "timestamp"
    VDX = "vdx"  # Speed-Distance-X data


@dataclass
class Record:
    """
    Generic record for ATP mission data.
    
    Attributes:
        mission_id: Foreign key to ATPMission
        record_type: Type of record
        timestamp: Time of record
        data: JSON-serializable data for the record
        sequence: Optional sequence number for ordering
    """
    
    mission_id: int
    record_type: RecordType
    timestamp: datetime
    data: dict
    sequence: Optional[int] = None
    
    # Internal ID for database operations
    id: Optional[int] = field(default=None, repr=False)
    
    def __post_init__(self):
        """Validate record data after initialization."""
        if isinstance(self.record_type, str):
            self.record_type = RecordType(self.record_type)
        
        if not isinstance(self.data, dict):
            raise ValueError("data must be a dictionary")


@dataclass
class DynamicRecord(Record):
    """
    Dynamic data record containing real-time train data.
    
    Typical fields in data dict:
        - speed: Current speed
        - position: Current position
        - acceleration: Current acceleration
        - target_speed: Target speed
        - target_distance: Target distance
    """
    
    def __init__(
        self,
        mission_id: int,
        timestamp: datetime,
        speed: Optional[float] = None,
        position: Optional[float] = None,
        acceleration: Optional[float] = None,
        target_speed: Optional[float] = None,
        target_distance: Optional[float] = None,
        **kwargs
    ):
        """Initialize dynamic record with common fields."""
        data = {
            "speed": speed,
            "position": position,
            "acceleration": acceleration,
            "target_speed": target_speed,
            "target_distance": target_distance,
        }
        # Add any additional fields
        data.update(kwargs)
        
        super().__init__(
            mission_id=mission_id,
            record_type=RecordType.DYNAMIC,
            timestamp=timestamp,
            data=data
        )


@dataclass
class StatusRecord(Record):
    """
    Status data record containing system state information.
    
    Typical fields in data dict:
        - atp_mode: ATP operating mode
        - brake_status: Brake system status
        - door_status: Door status
        - system_health: System health indicators
    """
    
    def __init__(
        self,
        mission_id: int,
        timestamp: datetime,
        atp_mode: Optional[str] = None,
        brake_status: Optional[str] = None,
        door_status: Optional[str] = None,
        **kwargs
    ):
        """Initialize status record with common fields."""
        data = {
            "atp_mode": atp_mode,
            "brake_status": brake_status,
            "door_status": door_status,
        }
        # Add any additional fields
        data.update(kwargs)
        
        super().__init__(
            mission_id=mission_id,
            record_type=RecordType.STATUS,
            timestamp=timestamp,
            data=data
        )


@dataclass
class VDXRecord(Record):
    """
    VDX (Velocity-Distance-X) data record.
    
    Typical fields in data dict:
        - velocity: Speed value
        - distance: Distance value
        - x_coordinate: X coordinate or additional parameter
    """
    
    def __init__(
        self,
        mission_id: int,
        timestamp: datetime,
        velocity: Optional[float] = None,
        distance: Optional[float] = None,
        x_coordinate: Optional[float] = None,
        **kwargs
    ):
        """Initialize VDX record with common fields."""
        data = {
            "velocity": velocity,
            "distance": distance,
            "x_coordinate": x_coordinate,
        }
        # Add any additional fields
        data.update(kwargs)
        
        super().__init__(
            mission_id=mission_id,
            record_type=RecordType.VDX,
            timestamp=timestamp,
            data=data
        )
