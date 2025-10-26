package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.DiskTools.CopySmbDir;
import com.MiTAC.TRA.ATP.Tools.DiskTools.DiskStatus;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.ui.pnlParaPath_btnMO_actionAdapter;
import com.MiTAC.TRA.ATP.ui.pnlParaPath_btnMW_actionAdapter;
import com.MiTAC.TRA.ATP.ui.pnlParaPath_btnTestCon_actionAdapter;
import com.MiTAC.TRA.ATP.ui.pnlParaPath_btnUSB_actionAdapter;
import com.MiTAC.TRA.ATP.ui.pnlParaPath_txtPathField_focusAdapter;
import com.MiTAC.TRA.ATP.ui.pnlParaPath_txtPathField_keyAdapter;
import com.MiTAC.TRA.ATP.ui.pnlParaPath_txtSMBPathField_keyAdapter;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import java.awt.Color;
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
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class pnlParaPath extends JPanel implements ActionListener {
  String MO_PATH = "failure";
  
  String MW_PATH = "failure";
  
  boolean MW_PATH_SMB = false;
  
  String MW_SMB_PWD = "failure";
  
  String MW_SMB_Path = "failure";
  
  String MW_SMB_Path_folder = "failure";
  
  String MW_SMB_Path_host = "failure";
  
  String MW_SMB_USER = "failure";
  
  String USB_PATH = "failure";
  
  ButtonGroup bgroup = new ButtonGroup();
  
  JButton btnMO = new JButton();
  
  JButton btnMW = new JButton();
  
  JButton btnTestCon = new JButton();
  
  JButton btnUSB = new JButton();
  
  boolean changes = false;
  
  JLabel jLabel5 = new JLabel();
  
  JFileChooser jfc;
  
  JLabel lblLocalPath = new JLabel();
  
  JLabel lblMOPath = new JLabel();
  
  JLabel lblMWPath = new JLabel();
  
  JLabel lblMWSmbPWD = new JLabel();
  
  JLabel lblMWSmbPath = new JLabel();
  
  JLabel lblMWSmbSlash = new JLabel("/");
  
  JLabel lblMWSmbSymb = new JLabel("SMB://");
  
  JLabel lblMWSmbUser = new JLabel();
  
  JLabel lblUSBPath = new JLabel();
  
  String new_MO_PATH = "failure";
  
  String new_MW_PATH = "failure";
  
  boolean new_MW_PATH_SMB = false;
  
  String new_MW_Smb_PATH = "failure";
  
  String new_MW_Smb_PATH_folder = "failure";
  
  String new_MW_Smb_PATH_host = "failure";
  
  String new_MW_Smb_PWD = "failure";
  
  String new_MW_Smb_USER = "failure";
  
  String new_USB_PATH = "failure";
  
  JRadioButton rdoLocalPath = new JRadioButton();
  
  JRadioButton rdoSMBPath = new JRadioButton();
  
  private static final long serialVersionUID = 1L;
  
  JTextField txtMOPath = new JTextField();
  
  JTextField txtMWPath = new JTextField();
  
  JPasswordField txtMWSmbPWD = new JPasswordField();
  
  JTextField txtMWSmbPath_folder = new JTextField();
  
  JTextField txtMWSmbPath_host = new JTextField();
  
  JTextField txtMWSmbUser = new JTextField();
  
  JTextField txtUSBPath = new JTextField();
  
  XYLayout xYLayout1 = new XYLayout();
  
  public pnlParaPath() {
    try {
      init();
      this.jfc = new JFileChooser("c:\\");
      callbackValue();
      this.rdoSMBPath.setVisible(true);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    _$17646();
  }
  
  void btnMO_actionPerformed(ActionEvent paramActionEvent) {
    this.jfc.setFileSelectionMode(1);
    int i = this.jfc.showOpenDialog(this);
    if (i == 0)
      this.txtMOPath.setText(this.jfc.getSelectedFile().getAbsolutePath()); 
  }
  
  void btnMW_actionPerformed(ActionEvent paramActionEvent) {
    this.jfc.setFileSelectionMode(1);
    int i = this.jfc.showOpenDialog(this);
    if (i == 0)
      this.txtMWPath.setText(this.jfc.getSelectedFile().getAbsolutePath()); 
  }
  
  void btnTestCon_actionPerformed(ActionEvent paramActionEvent) {
    StringBuffer stringBuffer = new StringBuffer();
    boolean bool = true;
    try {
      bool &= _$17650();
    } catch (Exception exception) {
      bool = false;
      stringBuffer.append(ATPMessages.getString("MW.PARA.PATH.USBPATH") + "\n");
      stringBuffer.append("　" + exception.getMessage() + "\n");
    } 
    try {
      bool &= _$17651();
    } catch (Exception exception) {
      bool = false;
      stringBuffer.append(ATPMessages.getString("MW.PARA.PATH.MOPATH") + "\n");
      stringBuffer.append("　" + exception.getMessage() + "\n");
    } 
    try {
      bool &= _$17652();
    } catch (Exception exception) {
      exception.printStackTrace();
      bool = false;
      stringBuffer.append(ATPMessages.getString("MW.PARA.PATH.MWPATH") + "\n");
      stringBuffer.append("　" + exception.getMessage() + "\n");
    } 
    if (bool) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.PARA.PATH.TEST_CONN.OK"), ATPMessages.getString("MW.GNL.SUCCESS"), 1);
    } else {
      JOptionPane.showMessageDialog(this, stringBuffer.toString() + ATPMessages.getString("MW.GNL.ERROR") + "!!!", ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnUSB_actionPerformed(ActionEvent paramActionEvent) {
    this.jfc.setFileSelectionMode(1);
    int i = this.jfc.showOpenDialog(this);
    if (i == 0)
      this.txtUSBPath.setText(this.jfc.getSelectedFile().getAbsolutePath()); 
  }
  
  protected void callbackValue() {
    this.USB_PATH = InitParameters.USBPath;
    this.MO_PATH = InitParameters.MOPath;
    this.MW_PATH_SMB = InitParameters.MWPath.equals("SMB");
    this.MW_PATH = InitParameters.MWLogPath;
    this.MW_SMB_Path = InitParameters.MWSMBPath;
    String[] arrayOfString = this.MW_SMB_Path.split("/");
    this.MW_SMB_Path_host = arrayOfString[arrayOfString.length - 2];
    this.MW_SMB_Path_folder = arrayOfString[arrayOfString.length - 1];
    this.MW_SMB_USER = InitParameters.MWSMBUser;
    this.MW_SMB_PWD = InitParameters.MWSMBPWD;
    this.txtUSBPath.setText(this.USB_PATH);
    this.txtMOPath.setText(this.MO_PATH);
    if (this.MW_PATH_SMB) {
      this.rdoSMBPath.setSelected(true);
    } else {
      this.rdoLocalPath.setSelected(true);
    } 
    _$17646();
    this.txtMWPath.setText(this.MW_PATH);
    this.txtMWSmbPath_host.setText(this.MW_SMB_Path_host);
    this.txtMWSmbPath_folder.setText(this.MW_SMB_Path_folder);
    this.txtMWSmbUser.setText(this.MW_SMB_USER);
    this.txtMWSmbPWD.setText(this.MW_SMB_PWD);
    this.new_MW_PATH_SMB = this.MW_PATH_SMB;
    this.new_USB_PATH = this.USB_PATH;
    this.new_MO_PATH = this.MO_PATH;
    this.new_MW_PATH = this.MW_PATH;
    this.new_MW_Smb_PATH = this.MW_SMB_Path;
    this.new_MW_Smb_PATH_host = this.MW_SMB_Path_host;
    this.new_MW_Smb_PATH_folder = this.MW_SMB_Path_folder;
    this.new_MW_Smb_USER = this.MW_SMB_USER;
    this.new_MW_Smb_PWD = this.MW_SMB_PWD;
  }
  
  private void _$17646() {
    this.new_MW_PATH_SMB = this.rdoSMBPath.isSelected();
    if (this.rdoLocalPath.isSelected()) {
      this.lblLocalPath.setVisible(true);
      this.txtMWPath.setVisible(true);
      this.btnMW.setVisible(true);
      this.lblMWSmbPath.setVisible(false);
      this.txtMWSmbPath_host.setVisible(false);
      this.txtMWSmbPath_folder.setVisible(false);
      this.lblMWSmbPWD.setVisible(false);
      this.txtMWSmbPWD.setVisible(false);
      this.lblMWSmbUser.setVisible(false);
      this.txtMWSmbUser.setVisible(false);
    } else if (this.rdoSMBPath.isSelected()) {
      this.lblLocalPath.setVisible(false);
      this.txtMWPath.setVisible(false);
      this.btnMW.setVisible(false);
      this.lblMWSmbPath.setVisible(true);
      this.txtMWSmbPath_host.setVisible(true);
      this.txtMWSmbPath_folder.setVisible(true);
      this.lblMWSmbPWD.setVisible(true);
      this.txtMWSmbPWD.setVisible(true);
      this.lblMWSmbUser.setVisible(true);
      this.txtMWSmbUser.setVisible(true);
    } 
  }
  
  public Object[] getValues() {
    Object[] arrayOfObject = new Object[10];
    arrayOfObject[0] = this.new_USB_PATH;
    arrayOfObject[1] = this.new_USB_PATH + (this.new_USB_PATH.endsWith("\\") ? "" : "\\") + "logdata\\";
    arrayOfObject[2] = this.new_USB_PATH + (this.new_USB_PATH.endsWith("\\") ? "" : "\\") + "Maintain\\";
    arrayOfObject[3] = this.new_USB_PATH + (this.new_USB_PATH.endsWith("\\") ? "" : "\\") + "ru_logdata\\";
    arrayOfObject[4] = this.new_MO_PATH;
    arrayOfObject[5] = this.new_MW_PATH;
    arrayOfObject[6] = this.new_MW_PATH_SMB ? "SMB" : "LOCAL";
    arrayOfObject[7] = this.new_MW_Smb_PATH;
    arrayOfObject[8] = this.new_MW_Smb_USER;
    arrayOfObject[9] = this.new_MW_Smb_PWD;
    return arrayOfObject;
  }
  
  void init() throws Exception {
    this.btnTestCon.setBorderPainted(true);
    this.btnTestCon.setText(ATPMessages.getString("MW.PARA.PATH.TEST_CONN"));
    this.btnTestCon.addActionListener((ActionListener)new pnlParaPath_btnTestCon_actionAdapter(this));
    this.jLabel5.setText(ATPMessages.getString("MW.PARA.PATH.SETTING"));
    this.jLabel5.setFont(new Font("Dialog", 1, 14));
    this.lblUSBPath.setRequestFocusEnabled(true);
    this.lblUSBPath.setText(ATPMessages.getString("MW.PARA.PATH.USBPATH"));
    this.txtUSBPath.setNextFocusableComponent(this.txtMOPath);
    this.txtUSBPath.setText("jTextField3");
    this.txtUSBPath.addFocusListener((FocusListener)new pnlParaPath_txtPathField_focusAdapter(this));
    this.txtUSBPath.addKeyListener((KeyListener)new pnlParaPath_txtPathField_keyAdapter(this));
    this.lblMOPath.setText(ATPMessages.getString("MW.PARA.PATH.MOPATH"));
    this.txtMOPath.setNextFocusableComponent(this.txtMWPath);
    this.txtMOPath.setText("jTextField1");
    this.txtMOPath.addFocusListener((FocusListener)new pnlParaPath_txtPathField_focusAdapter(this));
    this.txtMOPath.addKeyListener((KeyListener)new pnlParaPath_txtPathField_keyAdapter(this));
    this.lblMWPath.setText(ATPMessages.getString("MW.PARA.PATH.MWPATH"));
    this.rdoLocalPath.setText(ATPMessages.getString("MW.PARA.PATH.MW.LOCAL"));
    this.rdoSMBPath.setText(ATPMessages.getString("MW.PARA.PATH.MW.REMOTE"));
    this.rdoLocalPath.addActionListener(this);
    this.rdoSMBPath.addActionListener(this);
    ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add(this.rdoLocalPath);
    buttonGroup.add(this.rdoSMBPath);
    this.lblLocalPath.setText(ATPMessages.getString("MW.PARA.PATH.MW.LOCAL.PATH"));
    this.txtMWPath.setNextFocusableComponent(this.btnTestCon);
    this.txtMWPath.setText("jTextField2");
    this.txtMWPath.addFocusListener((FocusListener)new pnlParaPath_txtPathField_focusAdapter(this));
    this.txtMWPath.addKeyListener((KeyListener)new pnlParaPath_txtPathField_keyAdapter(this));
    this.lblMWSmbPath.setText(ATPMessages.getString("MW.PARA.PATH.MW.REMOTE.SMB.PATH"));
    this.lblMWSmbSymb.setBackground(Color.white);
    this.lblMWSmbSymb.setOpaque(true);
    this.txtMWSmbPath_host.setNextFocusableComponent(this.txtMWSmbPath_folder);
    this.txtMWSmbPath_host.setText("");
    this.txtMWSmbPath_host.addFocusListener((FocusListener)new pnlParaPath_txtPathField_focusAdapter(this));
    this.txtMWSmbPath_host.addKeyListener((KeyListener)new pnlParaPath_txtSMBPathField_keyAdapter(this));
    this.lblMWSmbSlash.setBackground(Color.white);
    this.lblMWSmbSlash.setOpaque(true);
    this.txtMWSmbPath_folder.setNextFocusableComponent(this.txtMWSmbUser);
    this.txtMWSmbPath_folder.setText("");
    this.txtMWSmbPath_folder.addFocusListener((FocusListener)new pnlParaPath_txtPathField_focusAdapter(this));
    this.txtMWSmbPath_folder.addKeyListener((KeyListener)new pnlParaPath_txtSMBPathField_keyAdapter(this));
    this.lblMWSmbUser.setText(ATPMessages.getString("MW.PARA.PATH.MW.REMOTE.SMB.USER"));
    this.txtMWSmbUser.setNextFocusableComponent(this.txtMWSmbPWD);
    this.txtMWSmbUser.setText("");
    this.txtMWSmbUser.addFocusListener((FocusListener)new pnlParaPath_txtPathField_focusAdapter(this));
    this.txtMWSmbUser.addKeyListener((KeyListener)new pnlParaPath_txtPathField_keyAdapter(this));
    this.lblMWSmbPWD.setText(ATPMessages.getString("MW.PARA.PATH.MW.REMOTE.SMB.PWD"));
    this.txtMWSmbPWD.setNextFocusableComponent(this.btnTestCon);
    this.txtMWSmbPWD.setText("");
    this.txtMWSmbPWD.addFocusListener((FocusListener)new pnlParaPath_txtPathField_focusAdapter(this));
    this.txtMWSmbPWD.addKeyListener((KeyListener)new pnlParaPath_txtPathField_keyAdapter(this));
    this.btnUSB.setText("...");
    this.btnUSB.addActionListener((ActionListener)new pnlParaPath_btnUSB_actionAdapter(this));
    this.btnUSB.addFocusListener((FocusListener)new pnlParaPath_txtPathField_focusAdapter(this));
    this.btnMO.setText("...");
    this.btnMO.addActionListener((ActionListener)new pnlParaPath_btnMO_actionAdapter(this));
    this.btnMO.addFocusListener((FocusListener)new pnlParaPath_txtPathField_focusAdapter(this));
    this.btnMW.setText("...");
    this.btnMW.addActionListener((ActionListener)new pnlParaPath_btnMW_actionAdapter(this));
    this.btnMW.addFocusListener((FocusListener)new pnlParaPath_txtPathField_focusAdapter(this));
    setMaximumSize(new Dimension(2147483647, 2147483647));
    setMinimumSize(new Dimension(404, 418));
    setPreferredSize(new Dimension(404, 418));
    setLayout((LayoutManager)this.xYLayout1);
    add(this.jLabel5, new XYConstraints(33, 11, -1, -1));
    add(this.lblUSBPath, new XYConstraints(50, 50, -1, -1));
    add(this.txtUSBPath, new XYConstraints(120, 50, 148, -1));
    add(this.btnUSB, new XYConstraints(267, 50, 15, -1));
    add(this.lblMOPath, new XYConstraints(50, 90, -1, -1));
    add(this.txtMOPath, new XYConstraints(120, 90, 148, -1));
    add(this.btnMO, new XYConstraints(267, 90, 15, -1));
    add(this.lblMWPath, new XYConstraints(50, 130, -1, -1));
    add(this.rdoLocalPath, new XYConstraints(120, 130, -1, -1));
    add(this.rdoSMBPath, new XYConstraints(200, 130, -1, -1));
    add(this.lblLocalPath, new XYConstraints(80, 170, -1, -1));
    add(this.txtMWPath, new XYConstraints(150, 170, 148, -1));
    add(this.btnMW, new XYConstraints(297, 170, 15, -1));
    add(this.lblMWSmbPath, new XYConstraints(80, 170, -1, -1));
    add(this.lblMWSmbSymb, new XYConstraints(150, 172, -1, -1));
    add(this.lblMWSmbSlash, new XYConstraints(300, 172, -1, -1));
    add(this.txtMWSmbPath_host, new XYConstraints(180, 170, 120, -1));
    add(this.txtMWSmbPath_folder, new XYConstraints(302, 170, 70, -1));
    add(this.lblMWSmbUser, new XYConstraints(80, 210, -1, -1));
    add(this.txtMWSmbUser, new XYConstraints(150, 210, 148, -1));
    add(this.lblMWSmbPWD, new XYConstraints(80, 250, -1, -1));
    add(this.txtMWSmbPWD, new XYConstraints(150, 250, 148, -1));
    add(this.btnTestCon, new XYConstraints(50, 290, -1, -1));
  }
  
  public boolean isChanged() {
    return this.changes;
  }
  
  private boolean _$17651() throws Exception {
    File file = new File(this.txtMOPath.getText());
    DiskStatus.getDiskStatus(file);
    return true;
  }
  
  private boolean _$17652() throws Exception {
    if (this.rdoSMBPath.isSelected())
      return CopySmbDir.isFileExist("smb://" + this.txtMWSmbPath_host.getText() + "/" + this.txtMWSmbPath_folder.getText() + "/", this.txtMWSmbUser.getText(), this.txtMWSmbPWD.getText()); 
    File file = new File(this.txtMWPath.getText());
    DiskStatus.getDiskStatus(file);
    return true;
  }
  
  private void _$8475() {
    this.changes = true;
    this.new_USB_PATH = this.txtUSBPath.getText();
    this.new_MO_PATH = this.txtMOPath.getText();
    this.new_MW_PATH_SMB = this.rdoSMBPath.isSelected();
    if (this.new_MW_PATH_SMB) {
      this.new_MW_Smb_PATH = "SMB://" + this.txtMWSmbPath_host.getText().trim() + "/" + this.txtMWSmbPath_folder.getText().trim() + "/";
      this.new_MW_Smb_USER = this.txtMWSmbUser.getText();
      this.new_MW_Smb_PWD = this.txtMWSmbPWD.getText();
    } else {
      this.new_MW_PATH = this.txtMWPath.getText();
    } 
  }
  
  void txtPathField_focusLost(FocusEvent paramFocusEvent) {
    _$8475();
  }
  
  void txtPathField_keyPressed(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyCode() == 10) {
      _$8475();
      ((Component)paramKeyEvent.getSource()).transferFocus();
    } 
  }
  
  void txtSMBPathField_keyTyped(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyChar() == '\\' || paramKeyEvent.getKeyChar() == '/' || paramKeyEvent.getKeyChar() == ':' || paramKeyEvent.getKeyChar() == '*' || paramKeyEvent.getKeyChar() == '?' || paramKeyEvent.getKeyChar() == '<' || paramKeyEvent.getKeyChar() == '>' || paramKeyEvent.getKeyChar() == '|') {
      paramKeyEvent.consume();
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.PARA.PATH.MW.LOCAL.PATH.WRONGCHARACTER") + "\n \\ / : * ? < > |");
    } 
  }
  
  private boolean _$17650() throws Exception {
    File file = new File(this.txtUSBPath.getText());
    DiskStatus.getDiskStatus(file);
    return true;
  }
}
