package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlLineMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlLineMgn_btnDelLine_actionAdapter implements ActionListener {
  pnlLineMgn adaptee;
  
  pnlLineMgn_btnDelLine_actionAdapter(pnlLineMgn parampnlLineMgn) {
    this.adaptee = parampnlLineMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnDelLine_actionPerformed(paramActionEvent);
  }
}
