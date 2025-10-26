package ui;

import com.MiTAC.TRA.ATP.ui.dlgVehicleTypeEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class dlgVehicleTypeEdit_btnConfirm_actionAdapter implements ActionListener {
  dlgVehicleTypeEdit adaptee;
  
  dlgVehicleTypeEdit_btnConfirm_actionAdapter(dlgVehicleTypeEdit paramdlgVehicleTypeEdit) {
    this.adaptee = paramdlgVehicleTypeEdit;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnConfirm_actionPerformed(paramActionEvent);
  }
}
