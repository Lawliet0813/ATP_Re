package ui.adapters;

import com.MiTAC.TRA.ATP.ui.pnlParaGraphic;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class pnlParaGraphic_jTabbedPane1_changeAdapter implements ChangeListener {
  pnlParaGraphic adaptee;
  
  pnlParaGraphic_jTabbedPane1_changeAdapter(pnlParaGraphic parampnlParaGraphic) {
    this.adaptee = parampnlParaGraphic;
  }
  
  public void stateChanged(ChangeEvent paramChangeEvent) {
    this.adaptee.jTabbedPane1_stateChanged(paramChangeEvent);
  }
}
