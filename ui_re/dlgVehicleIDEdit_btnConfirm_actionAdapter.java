package ui;

import com.MiTAC.TRA.ATP.ui.dlgVehicleIDEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class dlgVehicleIDEdit_btnConfirm_actionAdapter implements ActionListener {
  dlgVehicleIDEdit adaptee;
  
  dlgVehicleIDEdit_btnConfirm_actionAdapter(dlgVehicleIDEdit paramdlgVehicleIDEdit) {
    this.adaptee = paramdlgVehicleIDEdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnConfirm_actionPerformed(paramActionEvent);
  }
}
