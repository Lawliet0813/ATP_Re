package ui;

import com.MiTAC.TRA.ATP.ui.pnlMissionDownload;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class pnlMissionDownload_jTable1_mouseAdapter extends MouseAdapter {
  pnlMissionDownload adaptee;
  
  pnlMissionDownload_jTable1_mouseAdapter(pnlMissionDownload parampnlMissionDownload) {
    this.adaptee = parampnlMissionDownload;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    this.adaptee.jTable1_mouseClicked(paramMouseEvent);
  }
}
