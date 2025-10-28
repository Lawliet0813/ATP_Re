package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlParaFTP;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlParaFTP_txtField_keyAdapter extends KeyAdapter {
  pnlParaFTP adaptee;
  
  pnlParaFTP_txtField_keyAdapter(pnlParaFTP parampnlParaFTP) {
    this.adaptee = parampnlParaFTP;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtField_keyPressed(paramKeyEvent);
  }
}
