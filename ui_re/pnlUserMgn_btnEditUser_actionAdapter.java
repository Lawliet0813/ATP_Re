package ui;

import com.MiTAC.TRA.ATP.ui.pnlUserMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlUserMgn_btnEditUser_actionAdapter implements ActionListener {
  pnlUserMgn adaptee;
  
  pnlUserMgn_btnEditUser_actionAdapter(pnlUserMgn parampnlUserMgn) {
    this.adaptee = parampnlUserMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnEditUser_actionPerformed(paramActionEvent);
  }
}
