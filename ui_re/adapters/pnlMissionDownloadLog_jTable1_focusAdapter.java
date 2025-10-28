package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlMissionDownloadLog;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class pnlMissionDownloadLog_jTable1_focusAdapter extends FocusAdapter {
  pnlMissionDownloadLog adaptee;
  
  pnlMissionDownloadLog_jTable1_focusAdapter(pnlMissionDownloadLog parampnlMissionDownloadLog) {
    this.adaptee = parampnlMissionDownloadLog;
  }
  
  public void focusGained(FocusEvent paramFocusEvent) {
    this.adaptee.jTable1_focusGained(paramFocusEvent);
  }
}
