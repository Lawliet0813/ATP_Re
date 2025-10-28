package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlMWOperateLog;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class pnlMWOperateLog_jTable1_focusAdapter extends FocusAdapter {
  pnlMWOperateLog adaptee;
  
  pnlMWOperateLog_jTable1_focusAdapter(pnlMWOperateLog parampnlMWOperateLog) {
    this.adaptee = parampnlMWOperateLog;
  }
  
  public void focusGained(FocusEvent paramFocusEvent) {
    this.adaptee.jTable1_focusGained(paramFocusEvent);
  }
}
