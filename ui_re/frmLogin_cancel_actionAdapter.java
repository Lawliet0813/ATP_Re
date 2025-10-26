package ui;

import com.MiTAC.TRA.ATP.ui.frmLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmLogin_cancel_actionAdapter implements ActionListener {
  frmLogin adaptee;
  
  frmLogin_cancel_actionAdapter(frmLogin paramfrmLogin) {
    this.adaptee = paramfrmLogin;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.cancel_actionPerformed(paramActionEvent);
  }
}
