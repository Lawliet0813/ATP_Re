package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.Tools.PasswordInputDialog;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.Tools.StringHandler;
import com.MiTAC.TRA.ATP.Tools.UpperCaseAndMaxLenghtDocument;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.frmMain;
import com.MiTAC.TRA.ATP.ui.frmTREdit_cancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmTREdit_comboLine_itemAdapter;
import com.MiTAC.TRA.ATP.ui.frmTREdit_confirm_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmTREdit_forward_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmTREdit_reverse_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmTREdit_txtTrainName_focusAdapter;
import com.MiTAC.TRA.ATP.ui.frmTREdit_txtTrainNo_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmTREdit_txtTrainNo_focusAdapter;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;

public class frmTREdit extends JDialog {
  Vector allStationName = new Vector();
  
  Vector allStopStation;
  
  BorderLayout borderLayout1 = new BorderLayout();
  
  BorderLayout borderLayout2 = new BorderLayout();
  
  JButton cancel = new JButton();
  
  JComboBox comboCategory = new JComboBox();
  
  JComboBox comboDriverName = new JComboBox();
  
  JComboBox comboLine = new JComboBox();
  
  JButton confirm = new JButton();
  
  ConnectDB connDB = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  JDateChooser dateChooser = new JDateChooser();
  
  private Date _$21250;
  
  Document doc = (Document)new UpperCaseAndMaxLenghtDocument(6);
  
  DefaultSortTableModel dtm2 = new DefaultSortTableModel();
  
  JButton forward = new JButton();
  
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  
  boolean insert = false;
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
  JLabel jLabel4 = new JLabel();
  
  JLabel jLabel6 = new JLabel();
  
  JLabel jLabel7 = new JLabel();
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel3 = new JPanel();
  
  JPanel jPanel4 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JScrollPane jScrollPane2 = new JScrollPane();
  
  JSortTable jTable1 = new JSortTable((SortTableModel)this.testModel);
  
  JSortTable jTable2 = new JSortTable((SortTableModel)this.dtm2);
  
  PasswordInputDialog pwdInputDialog;
  
  JButton reverse = new JButton();
  
  StringHandler strHandler;
  
  TestModel testModel = new TestModel(this);
  
  Vector thisStopStation = new Vector();
  
  Vector thisStopStationName = new Vector();
  
  JTextField txtTrainName = new JTextField();
  
  JTextField txtTrainNo = new JTextField();
  
  boolean update = false;
  
  private Vector _$21079 = new Vector();
  
  Vector vcmbdata;
  
  Vector vtemp = new Vector();
  
  XYLayout xYLayout1 = new XYLayout();
  
