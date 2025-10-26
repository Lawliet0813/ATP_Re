package ui;

import com.MiTAC.TRA.ATP.ui.pnlParaGraphic;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class pnlParaGraphic_rdoDrawByTime_changeAdapter implements ChangeListener {
  pnlParaGraphic adaptee;
  
  pnlParaGraphic_rdoDrawByTime_changeAdapter(pnlParaGraphic parampnlParaGraphic) {
    this.adaptee = parampnlParaGraphic;
  }
  
  public void stateChanged(ChangeEvent paramChangeEvent) {
    this.adaptee.rdoDrawByTime_stateChanged(paramChangeEvent);
  }
}
