package drawGraphics;

import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.core.ATPMissionDetail;
import com.MiTAC.TRA.ATP.drawGraphics.DrawBase;
import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.drawGraphics.drawATP;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.util.Arrays;
import java.util.Vector;

public class DrawCurve extends DrawBase implements drawATP {
  private Color _$18217 = Color.blue.brighter();
  
  private boolean _$18218 = true;
  
  private Vector _$18214;
  
  private Vector _$26716 = new Vector();
  
  private Stroke _$18036 = new BasicStroke();
  
  private Vector _$26656 = new Vector();
  
  private Vector _$26657 = new Vector();
  
  public DrawCurve(commonParaSetting paramcommonParaSetting, drawParameters paramdrawParameters, Vector paramVector) {
    super(paramcommonParaSetting, paramdrawParameters, paramVector);
  }
  
  public void paintBody(Graphics paramGraphics) throws Exception {
    super.paintBody(paramGraphics);
    Color color1 = Color.gray.brighter();
    Color color2 = new Color(color1.getRed(), color1.getGreen(), color1.getBlue(), 50);
    paramGraphics.setColor(color2);
    paramGraphics.fillRect(1, showsoldierRangeToDefence(0), showBattleLingDepth(), showBattleLineRange() / 2 + 1);
    if (this._$18218)
      _$18223(); 
  }
  
  public void paintHeader(Graphics paramGraphics) throws Exception {
    super.paintHeader(paramGraphics);
  }
  
  public void resetScale() {
    this._$26656 = new Vector();
    this._$26657 = new Vector();
    for (byte b = 0; b < this._$18214.size(); b++) {
      try {
        Vector vector = this._$18214.get(b);
        int i = ((Integer)vector.get(2)).intValue() / 100;
        if (b + 1 == this._$18214.size()) {
          int i2 = 99999999;
        } else {
          Vector vector1 = this._$18214.get(b + 1);
          int i2 = ((Integer)vector1.get(2)).intValue() / 100;
        } 
        int j = Arrays.binarySearch((Object[])DrawBase.time, vector.get(0));
        j = (j < 0) ? (Math.abs(j) - 1) : j;
        int k = showsoldierWhereToStand(DrawBase.time[j].getTime());
        int m = DrawBase.location[j] / 100;
        int n = showsoldierWhereToStand(DrawBase.time[++j].getTime());
        int i1 = DrawBase.location[++j] / 100;
        for (byte b1 = 0; b1 < 9; b1++) {
          try {
            int i2 = 3 + 3 * b1;
            int i3 = ((Integer)vector.get(i2)).intValue();
            int i4 = ((Integer)vector.get(++i2)).intValue();
            int i5 = ((Integer)vector.get(++i2)).intValue();
            if (i3 == -1)
              break; 
            int i6 = -1;
            int i7 = -1;
            int i8 = j;
            int i9 = m;
            for (int i10 = i1;; i10 = DrawBase.location[i8] / 100) {
              if (i10 > i9 && i10 >= i + i3) {
                k = showsoldierWhereToStand(DrawBase.time[i8 - 1].getTime());
                n = showsoldierWhereToStand(DrawBase.time[i8].getTime());
                i6 = k + (n - k) / (i10 - i9) * i3;
                i8 = j;
                i9 = m;
                for (i10 = i1;; i10 = DrawBase.location[i8] / 100) {
                  if (i10 > i9 && i10 >= i + i3 + i4) {
                    k = showsoldierWhereToStand(DrawBase.time[i8 - 1].getTime());
                    n = showsoldierWhereToStand(DrawBase.time[i8].getTime());
                    i7 = k + (n - k) / (i10 - i9) * (i3 + i4);
                    Vector vector1 = new Vector();
                    vector1.add(new Integer(i6));
                    vector1.add(new Integer(i7));
                    this._$26656.add(vector1);
                    Vector vector2 = new Vector();
                    vector2.add(new Integer(i4));
                    for (byte b2 = 0; b2 < this._$26716.size(); b2++) {
                      Vector vector3 = this._$26716.get(b2);
                      if (i5 == ((Integer)vector3.get(0)).intValue()) {
                        i5 = ((Integer)vector3.get(1)).intValue();
                        if (vector3.get(2).toString().startsWith("Left"))
                          i5 = -i5; 
                        break;
                      } 
                    } 
                    vector2.add(new Integer(i5));
                    this._$26657.add(vector2);
                    i = i + i3 + i4;
                    break;
                  } 
                  i9 = DrawBase.location[++i8 - 1] / 100;
                } 
                break;
              } 
              i9 = DrawBase.location[++i8 - 1] / 100;
            } 
          } catch (Exception exception) {
            exception.printStackTrace();
          } 
        } 
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } 
  }
  
  public void setData(ATPMissionDetail paramATPMissionDetail) {
    drawStopStation(paramATPMissionDetail.getStopStation());
  }
  
  public void setData(Vector paramVector) {
    this._$18214 = paramVector;
    try {
      ConnectDB connectDB = new ConnectDB();
      String str = "SELECT * FROM M_CURVERADIUS";
      this._$26716 = connectDB.getData(str);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    resetScale();
  }
  
  public void setDataLineColor(Color paramColor) throws Exception {
    this._$18217 = paramColor;
  }
  
  public void setDrawCurve(boolean paramBoolean) {
    this._$18218 = paramBoolean;
  }
  
  public void setDrawType(int paramInt) {
    super.setDrawType(paramInt);
    if (paramInt != DrawBase.drawType)
      resetScale(); 
  }
  
  public void setStroke(Stroke paramStroke) {
    this._$18036 = paramStroke;
  }
  
  private void _$18223() throws Exception {
    for (byte b = 0; b < this._$26656.size(); b++) {
      Vector vector1 = this._$26656.get(b);
      int i = ((Integer)vector1.get(0)).intValue();
      int j = ((Integer)vector1.get(1)).intValue();
      Vector vector2 = this._$26657.get(b);
      int k = ((Integer)vector2.get(0)).intValue();
      int m = ((Integer)vector2.get(1)).intValue();
      if (m <= 0) {
        this.g2.setColor(this._$18217);
        this.g2.fillRect(i, showsoldierRangeToDefence(10), j - i, showsoldierRangeToDefence(0) - showsoldierRangeToDefence(10));
      } else {
        this.g2.setColor(this._$18217);
        this.g2.fillRect(i, showsoldierRangeToDefence(0), j - i, showsoldierRangeToDefence(-10) - showsoldierRangeToDefence(0) + 1);
      } 
      this.g2.setColor(this._$18217);
      if (m <= 0) {
        this.g2.drawString(-m + "", i, showsoldierRangeToDefence(this.para.MaxNum) - 2);
      } else {
        this.g2.drawString(m + "", i, showsoldierRangeToDefence(this.para.MinNum) + 12);
      } 
    } 
  }
  
  private void _$18225(GeneralPath paramGeneralPath) {}
  
  private void _$18226(GeneralPath paramGeneralPath) {}
}
