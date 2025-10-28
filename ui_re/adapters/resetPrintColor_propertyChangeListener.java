package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlParaPrint;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class resetPrintColor_propertyChangeListener implements PropertyChangeListener {
  pnlParaPrint adaptee;
  
  resetPrintColor_propertyChangeListener(pnlParaPrint parampnlParaPrint) {
    this.adaptee = parampnlParaPrint;
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    this.adaptee.resetColor(paramPropertyChangeEvent);
  }
}
