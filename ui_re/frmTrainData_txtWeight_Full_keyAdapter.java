package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainData;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class frmTrainData_txtWeight_Full_keyAdapter extends KeyAdapter {
  frmTrainData adaptee;
  
  frmTrainData_txtWeight_Full_keyAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtWeight_Full_keyPressed(paramKeyEvent);
  }
}
