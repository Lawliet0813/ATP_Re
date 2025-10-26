package ui;

import com.MiTAC.TRA.ATP.ui.pnlTrainDataMgn;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlTrainDataMgn_jTable1_keyAdapter extends KeyAdapter {
  pnlTrainDataMgn adaptee;
  
  pnlTrainDataMgn_jTable1_keyAdapter(pnlTrainDataMgn parampnlTrainDataMgn) {
    this.adaptee = parampnlTrainDataMgn;
  }
  
  public void keyReleased(KeyEvent paramKeyEvent) {
    this.adaptee.jTable1_keyReleased(paramKeyEvent);
  }
}
