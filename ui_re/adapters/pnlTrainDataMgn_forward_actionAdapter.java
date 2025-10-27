package ui.adapters;

import com.MiTAC.TRA.ATP.ui.pnlTrainDataMgn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pnlTrainDataMgn_forward_actionAdapter implements ActionListener {
  pnlTrainDataMgn adaptee;
  
  pnlTrainDataMgn_forward_actionAdapter(pnlTrainDataMgn parampnlTrainDataMgn) {
    this.adaptee = parampnlTrainDataMgn;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.forward_actionPerformed(paramActionEvent);
  }
}
