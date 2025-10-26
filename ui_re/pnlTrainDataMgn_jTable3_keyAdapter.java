package ui;

import com.MiTAC.TRA.ATP.ui.pnlTrainDataMgn;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlTrainDataMgn_jTable3_keyAdapter extends KeyAdapter {
  pnlTrainDataMgn adaptee;
  
  pnlTrainDataMgn_jTable3_keyAdapter(pnlTrainDataMgn parampnlTrainDataMgn) {
    this.adaptee = parampnlTrainDataMgn;
  }
  
  public void keyReleased(KeyEvent paramKeyEvent) {
    this.adaptee.jTable3_keyReleased(paramKeyEvent);
  }
}
