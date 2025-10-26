package ui;

import com.MiTAC.TRA.ATP.ui.pnlViewCode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class pnlViewCode_Table_mouseAdapter extends MouseAdapter {
  pnlViewCode adaptee;
  
  pnlViewCode_Table_mouseAdapter(pnlViewCode parampnlViewCode) {
    this.adaptee = parampnlViewCode;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    this.adaptee.Table_mouseClicked(paramMouseEvent);
  }
}
