"""
Integration tests for DatabaseManager.

These tests require a PostgreSQL database to be available.
Run with: pytest tests/integration/ --db-url=postgresql://user:pass@host/dbname
"""

import pytest
import os
from datetime import datetime
from atp_re.database import DatabaseManager
from atp_re.models import ATPMission, Station


# Skip all tests if database URL is not provided
pytestmark = pytest.mark.skipif(
    not os.getenv("TEST_DB_URL"),
    reason="Database URL not provided. Set TEST_DB_URL environment variable."
)


@pytest.fixture(scope="module")
def db():
    """Create database connection for tests."""
    # Parse DB URL or use individual params
    db_url = os.getenv("TEST_DB_URL")
    if not db_url:
        pytest.skip("TEST_DB_URL not set")
    
    # For simplicity, assume format: postgresql://user:pass@host:port/dbname
    # In production, use proper URL parsing
    db = DatabaseManager.from_env()
    
    yield db
    
    db.close()


@pytest.fixture(scope="function")
def clean_db(db):
    """Clean database before each test."""
    # Delete all test data
    with db.get_cursor(dict_cursor=False) as cursor:
        cursor.execute("DELETE FROM btm_fragments")
        cursor.execute("DELETE FROM balises")
        cursor.execute("DELETE FROM events")
        cursor.execute("DELETE FROM records")
        cursor.execute("DELETE FROM atp_missions")
        cursor.execute("DELETE FROM stations WHERE station_id >= 9000")
    
    yield db


class TestDatabaseManager:
    """Test DatabaseManager operations."""
    
    def test_connection_pool(self, db):
        """Test that connection pool works."""
        with db.get_connection() as conn:
            assert conn is not None
            cursor = conn.cursor()
            cursor.execute("SELECT 1")
            result = cursor.fetchone()
            assert result[0] == 1
    
    def test_execute_query(self, db):
        """Test executing a query."""
        result = db.execute_query("SELECT 1 as num", fetch=True)
        assert result[0]["num"] == 1
    
    def test_insert(self, clean_db):
        """Test inserting a row."""
        station_id = clean_db.insert("stations", {
            "station_id": 9001,
            "name_chinese": "測試站",
            "name_english": "Test Station"
        })
        
        assert station_id is not None
        
        # Verify insertion
        result = clean_db.select("stations", where={"id": station_id})
        assert len(result) == 1
        assert result[0]["name_chinese"] == "測試站"
    
    def test_insert_many(self, clean_db):
        """Test inserting multiple rows."""
        stations = [
            {
                "station_id": 9001,
                "name_chinese": "站A",
                "name_english": "Station A"
            },
            {
                "station_id": 9002,
                "name_chinese": "站B",
                "name_english": "Station B"
            }
        ]
        
        count = clean_db.insert_many("stations", stations)
        assert count == 2
        
        # Verify insertions
        result = clean_db.select("stations", where={"station_id": 9001})
        assert len(result) == 1
    
    def test_update(self, clean_db):
        """Test updating a row."""
        # Insert a station
        station_id = clean_db.insert("stations", {
            "station_id": 9001,
            "name_chinese": "舊名",
            "name_english": "Old Name"
        })
        
        # Update it
        count = clean_db.update(
            "stations",
            {"name_chinese": "新名"},
            {"id": station_id}
        )
        assert count == 1
        
        # Verify update
        result = clean_db.select("stations", where={"id": station_id})
        assert result[0]["name_chinese"] == "新名"
    
    def test_delete(self, clean_db):
        """Test deleting a row."""
        # Insert a station
        station_id = clean_db.insert("stations", {
            "station_id": 9001,
            "name_chinese": "測試",
            "name_english": "Test"
        })
        
        # Delete it
        count = clean_db.delete("stations", {"id": station_id})
        assert count == 1
        
        # Verify deletion
        result = clean_db.select("stations", where={"id": station_id})
        assert len(result) == 0
    
    def test_select_with_options(self, clean_db):
        """Test select with various options."""
        # Insert test data
        stations = [
            {
                "station_id": 9001 + i,
                "name_chinese": f"站{i}",
                "name_english": f"Station {i}"
            }
            for i in range(5)
        ]
        clean_db.insert_many("stations", stations)
        
        # Test limit
        result = clean_db.select("stations", limit=2)
        assert len(result) <= 2
        
        # Test offset
        result = clean_db.select("stations", limit=1, offset=1)
        assert len(result) == 1
        
        # Test order by
        result = clean_db.select(
            "stations",
            where={"station_id": 9001},
            order_by=["station_id"]
        )
        assert len(result) >= 1


class TestATPMissionDatabase:
    """Test ATPMission database operations."""
    
    def test_insert_mission(self, clean_db):
        """Test inserting an ATP mission."""
        mission = ATPMission.from_database(
            mission_date=datetime(2024, 1, 15, 10, 30),
            work_shift="A001",
            train_running="T123",
            driver_id="D456",
            vehicle_id="V789"
        )
        
        mission_id = clean_db.insert("atp_missions", {
            "mission_date": mission.mission_date,
            "work_shift": mission.work_shift,
            "train_running": mission.train_running,
            "driver_id": mission.driver_id,
            "vehicle_id": mission.vehicle_id,
            "data_source": mission.data_source
        })
        
        assert mission_id is not None
        
        # Verify insertion
        result = clean_db.select("atp_missions", where={"id": mission_id})
        assert len(result) == 1
        assert result[0]["work_shift"] == "A001"
    
    def test_unique_mission_constraint(self, clean_db):
        """Test that duplicate missions are rejected."""
        mission_data = {
            "mission_date": datetime(2024, 1, 15, 10, 30),
            "work_shift": "A001",
            "train_running": "T123",
            "driver_id": "D456",
            "vehicle_id": "V789",
            "data_source": "database"
        }
        
        # Insert first mission
        clean_db.insert("atp_missions", mission_data)
        
        # Try to insert duplicate - should fail
        with pytest.raises(Exception):  # Will raise IntegrityError
            clean_db.insert("atp_missions", mission_data)


class TestStationCache:
    """Test Station model with database."""
    
    def test_load_stations_from_db(self, clean_db):
        """Test loading stations from database into cache."""
        # Insert test stations
        stations_data = [
            {
                "station_id": 9001,
                "name_chinese": "台北",
                "name_english": "Taipei"
            },
            {
                "station_id": 9002,
                "name_chinese": "台中",
                "name_english": "Taichung"
            }
        ]
        clean_db.insert_many("stations", stations_data)
        
        # Load from database
        results = clean_db.select("stations", where={"station_id": 9001})
        
        # Create Station objects and cache them
        stations = [
            Station(
                station_id=r["station_id"],
                name_chinese=r["name_chinese"],
                name_english=r["name_english"]
            )
            for r in results
        ]
        
        Station.initialize_cache(stations)
        
        # Test cache
        station = Station.get_by_id(9001)
        assert station is not None
        assert station.name_chinese == "台北"
