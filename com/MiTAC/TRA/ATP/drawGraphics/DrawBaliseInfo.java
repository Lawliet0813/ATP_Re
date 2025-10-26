// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.drawGraphics;

import java.awt.Graphics2D;
import java.awt.Graphics;
import com.MiTAC.TRA.ATP.ATPMessages;
import java.util.Arrays;
import java.util.Date;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.text.SimpleDateFormat;
import com.MiTAC.TRA.ATP.core.ATPMissionDetail;
import java.awt.Color;
import java.util.Vector;

public class DrawBaliseInfo extends DrawBase implements drawATP
{
    private Vector _$26624;
    private Vector _$26622;
    private Vector _$26623;
    private Vector _$26626;
    private int[] _$26628;
    private int _$26629;
    private Vector _$26625;
    private Vector _$26620;
    private Color _$18217;
    private boolean _$18218;
    private final int _$5541 = 0;
    private ATPMissionDetail _$18234;
    private final int _$26670 = 7;
    private final int _$5533 = 6;
    private final int _$26672 = 9;
    private final int _$26669 = 5;
    private final int _$26668 = 4;
    private final int _$26667 = 3;
    private SimpleDateFormat _$3906;
    private boolean _$26627;
    private final String[] _$26665;
    private Vector _$26621;
    private Stroke _$18036;
    private Vector _$4436;
    private final int _$5539 = 1;
    private final int _$26666 = 2;
    private final int _$26671 = 8;
    
    public DrawBaliseInfo(final commonParaSetting commonParaSetting, final drawParameters drawParameters, final Vector vector) {
        super(commonParaSetting, drawParameters, vector);
        this._$26627 = true;
        this._$18218 = true;
        this._$18036 = new BasicStroke();
        this._$3906 = new SimpleDateFormat("HH:mm:ss");
        this._$26665 = new String[] { "G", "Y", "Yf", "RYf", "RY", "Rf", "R", "Off", "YY", "R&O" };
    }
    
    public void drawStopInformation(final Date key) {
        if (this._$26624.size() != 0) {
            final int binarySearch = Arrays.binarySearch(this._$4436.toArray(), key);
            final int n = (binarySearch >= 0) ? binarySearch : ((Math.abs(binarySearch) - 2 < 0) ? 0 : (Math.abs(binarySearch) - 2));
            ((Integer)this._$26624.get(n)).intValue();
            final int binarySearch2 = Arrays.binarySearch(this._$26620.toArray(), key);
            final int intValue = this._$26625.get((binarySearch2 >= 0) ? binarySearch2 : ((Math.abs(binarySearch2) - 2 < 0) ? 0 : (Math.abs(binarySearch2) - 2)));
            this.g2.setColor(Color.CYAN);
            final int showsoldierWhereToStand = super.showsoldierWhereToStand(this._$4436.get(n).getTime());
            final int arrangesoldierPosition = super.arrangesoldierPosition(key.getTime());
            final int n2 = super.showsoldierRangeToDefence(this.para.MaxNum) + 12;
            final int n3 = super.showsoldierRangeToDefence(this.para.MinNum) + 2;
            this.g2.drawLine(showsoldierWhereToStand, n2, arrangesoldierPosition, n2);
            this.g2.drawLine(showsoldierWhereToStand, n2 + 3, showsoldierWhereToStand, n2 - 3);
            this.g2.drawLine(arrangesoldierPosition, n2 - 3, arrangesoldierPosition, n3);
            this.g2.drawLine(arrangesoldierPosition + 3, n3 + 3, arrangesoldierPosition, n3);
            this.g2.drawLine(arrangesoldierPosition - 3, n3 + 3, arrangesoldierPosition, n3);
            final int binarySearch3 = Arrays.binarySearch(this._$26621.toArray(), key);
            final int n4 = (binarySearch3 >= 0) ? binarySearch3 : (Math.abs(binarySearch3) - 1);
            if (n4 < this._$26621.size()) {
                final int n5 = this._$26625.get(Arrays.binarySearch(this._$26620.toArray(), this._$26621.get(n4))) - intValue;
                final int n6 = arrangesoldierPosition;
                final int arrangesoldierPosition2 = super.arrangesoldierPosition(this._$26621.get(n4).getTime());
                this.g2.setColor(Color.RED);
                this.g2.drawLine(n6, n2, arrangesoldierPosition2, n2);
                this.g2.drawLine(n6, n2 + 3, n6, n2 - 3);
                this.g2.drawLine(arrangesoldierPosition2, n2 - 3, arrangesoldierPosition2, n3);
                this.g2.drawLine(arrangesoldierPosition2 + 3, n3 + 3, arrangesoldierPosition2, n3);
                this.g2.drawLine(arrangesoldierPosition2 - 3, n3 + 3, arrangesoldierPosition2, n3);
                this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.TRAIN.STOP") + n5 / 100 + " m, " + (this._$26621.get(n4).getTime() - key.getTime()) / 1000L + ATPMessages.getString("MW.BEHAVIOR.TRAIN.STOP.SECOND") + " @ " + this._$3906.format(this._$26621.get(n4)), n6 + 1, n2 - 1);
            }
        }
    }
    
