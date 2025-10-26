package ui;

import com.MiTAC.TRA.ATP.ui.pnlMissionEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlMissionEdit_btnAddMission_actionAdapter implements ActionListener {
  pnlMissionEdit adaptee;
  
  pnlMissionEdit_btnAddMission_actionAdapter(pnlMissionEdit parampnlMissionEdit) {
    this.adaptee = parampnlMissionEdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnAddMission_actionPerformed(paramActionEvent);
  }
}
