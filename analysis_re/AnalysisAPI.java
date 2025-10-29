package analysis;

import analysis.SpeedAnalyzer.SpeedAnalysisResult;
import analysis.EventDetector.EventDetectionResult;
import analysis.ParkingAccuracyAnalyzer.ParkingAnalysisResult;
import analysis.StatisticalSummary.SummaryResult;
import java.util.Date;
import java.util.Vector;

/**
 * Analysis API interface
 * Provides unified interface for all ATP data analysis operations
 */
public interface AnalysisAPI {
    
    /**
     * Analyze speed curve data
     * @param timestamps Time series data
     * @param locations Location series data (in meters)
     * @param speeds Speed series data (in km/h)
     * @param targetSpeeds Target speed series data (in km/h)
     * @return Speed analysis result
     */
    SpeedAnalysisResult analyzeSpeedCurve(Vector<Date> timestamps, 
                                         Vector<Integer> locations,
                                         Vector<Integer> speeds,
                                         Vector<Integer> targetSpeeds);
    
    /**
     * Detect events from ATP data
     * @param timestamps Time series data
     * @param locations Location series data (in meters)
     * @param speeds Speed series data (in km/h)
     * @param targetSpeeds Target speed series data (in km/h)
     * @param failureData Failure data records
     * @return Event detection result
     */
    EventDetectionResult detectEvents(Vector<Date> timestamps,
                                     Vector<Integer> locations,
                                     Vector<Integer> speeds,
                                     Vector<Integer> targetSpeeds,
                                     Vector failureData);
    
    /**
     * Analyze parking accuracy
     * @param timestamps Time series data
     * @param locations Location series data (in meters)
     * @param speeds Speed series data (in km/h)
     * @param stationNames Station name list
     * @param stationLocations Station location list (in meters)
     * @return Parking accuracy analysis result
     */
    ParkingAnalysisResult analyzeParkingAccuracy(Vector<Date> timestamps,
                                                 Vector<Integer> locations,
                                                 Vector<Integer> speeds,
                                                 Vector<String> stationNames,
                                                 Vector<Integer> stationLocations);
    
    /**
     * Calculate comprehensive statistical summary
     * @param timestamps Time series data
     * @param locations Location series data (in meters)
     * @param speeds Speed series data (in km/h)
     * @return Statistical summary result
     */
    SummaryResult calculateStatistics(Vector<Date> timestamps,
                                     Vector<Integer> locations,
                                     Vector<Integer> speeds);
    
    /**
     * Calculate comprehensive statistical summary with events and parking data
     * @param timestamps Time series data
     * @param locations Location series data (in meters)
     * @param speeds Speed series data (in km/h)
     * @param eventResult Event detection result
     * @param parkingResult Parking analysis result
     * @return Statistical summary result
     */
    SummaryResult calculateComprehensiveStatistics(Vector<Date> timestamps,
                                                   Vector<Integer> locations,
                                                   Vector<Integer> speeds,
                                                   EventDetectionResult eventResult,
                                                   ParkingAnalysisResult parkingResult);
    
    /**
     * Export analysis results to formatted string
     * @param speedResult Speed analysis result
     * @param eventResult Event detection result
     * @param parkingResult Parking analysis result
     * @param summaryResult Statistical summary result
     * @return Formatted analysis report
     */
    String exportAnalysisReport(SpeedAnalysisResult speedResult,
                               EventDetectionResult eventResult,
                               ParkingAnalysisResult parkingResult,
                               SummaryResult summaryResult);
}
