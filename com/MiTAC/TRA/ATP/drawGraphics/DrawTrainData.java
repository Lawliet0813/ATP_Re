// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.drawGraphics;

import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.util.Vector;
import java.util.Date;
import java.awt.Color;

public class DrawTrainData extends DrawBase implements drawATP
{
    private Color _$18217;
    private Date[] _$18215;
    private Vector[] _$18216;
    private boolean _$18218;
    private Vector _$18214;
    private Stroke _$18036;
    private int[] _$1632;
    private int _$2019;
    
    public DrawTrainData(final commonParaSetting commonParaSetting, final drawParameters drawParameters, final Vector vector) {
        super(commonParaSetting, drawParameters, vector);
        this._$18217 = Color.red;
        this._$18218 = true;
        this._$18036 = new BasicStroke();
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
        this._$18215 = new Date[this._$18214.size()];
        this._$1632 = new int[this._$18214.size()];
        for (int i = 0; i < this._$18214.size(); ++i) {
            this._$18215[i] = ((Vector<Date>)this._$18214.get(i)).get(5);
            this._$1632[i] = this.showsoldierWhereToStand(this._$18215[i].getTime());
            this._$2019 = super.showsoldierRangeToDefence(this.para.MaxNum) + 1;
        }
    }
    
    public void setData(final Vector $18214) {
        this._$18214 = $18214;
        this.resetScale();
    }
    
    public void setDataLineColor(final Color $18217) throws Exception {
        this._$18217 = $18217;
    }
    
    public void setDrawCurve(final boolean $18218) {
        this._$18218 = $18218;
    }
    
    public void setDrawType(final int drawType) {
        super.setDrawType(drawType);
        if (drawType != DrawBase.drawType) {
            this.resetScale();
        }
    }
    
    public void setStroke(final Stroke $18036) {
        this._$18036 = $18036;
    }
    
