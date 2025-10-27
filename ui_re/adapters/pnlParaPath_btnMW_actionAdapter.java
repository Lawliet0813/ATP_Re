package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlParaPath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlParaPath_btnMW_actionAdapter implements ActionListener {
  pnlParaPath adaptee;
  
  pnlParaPath_btnMW_actionAdapter(pnlParaPath parampnlParaPath) {
    this.adaptee = parampnlParaPath;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnMW_actionPerformed(paramActionEvent);
  }
}
