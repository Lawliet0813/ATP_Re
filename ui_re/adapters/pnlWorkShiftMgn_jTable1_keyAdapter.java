package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlWorkShiftMgn;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlWorkShiftMgn_jTable1_keyAdapter extends KeyAdapter {
  pnlWorkShiftMgn adaptee;
  
  pnlWorkShiftMgn_jTable1_keyAdapter(pnlWorkShiftMgn parampnlWorkShiftMgn) {
    this.adaptee = parampnlWorkShiftMgn;
  }
  
  public void keyReleased(KeyEvent paramKeyEvent) {
    this.adaptee.jTable1_keyReleased(paramKeyEvent);
  }
}
