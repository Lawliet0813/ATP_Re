package ui;

import com.MiTAC.TRA.ATP.ui.pnlVehicleIDMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlVehicleIDMgn_btnVehicleIDDelete_actionAdapter implements ActionListener {
  pnlVehicleIDMgn adaptee;
  
  pnlVehicleIDMgn_btnVehicleIDDelete_actionAdapter(pnlVehicleIDMgn parampnlVehicleIDMgn) {
    this.adaptee = parampnlVehicleIDMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnVehicleIDDelete_actionPerformed(paramActionEvent);
  }
}
