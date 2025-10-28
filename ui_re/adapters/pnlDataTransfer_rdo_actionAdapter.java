package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlDataTransfer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlDataTransfer_rdo_actionAdapter implements ActionListener {
  pnlDataTransfer adaptee;
  
  pnlDataTransfer_rdo_actionAdapter(pnlDataTransfer parampnlDataTransfer) {
    this.adaptee = parampnlDataTransfer;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    if (paramActionEvent.getActionCommand().equals(this.adaptee.rdoPath.getActionCommand()) || paramActionEvent.getActionCommand().equals(this.adaptee.rdoUSB.getActionCommand()) || paramActionEvent.getActionCommand().equals(this.adaptee.rdoFromFTP.getActionCommand())) {
      this.adaptee.rdoMMILog.setVisible(false);
      this.adaptee.rdoRULog.setVisible(false);
      this.adaptee.chxVIDMgn.setEnabled(false);
    } 
    if (paramActionEvent.getActionCommand().equals(this.adaptee.rdoPath.getActionCommand())) {
      this.adaptee.rdoPath_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.rdoUSB.getActionCommand())) {
      this.adaptee.rdoUSB_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.rdoFromFTP.getActionCommand())) {
      this.adaptee.rdoFromFTP_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.rdoTOLocal.getActionCommand())) {
      this.adaptee.rdoTOLocal_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.rdoTOFTP.getActionCommand())) {
      this.adaptee.rdoTOFTP_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.rdoTOAuto.getActionCommand())) {
      this.adaptee.rdoTOAuto_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.rdoRULog.getActionCommand())) {
      this.adaptee.rdoRUMMILog_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.rdoMMILog.getActionCommand())) {
      this.adaptee.rdoRUMMILog_actionPerformed(paramActionEvent);
    } 
  }
}
