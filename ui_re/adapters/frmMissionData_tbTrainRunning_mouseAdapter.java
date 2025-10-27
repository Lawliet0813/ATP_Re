package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmMissionData;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class frmMissionData_tbTrainRunning_mouseAdapter extends MouseAdapter {
  frmMissionData adaptee;
  
  frmMissionData_tbTrainRunning_mouseAdapter(frmMissionData paramfrmMissionData) {
    this.adaptee = paramfrmMissionData;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    this.adaptee.tbTrainRunning_mouseClicked(paramMouseEvent);
  }
}
