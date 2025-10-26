package ui;

import com.MiTAC.TRA.ATP.ui.pnlMissionDownload;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class pnlMissionDownload_jTable1_focusAdapter extends FocusAdapter {
  pnlMissionDownload adaptee;
  
  pnlMissionDownload_jTable1_focusAdapter(pnlMissionDownload parampnlMissionDownload) {
    this.adaptee = parampnlMissionDownload;
  }
  
  public void focusGained(FocusEvent paramFocusEvent) {
    this.adaptee.jTable1_focusGained(paramFocusEvent);
  }
}
