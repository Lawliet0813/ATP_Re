package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTrainData_txtEB_deceleration_actionAdapter implements ActionListener {
  frmTrainData adaptee;
  
  frmTrainData_txtEB_deceleration_actionAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtEB_deceleration_actionPerformed(paramActionEvent);
  }
}
