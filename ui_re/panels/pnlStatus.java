package ui.panels;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.connect.ConnectFTP;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class pnlStatus extends JPanel {
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
  JLabel jLabel5 = new JLabel();
  
  JLabel lblFTP = new JLabel();
  
  JLabel lblHD = new JLabel();
  
  JLabel lblOpened = new JLabel();
  
  JLabel lblSQL = new JLabel();
  
  JLabel lblTitle = new JLabel();
  
  JLabel lblUserName = new JLabel();
  
  public pnlStatus() {
    try {
      _$3120();
      _$16978();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$16981() {
    boolean bool = false;
    try {
      ConnectFTP connectFTP = new ConnectFTP();
      bool = connectFTP.testConnectFTP(InitParameters.FTPHostIP, InitParameters.FTPPort, InitParameters.FTPUserName, InitParameters.FTPUserPWD);
      if (bool) {
        this.lblFTP.setText(ATPMessages.getString("MW.STATUS.FTP_CONNECTION.OK"));
      } else {
        this.lblFTP.setText(ATPMessages.getString("MW.STATUS.FTP_CONNECTION.FAILURE"));
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      this.lblFTP.setText(ATPMessages.getString("MW.STATUS.FTP_CONNECTION.UNKNOW_FAILURE"));
    } 
  }
  
  private void _$3120() throws Exception {
    setLayout(this.gridBagLayout1);
    this.lblOpened.setText("");
    this.jLabel1.setBorder(BorderFactory.createLoweredBevelBorder());
    this.jLabel1.setDebugGraphicsOptions(0);
    this.jLabel1.setOpaque(false);
    this.jLabel1.setText(ATPMessages.getString("MW.STATUS.FTP_CONNECTION"));
    this.jLabel1.setVerticalAlignment(0);
    this.jLabel1.setVerticalTextPosition(0);
    setBorder(BorderFactory.createEtchedBorder());
    this.lblFTP.setBorder(BorderFactory.createLoweredBevelBorder());
    this.lblFTP.setDebugGraphicsOptions(0);
    this.lblFTP.setText(ATPMessages.getString("MW.STATUS"));
    this.jLabel3.setBorder(BorderFactory.createLoweredBevelBorder());
    this.jLabel3.setDebugGraphicsOptions(0);
    this.jLabel3.setText(ATPMessages.getString("MW.STATUS.SQL_CONNECTION"));
    this.lblSQL.setText(ATPMessages.getString("MW.STATUS.SQL_CONNECTION.OK"));
    this.lblSQL.setDebugGraphicsOptions(0);
    this.lblSQL.setBorder(BorderFactory.createLoweredBevelBorder());
    this.jLabel5.setText(ATPMessages.getString("MW.STATUS.MWDISK_SPACE"));
    this.jLabel5.setDebugGraphicsOptions(0);
    this.jLabel5.setBorder(BorderFactory.createLoweredBevelBorder());
    this.lblHD.setText(ATPMessages.getString("MW.STATUS.MWDISK_SPACE_GOOD"));
    this.lblHD.setDebugGraphicsOptions(0);
    this.lblHD.setBorder(BorderFactory.createLoweredBevelBorder());
    this.lblUserName.setBorder(BorderFactory.createLoweredBevelBorder());
    this.lblUserName.setText(ATPMessages.getString("MW.LOGIN.USERNAME"));
    this.lblTitle.setBorder(BorderFactory.createLoweredBevelBorder());
    this.lblTitle.setText(ATPMessages.getString("MW.LOGIN.USERPRIORITY"));
    add(this.lblOpened, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 80, 1));
    add(this.jLabel3, new GridBagConstraints(5, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 11, 0, 0), 2, 1));
    add(this.lblSQL, new GridBagConstraints(6, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 35, 1));
    add(this.jLabel5, new GridBagConstraints(7, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 7, 0, 0), 2, 1));
    add(this.lblHD, new GridBagConstraints(8, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 1), 35, 1));
    add(this.lblFTP, new GridBagConstraints(4, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 35, 1));
    add(this.jLabel1, new GridBagConstraints(3, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 5, 1));
    add(this.lblTitle, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 1));
    add(this.lblUserName, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 82, 0, 0), 0, 1));
  }
  
  private void _$16978() {
    Timer timer1 = new Timer(3600000, (ActionListener)new Object(this));
    Timer timer2 = new Timer(3600000, (ActionListener)new Object(this));
    Timer timer3 = new Timer(3600000, (ActionListener)new Object(this));
    timer1.setInitialDelay(0);
    timer1.start();
    timer2.setInitialDelay(0);
    timer2.start();
    timer3.setInitialDelay(0);
    timer3.start();
  }
  
  private void _$16983() {
    Connection connection = null;
    try {
      Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
      connection = DriverManager.getConnection("jdbc:microsoft:sqlserver://" + InitParameters.SQLName + ":1433", InitParameters.SQLUserName, InitParameters.SQLUserPWD);
      this.lblSQL.setText(ATPMessages.getString("MW.STATUS.SQL_CONNECTION.OK"));
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      this.lblSQL.setText(ATPMessages.getString("MW.STATUS.SQL_CONNECTION.FAILURE"));
    } catch (Exception exception) {
      exception.printStackTrace();
      this.lblSQL.setText(ATPMessages.getString("MW.STATUS.SQL_CONNECTION.UNKNOW_FAILURE"));
    } finally {
      try {
        if (!connection.isClosed())
          connection.close(); 
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } 
  }
}
