package ui.utils;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.ui.frames.frmLogin;
import javax.swing.UIManager;

public class ManagementWorkstation {
  boolean packFrame = false;
  
  public ManagementWorkstation() {
    frmLogin frmLogin = new frmLogin();
    if (this.packFrame) {
      frmLogin.pack();
    } else {
      frmLogin.validate();
    } 
    frmLogin.setVisible(true);
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      if (paramArrayOfString[0] != null)
        ATPMessages.changeLocale(paramArrayOfString[0]); 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    new com.MiTAC.TRA.ATP.ui.ManagementWorkstation();
  }
}
