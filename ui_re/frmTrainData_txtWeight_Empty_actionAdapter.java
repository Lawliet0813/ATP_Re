package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTrainData_txtWeight_Empty_actionAdapter implements ActionListener {
  frmTrainData adaptee;
  
  frmTrainData_txtWeight_Empty_actionAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtWeight_Empty_actionPerformed(paramActionEvent);
  }
}
