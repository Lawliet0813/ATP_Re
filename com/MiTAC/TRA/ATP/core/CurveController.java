// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.core;

import java.awt.Graphics;
import com.MiTAC.TRA.ATP.drawGraphics.DrawContinuous;
import java.util.Vector;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.drawGraphics.drawATP;

public class CurveController
{
    private boolean _$29760;
    private boolean _$29762;
    private boolean _$29764;
    private boolean _$29766;
    private boolean _$29768;
    private drawATP[] _$29761;
    private drawATP[] _$29763;
    private drawATP[] _$29765;
    private drawATP[] _$29767;
    private drawATP[] _$29769;
    private ATPMissionDetail _$29770;
    private InitParameters _$2498;
    
    public CurveController() {
        this._$29760 = true;
        this._$29761 = new drawATP[5];
        this._$29762 = true;
        this._$29763 = new drawATP[5];
        this._$29764 = true;
        this._$29765 = new drawATP[5];
        this._$29766 = true;
        this._$29767 = new drawATP[5];
        this._$29768 = true;
        this._$29769 = new drawATP[5];
    }
    
    public void CurverController(final ATPMissionDetail $29770, final InitParameters $29771) {
        this._$29770 = $29770;
        this._$2498 = $29771;
        this._$29761[0] = (drawATP)new DrawContinuous(new commonParaSetting(), new drawParameters(), new Vector());
    }
    
    public void graphicDialog() {
    }
    
    public void paint(final Graphics graphics) {
    }
    
    public void search() {
    }
}
