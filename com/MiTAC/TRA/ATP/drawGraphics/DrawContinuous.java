// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.drawGraphics;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import com.MiTAC.TRA.ATP.core.ATPMissionDetail;
import java.awt.Graphics;
import com.MiTAC.TRA.ATP.ATPMessages;
import java.util.Arrays;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.util.Vector;
import java.util.Date;
import java.awt.Color;

public class DrawContinuous extends DrawBase implements drawATP
{
    private Color _$2892;
    private Date[] _$2888;
    private int[] _$2890;
    private boolean _$2893;
    private Vector _$2887;
    private Stroke _$2894;
    private int[] _$2889;
    private int[] _$2891;
    
    public DrawContinuous(final commonParaSetting commonParaSetting, final drawParameters drawParameters, final Vector vector) {
        super(commonParaSetting, drawParameters, vector);
        this._$2893 = true;
        this._$2894 = new BasicStroke();
    }
    
    public Object getMousePointStatus() throws Exception {
        try {
            final int binarySearch = Arrays.binarySearch(this._$2888, new Date(super.soloderReport()));
            if (binarySearch < 0) {
                try {
                    if (Math.abs(binarySearch) == 1 && this._$2890[0] != 0) {
                        return ATPMessages.getString("MW.BEHAVIOR.LOG.STATUS.NOT_ACTIVE");
                    }
                    return new Integer(this._$2890[Math.abs(binarySearch) - 2]);
                }
                catch (final ArrayIndexOutOfBoundsException ex) {
                    return new Integer(this._$2890[this._$2890.length - 1]);
                }
                catch (final Exception ex2) {
                    return ATPMessages.getString("MW.GNL.ERROR");
                }
            }
            return new Integer(this._$2890[binarySearch]);
        }
        catch (final ArrayIndexOutOfBoundsException ex3) {
            return ATPMessages.getString("MW.BEHAVIOR.LOG.STATUS.NO_DATA");
        }
    }
    
    public void paintBody(final Graphics graphics) throws Exception {
        super.paintBody(graphics);
        if (this._$2893) {
            this._$2921();
            super.drawScanner();
        }
    }
    
    public void paintHeader(final Graphics graphics) throws Exception {
        super.paintHeader(graphics);
    }
    
    public void resetScale() {
        this._$2888 = new Date[this._$2887.size()];
        this._$2890 = new int[this._$2887.size()];
        this._$2889 = new int[this._$2887.size()];
        this._$2891 = new int[this._$2887.size()];
        for (int i = 0; i < this._$2887.size(); ++i) {
            final Vector vector = this._$2887.get(i);
            this._$2888[i] = (Date)vector.get(0);
            this._$2890[i] = (int)vector.get(1);
            this._$2889[i] = this.showsoldierWhereToStand(this._$2888[i].getTime());
            if (this._$2889[i] < this._$2889[(i - 1 < 0) ? 0 : (i - 1)]) {
                System.out.println("err");
            }
            this._$2891[i] = this.showsoldierRangeToDefence(this._$2890[i]);
        }
    }
    
    public void setData(final ATPMissionDetail atpMissionDetail) {
        this.drawStopStation(atpMissionDetail.getStopStation());
    }
    
    public void setData(final Vector $2887) {
        this._$2887 = $2887;
        this.resetScale();
    }
    
    public void setDataLineColor(final Color $2892) throws Exception {
        this._$2892 = $2892;
    }
    
    public void setDrawCurve(final boolean $2893) {
        this._$2893 = $2893;
    }
    
    public void setDrawType(final int drawType) {
        super.setDrawType(drawType);
        if (drawType != DrawBase.drawType) {
            this.resetScale();
        }
    }
    
    public void setStroke(final Stroke $2894) {
        this._$2894 = $2894;
    }
    
    private void _$2921() throws Exception {
        final GeneralPath generalPath = new GeneralPath();
        final Stroke stroke = this.g2.getStroke();
        this.g2.setStroke(this._$2894);
        if (this._$2890.length == 0) {
            super.drawBasicMessage();
        }
        else {
            this.g2.setColor(this._$2892);
            if (this.isFollowMode()) {
                this._$2928(generalPath);
            }
            else {
                this._$2929(generalPath);
            }
            this.g2.draw(generalPath);
            generalPath.reset();
            this.g2.setColor(this._$2892.darker());
            generalPath.moveTo((float)this._$2889[this._$2889.length - 1], (float)this._$2891[this._$2891.length - 1]);
            generalPath.lineTo((float)this.showsoldierWhereToStand(DrawBase.endTime), (float)this._$2891[this._$2891.length - 1]);
            this.g2.draw(generalPath);
        }
        this.g2.setStroke(stroke);
    }
    
    private void _$2928(final GeneralPath generalPath) {
        boolean b = false;
        for (int n = 1; n < this._$2889.length && this._$2889[n] <= DrawBase.mouseX + 600; ++n) {
            if (this._$2889[n] >= DrawBase.mouseX - 600) {
                if (!b) {
                    generalPath.moveTo((float)this._$2889[n], (float)this._$2891[n]);
                    b = !b;
                }
                generalPath.lineTo((float)this._$2889[n], (float)this._$2891[n]);
            }
        }
    }
    
    private void _$2929(final GeneralPath generalPath) {
        generalPath.moveTo((float)this._$2889[0], (float)this._$2891[0]);
        for (int i = 1; i < this._$2889.length; ++i) {
            generalPath.lineTo((float)this._$2889[i], (float)this._$2891[i]);
        }
    }
}
