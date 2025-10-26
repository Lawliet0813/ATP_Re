package ui;

import com.MiTAC.TRA.ATP.ui.pnlParaSQL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlParaSQL_btnTestCon_actionAdapter implements ActionListener {
  pnlParaSQL adaptee;
  
  pnlParaSQL_btnTestCon_actionAdapter(pnlParaSQL parampnlParaSQL) {
    this.adaptee = parampnlParaSQL;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnTestCon_actionPerformed(paramActionEvent);
  }
}
