package decode;

import com.MiTAC.TRA.ATP.decode.Frame1;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;

public class Application1 {
  boolean packFrame = false;
  
  public Application1() {
    Frame1 frame1 = new Frame1();
    if (this.packFrame) {
      frame1.pack();
    } else {
      frame1.validate();
    } 
    Dimension dimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dimension2 = frame1.getSize();
    if (dimension2.height > dimension1.height)
      dimension2.height = dimension1.height; 
    if (dimension2.width > dimension1.width)
      dimension2.width = dimension1.width; 
    frame1.setLocation((dimension1.width - dimension2.width) / 2, (dimension1.height - dimension2.height) / 2);
    frame1.setTitle("TRA ATP RU-MMI Decoder    Version: 2.6");
    frame1.setVisible(true);
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    new com.MiTAC.TRA.ATP.decode.Application1();
  }
}