    private void _$26653(final int n) {
        this.g2.fillPolygon(new int[] { this._$26628[n], this._$26628[n] - 2, this._$26628[n] + 4 }, new int[] { this._$26629 - 2, this._$26629 + 2, this._$26629 + 2 }, 3);
    }
    
    private void _$26653(final int n, final int n2, final int n3, final int i) {
        if (n2 < 16380) {
            final int n4 = this._$26628[n];
            final int n5 = this._$26629 + 5;
            final double n6 = 1.5707963267948966;
            this.g2.translate(n4, n5);
            this.g2.rotate(n6);
            this._$26658(n, n2, n3);
            this.g2.drawString((i < 0 || i > 9) ? ("" + i) : this._$26665[i], -55, 0);
            this.g2.rotate(-n6);
            this._$26664(i);
            this.g2.translate(-n4, -n5);
            this._$26653(n);
        }
    }
    
    private void _$26653(final int n, final int n2) {
        this._$26653(n);
        final double n3 = 0.7853981633974483;
        final int n4 = this._$26628[n];
        final int n5 = this._$26629 + 15;
        this.g2.translate(n4, n5);
        this.g2.rotate(n3);
        if (n2 <= 16380) {
            this.g2.drawString("" + n2, 0, 0);
        }
        else {
            this.g2.drawString("" + n2, -50, -20);
        }
        this.g2.rotate(-n3);
        this.g2.translate(-n4, -n5);
    }
    
    private Vector _$26631() {
        final Vector vector = new Vector();
        for (int i = 0; i < this._$18234.getBTM().size() - 1; ++i) {
            final Vector e = this._$18234.getBTM().get(i);
            final int intValue = (int)e.get(6);
            final long time = ((Date)e.get(5)).getTime();
            final Vector e2 = this._$18234.getBTM().get(i + 1);
            final int intValue2 = (int)e2.get(6);
            final long time2 = ((Date)e2.get(5)).getTime();
            if (intValue < 16380 && intValue2 > 16380 && time2 - time < 4000L) {
                vector.add(e);
                vector.add(e2);
                ++i;
            }
            else if (intValue2 < 16380 && intValue > 16380 && time2 - time < 4000L) {
                ++i;
            }
        }
        return vector;
    }
    
    public Object getMousePointStatus() throws Exception {
        final int binarySearch = Arrays.binarySearch(this._$4436.toArray(), new Date(super.soloderReport()));
        if (binarySearch < 0) {
            try {
                return this._$26622.get(Math.abs(binarySearch) - 2);
            }
            catch (final ArrayIndexOutOfBoundsException ex) {
                return this._$26622.get(this._$26622.size() - 1);
            }
            catch (final Exception ex2) {
                return ATPMessages.getString("MW.GNL.ERROR");
            }
        }
        return this._$26622.get(binarySearch);
    }
    
    public void paintBody(final Graphics graphics) throws Exception {
        super.paintBody(graphics);
        this.g2 = (Graphics2D)graphics;
        this._$18223();
        this._$26640();
    }
    
    public void paintHeader(final Graphics graphics) throws Exception {
        super.paintHeader(graphics);
        this.g2 = (Graphics2D)graphics;
    }
    
