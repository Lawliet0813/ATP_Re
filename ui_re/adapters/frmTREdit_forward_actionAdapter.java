package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmTREdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTREdit_forward_actionAdapter implements ActionListener {
  frmTREdit adaptee;
  
  frmTREdit_forward_actionAdapter(frmTREdit paramfrmTREdit) {
    this.adaptee = paramfrmTREdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.forward_actionPerformed(paramActionEvent);
  }
}
