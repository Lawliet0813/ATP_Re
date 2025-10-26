package ui;

import com.MiTAC.TRA.ATP.ui.pnlDataTransfer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlDataTransfer_btn_actionAdapter implements ActionListener {
  pnlDataTransfer adaptee;
  
  pnlDataTransfer_btn_actionAdapter(pnlDataTransfer parampnlDataTransfer) {
    this.adaptee = parampnlDataTransfer;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    if (paramActionEvent.getActionCommand().equals(this.adaptee.btnUpload.getActionCommand())) {
      this.adaptee.btnUpload_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnRefresh.getActionCommand())) {
      this.adaptee.btnRefresh_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnFileChoose.getActionCommand())) {
      this.adaptee.btnFileChoose_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnPathConfirm.getActionCommand())) {
      this.adaptee.btnPathConfirm_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnDelete.getActionCommand())) {
      this.adaptee.btnDelete_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.chxVIDMgn.getActionCommand())) {
      this.adaptee.chxVIDMgn_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnCheckUSBFreeSpace.getActionCommand())) {
      this.adaptee.btnCheckUSBFreeSpace_actionPerformed(paramActionEvent);
    } 
  }
}
