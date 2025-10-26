package ui;

import com.MiTAC.TRA.ATP.ui.pnlLineMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlLineMgn_btnEditLine_actionAdapter implements ActionListener {
  pnlLineMgn adaptee;
  
  pnlLineMgn_btnEditLine_actionAdapter(pnlLineMgn parampnlLineMgn) {
    this.adaptee = parampnlLineMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnEditLine_actionPerformed(paramActionEvent);
  }
}
