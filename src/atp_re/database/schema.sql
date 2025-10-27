-- ATP Re PostgreSQL Database Schema
-- Stage 2: Core Data Models

-- Drop existing tables if they exist (for development)
DROP TABLE IF EXISTS btm_fragments CASCADE;
DROP TABLE IF EXISTS balises CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS records CASCADE;
DROP TABLE IF EXISTS atp_missions CASCADE;
DROP TABLE IF EXISTS stations CASCADE;

-- ============================================================================
-- Stations Table
-- Stores railway station information
-- ============================================================================
CREATE TABLE stations (
    id SERIAL PRIMARY KEY,
    station_id INTEGER NOT NULL UNIQUE,
    name_chinese VARCHAR(100) NOT NULL,
    name_english VARCHAR(100) NOT NULL,
    line VARCHAR(50),
    kilometer DECIMAL(10, 3),
    latitude DECIMAL(10, 7),
    longitude DECIMAL(11, 7),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for stations
CREATE INDEX idx_stations_station_id ON stations(station_id);
CREATE INDEX idx_stations_name_chinese ON stations(name_chinese);
CREATE INDEX idx_stations_name_english ON stations(name_english);

-- ============================================================================
-- ATP Missions Table
-- Stores core mission identification data
-- ============================================================================
CREATE TABLE atp_missions (
    id SERIAL PRIMARY KEY,
    mission_date TIMESTAMP NOT NULL,
    work_shift VARCHAR(50) NOT NULL,
    train_running VARCHAR(50) NOT NULL,
    driver_id VARCHAR(50) NOT NULL,
    vehicle_id VARCHAR(50) NOT NULL,
    file_path TEXT,
    data_source VARCHAR(20) NOT NULL CHECK (data_source IN ('file', 'database')),
    
    -- Statistics (from ATPMissionGeneral)
    mission_start_time TIMESTAMP,
    mission_end_time TIMESTAMP,
    start_position INTEGER,
    end_position INTEGER,
    eb_brake_count INTEGER DEFAULT 0,
    sb_brake_count INTEGER DEFAULT 0,
    cabin_failure_count INTEGER DEFAULT 0,
    wayside_failure_count INTEGER DEFAULT 0,
    
    -- Metadata
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Unique constraint on mission key
    CONSTRAINT unique_mission UNIQUE (mission_date, work_shift, train_running, driver_id, vehicle_id)
);

-- Indexes for ATP missions
CREATE INDEX idx_missions_date ON atp_missions(mission_date);
CREATE INDEX idx_missions_driver ON atp_missions(driver_id);
CREATE INDEX idx_missions_vehicle ON atp_missions(vehicle_id);
CREATE INDEX idx_missions_work_shift ON atp_missions(work_shift);
CREATE INDEX idx_missions_train_running ON atp_missions(train_running);

-- ============================================================================
-- Records Table
-- Stores various types of records (dynamic, status, VDX, etc.)
-- ============================================================================
CREATE TABLE records (
    id SERIAL PRIMARY KEY,
    mission_id INTEGER NOT NULL REFERENCES atp_missions(id) ON DELETE CASCADE,
    record_type VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    sequence INTEGER,
    data JSONB NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_mission FOREIGN KEY (mission_id) REFERENCES atp_missions(id)
);

-- Indexes for records
CREATE INDEX idx_records_mission ON records(mission_id);
CREATE INDEX idx_records_type ON records(record_type);
CREATE INDEX idx_records_timestamp ON records(timestamp);
CREATE INDEX idx_records_mission_timestamp ON records(mission_id, timestamp);
CREATE INDEX idx_records_data_gin ON records USING gin(data);

-- ============================================================================
-- Events Table
-- Stores events (buttons, driver messages, failures, etc.)
-- ============================================================================
CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    mission_id INTEGER NOT NULL REFERENCES atp_missions(id) ON DELETE CASCADE,
    event_type VARCHAR(50) NOT NULL,
    severity VARCHAR(20) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    message TEXT NOT NULL,
    data JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_mission_event FOREIGN KEY (mission_id) REFERENCES atp_missions(id)
);

