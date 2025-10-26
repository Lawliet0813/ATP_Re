package ui;

import com.MiTAC.TRA.ATP.ui.frmMissionData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmMissionData_jButton1_actionAdapter implements ActionListener {
  frmMissionData adaptee;
  
  frmMissionData_jButton1_actionAdapter(frmMissionData paramfrmMissionData) {
    this.adaptee = paramfrmMissionData;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.jButton1_actionPerformed(paramActionEvent);
  }
}
