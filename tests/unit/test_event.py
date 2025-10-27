"""
Unit tests for Event models.
"""

import pytest
from datetime import datetime
from atp_re.models.event import (
    Event, EventType, EventSeverity,
    ButtonEvent, DriverMessageEvent, FailureEvent, BrakeEvent
)


class TestEvent:
    """Test cases for Event model."""
    
    def test_create_event(self):
        """Test creating a basic event."""
        timestamp = datetime.now()
        event = Event(
            mission_id=1,
            event_type=EventType.BUTTON,
            timestamp=timestamp,
            severity=EventSeverity.INFO,
            message="Test event"
        )
        
        assert event.mission_id == 1
        assert event.event_type == EventType.BUTTON
        assert event.severity == EventSeverity.INFO
        assert event.message == "Test event"
    
    def test_event_type_from_string(self):
        """Test that event type can be created from string."""
        event = Event(
            mission_id=1,
            event_type="button",
            timestamp=datetime.now(),
            severity="info",
            message="Test"
        )
        
        assert event.event_type == EventType.BUTTON
        assert event.severity == EventSeverity.INFO


class TestButtonEvent:
    """Test cases for ButtonEvent model."""
    
    def test_create_button_event(self):
        """Test creating a button event."""
        timestamp = datetime.now()
        event = ButtonEvent(
            mission_id=1,
            timestamp=timestamp,
            button_name="ACKNOWLEDGE",
            button_code="ACK001"
        )
        
        assert event.event_type == EventType.BUTTON
        assert event.severity == EventSeverity.INFO
        assert event.data["button_name"] == "ACKNOWLEDGE"
        assert event.data["button_code"] == "ACK001"
        assert "ACKNOWLEDGE" in event.message


class TestDriverMessageEvent:
    """Test cases for DriverMessageEvent model."""
    
    def test_create_driver_message_event(self):
        """Test creating a driver message event."""
        timestamp = datetime.now()
        event = DriverMessageEvent(
            mission_id=1,
            timestamp=timestamp,
            message_text="Speed limit ahead",
            message_type="WARNING"
        )
        
        assert event.event_type == EventType.DRIVER_MESSAGE
        assert event.message == "Speed limit ahead"
        assert event.data["message_type"] == "WARNING"


class TestFailureEvent:
    """Test cases for FailureEvent model."""
    
    def test_create_failure_event(self):
        """Test creating a failure event."""
        timestamp = datetime.now()
        event = FailureEvent(
            mission_id=1,
            timestamp=timestamp,
            failure_type="SENSOR_FAILURE",
            failure_code="F001",
            location="TRACK_KM_123"
        )
        
        assert event.event_type == EventType.FAILURE
        assert event.severity == EventSeverity.ERROR
        assert event.data["failure_type"] == "SENSOR_FAILURE"
        assert event.data["failure_code"] == "F001"
    
    def test_cabin_failure_event(self):
        """Test creating a cabin failure event."""
        event = FailureEvent(
            mission_id=1,
            timestamp=datetime.now(),
            failure_type="DOOR_SENSOR",
            is_cabin_failure=True
        )
        
        assert event.event_type == EventType.CABIN_FAILURE
        assert event.data["is_cabin_failure"] is True
    
    def test_wayside_failure_event(self):
        """Test creating a wayside failure event."""
        event = FailureEvent(
            mission_id=1,
            timestamp=datetime.now(),
            failure_type="BALISE_ERROR",
            is_wayside_failure=True
        )
        
        assert event.event_type == EventType.WAYSIDE_FAILURE
        assert event.data["is_wayside_failure"] is True


class TestBrakeEvent:
    """Test cases for BrakeEvent model."""
    
    def test_create_eb_event(self):
        """Test creating an emergency brake event."""
        timestamp = datetime.now()
        event = BrakeEvent(
            mission_id=1,
            timestamp=timestamp,
            brake_type="EB",
            reason="Speed limit exceeded"
        )
        
        assert event.severity == EventSeverity.CRITICAL
        assert event.data["brake_type"] == "EB"
        assert "EB brake applied" in event.message
    
    def test_create_sb_event(self):
        """Test creating a service brake event."""
        timestamp = datetime.now()
        event = BrakeEvent(
            mission_id=1,
            timestamp=timestamp,
            brake_type="SB",
            reason="Target speed adjustment"
        )
        
        assert event.severity == EventSeverity.WARNING
        assert event.data["brake_type"] == "SB"
