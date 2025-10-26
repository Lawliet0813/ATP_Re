package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.FtpBuffer;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.Timer;

public class btnFTPWatcher extends JButton {
  public btnFTPWatcher() {
    Timer timer = new Timer(3000, (ActionListener)new Object(this));
    timer.start();
  }
  
  private void _$12168() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(ATPMessages.getString("MW.FTP.BUFFER") + ": ");
    stringBuffer.append(FtpBuffer.getBufferSize() + ".");
    stringBuffer.append("  " + ATPMessages.getString("MW.FTP.STATUS.COMPLETED") + ": ");
    stringBuffer.append(FtpBuffer.getBufferFinished() + ".");
    stringBuffer.append("  " + ATPMessages.getString("MW.FTP.STATUS.WAIT") + ": ");
    stringBuffer.append(FtpBuffer.getBufferWaiting() + ".");
    setText(stringBuffer.toString());
  }
}
