package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlLineMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlLineMgn_btnAddStation_actionAdapter implements ActionListener {
  pnlLineMgn adaptee;
  
  pnlLineMgn_btnAddStation_actionAdapter(pnlLineMgn parampnlLineMgn) {
    this.adaptee = parampnlLineMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnAddStation_actionPerformed(paramActionEvent);
  }
}
