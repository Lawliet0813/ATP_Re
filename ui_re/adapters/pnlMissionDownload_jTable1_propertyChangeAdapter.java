package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlMissionDownload;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class pnlMissionDownload_jTable1_propertyChangeAdapter implements PropertyChangeListener {
  pnlMissionDownload adaptee;
  
  pnlMissionDownload_jTable1_propertyChangeAdapter(pnlMissionDownload parampnlMissionDownload) {
    this.adaptee = parampnlMissionDownload;
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    this.adaptee.jTable1_propertyChange(paramPropertyChangeEvent);
  }
}
