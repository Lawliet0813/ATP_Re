package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlViewCode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlViewCode_btnOpen_actionAdapter implements ActionListener {
  pnlViewCode adaptee;
  
  pnlViewCode_btnOpen_actionAdapter(pnlViewCode parampnlViewCode) {
    this.adaptee = parampnlViewCode;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnOpen_actionPerformed(paramActionEvent);
  }
}
