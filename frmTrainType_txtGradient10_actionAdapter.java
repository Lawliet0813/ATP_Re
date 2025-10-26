package ui;

import com.MiTAC.TRA.ATP.ui.frmTrainType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTrainType_txtGradient10_actionAdapter implements ActionListener {
  frmTrainType adaptee;
  
  frmTrainType_txtGradient10_actionAdapter(frmTrainType paramfrmTrainType) {
    this.adaptee = paramfrmTrainType;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtGradient10_actionPerformed(paramActionEvent);
  }
}
