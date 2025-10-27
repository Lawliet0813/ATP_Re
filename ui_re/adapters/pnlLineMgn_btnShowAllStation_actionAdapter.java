package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlLineMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlLineMgn_btnShowAllStation_actionAdapter implements ActionListener {
  pnlLineMgn adaptee;
  
  pnlLineMgn_btnShowAllStation_actionAdapter(pnlLineMgn parampnlLineMgn) {
    this.adaptee = parampnlLineMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnShowAllStation_actionPerformed(paramActionEvent);
  }
}
