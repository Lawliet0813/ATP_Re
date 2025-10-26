package ui;

import com.MiTAC.TRA.ATP.ui.frmTREdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTREdit_reverse_actionAdapter implements ActionListener {
  frmTREdit adaptee;
  
  frmTREdit_reverse_actionAdapter(frmTREdit paramfrmTREdit) {
    this.adaptee = paramfrmTREdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.reverse_actionPerformed(paramActionEvent);
  }
}
