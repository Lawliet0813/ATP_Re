package drawGraphics;

import com.MiTAC.TRA.ATP.ATPMessages;
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
import java.util.Date;
import java.util.Vector;

public class DrawRightAngle extends DrawBase implements drawATP {
  private Color _$18217;
  
  private Date[] _$18215;
  
  private int[] _$18216;
  
  private boolean _$18218 = true;
  
  private Vector _$18214;
  
  private Stroke _$18036 = new BasicStroke();
  
  private int[] _$1632;
  
  private int[] _$2019;
  
  public DrawRightAngle(commonParaSetting paramcommonParaSetting, drawParameters paramdrawParameters, Vector paramVector) {
    super(paramcommonParaSetting, paramdrawParameters, paramVector);
  }
  
  public Object getMousePointStatus() throws Exception {
    try {
      int i = Arrays.binarySearch((Object[])this._$18215, new Date(soloderReport()));
      if (i < 0)
        try {
          return (Math.abs(i) == 1 && this._$18216[0] != 0) ? ATPMessages.getString("MW.BEHAVIOR.LOG.STATUS.NOT_ACTIVE") : new Integer(this._$18216[Math.abs(i) - 2]);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
          return new Integer(this._$18216[this._$18216.length - 1]);
        } catch (Exception exception) {
          return ATPMessages.getString("MW.GNL.ERROR");
        }  
      return new Integer(this._$18216[i]);
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      return ATPMessages.getString("MW.BEHAVIOR.LOG.STATUS.NO_DATA");
    } 
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
    this._$18215 = new Date[this._$18214.size()];
    this._$18216 = new int[this._$18214.size()];
    this._$1632 = new int[this._$18214.size()];
    this._$2019 = new int[this._$18214.size()];
    for (byte b = 0; b < this._$18214.size(); b++) {
      Vector vector = this._$18214.get(b);
      this._$18215[b] = vector.get(0);
      this._$18216[b] = ((Integer)vector.get(1)).intValue();
      this._$1632[b] = showsoldierWhereToStand(this._$18215[b].getTime());
      this._$2019[b] = showsoldierRangeToDefence(this._$18216[b]);
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
    GeneralPath generalPath = new GeneralPath();
    if (this._$18216.length == 0) {
      drawBasicMessage();
    } else if (isFollowMode()) {
      _$18225(generalPath);
    } else {
      _$18226(generalPath);
    } 
    this.g2.setStroke(stroke);
  }
  
  private void _$18225(GeneralPath paramGeneralPath) {
    int i = this._$2019[0];
    this.g2.setColor(this._$18217);
    paramGeneralPath.moveTo(this._$1632[0], this._$2019[0]);
    for (byte b = 1; b < this._$18215.length && this._$1632[b] <= DrawBase.mouseX + 600; b++) {
      if (this._$1632[b] >= DrawBase.mouseX - 600)
        if (this._$2019[b] != i) {
          paramGeneralPath.lineTo(this._$1632[b], i);
          paramGeneralPath.lineTo(this._$1632[b], this._$2019[b]);
          i = this._$2019[b];
        } else {
          paramGeneralPath.lineTo(this._$1632[b], i);
        }  
    } 
    this.g2.draw(paramGeneralPath);
    paramGeneralPath.reset();
    this.g2.setColor(this._$18217.darker());
    paramGeneralPath.moveTo(this._$1632[this._$1632.length - 1], i);
    paramGeneralPath.lineTo(showsoldierWhereToStand(DrawBase.endTime), i);
    this.g2.draw(paramGeneralPath);
  }
  
  private void _$18226(GeneralPath paramGeneralPath) {
    int i = this._$2019[0];
    this.g2.setColor(this._$18217);
    paramGeneralPath.moveTo(this._$1632[0], this._$2019[0]);
    for (byte b = 1; b < this._$18215.length; b++) {
      if (this._$2019[b] != i) {
        paramGeneralPath.lineTo(this._$1632[b], i);
        paramGeneralPath.lineTo(this._$1632[b], this._$2019[b]);
        i = this._$2019[b];
      } else {
        paramGeneralPath.lineTo(this._$1632[b], i);
      } 
    } 
    this.g2.draw(paramGeneralPath);
    paramGeneralPath.reset();
    this.g2.setColor(this._$18217.darker());
    paramGeneralPath.moveTo(this._$1632[this._$1632.length - 1], i);
    paramGeneralPath.lineTo(showsoldierWhereToStand(DrawBase.endTime), i);
    this.g2.draw(paramGeneralPath);
  }
}
