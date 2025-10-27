package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmAddStation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmAddStation_commit_actionAdapter implements ActionListener {
  frmAddStation adaptee;
  
  frmAddStation_commit_actionAdapter(frmAddStation paramfrmAddStation) {
    this.adaptee = paramfrmAddStation;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.commit_actionPerformed(paramActionEvent);
  }
}
