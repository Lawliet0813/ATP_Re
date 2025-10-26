package ui;

import com.MiTAC.TRA.ATP.ui.pnlDriverBehavior;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlDriverBehavior_btn_actionAdapter implements ActionListener {
  pnlDriverBehavior adaptee;
  
  pnlDriverBehavior_btn_actionAdapter(pnlDriverBehavior parampnlDriverBehavior) {
    this.adaptee = parampnlDriverBehavior;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    if (paramActionEvent.getActionCommand().equals(this.adaptee.btnPrintLarge.getActionCommand())) {
      this.adaptee.btnPrint_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnPrintSmall.getActionCommand())) {
      this.adaptee.btnPrint_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnZoom.getActionCommand())) {
      this.adaptee.btnZoom_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnOrgiZoom.getActionCommand())) {
      this.adaptee.btnOrgiZoom_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnZoomIn.getActionCommand())) {
      this.adaptee.btnZoomIn_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnSaveLarge.getActionCommand())) {
      this.adaptee.btnSaveLarge_actionPerformed(paramActionEvent);
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnSaveSmall.getActionCommand())) {
      this.adaptee.btnSaveSmall_actionPerformed(paramActionEvent);
    } 
  }
}
