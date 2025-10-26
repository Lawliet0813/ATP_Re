// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.drawGraphics;

import java.awt.Stroke;
import java.awt.Color;
import java.util.Vector;
import java.awt.Graphics;

public interface drawATP
{
    void paintBody(final Graphics p0) throws Exception;
    
    void paintHeader(final Graphics p0) throws Exception;
    
    void setData(final Vector p0) throws Exception;
    
    void setDataLineColor(final Color p0) throws Exception;
    
    void setDrawCurve(final boolean p0) throws Exception;
    
    void setStroke(final Stroke p0);
}
