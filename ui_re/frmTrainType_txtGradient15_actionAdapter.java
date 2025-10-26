package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTrainType_txtGradient15_actionAdapter implements ActionListener {
  frmTrainType adaptee;
  
  frmTrainType_txtGradient15_actionAdapter(frmTrainType paramfrmTrainType) {
    this.adaptee = paramfrmTrainType;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtGradient15_actionPerformed(paramActionEvent);
  }
}
