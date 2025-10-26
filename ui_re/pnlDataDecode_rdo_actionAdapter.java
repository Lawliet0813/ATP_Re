package ui;

import com.MiTAC.TRA.ATP.ui.pnlDataDecode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlDataDecode_rdo_actionAdapter implements ActionListener {
  pnlDataDecode adaptee;
  
  pnlDataDecode_rdo_actionAdapter(pnlDataDecode parampnlDataDecode) {
    this.adaptee = parampnlDataDecode;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    if (paramActionEvent.getActionCommand().equals(this.adaptee.rdoDecodeLocal.getActionCommand())) {
      this.adaptee.rdoDecodeLocal_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.rdoDecodeMO.getActionCommand())) {
      this.adaptee.rdoDecodeMO_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.rdoPath.getActionCommand())) {
      this.adaptee.rdoPath_actionPerformed(paramActionEvent);
    } 
  }
}
