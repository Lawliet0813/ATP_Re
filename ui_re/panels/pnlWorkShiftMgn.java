package ui.panels;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CheckUser;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.Tools.PasswordInputDialog;
import com.MiTAC.TRA.ATP.Tools.RefreshFrame;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.Tools.TimeRenderer;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.frames.frmMain;
import com.MiTAC.TRA.ATP.ui.frames.frmWSEdit;
import com.MiTAC.TRA.ATP.ui.adapters.pnlWorkShiftMgn_btnAddWS_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlWorkShiftMgn_btnDelWS_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlWorkShiftMgn_btnEditWS_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlWorkShiftMgn_jTable1_keyAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlWorkShiftMgn_jTable1_mouseAdapter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class pnlWorkShiftMgn extends JPanel implements RefreshFrame {
  private static int _$15135 = 61;
  
  JButton btnAddWS = new JButton();
  
  JButton btnDelWS = new JButton();
  
  JButton btnEditWS = new JButton();
  
  CheckUser checkUser = new CheckUser();
  
  ConnectDB connDB = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  private static int _$15136 = 62;
  
  DefaultSortTableModel dtm1 = new DefaultSortTableModel();
  
  DefaultSortTableModel dtm2 = new DefaultSortTableModel();
  
  private static int _$15137 = 63;
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel2 = new JPanel();
  
  JPanel jPanel3 = new JPanel();
  
  JPanel jPanel4 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JScrollPane jScrollPane2 = new JScrollPane();
  
  JSplitPane jSplitPane1 = new JSplitPane();
  
  JSortTable jTable1 = new JSortTable((SortTableModel)this.dtm1);
  
  JSortTable jTable2 = new JSortTable((SortTableModel)this.dtm2);
  
  PasswordInputDialog pwdInputDialog;
  
  Vector trainRunningdata;
  
  Vector trainRunningdataName = new Vector();
  
  int userSelectRow = 0;
  
  Vector vWSDataInMission;
  
  Vector workShiftData;
  
  Vector workShiftData2;
  
  Vector workShiftDataName = new Vector();
  
  Vector workShiftDataTemp;
  
  public pnlWorkShiftMgn() {
    try {
      init();
      dataInit();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void Refresh() {
    try {
      dataInit();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnAddWS_actionPerformed(ActionEvent paramActionEvent) {
    try {
      frmWSEdit frmWSEdit = new frmWSEdit((Frame)getRootPane().getParent(), ATPMessages.getString("MW.WS.NEW"), true);
      frmWSEdit.setVisible(true);
      dataInit();
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      JOptionPane.showMessageDialog(this, sQLException.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnDelWS_actionPerformed(ActionEvent paramActionEvent) {
    Vector vector = new Vector();
    Vector vector1 = new Vector();
    try {
      if (this.jTable1.getSelectedRow() == -1) {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.WS.NO_DATA"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      } else {
        this.pwdInputDialog = new PasswordInputDialog((Frame)getRootPane().getParent(), "", true);
        this.pwdInputDialog.show();
        if (!this.pwdInputDialog.isCancel()) {
          Vector vector2 = new Vector();
          StringBuffer stringBuffer = new StringBuffer();
          int[] arrayOfInt = this.jTable1.getSelectedRows();
          for (byte b = 0; b < arrayOfInt.length; b++) {
            vector1 = new Vector();
            stringBuffer.append("工作班：");
            stringBuffer.append(this.jTable1.getValueAt(arrayOfInt[b], 0));
            stringBuffer.append(",");
            stringBuffer.append("啟用日期：");
            stringBuffer.append((new SimpleDateFormat("yyyy/M/d")).format(this.jTable1.getValueAt(arrayOfInt[b], 3)));
            stringBuffer.append("\n");
            vector1.addElement(this.jTable1.getValueAt(arrayOfInt[b], 0));
            vector1.addElement(this.jTable1.getValueAt(arrayOfInt[b], 3));
            vector.addElement(vector1);
          } 
          int i = JOptionPane.showConfirmDialog(getRootPane().getParent(), ATPMessages.getString("MW.WS.DELETE.Q.CONFIRM") + "\n" + stringBuffer, ATPMessages.getString("MW.GNL.ASK"), 0, 3);
          if (i == 0) {
            if (_$8111(vector)) {
              JOptionPane.showMessageDialog(this, "無法執行刪除，因為仍有下列資料存在於派班\n" + _$8112() + "請先確認派班內的資料，先將不需使用的派班予以刪除，" + "\n或將工作班內的車次內容修正為最新資料，並完成重新派班後。" + "\n再執行工作班的刪除。謝謝！", ATPMessages.getString("MW.GNL.ERROR"), 0);
            } else {
              _$8107();
            } 
            Refresh();
          } 
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnEditWS_actionPerformed(ActionEvent paramActionEvent) {
    try {
      if (this.jTable1.getSelectedRow() == -1)
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.WS.NO_DATA"), ATPMessages.getString("MW.GNL.ERROR"), 0); 
      if (this.jTable1.getSelectedRowCount() > 1) {
        JOptionPane.showMessageDialog(this, "修改資料時一次僅能選擇一筆作修改", ATPMessages.getString("MW.GNL.ERROR"), 0);
      } else {
        this.userSelectRow = this.jTable1.getSelectedRow();
        this.workShiftDataTemp = this.workShiftData.get(this.userSelectRow);
        frmWSEdit frmWSEdit = new frmWSEdit(this.workShiftDataTemp, this.trainRunningdata, (Frame)getRootPane().getParent(), ATPMessages.getString("MW.WS.MODIFY"), true);
        frmWSEdit.setVisible(true);
        dataInit();
      } 
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      JOptionPane.showMessageDialog(this, sQLException.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void dataInit() throws Exception {
    this.dtm1.setDataVector(_$15138(), this.workShiftDataName);
    TableColumn tableColumn1 = this.jTable1.getColumnModel().getColumn(1);
    tableColumn1.setCellRenderer((TableCellRenderer)new TimeRenderer());
    TableColumn tableColumn2 = this.jTable1.getColumnModel().getColumn(2);
    tableColumn2.setCellRenderer((TableCellRenderer)new TimeRenderer());
    if (!this.workShiftData.isEmpty()) {
      this.jTable1.setRowSelectionInterval(0, 0);
      this.dtm2.setDataVector(_$15140(), this.trainRunningdataName);
    } else {
      this.dtm2.setDataVector(this.trainRunningdata, this.trainRunningdataName);
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.WS.NO_DATA"), ATPMessages.getString("MW.GNL.ERROR"), 2);
    } 
    this.jLabel3.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 0).toString() + "的" + ATPMessages.getString("MW.TR.ID"));
  }
  
  private void _$8107() {
    try {
      StringBuffer stringBuffer = new StringBuffer();
      for (byte b = 0; b < (this.jTable1.getSelectedRows()).length; b++) {
        String str1 = "DELETE FROM WorkShift_TRList WHERE (WSNo='" + this.jTable1.getValueAt(this.jTable1.getSelectedRows()[b], 0).toString() + "' AND WSMotionDate = '" + (new SimpleDateFormat("yyyy/MM/dd")).format(this.jTable1.getValueAt(this.jTable1.getSelectedRows()[b], 3)) + "')";
        this.connDB.Delete(str1);
        String str2 = "DELETE FROM WorkShift WHERE (WSNo='" + this.jTable1.getValueAt(this.jTable1.getSelectedRows()[b], 0).toString() + "' AND WSMotionDate = '" + (new SimpleDateFormat("yyyy/MM/dd")).format(this.jTable1.getValueAt(this.jTable1.getSelectedRows()[b], 3)) + "')";
        this.connDB.Delete(str2);
        stringBuffer.append("工作班:");
        stringBuffer.append(this.jTable1.getValueAt(this.jTable1.getSelectedRows()[b], 0).toString());
        stringBuffer.append(",工作班啟用日期:");
        stringBuffer.append((new SimpleDateFormat("yyyy/MM/dd")).format(this.jTable1.getValueAt(this.jTable1.getSelectedRows()[b], 3)));
        stringBuffer.append("<>");
      } 
      this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), this.jLabel1.getText(), "工作班", "刪除", stringBuffer.toString());
      dataInit();
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      JOptionPane.showMessageDialog(this, sQLException.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  private Vector _$15140() throws Exception {
    this.trainRunningdata = new Vector();
    String str = "SELECT TRNo,TRMotionDate FROM WorkShift_TRList WHERE WSNo ='" + this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 0) + "' AND WSMotionDate = '" + (new SimpleDateFormat("yyyy/M/d")).format(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 3)) + "'";
    this.trainRunningdata = this.connDB.getData(str);
    return this.trainRunningdata;
  }
  
  private Vector _$15138() throws Exception {
    this.workShiftData = new Vector();
    this.workShiftData2 = new Vector();
    String str = "SELECT WSNo,Begin_Time,End_Time,WSMotionDate FROM WorkShift";
    this.workShiftData = this.connDB.getData(str);
    return this.workShiftData;
  }
  
  private boolean _$8111(Vector paramVector) {
    boolean bool = false;
    Vector vector = new Vector();
    this.vWSDataInMission = new Vector();
    int i = paramVector.size();
    try {
      for (byte b = 0; b < i; b++) {
        String str = "SELECT DISTINCT DriverID,RunningDate,WSNo,WSMotionDate FROM Mission WHERE WSNo = '" + ((Vector)paramVector.get(b)).get(0) + "' AND WSMotionDate = '" + (new SimpleDateFormat("yyyy/MM/dd")).format(((Vector)paramVector.get(b)).get(1)) + "'";
        vector = this.connDB.getData(str);
        for (byte b1 = 0; b1 < vector.size(); b1++)
          this.vWSDataInMission.addElement(vector.get(b1)); 
      } 
      if (this.vWSDataInMission.isEmpty()) {
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
    this.jLabel1.setFont(new Font("Dialog", 1, 15));
    this.jLabel1.setBorder(BorderFactory.createEtchedBorder());
    this.jLabel1.setText(ATPMessages.getString("MW.MAIN.MISSION_MGN.WORKSHIFT_MGN"));
    setLayout(new BorderLayout());
    this.btnAddWS.setText(ATPMessages.getString("MW.WS.NEW"));
    this.btnAddWS.addActionListener((ActionListener)new pnlWorkShiftMgn_btnAddWS_actionAdapter(this));
    this.btnDelWS.setText(ATPMessages.getString("MW.WS.DELETE"));
    this.btnDelWS.addActionListener((ActionListener)new pnlWorkShiftMgn_btnDelWS_actionAdapter(this));
    this.btnEditWS.setText(ATPMessages.getString("MW.WS.MODIFY"));
    this.btnEditWS.addActionListener((ActionListener)new pnlWorkShiftMgn_btnEditWS_actionAdapter(this));
    this.jPanel4.setLayout(new BorderLayout());
    this.jLabel2.setText(ATPMessages.getString("MW.WS.ID"));
    this.jLabel3.setText(ATPMessages.getString("MW.TR.ID"));
    setPreferredSize(new Dimension(600, 400));
    this.jTable1.addMouseListener((MouseListener)new pnlWorkShiftMgn_jTable1_mouseAdapter(this));
    this.jPanel1.setLayout(new BorderLayout());
    this.jPanel2.setLayout(new BorderLayout());
    this.jSplitPane1.setDividerSize(2);
    this.jTable1.addKeyListener((KeyListener)new pnlWorkShiftMgn_jTable1_keyAdapter(this));
    add(this.jLabel1, "North");
    add(this.jPanel4, "Center");
    this.jPanel4.add(this.jSplitPane1, "Center");
    this.jSplitPane1.add(this.jPanel1, "left");
    this.jPanel1.add(this.jLabel2, "North");
    this.jPanel1.add(this.jPanel3, "South");
    this.jPanel3.add(this.btnAddWS, (Object)null);
    this.jPanel3.add(this.btnEditWS, (Object)null);
    this.jPanel3.add(this.btnDelWS, (Object)null);
    this.jPanel1.add(this.jScrollPane1, "Center");
    this.jScrollPane1.getViewport().add((Component)this.jTable1, (Object)null);
    this.jSplitPane1.add(this.jPanel2, "right");
    this.jPanel2.add(this.jLabel3, "North");
    this.jPanel2.add(this.jScrollPane2, "Center");
    this.jScrollPane2.getViewport().add((Component)this.jTable2, (Object)null);
    this.jSplitPane1.setDividerLocation(450);
    this.workShiftDataName.add(ATPMessages.getString("MW.WS.ID"));
    this.workShiftDataName.add(ATPMessages.getString("MW.WS.START_TIME"));
    this.workShiftDataName.add(ATPMessages.getString("MW.WS.END_TIME"));
    this.workShiftDataName.add(ATPMessages.getString("MW.WS.EXECUTE_DATE"));
    this.trainRunningdataName.add(ATPMessages.getString("MW.TR.ID"));
    this.trainRunningdataName.add(ATPMessages.getString("MW.TR.EXECUTE_DATE"));
    this.btnAddWS.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$15135));
    this.btnDelWS.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$15136));
    this.btnEditWS.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$15137));
  }
  
  private boolean _$8114() {
    boolean bool = true;
    byte b1 = 0;
    int[] arrayOfInt = this.jTable1.getSelectedRows();
    for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
      for (byte b = 0; b < this.workShiftData.size(); b++) {
        if (((Vector)this.workShiftData.get(b)).get(0).equals(this.jTable1.getValueAt(arrayOfInt[b2], 0)))
          b1++; 
      } 
    } 
    if (b1 < 2)
      bool = false; 
    return bool;
  }
  
  private boolean _$15145() {
    boolean bool = false;
    if (((Vector)this.workShiftData2.get(0)).get(1).equals(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 3)))
      bool = true; 
    return bool;
  }
  
  void jTable1_keyReleased(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyCode() == 38 || paramKeyEvent.getKeyCode() == 40 || paramKeyEvent.getKeyCode() == 10)
      try {
        this.dtm2.setDataVector(_$15140(), this.trainRunningdataName);
      } catch (Exception exception) {
        exception.printStackTrace();
        JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
      }  
  }
  
  void jTable1_mouseClicked(MouseEvent paramMouseEvent) {
    try {
      String str = "SELECT WSNo,MAX(WSMotionDate) FROM WorkShift WHERE WSNo ='" + this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 0) + "' GROUP BY WSNo";
      this.workShiftData2 = this.connDB.getData(str);
      this.dtm2.setDataVector(_$15140(), this.trainRunningdataName);
      this.jLabel3.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 0).toString() + "的" + ATPMessages.getString("MW.TR.ID"));
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  private String _$8112() {
    int i = this.vWSDataInMission.size();
    StringBuffer stringBuffer = new StringBuffer();
    for (byte b = 0; b < i; b++) {
      stringBuffer.append("司機員編號：");
      stringBuffer.append(((Vector)this.vWSDataInMission.get(b)).get(0));
      stringBuffer.append(",");
      stringBuffer.append("工作班執行日期：");
      stringBuffer.append((new SimpleDateFormat("yyyy/M/d")).format(((Vector)this.vWSDataInMission.get(b)).get(1)));
      stringBuffer.append(",");
      stringBuffer.append("工作班：");
      stringBuffer.append(((Vector)this.vWSDataInMission.get(b)).get(2));
      stringBuffer.append("\n");
    } 
    return stringBuffer.toString();
  }
}
