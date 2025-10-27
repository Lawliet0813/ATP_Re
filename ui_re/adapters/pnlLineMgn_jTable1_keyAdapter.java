package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlLineMgn;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlLineMgn_jTable1_keyAdapter extends KeyAdapter {
  pnlLineMgn adaptee;
  
  pnlLineMgn_jTable1_keyAdapter(pnlLineMgn parampnlLineMgn) {
    this.adaptee = parampnlLineMgn;
  }
  
  public void keyReleased(KeyEvent paramKeyEvent) {
    this.adaptee.jTable1_keyReleased(paramKeyEvent);
  }
}
