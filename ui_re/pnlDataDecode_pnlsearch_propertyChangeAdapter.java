package ui;

import com.MiTAC.TRA.ATP.ui.pnlDataDecode;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class pnlDataDecode_pnlsearch_propertyChangeAdapter implements PropertyChangeListener {
  pnlDataDecode adaptee;
  
  pnlDataDecode_pnlsearch_propertyChangeAdapter(pnlDataDecode parampnlDataDecode) {
    this.adaptee = parampnlDataDecode;
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    this.adaptee.pnlsearch_propertyChange(paramPropertyChangeEvent);
  }
}
