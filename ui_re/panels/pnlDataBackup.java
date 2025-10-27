package ui.panels;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CheckUser;
import com.MiTAC.TRA.ATP.Tools.CopyTask;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.Tools.DiskTools.DiskName;
import com.MiTAC.TRA.ATP.Tools.DiskTools.DiskStatus;
import com.MiTAC.TRA.ATP.Tools.FormatTask;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.PasswordDialog;
import com.MiTAC.TRA.ATP.Tools.PasswordInputDialog;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import com.MiTAC.TRA.ATP.Tools.RefreshFrame;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.Tools.UnknowProgressMonitor;
import com.MiTAC.TRA.ATP.ui.frames.frmMain;
import com.MiTAC.TRA.ATP.ui.adapters.pnlDataBackup_btn_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlDataBackup_pnlsearch_propertyChangeAdapter;
import com.MiTAC.TRA.ATP.ui.panels.pnlSearch;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ProgressMonitor;
import javax.swing.Timer;
import javax.swing.table.TableModel;

public class pnlDataBackup extends JPanel implements RefreshFrame {
  private String _$16560 = InitParameters.MWLogPath;
  
  private String _$1724 = InitParameters.MOPath;
  
  Vector bList;
  
  private static int _$16584;
  
  JButton btnBackup = new JButton();
  
  JButton btnDelBackup = new JButton();
  
  JButton btnDelete = new JButton();
  
  JButton btnFormat = new JButton();
  
  JButton btnRefresh = new JButton();
  
  CheckUser checkUser = new CheckUser();
  
  CopyTask copyTask;
  
  CreatMWSystemLog creatLog;
  
  private static int _$16583;
  
  private static int _$939 = 121;
  
  DefaultSortTableModel dtm = new DefaultSortTableModel();
  
  DefaultSortTableModel dtm2 = new DefaultSortTableModel();
  
  private static int _$1094 = 122;
  
  FormatTask formatTask;
  
  JLabel jLabel10 = new JLabel();
  
  JLabel jLabel11 = new JLabel();
  
  JLabel jLabel12 = new JLabel();
  
  JLabel jLabel16 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel21 = new JLabel();
  
  JLabel jLabel22 = new JLabel();
  
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
  
  JPanel jPanel6 = new JPanel();
  
  JPanel jPanel7 = new JPanel();
  
  JPanel jPanel9 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JScrollPane jScrollPane2 = new JScrollPane();
  
  JSplitPane jSplitPane1 = new JSplitPane();
  
  JSortTable jTable1 = new JSortTable((SortTableModel)this.dtm);
  
  JSortTable jTable2 = new JSortTable((SortTableModel)this.dtm2);
  
  JLabel lblBK = new JLabel();
  
  JLabel lblBKFree = new JLabel();
  
  JLabel lblBKUsed = new JLabel();
  
  JLabel lblMO = new JLabel();
  
  JLabel lblMOFree = new JLabel();
  
  JLabel lblMOUsed = new JLabel();
  
  JLabel lblMOtotal = new JLabel();
  
  JLabel lblTitle = new JLabel();
  
  ProgressMonitor monitor;
  
  JPanel pnlBtnCollector = new JPanel();
  
  private com.MiTAC.TRA.ATP.ui.pnlDataBackup _$16561;
  
  pnlSearch pnlsearch = new pnlSearch(true);
  
  UnknowProgressMonitor progressMonitorFormat;
  
  PasswordDialog pwdDialog;
  
  PasswordInputDialog pwdInputDialog;
  
  Vector tmp;
  
  UnknowProgressMonitor unpm;
  
  Vector vBackup;
  
  Vector vDataName;
  
  Vector vMo;
  
  Timer watcher;
  
  static {
    _$16583 = 123;
    _$16584 = 124;
  }
  
