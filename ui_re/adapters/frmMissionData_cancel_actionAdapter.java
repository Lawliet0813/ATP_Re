package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmMissionData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmMissionData_cancel_actionAdapter implements ActionListener {
  frmMissionData adaptee;
  
  frmMissionData_cancel_actionAdapter(frmMissionData paramfrmMissionData) {
    this.adaptee = paramfrmMissionData;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.cancel_actionPerformed(paramActionEvent);
  }
}
