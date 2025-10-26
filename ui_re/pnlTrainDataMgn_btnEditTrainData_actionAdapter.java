package ui;

import com.MiTAC.TRA.ATP.ui.pnlTrainDataMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlTrainDataMgn_btnEditTrainData_actionAdapter implements ActionListener {
  pnlTrainDataMgn adaptee;
  
  pnlTrainDataMgn_btnEditTrainData_actionAdapter(pnlTrainDataMgn parampnlTrainDataMgn) {
    this.adaptee = parampnlTrainDataMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnEditTrainData_actionPerformed(paramActionEvent);
  }
}
