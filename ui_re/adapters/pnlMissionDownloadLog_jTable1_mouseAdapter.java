package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlMissionDownloadLog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class pnlMissionDownloadLog_jTable1_mouseAdapter extends MouseAdapter {
  pnlMissionDownloadLog adaptee;
  
  pnlMissionDownloadLog_jTable1_mouseAdapter(pnlMissionDownloadLog parampnlMissionDownloadLog) {
    this.adaptee = parampnlMissionDownloadLog;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    this.adaptee.jTable1_mouseClicked(paramMouseEvent);
  }
}
