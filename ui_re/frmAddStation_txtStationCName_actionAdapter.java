package ui;

import com.MiTAC.TRA.ATP.ui.frmAddStation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmAddStation_txtStationCName_actionAdapter implements ActionListener {
  frmAddStation adaptee;
  
  frmAddStation_txtStationCName_actionAdapter(frmAddStation paramfrmAddStation) {
    this.adaptee = paramfrmAddStation;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.txtStationCName_actionPerformed(paramActionEvent);
  }
}
