# ATP Data Analysis API Documentation

## Overview
The ATP Data Analysis API provides comprehensive analysis capabilities for Automatic Train Protection (ATP) system data, including speed curve analysis, event detection, parking accuracy analysis, and statistical summaries.

## Package Structure

```
analysis_re/
├── AnalysisAPI.java              - Main API interface
├── AnalysisAPIImpl.java          - Default API implementation
├── SpeedAnalyzer.java            - Speed curve analysis
├── EventDetector.java            - Event detection
├── ParkingAccuracyAnalyzer.java  - Parking accuracy analysis
└── StatisticalSummary.java       - Statistical calculations

drawGraphics_re/
├── SpeedCurveDrawer.java         - Speed curve visualization
└── EventDrawer.java              - Event visualization

ui_re/
└── InteractiveChartPanel.java    - Interactive chart component
```

## Core Components

### 1. SpeedAnalyzer
Analyzes speed curve data to extract patterns, trends, and anomalies.

**Key Features:**
- Maximum, minimum, and average speed calculation
- Overspeed detection
- Braking point detection
- Distance and time calculation

**Usage Example:**
```java
SpeedAnalyzer analyzer = new SpeedAnalyzer();
analyzer.setSpeedData(timestamps, locations, speeds, targetSpeeds);
SpeedAnalysisResult result = analyzer.analyze();

System.out.println("Max Speed: " + result.maxSpeed + " km/h");
System.out.println("Overspeed Count: " + result.overspeedCount);
```

### 2. EventDetector
Detects and classifies ATP system events.

**Event Types:**
- `EVENT_BRAKE` (1) - Braking events
- `EVENT_OVERSPEED` (2) - Overspeed violations
- `EVENT_FAILURE` (3) - System failures
- `EVENT_DRIVER_MESSAGE` (4) - Driver messages
- `EVENT_BALISE` (5) - Balise signals
- `EVENT_STATION_APPROACH` (6) - Station approach
- `EVENT_STATION_DEPARTURE` (7) - Station departure

**Usage Example:**
```java
EventDetector detector = new EventDetector();
detector.detectFromSpeedData(timestamps, locations, speeds, targetSpeeds);
detector.detectFromFailureData(failureData);
EventDetectionResult result = detector.analyze();

System.out.println("Total Events: " + result.totalEventCount);
System.out.println("Brake Events: " + result.brakeEvents.size());
```

### 3. ParkingAccuracyAnalyzer
Analyzes parking precision at stations.

**Accuracy Criteria:**
- Accurate: Within ±50cm of target
- Acceptable: Within ±1m of target

**Usage Example:**
```java
ParkingAccuracyAnalyzer analyzer = new ParkingAccuracyAnalyzer();
analyzer.detectParkingEvents(timestamps, locations, speeds, stationNames, stationLocations);
ParkingAnalysisResult result = analyzer.analyze();

System.out.println("Accuracy Rate: " + result.accuracyRate + "%");
System.out.println("Average Deviation: " + result.avgDeviation + " cm");
```

### 4. StatisticalSummary
Calculates comprehensive statistics for ATP mission data.

**Usage Example:**
```java
SummaryResult summary = StatisticalSummary.calculateSummary(
    timestamps, locations, speeds
);

String report = StatisticalSummary.formatSummary(summary);
System.out.println(report);
```

## Using the Analysis API

### Basic Usage

```java
// Create API instance
AnalysisAPI api = new AnalysisAPIImpl();

// Prepare data
Vector<Date> timestamps = ...;
Vector<Integer> locations = ...;
Vector<Integer> speeds = ...;
Vector<Integer> targetSpeeds = ...;

// Analyze speed curve
SpeedAnalysisResult speedResult = api.analyzeSpeedCurve(
    timestamps, locations, speeds, targetSpeeds
);

// Detect events
EventDetectionResult eventResult = api.detectEvents(
    timestamps, locations, speeds, targetSpeeds, failureData
);

// Analyze parking accuracy
ParkingAnalysisResult parkingResult = api.analyzeParkingAccuracy(
    timestamps, locations, speeds, stationNames, stationLocations
);

// Calculate comprehensive statistics
SummaryResult summary = api.calculateComprehensiveStatistics(
    timestamps, locations, speeds, eventResult, parkingResult
);

// Export report
String report = api.exportAnalysisReport(
    speedResult, eventResult, parkingResult, summary
);
System.out.println(report);
```

## Visualization Components

### SpeedCurveDrawer
Extends `DrawBase` to visualize speed curves with analysis features.

**Features:**
- Speed curve rendering
- Target speed curve (dashed line)
- Overspeed zone highlighting (red zones)
- Interactive legend

**Usage Example:**
```java
SpeedCurveDrawer drawer = new SpeedCurveDrawer(commonParams, speedParams, data);
drawer.setAnalyzer(speedAnalyzer);
drawer.setDataLineColor(Color.GREEN);
drawer.paintBody(graphics);
```

### EventDrawer
Extends `DrawBase` to visualize events on timeline.

**Features:**
- Event markers with type-specific colors
- Vertical event lines
- Rotated event labels
- Interactive legend

