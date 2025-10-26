package ui;

import com.MiTAC.TRA.ATP.ui.pnlLineMgn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class pnlLineMgn_jTable1_mouseAdapter extends MouseAdapter {
  pnlLineMgn adaptee;
  
  pnlLineMgn_jTable1_mouseAdapter(pnlLineMgn parampnlLineMgn) {
    this.adaptee = parampnlLineMgn;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    this.adaptee.jTable1_mouseClicked(paramMouseEvent);
  }
}
