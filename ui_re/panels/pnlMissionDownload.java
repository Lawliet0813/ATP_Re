package ui.panels;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CRC;
import com.MiTAC.TRA.ATP.Tools.CheckDataCount;
import com.MiTAC.TRA.ATP.Tools.DateRenderer;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.MaintenanceRunnable;
import com.MiTAC.TRA.ATP.Tools.RefreshFrame;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.StringHandler;
import com.MiTAC.TRA.ATP.Tools.TrainCategoryRunnable;
import com.MiTAC.TRA.ATP.Tools.TrainTypeRunnable;
import com.MiTAC.TRA.ATP.Tools.UnknowProgressMonitor;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.frames.frmMain;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMissionDownload_btnLoad_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMissionDownload_btnUSBDelete_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMissionDownload_btnUSBRefrsh_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMissionDownload_dateChooser_propertyChangeAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMissionDownload_jTable1_focusAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMissionDownload_jTable1_keyAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMissionDownload_jTable1_mouseAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMissionDownload_jTable1_propertyChangeAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMissionDownload_showTrainRunning_actionAdapter;
import com.MiTAC.TRA.ATP.ui.panels.pnlMissionLoadConfirm;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import src.com.toedter.calendar.JDateChooser;

public class pnlMissionDownload extends JPanel implements RefreshFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  
  BorderLayout borderLayout2 = new BorderLayout();
  
  BorderLayout borderLayout3 = new BorderLayout();
  
  BorderLayout borderLayout4 = new BorderLayout();
  
  BorderLayout borderLayout5 = new BorderLayout();
  
  JButton btnLoad = new JButton();
  
  JButton btnUSBDelete = new JButton();
  
  JButton btnUSBRefrsh = new JButton();
  
  Calendar calendar_Today = GregorianCalendar.getInstance();
  
  Timer changeDateTimer;
  
  private CheckDataCount _$2190;
  
  private Vector _$2153 = new Vector();
  
  private Vector _$2154 = new Vector();
  
  private Vector _$2155 = new Vector();
  
  JComboBox comboDriverName = new JComboBox();
  
  int confirm = 0;
  
  ConnectDB connDB = new ConnectDB();
  
  Timer dataDownloadTimer;
  
  JDateChooser dateChooser = new JDateChooser();
  
  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  Date day = new Date();
  
  int download = 0;
  
  boolean downloadFinish = false;
  
  DefaultSortTableModel dtm2 = new DefaultSortTableModel();
  
  String fileTableA = InitParameters.USBPath;
  
  Thread first;
  
  FlowLayout flowLayout1 = new FlowLayout();
  
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  
  boolean isShowAllTrainRunningData = false;
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
  JLabel jLabel4 = new JLabel();
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel2 = new JPanel();
  
  JPanel jPanel3 = new JPanel();
  
  JPanel jPanel4 = new JPanel();
  
  JPanel jPanel5 = new JPanel();
  
  JPanel jPanel6 = new JPanel();
  
  JPanel jPanel7 = new JPanel();
  
  JPanel jPanel8 = new JPanel();
  
  JPanel jPanel9 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JScrollPane jScrollPane2 = new JScrollPane();
  
  JScrollPane jScrollPane3 = new JScrollPane();
  
  JSplitPane jSplitPane1 = new JSplitPane();
  
  JSplitPane jSplitPane2 = new JSplitPane();
  
  JTable jTable1 = new JTable((TableModel)this.tm1);
  
  JTable jTable2 = new JTable((TableModel)this.dtm2);
  
  JTable jTable3 = new JTable((TableModel)this.tm2);
  
  MaintenanceRunnable maintenanceRunnable;
  
  private Vector _$2150;
  
  private Vector _$2151;
  
  com.MiTAC.TRA.ATP.ui.pnlMissionDownload missionDownload;
  
  private int _$2188;
  
  private NumberFormat _$2189;
  
  private Vector _$2156;
  
  UnknowProgressMonitor progressMonitorFormat;
  
  StringBuffer sbUSBTrainData = new StringBuffer();
  
  Thread second;
  
  int selectRowOfTable1;
  
  JButton showAllTrainRunning = new JButton();
  
  boolean stop = false;
  
  Thread third;
  
  DownloadThread thread;
  
  TestModel tm1 = new TestModel(this, null);
  
  TestModel tm2 = new TestModel(this, null);
  
  Date today = new Date();
  
  TrainCategoryRunnable trainCategoryRunnable;
  
  TrainTypeRunnable trainTypeRunnable;
  
  private Vector _$2160;
  
  private Vector _$2152;
  
  private Vector _$2159;
  
  private Vector _$2157 = new Vector();
  
  private Vector _$2158 = new Vector();
  
  XYLayout xYLayout1 = new XYLayout();
  
  public pnlMissionDownload() {
    try {
      init();
      this._$2189 = NumberFormat.getInstance();
      this._$2189.setMaximumFractionDigits(3);
      this.missionDownload = this;
      _$2200(this.dateChooser.getDate());
      if (!this._$2150.isEmpty()) {
        this.changeDateTimer = new Timer(5000, (ActionListener)new Object(this));
        this.changeDateTimer.start();
      } 
      this.dtm2.setDataVector(_$2208(), this._$2153);
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      JOptionPane.showMessageDialog(this, sQLException.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  public void Refresh() {
    try {
      this.jTable2.removeAll();
      _$2200(this.dateChooser.getDate());
      this.dtm2.setDataVector(_$2208(), this._$2153);
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      JOptionPane.showMessageDialog(this, sQLException.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnLoad_actionPerformed(ActionEvent paramActionEvent) {
    Vector vector1 = new Vector();
    Vector vector2 = new Vector();
    Vector vector = new Vector();
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.MD.DOWNLOAD_MISSION.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else if (!_$2253()) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.MD.NO_USB"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      Vector vector3 = _$2212(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 1).toString(), (Date)this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 2), this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 3).toString());
      this.selectRowOfTable1 = this.jTable1.getSelectedRow();
      for (byte b = 0; b < vector3.size(); b++) {
        Vector vector4 = new Vector();
        vector4.add(((Vector)vector3.get(b)).get(0));
        vector4.add(((Vector)vector3.get(b)).get(1));
        vector4.add(((Vector)vector3.get(b)).get(2));
        vector4.add(((Vector)vector3.get(b)).get(3));
        vector4.add(((Vector)vector3.get(b)).get(4));
        vector4.add(((Vector)vector3.get(b)).get(5));
        vector4.add(((Vector)vector3.get(b)).get(6));
        vector4.add(((Vector)vector3.get(b)).get(7));
        vector1.add(vector4);
        vector2.add((Vector)vector4.clone());
      } 
      int i = JOptionPane.showConfirmDialog(this, new pnlMissionLoadConfirm(vector1, "confirm"), ATPMessages.getString("MW.GNL.ASK"), 0);
      if (i == 0)
        _$2312(vector1, this._$2159); 
    } 
  }
  
  void btnUSBDelete_actionPerformed(ActionEvent paramActionEvent) {
    Vector vector = _$2208();
    Vector vector1 = new Vector();
    int[] arrayOfInt = this.jTable2.getSelectedRows();
    boolean bool = false;
    if (this.jTable2.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.MD.DELETE.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      Vector vector2 = new Vector();
      for (byte b = 0; b < (this.jTable2.getSelectedRows()).length; b++) {
        int j = this.jTable2.getSelectedRows()[b];
        vector2.addElement(((Vector)vector.get(j)).get(0));
      } 
      int i = JOptionPane.showConfirmDialog(getRootPane().getParent(), ATPMessages.getString("MW.MD.DELETE.Q.CONFIRM") + "\n" + vector2, ATPMessages.getString("MW.GNL.ASK"), 0, 3);
      if (i == 0) {
        for (int j = arrayOfInt.length - 1; j >= 0; j--)
          this._$2160.removeElementAt(arrayOfInt[j]); 
        _$2339(this._$2160);
        this.dtm2.setDataVector(_$2208(), this._$2153);
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.MD.DELETE.OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
      } 
    } 
    File file1 = new File(this.fileTableA + "TrainData.txt");
    File file2 = new File(this.fileTableA + "TrainData2.txt");
    if (this.jTable2.getRowCount() == 0) {
      file1.delete();
      file2.delete();
    } 
    Refresh();
  }
  
  void btnUSBRefrsh_actionPerformed(ActionEvent paramActionEvent) {
    this.dtm2.setDataVector(_$2208(), this._$2153);
  }
  
  private void _$2312(Vector paramVector1, Vector paramVector2) {
    Vector vector1 = new Vector();
    Vector vector2 = new Vector();
    Vector vector3 = new Vector(1, 1);
    Vector vector4 = paramVector1;
    Vector vector5 = _$2208();
    Vector vector6 = new Vector();
    Vector vector = new Vector();
    try {
      for (byte b1 = 0; b1 < vector4.size(); b1++) {
        vector6.addElement(((Vector)vector4.get(b1)).get(0));
        vector6.addElement(this.dateFormat.format(((Vector)vector4.get(b1)).get(2)));
        vector6.addElement(((Vector)vector4.get(b1)).get(3));
        vector6.addElement(((Vector)vector4.get(b1)).get(4));
        vector6.addElement(this.dateFormat.format(((Vector)vector4.get(b1)).get(5)));
        vector6.addElement(((Vector)vector4.get(b1)).get(6).toString());
        vector6.addElement(((Vector)vector4.get(b1)).get(7).toString());
        vector.addElement(vector6);
        vector6 = new Vector();
      } 
      for (byte b2 = 0; b2 < vector.size(); b2++) {
        boolean bool = false;
        for (byte b = 0; b < vector5.size(); b++) {
          if (vector.get(b2).equals(vector5.get(b))) {
            bool = true;
            vector3.add(String.valueOf(b));
            break;
          } 
        } 
        if (bool == true) {
          vector1.addElement(vector4.get(b2));
        } else {
          vector2.addElement(vector4.get(b2));
        } 
      } 
      if (vector1.isEmpty()) {
        this._$2190 = new CheckDataCount(20, vector4.size(), this._$2159.size());
        if (this._$2190.isMoreThanCount()) {
          JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.MD.USB.MORE_THAN_20_1") + this._$2190.getSum() + ATPMessages.getString("MW.MD.USB.MORE_THAN_20_2"), ATPMessages.getString("MW.GNL.ERROR"), 2);
        } else {
          this.progressMonitorFormat = new UnknowProgressMonitor(this, "派班資料下載中........", "", 0, 0);
          for (byte b = 0; b < this._$2152.size(); b++)
            this._$2160.addElement(_$2325(this._$2152.get(b))); 
          this.thread = new DownloadThread(this, this._$2160);
          this.thread.start();
          _$2326(this._$2152);
          this.progressMonitorFormat.show();
          Refresh();
          JOptionPane.showMessageDialog(this, "資料已下載完成，若您要取出司機員隨身碟，請完成退出程序後，再取出司機員隨身碟。", "訊息提示", 1);
        } 
      } else {
        int i = JOptionPane.showConfirmDialog(this, new pnlMissionLoadConfirm(vector1, "repeat"), ATPMessages.getString("MW.GNL.ASK"), 0);
        if (i == 0) {
          this._$2190 = new CheckDataCount(20, vector2.size(), this._$2159.size());
          for (byte b3 = 0; b3 < vector3.size(); b3++)
            this._$2160.setElementAt(_$2325(this._$2152.get(b3)), Integer.parseInt(vector3.get(b3))); 
          for (byte b4 = 0; b4 < vector2.size(); b4++)
            this._$2160.addElement(_$2325(vector2.get(b4))); 
          if (this._$2190.isMoreThanCount()) {
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.MD.USB.MORE_THAN_20_1") + this._$2190.getSum() + ATPMessages.getString("MW.MD.USB.MORE_THAN_20_2"), ATPMessages.getString("MW.GNL.ERROR"), 2);
          } else {
            this.progressMonitorFormat = new UnknowProgressMonitor(this, "派班資料下載中........", "", 0, 0);
            this.thread = new DownloadThread(this, this._$2160);
            this.thread.start();
            _$2326(this._$2152);
            this.progressMonitorFormat.show();
            Refresh();
            JOptionPane.showMessageDialog(this, "資料已下載完成，若您要取出司機員隨身碟，請完成退出程序後，再取出司機員隨身碟。", "訊息提示", 1);
          } 
        } else if (i == 1) {
          this._$2190 = new CheckDataCount(20, vector4.size(), this._$2159.size());
          if (this._$2190.isMoreThanCount()) {
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.MD.USB.MORE_THAN_20_1") + this._$2190.getSum() + ATPMessages.getString("MW.MD.USB.MORE_THAN_20_2"), ATPMessages.getString("MW.GNL.ERROR"), 2);
          } else {
            this.progressMonitorFormat = new UnknowProgressMonitor(this, "派班資料下載中........", "", 0, 0);
            for (byte b = 0; b < this._$2152.size(); b++)
              this._$2160.addElement(_$2325(this._$2152.get(b))); 
            this.thread = new DownloadThread(this, this._$2160);
            this.thread.start();
            _$2326(this._$2152);
            this.progressMonitorFormat.show();
            Refresh();
            JOptionPane.showMessageDialog(this, "資料已下載完成，若您要取出司機員隨身碟，請完成退出程序後，再取出司機員隨身碟。", "訊息提示", 1);
          } 
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$2339(Vector paramVector) {
    File file = new File(this.fileTableA + "\\TrainData.txt");
    if (file.exists()) {
      _$2340(paramVector);
    } else {
      file = new File(this.fileTableA + "\\TrainData.txt");
      _$2340(paramVector);
    } 
  }
  
  private void _$2350(String paramString1, String paramString2) throws IOException {
    String str1 = paramString1;
    String str2 = "";
    int i = str1.length();
    byte[] arrayOfByte1 = new byte[i];
    byte[] arrayOfByte2 = new byte[arrayOfByte1.length];
    for (byte b1 = 0; b1 < arrayOfByte1.length; b1++)
      arrayOfByte1[b1] = (byte)str1.charAt(b1); 
    for (byte b2 = 0; b2 < arrayOfByte1.length; b2++)
      arrayOfByte2[b2] = (byte)(arrayOfByte1[b2] ^ 0xFFFFFFFF); 
    FileOutputStream fileOutputStream = new FileOutputStream(this.fileTableA + paramString2);
    for (byte b3 = 0; b3 < arrayOfByte2.length; b3++)
      fileOutputStream.write(arrayOfByte2[b3]); 
    fileOutputStream.close();
  }
  
  private String _$2349(String paramString) {
    String str1 = "";
    String str2 = paramString;
    try {
      int i = str2.length();
      char[] arrayOfChar = new char[i + 2];
      for (byte b = 0; b < i; b++)
        arrayOfChar[b] = str2.charAt(b); 
      CRC cRC = new CRC(arrayOfChar, arrayOfChar.length - 2);
      str1 = str1 + "crc:" + cRC.getCRC();
      str2 = str2 + str1;
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return str2;
  }
  
  private Vector _$2325(Vector paramVector) {
    Vector vector = paramVector;
    Vector vector1 = new Vector();
    try {
      this._$2156 = new Vector();
      Vector vector2 = _$2258(vector.get(6).toString());
      Vector vector3 = _$2255(vector.get(7).toString());
      Vector vector4 = _$2261(vector);
      vector1.addElement(vector.get(0).toString());
      vector1.addElement(vector.get(3).toString());
      vector1.addElement(vector.get(4).toString());
      String str1 = this.dateFormat.format((Date)vector.get(2));
      if (str1.length() == 10) {
        vector1.addElement(str1);
      } else {
        vector1.addElement(str1.substring(0, str1.length() - 9));
      } 
      String str2 = this.dateFormat.format((Date)vector.get(5));
      if (str2.length() == 10) {
        vector1.addElement(str2);
      } else {
        vector1.addElement(str2.substring(0, str2.length() - 9));
      } 
      vector1.addElement(vector.get(6).toString());
      vector1.addElement(vector.get(7).toString());
      vector1.addElement("spare");
      vector1.addElement("spare");
      vector1.addElement("spare");
      vector1.addElement(((Vector)vector3.get(0)).get(3).toString());
      vector1.addElement(((Vector)vector2.get(0)).get(2).toString());
      vector1.addElement("spare");
      vector1.addElement(this._$2189.format(((Vector)vector3.get(0)).get(8)));
      vector1.addElement("spare");
      vector1.addElement(this._$2189.format(((Vector)vector3.get(0)).get(10)));
      vector1.addElement(this._$2189.format(((Vector)vector3.get(0)).get(7)));
      vector1.addElement(this._$2189.format(((Vector)vector3.get(0)).get(9)));
      vector1.addElement("spare");
      vector1.addElement(this._$2189.format(((Vector)vector3.get(0)).get(6)));
      vector1.addElement(this._$2189.format(((Vector)vector3.get(0)).get(11)));
      vector1.addElement(((Vector)vector2.get(0)).get(7).toString());
      vector1.addElement(((Vector)vector2.get(0)).get(8).toString());
      vector1.addElement(((Vector)vector2.get(0)).get(9).toString());
      vector1.addElement(((Vector)vector2.get(0)).get(10).toString());
      vector1.addElement("spare");
      vector1.addElement("spare");
      vector1.addElement("spare");
      vector1.addElement("spare");
      vector1.addElement("spare");
      for (byte b = 0; b < vector4.size(); b++)
        vector1.addElement(((Vector)vector4.get(b)).get(0).toString().toUpperCase()); 
      this._$2156.addElement(vector1);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
    return vector1;
  }
  
  void dateChooser_propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    try {
      if (paramPropertyChangeEvent.getOldValue() != null) {
        this.isShowAllTrainRunningData = false;
        _$2200(this.dateChooser.getDate());
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private Vector _$2212(String paramString1, Date paramDate, String paramString2) {
    try {
      String str = "SELECT Mission.DriverID,Driver_INFO.Name,Mission.WorkDate,Mission.WSNo,Mission.TRNo,Mission.RunningDate,Mission.TrainType,Mission.TrainSN,Mission.VehicleID,Mission.Comit,Mission.Download,Mission.WSMotionDate,Mission.TRMotionDate FROM Mission JOIN Driver_INFO ON Mission.DriverID = Driver_INFO.Driver_ID WHERE DriverID = '" + paramString2 + "' AND WSNo = '" + paramString1 + "' AND WorkDate ='" + this.dateFormat.format(paramDate) + "'";
      this._$2152 = this.connDB.getData(str);
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      JOptionPane.showMessageDialog(this, sQLException.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
    return this._$2152;
  }
  
  private Vector _$2261(Vector paramVector) {
    Vector vector = new Vector();
    Vector vector1 = new Vector();
    try {
      String str = "SELECT  DISTINCT StationID, TRMotionDate,Arrival_Time FROM TrainStopStation WHERE TRNO='" + paramVector.get(4) + "' AND TRMotionDate ='" + this.dateFormat.format((Date)paramVector.get(12)) + "' ORDER BY Arrival_Time ASC ,StationID ASC";
      vector = this.connDB.getData(str);
      if (vector.size() == 0) {
        String str1 = "SELECT MAX(TRMotionDate) FROM TrainRunning WHERE TRNO='" + paramVector.get(4) + "' AND TRMotionDate <='" + this.dateFormat.format((Date)paramVector.get(5)) + "'";
        vector1 = this.connDB.getData(str1);
        str = "SELECT  DISTINCT StationID, TRMotionDate,Arrival_Time FROM TrainStopStation WHERE TRNO='" + paramVector.get(4) + "' AND TRMotionDate ='" + this.dateFormat.format(((Vector)vector1.get(0)).get(0)) + "' ORDER BY Arrival_Time ASC ,StationID ASC";
        vector = this.connDB.getData(str);
        String str2 = "UPDATE Mission SET TRMotionDate = '" + this.dateFormat.format(((Vector)vector1.get(0)).get(0)) + "' WHERE DriverID = '" + paramVector.get(0) + "' AND WorkDate = '" + (new SimpleDateFormat("yyyy-MM-dd")).format(paramVector.get(2)) + "' AND RunningDate = '" + (new SimpleDateFormat("yyyy-MM-dd")).format(paramVector.get(5)) + "' AND WSNo = '" + paramVector.get(3) + "' AND TRNo = '" + paramVector.get(4) + "'";
        this.connDB.Update(str2);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return vector;
  }
  
  private Vector _$2258(String paramString) {
    Vector vector = new Vector();
    try {
      String str = "SELECT * FROM Train_Type WHERE Train_Type='" + paramString + "'";
      vector = this.connDB.getData(str);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return vector;
  }
  
  private void _$2233() throws Exception {
    String str = "";
    Vector vector = new Vector();
    if (this.isShowAllTrainRunningData) {
      this.jLabel4.setText(ATPMessages.getString("MW.TR.ALL"));
      str = "SELECT Mission.TRNo,Mission.RunningDate,Mission.TrainType,Mission.TrainSN,Mission.VehicleID,Mission.TRMotionDate FROM Mission WHERE Mission.WorkDate = '" + this.dateFormat.format(this.dateChooser.getDate()) + "'";
      this._$2151 = this.connDB.getData(str);
      vector = this.connDB.getData(str);
    } else if (!this._$2150.isEmpty()) {
      str = "SELECT Mission.TRNo,Mission.RunningDate,Mission.TrainType,Mission.TrainSN,Mission.VehicleID,Mission.TRMotionDate FROM Mission WHERE Mission.WSNo = '" + this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 1) + "' AND Mission.DriverID = '" + this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 3) + "'  AND Mission.WorkDate = '" + this.dateFormat.format(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 2)) + "'";
      this._$2151 = this.connDB.getData(str);
      vector = this.connDB.getData(str);
      this.jLabel4.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 1) + " " + ATPMessages.getString("MW.WS.TRLIST"));
    } 
    this.tm2.setDataVector(vector, this._$2155);
    TableColumn tableColumn = this.jTable3.getColumnModel().getColumn(1);
    tableColumn.setCellRenderer((TableCellRenderer)new DateRenderer());
    this.jTable3.setAutoResizeMode(0);
    this.jTable3.getColumnModel().getColumn(0).setMaxWidth(70);
    this.jTable3.getColumnModel().getColumn(1).setMaxWidth(130);
    this.jTable3.getColumnModel().getColumn(2).setMaxWidth(70);
    this.jTable3.getColumnModel().getColumn(3).setPreferredWidth(90);
    this.jTable3.getColumnModel().getColumn(4).setPreferredWidth(80);
  }
  
  private Vector _$2255(String paramString) {
    Vector vector = new Vector();
    try {
      String str = "SELECT * FROM Train_Data WHERE Train_SN='" + paramString + "'";
      vector = this.connDB.getData(str);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return vector;
  }
  
  private Vector _$2208() {
    String str1 = InitParameters.USBPath;
    String str2 = "";
    String str3 = "";
    Vector vector = new Vector();
    this._$2159 = new Vector();
    this._$2160 = new Vector();
    File file = new File(this.fileTableA + "\\TrainData.txt");
    if ((new File(str1)).exists()) {
      if (file.exists()) {
        try {
          byte b = 0;
          str3 = _$2248(this.fileTableA + "\\TrainData.txt");
          if (str3.length() != 0) {
            for (byte b1 = 0; b1 < str3.length(); b1++) {
              if (str3.charAt(b1) != ',' && str3.charAt(b1) != ';') {
                str2 = str2 + str3.charAt(b1);
              } else {
                if (str3.charAt(b1) == ';') {
                  this._$2160.addElement(vector);
                  vector = new Vector();
                  b++;
                } else {
                  vector.addElement(str2);
                } 
                str2 = "";
              } 
            } 
            for (byte b2 = 0; b2 < this._$2160.size(); b2++) {
              Vector vector1 = new Vector();
              vector1.addElement(((Vector)this._$2160.get(b2)).get(0));
              vector1.addElement(((Vector)this._$2160.get(b2)).get(3));
              vector1.addElement(((Vector)this._$2160.get(b2)).get(1));
              vector1.addElement(((Vector)this._$2160.get(b2)).get(2));
              vector1.addElement(((Vector)this._$2160.get(b2)).get(4));
              vector1.addElement(((Vector)this._$2160.get(b2)).get(5));
              vector1.addElement(((Vector)this._$2160.get(b2)).get(6));
              this._$2159.addElement(vector1);
            } 
          } 
        } catch (IOException iOException) {
          iOException.printStackTrace();
          JOptionPane.showMessageDialog(this, iOException.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
        } catch (Exception exception) {
          exception.printStackTrace();
          JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
        } 
      } else {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.MD.USB.NO_DATA"), ATPMessages.getString("MW.GNL.WARN"), 2);
      } 
    } else {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.MD.NO_USB"), ATPMessages.getString("MW.GNL.WARN"), 2);
    } 
    return this._$2159;
  }
  
  private void _$2200(Date paramDate) throws Exception {
    Vector vector = new Vector();
    String str = "SELECT DISTINCT WorkShift.Begin_Time,Mission.WSNo,Mission.WorkDate,Mission.DriverID,Driver_INFO.Name,Mission.Download,Mission.WSMotionDate FROM Driver_INFO JOIN Mission ON Driver_INFO.Driver_ID = Mission.DriverID JOIN WorkShift ON WorkShift.WSNo = Mission.WSNo  AND WorkShift.WSMotionDate = Mission.WSMotionDate WHERE Mission.WorkDate = '" + this.dateFormat.format(paramDate) + "' ORDER BY Mission.WSNo";
    this._$2150 = this.connDB.getData(str);
    vector = this.connDB.getData(str);
    if (vector.size() == 0) {
      this.tm1.setDataVector(vector, this._$2154);
      this.tm2.setDataVector(new Vector(), this._$2155);
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.MD.NO_DATA"), ATPMessages.getString("MW.GNL.WARN"), 2);
    } else {
      this.tm1.setDataVector(vector, this._$2154);
      this.jTable1.setRowSelectionInterval(0, 0);
      TableColumn tableColumn1 = this.jTable1.getColumnModel().getColumn(0);
      tableColumn1.setCellRenderer((TableCellRenderer)new Object(this));
      TableColumn tableColumn2 = this.jTable1.getColumnModel().getColumn(2);
      tableColumn2.setCellRenderer((TableCellRenderer)new DateRenderer());
      this.jTable1.getColumnModel().getColumn(0).setMaxWidth(80);
      this.jTable1.getColumnModel().getColumn(1).setMaxWidth(80);
      this.jTable1.getColumnModel().getColumn(2).setMaxWidth(150);
      this.jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
      this.jTable1.getColumnModel().getColumn(4).setPreferredWidth(75);
      this.jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
      this.jLabel4.setText(((Vector)vector.get(0)).get(1).toString() + " " + ATPMessages.getString("MW.WS.TRLIST"));
      _$2233();
    } 
  }
  
  void init() throws Exception {
    this.jLabel1.setFont(new Font("Dialog", 1, 15));
    this.jLabel1.setBorder(BorderFactory.createEtchedBorder());
    this.jLabel1.setText(ATPMessages.getString("MW.MAIN.MISSION_MGN.MISSION_DOWNLOAD"));
    setLayout(this.borderLayout1);
    this.jPanel1.setLayout(this.gridBagLayout1);
    this.jSplitPane1.setOrientation(0);
    this.jSplitPane1.setLastDividerLocation(150);
    this.jLabel2.setToolTipText("");
    this.jLabel2.setText(ATPMessages.getString("MW.MD.USB_MISSION_LIST"));
    this.jPanel3.setLayout(this.borderLayout2);
    this.jPanel2.setLayout(this.borderLayout3);
    this.jLabel3.setOpaque(false);
    this.jLabel3.setText(ATPMessages.getString("MW.MD.MW_MISSION_LIST"));
    this.jPanel4.setLayout((LayoutManager)this.xYLayout1);
    this.btnLoad.setText(ATPMessages.getString("MW.MD.DOWNLOAD_MISSION"));
    this.btnLoad.addActionListener((ActionListener)new pnlMissionDownload_btnLoad_actionAdapter(this));
    this.jPanel5.setLayout(this.flowLayout1);
    this.jPanel6.setAlignmentX(0.5F);
    this.btnUSBDelete.setText(ATPMessages.getString("MW.GNL.DELETE"));
    this.btnUSBDelete.addActionListener((ActionListener)new pnlMissionDownload_btnUSBDelete_actionAdapter(this));
    this.btnUSBRefrsh.setText(ATPMessages.getString("MW.MD.REFRESH"));
    this.btnUSBRefrsh.addActionListener((ActionListener)new pnlMissionDownload_btnUSBRefrsh_actionAdapter(this));
    this.jTable1.setAutoResizeMode(0);
    this.jTable1.setRowSelectionAllowed(true);
    this.jTable1.setSelectionMode(0);
    this.jTable1.setSelectionBackground(Color.blue);
    this.jTable1.addPropertyChangeListener((PropertyChangeListener)new pnlMissionDownload_jTable1_propertyChangeAdapter(this));
    this.jTable1.addKeyListener((KeyListener)new pnlMissionDownload_jTable1_keyAdapter(this));
    this.jTable1.addMouseListener((MouseListener)new pnlMissionDownload_jTable1_mouseAdapter(this));
    this.jTable1.addFocusListener((FocusListener)new pnlMissionDownload_jTable1_focusAdapter(this));
    this.jTable3.setAutoResizeMode(0);
    this.jPanel4.setBorder(BorderFactory.createRaisedBevelBorder());
    this.jPanel8.setLayout(this.borderLayout4);
    this.jPanel7.setLayout(this.borderLayout5);
    this.showAllTrainRunning.setText(ATPMessages.getString("MW.MD.SHOW_MISSION_TODAY"));
    this.showAllTrainRunning.addActionListener((ActionListener)new pnlMissionDownload_showTrainRunning_actionAdapter(this));
    this.dateChooser.setDateFormatString("yyyy-MM-dd");
    this.dateChooser.setCalendar(new GregorianCalendar());
    this.dateChooser.addPropertyChangeListener((PropertyChangeListener)new pnlMissionDownload_dateChooser_propertyChangeAdapter(this));
    this.jLabel4.setText("");
    add(this.jLabel1, "North");
    add(this.jPanel1, "Center");
    this.jPanel1.add(this.jSplitPane1, new GridBagConstraints(0, 0, 2, 1, 1.0D, 1.0D, 10, 1, new Insets(4, 3, 0, 5), -64, -633));
    this.jSplitPane1.add(this.jPanel2, "top");
    this.jPanel2.add(this.jPanel4, "North");
    this.jPanel4.add(this.jLabel3, new XYConstraints(1, 2, -1, -1));
    this.jPanel4.add((Component)this.dateChooser, new XYConstraints(135, 1, 100, -1));
    this.jPanel2.add(this.jSplitPane2, "Center");
    this.jSplitPane2.add(this.jPanel7, "right");
    this.jPanel7.add(this.jScrollPane3, "Center");
    this.jPanel7.add(this.jPanel9, "South");
    this.jPanel7.add(this.jLabel4, "North");
    this.jPanel9.add(this.showAllTrainRunning, (Object)null);
    this.jSplitPane2.add(this.jPanel8, "left");
    this.jPanel8.add(this.jScrollPane1, "Center");
    this.jPanel8.add(this.jPanel5, "South");
    this.jPanel5.add(this.btnLoad, (Object)null);
    this.jScrollPane1.getViewport().add(this.jTable1, (Object)null);
    this.jScrollPane3.getViewport().add(this.jTable3, (Object)null);
    this.jSplitPane1.add(this.jPanel3, "bottom");
    this.jPanel3.add(this.jLabel2, "North");
    this.jPanel3.add(this.jScrollPane2, "Center");
    this.jPanel3.add(this.jPanel6, "South");
    this.jPanel6.add(this.btnUSBRefrsh, (Object)null);
    this.jPanel6.add(this.btnUSBDelete, (Object)null);
    this.jScrollPane2.getViewport().add(this.jTable2, (Object)null);
    this.jSplitPane1.setDividerLocation(310);
    this.jSplitPane2.setDividerLocation(540);
    this._$2154.add(ATPMessages.getString("MW.WS.START_TIME"));
    this._$2154.add(ATPMessages.getString("MW.WS.ID"));
    this._$2154.add(ATPMessages.getString("MW.WS.WORKDATE"));
    this._$2154.add(ATPMessages.getString("MW.DRIVER.ID"));
    this._$2154.add(ATPMessages.getString("MW.DRIVER.NAME"));
    this._$2154.add(ATPMessages.getString("MW.MD.Q_MISSION_DOWNLOAD"));
    this._$2155.add(ATPMessages.getString("MW.TR.ID"));
    this._$2155.add(ATPMessages.getString("MW.WS.TR_WORKDATE"));
    this._$2155.add(ATPMessages.getString("MW.TT.ID"));
    this._$2155.add(ATPMessages.getString("MW.TD.ID"));
    this._$2155.add(ATPMessages.getString("MW.VEHICLE.ID"));
    this._$2153.add(ATPMessages.getString("MW.DRIVER.ID"));
    this._$2153.add(ATPMessages.getString("MW.WS.WORKDATE"));
    this._$2153.add(ATPMessages.getString("MW.WS.ID"));
    this._$2153.add(ATPMessages.getString("MW.TR.ID"));
    this._$2153.add(ATPMessages.getString("MW.WS.TR_WORKDATE"));
    this._$2153.add(ATPMessages.getString("MW.TT.ID"));
    this._$2153.add(ATPMessages.getString("MW.TD.ID"));
  }
  
  private boolean _$2253() {
    String str = InitParameters.USBPath;
    boolean bool = false;
    if ((new File(str)).exists()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  void jTable1_focusGained(FocusEvent paramFocusEvent) {
    try {
      _$2233();
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void jTable1_keyReleased(KeyEvent paramKeyEvent) {
    try {
      if (paramKeyEvent.getKeyCode() == 38 || paramKeyEvent.getKeyCode() == 40 || paramKeyEvent.getKeyCode() == 10)
        _$2233(); 
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void jTable1_mouseClicked(MouseEvent paramMouseEvent) {
    try {
      this.isShowAllTrainRunningData = false;
      _$2233();
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void jTable1_propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
  
  private void _$2326(Vector paramVector) throws Exception {
    Vector vector = new Vector();
    Vector vector1 = new Vector();
    Vector vector2 = new Vector();
    Vector vector3 = new Vector();
    for (byte b = 0; b < paramVector.size(); b++) {
      StringBuffer stringBuffer = new StringBuffer();
      vector1 = _$2255(((Vector)paramVector.get(b)).get(7).toString());
      vector2 = _$2258(((Vector)paramVector.get(b)).get(6).toString());
      vector3 = _$2261(paramVector.get(b));
      for (byte b1 = 0; b1 < vector3.size(); b1++) {
        stringBuffer.append(((Vector)vector3.get(b1)).get(0));
        stringBuffer.append(",");
      } 
      String str = "INSERT INTO MissionDownloadHistory  VALUES ('" + frmMain.getUserID() + "','" + (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date()) + "','" + ((Vector)paramVector.get(b)).get(0) + "','" + ((Vector)paramVector.get(b)).get(3) + "','" + ((Vector)paramVector.get(b)).get(4) + "','" + this.dateFormat.format(((Vector)paramVector.get(b)).get(2)) + "','" + this.dateFormat.format(((Vector)paramVector.get(b)).get(5)) + "','" + ((Vector)paramVector.get(b)).get(6) + "','" + ((Vector)paramVector.get(b)).get(7) + "','spare','spare','" + ((Vector)vector1.get(0)).get(4) + "','" + ((Vector)vector1.get(0)).get(3).toString() + "','" + ((Vector)vector2.get(0)).get(2).toString() + "','spare','" + this._$2189.format(((Vector)vector1.get(0)).get(8)) + "','spare','" + this._$2189.format(((Vector)vector1.get(0)).get(10)) + "','" + this._$2189.format(((Vector)vector1.get(0)).get(7)) + "','" + this._$2189.format(((Vector)vector1.get(0)).get(9)) + "','','" + this._$2189.format(((Vector)vector1.get(0)).get(6)) + "','spare','" + ((Vector)vector2.get(0)).get(6).toString() + "','" + ((Vector)vector2.get(0)).get(7).toString() + "','" + ((Vector)vector2.get(0)).get(8).toString() + "','" + ((Vector)vector2.get(0)).get(9).toString() + "','" + ((Vector)vector2.get(0)).get(10).toString() + "','spare','spare','spare','spare','spare','" + stringBuffer + "','" + ((Vector)paramVector.get(b)).get(9) + "') ";
      this.connDB.Insert(str);
    } 
  }
  
  private String _$2248(String paramString) throws Exception {
    boolean bool = false;
    String str = "";
    File file = new File(paramString);
    FileInputStream fileInputStream = new FileInputStream(file);
    str = StringHandler.InputStreamToString(fileInputStream);
    str = str.substring(0, str.length() - 8);
    str = str.replaceAll("\n", "");
    fileInputStream.close();
    return str;
  }
  
  private void _$2340(Vector paramVector) {
    String str = "";
    Vector vector = paramVector;
    File file1 = new File(this.fileTableA + "\\TrainData.txt");
    File file2 = new File(this.fileTableA + "\\TrainData2.txt");
    try {
      int i = this.dtm2.getRowCount();
      file1.delete();
      file2.delete();
      FileWriter fileWriter = new FileWriter(this.fileTableA + "\\TrainData.txt");
      for (byte b = 0; b < vector.size(); b++) {
        String str1 = "";
        str1 = "";
        str1 = str1 + vector.get(b);
        str1 = str1.substring(1, str1.length() - 1).trim();
        str = str + str1 + ",;\n";
        str = str.replaceAll(" ", "");
      } 
      fileWriter.write(_$2349(str));
      fileWriter.close();
      _$2350(_$2349(str), "\\TrainData2.txt");
      Refresh();
    } catch (IOException iOException) {
      iOException.printStackTrace();
      vector = new Vector();
      this.dtm2.setDataVector(vector, this._$2153);
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.MD.NO_USB"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      this.stop = true;
    } 
  }
  
  void showTrainRunning_actionPerformed(ActionEvent paramActionEvent) {
    try {
      this.isShowAllTrainRunningData = true;
      _$2233();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$2196() {
    this.trainCategoryRunnable = new TrainCategoryRunnable();
    this.trainTypeRunnable = new TrainTypeRunnable();
    this.maintenanceRunnable = new MaintenanceRunnable();
  }
}
