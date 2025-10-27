package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmLogSearch;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmLogSearch_btnConfirm_actionAdapter implements ActionListener {
  frmLogSearch adaptee;
  
  frmLogSearch_btnConfirm_actionAdapter(frmLogSearch paramfrmLogSearch) {
    this.adaptee = paramfrmLogSearch;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnConfirm_actionPerformed(paramActionEvent);
  }
}
