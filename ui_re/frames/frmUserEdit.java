package ui.frames;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CheckUser;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.Tools.PasswordInputDialog;
import com.MiTAC.TRA.ATP.Tools.UpperCaseAndMaxLenghtDocument;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.frames.frmMain;
import com.MiTAC.TRA.ATP.ui.adapters.frmUserEdit_btnEditPwd_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.frmUserEdit_cancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.frmUserEdit_checkPassword_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.frmUserEdit_commit_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmUserEdit_jComboBox1_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.frmUserEdit_password_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.frmUserEdit_txtUserID_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.frmUserEdit_txtUserName_actionAdapter;
import com.MiTAC.Tools.MD5.MD5;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.Document;

public class frmUserEdit extends JDialog {
  JButton btnCancel = new JButton();
  
  JButton btnCommit = new JButton();
  
  JButton btnEditPwd = new JButton();
  
  ButtonGroup buttonGroup1 = new ButtonGroup();
  
  JPasswordField checkPassword = new JPasswordField();
  
  CheckUser checkUser;
  
  ConnectDB connDB = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  boolean editPassword;
  
  boolean insert;
  
  JComboBox jComboBox1 = new JComboBox();
  
  JLabel jLabel7 = new JLabel();
  
  JLabel jLabel8 = new JLabel();
  
  JPanel jPanel1 = new JPanel();
  
  JLabel lblPWDConfirm = new JLabel();
  
  JLabel lblTitle = new JLabel();
  
  JLabel lblUserID = new JLabel();
  
  JLabel lblUserName = new JLabel();
  
  JLabel lblUserPWD = new JLabel();
  
  JLabel lblUserPriority = new JLabel();
  
  Document nameDoc = (Document)new UpperCaseAndMaxLenghtDocument(8);
  
  Document numberDoc = (Document)new UpperCaseAndMaxLenghtDocument(8);
  
  JPasswordField password = new JPasswordField();
  
  Vector pwd;
  
  PasswordInputDialog pwdInputDialog;
  
  JTextField txtUserID = new JTextField();
  
  JTextField txtUserName = new JTextField();
  
  boolean update;
  
  String user_ID;
  
