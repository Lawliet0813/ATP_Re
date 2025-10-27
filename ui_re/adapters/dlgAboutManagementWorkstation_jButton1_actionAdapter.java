package ui.adapters;

import com.MiTAC.TRA.ATP.ui.dialogs.dlgAboutManagementWorkstation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class dlgAboutManagementWorkstation_jButton1_actionAdapter implements ActionListener {
  dlgAboutManagementWorkstation adaptee;
  
  dlgAboutManagementWorkstation_jButton1_actionAdapter(dlgAboutManagementWorkstation paramdlgAboutManagementWorkstation) {
    this.adaptee = paramdlgAboutManagementWorkstation;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.jButton1_actionPerformed(paramActionEvent);
  }
}
