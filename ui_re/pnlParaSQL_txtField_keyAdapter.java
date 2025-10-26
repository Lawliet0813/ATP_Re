package ui;

import com.MiTAC.TRA.ATP.ui.pnlParaSQL;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlParaSQL_txtField_keyAdapter extends KeyAdapter {
  pnlParaSQL adaptee;
  
  pnlParaSQL_txtField_keyAdapter(pnlParaSQL parampnlParaSQL) {
    this.adaptee = parampnlParaSQL;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtField_keyPressed(paramKeyEvent);
  }
}
