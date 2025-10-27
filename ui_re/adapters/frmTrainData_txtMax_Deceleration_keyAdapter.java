package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmTrainData;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class frmTrainData_txtMax_Deceleration_keyAdapter extends KeyAdapter {
  frmTrainData adaptee;
  
  frmTrainData_txtMax_Deceleration_keyAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtMax_Deceleration_keyPressed(paramKeyEvent);
  }
}
