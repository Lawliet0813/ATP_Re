// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.drawGraphics;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import com.MiTAC.TRA.ATP.Tools.SortTable.ColumnComparator;
import java.awt.Graphics;
import com.MiTAC.TRA.ATP.core.Station;
import com.MiTAC.TRA.ATP.ATPMessages;
import java.awt.Color;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.Graphics2D;
import java.util.Vector;

public abstract class DrawBase
{
    protected Vector BaseData;
    private static int _$18157;
    private static int _$18156;
    private static int _$2995;
    protected commonParaSetting cps;
    private static int _$18161;
    private static long _$18159;
    private static int _$18160;
    private static long _$18158;
    public static final int drawByDistance = 0;
    public static final int drawByTime = 1;
    protected static int drawType;
    protected static int endLocation;
    protected static long endTime;
    protected Graphics2D g2;
    protected Graphics2D g2h;
    private static boolean _$18155;
    protected static int[] location;
    protected static int mouseX;
    protected static int mouseY;
    protected boolean outOfView;
    protected drawParameters para;
    private static int[] _$18154;
    private SimpleDateFormat _$3906;
    protected static int startLocation;
    protected static long startTime;
    protected static Date[] time;
    protected static boolean typeChanged;
    
    static {
        DrawBase._$2995 = 0;
        DrawBase.drawType = -1;
        DrawBase.typeChanged = true;
        DrawBase._$18155 = false;
        DrawBase.mouseX = 0;
        DrawBase.mouseY = 0;
    }
    
    public DrawBase(final commonParaSetting cps, final drawParameters para, final Vector baseData) {
        this.outOfView = false;
        this._$3906 = new SimpleDateFormat("HH:mm:ss");
        this.cps = cps;
        this.para = para;
        this.BaseData = baseData;
        DrawBase.drawType = (this.cps.drawByDist ? 0 : 1);
        this.setScaleData();
    }
    
    protected int arrangesoldierPosition(final int n) {
        return (int)(this.cps.basePointX + (n - DrawBase._$18160) / this.cps.dpiDistX);
    }
    
    protected int arrangesoldierPosition(final long n) {
        return this.cps.basePointX + (int)((n - DrawBase._$18158) / 1000L / this.cps.dpiX);
    }
    
    protected void drawBasicMessage() {
    }
    
    private void _$18198() {
        if (DrawBase.drawType == 1) {
            for (int i = this.para.MinNum; i <= this.para.MaxNum; i += (int)(this.para.intervalY * this.para.dpiY)) {
                if (i == this.para.MinNum) {
                    this.g2.setColor(this.cps.mainLineColor);
                }
                else {
                    this.g2.setColor(this.cps.baseLineColor);
                }
                this.g2.drawLine(this.arrangesoldierPosition(DrawBase._$18158), this.showsoldierRangeToDefence(i), this.arrangesoldierPosition(DrawBase._$18159), this.showsoldierRangeToDefence(i));
            }
            if (this.para.MinNum <= 0 && this.para.MaxNum >= 0) {
                this.g2.setColor(this.cps.mainLineColor);
                this.g2.drawLine(this.arrangesoldierPosition(DrawBase._$18158), this.showsoldierRangeToDefence(0), this.arrangesoldierPosition(DrawBase._$18159), this.showsoldierRangeToDefence(0));
            }
        }
        else {
            for (int j = this.para.MinNum; j <= this.para.MaxNum; j += (int)(this.para.intervalY * this.para.dpiY)) {
                if (j == this.para.MinNum) {
                    this.g2.setColor(this.cps.mainLineColor);
                }
                else {
                    this.g2.setColor(this.cps.baseLineColor);
                }
                this.g2.drawLine(this.arrangesoldierPosition(DrawBase._$18160), this.showsoldierRangeToDefence(j), this.arrangesoldierPosition(DrawBase._$18161), this.showsoldierRangeToDefence(j));
            }
            if (this.para.MinNum <= 0 && this.para.MaxNum >= 0) {
                this.g2.setColor(this.cps.mainLineColor);
                this.g2.drawLine(this.arrangesoldierPosition(DrawBase._$18160), this.showsoldierRangeToDefence(0), this.arrangesoldierPosition(DrawBase._$18161), this.showsoldierRangeToDefence(0));
            }
        }
    }
    
