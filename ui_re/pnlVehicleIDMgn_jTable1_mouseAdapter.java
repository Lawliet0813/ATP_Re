package ui;

import com.MiTAC.TRA.ATP.ui.pnlVehicleIDMgn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class pnlVehicleIDMgn_jTable1_mouseAdapter extends MouseAdapter {
  pnlVehicleIDMgn adaptee;
  
  pnlVehicleIDMgn_jTable1_mouseAdapter(pnlVehicleIDMgn parampnlVehicleIDMgn) {
    this.adaptee = parampnlVehicleIDMgn;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    this.adaptee.jTable1_mouseClicked(paramMouseEvent);
  }
}
