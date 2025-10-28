package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlMissionDownload;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlMissionDownload_showTrainRunning_actionAdapter implements ActionListener {
  pnlMissionDownload adaptee;
  
  pnlMissionDownload_showTrainRunning_actionAdapter(pnlMissionDownload parampnlMissionDownload) {
    this.adaptee = parampnlMissionDownload;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.showTrainRunning_actionPerformed(paramActionEvent);
  }
}
