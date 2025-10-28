package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmMissionData;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class frmMissionData_dateChooser_propertyChangeAdapter implements PropertyChangeListener {
  frmMissionData adaptee;
  
  frmMissionData_dateChooser_propertyChangeAdapter(frmMissionData paramfrmMissionData) {
    this.adaptee = paramfrmMissionData;
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    this.adaptee.dateChooser_propertyChange(paramPropertyChangeEvent);
  }
}
