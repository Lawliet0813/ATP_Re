package ui;

import com.MiTAC.TRA.ATP.ui.frmAccidentDefine;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

class frmAccidentDefine_scrollStartTime_adjustmentAdapter implements AdjustmentListener {
  frmAccidentDefine adaptee;
  
  frmAccidentDefine_scrollStartTime_adjustmentAdapter(frmAccidentDefine paramfrmAccidentDefine) {
    this.adaptee = paramfrmAccidentDefine;
  }
  
  public void adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent) {
    this.adaptee.scrollStartTime_adjustmentValueChanged(paramAdjustmentEvent);
  }
}
