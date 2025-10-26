package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.frmAddStation_cancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmAddStation_cmboLine_itemAdapter;
import com.MiTAC.TRA.ATP.ui.frmAddStation_commit_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmAddStation_ftxtStationID_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmAddStation_ftxtStationID_focusAdapter;
import com.MiTAC.TRA.ATP.ui.frmAddStation_tfStationID_BT_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmAddStation_txtStationCName_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmAddStation_txtStationEName_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmAddStation_txtStationImg_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmAddStation_txtStationKM_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmError;
import com.MiTAC.TRA.ATP.ui.frmMain;
import java.awt.BorderLayout;
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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class frmAddStation extends JDialog {
  boolean IncludeTransaction = false;
  
  String Linebelong;
  
  String Linebelongno;
  
  JButton btnCancel = new JButton();
  
  JButton btnCommit = new JButton();
  
  JComboBox cmboLine = new JComboBox();
  
  ConnectDB cnDB = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  int flag;
  
  JFormattedTextField ftxtStationID;
  
  boolean insert = false;
  
  JLabel jLabel1 = new JLabel();
  
  JPanel jPanel = new JPanel();
  
  JLabel lbLine = new JLabel();
  
  JLabel lbLineData = new JLabel();
  
  JLabel lbStationDist = new JLabel();
  
  JLabel lbStationEngName = new JLabel();
  
  JLabel lbStationID = new JLabel();
  
  JLabel lbStationID_BT = new JLabel();
  
  JLabel lbStationName = new JLabel();
  
  Vector line;
  
  MaskFormatter mf1;
  
  Vector station;
  
  JTextField tfStationID_BT = new JTextField();
  
  JTextField txtLinebelong = new JTextField();
  
  JTextField txtStationCName = new JTextField();
  
  JTextField txtStationEName = new JTextField();
  
  JTextField txtStationImg = new JTextField();
  
  JTextField txtStationKM = new JTextField();
  
  boolean update = false;
  
  Vector vTmpData;
  
  public frmAddStation(int paramInt, Vector paramVector, Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    this.line = paramVector;
    this.update = false;
    this.IncludeTransaction = false;
    try {
      init();
      _$21906();
      this.cmboLine.setSelectedIndex(paramInt);
      pack();
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public frmAddStation(Vector paramVector1, Vector paramVector2, Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    this.update = true;
    this.IncludeTransaction = false;
    this.station = paramVector2;
    this.line = paramVector1;
    try {
      init();
      _$21908();
      pack();
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public frmAddStation(Vector paramVector, Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    this.flag = 0;
    this.line = paramVector;
    this.update = false;
    this.IncludeTransaction = false;
    try {
      init();
      _$21906();
      pack();
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public frmAddStation(Vector paramVector, String paramString1, String paramString2, Frame paramFrame, String paramString3, boolean paramBoolean) {
    super(paramFrame, paramString3, paramBoolean);
    this.flag = 0;
    this.line = paramVector;
    this.Linebelong = paramString1;
    this.Linebelongno = paramString2;
    this.IncludeTransaction = false;
    try {
      init();
      _$21906();
      pack();
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    this.cmboLine.setVisible(false);
    this.txtLinebelong.setVisible(true);
    this.txtLinebelong.setText(this.Linebelong);
  }
  
  void cancel_actionPerformed(ActionEvent paramActionEvent) {
    if (this.update) {
      int i = JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.STATION.MODIFY.Q.CANCEL"), ATPMessages.getString("MW.GNL.ASK"), 0, 3);
      if (i == 0)
        dispose(); 
    } else {
      dispose();
    } 
  }
  
  private boolean _$20947(String paramString) {
    boolean bool = false;
    char[] arrayOfChar = { 
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
        'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', 
        '4', '5', '6', '7', '8', '9', '-' };
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
  
  private boolean _$21911() {
    return true;
  }
  
  void cmboLine_itemStateChanged(ItemEvent paramItemEvent) {
    this.ftxtStationID.setText(((Vector)this.line.get(this.cmboLine.getSelectedIndex())).get(0).toString());
  }
  
  void commit_actionPerformed(ActionEvent paramActionEvent) {
    try {
      this.vTmpData = new Vector();
      int i = this.cmboLine.getSelectedIndex();
      if (_$21911()) {
        String[] arrayOfString = { "", "" };
        String str = ((Vector)this.line.get(i)).get(0);
        this.vTmpData.addElement(this.ftxtStationID.getText().trim().toUpperCase());
        if (this.txtStationKM.getText().length() == 0) {
          this.vTmpData.addElement(new Float(0.0F));
        } else {
          this.vTmpData.addElement(new Float(this.txtStationKM.getText()));
        } 
        this.vTmpData.addElement(this.txtStationEName.getText().trim());
        this.vTmpData.addElement(this.txtStationCName.getText().trim());
        this.vTmpData.addElement(this.txtStationImg.getText().trim());
        this.vTmpData.addElement(str);
        this.vTmpData.addElement(this.tfStationID_BT.getText().trim());
        if (this.update) {
          this.vTmpData.addElement(this.station.get(0));
          arrayOfString = this.cnDB.Update(this.vTmpData);
          String str1 = this.lbLine.getText() + ":" + this.cmboLine.getSelectedItem() + "," + this.lbStationID.getText() + ":" + this.ftxtStationID.getText().toUpperCase() + "," + this.lbStationDist.getText() + ":" + this.txtStationKM.getText() + "," + this.lbStationName.getText() + ":" + this.txtStationCName.getText() + "," + this.lbStationEngName.getText() + ":" + this.txtStationEName.getText() + "," + this.lbLineData.getText() + ":" + this.txtStationImg.getText() + "," + this.lbStationID_BT.getText() + ":" + this.tfStationID_BT.getText();
          this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "車站", "修改", str1);
        } else {
          arrayOfString = this.cnDB.Insert(this.vTmpData);
          String str1 = this.lbLine.getText() + ":" + this.cmboLine.getSelectedItem() + "," + this.lbStationID.getText() + ":" + this.ftxtStationID.getText().toUpperCase() + "," + this.lbStationDist.getText() + ":" + this.txtStationKM.getText() + "," + this.lbStationName.getText() + ":" + this.txtStationCName.getText() + "," + this.lbStationEngName.getText() + ":" + this.txtStationEName.getText() + "," + this.lbLineData.getText() + ":" + this.txtStationImg.getText() + "," + this.lbStationID_BT.getText() + ":" + this.tfStationID_BT.getText();
          this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "車站", "新增", str1);
        } 
        frmError frmError = new frmError(arrayOfString[0], (Frame)getOwner(), ATPMessages.getString("MW.GNL.INFO"), true);
        frmError.show();
        if (arrayOfString[1].equals("1"))
          dispose(); 
      } 
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void ftxtStationID_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void ftxtStationID_focusGained(FocusEvent paramFocusEvent) {
    this.ftxtStationID.select(1, 4);
  }
  
  public Vector getAddData() {
    return this.vTmpData;
  }
  
  void init() throws Exception {
    getContentPane().setLayout(new BorderLayout());
    this.jPanel.setLayout(new GridBagLayout());
    this.jLabel1.setBorder(BorderFactory.createEtchedBorder());
    this.jLabel1.setFont(new Font("Dialog", 1, 16));
    this.lbLine.setText(ATPMessages.getString("MW.STATION.BELONG"));
    this.lbStationName.setText(ATPMessages.getString("MW.STATION.NAME"));
    this.txtStationCName.setText("");
    this.txtStationCName.addActionListener((ActionListener)new frmAddStation_txtStationCName_actionAdapter(this));
    this.lbStationEngName.setText(ATPMessages.getString("MW.STATION.E_NAME"));
    this.txtStationEName.setText("");
    this.txtStationEName.addActionListener((ActionListener)new frmAddStation_txtStationEName_actionAdapter(this));
    this.lbStationDist.setText(ATPMessages.getString("MW.STATION.KM"));
    this.txtStationKM.setText("");
    this.txtStationKM.addActionListener((ActionListener)new frmAddStation_txtStationKM_actionAdapter(this));
    this.btnCommit.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.btnCommit.addActionListener((ActionListener)new frmAddStation_commit_actionAdapter(this));
    this.btnCancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.btnCancel.addActionListener((ActionListener)new frmAddStation_cancel_actionAdapter(this));
    this.mf1 = new MaskFormatter("?###");
    this.mf1.setPlaceholderCharacter('_');
    this.ftxtStationID = new JFormattedTextField(this.mf1);
    this.ftxtStationID.addActionListener((ActionListener)new frmAddStation_ftxtStationID_actionAdapter(this));
    this.lbStationID.setText(ATPMessages.getString("MW.STATION.ID"));
    this.txtStationImg.setText("");
    this.txtStationImg.addActionListener((ActionListener)new frmAddStation_txtStationImg_actionAdapter(this));
    this.lbLineData.setText(ATPMessages.getString("MW.STATION.DATA"));
    this.lbStationID_BT.setText("地上設備停站代碼");
    this.tfStationID_BT.setText("");
    this.tfStationID_BT.addActionListener((ActionListener)new frmAddStation_tfStationID_BT_actionAdapter(this));
    this.cmboLine.addItemListener((ItemListener)new frmAddStation_cmboLine_itemAdapter(this));
    this.ftxtStationID.addFocusListener((FocusListener)new frmAddStation_ftxtStationID_focusAdapter(this));
    this.jPanel.add(this.lbLine, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(10, 30, 4, 4), 0, 0));
    this.jPanel.add(this.cmboLine, new GridBagConstraints(2, 1, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.jPanel.add(this.lbStationID, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.jPanel.add(this.ftxtStationID, new GridBagConstraints(2, 2, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.jPanel.add(this.lbStationDist, new GridBagConstraints(1, 3, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.jPanel.add(this.txtStationKM, new GridBagConstraints(2, 3, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.jPanel.add(this.lbStationName, new GridBagConstraints(1, 4, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.jPanel.add(this.txtStationCName, new GridBagConstraints(2, 4, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.jPanel.add(this.lbStationEngName, new GridBagConstraints(1, 5, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.jPanel.add(this.txtStationEName, new GridBagConstraints(2, 5, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.jPanel.add(this.lbLineData, new GridBagConstraints(1, 6, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.jPanel.add(this.txtStationImg, new GridBagConstraints(2, 6, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.jPanel.add(this.lbStationID_BT, new GridBagConstraints(1, 7, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.jPanel.add(this.tfStationID_BT, new GridBagConstraints(2, 7, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.jPanel.add(this.btnCommit, new GridBagConstraints(3, 8, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.jPanel.add(this.btnCancel, new GridBagConstraints(4, 8, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 4, 4), 0, 0));
    getContentPane().add(this.jLabel1, "North");
    getContentPane().add(this.jPanel, "Center");
    this.txtLinebelong.setVisible(false);
  }
  
  private void _$21908() throws Exception {
    byte b1 = 0;
    String str = ((String)this.station.get(5)).toString().trim();
    for (byte b2 = 0; b2 <= this.line.size() - 1; b2++) {
      this.cmboLine.addItem(((String)((Vector)this.line.get(b2)).get(1)).toString().trim());
      if (((String)((Vector)this.line.get(b2)).get(0)).toString().trim().equals(str))
        b1 = b2; 
    } 
    this.cmboLine.setSelectedIndex(b1);
    for (byte b3 = 0; b3 <= 5; b3++) {
      if (null != this.station.get(b3))
        switch (b3) {
          case 0:
            this.ftxtStationID.setText(((String)this.station.get(0)).toString().trim());
            break;
          case 1:
            this.txtStationKM.setText(((Double)this.station.get(1)).toString().trim());
            break;
          case 2:
            this.txtStationEName.setText(((String)this.station.get(2)).toString().trim());
            break;
          case 3:
            this.txtStationCName.setText(((String)this.station.get(3)).toString().trim());
            break;
          case 4:
            this.txtStationImg.setText(((String)this.station.get(4)).toString().trim());
            break;
          case 5:
            this.tfStationID_BT.setText(this.station.get(6).toString().trim());
            break;
        }  
    } 
    this.jLabel1.setText(ATPMessages.getString("MW.STATION.MODIFY"));
    setTitle(ATPMessages.getString("MW.STATION.MODIFY"));
  }
  
  private void _$21906() throws Exception {
    for (byte b = 0; b < this.line.size(); b++)
      this.cmboLine.addItem(((String)((Vector)this.line.get(b)).get(1)).toString().trim()); 
    this.jLabel1.setText(ATPMessages.getString("MW.STATION.NEW"));
    setTitle(ATPMessages.getString("MW.STATION.NEW"));
    this.ftxtStationID.setText(((Vector)this.line.get(this.cmboLine.getSelectedIndex())).get(0).toString());
  }
  
  void tfStationID_BT_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtStationCName_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtStationEName_actionPerformed(ActionEvent paramActionEvent) {
    if (!_$20947(this.txtStationEName.getText())) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.STATION.ERROR.ENG_WORD_OR_SPACE_ONLY"), ATPMessages.getString("MW.GNL.WARN"), 2);
      this.txtStationEName.select(this.txtStationEName.getText().length() - 1, this.txtStationEName.getText().length());
      this.txtStationEName.requestFocusInWindow();
    } else {
      ((Component)paramActionEvent.getSource()).transferFocus();
    } 
  }
  
  void txtStationImg_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtStationKM_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
}
