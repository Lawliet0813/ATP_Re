package ui;

import com.MiTAC.TRA.ATP.ui.pnlMWOperateLog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class pnlMWOperateLog_jTable1_mouseAdapter extends MouseAdapter {
  pnlMWOperateLog adaptee;
  
  pnlMWOperateLog_jTable1_mouseAdapter(pnlMWOperateLog parampnlMWOperateLog) {
    this.adaptee = parampnlMWOperateLog;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    this.adaptee.jTable1_mouseClicked(paramMouseEvent);
  }
}
