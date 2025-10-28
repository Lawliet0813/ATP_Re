package ui.panels;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class pnlReport extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  
  public pnlReport() {
    try {
      jbInit();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void jbInit() throws Exception {
    setLayout(this.borderLayout1);
  }
}