    private void _$26658(final int n, final int i, final int j) {
        this.g2.setColor(Color.green);
        if (i == j) {
            this.g2.drawString("*", 25, 0);
            this.g2.drawString("" + i, 0, 0);
        }
        else if (j == 0) {
            this.g2.setColor(Color.red);
            this.g2.drawString("" + i, 0, 0);
            this.g2.setColor(Color.pink);
            this.g2.drawLine(-2, 0, -2, -10);
            this.g2.drawString("DBn/F", 0, -10);
            this.g2.setColor(Color.green);
        }
        else {
            this.g2.setColor(this.g2.getColor().darker().darker());
            this.g2.drawString("" + i, 0, 0);
            final int n2 = 10;
            final int n3 = (n - n2 > 0) ? (n - n2) : 0;
            final int n4 = (n + n2 < this._$26622.size()) ? (n + n2) : this._$26622.size();
            int n5 = -1;
            for (int k = n3; k < n4; ++k) {
                if ((int)this._$26622.get(k) == j) {
                    n5 = k;
                    break;
                }
            }
            if (n5 == -1) {
                this.g2.setColor(Color.pink);
                this.g2.drawLine(-2, 0, -2, -10);
                this.g2.drawString(" " + j, 0, -10);
                this.g2.setColor(Color.green);
            }
            else {
                this.g2.drawLine(-2, 0, -2, -Math.abs(this._$26628[n5] - this._$26628[n]));
            }
        }
    }
    
    public void resetScale() {
        final Vector $26631 = this._$26631();
        this._$4436 = new Vector();
        this._$26622 = new Vector();
        this._$26623 = new Vector();
        this._$26624 = new Vector();
        this._$26626 = new Vector();
        this._$26628 = new int[$26631.size()];
        this._$26629 = this.showsoldierRangeToDefence(this.para.MinNum);
        for (int i = 0; i < $26631.size(); ++i) {
            final Vector x = $26631.get(i);
            this._$4436.add(x.get(5));
            this._$26622.add(x.get(6));
            this._$26623.add(x.get(9));
            this._$26624.add(x.get(7));
            final int intValue = (int)x.get(8);
            if ((int)x.get(6) == 2025) {
                System.out.println(x);
            }
            int n = 0;
            for (int j = 0; j < 10; ++j) {
                if ((int)x.get(10 + j) != 0) {
                    n = -1;
                    break;
                }
                n += (int)x.get(10 + j);
            }
            if (n == 0) {
                this._$26626.add(new Integer(-(intValue + 10)));
                this._$26628[i] = this.showsoldierWhereToStand(((Date)this._$4436.get(i)).getTime());
            }
            else {
                if (intValue > 9) {
                    this._$26626.add(new Integer(-intValue));
                }
                else {
                    try {
                        this._$26626.add(x.get(10 + intValue));
                    }
                    catch (final Exception ex) {
                        this._$26626.add(new Integer(-intValue));
                        ex.printStackTrace();
                    }
                }
                this._$26628[i] = this.showsoldierWhereToStand(((Date)this._$4436.get(i)).getTime());
            }
        }
        this._$26620 = new Vector();
        this._$26621 = new Vector();
        this._$26625 = new Vector();
        int intValue2 = 0;
        for (int k = 0; k < this._$18234.getTimeStamp().size(); ++k) {
            final Vector vector = this._$18234.getTimeStamp().get(k);
            this._$26620.add(vector.get(5));
            this._$26625.add(vector.get(7));
            boolean b = false;
            if (intValue2 != 0 && (int)vector.get(6) == 0) {
                b = true;
            }
            if (b) {
                this._$26621.add(vector.get(5));
            }
            intValue2 = (int)vector.get(6);
        }
    }
    
    public void setData(final ATPMissionDetail $18234) {
        this._$18234 = $18234;
        this.resetScale();
    }
    
