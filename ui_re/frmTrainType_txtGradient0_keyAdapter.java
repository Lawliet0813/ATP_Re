package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainType;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class frmTrainType_txtGradient0_keyAdapter extends KeyAdapter {
  frmTrainType adaptee;
  
  frmTrainType_txtGradient0_keyAdapter(frmTrainType paramfrmTrainType) {
    this.adaptee = paramfrmTrainType;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtGradient0_keyPressed(paramKeyEvent);
  }
}
