package ui;

import com.MiTAC.TRA.ATP.ui.pnlLineMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlLineMgn_btnDelStation_actionAdapter implements ActionListener {
  pnlLineMgn adaptee;
  
  pnlLineMgn_btnDelStation_actionAdapter(pnlLineMgn parampnlLineMgn) {
    this.adaptee = parampnlLineMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnDelStation_actionPerformed(paramActionEvent);
  }
}
