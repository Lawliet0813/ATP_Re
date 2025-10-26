package ui;

import com.MiTAC.TRA.ATP.ui.pnlMissionDownload;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlMissionDownload_btnUSBRefrsh_actionAdapter implements ActionListener {
  pnlMissionDownload adaptee;
  
  pnlMissionDownload_btnUSBRefrsh_actionAdapter(pnlMissionDownload parampnlMissionDownload) {
    this.adaptee = parampnlMissionDownload;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnUSBRefrsh_actionPerformed(paramActionEvent);
  }
}
