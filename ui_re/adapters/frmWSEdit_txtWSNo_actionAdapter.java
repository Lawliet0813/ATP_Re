package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmWSEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmWSEdit_txtWSNo_actionAdapter implements ActionListener {
  frmWSEdit adaptee;
  
  frmWSEdit_txtWSNo_actionAdapter(frmWSEdit paramfrmWSEdit) {
    this.adaptee = paramfrmWSEdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtWSNo_actionPerformed(paramActionEvent);
  }
}
