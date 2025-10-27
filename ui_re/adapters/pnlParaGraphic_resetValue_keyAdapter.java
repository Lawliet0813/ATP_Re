package ui.adapters;

import com.MiTAC.TRA.ATP.ui.pnlParaGraphic;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlParaGraphic_resetValue_keyAdapter extends KeyAdapter {
  pnlParaGraphic adaptee;
  
  pnlParaGraphic_resetValue_keyAdapter(pnlParaGraphic parampnlParaGraphic) {
    this.adaptee = parampnlParaGraphic;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtNewValue_keyPressed(paramKeyEvent);
  }
}