    private void _$18196() {
        int n = 0;
        if (DrawBase.drawType == 1) {
            for (long $18158 = DrawBase._$18158; $18158 <= DrawBase._$18159; $18158 += (long)(this.cps.intervalX * this.cps.dpiX * 1000.0)) {
                if (n % this.cps.BrightLine == 0) {
                    this.g2.setColor(this.cps.baseLineColor_light);
                }
                else {
                    this.g2.setColor(this.cps.baseLineColor);
                }
                this.g2.drawLine(this.arrangesoldierPosition($18158), this.showsoldierRangeToDefence(this.para.MaxNum), this.arrangesoldierPosition($18158), this.showsoldierRangeToDefence(this.para.MinNum));
                ++n;
            }
        }
        else {
            for (int i = DrawBase._$18160; i <= DrawBase._$18161; i += (int)(this.cps.intervalDistX * this.cps.dpiDistX)) {
                if (n % this.cps.BrightLine == 0) {
                    this.g2.setColor(this.cps.baseLineColor_light);
                }
                else {
                    this.g2.setColor(this.cps.baseLineColor);
                }
                this.g2.drawLine(this.arrangesoldierPosition(i), this.showsoldierRangeToDefence(this.para.MaxNum), this.arrangesoldierPosition(i), this.showsoldierRangeToDefence(this.para.MinNum));
                ++n;
            }
        }
    }
    
    private void _$18192() {
        int n = 0;
        if (DrawBase.drawType == 1) {
            for (long $18158 = DrawBase._$18158; $18158 <= DrawBase._$18159; $18158 += (long)(this.cps.intervalX * this.cps.dpiX * 1000.0)) {
                if (n % this.cps.BrightLine == 0) {
                    this.g2.setColor(this.cps.charColor);
                    this.g2.drawString(this._$3906.format(new Date($18158)), this.arrangesoldierPosition($18158) - 20, this.para.basePointY() + 12);
                }
                ++n;
            }
        }
        else {
            for (int i = DrawBase._$18160; i <= DrawBase._$18161; i += (int)(this.cps.intervalDistX * this.cps.dpiDistX)) {
                if (n % this.cps.BrightLine == 0) {
                    this.g2.setColor(this.cps.charColor);
                    this.g2.drawString(i / 100 + "m", this.arrangesoldierPosition(i) - 20, this.para.basePointY() + 12);
                }
                ++n;
            }
        }
    }
    
    public void drawScanner() {
        this.g2.setColor(this.cps.mouse);
        if (DrawBase.mouseX > 0 && DrawBase.mouseX < this.showBattleLingDepth() && DrawBase.mouseY > this.para.UpperBound && DrawBase.mouseY < this.para.UpperBound + this.showBattleLineRange()) {
            this.g2.drawLine(DrawBase.mouseX, this.para.UpperBound, DrawBase.mouseX, (DrawBase.mouseY - 10 < this.para.UpperBound) ? this.para.UpperBound : (DrawBase.mouseY - 10));
            this.g2.drawLine(DrawBase.mouseX, (DrawBase.mouseY + 10 > this.showBattleLineRange() + this.para.UpperBound) ? (this.showBattleLineRange() + this.para.UpperBound) : (DrawBase.mouseY + 10), DrawBase.mouseX, this.showBattleLineRange() + this.para.UpperBound);
            this.g2.drawLine(0, DrawBase.mouseY, DrawBase.mouseX - 10, DrawBase.mouseY);
            this.g2.drawLine(DrawBase.mouseX + 10, DrawBase.mouseY, this.showBattleLingDepth(), DrawBase.mouseY);
            if (!this.outOfView) {
                this.g2.setColor(new Color(0, 0, 255, 80));
                this.g2.fillRect(DrawBase.mouseX + 10, DrawBase.mouseY, 50, 13);
                this.g2.setColor(Color.white);
                this.g2.drawString(this._$3906.format(new Date(this.soloderReport(DrawBase.mouseX))), DrawBase.mouseX + 13, DrawBase.mouseY + 10);
            }
            else {
                this.g2.setColor(new Color(0, 0, 255, 80));
                this.g2.fillRect(DrawBase.mouseX + 10 - 70, DrawBase.mouseY, 50, 13);
                this.g2.setColor(Color.white);
                this.g2.drawString(this._$3906.format(new Date(this.soloderReport(DrawBase.mouseX))), DrawBase.mouseX + 13 - 70, DrawBase.mouseY + 10);
            }
        }
    }
    
