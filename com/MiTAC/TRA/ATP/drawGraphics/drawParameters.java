// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.drawGraphics;

public class drawParameters implements Cloneable
{
    public int MaxNum;
    public int MinNum;
    public int UpperBound;
    public double dpiY;
    public boolean drawBody;
    public boolean drawValues;
    public int intervalY;
    public String message;
    public int sequence;
    
    public drawParameters() {
        this.MaxNum = 130;
        this.MinNum = 0;
        this.dpiY = 1.0;
        this.intervalY = 20;
        this.message = "";
        this.UpperBound = 0;
        this.sequence = 0;
        this.drawValues = true;
        this.drawBody = true;
    }
    
    public int basePointY() {
        return (int)(this.UpperBound + (this.MaxNum - this.MinNum) / this.dpiY);
    }
    
    public Object clone() {
        final drawParameters drawParameters = new drawParameters();
        drawParameters.dpiY = this.dpiY;
        drawParameters.drawBody = this.drawBody;
        drawParameters.drawValues = this.drawValues;
        drawParameters.intervalY = this.intervalY;
        drawParameters.MaxNum = this.MaxNum;
        drawParameters.message = this.message;
        drawParameters.MinNum = this.MinNum;
        drawParameters.sequence = this.sequence;
        drawParameters.UpperBound = this.UpperBound;
        return drawParameters;
    }
}
