package ui;

import com.MiTAC.TRA.ATP.ui.pnlDriverBehavior;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class pnlDriverBehavior_mouseMotionAdapter extends MouseMotionAdapter {
  pnlDriverBehavior adaptee;
  
  pnlDriverBehavior_mouseMotionAdapter(pnlDriverBehavior parampnlDriverBehavior) {
    this.adaptee = parampnlDriverBehavior;
  }
  
  public void mouseMoved(MouseEvent paramMouseEvent) {
    this.adaptee.mouseMoved(paramMouseEvent);
  }
}
