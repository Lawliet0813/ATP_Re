package ui.panels;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.Tools.DiskTools.DiskStatus;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.MWFileWatcher;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import com.MiTAC.TRA.ATP.Tools.RecordHandler;
import com.MiTAC.TRA.ATP.Tools.RefreshFrame;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.core.ATPMissionFailure;
import com.MiTAC.TRA.ATP.decoder.DataFeeder;
import com.MiTAC.TRA.ATP.ui.adapters.pnlDataDecode_btn_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlDataDecode_pnlsearch_propertyChangeAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlDataDecode_rdo_actionAdapter;
import com.MiTAC.TRA.ATP.ui.panels.pnlSearch;
import com.MiTAC.TRA.ATP.ui.panels.pnlViewCode;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class pnlDataDecode extends JPanel implements RefreshFrame {
  String FilePath = this.MWLogPath;
  
  DataFeeder Logdata;
  
  String MOLogPath = InitParameters.MOPath;
  
  String MWLogPath = InitParameters.MWLogPath;
  
  RecordHandler behaviorFailure;
  
  JButton btnDecode = new JButton();
  
  JButton btnFileChoose = new JButton();
  
  JButton btnPathRefresh = new JButton();
  
  JButton btnPathSubmit = new JButton();
  
  JButton btnViewCode = new JButton();
  
  ButtonGroup buttonGroup1 = new ButtonGroup();
  
  RecordHandler cabinFailure;
  
  CreatMWSystemLog creatLog;
  
  DefaultSortTableModel dtm = new DefaultSortTableModel();
  
  Vector errorList;
  
  Vector fileList;
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel3 = new JPanel();
  
  JPanel jPanel4 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JSortTable jTable1 = new JSortTable((SortTableModel)this.dtm);
  
  private JFileChooser _$16208 = new JFileChooser();
  
  JLabel lblDataSoruce = new JLabel();
  
  JLabel lblRefreshMsg = new JLabel("----------");
  
  JLabel lblRefreshTime = new JLabel("----------");
  
  JLabel lblTitle = new JLabel();
  
  private boolean _$9531 = false;
  
  ATPMissionFailure[] missionList;
  
  Vector outRangeList;
  
  ProgressMonitor pgsMtr;
  
  pnlSearch pnlsearch = new pnlSearch(true);
  
  JRadioButton rdoDecodeLocal = new JRadioButton();
  
  JRadioButton rdoDecodeMO = new JRadioButton();
  
  JRadioButton rdoPath = new JRadioButton();
  
  Timer t;
  
  Timer t2;
  
  JLabel tagRefreshMsg = new JLabel("更新訊息: ");
  
  JLabel tagRefreshTime = new JLabel("更新時間: ");
  
  com.MiTAC.TRA.ATP.ui.pnlDataDecode thisPanel;
  
  JTextField txtPath = new JTextField();
  
  Vector vData;
  
  Vector vDataName;
  
  RecordHandler waysideFailure;
  
  public pnlDataDecode() {
    try {
      init();
      _$16214();
      Refresh();
      this.thisPanel = this;
      Object object = new Object(this);
      this.t2 = new Timer();
      this.t2.scheduleAtFixedRate((TimerTask)object, 0L, 5000L);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void Refresh() {
    _$9541();
  }
  
  void btnDecode_actionPerformed(ActionEvent paramActionEvent) {
    try {
      this.pgsMtr = new ProgressMonitor(this, ATPMessages.getString("MW.MONITOR.ANALYZING"), ATPMessages.getString("MW.LD.DECODE"), 0, 100);
      this.pgsMtr.setMillisToDecideToPopup(0);
      this.pgsMtr.setMillisToPopup(0);
      this.pgsMtr.setMaximum(2);
      this.pgsMtr.setProgress(1);
      this.pgsMtr.setNote(ATPMessages.getString("MW.MONITOR.PREPARING"));
      Object object = new Object(this);
      object.start();
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnFileChoose_actionPerformed(ActionEvent paramActionEvent) {
    this._$16208.setFileSelectionMode(1);
    int i = this._$16208.showOpenDialog(this);
    if (i == 0)
      this.txtPath.setText(this._$16208.getSelectedFile().getAbsolutePath()); 
  }
  
  private void _$16214() {
    this.txtPath.setEnabled(false);
    this.btnPathSubmit.setEnabled(false);
    this.btnFileChoose.setEnabled(false);
  }
  
  void btnPathRefresh_actionPerformed(ActionEvent paramActionEvent) {
    this._$9531 = true;
    Refresh();
    this._$9531 = false;
  }
  
  void btnPathSubmit_actionPerformed(ActionEvent paramActionEvent) {
    if (this.txtPath.getText().startsWith("\\")) {
      JOptionPane.showMessageDialog(this, "[" + this.txtPath.getText() + "]" + ATPMessages.getString("MW.LT.CUSTOM.UNC_PATH_NOT_SUPPORT"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      this.FilePath = this.txtPath.getText();
      Refresh();
    } 
  }
  
  void btnViewCode_actionPerformed(ActionEvent paramActionEvent) {
    try {
      JFrame jFrame = new JFrame();
      jFrame.setSize(800, 600);
      pnlViewCode pnlViewCode = new pnlViewCode(_$16220());
      jFrame.getContentPane().add((Component)pnlViewCode, "Center");
      jFrame.setTitle(ATPMessages.getString("MW.LD.VIEW_LOG"));
      jFrame.show();
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      arrayIndexOutOfBoundsException.printStackTrace();
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LD.VIEW_LOG.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  private String _$16220() throws Exception {
    int i = this.jTable1.getSelectedRow();
    null = this.FilePath;
    Vector vector = this.dtm.getDataVector().get(i);
    return null + PathHandler.getEncodePath(vector);
  }
  
  void init() throws Exception {
    this.lblTitle.setFont(new Font("Dialog", 1, 15));
    this.lblTitle.setBorder(BorderFactory.createEtchedBorder());
    this.lblTitle.setText(ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_DECODE"));
    setLayout(new BorderLayout());
    this.jPanel1.setLayout(new BorderLayout());
    this.btnDecode.setText(ATPMessages.getString("MW.LD.DECODE"));
    this.btnDecode.addActionListener((ActionListener)new pnlDataDecode_btn_actionAdapter(this));
    this.rdoDecodeMO.setText(ATPMessages.getString("MW.LD.MO"));
    this.rdoDecodeMO.addActionListener((ActionListener)new pnlDataDecode_rdo_actionAdapter(this));
    this.rdoDecodeLocal.setSelected(true);
    this.rdoDecodeLocal.setText(ATPMessages.getString("MW.LD.LOCAL"));
    this.rdoDecodeLocal.addActionListener((ActionListener)new pnlDataDecode_rdo_actionAdapter(this));
    this.txtPath.setEnabled(false);
    this.txtPath.setPreferredSize(new Dimension(150, 23));
    this.txtPath.setRequestFocusEnabled(true);
    this.txtPath.setText(ATPMessages.getString("MW.LT.CUSTOM.REQUEST_PATH"));
    this.btnPathRefresh.setText(ATPMessages.getString("MW.LD.REFRESH"));
    this.btnPathRefresh.addActionListener((ActionListener)new pnlDataDecode_btn_actionAdapter(this));
    this.rdoPath.setText(ATPMessages.getString("MW.LD.CUSTOM"));
    this.rdoPath.addActionListener((ActionListener)new pnlDataDecode_rdo_actionAdapter(this));
    this.btnPathSubmit.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.btnPathSubmit.addActionListener((ActionListener)new pnlDataDecode_btn_actionAdapter(this));
    this.btnViewCode.setText(ATPMessages.getString("MW.LD.VIEW_LOG"));
    this.btnViewCode.addActionListener((ActionListener)new pnlDataDecode_btn_actionAdapter(this));
    this.btnFileChoose.setPreferredSize(new Dimension(15, 25));
    this.btnFileChoose.setText("...");
    this.btnFileChoose.addActionListener((ActionListener)new pnlDataDecode_btn_actionAdapter(this));
    this.jPanel3.setLayout(new GridBagLayout());
    this.jPanel4.setLayout(new GridBagLayout());
    this.lblDataSoruce.setRequestFocusEnabled(true);
    this.lblDataSoruce.setText(ATPMessages.getString("MW.LD.DATA_SOURCE"));
    this.jPanel3.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel4.setPreferredSize(new Dimension(298, 598));
    this.pnlsearch.addPropertyChangeListener((PropertyChangeListener)new pnlDataDecode_pnlsearch_propertyChangeAdapter(this));
    this.pnlsearch.hideOption(7, false);
    this.pnlsearch.hideOption(6, false);
    add(this.lblTitle, "North");
    add(this.jPanel1, "Center");
    this.jPanel1.add(this.jScrollPane1, "Center");
    this.jPanel1.add(this.jPanel4, "East");
    this.jScrollPane1.getViewport().add((Component)this.jTable1, (Object)null);
    this.buttonGroup1.add(this.rdoDecodeMO);
    this.buttonGroup1.add(this.rdoDecodeLocal);
    this.buttonGroup1.add(this.rdoPath);
    this.jPanel3.add(this.lblDataSoruce, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 0, 0), 0, 0));
    this.jPanel3.add(this.rdoDecodeMO, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 30, 0, 0), 0, 0));
    this.jPanel3.add(this.rdoDecodeLocal, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 20, 0, 0), 0, 0));
    this.jPanel3.add(this.rdoPath, new GridBagConstraints(2, 1, 3, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 13, 0, 0), 0, 0));
    this.jPanel3.add(this.txtPath, new GridBagConstraints(0, 2, 3, 1, 1.0D, 0.0D, 17, 2, new Insets(0, 30, 0, 0), 0, 0));
    this.jPanel3.add(this.btnFileChoose, new GridBagConstraints(3, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
    this.jPanel3.add(this.btnPathSubmit, new GridBagConstraints(4, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 12, 3, 3), 0, 0));
    this.jPanel4.add((Component)this.pnlsearch, new GridBagConstraints(0, 0, 3, 1, 1.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
    this.jPanel4.add(this.jPanel3, new GridBagConstraints(0, 1, 3, 1, 0.0D, 1.0D, 11, 2, new Insets(5, 0, 0, 0), 0, 0));
    this.jPanel4.add(this.btnDecode, new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 50, 3, 5), 0, 0));
    this.jPanel4.add(this.btnViewCode, new GridBagConstraints(2, 3, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 50, 3, 5), 0, 0));
    this.jPanel4.add(this.btnPathRefresh, new GridBagConstraints(2, 4, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 50, 3, 5), 0, 0));
    this.jPanel4.add(this.tagRefreshTime, new GridBagConstraints(0, 5, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
    this.jPanel4.add(this.lblRefreshTime, new GridBagConstraints(1, 5, 2, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    this.jPanel4.add(this.tagRefreshMsg, new GridBagConstraints(0, 6, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
    this.jPanel4.add(this.lblRefreshMsg, new GridBagConstraints(1, 6, 2, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
  }
  
  private void _$9541() {
    try {
      DiskStatus.getDiskStatus(new File(this.FilePath));
      if (this._$9531) {
        if (this.rdoDecodeLocal.isSelected()) {
          Object object = new Object(this);
          this.t = new Timer();
          this.t.scheduleAtFixedRate((TimerTask)object, 0L, 18000000L);
        } else {
          this.vData = PathHandler.getPathList(this.FilePath);
        } 
      } else if (this.rdoDecodeLocal.isSelected()) {
        this.vData = MWFileWatcher.getList();
      } else {
        this.vData = PathHandler.getPathList(this.FilePath);
      } 
      this.vData = this.pnlsearch.SearchData(this.vData);
      this.vDataName = new Vector();
      this.vDataName.add(ATPMessages.getString("MW.LOG.RUNNINGDATE"));
      this.vDataName.add(ATPMessages.getString("MW.WS.ID"));
      this.vDataName.add(ATPMessages.getString("MW.TR.ID"));
      this.vDataName.add(ATPMessages.getString("MW.DRIVER.ID"));
      this.vDataName.add(ATPMessages.getString("MW.VEHICLE.ID"));
      this.dtm.setDataVector(this.vData, this.vDataName);
      if (this.vData.size() == 0)
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.SEARCH.NOT_FOUND"), ATPMessages.getString("MW.GNL.INFO"), 1); 
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.WARN"), 2);
    } finally {
      TableColumn tableColumn = this.jTable1.getColumnModel().getColumn(3);
      tableColumn.setCellRenderer((TableCellRenderer)new Object(this));
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
  
  void rdoDecodeLocal_actionPerformed(ActionEvent paramActionEvent) {
    this.FilePath = this.MWLogPath;
    _$16214();
    Refresh();
  }
  
  void rdoDecodeMO_actionPerformed(ActionEvent paramActionEvent) {
    this.FilePath = this.MOLogPath;
    _$16214();
    Refresh();
  }
  
  void rdoPath_actionPerformed(ActionEvent paramActionEvent) {
    this.FilePath = this.txtPath.getText();
    this.txtPath.setEnabled(true);
    this.btnPathSubmit.setEnabled(true);
    this.btnFileChoose.setEnabled(true);
    this.dtm.setDataVector(null, this.vDataName);
  }
}
