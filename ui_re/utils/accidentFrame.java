package ui.utils;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.core.ATPMission;
import com.MiTAC.TRA.ATP.core.ATPMissionGeneral;
import com.MiTAC.TRA.ATP.ui.panels.pnlDriverBehavior;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;

class accidentFrame extends JFrame {
  private static final long serialVersionUID = 1L;
  
  public accidentFrame(ATPMissionGeneral paramATPMissionGeneral, long paramLong1, long paramLong2, String paramString) {
    setSize(new Dimension(800, 600));
    setTitle(ATPMessages.getString("MW.LA.ACCIDENT.ANALYZE"));
    pnlDriverBehavior pnlDriverBehavior = new pnlDriverBehavior((ATPMission)paramATPMissionGeneral, paramLong1, paramLong2, paramString);
    getContentPane().add((Component)pnlDriverBehavior, "Center");
    setSize(Toolkit.getDefaultToolkit().getScreenSize());
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
