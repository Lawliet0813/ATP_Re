package ui;

import com.MiTAC.TRA.ATP.ui.frmWSEdit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class frmWSEdit_jFormattedTextField1_focusAdapter extends FocusAdapter {
  frmWSEdit adaptee;
  
  frmWSEdit_jFormattedTextField1_focusAdapter(frmWSEdit paramfrmWSEdit) {
    this.adaptee = paramfrmWSEdit;
  }
  
  public void focusLost(FocusEvent paramFocusEvent) {
    this.adaptee.jFormattedTextField1_focusLost(paramFocusEvent);
  }
}
