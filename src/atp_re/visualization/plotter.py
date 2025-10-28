"""
Base plotter for ATP train driving curves.

This module provides the base functionality for plotting train driving curves
with support for both time-based and distance-based plotting modes.
"""

from enum import Enum
from typing import List, Optional, Tuple
from datetime import datetime
import matplotlib.pyplot as plt
import matplotlib.dates as mdates
from matplotlib.figure import Figure
from matplotlib.axes import Axes


class PlotMode(Enum):
    """Plotting mode enumeration."""
    BY_TIME = "time"
    BY_DISTANCE = "distance"


class CurvePlotter:
    """
    Base class for plotting ATP train driving curves.
    
    Supports two plotting modes:
    - BY_TIME: X-axis represents time
    - BY_DISTANCE: X-axis represents distance
    
    Attributes:
        mode: Plotting mode (BY_TIME or BY_DISTANCE)
        figure: Matplotlib figure object
        axes: Matplotlib axes object
    """
    
    def __init__(
        self,
        mode: PlotMode = PlotMode.BY_TIME,
        figsize: Tuple[int, int] = (12, 6),
        title: str = "ATP Train Driving Curve"
    ):
        """
        Initialize the curve plotter.
        
        Args:
            mode: Plotting mode (BY_TIME or BY_DISTANCE)
            figsize: Figure size as (width, height)
            title: Plot title
        """
        self.mode = mode
        self.figsize = figsize
        self.title = title
        self.figure: Optional[Figure] = None
        self.axes: Optional[Axes] = None
        
    def _create_figure(self) -> Tuple[Figure, Axes]:
        """
        Create a new figure and axes.
        
        Returns:
            Tuple of (figure, axes)
        """
        fig, ax = plt.subplots(figsize=self.figsize)
        ax.set_title(self.title)
        ax.grid(True, alpha=0.3)
        
        # Set x-axis label based on mode
        if self.mode == PlotMode.BY_TIME:
            ax.set_xlabel('Time')
        else:
            ax.set_xlabel('Distance (m)')
            
        return fig, ax
    
    def _format_time_axis(self, ax: Axes):
        """
        Format the x-axis for time-based plots.
        
        Args:
            ax: Matplotlib axes object
        """
        ax.xaxis.set_major_formatter(mdates.DateFormatter('%H:%M:%S'))
        ax.xaxis.set_major_locator(mdates.AutoDateLocator())
        self.figure.autofmt_xdate()
        
    def plot(self, *args, **kwargs):
        """
        Plot the curve. To be implemented by subclasses.
        
        Raises:
            NotImplementedError: Must be implemented by subclasses
        """
        raise NotImplementedError("Subclasses must implement plot() method")
    
    def save(self, filename: str, dpi: int = 300):
        """
        Save the plot to a file.
        
        Args:
            filename: Output filename (supports .png, .jpg, .pdf, .svg)
            dpi: Resolution in dots per inch
        """
        if self.figure is None:
            raise ValueError("No figure to save. Call plot() first.")
        
        self.figure.savefig(filename, dpi=dpi, bbox_inches='tight')
        print(f"Plot saved to {filename}")
    
    def show(self):
        """Display the plot."""
        if self.figure is None:
            raise ValueError("No figure to show. Call plot() first.")
        
        plt.show()
    
    def close(self):
        """Close the figure and free resources."""
        if self.figure is not None:
            plt.close(self.figure)
            self.figure = None
            self.axes = None
