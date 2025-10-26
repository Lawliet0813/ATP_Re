package ui;

import com.MiTAC.TRA.ATP.ui.frmError;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmError_btnConfirm_actionAdapter implements ActionListener {
  frmError adaptee;
  
  frmError_btnConfirm_actionAdapter(frmError paramfrmError) {
    this.adaptee = paramfrmError;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnConfirm_actionPerformed(paramActionEvent);
  }
}
