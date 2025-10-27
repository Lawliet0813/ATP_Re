package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmMissionData;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class frmMissionData_jcbWorkShiftNo_itemAdapter implements ItemListener {
  frmMissionData adaptee;
  
  frmMissionData_jcbWorkShiftNo_itemAdapter(frmMissionData paramfrmMissionData) {
    this.adaptee = paramfrmMissionData;
  }
  
  public void itemStateChanged(ItemEvent paramItemEvent) {
    this.adaptee.jcbWorkShiftNo_itemStateChanged(paramItemEvent);
  }
}
