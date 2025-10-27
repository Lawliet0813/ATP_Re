"""
Station data model.

Represents railway station information with support for both
Chinese and English names, and efficient lookup capabilities.
"""

from dataclasses import dataclass, field
from typing import Optional, List, ClassVar
import bisect


@dataclass
class Station:
    """
    Railway station data model.
    
    Attributes:
        station_id: Unique station identifier (inner ID)
        name_chinese: Chinese name of the station
        name_english: English name of the station
        line: Optional railway line identifier
        kilometer: Optional kilometer position
        latitude: Optional latitude coordinate
        longitude: Optional longitude coordinate
    """
    
    station_id: int
    name_chinese: str
    name_english: str
    line: Optional[str] = None
    kilometer: Optional[float] = None
    latitude: Optional[float] = None
    longitude: Optional[float] = None
    
    # Internal ID for database operations
    id: Optional[int] = field(default=None, repr=False)
    
    # Class-level cache for fast lookups (similar to Java static fields)
    _cache: ClassVar[Optional[List["Station"]]] = None
    _id_to_station: ClassVar[Optional[dict]] = None
    
    def __post_init__(self):
        """Validate station data after initialization."""
        if not self.name_chinese:
            raise ValueError("name_chinese cannot be empty")
        if not self.name_english:
            raise ValueError("name_english cannot be empty")
    
    @classmethod
    def initialize_cache(cls, stations: List["Station"]) -> None:
        """
        Initialize the station cache for fast lookups.
        
        This method should be called once at startup with all stations
        from the database. It implements a similar caching mechanism to
        the original Java implementation.
        
        Args:
            stations: List of all stations, sorted by station_id
        """
        # Sort by station_id for binary search
        cls._cache = sorted(stations, key=lambda s: s.station_id)
        cls._id_to_station = {s.station_id: s for s in stations}
    
    @classmethod
    def get_by_id(cls, station_id: int) -> Optional["Station"]:
        """
        Get a station by its ID using cached data.
        
        Uses binary search for O(log n) lookup performance,
        similar to the Java implementation.
        
        Args:
            station_id: Station identifier
            
        Returns:
            Station object if found, None otherwise
        """
        if cls._id_to_station is None:
            return None
        
        return cls._id_to_station.get(station_id)
    
    @classmethod
    def get_chinese_name(cls, station_id: int, default: str = "未知") -> str:
        """
        Get the Chinese name of a station by ID.
        
        Args:
            station_id: Station identifier
            default: Default value if station not found
            
        Returns:
            Chinese name of the station or default value
        """
        station = cls.get_by_id(station_id)
        return station.name_chinese if station else default
    
    @classmethod
    def get_english_name(cls, station_id: int, default: str = "unknown") -> str:
        """
        Get the English name of a station by ID.
        
        Args:
            station_id: Station identifier
            default: Default value if station not found
            
        Returns:
            English name of the station or default value
        """
        station = cls.get_by_id(station_id)
        return station.name_english if station else default
    
    @classmethod
    def search_by_name(cls, name: str, language: str = "chinese") -> List["Station"]:
        """
        Search for stations by name (partial match).
        
        Args:
            name: Name to search for (case-insensitive partial match)
            language: Language to search in ("chinese" or "english")
            
        Returns:
            List of matching stations
        """
        if cls._cache is None:
            return []
        
        name_lower = name.lower()
        results = []
        
        for station in cls._cache:
            if language == "chinese":
                if name in station.name_chinese:
                    results.append(station)
            elif language == "english":
                if name_lower in station.name_english.lower():
                    results.append(station)
        
        return results
    
    @classmethod
    def get_all_cached(cls) -> List["Station"]:
        """
        Get all cached stations.
        
        Returns:
            List of all cached stations
        """
        return cls._cache if cls._cache is not None else []
    
    @classmethod
    def clear_cache(cls) -> None:
        """Clear the station cache."""
        cls._cache = None
        cls._id_to_station = None
