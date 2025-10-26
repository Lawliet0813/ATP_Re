package ui;

import com.MiTAC.TRA.ATP.ui.pnlVehicleIDMgn;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class pnlVehicleIDMgn_jTable1_keyAdapter extends KeyAdapter {
  pnlVehicleIDMgn adaptee;
  
  pnlVehicleIDMgn_jTable1_keyAdapter(pnlVehicleIDMgn parampnlVehicleIDMgn) {
    this.adaptee = parampnlVehicleIDMgn;
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {
    this.adaptee.jTable1_keyPressed(paramKeyEvent);
  }
}
