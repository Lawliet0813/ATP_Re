package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmTrainData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTrainData_txtSB_delay_time_actionAdapter implements ActionListener {
  frmTrainData adaptee;
  
  frmTrainData_txtSB_delay_time_actionAdapter(frmTrainData paramfrmTrainData) {
    this.adaptee = paramfrmTrainData;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtSB_delay_time_actionPerformed(paramActionEvent);
  }
}
