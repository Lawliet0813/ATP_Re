package ui.adapters;

import com.MiTAC.TRA.ATP.ui.pnlTrainRunningMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlTrainRunningMgn_btnAddTR_actionAdapter implements ActionListener {
  pnlTrainRunningMgn adaptee;
  
  pnlTrainRunningMgn_btnAddTR_actionAdapter(pnlTrainRunningMgn parampnlTrainRunningMgn) {
    this.adaptee = parampnlTrainRunningMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnAddTR_actionPerformed(paramActionEvent);
  }
}
