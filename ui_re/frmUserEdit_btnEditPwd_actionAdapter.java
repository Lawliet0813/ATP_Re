package ui;

import com.MiTAC.TRA.ATP.ui.frmUserEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmUserEdit_btnEditPwd_actionAdapter implements ActionListener {
  frmUserEdit adaptee;
  
  frmUserEdit_btnEditPwd_actionAdapter(frmUserEdit paramfrmUserEdit) {
    this.adaptee = paramfrmUserEdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnEditPwd_actionPerformed(paramActionEvent);
  }
}
