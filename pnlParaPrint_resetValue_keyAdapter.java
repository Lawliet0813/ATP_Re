package ui;

import com.MiTAC.TRA.ATP.ui.pnlParaPrint;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlParaPrint_resetValue_keyAdapter extends KeyAdapter {
  pnlParaPrint adaptee;
  
  pnlParaPrint_resetValue_keyAdapter(pnlParaPrint parampnlParaPrint) {
    this.adaptee = parampnlParaPrint;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.txtNewValue_keyPressed(paramKeyEvent);
  }
}
