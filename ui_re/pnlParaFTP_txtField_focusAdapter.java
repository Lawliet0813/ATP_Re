package ui;

import com.MiTAC.TRA.ATP.ui.pnlParaFTP;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class pnlParaFTP_txtField_focusAdapter extends FocusAdapter {
  pnlParaFTP adaptee;
  
  pnlParaFTP_txtField_focusAdapter(pnlParaFTP parampnlParaFTP) {
    this.adaptee = parampnlParaFTP;
  }
  
  public void focusLost(FocusEvent paramFocusEvent) {
    this.adaptee.txtField_focusLost(paramFocusEvent);
  }
}
