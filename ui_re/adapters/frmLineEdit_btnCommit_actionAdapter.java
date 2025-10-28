package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmLineEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmLineEdit_btnCommit_actionAdapter implements ActionListener {
  frmLineEdit adaptee;
  
  frmLineEdit_btnCommit_actionAdapter(frmLineEdit paramfrmLineEdit) {
    this.adaptee = paramfrmLineEdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnCommit_actionPerformed(paramActionEvent);
  }
}
