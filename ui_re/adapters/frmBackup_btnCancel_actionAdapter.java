package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmBackup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmBackup_btnCancel_actionAdapter implements ActionListener {
  frmBackup adaptee;
  
  frmBackup_btnCancel_actionAdapter(frmBackup paramfrmBackup) {
    this.adaptee = paramfrmBackup;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnCancel_actionPerformed(paramActionEvent);
  }
}
