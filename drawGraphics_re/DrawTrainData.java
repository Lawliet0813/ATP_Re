package drawGraphics;

import com.MiTAC.TRA.ATP.drawGraphics.DrawBase;
import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.drawGraphics.drawATP;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.util.Date;
import java.util.Vector;

public class DrawTrainData extends DrawBase implements drawATP {
  private Color _$18217 = Color.red;
  
  private Date[] _$18215;
  
  private Vector[] _$18216;
  
  private boolean _$18218 = true;
  
  private Vector _$18214;
  
  private Stroke _$18036 = new BasicStroke();
  
  private int[] _$1632;
  
  private int _$2019;
  
  public DrawTrainData(commonParaSetting paramcommonParaSetting, drawParameters paramdrawParameters, Vector paramVector) {
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
    this._$18215 = new Date[this._$18214.size()];
    this._$1632 = new int[this._$18214.size()];
    for (byte b = 0; b < this._$18214.size(); b++) {
      Vector vector = this._$18214.get(b);
      this._$18215[b] = vector.get(5);
      this._$1632[b] = showsoldierWhereToStand(this._$18215[b].getTime());
      this._$2019 = showsoldierRangeToDefence(this.para.MaxNum) + 1;
    } 
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
    Stroke stroke = this.g2.getStroke();
    this.g2.setStroke(this._$18036);
    this.g2.setColor(this._$18217);
    if (this._$18215.length == 0) {
      drawBasicMessage();
    } else {
      for (byte b = 0; b < this._$1632.length; b++) {
        Vector vector = this._$18214.get(b);
        byte b1 = (this._$1632[b] - 70 < 0) ? 70 : this._$1632[b];
        this.g2.setColor(Color.red);
        this.g2.fillOval(this._$1632[b], this._$2019 - 30, 3, 3);
        this.g2.drawOval(this._$1632[b] - 2, this._$2019 - 32, 6, 6);
        this.g2.setColor(Color.white);
        this.g2.fillRect(b1 - 62, this._$2019 - 1, 110, showsoldierRangeToDefence(this.para.MinNum) - showsoldierRangeToDefence(this.para.MaxNum));
        this.g2.setColor(Color.red);
        this.g2.drawRect(b1 - 61, this._$2019, 107, showsoldierRangeToDefence(this.para.MinNum) - showsoldierRangeToDefence(this.para.MaxNum) - 3);
        int i = b1 - 58;
        int j = b1 + 2;
        byte b2 = 11;
        int k = this._$2019 + 13;
        this.g2.setColor(Color.black);
        this.g2.drawString("列車編組", i, k);
        this.g2.drawString(vector.get(17).toString(), j, k);
        this.g2.setColor(new Color(233, 233, 233));
        this.g2.drawLine(b1 - 60, k + 1, b1 + 45, k + 1);
        this.g2.setColor(Color.black);
        this.g2.drawString("車長", i, k + b2);
        this.g2.drawString(vector.get(9).toString() + " m", j, k + b2);
        this.g2.setColor(new Color(233, 233, 233));
        this.g2.drawLine(b1 - 60, k + 1 + b2, b1 + 45, k + 1 + b2);
        this.g2.setColor(Color.black);
        this.g2.drawString("最大車速", i, k + b2 * 2);
        this.g2.drawString(((new Integer(vector.get(10).toString())).intValue() * 36 / 1000) + " km/h", j, k + b2 * 2);
        this.g2.setColor(new Color(233, 233, 233));
        this.g2.drawLine(b1 - 60, k + 1 + b2 * 2, b1 + 45, k + 1 + b2 * 2);
        this.g2.setColor(Color.black);
        this.g2.drawString("　5~9", i, k + b2 * 3);
        this.g2.drawString(((new Integer(vector.get(18).toString())).intValue() * 36 / 1000) + " km/h", j, k + b2 * 3);
        this.g2.setColor(new Color(233, 233, 233));
        this.g2.drawLine(b1 - 60, k + 1 + b2 * 3, b1 + 45, k + 1 + b2 * 3);
        this.g2.setColor(Color.black);
        this.g2.drawString("　10~14", i, k + b2 * 4);
        this.g2.drawString(((new Integer(vector.get(19).toString())).intValue() * 36 / 1000) + " km/h", j, k + b2 * 4);
        this.g2.setColor(new Color(233, 233, 233));
        this.g2.drawLine(b1 - 60, k + 1 + b2 * 4, b1 + 45, k + 1 + b2 * 4);
        this.g2.setColor(Color.black);
        this.g2.drawString("　15~19", i, k + b2 * 5);
        this.g2.drawString(((new Integer(vector.get(20).toString())).intValue() * 36 / 1000) + " km/h", j, k + b2 * 5);
        this.g2.setColor(new Color(233, 233, 233));
        this.g2.drawLine(b1 - 60, k + 1 + b2 * 5, b1 + 45, k + 1 + b2 * 5);
        this.g2.setColor(Color.black);
        this.g2.drawString("　>=20", i, k + b2 * 6);
        this.g2.drawString(((new Integer(vector.get(21).toString())).intValue() * 36 / 1000) + " km/h", j, k + b2 * 6);
        this.g2.setColor(new Color(233, 233, 233));
        this.g2.drawLine(b1 - 60, k + 1 + b2 * 6, b1 + 45, k + 1 + b2 * 6);
        this.g2.setColor(Color.black);
        this.g2.drawString("最大加速度", i, k + b2 * 7);
        this.g2.drawString(new Float((((Integer)vector.get(15)).intValue() / 100)) + " m/s^2", j, k + b2 * 7);
        this.g2.setColor(new Color(233, 233, 233));
        this.g2.drawLine(b1 - 60, k + 1 + b2 * 7, b1 + 45, k + 1 + b2 * 7);
        this.g2.setColor(Color.black);
        this.g2.drawString("最大減速度", i, k + b2 * 8);
        this.g2.drawString(new Float((((Integer)vector.get(16)).intValue() / 100)) + " m/s^2", j, k + b2 * 8);
        this.g2.setColor(new Color(233, 233, 233));
        this.g2.drawLine(b1 - 60, k + 1 + b2 * 8, b1 + 45, k + 1 + b2 * 8);
        this.g2.setColor(Color.black);
        this.g2.drawString("T_EB", i, k + b2 * 9);
        this.g2.drawString(new Float((((Integer)vector.get(13)).intValue() / 1000)) + " s", j, k + b2 * 9);
        this.g2.setColor(new Color(233, 233, 233));
        this.g2.drawLine(b1 - 60, k + 1 + b2 * 9, b1 + 45, k + 1 + b2 * 9);
        this.g2.setColor(Color.black);
        this.g2.drawString("T_SB", i, k + b2 * 10);
        this.g2.drawString(new Float((((Integer)vector.get(12)).intValue() / 1000)) + " s", j, k + b2 * 10);
        this.g2.setColor(new Color(233, 233, 233));
        this.g2.drawLine(b1 - 60, k + 1 + b2 * 10, b1 + 45, k + 1 + b2 * 10);
        this.g2.setColor(Color.black);
        this.g2.drawString("T_CUT", i, k + b2 * 11);
        this.g2.drawString(new Float((((Integer)vector.get(14)).intValue() / 1000)) + " s", j, k + b2 * 11);
        this.g2.setColor(new Color(233, 233, 233));
        this.g2.drawLine(b1 - 60, k + 1 + b2 * 11, b1 + 45, k + 1 + b2 * 11);
        this.g2.setColor(Color.black);
      } 
    } 
    this.g2.setStroke(stroke);
  }
}
