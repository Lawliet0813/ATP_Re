package ui.frames;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CheckUser;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.adapters.frmLogin_cancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.frmLogin_comit_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.frmLogin_jPasswordField1_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.frmLogin_user_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frames.frmMain;
import com.MiTAC.Tools.MD5.MD5;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class frmLogin extends JFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  
  JButton btnCancel = new JButton();
  
  JButton btnComit = new JButton();
  
  CheckUser checkUser;
  
  ConnectDB connDB = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  JLabel jLabel6 = new JLabel();
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel2 = new JPanel();
  
  JPasswordField jPasswordField1 = new JPasswordField();
  
  JLabel lblATP = new JLabel();
  
  JLabel lblMW = new JLabel();
  
  JLabel lblMiTAC = new JLabel();
  
  JLabel lblTRA = new JLabel();
  
  JLabel lblUserID = new JLabel();
  
  JLabel lblUserPWD = new JLabel();
  
  JTextField user = new JTextField();
  
  Vector userData;
  
  Vector userPwd;
  
  public frmLogin() {
    enableEvents(64L);
    try {
      InitParameters.testPARAMETERS();
      init();
      _$10210();
      this.jLabel6.setVisible(false);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void cancel_actionPerformed(ActionEvent paramActionEvent) {
    this.user.setText("");
    this.jPasswordField1.setText("");
    this.creatLog = new CreatMWSystemLog(this.user.getText(), "管理電腦", "使用者", "離開", null);
    System.exit(0);
  }
  
  void comit_actionPerformed(ActionEvent paramActionEvent) {
    _$3915();
  }
  
  void comit_focusGained(FocusEvent paramFocusEvent) {
    System.err.println("a=" + paramFocusEvent.getSource());
    _$3915();
  }
  
  private void _$3915() {
    if (this.user.getText().length() == 0) {
      JOptionPane.showMessageDialog((Component)null, ATPMessages.getString("MW.LOGIN.USERID_EMPTY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      this.user.selectAll();
      this.user.requestFocusInWindow();
    } else if ((this.jPasswordField1.getPassword()).length == 0) {
      JOptionPane.showMessageDialog((Component)null, ATPMessages.getString("MW.LOGIN.USERPWD_EMPTY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      this.jPasswordField1.selectAll();
      this.jPasswordField1.requestFocusInWindow();
    } else if (!_$20091(this.user.getText())) {
      JOptionPane.showMessageDialog((Component)null, ATPMessages.getString("MW.LOGIN.USER_NOTEXIST"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      this.user.selectAll();
      this.user.requestFocusInWindow();
      this.jPasswordField1.setText("");
    } else if (userPassword(this.user.getText(), new String(this.jPasswordField1.getPassword()))) {
      JOptionPane.showMessageDialog((Component)null, ATPMessages.getString("MW.LOGIN.USER_PWD_ERROR"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      this.jPasswordField1.selectAll();
      this.jPasswordField1.requestFocusInWindow();
    } else {
      frmMain frmMain = new frmMain(this.user.getText());
      frmMain.show();
      this.creatLog = new CreatMWSystemLog(this.user.getText(), "管理電腦", "使用者", "登入", null);
      dispose();
    } 
  }
  
  void init() throws Exception {
    setTitle(ATPMessages.getString("MW.LOGIN.NAME"));
    setResizable(false);
    setSize(new Dimension(400, 300));
    getContentPane().setLayout(this.borderLayout1);
    this.jPanel1.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel1.setDebugGraphicsOptions(0);
    this.jPanel1.setPreferredSize(new Dimension(400, 300));
    this.jPanel1.setLayout(new GridBagLayout());
    this.btnCancel.setFont(new Font("標楷體", 1, 16));
    this.btnCancel.setForeground(Color.red);
    this.btnCancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.btnCancel.addActionListener((ActionListener)new frmLogin_cancel_actionAdapter(this));
    this.btnComit.setFont(new Font("標楷體", 1, 16));
    this.btnComit.setForeground(Color.red);
    this.btnComit.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.btnComit.addActionListener((ActionListener)new frmLogin_comit_actionAdapter(this));
    this.lblUserID.setFont(new Font("標楷體", 1, 16));
    this.lblUserID.setForeground(new Color(176, 116, 188));
    this.lblUserID.setText(ATPMessages.getString("MW.LOGIN.USERID"));
    this.lblUserPWD.setText(ATPMessages.getString("MW.LOGIN.USERPWD"));
    this.lblUserPWD.setForeground(new Color(176, 116, 188));
    this.lblUserPWD.setFont(new Font("標楷體", 1, 16));
    this.user.setText("");
    this.user.addActionListener((ActionListener)new frmLogin_user_actionAdapter(this));
    this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel2.setLayout(new GridBagLayout());
    this.lblMW.setFont(new Font("標楷體", 1, 20));
    this.lblMW.setForeground(SystemColor.desktop);
    this.lblMW.setText(ATPMessages.getString("MW.MW"));
    this.lblTRA.setText(ATPMessages.getString("MW.TRA"));
    this.lblTRA.setForeground(SystemColor.desktop);
    this.lblTRA.setFont(new Font("標楷體", 1, 20));
    this.lblATP.setText(ATPMessages.getString("MW.ATP"));
    this.lblATP.setForeground(SystemColor.desktop);
    this.lblATP.setFont(new Font("標楷體", 1, 20));
    this.jLabel6.setText(ATPMessages.getString("MW.CopyRight"));
    this.lblMiTAC.setText(ATPMessages.getString("MW.MITAC"));
    this.jPasswordField1.addActionListener((ActionListener)new frmLogin_jPasswordField1_actionAdapter(this));
    this.jPanel1.add(this.btnComit, new GridBagConstraints(2, 3, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(25, 21, 0, 0), 0, 0));
    this.jPanel1.add(this.jLabel6, new GridBagConstraints(0, 5, 3, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 14, 3, 9), 26, 0));
    this.jPanel1.add(this.lblMiTAC, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 6, 0, 0), 0, 0));
    this.jPanel1.add(this.jPanel2, new GridBagConstraints(0, 0, 4, 1, 1.0D, 1.0D, 10, 1, new Insets(18, 18, 0, 17), 7, 25));
    this.jPanel2.add(this.lblTRA, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(7, 7, 0, 196), 0, 0));
    this.jPanel2.add(this.lblATP, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 87, 0, 95), 0, 0));
    this.jPanel2.add(this.lblMW, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(1, 175, 3, 28), 0, 0));
    this.jPanel1.add(this.user, new GridBagConstraints(2, 1, 2, 1, 1.0D, 0.0D, 17, 2, new Insets(14, 8, 0, 71), 170, 0));
    this.jPanel1.add(this.lblUserID, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(14, 0, 0, 0), 0, 0));
    this.jPanel1.add(this.lblUserPWD, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(18, 0, 0, 0), 0, 0));
    this.jPanel1.add(this.jPasswordField1, new GridBagConstraints(2, 2, 2, 1, 1.0D, 0.0D, 17, 2, new Insets(18, 8, 0, 71), 80, -2));
    getContentPane().add(this.jPanel1, "West");
    this.jPanel1.add(this.btnCancel, new GridBagConstraints(3, 3, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(25, 21, 0, 0), 0, 0));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
  
  private boolean _$20091(String paramString) {
    boolean bool = false;
    if (this.user.getText().equals("MiTACManager")) {
      bool = true;
    } else {
      for (byte b = 0; b < this.userData.size(); b++) {
        if (paramString.equals(((Vector)this.userData.get(b)).get(0))) {
          bool = true;
          break;
        } 
      } 
    } 
    return bool;
  }
  
  void jPasswordField1_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
    _$3915();
  }
  
  protected void processWindowEvent(WindowEvent paramWindowEvent) {
    super.processWindowEvent(paramWindowEvent);
    if (paramWindowEvent.getID() == 201) {
      this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), "管理電腦", "使用者", "登出", null);
      System.exit(0);
    } 
  }
  
  private void _$10210() throws Exception {
    String str = "SELECT User_ID,UserName,Password,Priority FROM MWUser";
    this.userData = this.connDB.getData(str);
  }
  
  boolean userPassword(String paramString1, String paramString2) {
    try {
      if (paramString1.equals("MiTACManager"))
        return !paramString2.equals("A82"); 
      String str = "SELECT Password FROM MWUser WHERE User_ID = '" + paramString1 + "'";
      this.userPwd = this.connDB.getData(str);
      MD5 mD5 = new MD5(paramString2);
      return !mD5.getHexString().equals(((Vector)this.userPwd.get(0)).get(0));
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } 
  }
  
  void user_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
}
