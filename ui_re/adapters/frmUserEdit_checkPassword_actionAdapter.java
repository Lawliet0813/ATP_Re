package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmUserEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmUserEdit_checkPassword_actionAdapter implements ActionListener {
  frmUserEdit adaptee;
  
  frmUserEdit_checkPassword_actionAdapter(frmUserEdit paramfrmUserEdit) {
    this.adaptee = paramfrmUserEdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.checkPassword_actionPerformed(paramActionEvent);
  }
}
