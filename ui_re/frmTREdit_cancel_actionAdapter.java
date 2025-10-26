package ui;

import com.MiTAC.TRA.ATP.ui.frmTREdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTREdit_cancel_actionAdapter implements ActionListener {
  frmTREdit adaptee;
  
  frmTREdit_cancel_actionAdapter(frmTREdit paramfrmTREdit) {
    this.adaptee = paramfrmTREdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.cancel_actionPerformed(paramActionEvent);
  }
}
