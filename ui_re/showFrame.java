package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.core.ATPMission;
import com.MiTAC.TRA.ATP.ui.pnlDriverBehavior;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;

class showFrame extends JFrame {
  private static final long serialVersionUID = 1L;
  
  public showFrame(ATPMission paramATPMission, long paramLong) {
    setDefaultCloseOperation(2);
    setTitle(ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.ANALYZE") + " " + paramATPMission.getPath());
    pnlDriverBehavior pnlDriverBehavior = new pnlDriverBehavior(paramATPMission, paramLong);
    setContentPane((Container)pnlDriverBehavior);
    setSize(Toolkit.getDefaultToolkit().getScreenSize());
    setExtendedState(6);
  }
  
  public showFrame(ATPMission paramATPMission) {
    setDefaultCloseOperation(2);
    setTitle(ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.ANALYZE") + " " + paramATPMission.getPath());
    pnlDriverBehavior pnlDriverBehavior = new pnlDriverBehavior(paramATPMission);
    setContentPane((Container)pnlDriverBehavior);
    setSize(Toolkit.getDefaultToolkit().getScreenSize());
    setExtendedState(6);
  }
  
  protected void processWindowEvent(WindowEvent paramWindowEvent) {
    super.processWindowEvent(paramWindowEvent);
    if (paramWindowEvent.getID() == 201) {
      File file = new File("C:\\ATPMW\\windowsChecking");
      if (file.exists())
        file.delete(); 
    } 
  }
}
