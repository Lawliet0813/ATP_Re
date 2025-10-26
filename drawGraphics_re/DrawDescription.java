package drawGraphics;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;
import javax.swing.JFrame;

public class DrawDescription {
  private String _$26686 = ATPMessages.getString("MW.BEHAVIOR.CAN_NOT_REACH_DATA");
  
  private Vector _$2586;
  
  private int _$26682 = 1000;
  
  private int _$26681 = 430;
  
  private boolean _$26012 = true;
  
  private boolean _$26015 = true;
  
  private boolean _$26008 = true;
  
  private boolean _$26011 = true;
  
  private boolean _$26009 = true;
  
  private boolean _$26006 = true;
  
  private boolean _$26005 = true;
  
  private boolean _$26010 = true;
  
  private Vector _$26683;
  
  private Vector _$18188;
  
  private String _$26685 = ATPMessages.getString("MW.BEHAVIOR.NULLSTATUS");
  
  private Vector _$1687;
  
  private boolean _$26684 = ATPMessages.showChinese;
  
  private Vector _$18391;
  
  private Vector _$18390;
  
  private Vector _$18392;
  
  private int _$1632 = 0;
  
  private int _$2019 = 0;
  
  public DrawDescription() {
    try {
      InitParameters.start();
      if (this._$26684) {
        _$26687();
      } else {
        _$26688();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$26003(Graphics2D paramGraphics2D) {
    paramGraphics2D.setColor(new Color(50, 50, 50, 200));
    paramGraphics2D.fillRect(this._$1632, 0, this._$26681, this._$26682);
    int i = this._$1632 + 10;
    int j = this._$1632 + 30;
    int k = this._$1632 + 200;
    int m = this._$1632 + 230;
    byte b1 = 12;
    byte b2 = 20;
    int n = this._$1632;
    int i1 = 15;
    paramGraphics2D.setColor(InitParameters.SpeedColor);
    paramGraphics2D.drawString(ATPMessages.getString("MW.GNL.SPEED"), i - 5, i1);
    i1 += b1;
    paramGraphics2D.drawString("-", i + 15, i1);
    paramGraphics2D.drawString(ATPMessages.getString("MW.GNL.KM/HR"), j, i1);
    i1 += b2;
    if (this._$26012) {
      paramGraphics2D.setColor(InitParameters.BTColor);
      paramGraphics2D.drawString(ATPMessages.getString("MW.GNL.TARGET_DISTANCE"), i - 5, i1);
      i1 += b1;
      paramGraphics2D.drawString("-", i + 15, i1);
      paramGraphics2D.drawString(ATPMessages.getString("MW.GNL.METER"), j, i1);
      i1 += b2;
    } 
    if (this._$26010) {
      paramGraphics2D.setColor(InitParameters.WarnColor);
      paramGraphics2D.drawString(ATPMessages.getString("MW.GNL.WARN"), i - 5, i1);
      if (this._$18392 != null) {
        for (byte b = 0; b < this._$18392.size(); b++) {
          String str;
          i1 += b1;
          Vector vector = this._$18392.get(b);
          paramGraphics2D.drawString(((Integer)vector.get(0)).intValue() + "", i, i1);
          paramGraphics2D.drawString("-", i + 15, i1);
          if (vector.get(1) == null) {
            str = this._$26685;
          } else {
            str = vector.get(1).toString();
          } 
          paramGraphics2D.drawString(str, j, i1);
        } 
      } else {
        i1 += b1;
        paramGraphics2D.drawString("-", i + 15, i1);
        paramGraphics2D.drawString(this._$26686, j, i1);
      } 
      i1 += b2;
    } 
    if (this._$26005) {
      paramGraphics2D.setColor(InitParameters.SlipColor);
      paramGraphics2D.drawString(ATPMessages.getString("MW.GNL.SLIP"), i - 5, i1);
      if (this._$18390 != null) {
        for (byte b = 0; b < this._$18390.size(); b++) {
          String str;
          i1 += b1;
          Vector vector = this._$18390.get(b);
          paramGraphics2D.drawString(((Integer)vector.get(0)).intValue() + "", i, i1);
          paramGraphics2D.drawString("-", i + 15, i1);
          if (vector.get(1) == null) {
            str = this._$26685;
          } else {
            str = vector.get(1).toString();
          } 
          paramGraphics2D.drawString(str, j, i1);
        } 
      } else {
        i1 += b1;
        paramGraphics2D.drawString("-", i + 15, i1);
        paramGraphics2D.drawString(this._$26686, j, i1);
      } 
      i1 += b2;
    } 
    if (this._$26006) {
      paramGraphics2D.setColor(InitParameters.SlideColor);
      paramGraphics2D.drawString(ATPMessages.getString("MW.GNL.SLIDE"), i - 5, i1);
      if (this._$18391 != null) {
        for (byte b = 0; b < this._$18391.size(); b++) {
          String str;
          i1 += b1;
          Vector vector = this._$18391.get(b);
          paramGraphics2D.drawString(((Integer)vector.get(0)).intValue() + "", i, i1);
          paramGraphics2D.drawString("-", i + 15, i1);
          if (vector.get(1) == null) {
            str = this._$26685;
          } else {
            str = vector.get(1).toString();
          } 
          paramGraphics2D.drawString(str, j, i1);
        } 
      } else {
        i1 += b1;
        paramGraphics2D.drawString("-", i + 15, i1);
        paramGraphics2D.drawString(this._$26686, j, i1);
      } 
      i1 += b2;
    } 
    if (this._$26011) {
      paramGraphics2D.setColor(InitParameters.ModeColor);
      paramGraphics2D.drawString(ATPMessages.getString("MW.GNL.ATP_MODE"), i - 5, i1);
      if (this._$18188 != null) {
        for (byte b = 0; b < this._$18188.size(); b++) {
          String str;
          i1 += b1;
          Vector vector = this._$18188.get(b);
          paramGraphics2D.drawString(((Integer)vector.get(0)).intValue() + "", i, i1);
          paramGraphics2D.drawString("-", i + 15, i1);
          if (vector.get(1) == null) {
            str = this._$26685;
          } else {
            str = vector.get(1).toString();
          } 
          paramGraphics2D.drawString(str, j, i1);
        } 
      } else {
        i1 += b1;
        paramGraphics2D.drawString("-", i + 15, i1);
        paramGraphics2D.drawString(this._$26686, j, i1);
      } 
      i1 += b2;
    } 
    if (this._$26008) {
      paramGraphics2D.setColor(InitParameters.EBColor);
      paramGraphics2D.drawString(ATPMessages.getString("MW.GNL.EB"), i - 5, i1);
      if (this._$26683 != null) {
        for (byte b = 0; b < this._$26683.size(); b++) {
          String str;
          i1 += b1;
          Vector vector = this._$26683.get(b);
          paramGraphics2D.drawString(((Integer)vector.get(0)).intValue() + "", i, i1);
          paramGraphics2D.drawString("-", i + 15, i1);
          if (vector.get(1) == null) {
            str = this._$26685;
          } else {
            str = vector.get(1).toString();
          } 
          paramGraphics2D.drawString(str, j, i1);
        } 
      } else {
        i1 += b1;
        paramGraphics2D.drawString("-", i + 15, i1);
        paramGraphics2D.drawString(this._$26686, j, i1);
      } 
      i1 += b2;
    } 
    if (this._$26009) {
      paramGraphics2D.setColor(InitParameters.SBColor);
      paramGraphics2D.drawString(ATPMessages.getString("MW.GNL.SB"), i - 5, i1);
      if (this._$1687 != null) {
        for (byte b = 0; b < this._$1687.size(); b++) {
          String str;
          i1 += b1;
          Vector vector = this._$1687.get(b);
          paramGraphics2D.drawString(((Integer)vector.get(0)).intValue() + "", i, i1);
          paramGraphics2D.drawString("-", i + 15, i1);
          if (vector.get(1) == null) {
            str = this._$26685;
          } else {
            str = vector.get(1).toString();
          } 
          paramGraphics2D.drawString(str, j, i1);
        } 
      } else {
        i1 += b1;
        paramGraphics2D.drawString("-", i + 15, i1);
        paramGraphics2D.drawString(this._$26686, j, i1);
      } 
      i1 += b2;
    } 
    if (this._$26015) {
      this._$26681 = 430;
      if (this._$2586 != null) {
        paramGraphics2D.setColor(Color.yellow.darker());
        paramGraphics2D.drawString(ATPMessages.getString("MW.GNL.DRIVER_MESSAGE"), i - 5, i1);
        for (byte b3 = 0; b3 < 4; b3++) {
          String str;
          i1 += b1;
          Vector vector = this._$2586.get(b3);
          paramGraphics2D.drawString(((Integer)vector.get(0)).intValue() + "", i, i1);
          paramGraphics2D.drawString("-", i + 19, i1);
          if (vector.get(1) == null) {
            str = this._$26685;
          } else {
            str = vector.get(1).toString();
          } 
          paramGraphics2D.drawString(str, j + 4, i1);
        } 
        i1 = 15;
        paramGraphics2D.setColor(Color.yellow.darker().darker());
        paramGraphics2D.drawLine(k - 2, 0, k - 2, 1000);
        paramGraphics2D.setColor(Color.yellow.darker());
        paramGraphics2D.drawString(ATPMessages.getString("MW.GNL.DRIVER_MESSAGE"), k, i1);
        for (byte b4 = 4; b4 < this._$2586.size(); b4++) {
          String str;
          i1 += b1;
          Vector vector = this._$2586.get(b4);
          paramGraphics2D.drawString(((Integer)vector.get(0)).intValue() + "", k + 10, i1);
          paramGraphics2D.drawString("-", m, i1);
          if (vector.get(1) == null) {
            str = this._$26685;
          } else {
            str = vector.get(1).toString();
          } 
          paramGraphics2D.drawString(str, m + 5, i1);
        } 
      } else {
        i1 += b1;
        paramGraphics2D.drawString("-", 260, i1);
        paramGraphics2D.drawString(this._$26686, 200, i1);
      } 
    } else {
      this._$26681 = 200;
    } 
  }
  
  private void _$26687() {
    try {
      ConnectDB connectDB = new ConnectDB();
      String str = "SELECT NO, DESCRIPTION_C FROM M_WARNING";
      this._$18392 = connectDB.getData(str);
      str = "SELECT NO, DESCRIPTION_C FROM M_SLIP";
      this._$18390 = connectDB.getData(str);
      str = "SELECT NO, DESCRIPTION_C FROM M_SLIDE";
      this._$18391 = connectDB.getData(str);
      str = "SELECT No, Description_C FROM M_MODE";
      this._$18188 = connectDB.getData(str);
      str = "SELECT NO, DESCRIPTION_C FROM M_EmerBrake";
      this._$26683 = connectDB.getData(str);
      str = "SELECT NO, DESCRIPTION_C FROM M_ServiceBrake";
      this._$1687 = connectDB.getData(str);
      str = "SELECT NO, DESCRIPTION_C FROM Q_TEXT WHERE DESCRIPTION_C != DESCRIPTION_E ORDER BY 1";
      this._$2586 = connectDB.getData(str);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$26688() {
    try {
      ConnectDB connectDB = new ConnectDB();
      String str = "SELECT NO, DESCRIPTION_E FROM M_WARNING";
      this._$18392 = connectDB.getData(str);
      str = "SELECT NO, DESCRIPTION_E FROM M_SLIP";
      this._$18390 = connectDB.getData(str);
      str = "SELECT NO, DESCRIPTION_E FROM M_SLIDE";
      this._$18391 = connectDB.getData(str);
      str = "SELECT No, Description_E FROM M_MODE";
      this._$18188 = connectDB.getData(str);
      str = "SELECT NO, DESCRIPTION_E FROM M_EmerBrake";
      this._$26683 = connectDB.getData(str);
      str = "SELECT NO, DESCRIPTION_E FROM M_ServiceBrake";
      this._$1687 = connectDB.getData(str);
      str = "SELECT NO, DESCRIPTION_E FROM Q_TEXT WHERE DESCRIPTION_C != DESCRIPTION_E ORDER BY 1";
      this._$2586 = connectDB.getData(str);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public int getHeight() {
    return this._$26682;
  }
  
  public int getWidth() {
    return this._$26681;
  }
  
  public static void main(String[] paramArrayOfString) {
    JFrame jFrame = new JFrame("test");
    jFrame.setSize(500, 1000);
    jFrame.setBackground(Color.black);
    com.MiTAC.TRA.ATP.drawGraphics.DrawDescription drawDescription = new com.MiTAC.TRA.ATP.drawGraphics.DrawDescription();
    jFrame.show();
    jFrame.repaint();
    drawDescription.setLocation(0, 50);
    drawDescription.paint(jFrame.getGraphics());
    drawDescription.paint(jFrame.getGraphics());
  }
  
  public void paint(Graphics paramGraphics) {
    _$26003((Graphics2D)paramGraphics);
  }
  
  public void setDrawBT(boolean paramBoolean) {
    this._$26012 = paramBoolean;
  }
  
  public void setDrawDriverMessage(boolean paramBoolean) {
    this._$26015 = paramBoolean;
  }
  
  public void setDrawEB(boolean paramBoolean) {
    this._$26008 = paramBoolean;
  }
  
  public void setDrawMode(boolean paramBoolean) {
    this._$26011 = paramBoolean;
  }
  
  public void setDrawSB(boolean paramBoolean) {
    this._$26009 = paramBoolean;
  }
  
  public void setDrawSlide(boolean paramBoolean) {
    this._$26006 = paramBoolean;
  }
  
  public void setDrawSlip(boolean paramBoolean) {
    this._$26005 = paramBoolean;
  }
  
  public void setDrawWarn(boolean paramBoolean) {
    this._$26010 = paramBoolean;
  }
  
  public void setLocation(int paramInt1, int paramInt2) {
    this._$1632 = paramInt1;
    this._$2019 = paramInt2;
  }
}
