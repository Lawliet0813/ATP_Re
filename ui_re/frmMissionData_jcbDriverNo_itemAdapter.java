package ui;

import com.MiTAC.TRA.ATP.ui.frmMissionData;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class frmMissionData_jcbDriverNo_itemAdapter implements ItemListener {
  frmMissionData adaptee;
  
  frmMissionData_jcbDriverNo_itemAdapter(frmMissionData paramfrmMissionData) {
    this.adaptee = paramfrmMissionData;
  }
  
  public void itemStateChanged(ItemEvent paramItemEvent) {
    this.adaptee.jcbDriverNo_itemStateChanged(paramItemEvent);
  }
}