  public pnlDataBackup() {
    try {
      init();
      Refresh();
      this._$16561 = this;
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void Refresh() {
    try {
      _$16585();
      this.vBackup = PathHandler.getPathList(this._$16560);
      this.vDataName = new Vector();
      this.vDataName.add(ATPMessages.getString("MW.LOG.RUNNINGDATE"));
      this.vDataName.add(ATPMessages.getString("MW.WS.ID"));
      this.vDataName.add(ATPMessages.getString("MW.TR.ID"));
      this.vDataName.add(ATPMessages.getString("MW.DRIVER.ID"));
      this.vDataName.add(ATPMessages.getString("MW.VEHICLE.ID"));
      this.vBackup = this.pnlsearch.SearchData(this.vBackup);
      this.dtm.setDataVector(this.vBackup, this.vDataName);
      if ((new File(this._$1724)).exists()) {
        this.vMo = PathHandler.getPathList(this._$1724);
        this.dtm2.setDataVector(this.vMo, this.vDataName);
      } else {
        this.dtm2.setDataVector(null, this.vDataName);
      } 
      if (this.vBackup.size() == 0)
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.SEARCH.NOT_FOUND"), ATPMessages.getString("MW.GNL.INFO"), 2); 
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnBackup_actionPerformed(ActionEvent paramActionEvent) {
    try {
      DiskStatus.getDiskStatus(new File(this._$1724));
      if (this.jTable1.getSelectedRowCount() == 0) {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LB.BACKUP.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      } else {
        int i = JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.LB.BACKUP.Q.CONFIRM"), ATPMessages.getString("MW.GNL.ASK"), 0, 3);
        if (i == 0) {
          this.monitor = new ProgressMonitor(this, ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_BACKUP"), ATPMessages.getString("MW.MONITOR.PREPARING"), 0, 100);
          this.monitor.setMillisToPopup(0);
          this.watcher = new Timer(100, (ActionListener)new Object(this));
          Object object = new Object(this);
          object.start();
          this.watcher.start();
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } finally {
      Refresh();
    } 
  }
  
  void btnDelBackup_actionPerformed(ActionEvent paramActionEvent) {
    this.unpm = new UnknowProgressMonitor(this, ATPMessages.getString("MW.LB.DELETE_BACKUP_LOG") + ", " + ATPMessages.getString("MW.MONITOR.PREPARING"), "", 0, 100);
    Object object = new Object(this);
    object.start();
    this.unpm.show();
  }
  
  void btnDelete_actionPerformed(ActionEvent paramActionEvent) {
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LB.BACKUP.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      int[] arrayOfInt = this.jTable1.getSelectedRows();
      this.bList = new Vector();
      for (byte b = 0; b < arrayOfInt.length; b++)
        this.bList.add(this.dtm.getDataVector().get(arrayOfInt[b])); 
      if (JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.LB.DELETE_BACKUP_LOG.Q.CONFIRM"), ATPMessages.getString("MW.GNL.ASK"), 0, 3) == 0) {
        this.pwdInputDialog = new PasswordInputDialog((Frame)getRootPane().getParent(), "密碼確認", true);
        this.pwdInputDialog.show();
        if (!this.pwdInputDialog.isCancel()) {
          this.unpm = new UnknowProgressMonitor(this, "行車紀錄刪除中", "starting", 1, 100);
          Object object = new Object(this);
          object.start();
        } 
      } 
    } 
  }
  
  void btnFormat_actionPerformed(ActionEvent paramActionEvent) {
    try {
      DiskStatus.getDiskStatus(new File(this._$1724));
      String str = DiskStatus.getDiskLabel();
      if (!DiskName.check2YearsOld(str))
        throw new Exception(ATPMessages.getString("MW.LB.MO.IN_2_YEAR")); 
      int i = JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.LB.MO.FORMAT.Q.CONFIRM"), ATPMessages.getString("MW.GNL.ASK"), 0);
      if (i == 0) {
        this.unpm = new UnknowProgressMonitor(this, ATPMessages.getString("MW.LB.MO.FORMATTING"), "", 0, 100);
        Object object = new Object(this);
        object.start();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnRefresh_actionPerformed(ActionEvent paramActionEvent) {
    Refresh();
  }
  
  private void _$16585() throws Exception {
    boolean bool1;
    boolean bool2;
    Vector vector = DiskStatus.getDiskStatus(new File(this._$16560));
    String str = vector.get(0);
    this.lblBK.setText(str);
    long l = ((Long)vector.get(1)).longValue();
    int k = 0;
    int i = (int)(l / 1048576L);
    char c = '썐';
    int j = c - i;
    this.lblBKFree.setText(i + " MB");
    this.lblBKUsed.setText(j + " MB");
    try {
      Vector vector1 = DiskStatus.getDiskStatus(new File(this._$1724));
      String str1 = vector1.get(0);
      this.lblMO.setText(str1);
      long l1 = ((Long)vector1.get(1)).longValue();
      FileReader fileReader = new FileReader(this._$1724 + "\\diskInfo.txt");
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      for (byte b = 0; b <= 10; b++) {
        String str2 = bufferedReader.readLine();
        if (str2.startsWith("-=- Total Space:")) {
          String[] arrayOfString = str2.split(":");
          str2 = arrayOfString[1].substring(0, arrayOfString[1].length() - 5);
          str2 = str2.replaceAll(" ", "");
          k = (new Integer(str2)).intValue();
          k /= 1048576;
          break;
        } 
      } 
      bufferedReader.close();
      fileReader.close();
      if (k == 0)
        k = 605; 
      bool1 = (int)(l1 / 1048576L);
      bool2 = k - bool1;
    } catch (FileNotFoundException fileNotFoundException) {
      this.lblMO.setText(ATPMessages.getString("MW.LB.MO.NOT_ATP_FORMAT"));
      k = 0;
      bool1 = false;
      bool2 = false;
    } catch (IOException iOException) {
      this.lblMO.setText(ATPMessages.getString("MW.LB.MO.INSERT_MO"));
      k = 0;
      bool1 = false;
      bool2 = false;
    } 
    this.lblMOtotal.setText(k + " MB");
    this.lblMOFree.setText(bool1 + " MB");
    this.lblMOUsed.setText(bool2 + " MB");
  }
  
  void init() throws Exception {
    this.lblTitle.setBorder(BorderFactory.createEtchedBorder());
    this.lblTitle.setRequestFocusEnabled(true);
    this.lblTitle.setText(ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_BACKUP"));
    setLayout(new BorderLayout());
    this.jPanel1.setLayout(new BorderLayout());
    this.jPanel2.setLayout(new BorderLayout());
    this.jPanel3.setLayout(new GridBagLayout());
    this.jLabel3.setText("");
    this.jPanel5.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel5.setToolTipText(ATPMessages.getString("MW.LB.MO.DISK_INFO"));
    this.jPanel5.setLayout(new GridBagLayout());
    this.jLabel2.setText(ATPMessages.getString("MW.LB.MO.DISK_INFO"));
    this.jLabel4.setText(ATPMessages.getString("MW.LB.DISK.LABEL"));
    this.jLabel5.setText(ATPMessages.getString("MW.LB.DISK.TOTAL"));
    this.jLabel6.setText(ATPMessages.getString("MW.LB.DISK.USED"));
    this.jLabel7.setText(ATPMessages.getString("MW.LB.DISK.FREE"));
    this.jLabel8.setText(ATPMessages.getString("MW.LB.MW.DISK_INFO"));
    this.jPanel6.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel6.setLayout(new GridBagLayout());
    this.jLabel9.setText(ATPMessages.getString("MW.LB.DISK.LABEL"));
    this.jLabel10.setText(ATPMessages.getString("MW.LB.DISK.TOTAL"));
    this.jLabel11.setText(ATPMessages.getString("MW.LB.DISK.USED"));
    this.jLabel12.setText(ATPMessages.getString("MW.LB.DISK.FREE"));
    this.lblBK.setText("MHY00T22");
    this.jLabel16.setText("50000MB");
    this.lblMO.setText("MHT00T22_20040512");
    this.lblMOtotal.setRequestFocusEnabled(true);
    this.jPanel7.setLayout(new BorderLayout());
    this.jPanel4.setMaximumSize(new Dimension(32767, 32767));
    this.jPanel4.setPreferredSize(new Dimension(24, 28));
    this.jPanel4.setLayout(new BorderLayout());
    this.jPanel7.setEnabled(true);
    this.jPanel7.setPreferredSize(new Dimension(24, 28));
    this.jLabel21.setText(ATPMessages.getString("MW.LB.MO.LOGLIST"));
    this.jSplitPane1.setOrientation(0);
    this.jSplitPane1.setBottomComponent(this.jPanel7);
    this.jSplitPane1.setLastDividerLocation(300);
    this.jSplitPane1.setRightComponent(this.jPanel7);
    this.jSplitPane1.setTopComponent(this.jPanel4);
    this.btnDelBackup.setText(ATPMessages.getString("MW.LB.DELETE_BACKUP_LOG"));
    this.btnDelBackup.setActionCommand("deleteBackup");
    this.btnDelBackup.addActionListener((ActionListener)new pnlDataBackup_btn_actionAdapter(this));
    this.btnBackup.setText(ATPMessages.getString("MW.LB.BACKUP"));
    this.btnBackup.setActionCommand("backup");
    this.btnBackup.addActionListener((ActionListener)new pnlDataBackup_btn_actionAdapter(this));
    this.btnFormat.setText(ATPMessages.getString("MW.LB.MO.FORMAT"));
    this.btnFormat.setActionCommand("format");
    this.btnFormat.addActionListener((ActionListener)new pnlDataBackup_btn_actionAdapter(this));
    this.btnDelete.setText(ATPMessages.getString("MW.Delete"));
    this.btnDelete.setActionCommand("delete");
    this.btnDelete.addActionListener((ActionListener)new pnlDataBackup_btn_actionAdapter(this));
    this.btnDelete.setVisible(false);
    this.btnRefresh.setText(ATPMessages.getString("MW.LB.REFRESH"));
    this.btnRefresh.setActionCommand("refresh");
    this.btnRefresh.addActionListener((ActionListener)new pnlDataBackup_btn_actionAdapter(this));
    this.pnlBtnCollector.setLayout(new GridBagLayout());
    this.jLabel22.setText(ATPMessages.getString("MW.LB.MW.LOGLIST"));
    this.jPanel9.setLayout(new BorderLayout());
    this.pnlsearch.addPropertyChangeListener((PropertyChangeListener)new pnlDataBackup_pnlsearch_propertyChangeAdapter(this));
    add(this.lblTitle, "North");
    add(this.jPanel1, "Center");
    this.jPanel1.add(this.jPanel2, "South");
    this.jPanel2.add(this.jPanel3, "Center");
    this.jPanel3.add(this.jLabel3, new GridBagConstraints(0, 0, 0, 0, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    this.jPanel3.add(this.jPanel5, new GridBagConstraints(0, 2, 2, 1, 1.0D, 1.0D, 10, 1, new Insets(0, 0, 0, 0), 13, -2));
    this.jPanel5.add(this.lblMOtotal, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 9, 0, 0), 0, 0));
    this.jPanel5.add(this.lblMO, new GridBagConstraints(1, 0, 3, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 7, 0, 53), 0, 0));
    this.jPanel5.add(this.jLabel4, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 5, 0, 22), 0, 0));
    this.jPanel5.add(this.jLabel5, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 5, 0, 0), 0, 0));
    this.jPanel5.add(this.jLabel6, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 6, 0, 0), 0, 0));
    this.jPanel5.add(this.jLabel7, new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 6, 3, 11), 0, 0));
    this.jPanel5.add(this.lblMOUsed, new GridBagConstraints(3, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 7, 0, 35), 0, 0));
    this.jPanel5.add(this.lblMOFree, new GridBagConstraints(3, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 7, 3, 35), 0, 0));
    this.jPanel3.add(this.jPanel6, new GridBagConstraints(2, 2, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(0, 0, 0, 0), 6, 1));
    this.jPanel6.add(this.jLabel11, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    this.jPanel6.add(this.jLabel12, new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 1, 11), 0, 0));
    this.jPanel6.add(this.lblBKUsed, new GridBagConstraints(3, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 7, 0, 3), 1, 0));
    this.jPanel6.add(this.lblBKFree, new GridBagConstraints(3, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 8, 1, 3), 0, 0));
    this.jPanel6.add(this.lblBK, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(1, 12, 0, 0), 0, 0));
    this.jPanel6.add(this.jLabel9, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(1, 2, 0, 21), 0, 0));
    this.jPanel6.add(this.jLabel10, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 2, 0, 0), 0, 0));
    this.jPanel6.add(this.jLabel16, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 10, 0, 28), 0, 0));
    this.jPanel3.add(this.jLabel8, new GridBagConstraints(2, 0, 1, 2, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 116), 0, 0));
    this.jPanel3.add(this.jLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 132), 0, 0));
    this.jPanel4.add(this.jPanel9, "Center");
    this.pnlBtnCollector.add(this.btnFormat, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
    this.pnlBtnCollector.add(this.btnBackup, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
    this.pnlBtnCollector.add(this.btnDelete, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 85, 0, 0), 0, 0));
    this.pnlBtnCollector.add(this.btnDelBackup, new GridBagConstraints(3, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 12, 0, 14), 0, 0));
    this.pnlBtnCollector.add(this.btnRefresh, new GridBagConstraints(4, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 12, 0, 0), 0, 0));
    this.jPanel9.add(this.jScrollPane1, "Center");
    this.jPanel9.add(this.jLabel22, "North");
    this.jScrollPane1.getViewport().add((Component)this.jTable1, (Object)null);
    this.jPanel9.add(this.pnlBtnCollector, "South");
    this.jSplitPane1.add(this.jPanel7, "bottom");
    this.jPanel7.add(this.jScrollPane2, "Center");
    this.jPanel7.add(this.jLabel21, "North");
    this.jScrollPane2.getViewport().add((Component)this.jTable2, (Object)null);
    this.jPanel1.add(this.jSplitPane1, "Center");
    this.jSplitPane1.add(this.jPanel4, "top");
    this.jSplitPane1.setDividerLocation(310);
    this.jPanel4.add((Component)this.pnlsearch, "East");
    this.btnDelBackup.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$16583));
    this.btnBackup.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$16584));
    this.btnFormat.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$1094));
    this.btnDelete.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$939));
  }
  
  void pnlsearch_propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    if (paramPropertyChangeEvent.getPropertyName().equals("search")) {
      Refresh();
    } else if (paramPropertyChangeEvent.getPropertyName().equals("save")) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");
      String str = this.lblTitle.getText();
      str = str + "_MO標籤：" + this.lblMO.getText() + "_" + simpleDateFormat.format(new Date(System.currentTimeMillis()));
      this.pnlsearch.doExport((TableModel)this.dtm2, str, false);
    } 
  }
}
