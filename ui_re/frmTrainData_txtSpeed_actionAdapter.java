package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTrainData_txtSpeed_actionAdapter implements ActionListener {
  frmTrainData adaptee;
  
  frmTrainData_txtSpeed_actionAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtSpeed_actionPerformed(paramActionEvent);
  }
}
