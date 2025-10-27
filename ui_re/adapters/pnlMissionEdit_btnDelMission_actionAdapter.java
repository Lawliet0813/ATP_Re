package ui.adapters;

import com.MiTAC.TRA.ATP.ui.pnlMissionEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlMissionEdit_btnDelMission_actionAdapter implements ActionListener {
  pnlMissionEdit adaptee;
  
  pnlMissionEdit_btnDelMission_actionAdapter(pnlMissionEdit parampnlMissionEdit) {
    this.adaptee = parampnlMissionEdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnDelMission_actionPerformed(paramActionEvent);
  }
}
