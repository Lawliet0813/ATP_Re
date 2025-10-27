package analysis;

import java.util.Vector;
import java.util.Date;

/**
 * Statistical summary calculator
 * Calculates comprehensive statistics for ATP mission data
 */
public class StatisticalSummary {
    
    /**
     * Statistical summary result
     */
    public static class SummaryResult {
        // Distance statistics
        public int totalDistance;        // in meters
        public int maxLocation;
        public int minLocation;
        
        // Time statistics
        public long totalTime;           // in milliseconds
        public Date startTime;
        public Date endTime;
        
        // Speed statistics
        public int maxSpeed;             // in km/h
        public int minSpeed;             // in km/h
        public double avgSpeed;          // in km/h
        public double medianSpeed;       // in km/h
        
        // Event statistics
        public int totalEvents;
        public int brakeEventCount;
        public int overspeedEventCount;
        public int failureEventCount;
        
        // Parking statistics
        public int totalStops;
        public double avgParkingAccuracy; // in meters
        
        // Operational statistics
        public int dataPointCount;
        public double dataQuality;       // percentage of valid data points
        
        public SummaryResult() {
            minSpeed = Integer.MAX_VALUE;
        }
    }
    
    /**
     * Calculate comprehensive statistical summary
     */
    public static SummaryResult calculateSummary(Vector<Date> timestamps, 
                                                 Vector<Integer> locations,
                                                 Vector<Integer> speeds) {
        SummaryResult result = new SummaryResult();
        
        if (timestamps == null || timestamps.isEmpty() || 
            locations == null || locations.isEmpty() ||
            speeds == null || speeds.isEmpty()) {
            return result;
        }
        
        result.dataPointCount = Math.min(Math.min(timestamps.size(), locations.size()), speeds.size());
        
        // Time statistics
        result.startTime = timestamps.firstElement();
        result.endTime = timestamps.lastElement();
        result.totalTime = result.endTime.getTime() - result.startTime.getTime();
        
        // Distance statistics
        result.minLocation = Integer.MAX_VALUE;
        result.maxLocation = Integer.MIN_VALUE;
        
        // Speed statistics
        long speedSum = 0;
        Vector<Integer> validSpeeds = new Vector<>();
        
        for (int i = 0; i < result.dataPointCount; i++) {
            int location = locations.get(i);
            int speed = speeds.get(i);
            
            // Location statistics
            if (location < result.minLocation) {
                result.minLocation = location;
            }
            if (location > result.maxLocation) {
                result.maxLocation = location;
            }
            
            // Speed statistics
            if (speed >= 0) {  // Valid speed
                if (speed > result.maxSpeed) {
                    result.maxSpeed = speed;
                }
                if (speed < result.minSpeed) {
                    result.minSpeed = speed;
                }
                speedSum += speed;
                validSpeeds.add(speed);
            }
        }
        
        result.totalDistance = result.maxLocation - result.minLocation;
        
        // Calculate average speed
        if (!validSpeeds.isEmpty()) {
            result.avgSpeed = (double) speedSum / validSpeeds.size();
            
            // Calculate median speed
            Vector<Integer> sortedSpeeds = new Vector<>(validSpeeds);
            sortedSpeeds.sort(null);
            int medianIndex = sortedSpeeds.size() / 2;
            if (sortedSpeeds.size() % 2 == 0) {
                result.medianSpeed = (sortedSpeeds.get(medianIndex - 1) + sortedSpeeds.get(medianIndex)) / 2.0;
            } else {
                result.medianSpeed = sortedSpeeds.get(medianIndex);
            }
        }
        
        // Calculate data quality
        result.dataQuality = (double) validSpeeds.size() / result.dataPointCount * 100.0;
        
        // Count stops
        for (int i = 1; i < result.dataPointCount; i++) {
            if (speeds.get(i) == 0 && speeds.get(i - 1) > 0) {
                result.totalStops++;
            }
        }
        
        return result;
    }
    
    /**
     * Calculate summary with event data
     */
    public static SummaryResult calculateSummaryWithEvents(Vector<Date> timestamps, 
                                                           Vector<Integer> locations,
                                                           Vector<Integer> speeds,
                                                           EventDetector.EventDetectionResult eventResult) {
        SummaryResult result = calculateSummary(timestamps, locations, speeds);
        
        if (eventResult != null) {
            result.totalEvents = eventResult.totalEventCount;
            result.brakeEventCount = eventResult.brakeEvents.size();
            result.overspeedEventCount = eventResult.overspeedEvents.size();
            result.failureEventCount = eventResult.failureEvents.size();
        }
        
        return result;
    }
    
    /**
     * Calculate summary with parking data
     */
    public static SummaryResult calculateSummaryWithParking(Vector<Date> timestamps, 
                                                            Vector<Integer> locations,
                                                            Vector<Integer> speeds,
                                                            ParkingAccuracyAnalyzer.ParkingAnalysisResult parkingResult) {
        SummaryResult result = calculateSummary(timestamps, locations, speeds);
        
        if (parkingResult != null) {
            result.totalStops = parkingResult.totalParkings;
            result.avgParkingAccuracy = parkingResult.avgDeviation;
        }
        
        return result;
    }
    
    /**
     * Format summary as string
     */
    public static String formatSummary(SummaryResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Statistical Summary ===\n");
        sb.append("\n[Distance Statistics]\n");
        sb.append(String.format("Total Distance: %.2f km\n", result.totalDistance / 1000.0));
        sb.append(String.format("Location Range: %d - %d m\n", result.minLocation, result.maxLocation));
        
        sb.append("\n[Time Statistics]\n");
        sb.append(String.format("Start Time: %s\n", result.startTime));
        sb.append(String.format("End Time: %s\n", result.endTime));
        sb.append(String.format("Total Time: %.2f minutes\n", result.totalTime / 60000.0));
        
        sb.append("\n[Speed Statistics]\n");
        sb.append(String.format("Max Speed: %d km/h\n", result.maxSpeed));
        sb.append(String.format("Min Speed: %d km/h\n", result.minSpeed));
        sb.append(String.format("Average Speed: %.2f km/h\n", result.avgSpeed));
        sb.append(String.format("Median Speed: %.2f km/h\n", result.medianSpeed));
        
        sb.append("\n[Operational Statistics]\n");
        sb.append(String.format("Data Points: %d\n", result.dataPointCount));
        sb.append(String.format("Data Quality: %.2f%%\n", result.dataQuality));
        sb.append(String.format("Total Stops: %d\n", result.totalStops));
        
        if (result.totalEvents > 0) {
            sb.append("\n[Event Statistics]\n");
            sb.append(String.format("Total Events: %d\n", result.totalEvents));
            sb.append(String.format("Brake Events: %d\n", result.brakeEventCount));
            sb.append(String.format("Overspeed Events: %d\n", result.overspeedEventCount));
            sb.append(String.format("Failure Events: %d\n", result.failureEventCount));
        }
        
        if (result.avgParkingAccuracy > 0) {
            sb.append("\n[Parking Statistics]\n");
            sb.append(String.format("Average Parking Accuracy: %.2f m\n", result.avgParkingAccuracy));
        }
        
        return sb.toString();
    }
}
