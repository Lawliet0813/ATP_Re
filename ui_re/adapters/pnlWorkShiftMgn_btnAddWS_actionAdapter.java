package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlWorkShiftMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlWorkShiftMgn_btnAddWS_actionAdapter implements ActionListener {
  pnlWorkShiftMgn adaptee;
  
  pnlWorkShiftMgn_btnAddWS_actionAdapter(pnlWorkShiftMgn parampnlWorkShiftMgn) {
    this.adaptee = parampnlWorkShiftMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnAddWS_actionPerformed(paramActionEvent);
  }
}
