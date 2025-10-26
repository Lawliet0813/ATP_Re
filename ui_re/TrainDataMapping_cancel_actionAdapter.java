package ui;

import com.MiTAC.TRA.ATP.ui.TrainDataMapping;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TrainDataMapping_cancel_actionAdapter implements ActionListener {
  TrainDataMapping adaptee;
  
  TrainDataMapping_cancel_actionAdapter(TrainDataMapping paramTrainDataMapping) {
    this.adaptee = paramTrainDataMapping;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.cancel_actionPerformed(paramActionEvent);
  }
}
