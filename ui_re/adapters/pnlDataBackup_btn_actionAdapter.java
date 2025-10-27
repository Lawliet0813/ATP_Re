package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlDataBackup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlDataBackup_btn_actionAdapter implements ActionListener {
  pnlDataBackup adaptee;
  
  pnlDataBackup_btn_actionAdapter(pnlDataBackup parampnlDataBackup) {
    this.adaptee = parampnlDataBackup;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    if (paramActionEvent.getActionCommand().equals(this.adaptee.btnRefresh.getActionCommand())) {
      this.adaptee.btnRefresh_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnBackup.getActionCommand())) {
      this.adaptee.btnBackup_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnDelBackup.getActionCommand())) {
      this.adaptee.btnDelBackup_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnDelete.getActionCommand())) {
      this.adaptee.btnDelete_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnFormat.getActionCommand())) {
      this.adaptee.btnFormat_actionPerformed(paramActionEvent);
    } 
  }
}
