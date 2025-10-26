package ui;

import com.MiTAC.TRA.ATP.ui.pnlTrainDataMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlTrainDataMgn_btnCreateTrainData_actionAdapter implements ActionListener {
  pnlTrainDataMgn adaptee;
  
  pnlTrainDataMgn_btnCreateTrainData_actionAdapter(pnlTrainDataMgn parampnlTrainDataMgn) {
    this.adaptee = parampnlTrainDataMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnCreateTrainData_actionPerformed(paramActionEvent);
  }
}
