package ui;

import com.MiTAC.TRA.ATP.ui.frmAddLine;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmAddLine_commit_actionAdapter implements ActionListener {
  frmAddLine adaptee;
  
  frmAddLine_commit_actionAdapter(frmAddLine paramfrmAddLine) {
    this.adaptee = paramfrmAddLine;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.commit_actionPerformed(paramActionEvent);
  }
}
