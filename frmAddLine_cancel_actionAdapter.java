package ui;

import com.MiTAC.TRA.ATP.ui.frmAddLine;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmAddLine_cancel_actionAdapter implements ActionListener {
  frmAddLine adaptee;
  
  frmAddLine_cancel_actionAdapter(frmAddLine paramfrmAddLine) {
    this.adaptee = paramfrmAddLine;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.cancel_actionPerformed(paramActionEvent);
  }
}