  public frmTREdit(Vector paramVector1, Vector paramVector2, Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    this.update = true;
    this.thisStopStation = paramVector2;
    try {
      init();
      this.txtTrainNo.setText(paramVector1.get(0).toString());
      this.txtTrainNo.setEnabled(false);
      this.txtTrainName.setEnabled(false);
      this.jLabel6.setVisible(false);
      this.txtTrainName.setVisible(false);
      comboLine();
      this.testModel.setDataVector(this.thisStopStation, this.thisStopStationName);
      TableColumn tableColumn1 = this.jTable1.getColumnModel().getColumn(2);
      tableColumn1.setCellEditor((TableCellEditor)new TimeEditor(this));
      tableColumn1.setCellRenderer((TableCellRenderer)new TimeRenderer(this));
      TableColumn tableColumn2 = this.jTable1.getColumnModel().getColumn(3);
      tableColumn2.setCellEditor((TableCellEditor)new TimeEditor(this));
      tableColumn2.setCellRenderer((TableCellRenderer)new TimeRenderer(this));
      _$21251(this.thisStopStation, this.allStopStation);
      this.dateChooser.setDate((Date)paramVector1.get(1));
      this._$21250 = (Date)paramVector1.get(1);
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public frmTREdit(Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    this.insert = true;
    try {
      init();
      comboLine();
      this.jLabel6.setVisible(false);
      this.txtTrainName.setVisible(false);
      this.testModel.setDataVector(this.thisStopStation, this.thisStopStationName);
      _$21251(this.thisStopStation, this.allStopStation);
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
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
  
  void comboLine() throws Exception {
    String str = "SELECT LineCName FROM Line";
    this.vcmbdata = this.connDB.getData(str);
    for (byte b = 0; b < this.vcmbdata.size(); b++)
      this.comboLine.addItem(((Vector)this.vcmbdata.get(b)).get(0)); 
  }
  
  void comboLine_itemStateChanged(ItemEvent paramItemEvent) {
    try {
      String str = "SELECT StationID,StationCName FROM Station WHERE LineBelong = (SELECT LineID FROM Line WHERE LineCName='" + this.comboLine.getSelectedItem() + "')";
      this.allStopStation = this.connDB.getData(str);
      _$21251(this.thisStopStation, this.allStopStation);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage().toString(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  private void _$21251(Vector paramVector1, Vector paramVector2) {
    this.vtemp = new Vector();
    for (byte b = 0; b <= paramVector2.size() - 1; b++) {
      byte b1 = 0;
      String str = ((Vector)paramVector2.get(b)).get(0).toString();
      for (byte b2 = 0; b2 < paramVector1.size(); b2++) {
        String str1 = ((Vector)paramVector1.get(b2)).get(0).toString();
        if (str.equals(str1))
          b1++; 
      } 
      if (b1 == 0)
        this.vtemp.addElement(paramVector2.get(b)); 
    } 
    this.dtm2.setDataVector(this.vtemp, this.allStationName);
  }
  
  void confirm_actionPerformed(ActionEvent paramActionEvent) {
    try {
      if (this.insert) {
        if (!_$20947(this.txtTrainNo.getText())) {
          JOptionPane.showMessageDialog(this, "您所輸人的車次編號格式錯誤,\n司機員員工編號只可輸人英文字母及阿拉伯數字,\n請重新輸入。", ATPMessages.getString("MW.GNL.ERROR"), 0);
          this.txtTrainNo.selectAll();
          this.txtTrainNo.requestFocus(true);
        } else {
          _$21257();
        } 
      } else if (this.update) {
        if ((new SimpleDateFormat("yyyy/M/d")).format(this._$21250).equals((new SimpleDateFormat("yyyy/M/d")).format(this.dateChooser.getDate()))) {
          _$21258();
        } else if (_$21113()) {
          int i = JOptionPane.showConfirmDialog(this, "因為啟用日期的變動，在派班記錄中將有下列資料會被刪除\n" + _$8112() + "，請重新將工作班內的車次內容調整為新啟用日期的車次後" + "，\n再重新派班。謝謝！" + "請確認是否要修改本車次。\n" + "是，將會執行刪除上述派班記錄內的資料", ATPMessages.getString("MW.GNL.ERROR"), 0, 3);
          if (i == 0) {
            this.pwdInputDialog = new PasswordInputDialog((Frame)getParent(), "密碼確認", true);
            this.pwdInputDialog.show();
            if (this.pwdInputDialog.isCancel()) {
              dispose();
            } else {
              _$15391();
              _$21257();
              dispose();
            } 
          } 
        } else {
          _$21257();
        } 
      } 
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      if (sQLException.getErrorCode() == 2627)
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TR.EXIST"), ATPMessages.getString("MW.GNL.ERROR"), 0); 
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  private String _$21262(String paramString) {
    String str = new String();
    null = new String();
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (Character.isDigit(c)) {
        null = null + c;
      } else if (Character.isLetter(c)) {
        str = str + c;
      } 
    } 
    null = StringHandler.fillLeft(null, "0", 4);
    return null + str;
  }
  
  private void _$15391() {
    int i = this._$21079.size();
    try {
      for (byte b = 0; b < i; b++) {
        String str = "DELETE FROM Mission WHERE (DriverID='" + ((Vector)this._$21079.get(b)).get(0) + "' AND WSNo = '" + ((Vector)this._$21079.get(b)).get(2) + "' AND WorkDate = '" + (new SimpleDateFormat("yyyy/M/d")).format(((Vector)this._$21079.get(b)).get(1)) + "')";
        this.connDB.Delete(str);
        this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "派班", "刪除", "司機員編號:" + ((Vector)this._$21079.get(b)).get(0) + ",工作班:" + ((Vector)this._$21079.get(b)).get(2) + ",工作班執行日期:" + (new SimpleDateFormat("yyyy/M/d")).format(((Vector)this._$21079.get(b)).get(1)) + ",車次:" + ((Vector)this._$21079.get(b)).get(3));
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void forward_actionPerformed(ActionEvent paramActionEvent) {
    try {
      Vector vector = new Vector();
      Vector vector1 = new Vector();
      if (this.jTable2.getSelectedRow() == -1) {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TR.NO_STATION"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      } else {
        for (byte b = 0; b < this.jTable2.getRowCount(); b++) {
          if (this.jTable2.isRowSelected(b)) {
            vector.addElement(this.jTable2.getValueAt(b, 0));
            vector.addElement(this.jTable2.getValueAt(b, 1));
            this.thisStopStation.addElement(vector);
            vector = new Vector();
          } else {
            vector1.addElement(this.vtemp.get(b));
          } 
        } 
        this.testModel.setDataVector(this.thisStopStation, this.thisStopStationName);
        TableColumn tableColumn1 = this.jTable1.getColumnModel().getColumn(2);
        tableColumn1.setCellRenderer((TableCellRenderer)new TimeRenderer(this));
        tableColumn1.setCellEditor((TableCellEditor)new TimeEditor(this));
        TableColumn tableColumn2 = this.jTable1.getColumnModel().getColumn(3);
        tableColumn2.setCellRenderer((TableCellRenderer)new TimeRenderer(this));
        tableColumn2.setCellEditor((TableCellEditor)new TimeEditor(this));
        _$21251(this.thisStopStation, this.allStopStation);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  private void _$21267() {}
  
  private String _$931(int paramInt1, int paramInt2) {
    return (this.testModel.getValueAt(paramInt1, paramInt2) != null) ? (String)this.testModel.getValueAt(paramInt1, paramInt2) : "";
  }
  
  private boolean _$21113() {
    boolean bool = false;
    try {
      String str = "SELECT DriverID,WorkDate,WSNo,TRNo FROM Mission WHERE TRNo = '" + this.txtTrainNo.getText() + "' AND WorkDate >='" + (new SimpleDateFormat("yyyy/M/d")).format(this.dateChooser.getDate()) + "'";
      this._$21079 = this.connDB.getData(str);
      if (this._$21079.isEmpty()) {
        bool = false;
      } else {
        bool = true;
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return bool;
  }
  
  void init() throws Exception {
    this.jLabel1.setRequestFocusEnabled(true);
    this.jLabel1.setText(ATPMessages.getString("MW.TR.ID"));
    getContentPane().setBackground(SystemColor.control);
    setResizable(false);
    getContentPane().setLayout((LayoutManager)this.xYLayout1);
    this.txtTrainNo.setBackground(Color.white);
    this.txtTrainNo.setEnabled(true);
    this.txtTrainNo.setDocument(this.doc);
    this.txtTrainNo.setEditable(true);
    this.txtTrainNo.setText("");
    this.txtTrainNo.setToolTipText(ATPMessages.getString("MW.TR.ID.TIP"));
    this.txtTrainNo.addFocusListener((FocusListener)new frmTREdit_txtTrainNo_focusAdapter(this));
    this.txtTrainNo.addActionListener((ActionListener)new frmTREdit_txtTrainNo_actionAdapter(this));
    this.jLabel2.setText(ATPMessages.getString("MW.TR.STOPSTATION_AND_ALTIME"));
    this.forward.setActionCommand("forward");
    this.forward.setText(ATPMessages.getString("MW.GNL.MOVE_LEFT"));
    this.forward.addActionListener((ActionListener)new frmTREdit_forward_actionAdapter(this));
    this.reverse.setText(ATPMessages.getString("MW.GNL.MOVE_RIGHT"));
    this.reverse.addActionListener((ActionListener)new frmTREdit_reverse_actionAdapter(this));
    this.jLabel3.setHorizontalTextPosition(11);
    this.jLabel3.setText(ATPMessages.getString("MW.STATION.ALL"));
    this.jLabel4.setBorder((Border)null);
    this.jLabel4.setHorizontalAlignment(0);
    this.jLabel4.setText(ATPMessages.getString("MW.LINE"));
    this.jPanel1.setLayout(this.gridBagLayout1);
    this.confirm.setHorizontalTextPosition(11);
    this.confirm.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.confirm.addActionListener((ActionListener)new frmTREdit_confirm_actionAdapter(this));
    this.cancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.cancel.addActionListener((ActionListener)new frmTREdit_cancel_actionAdapter(this));
    this.comboLine.addItemListener((ItemListener)new frmTREdit_comboLine_itemAdapter(this));
    this.txtTrainName.setBackground(Color.white);
    this.txtTrainName.setText("");
    this.txtTrainName.addFocusListener((FocusListener)new frmTREdit_txtTrainName_focusAdapter(this));
    this.jLabel7.setText(ATPMessages.getString("MW.TR.EXECUTE_DATE"));
    this.dateChooser.setDateFormatString("yyyy-MM-dd");
    this.dateChooser.setCalendar(new GregorianCalendar());
    this.jPanel3.setLayout(this.borderLayout1);
    this.jPanel4.setBorder((Border)null);
    this.jPanel4.setDebugGraphicsOptions(0);
    this.jPanel4.setLayout(this.borderLayout2);
    this.jPanel1.setBorder(BorderFactory.createLoweredBevelBorder());
    getContentPane().add(this.txtTrainNo, new XYConstraints(65, 30, 85, -1));
    getContentPane().add(this.jLabel1, new XYConstraints(30, 30, -1, -1));
    this.jPanel1.add(this.comboLine, new GridBagConstraints(1, 0, 1, 1, 1.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 121, 0));
    this.jPanel1.add(this.jScrollPane2, new GridBagConstraints(0, 1, 2, 1, 1.0D, 1.0D, 10, 1, new Insets(0, 0, 0, 0), -262, -199));
    this.jPanel1.add(this.jLabel4, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 8, 5));
    this.jScrollPane2.getViewport().add((Component)this.jTable2, (Object)null);
    getContentPane().add(this.jPanel3, new XYConstraints(350, 100, 230, 300));
    this.jPanel3.add(this.jPanel1, "Center");
    getContentPane().add(this.jLabel6, new XYConstraints(170, 30, -1, -1));
    getContentPane().add(this.txtTrainName, new XYConstraints(200, 30, 85, -1));
    getContentPane().add(this.jLabel7, new XYConstraints(305, 30, -1, -1));
    getContentPane().add((Component)this.dateChooser, new XYConstraints(380, 30, 100, -1));
    getContentPane().add(this.confirm, new XYConstraints(430, 430, -1, -1));
    getContentPane().add(this.cancel, new XYConstraints(500, 430, -1, -1));
    getContentPane().add(this.reverse, new XYConstraints(305, 160, -1, -1));
    getContentPane().add(this.forward, new XYConstraints(305, 120, -1, -1));
    getContentPane().add(this.jLabel3, new XYConstraints(350, 80, -1, -1));
    getContentPane().add(this.jLabel2, new XYConstraints(20, 80, -1, -1));
    getContentPane().add(this.jPanel4, new XYConstraints(20, 100, 280, 300));
    this.jPanel4.add(this.jScrollPane1, "Center");
    this.jScrollPane1.getViewport().add((Component)this.jTable1, (Object)null);
    this.allStationName.add(ATPMessages.getString("MW.STATION.ID"));
    this.allStationName.add(ATPMessages.getString("MW.STATION.NAME"));
    this.thisStopStationName.add(ATPMessages.getString("MW.STATION.ID"));
    this.thisStopStationName.add(ATPMessages.getString("MW.STATION.NAME"));
    this.thisStopStationName.add(ATPMessages.getString("MW.TR.ARRIVE_TIME"));
    this.thisStopStationName.add(ATPMessages.getString("MW.TR.LEAVE_TIME"));
    setSize(new Dimension(600, 500));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
  
  private void _$21257() throws Exception {
    StringBuffer stringBuffer = new StringBuffer();
    if (!_$21259(this.txtTrainNo.getText())) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TR.ID_SUGGEST"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      this.txtTrainNo.selectAll();
      this.txtTrainNo.requestFocusInWindow();
    } else if (!_$21260(this.txtTrainNo.getText())) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TR.ID_SUGGEST"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      this.txtTrainNo.selectAll();
      this.txtTrainNo.requestFocusInWindow();
    } else if (this.txtTrainNo.getText().length() == 0) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.TR.ID_EMPTY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      this.txtTrainNo.selectAll();
      this.txtTrainNo.requestFocusInWindow();
    } else {
      String str1 = "INSERT INTO TrainRunning (TRNo,TRName,TRMotionDate) VALUES('" + _$21262(this.txtTrainNo.getText()) + "','" + this.txtTrainName.getText() + "','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "')";
      this.connDB.Insert(str1);
      for (byte b = 0; b < this.jTable1.getRowCount(); b++) {
        String str = "INSERT INTO TrainStopStation (TRNo,StationID,Arrival_Time,Leave_Time,TRMotionDate) VALUES('" + _$21262(this.txtTrainNo.getText()) + "','" + _$931(b, 0) + "','" + _$931(b, 2) + "','" + _$931(b, 3) + "','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "')";
        this.connDB.Insert(str);
        stringBuffer.append("車站代碼:");
        stringBuffer.append(((Vector)this.thisStopStation.get(b)).get(0));
        stringBuffer.append(",車站名稱:");
        stringBuffer.append(((Vector)this.thisStopStation.get(b)).get(1));
        stringBuffer.append("<>");
      } 
      String str2 = "車次:" + _$21262(this.txtTrainNo.getText()) + "," + stringBuffer.toString();
      this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "車次", "新增", str2);
      dispose();
    } 
  }
  
  private boolean _$21260(String paramString) {
    byte b1 = 0;
    boolean bool = true;
    for (byte b2 = 0; b2 < paramString.length(); b2++) {
      char c = paramString.charAt(b2);
      if (Character.isDigit(c))
        b1++; 
    } 
    if (b1 > 4)
      bool = false; 
    return bool;
  }
  
  private boolean _$21259(String paramString) {
    byte b1 = 0;
    boolean bool = true;
    String str = "";
    for (byte b2 = 0; b2 < paramString.length(); b2++) {
      char c = paramString.charAt(b2);
      if (Character.isLetter(c))
        if (b2 == 0) {
          bool = false;
        } else {
          str = paramString.substring(b2, paramString.length());
          break;
        }  
    } 
    for (byte b3 = 0; b3 < str.length(); b3++) {
      char c = str.charAt(b3);
      if (Character.isDigit(c)) {
        bool = false;
      } else {
        b1++;
      } 
    } 
    if (b1 > 2)
      bool = false; 
    return bool;
  }
  
  public void refresh() throws Exception {
    String str = "SELECT StationID,StationCName FROM Station WHERE LineBelong = (SELECT LineID FROM Line WHERE LineCName='" + this.comboLine.getSelectedItem() + "')";
    this.allStopStation = this.connDB.getData(str);
    this.dtm2.setDataVector(this.allStopStation, this.allStationName);
    _$21251(new Vector(), this.allStopStation);
  }
  
  void reverse_actionPerformed(ActionEvent paramActionEvent) {
    Vector vector = (Vector)this.thisStopStation.clone();
    this.thisStopStation = new Vector();
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.GNL.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      for (byte b = 0; b < this.jTable1.getRowCount(); b++) {
        if (!this.jTable1.isRowSelected(b))
          this.thisStopStation.addElement(vector.get(b)); 
      } 
      this.testModel.setDataVector(this.thisStopStation, this.thisStopStationName);
      _$21251(this.thisStopStation, this.allStopStation);
      TableColumn tableColumn1 = this.jTable1.getColumnModel().getColumn(2);
      tableColumn1.setCellRenderer((TableCellRenderer)new TimeRenderer(this));
      tableColumn1.setCellEditor((TableCellEditor)new TimeEditor(this));
      TableColumn tableColumn2 = this.jTable1.getColumnModel().getColumn(3);
      tableColumn2.setCellRenderer((TableCellRenderer)new TimeRenderer(this));
      tableColumn2.setCellEditor((TableCellEditor)new TimeEditor(this));
    } 
  }
  
  private String _$8112() {
    int i = this._$21079.size();
    StringBuffer stringBuffer = new StringBuffer();
    for (byte b = 0; b < i; b++) {
      stringBuffer.append("司機員編號：");
      stringBuffer.append(((Vector)this._$21079.get(b)).get(0));
      stringBuffer.append(",");
      stringBuffer.append("工作班執行日期：");
      stringBuffer.append((new SimpleDateFormat("yyyy/M/d")).format(((Vector)this._$21079.get(b)).get(1)));
      stringBuffer.append(",");
      stringBuffer.append("工作班：");
      stringBuffer.append(((Vector)this._$21079.get(b)).get(2));
      stringBuffer.append(",");
      stringBuffer.append("車次：");
      stringBuffer.append(((Vector)this._$21079.get(b)).get(3));
      stringBuffer.append("\n");
    } 
    return stringBuffer.toString();
  }
  
  void txtTrainName_focusLost(FocusEvent paramFocusEvent) {}
  
  void txtTrainNo_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  void txtTrainNo_focusLost(FocusEvent paramFocusEvent) {}
  
  private void _$21258() throws Exception {
    StringBuffer stringBuffer = new StringBuffer();
    String str = "DELETE TrainStopStation WHERE TRNo='" + this.txtTrainNo.getText().toUpperCase() + "'" + "AND TRMotionDate = '" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "'";
    this.connDB.Delete(str);
    for (byte b = 0; b < this.jTable1.getRowCount(); b++) {
      String str1 = "INSERT INTO TrainStopStation (TRNo,StationID,Arrival_Time,Leave_Time,TRMotionDate) VALUES('" + this.txtTrainNo.getText().toUpperCase() + "','" + _$931(b, 0) + "','" + _$931(b, 2) + "','" + _$931(b, 3) + "','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "')";
      this.connDB.Insert(str1);
      stringBuffer.append("車站代碼:");
      stringBuffer.append(((Vector)this.thisStopStation.get(b)).get(0));
      stringBuffer.append(",車站名稱:");
      stringBuffer.append(((Vector)this.thisStopStation.get(b)).get(1));
      stringBuffer.append("<>");
    } 
    this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "車次", "修改", stringBuffer.toString());
    dispose();
  }
}
