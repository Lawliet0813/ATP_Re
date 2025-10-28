package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlMissionDownload;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlMissionDownload_jTable1_keyAdapter extends KeyAdapter {
  pnlMissionDownload adaptee;
  
  pnlMissionDownload_jTable1_keyAdapter(pnlMissionDownload parampnlMissionDownload) {
    this.adaptee = parampnlMissionDownload;
  }
  
  public void keyReleased(KeyEvent paramKeyEvent) {
    this.adaptee.jTable1_keyReleased(paramKeyEvent);
  }
}
