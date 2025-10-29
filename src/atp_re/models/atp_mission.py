"""
ATPMission data model.

Represents an ATP mission with basic identification information.
Based on the original Java ATPMission class.
"""

from dataclasses import dataclass, field
from datetime import datetime
from typing import Optional
from pathlib import Path


@dataclass
class ATPMission:
    """
    ATP Mission base class containing core mission identification data.
    
    Attributes:
        mission_date: Date of the mission
        work_shift: Work shift identifier
        train_running: Train running number
        driver_id: Driver identification
        vehicle_id: Vehicle identification
        file_path: Optional file path for file-based missions
        data_source: Source of data (file or database)
    """
    
    mission_date: datetime
    work_shift: str
    train_running: str
    driver_id: str
    vehicle_id: str
    file_path: Optional[Path] = None
    data_source: str = "database"  # "file" or "database"
    
    # Internal ID for database operations
    id: Optional[int] = field(default=None, repr=False)
    
    def __post_init__(self):
        """Validate mission data after initialization."""
        if self.data_source not in ["file", "database"]:
            raise ValueError("data_source must be 'file' or 'database'")
        
        # Convert string path to Path object if needed
        if isinstance(self.file_path, str):
            self.file_path = Path(self.file_path)
    
    @classmethod
    def from_file(cls, file_path: Path) -> "ATPMission":
        """
        Create an ATPMission from a file path.
        
        The file path is expected to follow a specific structure that can be
        parsed to extract mission information.
        
        Args:
            file_path: Path to the mission data file
            
        Returns:
            ATPMission instance
        """
        # Parse file path to extract mission info
        # This would need to be implemented based on actual file structure
        # For now, creating a placeholder
        return cls(
            mission_date=datetime.now(),
            work_shift="notInit",
            train_running="notInit",
            driver_id="notInit",
            vehicle_id="notInit",
            file_path=file_path,
            data_source="file"
        )
    
    @classmethod
    def from_database(
        cls,
        mission_date: datetime,
        work_shift: str,
        train_running: str,
        driver_id: str,
        vehicle_id: str,
        mission_id: Optional[int] = None
    ) -> "ATPMission":
        """
        Create an ATPMission from database parameters.
        
        Args:
            mission_date: Date of the mission
            work_shift: Work shift identifier
            train_running: Train running number
            driver_id: Driver identification
            vehicle_id: Vehicle identification
            mission_id: Optional database ID
            
        Returns:
            ATPMission instance
        """
        return cls(
            mission_date=mission_date,
            work_shift=work_shift,
            train_running=train_running,
            driver_id=driver_id,
            vehicle_id=vehicle_id,
            data_source="database",
            id=mission_id
        )
    
    def get_mission_key(self) -> tuple:
        """
        Get a unique key tuple for this mission.
        
        Returns:
            Tuple of (mission_date, work_shift, train_running, driver_id, vehicle_id)
        """
        return (
            self.mission_date,
            self.work_shift,
            self.train_running,
            self.driver_id,
            self.vehicle_id
        )
