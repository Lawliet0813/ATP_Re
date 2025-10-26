package ui;

import com.MiTAC.TRA.ATP.ui.frmBackup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmBackup_btnOpenFile_actionAdapter implements ActionListener {
  frmBackup adaptee;
  
  frmBackup_btnOpenFile_actionAdapter(frmBackup paramfrmBackup) {
    this.adaptee = paramfrmBackup;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnOpenFile_actionPerformed(paramActionEvent);
  }
}
