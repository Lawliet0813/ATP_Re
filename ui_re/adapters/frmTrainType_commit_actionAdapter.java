package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frmTrainType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTrainType_commit_actionAdapter implements ActionListener {
  frmTrainType adaptee;
  
  frmTrainType_commit_actionAdapter(frmTrainType paramfrmTrainType) {
    this.adaptee = paramfrmTrainType;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.commit_actionPerformed(paramActionEvent);
  }
}
