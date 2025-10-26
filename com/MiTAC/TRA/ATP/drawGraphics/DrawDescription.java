// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.drawGraphics;

import java.awt.Graphics;
import javax.swing.JFrame;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.awt.Color;
import java.awt.Graphics2D;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.ATPMessages;
import java.util.Vector;

public class DrawDescription
{
    private String _$26686;
    private Vector _$2586;
    private int _$26682;
    private int _$26681;
    private boolean _$26012;
    private boolean _$26015;
    private boolean _$26008;
    private boolean _$26011;
    private boolean _$26009;
    private boolean _$26006;
    private boolean _$26005;
    private boolean _$26010;
    private Vector _$26683;
    private Vector _$18188;
    private String _$26685;
    private Vector _$1687;
    private boolean _$26684;
    private Vector _$18391;
    private Vector _$18390;
    private Vector _$18392;
    private int _$1632;
    private int _$2019;
    
    public DrawDescription() {
        this._$1632 = 0;
        this._$2019 = 0;
        this._$26681 = 430;
        this._$26682 = 1000;
        this._$26684 = ATPMessages.showChinese;
        this._$26010 = true;
        this._$26005 = true;
        this._$26006 = true;
        this._$26011 = true;
        this._$26008 = true;
        this._$26009 = true;
        this._$26012 = true;
        this._$26015 = true;
        this._$26685 = ATPMessages.getString("MW.BEHAVIOR.NULLSTATUS");
        this._$26686 = ATPMessages.getString("MW.BEHAVIOR.CAN_NOT_REACH_DATA");
        try {
            InitParameters.start();
            if (this._$26684) {
                this._$26687();
            }
            else {
                this._$26688();
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void _$26003(final Graphics2D graphics2D) {
        graphics2D.setColor(new Color(50, 50, 50, 200));
        graphics2D.fillRect(this._$1632, 0, this._$26681, this._$26682);
        final int n = this._$1632 + 10;
        final int n2 = this._$1632 + 30;
        final int n3 = this._$1632 + 200;
        final int n4 = this._$1632 + 230;
        final int n5 = 12;
        final int n6 = 20;
        final int $1632 = this._$1632;
        final int n7 = 15;
        graphics2D.setColor(InitParameters.SpeedColor);
        graphics2D.drawString(ATPMessages.getString("MW.GNL.SPEED"), n - 5, n7);
        final int n8 = n7 + n5;
        graphics2D.drawString("-", n + 15, n8);
        graphics2D.drawString(ATPMessages.getString("MW.GNL.KM/HR"), n2, n8);
        int n9 = n8 + n6;
        if (this._$26012) {
            graphics2D.setColor(InitParameters.BTColor);
            graphics2D.drawString(ATPMessages.getString("MW.GNL.TARGET_DISTANCE"), n - 5, n9);
            final int n10 = n9 + n5;
            graphics2D.drawString("-", n + 15, n10);
            graphics2D.drawString(ATPMessages.getString("MW.GNL.METER"), n2, n10);
            n9 = n10 + n6;
        }
        if (this._$26010) {
            graphics2D.setColor(InitParameters.WarnColor);
            graphics2D.drawString(ATPMessages.getString("MW.GNL.WARN"), n - 5, n9);
            if (this._$18392 != null) {
                for (int i = 0; i < this._$18392.size(); ++i) {
                    n9 += n5;
                    final Vector vector = this._$18392.get(i);
                    graphics2D.drawString((int)vector.get(0) + "", n, n9);
                    graphics2D.drawString("-", n + 15, n9);
                    String s;
                    if (vector.get(1) == null) {
                        s = this._$26685;
                    }
                    else {
                        s = vector.get(1).toString();
                    }
                    graphics2D.drawString(s, n2, n9);
                }
            }
            else {
                n9 += n5;
                graphics2D.drawString("-", n + 15, n9);
                graphics2D.drawString(this._$26686, n2, n9);
            }
            n9 += n6;
        }
        if (this._$26005) {
            graphics2D.setColor(InitParameters.SlipColor);
            graphics2D.drawString(ATPMessages.getString("MW.GNL.SLIP"), n - 5, n9);
            if (this._$18390 != null) {
                for (int j = 0; j < this._$18390.size(); ++j) {
                    n9 += n5;
                    final Vector vector2 = this._$18390.get(j);
                    graphics2D.drawString((int)vector2.get(0) + "", n, n9);
                    graphics2D.drawString("-", n + 15, n9);
                    String s2;
                    if (vector2.get(1) == null) {
                        s2 = this._$26685;
                    }
                    else {
                        s2 = vector2.get(1).toString();
                    }
                    graphics2D.drawString(s2, n2, n9);
                }
            }
            else {
                n9 += n5;
                graphics2D.drawString("-", n + 15, n9);
                graphics2D.drawString(this._$26686, n2, n9);
            }
            n9 += n6;
        }
        if (this._$26006) {
            graphics2D.setColor(InitParameters.SlideColor);
            graphics2D.drawString(ATPMessages.getString("MW.GNL.SLIDE"), n - 5, n9);
            if (this._$18391 != null) {
                for (int k = 0; k < this._$18391.size(); ++k) {
                    n9 += n5;
                    final Vector vector3 = this._$18391.get(k);
                    graphics2D.drawString((int)vector3.get(0) + "", n, n9);
                    graphics2D.drawString("-", n + 15, n9);
                    String s3;
                    if (vector3.get(1) == null) {
                        s3 = this._$26685;
                    }
                    else {
                        s3 = vector3.get(1).toString();
                    }
                    graphics2D.drawString(s3, n2, n9);
                }
            }
            else {
                n9 += n5;
                graphics2D.drawString("-", n + 15, n9);
                graphics2D.drawString(this._$26686, n2, n9);
            }
            n9 += n6;
        }
        if (this._$26011) {
            graphics2D.setColor(InitParameters.ModeColor);
            graphics2D.drawString(ATPMessages.getString("MW.GNL.ATP_MODE"), n - 5, n9);
            if (this._$18188 != null) {
                for (int l = 0; l < this._$18188.size(); ++l) {
                    n9 += n5;
                    final Vector vector4 = this._$18188.get(l);
                    graphics2D.drawString((int)vector4.get(0) + "", n, n9);
                    graphics2D.drawString("-", n + 15, n9);
                    String s4;
                    if (vector4.get(1) == null) {
                        s4 = this._$26685;
                    }
                    else {
                        s4 = vector4.get(1).toString();
                    }
                    graphics2D.drawString(s4, n2, n9);
                }
            }
            else {
                n9 += n5;
                graphics2D.drawString("-", n + 15, n9);
                graphics2D.drawString(this._$26686, n2, n9);
            }
            n9 += n6;
        }
        if (this._$26008) {
            graphics2D.setColor(InitParameters.EBColor);
            graphics2D.drawString(ATPMessages.getString("MW.GNL.EB"), n - 5, n9);
            if (this._$26683 != null) {
                for (int index = 0; index < this._$26683.size(); ++index) {
                    n9 += n5;
                    final Vector vector5 = this._$26683.get(index);
                    graphics2D.drawString((int)vector5.get(0) + "", n, n9);
                    graphics2D.drawString("-", n + 15, n9);
                    String s5;
                    if (vector5.get(1) == null) {
                        s5 = this._$26685;
                    }
                    else {
                        s5 = vector5.get(1).toString();
                    }
                    graphics2D.drawString(s5, n2, n9);
                }
            }
            else {
                n9 += n5;
                graphics2D.drawString("-", n + 15, n9);
                graphics2D.drawString(this._$26686, n2, n9);
            }
            n9 += n6;
        }
        if (this._$26009) {
            graphics2D.setColor(InitParameters.SBColor);
            graphics2D.drawString(ATPMessages.getString("MW.GNL.SB"), n - 5, n9);
            if (this._$1687 != null) {
                for (int index2 = 0; index2 < this._$1687.size(); ++index2) {
                    n9 += n5;
                    final Vector vector6 = this._$1687.get(index2);
                    graphics2D.drawString((int)vector6.get(0) + "", n, n9);
                    graphics2D.drawString("-", n + 15, n9);
                    String s6;
                    if (vector6.get(1) == null) {
                        s6 = this._$26685;
                    }
                    else {
                        s6 = vector6.get(1).toString();
                    }
                    graphics2D.drawString(s6, n2, n9);
                }
            }
            else {
                n9 += n5;
                graphics2D.drawString("-", n + 15, n9);
                graphics2D.drawString(this._$26686, n2, n9);
            }
            n9 += n6;
        }
        if (this._$26015) {
            this._$26681 = 430;
            if (this._$2586 != null) {
                graphics2D.setColor(Color.yellow.darker());
                graphics2D.drawString(ATPMessages.getString("MW.GNL.DRIVER_MESSAGE"), n - 5, n9);
                for (int index3 = 0; index3 < 4; ++index3) {
                    n9 += n5;
                    final Vector vector7 = this._$2586.get(index3);
                    graphics2D.drawString((int)vector7.get(0) + "", n, n9);
                    graphics2D.drawString("-", n + 19, n9);
                    String s7;
                    if (vector7.get(1) == null) {
                        s7 = this._$26685;
                    }
                    else {
                        s7 = vector7.get(1).toString();
                    }
                    graphics2D.drawString(s7, n2 + 4, n9);
                }
                int n11 = 15;
                graphics2D.setColor(Color.yellow.darker().darker());
                graphics2D.drawLine(n3 - 2, 0, n3 - 2, 1000);
                graphics2D.setColor(Color.yellow.darker());
                graphics2D.drawString(ATPMessages.getString("MW.GNL.DRIVER_MESSAGE"), n3, n11);
                for (int index4 = 4; index4 < this._$2586.size(); ++index4) {
                    n11 += n5;
                    final Vector vector8 = this._$2586.get(index4);
                    graphics2D.drawString((int)vector8.get(0) + "", n3 + 10, n11);
                    graphics2D.drawString("-", n4, n11);
                    String s8;
                    if (vector8.get(1) == null) {
                        s8 = this._$26685;
                    }
                    else {
                        s8 = vector8.get(1).toString();
                    }
                    graphics2D.drawString(s8, n4 + 5, n11);
                }
            }
            else {
                final int n12 = n9 + n5;
                graphics2D.drawString("-", 260, n12);
                graphics2D.drawString(this._$26686, 200, n12);
            }
        }
        else {
            this._$26681 = 200;
        }
    }
    
    private void _$26687() {
        try {
            final ConnectDB connectDB = new ConnectDB();
            this._$18392 = connectDB.getData("SELECT NO, DESCRIPTION_C FROM M_WARNING");
            this._$18390 = connectDB.getData("SELECT NO, DESCRIPTION_C FROM M_SLIP");
            this._$18391 = connectDB.getData("SELECT NO, DESCRIPTION_C FROM M_SLIDE");
            this._$18188 = connectDB.getData("SELECT No, Description_C FROM M_MODE");
            this._$26683 = connectDB.getData("SELECT NO, DESCRIPTION_C FROM M_EmerBrake");
            this._$1687 = connectDB.getData("SELECT NO, DESCRIPTION_C FROM M_ServiceBrake");
            this._$2586 = connectDB.getData("SELECT NO, DESCRIPTION_C FROM Q_TEXT WHERE DESCRIPTION_C != DESCRIPTION_E ORDER BY 1");
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void _$26688() {
        try {
            final ConnectDB connectDB = new ConnectDB();
            this._$18392 = connectDB.getData("SELECT NO, DESCRIPTION_E FROM M_WARNING");
            this._$18390 = connectDB.getData("SELECT NO, DESCRIPTION_E FROM M_SLIP");
            this._$18391 = connectDB.getData("SELECT NO, DESCRIPTION_E FROM M_SLIDE");
            this._$18188 = connectDB.getData("SELECT No, Description_E FROM M_MODE");
            this._$26683 = connectDB.getData("SELECT NO, DESCRIPTION_E FROM M_EmerBrake");
            this._$1687 = connectDB.getData("SELECT NO, DESCRIPTION_E FROM M_ServiceBrake");
            this._$2586 = connectDB.getData("SELECT NO, DESCRIPTION_E FROM Q_TEXT WHERE DESCRIPTION_C != DESCRIPTION_E ORDER BY 1");
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int getHeight() {
        return this._$26682;
    }
    
    public int getWidth() {
        return this._$26681;
    }
    
    public static void main(final String[] array) {
        final JFrame frame = new JFrame("test");
        frame.setSize(500, 1000);
        frame.setBackground(Color.black);
        final DrawDescription drawDescription = new DrawDescription();
        frame.show();
        frame.repaint();
        drawDescription.setLocation(0, 50);
        drawDescription.paint(frame.getGraphics());
        drawDescription.paint(frame.getGraphics());
    }
    
    public void paint(final Graphics graphics) {
        this._$26003((Graphics2D)graphics);
    }
    
    public void setDrawBT(final boolean $26012) {
        this._$26012 = $26012;
    }
    
    public void setDrawDriverMessage(final boolean $26015) {
        this._$26015 = $26015;
    }
    
    public void setDrawEB(final boolean $26008) {
        this._$26008 = $26008;
    }
    
    public void setDrawMode(final boolean $26011) {
        this._$26011 = $26011;
    }
    
    public void setDrawSB(final boolean $26009) {
        this._$26009 = $26009;
    }
    
    public void setDrawSlide(final boolean $26006) {
        this._$26006 = $26006;
    }
    
    public void setDrawSlip(final boolean $26005) {
        this._$26005 = $26005;
    }
    
    public void setDrawWarn(final boolean $26010) {
        this._$26010 = $26010;
    }
    
    public void setLocation(final int $1632, final int $1633) {
        this._$1632 = $1632;
        this._$2019 = $1633;
    }
}
