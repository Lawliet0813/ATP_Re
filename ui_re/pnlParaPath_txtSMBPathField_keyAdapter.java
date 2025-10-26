package ui;

import com.MiTAC.TRA.ATP.ui.pnlParaPath;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlParaPath_txtSMBPathField_keyAdapter extends KeyAdapter {
  pnlParaPath adaptee;
  
  pnlParaPath_txtSMBPathField_keyAdapter(pnlParaPath parampnlParaPath) {
    this.adaptee = parampnlParaPath;
  }
  
  public void keyTyped(KeyEvent paramKeyEvent) {
    this.adaptee.txtSMBPathField_keyTyped(paramKeyEvent);
  }
}
