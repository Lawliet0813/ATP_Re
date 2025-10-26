package ui;

import com.MiTAC.TRA.ATP.ui.frmMissionData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmMissionData_confirm_actionAdapter implements ActionListener {
  frmMissionData adaptee;
  
  frmMissionData_confirm_actionAdapter(frmMissionData paramfrmMissionData) {
    this.adaptee = paramfrmMissionData;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.confirm_actionPerformed(paramActionEvent);
  }
}
