package drawGraphics;

import com.MiTAC.TRA.ATP.ATPMessages;
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
import java.util.Date;
import java.util.Vector;

public class DrawContinuous extends DrawBase implements drawATP {
  private Color _$2892;
  
  private Date[] _$2888;
  
  private int[] _$2890;
  
  private boolean _$2893 = true;
  
  private Vector _$2887;
  
  private Stroke _$2894 = new BasicStroke();
  
  private int[] _$2889;
  
  private int[] _$2891;
  
  public DrawContinuous(commonParaSetting paramcommonParaSetting, drawParameters paramdrawParameters, Vector paramVector) {
    super(paramcommonParaSetting, paramdrawParameters, paramVector);
  }
  
  public Object getMousePointStatus() throws Exception {
    try {
      int i = Arrays.binarySearch((Object[])this._$2888, new Date(soloderReport()));
      if (i < 0)
        try {
          return (Math.abs(i) == 1 && this._$2890[0] != 0) ? ATPMessages.getString("MW.BEHAVIOR.LOG.STATUS.NOT_ACTIVE") : new Integer(this._$2890[Math.abs(i) - 2]);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
          return new Integer(this._$2890[this._$2890.length - 1]);
        } catch (Exception exception) {
          return ATPMessages.getString("MW.GNL.ERROR");
        }  
      return new Integer(this._$2890[i]);
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      return ATPMessages.getString("MW.BEHAVIOR.LOG.STATUS.NO_DATA");
    } 
  }
  
  public void paintBody(Graphics paramGraphics) throws Exception {
    super.paintBody(paramGraphics);
    if (this._$2893) {
      _$2921();
      drawScanner();
    } 
  }
  
  public void paintHeader(Graphics paramGraphics) throws Exception {
    super.paintHeader(paramGraphics);
  }
  
  public void resetScale() {
    this._$2888 = new Date[this._$2887.size()];
    this._$2890 = new int[this._$2887.size()];
    this._$2889 = new int[this._$2887.size()];
    this._$2891 = new int[this._$2887.size()];
    for (byte b = 0; b < this._$2887.size(); b++) {
      Vector vector = this._$2887.get(b);
      this._$2888[b] = vector.get(0);
      this._$2890[b] = ((Integer)vector.get(1)).intValue();
      this._$2889[b] = showsoldierWhereToStand(this._$2888[b].getTime());
      if (this._$2889[b] < this._$2889[(b - 1 < 0) ? 0 : (b - 1)])
        System.out.println("err"); 
      this._$2891[b] = showsoldierRangeToDefence(this._$2890[b]);
    } 
  }
  
  public void setData(ATPMissionDetail paramATPMissionDetail) {
    drawStopStation(paramATPMissionDetail.getStopStation());
  }
  
  public void setData(Vector paramVector) {
    this._$2887 = paramVector;
    resetScale();
  }
  
  public void setDataLineColor(Color paramColor) throws Exception {
    this._$2892 = paramColor;
  }
  
  public void setDrawCurve(boolean paramBoolean) {
    this._$2893 = paramBoolean;
  }
  
  public void setDrawType(int paramInt) {
    super.setDrawType(paramInt);
    if (paramInt != DrawBase.drawType)
      resetScale(); 
  }
  
  public void setStroke(Stroke paramStroke) {
    this._$2894 = paramStroke;
  }
  
  private void _$2921() throws Exception {
    GeneralPath generalPath = new GeneralPath();
    Stroke stroke = this.g2.getStroke();
    this.g2.setStroke(this._$2894);
    if (this._$2890.length == 0) {
      drawBasicMessage();
    } else {
      this.g2.setColor(this._$2892);
      if (isFollowMode()) {
        _$2928(generalPath);
      } else {
        _$2929(generalPath);
      } 
      this.g2.draw(generalPath);
      generalPath.reset();
      this.g2.setColor(this._$2892.darker());
      generalPath.moveTo(this._$2889[this._$2889.length - 1], this._$2891[this._$2891.length - 1]);
      generalPath.lineTo(showsoldierWhereToStand(DrawBase.endTime), this._$2891[this._$2891.length - 1]);
      this.g2.draw(generalPath);
    } 
    this.g2.setStroke(stroke);
  }
  
  private void _$2928(GeneralPath paramGeneralPath) {
    boolean bool = false;
    for (byte b = 1; b < this._$2889.length && this._$2889[b] <= DrawBase.mouseX + 600; b++) {
      if (this._$2889[b] >= DrawBase.mouseX - 600) {
        if (!bool) {
          paramGeneralPath.moveTo(this._$2889[b], this._$2891[b]);
          bool = !bool ? true : false;
        } 
        paramGeneralPath.lineTo(this._$2889[b], this._$2891[b]);
      } 
    } 
  }
  
  private void _$2929(GeneralPath paramGeneralPath) {
    paramGeneralPath.moveTo(this._$2889[0], this._$2891[0]);
    for (byte b = 1; b < this._$2889.length; b++)
      paramGeneralPath.lineTo(this._$2889[b], this._$2891[b]); 
  }
}
