package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmTrainData;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class frmTrainData_txtEB_deceleration_keyAdapter extends KeyAdapter {
  frmTrainData adaptee;
  
  frmTrainData_txtEB_deceleration_keyAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtEB_deceleration_keyPressed(paramKeyEvent);
  }
}
