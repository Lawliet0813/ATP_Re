package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlParaPrint;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class pnlParaPrint_jTabbedPane1_changeAdapter implements ChangeListener {
  pnlParaPrint adaptee;
  
  pnlParaPrint_jTabbedPane1_changeAdapter(pnlParaPrint parampnlParaPrint) {
    this.adaptee = parampnlParaPrint;
  }
  
  public void stateChanged(ChangeEvent paramChangeEvent) {
    this.adaptee.jTabbedPane1_stateChanged(paramChangeEvent);
  }
}
