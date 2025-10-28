package ui.adapters;

import com.MiTAC.TRA.ATP.ui.utils.TrainDataMapping;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TrainDataMapping_confirm_actionAdapter implements ActionListener {
  TrainDataMapping adaptee;
  
  TrainDataMapping_confirm_actionAdapter(TrainDataMapping paramTrainDataMapping) {
    this.adaptee = paramTrainDataMapping;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    this.adaptee.confirm_actionPerformed(paramActionEvent);
  }
}
