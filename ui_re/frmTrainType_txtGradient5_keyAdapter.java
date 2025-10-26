package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainType;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class frmTrainType_txtGradient5_keyAdapter extends KeyAdapter {
  frmTrainType adaptee;
  
  frmTrainType_txtGradient5_keyAdapter(frmTrainType paramfrmTrainType) {
    this.adaptee = paramfrmTrainType;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtGradient5_keyPressed(paramKeyEvent);
  }
}
