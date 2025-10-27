package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlDataAnalyze;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class pnlDataAnalyze_pnlsearch_propertyChangeAdapter implements PropertyChangeListener {
  pnlDataAnalyze adaptee;
  
  pnlDataAnalyze_pnlsearch_propertyChangeAdapter(pnlDataAnalyze parampnlDataAnalyze) {
    this.adaptee = parampnlDataAnalyze;
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    this.adaptee.pnlsearch_propertyChange(paramPropertyChangeEvent);
  }
}
