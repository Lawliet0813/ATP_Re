package drawGraphics;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.SortTable.ColumnComparator;
import com.MiTAC.TRA.ATP.core.Station;
import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

public abstract class DrawBase {
  protected Vector BaseData;
  
  private static int rightEdgeCache;
  
  private static int leftEdgeCache;
  
  private static int drawMode = 0;
  
  protected commonParaSetting cps;
  
  private static int scaleEndLocation;
  
  private static long scaleEndTime;
  
  private static int scaleStartLocation;
  
  private static long scaleStartTime;
  
  public static final int drawByDistance = 0;
  
  public static final int drawByTime = 1;
  
  protected static int drawType = -1;
  
  protected static int endLocation;
  
  protected static long endTime;
  
  protected Graphics2D g2;
  
  protected Graphics2D g2h;
  
  private static boolean followMode;
  
  protected static int[] location;
  
  protected static int mouseX;
  
  protected static int mouseY;
  
  protected boolean outOfView = false;
  
  protected drawParameters para;
  
  private static int[] cachedScreenPositions;
  
  private SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
  
  protected static int startLocation;
  
  protected static long startTime;
  
  protected static Date[] time;
  
  protected static boolean typeChanged = true;
  
  static {
    followMode = false;
    mouseX = 0;
    mouseY = 0;
  }
  
  public DrawBase(commonParaSetting paramcommonParaSetting, drawParameters paramdrawParameters, Vector paramVector) {
    this.cps = paramcommonParaSetting;
    this.para = paramdrawParameters;
    this.BaseData = paramVector;
    drawType = this.cps.drawByDist ? 0 : 1;
    setScaleData();
  }
  
  /**
   * Converts a real-world distance value to screen X coordinate.
   * Formula: screenX = basePointX + (distance - scaleStartLocation) / dpiDistX
   * 
   * @param distance the distance in centimeters from the origin
   * @return the corresponding X coordinate on the screen in pixels
   */
  protected int convertDistanceToScreenX(int distance) {
    null = 0;
    return (int)(this.cps.basePointX + (distance - scaleStartLocation) / this.cps.dpiDistX);
  }
  
  /**
   * Converts a real-world time value to screen X coordinate.
   * Formula: screenX = basePointX + ((time - scaleStartTime) / 1000) / dpiX
   * 
   * @param time the time in milliseconds since epoch
   * @return the corresponding X coordinate on the screen in pixels
   */
  protected int convertTimeToScreenX(long time) {
    null = 0;
    return this.cps.basePointX + (int)(((time - scaleStartTime) / 1000L) / this.cps.dpiX);
  }
  
  protected void drawBasicMessage() {}
  
  private void drawHorizontalGridLines() {
    if (drawType == 1) {
      for (int i = this.para.MinNum; i <= this.para.MaxNum; i = (int)(i + this.para.intervalY * this.para.dpiY)) {
        if (i == this.para.MinNum) {
          this.g2.setColor(this.cps.mainLineColor);
        } else {
          this.g2.setColor(this.cps.baseLineColor);
        } 
        this.g2.drawLine(convertTimeToScreenX(scaleStartTime), convertValueToScreenY(i), convertTimeToScreenX(scaleEndTime), convertValueToScreenY(i));
      } 
      if (this.para.MinNum <= 0 && this.para.MaxNum >= 0) {
        this.g2.setColor(this.cps.mainLineColor);
        this.g2.drawLine(convertTimeToScreenX(scaleStartTime), convertValueToScreenY(0), convertTimeToScreenX(scaleEndTime), convertValueToScreenY(0));
      } 
    } else {
      for (int i = this.para.MinNum; i <= this.para.MaxNum; i = (int)(i + this.para.intervalY * this.para.dpiY)) {
        if (i == this.para.MinNum) {
          this.g2.setColor(this.cps.mainLineColor);
        } else {
          this.g2.setColor(this.cps.baseLineColor);
        } 
        this.g2.drawLine(convertDistanceToScreenX(scaleStartLocation), convertValueToScreenY(i), convertDistanceToScreenX(scaleEndLocation), convertValueToScreenY(i));
      } 
      if (this.para.MinNum <= 0 && this.para.MaxNum >= 0) {
        this.g2.setColor(this.cps.mainLineColor);
        this.g2.drawLine(convertDistanceToScreenX(scaleStartLocation), convertValueToScreenY(0), convertDistanceToScreenX(scaleEndLocation), convertValueToScreenY(0));
      } 
    } 
  }
  
