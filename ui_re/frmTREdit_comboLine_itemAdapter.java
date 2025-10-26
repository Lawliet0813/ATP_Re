package ui;

import com.MiTAC.TRA.ATP.ui.frmTREdit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class frmTREdit_comboLine_itemAdapter implements ItemListener {
  frmTREdit adaptee;
  
  frmTREdit_comboLine_itemAdapter(frmTREdit paramfrmTREdit) {
    this.adaptee = paramfrmTREdit;
  }
  
  public void itemStateChanged(ItemEvent paramItemEvent) {
    this.adaptee.comboLine_itemStateChanged(paramItemEvent);
  }
}
