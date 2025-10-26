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
  
  private static int _$18157;
  
  private static int _$18156;
  
  private static int _$2995 = 0;
  
  protected commonParaSetting cps;
  
  private static int _$18161;
  
  private static long _$18159;
  
  private static int _$18160;
  
  private static long _$18158;
  
  public static final int drawByDistance = 0;
  
  public static final int drawByTime = 1;
  
  protected static int drawType = -1;
  
  protected static int endLocation;
  
  protected static long endTime;
  
  protected Graphics2D g2;
  
  protected Graphics2D g2h;
  
  private static boolean _$18155;
  
  protected static int[] location;
  
  protected static int mouseX;
  
  protected static int mouseY;
  
  protected boolean outOfView = false;
  
  protected drawParameters para;
  
  private static int[] _$18154;
  
  private SimpleDateFormat _$3906 = new SimpleDateFormat("HH:mm:ss");
  
  protected static int startLocation;
  
  protected static long startTime;
  
  protected static Date[] time;
  
  protected static boolean typeChanged = true;
  
  static {
    _$18155 = false;
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
  
  protected int arrangesoldierPosition(int paramInt) {
    null = 0;
    return (int)(this.cps.basePointX + (paramInt - _$18160) / this.cps.dpiDistX);
  }
  
  protected int arrangesoldierPosition(long paramLong) {
    null = 0;
    return this.cps.basePointX + (int)(((paramLong - _$18158) / 1000L) / this.cps.dpiX);
  }
  
  protected void drawBasicMessage() {}
  
  private void _$18198() {
    if (drawType == 1) {
      for (int i = this.para.MinNum; i <= this.para.MaxNum; i = (int)(i + this.para.intervalY * this.para.dpiY)) {
        if (i == this.para.MinNum) {
          this.g2.setColor(this.cps.mainLineColor);
        } else {
          this.g2.setColor(this.cps.baseLineColor);
        } 
        this.g2.drawLine(arrangesoldierPosition(_$18158), showsoldierRangeToDefence(i), arrangesoldierPosition(_$18159), showsoldierRangeToDefence(i));
      } 
      if (this.para.MinNum <= 0 && this.para.MaxNum >= 0) {
        this.g2.setColor(this.cps.mainLineColor);
        this.g2.drawLine(arrangesoldierPosition(_$18158), showsoldierRangeToDefence(0), arrangesoldierPosition(_$18159), showsoldierRangeToDefence(0));
      } 
    } else {
      for (int i = this.para.MinNum; i <= this.para.MaxNum; i = (int)(i + this.para.intervalY * this.para.dpiY)) {
        if (i == this.para.MinNum) {
          this.g2.setColor(this.cps.mainLineColor);
        } else {
          this.g2.setColor(this.cps.baseLineColor);
        } 
        this.g2.drawLine(arrangesoldierPosition(_$18160), showsoldierRangeToDefence(i), arrangesoldierPosition(_$18161), showsoldierRangeToDefence(i));
      } 
      if (this.para.MinNum <= 0 && this.para.MaxNum >= 0) {
        this.g2.setColor(this.cps.mainLineColor);
        this.g2.drawLine(arrangesoldierPosition(_$18160), showsoldierRangeToDefence(0), arrangesoldierPosition(_$18161), showsoldierRangeToDefence(0));
      } 
    } 
  }
  
  private void _$18196() {
    byte b = 0;
    if (drawType == 1) {
      for (long l = _$18158; l <= _$18159; l = (long)(l + this.cps.intervalX * this.cps.dpiX * 1000.0D)) {
        if (b % this.cps.BrightLine == 0) {
          this.g2.setColor(this.cps.baseLineColor_light);
        } else {
          this.g2.setColor(this.cps.baseLineColor);
        } 
        this.g2.drawLine(arrangesoldierPosition(l), showsoldierRangeToDefence(this.para.MaxNum), arrangesoldierPosition(l), showsoldierRangeToDefence(this.para.MinNum));
        b++;
      } 
    } else {
      for (int i = _$18160; i <= _$18161; i = (int)(i + this.cps.intervalDistX * this.cps.dpiDistX)) {
        if (b % this.cps.BrightLine == 0) {
          this.g2.setColor(this.cps.baseLineColor_light);
        } else {
          this.g2.setColor(this.cps.baseLineColor);
        } 
        this.g2.drawLine(arrangesoldierPosition(i), showsoldierRangeToDefence(this.para.MaxNum), arrangesoldierPosition(i), showsoldierRangeToDefence(this.para.MinNum));
        b++;
      } 
    } 
  }
  
  private void _$18192() {
    byte b = 0;
    if (drawType == 1) {
      for (long l = _$18158; l <= _$18159; l = (long)(l + this.cps.intervalX * this.cps.dpiX * 1000.0D)) {
        if (b % this.cps.BrightLine == 0) {
          this.g2.setColor(this.cps.charColor);
          this.g2.drawString(this._$3906.format(new Date(l)), arrangesoldierPosition(l) - 20, this.para.basePointY() + 12);
        } 
        b++;
      } 
    } else {
      for (int i = _$18160; i <= _$18161; i = (int)(i + this.cps.intervalDistX * this.cps.dpiDistX)) {
        if (b % this.cps.BrightLine == 0) {
          this.g2.setColor(this.cps.charColor);
          this.g2.drawString((i / 100) + "m", arrangesoldierPosition(i) - 20, this.para.basePointY() + 12);
        } 
        b++;
      } 
    } 
  }
  
  public void drawScanner() {
    this.g2.setColor(this.cps.mouse);
    if (mouseX > 0 && mouseX < showBattleLingDepth() && mouseY > this.para.UpperBound && mouseY < this.para.UpperBound + showBattleLineRange()) {
      this.g2.drawLine(mouseX, this.para.UpperBound, mouseX, (mouseY - 10 < this.para.UpperBound) ? this.para.UpperBound : (mouseY - 10));
      this.g2.drawLine(mouseX, (mouseY + 10 > showBattleLineRange() + this.para.UpperBound) ? (showBattleLineRange() + this.para.UpperBound) : (mouseY + 10), mouseX, showBattleLineRange() + this.para.UpperBound);
      this.g2.drawLine(0, mouseY, mouseX - 10, mouseY);
      this.g2.drawLine(mouseX + 10, mouseY, showBattleLingDepth(), mouseY);
      if (!this.outOfView) {
        this.g2.setColor(new Color(0, 0, 255, 80));
        this.g2.fillRect(mouseX + 10, mouseY, 50, 13);
        this.g2.setColor(Color.white);
        this.g2.drawString(this._$3906.format(new Date(soloderReport(mouseX))), mouseX + 13, mouseY + 10);
      } else {
        this.g2.setColor(new Color(0, 0, 255, 80));
        this.g2.fillRect(mouseX + 10 - 70, mouseY, 50, 13);
        this.g2.setColor(Color.white);
        this.g2.drawString(this._$3906.format(new Date(soloderReport(mouseX))), mouseX + 13 - 70, mouseY + 10);
      } 
    } 
  }
  
  protected void drawStopStation(Vector paramVector) {
    this.g2.setColor(Color.CYAN);
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector = paramVector.get(b);
      long l = ((Date)vector.get(0)).getTime();
      int i = ((Integer)vector.get(1)).intValue() - 60000;
      int j = showsoldierWhereToStand(l);
      this.g2.drawLine(j, showsoldierRangeToDefence(this.para.MinNum), j, showsoldierRangeToDefence(this.para.MaxNum));
      if (ATPMessages.showChinese) {
        this.g2.drawString(Station.getStationChtName(i), j - 4, showsoldierRangeToDefence(this.para.MaxNum));
      } else {
        this.g2.drawString(Station.getStationEngName(i) + "(" + i + ")", j - 4, showsoldierRangeToDefence(this.para.MaxNum));
      } 
    } 
  }
  
  public int getLeftEdge() {
    return _$18157;
  }
  
  public int getRightEdge() {
    return _$18156;
  }
  
  public boolean isFollowMode() {
    return _$18155;
  }
  
  public void isMessageOutOfView(boolean paramBoolean) {
    this.outOfView = paramBoolean;
  }
  
  public void paintBody(Graphics paramGraphics) throws Exception {
    this.g2 = (Graphics2D)paramGraphics;
    if (this.para.drawBody) {
      _$18198();
      _$18196();
    } 
    if (this.para.drawValues)
      _$18192(); 
  }
  
  public void paintHeader(Graphics paramGraphics) throws Exception {
    this.g2h = (Graphics2D)paramGraphics;
    _$18200(this.para.message);
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
    _$18155 = paramBoolean;
  }
  
  public void setLeftEdge(int paramInt) {
    _$18157 = paramInt;
  }
  
  public void setMouseXY(int paramInt1, int paramInt2) {
    mouseX = paramInt1;
    mouseY = paramInt2;
  }
  
  public void setRightEdge(int paramInt) {
    _$18156 = paramInt;
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
      _$18154 = new int[this.BaseData.size()];
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
      _$18175();
      for (byte b2 = 0; b2 < this.BaseData.size(); b2++)
        _$18154[b2] = (drawType == 1) ? arrangesoldierPosition(time[b2].getTime()) : arrangesoldierPosition(location[b2]); 
    } 
  }
  
  private void _$18175() {
    if (drawType == 1) {
      long l1 = (long)((3 * this.cps.intervalX) * this.cps.dpiX * 1000.0D);
      long l2 = startTime % l1;
      if (l2 != 0L) {
        _$18158 = startTime - l2;
      } else {
        _$18158 = startTime - l1;
      } 
      l2 = endTime % l1;
      if (l2 != 0L) {
        _$18159 = endTime + l1 - l2;
      } else {
        _$18159 = endTime;
      } 
    } else {
      int i = (int)((3 * this.cps.intervalDistX) * this.cps.dpiDistX);
      int j = startLocation % i;
      if (j != 0) {
        _$18160 = startLocation - j;
      } else {
        _$18160 = startLocation - i;
      } 
      j = endLocation % i;
      if (j != 0) {
        _$18161 = endLocation + i - j;
      } else {
        _$18161 = endLocation;
      } 
    } 
  }
  
  protected boolean shouldsoldierJoinThisWar(long paramLong) {
    return true;
  }
  
  public int showBattleLineRange() {
    null = 0;
    return Math.abs(showsoldierRangeToDefence(this.para.MaxNum) - showsoldierRangeToDefence(this.para.MinNum));
  }
  
  public int showBattleLingDepth() {
    return (drawType == 1) ? (int)(((_$18159 - _$18158) / 1000L) / this.cps.dpiX) : (int)((_$18161 - _$18160) / this.cps.dpiDistX);
  }
  
  private void _$18200(String paramString) {
    this.g2h.setColor(this.cps.charColor);
    this.g2h.drawString(paramString, 5, this.para.basePointY() - showBattleLineRange() - 15);
    for (int i = this.para.MinNum; i <= this.para.MaxNum; i = (int)(i + this.para.intervalY * this.para.dpiY)) {
      this.g2h.setColor(this.cps.charColor);
      this.g2h.drawString("" + i, this.cps.headerWidth - 25, showsoldierRangeToDefence(i));
      this.g2h.setColor(this.cps.mainLineColor);
      this.g2h.drawLine(this.cps.headerWidth - 1, showsoldierRangeToDefence(i), this.cps.headerWidth - 3, showsoldierRangeToDefence(i));
    } 
    this.g2h.drawLine(this.cps.headerWidth - 1, showsoldierRangeToDefence(this.para.MaxNum) - 10, this.cps.headerWidth - 1, showsoldierRangeToDefence(this.para.MinNum));
  }
  
  public int showsoldierRangeToDefence(int paramInt) {
    null = 0;
    return this.para.basePointY() - (int)((paramInt - this.para.MinNum) / this.para.dpiY);
  }
  
  public int showsoldierWhereToStand(long paramLong) {
    int i = Arrays.binarySearch((Object[])time, new Date(paramLong));
    if (i < 0)
      try {
        return _$18154[Math.abs(i) - 1];
      } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        return _$18154[_$18154.length - 1];
      } catch (Exception exception) {
        exception.printStackTrace();
        return 0;
      }  
    return _$18154[i];
  }
  
  public long soloderReport() {
    return soloderReport(mouseX);
  }
  
  public long soloderReport(int paramInt) {
    null = 0L;
    return (long)((paramInt - this.cps.basePointX) * this.cps.dpiX * 1000.0D + _$18158);
  }
}