    protected void drawStopStation(final Vector vector) {
        this.g2.setColor(Color.CYAN);
        for (int i = 0; i < vector.size(); ++i) {
            final Vector vector2 = vector.get(i);
            final long time = ((Date)vector2.get(0)).getTime();
            final int j = (int)vector2.get(1) - 60000;
            final int showsoldierWhereToStand = this.showsoldierWhereToStand(time);
            this.g2.drawLine(showsoldierWhereToStand, this.showsoldierRangeToDefence(this.para.MinNum), showsoldierWhereToStand, this.showsoldierRangeToDefence(this.para.MaxNum));
            if (ATPMessages.showChinese) {
                this.g2.drawString(Station.getStationChtName(j), showsoldierWhereToStand - 4, this.showsoldierRangeToDefence(this.para.MaxNum));
            }
            else {
                this.g2.drawString(Station.getStationEngName(j) + "(" + j + ")", showsoldierWhereToStand - 4, this.showsoldierRangeToDefence(this.para.MaxNum));
            }
        }
    }
    
    public int getLeftEdge() {
        return DrawBase._$18157;
    }
    
    public int getRightEdge() {
        return DrawBase._$18156;
    }
    
    public boolean isFollowMode() {
        return DrawBase._$18155;
    }
    
    public void isMessageOutOfView(final boolean outOfView) {
        this.outOfView = outOfView;
    }
    
    public void paintBody(final Graphics graphics) throws Exception {
        this.g2 = (Graphics2D)graphics;
        if (this.para.drawBody) {
            this._$18198();
            this._$18196();
        }
        if (this.para.drawValues) {
            this._$18192();
        }
    }
    
    public void paintHeader(final Graphics graphics) throws Exception {
        this.g2h = (Graphics2D)graphics;
        this._$18200(this.para.message);
    }
    
    public void resetScale() {
    }
    
    public void setDrawBody(final boolean drawBody) {
        this.para.drawBody = drawBody;
    }
    
    public void setDrawType(final int n) {
        if (DrawBase.drawType != n) {
            DrawBase.typeChanged = true;
            if (n == 1) {
                DrawBase.drawType = 1;
            }
            else {
                DrawBase.drawType = 0;
            }
            this.setScaleData();
            DrawBase.typeChanged = false;
        }
    }
    
    public void setDrawValue(final boolean drawValues) {
        this.para.drawValues = drawValues;
    }
    
    public void setEdge(final int[] array) {
        this.setLeftEdge(array[0]);
        this.setRightEdge(array[1]);
    }
    
    public void setFollowMode(final boolean $18155) {
        DrawBase._$18155 = $18155;
    }
    
    public void setLeftEdge(final int $18157) {
        DrawBase._$18157 = $18157;
    }
    
    public void setMouseXY(final int mouseX, final int mouseY) {
        DrawBase.mouseX = mouseX;
        DrawBase.mouseY = mouseY;
    }
    
    public void setRightEdge(final int $18156) {
        DrawBase._$18156 = $18156;
    }
    
    public void setScaleData() {
        if (DrawBase.typeChanged) {
            if (DrawBase.drawType == 1) {
                Collections.sort((List<Object>)this.BaseData, (Comparator<? super Object>)new ColumnComparator(0, true));
            }
            else {
                Collections.sort((List<Object>)this.BaseData, (Comparator<? super Object>)new ColumnComparator(1, true));
            }
            DrawBase.time = new Date[this.BaseData.size()];
            DrawBase.location = new int[this.BaseData.size()];
            DrawBase._$18154 = new int[this.BaseData.size()];
            if (this.BaseData.size() > 0) {
                DrawBase.startTime = this.BaseData.get(0).get(0).getTime();
                DrawBase.endTime = DrawBase.startTime;
                DrawBase.startLocation = this.BaseData.get(0).get(1);
                DrawBase.endLocation = DrawBase.startLocation;
            }
            for (int i = 0; i < this.BaseData.size(); ++i) {
                final Vector vector = this.BaseData.get(i);
                DrawBase.time[i] = (Date)vector.get(0);
                DrawBase.location[i] = (int)vector.get(1);
                DrawBase.startTime = ((DrawBase.time[i].getTime() < DrawBase.startTime) ? DrawBase.time[i].getTime() : DrawBase.startTime);
                DrawBase.endTime = ((DrawBase.time[i].getTime() > DrawBase.endTime) ? DrawBase.time[i].getTime() : DrawBase.endTime);
                DrawBase.startLocation = ((DrawBase.location[i] < DrawBase.startLocation) ? DrawBase.location[i] : DrawBase.startLocation);
                DrawBase.endLocation = ((DrawBase.location[i] > DrawBase.endLocation) ? DrawBase.location[i] : DrawBase.endLocation);
            }
            this._$18175();
            for (int j = 0; j < this.BaseData.size(); ++j) {
                DrawBase._$18154[j] = ((DrawBase.drawType == 1) ? this.arrangesoldierPosition(DrawBase.time[j].getTime()) : this.arrangesoldierPosition(DrawBase.location[j]));
            }
        }
    }
    
