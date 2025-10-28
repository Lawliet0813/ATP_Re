package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlDataBackup;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class pnlDataBackup_pnlsearch_propertyChangeAdapter implements PropertyChangeListener {
  pnlDataBackup adaptee;
  
  pnlDataBackup_pnlsearch_propertyChangeAdapter(pnlDataBackup parampnlDataBackup) {
    this.adaptee = parampnlDataBackup;
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    this.adaptee.pnlsearch_propertyChange(paramPropertyChangeEvent);
  }
}
