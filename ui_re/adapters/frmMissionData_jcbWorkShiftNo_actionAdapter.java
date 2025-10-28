package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmMissionData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmMissionData_jcbWorkShiftNo_actionAdapter implements ActionListener {
  frmMissionData adaptee;
  
  frmMissionData_jcbWorkShiftNo_actionAdapter(frmMissionData paramfrmMissionData) {
    this.adaptee = paramfrmMissionData;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.jcbWorkShiftNo_actionPerformed(paramActionEvent);
  }
}
