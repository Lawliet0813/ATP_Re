#!/usr/bin/env python3
"""
Example script demonstrating ATP train driving curve plotting.

This script generates sample data and creates various plots to demonstrate
the visualization capabilities of the ATP system.
"""

import os
from datetime import datetime
from atp_re.visualization import SpeedCurvePlotter, PlotMode
from atp_re.visualization.sample_data import SampleDataGenerator


def main():
    """Generate sample data and create example plots."""
    
    # Create output directory
    output_dir = "output/plots"
    os.makedirs(output_dir, exist_ok=True)
    
    print("=" * 70)
    print("ATP Train Driving Curve Plotting - Example Demonstration")
    print("=" * 70)
    
    # Example 1: Simple speed profile (time-based)
    print("\n1. Generating simple speed profile...")
    simple_records = SampleDataGenerator.generate_simple_speed_profile(
        num_points=100,
        max_speed=100.0
    )
    
    plotter = SpeedCurvePlotter(
        mode=PlotMode.BY_TIME,
        title="Simple Speed Profile (Time-Based)"
    )
    plotter.plot_from_records(simple_records)
    output_file = os.path.join(output_dir, "simple_speed_time.png")
    plotter.save(output_file)
    plotter.close()
    print(f"   ✓ Saved to {output_file}")
    
    # Example 2: Simple speed profile (distance-based)
    print("\n2. Generating simple speed profile (distance-based)...")
    plotter = SpeedCurvePlotter(
        mode=PlotMode.BY_DISTANCE,
        title="Simple Speed Profile (Distance-Based)"
    )
    plotter.plot_from_records(simple_records)
    output_file = os.path.join(output_dir, "simple_speed_distance.png")
    plotter.save(output_file)
    plotter.close()
    print(f"   ✓ Saved to {output_file}")
    
    # Example 3: Realistic speed profile with all features
    print("\n3. Generating realistic speed profile with acceleration and braking...")
    realistic_records = SampleDataGenerator.generate_speed_profile(
        duration_minutes=30,
        interval_seconds=1.0,
        max_speed=130.0,
        include_acceleration=True,
        include_braking=True,
        include_overspeed=False
    )
    
    plotter = SpeedCurvePlotter(
        mode=PlotMode.BY_TIME,
        title="Realistic Train Speed Profile"
    )
    plotter.plot_from_records(
        realistic_records,
        mission_info="Train: T123 | Driver: D456 | Date: 2024-01-01"
    )
    output_file = os.path.join(output_dir, "realistic_speed_profile.png")
    plotter.save(output_file)
    plotter.close()
    print(f"   ✓ Saved to {output_file}")
    
    # Example 4: Speed profile with overspeed events
    print("\n4. Generating speed profile with overspeed events...")
    overspeed_records = SampleDataGenerator.generate_speed_profile(
        duration_minutes=20,
        interval_seconds=1.0,
        max_speed=120.0,
        include_acceleration=True,
        include_braking=True,
        include_overspeed=True
    )
    
    plotter = SpeedCurvePlotter(
        mode=PlotMode.BY_TIME,
        title="Speed Profile with Overspeed Detection"
    )
    plotter.plot_from_records(
        overspeed_records,
        overspeed_threshold=130.0,
        mission_info="Overspeed events highlighted in red"
    )
    output_file = os.path.join(output_dir, "overspeed_profile.png")
    plotter.save(output_file)
    plotter.close()
    print(f"   ✓ Saved to {output_file}")
    
    # Example 5: Save data to CSV for later use
    print("\n5. Saving sample data to CSV...")
    csv_file = os.path.join(output_dir, "sample_driving_data.csv")
    SampleDataGenerator.save_to_csv(realistic_records, csv_file)
    print(f"   ✓ Saved to {csv_file}")
    
    # Example 6: Load data from CSV and plot
    print("\n6. Loading data from CSV and plotting...")
    loaded_records = SampleDataGenerator.load_from_csv(csv_file)
    
    plotter = SpeedCurvePlotter(
        mode=PlotMode.BY_DISTANCE,
        title="Speed Profile from CSV Data"
    )
    plotter.plot_from_records(loaded_records)
    output_file = os.path.join(output_dir, "csv_loaded_profile.png")
    plotter.save(output_file)
    plotter.close()
    print(f"   ✓ Saved to {output_file}")
    
    # Summary
    print("\n" + "=" * 70)
    print("Summary:")
    print(f"  • Generated {len(simple_records)} data points for simple profile")
    print(f"  • Generated {len(realistic_records)} data points for realistic profile")
    print(f"  • Created 5 visualization plots")
    print(f"  • Saved sample data to CSV")
    print(f"  • All outputs saved to: {output_dir}/")
    print("=" * 70)
    print("\n✓ Example demonstration completed successfully!")
    print(f"\nView the generated plots in the '{output_dir}' directory.")


if __name__ == "__main__":
    main()
