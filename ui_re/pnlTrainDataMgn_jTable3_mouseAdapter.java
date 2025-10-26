package ui;

import com.MiTAC.TRA.ATP.ui.pnlTrainDataMgn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class pnlTrainDataMgn_jTable3_mouseAdapter extends MouseAdapter {
  pnlTrainDataMgn adaptee;
  
  pnlTrainDataMgn_jTable3_mouseAdapter(pnlTrainDataMgn parampnlTrainDataMgn) {
    this.adaptee = parampnlTrainDataMgn;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    this.adaptee.jTable3_mouseClicked(paramMouseEvent);
  }
}
