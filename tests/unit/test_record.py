"""
Unit tests for Record models.
"""

import pytest
from datetime import datetime
from atp_re.models.record import (
    Record, RecordType, DynamicRecord, StatusRecord, VDXRecord
)


class TestRecord:
    """Test cases for Record model."""
    
    def test_create_record(self):
        """Test creating a basic record."""
        timestamp = datetime.now()
        record = Record(
            mission_id=1,
            record_type=RecordType.DYNAMIC,
            timestamp=timestamp,
            data={"speed": 100.5}
        )
        
        assert record.mission_id == 1
        assert record.record_type == RecordType.DYNAMIC
        assert record.timestamp == timestamp
        assert record.data == {"speed": 100.5}
    
    def test_record_type_from_string(self):
        """Test that record type can be created from string."""
        record = Record(
            mission_id=1,
            record_type="dynamic",
            timestamp=datetime.now(),
            data={"test": "value"}
        )
        
        assert record.record_type == RecordType.DYNAMIC
    
    def test_invalid_data_type(self):
        """Test that non-dict data raises an error."""
        with pytest.raises(ValueError, match="data must be a dictionary"):
            Record(
                mission_id=1,
                record_type=RecordType.DYNAMIC,
                timestamp=datetime.now(),
                data="invalid"
            )


class TestDynamicRecord:
    """Test cases for DynamicRecord model."""
    
    def test_create_dynamic_record(self):
        """Test creating a dynamic record with common fields."""
        timestamp = datetime.now()
        record = DynamicRecord(
            mission_id=1,
            timestamp=timestamp,
            speed=100.5,
            position=1234.5,
            acceleration=2.3,
            target_speed=120.0
        )
        
        assert record.mission_id == 1
        assert record.record_type == RecordType.DYNAMIC
        assert record.data["speed"] == 100.5
        assert record.data["position"] == 1234.5
        assert record.data["acceleration"] == 2.3
        assert record.data["target_speed"] == 120.0
    
    def test_dynamic_record_with_extra_fields(self):
        """Test that extra fields are included in data."""
        record = DynamicRecord(
            mission_id=1,
            timestamp=datetime.now(),
            speed=100.5,
            custom_field="custom_value"
        )
        
        assert record.data["custom_field"] == "custom_value"


class TestStatusRecord:
    """Test cases for StatusRecord model."""
    
    def test_create_status_record(self):
        """Test creating a status record."""
        timestamp = datetime.now()
        record = StatusRecord(
            mission_id=1,
            timestamp=timestamp,
            atp_mode="FULL",
            brake_status="RELEASED",
            door_status="CLOSED"
        )
        
        assert record.record_type == RecordType.STATUS
        assert record.data["atp_mode"] == "FULL"
        assert record.data["brake_status"] == "RELEASED"


class TestVDXRecord:
    """Test cases for VDXRecord model."""
    
    def test_create_vdx_record(self):
        """Test creating a VDX record."""
        timestamp = datetime.now()
        record = VDXRecord(
            mission_id=1,
            timestamp=timestamp,
            velocity=100.5,
            distance=1234.5,
            x_coordinate=567.8
        )
        
        assert record.record_type == RecordType.VDX
        assert record.data["velocity"] == 100.5
        assert record.data["distance"] == 1234.5
        assert record.data["x_coordinate"] == 567.8
