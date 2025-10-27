package drawGraphics;

import com.MiTAC.TRA.ATP.drawGraphics.DrawBase;
import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.drawGraphics.drawATP;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import analysis.SpeedAnalyzer;
import analysis.SpeedAnalyzer.SpeedPoint;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.util.Vector;

/**
 * Speed curve drawer with advanced analysis features
 * Extends DrawBase to provide speed curve visualization
 */
public class SpeedCurveDrawer extends DrawBase implements drawATP {
    
    private Color speedLineColor = Color.GREEN;
    private Color targetSpeedColor = Color.YELLOW;
    private Color overspeedColor = Color.RED;
    private boolean drawCurve = true;
    private Stroke lineStroke = new BasicStroke(2.0f);
    private SpeedAnalyzer analyzer;
    private Vector<SpeedPoint> speedData;
    
    public SpeedCurveDrawer(commonParaSetting cps, drawParameters para, Vector data) {
        super(cps, para, data);
        this.speedData = new Vector<>();
        this.analyzer = new SpeedAnalyzer();
    }
    
    /**
     * Set speed data from analyzer
     */
    public void setAnalyzer(SpeedAnalyzer analyzer) {
        this.analyzer = analyzer;
        if (analyzer != null) {
            this.speedData = analyzer.getSpeedData();
        }
    }
    
    /**
     * Set data line color
     */
    public void setDataLineColor(Color color) throws Exception {
        this.speedLineColor = color;
    }
    
    /**
     * Set whether to draw curve
     */
    public void setDrawCurve(boolean draw) throws Exception {
        this.drawCurve = draw;
    }
    
    /**
     * Set stroke style
     */
    public void setStroke(Stroke stroke) {
        this.lineStroke = stroke;
    }
    
    /**
     * Paint header
     */
    public void paintHeader(Graphics g) throws Exception {
        super.paintHeader(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Draw legend
        int legendX = 10;
        int legendY = 5;
        
        // Speed line legend
        g2.setColor(speedLineColor);
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawLine(legendX, legendY, legendX + 20, legendY);
        g2.setColor(Color.WHITE);
        g2.drawString("Speed", legendX + 25, legendY + 4);
        
        // Target speed legend
        legendX += 80;
        g2.setColor(targetSpeedColor);
        g2.drawLine(legendX, legendY, legendX + 20, legendY);
        g2.setColor(Color.WHITE);
        g2.drawString("Target", legendX + 25, legendY + 4);
        
        // Overspeed legend
        legendX += 80;
        g2.setColor(overspeedColor);
        g2.fillRect(legendX, legendY - 3, 20, 6);
        g2.setColor(Color.WHITE);
        g2.drawString("Overspeed", legendX + 25, legendY + 4);
    }
    
    /**
     * Paint body - main drawing method
     */
    public void paintBody(Graphics g) throws Exception {
        super.paintBody(g);
        
        if (!drawCurve || speedData.isEmpty()) {
            return;
        }
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(lineStroke);
        
        // Draw speed curve
        drawSpeedCurve(g2);
        
        // Draw target speed curve
        drawTargetSpeedCurve(g2);
        
        // Highlight overspeed zones
        drawOverspeedZones(g2);
    }
    
    /**
     * Draw speed curve
     */
    private void drawSpeedCurve(Graphics2D g2) {
        if (speedData.size() < 2) {
            return;
        }
        
        GeneralPath path = new GeneralPath();
        boolean firstPoint = true;
        
        for (SpeedPoint point : speedData) {
            int x = getXCoordinate(point);
            int y = getYCoordinate(point.speed);
            
            if (x < 0 || y < 0) {
                continue;
            }
            
            if (firstPoint) {
                path.moveTo(x, y);
                firstPoint = false;
            } else {
                path.lineTo(x, y);
            }
        }
        
        g2.setColor(speedLineColor);
        g2.draw(path);
    }
    
    /**
     * Draw target speed curve
     */
    private void drawTargetSpeedCurve(Graphics2D g2) {
        if (speedData.size() < 2) {
            return;
        }
        
        GeneralPath path = new GeneralPath();
        boolean firstPoint = true;
        
        for (SpeedPoint point : speedData) {
            int x = getXCoordinate(point);
            int y = getYCoordinate(point.targetSpeed);
            
            if (x < 0 || y < 0) {
                continue;
            }
            
            if (firstPoint) {
                path.moveTo(x, y);
                firstPoint = false;
            } else {
                path.lineTo(x, y);
            }
        }
        
        g2.setColor(targetSpeedColor);
        Stroke originalStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 
                                     0, new float[]{5, 5}, 0));
        g2.draw(path);
        g2.setStroke(originalStroke);
    }
    
    /**
     * Draw overspeed zones
     */
    private void drawOverspeedZones(Graphics2D g2) {
        for (int i = 0; i < speedData.size(); i++) {
            SpeedPoint point = speedData.get(i);
            
            if (point.speed > point.targetSpeed) {
                int x = getXCoordinate(point);
                int yTop = getYCoordinate(point.speed);
                int yBottom = getYCoordinate(point.targetSpeed);
                
                if (x >= 0 && yTop >= 0 && yBottom >= 0) {
                    g2.setColor(new Color(255, 0, 0, 50)); // Semi-transparent red
                    g2.fillRect(x - 2, yTop, 4, yBottom - yTop);
                }
            }
        }
    }
    
    /**
     * Get X coordinate based on draw type
     */
    private int getXCoordinate(SpeedPoint point) {
        if (drawType == drawByDistance) {
            return arrangesoldierPosition(point.location);
        } else {
            return arrangesoldierPosition(point.timestamp.getTime());
        }
    }
    
    /**
     * Get Y coordinate from speed value
     */
    private int getYCoordinate(int speed) {
        if (speed < para.MinNum || speed > para.MaxNum) {
            return -1;
        }
        return para.basePointY() - (int)((speed - para.MinNum) / para.dpiY);
    }
    
    /**
     * Set data - required by drawATP interface
     */
    public void setData(Vector data) throws Exception {
        this.BaseData = data;
        // Optionally parse data to speed points here
    }
}
