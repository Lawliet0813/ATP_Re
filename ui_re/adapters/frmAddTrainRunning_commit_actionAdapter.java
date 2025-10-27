package ui.adapters;

import com.MiTAC.TRA.ATP.ui.frames.frmAddTrainRunning;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class frmAddTrainRunning_commit_actionAdapter implements ActionListener {
  frmAddTrainRunning adaptee;
  
  frmAddTrainRunning_commit_actionAdapter(frmAddTrainRunning paramfrmAddTrainRunning) {
    this.adaptee = paramfrmAddTrainRunning;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.commit_actionPerformed(paramActionEvent);
  }
}
