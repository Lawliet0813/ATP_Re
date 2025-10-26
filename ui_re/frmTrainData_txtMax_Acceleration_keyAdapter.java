package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainData;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class frmTrainData_txtMax_Acceleration_keyAdapter extends KeyAdapter {
  frmTrainData adaptee;
  
  frmTrainData_txtMax_Acceleration_keyAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtMax_Acceleration_keyPressed(paramKeyEvent);
  }
}
