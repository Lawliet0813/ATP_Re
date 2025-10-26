package ui;

import com.MiTAC.TRA.ATP.ui.dlgsetTime;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class setTime_jButton2_actionAdapter implements ActionListener {
  dlgsetTime adaptee;
  
  setTime_jButton2_actionAdapter(dlgsetTime paramdlgsetTime) {
    this.adaptee = paramdlgsetTime;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.jButton2_actionPerformed(paramActionEvent);
  }
}
