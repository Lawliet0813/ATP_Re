package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frmTrainType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmTrainType_txtAllowSpeedAtCurves_actionAdapter implements ActionListener {
  frmTrainType adaptee;
  
  frmTrainType_txtAllowSpeedAtCurves_actionAdapter(frmTrainType paramfrmTrainType) {
    this.adaptee = paramfrmTrainType;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtAllowSpeedAtCurves_actionPerformed(paramActionEvent);
  }
}
