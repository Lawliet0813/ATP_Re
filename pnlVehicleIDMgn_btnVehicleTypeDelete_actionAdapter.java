package ui;

import com.MiTAC.TRA.ATP.ui.pnlVehicleIDMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlVehicleIDMgn_btnVehicleTypeDelete_actionAdapter implements ActionListener {
  pnlVehicleIDMgn adaptee;
  
  pnlVehicleIDMgn_btnVehicleTypeDelete_actionAdapter(pnlVehicleIDMgn parampnlVehicleIDMgn) {
    this.adaptee = parampnlVehicleIDMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnVehicleTypeDelete_actionPerformed(paramActionEvent);
  }
}
