package ui;

import com.MiTAC.TRA.ATP.ui.pnlDataSearch;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlDataSearch_rdo_actionAdapter implements ActionListener {
  pnlDataSearch adaptee;
  
  pnlDataSearch_rdo_actionAdapter(pnlDataSearch parampnlDataSearch) {
    this.adaptee = parampnlDataSearch;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.rdo_Refresh(paramActionEvent);
  }
}
