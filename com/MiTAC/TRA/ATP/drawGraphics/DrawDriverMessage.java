// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.drawGraphics;

import java.util.Arrays;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.util.Vector;
import java.util.Date;
import java.awt.Color;

public class DrawDriverMessage extends DrawBase implements drawATP
{
    private Color _$18217;
    private Date[] _$26694;
    private Date[] _$26699;
    private int[][] _$26696;
    private int[][] _$26701;
    private boolean _$18218;
    private boolean _$26705;
    private Color[] _$26704;
    private Vector _$26693;
    private Vector _$26698;
    private Stroke _$18036;
    private int[] _$26695;
    private int[] _$26700;
    private int[] _$26702;
    private int _$26697;
    private int _$26703;
    
    public DrawDriverMessage(final commonParaSetting commonParaSetting, final drawParameters drawParameters, final Vector vector) {
        super(commonParaSetting, drawParameters, vector);
        this._$26704 = new Color[] { new Color(245, 177, 109), new Color(240, 156, 66), new Color(236, 135, 14), new Color(208, 119, 11).darker(), new Color(189, 107, 9).darker(), new Color(148, 83, 5).darker() };
        this._$18218 = true;
        this._$18036 = new BasicStroke();
        this._$26705 = true;
    }
    
    private void _$26653(final int n) {
        this.g2.fillPolygon(new int[] { this._$26695[n], this._$26695[n] - 2, this._$26695[n] + 3 }, new int[] { this._$26697 - 1, this._$26697 - 4, this._$26697 - 4 }, 3);
    }
    
    private void _$26708(final int n) {
        this.g2.fillPolygon(new int[] { this._$26700[n], this._$26700[n] - 3, this._$26700[n] + 3 }, new int[] { this._$26703, this._$26703 + 4, this._$26703 + 4 }, 3);
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
        this._$26694 = new Date[this._$26693.size()];
        this._$26696 = new int[this._$26693.size()][5];
        this._$26695 = new int[this._$26693.size()];
        this._$26697 = super.showsoldierRangeToDefence(this.para.MaxNum) - 25;
        for (int i = 0; i < this._$26693.size(); ++i) {
            final Vector vector = this._$26693.get(i);
            this._$26694[i] = (Date)vector.get(0);
            this._$26696[i][0] = (int)((vector.get(1) == null) ? 0 : vector.get(1));
            this._$26696[i][1] = 0;
            this._$26696[i][2] = (int)((vector.get(3) == null) ? 0 : vector.get(3));
            this._$26696[i][3] = (int)((vector.get(4) == null) ? 0 : vector.get(4));
            this._$26696[i][4] = 0;
            this._$26695[i] = this.showsoldierWhereToStand(this._$26694[i].getTime());
        }
        this._$26699 = new Date[this._$26698.size()];
        this._$26700 = new int[this._$26698.size()];
        this._$26702 = new int[this._$26698.size()];
        this._$26701 = new int[this._$26698.size()][2];
        this._$26703 = super.showsoldierRangeToDefence(this.para.MaxNum) - 25;
        for (int j = 0; j < this._$26698.size(); ++j) {
            final Vector vector2 = this._$26698.get(j);
            this._$26699[j] = (Date)vector2.get(0);
            this._$26701[j][0] = (int)((vector2.get(1) == null) ? 0 : vector2.get(1));
            this._$26701[j][1] = (int)((vector2.get(2) == null) ? 0 : vector2.get(2));
            int binarySearch = Arrays.binarySearch(this._$26694, this._$26699[j]);
            if (binarySearch < 0) {
                binarySearch = Math.abs(binarySearch) - 2;
            }
            this._$26702[j] = -1;
            for (int k = binarySearch; k >= 0; --k) {
                if (this._$26696[k][4] == 1) {
                    this._$26702[j] = -1;
                    break;
                }
                if (this._$26701[j][0] == this._$26696[k][0] && this._$26696[k][2] <= 2) {
                    if (this._$26696[k][3] > 60000) {
                        this._$26702[j] = -2;
                    }
                    else {
                        this._$26702[j] = this.showsoldierWhereToStand(this._$26694[k].getTime());
                    }
                    this._$26696[k][4] = 1;
                    break;
                }
            }
            this._$26700[j] = this.showsoldierWhereToStand(this._$26699[j].getTime());
        }
    }
    
    public void setData(final Vector $26693, final Vector $26694) throws Exception {
        this._$26693 = $26693;
        this._$26698 = $26694;
        this.resetScale();
    }
    
