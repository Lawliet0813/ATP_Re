package ui;

import com.MiTAC.TRA.ATP.ui.frmWSEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmWSEdit_confirm_actionAdapter implements ActionListener {
  frmWSEdit adaptee;
  
  frmWSEdit_confirm_actionAdapter(frmWSEdit paramfrmWSEdit) {
    this.adaptee = paramfrmWSEdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.confirm_actionPerformed(paramActionEvent);
  }
}