**Usage Example:**
```java
EventDrawer drawer = new EventDrawer(commonParams, eventParams, data);
drawer.setDetector(eventDetector);
drawer.setShowEventLabels(true);
drawer.paintBody(graphics);
```

### InteractiveChartPanel
JPanel component with interactive chart features.

**Features:**
- Pan: Click and drag to move the view
- Zoom: Mouse wheel to zoom in/out
- Crosshair: Automatic crosshair at mouse position
- Grid: Optional grid overlay
- Keyboard shortcuts:
  - R - Reset view
  - G - Toggle grid
  - C - Toggle crosshair
  - S - Toggle speed curve
  - E - Toggle events

**Usage Example:**
```java
InteractiveChartPanel panel = new InteractiveChartPanel();
panel.setSpeedAnalyzer(speedAnalyzer);
panel.setEventDetector(eventDetector);

JFrame frame = new JFrame("ATP Data Visualization");
frame.add(panel);
frame.pack();
frame.setVisible(true);
```

## Data Structures

### SpeedPoint
```java
class SpeedPoint {
    Date timestamp;
    int location;      // meters
    int speed;         // km/h
    int targetSpeed;   // km/h
}
```

### Event
```java
class Event {
    int type;
    Date timestamp;
    int location;      // meters
    String description;
    Object data;
}
```

### ParkingRecord
```java
class ParkingRecord {
    String stationName;
    int targetLocation;   // meters
    int actualLocation;   // meters
    int deviation;        // meters
    Date timestamp;
    int finalSpeed;       // km/h
}
```

## Integration with Existing System

### With core_re Package
```java
import com.MiTAC.TRA.ATP.core.ATPMissionDetail;

ATPMissionDetail mission = ...;
// Extract data from mission
Vector<Date> timestamps = mission.getTimestamps();
Vector<Integer> locations = mission.getLocations();
Vector<Integer> speeds = mission.getSpeeds();

// Analyze
AnalysisAPI api = new AnalysisAPIImpl();
SpeedAnalysisResult result = api.analyzeSpeedCurve(
    timestamps, locations, speeds, targetSpeeds
);
```

### With decoder_re Package
```java
import com.MiTAC.TRA.ATP.decoder.DataFeeder;

DataFeeder feeder = ...;
// Get decoded data and analyze
// Implementation depends on DataFeeder structure
```

### With ui_re Package
```java
import com.MiTAC.TRA.ATP.ui.MainWindow;

// Add analysis panel to existing UI
InteractiveChartPanel chartPanel = new InteractiveChartPanel();
mainWindow.addPanel(chartPanel);
```

## Performance Considerations

1. **Data Volume**: Analyzers handle vectors efficiently, but very large datasets (>100,000 points) may require optimization.

2. **Real-time Updates**: For real-time analysis, use incremental update methods:
   ```java
   analyzer.addSpeedPoint(timestamp, location, speed, targetSpeed);
   ```

3. **Memory**: Clear analyzers when switching between missions:
   ```java
   analyzer.clear();
   ```

4. **Visualization**: Interactive chart uses double buffering and optimized rendering.

## Thread Safety

Current implementation is **not thread-safe**. For multi-threaded environments:

```java
AnalysisAPI api = new AnalysisAPIImpl();
synchronized(api) {
    SpeedAnalysisResult result = api.analyzeSpeedCurve(...);
}
```

## Error Handling

All analysis methods handle null and empty data gracefully:

```java
// Returns empty result if data is null or empty
SpeedAnalysisResult result = api.analyzeSpeedCurve(null, null, null, null);
// result.maxSpeed == Integer.MIN_VALUE
// result.overspeedCount == 0
```

## Extension Points

### Custom Event Types
```java
public class CustomEventDetector extends EventDetector {
    public static final int EVENT_CUSTOM = 100;
    
    public void detectCustomEvents(Vector data) {
        // Custom detection logic
        addEvent(EVENT_CUSTOM, timestamp, location, "Custom event");
    }
}
```

### Custom Analyzers
```java
public class TractionAnalyzer {
    public TractionAnalysisResult analyzeTraction(Vector<Integer> tractionData) {
        // Custom analysis logic
    }
}
```

## Testing

Example test case:

```java
@Test
public void testSpeedAnalysis() {
    SpeedAnalyzer analyzer = new SpeedAnalyzer();
    
    Vector<Date> timestamps = new Vector<>();
    Vector<Integer> locations = new Vector<>();
    Vector<Integer> speeds = new Vector<>();
    Vector<Integer> targetSpeeds = new Vector<>();
    
    // Add test data
    timestamps.add(new Date());
    locations.add(0);
    speeds.add(50);
    targetSpeeds.add(60);
    
    analyzer.setSpeedData(timestamps, locations, speeds, targetSpeeds);
    SpeedAnalysisResult result = analyzer.analyze();
    
    assertEquals(50, result.maxSpeed);
    assertEquals(0, result.overspeedCount);
}
```

## Support

For issues or questions:
1. Check existing code in `drawGraphics_re` for visualization patterns
2. Review `core_re/ATPMissionDetail.java` for data structures
3. Examine `decoder_re` for data decoding examples
