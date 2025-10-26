package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainData;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class frmTrainData_txtEB_delay_time_keyAdapter extends KeyAdapter {
  frmTrainData adaptee;
  
  frmTrainData_txtEB_delay_time_keyAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtEB_delay_time_keyPressed(paramKeyEvent);
  }
}
