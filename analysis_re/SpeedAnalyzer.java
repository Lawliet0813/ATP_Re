package analysis;

import java.util.Date;
import java.util.Vector;

/**
 * Speed curve analysis algorithm
 * Analyzes speed data to extract patterns, trends, and anomalies
 */
public class SpeedAnalyzer {
    
    /**
     * Speed data point structure
     */
    public static class SpeedPoint {
        public Date timestamp;
        public int location;      // in meters
        public int speed;         // in km/h
        public int targetSpeed;   // target speed in km/h
        
        public SpeedPoint(Date timestamp, int location, int speed, int targetSpeed) {
            this.timestamp = timestamp;
            this.location = location;
            this.speed = speed;
            this.targetSpeed = targetSpeed;
        }
    }
    
    /**
     * Speed analysis result
     */
    public static class SpeedAnalysisResult {
        public int maxSpeed;
        public int minSpeed;
        public double avgSpeed;
        public int overspeedCount;
        public Vector<SpeedPoint> overspeedPoints;
        public Vector<SpeedPoint> brakingPoints;
        public double totalDistance;
        public long totalTime;
        
        public SpeedAnalysisResult() {
            overspeedPoints = new Vector<>();
            brakingPoints = new Vector<>();
        }
    }
    
    private Vector<SpeedPoint> speedData;
    
    public SpeedAnalyzer() {
        this.speedData = new Vector<>();
    }
    
    /**
     * Add speed data point
     */
    public void addSpeedPoint(Date timestamp, int location, int speed, int targetSpeed) {
        speedData.add(new SpeedPoint(timestamp, location, speed, targetSpeed));
    }
    
    /**
     * Set speed data from vectors
     */
    public void setSpeedData(Vector<Date> timestamps, Vector<Integer> locations, 
                             Vector<Integer> speeds, Vector<Integer> targetSpeeds) {
        speedData.clear();
        for (int i = 0; i < timestamps.size() && i < locations.size() && 
             i < speeds.size() && i < targetSpeeds.size(); i++) {
            speedData.add(new SpeedPoint(timestamps.get(i), locations.get(i), 
                                        speeds.get(i), targetSpeeds.get(i)));
        }
    }
    
    /**
     * Analyze speed curve
     */
    public SpeedAnalysisResult analyze() {
        SpeedAnalysisResult result = new SpeedAnalysisResult();
        
        if (speedData.isEmpty()) {
            return result;
        }
        
        // Calculate basic statistics
        result.maxSpeed = Integer.MIN_VALUE;
        result.minSpeed = Integer.MAX_VALUE;
        long speedSum = 0;
        
        for (SpeedPoint point : speedData) {
            if (point.speed > result.maxSpeed) {
                result.maxSpeed = point.speed;
            }
            if (point.speed < result.minSpeed) {
                result.minSpeed = point.speed;
            }
            speedSum += point.speed;
            
            // Detect overspeed
            if (point.speed > point.targetSpeed) {
                result.overspeedCount++;
                result.overspeedPoints.add(point);
            }
        }
        
        result.avgSpeed = (double) speedSum / speedData.size();
        
        // Detect braking points (speed decrease > 5 km/h in short time)
        for (int i = 1; i < speedData.size(); i++) {
            SpeedPoint prev = speedData.get(i - 1);
            SpeedPoint curr = speedData.get(i);
            
            int speedDelta = prev.speed - curr.speed;
            if (speedDelta > 5) {
                result.brakingPoints.add(curr);
            }
        }
        
        // Calculate total distance and time
        if (speedData.size() > 1) {
            result.totalDistance = speedData.lastElement().location - speedData.firstElement().location;
            result.totalTime = speedData.lastElement().timestamp.getTime() - 
                              speedData.firstElement().timestamp.getTime();
        }
        
        return result;
    }
    
    /**
     * Get speed data
     */
    public Vector<SpeedPoint> getSpeedData() {
        return speedData;
    }
    
    /**
     * Clear speed data
     */
    public void clear() {
        speedData.clear();
    }
}
