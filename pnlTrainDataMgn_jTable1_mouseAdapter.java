package ui;

import com.MiTAC.TRA.ATP.ui.pnlTrainDataMgn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class pnlTrainDataMgn_jTable1_mouseAdapter extends MouseAdapter {
  pnlTrainDataMgn adaptee;
  
  pnlTrainDataMgn_jTable1_mouseAdapter(pnlTrainDataMgn parampnlTrainDataMgn) {
    this.adaptee = parampnlTrainDataMgn;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    this.adaptee.jTable1_mouseClicked(paramMouseEvent);
  }
}
