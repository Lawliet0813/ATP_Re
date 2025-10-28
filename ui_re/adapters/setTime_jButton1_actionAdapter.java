package ui.adapters;

import com.MiTAC.TRA.ATP.ui.dialogs.dlgsetTime;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class setTime_jButton1_actionAdapter implements ActionListener {
  dlgsetTime adaptee;
  
  setTime_jButton1_actionAdapter(dlgsetTime paramdlgsetTime) {
    this.adaptee = paramdlgsetTime;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.jButton1_actionPerformed(paramActionEvent);
  }
}
