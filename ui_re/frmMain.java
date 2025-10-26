package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CheckUser;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.Tools.FtpBuffer;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.btnFTPWatcher;
import com.MiTAC.TRA.ATP.Tools.frmFTPWatcher;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.decoder.DecodeBuffer;
import com.MiTAC.TRA.ATP.decoder.btnDecodeWatcher;
import com.MiTAC.TRA.ATP.decoder.frmDecodeWatcher;
import com.MiTAC.TRA.ATP.ui.frmLogin;
import com.MiTAC.TRA.ATP.ui.pnlDataAnalyze;
import com.MiTAC.TRA.ATP.ui.pnlDataBackup;
import com.MiTAC.TRA.ATP.ui.pnlDataDecode;
import com.MiTAC.TRA.ATP.ui.pnlDataSearch;
import com.MiTAC.TRA.ATP.ui.pnlDataTransfer;
import com.MiTAC.TRA.ATP.ui.pnlDriverMgn;
import com.MiTAC.TRA.ATP.ui.pnlLineMgn;
import com.MiTAC.TRA.ATP.ui.pnlMWOperateLog;
import com.MiTAC.TRA.ATP.ui.pnlMissionDownload;
import com.MiTAC.TRA.ATP.ui.pnlMissionDownloadLog;
import com.MiTAC.TRA.ATP.ui.pnlMissionEdit;
import com.MiTAC.TRA.ATP.ui.pnlStationInfoMgn;
import com.MiTAC.TRA.ATP.ui.pnlStatus;
import com.MiTAC.TRA.ATP.ui.pnlTrainDataMgn;
import com.MiTAC.TRA.ATP.ui.pnlTrainRunningMgn;
import com.MiTAC.TRA.ATP.ui.pnlUserMgn;
import com.MiTAC.TRA.ATP.ui.pnlVehicleIDMgn;
import com.MiTAC.TRA.ATP.ui.pnlWorkShiftMgn;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class frmMain extends JFrame {
  private static int _$4856;
  
  private static int _$4853;
  
  BorderLayout borderLayout1 = new BorderLayout();
  
  btnDecodeWatcher btnDecodeWatcher;
  
  btnFTPWatcher btnFTPWatch;
  
  JButton btnMissionDownload = new JButton();
  
  JButton btnMissionEdit = new JButton();
  
  JButton btnTrainRunningLogAnalyze = new JButton();
  
  JButton btnTrainRunningLogDecode = new JButton();
  
  JButton btnTrainRunningLogTransport = new JButton();
  
  private static int _$4854;
  
  CheckUser checkUser = new CheckUser();
  
  ConnectDB connDB = new ConnectDB();
  
  JPanel contentPane;
  
  CreatMWSystemLog creatLog;
  
  JPanel currentPanel;
  
  private static int _$4837;
  
  private static int _$4833 = 0;
  
  private static int _$4266;
  
  frmDecodeWatcher frmDecodeWatcher;
  
  frmFTPWatcher frmFTPWatcher;
  
  JMenuBar jMenuBar1 = new JMenuBar();
  
  JToolBar jToolBar1 = new JToolBar();
  
  private static int _$4845;
  
  private static int _$4851;
  
  private static int _$4848;
  
  private static int _$4846;
  
  private static int _$4850;
  
  private static int _$4835;
  
  private static int _$4849;
  
  private static int _$4847;
  
  private static int _$4836;
  
  private static int _$4841;
  
  JMenu muInfo = new JMenu();
  
  JMenuItem muItemAbout = new JMenuItem();
  
  JMenuItem muItemBackup = new JMenuItem();
  
  JMenuItem muItemBehaviorReport = new JMenuItem();
  
  JMenuItem muItemCarErrorReport = new JMenuItem();
  
  JMenuItem muItemDirverMgn = new JMenuItem();
  
  JMenuItem muItemEditUser = new JMenuItem();
  
  JMenuItem muItemExit = new JMenuItem();
  
  JMenuItem muItemLineInfoMgn = new JMenuItem();
  
  JMenuItem muItemLogAnalyze = new JMenuItem();
  
  JMenuItem muItemLogBackup = new JMenuItem();
  
  JMenuItem muItemLogDecode = new JMenuItem();
  
  JMenuItem muItemLogSearch = new JMenuItem();
  
  JMenuItem muItemLogUpload = new JMenuItem();
  
  JMenuItem muItemLogout = new JMenuItem();
  
  JMenuItem muItemMWOperateSearch = new JMenuItem();
  
  JMenuItem muItemMissionDownloadSearch = new JMenuItem();
  
  JMenuItem muItemMissionEdit = new JMenuItem();
  
  JMenuItem muItemParaSetting = new JMenuItem();
  
  JMenuItem muItemStationMgn = new JMenuItem();
  
  JMenuItem muItemTRMgn = new JMenuItem();
  
  JMenuItem muItemTrainDataMgn = new JMenuItem();
  
  JMenuItem muItemTrainDataToMS = new JMenuItem();
  
  JMenuItem muItemVIDMgn = new JMenuItem();
  
  JMenuItem muItemWSMgn = new JMenuItem();
  
  JMenuItem muItemWaySideErrorReport = new JMenuItem();
  
  JMenu muLogData = new JMenu();
  
  JMenu muMgn = new JMenu();
  
  JMenu muMission = new JMenu();
  
  JMenu muReport = new JMenu();
  
  JMenu muSearch = new JMenu();
  
  JMenu muTrain = new JMenu();
  
  private static int _$4834 = 1;
  
  pnlDataAnalyze pnlDA;
  
  pnlDataBackup pnlDBp;
  
  pnlDataDecode pnlDD;
  
  pnlDriverMgn pnlDRMgn;
  
  pnlDataSearch pnlDS;
  
  pnlDataTransfer pnlDT;
  
  pnlLineMgn pnlLineMgn;
  
  pnlMissionDownload pnlMD;
  
  pnlMissionDownloadLog pnlMDS;
  
  pnlMissionEdit pnlMSEdit;
  
  pnlMWOperateLog pnlMWOS;
  
  pnlStationInfoMgn pnlSIMgn;
  
  pnlStatus pnlStatus;
  
  pnlTrainDataMgn pnlTDMgn;
  
  pnlTrainRunningMgn pnlTRMgn;
  
  pnlUserMgn pnlUserMgn;
  
  pnlVehicleIDMgn pnlVIDMgn;
  
  pnlWorkShiftMgn pnlWSMgn;
  
  private static int _$4827;
  
  private static int _$4852;
  
  private Timer _$2787;
  
  com.MiTAC.TRA.ATP.ui.frmMain thisFrame;
  
  private static int _$4843;
  
  private static int _$4844;
  
  private static int _$4842;
  
  private static int _$4840;
  
  Vector userData = new Vector();
  
  private static String _$4828;
  
  private static int _$4838;
  
  private static int _$4855;
  
  private static int _$4839;
  
  static {
    _$4835 = 2;
    _$4266 = 3;
    _$4836 = 4;
    _$4837 = 5;
    _$4838 = 6;
    _$4839 = 7;
    _$4840 = 8;
    _$4841 = 9;
    _$4842 = 10;
    _$4843 = 11;
    _$4844 = 12;
    _$4845 = 13;
    _$4846 = 14;
    _$4847 = 15;
    _$4848 = 16;
    _$4849 = 17;
    _$4850 = 18;
    _$4851 = 19;
    _$4852 = 20;
    _$4853 = 21;
    _$4854 = 22;
    _$4855 = 23;
    _$4856 = 125;
  }
  
  public frmMain(String paramString) {
    enableEvents(64L);
    try {
      _$4828 = paramString;
      _$4827 = _$4874(paramString);
      _$4300();
      InitParameters.start();
      if (paramString.equals("MiTACManager")) {
        this.pnlStatus.lblUserName.setText(ATPMessages.getString("MW.LOGIN.USERNAME") + ":" + "MiTACManger");
        this.pnlStatus.lblTitle.setText(ATPMessages.getString("MW.LOGIN.USERPRIORITY") + ":" + "系統管理員");
      } else {
        this.pnlStatus.lblUserName.setText(ATPMessages.getString("MW.LOGIN.USERNAME") + ":" + (String)((Vector)this.userData.get(0)).get(0));
        this.pnlStatus.lblTitle.setText(ATPMessages.getString("MW.LOGIN.USERPRIORITY") + ":" + (String)((Vector)this.userData.get(0)).get(2));
      } 
      if (!FtpBuffer.isRunning()) {
        FtpBuffer ftpBuffer = new FtpBuffer();
        ftpBuffer.start();
      } 
      if (!DecodeBuffer.isRunning()) {
        DecodeBuffer decodeBuffer = new DecodeBuffer();
        decodeBuffer.start();
      } 
      Object object = new Object(this);
      this._$2787 = new Timer();
      this._$2787.scheduleAtFixedRate((TimerTask)object, 0L, 1800000L);
    } catch (Exception exception) {
      exception.printStackTrace();
      if (!this.pnlStatus.lblFTP.getText().equals(ATPMessages.getString("MW.GNL.SUCCESS")))
        this.pnlStatus.lblFTP.setText(ATPMessages.getString("MW.GNL.FAILURE")); 
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 2);
    } 
  }
  
  public frmMain() {
    this.thisFrame = this;
    enableEvents(64L);
    try {
      _$4300();
    } catch (Exception exception) {
      exception.printStackTrace();
      if (!this.pnlStatus.lblFTP.getText().equals(ATPMessages.getString("MW.GNL.SUCCESS")))
        this.pnlStatus.lblFTP.setText(ATPMessages.getString("MW.GNL.FAILURE")); 
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 2);
    } 
  }
  
  public static int getPriority() {
    return _$4827;
  }
  
  public static String getUserID() {
    return _$4828;
  }
  
  private void _$4300() throws Exception {
    this.contentPane = (JPanel)getContentPane();
    this.contentPane.setDebugGraphicsOptions(0);
    setJMenuBar(this.jMenuBar1);
    setResizable(true);
    setState(0);
    setTitle(ATPMessages.getString("MW.TRA") + "-" + ATPMessages.getString("MW.ATP") + "-" + ATPMessages.getString("MW.MW") + " Ver 3.5.4 Build 100802");
    this.muMgn.setText(ATPMessages.getString("MW.MAIN.SYSTEM"));
    this.muItemEditUser.setText(ATPMessages.getString("MW.MAIN.SYSTEM.USER_MGN"));
    this.muItemEditUser.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muItemParaSetting.setText(ATPMessages.getString("MW.MAIN.SYSTEM.USER_PARA_SETTING"));
    this.muItemParaSetting.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muItemBackup.setText("資料庫備份");
    this.muItemBackup.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muItemLogout.setText(ATPMessages.getString("MW.MAIN.SYSTEM.LOGOUT"));
    this.muItemLogout.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muItemExit.setText(ATPMessages.getString("MW.MAIN.SYSTEM.EXIT"));
    this.muItemExit.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muMission.setText(ATPMessages.getString("MW.MAIN.MISSION_MGN"));
    this.muItemDirverMgn.setText(ATPMessages.getString("MW.MAIN.MISSION_MGN.DIRVER_MGN"));
    this.muItemDirverMgn.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muItemVIDMgn.setText(ATPMessages.getString("MW.MAIN.MISSION_MGN.VEHICLE_ID_MGN"));
    this.muItemVIDMgn.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muItemWSMgn.setText(ATPMessages.getString("MW.MAIN.MISSION_MGN.WORKSHIFT_MGN"));
    this.muItemWSMgn.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muItemTRMgn.setText(ATPMessages.getString("MW.MAIN.MISSION_MGN.TRAINRUNNING_MGN"));
    this.muItemTRMgn.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muItemMissionEdit.setText(ATPMessages.getString("MW.MAIN.MISSION_MGN.MISSION_EDIT"));
    this.muItemMissionEdit.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muItemTrainDataToMS.setText(ATPMessages.getString("MW.MAIN.MISSION_MGN.MISSION_DOWNLOAD"));
    this.muItemTrainDataToMS.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muTrain.setText(ATPMessages.getString("MW.MAIN.TRAIN_AND_STATION_MGN"));
    this.muItemTrainDataMgn.setText(ATPMessages.getString("MW.MAIN.TRAIN_AND_STATION_MGN.TRAINDATA_MGN"));
    this.muItemTrainDataMgn.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muItemLineInfoMgn.setText(ATPMessages.getString("MW.MAIN.TRAIN_AND_STATION_MGN.LINE_STATION_MGN"));
    this.muItemLineInfoMgn.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muLogData.setText(ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN"));
    this.muItemLogUpload.setText(ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_TRANSPORT"));
    this.muItemLogUpload.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muItemWaySideErrorReport.setActionCommand(ATPMessages.getString("MW.WaySideErrorReport"));
    this.jToolBar1.setFloatable(true);
    this.muLogData.add(this.muItemLogUpload);
    this.muItemLogBackup.setText(ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_BACKUP"));
    this.muItemLogBackup.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muLogData.add(this.muItemLogBackup);
    this.muItemLogSearch.setText(ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_INDEX"));
    this.muItemLogSearch.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muLogData.add(this.muItemLogSearch);
    this.muLogData.addSeparator();
    this.muItemLogDecode.setText(ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_DECODE"));
    this.muItemLogDecode.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muLogData.add(this.muItemLogDecode);
    this.muItemLogAnalyze.setText(ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_ANALYZE"));
    this.muItemLogAnalyze.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muLogData.add(this.muItemLogAnalyze);
    this.muSearch.setText(ATPMessages.getString("MW.MAIN.HISTORY"));
    this.muItemMWOperateSearch.setText(ATPMessages.getString("MW.MAIN.HISTORY.MW_OPERATION"));
    this.muItemMWOperateSearch.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muItemMissionDownloadSearch.setText(ATPMessages.getString("MW.MAIN.HISTORY.MISSION_DOWNLOAD"));
    this.muItemMissionDownloadSearch.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.muInfo.setText(ATPMessages.getString("MW.MAIN.INFO"));
    this.muItemAbout.setText(ATPMessages.getString("MW.MAIN.INFO.ABOUT_MW"));
    this.muItemAbout.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.jMenuBar1.add(this.muMgn);
    this.jMenuBar1.add(this.muMission);
    this.jMenuBar1.add(this.muTrain);
    this.jMenuBar1.add(this.muLogData);
    this.jMenuBar1.add(this.muSearch);
    this.jMenuBar1.add(this.muInfo);
    this.muMission.add(this.muItemDirverMgn);
    this.muMission.add(this.muItemVIDMgn);
    this.muMission.add(this.muItemTRMgn);
    this.muMission.add(this.muItemWSMgn);
    this.muMission.add(this.muItemMissionEdit);
    this.muMission.add(this.muItemTrainDataToMS);
    this.muMgn.add(this.muItemEditUser);
    this.muMgn.add(this.muItemParaSetting);
    this.muMgn.add(this.muItemBackup);
    this.muMgn.addSeparator();
    this.muMgn.add(this.muItemLogout);
    this.muMgn.add(this.muItemExit);
    this.muTrain.add(this.muItemTrainDataMgn);
    this.muTrain.add(this.muItemLineInfoMgn);
    this.muInfo.add(this.muItemAbout);
    this.muSearch.add(this.muItemMWOperateSearch);
    this.muSearch.add(this.muItemMissionDownloadSearch);
    this.muItemEditUser.setEnabled(this.checkUser.isEnable(_$4827, _$4833));
    this.muItemParaSetting.setEnabled(this.checkUser.isEnable(_$4827, _$4834));
    this.muItemLogout.setEnabled(this.checkUser.isEnable(_$4827, _$4835));
    this.muItemExit.setEnabled(this.checkUser.isEnable(_$4827, _$4266));
    this.muMission.setEnabled(this.checkUser.isEnable(_$4827, _$4836));
    this.muItemDirverMgn.setEnabled(this.checkUser.isEnable(_$4827, _$4837));
    this.muItemVIDMgn.setEnabled(this.checkUser.isEnable(_$4827, _$4838));
    this.muItemWSMgn.setEnabled(this.checkUser.isEnable(_$4827, _$4839));
    this.muItemTRMgn.setEnabled(this.checkUser.isEnable(_$4827, _$4840));
    this.muItemMissionEdit.setEnabled(this.checkUser.isEnable(_$4827, _$4841));
    this.muItemTrainDataToMS.setEnabled(this.checkUser.isEnable(_$4827, _$4842));
    this.muTrain.setEnabled(this.checkUser.isEnable(_$4827, _$4843));
    this.muItemTrainDataMgn.setEnabled(this.checkUser.isEnable(_$4827, _$4844));
    this.muItemLineInfoMgn.setEnabled(this.checkUser.isEnable(_$4827, _$4845));
    this.muLogData.setEnabled(this.checkUser.isEnable(_$4827, _$4846));
    this.muItemLogUpload.setEnabled(this.checkUser.isEnable(_$4827, _$4847));
    this.muItemLogBackup.setEnabled(this.checkUser.isEnable(_$4827, _$4848));
    this.muItemLogSearch.setEnabled(this.checkUser.isEnable(_$4827, _$4849));
    this.muItemLogDecode.setEnabled(this.checkUser.isEnable(_$4827, _$4850));
    this.muItemLogAnalyze.setEnabled(this.checkUser.isEnable(_$4827, _$4851));
    this.muReport.setEnabled(this.checkUser.isEnable(_$4827, _$4852));
    this.muItemBehaviorReport.setEnabled(this.checkUser.isEnable(_$4827, _$4853));
    this.muItemCarErrorReport.setEnabled(this.checkUser.isEnable(_$4827, _$4854));
    this.muItemWaySideErrorReport.setEnabled(this.checkUser.isEnable(_$4827, _$4855));
    this.pnlStatus = new pnlStatus();
    this.contentPane.add((Component)this.pnlStatus, "South");
    this.btnMissionEdit.setText(ATPMessages.getString("MW.MAIN.MISSION_MGN.MISSION_EDIT"));
    this.btnMissionEdit.setEnabled(this.checkUser.isEnable(_$4827, _$4841));
    this.btnMissionEdit.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.btnMissionDownload.setText(ATPMessages.getString("MW.MAIN.MISSION_MGN.MISSION_DOWNLOAD"));
    this.btnMissionDownload.setEnabled(this.checkUser.isEnable(_$4827, _$4842));
    this.btnMissionDownload.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.btnTrainRunningLogDecode.setText(ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_DECODE"));
    this.btnTrainRunningLogDecode.setEnabled(this.checkUser.isEnable(_$4827, _$4850));
    this.btnTrainRunningLogDecode.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.btnTrainRunningLogTransport.setText(ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_TRANSPORT"));
    this.btnTrainRunningLogTransport.setEnabled(this.checkUser.isEnable(_$4827, _$4847));
    this.btnTrainRunningLogTransport.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.btnTrainRunningLogAnalyze.setText(ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_ANALYZE"));
    this.btnTrainRunningLogAnalyze.setEnabled(this.checkUser.isEnable(_$4827, _$4851));
    this.btnTrainRunningLogAnalyze.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.contentPane.add(this.jToolBar1, "North");
    this.jToolBar1.add(this.btnMissionEdit);
    this.jToolBar1.add(this.btnMissionDownload);
    this.jToolBar1.add(this.btnTrainRunningLogTransport);
    this.jToolBar1.add(this.btnTrainRunningLogDecode);
    this.jToolBar1.add(this.btnTrainRunningLogAnalyze);
    this.btnFTPWatch = new btnFTPWatcher();
    this.btnFTPWatch.setActionCommand("FTPWatcher");
    this.btnFTPWatch.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.btnDecodeWatcher = new btnDecodeWatcher();
    this.btnDecodeWatcher.setActionCommand("DecodeWatcher");
    this.btnDecodeWatcher.addActionListener((ActionListener)new MenuActionListener(this, this));
    this.jToolBar1.add((Component)this.btnFTPWatch);
    this.jToolBar1.add((Component)this.btnDecodeWatcher);
    setSize(Toolkit.getDefaultToolkit().getScreenSize());
    setExtendedState(6);
  }
  
  protected void processWindowEvent(WindowEvent paramWindowEvent) {
    super.processWindowEvent(paramWindowEvent);
    if (paramWindowEvent.getID() == 201) {
      frmLogin frmLogin = new frmLogin();
      frmLogin.show();
      this.creatLog = new CreatMWSystemLog(getUserID(), "管理電腦", "使用者", "登出", null);
      dispose();
      this._$2787.cancel();
    } 
  }
  
  public void setUserID(String paramString) {
    _$4828 = paramString;
  }
  
  private int _$4874(String paramString) throws Exception {
    if (paramString.equals("MiTACManager"))
      return 3; 
    String str = "SELECT MWUser.UserName,MWUser.Priority,MWUserClass.UserClass FROM MWUser JOIN MWUserClass ON MWUser.Priority = MWUserClass.Priority WHERE MWUser.User_ID = '" + paramString + "'";
    this.userData = this.connDB.getData(str);
    return Integer.parseInt(((Vector)this.userData.get(0)).get(1).toString());
  }
}
