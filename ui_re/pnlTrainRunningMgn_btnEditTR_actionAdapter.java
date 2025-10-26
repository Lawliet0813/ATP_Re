package ui;

import com.MiTAC.TRA.ATP.ui.pnlTrainRunningMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlTrainRunningMgn_btnEditTR_actionAdapter implements ActionListener {
  pnlTrainRunningMgn adaptee;
  
  pnlTrainRunningMgn_btnEditTR_actionAdapter(pnlTrainRunningMgn parampnlTrainRunningMgn) {
    this.adaptee = parampnlTrainRunningMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnEditTR_actionPerformed(paramActionEvent);
  }
}
