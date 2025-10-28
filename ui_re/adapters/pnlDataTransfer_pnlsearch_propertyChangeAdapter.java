package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlDataTransfer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class pnlDataTransfer_pnlsearch_propertyChangeAdapter implements PropertyChangeListener {
  pnlDataTransfer adaptee;
  
  pnlDataTransfer_pnlsearch_propertyChangeAdapter(pnlDataTransfer parampnlDataTransfer) {
    this.adaptee = parampnlDataTransfer;
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    this.adaptee.pnlsearch_propertyChange(paramPropertyChangeEvent);
  }
}
