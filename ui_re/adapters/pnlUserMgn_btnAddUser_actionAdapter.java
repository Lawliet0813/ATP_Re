package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlUserMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlUserMgn_btnAddUser_actionAdapter implements ActionListener {
  pnlUserMgn adaptee;
  
  pnlUserMgn_btnAddUser_actionAdapter(pnlUserMgn parampnlUserMgn) {
    this.adaptee = parampnlUserMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnAddUser_actionPerformed(paramActionEvent);
  }
}
