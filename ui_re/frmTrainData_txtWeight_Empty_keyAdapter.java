package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainData;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class frmTrainData_txtWeight_Empty_keyAdapter extends KeyAdapter {
  frmTrainData adaptee;
  
  frmTrainData_txtWeight_Empty_keyAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtWeight_Empty_keyPressed(paramKeyEvent);
  }
}
