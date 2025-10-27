package ui.panels;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CheckUser;
import com.MiTAC.TRA.ATP.Tools.CopyTask;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.Tools.DiskTools.DeleteDir;
import com.MiTAC.TRA.ATP.Tools.DiskTools.DiskStatus;
import com.MiTAC.TRA.ATP.Tools.FileFilter.FilterVID;
import com.MiTAC.TRA.ATP.Tools.FtpBuffer;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.PasswordInputDialog;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import com.MiTAC.TRA.ATP.Tools.RefreshFrame;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.Tools.UnknowProgressMonitor;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.connect.ConnectFTP;
import com.MiTAC.TRA.ATP.ui.frames.frmMain;
import com.MiTAC.TRA.ATP.ui.adapters.pnlDataTransfer_btn_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlDataTransfer_pnlsearch_propertyChangeAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlDataTransfer_rdo_actionAdapter;
import com.MiTAC.TRA.ATP.ui.panels.pnlSearch;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.Timer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class pnlDataTransfer extends JPanel implements RefreshFrame {
  String FilePath = InitParameters.USBLogPath;
  
  boolean LogFromFTP = false;
  
  boolean LogToFTP = false;
  
  JButton btnCheckUSBFreeSpace = new JButton();
  
  JButton btnDelete = new JButton();
  
  JButton btnFileChoose = new JButton();
  
  JButton btnPathConfirm = new JButton();
  
  JButton btnRefresh = new JButton();
  
  JButton btnUpload = new JButton();
  
  ButtonGroup btngrpUSB = new ButtonGroup();
  
  ButtonGroup buttonGroup1 = new ButtonGroup();
  
  ButtonGroup buttonGroup2 = new ButtonGroup();
  
  CheckUser checkUser = new CheckUser();
  
  JCheckBox chxVIDMgn = new JCheckBox();
  
  CopyTask copyTask;
  
  CreatMWSystemLog creatLog;
  
  private static int _$939;
  
  DefaultSortTableModel dtm = new DefaultSortTableModel();
  
  boolean foundRULogData = false;
  
  boolean ftpFilter = false;
  
  Date ftpRefreshTime;
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel2 = new JPanel();
  
  JPanel jPanel5 = new JPanel();
  
  JPanel jPanel6 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JSortTable jTable1 = new JSortTable((SortTableModel)this.dtm);
  
  private JFileChooser _$16208 = new JFileChooser(InitParameters.MWLogPath);
  
  JLabel lblDataDestination = new JLabel();
  
  JLabel lblDataSource = new JLabel();
  
  JLabel lblTitle = new JLabel();
  
  Vector list;
  
  com.MiTAC.TRA.ATP.ui.pnlDataTransfer plg;
  
  pnlSearch pnlsearch = new pnlSearch(true);
  
  ProgressMonitor progressMonitor;
  
  PasswordInputDialog pwdInputDialog;
  
  JRadioButton rdoFromFTP = new JRadioButton();
  
  JRadioButton rdoMMILog = new JRadioButton();
  
  JRadioButton rdoPath = new JRadioButton();
  
  JRadioButton rdoRULog = new JRadioButton();
  
  JRadioButton rdoTOAuto = new JRadioButton();
  
  JRadioButton rdoTOFTP = new JRadioButton();
  
  JRadioButton rdoTOLocal = new JRadioButton();
  
  JRadioButton rdoUSB = new JRadioButton();
  
  boolean readRU = true;
  
  com.MiTAC.TRA.ATP.ui.pnlDataTransfer thisPanel;
  
  JTextField txtPath = new JTextField();
  
  UnknowProgressMonitor unpm;
  
  private static int _$17310 = 110;
  
  Vector vData = new Vector();
  
  Vector vDataName;
  
  Timer watcher;
  
  static {
    _$939 = 111;
  }
  
  public pnlDataTransfer() {
    try {
      this.thisPanel = this;
      init();
      _$16214();
      this.plg = this;
      dataInit();
      Refresh();
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(getParent(), exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  private void _$17336(Vector paramVector) throws Exception {
    ConnectFTP connectFTP = new ConnectFTP();
    connectFTP.ftpconnect();
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector = paramVector.get(b);
      String str1 = PathHandler.getEncodeSubDate(vector);
      String str2 = PathHandler.getEncodeSubPath(vector);
      connectFTP.rmdir(str1, str2);
    } 
    connectFTP.closeServer();
    ConnectDB connectDB = new ConnectDB();
    connectDB.InsertLogDeleteLibrary(paramVector, ATPMessages.getString("MW.FTP"));
    _$17339(paramVector);
  }
  
  public void Refresh() {
    try {
      this.vData = new Vector();
      FilterVID filterVID = new FilterVID();
      if (!this.rdoFromFTP.isSelected()) {
        if (this.rdoUSB.isSelected()) {
          File file = new File(InitParameters.USBPath);
          if (!file.exists()) {
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LT.USB.NOT_EXIST"), ATPMessages.getString("MW.GNL.ERROR"), 0);
          } else {
            File file1 = new File(InitParameters.USBRULogPath);
            File file2 = new File(InitParameters.USBLogPath);
            Vector vector1 = new Vector();
            Vector vector2 = new Vector();
            if (file1.exists())
              vector1 = PathHandler.getPathList(file1.getAbsolutePath()); 
            if (file2.exists())
              vector2 = PathHandler.getPathList(file2.getAbsolutePath()); 
            if (vector1.size() == 0 && vector2.size() == 0) {
              JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LT.USB.NO_LOG"), ATPMessages.getString("MW.GNL.INFO"), 2);
            } else if (vector1.size() != 0 && vector2.size() != 0) {
              if (this.rdoMMILog.isSelected()) {
                this.FilePath = InitParameters.USBLogPath;
              } else {
                this.FilePath = InitParameters.USBRULogPath;
              } 
              this.rdoRULog.setVisible(true);
              this.rdoMMILog.setVisible(true);
              this.rdoRULog.setEnabled(true);
              this.rdoMMILog.setEnabled(true);
              if (this.rdoRULog.isSelected()) {
                this.vData = vector1;
              } else {
                this.vData = vector2;
              } 
            } else if (vector1.size() != 0 && vector2.size() == 0) {
              this.FilePath = InitParameters.USBRULogPath;
              this.rdoRULog.setVisible(true);
              this.rdoMMILog.setVisible(true);
              this.rdoMMILog.setEnabled(false);
              this.rdoRULog.setSelected(true);
              this.vData = vector1;
            } else {
              this.FilePath = InitParameters.USBLogPath;
              this.rdoRULog.setVisible(false);
              this.rdoMMILog.setVisible(false);
              this.vData = vector2;
            } 
            if (this.vData.size() == 0) {
              JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LT.CUSTOM.NO_LOG") + this.FilePath, ATPMessages.getString("MW.GNL.INFO"), 2);
            } else {
              this.vData = filterVID.removeEmptyVID(this.vData);
            } 
          } 
        } else {
          File file = new File(this.FilePath);
          if (file.exists()) {
            DiskStatus.getDiskStatus(new File(this.FilePath));
            this.vData = PathHandler.getPathList(this.FilePath);
            if (this.vData.size() == 0)
              JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LT.CUSTOM.NO_LOG") + this.FilePath, ATPMessages.getString("MW.GNL.INFO"), 2); 
          } else {
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LT.CUSTOM.NOT_EXIST") + "[" + this.FilePath + "]", ATPMessages.getString("MW.GNL.ERROR"), 0);
          } 
        } 
      } else {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String str = (this.ftpRefreshTime == null) ? ATPMessages.getString("MW.LT.INFORMATION_CENTER.NEVER_REFRESH") : simpleDateFormat.format(this.ftpRefreshTime);
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LT.INFORMATION_CENTER.HOWTO_REFRESH") + "\n" + ATPMessages.getString("MW.LT.INFORMATION_CENTER.REFRESH_TIME") + ":" + str, ATPMessages.getString("MW.GNL.INFO"), 1);
        this.chxVIDMgn.setVisible(true);
        this.vData = FtpBuffer.getFTPFileList();
        this.vData = filterVID.removeEmptyVID(this.vData);
        if (this.ftpFilter) {
          ConnectDB connectDB = new ConnectDB();
          Vector vector = connectDB.getData("select VehicleID from VehicleID");
          this.vData = filterVID.removeNotBelong(this.vData, vector);
        } 
      } 
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
    
    } catch (Exception exception) {
      exception.printStackTrace();
      this.vData.clear();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } finally {
      if (this.vData != null)
        if (this.rdoFromFTP.isSelected()) {
          this.vData = this.pnlsearch.SearchDataWithCRCcode(this.vData);
        } else {
          this.vData = this.pnlsearch.SearchData(this.vData);
        }  
      this.dtm.setDataVector(this.vData, this.vDataName);
      TableColumn tableColumn1 = this.jTable1.getColumnModel().getColumn(3);
      tableColumn1.setCellRenderer((TableCellRenderer)new Object(this));
      TableColumn tableColumn2 = this.jTable1.getColumnModel().getColumn(4);
      tableColumn2.setCellRenderer((TableCellRenderer)new Object(this));
    } 
  }
  
  void btnCheckUSBFreeSpace_actionPerformed(ActionEvent paramActionEvent) {
    try {
      DiskStatus.getDiskStatus(new File(InitParameters.USBPath));
      float f = (float)(DiskStatus.getDiskFree() / 1024L / 1024L);
      if (DiskStatus.getDiskFree() / 1024L / 1024L < 20L) {
        JOptionPane.showMessageDialog(this.plg, ATPMessages.getString("MW.LT.USB.AVAILABLE_SPACE") + DiskStatus.getDiskFree() + ATPMessages.getString("MW.LT.USB.BYTES") + "(" + f + "MB)\n" + ATPMessages.getString("MW.LT.USB.WARN_SPACE_LOW"), ATPMessages.getString("MW.GNL.WARN"), 2);
      } else {
        JOptionPane.showMessageDialog(this.plg, ATPMessages.getString("MW.LT.USB.AVAILABLE_SPACE") + DiskStatus.getDiskFree() + ATPMessages.getString("MW.LT.USB.BYTES") + "(" + f + "MB)", ATPMessages.getString("MW.GNL.INFO"), 1);
      } 
    } catch (IOException iOException) {
      iOException.printStackTrace();
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LT.USB.NOT_EXIST"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnDelete_actionPerformed(ActionEvent paramActionEvent) {
    try {
      this.pwdInputDialog = new PasswordInputDialog((Frame)getRootPane().getParent(), "", true);
      this.pwdInputDialog.show();
      if (this.pwdInputDialog.getPassword().length() != 0) {
        this.checkUser = new CheckUser(frmMain.getUserID(), this.pwdInputDialog.getPassword());
        if (this.checkUser.isPasswordRight())
          if (this.jTable1.getSelectedRowCount() > 0) {
            Vector vector = new Vector();
            for (byte b = 0; b < this.jTable1.getSelectedRowCount(); b++)
              vector.add(this.dtm.getDataVector().get(this.jTable1.getSelectedRows()[b])); 
            int i = JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.LT.DELETE.Q.DELETE"), ATPMessages.getString("MW.GNL.ASK"), 0);
            if (i == 0)
              if (this.rdoFromFTP.isSelected()) {
                _$17336(vector);
              } else {
                _$17337(vector);
              }  
          } else {
            throw new Exception(ATPMessages.getString("MW.LT.DELETE.NO_SELECTED"));
          }  
      } 
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } finally {
      Refresh();
    } 
  }
  
  void btnFileChoose_actionPerformed(ActionEvent paramActionEvent) {
    this._$16208.setFileSelectionMode(1);
    this.rdoTOFTP.setEnabled(true);
    int i = this._$16208.showOpenDialog(this);
    if (i == 0)
      this.txtPath.setText(this._$16208.getSelectedFile().getAbsolutePath()); 
  }
  
  private void _$16214() {
    this.txtPath.setEnabled(false);
    this.btnFileChoose.setEnabled(false);
    this.btnPathConfirm.setEnabled(false);
  }
  
  void btnPathConfirm_actionPerformed(ActionEvent paramActionEvent) {
    if (this.txtPath.getText().startsWith("\\")) {
      JOptionPane.showMessageDialog(this, "[" + this.txtPath.getText() + "]" + ATPMessages.getString("MW.LT.CUSTOM.UNC_PATH_NOT_SUPPORT"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      this.FilePath = this.txtPath.getText();
      Refresh();
    } 
  }
  
  void btnRefresh_actionPerformed(ActionEvent paramActionEvent) {
    try {
      if (this.rdoFromFTP.isSelected()) {
        this.unpm = new UnknowProgressMonitor(this, ATPMessages.getString("MW.LT.INFORMATION_CENTER.REFRESHING"), ATPMessages.getString("MW.MONITOR.PROCESSING"), 0, 100);
        Object object = new Object(this);
        object.start();
        this.unpm.show();
      } 
      Refresh();
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(getParent(), exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnUpload_actionPerformed(ActionEvent paramActionEvent) {
    try {
      this.list = new Vector();
      for (byte b = 0; b < this.jTable1.getSelectedRowCount(); b++)
        this.list.add(this.dtm.getDataVector().get(this.jTable1.getSelectedRows()[b])); 
      if (this.list.size() > 0) {
        int i = JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.LT.TRANSFER.Q.CONFIRM"), ATPMessages.getString("MW.GNL.ASK"), 0);
        if (i == 0) {
          this.progressMonitor = new ProgressMonitor(this, ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_TRANSPORT"), ATPMessages.getString("MW.MONITOR.PREPARING"), 0, 100);
          this.progressMonitor.setMillisToDecideToPopup(0);
          this.progressMonitor.setMillisToPopup(0);
          this.watcher = new Timer(100, (ActionListener)new Object(this));
          Object object = new Object(this);
          object.start();
          this.watcher.start();
        } 
      } else {
        throw new Exception(ATPMessages.getString("MW.LT.NO_SELECTED"));
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void chxVIDMgn_actionPerformed(ActionEvent paramActionEvent) {
    this.ftpFilter = this.chxVIDMgn.isSelected();
    Refresh();
  }
  
  public void dataInit() {
    switch (frmMain.getPriority()) {
      case 0:
        this.btnDelete.setEnabled(false);
        break;
      case 1:
        this.btnDelete.setEnabled(false);
        break;
    } 
  }
  
  void init() throws Exception {
    this.lblTitle.setFont(new Font("", 1, 15));
    this.lblTitle.setBorder(BorderFactory.createEtchedBorder());
    this.lblTitle.setDebugGraphicsOptions(0);
    this.lblTitle.setText(ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_TRANSPORT"));
    setLayout(new BorderLayout());
    this.jPanel1.setLayout(new BorderLayout());
    this.chxVIDMgn.setText(ATPMessages.getString("MW.LT.INFORMATION_CENTER.FILTER_MGN"));
    this.chxVIDMgn.setSelected(this.ftpFilter);
    this.chxVIDMgn.setEnabled(false);
    this.chxVIDMgn.addActionListener((ActionListener)new pnlDataTransfer_btn_actionAdapter(this));
    this.btnUpload.setText(ATPMessages.getString("MW.LT.TRANSFER"));
    this.btnUpload.addActionListener((ActionListener)new pnlDataTransfer_btn_actionAdapter(this));
    this.btnDelete.setText(ATPMessages.getString("MW.LT.DELETE"));
    this.btnDelete.addActionListener((ActionListener)new pnlDataTransfer_btn_actionAdapter(this));
    this.btnCheckUSBFreeSpace.setText(ATPMessages.getString("MW.LT.USB.CHECK_SPACE"));
    this.btnCheckUSBFreeSpace.addActionListener((ActionListener)new pnlDataTransfer_btn_actionAdapter(this));
    this.btnPathConfirm.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.btnPathConfirm.addActionListener((ActionListener)new pnlDataTransfer_btn_actionAdapter(this));
    this.txtPath.setPreferredSize(new Dimension(150, 23));
    this.txtPath.setText(ATPMessages.getString("MW.LT.CUSTOM.REQUEST_PATH"));
    this.rdoUSB.setSelected(true);
    this.rdoUSB.setText(ATPMessages.getString("MW.LT.USB"));
    this.rdoUSB.addActionListener((ActionListener)new pnlDataTransfer_rdo_actionAdapter(this));
    this.rdoFromFTP.setText(ATPMessages.getString("MW.LT.INFORMATION_CENTER"));
    this.rdoFromFTP.setActionCommand("fromFTP");
    this.rdoFromFTP.addActionListener((ActionListener)new pnlDataTransfer_rdo_actionAdapter(this));
    this.lblDataDestination.setText(ATPMessages.getString("MW.LT.DATA_DESTINATION"));
    this.btnRefresh.setToolTipText("");
    this.btnRefresh.setText(ATPMessages.getString("MW.LT.REFRESH"));
    this.btnRefresh.addActionListener((ActionListener)new pnlDataTransfer_btn_actionAdapter(this));
    this.rdoTOFTP.setText(ATPMessages.getString("MW.LT.INFORMATION_CENTER"));
    this.rdoTOFTP.setActionCommand("toFTP");
    this.rdoTOFTP.addActionListener((ActionListener)new pnlDataTransfer_rdo_actionAdapter(this));
    this.lblDataSource.setText(ATPMessages.getString("MW.LT.DATA_SOURCE"));
    this.jPanel6.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel6.setLayout(new GridBagLayout());
    this.btnFileChoose.setOpaque(true);
    this.btnFileChoose.setPreferredSize(new Dimension(15, 25));
    this.btnFileChoose.setText("...");
    this.btnFileChoose.addActionListener((ActionListener)new pnlDataTransfer_btn_actionAdapter(this));
    this.rdoTOLocal.setSelected(false);
    this.rdoTOLocal.setText(ATPMessages.getString("MW.LT.LOCAL"));
    this.rdoTOLocal.addActionListener((ActionListener)new pnlDataTransfer_rdo_actionAdapter(this));
    this.rdoTOAuto.setSelected(true);
    this.rdoTOAuto.setText(ATPMessages.getString("MW.LT.AUTO"));
    this.rdoTOAuto.addActionListener((ActionListener)new pnlDataTransfer_rdo_actionAdapter(this));
    this.jPanel5.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel5.setLayout(new GridBagLayout());
    this.rdoPath.setText(ATPMessages.getString("MW.LT.CUSTOM"));
    this.rdoPath.addActionListener((ActionListener)new pnlDataTransfer_rdo_actionAdapter(this));
    this.jPanel2.setLayout(new GridBagLayout());
    this.rdoRULog.setText(ATPMessages.getString("MW.LT.USB.RU_LOG"));
    this.rdoRULog.setSelected(this.readRU);
    this.rdoRULog.addActionListener((ActionListener)new pnlDataTransfer_rdo_actionAdapter(this));
    this.rdoMMILog.setText(ATPMessages.getString("MW.LT.USB.MMI_LOG"));
    this.rdoMMILog.addActionListener((ActionListener)new pnlDataTransfer_rdo_actionAdapter(this));
    this.pnlsearch.addPropertyChangeListener((PropertyChangeListener)new pnlDataTransfer_pnlsearch_propertyChangeAdapter(this));
    this.pnlsearch.hideOption(7, false);
    this.pnlsearch.hideOption(6, false);
    this.jPanel2.setPreferredSize(new Dimension(298, 649));
    add(this.lblTitle, "North");
    add(this.jPanel1, "Center");
    this.jPanel1.add(this.jScrollPane1, "Center");
    this.jPanel1.add(this.jPanel2, "East");
    this.jScrollPane1.getViewport().add((Component)this.jTable1, (Object)null);
    this.jPanel6.add(this.lblDataDestination, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 0, 0), 0, 0));
    this.jPanel6.add(this.rdoTOAuto, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 30, 0, 0), 0, 0));
    this.jPanel6.add(this.rdoTOLocal, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 20, 0, 0), 0, 0));
    this.jPanel6.add(this.rdoTOFTP, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 20, 0, 0), 0, 0));
    this.buttonGroup1.add(this.rdoFromFTP);
    this.buttonGroup1.add(this.rdoUSB);
    this.buttonGroup1.add(this.rdoPath);
    this.buttonGroup2.add(this.rdoTOAuto);
    this.buttonGroup2.add(this.rdoTOLocal);
    this.buttonGroup2.add(this.rdoTOFTP);
    this.btngrpUSB.add(this.rdoRULog);
    this.btngrpUSB.add(this.rdoMMILog);
    this.jPanel5.add(this.lblDataSource, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 0, 0), 0, 0));
    this.jPanel5.add(this.rdoUSB, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 30, 0, 0), 0, 0));
    this.jPanel5.add(this.rdoFromFTP, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 20, 0, 0), 0, 0));
    this.jPanel5.add(this.rdoPath, new GridBagConstraints(2, 1, 3, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 13, 0, 0), 0, 0));
    this.jPanel5.add(this.txtPath, new GridBagConstraints(0, 2, 3, 1, 1.0D, 0.0D, 17, 2, new Insets(0, 30, 0, 0), 0, 0));
    this.jPanel5.add(this.btnFileChoose, new GridBagConstraints(3, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
    this.jPanel5.add(this.btnPathConfirm, new GridBagConstraints(4, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 12, 0, 0), 0, 0));
    this.jPanel2.add((Component)this.pnlsearch, new GridBagConstraints(0, 0, 2, 1, 1.0D, 1.0D, 10, 1, new Insets(0, 0, 3, 0), 1, 6));
    this.jPanel2.add(this.jPanel5, new GridBagConstraints(0, 1, 2, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 3, 0), -36, 7));
    this.jPanel2.add(this.jPanel6, new GridBagConstraints(0, 2, 2, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 3, 0), -12, 18));
    this.jPanel2.add(this.rdoRULog, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(3, 0, 0, 0), 0, 0));
    this.jPanel2.add(this.rdoMMILog, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(3, 0, 0, 0), 0, 0));
    this.jPanel2.add(this.btnCheckUSBFreeSpace, new GridBagConstraints(0, 5, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(3, 5, 3, 0), 0, 0));
    this.jPanel2.add(this.btnUpload, new GridBagConstraints(1, 3, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 30, 3, 3), 0, 0));
    this.jPanel2.add(this.btnDelete, new GridBagConstraints(1, 4, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 30, 3, 3), 0, 0));
    this.jPanel2.add(this.btnRefresh, new GridBagConstraints(1, 5, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(21, 30, 3, 3), 0, 0));
    this.jPanel2.add(this.chxVIDMgn, new GridBagConstraints(0, 6, 2, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 3, 0), 0, 0));
    this.vDataName = new Vector();
    this.vDataName.addElement(ATPMessages.getString("MW.LOG.RUNNINGDATE"));
    this.vDataName.addElement(ATPMessages.getString("MW.WS.ID"));
    this.vDataName.addElement(ATPMessages.getString("MW.TR.ID"));
    this.vDataName.addElement(ATPMessages.getString("MW.DRIVER.ID"));
    this.vDataName.addElement(ATPMessages.getString("MW.VEHICLE.ID"));
    this.btnUpload.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$17310));
    this.btnDelete.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$939));
  }
  
  private void _$17337(Vector paramVector) throws Exception {
    for (byte b = 0; b < paramVector.size(); b++) {
      String str = this.FilePath;
      if (!this.FilePath.endsWith("\\"))
        str = str + "\\"; 
      File file = new File(str + PathHandler.getEncodePath(paramVector.get(b)));
      DeleteDir.delDir(file);
    } 
    ConnectDB connectDB = new ConnectDB();
    connectDB.InsertLogDeleteLibrary(paramVector, this.FilePath);
    _$17339(paramVector);
  }
  
  private void _$17339(Vector paramVector) {
    StringBuffer stringBuffer = new StringBuffer();
    Vector vector = paramVector;
    for (byte b = 0; b < vector.size(); b++) {
      stringBuffer.append("車次執行日期:");
      stringBuffer.append((new SimpleDateFormat("yyyy/MM/dd")).format(((Vector)vector.get(b)).get(0)));
      stringBuffer.append(",工作班:");
      stringBuffer.append(((Vector)vector.get(b)).get(1));
      stringBuffer.append("車次:");
      stringBuffer.append(((Vector)vector.get(b)).get(2));
      stringBuffer.append(",司機員代號:");
      stringBuffer.append(((Vector)vector.get(b)).get(3));
      stringBuffer.append(",動力車號碼:");
      stringBuffer.append(((Vector)vector.get(b)).get(4));
      stringBuffer.append(",行車記錄來源:");
      if (this.rdoFromFTP.isSelected()) {
        stringBuffer.append(this.rdoFromFTP.getText());
      } else if (this.rdoUSB.isSelected()) {
        stringBuffer.append(this.rdoUSB.getText());
        stringBuffer.append(",路徑:");
        stringBuffer.append(this.FilePath);
      } else {
        stringBuffer.append(this.rdoPath.getText());
        stringBuffer.append(",路徑:");
        stringBuffer.append(this.txtPath.getText());
      } 
      stringBuffer.append("<>");
    } 
    this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), this.lblTitle.getText(), "行車記錄", "刪除", stringBuffer.toString());
  }
  
  void pnlsearch_propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    if (paramPropertyChangeEvent.getPropertyName() == "search") {
      Refresh();
    } else if (paramPropertyChangeEvent.getPropertyName().equals("save")) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");
      String str = this.lblTitle.getText();
      Enumeration enumeration = this.buttonGroup1.getElements();
      while (enumeration.hasMoreElements()) {
        JRadioButton jRadioButton = (JRadioButton)enumeration.nextElement();
        if (jRadioButton.isSelected()) {
          str = str + "_" + jRadioButton.getText();
          break;
        } 
      } 
      str = str + "_" + simpleDateFormat.format(new Date(System.currentTimeMillis()));
      this.pnlsearch.doExport((TableModel)this.dtm, str, true);
    } 
  }
  
  void rdoFromFTP_actionPerformed(ActionEvent paramActionEvent) {
    try {
      this.rdoTOFTP.setEnabled(false);
      this.rdoTOAuto.setEnabled(false);
      this.rdoTOLocal.setSelected(true);
      this.LogFromFTP = true;
      this.chxVIDMgn.setEnabled(true);
      _$16214();
      Refresh();
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void rdoPath_actionPerformed(ActionEvent paramActionEvent) {
    this.FilePath = "";
    this.btnFileChoose.setEnabled(true);
    this.btnPathConfirm.setEnabled(true);
    this.txtPath.setEnabled(true);
    this.rdoTOLocal.setEnabled(true);
    this.rdoTOAuto.setEnabled(true);
    this.rdoTOFTP.setEnabled(true);
    this.LogFromFTP = false;
    Refresh();
  }
  
  void rdoRUMMILog_actionPerformed(ActionEvent paramActionEvent) {
    Refresh();
  }
  
  void rdoTOAuto_actionPerformed(ActionEvent paramActionEvent) {
    this.rdoFromFTP.setEnabled(true);
    this.LogToFTP = false;
  }
  
  void rdoTOFTP_actionPerformed(ActionEvent paramActionEvent) {
    this.rdoFromFTP.setEnabled(false);
    this.LogToFTP = true;
  }
  
  void rdoTOLocal_actionPerformed(ActionEvent paramActionEvent) {
    this.rdoFromFTP.setEnabled(true);
    this.LogToFTP = false;
  }
  
  void rdoUSB_actionPerformed(ActionEvent paramActionEvent) {
    this.rdoTOFTP.setEnabled(true);
    this.rdoTOLocal.setEnabled(true);
    this.rdoTOAuto.setEnabled(true);
    this.LogFromFTP = false;
    _$16214();
    Refresh();
  }
}
