package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmSetParameters;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmSetParameters_btn_actionAdapter implements ActionListener {
  frmSetParameters adaptee;
  
  frmSetParameters_btn_actionAdapter(frmSetParameters paramfrmSetParameters) {
    this.adaptee = paramfrmSetParameters;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    if (paramActionEvent.getActionCommand() == "confirm") {
      this.adaptee.btnConfirm_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand() == "cancel") {
      this.adaptee.btnCancel_actionPerformed(paramActionEvent);
    } else {
      this.adaptee.btnReset_actionPerformed(paramActionEvent);
    } 
  }
}