    public void setData(final Vector vector) {
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
    
    private void _$26664(final int n) {
        final int[] array = { -28, -23, -18, -13 };
        final Color[] array2 = { Color.green, Color.yellow, Color.red, Color.yellow };
        int[] array3 = { 0, 0, 0, 0 };
        switch (n) {
            case 0: {
                array3 = new int[] { 1, 0, 0, 0 };
                break;
            }
            case 1: {
                array3 = new int[] { 0, 1, 0, 0 };
                break;
            }
            case 2: {
                array3 = new int[] { 0, 2, 0, 0 };
                break;
            }
            case 3: {
                array3 = new int[] { 0, 0, 1, 2 };
                break;
            }
            case 4: {
                array3 = new int[] { 0, 0, 1, 1 };
                break;
            }
            case 5: {
                array3 = new int[] { 0, 0, 2, 0 };
                break;
            }
            case 6: {
                array3 = new int[] { 0, 0, 1, 0 };
                break;
            }
            case 7: {
                array3 = new int[] { 0, 0, 0, 0 };
                break;
            }
            case 8: {
                array3 = new int[] { 0, 1, 0, 1 };
                break;
            }
            case 9: {
                break;
            }
            default: {
                array3 = new int[] { 3, 3, 3, 3 };
                break;
            }
        }
        this.g2.setColor(Color.gray.darker().darker().darker());
        this.g2.fillRect(0, array[0] - 2, 6, 25);
        this.g2.drawLine(1, array[0] - 3, 4, array[0] - 3);
        for (int i = 0; i < array3.length; ++i) {
            switch (array3[i]) {
                case 0: {
                    break;
                }
                case 1:
                case 2:
                case 3: {
                    this.g2.setColor(Color.blue.darker().darker());
                    this.g2.fillOval(0, array[i] - 1, 5, 6);
                    this.g2.setColor(array2[i].brighter());
                    this.g2.fillOval(0, array[i], 4, 4);
                    this.g2.setColor(Color.blue.darker());
                    this.g2.drawLine(0, array[i] - 1, 3, array[i] - 1);
                    this.g2.setColor(Color.blue.darker().darker());
                    this.g2.drawLine(2, array[i], 4, array[i]);
                    break;
                }
                default: {
                    this.g2.setColor(Color.green.darker().darker());
                    this.g2.fillOval(0, array[i] - 1, 5, 5);
                    this.g2.setColor(Color.red);
                    this.g2.drawString("x", 0, array[i]);
                    break;
                }
            }
        }
        this.g2.setColor(Color.green);
    }
    
    private void _$18223() throws Exception {
        if (this._$4436.size() == 0) {
            super.drawBasicMessage();
        }
        else {
            this.g2.setColor(this._$18217);
            if (super.isFollowMode()) {
                this._$18225();
            }
            else {
                this._$18226();
            }
        }
    }
    
    private void _$18225() {
        for (int n = 0; n < this._$26622.size() && this._$26628[n] <= DrawBase.mouseX + 600; ++n) {
            if (this._$26628[n] >= DrawBase.mouseX - 600) {
                if (this._$26627) {
                    if (this._$26626.get(n) == null) {
                        this._$26653(n, new Integer(this._$26622.get(n).toString()));
                    }
                    else {
                        this._$26653(n, new Integer(this._$26622.get(n).toString()), new Integer(this._$26623.get(n).toString()), new Integer(this._$26626.get(n).toString()));
                    }
                }
                else {
                    this._$26653(n, new Integer(this._$26622.get(n).toString()));
                }
            }
        }
    }
    
    private void _$18226() {
        for (int i = 0; i < this._$26622.size(); ++i) {
            if (this._$26627) {
                this._$26653(i, new Integer(this._$26622.get(i).toString()), new Integer(this._$26623.get(i).toString()), new Integer(this._$26626.get(i).toString()));
            }
            else {
                this._$26653(i, new Integer(this._$26622.get(i).toString()));
            }
        }
    }
    
    private void _$26640() throws Exception {
        if (this._$26624.size() != 0) {
            final int binarySearch = Arrays.binarySearch(this._$4436.toArray(), new Date(super.soloderReport()));
            final int n = (binarySearch >= 0) ? binarySearch : ((Math.abs(binarySearch) - 2 < 0) ? 0 : (Math.abs(binarySearch) - 3));
            final int intValue = this._$26624.get(n);
            final int binarySearch2 = Arrays.binarySearch(this._$26620.toArray(), new Date(super.soloderReport()));
            final int intValue2 = this._$26625.get((binarySearch2 >= 0) ? binarySearch2 : ((Math.abs(binarySearch2) - 2 < 0) ? 0 : (Math.abs(binarySearch2) - 2)));
            final int n2 = intValue2 - intValue;
            final int showsoldierWhereToStand = super.showsoldierWhereToStand(this._$4436.get(n).getTime());
            final int arrangesoldierPosition = super.arrangesoldierPosition(super.soloderReport());
            final int n3 = super.showsoldierRangeToDefence(this.para.MaxNum) - 11;
            final int n4 = super.showsoldierRangeToDefence(this.para.MinNum) - 2;
            this.g2.drawLine(showsoldierWhereToStand, n3, arrangesoldierPosition, n3);
            this.g2.drawLine(showsoldierWhereToStand, n3 - 5, showsoldierWhereToStand, n3 + 5);
            this.g2.drawLine(arrangesoldierPosition, n3 + 3, arrangesoldierPosition, n4);
            this.g2.drawLine(arrangesoldierPosition - 5, n4 - 5, arrangesoldierPosition, n4);
            this.g2.drawLine(arrangesoldierPosition + 5, n4 - 5, arrangesoldierPosition, n4);
            this.g2.setColor(new Color(Color.blue.getRed(), Color.blue.getGreen(), Color.blue.getBlue(), 220));
            if (!this.outOfView) {
                this.g2.fillRect(arrangesoldierPosition - 14, n3 - 24, 210, 24);
                this.g2.setColor(Color.ORANGE);
                this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.DISTANCE_TO_BALISE") + ": " + n2 / 100 + "m", arrangesoldierPosition - 10, n3 - 3);
                this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.BALISE_ID") + ": " + this._$26622.get(n), arrangesoldierPosition - 10, n3 - 14);
                this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.TRAIN_POSITION") + ": " + intValue2 / 100, arrangesoldierPosition - 10 + 100, n3 - 3);
                this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.BALISE_POSITION") + ": " + intValue / 100, arrangesoldierPosition - 10 + 100, n3 - 14);
            }
            else {
                this.g2.fillRect(arrangesoldierPosition - 14 - 250, n3 - 24, 210, 24);
                this.g2.setColor(Color.ORANGE);
                this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.DISTANCE_TO_BALISE") + ": " + n2 / 100 + "m", arrangesoldierPosition - 10 - 250, n3 - 3);
                this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.BALISE_ID") + ": " + this._$26622.get(n), arrangesoldierPosition - 10 - 250, n3 - 14);
                this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.TRAIN_POSITION") + ": " + intValue2 / 100, arrangesoldierPosition - 10 + 100 - 250, n3 - 3);
                this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.BALISE_POSITION") + ": " + intValue / 100, arrangesoldierPosition - 10 + 100 - 250, n3 - 14);
            }
            this.g2.drawLine(showsoldierWhereToStand, n3, arrangesoldierPosition, n3);
            this.g2.drawLine(showsoldierWhereToStand, n3 - 5, showsoldierWhereToStand, n3 + 5);
            this.g2.drawLine(arrangesoldierPosition, n3 + 3, arrangesoldierPosition, n4);
            this.g2.drawLine(arrangesoldierPosition - 3, n4 - 3, arrangesoldierPosition, n4);
            this.g2.drawLine(arrangesoldierPosition + 3, n4 - 3, arrangesoldierPosition, n4);
            final int binarySearch3 = Arrays.binarySearch(this._$26621.toArray(), new Date(super.soloderReport()));
            final int index = (binarySearch3 >= 0) ? binarySearch3 : (Math.abs(binarySearch3) - 1);
            if (index < this._$26621.size()) {
                final int n5 = this._$26625.get(Arrays.binarySearch(this._$26620.toArray(), this._$26621.get(index))) - intValue2;
                final int n6 = arrangesoldierPosition;
                final int arrangesoldierPosition2 = super.arrangesoldierPosition(this._$26621.get(index).getTime());
                this.g2.setColor(Color.RED);
                this.g2.drawLine(n6, n3, arrangesoldierPosition2, n3);
                this.g2.drawLine(n6, n3 + 3, n6, n3 - 3);
                this.g2.drawLine(arrangesoldierPosition2, n3 - 3, arrangesoldierPosition2, n4);
                this.g2.drawLine(arrangesoldierPosition2 - 3, n4 - 3, arrangesoldierPosition2, n4);
                this.g2.drawLine(arrangesoldierPosition2 + 3, n4 - 3, arrangesoldierPosition2, n4);
                final long n7 = this._$26621.get(index).getTime() - super.soloderReport();
                if (!this.outOfView) {
                    this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.TRAIN.STOP") + n5 / 100 + " m, " + n7 / 1000L + ATPMessages.getString("MW.BEHAVIOR.TRAIN.STOP.SECOND") + " @ " + this._$3906.format(this._$26621.get(index)), n6 + 5, n3 + 10);
                }
                else {
                    this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.TRAIN.STOP") + n5 / 100 + " m, " + n7 / 1000L + ATPMessages.getString("MW.BEHAVIOR.TRAIN.STOP.SECOND") + " @ " + this._$3906.format(this._$26621.get(index)), n6 - 250, n3 + 10);
                }
            }
        }
    }
}
