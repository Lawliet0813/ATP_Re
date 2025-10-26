package ui;

import com.MiTAC.TRA.ATP.ui.pnlMissionDownloadLog;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class pnlMissionDownloadLog_searchOfMissionDownload_propertyChangeAdapter implements PropertyChangeListener {
  pnlMissionDownloadLog adaptee;
  
  pnlMissionDownloadLog_searchOfMissionDownload_propertyChangeAdapter(pnlMissionDownloadLog parampnlMissionDownloadLog) {
    this.adaptee = parampnlMissionDownloadLog;
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    this.adaptee.searchOfMissionDownload_propertyChange(paramPropertyChangeEvent);
  }
}
