package ui;

import com.MiTAC.TRA.ATP.ui.pnlParaFTP;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlParaFTP_btnTestCon_actionAdapter implements ActionListener {
  pnlParaFTP adaptee;
  
  pnlParaFTP_btnTestCon_actionAdapter(pnlParaFTP parampnlParaFTP) {
    this.adaptee = parampnlParaFTP;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnTestCon_actionPerformed(paramActionEvent);
  }
}
