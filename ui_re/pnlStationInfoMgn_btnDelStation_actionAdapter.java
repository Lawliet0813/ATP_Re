package ui;

import com.MiTAC.TRA.ATP.ui.pnlStationInfoMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlStationInfoMgn_btnDelStation_actionAdapter implements ActionListener {
  pnlStationInfoMgn adaptee;
  
  pnlStationInfoMgn_btnDelStation_actionAdapter(pnlStationInfoMgn parampnlStationInfoMgn) {
    this.adaptee = parampnlStationInfoMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnDelStation_actionPerformed(paramActionEvent);
  }
}
