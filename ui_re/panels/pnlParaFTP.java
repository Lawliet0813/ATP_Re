package ui.panels;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.connect.ConnectFTP;
import com.MiTAC.TRA.ATP.ui.adapters.pnlParaFTP_btnTestCon_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlParaFTP_txtField_focusAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlParaFTP_txtField_keyAdapter;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class pnlParaFTP extends JPanel {
  private String _$17663 = "failure";
  
  private int _$17664 = -1;
  
  private String _$17666 = "failure";
  
  private String _$17665 = "failure";
  
  JButton btnTestCon = new JButton();
  
  boolean changes = false;
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
  JLabel jLabel4 = new JLabel();
  
  JLabel jLabel5 = new JLabel();
  
  private String _$17667 = "failure";
  
  private int _$17668 = -1;
  
  private String _$17670 = "failure";
  
  private String _$17669 = "failure";
  
  JTextField txtFTPIP = new JTextField();
  
  JTextField txtFTPPort = new JTextField();
  
  JPasswordField txtFTPPwd = new JPasswordField();
  
  JTextField txtFTPUser = new JTextField();
  
  XYLayout xYLayout1 = new XYLayout();
  
  public pnlParaFTP() {
    try {
      _$3120();
      callbackValue();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnTestCon_actionPerformed(ActionEvent paramActionEvent) {
    try {
      ConnectFTP connectFTP = new ConnectFTP();
      connectFTP.testConnectFTP(this._$17667, this._$17668, this._$17669, this._$17670);
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.PARA.FTP.TEST_CONN.OK") + "\n" + ATPMessages.getString("MW.PARA.FTP.IP") + this._$17667 + ATPMessages.getString("MW.PARA.FTP.PORT") + this._$17668 + ATPMessages.getString("MW.PARA.FTP.USER") + this._$17669, ATPMessages.getString("MW.GNL.SUCCESS"), 1);
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  protected void callbackValue() {
    this._$17663 = InitParameters.FTPHostIP;
    this._$17664 = InitParameters.FTPPort;
    this._$17665 = InitParameters.FTPUserName;
    this._$17666 = InitParameters.FTPUserPWD;
    this.txtFTPIP.setText(this._$17663);
    this.txtFTPPort.setText("" + this._$17664);
    this.txtFTPUser.setText(this._$17665);
    this.txtFTPPwd.setText(this._$17666);
    this._$17667 = this._$17663;
    this._$17668 = this._$17664;
    this._$17669 = this._$17665;
    this._$17670 = this._$17666;
  }
  
  public Object[] getValues() {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = this._$17667;
    arrayOfObject[1] = new Integer(this._$17668);
    arrayOfObject[2] = this._$17669;
    arrayOfObject[3] = this._$17670;
    return arrayOfObject;
  }
  
  private void _$3120() throws Exception {
    this.jLabel1.setText(ATPMessages.getString("MW.PARA.FTP.IP"));
    setLayout((LayoutManager)this.xYLayout1);
    setMaximumSize(new Dimension(2147483647, 2147483647));
    setMinimumSize(new Dimension(404, 418));
    setPreferredSize(new Dimension(404, 418));
    this.jLabel2.setText(ATPMessages.getString("MW.PARA.FTP.PORT"));
    this.txtFTPPort.setNextFocusableComponent(this.txtFTPUser);
    this.txtFTPPort.setText("jTextField1");
    this.jLabel3.setText(ATPMessages.getString("MW.PARA.FTP.USER"));
    this.txtFTPUser.setNextFocusableComponent(this.txtFTPPwd);
    this.txtFTPUser.setText("jTextField2");
    this.jLabel4.setText(ATPMessages.getString("MW.PARA.FTP.PWD"));
    this.btnTestCon.setText(ATPMessages.getString("MW.PARA.FTP.TEST_CONN"));
    this.txtFTPIP.setNextFocusableComponent(this.txtFTPPort);
    this.txtFTPIP.setText("jTextField3");
    this.txtFTPIP.addFocusListener((FocusListener)new pnlParaFTP_txtField_focusAdapter(this));
    this.txtFTPPort.addFocusListener((FocusListener)new pnlParaFTP_txtField_focusAdapter(this));
    this.txtFTPUser.addFocusListener((FocusListener)new pnlParaFTP_txtField_focusAdapter(this));
    this.txtFTPPwd.addFocusListener((FocusListener)new pnlParaFTP_txtField_focusAdapter(this));
    this.txtFTPIP.addKeyListener((KeyListener)new pnlParaFTP_txtField_keyAdapter(this));
    this.txtFTPPort.addKeyListener((KeyListener)new pnlParaFTP_txtField_keyAdapter(this));
    this.txtFTPUser.addKeyListener((KeyListener)new pnlParaFTP_txtField_keyAdapter(this));
    this.txtFTPPwd.addKeyListener((KeyListener)new pnlParaFTP_txtField_keyAdapter(this));
    this.txtFTPPwd.setNextFocusableComponent(this.btnTestCon);
    this.txtFTPPwd.setText("jPasswordField1");
    this.jLabel5.setFont(new Font("Dialog", 1, 14));
    this.jLabel5.setText(ATPMessages.getString("MW.PARA.FTP.SETTING"));
    this.xYLayout1.setWidth(391);
    this.xYLayout1.setHeight(423);
    add(this.jLabel1, new XYConstraints(49, 48, -1, -1));
    add(this.jLabel2, new XYConstraints(50, 90, -1, -1));
    add(this.jLabel4, new XYConstraints(50, 170, -1, -1));
    add(this.jLabel3, new XYConstraints(50, 130, -1, -1));
    add(this.txtFTPIP, new XYConstraints(120, 50, 155, -1));
    add(this.txtFTPPort, new XYConstraints(120, 90, 50, -1));
    add(this.txtFTPUser, new XYConstraints(120, 130, 100, -1));
    add(this.txtFTPPwd, new XYConstraints(120, 170, 100, -1));
    add(this.jLabel5, new XYConstraints(33, 11, -1, -1));
    add(this.btnTestCon, new XYConstraints(50, 210, -1, -1));
    this.btnTestCon.addActionListener((ActionListener)new pnlParaFTP_btnTestCon_actionAdapter(this));
  }
  
  public boolean isChanged() {
    return this.changes;
  }
  
  private void _$8475() {
    this.changes = true;
    this._$17667 = this.txtFTPIP.getText();
    this._$17668 = (new Integer(this.txtFTPPort.getText())).intValue();
    this._$17669 = this.txtFTPUser.getText();
    this._$17670 = this.txtFTPPwd.getText();
  }
  
  void txtField_focusLost(FocusEvent paramFocusEvent) {
    _$8475();
  }
  
  void txtField_keyPressed(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyCode() == 10) {
      _$8475();
      ((Component)paramKeyEvent.getSource()).transferFocus();
    } 
  }
}
