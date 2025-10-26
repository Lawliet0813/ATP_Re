package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainData;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class frmTrainData_txtSB_deceleration_keyAdapter extends KeyAdapter {
  frmTrainData adaptee;
  
  frmTrainData_txtSB_deceleration_keyAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtSB_deceleration_keyPressed(paramKeyEvent);
  }
}
