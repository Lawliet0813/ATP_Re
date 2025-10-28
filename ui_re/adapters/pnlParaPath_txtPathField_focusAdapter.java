package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlParaPath;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class pnlParaPath_txtPathField_focusAdapter extends FocusAdapter {
  pnlParaPath adaptee;
  
  pnlParaPath_txtPathField_focusAdapter(pnlParaPath parampnlParaPath) {
    this.adaptee = parampnlParaPath;
  }
  
  public void focusLost(FocusEvent paramFocusEvent) {
    this.adaptee.txtPathField_focusLost(paramFocusEvent);
  }
}
