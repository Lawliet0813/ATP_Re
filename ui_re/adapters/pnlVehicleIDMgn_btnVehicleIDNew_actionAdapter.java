package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlVehicleIDMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlVehicleIDMgn_btnVehicleIDNew_actionAdapter implements ActionListener {
  pnlVehicleIDMgn adaptee;
  
  pnlVehicleIDMgn_btnVehicleIDNew_actionAdapter(pnlVehicleIDMgn parampnlVehicleIDMgn) {
    this.adaptee = parampnlVehicleIDMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnVehicleIDNew_actionPerformed(paramActionEvent);
  }
}
