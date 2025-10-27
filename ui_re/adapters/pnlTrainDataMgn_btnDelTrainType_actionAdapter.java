package ui.adapters;

import com.MiTAC.TRA.ATP.ui.pnlTrainDataMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlTrainDataMgn_btnDelTrainType_actionAdapter implements ActionListener {
  pnlTrainDataMgn adaptee;
  
  pnlTrainDataMgn_btnDelTrainType_actionAdapter(pnlTrainDataMgn parampnlTrainDataMgn) {
    this.adaptee = parampnlTrainDataMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.btnDelTrainType_actionPerformed(paramActionEvent);
  }
}
