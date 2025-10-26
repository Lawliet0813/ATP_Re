package ui;

import com.MiTAC.TRA.ATP.ui.frmTREdit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class frmTREdit_txtTrainNo_focusAdapter extends FocusAdapter {
  frmTREdit adaptee;
  
  frmTREdit_txtTrainNo_focusAdapter(frmTREdit paramfrmTREdit) {
    this.adaptee = paramfrmTREdit;
  }
  
  public void focusLost(FocusEvent paramFocusEvent) {
    this.adaptee.txtTrainNo_focusLost(paramFocusEvent);
  }
}
