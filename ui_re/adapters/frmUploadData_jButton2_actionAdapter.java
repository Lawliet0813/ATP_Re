package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmUploadData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmUploadData_jButton2_actionAdapter implements ActionListener {
  frmUploadData adaptee;
  
  frmUploadData_jButton2_actionAdapter(frmUploadData paramfrmUploadData) {
    this.adaptee = paramfrmUploadData;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.jButton2_actionPerformed(paramActionEvent);
  }
}
