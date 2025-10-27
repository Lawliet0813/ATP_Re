package ui.adapters;

import com.MiTAC.TRA.ATP.ui.panels.pnlDriverBehavior;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class pnlDriverBehavior_jPanel1_mouseAdapter extends MouseAdapter {
  pnlDriverBehavior adaptee;
  
  pnlDriverBehavior_jPanel1_mouseAdapter(pnlDriverBehavior parampnlDriverBehavior) {
    this.adaptee = parampnlDriverBehavior;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    this.adaptee.jPanel1_mouseClicked(paramMouseEvent);
  }
}
