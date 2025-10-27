package ui.adapters;

import com.MiTAC.TRA.ATP.ui.pnlSearchOfMissionDownload;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlSearchOfMissionDownload_btnSearch_actionAdapter implements ActionListener {
  pnlSearchOfMissionDownload adaptee;
  
  pnlSearchOfMissionDownload_btnSearch_actionAdapter(pnlSearchOfMissionDownload parampnlSearchOfMissionDownload) {
    this.adaptee = parampnlSearchOfMissionDownload;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnSearch_actionPerformed(paramActionEvent);
  }
}
