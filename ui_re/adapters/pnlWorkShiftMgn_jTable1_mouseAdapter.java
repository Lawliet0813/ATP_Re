package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlWorkShiftMgn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class pnlWorkShiftMgn_jTable1_mouseAdapter extends MouseAdapter {
  pnlWorkShiftMgn adaptee;
  
  pnlWorkShiftMgn_jTable1_mouseAdapter(pnlWorkShiftMgn parampnlWorkShiftMgn) {
    this.adaptee = parampnlWorkShiftMgn;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    this.adaptee.jTable1_mouseClicked(paramMouseEvent);
  }
}
