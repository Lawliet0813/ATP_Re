package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmUserEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmUserEdit_txtUserName_actionAdapter implements ActionListener {
  frmUserEdit adaptee;
  
  frmUserEdit_txtUserName_actionAdapter(frmUserEdit paramfrmUserEdit) {
    this.adaptee = paramfrmUserEdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtUserName_actionPerformed(paramActionEvent);
  }
}
