package ui.panels;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.RefreshFrame;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.MultiLineHeaderRenderer;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMissionDownloadLog_jTable1_focusAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMissionDownloadLog_jTable1_keyAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMissionDownloadLog_jTable1_mouseAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMissionDownloadLog_searchOfMissionDownload_propertyChangeAdapter;
import com.MiTAC.TRA.ATP.ui.panels.pnlSearch;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class pnlMissionDownloadLog extends JPanel implements RefreshFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  
  GregorianCalendar calendar = new GregorianCalendar();
  
  ConnectDB conDB = new ConnectDB();
  
  DefaultSortTableModel dtm1 = new DefaultSortTableModel();
  
  JPanel jPanel1 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JScrollPane jScrollPane2 = new JScrollPane();
  
  JSplitPane jSplitPane1 = new JSplitPane();
  
  JSortTable jTable1 = new JSortTable((SortTableModel)this.dtm1);
  
  JTextField jTextField19 = new JTextField();
  
  JLabel lblTitle = new JLabel();
  
  Vector logData = new Vector();
  
  Vector logDataName = new Vector();
  
  Vector logDataSearch = new Vector();
  
  pnlSearch pnlsearch = new pnlSearch(true);
  
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  JTextArea stationList = new JTextArea("");
  
  JTextArea taCategory = new JTextArea();
  
  JTextArea taEBDec = new JTextArea();
  
  JTextArea taEBDelay = new JTextArea();
  
  JTextArea taGradient0 = new JTextArea();
  
  JTextArea taGradient10 = new JTextArea();
  
  JTextArea taGradient15 = new JTextArea();
  
  JTextArea taGradient20 = new JTextArea();
  
  JTextArea taGradient5 = new JTextArea();
  
  JTextArea taLength = new JTextArea();
  
  JTextArea taMaxAcc = new JTextArea();
  
  JTextArea taSBDec = new JTextArea();
  
  JTextArea taSBDelay = new JTextArea();
  
  JTextArea taSpeed = new JTextArea();
  
  JTextArea taStation = new JTextArea();
  
  JTextArea taTRDate = new JTextArea();
  
  JTextArea taType = new JTextArea();
  
  JTextArea taVID = new JTextArea();
  
  JTextArea taWSDate = new JTextArea();
  
  JTextArea taWeight = new JTextArea();
  
  JTextField tfCategory = new JTextField();
  
  JTextField tfEBDec = new JTextField();
  
  JTextField tfEBDelay = new JTextField();
  
  JTextField tfGradient0 = new JTextField();
  
  JTextField tfGradient10 = new JTextField();
  
  JTextField tfGradient15 = new JTextField();
  
  JTextField tfGradient20 = new JTextField();
  
  JTextField tfGradient5 = new JTextField();
  
  JTextField tfLength = new JTextField();
  
  JTextField tfMaxAcc = new JTextField();
  
  JTextField tfSBDec = new JTextField();
  
  JTextField tfSBDelay = new JTextField();
  
  JTextField tfSpeed = new JTextField();
  
  JTextField tfTRDate = new JTextField();
  
  JTextField tfType = new JTextField();
  
  JTextField tfVID = new JTextField();
  
  JTextField tfWSDate = new JTextField();
  
  JTextField tfWeight = new JTextField();
  
  XYLayout xYLayout1 = new XYLayout();
  
  public pnlMissionDownloadLog() {
    try {
      _$3120();
      _$17368();
      _$1631();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void Refresh() {
    try {
      String str1 = "SELECT UserID, UseTime,DID,WSNo,TRNo FROM MissionDownloadHistory";
      Date date = this.pnlsearch.getSearchList().get(0);
      StringBuffer stringBuffer = new StringBuffer();
      if (date != null) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.add(5, 1);
        stringBuffer.append(" UseTime between '");
        stringBuffer.append("" + simpleDateFormat.format(date));
        stringBuffer.append("' AND '");
        stringBuffer.append(simpleDateFormat.format(gregorianCalendar.getTime()));
        stringBuffer.append("'");
      } else {
        stringBuffer.append(" UseTime not like ''");
      } 
      String str2 = this.pnlsearch.getSearchString();
      int i = str2.indexOf(" AND");
      if (i > 0) {
        str2 = str2.substring(i, str2.length());
      } else {
        str2 = "";
      } 
      str1 = str1 + " WHERE " + stringBuffer.toString() + str2;
      this.logData = this.conDB.getData(str1);
      if (this.logData.size() == 0) {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.SEARCH.NOT_FOUND"), ATPMessages.getString("MW.GNL.INFO"), 1);
        this.dtm1.setDataVector(this.logData, this.logDataName);
        this.jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.jTable1.getColumnModel().getColumn(1).setPreferredWidth(120);
        this.jTable1.getColumnModel().getColumn(2).setPreferredWidth(60);
        this.jTable1.getColumnModel().getColumn(3).setPreferredWidth(60);
        this.jTable1.getColumnModel().getColumn(4).setPreferredWidth(50);
        this.tfWSDate.setText("");
        this.tfTRDate.setText("");
        this.tfCategory.setText("");
        this.tfVID.setText("");
        this.tfType.setText("");
        this.tfWeight.setText("");
        this.tfLength.setText("");
        this.tfSpeed.setText("");
        this.tfSBDec.setText("");
        this.tfEBDec.setText("");
        this.tfSBDelay.setText("");
        this.tfEBDelay.setText("");
        this.tfMaxAcc.setText("");
        this.tfGradient0.setText("");
        this.tfGradient5.setText("");
        this.tfGradient10.setText("");
        this.tfGradient15.setText("");
        this.tfGradient20.setText("");
        this.stationList.setText("");
      } else {
        this.dtm1.setDataVector(this.logData, this.logDataName);
        this.jTable1.setRowSelectionInterval(0, 0);
        _$17413();
        this.jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.jTable1.getColumnModel().getColumn(1).setPreferredWidth(120);
        this.jTable1.getColumnModel().getColumn(2).setPreferredWidth(60);
        this.jTable1.getColumnModel().getColumn(3).setPreferredWidth(60);
        this.jTable1.getColumnModel().getColumn(4).setPreferredWidth(50);
        TableColumn tableColumn = this.jTable1.getColumnModel().getColumn(1);
        tableColumn.setCellRenderer((TableCellRenderer)new Object(this));
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$17368() throws Exception {
    this.calendar.setTimeInMillis(System.currentTimeMillis());
    this.calendar.add(2, -2);
    String str = "DELETE FROM MissionDownloadHistory WHERE UseTime < '" + this.simpleDateFormat.format(this.calendar.getTime()) + "'";
    this.conDB.Delete(str);
  }
  
  private void _$1631() {
    try {
      String str = "SELECT UserID,UseTime,DID,WSNo,TRNo FROM MissionDownloadHistory";
      this.logData = this.conDB.getData(str);
      this.dtm1.setDataVector(this.logData, this.logDataName);
      this.jTable1.setRowSelectionInterval(0, 0);
      _$17413();
      this.jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
      this.jTable1.getColumnModel().getColumn(1).setPreferredWidth(120);
      this.jTable1.getColumnModel().getColumn(2).setPreferredWidth(60);
      this.jTable1.getColumnModel().getColumn(3).setPreferredWidth(60);
      this.jTable1.getColumnModel().getColumn(4).setPreferredWidth(50);
      TableColumn tableColumn = this.jTable1.getColumnModel().getColumn(1);
      tableColumn.setCellRenderer((TableCellRenderer)new Object(this));
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$17413() {
    try {
      String str = "SELECT VaildDate,EndDate,TRA_Train_Category,Vehicle_ID,Train_TypeNo,Max_Train_Weight,Max_Train_Length,Max_Train_Speed,Min_SB_Deceleration,Min_EB_Deceleration,SB_delay_time,EB_delay_time,Max_Acceleration,Downhill_0,Downhill_5,Downhill_10,Downhill_15,Downhill_20,Station FROM MissionDownloadHistory WHERE UseTime ='" + (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 1)) + "' AND DID ='" + this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 2) + "' AND WSNo ='" + this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 3) + "' AND TRNo ='" + this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 4) + "'";
      this.logDataSearch = this.conDB.getData(str);
      this.tfWSDate.setText((new SimpleDateFormat("yyyy/MM/dd")).format(((Vector)this.logDataSearch.get(0)).get(0)));
      this.tfTRDate.setText((new SimpleDateFormat("yyyy/MM/dd")).format(((Vector)this.logDataSearch.get(0)).get(1)));
      this.tfCategory.setText(((Vector)this.logDataSearch.get(0)).get(2).toString());
      this.tfVID.setText(((Vector)this.logDataSearch.get(0)).get(3).toString());
      this.tfType.setText(((Vector)this.logDataSearch.get(0)).get(4).toString());
      this.tfWeight.setText(((Vector)this.logDataSearch.get(0)).get(5).toString());
      this.tfLength.setText(((Vector)this.logDataSearch.get(0)).get(6).toString());
      this.tfSpeed.setText(((Vector)this.logDataSearch.get(0)).get(7).toString());
      this.tfSBDec.setText(((Vector)this.logDataSearch.get(0)).get(8).toString());
      this.tfEBDec.setText(((Vector)this.logDataSearch.get(0)).get(9).toString());
      this.tfSBDelay.setText(((Vector)this.logDataSearch.get(0)).get(10).toString());
      this.tfEBDelay.setText(((Vector)this.logDataSearch.get(0)).get(11).toString());
      this.tfMaxAcc.setText(((Vector)this.logDataSearch.get(0)).get(12).toString());
      this.tfGradient0.setText(((Vector)this.logDataSearch.get(0)).get(13).toString());
      this.tfGradient5.setText(((Vector)this.logDataSearch.get(0)).get(14).toString());
      this.tfGradient10.setText(((Vector)this.logDataSearch.get(0)).get(15).toString());
      this.tfGradient15.setText(((Vector)this.logDataSearch.get(0)).get(16).toString());
      this.tfGradient20.setText(((Vector)this.logDataSearch.get(0)).get(17).toString());
      this.stationList.setText(_$17414(((Vector)this.logDataSearch.get(0)).get(18).toString()));
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private String _$17414(String paramString) {
    String str1 = paramString;
    StringBuffer stringBuffer1 = new StringBuffer();
    StringBuffer stringBuffer2 = new StringBuffer();
    String str2 = "";
    Vector vector = new Vector();
    String str3 = "";
    Vector vector1 = new Vector();
    StringBuffer stringBuffer3 = new StringBuffer();
    int i = str1.length();
    try {
      if (i != 0) {
        for (byte b1 = 0; b1 < i; b1++) {
          if (str1.charAt(b1) != ',') {
            stringBuffer3.append(str1.charAt(b1));
          } else {
            vector1.addElement(stringBuffer3);
            stringBuffer3 = new StringBuffer();
          } 
        } 
        int j = vector1.size();
        for (byte b2 = 0; b2 < j; b2++) {
          vector = new Vector();
          str3 = "SELECT  StationCName FROM Station WHERE StationID ='" + vector1.get(b2) + "'";
          vector = this.conDB.getData(str3);
          stringBuffer2.append("[");
          stringBuffer2.append(((Vector)vector.get(0)).get(0));
          stringBuffer2.append("]");
        } 
        str2 = stringBuffer2.toString();
      } else {
        str2 = "無設定停靠站";
      } 
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
      stringIndexOutOfBoundsException.printStackTrace();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return str2;
  }
  
  private void _$3120() throws Exception {
    this.lblTitle.setBorder(BorderFactory.createEtchedBorder());
    this.lblTitle.setText(ATPMessages.getString("MW.MAIN.HISTORY.MISSION_DOWNLOAD"));
    setLayout(this.borderLayout1);
    this.jTable1.setAutoResizeMode(0);
    this.jTable1.addMouseListener((MouseListener)new pnlMissionDownloadLog_jTable1_mouseAdapter(this));
    this.jTable1.addFocusListener((FocusListener)new pnlMissionDownloadLog_jTable1_focusAdapter(this));
    this.jTable1.addKeyListener((KeyListener)new pnlMissionDownloadLog_jTable1_keyAdapter(this));
    this.jTable1.getTableHeader().setDefaultRenderer((TableCellRenderer)new MultiLineHeaderRenderer());
    this.jPanel1.setLayout((LayoutManager)this.xYLayout1);
    add(this.lblTitle, "North");
    add(this.jSplitPane1, "Center");
    add((Component)this.pnlsearch, "East");
    this.jSplitPane1.add(this.jScrollPane1, "left");
    this.jSplitPane1.add(this.jPanel1, "right");
    this.taWSDate.setBackground(new Color(212, 208, 200));
    this.taWSDate.setLineWrap(true);
    this.taWSDate.setText(ATPMessages.getString("MW.WS.WORKDATE"));
    this.taTRDate.setBackground(new Color(212, 208, 200));
    this.taTRDate.setLineWrap(true);
    this.taTRDate.setText(ATPMessages.getString("MW.WS.TR_WORKDATE"));
    this.taType.setBackground(new Color(212, 208, 200));
    this.taType.setLineWrap(true);
    this.taType.setText(ATPMessages.getString("MW.TT"));
    this.taVID.setBackground(new Color(212, 208, 200));
    this.taVID.setLineWrap(true);
    this.taVID.setText(ATPMessages.getString("MW.VEHICLE.ID"));
    this.taCategory.setBackground(new Color(212, 208, 200));
    this.taCategory.setLineWrap(true);
    this.taCategory.setText(ATPMessages.getString("MW.TD"));
    this.taWeight.setBackground(new Color(212, 208, 200));
    this.taWeight.setLineWrap(true);
    this.taWeight.setText(ATPMessages.getString("MW.TD.WEIGHT_EMPTY(ton)"));
    this.taLength.setBackground(new Color(212, 208, 200));
    this.taLength.setLineWrap(true);
    this.taLength.setText(ATPMessages.getString("MW.TD.LENGTH(m)"));
    this.taSpeed.setBackground(new Color(212, 208, 200));
    this.taSpeed.setText(ATPMessages.getString("MW.TD.SPEED(Km/hr)"));
    this.taSBDec.setBackground(new Color(212, 208, 200));
    this.taSBDec.setLineWrap(true);
    this.taSBDec.setText(ATPMessages.getString("MW.TD.SB_DECELERATION(m/s/s)"));
    this.taEBDec.setBackground(new Color(212, 208, 200));
    this.taEBDec.setLineWrap(true);
    this.taEBDec.setText(ATPMessages.getString("MW.TD.EB_DECELERATION(m/s/s)"));
    this.taSBDelay.setBackground(new Color(212, 208, 200));
    this.taSBDelay.setLineWrap(true);
    this.taSBDelay.setText(ATPMessages.getString("MW.TD.SB_DELAY_TIME(s)"));
    this.taEBDelay.setBackground(new Color(212, 208, 200));
    this.taEBDelay.setLineWrap(true);
    this.taEBDelay.setText(ATPMessages.getString("MW.TD.EB_DELAY_TIME(s)"));
    this.taMaxAcc.setBackground(new Color(212, 208, 200));
    this.taMaxAcc.setLineWrap(true);
    this.taMaxAcc.setText(ATPMessages.getString("MW.TD.MAX_ACCELERATION(m/s/s)"));
    this.taGradient0.setBackground(new Color(212, 208, 200));
    this.taGradient0.setLineWrap(true);
    this.taGradient0.setText(ATPMessages.getString("MW.TT.GRADIENTS0(Km/hr)"));
    this.taGradient5.setBackground(new Color(212, 208, 200));
    this.taGradient5.setLineWrap(true);
    this.taGradient5.setText(ATPMessages.getString("MW.TT.GRADIENTS5(Km/hr)"));
    this.taGradient10.setBackground(new Color(212, 208, 200));
    this.taGradient10.setLineWrap(true);
    this.taGradient10.setText(ATPMessages.getString("MW.TT.GRADIENTS10(Km/hr)"));
    this.taGradient15.setBackground(new Color(212, 208, 200));
    this.taGradient15.setLineWrap(true);
    this.taGradient15.setText(ATPMessages.getString("MW.TT.GRADIENTS15(Km/hr)"));
    this.taGradient20.setBackground(new Color(212, 208, 200));
    this.taGradient20.setLineWrap(true);
    this.taGradient20.setText(ATPMessages.getString("MW.TT.GRADIENTS20(Km/hr)"));
    this.taStation.setBackground(new Color(212, 208, 200));
    this.taStation.setText(ATPMessages.getString("MW.TR.STOPSTATION"));
    this.stationList.setLineWrap(true);
    this.stationList.setWrapStyleWord(true);
    this.jScrollPane2.add(this.stationList);
    this.tfWSDate.setText("");
    this.tfTRDate.setText("");
    this.tfCategory.setText("");
    this.tfVID.setText("");
    this.tfType.setText("");
    this.tfWeight.setText("");
    this.tfLength.setText("");
    this.tfSpeed.setText("");
    this.tfSBDec.setText("");
    this.tfEBDec.setText("");
    this.tfSBDelay.setText("");
    this.tfEBDelay.setText("");
    this.tfMaxAcc.setText("");
    this.tfGradient0.setText("");
    this.tfGradient5.setText("");
    this.tfGradient10.setText("");
    this.tfGradient15.setText("");
    this.tfGradient20.setText("");
    this.jPanel1.add(this.taWSDate, new XYConstraints(10, 10, 85, -1));
    this.jPanel1.add(this.tfWSDate, new XYConstraints(20, 30, 65, 20));
    this.jPanel1.add(this.taTRDate, new XYConstraints(130, 10, 85, -1));
    this.jPanel1.add(this.tfTRDate, new XYConstraints(130, 30, 65, 20));
    this.jPanel1.add(this.taType, new XYConstraints(250, 10, 85, -1));
    this.jPanel1.add(this.tfCategory, new XYConstraints(250, 30, 50, 20));
    this.jPanel1.add(this.taVID, new XYConstraints(10, 60, 85, -1));
    this.jPanel1.add(this.tfVID, new XYConstraints(20, 80, 60, 20));
    this.jPanel1.add(this.taCategory, new XYConstraints(130, 60, 85, -1));
    this.jPanel1.add(this.tfType, new XYConstraints(130, 80, 50, 20));
    this.jPanel1.add(this.taWeight, new XYConstraints(250, 60, 85, -1));
    this.jPanel1.add(this.tfWeight, new XYConstraints(250, 80, 60, 20));
    this.jPanel1.add(this.taLength, new XYConstraints(10, 110, 85, -1));
    this.jPanel1.add(this.tfLength, new XYConstraints(20, 130, 60, 20));
    this.jPanel1.add(this.taSpeed, new XYConstraints(130, 110, 85, -1));
    this.jPanel1.add(this.tfSpeed, new XYConstraints(130, 130, 60, 20));
    this.jPanel1.add(this.taSBDec, new XYConstraints(250, 110, 85, -1));
    this.jPanel1.add(this.tfSBDec, new XYConstraints(250, 150, 60, 20));
    this.jPanel1.add(this.taEBDec, new XYConstraints(10, 180, 85, -1));
    this.jPanel1.add(this.tfEBDec, new XYConstraints(20, 220, 60, 20));
    this.jPanel1.add(this.taSBDelay, new XYConstraints(130, 180, 85, -1));
    this.jPanel1.add(this.tfSBDelay, new XYConstraints(130, 220, 60, 20));
    this.jPanel1.add(this.taEBDelay, new XYConstraints(250, 180, 85, -1));
    this.jPanel1.add(this.tfEBDelay, new XYConstraints(250, 220, 60, 20));
    this.jPanel1.add(this.taMaxAcc, new XYConstraints(10, 250, 85, -1));
    this.jPanel1.add(this.tfMaxAcc, new XYConstraints(20, 290, 60, 20));
    this.jPanel1.add(this.taGradient0, new XYConstraints(130, 250, 85, -1));
    this.jPanel1.add(this.tfGradient0, new XYConstraints(130, 310, 60, 20));
    this.jPanel1.add(this.taGradient5, new XYConstraints(250, 250, 85, -1));
    this.jPanel1.add(this.tfGradient5, new XYConstraints(250, 310, 60, 20));
    this.jPanel1.add(this.taGradient10, new XYConstraints(10, 340, 85, -1));
    this.jPanel1.add(this.tfGradient10, new XYConstraints(20, 400, 60, 20));
    this.jPanel1.add(this.taGradient15, new XYConstraints(130, 340, 85, -1));
    this.jPanel1.add(this.tfGradient15, new XYConstraints(130, 400, 60, 20));
    this.jPanel1.add(this.taGradient20, new XYConstraints(250, 340, 85, -1));
    this.jPanel1.add(this.tfGradient20, new XYConstraints(250, 400, 60, 20));
    this.jPanel1.add(this.taStation, new XYConstraints(10, 430, 60, -1));
    this.jPanel1.add(this.jScrollPane2, new XYConstraints(20, 450, 307, 150));
    this.jScrollPane1.getViewport().add((Component)this.jTable1, (Object)null);
    this.jScrollPane2.getViewport().add(this.stationList, (Object)null);
    this.jSplitPane1.setDividerLocation(345);
    this.logDataName.add(ATPMessages.getString("MW.LOGIN.USERID"));
    this.logDataName.add(ATPMessages.getString("MW.MD.DOWNLOAD_MISSION.TIME"));
    this.logDataName.add(ATPMessages.getString("MW.DRIVER.ID"));
    this.logDataName.add(ATPMessages.getString("MW.WS.ID"));
    this.logDataName.add(ATPMessages.getString("MW.TR.ID"));
    this.pnlsearch.addPropertyChangeListener((PropertyChangeListener)new pnlMissionDownloadLog_searchOfMissionDownload_propertyChangeAdapter(this));
    this.pnlsearch.hideOption(7, false);
    this.pnlsearch.hideOption(5, false);
    this.pnlsearch.hideOption(3, false);
  }
  
  void jTable1_focusGained(FocusEvent paramFocusEvent) {
    _$17413();
  }
  
  void jTable1_keyReleased(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyCode() == 38 || paramKeyEvent.getKeyCode() == 40 || paramKeyEvent.getKeyCode() == 10)
      try {
        _$17413();
      } catch (Exception exception) {
        exception.printStackTrace();
        JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
      }  
  }
  
  void jTable1_mouseClicked(MouseEvent paramMouseEvent) {
    _$17413();
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      InitParameters.start();
      new com.MiTAC.TRA.ATP.ui.pnlMissionDownloadLog();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void searchOfMissionDownload_propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    if (paramPropertyChangeEvent.getPropertyName() == "search") {
      Refresh();
    } else if (paramPropertyChangeEvent.getPropertyName().equals("save")) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");
      this.pnlsearch.doExport((TableModel)this.dtm1, this.lblTitle.getText() + "_" + simpleDateFormat.format(new Date(System.currentTimeMillis())), true);
    } 
  }
}
