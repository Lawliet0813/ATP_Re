// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.drawGraphics;

import com.MiTAC.TRA.ATP.ATPMessages;
import java.awt.Stroke;
import java.awt.Graphics;
import java.util.Vector;
import java.util.Date;
import java.awt.Color;

public class DrawFailureMsg extends DrawBase implements drawATP
{
    public static final int CabinFailure = 1;
    private int _$26710;
    public static final int WaysideFailure = 0;
    private Color _$18217;
    private Date[] _$18215;
    private Object[][] _$18216;
    private boolean _$18218;
    private Vector _$18214;
    private int[] _$1632;
    private int _$2019;
    
    public DrawFailureMsg(final commonParaSetting commonParaSetting, final drawParameters drawParameters, final Vector vector, final int $26710) {
        super(commonParaSetting, drawParameters, vector);
        this._$26710 = 0;
        this._$18218 = true;
        this._$26710 = $26710;
    }
    
    private void _$26711() {
        this.g2.setColor(this._$18217);
        for (int i = 0; i < this._$1632.length; ++i) {
            this.g2.drawLine(this._$1632[i], this._$2019, this._$1632[i], this._$2019 - 50);
            this.g2.drawLine(this._$1632[i] - 5, this._$2019 - 5, this._$1632[i], this._$2019);
            this.g2.drawLine(this._$1632[i] + 5, this._$2019 - 5, this._$1632[i], this._$2019);
        }
    }
    
    public void paintBody(final Graphics graphics) throws Exception {
        super.paintBody(graphics);
        if (this._$18218) {
            this._$18223();
        }
    }
    
    public void paintHeader(final Graphics graphics) throws Exception {
        super.paintHeader(graphics);
    }
    
    public void resetScale() {
        if (this._$18214 != null && this._$18214.size() != 0) {
            this._$18215 = new Date[this._$18214.size()];
            this._$18216 = new Object[this._$18214.size()][3];
            this._$1632 = new int[this._$18214.size()];
            this._$2019 = this.showsoldierRangeToDefence(this.para.MaxNum);
            for (int i = 0; i < this._$18214.size(); ++i) {
                final Vector vector = this._$18214.get(i);
                if (this._$26710 == 1) {
                    this._$18215[i] = (Date)vector.get(4);
                    this._$18216[i][0] = vector.get(5);
                    this._$18216[i][1] = vector.get(6);
                    this._$18216[i][2] = vector.get(7);
                }
                else {
                    this._$18215[i] = (Date)vector.get(3);
                    this._$18216[i][0] = vector.get(4);
                    this._$18216[i][1] = vector.get(5);
                    this._$18216[i][2] = vector.get(6);
                }
                this._$1632[i] = this.showsoldierWhereToStand(this._$18215[i].getTime());
            }
        }
    }
    
    public void setData(final Vector $18214) throws Exception {
        this._$18214 = $18214;
        this.resetScale();
    }
    
    public void setDataLineColor(final Color $18217) throws Exception {
        this._$18217 = $18217;
    }
    
    public void setDrawCurve(final boolean $18218) {
        this._$18218 = $18218;
    }
    
    public void setStroke(final Stroke stroke) {
    }
    
    private void _$18223() {
        if (this._$18214 != null && this._$18214.size() != 0) {
            this._$26711();
            this._$26712();
        }
    }
    
    private void _$26712() {
        this.g2.setColor(this._$18217);
        String str;
        if (this._$26710 == 1) {
            str = ATPMessages.getString("MW.BEHAVIOR.FAILURE.CABIN");
        }
        else {
            str = ATPMessages.getString("MW.BEHAVIOR.FAILURE.WAYSIDE");
        }
        for (int i = 0; i < this._$18216.length; ++i) {
            this.g2.setColor(this._$18217);
            this.g2.fillRect(this._$1632[i], this._$2019 - 60, 11, 12);
            this.g2.fillRect(this._$1632[i] + 12, this._$2019 - 60, 100, 12);
            this.g2.fillRect(this._$1632[i], this._$2019 - 47, 112, 12);
            this.g2.setColor(Color.red);
            this.g2.drawString(str + " " + ATPMessages.getString("MW.BEHAVIOR.FAILURE.CLASS") + " " + this._$18216[i][0] + " " + ATPMessages.getString("MW.BEHAVIOR.FAILURE.NUMBER") + " " + this._$18216[i][1], this._$1632[i], this._$2019 - 50);
            this.g2.drawString("" + this._$18216[i][2] + "", this._$1632[i], this._$2019 - 37);
        }
    }
}
