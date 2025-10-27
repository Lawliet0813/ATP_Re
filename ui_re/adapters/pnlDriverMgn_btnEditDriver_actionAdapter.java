package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlDriverMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlDriverMgn_btnEditDriver_actionAdapter implements ActionListener {
  pnlDriverMgn adaptee;
  
  pnlDriverMgn_btnEditDriver_actionAdapter(pnlDriverMgn parampnlDriverMgn) {
    this.adaptee = parampnlDriverMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnEditDriver_actionPerformed(paramActionEvent);
  }
}
