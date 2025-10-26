package ui;

import com.MiTAC.TRA.ATP.ui.pnlParaGraphic;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class resetColor_propertyChangeListener implements PropertyChangeListener {
  pnlParaGraphic adaptee;
  
  resetColor_propertyChangeListener(pnlParaGraphic parampnlParaGraphic) {
    this.adaptee = parampnlParaGraphic;
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    this.adaptee.resetColor(paramPropertyChangeEvent);
  }
}
