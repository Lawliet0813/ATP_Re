package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.Tools.UpperCaseAndMaxLenghtDocument;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.frmDriverEdit_cancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmDriverEdit_confirm_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmDriverEdit_txtDriverID_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmDriverEdit_txtDriverName_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmDriverEdit_txtHphone_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmDriverEdit_txtMail_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmDriverEdit_txtMphone_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmMain;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;

public class frmDriverEdit extends JDialog {
  JButton btnCancel = new JButton();
  
  JButton btnConfirm = new JButton();
  
  ConnectDB connDB = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  Vector driverData;
  
  String driverID = null;
  
  int flagno;
  
  boolean insert = false;
  
  JLabel jLabel10 = new JLabel();
  
  JLabel jLabel11 = new JLabel();
  
  JLabel jLabel12 = new JLabel();
  
  JLabel lblDriverAddress = new JLabel();
  
  JLabel lblDriverID = new JLabel();
  
  JLabel lblDriverMail = new JLabel();
  
  JLabel lblDriverName = new JLabel();
  
  JLabel lblDriverPhoneH = new JLabel();
  
  JLabel lblDriverPhoneM = new JLabel();
  
  JLabel lblDriverTitle = new JLabel();
  
  JLabel lblTitle = new JLabel();
  
  Document nameDoc = (Document)new UpperCaseAndMaxLenghtDocument(8);
  
  Document numberDoc = (Document)new UpperCaseAndMaxLenghtDocument(8);
  
  JPanel pnlData = new JPanel();
  
  JComboBox titleComboBox = new JComboBox();
  
  JTextField txtAddress = new JTextField();
  
  JTextField txtDriverID = new JTextField(8);
  
  JTextField txtDriverName = new JTextField(8);
  
  JTextField txtHphone = new JTextField();
  
  JTextField txtMail = new JTextField();
  
  JTextField txtMphone = new JTextField();
  
  boolean update = false;
  
