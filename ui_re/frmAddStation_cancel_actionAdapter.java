package ui;

import com.MiTAC.TRA.ATP.ui.frmAddStation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmAddStation_cancel_actionAdapter implements ActionListener {
  frmAddStation adaptee;
  
  frmAddStation_cancel_actionAdapter(frmAddStation paramfrmAddStation) {
    this.adaptee = paramfrmAddStation;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.cancel_actionPerformed(paramActionEvent);
  }
}
