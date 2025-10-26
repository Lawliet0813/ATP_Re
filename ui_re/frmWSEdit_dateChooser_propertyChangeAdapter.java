package ui;

import com.MiTAC.TRA.ATP.ui.frmWSEdit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class frmWSEdit_dateChooser_propertyChangeAdapter implements PropertyChangeListener {
  frmWSEdit adaptee;
  
  frmWSEdit_dateChooser_propertyChangeAdapter(frmWSEdit paramfrmWSEdit) {
    this.adaptee = paramfrmWSEdit;
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    this.adaptee.dateChooser_propertyChange(paramPropertyChangeEvent);
  }
}
