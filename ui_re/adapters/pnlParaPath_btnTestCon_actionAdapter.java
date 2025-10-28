package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlParaPath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlParaPath_btnTestCon_actionAdapter implements ActionListener {
  pnlParaPath adaptee;
  
  pnlParaPath_btnTestCon_actionAdapter(pnlParaPath parampnlParaPath) {
    this.adaptee = parampnlParaPath;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnTestCon_actionPerformed(paramActionEvent);
  }
}
