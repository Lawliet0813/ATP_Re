package ui;

import com.MiTAC.TRA.ATP.ui.pnlSearchOfMWOperate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlSearchOfMWOperate_btnSearch_actionAdapter implements ActionListener {
  pnlSearchOfMWOperate adaptee;
  
  pnlSearchOfMWOperate_btnSearch_actionAdapter(pnlSearchOfMWOperate parampnlSearchOfMWOperate) {
    this.adaptee = parampnlSearchOfMWOperate;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnSearch_actionPerformed(paramActionEvent);
  }
}
