"""
Tests for speed curve plotter.
"""

import pytest
from datetime import datetime, timedelta
from atp_re.visualization import SpeedCurvePlotter, PlotMode
from atp_re.visualization.sample_data import SampleDataGenerator


class TestSpeedCurvePlotter:
    """Test cases for SpeedCurvePlotter class."""
    
    def test_plotter_initialization(self):
        """Test plotter can be initialized with different modes."""
        plotter_time = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
        assert plotter_time.mode == PlotMode.BY_TIME
        
        plotter_dist = SpeedCurvePlotter(mode=PlotMode.BY_DISTANCE)
        assert plotter_dist.mode == PlotMode.BY_DISTANCE
    
    def test_plot_by_time(self):
        """Test plotting speed curve by time."""
        # Generate sample data
        time = [datetime(2024, 1, 1, 10, 0, i) for i in range(10)]
        speed = [0, 20, 40, 60, 80, 100, 80, 60, 40, 20]
        distance = [i * 100 for i in range(10)]
        
        plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
        fig, ax = plotter.plot(
            time=time,
            distance=distance,
            speed=speed
        )
        
        assert plotter.figure is not None
        assert plotter.axes is not None
        assert ax.get_ylabel() == 'Speed (km/h)'
        
        plotter.close()
    
    def test_plot_by_distance(self):
        """Test plotting speed curve by distance."""
        distance = [i * 100 for i in range(10)]
        speed = [0, 20, 40, 60, 80, 100, 80, 60, 40, 20]
        time = [datetime(2024, 1, 1, 10, 0, i) for i in range(10)]
        
        plotter = SpeedCurvePlotter(mode=PlotMode.BY_DISTANCE)
        fig, ax = plotter.plot(
            time=time,
            distance=distance,
            speed=speed
        )
        
        assert plotter.figure is not None
        assert plotter.axes is not None
        assert 'Distance' in ax.get_xlabel()
        
        plotter.close()
    
    def test_plot_with_target_speed(self):
        """Test plotting with target speed."""
        time = [datetime(2024, 1, 1, 10, 0, i) for i in range(10)]
        speed = [0, 20, 40, 60, 80, 100, 80, 60, 40, 20]
        target_speed = [100] * 10
        distance = [i * 100 for i in range(10)]
        
        plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
        fig, ax = plotter.plot(
            time=time,
            distance=distance,
            speed=speed,
            target_speed=target_speed
        )
        
        # Check that two lines were plotted
        lines = ax.get_lines()
        assert len(lines) >= 2
        
        plotter.close()
    
    def test_plot_with_speed_limit(self):
        """Test plotting with speed limit."""
        time = [datetime(2024, 1, 1, 10, 0, i) for i in range(10)]
        speed = [0, 20, 40, 60, 80, 100, 80, 60, 40, 20]
        speed_limit = [120] * 10
        distance = [i * 100 for i in range(10)]
        
        plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
        fig, ax = plotter.plot(
            time=time,
            distance=distance,
            speed=speed,
            speed_limit=speed_limit
        )
        
        assert plotter.figure is not None
        plotter.close()
    
    def test_plot_with_overspeed_threshold(self):
        """Test plotting with overspeed threshold highlighting."""
        time = [datetime(2024, 1, 1, 10, 0, i) for i in range(10)]
        speed = [50, 60, 70, 110, 120, 130, 100, 80, 60, 40]  # Overspeed in middle
        distance = [i * 100 for i in range(10)]
        
        plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
        fig, ax = plotter.plot(
            time=time,
            distance=distance,
            speed=speed,
            overspeed_threshold=100
        )
        
        assert plotter.figure is not None
        plotter.close()
    
    def test_plot_missing_time_data(self):
        """Test that missing time data raises error in BY_TIME mode."""
        speed = [0, 20, 40, 60, 80, 100]
        distance = [i * 100 for i in range(6)]
        
        plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
        
        with pytest.raises(ValueError, match="time data is required"):
            plotter.plot(distance=distance, speed=speed)
    
    def test_plot_missing_distance_data(self):
        """Test that missing distance data raises error in BY_DISTANCE mode."""
        time = [datetime(2024, 1, 1, 10, 0, i) for i in range(6)]
        speed = [0, 20, 40, 60, 80, 100]
        
        plotter = SpeedCurvePlotter(mode=PlotMode.BY_DISTANCE)
        
        with pytest.raises(ValueError, match="distance data is required"):
            plotter.plot(time=time, speed=speed)
    
    def test_plot_missing_speed_data(self):
        """Test that missing speed data raises error."""
        time = [datetime(2024, 1, 1, 10, 0, i) for i in range(6)]
        distance = [i * 100 for i in range(6)]
        
        plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
        
        with pytest.raises(ValueError, match="speed data is required"):
            plotter.plot(time=time, distance=distance)
    
    def test_plot_data_length_mismatch(self):
        """Test that mismatched data lengths raise error."""
        time = [datetime(2024, 1, 1, 10, 0, i) for i in range(6)]
        speed = [0, 20, 40]  # Wrong length
        distance = [i * 100 for i in range(6)]
        
        plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
        
        with pytest.raises(ValueError, match="same length"):
            plotter.plot(time=time, distance=distance, speed=speed)
    
    def test_plot_from_records(self):
        """Test plotting from record dictionaries."""
        records = [
            {
                'timestamp': datetime(2024, 1, 1, 10, 0, i),
                'position': i * 100,
                'speed': i * 10,
                'target_speed': 100,
                'speed_limit': 120
            }
            for i in range(10)
        ]
        
        plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
        fig, ax = plotter.plot_from_records(records)
        
        assert plotter.figure is not None
        plotter.close()
    
    def test_plot_from_empty_records(self):
        """Test that empty records raise error."""
        plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
        
        with pytest.raises(ValueError, match="empty"):
            plotter.plot_from_records([])
    
    def test_save_plot(self, tmp_path):
        """Test saving plot to file."""
        time = [datetime(2024, 1, 1, 10, 0, i) for i in range(10)]
        speed = [0, 20, 40, 60, 80, 100, 80, 60, 40, 20]
        distance = [i * 100 for i in range(10)]
        
        plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
        plotter.plot(time=time, distance=distance, speed=speed)
        
        output_file = tmp_path / "test_plot.png"
        plotter.save(str(output_file))
        
        assert output_file.exists()
        plotter.close()
    
    def test_save_without_plot(self):
        """Test that saving without plotting raises error."""
        plotter = SpeedCurvePlotter(mode=PlotMode.BY_TIME)
        
        with pytest.raises(ValueError, match="No figure to save"):
            plotter.save("test.png")


