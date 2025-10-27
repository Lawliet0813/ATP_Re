package analysis;

import analysis.*;
import java.util.Date;
import java.util.Vector;

/**
 * Simple test program for analysis module
 */
public class AnalysisTest {
    
    public static void main(String[] args) {
        System.out.println("=== ATP Analysis Module Test ===\n");
        
        // Test Speed Analyzer
        testSpeedAnalyzer();
        
        // Test Event Detector
        testEventDetector();
        
        // Test Parking Analyzer
        testParkingAnalyzer();
        
        // Test Statistical Summary
        testStatisticalSummary();
        
        // Test API
        testAPI();
        
        System.out.println("\n=== All Tests Completed ===");
    }
    
    private static void testSpeedAnalyzer() {
        System.out.println("--- Testing SpeedAnalyzer ---");
        
        SpeedAnalyzer analyzer = new SpeedAnalyzer();
        
        // Create test data
        Vector<Date> timestamps = new Vector<>();
        Vector<Integer> locations = new Vector<>();
        Vector<Integer> speeds = new Vector<>();
        Vector<Integer> targetSpeeds = new Vector<>();
        
        long baseTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            timestamps.add(new Date(baseTime + i * 1000));
            locations.add(i * 100);
            speeds.add(i * 10 + (i % 2 == 0 ? 5 : 0));
            targetSpeeds.add(50);
        }
        
        analyzer.setSpeedData(timestamps, locations, speeds, targetSpeeds);
        SpeedAnalyzer.SpeedAnalysisResult result = analyzer.analyze();
        
        System.out.println("Max Speed: " + result.maxSpeed + " km/h");
        System.out.println("Min Speed: " + result.minSpeed + " km/h");
        System.out.println("Avg Speed: " + String.format("%.2f", result.avgSpeed) + " km/h");
        System.out.println("Overspeed Count: " + result.overspeedCount);
        System.out.println("Braking Points: " + result.brakingPoints.size());
        System.out.println();
    }
    
    private static void testEventDetector() {
        System.out.println("--- Testing EventDetector ---");
        
        EventDetector detector = new EventDetector();
        
        // Create test data
        Vector<Date> timestamps = new Vector<>();
        Vector<Integer> locations = new Vector<>();
        Vector<Integer> speeds = new Vector<>();
        Vector<Integer> targetSpeeds = new Vector<>();
        
        long baseTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            timestamps.add(new Date(baseTime + i * 1000));
            locations.add(i * 100);
            speeds.add(i < 5 ? i * 15 : (10 - i) * 15);
            targetSpeeds.add(50);
        }
        
        detector.detectFromSpeedData(timestamps, locations, speeds, targetSpeeds);
        EventDetector.EventDetectionResult result = detector.analyze();
        
        System.out.println("Total Events: " + result.totalEventCount);
        System.out.println("Brake Events: " + result.brakeEvents.size());
        System.out.println("Overspeed Events: " + result.overspeedEvents.size());
        System.out.println();
    }
    
    private static void testParkingAnalyzer() {
        System.out.println("--- Testing ParkingAccuracyAnalyzer ---");
        
        ParkingAccuracyAnalyzer analyzer = new ParkingAccuracyAnalyzer();
        
        // Add test parking records
        analyzer.addParkingRecord("Station A", 1000, 1020, new Date(), 5);
        analyzer.addParkingRecord("Station B", 2000, 1995, new Date(), 3);
        analyzer.addParkingRecord("Station C", 3000, 3005, new Date(), 2);
        
        ParkingAccuracyAnalyzer.ParkingAnalysisResult result = analyzer.analyze();
        
        System.out.println("Total Parkings: " + result.totalParkings);
        System.out.println("Accurate Parkings: " + result.accurateParkings);
        System.out.println("Accuracy Rate: " + String.format("%.2f", result.accuracyRate) + "%");
        System.out.println("Avg Deviation: " + String.format("%.2f", result.avgDeviation / 100.0) + " m");
        System.out.println();
    }
    
    private static void testStatisticalSummary() {
        System.out.println("--- Testing StatisticalSummary ---");
        
        // Create test data
        Vector<Date> timestamps = new Vector<>();
        Vector<Integer> locations = new Vector<>();
        Vector<Integer> speeds = new Vector<>();
        
        long baseTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            timestamps.add(new Date(baseTime + i * 1000));
            locations.add(i * 50);
            speeds.add(40 + (int)(Math.random() * 40));
        }
        
        StatisticalSummary.SummaryResult result = 
            StatisticalSummary.calculateSummary(timestamps, locations, speeds);
        
        System.out.println("Total Distance: " + String.format("%.2f", result.totalDistance / 1000.0) + " km");
        System.out.println("Total Time: " + String.format("%.2f", result.totalTime / 60000.0) + " min");
        System.out.println("Max Speed: " + result.maxSpeed + " km/h");
        System.out.println("Avg Speed: " + String.format("%.2f", result.avgSpeed) + " km/h");
        System.out.println("Data Quality: " + String.format("%.2f", result.dataQuality) + "%");
        System.out.println();
    }
    
    private static void testAPI() {
        System.out.println("--- Testing AnalysisAPI ---");
        
        AnalysisAPI api = new AnalysisAPIImpl();
        
        // Create test data
        Vector<Date> timestamps = new Vector<>();
        Vector<Integer> locations = new Vector<>();
        Vector<Integer> speeds = new Vector<>();
        Vector<Integer> targetSpeeds = new Vector<>();
        
        long baseTime = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            timestamps.add(new Date(baseTime + i * 1000));
            locations.add(i * 100);
            speeds.add(30 + (int)(Math.random() * 50));
            targetSpeeds.add(60);
        }
        
        Vector<String> stationNames = new Vector<>();
        Vector<Integer> stationLocations = new Vector<>();
        stationNames.add("Station A");
        stationLocations.add(1000);
        stationNames.add("Station B");
        stationLocations.add(3000);
        
        SpeedAnalyzer.SpeedAnalysisResult speedResult = 
            api.analyzeSpeedCurve(timestamps, locations, speeds, targetSpeeds);
        
        EventDetector.EventDetectionResult eventResult = 
            api.detectEvents(timestamps, locations, speeds, targetSpeeds, null);
        
        ParkingAccuracyAnalyzer.ParkingAnalysisResult parkingResult = 
            api.analyzeParkingAccuracy(timestamps, locations, speeds, stationNames, stationLocations);
        
        StatisticalSummary.SummaryResult summaryResult = 
            api.calculateComprehensiveStatistics(timestamps, locations, speeds, 
                                                eventResult, parkingResult);
        
        String report = api.exportAnalysisReport(speedResult, eventResult, 
                                                parkingResult, summaryResult);
        
        System.out.println("Generated comprehensive report:");
        System.out.println(report);
    }
}
