package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlDataSearch;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class pnlDataSearch_pnlsearch_propertyChangeAdapter implements PropertyChangeListener {
  pnlDataSearch adaptee;
  
  pnlDataSearch_pnlsearch_propertyChangeAdapter(pnlDataSearch parampnlDataSearch) {
    this.adaptee = parampnlDataSearch;
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    this.adaptee.pnlsearch_propertyChange(paramPropertyChangeEvent);
  }
}
