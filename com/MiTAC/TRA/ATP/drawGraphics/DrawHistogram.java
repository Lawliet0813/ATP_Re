// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.drawGraphics;

import java.awt.Graphics;
import com.MiTAC.TRA.ATP.ATPMessages;
import java.util.Arrays;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.util.Vector;
import java.util.Date;
import java.awt.Color;

public class DrawHistogram extends DrawBase implements drawATP
{
    private Color _$18217;
    private Date[] _$18215;
    private int[] _$18216;
    private boolean _$18218;
    private Vector _$18214;
    private Stroke _$18036;
    private int[] _$1632;
    private int[] _$2019;
    
    public DrawHistogram(final commonParaSetting commonParaSetting, final drawParameters drawParameters, final Vector vector) {
        super(commonParaSetting, drawParameters, vector);
        this._$18218 = true;
        this._$18036 = new BasicStroke();
    }
    
    public Object getMousePointStatus() throws Exception {
        try {
            final int binarySearch = Arrays.binarySearch(this._$18215, new Date(super.soloderReport()));
            if (binarySearch < 0) {
                try {
                    if (Math.abs(binarySearch) == 1 && this._$18216[0] != 0) {
                        return ATPMessages.getString("MW.BEHAVIOR.LOG.STATUS.NOT_ACTIVE");
                    }
                    return new Integer(this._$18216[Math.abs(binarySearch) - 2]);
                }
                catch (final ArrayIndexOutOfBoundsException ex) {
                    return new Integer(this._$18216[this._$18216.length - 1]);
                }
                catch (final Exception ex2) {
                    return ATPMessages.getString("MW.GNL.ERROR");
                }
            }
            return new Integer(this._$18216[binarySearch]);
        }
        catch (final ArrayIndexOutOfBoundsException ex3) {
            return ATPMessages.getString("MW.BEHAVIOR.LOG.STATUS.NO_DATA");
        }
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
        this._$18215 = new Date[this._$18214.size()];
        this._$18216 = new int[this._$18214.size()];
        this._$1632 = new int[this._$18214.size()];
        this._$2019 = new int[this._$18214.size()];
        for (int i = 0; i < this._$18214.size(); ++i) {
            final Vector vector = this._$18214.get(i);
            this._$18215[i] = (Date)vector.get(0);
            this._$18216[i] = (int)vector.get(1);
            this._$1632[i] = this.showsoldierWhereToStand(this._$18215[i].getTime());
            this._$2019[i] = this.showsoldierRangeToDefence(this._$18216[i]);
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
        if (this._$18216.length == 0) {
            super.drawBasicMessage();
        }
        else if (this.isFollowMode()) {
            this._$18225();
        }
        else {
            this._$18226();
        }
        this.g2.setStroke(stroke);
    }
    
    private void _$18225() {
        int n = this._$1632[0];
        int n2 = this._$2019[0];
        final int showsoldierRangeToDefence = this.showsoldierRangeToDefence(0);
        this.g2.setColor(this._$18217);
        for (int n3 = 1; n3 < this._$18215.length && this._$1632[n3] <= DrawBase.mouseX + 600; ++n3) {
            if (this._$1632[n3] >= DrawBase.mouseX - 600) {
                if (n2 != this._$2019[n3]) {
                    this.g2.fillRect(n, n2, this._$1632[n3] - n, Math.abs(showsoldierRangeToDefence - n2));
                    n = this._$1632[n3];
                    n2 = this._$2019[n3];
                }
                if (n3 == this._$18215.length - 1) {
                    this.g2.fillRect(n, n2, this._$1632[n3] - n, Math.abs(showsoldierRangeToDefence - n2));
                    n = this._$1632[n3];
                    n2 = this._$2019[n3];
                }
            }
        }
        final Color darker = this._$18217.darker();
        final int n4 = this.showsoldierWhereToStand(DrawBase.endTime) - n;
        this.g2.setColor(darker);
        this.g2.fillRect(n, n2, n4, Math.abs(showsoldierRangeToDefence - n2));
    }
    
    private void _$18226() {
        int n = this._$1632[0];
        int n2 = this._$2019[0];
        final int showsoldierRangeToDefence = this.showsoldierRangeToDefence(0);
        this.g2.setColor(this._$18217);
        for (int i = 1; i < this._$18215.length; ++i) {
            if (n2 != this._$2019[i]) {
                this.g2.fillRect(n, n2, this._$1632[i] - n, Math.abs(showsoldierRangeToDefence - n2));
                n = this._$1632[i];
                n2 = this._$2019[i];
            }
            if (i == this._$18215.length - 1) {
                this.g2.fillRect(n, n2, this._$1632[i] - n, Math.abs(showsoldierRangeToDefence - n2));
                n = this._$1632[i];
                n2 = this._$2019[i];
            }
        }
        final Color darker = this._$18217.darker();
        final int n3 = this.showsoldierWhereToStand(DrawBase.endTime) - n;
        this.g2.setColor(darker);
        this.g2.fillRect(n, n2, n3, Math.abs(showsoldierRangeToDefence - n2));
    }
}
