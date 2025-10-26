package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainData;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class frmTrainData_txtSB_delay_time_keyAdapter extends KeyAdapter {
  frmTrainData adaptee;
  
  frmTrainData_txtSB_delay_time_keyAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtSB_delay_time_keyPressed(paramKeyEvent);
  }
}