class TestSampleDataGenerator:
    """Test cases for SampleDataGenerator class."""
    
    def test_generate_speed_profile(self):
        """Test generating speed profile."""
        records = SampleDataGenerator.generate_speed_profile(
            duration_minutes=5,
            interval_seconds=1.0
        )
        
        assert len(records) == 300  # 5 minutes * 60 seconds
        assert all('timestamp' in r for r in records)
        assert all('position' in r for r in records)
        assert all('speed' in r for r in records)
        assert all('target_speed' in r for r in records)
    
    def test_generate_simple_speed_profile(self):
        """Test generating simple speed profile."""
        records = SampleDataGenerator.generate_simple_speed_profile(
            num_points=100,
            max_speed=100.0
        )
        
        assert len(records) == 100
        assert all('speed' in r for r in records)
        assert max(r['speed'] for r in records) <= 100.0
    
    def test_save_and_load_csv(self, tmp_path):
        """Test saving and loading records from CSV."""
        records = SampleDataGenerator.generate_simple_speed_profile(num_points=10)
        
        csv_file = tmp_path / "test_data.csv"
        SampleDataGenerator.save_to_csv(records, str(csv_file))
        
        assert csv_file.exists()
        
        loaded_records = SampleDataGenerator.load_from_csv(str(csv_file))
        
        assert len(loaded_records) == len(records)
        assert all('speed' in r for r in loaded_records)
