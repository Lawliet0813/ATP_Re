package ui;

import com.MiTAC.TRA.ATP.ui.frmLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmLogin_comit_actionAdapter implements ActionListener {
  frmLogin adaptee;
  
  frmLogin_comit_actionAdapter(frmLogin paramfrmLogin) {
    this.adaptee = paramfrmLogin;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.comit_actionPerformed(paramActionEvent);
  }
}