  public frmUserEdit(Vector paramVector1, Vector paramVector2, Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    this.pwd = paramVector2;
    this.user_ID = paramVector1.get(0);
    try {
      this.update = true;
      jbInit();
      pack();
      this.txtUserID.setEditable(false);
      this.txtUserName.setEditable(false);
      this.lblUserPWD.setVisible(false);
      this.password.setVisible(false);
      this.lblPWDConfirm.setVisible(false);
      this.checkPassword.setVisible(false);
      setTitle(ATPMessages.getString("MW.USER.MODIFY_USER"));
      this.lblTitle.setText(ATPMessages.getString("MW.USER.MODIFY_USER"));
      this.txtUserID.setText(paramVector1.get(0));
      this.txtUserName.setText(paramVector1.get(1));
      _$3120();
      this.jComboBox1.setSelectedItem(paramVector1.get(2).toString());
      if (frmMain.getPriority() == 3) {
        this.jComboBox1.setEnabled(true);
      } else {
        this.jComboBox1.setEnabled(false);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public frmUserEdit(Vector paramVector, Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    try {
      this.insert = true;
      jbInit();
      _$3120();
      pack();
      setTitle(ATPMessages.getString("MW.USER.NEW_USER"));
      this.lblTitle.setFont(new Font("Dialog", 1, 16));
      this.lblTitle.setText(ATPMessages.getString("MW.USER.NEW_USER"));
      this.lblUserPWD.setVisible(false);
      this.password.setText("12345");
      this.password.setVisible(false);
      this.lblPWDConfirm.setVisible(false);
      this.checkPassword.setVisible(false);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnEditPwd_actionPerformed(ActionEvent paramActionEvent) {
    this.pwdInputDialog = new PasswordInputDialog((Frame)getOwner(), "", true);
    this.pwdInputDialog.show();
    if (this.pwdInputDialog.getPassword().length() != 0) {
      this.checkUser = new CheckUser(frmMain.getUserID(), this.pwdInputDialog.getPassword());
      if (this.checkUser.isPasswordRight()) {
        this.editPassword = true;
        if (this.insert) {
          this.lblUserPWD.setVisible(true);
          this.password.setVisible(true);
          this.password.setText("");
          this.lblPWDConfirm.setVisible(true);
          this.checkPassword.setVisible(true);
          this.btnEditPwd.setVisible(false);
        } else {
          this.lblUserPWD.setVisible(true);
          this.password.setVisible(true);
          this.password.setText("");
          this.lblPWDConfirm.setVisible(true);
          this.checkPassword.setVisible(true);
          this.password.setSelectionStart(0);
          this.password.setSelectionEnd(0);
          this.btnEditPwd.setVisible(false);
        } 
      } else {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.PWD.PWD_ERROR"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      } 
    } 
  }
  
  void cancel_actionPerformed(ActionEvent paramActionEvent) {
    if (this.update == true) {
      int i = JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.LOGIN.MODIFY.Q.CANCEL"), ATPMessages.getString("MW.GNL.ASK"), 0, 3);
      if (i == 0)
        dispose(); 
    } else {
      dispose();
    } 
  }
  
  void checkPassword_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void commit_actionPerformed(ActionEvent paramActionEvent) {
    try {
      String str1 = new String(this.password.getPassword());
      String str2 = new String(this.checkPassword.getPassword());
      if (this.insert) {
        if (this.txtUserID.getText().length() == 0) {
          JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.USER.USERID_CANT_EMPTY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
          this.txtUserID.selectAll();
          this.txtUserID.requestFocusInWindow();
        } else if (this.txtUserName.getText().length() == 0) {
          JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.USER.USERNAME_CANT_EMPTY"), ATPMessages.getString("MW.GNL_ERROR"), 0);
          this.txtUserName.selectAll();
          this.txtUserName.requestFocusInWindow();
        } else if (this.editPassword) {
          if (str1.equals(str2)) {
            MD5 mD5 = new MD5(new String(this.password.getPassword()));
            String str = "INSERT INTO MWUser(User_ID,UserName,Password,Priority) VALUES('" + this.txtUserID.getText() + "','" + this.txtUserName.getText() + "','" + mD5.getHexString() + "','" + this.jComboBox1.getSelectedIndex() + "')";
            this.connDB.Insert(str);
            this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), "管理電腦", "使用者", "新增", this.lblUserID.getText() + ":" + this.txtUserID.getText() + "," + this.lblUserName.getText() + ":" + this.txtUserName.getText() + "," + this.lblUserPriority.getText() + ":" + this.jComboBox1.getSelectedItem());
            dispose();
          } else {
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.USER.PWD_CHECK_ERROR"), ATPMessages.getString("MW.GNL.ERROR"), 0);
            this.checkPassword.selectAll();
            this.checkPassword.requestFocusInWindow();
          } 
        } else {
          MD5 mD5 = new MD5(new String(this.password.getPassword()));
          String str = "INSERT INTO MWUser(User_ID,UserName,Password,Priority) VALUES('" + this.txtUserID.getText() + "','" + this.txtUserName.getText() + "','" + mD5.getHexString() + "','" + this.jComboBox1.getSelectedIndex() + "')";
          this.connDB.Insert(str);
          this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), "管理電腦", "使用者", "新增", this.lblUserID.getText() + ":" + this.txtUserID.getText() + "," + this.lblUserName.getText() + ":" + this.txtUserName.getText() + "," + this.lblUserPriority.getText() + ":" + this.jComboBox1.getSelectedItem());
          dispose();
        } 
      } else if (this.editPassword) {
        String str = new String(this.password.getPassword());
        if (str1.equals(str2)) {
          MD5 mD5 = new MD5(str);
          String str3 = "UPDATE MWUser SET User_ID = '" + this.txtUserID.getText() + "',UserName = '" + this.txtUserName.getText() + "',Password = '" + mD5.getHexString() + "',Priority = '" + this.jComboBox1.getSelectedIndex() + "' WHERE User_ID = '" + this.user_ID + "'";
          this.connDB.Update(str3);
          this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), "管理電腦", "使用者", "修改", this.lblUserID.getText() + ":" + this.txtUserID.getText() + "," + this.lblUserName.getText() + ":" + this.txtUserName.getText() + "," + this.lblUserPriority.getText() + ":" + this.jComboBox1.getSelectedItem() + "," + this.lblUserPWD.getText() + ":" + str);
          dispose();
        } else {
          JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.USER.PWD_CHECK_ERROR"), ATPMessages.getString("MW.GNL.ERROR"), 0);
          this.checkPassword.selectAll();
          this.checkPassword.requestFocusInWindow();
        } 
      } else {
        String str3 = this.pwd.get(0);
        String str4 = "UPDATE MWUser SET User_ID = '" + this.txtUserID.getText() + "',UserName = '" + this.txtUserName.getText() + "',Priority = '" + this.jComboBox1.getSelectedIndex() + "' WHERE User_ID = '" + this.user_ID + "'";
        this.connDB.Update(str4);
        this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), "管理電腦", "使用者", "修改", this.lblUserID.getText() + ":" + this.txtUserID.getText() + "," + this.lblUserName.getText() + ":" + this.txtUserName.getText() + "," + this.lblUserPriority.getText() + ":" + this.jComboBox1.getSelectedItem() + ",");
        dispose();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$3120() {
    try {
      String str = "SELECT UserClass, Priority FROM MWUserClass ORDER BY Priority ASC";
      Vector vector = this.connDB.getData(str);
      for (byte b = 0; b < vector.size(); b++)
        this.jComboBox1.addItem(((Vector)vector.get(b)).get(0).toString()); 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  boolean isPasswordRight() {
    return (new String(this.password.getPassword())).equals(new String(this.checkPassword.getPassword()));
  }
  
  void jComboBox1_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void jbInit() throws Exception {
    this.lblTitle.setBorder(BorderFactory.createEtchedBorder());
    getContentPane().setLayout(new BorderLayout());
    this.btnCommit.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.btnCommit.addActionListener((ActionListener)new frmUserEdit_commit_actionAdapter(this));
    this.jPanel1.setLayout(new GridBagLayout());
    this.lblUserID.setText(ATPMessages.getString("MW.LOGIN.USERID"));
    this.txtUserID.setText("");
    this.txtUserID.setDocument(this.numberDoc);
    this.txtUserID.addActionListener((ActionListener)new frmUserEdit_txtUserID_actionAdapter(this));
    this.lblUserPWD.setText(ATPMessages.getString("MW.LOGIN.USERPWD"));
    this.lblPWDConfirm.setText(ATPMessages.getString("MW.USER.CONFIRM_PWD"));
    this.btnEditPwd.setText(ATPMessages.getString("MW.USER.MODIFY_PWD"));
    this.btnEditPwd.addActionListener((ActionListener)new frmUserEdit_btnEditPwd_actionAdapter(this));
    this.lblUserName.setText(ATPMessages.getString("MW.LOGIN.USERNAME"));
    this.txtUserName.setSelectionStart(11);
    this.txtUserName.setText("");
    this.txtUserName.setDocument(this.nameDoc);
    this.txtUserName.addActionListener((ActionListener)new frmUserEdit_txtUserName_actionAdapter(this));
    this.btnCancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.btnCancel.addActionListener((ActionListener)new frmUserEdit_cancel_actionAdapter(this));
    this.lblUserPriority.setText(ATPMessages.getString("MW.LOGIN.USERPRIORITY"));
    this.password.setText("");
    this.password.addActionListener((ActionListener)new frmUserEdit_password_actionAdapter(this));
    this.checkPassword.setText("");
    this.checkPassword.addActionListener((ActionListener)new frmUserEdit_checkPassword_actionAdapter(this));
    this.jComboBox1.addActionListener((ActionListener)new frmUserEdit_jComboBox1_actionAdapter(this));
    this.jLabel7.setText(ATPMessages.getString("MW.GNL.MUST_INPUT"));
    this.jLabel7.setForeground(Color.red);
    this.jLabel8.setText(ATPMessages.getString("MW.GNL.MUST_INPUT"));
    this.jLabel8.setForeground(Color.red);
    getContentPane().add(this.lblTitle, "North");
    getContentPane().add(this.jPanel1, "Center");
    this.jPanel1.add(this.lblUserID, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.jPanel1.add(this.txtUserID, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 2, 2), 0, 0));
    this.jPanel1.add(this.jLabel7, new GridBagConstraints(3, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 2, 2), 0, 0));
    this.jPanel1.add(this.lblUserName, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.jPanel1.add(this.txtUserName, new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 2, 2), 0, 0));
    this.jPanel1.add(this.jLabel8, new GridBagConstraints(3, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 2, 2), 0, 0));
    this.jPanel1.add(this.lblUserPriority, new GridBagConstraints(1, 3, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.jPanel1.add(this.jComboBox1, new GridBagConstraints(2, 3, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 2, 2), 0, 0));
    this.jPanel1.add(this.lblUserPWD, new GridBagConstraints(1, 4, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.jPanel1.add(this.password, new GridBagConstraints(2, 4, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 2, 2), 0, 0));
    this.jPanel1.add(this.lblPWDConfirm, new GridBagConstraints(1, 5, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 2, 2), 0, 0));
    this.jPanel1.add(this.checkPassword, new GridBagConstraints(2, 5, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 2, 2), 0, 0));
    this.jPanel1.add(this.btnEditPwd, new GridBagConstraints(2, 6, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 2, 2), 0, 0));
    this.jPanel1.add(this.btnCommit, new GridBagConstraints(3, 7, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 2, 2), 0, 0));
    this.jPanel1.add(this.btnCancel, new GridBagConstraints(4, 7, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 2, 2), 0, 0));
    setSize(new Dimension(500, 400));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
  
  void password_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtUserID_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtUserName_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
}
