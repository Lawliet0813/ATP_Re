package drawGraphics;

import com.MiTAC.TRA.ATP.drawGraphics.DrawBase;
import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.drawGraphics.drawATP;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import analysis.EventDetector;
import analysis.EventDetector.Event;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.util.Vector;

/**
 * Event drawer for visualizing ATP system events
 * Extends DrawBase to provide event markers on timeline
 */
public class EventDrawer extends DrawBase implements drawATP {
    
    private EventDetector detector;
    private Vector<Event> events;
    
    // Event colors
    private static final Color BRAKE_COLOR = Color.ORANGE;
    private static final Color OVERSPEED_COLOR = Color.RED;
    private static final Color FAILURE_COLOR = Color.MAGENTA;
    private static final Color DRIVER_MSG_COLOR = Color.CYAN;
    private static final Color BALISE_COLOR = Color.YELLOW;
    private static final Color STATION_COLOR = Color.GREEN;
    private static final Color DEFAULT_COLOR = Color.GRAY;
    
    private boolean showEventLabels = true;
    private boolean drawEvents = true;
    
    public EventDrawer(commonParaSetting cps, drawParameters para, Vector data) {
        super(cps, para, data);
        this.events = new Vector<>();
        this.detector = new EventDetector();
    }
    
    /**
     * Set event detector
     */
    public void setDetector(EventDetector detector) {
        this.detector = detector;
        if (detector != null) {
            this.events = detector.getEvents();
        }
    }
    
    /**
     * Set whether to show event labels
     */
    public void setShowEventLabels(boolean show) {
        this.showEventLabels = show;
    }
    
    /**
     * Set data line color - required by interface
     */
    public void setDataLineColor(Color color) throws Exception {
        // Not used for events
    }
    
    /**
     * Set whether to draw events
     */
    public void setDrawCurve(boolean draw) throws Exception {
        this.drawEvents = draw;
    }
    
    /**
     * Set stroke style
     */
    public void setStroke(Stroke stroke) {
        // Can be used to customize event markers
    }
    
    /**
     * Paint header with legend
     */
    public void paintHeader(Graphics g) throws Exception {
        super.paintHeader(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Draw event legend
        int legendX = 10;
        int legendY = 5;
        int markerSize = 8;
        
        Font originalFont = g2.getFont();
        g2.setFont(new Font("Arial", Font.PLAIN, 10));
        
        // Brake event legend
        g2.setColor(BRAKE_COLOR);
        g2.fillOval(legendX, legendY - markerSize/2, markerSize, markerSize);
        g2.setColor(Color.WHITE);
        g2.drawString("Brake", legendX + markerSize + 3, legendY + 4);
        
        // Overspeed event legend
        legendX += 60;
        g2.setColor(OVERSPEED_COLOR);
        g2.fillOval(legendX, legendY - markerSize/2, markerSize, markerSize);
        g2.setColor(Color.WHITE);
        g2.drawString("Overspeed", legendX + markerSize + 3, legendY + 4);
        
        // Failure event legend
        legendX += 75;
        g2.setColor(FAILURE_COLOR);
        g2.fillOval(legendX, legendY - markerSize/2, markerSize, markerSize);
        g2.setColor(Color.WHITE);
        g2.drawString("Failure", legendX + markerSize + 3, legendY + 4);
        
        g2.setFont(originalFont);
    }
    
    /**
     * Paint body - main drawing method
     */
    public void paintBody(Graphics g) throws Exception {
        super.paintBody(g);
        
        if (!drawEvents || events.isEmpty()) {
            return;
        }
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2.0f));
        
        // Draw all events
        for (Event event : events) {
            drawEvent(g2, event);
        }
    }
    
    /**
     * Draw a single event
     */
    private void drawEvent(Graphics2D g2, Event event) {
        int x = getXCoordinate(event);
        
        if (x < 0) {
            return;
        }
        
        // Get Y coordinate (center of drawing area)
        int y = para.basePointY() - (para.MaxNum - para.MinNum) / 2;
        
        // Select color based on event type
        Color eventColor = getEventColor(event.type);
        
        // Draw event marker (circle)
        int markerSize = 10;
        g2.setColor(eventColor);
        g2.fill(new Ellipse2D.Double(x - markerSize/2, y - markerSize/2, markerSize, markerSize));
        
        // Draw border
        g2.setColor(Color.WHITE);
        g2.draw(new Ellipse2D.Double(x - markerSize/2, y - markerSize/2, markerSize, markerSize));
        
        // Draw vertical line
        g2.setColor(new Color(eventColor.getRed(), eventColor.getGreen(), 
                             eventColor.getBlue(), 100)); // Semi-transparent
        Stroke originalStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                                    0, new float[]{5, 5}, 0));
        g2.drawLine(x, para.UpperBound, x, para.basePointY());
        g2.setStroke(originalStroke);
        
        // Draw label if enabled
        if (showEventLabels && event.description != null && !event.description.isEmpty()) {
            g2.setColor(Color.WHITE);
            Font originalFont = g2.getFont();
            g2.setFont(new Font("Arial", Font.PLAIN, 9));
            
            // Truncate long descriptions
            String label = event.description;
            if (label.length() > 15) {
                label = label.substring(0, 12) + "...";
            }
            
            // Draw label rotated vertically
            g2.translate(x, y - markerSize - 5);
            g2.rotate(-Math.PI / 2);
            g2.drawString(label, 0, 0);
            g2.rotate(Math.PI / 2);
            g2.translate(-x, -(y - markerSize - 5));
            
            g2.setFont(originalFont);
        }
    }
    
    /**
     * Get color for event type
     */
    private Color getEventColor(int eventType) {
        switch (eventType) {
            case EventDetector.EVENT_BRAKE:
                return BRAKE_COLOR;
            case EventDetector.EVENT_OVERSPEED:
                return OVERSPEED_COLOR;
            case EventDetector.EVENT_FAILURE:
                return FAILURE_COLOR;
            case EventDetector.EVENT_DRIVER_MESSAGE:
                return DRIVER_MSG_COLOR;
            case EventDetector.EVENT_BALISE:
                return BALISE_COLOR;
            case EventDetector.EVENT_STATION_APPROACH:
            case EventDetector.EVENT_STATION_DEPARTURE:
                return STATION_COLOR;
            default:
                return DEFAULT_COLOR;
        }
    }
    
    /**
     * Get X coordinate based on draw type
     */
    private int getXCoordinate(Event event) {
        if (drawType == drawByDistance) {
            return arrangesoldierPosition(event.location);
        } else {
            return arrangesoldierPosition(event.timestamp.getTime());
        }
    }
    
    /**
     * Set data - required by drawATP interface
     */
    public void setData(Vector data) throws Exception {
        this.BaseData = data;
        // Optionally parse data to events here
    }
}
