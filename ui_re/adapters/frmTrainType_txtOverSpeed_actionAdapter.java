package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frmTrainType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTrainType_txtOverSpeed_actionAdapter implements ActionListener {
  frmTrainType adaptee;
  
  frmTrainType_txtOverSpeed_actionAdapter(frmTrainType paramfrmTrainType) {
    this.adaptee = paramfrmTrainType;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtOverSpeed_actionPerformed(paramActionEvent);
  }
}
