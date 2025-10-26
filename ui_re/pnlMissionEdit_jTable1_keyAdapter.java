package ui;

import com.MiTAC.TRA.ATP.ui.pnlMissionEdit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlMissionEdit_jTable1_keyAdapter extends KeyAdapter {
  pnlMissionEdit adaptee;
  
  pnlMissionEdit_jTable1_keyAdapter(pnlMissionEdit parampnlMissionEdit) {
    this.adaptee = parampnlMissionEdit;
  }
  
  public void keyReleased(KeyEvent paramKeyEvent) {
    this.adaptee.jTable1_keyReleased(paramKeyEvent);
  }
}
