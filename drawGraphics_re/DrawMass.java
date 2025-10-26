package drawGraphics;

import com.MiTAC.TRA.ATP.drawGraphics.DrawBase;
import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.drawGraphics.drawATP;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.util.Date;
import java.util.Vector;

public class DrawMass extends DrawBase implements drawATP {
  private int[] _$18244;
  
  private Color _$18245;
  
  private Date[] _$18242;
  
  private int[] _$18243;
  
  private Vector _$18240;
  
  private Vector _$18241;
  
  private boolean _$18218 = true;
  
  private Stroke _$18036 = new BasicStroke();
  
  private int[] _$1632;
  
  private int[] _$2019;
  
  public DrawMass(commonParaSetting paramcommonParaSetting, drawParameters paramdrawParameters, Vector paramVector) {
    super(paramcommonParaSetting, paramdrawParameters, paramVector);
  }
  
  public void paintBody(Graphics paramGraphics) throws Exception {
    super.paintBody(paramGraphics);
    if (this._$18218) {
      _$18223();
      drawScanner();
    } 
  }
  
  public void paintHeader(Graphics paramGraphics) throws Exception {
    super.paintHeader(paramGraphics);
  }
  
  public void resetScale() {
    this._$18242 = new Date[this._$18241.size()];
    this._$18244 = new int[this._$18241.size()];
    this._$18243 = new int[this._$18241.size()];
    this._$1632 = new int[this._$18240.size()];
    this._$2019 = new int[this._$18240.size()];
    for (byte b = 0; b < this._$18241.size(); b++) {
      Vector vector = this._$18241.get(b);
      this._$18242[b] = vector.get(0);
      this._$18244[b] = ((Integer)vector.get(1)).intValue();
      Vector vector1 = this._$18240.get(b);
      this._$18243[b] = ((Integer)vector1.get(1)).intValue();
      this._$1632[b] = showsoldierWhereToStand(this._$18242[b].getTime());
      int i = this._$18244[b] - this._$18243[b];
      this._$2019[b] = showsoldierRangeToDefence(i);
    } 
  }
  
  public void setData(Vector paramVector1, Vector paramVector2) {
    this._$18240 = paramVector1;
    this._$18241 = paramVector2;
    resetScale();
  }
  
  public void setData(Vector paramVector) {}
  
  public void setDataLineColor(Color paramColor) {
    this._$18245 = paramColor;
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
    if (isFollowMode()) {
      _$18225();
    } else {
      _$18226();
    } 
  }
  
  private void _$18225() {
    int i = -1;
    int j = 0;
    Stroke stroke = this.g2.getStroke();
    this.g2.setStroke(this._$18036);
    this.g2.setColor(this._$18245);
    GeneralPath generalPath = new GeneralPath();
    for (byte b = 0; b < this._$18242.length && this._$1632[b] <= DrawBase.mouseX + 600; b++) {
      if (this._$1632[b] >= DrawBase.mouseX - 600 && (i != -1 || this._$18244[b] != -1))
        if (i == -1 && this._$18244[b] != -1) {
          i = this._$18244[b];
          j = this._$2019[b];
          generalPath.moveTo(this._$1632[b], this._$2019[b]);
        } else if (i != -1 && this._$18244[b] == -1) {
          generalPath.lineTo(this._$1632[b], j);
          i = -1;
          this.g2.draw(generalPath);
          generalPath.reset();
        } else {
          generalPath.lineTo(this._$1632[b], this._$2019[b]);
          i = this._$18244[b];
          j = this._$2019[b];
        }  
    } 
    this.g2.draw(generalPath);
    generalPath.reset();
    this.g2.setStroke(stroke);
  }
  
  private void _$18226() {
    int i = -1;
    int j = 0;
    Stroke stroke = this.g2.getStroke();
    this.g2.setStroke(this._$18036);
    this.g2.setColor(this._$18245);
    GeneralPath generalPath = new GeneralPath();
    for (byte b = 0; b < this._$18242.length; b++) {
      if (i != -1 || this._$18244[b] != -1)
        if (i == -1 && this._$18244[b] != -1) {
          i = this._$18244[b];
          j = this._$2019[b];
          generalPath.moveTo(this._$1632[b], this._$2019[b]);
        } else if (i != -1 && this._$18244[b] == -1) {
          generalPath.lineTo(this._$1632[b], j);
          i = -1;
          this.g2.draw(generalPath);
          generalPath.reset();
        } else {
          generalPath.lineTo(this._$1632[b], this._$2019[b]);
          i = this._$18244[b];
          j = this._$2019[b];
        }  
    } 
    this.g2.draw(generalPath);
    generalPath.reset();
    this.g2.setStroke(stroke);
  }
}
