package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.Tools.DateRenderer;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.MultiLineCellRenderer;
import com.MiTAC.TRA.ATP.Tools.SortTable.MultiLineHeaderRenderer;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.Tools.StringHandler;
import com.MiTAC.TRA.ATP.Tools.WorkDatetimeTransfer;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.TrainDataMapping;
import com.MiTAC.TRA.ATP.ui.frmMain;
import com.MiTAC.TRA.ATP.ui.frmMissionData_cancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmMissionData_confirm_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmMissionData_dateChooser_propertyChangeAdapter;
import com.MiTAC.TRA.ATP.ui.frmMissionData_jButton1_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmMissionData_jcbDriverNo_itemAdapter;
import com.MiTAC.TRA.ATP.ui.frmMissionData_jcbWorkShiftNo_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmMissionData_jcbWorkShiftNo_itemAdapter;
import com.MiTAC.TRA.ATP.ui.frmMissionData_tbTrainRunning_mouseAdapter;
import com.MiTAC.TRA.ATP.ui.frmMissionData_tbTrainType_mouseAdapter;
import com.MiTAC.TRA.ATP.ui.pnlMissionEdit;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.toedter.calendar.JCalendar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import src.com.toedter.calendar.JDateChooser;

public class frmMissionData extends JDialog {
  private Vector _$2493 = new Vector();
  
  private Vector _$2491 = new Vector();
  
  Date basicDay;
  
  BorderLayout borderLayout1 = new BorderLayout();
  
  JCalendar calendar = new JCalendar();
  
  JButton cancel = new JButton();
  
  JButton confirm = new JButton();
  
  ConnectDB connDB = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  JDateChooser dateChooser = new JDateChooser();
  
  private Vector _$10922 = new Vector();
  
  String driverIDNo;
  
  DefaultSortTableModel dtm1 = new DefaultSortTableModel();
  
  DefaultSortTableModel dtm2 = new DefaultSortTableModel();
  
  DefaultSortTableModel dtm3 = new DefaultSortTableModel();
  
  DefaultSortTableModel dtm4 = new DefaultSortTableModel();
  
  private int _$10939 = 0;
  
  boolean insert = false;
  
  JButton jButton1 = new JButton();
  
  JFormattedTextField jFormattedTextField1;
  
  JFormattedTextField jFormattedTextField2;
  
  JLabel jLabel10 = new JLabel();
  
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
  
  JPanel jPanel5 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JScrollPane jScrollPane2 = new JScrollPane();
  
  JScrollPane jScrollPane3 = new JScrollPane();
  
  JScrollPane jScrollPane4 = new JScrollPane();
  
  JComboBox jcbDriverNo = new JComboBox();
  
  JComboBox jcbVehicleID = new JComboBox();
  
  JComboBox jcbWorkShiftNo = new JComboBox();
  
  JLabel lbDriverID = new JLabel();
  
  MaskFormatter mf1;
  
  MaskFormatter mf2;
  
  private NumberFormat _$10936;
  
  Date oldWorkDate;
  
  private Date _$10938;
  
  pnlMissionEdit pnlmedit;
  
  private String _$10935;
  
  private String _$10941 = new String();
  
  private String _$10934;
  
  private String _$10940 = new String();
  
  private Vector _$10918;
  
  private Vector _$10919 = new Vector();
  
  JTable tbStopStation = new JTable((TableModel)this.dtm1);
  
  JSortTable tbTrainCategory = new JSortTable((SortTableModel)this.dtm4);
  
  JSortTable tbTrainRunning = new JSortTable((SortTableModel)this.dtm2);
  
  JSortTable tbTrainType = new JSortTable((SortTableModel)this.dtm3);
  
  TitledBorder titledBorder1;
  
  TitledBorder titledBorder2;
  
  TitledBorder titledBorder3;
  
  Date today;
  
  private Vector _$10913;
  
  private Vector _$10915 = new Vector();
  
  private Vector _$10914;
  
  boolean update = false;
  
  boolean useNewWorkShift = false;
  
  private Vector _$10911;
  
  private Vector _$2494;
  
  private Vector _$10917;
  
  private Vector _$10916;
  
  private Vector _$10921;
  
  private Vector _$10920;
  
  String workShift;
  
  private Vector _$10912;
  
  XYLayout xYLayout1 = new XYLayout();
  
  XYLayout xYLayout2 = new XYLayout();
  
  XYLayout xYLayout3 = new XYLayout();
  
  XYLayout xYLayout4 = new XYLayout();
  
