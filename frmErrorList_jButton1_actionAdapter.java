package ui;

import com.MiTAC.TRA.ATP.ui.frmErrorList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmErrorList_jButton1_actionAdapter implements ActionListener {
  frmErrorList adaptee;
  
  frmErrorList_jButton1_actionAdapter(frmErrorList paramfrmErrorList) {
    this.adaptee = paramfrmErrorList;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.jButton1_actionPerformed(paramActionEvent);
  }
}