  private void drawVerticalGridLines() {
    byte b = 0;
    if (drawType == 1) {
      for (long l = scaleStartTime; l <= scaleEndTime; l = (long)(l + this.cps.intervalX * this.cps.dpiX * 1000.0D)) {
        if (b % this.cps.BrightLine == 0) {
          this.g2.setColor(this.cps.baseLineColor_light);
        } else {
          this.g2.setColor(this.cps.baseLineColor);
        } 
        this.g2.drawLine(convertTimeToScreenX(l), convertValueToScreenY(this.para.MaxNum), convertTimeToScreenX(l), convertValueToScreenY(this.para.MinNum));
        b++;
      } 
    } else {
      for (int i = scaleStartLocation; i <= scaleEndLocation; i = (int)(i + this.cps.intervalDistX * this.cps.dpiDistX)) {
        if (b % this.cps.BrightLine == 0) {
          this.g2.setColor(this.cps.baseLineColor_light);
        } else {
          this.g2.setColor(this.cps.baseLineColor);
        } 
        this.g2.drawLine(convertDistanceToScreenX(i), convertValueToScreenY(this.para.MaxNum), convertDistanceToScreenX(i), convertValueToScreenY(this.para.MinNum));
        b++;
      } 
    } 
  }
  
  private void drawAxisLabels() {
    byte b = 0;
    if (drawType == 1) {
      for (long l = scaleStartTime; l <= scaleEndTime; l = (long)(l + this.cps.intervalX * this.cps.dpiX * 1000.0D)) {
        if (b % this.cps.BrightLine == 0) {
          this.g2.setColor(this.cps.charColor);
          this.g2.drawString(this.timeFormatter.format(new Date(l)), convertTimeToScreenX(l) - 20, this.para.basePointY() + 12);
        } 
        b++;
      } 
    } else {
      for (int i = scaleStartLocation; i <= scaleEndLocation; i = (int)(i + this.cps.intervalDistX * this.cps.dpiDistX)) {
        if (b % this.cps.BrightLine == 0) {
          this.g2.setColor(this.cps.charColor);
          this.g2.drawString((i / 100) + "m", convertDistanceToScreenX(i) - 20, this.para.basePointY() + 12);
        } 
        b++;
      } 
    } 
  }
  
  public void drawScanner() {
    this.g2.setColor(this.cps.mouse);
    if (mouseX > 0 && mouseX < getChartWidth() && mouseY > this.para.UpperBound && mouseY < this.para.UpperBound + getChartHeight()) {
      this.g2.drawLine(mouseX, this.para.UpperBound, mouseX, (mouseY - 10 < this.para.UpperBound) ? this.para.UpperBound : (mouseY - 10));
      this.g2.drawLine(mouseX, (mouseY + 10 > getChartHeight() + this.para.UpperBound) ? (getChartHeight() + this.para.UpperBound) : (mouseY + 10), mouseX, getChartHeight() + this.para.UpperBound);
      this.g2.drawLine(0, mouseY, mouseX - 10, mouseY);
      this.g2.drawLine(mouseX + 10, mouseY, getChartWidth(), mouseY);
      if (!this.outOfView) {
        this.g2.setColor(new Color(0, 0, 255, 80));
        this.g2.fillRect(mouseX + 10, mouseY, 50, 13);
        this.g2.setColor(Color.white);
        this.g2.drawString(this.timeFormatter.format(new Date(getTimeForScreenX(mouseX))), mouseX + 13, mouseY + 10);
      } else {
        this.g2.setColor(new Color(0, 0, 255, 80));
        this.g2.fillRect(mouseX + 10 - 70, mouseY, 50, 13);
        this.g2.setColor(Color.white);
        this.g2.drawString(this.timeFormatter.format(new Date(getTimeForScreenX(mouseX))), mouseX + 13 - 70, mouseY + 10);
      } 
    } 
  }
  
