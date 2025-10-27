package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmDriverEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmDriverEdit_confirm_actionAdapter implements ActionListener {
  frmDriverEdit adaptee;
  
  frmDriverEdit_confirm_actionAdapter(frmDriverEdit paramfrmDriverEdit) {
    this.adaptee = paramfrmDriverEdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.confirm_actionPerformed(paramActionEvent);
  }
}
