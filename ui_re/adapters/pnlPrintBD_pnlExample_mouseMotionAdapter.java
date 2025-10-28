package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlParaPrint;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class pnlPrintBD_pnlExample_mouseMotionAdapter extends MouseMotionAdapter {
  pnlParaPrint adaptee;
  
  pnlPrintBD_pnlExample_mouseMotionAdapter(pnlParaPrint parampnlParaPrint) {
    this.adaptee = parampnlParaPrint;
  }
  
  public void mouseMoved(MouseEvent paramMouseEvent) {
    this.adaptee.pnlExample_mouseMoved(paramMouseEvent);
  }
}
