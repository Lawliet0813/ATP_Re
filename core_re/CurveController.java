package core;

import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.core.ATPMissionDetail;
import com.MiTAC.TRA.ATP.drawGraphics.DrawContinuous;
import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.drawGraphics.drawATP;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import java.awt.Graphics;
import java.util.Vector;

public class CurveController {
  private boolean _$29760 = true;
  
  private boolean _$29762 = true;
  
  private boolean _$29764 = true;
  
  private boolean _$29766 = true;
  
  private boolean _$29768 = true;
  
  private drawATP[] _$29761 = new drawATP[5];
  
  private drawATP[] _$29763 = new drawATP[5];
  
  private drawATP[] _$29765 = new drawATP[5];
  
  private drawATP[] _$29767 = new drawATP[5];
  
  private drawATP[] _$29769 = new drawATP[5];
  
  private ATPMissionDetail _$29770;
  
  private InitParameters _$2498;
  
  public void CurverController(ATPMissionDetail paramATPMissionDetail, InitParameters paramInitParameters) {
    this._$29770 = paramATPMissionDetail;
    this._$2498 = paramInitParameters;
    commonParaSetting commonParaSetting = new commonParaSetting();
    drawParameters drawParameters = new drawParameters();
    DrawContinuous drawContinuous = new DrawContinuous(commonParaSetting, drawParameters, new Vector());
    this._$29761[0] = (drawATP)drawContinuous;
  }
  
  public void graphicDialog() {}
  
  public void paint(Graphics paramGraphics) {}
  
  public void search() {}
}
