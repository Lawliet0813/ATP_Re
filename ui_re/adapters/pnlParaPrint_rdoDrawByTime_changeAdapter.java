package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlParaPrint;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class pnlParaPrint_rdoDrawByTime_changeAdapter implements ChangeListener {
  pnlParaPrint adaptee;
  
  pnlParaPrint_rdoDrawByTime_changeAdapter(pnlParaPrint parampnlParaPrint) {
    this.adaptee = parampnlParaPrint;
  }
  
  public void stateChanged(ChangeEvent paramChangeEvent) {
    this.adaptee.rdoDrawByTime_stateChanged(paramChangeEvent);
  }
}
