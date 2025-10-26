package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.Tools.PasswordInputDialog;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.Tools.StringHandler;
import com.MiTAC.TRA.ATP.Tools.UpperCaseAndMaxLenghtDocument;
import com.MiTAC.TRA.ATP.Tools.WorkDatetimeTransfer;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.TestModel;
import com.MiTAC.TRA.ATP.ui.frmMain;
import com.MiTAC.TRA.ATP.ui.frmWSEdit_cancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmWSEdit_confirm_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmWSEdit_dateChooser_propertyChangeAdapter;
import com.MiTAC.TRA.ATP.ui.frmWSEdit_forward_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmWSEdit_jComboBox4_itemAdapter;
import com.MiTAC.TRA.ATP.ui.frmWSEdit_reverse_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmWSEdit_txtWSNo_actionAdapter;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.DateFormatter;
import javax.swing.text.Document;
import javax.swing.text.MaskFormatter;

public class frmWSEdit extends JDialog {
  Vector allTRName = new Vector();
  
  Vector allTRNo = new Vector();
  
  Vector allTrainNo;
  
  BorderLayout borderLayout1 = new BorderLayout();
  
  BorderLayout borderLayout2 = new BorderLayout();
  
  JButton cancel = new JButton();
  
  JButton confirm = new JButton();
  
  ConnectDB connDB = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  JDateChooser dateChooser = new JDateChooser();
  
  String dateOfWSMotion;
  
  DateFormatter df = new DateFormatter(this.format);
  
  Document doc = (Document)new UpperCaseAndMaxLenghtDocument(8);
  
  DefaultSortTableModel dtm2 = new DefaultSortTableModel();
  
  FlowLayout flowLayout1 = new FlowLayout();
  
  FlowLayout flowLayout2 = new FlowLayout();
  
  DateFormat format = new SimpleDateFormat("d-hh:mm");
  
  JButton forward = new JButton();
  
  boolean giveup = false;
  
  boolean insert = false;
  
  JComboBox jComboBox1 = new JComboBox();
  
  JComboBox jComboBox2 = new JComboBox();
  
  JComboBox jComboBox3 = new JComboBox();
  
  JComboBox jComboBox4 = new JComboBox();
  
  JComboBox jComboBox5 = new JComboBox();
  
  JComboBox jComboBox6 = new JComboBox();
  
  JFormattedTextField jFormattedTextField1;
  
  JFormattedTextField jFormattedTextField2;
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel10 = new JLabel();
  
  JLabel jLabel11 = new JLabel();
  
  JLabel jLabel12 = new JLabel();
  
  JLabel jLabel13 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
  JLabel jLabel4 = new JLabel();
  
  JLabel jLabel5 = new JLabel();
  
  JLabel jLabel6 = new JLabel();
  
  JLabel jLabel7 = new JLabel();
  
  JLabel jLabel8 = new JLabel();
  
  JLabel jLabel9 = new JLabel();
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel2 = new JPanel();
  
  JPanel jPanel3 = new JPanel();
  
  JPanel jPanel4 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JScrollPane jScrollPane2 = new JScrollPane();
  
  JSortTable jTable1 = new JSortTable((SortTableModel)this.testModel);
  
  JSortTable jTable2 = new JSortTable((SortTableModel)this.dtm2);
  
  JTextField jTextField1 = new JTextField("0");
  
  MaskFormatter mf1;
  
  MaskFormatter mf2;
  
  PasswordInputDialog pwdInputDialog;
  
  JButton reverse = new JButton();
  
  TestModel testModel = new TestModel();
  
  Vector thisTRName = new Vector();
  
  Vector thisTRNo = new Vector();
  
  Vector thisTrainNoTemp;
  
  Vector thisTrainRunning;
  
  Vector thisWorkShiftData;
  
  TitledBorder titledBorder1;
  
  JTextField txtWSNo = new JTextField();
  
  boolean update = false;
  
  Vector vLateInMission = new Vector();
  