  protected void drawStopStation(Vector paramVector) {
    this.g2.setColor(Color.CYAN);
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector = paramVector.get(b);
      long l = ((Date)vector.get(0)).getTime();
      int i = ((Integer)vector.get(1)).intValue() - 60000;
      int j = getScreenXForTime(l);
      this.g2.drawLine(j, convertValueToScreenY(this.para.MinNum), j, convertValueToScreenY(this.para.MaxNum));
      if (ATPMessages.showChinese) {
        this.g2.drawString(Station.getStationChtName(i), j - 4, convertValueToScreenY(this.para.MaxNum));
      } else {
        this.g2.drawString(Station.getStationEngName(i) + "(" + i + ")", j - 4, convertValueToScreenY(this.para.MaxNum));
      } 
    } 
  }
  
  public int getLeftEdge() {
    return leftEdgeCache;
  }
  
  public int getRightEdge() {
    return rightEdgeCache;
  }
  
  public boolean isFollowMode() {
    return followMode;
  }
  
  public void isMessageOutOfView(boolean paramBoolean) {
    this.outOfView = paramBoolean;
  }
  
  public void paintBody(Graphics paramGraphics) throws Exception {
    this.g2 = (Graphics2D)paramGraphics;
    if (this.para.drawBody) {
      drawHorizontalGridLines();
      drawVerticalGridLines();
    } 
    if (this.para.drawValues)
      drawAxisLabels(); 
  }
  
  public void paintHeader(Graphics paramGraphics) throws Exception {
    this.g2h = (Graphics2D)paramGraphics;
    drawHeaderLabels(this.para.message);
  }
  
  public void resetScale() {}
  
  public void setDrawBody(boolean paramBoolean) {
    this.para.drawBody = paramBoolean;
  }
  
  public void setDrawType(int paramInt) {
    if (drawType != paramInt) {
      typeChanged = true;
      if (paramInt == 1) {
        drawType = 1;
      } else {
        drawType = 0;
      } 
      setScaleData();
      typeChanged = false;
    } 
  }
  
  public void setDrawValue(boolean paramBoolean) {
    this.para.drawValues = paramBoolean;
  }
  
  public void setEdge(int[] paramArrayOfint) {
    setLeftEdge(paramArrayOfint[0]);
    setRightEdge(paramArrayOfint[1]);
  }
  
  public void setFollowMode(boolean paramBoolean) {
    followMode = paramBoolean;
  }
  
  public void setLeftEdge(int paramInt) {
    leftEdgeCache = paramInt;
  }
  
  public void setMouseXY(int paramInt1, int paramInt2) {
    mouseX = paramInt1;
    mouseY = paramInt2;
  }
  
  public void setRightEdge(int paramInt) {
    rightEdgeCache = paramInt;
  }
  
  public void setScaleData() {
    if (typeChanged) {
      if (drawType == 1) {
        Collections.sort(this.BaseData, (Comparator)new ColumnComparator(0, true));
      } else {
        Collections.sort(this.BaseData, (Comparator)new ColumnComparator(1, true));
      } 
      time = new Date[this.BaseData.size()];
      location = new int[this.BaseData.size()];
      cachedScreenPositions = new int[this.BaseData.size()];
      if (this.BaseData.size() > 0) {
        startTime = ((Date)((Vector)this.BaseData.get(0)).get(0)).getTime();
        endTime = startTime;
        startLocation = ((Integer)((Vector)this.BaseData.get(0)).get(1)).intValue();
        endLocation = startLocation;
      } 
      for (byte b1 = 0; b1 < this.BaseData.size(); b1++) {
        Vector vector = this.BaseData.get(b1);
        time[b1] = vector.get(0);
        location[b1] = ((Integer)vector.get(1)).intValue();
        startTime = (time[b1].getTime() < startTime) ? time[b1].getTime() : startTime;
        endTime = (time[b1].getTime() > endTime) ? time[b1].getTime() : endTime;
        startLocation = (location[b1] < startLocation) ? location[b1] : startLocation;
        endLocation = (location[b1] > endLocation) ? location[b1] : endLocation;
      } 
      calculateScaleBounds();
      for (byte b2 = 0; b2 < this.BaseData.size(); b2++)
        cachedScreenPositions[b2] = (drawType == 1) ? convertTimeToScreenX(time[b2].getTime()) : convertDistanceToScreenX(location[b2]); 
    } 
  }
  
  private void calculateScaleBounds() {
    if (drawType == 1) {
      long l1 = (long)((3 * this.cps.intervalX) * this.cps.dpiX * 1000.0D);
      long l2 = startTime % l1;
      if (l2 != 0L) {
        scaleStartTime = startTime - l2;
      } else {
        scaleStartTime = startTime - l1;
      } 
      l2 = endTime % l1;
      if (l2 != 0L) {
        scaleEndTime = endTime + l1 - l2;
      } else {
        scaleEndTime = endTime;
      } 
    } else {
      int i = (int)((3 * this.cps.intervalDistX) * this.cps.dpiDistX);
      int j = startLocation % i;
      if (j != 0) {
        scaleStartLocation = startLocation - j;
      } else {
        scaleStartLocation = startLocation - i;
      } 
      j = endLocation % i;
      if (j != 0) {
        scaleEndLocation = endLocation + i - j;
      } else {
        scaleEndLocation = endLocation;
      } 
    } 
  }
  
  protected boolean isTimeInView(long time) {
    return true;
  }
  
  public int getChartHeight() {
    null = 0;
    return Math.abs(convertValueToScreenY(this.para.MaxNum) - convertValueToScreenY(this.para.MinNum));
  }
  
  public int getChartWidth() {
    return (drawType == 1) ? (int)(((scaleEndTime - scaleStartTime) / 1000L) / this.cps.dpiX) : (int)((scaleEndLocation - scaleStartLocation) / this.cps.dpiDistX);
  }
  
  private void drawHeaderLabels(String paramString) {
    this.g2h.setColor(this.cps.charColor);
    this.g2h.drawString(paramString, 5, this.para.basePointY() - getChartHeight() - 15);
    for (int i = this.para.MinNum; i <= this.para.MaxNum; i = (int)(i + this.para.intervalY * this.para.dpiY)) {
      this.g2h.setColor(this.cps.charColor);
      this.g2h.drawString("" + i, this.cps.headerWidth - 25, convertValueToScreenY(i));
      this.g2h.setColor(this.cps.mainLineColor);
      this.g2h.drawLine(this.cps.headerWidth - 1, convertValueToScreenY(i), this.cps.headerWidth - 3, convertValueToScreenY(i));
    } 
    this.g2h.drawLine(this.cps.headerWidth - 1, convertValueToScreenY(this.para.MaxNum) - 10, this.cps.headerWidth - 1, convertValueToScreenY(this.para.MinNum));
  }
  
  /**
   * Converts a value (speed, distance, etc.) to screen Y coordinate.
   * Formula: screenY = basePointY - (value - MinNum) / dpiY
   * 
   * @param value the real-world value to convert (e.g., speed in km/h)
   * @return the corresponding Y coordinate on the screen in pixels
   */
  public int convertValueToScreenY(int value) {
    null = 0;
    return this.para.basePointY() - (int)((value - this.para.MinNum) / this.para.dpiY);
  }
  
  /**
   * Gets the screen X coordinate for a given time value by looking it up in the cached positions.
   * 
   * @param time the time in milliseconds since epoch
   * @return the corresponding X coordinate on the screen in pixels
   */
  public int getScreenXForTime(long time) {
    int i = Arrays.binarySearch((Object[])time, new Date(time));
    if (i < 0)
      try {
        return cachedScreenPositions[Math.abs(i) - 1];
      } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        return cachedScreenPositions[cachedScreenPositions.length - 1];
      } catch (Exception exception) {
        exception.printStackTrace();
        return 0;
      }  
    return cachedScreenPositions[i];
  }
  
  /**
   * Converts screen X coordinate back to time value.
   * Inverse of convertTimeToScreenX.
   * Formula: time = (screenX - basePointX) * dpiX * 1000 + scaleStartTime
   * 
   * @return the time in milliseconds for the current mouse position
   */
  public long getTimeForMouseX() {
    return getTimeForScreenX(mouseX);
  }
  
  /**
   * Converts screen X coordinate back to time value.
   * 
   * @param screenX the X coordinate on the screen in pixels
   * @return the time in milliseconds since epoch
   */
  public long getTimeForScreenX(int screenX) {
    null = 0L;
    return (long)((screenX - this.cps.basePointX) * this.cps.dpiX * 1000.0D + scaleStartTime);
  }
}
