"""
Sample data generator for testing ATP visualization.

Generates realistic train driving data for testing plotting functionality.
"""

from datetime import datetime, timedelta
from typing import List, Dict, Any
import math


class SampleDataGenerator:
    """Generate sample ATP train driving data."""
    
    @staticmethod
    def generate_speed_profile(
        duration_minutes: int = 30,
        interval_seconds: float = 1.0,
        max_speed: float = 130.0,
        include_acceleration: bool = True,
        include_braking: bool = True,
        include_overspeed: bool = False
    ) -> List[Dict[str, Any]]:
        """
        Generate a realistic speed profile.
        
        Args:
            duration_minutes: Duration of the profile in minutes
            interval_seconds: Time interval between data points
            max_speed: Maximum speed in km/h
            include_acceleration: Include acceleration phases
            include_braking: Include braking phases
            include_overspeed: Include occasional overspeed events
            
        Returns:
            List of record dictionaries with timestamp, position, speed, etc.
        """
        records = []
        start_time = datetime(2024, 1, 1, 10, 0, 0)
        
        total_seconds = duration_minutes * 60
        num_points = int(total_seconds / interval_seconds)
        
        position = 0.0  # meters
        speed = 0.0  # km/h
        target_speed = 80.0  # km/h
        
        for i in range(num_points):
            # Calculate current time
            current_time = start_time + timedelta(seconds=i * interval_seconds)
            
            # Simulate speed profile with acceleration and braking
            progress = i / num_points
            
            # Create varied speed profile
            if progress < 0.1 and include_acceleration:
                # Acceleration phase (0-10%)
                target_speed = max_speed * progress * 10
                acceleration = 0.5  # m/s²
            elif progress < 0.3:
                # Cruising at high speed (10-30%)
                target_speed = max_speed
                acceleration = 0.0
            elif progress < 0.4 and include_braking:
                # First braking (30-40%)
                target_speed = max_speed * 0.6
                acceleration = -0.8
            elif progress < 0.6:
                # Medium speed cruising (40-60%)
                target_speed = max_speed * 0.6
                acceleration = 0.0
            elif progress < 0.7:
                # Re-acceleration (60-70%)
                target_speed = max_speed * 0.9
                acceleration = 0.4
            elif progress < 0.85:
                # Final cruising (70-85%)
                target_speed = max_speed * 0.9
                acceleration = 0.0
            else:
                # Final braking (85-100%)
                target_speed = max(0, max_speed * 0.9 * (1 - (progress - 0.85) / 0.15))
                acceleration = -1.0
            
            # Update speed with some inertia
            speed_diff = target_speed - speed
            speed += speed_diff * 0.1  # Smooth transition
            
            # Add some noise
            noise = math.sin(i * 0.1) * 2
            speed = max(0, speed + noise)
            
            # Simulate overspeed events
            if include_overspeed and 0.25 < progress < 0.28:
                speed += 10  # Temporary overspeed
            
            # Update position (convert km/h to m/s)
            position += (speed / 3.6) * interval_seconds
            
            # Calculate speed limit (varies along track)
            speed_limit = 130.0
            if 0.35 < progress < 0.45:
                speed_limit = 90.0  # Temporary speed restriction
            elif 0.75 < progress < 0.85:
                speed_limit = 100.0
            
            # Create record
            record = {
                'timestamp': current_time,
                'position': position,
                'speed': speed,
                'target_speed': target_speed,
                'speed_limit': speed_limit,
                'acceleration': acceleration,
            }
            records.append(record)
        
        return records
    
    @staticmethod
    def generate_simple_speed_profile(
        num_points: int = 100,
        max_speed: float = 100.0
    ) -> List[Dict[str, Any]]:
        """
        Generate a simple triangular speed profile (accelerate, cruise, brake).
        
        Args:
            num_points: Number of data points
            max_speed: Maximum speed in km/h
            
        Returns:
            List of record dictionaries
        """
        records = []
        start_time = datetime(2024, 1, 1, 10, 0, 0)
        
        position = 0.0
        
        for i in range(num_points):
            progress = i / num_points
            current_time = start_time + timedelta(seconds=i)
            
            # Simple triangular profile
            if progress < 0.33:
                # Acceleration
                speed = max_speed * (progress / 0.33)
            elif progress < 0.67:
                # Cruising
                speed = max_speed
            else:
                # Braking
                speed = max_speed * (1 - (progress - 0.67) / 0.33)
            
            # Update position
            position += (speed / 3.6)  # Convert km/h to m/s
            
            record = {
                'timestamp': current_time,
                'position': position,
                'speed': speed,
                'target_speed': max_speed if progress < 0.67 else 0,
                'speed_limit': 120.0,
            }
            records.append(record)
        
        return records
    
    @staticmethod
    def save_to_csv(records: List[Dict[str, Any]], filename: str):
        """
        Save records to CSV file.
        
        Args:
            records: List of record dictionaries
            filename: Output CSV filename
        """
        import csv
        
        if not records:
            raise ValueError("No records to save")
        
        with open(filename, 'w', newline='') as f:
            writer = csv.DictWriter(f, fieldnames=records[0].keys())
            writer.writeheader()
            writer.writerows(records)
        
        print(f"Saved {len(records)} records to {filename}")
    
    @staticmethod
    def load_from_csv(filename: str) -> List[Dict[str, Any]]:
        """
        Load records from CSV file.
        
        Args:
            filename: Input CSV filename
            
        Returns:
            List of record dictionaries
        """
        import csv
        from datetime import datetime
        
        records = []
        
        with open(filename, 'r') as f:
            reader = csv.DictReader(f)
            for row in reader:
                # Convert string values to appropriate types
                record = {
                    'timestamp': datetime.fromisoformat(row['timestamp']),
                    'position': float(row['position']),
                    'speed': float(row['speed']),
                    'target_speed': float(row['target_speed']),
                    'speed_limit': float(row['speed_limit']),
                }
                if 'acceleration' in row:
                    record['acceleration'] = float(row['acceleration'])
                records.append(record)
        
        print(f"Loaded {len(records)} records from {filename}")
        return records
