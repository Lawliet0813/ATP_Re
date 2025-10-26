package ui;

import com.MiTAC.TRA.ATP.ui.pnlParaPrint;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class pnlParaPrint_txtParaField_focusAdapter extends FocusAdapter {
  pnlParaPrint adaptee;
  
  pnlParaPrint_txtParaField_focusAdapter(pnlParaPrint parampnlParaPrint) {
    this.adaptee = parampnlParaPrint;
  }
  
  public void focusLost(FocusEvent paramFocusEvent) {
    this.adaptee.txtParaField_focusLost(paramFocusEvent);
  }
}
