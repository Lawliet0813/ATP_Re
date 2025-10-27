package ui.adapters;

import com.MiTAC.TRA.ATP.ui.pnlMissionEdit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class pnlMissionEdit_jTable1_mouseAdapter extends MouseAdapter {
  pnlMissionEdit adaptee;
  
  pnlMissionEdit_jTable1_mouseAdapter(pnlMissionEdit parampnlMissionEdit) {
    this.adaptee = parampnlMissionEdit;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    this.adaptee.jTable1_mouseClicked(paramMouseEvent);
  }
}
