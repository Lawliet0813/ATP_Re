package ui;

import com.MiTAC.TRA.ATP.ui.frmDriverEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmDriverEdit_txtHphone_actionAdapter implements ActionListener {
  frmDriverEdit adaptee;
  
  frmDriverEdit_txtHphone_actionAdapter(frmDriverEdit paramfrmDriverEdit) {
    this.adaptee = paramfrmDriverEdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtHphone_actionPerformed(paramActionEvent);
  }
}