    private void _$18223() throws Exception {
        final Stroke stroke = this.g2.getStroke();
        this.g2.setStroke(this._$18036);
        this.g2.setColor(this._$18217);
        if (this._$18215.length == 0) {
            super.drawBasicMessage();
        }
        else {
            for (int i = 0; i < this._$1632.length; ++i) {
                final Vector vector = this._$18214.get(i);
                final int n = (this._$1632[i] - 70 < 0) ? 70 : this._$1632[i];
                this.g2.setColor(Color.red);
                this.g2.fillOval(this._$1632[i], this._$2019 - 30, 3, 3);
                this.g2.drawOval(this._$1632[i] - 2, this._$2019 - 32, 6, 6);
                this.g2.setColor(Color.white);
                this.g2.fillRect(n - 62, this._$2019 - 1, 110, super.showsoldierRangeToDefence(this.para.MinNum) - super.showsoldierRangeToDefence(this.para.MaxNum));
                this.g2.setColor(Color.red);
                this.g2.drawRect(n - 61, this._$2019, 107, super.showsoldierRangeToDefence(this.para.MinNum) - super.showsoldierRangeToDefence(this.para.MaxNum) - 3);
                final int n2 = n - 58;
                final int n3 = n + 2;
                final int n4 = 11;
                final int n5 = this._$2019 + 13;
                this.g2.setColor(Color.black);
                this.g2.drawString("\u5217\u8eca\u7de8\u7d44", n2, n5);
                this.g2.drawString(vector.get(17).toString(), n3, n5);
                this.g2.setColor(new Color(233, 233, 233));
                this.g2.drawLine(n - 60, n5 + 1, n + 45, n5 + 1);
                this.g2.setColor(Color.black);
                this.g2.drawString("\u8eca\u9577", n2, n5 + n4);
                this.g2.drawString(vector.get(9).toString() + " m", n3, n5 + n4);
                this.g2.setColor(new Color(233, 233, 233));
                this.g2.drawLine(n - 60, n5 + 1 + n4, n + 45, n5 + 1 + n4);
                this.g2.setColor(Color.black);
                this.g2.drawString("\u6700\u5927\u8eca\u901f", n2, n5 + n4 * 2);
                this.g2.drawString(new Integer(vector.get(10).toString()) * 36 / 1000 + " km/h", n3, n5 + n4 * 2);
                this.g2.setColor(new Color(233, 233, 233));
                this.g2.drawLine(n - 60, n5 + 1 + n4 * 2, n + 45, n5 + 1 + n4 * 2);
                this.g2.setColor(Color.black);
                this.g2.drawString("\u30005~9", n2, n5 + n4 * 3);
                this.g2.drawString(new Integer(vector.get(18).toString()) * 36 / 1000 + " km/h", n3, n5 + n4 * 3);
                this.g2.setColor(new Color(233, 233, 233));
                this.g2.drawLine(n - 60, n5 + 1 + n4 * 3, n + 45, n5 + 1 + n4 * 3);
                this.g2.setColor(Color.black);
                this.g2.drawString("\u300010~14", n2, n5 + n4 * 4);
                this.g2.drawString(new Integer(vector.get(19).toString()) * 36 / 1000 + " km/h", n3, n5 + n4 * 4);
                this.g2.setColor(new Color(233, 233, 233));
                this.g2.drawLine(n - 60, n5 + 1 + n4 * 4, n + 45, n5 + 1 + n4 * 4);
                this.g2.setColor(Color.black);
                this.g2.drawString("\u300015~19", n2, n5 + n4 * 5);
                this.g2.drawString(new Integer(vector.get(20).toString()) * 36 / 1000 + " km/h", n3, n5 + n4 * 5);
                this.g2.setColor(new Color(233, 233, 233));
                this.g2.drawLine(n - 60, n5 + 1 + n4 * 5, n + 45, n5 + 1 + n4 * 5);
                this.g2.setColor(Color.black);
                this.g2.drawString("\u3000>=20", n2, n5 + n4 * 6);
                this.g2.drawString(new Integer(vector.get(21).toString()) * 36 / 1000 + " km/h", n3, n5 + n4 * 6);
                this.g2.setColor(new Color(233, 233, 233));
                this.g2.drawLine(n - 60, n5 + 1 + n4 * 6, n + 45, n5 + 1 + n4 * 6);
                this.g2.setColor(Color.black);
                this.g2.drawString("\u6700\u5927\u52a0\u901f\u5ea6", n2, n5 + n4 * 7);
                this.g2.drawString(new Float((float)((int)vector.get(15) / 100)) + " m/s^2", n3, n5 + n4 * 7);
                this.g2.setColor(new Color(233, 233, 233));
                this.g2.drawLine(n - 60, n5 + 1 + n4 * 7, n + 45, n5 + 1 + n4 * 7);
                this.g2.setColor(Color.black);
                this.g2.drawString("\u6700\u5927\u6e1b\u901f\u5ea6", n2, n5 + n4 * 8);
                this.g2.drawString(new Float((float)((int)vector.get(16) / 100)) + " m/s^2", n3, n5 + n4 * 8);
                this.g2.setColor(new Color(233, 233, 233));
                this.g2.drawLine(n - 60, n5 + 1 + n4 * 8, n + 45, n5 + 1 + n4 * 8);
                this.g2.setColor(Color.black);
                this.g2.drawString("T_EB", n2, n5 + n4 * 9);
                this.g2.drawString(new Float((float)((int)vector.get(13) / 1000)) + " s", n3, n5 + n4 * 9);
                this.g2.setColor(new Color(233, 233, 233));
                this.g2.drawLine(n - 60, n5 + 1 + n4 * 9, n + 45, n5 + 1 + n4 * 9);
                this.g2.setColor(Color.black);
                this.g2.drawString("T_SB", n2, n5 + n4 * 10);
                this.g2.drawString(new Float((float)((int)vector.get(12) / 1000)) + " s", n3, n5 + n4 * 10);
                this.g2.setColor(new Color(233, 233, 233));
                this.g2.drawLine(n - 60, n5 + 1 + n4 * 10, n + 45, n5 + 1 + n4 * 10);
                this.g2.setColor(Color.black);
                this.g2.drawString("T_CUT", n2, n5 + n4 * 11);
                this.g2.drawString(new Float((float)((int)vector.get(14) / 1000)) + " s", n3, n5 + n4 * 11);
                this.g2.setColor(new Color(233, 233, 233));
                this.g2.drawLine(n - 60, n5 + 1 + n4 * 11, n + 45, n5 + 1 + n4 * 11);
                this.g2.setColor(Color.black);
            }
        }
        this.g2.setStroke(stroke);
    }
}
