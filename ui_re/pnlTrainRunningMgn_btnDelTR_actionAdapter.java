package ui;

import com.MiTAC.TRA.ATP.ui.pnlTrainRunningMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlTrainRunningMgn_btnDelTR_actionAdapter implements ActionListener {
  pnlTrainRunningMgn adaptee;
  
  pnlTrainRunningMgn_btnDelTR_actionAdapter(pnlTrainRunningMgn parampnlTrainRunningMgn) {
    this.adaptee = parampnlTrainRunningMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnDelTR_actionPerformed(paramActionEvent);
  }
}
