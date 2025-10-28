package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlDataSearch;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlDataSearch_btnCancel_actionAdapter implements ActionListener {
  pnlDataSearch adaptee;
  
  pnlDataSearch_btnCancel_actionAdapter(pnlDataSearch parampnlDataSearch) {
    this.adaptee = parampnlDataSearch;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnCancel_Action(paramActionEvent);
  }
}
