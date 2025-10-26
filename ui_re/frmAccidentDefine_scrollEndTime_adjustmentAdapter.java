package ui;

import com.MiTAC.TRA.ATP.ui.frmAccidentDefine;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

class frmAccidentDefine_scrollEndTime_adjustmentAdapter implements AdjustmentListener {
  frmAccidentDefine adaptee;
  
  frmAccidentDefine_scrollEndTime_adjustmentAdapter(frmAccidentDefine paramfrmAccidentDefine) {
    this.adaptee = paramfrmAccidentDefine;
  }
  
  public void adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent) {
    this.adaptee.scrollEndTime_adjustmentValueChanged(paramAdjustmentEvent);
  }
}
