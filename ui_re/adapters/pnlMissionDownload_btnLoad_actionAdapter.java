package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlMissionDownload;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlMissionDownload_btnLoad_actionAdapter implements ActionListener {
  pnlMissionDownload adaptee;
  
  pnlMissionDownload_btnLoad_actionAdapter(pnlMissionDownload parampnlMissionDownload) {
    this.adaptee = parampnlMissionDownload;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnLoad_actionPerformed(paramActionEvent);
  }
}
