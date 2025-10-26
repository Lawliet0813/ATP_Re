// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.drawGraphics;

import java.awt.Color;
import java.awt.Graphics;

public class commonParaSetting
{
    public Graphics BodyG;
    public int BrightLine;
    public Graphics HeaderG;
    public Color backgroundColor;
    public Color baseLineColor;
    public Color baseLineColor_light;
    public int basePointX;
    public Color charColor;
    public double dpiDistX;
    public double dpiX;
    public boolean drawByDist;
    public int endLocation;
    public long endTime;
    public int headerHeight;
    public int headerWidth;
    public int intervalDistX;
    public int intervalX;
    public Color mainLineColor;
    public Color mouse;
    public int startLocation;
    public long startTime;
    public Color stationColor;
    
    public commonParaSetting() {
        this.backgroundColor = Color.black;
        this.baseLineColor = Color.darkGray;
        this.baseLineColor_light = Color.gray;
        this.mainLineColor = Color.white;
        this.charColor = Color.white;
        this.headerHeight = 20;
        this.stationColor = Color.pink;
        this.mouse = Color.BLUE;
        this.dpiX = 1.0;
        this.dpiDistX = 1.0;
        this.drawByDist = true;
        this.intervalX = 20;
        this.intervalDistX = 20;
        this.BrightLine = 3;
        this.basePointX = -1;
        this.headerWidth = 60;
        this.startTime = 0L;
        this.endTime = 1000000L;
        this.startLocation = 0;
        this.endLocation = 100;
    }
}