    private void _$18175() {
        if (DrawBase.drawType == 1) {
            final long n = (long)(3 * this.cps.intervalX * this.cps.dpiX * 1000.0);
            final long n2 = DrawBase.startTime % n;
            if (n2 != 0L) {
                DrawBase._$18158 = DrawBase.startTime - n2;
            }
            else {
                DrawBase._$18158 = DrawBase.startTime - n;
            }
            final long n3 = DrawBase.endTime % n;
            if (n3 != 0L) {
                DrawBase._$18159 = DrawBase.endTime + (n - n3);
            }
            else {
                DrawBase._$18159 = DrawBase.endTime;
            }
        }
        else {
            final int n4 = (int)(3 * this.cps.intervalDistX * this.cps.dpiDistX);
            final int n5 = DrawBase.startLocation % n4;
            if (n5 != 0) {
                DrawBase._$18160 = DrawBase.startLocation - n5;
            }
            else {
                DrawBase._$18160 = DrawBase.startLocation - n4;
            }
            final int n6 = DrawBase.endLocation % n4;
            if (n6 != 0) {
                DrawBase._$18161 = DrawBase.endLocation + (n4 - n6);
            }
            else {
                DrawBase._$18161 = DrawBase.endLocation;
            }
        }
    }
    
    protected boolean shouldsoldierJoinThisWar(final long n) {
        return true;
    }
    
    public int showBattleLineRange() {
        return Math.abs(this.showsoldierRangeToDefence(this.para.MaxNum) - this.showsoldierRangeToDefence(this.para.MinNum));
    }
    
    public int showBattleLingDepth() {
        if (DrawBase.drawType == 1) {
            return (int)((DrawBase._$18159 - DrawBase._$18158) / 1000L / this.cps.dpiX);
        }
        return (int)((DrawBase._$18161 - DrawBase._$18160) / this.cps.dpiDistX);
    }
    
    private void _$18200(final String s) {
        this.g2h.setColor(this.cps.charColor);
        this.g2h.drawString(s, 5, this.para.basePointY() - this.showBattleLineRange() - 15);
        for (int i = this.para.MinNum; i <= this.para.MaxNum; i += (int)(this.para.intervalY * this.para.dpiY)) {
            this.g2h.setColor(this.cps.charColor);
            this.g2h.drawString("" + i, this.cps.headerWidth - 25, this.showsoldierRangeToDefence(i));
            this.g2h.setColor(this.cps.mainLineColor);
            this.g2h.drawLine(this.cps.headerWidth - 1, this.showsoldierRangeToDefence(i), this.cps.headerWidth - 3, this.showsoldierRangeToDefence(i));
        }
        this.g2h.drawLine(this.cps.headerWidth - 1, this.showsoldierRangeToDefence(this.para.MaxNum) - 10, this.cps.headerWidth - 1, this.showsoldierRangeToDefence(this.para.MinNum));
    }
    
    public int showsoldierRangeToDefence(final int n) {
        return this.para.basePointY() - (int)((n - this.para.MinNum) / this.para.dpiY);
    }
    
    public int showsoldierWhereToStand(final long date) {
        final int binarySearch = Arrays.binarySearch(DrawBase.time, new Date(date));
        if (binarySearch < 0) {
            try {
                return DrawBase._$18154[Math.abs(binarySearch) - 1];
            }
            catch (final ArrayIndexOutOfBoundsException ex) {
                return DrawBase._$18154[DrawBase._$18154.length - 1];
            }
            catch (final Exception ex2) {
                ex2.printStackTrace();
                return 0;
            }
        }
        return DrawBase._$18154[binarySearch];
    }
    
    public long soloderReport() {
        return this.soloderReport(DrawBase.mouseX);
    }
    
    public long soloderReport(final int n) {
        return (long)((n - this.cps.basePointX) * this.cps.dpiX * 1000.0 + DrawBase._$18158);
    }
}
