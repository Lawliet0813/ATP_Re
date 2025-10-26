package ui;

import com.MiTAC.TRA.ATP.ui.pnlWorkShiftMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlWorkShiftMgn_btnEditWS_actionAdapter implements ActionListener {
  pnlWorkShiftMgn adaptee;
  
  pnlWorkShiftMgn_btnEditWS_actionAdapter(pnlWorkShiftMgn parampnlWorkShiftMgn) {
    this.adaptee = parampnlWorkShiftMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnEditWS_actionPerformed(paramActionEvent);
  }
}
