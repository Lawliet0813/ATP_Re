package decoder;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.decoder.DecodeBuffer;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.Timer;

public class btnDecodeWatcher extends JButton {
  private int btnRed = getBackground().getRed();
  
  public btnDecodeWatcher() {
    Timer watcher = new Timer(3000, (ActionListener)new Object(this));
    watcher.start();
  }
  
  private void refresh() {
    StringBuffer sb = new StringBuffer();
    sb.append(String.valueOf(ATPMessages.getString("MW.DECODE.BUFFER")) + ": ");
    sb.append(String.valueOf(DecodeBuffer.getBufferSize()) + ".");
    sb.append("  " + ATPMessages.getString("MW.DECODE.STATUS.COMPLETED") + ": ");
    sb.append(String.valueOf(DecodeBuffer.getBufferFinished()) + ".");
    sb.append("  " + ATPMessages.getString("MW.DECODE.STATUS.WAIT") + ": ");
    sb.append(String.valueOf(DecodeBuffer.getBufferWaiting()) + ".");
    int red = this.btnRed;
    if (DecodeBuffer.getBufferWaiting() > 0)
      red = 255; 
    Color newColor = new Color(red, getBackground().getGreen(), getBackground().getBlue());
    setBackground(newColor);
    setText(sb.toString());
  }
}
