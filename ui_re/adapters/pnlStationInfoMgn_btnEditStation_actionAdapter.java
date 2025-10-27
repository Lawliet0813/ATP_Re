package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlStationInfoMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlStationInfoMgn_btnEditStation_actionAdapter implements ActionListener {
  pnlStationInfoMgn adaptee;
  
  pnlStationInfoMgn_btnEditStation_actionAdapter(pnlStationInfoMgn parampnlStationInfoMgn) {
    this.adaptee = parampnlStationInfoMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnEditStation_actionPerformed(paramActionEvent);
  }
}
