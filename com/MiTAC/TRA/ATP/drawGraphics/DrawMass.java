// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.drawGraphics;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.util.Vector;
import java.util.Date;
import java.awt.Color;

public class DrawMass extends DrawBase implements drawATP
{
    private int[] _$18244;
    private Color _$18245;
    private Date[] _$18242;
    private int[] _$18243;
    private Vector _$18240;
    private Vector _$18241;
    private boolean _$18218;
    private Stroke _$18036;
    private int[] _$1632;
    private int[] _$2019;
    
    public DrawMass(final commonParaSetting commonParaSetting, final drawParameters drawParameters, final Vector vector) {
        super(commonParaSetting, drawParameters, vector);
        this._$18218 = true;
        this._$18036 = new BasicStroke();
    }
    
    public void paintBody(final Graphics graphics) throws Exception {
        super.paintBody(graphics);
        if (this._$18218) {
            this._$18223();
            super.drawScanner();
        }
    }
    
    public void paintHeader(final Graphics graphics) throws Exception {
        super.paintHeader(graphics);
    }
    
    public void resetScale() {
        this._$18242 = new Date[this._$18241.size()];
        this._$18244 = new int[this._$18241.size()];
        this._$18243 = new int[this._$18241.size()];
        this._$1632 = new int[this._$18240.size()];
        this._$2019 = new int[this._$18240.size()];
        for (int i = 0; i < this._$18241.size(); ++i) {
            final Vector vector = this._$18241.get(i);
            this._$18242[i] = (Date)vector.get(0);
            this._$18244[i] = (int)vector.get(1);
            this._$18243[i] = ((Vector<Integer>)this._$18240.get(i)).get(1);
            this._$1632[i] = this.showsoldierWhereToStand(this._$18242[i].getTime());
            this._$2019[i] = this.showsoldierRangeToDefence(this._$18244[i] - this._$18243[i]);
        }
    }
    
    public void setData(final Vector $18240, final Vector $18241) {
        this._$18240 = $18240;
        this._$18241 = $18241;
        this.resetScale();
    }
    
    public void setData(final Vector vector) {
    }
    
    public void setDataLineColor(final Color $18245) {
        this._$18245 = $18245;
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
        if (this.isFollowMode()) {
            this._$18225();
        }
        else {
            this._$18226();
        }
    }
    
    private void _$18225() {
        int n = -1;
        int n2 = 0;
        final Stroke stroke = this.g2.getStroke();
        this.g2.setStroke(this._$18036);
        this.g2.setColor(this._$18245);
        final GeneralPath generalPath = new GeneralPath();
        for (int n3 = 0; n3 < this._$18242.length && this._$1632[n3] <= DrawBase.mouseX + 600; ++n3) {
            if (this._$1632[n3] >= DrawBase.mouseX - 600) {
                if (n != -1 || this._$18244[n3] != -1) {
                    if (n == -1 && this._$18244[n3] != -1) {
                        n = this._$18244[n3];
                        n2 = this._$2019[n3];
                        generalPath.moveTo((float)this._$1632[n3], (float)this._$2019[n3]);
                    }
                    else if (n != -1 && this._$18244[n3] == -1) {
                        generalPath.lineTo((float)this._$1632[n3], (float)n2);
                        n = -1;
                        this.g2.draw(generalPath);
                        generalPath.reset();
                    }
                    else {
                        generalPath.lineTo((float)this._$1632[n3], (float)this._$2019[n3]);
                        n = this._$18244[n3];
                        n2 = this._$2019[n3];
                    }
                }
            }
        }
        this.g2.draw(generalPath);
        generalPath.reset();
        this.g2.setStroke(stroke);
    }
    
    private void _$18226() {
        int n = -1;
        int n2 = 0;
        final Stroke stroke = this.g2.getStroke();
        this.g2.setStroke(this._$18036);
        this.g2.setColor(this._$18245);
        final GeneralPath generalPath = new GeneralPath();
        for (int i = 0; i < this._$18242.length; ++i) {
            if (n != -1 || this._$18244[i] != -1) {
                if (n == -1 && this._$18244[i] != -1) {
                    n = this._$18244[i];
                    n2 = this._$2019[i];
                    generalPath.moveTo((float)this._$1632[i], (float)this._$2019[i]);
                }
                else if (n != -1 && this._$18244[i] == -1) {
                    generalPath.lineTo((float)this._$1632[i], (float)n2);
                    n = -1;
                    this.g2.draw(generalPath);
                    generalPath.reset();
                }
                else {
                    generalPath.lineTo((float)this._$1632[i], (float)this._$2019[i]);
                    n = this._$18244[i];
                    n2 = this._$2019[i];
                }
            }
        }
        this.g2.draw(generalPath);
        generalPath.reset();
        this.g2.setStroke(stroke);
    }
}