-- Indexes for events
CREATE INDEX idx_events_mission ON events(mission_id);
CREATE INDEX idx_events_type ON events(event_type);
CREATE INDEX idx_events_severity ON events(severity);
CREATE INDEX idx_events_timestamp ON events(timestamp);
CREATE INDEX idx_events_mission_timestamp ON events(mission_id, timestamp);
CREATE INDEX idx_events_data_gin ON events USING gin(data);

-- ============================================================================
-- Balises Table
-- Stores balise (BTM) data
-- ============================================================================
CREATE TABLE balises (
    id SERIAL PRIMARY KEY,
    mission_id INTEGER NOT NULL REFERENCES atp_missions(id) ON DELETE CASCADE,
    timestamp TIMESTAMP NOT NULL,
    balise_id VARCHAR(100) NOT NULL,
    balise_type VARCHAR(50) NOT NULL,
    telegram_type VARCHAR(50) NOT NULL,
    position DECIMAL(10, 3) NOT NULL,
    telegram_data BYTEA NOT NULL,
    sequence INTEGER,
    is_valid BOOLEAN DEFAULT TRUE,
    error_code VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_mission_balise FOREIGN KEY (mission_id) REFERENCES atp_missions(id)
);

-- Indexes for balises
CREATE INDEX idx_balises_mission ON balises(mission_id);
CREATE INDEX idx_balises_balise_id ON balises(balise_id);
CREATE INDEX idx_balises_timestamp ON balises(timestamp);
CREATE INDEX idx_balises_position ON balises(position);
CREATE INDEX idx_balises_mission_timestamp ON balises(mission_id, timestamp);

-- ============================================================================
-- BTM Fragments Table
-- Stores BTM data fragments that need to be reassembled
-- ============================================================================
CREATE TABLE btm_fragments (
    id SERIAL PRIMARY KEY,
    mission_id INTEGER NOT NULL REFERENCES atp_missions(id) ON DELETE CASCADE,
    timestamp TIMESTAMP NOT NULL,
    balise_id VARCHAR(100) NOT NULL,
    fragment_number INTEGER NOT NULL,
    total_fragments INTEGER NOT NULL,
    fragment_data BYTEA NOT NULL,
    is_complete BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_mission_fragment FOREIGN KEY (mission_id) REFERENCES atp_missions(id),
    CONSTRAINT check_fragment_number CHECK (fragment_number > 0 AND fragment_number <= total_fragments)
);

-- Indexes for BTM fragments
CREATE INDEX idx_fragments_mission ON btm_fragments(mission_id);
CREATE INDEX idx_fragments_balise ON btm_fragments(balise_id);
CREATE INDEX idx_fragments_timestamp ON btm_fragments(timestamp);
CREATE INDEX idx_fragments_assembly ON btm_fragments(mission_id, balise_id, timestamp);

-- ============================================================================
-- Update Timestamp Trigger
-- Automatically update the updated_at column
-- ============================================================================
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Apply trigger to tables with updated_at
CREATE TRIGGER update_stations_updated_at
    BEFORE UPDATE ON stations
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_missions_updated_at
    BEFORE UPDATE ON atp_missions
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- ============================================================================
-- Views for Common Queries
-- ============================================================================

-- View for mission summary
CREATE VIEW mission_summary AS
SELECT 
    m.id,
    m.mission_date,
    m.work_shift,
    m.train_running,
    m.driver_id,
    m.vehicle_id,
    m.mission_start_time,
    m.mission_end_time,
    m.eb_brake_count,
    m.sb_brake_count,
    m.cabin_failure_count,
    m.wayside_failure_count,
    COUNT(DISTINCT r.id) as record_count,
    COUNT(DISTINCT e.id) as event_count,
    COUNT(DISTINCT b.id) as balise_count
FROM atp_missions m
LEFT JOIN records r ON m.id = r.mission_id
LEFT JOIN events e ON m.id = e.mission_id
LEFT JOIN balises b ON m.id = b.mission_id
GROUP BY m.id;

-- View for recent failures
CREATE VIEW recent_failures AS
SELECT 
    e.id,
    e.mission_id,
    m.mission_date,
    m.train_running,
    m.driver_id,
    e.event_type,
    e.severity,
    e.timestamp,
    e.message,
    e.data
FROM events e
JOIN atp_missions m ON e.mission_id = m.id
WHERE e.event_type IN ('failure', 'cabin_failure', 'wayside_failure')
ORDER BY e.timestamp DESC;
