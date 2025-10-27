package analysis;

import analysis.SpeedAnalyzer.SpeedAnalysisResult;
import analysis.EventDetector.EventDetectionResult;
import analysis.ParkingAccuracyAnalyzer.ParkingAnalysisResult;
import analysis.StatisticalSummary.SummaryResult;
import java.util.Date;
import java.util.Vector;

/**
 * Default implementation of AnalysisAPI
 * Provides concrete implementation of all analysis operations
 */
public class AnalysisAPIImpl implements AnalysisAPI {
    
    private SpeedAnalyzer speedAnalyzer;
    private EventDetector eventDetector;
    private ParkingAccuracyAnalyzer parkingAnalyzer;
    
    public AnalysisAPIImpl() {
        this.speedAnalyzer = new SpeedAnalyzer();
        this.eventDetector = new EventDetector();
        this.parkingAnalyzer = new ParkingAccuracyAnalyzer();
    }
    
    @Override
    public SpeedAnalysisResult analyzeSpeedCurve(Vector<Date> timestamps,
                                                 Vector<Integer> locations,
                                                 Vector<Integer> speeds,
                                                 Vector<Integer> targetSpeeds) {
        speedAnalyzer.clear();
        speedAnalyzer.setSpeedData(timestamps, locations, speeds, targetSpeeds);
        return speedAnalyzer.analyze();
    }
    
    @Override
    public EventDetectionResult detectEvents(Vector<Date> timestamps,
                                            Vector<Integer> locations,
                                            Vector<Integer> speeds,
                                            Vector<Integer> targetSpeeds,
                                            Vector failureData) {
        eventDetector.clear();
        
        // Detect events from speed data
        eventDetector.detectFromSpeedData(timestamps, locations, speeds, targetSpeeds);
        
        // Detect events from failure data
        if (failureData != null && !failureData.isEmpty()) {
            eventDetector.detectFromFailureData(failureData);
        }
        
        return eventDetector.analyze();
    }
    
    @Override
    public ParkingAnalysisResult analyzeParkingAccuracy(Vector<Date> timestamps,
                                                        Vector<Integer> locations,
                                                        Vector<Integer> speeds,
                                                        Vector<String> stationNames,
                                                        Vector<Integer> stationLocations) {
        parkingAnalyzer.clear();
        parkingAnalyzer.detectParkingEvents(timestamps, locations, speeds, 
                                           stationNames, stationLocations);
        return parkingAnalyzer.analyze();
    }
    
    @Override
    public SummaryResult calculateStatistics(Vector<Date> timestamps,
                                            Vector<Integer> locations,
                                            Vector<Integer> speeds) {
        return StatisticalSummary.calculateSummary(timestamps, locations, speeds);
    }
    
    @Override
    public SummaryResult calculateComprehensiveStatistics(Vector<Date> timestamps,
                                                          Vector<Integer> locations,
                                                          Vector<Integer> speeds,
                                                          EventDetectionResult eventResult,
                                                          ParkingAnalysisResult parkingResult) {
        SummaryResult summary = StatisticalSummary.calculateSummary(timestamps, locations, speeds);
        
        // Add event statistics
        if (eventResult != null) {
            summary.totalEvents = eventResult.totalEventCount;
            summary.brakeEventCount = eventResult.brakeEvents.size();
            summary.overspeedEventCount = eventResult.overspeedEvents.size();
            summary.failureEventCount = eventResult.failureEvents.size();
        }
        
        // Add parking statistics
        if (parkingResult != null) {
            summary.totalStops = parkingResult.totalParkings;
            summary.avgParkingAccuracy = parkingResult.avgDeviation;
        }
        
        return summary;
    }
    
    @Override
    public String exportAnalysisReport(SpeedAnalysisResult speedResult,
                                      EventDetectionResult eventResult,
                                      ParkingAnalysisResult parkingResult,
                                      SummaryResult summaryResult) {
        StringBuilder report = new StringBuilder();
        
        report.append("==========================================================\n");
        report.append("           ATP Data Analysis Report\n");
        report.append("==========================================================\n\n");
        
        // Statistical Summary
        if (summaryResult != null) {
            report.append(StatisticalSummary.formatSummary(summaryResult));
            report.append("\n");
        }
        
        // Speed Analysis
        if (speedResult != null) {
            report.append("=== Speed Curve Analysis ===\n");
            report.append(String.format("Maximum Speed: %d km/h\n", speedResult.maxSpeed));
            report.append(String.format("Minimum Speed: %d km/h\n", speedResult.minSpeed));
            report.append(String.format("Average Speed: %.2f km/h\n", speedResult.avgSpeed));
            report.append(String.format("Overspeed Occurrences: %d\n", speedResult.overspeedCount));
            report.append(String.format("Braking Points: %d\n", speedResult.brakingPoints.size()));
            report.append(String.format("Total Distance: %.2f km\n", speedResult.totalDistance / 1000.0));
            report.append(String.format("Total Time: %.2f minutes\n\n", speedResult.totalTime / 60000.0));
        }
        
        // Event Detection
        if (eventResult != null) {
            report.append("=== Event Detection ===\n");
            report.append(String.format("Total Events: %d\n", eventResult.totalEventCount));
            report.append(String.format("Brake Events: %d\n", eventResult.brakeEvents.size()));
            report.append(String.format("Overspeed Events: %d\n", eventResult.overspeedEvents.size()));
            report.append(String.format("Failure Events: %d\n\n", eventResult.failureEvents.size()));
        }
        
        // Parking Accuracy
        if (parkingResult != null) {
            report.append("=== Parking Accuracy Analysis ===\n");
            report.append(String.format("Total Parkings: %d\n", parkingResult.totalParkings));
            report.append(String.format("Accurate Parkings (±50cm): %d\n", parkingResult.accurateParkings));
            report.append(String.format("Acceptable Parkings (±1m): %d\n", parkingResult.acceptableParkings));
            report.append(String.format("Accuracy Rate: %.2f%%\n", parkingResult.accuracyRate));
            report.append(String.format("Average Deviation: %.2f m\n", parkingResult.avgDeviation / 100.0));
            report.append(String.format("Maximum Deviation: %.2f m\n\n", parkingResult.maxDeviation / 100.0));
        }
        
        report.append("==========================================================\n");
        report.append("                  End of Report\n");
        report.append("==========================================================\n");
        
        return report.toString();
    }
    
    /**
     * Get speed analyzer instance
     */
    public SpeedAnalyzer getSpeedAnalyzer() {
        return speedAnalyzer;
    }
    
    /**
     * Get event detector instance
     */
    public EventDetector getEventDetector() {
        return eventDetector;
    }
    
    /**
     * Get parking analyzer instance
     */
    public ParkingAccuracyAnalyzer getParkingAnalyzer() {
        return parkingAnalyzer;
    }
}
