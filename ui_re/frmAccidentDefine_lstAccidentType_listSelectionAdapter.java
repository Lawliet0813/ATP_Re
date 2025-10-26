package ui;

import com.MiTAC.TRA.ATP.ui.frmAccidentDefine;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class frmAccidentDefine_lstAccidentType_listSelectionAdapter implements ListSelectionListener {
  frmAccidentDefine adaptee;
  
  frmAccidentDefine_lstAccidentType_listSelectionAdapter(frmAccidentDefine paramfrmAccidentDefine) {
    this.adaptee = paramfrmAccidentDefine;
  }
  
  public void valueChanged(ListSelectionEvent paramListSelectionEvent) {
    this.adaptee.lstAccidentType_valueChanged(paramListSelectionEvent);
  }
}
