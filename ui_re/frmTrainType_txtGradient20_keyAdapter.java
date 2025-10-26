package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainType;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class frmTrainType_txtGradient20_keyAdapter extends KeyAdapter {
  frmTrainType adaptee;
  
  frmTrainType_txtGradient20_keyAdapter(frmTrainType paramfrmTrainType) {
    this.adaptee = paramfrmTrainType;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtGradient20_keyPressed(paramKeyEvent);
  }
}
