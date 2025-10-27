package analysis;

import java.util.Date;
import java.util.Vector;

/**
 * Parking accuracy analysis algorithm
 * Analyzes parking precision at stations
 */
public class ParkingAccuracyAnalyzer {
    
    /**
     * Parking record structure
     */
    public static class ParkingRecord {
        public String stationName;
        public int targetLocation;    // target parking position in meters
        public int actualLocation;    // actual parking position in meters
        public int deviation;         // deviation in meters (positive = overshoot, negative = undershoot)
        public Date timestamp;
        public int finalSpeed;        // speed at parking moment in km/h
        
        public ParkingRecord(String stationName, int targetLocation, int actualLocation, 
                            Date timestamp, int finalSpeed) {
            this.stationName = stationName;
            this.targetLocation = targetLocation;
            this.actualLocation = actualLocation;
            this.deviation = actualLocation - targetLocation;
            this.timestamp = timestamp;
            this.finalSpeed = finalSpeed;
        }
    }
    
    /**
     * Parking accuracy analysis result
     */
    public static class ParkingAnalysisResult {
        public Vector<ParkingRecord> parkingRecords;
        public int totalParkings;
        public int accurateParkings;    // within ±50cm
        public int acceptableParkings;  // within ±1m
        public double avgDeviation;      // average absolute deviation in meters
        public int maxDeviation;         // maximum absolute deviation in meters
        public double accuracyRate;      // percentage of accurate parkings
        
        public ParkingAnalysisResult() {
            parkingRecords = new Vector<>();
        }
    }
    
    private Vector<ParkingRecord> parkingRecords;
    
    public ParkingAccuracyAnalyzer() {
        this.parkingRecords = new Vector<>();
    }
    
    /**
     * Add parking record
     */
    public void addParkingRecord(String stationName, int targetLocation, int actualLocation,
                                Date timestamp, int finalSpeed) {
        parkingRecords.add(new ParkingRecord(stationName, targetLocation, actualLocation, 
                                            timestamp, finalSpeed));
    }
    
    /**
     * Detect parking events from location and speed data
     * A parking event is detected when speed drops to 0 near a station
     */
    public void detectParkingEvents(Vector<Date> timestamps, Vector<Integer> locations, 
                                    Vector<Integer> speeds, Vector<String> stationNames,
                                    Vector<Integer> stationLocations) {
        if (timestamps.isEmpty() || locations.isEmpty() || speeds.isEmpty()) {
            return;
        }
        
        for (int i = 1; i < speeds.size(); i++) {
            // Detect when train stops (speed becomes 0)
            if (speeds.get(i) == 0 && speeds.get(i - 1) > 0) {
                int actualLocation = locations.get(i);
                Date timestamp = timestamps.get(i);
                
                // Find nearest station
                String nearestStation = "Unknown";
                int targetLocation = actualLocation;
                int minDistance = Integer.MAX_VALUE;
                
                for (int j = 0; j < stationNames.size() && j < stationLocations.size(); j++) {
                    int distance = Math.abs(stationLocations.get(j) - actualLocation);
                    if (distance < minDistance && distance < 500) { // within 500m
                        minDistance = distance;
                        nearestStation = stationNames.get(j);
                        targetLocation = stationLocations.get(j);
                    }
                }
                
                addParkingRecord(nearestStation, targetLocation, actualLocation, 
                               timestamp, speeds.get(i - 1));
            }
        }
    }
    
    /**
     * Analyze parking accuracy
     */
    public ParkingAnalysisResult analyze() {
        ParkingAnalysisResult result = new ParkingAnalysisResult();
        
        result.parkingRecords = new Vector<>(parkingRecords);
        result.totalParkings = parkingRecords.size();
        
        if (parkingRecords.isEmpty()) {
            return result;
        }
        
        int deviationSum = 0;
        result.maxDeviation = 0;
        
        for (ParkingRecord record : parkingRecords) {
            int absDeviation = Math.abs(record.deviation);
            deviationSum += absDeviation;
            
            if (absDeviation > result.maxDeviation) {
                result.maxDeviation = absDeviation;
            }
            
            // Check accuracy (within ±50cm = ±0.5m)
            if (absDeviation <= 50) {
                result.accurateParkings++;
            }
            
            // Check acceptable range (within ±1m)
            if (absDeviation <= 100) {
                result.acceptableParkings++;
            }
        }
        
        result.avgDeviation = (double) deviationSum / parkingRecords.size();
        result.accuracyRate = (double) result.accurateParkings / result.totalParkings * 100.0;
        
        return result;
    }
    
    /**
     * Get parking records
     */
    public Vector<ParkingRecord> getParkingRecords() {
        return parkingRecords;
    }
    
    /**
     * Clear parking records
     */
    public void clear() {
        parkingRecords.clear();
    }
}
