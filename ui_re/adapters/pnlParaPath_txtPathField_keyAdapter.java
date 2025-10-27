package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlParaPath;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlParaPath_txtPathField_keyAdapter extends KeyAdapter {
  pnlParaPath adaptee;
  
  pnlParaPath_txtPathField_keyAdapter(pnlParaPath parampnlParaPath) {
    this.adaptee = parampnlParaPath;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtPathField_keyPressed(paramKeyEvent);
  }
}
