package ui;

import com.MiTAC.TRA.ATP.ui.pnlParaPath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlParaPath_btnUSB_actionAdapter implements ActionListener {
  pnlParaPath adaptee;
  
  pnlParaPath_btnUSB_actionAdapter(pnlParaPath parampnlParaPath) {
    this.adaptee = parampnlParaPath;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnUSB_actionPerformed(paramActionEvent);
  }
}
