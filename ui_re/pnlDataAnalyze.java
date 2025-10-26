package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.RefreshFrame;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.pnlDataAnalyze_btn_actionListener;
import com.MiTAC.TRA.ATP.ui.pnlDataAnalyze_pnlsearch_propertyChangeAdapter;
import com.MiTAC.TRA.ATP.ui.pnlSearch;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ProgressMonitor;
import javax.swing.Timer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class pnlDataAnalyze extends JPanel implements RefreshFrame {
  JButton btnAccidentAnalyze = new JButton();
  
  JButton btnCabinFailureReport = new JButton();
  
  JButton btnDeleteRecord = new JButton();
  
  JButton btnDriverBehaviorReport = new JButton();
  
  JButton btnFilterCabinFailureReport = new JButton();
  
  JButton btnFilterDriverBehaviorReport = new JButton();
  
  JButton btnFilterWaysideFailureReport = new JButton();
  
  JButton btnRefresh = new JButton();
  
  JButton btnStopStationExport = new JButton();
  
  JButton btnWaysideFailureReport = new JButton();
  
  Vector deleteData = new Vector();
  
  DefaultSortTableModel dtm = new DefaultSortTableModel();
  
  JPanel jPanel4 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JSortTable jTable1 = new JSortTable((SortTableModel)this.dtm);
  
  JLabel lblTitle = new JLabel();
  
  ProgressMonitor monitor;
  
  int monitorLength = 100;
  
  String monitorMessage = ATPMessages.getString("MW.MONITOR.PREPARING");
  
  int monitorProgress = 0;
  
  pnlSearch pnlsearch = new pnlSearch(true);
  
  private static final long serialVersionUID = 1L;
  
  com.MiTAC.TRA.ATP.ui.pnlDataAnalyze thisPanel;
  
  Vector tmp;
  
  Vector vData;
  
  Vector vDataName;
  
  Timer watcher;
  
  public pnlDataAnalyze() {
    try {
      init();
      Refresh();
      this.thisPanel = this;
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void Refresh() {
    try {
      ConnectDB connectDB = new ConnectDB();
      this.vData = connectDB.getData("SELECT MissionDate, WSNo, TRNo, DID, VID, EBTimes, SBTimes, CabinFailureTimes, WaysideFailureTimes,CAST([Isolation] AS bit) AS [Isolation], StartTime, EndTime, Duration FROM LOGCATEGORY " + this.pnlsearch.getSearchString());
      if (this.vData.size() == 0) {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.SEARCH.NOT_FOUND"), ATPMessages.getString("MW.GNL.INFO"), 1);
      } else {
        _$6990();
      } 
      this.dtm.setDataVector(this.vData, this.vDataName);
      this.jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
      this.jTable1.getColumnModel().getColumn(1).setPreferredWidth(80);
      this.jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
      this.jTable1.getColumnModel().getColumn(3).setPreferredWidth(130);
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      TableColumn tableColumn1 = this.jTable1.getColumnModel().getColumn(3);
      tableColumn1.setCellRenderer((TableCellRenderer)new Object(this));
      TableColumn tableColumn2 = this.jTable1.getColumnModel().getColumn(this.jTable1.getColumnCount() - 3);
      tableColumn2.setCellRenderer((TableCellRenderer)new Object(this));
      TableColumn tableColumn3 = this.jTable1.getColumnModel().getColumn(this.jTable1.getColumnCount() - 2);
      tableColumn3.setCellRenderer((TableCellRenderer)new Object(this));
      TableColumn tableColumn4 = this.jTable1.getColumnModel().getColumn(this.jTable1.getColumnCount() - 1);
      tableColumn4.setCellRenderer((TableCellRenderer)new Object(this));
    } 
  }
  
  void action_btnAccidentAnalyze() {
    this.monitor = new ProgressMonitor(this, ATPMessages.getString("MW.LA.ACCIDENT.ANALYZE"), ATPMessages.getString("MW.MONITOR.PREPARING"), 0, 100);
    this.monitor.setMillisToDecideToPopup(0);
    this.monitor.setMillisToPopup(0);
    this.watcher = new Timer(100, (ActionListener)new Object(this));
    Object object = new Object(this);
    object.start();
    this.watcher.start();
  }
  
  void action_btnCabinFailureReport() {
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LA.CABIN_FAILURE.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      this.monitor = new ProgressMonitor(this, ATPMessages.getString("MW.LA.CABIN_FAILURE.REPORT.MAKE"), ATPMessages.getString("MW.MONITOR.PREPARING"), 0, 100);
      this.monitor.setMillisToDecideToPopup(0);
      this.monitor.setMillisToPopup(0);
      this.watcher = new Timer(100, (ActionListener)new Object(this));
      Object object = new Object(this);
      object.start();
      this.watcher.start();
    } 
  }
  
  void action_btnDeleteRecord() {
    if (this.deleteData.size() <= 0) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LA.DELETE.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else if (JOptionPane.showConfirmDialog(this.thisPanel, ATPMessages.getString("MW.LA.DELETE.Q.CONFIRM"), ATPMessages.getString("MW.GNL.CONFIRM"), 0, 3) == 0) {
      this.monitor = new ProgressMonitor(this, ATPMessages.getString("MW.LA.DELETE"), ATPMessages.getString("MW.MONITOR.PREPARING"), 0, 100);
      this.monitor.setMillisToDecideToPopup(0);
      this.monitor.setMillisToPopup(0);
      this.watcher = new Timer(100, (ActionListener)new Object(this));
      Object object = new Object(this);
      object.start();
      this.watcher.start();
    } 
  }
  
  void action_btnDriverBehaviorReport() {
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.ANALYZE.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      this.monitor = new ProgressMonitor(this, ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.ANALYZE"), ATPMessages.getString("MW.MONITOR.PREPARING"), 0, 100);
      this.monitor.setMillisToDecideToPopup(0);
      this.monitor.setMillisToPopup(0);
      this.watcher = new Timer(100, (ActionListener)new Object(this));
      Object object = new Object(this);
      object.start();
      this.watcher.start();
    } 
  }
  
  void action_btnFilterCabinFailureReport() {
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LA.FILTER_CABIN_FAILURE.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      this.monitor = new ProgressMonitor(this, ATPMessages.getString("MW.LA.FILTER_CABIN_FAILURE.NO_SELECTED"), ATPMessages.getString("MW.MONITOR.PREPARING"), 0, 100);
      this.monitor.setMillisToDecideToPopup(0);
      this.monitor.setMillisToPopup(0);
      this.watcher = new Timer(100, (ActionListener)new Object(this));
      Object object = new Object(this);
      object.start();
      this.watcher.start();
    } 
  }
  
  void action_btnFilterDriverBehaviorReport() {
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LA.FILTER_DRIVER_BEHAVIOR.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      this.monitor = new ProgressMonitor(this, ATPMessages.getString("MW.LA.FILTER_DRIVER_BEHAVIOR.REPORT"), ATPMessages.getString("MW.MONITOR.PREPARING"), 0, 100);
      this.monitor.setMillisToDecideToPopup(0);
      this.monitor.setMillisToPopup(0);
      this.watcher = new Timer(100, (ActionListener)new Object(this));
      Object object = new Object(this);
      object.start();
      this.watcher.start();
    } 
  }
  
  void action_btnFilterWaysideFailureReport() {
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LA.FILTER_WAYSIDE_FAILURE.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      this.monitor = new ProgressMonitor(this, ATPMessages.getString("MW.LA.FILTER_WAYSIDE_FAILURE.REPORT.MAKE"), ATPMessages.getString("MW.MONITOR.PREPARING"), 0, 100);
      this.monitor.setMillisToDecideToPopup(0);
      this.monitor.setMillisToPopup(0);
      this.watcher = new Timer(100, (ActionListener)new Object(this));
      Object object = new Object(this);
      object.start();
      this.watcher.start();
    } 
  }
  
  void action_btnRefresh() {
    try {
      Refresh();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void action_btnStopStationExport() {
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.ANALYZE.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      this.monitor = new ProgressMonitor(this, ATPMessages.getString("車次停靠站匯出"), ATPMessages.getString("MW.MONITOR.PREPARING"), 0, 100);
      this.monitor.setMillisToDecideToPopup(0);
      this.monitor.setMillisToPopup(0);
      this.watcher = new Timer(100, (ActionListener)new Object(this));
      Object object = new Object(this);
      object.start();
      this.watcher.start();
    } 
  }
  
  void action_btnWaysideFailureReport() {
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LA.WAYSIDE_FAILURE.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      this.monitor = new ProgressMonitor(this, ATPMessages.getString("MW.LA.WAYSIDE_FAILURE.REPORT.MAKE"), ATPMessages.getString("MW.MONITOR.PREPARING"), 0, 100);
      this.monitor.setMillisToDecideToPopup(0);
      this.monitor.setMillisToPopup(0);
      this.watcher = new Timer(100, (ActionListener)new Object(this));
      Object object = new Object(this);
      object.start();
      this.watcher.start();
    } 
  }
  
  void init() throws Exception {
    this.lblTitle.setBorder(BorderFactory.createEtchedBorder());
    this.lblTitle.setText(ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_ANALYZE"));
    setLayout(new BorderLayout());
    this.btnDriverBehaviorReport.setText(ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.ANALYZE"));
    this.btnDriverBehaviorReport.setActionCommand("driverBehaviorReport");
    this.btnDriverBehaviorReport.addActionListener((ActionListener)new pnlDataAnalyze_btn_actionListener(this));
    this.btnStopStationExport.setText("匯出停靠站資料");
    this.btnStopStationExport.setActionCommand("stopStationExport");
    this.btnStopStationExport.addActionListener((ActionListener)new pnlDataAnalyze_btn_actionListener(this));
    this.btnFilterDriverBehaviorReport.setText(ATPMessages.getString("MW.LA.FILTER_DRIVER_BEHAVIOR.REPORT"));
    this.btnFilterDriverBehaviorReport.setActionCommand("filterDriverBehaviorReport");
    this.btnFilterDriverBehaviorReport.addActionListener((ActionListener)new pnlDataAnalyze_btn_actionListener(this));
    this.btnAccidentAnalyze.setText(ATPMessages.getString("MW.LA.ACCIDENT.ANALYZE"));
    this.btnAccidentAnalyze.setActionCommand("AccidentAnalyze");
    this.btnAccidentAnalyze.addActionListener((ActionListener)new pnlDataAnalyze_btn_actionListener(this));
    this.btnWaysideFailureReport.setText(ATPMessages.getString("MW.LA.WAYSIDE_FAILURE.REPORT"));
    this.btnWaysideFailureReport.setActionCommand("waysideFailureReport");
    this.btnWaysideFailureReport.addActionListener((ActionListener)new pnlDataAnalyze_btn_actionListener(this));
    this.btnFilterWaysideFailureReport.setText(ATPMessages.getString("MW.LA.FILTER_WAYSIDE_FAILURE.REPORT"));
    this.btnFilterWaysideFailureReport.setActionCommand("filterWaysideFailureReport");
    this.btnFilterWaysideFailureReport.addActionListener((ActionListener)new pnlDataAnalyze_btn_actionListener(this));
    this.btnCabinFailureReport.setText(ATPMessages.getString("MW.LA.CABIN_FAILURE.REPORT"));
    this.btnCabinFailureReport.setActionCommand("cabinFailureReport");
    this.btnCabinFailureReport.addActionListener((ActionListener)new pnlDataAnalyze_btn_actionListener(this));
    this.btnFilterCabinFailureReport.setText(ATPMessages.getString("MW.LA.FILTER_CABIN_FAILURE.REPORT"));
    this.btnFilterCabinFailureReport.setActionCommand("filterCabinFailureReport");
    this.btnFilterCabinFailureReport.addActionListener((ActionListener)new pnlDataAnalyze_btn_actionListener(this));
    this.btnDeleteRecord.setText(ATPMessages.getString("MW.LA.DELETE"));
    this.btnDeleteRecord.setActionCommand("deleteRecord");
    this.btnDeleteRecord.addActionListener((ActionListener)new pnlDataAnalyze_btn_actionListener(this));
    this.btnRefresh.setText(ATPMessages.getString("MW.LA.REFRESH"));
    this.btnRefresh.setActionCommand("refresh");
    this.btnRefresh.addActionListener((ActionListener)new pnlDataAnalyze_btn_actionListener(this));
    this.btnWaysideFailureReport.setVisible(true);
    this.btnCabinFailureReport.setVisible(true);
    this.btnFilterWaysideFailureReport.setVisible(false);
    this.btnFilterCabinFailureReport.setVisible(false);
    this.jPanel4.setLayout(new GridBagLayout());
    this.jPanel4.setPreferredSize(new Dimension(298, 573));
    this.pnlsearch.addPropertyChangeListener((PropertyChangeListener)new pnlDataAnalyze_pnlsearch_propertyChangeAdapter(this));
    this.pnlsearch.hideOption(7, false);
    this.pnlsearch.hideOption(6, false);
    add(this.lblTitle, "North");
    add(this.jScrollPane1, "Center");
    this.jScrollPane1.getViewport().add((Component)this.jTable1, (Object)null);
    this.vDataName = new Vector();
    this.vDataName.add(ATPMessages.getString("MW.LOG.RUNNINGDATE"));
    this.vDataName.add(ATPMessages.getString("MW.WS.ID"));
    this.vDataName.add(ATPMessages.getString("MW.TR.ID"));
    this.vDataName.add(ATPMessages.getString("MW.DRIVER.ID"));
    this.vDataName.add(ATPMessages.getString("MW.VEHICLE.ID"));
    this.vDataName.add(ATPMessages.getString("MW.LA.EB.TIMES"));
    this.vDataName.add(ATPMessages.getString("MW.LA.SB.TIMES"));
    this.vDataName.add(ATPMessages.getString("MW.LA.CABIN_FAILURE.TIMES"));
    this.vDataName.add(ATPMessages.getString("MW.LA.WAYSIDE_FAILURE.TIMES"));
    this.vDataName.add(ATPMessages.getString("MW.LA.ISOLATION.TIMES"));
    this.vDataName.add(ATPMessages.getString("MW.LA.STARTTIME"));
    this.vDataName.add(ATPMessages.getString("MW.LA.ENDTIME"));
    this.vDataName.add(ATPMessages.getString("MW.LA.DURATION"));
    add(this.jPanel4, "East");
    this.jPanel4.add((Component)this.pnlsearch, new GridBagConstraints(0, 0, 2, 1, 1.0D, 1.0D, 11, 0, new Insets(0, 0, 0, 0), 0, 0));
    this.jPanel4.add(this.btnFilterDriverBehaviorReport, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 5, 3, 5), 0, 0));
    this.jPanel4.add(this.btnDriverBehaviorReport, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 5, 3, 5), 0, 0));
    this.jPanel4.add(this.btnStopStationExport, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 5, 3, 5), 0, 0));
    this.jPanel4.add(this.btnAccidentAnalyze, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 5, 3, 5), 0, 0));
    this.jPanel4.add(this.btnFilterCabinFailureReport, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 10, 3, 5), 0, 0));
    this.jPanel4.add(this.btnCabinFailureReport, new GridBagConstraints(1, 3, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 5, 3, 5), 0, 0));
    this.jPanel4.add(this.btnFilterWaysideFailureReport, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 10, 3, 5), 0, 0));
    this.jPanel4.add(this.btnWaysideFailureReport, new GridBagConstraints(1, 4, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 5, 3, 5), 0, 0));
    this.jPanel4.add(this.btnRefresh, new GridBagConstraints(1, 5, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(9, 5, 3, 5), 0, 0));
    this.jPanel4.add(this.btnDeleteRecord, new GridBagConstraints(1, 6, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(18, 5, 3, 5), 0, 0));
  }
  
  private void _$6990() {
    Vector vector = new Vector();
    this.deleteData.clear();
    Calendar calendar = Calendar.getInstance();
    calendar.add(2, -2);
    for (byte b = 0; b < this.vData.size(); b++) {
      Vector vector1 = this.vData.get(b);
      Date date = vector1.get(0);
      if (date.before(calendar.getTime())) {
        this.deleteData.add(vector1);
      } else {
        vector.add(vector1);
      } 
    } 
    if (this.deleteData.size() > 0) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
      int i = JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.LA.Q.DELETE_OLD_LOG") + simpleDateFormat.format(calendar.getTime()) + "\n" + ATPMessages.getString("MW.LA.DELETE_OLD_LOG.BENEFIT") + "\n" + "\t[" + ATPMessages.getString("MW.LA.DELETE_OLD_LOG.REGET") + "]", ATPMessages.getString("MW.GNL.ASK"), 0, 3);
      if (i == 0) {
        action_btnDeleteRecord();
        this.vData = vector;
      } 
    } 
  }
  
  void pnlsearch_propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    if (paramPropertyChangeEvent.getPropertyName().equals("search")) {
      Refresh();
    } else if (paramPropertyChangeEvent.getPropertyName().equals("save")) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");
      this.pnlsearch.doExport((TableModel)this.dtm, this.lblTitle.getText() + "_" + simpleDateFormat.format(new Date(System.currentTimeMillis())), true);
    } 
  }
}
