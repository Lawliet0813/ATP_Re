"""
Speed curve plotter for ATP train driving data.

This module provides functionality to plot speed curves including:
- Current speed
- Target speed
- Speed limits
- Overspeed warnings
"""

from typing import List, Optional, Dict, Any
from datetime import datetime
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches

from .plotter import CurvePlotter, PlotMode


class SpeedCurvePlotter(CurvePlotter):
    """
    Plotter for train speed curves.
    
    Plots speed data with optional target speed, speed limits, and overspeed regions.
    """
    
    def __init__(
        self,
        mode: PlotMode = PlotMode.BY_TIME,
        figsize=(12, 6),
        title: str = "Train Speed Curve"
    ):
        """
        Initialize the speed curve plotter.
        
        Args:
            mode: Plotting mode (BY_TIME or BY_DISTANCE)
            figsize: Figure size as (width, height)
            title: Plot title
        """
        super().__init__(mode, figsize, title)
        
    def plot(
        self,
        time: Optional[List[datetime]] = None,
        distance: Optional[List[float]] = None,
        speed: List[float] = None,
        target_speed: Optional[List[float]] = None,
        speed_limit: Optional[List[float]] = None,
        overspeed_threshold: Optional[float] = None,
        **kwargs
    ):
        """
        Plot speed curve.
        
        Args:
            time: List of timestamps (required if mode is BY_TIME)
            distance: List of distances in meters (required if mode is BY_DISTANCE)
            speed: List of speed values in km/h
            target_speed: Optional list of target speeds in km/h
            speed_limit: Optional list of speed limits in km/h
            overspeed_threshold: Optional threshold for marking overspeed regions
            **kwargs: Additional plotting options
        
        Raises:
            ValueError: If required data is missing
        """
        # Validate inputs
        if speed is None or len(speed) == 0:
            raise ValueError("speed data is required")
        
        if self.mode == PlotMode.BY_TIME:
            if time is None or len(time) == 0:
                raise ValueError("time data is required for BY_TIME mode")
            x_data = time
        else:
            if distance is None or len(distance) == 0:
                raise ValueError("distance data is required for BY_DISTANCE mode")
            x_data = distance
        
        # Validate data lengths
        if len(x_data) != len(speed):
            raise ValueError("x_data and speed must have the same length")
        
        # Create figure and axes
        self.figure, self.axes = self._create_figure()
        ax = self.axes
        
        # Plot speed curve
        ax.plot(x_data, speed, 
                label='Current Speed',
                color='green',
                linewidth=2,
                **kwargs.get('speed_kwargs', {}))
        
        # Plot target speed if provided
        if target_speed is not None:
            if len(target_speed) != len(x_data):
                raise ValueError("target_speed must have the same length as x_data")
            ax.plot(x_data, target_speed,
                    label='Target Speed',
                    color='yellow',
                    linewidth=1.5,
                    linestyle='--',
                    **kwargs.get('target_kwargs', {}))
        
        # Plot speed limit if provided
        if speed_limit is not None:
            if len(speed_limit) != len(x_data):
                raise ValueError("speed_limit must have the same length as x_data")
            ax.plot(x_data, speed_limit,
                    label='Speed Limit',
                    color='orange',
                    linewidth=1.5,
                    linestyle=':',
                    **kwargs.get('limit_kwargs', {}))
        
        # Highlight overspeed regions if threshold is provided
        if overspeed_threshold is not None:
            overspeed_mask = [s > overspeed_threshold for s in speed]
            if any(overspeed_mask):
                # Find continuous overspeed regions
                in_region = False
                start_idx = 0
                
                for i, is_overspeed in enumerate(overspeed_mask):
                    if is_overspeed and not in_region:
                        start_idx = i
                        in_region = True
                    elif not is_overspeed and in_region:
                        ax.axvspan(x_data[start_idx], x_data[i-1],
                                   alpha=0.2, color='red')
                        in_region = False
                
                # Handle case where overspeed extends to end
                if in_region:
                    ax.axvspan(x_data[start_idx], x_data[-1],
                               alpha=0.2, color='red')
        
        # Set y-axis label and limits
        ax.set_ylabel('Speed (km/h)')
        
        # Get y-axis limits
        y_min = kwargs.get('y_min', 0)
        y_max = kwargs.get('y_max', None)
        if y_max is None:
            # Auto-calculate y_max with some margin
            max_speed = max(speed)
            if target_speed:
                max_speed = max(max_speed, max(target_speed))
            if speed_limit:
                max_speed = max(max_speed, max(speed_limit))
            y_max = max_speed * 1.1
        
        ax.set_ylim(y_min, y_max)
        
        # Format time axis if in time mode
        if self.mode == PlotMode.BY_TIME:
            self._format_time_axis(ax)
        
        # Add legend
        ax.legend(loc='best')
        
        # Add title with additional info if provided
        mission_info = kwargs.get('mission_info', '')
        if mission_info:
            ax.set_title(f"{self.title}\n{mission_info}", fontsize=12)
        
        return self.figure, self.axes
    
    def plot_from_records(
        self,
        records: List[Dict[str, Any]],
        **kwargs
    ):
        """
        Plot speed curve from a list of record dictionaries.
        
        Args:
            records: List of record dictionaries containing timestamp, speed, etc.
            **kwargs: Additional plotting options
            
        Expected record format:
            {
                'timestamp': datetime,
                'position': float (meters),
                'speed': float (km/h),
                'target_speed': float (optional),
                'speed_limit': float (optional)
            }
        """
        if not records:
            raise ValueError("records list is empty")
        
        # Extract data from records
        time = [r['timestamp'] for r in records if 'timestamp' in r]
        distance = [r['position'] for r in records if 'position' in r]
        speed = [r['speed'] for r in records if 'speed' in r]
        
        # Optional fields
        target_speed = None
        if all('target_speed' in r for r in records):
            target_speed = [r['target_speed'] for r in records]
        
        speed_limit = None
        if all('speed_limit' in r for r in records):
            speed_limit = [r['speed_limit'] for r in records]
        
        # Plot using extracted data
        return self.plot(
            time=time if time else None,
            distance=distance if distance else None,
            speed=speed,
            target_speed=target_speed,
            speed_limit=speed_limit,
            **kwargs
        )
