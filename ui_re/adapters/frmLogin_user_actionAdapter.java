package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmLogin_user_actionAdapter implements ActionListener {
  frmLogin adaptee;
  
  frmLogin_user_actionAdapter(frmLogin paramfrmLogin) {
    this.adaptee = paramfrmLogin;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.user_actionPerformed(paramActionEvent);
  }
}
