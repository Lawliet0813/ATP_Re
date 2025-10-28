"""
Visualization module for ATP train driving data.

This module provides functionality to plot various train driving curves including:
- Speed vs Time
- Speed vs Distance
- Acceleration curves
- Target speed curves
"""

from .plotter import CurvePlotter, PlotMode
from .speed_plotter import SpeedCurvePlotter
from .sample_data import SampleDataGenerator

__all__ = [
    'CurvePlotter',
    'PlotMode',
    'SpeedCurvePlotter',
    'SampleDataGenerator',
]
