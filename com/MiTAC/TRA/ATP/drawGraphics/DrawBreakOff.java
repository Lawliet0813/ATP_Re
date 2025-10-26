// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.drawGraphics;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.Graphics;
import com.MiTAC.TRA.ATP.ATPMessages;
import java.util.Arrays;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.util.Vector;
import java.util.Date;
import java.awt.Color;

public class DrawBreakOff extends DrawBase implements drawATP
{
    private Color _$18217;
    private Date[] _$18215;
    private int[] _$18216;
    private boolean _$18218;
    private Vector _$18214;
    private Stroke _$18036;
    private int[] _$1632;
    private int[] _$2019;
    
    public DrawBreakOff(final commonParaSetting commonParaSetting, final drawParameters drawParameters, final Vector vector) {
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
        this.g2.setColor(this._$18217);
        final GeneralPath generalPath = new GeneralPath();
        if (this._$18216.length == 0) {
            super.drawBasicMessage();
        }
        else if (this.isFollowMode()) {
            this._$18225(generalPath);
        }
        else {
            this._$18226(generalPath);
        }
        this.g2.setStroke(stroke);
    }
    
    private void _$18225(GeneralPath generalPath) {
        int n = -1;
        int n2 = 0;
        for (int n3 = 0; n3 < this._$1632.length && this._$1632[n3] <= DrawBase.mouseX + 600; ++n3) {
            if (this._$1632[n3] >= DrawBase.mouseX - 600) {
                if (n != -1 || this._$18216[n3] != -1) {
                    if (n == -1 && this._$18216[n3] != -1) {
                        n = this._$18216[n3];
                        n2 = this._$2019[n3];
                        generalPath = new GeneralPath();
                        generalPath.moveTo((float)this._$1632[n3], (float)this._$2019[n3]);
                    }
                    else if (n != -1 && this._$18216[n3] == -1) {
                        generalPath.lineTo((float)this._$1632[n3], (float)n2);
                        n = -1;
                        n2 = 0;
                        this.g2.draw(generalPath);
                    }
                    else if (this._$18216[n3] != n) {
                        generalPath.lineTo((float)this._$1632[n3], (float)n2);
                        generalPath.lineTo((float)this._$1632[n3], (float)this._$2019[n3]);
                        n = this._$18216[n3];
                        n2 = this._$2019[n3];
                    }
                    else {
                        generalPath.lineTo((float)this._$1632[n3], (float)this._$2019[n3]);
                    }
                }
            }
        }
        this.g2.draw(generalPath);
    }
    
    private void _$18226(GeneralPath generalPath) {
        int n = -1;
        int n2 = 0;
        for (int i = 0; i < this._$1632.length; ++i) {
            if (n != -1 || this._$18216[i] != -1) {
                if (n == -1 && this._$18216[i] != -1) {
                    n = this._$18216[i];
                    n2 = this._$2019[i];
                    generalPath = new GeneralPath();
                    generalPath.moveTo((float)this._$1632[i], (float)this._$2019[i]);
                }
                else if (n != -1 && this._$18216[i] == -1) {
                    generalPath.lineTo((float)this._$1632[i], (float)n2);
                    n = -1;
                    n2 = 0;
                    this.g2.draw(generalPath);
                }
                else if (this._$18216[i] != n) {
                    generalPath.lineTo((float)this._$1632[i], (float)n2);
                    generalPath.lineTo((float)this._$1632[i], (float)this._$2019[i]);
                    n = this._$18216[i];
                    n2 = this._$2019[i];
                }
                else {
                    generalPath.lineTo((float)this._$1632[i], (float)this._$2019[i]);
                }
            }
        }
        this.g2.draw(generalPath);
    }
}
