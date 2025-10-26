package ui;

import com.MiTAC.TRA.ATP.ui.frmDriverEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmDriverEdit_txtMphone_actionAdapter implements ActionListener {
  frmDriverEdit adaptee;
  
  frmDriverEdit_txtMphone_actionAdapter(frmDriverEdit paramfrmDriverEdit) {
    this.adaptee = paramfrmDriverEdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtMphone_actionPerformed(paramActionEvent);
  }
}
