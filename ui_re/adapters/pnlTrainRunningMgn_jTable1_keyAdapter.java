package ui.adapters;

import com.MiTAC.TRA.ATP.ui.pnlTrainRunningMgn;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlTrainRunningMgn_jTable1_keyAdapter extends KeyAdapter {
  pnlTrainRunningMgn adaptee;
  
  pnlTrainRunningMgn_jTable1_keyAdapter(pnlTrainRunningMgn parampnlTrainRunningMgn) {
    this.adaptee = parampnlTrainRunningMgn;
  }
  
  public void keyReleased(KeyEvent paramKeyEvent) {
    this.adaptee.jTable1_keyReleased(paramKeyEvent);
  }
}
