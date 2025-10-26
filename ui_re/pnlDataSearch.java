package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import com.MiTAC.TRA.ATP.Tools.RefreshFrame;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.myTableModel;
import com.MiTAC.TRA.ATP.ui.pnlDataSearch_btnCancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.pnlDataSearch_btnUpdate_actionAdapter;
import com.MiTAC.TRA.ATP.ui.pnlDataSearch_pnlsearch_propertyChangeAdapter;
import com.MiTAC.TRA.ATP.ui.pnlDataSearch_rdo_actionAdapter;
import com.MiTAC.TRA.ATP.ui.pnlSearch;
import com.borland.jbcl.layout.VerticalFlowLayout;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class pnlDataSearch extends JPanel implements RefreshFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  
  BorderLayout borderLayout2 = new BorderLayout();
  
  BorderLayout borderLayout3 = new BorderLayout();
  
  JButton btnCancelUpdate = new JButton(ATPMessages.getString("MW.GNL.CANCEL"));
  
  JButton btnUpdateFeedback = new JButton(ATPMessages.getString("MW.LI.UPDATE"));
  
  ButtonGroup buttonGroup1 = new ButtonGroup();
  
  DefaultSortTableModel dstm = new DefaultSortTableModel();
  
  myTableModel dtm = new myTableModel();
  
  ButtonGroup grpFeedback = new ButtonGroup();
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel2 = new JPanel();
  
  JPanel jPanel3 = new JPanel();
  
  JPanel jPanel4 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JSortTable jTable1 = new JSortTable((SortTableModel)this.dstm);
  
  JLabel lblTitle = new JLabel();
  
  JPanel pnlFeedback = new JPanel();
  
  pnlSearch pnlsearch = new pnlSearch(true);
  
  JRadioButton rdo1 = new JRadioButton(ATPMessages.getString("MW.LI.LOG_RETURN.SHOW.ALL"));
  
  JRadioButton rdo2 = new JRadioButton(ATPMessages.getString("MW.LI.LOG_RETURN.SHOW.ON_THE_WAY"));
  
  JRadioButton rdo3 = new JRadioButton(ATPMessages.getString("MW.LI.LOG_RETURN.SHOW.MISSED"));
  
  JRadioButton rdoBackup = new JRadioButton();
  
  JRadioButton rdoBackupCheck = new JRadioButton();
  
  JRadioButton rdoDelete = new JRadioButton();
  
  JRadioButton rdoFeedbackCheck = new JRadioButton();
  
  JRadioButton rdoLocal = new JRadioButton();
  
  JRadioButton rdoTransfer = new JRadioButton();
  
  JComboBox selMission = new JComboBox(this.statMission_cho);
  
  String[] statMission = new String[] { ATPMessages.getString("MW.LI.LOG_RETURN.RETURNED"), ATPMessages.getString("MW.LI.LOG_RETURN.FIX"), ATPMessages.getString("MW.LI.LOG_RETURN.MISSED") };
  
  String[] statMission_cho = new String[] { "未知", ATPMessages.getString("MW.LI.LOG_RETURN.FIX"), ATPMessages.getString("MW.LI.LOG_RETURN.MISSED") };
  
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  
  VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
  
  public pnlDataSearch() {
    try {
      init();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void Refresh() {
    try {
      Vector vector1;
      Vector vector = new Vector();
      vector.add(ATPMessages.getString("MW.LOG.RUNNINGDATE"));
      vector.add(ATPMessages.getString("MW.WS.ID"));
      vector.add(ATPMessages.getString("MW.TR.ID"));
      vector.add(ATPMessages.getString("MW.DRIVER.ID"));
      vector.add(ATPMessages.getString("MW.VEHICLE.ID"));
      if (this.rdoBackup.isSelected()) {
        vector.add(ATPMessages.getString("MW.LB.MO.LABEL"));
        vector.add(ATPMessages.getString("MW.LI.BACKUP.DATE"));
      } else if (this.rdoDelete.isSelected()) {
        vector.add(ATPMessages.getString("MW.LI.FILE_PATH"));
        vector.add(ATPMessages.getString("MW.LI.DELETE.DATE"));
      } else if (this.rdoTransfer.isSelected()) {
        vector.add(ATPMessages.getString("MW.LT.DATA_SOURCE"));
        vector.add(ATPMessages.getString("MW.LT.DATA_DESTINATION"));
        vector.add(ATPMessages.getString("MW.LI.TRANSPORT.DATE"));
      } else if (!this.rdoLocal.isSelected()) {
        if (this.rdoFeedbackCheck.isSelected()) {
          vector.add(ATPMessages.getString("MW.LI.LOG_RETURN.STATUS"));
        } else if (this.rdoBackupCheck.isSelected()) {
          vector.add("備份狀態");
        } 
      } 
      if (this.rdoLocal.isSelected()) {
        vector1 = PathHandler.getPathList(InitParameters.MWLogPath);
        vector1 = this.pnlsearch.SearchData(vector1);
      } else {
        ConnectDB connectDB = new ConnectDB();
        String str = _$15545();
        vector1 = connectDB.getData(str);
        if (vector1.size() == 0)
          JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.SEARCH.NOT_FOUND"), ATPMessages.getString("MW.GNL.INFO"), 2); 
      } 
      if (this.rdoFeedbackCheck.isSelected()) {
        this.jTable1 = new JSortTable((SortTableModel)this.dtm);
        this.jScrollPane1.getViewport().removeAll();
        this.jScrollPane1.getViewport().add((Component)this.jTable1);
        this.jPanel4.removeAll();
        this.jPanel4.add(this.jScrollPane1, "Center");
        this.jPanel4.add(this.pnlFeedback, "South");
        this.jPanel4.validate();
        this.dtm.setDataVector(vector1, vector);
        TableColumn tableColumn = this.jTable1.getColumnModel().getColumn(5);
        tableColumn.setCellEditor(new DefaultCellEditor(this.selMission));
        tableColumn.setCellRenderer((TableCellRenderer)new Object(this));
        this.jTable1.validate();
      } else if (this.rdoBackupCheck.isSelected()) {
        this.jTable1 = new JSortTable((SortTableModel)this.dtm);
        this.jScrollPane1.getViewport().removeAll();
        this.jScrollPane1.getViewport().add((Component)this.jTable1);
        this.jPanel4.removeAll();
        this.jPanel4.add(this.jScrollPane1, "Center");
        this.jPanel4.add(this.pnlFeedback, "South");
        this.jPanel4.validate();
        this.dtm.setDataVector(vector1, vector);
        TableColumn tableColumn = this.jTable1.getColumnModel().getColumn(5);
        tableColumn.setCellEditor(new DefaultCellEditor(this.selMission));
        tableColumn.setCellRenderer((TableCellRenderer)new Object(this));
        this.jTable1.validate();
      } else {
        this.jTable1 = new JSortTable((SortTableModel)this.dstm);
        this.jScrollPane1.getViewport().removeAll();
        this.jScrollPane1.getViewport().add((Component)this.jTable1);
        this.jPanel4.removeAll();
        this.jPanel4.add(this.jScrollPane1, "Center");
        this.jPanel4.validate();
        this.dstm.setDataVector(vector1, vector);
        this.jTable1.validate();
      } 
      repaint();
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } finally {
      TableColumn tableColumn = this.jTable1.getColumnModel().getColumn(3);
      tableColumn.setCellRenderer((TableCellRenderer)new Object(this));
    } 
  }
  
  void btnCancel_Action(ActionEvent paramActionEvent) {
    int i = JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.GNL.CANCEL.Q.CONFIRM"), ATPMessages.getString("MW.GNL.ASK"), 0, 3);
    if (i == 0)
      Refresh(); 
  }
  
  void btnSearch_actionPerformed(ActionEvent paramActionEvent) {
    Refresh();
  }
  
  void btnUpdateFeedback_actionPerformed(ActionEvent paramActionEvent) {
    ConnectDB connectDB = new ConnectDB();
  }
  
  void btnUpdate_Action(ActionEvent paramActionEvent) {
    Vector vector = _$16945();
    if (vector.size() == 0) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LI.UPDATE.NOCHANGE"), ATPMessages.getString("MW.GNL.INFO"), 2);
    } else {
      ConnectDB connectDB = new ConnectDB();
      connectDB.UpdateMission(vector);
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LI.UPDATE.OK"), ATPMessages.getString("MW.GNL.SUCCESS"), 1);
      Refresh();
    } 
  }
  
  private Vector _$16945() {
    Vector vector = (Vector)this.dtm.getDataVector().clone();
    Vector vector1 = new Vector();
    byte b1 = 5;
    for (byte b2 = 0; b2 < vector.size(); b2++) {
      if (((Vector)vector.get(b2)).get(b1) != null && !((Vector)vector.get(b2)).get(b1).toString().equals("1"))
        if (((Vector)vector.get(b2)).get(b1).toString().equals(this.statMission[1])) {
          ((Vector)vector.get(b2)).remove(b1);
          ((Vector)vector.get(b2)).add("2");
          vector1.add(vector.get(b2));
        } else if (((Vector)vector.get(b2)).get(b1).toString().equals(this.statMission[2])) {
          ((Vector)vector.get(b2)).remove(b1);
          ((Vector)vector.get(b2)).add("3");
          vector1.add(vector.get(b2));
        } else if (((Vector)vector.get(b2)).get(b1).toString().equals(this.statMission_cho[0])) {
          ((Vector)vector.get(b2)).remove(b1);
          ((Vector)vector.get(b2)).add(null);
          vector1.add(vector.get(b2));
        }  
    } 
    return vector1;
  }
  
  void init() throws Exception {
    setLayout(this.borderLayout1);
    this.jPanel3.setLayout((LayoutManager)this.verticalFlowLayout1);
    this.jPanel1.setLayout(this.borderLayout2);
    this.rdoBackup.setSelected(true);
    this.rdoBackup.setText(ATPMessages.getString("MW.LI.BACKUP_LOG_SEARCH"));
    this.rdoBackup.addActionListener((ActionListener)new pnlDataSearch_rdo_actionAdapter(this));
    this.rdoDelete.setText(ATPMessages.getString("MW.LI.DELETE_LOG_SEARCH"));
    this.rdoDelete.addActionListener((ActionListener)new pnlDataSearch_rdo_actionAdapter(this));
    this.rdoTransfer.setText(ATPMessages.getString("MW.LI.TRANSPROT_LOG_SEARCH"));
    this.rdoTransfer.addActionListener((ActionListener)new pnlDataSearch_rdo_actionAdapter(this));
    this.rdoLocal.setText(ATPMessages.getString("MW.LI.MW_LOG_SEARCH"));
    this.rdoLocal.addActionListener((ActionListener)new pnlDataSearch_rdo_actionAdapter(this));
    this.rdoFeedbackCheck.setText(ATPMessages.getString("MW.LI.FEEDBACK_LOG_SEARCH"));
    this.rdoFeedbackCheck.addActionListener((ActionListener)new pnlDataSearch_rdo_actionAdapter(this));
    this.rdoBackupCheck.setText("備份狀態查詢");
    this.rdoBackupCheck.addActionListener((ActionListener)new pnlDataSearch_rdo_actionAdapter(this));
    this.jPanel2.setLayout((LayoutManager)this.verticalFlowLayout2);
    this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel2.setMaximumSize(new Dimension(32767, 32767));
    this.jPanel1.setBorder(BorderFactory.createRaisedBevelBorder());
    this.jPanel4.setDebugGraphicsOptions(0);
    this.jPanel4.setLayout(this.borderLayout3);
    this.lblTitle.setText(ATPMessages.getString("MW.MAIN.TRAINRUNNING_LOG_MGN.LOG_INDEX"));
    this.lblTitle.setRequestFocusEnabled(true);
    this.lblTitle.setBorder(BorderFactory.createEtchedBorder());
    this.lblTitle.setFont(new Font("Dialog", 1, 15));
    this.pnlsearch.addPropertyChangeListener((PropertyChangeListener)new pnlDataSearch_pnlsearch_propertyChangeAdapter(this));
    this.pnlsearch.hideOption(7, false);
    this.pnlsearch.hideOption(6, false);
    this.jPanel2.add(this.rdoBackup, (Object)null);
    this.jPanel2.add(this.rdoDelete, (Object)null);
    this.jPanel2.add(this.rdoTransfer, (Object)null);
    this.jPanel2.add(this.rdoLocal, (Object)null);
    this.jPanel2.add(this.rdoFeedbackCheck, (Object)null);
    this.jPanel2.add(this.rdoBackupCheck, (Object)null);
    add(this.lblTitle, "North");
    add(this.jPanel4, "Center");
    this.jPanel4.add(this.jScrollPane1, "Center");
    this.jScrollPane1.getViewport().add((Component)this.jTable1, (Object)null);
    add(this.jPanel1, "East");
    this.jPanel1.add(this.jPanel3, "Center");
    this.jPanel1.add(this.jPanel2, "North");
    this.buttonGroup1.add(this.rdoBackup);
    this.buttonGroup1.add(this.rdoDelete);
    this.buttonGroup1.add(this.rdoTransfer);
    this.buttonGroup1.add(this.rdoLocal);
    this.buttonGroup1.add(this.rdoFeedbackCheck);
    this.buttonGroup1.add(this.rdoBackupCheck);
    this.jPanel1.add((Component)this.pnlsearch);
    this.pnlFeedback.setLayout(new FlowLayout());
    this.rdo1.addActionListener((ActionListener)new pnlDataSearch_rdo_actionAdapter(this));
    this.rdo2.addActionListener((ActionListener)new pnlDataSearch_rdo_actionAdapter(this));
    this.rdo3.addActionListener((ActionListener)new pnlDataSearch_rdo_actionAdapter(this));
    this.pnlFeedback.add(this.rdo1);
    this.pnlFeedback.add(this.rdo2);
    this.pnlFeedback.add(this.rdo3);
    this.btnUpdateFeedback.addActionListener((ActionListener)new pnlDataSearch_btnUpdate_actionAdapter(this));
    this.pnlFeedback.add(this.btnUpdateFeedback);
    this.btnCancelUpdate.addActionListener((ActionListener)new pnlDataSearch_btnCancel_actionAdapter(this));
    this.pnlFeedback.add(this.btnCancelUpdate);
    this.grpFeedback.add(this.rdo1);
    this.grpFeedback.add(this.rdo2);
    this.grpFeedback.add(this.rdo3);
    this.rdo1.setSelected(true);
  }
  
  void pnlsearch_propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    if (paramPropertyChangeEvent.getPropertyName().equals("search")) {
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
  
  private String _$15545() {
    null = "";
    if (this.rdoBackup.isSelected()) {
      null = "select MissionDate, WSNo, TRNo, DID, VID, DiskNo, BackupDate From LogBackupLibrary" + this.pnlsearch.getSearchString();
    } else if (this.rdoDelete.isSelected()) {
      null = "select MissionDate, WSNo, TRNo, DID, VID, LogPath, DeleteDate From LogDeleteLibrary" + this.pnlsearch.getSearchString();
    } else if (this.rdoTransfer.isSelected()) {
      null = "select MissionDate, WSNo, TRNo, DID, VID, LogFrom, LogTo, InsertDate From LogUploadLibrary" + this.pnlsearch.getSearchString();
    } else if (this.rdoFeedbackCheck.isSelected()) {
      null = "SELECT MissionDate, WSNo, TRNo, DID, VID, Comit FROM MissionHistory" + this.pnlsearch.getSearchString();
      if (this.rdo2.isSelected()) {
        null = null + " AND comit NOT IN (1, 2, 3)";
        null = null + " UNION ";
        null = null + "SELECT MissionDate, WSNo, TRNo, DID, VID, Comit FROM MissionHistory" + this.pnlsearch.getSearchString();
        null = null + " AND comit IS NULL";
      } else if (this.rdo3.isSelected()) {
        null = null + " AND comit = 3";
      } 
    } else if (this.rdoBackupCheck.isSelected()) {
      null = "SELECT MissionDate, WSNo, TRNo, DID, VID, [Backup] FROM MissionHistory" + this.pnlsearch.getSearchString();
      if (this.rdo2.isSelected()) {
        null = null + " AND [Backup] NOT IN (1, 2, 3)";
        null = null + " UNION ";
        null = null + "SELECT MissionDate, WSNo, TRNo, DID, VID, [Backup] FROM MissionHistory" + this.pnlsearch.getSearchString();
        null = null + " AND [Backup] IS NULL";
      } else if (this.rdo3.isSelected()) {
        null = null + " AND [Backup] = 3";
      } 
    } 
    return null + " ORDER BY MissionDate";
  }
  
  void rdo_Refresh(ActionEvent paramActionEvent) {
    Refresh();
  }
}
