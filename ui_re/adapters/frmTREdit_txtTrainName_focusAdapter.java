package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmTREdit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class frmTREdit_txtTrainName_focusAdapter extends FocusAdapter {
  frmTREdit adaptee;
  
  frmTREdit_txtTrainName_focusAdapter(frmTREdit paramfrmTREdit) {
    this.adaptee = paramfrmTREdit;
  }
  
  public void focusLost(FocusEvent paramFocusEvent) {
    this.adaptee.txtTrainName_focusLost(paramFocusEvent);
  }
}
