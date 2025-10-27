package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlSearch;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlSearch_btnsearch_actionAdapter implements ActionListener {
  pnlSearch adaptee;
  
  pnlSearch_btnsearch_actionAdapter(pnlSearch parampnlSearch) {
    this.adaptee = parampnlSearch;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnsearch_actionPerformed(paramActionEvent);
  }
}
