package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlStationInfoMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlStationInfoMgn_btnNewStation_actionAdapter implements ActionListener {
  pnlStationInfoMgn adaptee;
  
  pnlStationInfoMgn_btnNewStation_actionAdapter(pnlStationInfoMgn parampnlStationInfoMgn) {
    this.adaptee = parampnlStationInfoMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnNewStation_actionPerformed(paramActionEvent);
  }
}
