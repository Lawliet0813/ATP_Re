package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmWSEdit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class frmWSEdit_txtWSNo_focusAdapter extends FocusAdapter {
  frmWSEdit adaptee;
  
  frmWSEdit_txtWSNo_focusAdapter(frmWSEdit paramfrmWSEdit) {
    this.adaptee = paramfrmWSEdit;
  }
  
  public void focusLost(FocusEvent paramFocusEvent) {
    this.adaptee.txtWSNo_focusLost(paramFocusEvent);
  }
}
