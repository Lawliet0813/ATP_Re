package ui;

import com.MiTAC.TRA.ATP.ui.pnlMWOperateLog;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class pnlMWOperateLog_search_propertyChangeAdapter implements PropertyChangeListener {
  pnlMWOperateLog adaptee;
  
  pnlMWOperateLog_search_propertyChangeAdapter(pnlMWOperateLog parampnlMWOperateLog) {
    this.adaptee = parampnlMWOperateLog;
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    this.adaptee.search_propertyChange(paramPropertyChangeEvent);
  }
}
