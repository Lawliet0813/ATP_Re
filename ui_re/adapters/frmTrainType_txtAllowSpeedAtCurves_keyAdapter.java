package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frmTrainType;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class frmTrainType_txtAllowSpeedAtCurves_keyAdapter extends KeyAdapter {
  frmTrainType adaptee;
  
  frmTrainType_txtAllowSpeedAtCurves_keyAdapter(frmTrainType paramfrmTrainType) {
    this.adaptee = paramfrmTrainType;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtAllowSpeedAtCurves_keyPressed(paramKeyEvent);
  }
}
