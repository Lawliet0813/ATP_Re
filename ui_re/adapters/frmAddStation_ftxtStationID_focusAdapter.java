package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmAddStation;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class frmAddStation_ftxtStationID_focusAdapter extends FocusAdapter {
  frmAddStation adaptee;
  
  frmAddStation_ftxtStationID_focusAdapter(frmAddStation paramfrmAddStation) {
    this.adaptee = paramfrmAddStation;
  }
  
  public void focusGained(FocusEvent paramFocusEvent) {
    this.adaptee.ftxtStationID_focusGained(paramFocusEvent);
  }
}