  public frmDriverEdit(int paramInt, Vector paramVector, Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    this.update = true;
    this.driverData = paramVector;
    this.flagno = paramInt;
    this.driverID = ((Vector)paramVector.get(this.flagno)).get(0);
    try {
      init();
      this.lblTitle.setText(ATPMessages.getString("MW.DRIVER.MODIFY"));
      this.txtDriverID.setEditable(false);
      this.txtDriverName.setEditable(false);
      this.txtDriverName.setText(((Vector)paramVector.get(this.flagno)).get(1).toString());
      this.txtDriverID.setText(((Vector)paramVector.get(this.flagno)).get(0).toString());
      this.titleComboBox.setSelectedItem(((Vector)paramVector.get(this.flagno)).get(2).toString());
      this.txtHphone.setText(((Vector)paramVector.get(this.flagno)).get(3).toString());
      this.txtMphone.setText(((Vector)paramVector.get(this.flagno)).get(4).toString());
      this.txtMail.setText(((Vector)paramVector.get(this.flagno)).get(5).toString());
      pack();
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public frmDriverEdit(Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    this.insert = true;
    try {
      init();
      this.lblTitle.setText(ATPMessages.getString("MW.DRIVER.NEW"));
      pack();
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void cancel_actionPerformed(ActionEvent paramActionEvent) {
    if (this.update == true) {
      int i = JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.GNL.CANCEL.Q.CONFIRM"), ATPMessages.getString("MW.GNL.ASK"), 0, 3);
      if (i == 0)
        dispose(); 
    } else {
      dispose();
    } 
  }
  
  private boolean _$20946(int paramInt) {
    boolean bool = false;
    if (paramInt == 0) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.DRIVER.ID_EMPTY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      bool = false;
    } else if (paramInt < 6 && paramInt != 0) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.DRIVER.ID_MUST>6"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      bool = false;
    } else if (paramInt < 8 && paramInt >= 6) {
      int i = JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.DRIVER.ID.WARN.>6_<8"), ATPMessages.getString("MW.GNL.WARN"), 0, 2);
      if (i == 0) {
        bool = true;
      } else {
        bool = false;
      } 
    } else {
      bool = true;
    } 
    return bool;
  }
  
  private boolean _$20947(String paramString) {
    boolean bool = false;
    char[] arrayOfChar = { 
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
        'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', 
        '4', '5', '6', '7', '8', '9' };
    int i = paramString.length();
    for (byte b = 0; b < i; b++) {
      char c = Character.toUpperCase(paramString.charAt(b));
      for (byte b1 = 0; b1 < arrayOfChar.length; b1++) {
        if (c == arrayOfChar[b1]) {
          bool = true;
          break;
        } 
        bool = false;
      } 
      if (!bool)
        break; 
    } 
    return bool;
  }
  
  void confirm_actionPerformed(ActionEvent paramActionEvent) {
    try {
      if (this.insert)
        if (this.txtDriverName.getText().length() == 0) {
          JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.DRIVER.NAME_EMPTY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
          this.txtDriverName.selectAll();
          this.txtDriverName.requestFocusInWindow();
        } else if (!_$20946(this.txtDriverID.getText().length())) {
          this.txtDriverID.selectAll();
          this.txtDriverID.requestFocus(true);
        } else if (!_$20947(this.txtDriverID.getText())) {
          JOptionPane.showMessageDialog(this, "您所輸人的司機員員工編號格式錯誤,\n司機員員工編號只可輸人英文字母及阿拉伯數字,\n請重新輸入。", ATPMessages.getString("MW.GNL.ERROR"), 0);
          this.txtDriverID.selectAll();
          this.txtDriverID.requestFocus(true);
        } else {
          String str1 = "INSERT INTO Driver_INFO(Driver_ID,Name,Email_Address,Mphone,Title,Hphone) VALUES('" + this.txtDriverID.getText() + "','" + this.txtDriverName.getText() + "','" + this.txtMail.getText() + "','" + this.txtMphone.getText() + "','" + this.titleComboBox.getSelectedItem() + "','" + this.txtHphone.getText() + "')";
          this.connDB.Insert(str1);
          String str2 = this.lblDriverName.getText() + ":" + this.txtDriverID.getText() + "," + this.lblDriverID.getText() + ":" + this.txtDriverName.getText() + "," + this.lblDriverTitle.getText() + ":" + this.titleComboBox.getSelectedItem() + "," + this.lblDriverPhoneM.getText() + ":" + this.txtHphone.getText() + "," + this.lblDriverPhoneH.getText() + ":" + this.txtMphone.getText() + "," + this.lblDriverMail.getText() + ":" + this.txtMail.getText();
          this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "司機員", "新增", str2);
          JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.DRIVER.NEW.OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
          dispose();
        }  
      if (this.update) {
        String str1 = "UPDATE Driver_INFO SET Name='" + this.txtDriverName.getText() + "',Driver_ID='" + this.txtDriverID.getText() + "',Email_Address='" + this.txtMail.getText() + "',Mphone='" + this.txtMphone.getText() + "',Title='" + this.titleComboBox.getSelectedItem() + "',Hphone='" + this.txtHphone.getText() + "' WHERE Driver_ID='" + this.driverID + "'";
        this.connDB.Update(str1);
        String str2 = this.lblDriverName.getText() + ":" + this.txtDriverID.getText() + "," + this.lblDriverID.getText() + ":" + this.txtDriverName.getText() + "," + this.lblDriverTitle.getText() + ":" + this.titleComboBox.getSelectedItem() + "," + this.lblDriverPhoneM.getText() + ":" + this.txtHphone.getText() + "," + this.lblDriverPhoneH.getText() + ":" + this.txtMphone.getText() + "," + this.lblDriverMail.getText() + ":" + this.txtMail.getText();
        this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "司機員", "修改", str2);
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.DRIVER.MODIFY.OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
        dispose();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void init() throws Exception {
    this.lblTitle.setFont(new Font("Dialog", 1, 16));
    this.lblTitle.setBorder(BorderFactory.createEtchedBorder());
    this.lblTitle.setDebugGraphicsOptions(0);
    getContentPane().setLayout(new BorderLayout());
    this.pnlData.setLayout(new GridBagLayout());
    this.lblDriverName.setText(ATPMessages.getString("MW.DRIVER.NAME"));
    this.lblDriverID.setText(ATPMessages.getString("MW.DRIVER.ID"));
    this.lblDriverTitle.setText(ATPMessages.getString("MW.DRIVER.TITLE"));
    this.lblDriverPhoneM.setRequestFocusEnabled(true);
    this.lblDriverPhoneM.setText(ATPMessages.getString("MW.DRIVER.PHONE_MOBILE"));
    this.lblDriverPhoneH.setText(ATPMessages.getString("MW.DRIVER.PHONE_HOME"));
    this.lblDriverMail.setEnabled(true);
    this.lblDriverMail.setText(ATPMessages.getString("MW.DRIVER.EMAIL"));
    this.lblDriverAddress.setFont(new Font("", 0, 12));
    this.lblDriverAddress.setText(ATPMessages.getString("MW.DriverAddress"));
    this.txtMphone.setText("");
    this.txtMphone.addActionListener((ActionListener)new frmDriverEdit_txtMphone_actionAdapter(this));
    this.txtDriverName.setDocument(this.nameDoc);
    this.txtDriverName.addActionListener((ActionListener)new frmDriverEdit_txtDriverName_actionAdapter(this));
    this.txtDriverID.setEditable(true);
    this.txtDriverID.setText("");
    this.txtDriverID.setDocument(this.numberDoc);
    this.txtDriverID.addActionListener((ActionListener)new frmDriverEdit_txtDriverID_actionAdapter(this));
    this.txtHphone.setText("");
    this.txtHphone.addActionListener((ActionListener)new frmDriverEdit_txtHphone_actionAdapter(this));
    this.txtMail.setText("");
    this.txtMail.addActionListener((ActionListener)new frmDriverEdit_txtMail_actionAdapter(this));
    this.txtAddress.setText("");
    this.btnConfirm.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.btnConfirm.addActionListener((ActionListener)new frmDriverEdit_confirm_actionAdapter(this));
    this.btnCancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.btnCancel.addActionListener((ActionListener)new frmDriverEdit_cancel_actionAdapter(this));
    setResizable(false);
    this.jLabel10.setForeground(Color.red);
    this.jLabel10.setText(ATPMessages.getString("MW.GNL.MUST_INPUT"));
    this.jLabel11.setText(ATPMessages.getString("MW.GNL.MUST_INPUT"));
    this.jLabel11.setForeground(Color.red);
    this.jLabel12.setText(ATPMessages.getString("MW.GNL.MUST_INPUT"));
    this.jLabel12.setForeground(Color.red);
    this.titleComboBox.setEditable(true);
    this.pnlData.add(this.lblDriverName, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(10, 30, 4, 4), 0, 0));
    this.pnlData.add(this.txtDriverName, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.pnlData.add(this.jLabel10, new GridBagConstraints(3, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 4, 4), 0, 0));
    this.pnlData.add(this.lblDriverID, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.pnlData.add(this.txtDriverID, new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.pnlData.add(this.jLabel11, new GridBagConstraints(3, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 4, 4), 0, 0));
    this.pnlData.add(this.lblDriverTitle, new GridBagConstraints(1, 3, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.pnlData.add(this.titleComboBox, new GridBagConstraints(2, 3, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.pnlData.add(this.jLabel12, new GridBagConstraints(3, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 4, 4), 0, 0));
    this.pnlData.add(this.lblDriverPhoneM, new GridBagConstraints(1, 4, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.pnlData.add(this.txtMphone, new GridBagConstraints(2, 4, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.pnlData.add(this.lblDriverPhoneH, new GridBagConstraints(1, 5, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.pnlData.add(this.txtHphone, new GridBagConstraints(2, 5, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.pnlData.add(this.lblDriverMail, new GridBagConstraints(1, 6, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.pnlData.add(this.txtMail, new GridBagConstraints(2, 6, 3, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.pnlData.add(this.lblDriverAddress, new GridBagConstraints(1, 7, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.pnlData.add(this.txtAddress, new GridBagConstraints(2, 7, 3, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.pnlData.add(this.btnConfirm, new GridBagConstraints(3, 8, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.pnlData.add(this.btnCancel, new GridBagConstraints(4, 8, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 4, 4), 0, 0));
    getContentPane().add(this.lblTitle, "North");
    getContentPane().add(this.pnlData, "Center");
    this.lblDriverAddress.setVisible(false);
    this.txtAddress.setVisible(false);
    this.titleComboBox.addItem("機車長");
    this.titleComboBox.addItem("司機員");
    this.titleComboBox.addItem("機車助理");
    this.titleComboBox.addItem("輔助司機員");
    this.titleComboBox.addItem("學習司機員");
    this.titleComboBox.addItem("學習機車助理");
  }
  
  void txtDriverID_actionPerformed(ActionEvent paramActionEvent) {
    if (_$20946(this.txtDriverID.getText().length())) {
      ((Component)paramActionEvent.getSource()).transferFocus();
    } else {
      this.txtDriverID.selectAll();
      this.txtDriverID.requestFocusInWindow();
    } 
  }
  
  void txtDriverName_actionPerformed(ActionEvent paramActionEvent) {
    if (this.txtDriverName.getText().length() == 0) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.DRIVER.NAME_EMPTY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      ((Component)paramActionEvent.getSource()).transferFocus();
    } 
  }
  
  void txtHphone_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtMail_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtMphone_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
}
