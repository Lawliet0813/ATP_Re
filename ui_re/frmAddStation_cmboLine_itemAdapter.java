package ui;

import com.MiTAC.TRA.ATP.ui.frmAddStation;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class frmAddStation_cmboLine_itemAdapter implements ItemListener {
  frmAddStation adaptee;
  
  frmAddStation_cmboLine_itemAdapter(frmAddStation paramfrmAddStation) {
    this.adaptee = paramfrmAddStation;
  }
  
  public void itemStateChanged(ItemEvent paramItemEvent) {
    this.adaptee.cmboLine_itemStateChanged(paramItemEvent);
  }
}
