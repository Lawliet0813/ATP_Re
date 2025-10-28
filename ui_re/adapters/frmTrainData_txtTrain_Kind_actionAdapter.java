package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmTrainData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTrainData_txtTrain_Kind_actionAdapter implements ActionListener {
  frmTrainData adaptee;
  
  frmTrainData_txtTrain_Kind_actionAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtTrain_Kind_actionPerformed(paramActionEvent);
  }
}
