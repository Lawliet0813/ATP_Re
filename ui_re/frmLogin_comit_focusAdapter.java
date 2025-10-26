package ui;

import com.MiTAC.TRA.ATP.ui.frmLogin;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class frmLogin_comit_focusAdapter extends FocusAdapter {
  frmLogin adaptee;
  
  frmLogin_comit_focusAdapter(frmLogin paramfrmLogin) {
    this.adaptee = paramfrmLogin;
  }
  
  public void focusGained(FocusEvent paramFocusEvent) {
    this.adaptee.comit_focusGained(paramFocusEvent);
  }
}
