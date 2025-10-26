package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTrainData_txtSB_deceleration_actionAdapter implements ActionListener {
  frmTrainData adaptee;
  
  frmTrainData_txtSB_deceleration_actionAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtSB_deceleration_actionPerformed(paramActionEvent);
  }
}