    public void setData(final Vector vector) throws Exception {
    }
    
    public void setDataLineColor(final Color color) throws Exception {
    }
    
    public void setDrawCurve(final boolean $18218) {
        this._$18218 = $18218;
    }
    
    public void setStroke(final Stroke stroke) {
    }
    
    private void _$18223() {
        if (this._$26694.length == 0) {
            super.drawBasicMessage();
        }
        else {
            final Stroke stroke = this.g2.getStroke();
            this.g2.setStroke(this._$18036);
            if (super.isFollowMode()) {
                this._$18225();
            }
            else {
                this._$18226();
            }
            this.g2.setStroke(stroke);
        }
    }
    
    private void _$18225() {
        for (int n = 0; n < this._$26694.length && this._$26695[n] <= DrawBase.mouseX + 600; ++n) {
            if (this._$26695[n] >= DrawBase.mouseX - 600) {
                switch (this._$26696[n][2]) {
                    case 0: {
                        this.g2.setColor(this._$26704[0]);
                        break;
                    }
                    case 1: {
                        this.g2.setColor(this._$26704[1]);
                        break;
                    }
                    case 2: {
                        this.g2.setColor(this._$26704[2]);
                        break;
                    }
                    case 3: {
                        this.g2.setColor(this._$26704[3]);
                        break;
                    }
                    case 4: {
                        this.g2.setColor(this._$26704[4]);
                        break;
                    }
                    default: {
                        this.g2.setColor(this._$26704[5]);
                        break;
                    }
                }
                this.g2.drawString("" + this._$26696[n][3], this._$26695[n], this._$26697 - 5);
                this._$26653(n);
            }
        }
        for (int n2 = 0; n2 < this._$26699.length && this._$26700[n2] <= DrawBase.mouseX; ++n2) {
            if (this._$26700[n2] >= DrawBase.mouseX - 300) {
                if (this._$26702[n2] == -1) {
                    this.g2.setColor(Color.red);
                    this.g2.drawString("?", this._$26700[n2], this._$26703);
                }
                else {
                    this.g2.setColor(Color.orange);
                    this.g2.drawLine(this._$26702[n2], this._$26703, this._$26700[n2], this._$26703);
                }
                this.g2.drawString("" + this._$26701[n2][1], this._$26700[n2], this._$26703 + 14);
                this._$26708(n2);
            }
        }
    }
    
    private void _$18226() {
        for (int i = 0; i < this._$26694.length; ++i) {
            switch (this._$26696[i][2]) {
                case 0: {
                    this.g2.setColor(this._$26704[0]);
                    break;
                }
                case 1: {
                    this.g2.setColor(this._$26704[1]);
                    break;
                }
                case 2: {
                    this.g2.setColor(this._$26704[2]);
                    break;
                }
                case 3: {
                    this.g2.setColor(this._$26704[3]);
                    break;
                }
                case 4: {
                    this.g2.setColor(this._$26704[4]);
                    break;
                }
                default: {
                    this.g2.setColor(this._$26704[5]);
                    break;
                }
            }
            if (this._$26696[i][3] <= 60000) {
                if (i > 0) {
                    if (this._$26695[i] - this._$26695[i - 1] < 20 && !this._$26705) {
                        this.g2.drawString("" + this._$26696[i][3], this._$26695[i], this._$26697 - 14);
                        this._$26705 = true;
                    }
                    else {
                        this.g2.drawString("" + this._$26696[i][3], this._$26695[i], this._$26697 - 5);
                        this._$26705 = false;
                    }
                }
                else {
                    this.g2.drawString("" + this._$26696[i][3], this._$26695[i], this._$26697 - 5);
                }
                if (this._$26696[i][3] == 550) {
                    this.g2.drawString("*", this._$26695[i], this._$26697 + 3);
                }
                this._$26653(i);
            }
        }
        for (int j = 0; j < this._$26699.length; ++j) {
            if (this._$26702[j] == -1) {
                this.g2.setColor(Color.red);
                this.g2.drawString("?", this._$26700[j], this._$26703);
            }
            else {
                if (this._$26702[j] == -2) {
                    continue;
                }
                this.g2.setColor(Color.orange);
                this.g2.drawLine(this._$26702[j], this._$26703, this._$26700[j], this._$26703);
            }
            this.g2.drawString("" + this._$26701[j][1], this._$26700[j], this._$26703 + 14);
            this._$26708(j);
        }
    }
}
