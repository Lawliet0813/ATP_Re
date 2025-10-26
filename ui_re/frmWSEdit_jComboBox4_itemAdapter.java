package ui;

import com.MiTAC.TRA.ATP.ui.frmWSEdit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class frmWSEdit_jComboBox4_itemAdapter implements ItemListener {
  frmWSEdit adaptee;
  
  frmWSEdit_jComboBox4_itemAdapter(frmWSEdit paramfrmWSEdit) {
    this.adaptee = paramfrmWSEdit;
  }
  
  public void itemStateChanged(ItemEvent paramItemEvent) {
    this.adaptee.jComboBox4_itemStateChanged(paramItemEvent);
  }
}
