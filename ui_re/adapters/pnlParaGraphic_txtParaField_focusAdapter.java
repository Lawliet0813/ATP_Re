package ui.adapters;

import com.MiTAC.TRA.ATP.ui.pnlParaGraphic;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class pnlParaGraphic_txtParaField_focusAdapter extends FocusAdapter {
  pnlParaGraphic adaptee;
  
  pnlParaGraphic_txtParaField_focusAdapter(pnlParaGraphic parampnlParaGraphic) {
    this.adaptee = parampnlParaGraphic;
  }
  
  public void focusLost(FocusEvent paramFocusEvent) {
    this.adaptee.txtParaField_focusLost(paramFocusEvent);
  }
}
