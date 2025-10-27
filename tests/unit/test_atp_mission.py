"""
Unit tests for ATPMission model.
"""

import pytest
from datetime import datetime
from pathlib import Path
from atp_re.models.atp_mission import ATPMission


class TestATPMission:
    """Test cases for ATPMission model."""
    
    def test_create_from_database(self):
        """Test creating an ATPMission from database parameters."""
        mission_date = datetime(2024, 1, 15, 10, 30)
        mission = ATPMission.from_database(
            mission_date=mission_date,
            work_shift="A001",
            train_running="T123",
            driver_id="D456",
            vehicle_id="V789",
            mission_id=1
        )
        
        assert mission.mission_date == mission_date
        assert mission.work_shift == "A001"
        assert mission.train_running == "T123"
        assert mission.driver_id == "D456"
        assert mission.vehicle_id == "V789"
        assert mission.data_source == "database"
        assert mission.id == 1
    
    def test_create_from_file(self):
        """Test creating an ATPMission from a file path."""
        file_path = Path("/tmp/test_mission.dat")
        mission = ATPMission.from_file(file_path)
        
        assert mission.file_path == file_path
        assert mission.data_source == "file"
    
    def test_mission_key(self):
        """Test getting a unique mission key."""
        mission_date = datetime(2024, 1, 15, 10, 30)
        mission = ATPMission(
            mission_date=mission_date,
            work_shift="A001",
            train_running="T123",
            driver_id="D456",
            vehicle_id="V789"
        )
        
        key = mission.get_mission_key()
        
        assert key == (mission_date, "A001", "T123", "D456", "V789")
    
    def test_invalid_data_source(self):
        """Test that invalid data source raises an error."""
        with pytest.raises(ValueError, match="data_source must be"):
            ATPMission(
                mission_date=datetime.now(),
                work_shift="A001",
                train_running="T123",
                driver_id="D456",
                vehicle_id="V789",
                data_source="invalid"
            )
    
    def test_string_path_conversion(self):
        """Test that string paths are converted to Path objects."""
        mission = ATPMission(
            mission_date=datetime.now(),
            work_shift="A001",
            train_running="T123",
            driver_id="D456",
            vehicle_id="V789",
            file_path="/tmp/test.dat"
        )
        
        assert isinstance(mission.file_path, Path)
        assert mission.file_path == Path("/tmp/test.dat")
