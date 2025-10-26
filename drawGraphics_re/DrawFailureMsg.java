package drawGraphics;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.drawGraphics.DrawBase;
import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.drawGraphics.drawATP;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.util.Date;
import java.util.Vector;

public class DrawFailureMsg extends DrawBase implements drawATP {
  public static final int CabinFailure = 1;
  
  private int _$26710 = 0;
  
  public static final int WaysideFailure = 0;
  
  private Color _$18217;
  
  private Date[] _$18215;
  
  private Object[][] _$18216;
  
  private boolean _$18218 = true;
  
  private Vector _$18214;
  
  private int[] _$1632;
  
  private int _$2019;
  
  public DrawFailureMsg(commonParaSetting paramcommonParaSetting, drawParameters paramdrawParameters, Vector paramVector, int paramInt) {
    super(paramcommonParaSetting, paramdrawParameters, paramVector);
    this._$26710 = paramInt;
  }
  
  private void _$26711() {
    this.g2.setColor(this._$18217);
    for (byte b = 0; b < this._$1632.length; b++) {
      this.g2.drawLine(this._$1632[b], this._$2019, this._$1632[b], this._$2019 - 50);
      this.g2.drawLine(this._$1632[b] - 5, this._$2019 - 5, this._$1632[b], this._$2019);
      this.g2.drawLine(this._$1632[b] + 5, this._$2019 - 5, this._$1632[b], this._$2019);
    } 
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
    if (this._$18214 != null && this._$18214.size() != 0) {
      this._$18215 = new Date[this._$18214.size()];
      this._$18216 = new Object[this._$18214.size()][3];
      this._$1632 = new int[this._$18214.size()];
      this._$2019 = showsoldierRangeToDefence(this.para.MaxNum);
      for (byte b = 0; b < this._$18214.size(); b++) {
        Vector vector = this._$18214.get(b);
        if (this._$26710 == 1) {
          this._$18215[b] = vector.get(4);
          this._$18216[b][0] = vector.get(5);
          this._$18216[b][1] = vector.get(6);
          this._$18216[b][2] = vector.get(7);
        } else {
          this._$18215[b] = vector.get(3);
          this._$18216[b][0] = vector.get(4);
          this._$18216[b][1] = vector.get(5);
          this._$18216[b][2] = vector.get(6);
        } 
        this._$1632[b] = showsoldierWhereToStand(this._$18215[b].getTime());
      } 
    } 
  }
  
  public void setData(Vector paramVector) throws Exception {
    this._$18214 = paramVector;
    resetScale();
  }
  
  public void setDataLineColor(Color paramColor) throws Exception {
    this._$18217 = paramColor;
  }
  
  public void setDrawCurve(boolean paramBoolean) {
    this._$18218 = paramBoolean;
  }
  
  public void setStroke(Stroke paramStroke) {}
  
  private void _$18223() {
    if (this._$18214 != null && this._$18214.size() != 0) {
      _$26711();
      _$26712();
    } 
  }
  
  private void _$26712() {
    this.g2.setColor(this._$18217);
    String str = "";
    if (this._$26710 == 1) {
      str = ATPMessages.getString("MW.BEHAVIOR.FAILURE.CABIN");
    } else {
      str = ATPMessages.getString("MW.BEHAVIOR.FAILURE.WAYSIDE");
    } 
    for (byte b = 0; b < this._$18216.length; b++) {
      this.g2.setColor(this._$18217);
      this.g2.fillRect(this._$1632[b], this._$2019 - 60, 11, 12);
      this.g2.fillRect(this._$1632[b] + 12, this._$2019 - 60, 100, 12);
      this.g2.fillRect(this._$1632[b], this._$2019 - 47, 112, 12);
      this.g2.setColor(Color.red);
      this.g2.drawString(str + " " + ATPMessages.getString("MW.BEHAVIOR.FAILURE.CLASS") + " " + this._$18216[b][0] + " " + ATPMessages.getString("MW.BEHAVIOR.FAILURE.NUMBER") + " " + this._$18216[b][1], this._$1632[b], this._$2019 - 50);
      this.g2.drawString("" + this._$18216[b][2] + "", this._$1632[b], this._$2019 - 37);
    } 
  }
}
