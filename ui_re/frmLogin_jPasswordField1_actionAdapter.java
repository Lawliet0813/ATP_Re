package ui;

import com.MiTAC.TRA.ATP.ui.frmLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmLogin_jPasswordField1_actionAdapter implements ActionListener {
  frmLogin adaptee;
  
  frmLogin_jPasswordField1_actionAdapter(frmLogin paramfrmLogin) {
    this.adaptee = paramfrmLogin;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.jPasswordField1_actionPerformed(paramActionEvent);
  }
}
