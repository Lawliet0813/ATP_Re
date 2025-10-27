package decode;

import com.MiTAC.TRA.ATP.ui.panels.pnlViewCode;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Frame1 extends JFrame {
  public Frame1() {
    enableEvents(64L);
    try {
      _$12828();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$12828() throws Exception {
    getContentPane().setLayout(new BorderLayout());
    setSize(new Dimension(800, 600));
    setTitle("MMI-RU Decoder");
    pnlViewCode pnlViewCode = new pnlViewCode();
    getContentPane().add((Component)pnlViewCode, "Center");
  }
  
  protected void processWindowEvent(WindowEvent paramWindowEvent) {
    super.processWindowEvent(paramWindowEvent);
    if (paramWindowEvent.getID() == 201)
      System.exit(0); 
  }
}
