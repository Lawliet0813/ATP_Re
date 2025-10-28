package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlMissionDownloadLog;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlMissionDownloadLog_jTable1_keyAdapter extends KeyAdapter {
  pnlMissionDownloadLog adaptee;
  
  pnlMissionDownloadLog_jTable1_keyAdapter(pnlMissionDownloadLog parampnlMissionDownloadLog) {
    this.adaptee = parampnlMissionDownloadLog;
  }
  
  public void keyReleased(KeyEvent paramKeyEvent) {
    this.adaptee.jTable1_keyReleased(paramKeyEvent);
  }
}
