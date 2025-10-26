package ui;

import com.MiTAC.TRA.ATP.ui.frmMissionData;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class frmMissionData_dateChooser_mouseAdapter extends MouseAdapter {
  frmMissionData adaptee;
  
  frmMissionData_dateChooser_mouseAdapter(frmMissionData paramfrmMissionData) {
    this.adaptee = paramfrmMissionData;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    this.adaptee.dateChooser_mouseClicked(paramMouseEvent);
  }
}
