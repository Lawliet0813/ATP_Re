package ui;

import com.MiTAC.TRA.ATP.ui.pnlDataAnalyze;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

class pnlDataAnalyze_btn_actionListener implements ActionListener {
  pnlDataAnalyze adaptee;
  
  public pnlDataAnalyze_btn_actionListener(pnlDataAnalyze parampnlDataAnalyze) {
    this.adaptee = parampnlDataAnalyze;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    if (paramActionEvent.getActionCommand().equals(this.adaptee.btnDeleteRecord.getActionCommand())) {
      int[] arrayOfInt = this.adaptee.jTable1.getSelectedRows();
      this.adaptee.deleteData.clear();
      for (byte b = 0; b < arrayOfInt.length; b++) {
        Vector vector = new Vector();
        vector.add(this.adaptee.dtm.getValueAt(arrayOfInt[b], 0));
        vector.add(this.adaptee.dtm.getValueAt(arrayOfInt[b], 1));
        vector.add(this.adaptee.dtm.getValueAt(arrayOfInt[b], 2));
        vector.add(this.adaptee.dtm.getValueAt(arrayOfInt[b], 3));
        vector.add(this.adaptee.dtm.getValueAt(arrayOfInt[b], 4));
        this.adaptee.deleteData.add(vector);
      } 
      this.adaptee.action_btnDeleteRecord();
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnCabinFailureReport.getActionCommand())) {
      this.adaptee.action_btnFilterCabinFailureReport();
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnFilterCabinFailureReport.getActionCommand())) {
      this.adaptee.action_btnFilterCabinFailureReport();
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnStopStationExport.getActionCommand())) {
      this.adaptee.action_btnStopStationExport();
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnDriverBehaviorReport.getActionCommand())) {
      this.adaptee.action_btnDriverBehaviorReport();
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnFilterDriverBehaviorReport.getActionCommand())) {
      this.adaptee.action_btnFilterDriverBehaviorReport();
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnRefresh.getActionCommand())) {
      this.adaptee.action_btnRefresh();
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnWaysideFailureReport.getActionCommand())) {
      this.adaptee.action_btnFilterWaysideFailureReport();
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnFilterWaysideFailureReport.getActionCommand())) {
      this.adaptee.action_btnFilterWaysideFailureReport();
    } else if (paramActionEvent.getActionCommand().equals(this.adaptee.btnAccidentAnalyze.getActionCommand())) {
      this.adaptee.action_btnAccidentAnalyze();
    } 
  }
}
