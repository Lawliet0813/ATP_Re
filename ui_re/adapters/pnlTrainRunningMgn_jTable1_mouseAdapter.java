package ui.adapters;

import com.MiTAC.TRA.ATP.ui.pnlTrainRunningMgn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class pnlTrainRunningMgn_jTable1_mouseAdapter extends MouseAdapter {
  pnlTrainRunningMgn adaptee;
  
  pnlTrainRunningMgn_jTable1_mouseAdapter(pnlTrainRunningMgn parampnlTrainRunningMgn) {
    this.adaptee = parampnlTrainRunningMgn;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    this.adaptee.jTable1_mouseClicked(paramMouseEvent);
  }
}
