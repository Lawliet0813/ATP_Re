package ui;

import com.MiTAC.TRA.ATP.ui.pnlParaSQL;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class pnlParaSQL_txtField_focusAdapter extends FocusAdapter {
  pnlParaSQL adaptee;
  
  pnlParaSQL_txtField_focusAdapter(pnlParaSQL parampnlParaSQL) {
    this.adaptee = parampnlParaSQL;
  }
  
  public void focusLost(FocusEvent paramFocusEvent) {
    this.adaptee.txtField_focusLost(paramFocusEvent);
  }
}