  public frmMissionData(Vector paramVector, Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    this.update = true;
    this._$2494 = paramVector;
    try {
      this.today = new Date(System.currentTimeMillis());
      this.basicDay = new Date(System.currentTimeMillis());
      init();
      this._$10936 = NumberFormat.getInstance();
      this._$10936.setMaximumFractionDigits(3);
      this.jcbWorkShiftNo.setEnabled(false);
      this.jLabel9.setVisible(true);
      this.jLabel10.setVisible(true);
      this.dateChooser.setDate(paramVector.get(2));
      _$10944();
      this.jcbDriverNo.setSelectedItem(paramVector.get(0).toString() + "(" + paramVector.get(1) + ")");
      this.driverIDNo = (String)paramVector.get(0);
      _$10945();
      this.jcbWorkShiftNo.setSelectedItem(paramVector.get(3).toString());
      this.workShift = paramVector.get(3).toString();
      this.oldWorkDate = paramVector.get(2);
      _$10946();
      _$10947(1);
      String str = "SELECT * FROM WorkShift WHERE WSNo = '" + (String)paramVector.get(3) + "'";
      this._$10921 = this.connDB.getData(str);
      this._$10938 = _$10951(paramVector.get(2), paramVector.get(3).toString());
      this._$10939 = 1;
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  public frmMissionData(Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    this.insert = true;
    try {
      this.today = new Date(System.currentTimeMillis());
      this.basicDay = new Date(System.currentTimeMillis());
      init();
      this._$10936 = NumberFormat.getInstance();
      this._$10936.setMaximumFractionDigits(3);
      this.jLabel9.setVisible(true);
      this.jLabel10.setVisible(true);
      this.dateChooser.setDate(this.today);
      _$10944();
      _$10945();
      _$10946();
      _$10947(0);
      this._$10939 = 1;
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void cancel_actionPerformed(ActionEvent paramActionEvent) {
    if (this.update == true) {
      int i = JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.ME.MODIFY.Q.CANCEL"), ATPMessages.getString("MW.GNL.ASK"), 0, 3);
      if (i == 0)
        dispose(); 
    } else {
      dispose();
    } 
  }
  
  private Vector _$11005() {
    Vector vector = new Vector();
    for (byte b = 0; b < this.dtm2.getRowCount(); b++) {
      if (this.dateChooser.getDate().after((Date)this.dtm2.getValueAt(b, 3))) {
        vector.addElement(String.valueOf(b));
        break;
      } 
    } 
    return vector;
  }
  
  void confirm_actionPerformed(ActionEvent paramActionEvent) {
    try {
      StringBuffer stringBuffer = new StringBuffer();
      if (this.insert) {
        if (_$10997()) {
          JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.ME.NEW.CANNOT_EMPTY"), ATPMessages.getString("MW.GNL.ERROR"), 0);
        } else {
          for (byte b = 0; b < this.dtm2.getRowCount(); b++) {
            String str = "INSERT INTO Mission (DriverID,RunningDate,WSNo,TRNo,TrainSN,TrainType,VehicleID,WorkDate,WSMotionDate,TRMotionDate)VALUES('" + _$11000(this.jcbDriverNo.getSelectedItem().toString()) + "','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.tbTrainRunning.getValueAt(b, 3)) + "','" + this.jcbWorkShiftNo.getSelectedItem() + "','" + this.tbTrainRunning.getValueAt(b, 0) + "'," + this.tbTrainRunning.getValueAt(b, 2) + "," + this.tbTrainRunning.getValueAt(b, 1) + ",'spare','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "','" + this._$10934 + "','" + _$10968(this.tbTrainRunning.getValueAt(b, 0).toString()) + "')";
            this.connDB.Insert(str);
            stringBuffer.append("司機員員工編號:");
            stringBuffer.append(_$11000(this.jcbDriverNo.getSelectedItem().toString()));
            stringBuffer.append(",工作班:");
            stringBuffer.append(this.jcbWorkShiftNo.getSelectedItem());
            stringBuffer.append(",工作班執行日期:");
            stringBuffer.append((new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()));
            stringBuffer.append(",車次:");
            stringBuffer.append(this.tbTrainRunning.getValueAt(b, 0));
            stringBuffer.append(",車種:");
            stringBuffer.append(this.tbTrainRunning.getValueAt(b, 1));
            stringBuffer.append(",列車編組:");
            stringBuffer.append(this.tbTrainRunning.getValueAt(b, 2));
            stringBuffer.append(",車次執行日期:");
            stringBuffer.append((new SimpleDateFormat("yyyy-MM-dd")).format(this.tbTrainRunning.getValueAt(b, 3)));
            stringBuffer.append("<>");
          } 
          this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), "派班", "派班", "新增", stringBuffer.toString());
          dispose();
        } 
      } else {
        String str1 = this.driverIDNo;
        String str2 = this.workShift;
        Date date = this.oldWorkDate;
        String str3 = "";
        if (_$11005().size() != 0) {
          JOptionPane.showMessageDialog(this, "您所輸入車次:" + this.dtm2.getValueAt(Integer.parseInt(_$11005().get(0).toString()), 0) + "的執行日期為:" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dtm2.getValueAt(Integer.parseInt(_$11005().get(0).toString()), 3)) + "，不可比工作班的執行日期:" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "早，請再次確認！！！", ATPMessages.getString("MW.GNL.ERROR"), 0);
        } else if (this.useNewWorkShift) {
          for (byte b = 0; b < this.dtm2.getRowCount(); b++) {
            String str = "INSERT INTO Mission (DriverID,RunningDate,WSNo,TRNo,TrainSN,TrainType,VehicleID,WorkDate,WSMotionDate,TRMotionDate)VALUES('" + _$11000(this.jcbDriverNo.getSelectedItem().toString()) + "','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.tbTrainRunning.getValueAt(b, 3)) + "','" + this.jcbWorkShiftNo.getSelectedItem() + "','" + this.tbTrainRunning.getValueAt(b, 0) + "'," + this.tbTrainRunning.getValueAt(b, 2) + "," + this.tbTrainRunning.getValueAt(b, 1) + ",'spare','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "','" + this._$10934 + "','" + _$10968(this.tbTrainRunning.getValueAt(b, 0).toString()) + "')";
            this.connDB.Insert(str);
            stringBuffer.append("司機員員工編號:");
            stringBuffer.append(_$11000(this.jcbDriverNo.getSelectedItem().toString()));
            stringBuffer.append(",工作班:");
            stringBuffer.append(this.jcbWorkShiftNo.getSelectedItem());
            stringBuffer.append(",工作班執行日期:");
            stringBuffer.append((new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()));
            stringBuffer.append(",車次:");
            stringBuffer.append(this.tbTrainRunning.getValueAt(b, 0));
            stringBuffer.append(",車種:");
            stringBuffer.append(this.tbTrainRunning.getValueAt(b, 1));
            stringBuffer.append(",列車編組:");
            stringBuffer.append(this.tbTrainRunning.getValueAt(b, 2));
            stringBuffer.append(",車次執行日期:");
            stringBuffer.append((new SimpleDateFormat("yyyy-MM-dd")).format(this.tbTrainRunning.getValueAt(b, 3)));
            stringBuffer.append("<>");
          } 
          this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), "派班", "派班", "修改", stringBuffer.toString());
          dispose();
        } else {
          if (this.driverIDNo.equals(_$11000(this.jcbDriverNo.getSelectedItem().toString())) && this.workShift.equals(this.jcbWorkShiftNo.getSelectedItem().toString()) && this.oldWorkDate.equals(this.dateChooser.getDate())) {
            for (byte b = 0; b < this.dtm2.getRowCount(); b++) {
              String str = "UPDATE Mission SET TrainSN = '" + this.tbTrainRunning.getValueAt(b, 2) + "',TrainType ='" + this.tbTrainRunning.getValueAt(b, 1) + "',RunningDate='" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.tbTrainRunning.getValueAt(b, 3)) + "' WHERE DriverID ='" + _$11000(this.jcbDriverNo.getSelectedItem().toString()) + "' AND WSNo ='" + this.jcbWorkShiftNo.getSelectedItem() + "' AND WorkDate ='" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "' AND TRNo ='" + this.tbTrainRunning.getValueAt(b, 0) + "'";
              this.connDB.Update(str);
              stringBuffer.append("司機員員工編號:");
              stringBuffer.append(_$11000(this.jcbDriverNo.getSelectedItem().toString()));
              stringBuffer.append(",工作班:");
              stringBuffer.append(this.jcbWorkShiftNo.getSelectedItem());
              stringBuffer.append(",工作班執行日期:");
              stringBuffer.append((new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()));
              stringBuffer.append(",車次:");
              stringBuffer.append(this.tbTrainRunning.getValueAt(b, 0));
              stringBuffer.append(",車種:");
              stringBuffer.append(this.tbTrainRunning.getValueAt(b, 1));
              stringBuffer.append(",列車編組:");
              stringBuffer.append(this.tbTrainRunning.getValueAt(b, 2));
              stringBuffer.append(",車次執行日期:");
              stringBuffer.append((new SimpleDateFormat("yyyy-MM-dd")).format(this.tbTrainRunning.getValueAt(b, 3)));
              stringBuffer.append("<>");
            } 
          } else {
            for (byte b = 0; b < this.dtm2.getRowCount(); b++) {
              String str = "INSERT INTO Mission (DriverID,RunningDate,WSNo,TRNo,TrainSN,TrainType,VehicleID,WorkDate,WSMotionDate,TRMotionDate)VALUES('" + _$11000(this.jcbDriverNo.getSelectedItem().toString()) + "','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.tbTrainRunning.getValueAt(b, 3)) + "','" + this.jcbWorkShiftNo.getSelectedItem() + "','" + this.tbTrainRunning.getValueAt(b, 0) + "'," + this.tbTrainRunning.getValueAt(b, 2) + "," + this.tbTrainRunning.getValueAt(b, 1) + ",'spare','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "','" + this._$10934 + "','" + _$10968(this.tbTrainRunning.getValueAt(b, 0).toString()) + "')";
              this.connDB.Insert(str);
              stringBuffer.append("司機員員工編號:");
              stringBuffer.append(_$11000(this.jcbDriverNo.getSelectedItem().toString()));
              stringBuffer.append(",工作班:");
              stringBuffer.append(this.jcbWorkShiftNo.getSelectedItem());
              stringBuffer.append(",工作班執行日期:");
              stringBuffer.append((new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()));
              stringBuffer.append(",車次:");
              stringBuffer.append(this.tbTrainRunning.getValueAt(b, 0));
              stringBuffer.append(",車種:");
              stringBuffer.append(this.tbTrainRunning.getValueAt(b, 1));
              stringBuffer.append(",列車編組:");
              stringBuffer.append(this.tbTrainRunning.getValueAt(b, 2));
              stringBuffer.append(",車次執行日期:");
              stringBuffer.append((new SimpleDateFormat("yyyy-MM-dd")).format(this.tbTrainRunning.getValueAt(b, 3)));
              stringBuffer.append("<>");
            } 
          } 
          this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), "派班", "派班", "修改", stringBuffer.toString());
          dispose();
        } 
      } 
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      if (sQLException.getErrorCode() == 2627)
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.ME.DATA_EXIST"), ATPMessages.getString("MW.GNL.ERROR"), 0); 
    } catch (NullPointerException nullPointerException) {
      try {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b = 0; b < this.dtm2.getRowCount(); b++) {
          String str = "INSERT INTO Mission (DriverID,RunningDate,WSNo,TRNo,TrainSN,TrainType,VehicleID,WorkDate,WSMotionDate,TRMotionDate)VALUES('" + _$11000(this.jcbDriverNo.getSelectedItem().toString()) + "','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.tbTrainRunning.getValueAt(b, 3)) + "','" + this.jcbWorkShiftNo.getSelectedItem() + "','" + this.tbTrainRunning.getValueAt(b, 0) + "'," + this.tbTrainRunning.getValueAt(b, 2) + "," + this.tbTrainRunning.getValueAt(b, 1) + ",'spare','" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "','" + this._$10934 + "','" + _$10968(this.tbTrainRunning.getValueAt(b, 0).toString()) + "')";
          this.connDB.Insert(str);
          stringBuffer.append("司機員員工編號:");
          stringBuffer.append(_$11000(this.jcbDriverNo.getSelectedItem().toString()));
          stringBuffer.append(",工作班:");
          stringBuffer.append(this.jcbWorkShiftNo.getSelectedItem());
          stringBuffer.append(",工作班執行日期:");
          stringBuffer.append((new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()));
          stringBuffer.append(",車次:");
          stringBuffer.append(this.tbTrainRunning.getValueAt(b, 0));
          stringBuffer.append(",車種:");
          stringBuffer.append(this.tbTrainRunning.getValueAt(b, 1));
          stringBuffer.append(",列車編組:");
          stringBuffer.append(this.tbTrainRunning.getValueAt(b, 2));
          stringBuffer.append(",車次執行日期:");
          stringBuffer.append((new SimpleDateFormat("yyyy-MM-dd")).format(this.tbTrainRunning.getValueAt(b, 3)));
          stringBuffer.append("<>");
        } 
        this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), "派班", "派班", "新增", stringBuffer.toString());
        dispose();
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void dateChooser_mouseClicked(MouseEvent paramMouseEvent) {}
  
  void dateChooser_propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    try {
      if (this.insert) {
        if (this._$10939 != 0)
          if ((new SimpleDateFormat("yyyy/M/d")).parse((new SimpleDateFormat("yyyy/M/d")).format(this.basicDay)).after((new SimpleDateFormat("yyyy/M/d")).parse((new SimpleDateFormat("yyyy/M/d")).format(this.dateChooser.getDate())))) {
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.ME.DATE.ERROR.OVERDUE"), ATPMessages.getString("MW.ME.DATE_ERROR"), 0);
            this.dateChooser.setDate(this.today);
            _$10984();
            _$10972();
            this._$10934 = _$10965(this.jcbWorkShiftNo.getSelectedItem().toString());
          } else {
            this._$10940 = this.jcbWorkShiftNo.getSelectedItem().toString();
            _$10945();
            _$10972();
            this._$10934 = _$10965(this.jcbWorkShiftNo.getSelectedItem().toString());
          }  
      } else if (this._$10939 != 0) {
        Date date = _$10951(this.dateChooser.getDate(), this.jcbWorkShiftNo.getSelectedItem().toString());
        String str = "SELECT DISTINCT WSNo,MAX(WSMotionDate) FROM WorkShift_TRList WHERE WSMotionDate <='" + (new SimpleDateFormat("yyyy/M/d")).format(this.dateChooser.getDate()) + "' and WSNo = '" + this.jcbWorkShiftNo.getSelectedItem().toString() + "' GROUP BY WSNo";
        Vector vector = this.connDB.getData(str);
        if ((new SimpleDateFormat("yyyy/M/d")).parse((new SimpleDateFormat("yyyy/M/d")).format(this._$10938)).before((new SimpleDateFormat("yyyy/M/d")).parse((new SimpleDateFormat("yyyy/M/d")).format(date)))) {
          JOptionPane.showMessageDialog(this, ATPMessages.getString("此工作班發現有新啟用日期的相關資料，即將套用"), ATPMessages.getString("MW.ME.DATE_INFO"), 1);
          Object object = null;
          this.useNewWorkShift = true;
          _$10984();
          _$10972();
          this._$10934 = _$10965(this.jcbWorkShiftNo.getSelectedItem().toString());
        } else {
          _$10984();
          _$10978();
          this._$10934 = _$10965(this.jcbWorkShiftNo.getSelectedItem().toString());
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private String _$11000(String paramString) {
    StringBuffer stringBuffer = new StringBuffer();
    for (byte b = 0; b < paramString.length() && paramString.charAt(b) != '('; b++)
      stringBuffer.append(paramString.charAt(b)); 
    return stringBuffer.toString();
  }
  
  private void _$10984() {
    try {
      Vector vector = new Vector();
      Vector vector1 = new Vector();
      Vector vector2 = new Vector();
      String str1 = "SELECT MAX(WSMotionDate) FROM WorkShift WHERE WSNo = '" + this.jcbWorkShiftNo.getSelectedItem() + "' AND WSMotionDate <='" + (new SimpleDateFormat("yyyy/M/d")).format(this.dateChooser.getDate()) + "'";
      vector2 = this.connDB.getData(str1);
      String str2 = "SELECT Begin_Time FROM WorkShift WHERE WSNo = '" + this.jcbWorkShiftNo.getSelectedItem() + "' AND WSMotionDate ='" + (new SimpleDateFormat("yyyy/M/d")).format(((Vector)vector2.get(0)).get(0)) + "'";
      vector = this.connDB.getData(str2);
      String str3 = "SELECT Begin_Time,End_Time FROM WorkShift WHERE WSNo = '" + this.jcbWorkShiftNo.getSelectedItem() + "' AND Begin_Time ='" + ((Vector)vector.get(0)).get(0) + "'";
      vector1 = this.connDB.getData(str3);
      vector1 = this.connDB.getData(str3);
      long l = 0L;
      StringBuffer stringBuffer1 = new StringBuffer((new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()));
      l = Integer.parseInt(((Vector)vector1.get(0)).get(0).toString());
      stringBuffer1 = _$10981(l);
      stringBuffer1.append("-" + StringHandler.fillLeft(String.valueOf((new WorkDatetimeTransfer(Integer.parseInt(((Vector)vector1.get(0)).get(0).toString()))).getHour()), "0", 2));
      stringBuffer1.append(":" + StringHandler.fillLeft(String.valueOf((new WorkDatetimeTransfer(Integer.parseInt(((Vector)vector1.get(0)).get(0).toString()))).getMinutes()), "0", 2));
      this.jFormattedTextField1.setText(stringBuffer1.toString());
      StringBuffer stringBuffer2 = new StringBuffer((new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()));
      l = Integer.parseInt(((Vector)vector1.get(0)).get(1).toString());
      stringBuffer2 = _$10981(l);
      stringBuffer2.append("-" + StringHandler.fillLeft(String.valueOf((new WorkDatetimeTransfer(Integer.parseInt(((Vector)vector1.get(0)).get(1).toString()))).getHour()), "0", 2));
      stringBuffer2.append(":" + StringHandler.fillLeft(String.valueOf((new WorkDatetimeTransfer(Integer.parseInt(((Vector)vector1.get(0)).get(1).toString()))).getMinutes()), "0", 2));
      this.jFormattedTextField2.setText(stringBuffer2.toString());
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private String _$2592(String paramString) {
    WorkDatetimeTransfer workDatetimeTransfer = new WorkDatetimeTransfer(Integer.parseInt(paramString));
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(workDatetimeTransfer.getDay());
    stringBuffer.append("-");
    stringBuffer.append(StringHandler.fillLeft("" + workDatetimeTransfer.getHour(), "0", 2));
    stringBuffer.append(":");
    stringBuffer.append(StringHandler.fillLeft("" + workDatetimeTransfer.getMinutes(), "0", 2));
    return stringBuffer.toString();
  }
  
  private void _$10944() throws Exception {
    String str = "SELECT Driver_ID + '(' + Name + ')' FROM Driver_INFO ORDER BY Name";
    this._$10911 = this.connDB.getData(str);
    this.jcbDriverNo.removeAllItems();
    for (byte b = 0; b < this._$10911.size(); b++)
      this.jcbDriverNo.addItem(((Vector)this._$10911.get(b)).get(0)); 
  }
  
  private Date _$10951(Date paramDate, String paramString) {
    Vector vector = new Vector();
    try {
      String str = "SELECT DISTINCT WSNo,MAX(WSMotionDate) FROM WorkShift_TRList WHERE WSMotionDate <='" + (new SimpleDateFormat("yyyy/M/d")).format(paramDate) + "' and WSNo = '" + paramString + "' GROUP BY WSNo";
      vector = this.connDB.getData(str);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return ((Vector)vector.get(0)).get(1);
  }
  
  private void _$10995() {
    try {
      String str = null;
      GregorianCalendar gregorianCalendar = new GregorianCalendar();
      this._$10913 = new Vector();
      str = "SELECT TRNo,FinishDate FROM WorkShift_TRList WHERE WSNo ='" + this.jcbWorkShiftNo.getSelectedItem() + "' AND WSMotionDate IN " + " (SELECT MAX(WSMotionDate) AS WSMotionDate FROM WorkShift WHERE WSNo = '" + this.jcbWorkShiftNo.getSelectedItem() + "' AND WSMotionDate <= '" + (new SimpleDateFormat("yyyy/MM/dd")).format(this.dateChooser.getDate()) + "' GROUP BY WSNo)";
      this._$10913 = this.connDB.getData(str);
      this._$10914 = this.connDB.getData(str);
      int i = 0;
      for (byte b = 0; b < this._$10914.size(); b++) {
        if (((Vector)this._$10914.get(b)).get(1).toString().equals("")) {
          i = 0;
        } else {
          i = (new Integer(((Vector)this._$10914.get(b)).get(1).toString())).intValue();
        } 
        if (i == 0) {
          ((Vector)this._$10914.get(b)).insertElementAt("", 1);
          ((Vector)this._$10914.get(b)).insertElementAt("", 2);
          ((Vector)this._$10914.get(b)).setElementAt(this.dateChooser.getDate(), 3);
        } else if (i == 1) {
          gregorianCalendar.setTimeInMillis(this.dateChooser.getDate().getTime() + 86400000L);
          ((Vector)this._$10914.get(b)).insertElementAt("", 1);
          ((Vector)this._$10914.get(b)).insertElementAt("", 2);
          ((Vector)this._$10914.get(b)).setElementAt(gregorianCalendar.getTime(), 3);
        } else if (i == 2) {
          gregorianCalendar.setTimeInMillis(this.dateChooser.getDate().getTime() + 172800000L);
          ((Vector)this._$10914.get(b)).insertElementAt("", 1);
          ((Vector)this._$10914.get(b)).insertElementAt("", 2);
          ((Vector)this._$10914.get(b)).setElementAt(gregorianCalendar.getTime(), 3);
        } else {
          gregorianCalendar.setTimeInMillis(this.dateChooser.getDate().getTime() + 259200000L);
          ((Vector)this._$10914.get(b)).insertElementAt("", 1);
          ((Vector)this._$10914.get(b)).insertElementAt("", 2);
          ((Vector)this._$10914.get(b)).setElementAt(gregorianCalendar.getTime(), 3);
        } 
      } 
      this.tbTrainRunning.removeAll();
      this.dtm2.setDataVector(this._$10914, this._$10915);
      TableColumn tableColumn = this.tbTrainRunning.getColumnModel().getColumn(3);
      tableColumn.setCellRenderer((TableCellRenderer)new DateRenderer());
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$10972() {
    try {
      Vector vector = new Vector();
      this._$10913 = new Vector();
      this._$10914 = new Vector();
      String str1 = null;
      GregorianCalendar gregorianCalendar = new GregorianCalendar();
      String str2 = "SELECT MAX(WSMotionDate) FROM WorkShift WHERE WSNo ='" + this.jcbWorkShiftNo.getSelectedItem() + "' AND WSMotionDate  <= '" + (new SimpleDateFormat("yyyy/M/d")).format(this.dateChooser.getDate()) + "'";
      vector = this.connDB.getData(str2);
      str1 = "SELECT TRNo,FinishDate,TRMotionDate FROM WorkShift_TRList WHERE WSNo ='" + this.jcbWorkShiftNo.getSelectedItem() + "' AND WSMotionDate = '" + (new SimpleDateFormat("yyyy/M/d")).format(((Vector)vector.get(0)).get(0)) + "'";
      this._$10913 = this.connDB.getData(str1);
      this._$10914 = this.connDB.getData(str1);
      int i = this._$10914.size();
      int j = 0;
      for (byte b = 0; b < i; b++) {
        if (((Vector)this._$10914.get(b)).get(1).toString().equals("")) {
          j = 0;
        } else {
          j = (new Integer(((Vector)this._$10914.get(b)).get(1).toString())).intValue();
        } 
        if (j == 0) {
          ((Vector)this._$10914.get(b)).insertElementAt("", 1);
          ((Vector)this._$10914.get(b)).insertElementAt("", 2);
          ((Vector)this._$10914.get(b)).setElementAt(this.dateChooser.getDate(), 3);
        } else if (j == 1) {
          gregorianCalendar.setTimeInMillis(this.dateChooser.getDate().getTime() + 86400000L);
          ((Vector)this._$10914.get(b)).insertElementAt("", 1);
          ((Vector)this._$10914.get(b)).insertElementAt("", 2);
          ((Vector)this._$10914.get(b)).setElementAt(gregorianCalendar.getTime(), 3);
        } else if (j == 2) {
          gregorianCalendar.setTimeInMillis(this.dateChooser.getDate().getTime() + 172800000L);
          ((Vector)this._$10914.get(b)).insertElementAt("", 1);
          ((Vector)this._$10914.get(b)).insertElementAt("", 2);
          ((Vector)this._$10914.get(b)).setElementAt(gregorianCalendar.getTime(), 3);
        } else {
          gregorianCalendar.setTimeInMillis(this.dateChooser.getDate().getTime() + 259200000L);
          ((Vector)this._$10914.get(b)).insertElementAt("", 1);
          ((Vector)this._$10914.get(b)).insertElementAt("", 2);
          ((Vector)this._$10914.get(b)).setElementAt(gregorianCalendar.getTime(), 3);
        } 
      } 
      this.tbTrainRunning.removeAll();
      this.dtm2.setDataVector(this._$10914, this._$10915);
      TableColumn tableColumn = this.tbTrainRunning.getColumnModel().getColumn(3);
      tableColumn.setCellRenderer((TableCellRenderer)new DateRenderer());
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$10978() {
    try {
      String str = null;
      GregorianCalendar gregorianCalendar = new GregorianCalendar();
      int j = 0;
      this._$10913 = new Vector();
      this._$10914 = new Vector();
      str = "SELECT TRNo,TrainType,TrainSN,RunningDate,WSMotionDate,TRMotionDate FROM Mission WHERE WSNo = '" + (String)this._$2494.get(3) + "' AND DriverID ='" + this._$2494.get(0).toString() + "' AND WorkDate ='" + (new SimpleDateFormat("yyyy/MM/dd")).format(this._$2494.get(2)) + "'";
      this._$10913 = this.connDB.getData(str);
      this._$10914 = this.connDB.getData(str);
      int i = this._$10913.size();
      for (byte b1 = 0; b1 < i; b1++) {
        j = (int)((new SimpleDateFormat("yyyy-MM-dd")).parse((new SimpleDateFormat("yyyy-MM-dd")).format(((Vector)this._$10914.get(b1)).get(3))).getTime() - (new SimpleDateFormat("yyyy-MM-dd")).parse((new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate())).getTime()) / 86400000;
        this._$10922.addElement(String.valueOf(j));
      } 
      long l = (new SimpleDateFormat("yyyy-MM-dd")).parse((new SimpleDateFormat("yyyy-MM-dd")).format(((Vector)this._$10914.get(0)).get(3))).getTime();
      for (byte b2 = 0; b2 < i; b2++) {
        j = (int)((new SimpleDateFormat("yyyy-MM-dd")).parse((new SimpleDateFormat("yyyy-MM-dd")).format(((Vector)this._$10914.get(b2)).get(3))).getTime() - l) / 86400000;
        if (Integer.parseInt(this._$10922.get(b2).toString()) == -1) {
          JOptionPane.showMessageDialog(this, "請檢查各車次的執行日期是否正確", "請注意", 1);
        } else if (Integer.parseInt(this._$10922.get(b2).toString()) == 0) {
          ((Vector)this._$10914.get(b2)).setElementAt(this.dateChooser.getDate(), 3);
        } else if (Integer.parseInt(this._$10922.get(b2).toString()) == 1) {
          gregorianCalendar.setTimeInMillis(this.dateChooser.getDate().getTime() + 86400000L);
          ((Vector)this._$10914.get(b2)).setElementAt(gregorianCalendar.getTime(), 3);
        } else if (Integer.parseInt(this._$10922.get(b2).toString()) == 2) {
          gregorianCalendar.setTimeInMillis(this.dateChooser.getDate().getTime() + 172800000L);
          ((Vector)this._$10914.get(b2)).setElementAt(gregorianCalendar.getTime(), 3);
        } else {
          gregorianCalendar.setTimeInMillis(this.dateChooser.getDate().getTime() + 259200000L);
          ((Vector)this._$10914.get(b2)).setElementAt(gregorianCalendar.getTime(), 3);
        } 
      } 
      this.tbTrainRunning.removeAll();
      this.dtm2.setDataVector(this._$10914, this._$10915);
      TableColumn tableColumn = this.tbTrainRunning.getColumnModel().getColumn(3);
      tableColumn.setCellRenderer((TableCellRenderer)new DateRenderer());
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private String _$10968(String paramString) {
    Vector vector = new Vector();
    try {
      String str = "SELECT MAX(TRMotionDate) FROM WorkShift_TRList WHERE WSNo = '" + this.jcbWorkShiftNo.getSelectedItem().toString() + "' AND WSMotionDate = '" + this._$10934 + "' AND TRNo = '" + paramString + "'";
      vector = this.connDB.getData(str);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return (new SimpleDateFormat("yyyy/M/d")).format(((Vector)vector.get(0)).get(0));
  }
  
  private void _$10947(int paramInt) throws Exception {
    String str = null;
    if (paramInt == 0) {
      str = "SELECT Train_SN,Train_Kind,Length,Weight_Empty,Weight_Full,Max_Acceleration,SB_delay_time,SB_deceleration,EB_delay_time,EB_deceleration,Max_Deceleration FROM Train_Data WHERE Train_SN IN (SELECT Train_SN from Train_Category where Train_Type = (SELECT Train_Type from Train_Type where train_Type_Name ='推拉式自強號')) ORDER BY Train_SN ASC";
    } else {
      str = "SELECT Train_SN,Train_Kind,Length,Weight_Empty,Weight_Full,Max_Acceleration,SB_delay_time,SB_deceleration,EB_delay_time,EB_deceleration,Max_Deceleration FROM Train_Data WHERE (Train_SN IN (SELECT Train_SN FROM Train_Category WHERE Train_Type = (SELECT Train_Type FROM Train_Type WHERE Train_Type = '" + this.tbTrainType.getValueAt(this.tbTrainType.getSelectedRow(), 0) + "'))) ORDER BY Train_SN ASC";
    } 
    this._$10917 = this.connDB.getData(str);
    this.dtm4.setDataVector(this._$10917, this._$2493);
    this.tbTrainCategory.getColumnModel().getColumn(0).setPreferredWidth(50);
    this.tbTrainCategory.getColumnModel().getColumn(1).setPreferredWidth(255);
    this.tbTrainCategory.getColumnModel().getColumn(2).setPreferredWidth(40);
    this.tbTrainCategory.getColumnModel().getColumn(3).setPreferredWidth(60);
    this.tbTrainCategory.getColumnModel().getColumn(4).setPreferredWidth(60);
    this.tbTrainCategory.getColumnModel().getColumn(5).setPreferredWidth(75);
    this.tbTrainCategory.getColumnModel().getColumn(6).setPreferredWidth(75);
    this.tbTrainCategory.getColumnModel().getColumn(7).setPreferredWidth(75);
    this.tbTrainCategory.getColumnModel().getColumn(8).setPreferredWidth(75);
    this.tbTrainCategory.getColumnModel().getColumn(9).setPreferredWidth(75);
    this.tbTrainCategory.getColumnModel().getColumn(10).setPreferredWidth(75);
  }
  
  private void _$10946() throws Exception {
    String str = "SELECT Train_Type,Train_Type_Name,Maxspeed FROM Train_Type ORDER BY Train_Type ASC";
    this._$10916 = this.connDB.getData(str);
    this.dtm3.setDataVector(this._$10916, this._$2491);
    this.tbTrainType.setRowSelectionInterval(0, this.tbTrainType.getSelectedColumnCount());
    this.tbTrainType.getColumnModel().getColumn(0).setPreferredWidth(30);
    this.tbTrainType.getColumnModel().getColumn(1).setPreferredWidth(395);
    this.tbTrainType.getColumnModel().getColumn(2).setPreferredWidth(55);
  }
  
  private void _$10962() throws Exception {
    String str = null;
    str = "SELECT VehicleID FROM VehicleID";
    this._$10920 = this.connDB.getData(str);
    for (byte b = 0; b < this._$10920.size(); b++)
      this.jcbVehicleID.addItem(((Vector)this._$10920.get(b)).get(0).toString()); 
  }
  
  private String _$10965(String paramString) {
    Vector vector = new Vector();
    try {
      String str = "SELECT MAX(WSMotionDate) FROM WorkShift WHERE WSNo = '" + paramString + "' AND WSMotionDate <='" + (new SimpleDateFormat("yyyy/M/d")).format(this.dateChooser.getDate()) + "'";
      vector = this.connDB.getData(str);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return (new SimpleDateFormat("yyyy/M/d")).format(((Vector)vector.get(0)).get(0));
  }
  
  private StringBuffer _$10981(long paramLong) {
    StringBuffer stringBuffer = new StringBuffer();
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    if (paramLong / 86400L == 0L) {
      stringBuffer.append((new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()));
    } else if (paramLong / 86400L == 1L) {
      gregorianCalendar.setTimeInMillis(this.dateChooser.getDate().getTime() + 86400000L);
      stringBuffer.append((new SimpleDateFormat("yyyy-MM-dd")).format(gregorianCalendar.getTime()));
    } else if (paramLong / 86400L == 2L) {
      gregorianCalendar.setTimeInMillis(this.dateChooser.getDate().getTime() + 172800000L);
      stringBuffer.append((new SimpleDateFormat("yyyy-MM-dd")).format(gregorianCalendar.getTime()));
    } else {
      gregorianCalendar.setTimeInMillis(this.dateChooser.getDate().getTime() + 259200000L);
      stringBuffer.append((new SimpleDateFormat("yyyy-MM-dd")).format(gregorianCalendar.getTime()));
    } 
    return stringBuffer;
  }
  
  private void _$10945() throws Exception {
    this._$10912 = new Vector();
    if (this.insert) {
      String str = "SELECT DISTINCT WSNo,MAX(WSMotionDate) FROM WorkShift_TRList WHERE WSMotionDate <='" + (new SimpleDateFormat("yyyy/M/d")).format(this.dateChooser.getDate()) + "' GROUP BY WSNo";
      this._$10912 = this.connDB.getData(str);
      this.jcbWorkShiftNo.removeAllItems();
      for (byte b = 0; b < this._$10912.size(); b++)
        this.jcbWorkShiftNo.addItem(((Vector)this._$10912.get(b)).get(0).toString()); 
      if (this._$10940.equals("null")) {
        this.jcbWorkShiftNo.setSelectedIndex(0);
      } else {
        this.jcbWorkShiftNo.setSelectedItem(this._$10940);
      } 
    } else {
      String str = "SELECT DISTINCT WSNo,MAX(WSMotionDate) FROM WorkShift_TRList WHERE WSMotionDate <='" + (new SimpleDateFormat("yyyy/M/d")).format(this.dateChooser.getDate()) + "'GROUP BY WSNo";
      this._$10912 = this.connDB.getData(str);
      for (byte b = 0; b < this._$10912.size(); b++)
        this.jcbWorkShiftNo.addItem(((Vector)this._$10912.get(b)).get(0).toString()); 
    } 
  }
  
  void init() throws Exception {
    this.titledBorder1 = new TitledBorder("");
    this.titledBorder2 = new TitledBorder("");
    this.titledBorder3 = new TitledBorder("");
    this.confirm.setFont(new Font("新細明體", 0, 14));
    this.confirm.setMargin(new Insets(2, 2, 2, 2));
    this.confirm.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    getContentPane().setLayout((LayoutManager)this.xYLayout1);
    this.lbDriverID.setFont(new Font("新細明體", 0, 15));
    this.lbDriverID.setText(ATPMessages.getString("MW.DRIVER.ID") + "(" + ATPMessages.getString("MW.DRIVER.NAME") + ")");
    this.jPanel1.setLayout((LayoutManager)this.xYLayout4);
    this.jPanel1.setBorder(BorderFactory.createEtchedBorder());
    this.jScrollPane2.setFont(new Font("新細明體", 0, 14));
    this.jScrollPane3.setFont(new Font("新細明體", 0, 14));
    this.jScrollPane4.setEnabled(true);
    this.jScrollPane4.setFont(new Font("新細明體", 0, 14));
    this.jLabel3.setText(ATPMessages.getString("MW.TR.ID"));
    this.jLabel3.setBorder(this.titledBorder2);
    this.jLabel3.setFont(new Font("新細明體", 0, 15));
    this.jLabel4.setText(ATPMessages.getString("MW.TT"));
    this.jLabel4.setBorder(this.titledBorder2);
    this.jLabel4.setFont(new Font("新細明體", 0, 15));
    this.jLabel5.setText(ATPMessages.getString("MW.TD"));
    this.jLabel5.setBorder(this.titledBorder2);
    this.jLabel5.setFont(new Font("新細明體", 0, 15));
    this.tbTrainRunning.setFont(new Font("新細明體", 0, 14));
    this.tbTrainRunning.setAutoResizeMode(2);
    this.tbTrainRunning.setRowHeight(16);
    this.tbTrainRunning.addMouseListener((MouseListener)new frmMissionData_tbTrainRunning_mouseAdapter(this));
    this.tbTrainRunning.setSelectionMode(0);
    this.tbTrainType.setFont(new Font("新細明體", 0, 12));
    this.tbTrainType.setAutoResizeMode(0);
    this.tbTrainType.addMouseListener((MouseListener)new frmMissionData_tbTrainType_mouseAdapter(this));
    this.tbTrainType.setDefaultRenderer(String.class, (TableCellRenderer)new MultiLineCellRenderer());
    this.tbTrainType.getTableHeader().setDefaultRenderer((TableCellRenderer)new MultiLineHeaderRenderer());
    this.tbTrainType.setSelectionMode(0);
    this.tbTrainCategory.setEnabled(true);
    this.tbTrainCategory.setFont(new Font("新細明體", 0, 12));
    this.tbTrainCategory.setAutoResizeMode(0);
    this.tbTrainCategory.setDefaultRenderer(String.class, (TableCellRenderer)new MultiLineCellRenderer());
    this.tbTrainCategory.getTableHeader().setDefaultRenderer((TableCellRenderer)new MultiLineHeaderRenderer());
    this.tbTrainCategory.setSelectionMode(0);
    this.jcbDriverNo.setFont(new Font("新細明體", 0, 14));
    this.jcbDriverNo.setActionCommand("comboBoxChanged");
    this.jcbDriverNo.setEditable(true);
    this.jcbDriverNo.addItemListener((ItemListener)new frmMissionData_jcbDriverNo_itemAdapter(this));
    this.cancel.addActionListener((ActionListener)new frmMissionData_cancel_actionAdapter(this));
    this.cancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.cancel.setMargin(new Insets(2, 2, 2, 2));
    this.cancel.setFont(new Font("新細明體", 0, 14));
    this.jLabel6.setFont(new Font("新細明體", 0, 15));
    this.jLabel6.setText(ATPMessages.getString("MW.WS.ID"));
    this.jcbWorkShiftNo.setActionCommand("comboBoxChanged");
    this.jcbWorkShiftNo.addItemListener((ItemListener)new frmMissionData_jcbWorkShiftNo_itemAdapter(this));
    this.jcbWorkShiftNo.addActionListener((ActionListener)new frmMissionData_jcbWorkShiftNo_actionAdapter(this));
    this.jButton1.setFont(new Font("新細明體", 0, 14));
    this.jButton1.setMargin(new Insets(2, 2, 2, 2));
    this.jButton1.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.jButton1.setBackground(Color.blue);
    this.jButton1.addActionListener((ActionListener)new frmMissionData_jButton1_actionAdapter(this));
    this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel2.setLayout((LayoutManager)this.xYLayout2);
    this.jPanel3.setBorder(BorderFactory.createLoweredBevelBorder());
    this.jPanel3.setLayout((LayoutManager)this.xYLayout3);
    this.jPanel3.setBackground(Color.yellow);
    this.jPanel4.setBorder(BorderFactory.createEtchedBorder());
    this.jLabel2.setFont(new Font("新細明體", 0, 15));
    this.jLabel2.setText(ATPMessages.getString("MW.WS.WORKDATE"));
    this.jLabel7.setFont(new Font("新細明體", 0, 15));
    this.jLabel7.setBorder(BorderFactory.createEtchedBorder());
    this.jLabel7.setText(ATPMessages.getString("MW.WS.TR_WORKDATE"));
    this.jPanel5.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel5.setLayout(this.borderLayout1);
    this.jLabel8.setFont(new Font("新細明體", 0, 14));
    this.jLabel8.setPreferredSize(new Dimension(33, 17));
    this.jLabel8.setText(ATPMessages.getString("MW.TR.STOPSTATION"));
    this.jLabel9.setFont(new Font("新細明體", 0, 14));
    this.jLabel9.setRequestFocusEnabled(true);
    this.jLabel9.setHorizontalAlignment(0);
    this.jLabel9.setText(ATPMessages.getString("MW.WS.START_TIME") + "(dd-HH-mm)");
    this.jLabel10.setFont(new Font("新細明體", 0, 14));
    this.jLabel10.setHorizontalAlignment(0);
    this.jLabel10.setText(ATPMessages.getString("MW.WS.END_TIME") + "(dd-HH-mm)");
    this.jFormattedTextField1 = new JFormattedTextField();
    this.jFormattedTextField1.setBackground(Color.white);
    this.jFormattedTextField1.setEditable(false);
    this.jFormattedTextField1.setHorizontalAlignment(0);
    this.jFormattedTextField1.setText("");
    this.jFormattedTextField2 = new JFormattedTextField();
    this.jFormattedTextField2.setBackground(Color.white);
    this.jFormattedTextField2.setEditable(false);
    this.jFormattedTextField2.setHorizontalAlignment(0);
    this.jFormattedTextField2.setText("");
    getContentPane().add(this.jPanel1, new XYConstraints(20, 10, 950, 65));
    this.dateChooser.addPropertyChangeListener((PropertyChangeListener)new frmMissionData_dateChooser_propertyChangeAdapter(this));
    this.jPanel1.add(this.lbDriverID, new XYConstraints(15, 5, -1, -1));
    this.jPanel1.add(this.jLabel6, new XYConstraints(215, 5, -1, -1));
    this.jPanel1.add(this.jLabel2, new XYConstraints(350, 5, -1, -1));
    this.jPanel1.add(this.jLabel9, new XYConstraints(670, 5, 127, -1));
    this.jPanel1.add(this.jLabel10, new XYConstraints(820, 5, 127, -1));
    this.jPanel1.add(this.jcbDriverNo, new XYConstraints(15, 30, 150, 27));
    this.jPanel1.add(this.jcbWorkShiftNo, new XYConstraints(190, 30, 100, 26));
    this.jPanel1.add((Component)this.dateChooser, new XYConstraints(350, 30, 100, -1));
    this.jPanel1.add(this.jFormattedTextField1, new XYConstraints(680, 30, 110, 25));
    this.jPanel1.add(this.jFormattedTextField2, new XYConstraints(830, 30, 110, 25));
    getContentPane().add(this.jPanel2, new XYConstraints(20, 80, 550, 150));
    this.jPanel2.add(this.jScrollPane2, new XYConstraints(10, 40, -1, 100));
    this.jPanel2.add(this.jLabel3, new XYConstraints(10, 5, -1, 30));
    this.jPanel2.add(this.cancel, new XYConstraints(480, 60, 50, 30));
    this.jPanel2.add(this.confirm, new XYConstraints(480, 100, 50, 30));
    this.jScrollPane2.getViewport().add((Component)this.tbTrainRunning, (Object)null);
    getContentPane().add(this.jPanel3, new XYConstraints(20, 240, 950, 450));
    this.jPanel3.add(this.jScrollPane4, new XYConstraints(10, 235, 930, 180));
    this.jPanel3.add(this.jLabel5, new XYConstraints(10, 200, -1, 30));
    this.jPanel3.add(this.jScrollPane3, new XYConstraints(10, 40, 500, 150));
    this.jPanel3.add(this.jLabel4, new XYConstraints(10, 5, -1, 30));
    this.jPanel3.add(this.jButton1, new XYConstraints(770, 420, 50, -1));
    this.jPanel3.add(this.jPanel4, new XYConstraints(520, 40, 324, 170));
    this.jPanel4.add((Component)this.calendar, (Object)null);
    this.jPanel3.add(this.jLabel7, new XYConstraints(520, 5, -1, 30));
    getContentPane().add(this.jPanel5, new XYConstraints(590, 80, 280, 145));
    this.jPanel5.add(this.jScrollPane1, "Center");
    this.jScrollPane1.getViewport().add(this.tbStopStation, (Object)null);
    this.jPanel5.add(this.jLabel8, "North");
    this.jScrollPane3.getViewport().add((Component)this.tbTrainType, (Object)null);
    this.jScrollPane4.getViewport().add((Component)this.tbTrainCategory, (Object)null);
    this.confirm.addActionListener((ActionListener)new frmMissionData_confirm_actionAdapter(this));
    this.tbTrainRunning.setFocusable(false);
    this.tbTrainType.setFocusable(false);
    this.tbTrainCategory.setFocusable(false);
    this._$10915.add(ATPMessages.getString("MW.TR.ID"));
    this._$10915.add(ATPMessages.getString("MW.TT.ID"));
    this._$10915.add(ATPMessages.getString("MW.TD.ID"));
    this._$10915.add(ATPMessages.getString("MW.WS.TR_WORKDATE"));
    this._$2491.add(ATPMessages.getString("MW.TT.ID"));
    this._$2491.add(ATPMessages.getString("MW.TT.NAME"));
    this._$2491.add(ATPMessages.getString("MW.TT.MAX_SPEED(Km/hr)"));
    this._$2493.add(ATPMessages.getString("MW.TD.ID"));
    this._$2493.add(ATPMessages.getString("MW.TD.NAME"));
    this._$2493.add(ATPMessages.getString("MW.TD.LENGTH(m)"));
    this._$2493.add(ATPMessages.getString("MW.TD.WEIGHT_EMPTY(ton)"));
    this._$2493.add(ATPMessages.getString("MW.TD.WEIGHT_FULL(ton)"));
    this._$2493.add(ATPMessages.getString("MW.TD.MAX_ACCELERATION(m/s/s)"));
    this._$2493.add(ATPMessages.getString("MW.TD.SB_DELAY_TIME(s)"));
    this._$2493.add(ATPMessages.getString("MW.TD.SB_DECELERATION(m/s/s)"));
    this._$2493.add(ATPMessages.getString("MW.TD.EB_DELAY_TIME(s)"));
    this._$2493.add(ATPMessages.getString("MW.TD.EB_DECELERATION(m/s/s)"));
    this._$2493.add(ATPMessages.getString("MW.TD.MAX_DECELERATION(m/s/s)"));
    this._$10919.add(ATPMessages.getString("MW.STATION.ID"));
    this._$10919.add(ATPMessages.getString("MW.STATION.NAME"));
    this._$10919.add(ATPMessages.getString("MW.TR.ARRIVE_TIME"));
    this._$10919.add(ATPMessages.getString("MW.TR.LEAVE_TIME"));
    setSize(new Dimension(1000, 720));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
  
  private boolean _$10997() {
    int i = this.tbTrainRunning.getColumnCount();
    int j = this.tbTrainRunning.getRowCount();
    boolean bool = false;
    for (byte b = 0; b < j; b++) {
      for (byte b1 = 0; b1 < i; b1++) {
        if (this.tbTrainRunning.getValueAt(b, b1).toString().equals("")) {
          bool = true;
          break;
        } 
      } 
    } 
    return bool;
  }
  
  private boolean _$11027(String paramString) {
    boolean bool = false;
    for (byte b = 0; b < this._$10920.size(); b++) {
      if (((Vector)this._$10920.get(b)).get(0).toString().equals(paramString))
        bool = true; 
    } 
    return bool;
  }
  
  void jButton1_actionPerformed(ActionEvent paramActionEvent) {
    int i = this.tbTrainRunning.getSelectedRow();
    Vector vector = new Vector();
    try {
      if (this.tbTrainRunning.getSelectedRow() == -1 || this.tbTrainType.getSelectedRow() == -1 || this.tbTrainCategory.getSelectedRow() == -1) {
        if (this.tbTrainRunning.getSelectedRow() == -1)
          JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.ME.NO_TR_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0); 
        if (this.tbTrainType.getSelectedRow() == -1)
          JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.ME.NO_TT_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0); 
        if (this.tbTrainCategory.getSelectedRow() == -1)
          JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.ME.NO_TD_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0); 
      } else if (this.dateChooser.getDate().after(this.calendar.getDate())) {
        JOptionPane.showMessageDialog(this, "您所輸入車次的執行日期為:" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.calendar.getDate()) + "，不可比工作班的執行日期:" + (new SimpleDateFormat("yyyy-MM-dd")).format(this.dateChooser.getDate()) + "早，請再次確認！！！", ATPMessages.getString("MW.GNL.ERROR"), 0);
      } else {
        TrainDataMapping trainDataMapping = new TrainDataMapping(this.tbTrainType.getValueAt(this.tbTrainType.getSelectedRow(), 0).toString(), this.tbTrainCategory.getValueAt(this.tbTrainCategory.getSelectedRow(), 0).toString(), (new SimpleDateFormat("yyyy-MM-dd")).format(this.calendar.getDate()), "spare", (Frame)getOwner(), ATPMessages.getString("MW.ME.CONFIRM"), true);
        trainDataMapping.show();
        if (trainDataMapping.isTrue()) {
          vector.add(this.tbTrainRunning.getValueAt(this.tbTrainRunning.getSelectedRow(), 0));
          vector.add(this.tbTrainType.getValueAt(this.tbTrainType.getSelectedRow(), 0));
          vector.add(this.tbTrainCategory.getValueAt(this.tbTrainCategory.getSelectedRow(), 0));
          vector.add(this.calendar.getDate());
          this._$10914.setElementAt(vector, i);
          this.dtm2.setDataVector(this._$10914, this._$10915);
          TableColumn tableColumn = this.tbTrainRunning.getColumnModel().getColumn(3);
          tableColumn.setCellRenderer((TableCellRenderer)new DateRenderer());
        } else {
          this.dtm2.setDataVector(this._$10914, this._$10915);
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void jcbDriverNo_itemStateChanged(ItemEvent paramItemEvent) {}
  
  void jcbWorkShiftNo_actionPerformed(ActionEvent paramActionEvent) {}
  
  void jcbWorkShiftNo_itemStateChanged(ItemEvent paramItemEvent) {
    long l = 0L;
    try {
      if (this.insert) {
        if (this.jcbWorkShiftNo.getItemCount() != 0) {
          _$10984();
          _$10972();
          this._$10934 = "";
          this._$10934 = _$10965(this.jcbWorkShiftNo.getSelectedItem().toString());
        } 
      } else {
        _$10984();
        this._$10934 = "";
        this._$10934 = _$10965(this.jcbWorkShiftNo.getSelectedItem().toString());
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private String _$11023(int paramInt) {
    StringBuffer stringBuffer = new StringBuffer(String.valueOf(paramInt));
    if (stringBuffer.length() < 2) {
      stringBuffer.insert(0, "0");
      return stringBuffer.toString();
    } 
    return stringBuffer.toString();
  }
  
  void tbTrainRunning_mouseClicked(MouseEvent paramMouseEvent) {
    Vector vector = new Vector();
    try {
      if (this.insert) {
        _$10947(1);
        this.calendar.setDate(((Vector)this._$10914.get(this.tbTrainRunning.getSelectedRow())).get(3));
      } else {
        this.calendar.setDate((Date)this.tbTrainRunning.getValueAt(this.tbTrainRunning.getSelectedRow(), 3));
        for (byte b1 = 0; b1 < this.tbTrainType.getRowCount(); b1++) {
          if (this.tbTrainType.getValueAt(b1, 0).toString().equals(this.tbTrainRunning.getValueAt(this.tbTrainRunning.getSelectedRow(), 1).toString())) {
            this.tbTrainType.setRowSelectionInterval(b1, b1);
            this.tbTrainType.changeSelection(b1, 0, false, false);
            break;
          } 
        } 
        _$10947(1);
        for (byte b2 = 0; b2 < this.tbTrainCategory.getRowCount(); b2++) {
          if (this.tbTrainCategory.getValueAt(b2, 0).toString().equals(this.tbTrainRunning.getValueAt(this.tbTrainRunning.getSelectedRow(), 2).toString())) {
            this.tbTrainCategory.setRowSelectionInterval(b2, b2);
            this.tbTrainCategory.changeSelection(b2, 0, false, false);
            break;
          } 
        } 
      } 
      String str1 = "SELECT MAX(TRMotionDate) FROM WorkShift_TRList WHERE WSNo = '" + this.jcbWorkShiftNo.getSelectedItem() + "' AND TRNo = '" + this.tbTrainRunning.getValueAt(this.tbTrainRunning.getSelectedRow(), 0) + "' AND WSMotionDate <='" + (new SimpleDateFormat("yyyy/M/d")).format(this.dateChooser.getDate()) + "'";
      vector = this.connDB.getData(str1);
      String str2 = "SELECT Station.StationID,Station.StationCName,TrainStopStation.Arrival_Time,TrainStopStation.Leave_Time FROM Station join TrainStopStation ON Station.StationID = TrainStopStation.StationID WHERE TRNo= '" + this.tbTrainRunning.getValueAt(this.tbTrainRunning.getSelectedRow(), 0) + "'" + "AND TRMotionDate in " + "(SELECT MAX(TRMotionDate) " + "FROM TrainRunning " + "WHERE TRNo = '" + this.tbTrainRunning.getValueAt(this.tbTrainRunning.getSelectedRow(), 0) + "'" + " AND TRMotionDate = '" + (new SimpleDateFormat("yyyy/MM/dd")).format(((Vector)vector.get(0)).get(0)) + "' GROUP BY TRNo)";
      this._$10918 = this.connDB.getData(str2);
      this.dtm1.setDataVector(this._$10918, this._$10919);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void tbTrainType_focusGained(FocusEvent paramFocusEvent) {}
  
  void tbTrainType_mouseClicked(MouseEvent paramMouseEvent) {
    try {
      _$10947(1);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
