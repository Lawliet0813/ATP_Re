package ui;

import com.MiTAC.TRA.ATP.ui.pnlMissionEdit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class pnlMissionEdit_pnlsearch_propertyChangeAdapter implements PropertyChangeListener {
  pnlMissionEdit adaptee;
  
  pnlMissionEdit_pnlsearch_propertyChangeAdapter(pnlMissionEdit parampnlMissionEdit) {
    this.adaptee = parampnlMissionEdit;
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    this.adaptee.pnlsearch_propertyChange(paramPropertyChangeEvent);
  }
}
