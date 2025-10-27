package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlDataDecode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlDataDecode_btn_actionAdapter implements ActionListener {
  pnlDataDecode adaptee;
  
  pnlDataDecode_btn_actionAdapter(pnlDataDecode parampnlDataDecode) {
    this.adaptee = parampnlDataDecode;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    if (paramActionEvent.getActionCommand().equals(this.adaptee.btnDecode.getActionCommand())) {
      this.adaptee.btnDecode_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnFileChoose.getActionCommand())) {
      this.adaptee.btnFileChoose_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnPathRefresh.getActionCommand())) {
      this.adaptee.btnPathRefresh_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnPathSubmit.getActionCommand())) {
      this.adaptee.btnPathSubmit_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnViewCode.getActionCommand())) {
      this.adaptee.btnViewCode_actionPerformed(paramActionEvent);
    } 
  }
}
