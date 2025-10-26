// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.drawGraphics;

import java.awt.geom.GeneralPath;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.core.ATPMissionDetail;
import java.util.Arrays;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.util.Vector;
import java.awt.Color;

public class DrawCurve extends DrawBase implements drawATP
{
    private Color _$18217;
    private boolean _$18218;
    private Vector _$18214;
    private Vector _$26716;
    private Stroke _$18036;
    private Vector _$26656;
    private Vector _$26657;
    
    public DrawCurve(final commonParaSetting commonParaSetting, final drawParameters drawParameters, final Vector vector) {
        super(commonParaSetting, drawParameters, vector);
        this._$26656 = new Vector();
        this._$26657 = new Vector();
        this._$26716 = new Vector();
        this._$18217 = Color.blue.brighter();
        this._$18218 = true;
        this._$18036 = new BasicStroke();
    }
    
    public void paintBody(final Graphics graphics) throws Exception {
        super.paintBody(graphics);
        final Color brighter = Color.gray.brighter();
        graphics.setColor(new Color(brighter.getRed(), brighter.getGreen(), brighter.getBlue(), 50));
        graphics.fillRect(1, super.showsoldierRangeToDefence(0), super.showBattleLingDepth(), super.showBattleLineRange() / 2 + 1);
        if (this._$18218) {
            this._$18223();
        }
    }
    
    public void paintHeader(final Graphics graphics) throws Exception {
        super.paintHeader(graphics);
    }
    
    public void resetScale() {
        this._$26656 = new Vector();
        this._$26657 = new Vector();
        for (int i = 0; i < this._$18214.size(); ++i) {
            try {
                final Vector vector = this._$18214.get(i);
                int n = (int)vector.get(2) / 100;
                if (i + 1 != this._$18214.size()) {
                    final int n2 = this._$18214.get(i + 1).get(2) / 100;
                }
                final int binarySearch = Arrays.binarySearch(DrawBase.time, vector.get(0));
                int n3 = (binarySearch < 0) ? (Math.abs(binarySearch) - 1) : binarySearch;
                super.showsoldierWhereToStand(DrawBase.time[n3].getTime());
                final int n4 = DrawBase.location[n3] / 100;
                super.showsoldierWhereToStand(DrawBase.time[++n3].getTime());
                final int n5 = DrawBase.location[++n3] / 100;
                for (int j = 0; j < 9; ++j) {
                    try {
                        int index = 3 + 3 * j;
                        final int intValue = (int)vector.get(index);
                        final int intValue2 = (int)vector.get(++index);
                        int value = (int)vector.get(++index);
                        if (intValue == -1) {
                            break;
                        }
                        int n6;
                        int n7;
                        int n8;
                        for (n6 = n3, n7 = n4, n8 = n5; n8 <= n7 || n8 < n + intValue; n7 = DrawBase.location[n6 - 1] / 100, n8 = DrawBase.location[n6] / 100) {
                            ++n6;
                        }
                        final int showsoldierWhereToStand = super.showsoldierWhereToStand(DrawBase.time[n6 - 1].getTime());
                        final int value2 = showsoldierWhereToStand + (super.showsoldierWhereToStand(DrawBase.time[n6].getTime()) - showsoldierWhereToStand) / (n8 - n7) * intValue;
                        int n9;
                        int n10;
                        int n11;
                        for (n9 = n3, n10 = n4, n11 = n5; n11 <= n10 || n11 < n + intValue + intValue2; n10 = DrawBase.location[n9 - 1] / 100, n11 = DrawBase.location[n9] / 100) {
                            ++n9;
                        }
                        final int showsoldierWhereToStand2 = super.showsoldierWhereToStand(DrawBase.time[n9 - 1].getTime());
                        final int value3 = showsoldierWhereToStand2 + (super.showsoldierWhereToStand(DrawBase.time[n9].getTime()) - showsoldierWhereToStand2) / (n11 - n10) * (intValue + intValue2);
                        final Vector<Integer> e = new Vector<Integer>();
                        e.add(new Integer(value2));
                        e.add(new Integer(value3));
                        this._$26656.add(e);
                        final Vector<Integer> e2 = new Vector<Integer>();
                        e2.add(new Integer(intValue2));
                        int k = 0;
                        while (k < this._$26716.size()) {
                            final Vector vector2 = this._$26716.get(k);
                            if (value == (int)vector2.get(0)) {
                                value = (int)vector2.get(1);
                                if (vector2.get(2).toString().startsWith("Left")) {
                                    value = -value;
                                    break;
                                }
                                break;
                            }
                            else {
                                ++k;
                            }
                        }
                        e2.add(new Integer(value));
                        this._$26657.add(e2);
                        n = n + intValue + intValue2;
                    }
                    catch (final Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            catch (final Exception ex2) {
                ex2.printStackTrace();
            }
        }
    }
    
    public void setData(final ATPMissionDetail atpMissionDetail) {
        this.drawStopStation(atpMissionDetail.getStopStation());
    }
    
    public void setData(final Vector $18214) {
        this._$18214 = $18214;
        try {
            this._$26716 = new ConnectDB().getData("SELECT * FROM M_CURVERADIUS");
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
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
        for (int i = 0; i < this._$26656.size(); ++i) {
            final Vector vector = this._$26656.get(i);
            final int intValue = (int)vector.get(0);
            final int intValue2 = (int)vector.get(1);
            final Vector vector2 = this._$26657.get(i);
            ((Integer)vector2.get(0)).intValue();
            final int intValue3 = (int)vector2.get(1);
            if (intValue3 <= 0) {
                this.g2.setColor(this._$18217);
                this.g2.fillRect(intValue, super.showsoldierRangeToDefence(10), intValue2 - intValue, super.showsoldierRangeToDefence(0) - super.showsoldierRangeToDefence(10));
            }
            else {
                this.g2.setColor(this._$18217);
                this.g2.fillRect(intValue, super.showsoldierRangeToDefence(0), intValue2 - intValue, super.showsoldierRangeToDefence(-10) - super.showsoldierRangeToDefence(0) + 1);
            }
            this.g2.setColor(this._$18217);
            if (intValue3 <= 0) {
                this.g2.drawString(-intValue3 + "", intValue, super.showsoldierRangeToDefence(super.para.MaxNum) - 2);
            }
            else {
                this.g2.drawString(intValue3 + "", intValue, super.showsoldierRangeToDefence(super.para.MinNum) + 12);
            }
        }
    }
    
    private void _$18225(final GeneralPath generalPath) {
    }
    
    private void _$18226(final GeneralPath generalPath) {
    }
}
