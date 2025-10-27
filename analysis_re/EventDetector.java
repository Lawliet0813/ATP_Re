package analysis;

import java.util.Date;
import java.util.Vector;

/**
 * Event detection algorithm
 * Detects and classifies ATP system events
 */
public class EventDetector {
    
    /**
     * Event types
     */
    public static final int EVENT_BRAKE = 1;
    public static final int EVENT_OVERSPEED = 2;
    public static final int EVENT_FAILURE = 3;
    public static final int EVENT_DRIVER_MESSAGE = 4;
    public static final int EVENT_BALISE = 5;
    public static final int EVENT_STATION_APPROACH = 6;
    public static final int EVENT_STATION_DEPARTURE = 7;
    
    /**
     * Event structure
     */
    public static class Event {
        public int type;
        public Date timestamp;
        public int location;
        public String description;
        public Object data;
        
        public Event(int type, Date timestamp, int location, String description) {
            this.type = type;
            this.timestamp = timestamp;
            this.location = location;
            this.description = description;
        }
    }
    
    /**
     * Event detection result
     */
    public static class EventDetectionResult {
        public Vector<Event> allEvents;
        public Vector<Event> brakeEvents;
        public Vector<Event> overspeedEvents;
        public Vector<Event> failureEvents;
        public int totalEventCount;
        
        public EventDetectionResult() {
            allEvents = new Vector<>();
            brakeEvents = new Vector<>();
            overspeedEvents = new Vector<>();
            failureEvents = new Vector<>();
        }
    }
    
    private Vector<Event> events;
    
    public EventDetector() {
        this.events = new Vector<>();
    }
    
    /**
     * Add event
     */
    public void addEvent(int type, Date timestamp, int location, String description) {
        events.add(new Event(type, timestamp, location, description));
    }
    
    /**
     * Detect events from speed data
     */
    public void detectFromSpeedData(Vector<Date> timestamps, Vector<Integer> locations, 
                                    Vector<Integer> speeds, Vector<Integer> targetSpeeds) {
        if (timestamps.isEmpty() || locations.isEmpty() || 
            speeds.isEmpty() || targetSpeeds.isEmpty()) {
            return;
        }
        
        for (int i = 0; i < timestamps.size() && i < locations.size() && 
             i < speeds.size() && i < targetSpeeds.size(); i++) {
            
            // Detect overspeed event
            if (speeds.get(i) > targetSpeeds.get(i)) {
                addEvent(EVENT_OVERSPEED, timestamps.get(i), locations.get(i),
                        "Overspeed: " + speeds.get(i) + " km/h (limit: " + targetSpeeds.get(i) + " km/h)");
            }
            
            // Detect brake event (speed decrease > 10 km/h)
            if (i > 0 && speeds.get(i - 1) - speeds.get(i) > 10) {
                addEvent(EVENT_BRAKE, timestamps.get(i), locations.get(i),
                        "Braking detected: " + (speeds.get(i - 1) - speeds.get(i)) + " km/h decrease");
            }
        }
    }
    
    /**
     * Detect events from failure data
     */
    public void detectFromFailureData(Vector failureData) {
        if (failureData == null || failureData.isEmpty()) {
            return;
        }
        
        for (Object obj : failureData) {
            if (obj instanceof Vector) {
                Vector failureRecord = (Vector) obj;
                if (failureRecord.size() >= 3) {
                    Date timestamp = (Date) failureRecord.get(0);
                    Integer location = (Integer) failureRecord.get(1);
                    String description = (String) failureRecord.get(2);
                    addEvent(EVENT_FAILURE, timestamp, location, "Failure: " + description);
                }
            }
        }
    }
    
    /**
     * Analyze events
     */
    public EventDetectionResult analyze() {
        EventDetectionResult result = new EventDetectionResult();
        
        result.allEvents = new Vector<>(events);
        result.totalEventCount = events.size();
        
        // Classify events by type
        for (Event event : events) {
            switch (event.type) {
                case EVENT_BRAKE:
                    result.brakeEvents.add(event);
                    break;
                case EVENT_OVERSPEED:
                    result.overspeedEvents.add(event);
                    break;
                case EVENT_FAILURE:
                    result.failureEvents.add(event);
                    break;
            }
        }
        
        return result;
    }
    
    /**
     * Get all events
     */
    public Vector<Event> getEvents() {
        return events;
    }
    
    /**
     * Get events by type
     */
    public Vector<Event> getEventsByType(int type) {
        Vector<Event> filtered = new Vector<>();
        for (Event event : events) {
            if (event.type == type) {
                filtered.add(event);
            }
        }
        return filtered;
    }
    
    /**
     * Clear events
     */
    public void clear() {
        events.clear();
    }
}
