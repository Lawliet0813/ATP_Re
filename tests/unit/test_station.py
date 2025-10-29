"""
Unit tests for Station model.
"""

import pytest
from atp_re.models.station import Station


class TestStation:
    """Test cases for Station model."""
    
    def test_create_station(self):
        """Test creating a station."""
        station = Station(
            station_id=1001,
            name_chinese="台北車站",
            name_english="Taipei Station",
            line="Main Line",
            kilometer=0.0
        )
        
        assert station.station_id == 1001
        assert station.name_chinese == "台北車站"
        assert station.name_english == "Taipei Station"
        assert station.line == "Main Line"
        assert station.kilometer == 0.0
    
    def test_station_requires_names(self):
        """Test that station requires both Chinese and English names."""
        with pytest.raises(ValueError, match="name_chinese cannot be empty"):
            Station(
                station_id=1001,
                name_chinese="",
                name_english="Test"
            )
        
        with pytest.raises(ValueError, match="name_english cannot be empty"):
            Station(
                station_id=1001,
                name_chinese="測試",
                name_english=""
            )
    
    def test_initialize_cache(self):
        """Test initializing the station cache."""
        stations = [
            Station(station_id=1, name_chinese="站A", name_english="Station A"),
            Station(station_id=2, name_chinese="站B", name_english="Station B"),
            Station(station_id=3, name_chinese="站C", name_english="Station C"),
        ]
        
        Station.initialize_cache(stations)
        
        cached = Station.get_all_cached()
        assert len(cached) == 3
        assert cached[0].station_id == 1
    
    def test_get_by_id(self):
        """Test getting a station by ID from cache."""
        stations = [
            Station(station_id=1, name_chinese="站A", name_english="Station A"),
            Station(station_id=2, name_chinese="站B", name_english="Station B"),
        ]
        
        Station.initialize_cache(stations)
        
        station = Station.get_by_id(1)
        assert station is not None
        assert station.name_chinese == "站A"
        
        station = Station.get_by_id(999)
        assert station is None
    
    def test_get_chinese_name(self):
        """Test getting Chinese name by station ID."""
        stations = [
            Station(station_id=1, name_chinese="台北", name_english="Taipei"),
        ]
        
        Station.initialize_cache(stations)
        
        name = Station.get_chinese_name(1)
        assert name == "台北"
        
        name = Station.get_chinese_name(999)
        assert name == "未知"
        
        name = Station.get_chinese_name(999, default="不明")
        assert name == "不明"
    
    def test_get_english_name(self):
        """Test getting English name by station ID."""
        stations = [
            Station(station_id=1, name_chinese="台北", name_english="Taipei"),
        ]
        
        Station.initialize_cache(stations)
        
        name = Station.get_english_name(1)
        assert name == "Taipei"
        
        name = Station.get_english_name(999)
        assert name == "unknown"
    
    def test_search_by_name_chinese(self):
        """Test searching stations by Chinese name."""
        stations = [
            Station(station_id=1, name_chinese="台北車站", name_english="Taipei"),
            Station(station_id=2, name_chinese="台中車站", name_english="Taichung"),
            Station(station_id=3, name_chinese="高雄車站", name_english="Kaohsiung"),
        ]
        
        Station.initialize_cache(stations)
        
        results = Station.search_by_name("台", language="chinese")
        assert len(results) == 2
        
        results = Station.search_by_name("高雄", language="chinese")
        assert len(results) == 1
        assert results[0].station_id == 3
    
    def test_search_by_name_english(self):
        """Test searching stations by English name."""
        stations = [
            Station(station_id=1, name_chinese="台北", name_english="Taipei"),
            Station(station_id=2, name_chinese="台中", name_english="Taichung"),
        ]
        
        Station.initialize_cache(stations)
        
        results = Station.search_by_name("tai", language="english")
        assert len(results) == 2
        
        results = Station.search_by_name("taipei", language="english")
        assert len(results) == 1
    
    def test_clear_cache(self):
        """Test clearing the station cache."""
        stations = [
            Station(station_id=1, name_chinese="站A", name_english="Station A"),
        ]
        
        Station.initialize_cache(stations)
        assert len(Station.get_all_cached()) == 1
        
        Station.clear_cache()
        assert len(Station.get_all_cached()) == 0
