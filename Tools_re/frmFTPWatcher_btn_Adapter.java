package Tools;

import com.MiTAC.TRA.ATP.Tools.frmFTPWatcher;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmFTPWatcher_btn_Adapter implements ActionListener {
  frmFTPWatcher adaptee;
  
  public frmFTPWatcher_btn_Adapter(frmFTPWatcher adaptee) {
    this.adaptee = adaptee;
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals(this.adaptee.btnCleanSuccess.getActionCommand())) {
      this.adaptee.btn_cleanSuccess_actionPerformed(e);
    } else if (e.getActionCommand().equals(this.adaptee.btnCloseFrame.getActionCommand())) {
      this.adaptee.btn_closeFrame_actionPerformed(e);
    } else if (e.getActionCommand().equals(this.adaptee.btnSaveErrorLog.getActionCommand())) {
      this.adaptee.btn_saveErrorLog_actionPerformed(e);
    } 
  }
}
