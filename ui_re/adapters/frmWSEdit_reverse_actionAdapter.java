package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmWSEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmWSEdit_reverse_actionAdapter implements ActionListener {
  frmWSEdit adaptee;
  
  frmWSEdit_reverse_actionAdapter(frmWSEdit paramfrmWSEdit) {
    this.adaptee = paramfrmWSEdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.reverse_actionPerformed(paramActionEvent);
  }
}
