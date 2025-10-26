package ui;

import com.MiTAC.TRA.ATP.ui.frmDriverMain;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmDriverMain_jButton4_actionAdapter implements ActionListener {
  frmDriverMain adaptee;
  
  frmDriverMain_jButton4_actionAdapter(frmDriverMain paramfrmDriverMain) {
    this.adaptee = paramfrmDriverMain;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.jButton4_actionPerformed(paramActionEvent);
  }
}
