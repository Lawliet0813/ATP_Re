package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTrainData_txtMax_Acceleration_actionAdapter implements ActionListener {
  frmTrainData adaptee;
  
  frmTrainData_txtMax_Acceleration_actionAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtMax_Acceleration_actionPerformed(paramActionEvent);
  }
}
