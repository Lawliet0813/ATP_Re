"""
Event data model.

Represents events in the ATP system including:
- Button events
- Driver messages
- Failure events
"""

from dataclasses import dataclass, field
from datetime import datetime
from typing import Optional
from enum import Enum


class EventType(Enum):
    """Types of events in the ATP system."""
    BUTTON = "button"
    DRIVER_MESSAGE = "driver_message"
    FAILURE = "failure"
    CABIN_FAILURE = "cabin_failure"
    WAYSIDE_FAILURE = "wayside_failure"


class EventSeverity(Enum):
    """Severity levels for events."""
    INFO = "info"
    WARNING = "warning"
    ERROR = "error"
    CRITICAL = "critical"


@dataclass
class Event:
    """
    Generic event for ATP mission.
    
    Attributes:
        mission_id: Foreign key to ATPMission
        event_type: Type of event
        timestamp: Time of event
        severity: Severity level
        message: Event message or description
        data: Additional event data
    """
    
    mission_id: int
    event_type: EventType
    timestamp: datetime
    severity: EventSeverity
    message: str
    data: Optional[dict] = None
    
    # Internal ID for database operations
    id: Optional[int] = field(default=None, repr=False)
    
    def __post_init__(self):
        """Validate event data after initialization."""
        if isinstance(self.event_type, str):
            self.event_type = EventType(self.event_type)
        
        if isinstance(self.severity, str):
            self.severity = EventSeverity(self.severity)


@dataclass
class ButtonEvent(Event):
    """
    Button press event.
    
    Represents a button pressed by the driver.
    """
    
    def __init__(
        self,
        mission_id: int,
        timestamp: datetime,
        button_name: str,
        button_code: Optional[str] = None,
        **kwargs
    ):
        """Initialize button event."""
        data = {
            "button_name": button_name,
            "button_code": button_code,
        }
        data.update(kwargs)
        
        super().__init__(
            mission_id=mission_id,
            event_type=EventType.BUTTON,
            timestamp=timestamp,
            severity=EventSeverity.INFO,
            message=f"Button pressed: {button_name}",
            data=data
        )


@dataclass
class DriverMessageEvent(Event):
    """
    Driver message event.
    
    Represents a message displayed to or from the driver.
    """
    
    def __init__(
        self,
        mission_id: int,
        timestamp: datetime,
        message_text: str,
        message_type: Optional[str] = None,
        **kwargs
    ):
        """Initialize driver message event."""
        data = {
            "message_text": message_text,
            "message_type": message_type,
        }
        data.update(kwargs)
        
        super().__init__(
            mission_id=mission_id,
            event_type=EventType.DRIVER_MESSAGE,
            timestamp=timestamp,
            severity=EventSeverity.INFO,
            message=message_text,
            data=data
        )


@dataclass
class FailureEvent(Event):
    """
    Failure event.
    
    Represents a system failure or error condition.
    """
    
    def __init__(
        self,
        mission_id: int,
        timestamp: datetime,
        failure_type: str,
        failure_code: Optional[str] = None,
        location: Optional[str] = None,
        is_cabin_failure: bool = False,
        is_wayside_failure: bool = False,
        **kwargs
    ):
        """Initialize failure event."""
        # Determine event type based on failure category
        if is_cabin_failure:
            event_type = EventType.CABIN_FAILURE
        elif is_wayside_failure:
            event_type = EventType.WAYSIDE_FAILURE
        else:
            event_type = EventType.FAILURE
        
        data = {
            "failure_type": failure_type,
            "failure_code": failure_code,
            "location": location,
            "is_cabin_failure": is_cabin_failure,
            "is_wayside_failure": is_wayside_failure,
        }
        data.update(kwargs)
        
        super().__init__(
            mission_id=mission_id,
            event_type=event_type,
            timestamp=timestamp,
            severity=EventSeverity.ERROR,
            message=f"Failure: {failure_type}",
            data=data
        )


@dataclass
class BrakeEvent(Event):
    """
    Brake application event.
    
    Represents emergency or service brake application.
    """
    
    def __init__(
        self,
        mission_id: int,
        timestamp: datetime,
        brake_type: str,  # "EB" (Emergency) or "SB" (Service)
        reason: Optional[str] = None,
        **kwargs
    ):
        """Initialize brake event."""
        data = {
            "brake_type": brake_type,
            "reason": reason,
        }
        data.update(kwargs)
        
        severity = EventSeverity.CRITICAL if brake_type == "EB" else EventSeverity.WARNING
        
        super().__init__(
            mission_id=mission_id,
            event_type=EventType.FAILURE,  # Braking is often related to failures
            timestamp=timestamp,
            severity=severity,
            message=f"{brake_type} brake applied",
            data=data
        )
