package ui;

import com.MiTAC.TRA.ATP.ui.frmAccidentDefine;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmAccidentDefine_btnCancel_actionAdapter implements ActionListener {
  frmAccidentDefine adaptee;
  
  frmAccidentDefine_btnCancel_actionAdapter(frmAccidentDefine paramfrmAccidentDefine) {
    this.adaptee = paramfrmAccidentDefine;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnCancel_actionPerformed(paramActionEvent);
  }
}
