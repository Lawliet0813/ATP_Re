package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlWorkShiftMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlWorkShiftMgn_btnDelWS_actionAdapter implements ActionListener {
  pnlWorkShiftMgn adaptee;
  
  pnlWorkShiftMgn_btnDelWS_actionAdapter(pnlWorkShiftMgn parampnlWorkShiftMgn) {
    this.adaptee = parampnlWorkShiftMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnDelWS_actionPerformed(paramActionEvent);
  }
}
