package ui.adapters;

import com.MiTAC.TRA.ATP.ui.pnlParaGraphic;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class pnlBD_pnlExample_mouseMotionAdapter extends MouseMotionAdapter {
  pnlParaGraphic adaptee;
  
  pnlBD_pnlExample_mouseMotionAdapter(pnlParaGraphic parampnlParaGraphic) {
    this.adaptee = parampnlParaGraphic;
  }
  
  public void mouseMoved(MouseEvent paramMouseEvent) {
    this.adaptee.pnlExample_mouseMoved(paramMouseEvent);
  }
}
