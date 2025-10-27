package ui;

import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import drawGraphics.SpeedCurveDrawer;
import drawGraphics.EventDrawer;
import analysis.SpeedAnalyzer;
import analysis.EventDetector;
import analysis.StatisticalSummary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * Interactive chart panel for ATP data visualization
 * Provides pan, zoom, and interactive features
 */
public class InteractiveChartPanel extends JPanel {
    
    private SpeedCurveDrawer speedDrawer;
    private EventDrawer eventDrawer;
    private commonParaSetting commonParams;
    private drawParameters speedParams;
    private drawParameters eventParams;
    
    // Zoom and pan state
    private double zoomLevel = 1.0;
    private int panOffsetX = 0;
    private int panOffsetY = 0;
    private Point dragStart = null;
    
    // Mouse tracking
    private Point mousePosition = null;
    private boolean showCrosshair = true;
    private boolean showTooltip = true;
    
    // Chart components visibility
    private boolean showSpeedCurve = true;
    private boolean showEvents = true;
    private boolean showGrid = true;
    
    // Data
    private SpeedAnalyzer speedAnalyzer;
    private EventDetector eventDetector;
    
    public InteractiveChartPanel() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 600));
        
        // Initialize parameters
        initializeParameters();
        
        // Setup mouse listeners
        setupMouseListeners();
        
        // Setup keyboard listeners
        setupKeyboardListeners();
    }
    
    /**
     * Initialize drawing parameters
     */
    private void initializeParameters() {
        commonParams = new commonParaSetting();
        commonParams.backgroundColor = Color.BLACK;
        commonParams.mainLineColor = Color.WHITE;
        commonParams.baseLineColor = Color.DARK_GRAY;
        commonParams.charColor = Color.WHITE;
        commonParams.drawByDist = true;
        commonParams.basePointX = 50;
        
        // Speed curve parameters
        speedParams = new drawParameters();
        speedParams.MaxNum = 130;  // Max speed 130 km/h
        speedParams.MinNum = 0;
        speedParams.UpperBound = 50;
        speedParams.dpiY = 1.0;
        speedParams.intervalY = 10;
        speedParams.message = "Speed Curve";
        speedParams.drawBody = true;
        
        // Event parameters
        eventParams = new drawParameters();
        eventParams.MaxNum = 100;
        eventParams.MinNum = 0;
        eventParams.UpperBound = 300;
        eventParams.dpiY = 1.0;
        eventParams.intervalY = 20;
        eventParams.message = "Events";
        eventParams.drawBody = true;
    }
    
    /**
     * Setup mouse listeners for interaction
     */
    private void setupMouseListeners() {
        // Mouse motion for crosshair
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mousePosition = e.getPoint();
                repaint();
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragStart != null) {
                    int dx = e.getX() - dragStart.x;
                    int dy = e.getY() - dragStart.y;
                    panOffsetX += dx;
                    panOffsetY += dy;
                    dragStart = e.getPoint();
                    repaint();
                }
            }
        });
        
        // Mouse press for drag start
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    dragStart = e.getPoint();
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                dragStart = null;
                setCursor(Cursor.getDefaultCursor());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                mousePosition = null;
                repaint();
            }
        });
        
        // Mouse wheel for zoom
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double oldZoom = zoomLevel;
                if (e.getWheelRotation() < 0) {
                    zoomLevel *= 1.1;  // Zoom in
                } else {
                    zoomLevel /= 1.1;  // Zoom out
                }
                
                // Clamp zoom level
                zoomLevel = Math.max(0.1, Math.min(10.0, zoomLevel));
                
                // Adjust pan to zoom towards mouse position
                if (mousePosition != null) {
                    double zoomRatio = zoomLevel / oldZoom;
                    panOffsetX = (int)((panOffsetX - mousePosition.x) * zoomRatio + mousePosition.x);
                    panOffsetY = (int)((panOffsetY - mousePosition.y) * zoomRatio + mousePosition.y);
                }
                
                repaint();
            }
        });
    }
    
    /**
     * Setup keyboard listeners for shortcuts
     */
    private void setupKeyboardListeners() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_R:  // Reset view
                        resetView();
                        break;
                    case KeyEvent.VK_G:  // Toggle grid
                        showGrid = !showGrid;
                        repaint();
                        break;
                    case KeyEvent.VK_C:  // Toggle crosshair
                        showCrosshair = !showCrosshair;
                        repaint();
                        break;
                    case KeyEvent.VK_S:  // Toggle speed curve
                        showSpeedCurve = !showSpeedCurve;
                        repaint();
                        break;
                    case KeyEvent.VK_E:  // Toggle events
                        showEvents = !showEvents;
                        repaint();
                        break;
                }
            }
        });
    }
    
    /**
     * Set speed analyzer
     */
    public void setSpeedAnalyzer(SpeedAnalyzer analyzer) {
        this.speedAnalyzer = analyzer;
        if (speedDrawer != null) {
            speedDrawer.setAnalyzer(analyzer);
        }
        repaint();
    }
    
    /**
     * Set event detector
     */
    public void setEventDetector(EventDetector detector) {
        this.eventDetector = detector;
        if (eventDrawer != null) {
            eventDrawer.setDetector(detector);
        }
        repaint();
    }
    
    /**
     * Reset view to default
     */
    public void resetView() {
        zoomLevel = 1.0;
        panOffsetX = 0;
        panOffsetY = 0;
        repaint();
    }
    
    /**
     * Paint component
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Enable anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Apply zoom and pan transform
        g2.translate(panOffsetX, panOffsetY);
        g2.scale(zoomLevel, zoomLevel);
        
        // Draw grid if enabled
        if (showGrid) {
            drawGrid(g2);
        }
        
        // Draw speed curve if enabled
        if (showSpeedCurve && speedDrawer != null) {
            try {
                speedDrawer.paintBody(g2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Draw events if enabled
        if (showEvents && eventDrawer != null) {
            try {
                eventDrawer.paintBody(g2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Reset transform for overlay elements
        g2.scale(1.0/zoomLevel, 1.0/zoomLevel);
        g2.translate(-panOffsetX, -panOffsetY);
        
        // Draw crosshair if enabled
        if (showCrosshair && mousePosition != null) {
            drawCrosshair(g2, mousePosition);
        }
        
        // Draw info panel
        drawInfoPanel(g2);
    }
    
    /**
     * Draw grid
     */
    private void drawGrid(Graphics2D g2) {
        g2.setColor(new Color(50, 50, 50));
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(1.0f));
        
        int width = getWidth();
        int height = getHeight();
        int gridSpacing = 50;
        
        // Vertical lines
        for (int x = 0; x < width; x += gridSpacing) {
            g2.drawLine(x, 0, x, height);
        }
        
        // Horizontal lines
        for (int y = 0; y < height; y += gridSpacing) {
            g2.drawLine(0, y, width, y);
        }
        
        g2.setStroke(oldStroke);
    }
    
    /**
     * Draw crosshair at mouse position
     */
    private void drawCrosshair(Graphics2D g2, Point pos) {
        g2.setColor(Color.CYAN);
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                                     0, new float[]{5, 5}, 0));
        
        g2.drawLine(pos.x, 0, pos.x, getHeight());
        g2.drawLine(0, pos.y, getWidth(), pos.y);
        
        g2.setStroke(oldStroke);
    }
    
    /**
     * Draw info panel with shortcuts
     */
    private void drawInfoPanel(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(10, getHeight() - 120, 200, 110);
        
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 11));
        
        int y = getHeight() - 100;
        g2.drawString("Shortcuts:", 15, y);
        y += 15;
        g2.drawString("R - Reset View", 15, y);
        y += 15;
        g2.drawString("G - Toggle Grid", 15, y);
        y += 15;
        g2.drawString("C - Toggle Crosshair", 15, y);
        y += 15;
        g2.drawString("S - Toggle Speed", 15, y);
        y += 15;
        g2.drawString("E - Toggle Events", 15, y);
        y += 15;
        g2.drawString(String.format("Zoom: %.1fx", zoomLevel), 15, y);
    }
    
    /**
     * Get current zoom level
     */
    public double getZoomLevel() {
        return zoomLevel;
    }
    
    /**
     * Set zoom level
     */
    public void setZoomLevel(double zoom) {
        this.zoomLevel = Math.max(0.1, Math.min(10.0, zoom));
        repaint();
    }
}
