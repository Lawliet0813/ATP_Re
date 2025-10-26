package drawGraphics;

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

public class DrawGradient extends DrawBase implements drawATP {
  private Color _$18217 = Color.magenta;
  
  private boolean _$18218 = true;
  
  private Vector _$18214;
  
  private Stroke _$18036 = new BasicStroke();
  
  private Vector _$26656 = new Vector();
  
  private Vector _$26657 = new Vector();
  
  public DrawGradient(commonParaSetting paramcommonParaSetting, drawParameters paramdrawParameters, Vector paramVector) {
    super(paramcommonParaSetting, paramdrawParameters, paramVector);
  }
  
  public void paintBody(Graphics paramGraphics) throws Exception {
    super.paintBody(paramGraphics);
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
            int i7 = j;
            int i8 = m;
            int i9;
            for (i9 = i1; (i9 <= i8 || i9 < i + i3) && i7 < DrawBase.location.length - 1; i9 = DrawBase.location[i7] / 100)
              i8 = DrawBase.location[++i7 - 1] / 100; 
            k = showsoldierWhereToStand(DrawBase.time[i7 - 1].getTime());
            n = showsoldierWhereToStand(DrawBase.time[i7].getTime());
            i6 = k + (n - k) / (i9 - i8) * i3;
            this._$26656.add(new Integer(i6));
            Vector vector1 = new Vector();
            vector1.add(new Integer(i + i3));
            vector1.add(new Integer(i4));
            vector1.add(new Integer(i5));
            this._$26657.add(vector1);
            i += i3;
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
    this.g2.setColor(this._$18217);
    int i = 0;
    int j = 0;
    if (this._$26656.size() > 0) {
      Vector vector = this._$26657.get(0);
      int k = ((Integer)vector.get(2)).intValue();
      k = (((Integer)vector.get(1)).intValue() == 0) ? -k : k;
      i = ((Integer)this._$26656.get(0)).intValue();
      j = showsoldierRangeToDefence(k);
      this.g2.drawString(k + "", ((Integer)this._$26656.get(0)).intValue(), showsoldierRangeToDefence(k));
    } 
    for (byte b = 1; b < this._$26656.size(); b++) {
      Vector vector = this._$26657.get(b);
      int k = ((Integer)vector.get(2)).intValue();
      k = (((Integer)vector.get(1)).intValue() == 0) ? -k : k;
      if (j < showsoldierRangeToDefence(0)) {
        this.g2.setColor(this._$18217.darker());
        this.g2.drawRect(i, j, ((Integer)this._$26656.get(b)).intValue() - i, showsoldierRangeToDefence(0) - j);
      } else {
        this.g2.setColor(this._$18217);
        this.g2.drawRect(i, showsoldierRangeToDefence(0), ((Integer)this._$26656.get(b)).intValue() - i, j - showsoldierRangeToDefence(0));
      } 
      this.g2.setColor(this._$18217);
      if (k > 0) {
        this.g2.drawString(k + "", ((Integer)this._$26656.get(b)).intValue(), showsoldierRangeToDefence(k) - 2);
      } else if (k < 0) {
        this.g2.drawString(k + "", ((Integer)this._$26656.get(b)).intValue(), showsoldierRangeToDefence(k) + 12);
      } 
      i = ((Integer)this._$26656.get(b)).intValue();
      j = showsoldierRangeToDefence(k);
    } 
  }
  
  private void _$18225(GeneralPath paramGeneralPath) {}
  
  private void _$18226(GeneralPath paramGeneralPath) {}
}
