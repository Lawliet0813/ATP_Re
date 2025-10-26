package ui;

import com.MiTAC.TRA.ATP.ui.pnlSearch;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlSearch_btn_actionAdapter implements ActionListener {
  pnlSearch adaptee;
  
  pnlSearch_btn_actionAdapter(pnlSearch parampnlSearch) {
    this.adaptee = parampnlSearch;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    if (paramActionEvent.getActionCommand() == this.adaptee.btnclean.getActionCommand()) {
      this.adaptee.btnclean_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand() == this.adaptee.btnsearch.getActionCommand()) {
      this.adaptee.btnsearch_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand() == this.adaptee.btnSave.getActionCommand()) {
      this.adaptee.btnSave_actionPerformed(paramActionEvent);
    } 
  }
}