  Vector vtemp = new Vector();
  
  Vector vtrdata;
  
  WorkDatetimeTransfer workDateTimeTransfer;
  
  XYLayout xYLayout1 = new XYLayout();
  
  public frmWSEdit(Vector paramVector1, Vector paramVector2, Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    this.thisTrainNoTemp = new Vector();
    this.thisWorkShiftData = paramVector1;
    this.thisTrainRunning = paramVector2;
    for (byte b = 0; b < paramVector2.size(); b++)
      this.thisTrainNoTemp.add(paramVector2.get(b)); 
    this.thisWorkShiftData = paramVector1;
    try {
      _$3120();
      this.txtWSNo.setEditable(false);
      this.jLabel4.setText(ATPMessages.getString("MW.WS.MODIFY"));
      this.update = true;
      this.txtWSNo.setText(this.thisWorkShiftData.get(0).toString());
      this.dateChooser.setDate((new SimpleDateFormat("yyyy-MM-dd")).parse((new SimpleDateFormat("yyyy-MM-dd")).format(this.thisWorkShiftData.get(3))));
      this.dateOfWSMotion = this.thisWorkShiftData.get(3).toString();
      _$21101(1, null, this.thisWorkShiftData.get(0).toString());
      _$21102();
      _$3558(this.thisTrainRunning);
      WorkDatetimeTransfer workDatetimeTransfer1 = new WorkDatetimeTransfer(Integer.parseInt(this.thisWorkShiftData.get(1).toString()));
      WorkDatetimeTransfer workDatetimeTransfer2 = new WorkDatetimeTransfer(Integer.parseInt(this.thisWorkShiftData.get(2).toString()));
      this.jComboBox2.setSelectedItem(String.valueOf(workDatetimeTransfer1.getHour()));
      this.jComboBox3.setSelectedItem(String.valueOf(workDatetimeTransfer1.getMinutes()));
      this.jComboBox4.setSelectedItem(String.valueOf(workDatetimeTransfer2.getDay()));
      this.jComboBox5.setSelectedItem(String.valueOf(workDatetimeTransfer2.getHour()));
      this.jComboBox6.setSelectedItem(String.valueOf(workDatetimeTransfer2.getMinutes()));
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      JOptionPane.showMessageDialog(this, sQLException.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  public frmWSEdit(Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    try {
      _$3120();
      this.jLabel4.setText(ATPMessages.getString("MW.WS.NEW"));
      this.insert = true;
      this.thisTrainRunning = new Vector();
      _$21101(0, this.thisTrainRunning, null);
      _$21102();
      _$3558(this.thisTrainRunning);
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      JOptionPane.showMessageDialog(this, sQLException.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void cancel_actionPerformed(ActionEvent paramActionEvent) {
    if (this.update == true) {
      int i = JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.GNL.CANCEL.Q.CONFIRM"), ATPMessages.getString("MW.GNL.ASK"), 0, 3);
      if (i == 0)
        dispose(); 
    } else {
      this.giveup = true;
      dispose();
    } 
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
  
  private int _$21118(String paramString) {
    return (paramString.equals("_") || paramString.equals("__")) ? 0 : Integer.parseInt(paramString);
  }
  
  private void _$3558(Vector paramVector) {
    Vector vector = new Vector();
    this.thisTrainRunning = paramVector;
    int i = this.allTrainNo.size();
    int j = paramVector.size();
    for (byte b = 0; b < i; b++) {
      byte b1 = 0;
      for (byte b2 = 0; b2 < j; b2++) {
        if (((Vector)this.allTrainNo.get(b)).get(0).equals(((Vector)paramVector.get(b2)).get(0)) && ((Vector)this.allTrainNo.get(b)).get(1).equals(((Vector)paramVector.get(b2)).get(1)))
          b1++; 
      } 
      if (b1 == 0)
        vector.addElement(this.allTrainNo.get(b)); 
    } 
    this.dtm2.setDataVector(vector, this.allTRName);
  }
  
  void confirm_actionPerformed(ActionEvent paramActionEvent) {
    try {
      if (this.txtWSNo.getText().length() == 0) {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.WS.ID_EMPTY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
        this.txtWSNo.selectAll();
        this.txtWSNo.requestFocusInWindow();
      } else if (this.jTable1.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.WS.TR_EMPYT"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      } else {
        WorkDatetimeTransfer workDatetimeTransfer1 = new WorkDatetimeTransfer(Integer.parseInt(this.jTextField1.getText()), this.jComboBox2.getSelectedIndex(), this.jComboBox3.getSelectedIndex());
        WorkDatetimeTransfer workDatetimeTransfer2 = new WorkDatetimeTransfer(this.jComboBox4.getSelectedIndex(), this.jComboBox5.getSelectedIndex(), this.jComboBox6.getSelectedIndex());
        String str = StringHandler.fillLeft(this.txtWSNo.getText(), "0", 8);
        if (this.insert) {
          if (!_$20947(this.txtWSNo.getText())) {
            JOptionPane.showMessageDialog(this, "您所輸人的工作班班號格式錯誤,\n司機員員工編號只可輸人英文字母及阿拉伯數字,\n請重新輸入。", ATPMessages.getString("MW.GNL.ERROR"), 0);
            this.txtWSNo.selectAll();
            this.txtWSNo.requestFocus(true);
          } else if (workDatetimeTransfer2.getTime() < workDatetimeTransfer1.getTime()) {
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.WS.END_TIME<START_TIME"), ATPMessages.getString("MW.GNL.ERROR"), 0);
          } else {
            _$21111(str, workDatetimeTransfer1, workDatetimeTransfer2);
            dispose();
          } 
        } else if (workDatetimeTransfer2.getTime() < workDatetimeTransfer1.getTime()) {
          JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.WS.END_TIME<START_TIME"), ATPMessages.getString("MW.GNL.ERROR"), 0);
        } else if (this.dateOfWSMotion.equals(this.dateChooser.getDate().toString())) {
          _$21112(str, workDatetimeTransfer1, workDatetimeTransfer2);
          dispose();
        } else if (_$21113()) {
          int i = JOptionPane.showConfirmDialog(this, "因為啟用日期的變動，在派班記錄中將有下列資料會被刪除\n" + _$8112() + "，且需要重新派班。" + "請確認是否要修改本工作班。謝謝！\n" + "是，將會執行刪除上述派班記錄內的資料", ATPMessages.getString("MW.GNL.ERROR"), 0, 3);
          if (i == 0) {
            this.pwdInputDialog = new PasswordInputDialog((Frame)getParent(), "密碼確認", true);
            this.pwdInputDialog.show();
            if (this.pwdInputDialog.isCancel()) {
              dispose();
            } else {
              workDatetimeTransfer1 = new WorkDatetimeTransfer(Integer.parseInt(this.jTextField1.getText()), this.jComboBox2.getSelectedIndex(), this.jComboBox3.getSelectedIndex());
              workDatetimeTransfer2 = new WorkDatetimeTransfer(this.jComboBox4.getSelectedIndex(), this.jComboBox5.getSelectedIndex(), this.jComboBox6.getSelectedIndex());
              _$15391();
              _$21111(this.txtWSNo.getText(), workDatetimeTransfer1, workDatetimeTransfer2);
              dispose();
            } 
          } 
        } else {
          _$21111(this.txtWSNo.getText(), workDatetimeTransfer1, workDatetimeTransfer2);
          dispose();
        } 
      } 
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      if (sQLException.getErrorCode() == 2627)
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.WS.EXIST"), ATPMessages.getString("MW.GNL.ERROR"), 0); 
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void dateChooser_propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    try {
      _$21102();
      _$3558(this.thisTrainRunning);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$15391() {
    int i = this.vLateInMission.size();
    StringBuffer stringBuffer = new StringBuffer();
    try {
      for (byte b = 0; b < i; b++) {
        String str = "DELETE FROM Mission WHERE (DriverID='" + ((Vector)this.vLateInMission.get(b)).get(0) + "' AND WSNo = '" + ((Vector)this.vLateInMission.get(b)).get(2) + "' AND WorkDate = '" + (new SimpleDateFormat("yyyy/M/d")).format(((Vector)this.vLateInMission.get(b)).get(1)) + "')";
        this.connDB.Delete(str);
        this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "派班", "刪除", "司機員編號:" + ((Vector)this.vLateInMission.get(b)).get(0) + ",工作班:" + ((Vector)this.vLateInMission.get(b)).get(2) + ",工作班執行日期:" + (new SimpleDateFormat("yyyy/M/d")).format(((Vector)this.vLateInMission.get(b)).get(1)) + ",車次:" + ((Vector)this.vLateInMission.get(b)).get(3));
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void forward_actionPerformed(ActionEvent paramActionEvent) {
    Vector vector = new Vector();
    Vector vector1 = new Vector();
    if (this.jTable2.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.GNL.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      for (byte b = 0; b < this.jTable2.getRowCount(); b++) {
        if (this.jTable2.isRowSelected(b)) {
          vector.addElement(this.jTable2.getValueAt(b, 0));
          vector.addElement(this.jTable2.getValueAt(b, 1));
          vector.addElement("0");
          this.thisTrainRunning.addElement(vector);
          vector = new Vector();
        } else {
          vector1.addElement(this.allTrainNo.get(b));
        } 
      } 
      this.testModel.setDataVector(this.thisTrainRunning, this.thisTRName);
      _$21110((JTable)this.jTable1, this.jTable1.getColumnModel().getColumn(2));
      _$3558(this.thisTrainRunning);
    } 
  }
  
  private void _$21102() throws Exception {
    String str = "SELECT DISTINCT TRNo,MAX(TRMotionDate) FROM TrainRunning WHERE TRMotionDate <='" + (new SimpleDateFormat("yyyy/M/d")).format(this.dateChooser.getDate()) + "'" + " GROUP BY TRNo";
    this.allTrainNo = this.connDB.getData(str);
  }
  
  private boolean _$21113() {
    boolean bool = false;
    try {
      String str = "SELECT DISTINCT DriverID,WorkDate,WSNo FROM Mission WHERE WSNo = '" + this.txtWSNo.getText() + "' AND WorkDate >='" + (new SimpleDateFormat("yyyy/M/d")).format(this.dateChooser.getDate()) + "'";
      this.vLateInMission = this.connDB.getData(str);
      if (this.vLateInMission.isEmpty()) {
        bool = false;
      } else {
        bool = true;
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return bool;
  }
  
  private void _$3120() throws Exception {
    this.titledBorder1 = new TitledBorder("");
    this.jLabel1.setRequestFocusEnabled(true);
    this.jLabel1.setText(ATPMessages.getString("MW.WS.ID"));
    getContentPane().setLayout((LayoutManager)this.xYLayout1);
    this.txtWSNo.setDocument(this.doc);
    this.txtWSNo.setText("");
    this.txtWSNo.addActionListener((ActionListener)new frmWSEdit_txtWSNo_actionAdapter(this));
    this.jLabel2.setText(ATPMessages.getString("MW.WS.TRLIST"));
    this.jLabel3.setRequestFocusEnabled(true);
    this.jLabel3.setText(ATPMessages.getString("MW.TR.ALL"));
    this.confirm.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.confirm.addActionListener((ActionListener)new frmWSEdit_confirm_actionAdapter(this));
    this.cancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.cancel.addActionListener((ActionListener)new frmWSEdit_cancel_actionAdapter(this));
    this.reverse.setText(ATPMessages.getString("MW.GNL.MOVE_RIGHT"));
    this.reverse.addActionListener((ActionListener)new frmWSEdit_reverse_actionAdapter(this));
    this.forward.setText(ATPMessages.getString("MW.GNL.MOVE_LEFT"));
    this.forward.addActionListener((ActionListener)new frmWSEdit_forward_actionAdapter(this));
    setResizable(false);
    this.jLabel4.setFont(new Font("Dialog", 1, 16));
    this.jLabel4.setBorder(BorderFactory.createEtchedBorder());
    this.jTable2.setEnabled(true);
    this.jTable2.setFocusable(true);
    this.jPanel1.setLayout(this.borderLayout1);
    this.jPanel1.setBorder((Border)null);
    this.jPanel2.setDebugGraphicsOptions(0);
    this.jPanel2.setLayout(this.borderLayout2);
    this.jPanel2.setBorder(BorderFactory.createEmptyBorder());
    this.jPanel3.setLayout(this.flowLayout1);
    this.jPanel3.setBorder(BorderFactory.createLineBorder(Color.black));
    this.jPanel4.setLayout(this.flowLayout2);
    this.jPanel4.setBorder(BorderFactory.createLineBorder(Color.black));
    this.mf1 = new MaskFormatter("#-##:##");
    this.mf1.setPlaceholderCharacter('_');
    this.jFormattedTextField1 = new JFormattedTextField(this.mf1);
    this.mf2 = new MaskFormatter("#-##:##");
    this.mf2.setPlaceholderCharacter('_');
    this.jFormattedTextField2 = new JFormattedTextField(this.mf2);
    this.jLabel5.setBorder(BorderFactory.createLineBorder(Color.black));
    this.jLabel5.setHorizontalAlignment(0);
    this.jLabel5.setText(ATPMessages.getString("MW.WS.START_TIME.FORMAT"));
    this.jFormattedTextField2.setFocusLostBehavior(0);
    this.jFormattedTextField2.setHorizontalAlignment(0);
    this.jFormattedTextField2.setText("");
    this.jLabel6.setText(ATPMessages.getString("MW.WS.END_TIME.FORMAT"));
    this.jLabel6.setBorder(BorderFactory.createLineBorder(Color.black));
    this.jLabel6.setHorizontalAlignment(0);
    this.jLabel7.setHorizontalAlignment(0);
    this.jLabel7.setText("~");
    this.jFormattedTextField1.setFocusLostBehavior(0);
    this.jFormattedTextField1.setHorizontalAlignment(0);
    this.jFormattedTextField1.setText("");
    this.jComboBox4.addItemListener((ItemListener)new frmWSEdit_jComboBox4_itemAdapter(this));
    this.jLabel8.setBorder(BorderFactory.createEtchedBorder());
    this.jLabel8.setText(ATPMessages.getString("MW.WS.TR_WORKDATE.EXAMPLE"));
    this.jLabel9.setText(ATPMessages.getString("MW.WS.EXECUTE_DATE"));
    this.jLabel10.setText("-");
    this.jLabel11.setText("：");
    this.jLabel12.setText("-");
    this.jLabel13.setText("：");
    this.jTextField1.setColumns(2);
    this.dateChooser.setDateFormatString("yyyy-MM-dd");
    this.dateChooser.setCalendar(new GregorianCalendar());
    this.dateChooser.addPropertyChangeListener((PropertyChangeListener)new frmWSEdit_dateChooser_propertyChangeAdapter(this));
    getContentPane().add(this.jLabel1, new XYConstraints(40, 70, -1, -1));
    getContentPane().add(this.jLabel2, new XYConstraints(65, 100, -1, -1));
    getContentPane().add(this.jLabel3, new XYConstraints(410, 100, -1, -1));
    getContentPane().add(this.confirm, new XYConstraints(450, 400, -1, -1));
    getContentPane().add(this.reverse, new XYConstraints(350, 180, -1, -1));
    getContentPane().add(this.forward, new XYConstraints(350, 140, -1, -1));
    getContentPane().add(this.jLabel4, new XYConstraints(20, 20, 95, -1));
    getContentPane().add(this.cancel, new XYConstraints(530, 400, -1, -1));
    getContentPane().add(this.jPanel1, new XYConstraints(400, 120, 220, 270));
    this.jPanel1.add(this.jScrollPane2, "Center");
    this.jScrollPane2.getViewport().add((Component)this.jTable2, (Object)null);
    getContentPane().add(this.jPanel2, new XYConstraints(45, 120, 300, 270));
    this.jPanel2.add(this.jScrollPane1, "Center");
    getContentPane().add(this.txtWSNo, new XYConstraints(80, 70, 70, -1));
    getContentPane().add(this.jLabel5, new XYConstraints(210, 15, 100, 25));
    getContentPane().add(this.jLabel6, new XYConstraints(330, 15, 100, 25));
    getContentPane().add(this.jLabel7, new XYConstraints(320, 70, 20, 25));
    getContentPane().add(this.jLabel8, new XYConstraints(210, 42, 220, 20));
    getContentPane().add(this.jLabel9, new XYConstraints(520, 45, 100, 25));
    getContentPane().add((Component)this.dateChooser, new XYConstraints(520, 70, 100, -1));
    this.jScrollPane1.getViewport().add((Component)this.jTable1, (Object)null);
    getContentPane().add(this.jPanel3, new XYConstraints(165, 65, -1, -1));
    getContentPane().add(this.jPanel4, new XYConstraints(345, 65, -1, -1));
    this.jPanel3.add(this.jTextField1, (Object)null);
    this.jPanel3.add(this.jLabel10, (Object)null);
    this.jPanel3.add(this.jComboBox2, (Object)null);
    this.jPanel3.add(this.jLabel11, (Object)null);
    this.jPanel3.add(this.jComboBox3, (Object)null);
    this.jPanel4.add(this.jComboBox4, (Object)null);
    this.jPanel4.add(this.jLabel12, (Object)null);
    this.jPanel4.add(this.jComboBox5, (Object)null);
    this.jPanel4.add(this.jLabel13, (Object)null);
    this.jPanel4.add(this.jComboBox6, (Object)null);
    for (byte b1 = 0; b1 < 3; b1++)
      this.jComboBox4.addItem(String.valueOf(b1)); 
    for (byte b2 = 0; b2 < 24; b2++) {
      this.jComboBox2.addItem(String.valueOf(b2));
      this.jComboBox5.addItem(String.valueOf(b2));
    } 
    for (byte b3 = 0; b3 < 60; b3++) {
      this.jComboBox3.addItem(String.valueOf(b3));
      this.jComboBox6.addItem(String.valueOf(b3));
    } 
    this.thisTRName.add(ATPMessages.getString("MW.TR.ID"));
    this.thisTRName.add(ATPMessages.getString("MW.TR.EXECUTE_DATE"));
    this.thisTRName.add(ATPMessages.getString("MW.WS.TR_WORKDATE"));
    this.allTRName.add(ATPMessages.getString("MW.TR.ID"));
    this.allTRName.add(ATPMessages.getString("MW.TR.EXECUTE_DATE"));
    setSize(new Dimension(650, 500));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
  
  private void _$21111(String paramString, WorkDatetimeTransfer paramWorkDatetimeTransfer1, WorkDatetimeTransfer paramWorkDatetimeTransfer2) throws Exception {
    String str1 = paramString;
    String str2 = "0";
    WorkDatetimeTransfer workDatetimeTransfer1 = paramWorkDatetimeTransfer1;
    WorkDatetimeTransfer workDatetimeTransfer2 = paramWorkDatetimeTransfer2;
    StringBuffer stringBuffer = new StringBuffer();
    String str3 = "INSERT INTO WorkShift (WSNo,Begin_Time,End_Time,WSMotionDate) VALUES ('" + str1 + "','" + workDatetimeTransfer1.getTime() + "','" + workDatetimeTransfer2.getTime() + "','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "')";
    this.connDB.Insert(str3);
    for (byte b = 0; b <= this.jTable1.getRowCount() - 1; b++) {
      if (this.jTable1.getValueAt(b, 2) != null)
        str2 = this.jTable1.getValueAt(b, 2).toString(); 
      String str = "INSERT INTO WorkShift_TRList (WSNo,TRNo,FinishDate,WSMotionDate,TRMotionDate)VALUES('" + str1 + "','" + this.jTable1.getValueAt(b, 0) + "','" + str2 + "','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.jTable1.getValueAt(b, 1)) + "')";
      this.connDB.Insert(str);
      stringBuffer.append(",車次:");
      stringBuffer.append(this.jTable1.getValueAt(b, 0));
      stringBuffer.append("<>");
    } 
    String str4 = "工作班:" + str1 + ",開始時間:" + this.jTextField1.getText() + "-" + this.jComboBox2.getSelectedIndex() + ":" + this.jComboBox3.getSelectedIndex() + ",結束時間:" + this.jComboBox4.getSelectedIndex() + "-" + this.jComboBox5.getSelectedIndex() + ":" + this.jComboBox6.getSelectedIndex() + stringBuffer.toString();
    this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "工作班", "新增", str4);
  }
  
  void jComboBox4_itemStateChanged(ItemEvent paramItemEvent) {
    if (this.update)
      _$21110((JTable)this.jTable1, this.jTable1.getColumnModel().getColumn(2)); 
  }
  
  void jFormattedTextField1_focusLost(FocusEvent paramFocusEvent) {
    if (this.jFormattedTextField1.getText().length() == 0) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.WS.START_TIME.EMPTY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      this.jFormattedTextField1.requestFocusInWindow();
    } 
  }
  
  void jFormattedTextField2_focusLost(FocusEvent paramFocusEvent) {
    System.err.println(this.jFormattedTextField2.getText().length());
    if (this.jFormattedTextField2.getText().length() == 0)
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.WS.END_TIME.EMPTY"), ATPMessages.getString("MW.GNL.ERROR"), 0); 
  }
  
  private String _$21116(int paramInt) {
    StringBuffer stringBuffer = new StringBuffer(String.valueOf(paramInt));
    if (stringBuffer.length() < 2) {
      stringBuffer.insert(0, "0");
      return stringBuffer.toString();
    } 
    return stringBuffer.toString();
  }
  
  void reverse_actionPerformed(ActionEvent paramActionEvent) {
    this.vtemp = new Vector();
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.GNL.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      for (byte b = 0; b < this.jTable1.getRowCount(); b++) {
        if (!this.jTable1.isRowSelected(b))
          this.vtemp.addElement(this.thisTrainRunning.get(b)); 
      } 
      this.testModel.setDataVector(this.vtemp, this.thisTRName);
      _$21110((JTable)this.jTable1, this.jTable1.getColumnModel().getColumn(2));
      _$3558(this.vtemp);
    } 
  }
  
  private void _$21110(JTable paramJTable, TableColumn paramTableColumn) {
    int i = Integer.parseInt("0");
    int j = Integer.parseInt(this.jComboBox4.getSelectedItem().toString());
    JComboBox jComboBox = new JComboBox();
    for (byte b = 0; b <= j; b++)
      jComboBox.addItem(String.valueOf(b)); 
    paramTableColumn.setCellEditor(new DefaultCellEditor(jComboBox));
    DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
    defaultTableCellRenderer.setToolTipText(ATPMessages.getString("MW.WS.TR_NORKDATE.EMPTY"));
    paramTableColumn.setCellRenderer(defaultTableCellRenderer);
  }
  
  private String _$8112() {
    int i = this.vLateInMission.size();
    StringBuffer stringBuffer = new StringBuffer();
    for (byte b = 0; b < i; b++) {
      stringBuffer.append("司機員編號：");
      stringBuffer.append(((Vector)this.vLateInMission.get(b)).get(0));
      stringBuffer.append(",");
      stringBuffer.append("工作班執行日期：");
      stringBuffer.append((new SimpleDateFormat("yyyy/M/d")).format(((Vector)this.vLateInMission.get(b)).get(1)));
      stringBuffer.append(",");
      stringBuffer.append("工作班：");
      stringBuffer.append(((Vector)this.vLateInMission.get(b)).get(2));
      stringBuffer.append("\n");
    } 
    return stringBuffer.toString();
  }
  
  private void _$21101(int paramInt, Vector paramVector, String paramString) throws Exception {
    if (paramInt == 0) {
      this.testModel.setDataVector(paramVector, this.thisTRName);
    } else {
      String str = "SELECT TRNo, TRMotionDate, FinishDate FROM WorkShift_TRList WHERE WorkShift_TRList.WSNo = '" + paramString + "' " + "AND WSMotionDate in (SELECT MAX(WSMotionDate) AS WSMotionDate " + "FROM WorkShift_TRList " + "WHERE WSNo = '" + paramString + "' AND WSMotionDate <= '" + (new SimpleDateFormat("yyyy/M/d")).format(this.dateChooser.getDate()) + "' GROUP BY WSNo)";
      this.thisTrainRunning = this.connDB.getData(str);
      this.testModel.setDataVector(this.thisTrainRunning, this.thisTRName);
      _$21110((JTable)this.jTable1, this.jTable1.getColumnModel().getColumn(2));
    } 
  }
  
  void txtWSNo_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtWSNo_focusLost(FocusEvent paramFocusEvent) {
    if (!this.giveup) {
      if (this.txtWSNo.getText().length() == 0)
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.GNL.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0); 
      this.txtWSNo.selectAll();
      this.txtWSNo.requestFocusInWindow();
    } 
  }
  
  private void _$21112(String paramString, WorkDatetimeTransfer paramWorkDatetimeTransfer1, WorkDatetimeTransfer paramWorkDatetimeTransfer2) throws Exception {
    String str1 = paramString;
    WorkDatetimeTransfer workDatetimeTransfer1 = paramWorkDatetimeTransfer1;
    WorkDatetimeTransfer workDatetimeTransfer2 = paramWorkDatetimeTransfer2;
    StringBuffer stringBuffer = new StringBuffer();
    String str2 = "UPDATE WorkShift SET Begin_Time ='" + workDatetimeTransfer1.getTime() + "',End_Time ='" + workDatetimeTransfer2.getTime() + "' WHERE WSNo ='" + str1 + "' AND WSMotionDate ='" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "'";
    this.connDB.Insert(str2);
    String str3 = "DELETE WorkShift_TRList WHERE WSNo='" + this.txtWSNo.getText() + "' AND WSMotionDate ='" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "'";
    this.connDB.Delete(str3);
    for (byte b = 0; b < this.jTable1.getRowCount(); b++) {
      String str = "INSERT INTO WorkShift_TRList (WSNo,TRNo,FinishDate,WSMotionDate,TRMotionDate) VALUES('" + this.txtWSNo.getText() + "','" + this.jTable1.getValueAt(b, 0) + "','" + this.jTable1.getValueAt(b, 2) + "','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.jTable1.getValueAt(b, 1)) + "')";
      this.connDB.Insert(str);
      stringBuffer.append(",車次:");
      stringBuffer.append(this.jTable1.getValueAt(b, 0));
      stringBuffer.append("<>");
    } 
    String str4 = "工作班:" + str1 + ",開始時間:" + this.jTextField1.getText() + "-" + this.jComboBox2.getSelectedIndex() + ":" + this.jComboBox3.getSelectedIndex() + ",結束時間:" + this.jComboBox4.getSelectedIndex() + "-" + this.jComboBox5.getSelectedIndex() + ":" + this.jComboBox6.getSelectedIndex() + stringBuffer.toString();
    this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "工作班", "修改", str4);
  }
}
