package ui.panels;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CheckUser;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.Tools.PasswordInputDialog;
import com.MiTAC.TRA.ATP.Tools.RefreshFrame;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.frames.frmAddLine;
import com.MiTAC.TRA.ATP.ui.frames.frmAddStation;
import com.MiTAC.TRA.ATP.ui.frames.frmLineEdit;
import com.MiTAC.TRA.ATP.ui.frames.frmMain;
import com.MiTAC.TRA.ATP.ui.adapters.pnlLineMgn_btnAddLine_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlLineMgn_btnAddStation_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlLineMgn_btnDelLine_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlLineMgn_btnDelStation_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlLineMgn_btnEditLine_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlLineMgn_btnEditStation_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlLineMgn_btnShowAllStation_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlLineMgn_jTable1_keyAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlLineMgn_jTable1_mouseAdapter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class pnlLineMgn extends JPanel implements RefreshFrame {
  private static int _$16176 = 91;
  
  private static int _$16179;
  
  BorderLayout borderLayout1 = new BorderLayout();
  
  BorderLayout borderLayout2 = new BorderLayout();
  
  BorderLayout borderLayout3 = new BorderLayout();
  
  JButton btnAddLine = new JButton();
  
  JButton btnAddStation = new JButton();
  
  JButton btnDelLine = new JButton();
  
  JButton btnDelStation = new JButton();
  
  JButton btnEditLine = new JButton();
  
  JButton btnEditStation = new JButton();
  
  JButton btnShowAllStation = new JButton();
  
  CheckUser checkUser = new CheckUser();
  
  ConnectDB connDB = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  private static int _$16178;
  
  private static int _$16181;
  
  DefaultSortTableModel dtm1 = new DefaultSortTableModel();
  
  DefaultSortTableModel dtm2 = new DefaultSortTableModel();
  
  private static int _$16177 = 92;
  
  private static int _$16180;
  
  FlowLayout flowLayout1 = new FlowLayout();
  
  GridLayout gridLayout1 = new GridLayout();
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
  JLabel jLabel4 = new JLabel();
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel2 = new JPanel();
  
  JPanel jPanel3 = new JPanel();
  
  JPanel jPanel4 = new JPanel();
  
  JPanel jPanel5 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JScrollPane jScrollPane2 = new JScrollPane();
  
  JSplitPane jSplitPane1 = new JSplitPane();
  
  JSortTable jTable1 = new JSortTable((SortTableModel)this.dtm1);
  
  JSortTable jTable2 = new JSortTable((SortTableModel)this.dtm2);
  
  PasswordInputDialog pwdInputDialog;
  
  Vector vLine;
  
  Vector vLineName = new Vector();
  
  Vector vStation;
  
  Vector vStationMLine;
  
  Vector vStationName = new Vector();
  
  static {
    _$16178 = 93;
    _$16179 = 94;
    _$16180 = 95;
    _$16181 = 96;
  }
  
  public pnlLineMgn() {
    try {
      init();
      Refresh();
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  public void Refresh() {
    try {
      String str = "SELECT LineID, LineCName FROM Line";
      this.vLine = this.connDB.getData(str);
      str = "SELECT * FROM Station";
      this.vStation = this.connDB.getData(str);
      this.dtm1.setDataVector(this.vLine, this.vLineName);
      this.dtm2.setDataVector(this.vStation, this.vStationName);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnAddLine_actionPerformed(ActionEvent paramActionEvent) {
    try {
      frmAddLine frmAddLine = new frmAddLine((Frame)getRootPane().getParent(), ATPMessages.getString("MW.LINE.NEW"), true);
      frmAddLine.show();
      Refresh();
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnAddStation_actionPerformed(ActionEvent paramActionEvent) {
    try {
      frmAddStation frmAddStation = new frmAddStation(this.vLine, (Frame)getRootPane().getParent(), "", true);
      frmAddStation.show();
      Refresh();
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnDelLine_actionPerformed(ActionEvent paramActionEvent) {
    try {
      if (this.jTable1.getSelectedRow() == -1) {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LINE.DELETE.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      } else {
        this.pwdInputDialog = new PasswordInputDialog((Frame)getRootPane().getParent(), "", true);
        this.pwdInputDialog.show();
        if (!this.pwdInputDialog.isCancel()) {
          StringBuffer stringBuffer = new StringBuffer();
          Vector vector = new Vector();
          for (byte b = 0; b < (this.jTable1.getSelectedRows()).length; b++) {
            int j = this.jTable1.getSelectedRows()[b];
            stringBuffer.append(((Vector)this.vLine.get(j)).get(0).toString());
            stringBuffer.append(",");
            vector.addElement(this.jTable1.getValueAt(j, 1));
          } 
          int i = JOptionPane.showConfirmDialog(getRootPane().getParent(), ATPMessages.getString("MW.LINE.DELETE.Q.CONFIRM") + "\n" + stringBuffer, ATPMessages.getString("MW.GNL.ASK"), 0, 3);
          if (i == 0) {
            String str = "DELETE FROM Line WHERE (LineID='" + this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 0).toString() + "')";
            this.connDB.Delete(str);
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.LINE.DELETE.OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
            Refresh();
          } 
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnDelStation_actionPerformed(ActionEvent paramActionEvent) {
    try {
      if (this.jTable2.getSelectedRow() == -1) {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.STATION.DELETE.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      } else {
        this.pwdInputDialog = new PasswordInputDialog((Frame)getRootPane().getParent(), "", true);
        this.pwdInputDialog.show();
        if (!this.pwdInputDialog.isCancel()) {
          StringBuffer stringBuffer = new StringBuffer();
          Vector vector = new Vector();
          for (byte b = 0; b < (this.jTable2.getSelectedRows()).length; b++) {
            int j = this.jTable2.getSelectedRows()[b];
            stringBuffer.append(((Vector)this.vStation.get(j)).get(0).toString());
            stringBuffer.append("[");
            stringBuffer.append(((Vector)this.vStation.get(j)).get(3).toString());
            stringBuffer.append("站],");
            vector.addElement(this.jTable2.getValueAt(j, 3));
          } 
          int i = JOptionPane.showConfirmDialog(getRootPane().getParent(), ATPMessages.getString("MW.STATION.DELETE.Q.CONFIRM") + "\n" + stringBuffer, ATPMessages.getString("MW.GNL.ASK"), 0, 3);
          if (i == 0) {
            for (byte b1 = 0; b1 < vector.size(); b1++) {
              String str = "DELETE FROM Station WHERE (StationCName='" + vector.get(b1) + "')";
              this.connDB.Delete(str);
            } 
            this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), this.jLabel3.getText(), "車站", "刪除", this.vStation, this.jTable2.getSelectedRows(), this.vStationName);
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.STATION.DELETE.OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
            Refresh();
          } 
        } 
      } 
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnEditLine_actionPerformed(ActionEvent paramActionEvent) {
    try {
      int i = this.jTable1.getSelectedRow();
      boolean bool = false;
      if (this.jTable1.getSelectedRow() == -1) {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.STATION.MODIFY.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      } else {
        frmLineEdit frmLineEdit = new frmLineEdit(i, this.vLine, (Frame)getRootPane().getParent(), ATPMessages.getString("MW.LINE.MODIFY"), true);
        frmLineEdit.show();
      } 
      Refresh();
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnEditStation_actionPerformed(ActionEvent paramActionEvent) {
    try {
      int i = 0;
      if (this.jTable2.getSelectedRow() == -1) {
        i = 0;
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.STATION.MODIFY.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      } else {
        i = this.jTable2.getSelectedRow();
        Vector vector = this.dtm2.getDataVector().get(i);
        frmAddStation frmAddStation = new frmAddStation(this.vLine, vector, (Frame)getRootPane().getParent(), "", true);
        frmAddStation.show();
        Refresh();
      } 
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnShowAllStation_actionPerformed(ActionEvent paramActionEvent) {
    Refresh();
    this.jLabel4.setText(ATPMessages.getString("MW.STATION.ALL"));
  }
  
  void init() throws Exception {
    this.jLabel1.setFont(new Font("Dialog", 1, 15));
    this.jLabel1.setBorder(BorderFactory.createEtchedBorder());
    this.jLabel1.setText(ATPMessages.getString("MW.MAIN.TRAIN_AND_STATION_MGN.LINE_STATION_MGN"));
    setLayout(this.borderLayout1);
    this.btnAddStation.setText(ATPMessages.getString("MW.STATION.NEW"));
    this.btnAddStation.addActionListener((ActionListener)new pnlLineMgn_btnAddStation_actionAdapter(this));
    this.btnDelStation.setText(ATPMessages.getString("MW.STATION.DELETE"));
    this.btnDelStation.addActionListener((ActionListener)new pnlLineMgn_btnDelStation_actionAdapter(this));
    this.btnEditStation.setText(ATPMessages.getString("MW.STATION.MODIFY"));
    this.btnEditStation.addActionListener((ActionListener)new pnlLineMgn_btnEditStation_actionAdapter(this));
    this.jSplitPane1.setOrientation(1);
    this.jSplitPane1.setDividerSize(5);
    this.jPanel2.setPreferredSize(new Dimension(500, 500));
    this.jPanel2.setInputVerifier((InputVerifier)null);
    this.jPanel2.setLayout(this.borderLayout2);
    this.jLabel2.setText(ATPMessages.getString("MW.LINE.ALL"));
    this.jPanel3.setLayout(this.borderLayout3);
    this.jLabel3.setText(ATPMessages.getString("MW.STATION"));
    this.jPanel3.setMaximumSize(new Dimension(2147483647, 2147483647));
    this.jLabel4.setText("jLabel4");
    this.btnShowAllStation.setAlignmentY(0.5F);
    this.btnShowAllStation.setPreferredSize(new Dimension(123, 17));
    this.btnShowAllStation.setToolTipText("");
    this.btnShowAllStation.setMargin(new Insets(2, 2, 2, 2));
    this.btnShowAllStation.setText(ATPMessages.getString("MW.STATION.SHOWALL"));
    this.btnShowAllStation.addActionListener((ActionListener)new pnlLineMgn_btnShowAllStation_actionAdapter(this));
    this.jTable1.setSelectionMode(0);
    this.jTable1.addMouseListener((MouseListener)new pnlLineMgn_jTable1_mouseAdapter(this));
    this.jTable1.addKeyListener((KeyListener)new pnlLineMgn_jTable1_keyAdapter(this));
    this.jTable2.setSelectionMode(0);
    this.btnEditLine.setMargin(new Insets(2, 2, 2, 2));
    this.btnEditLine.setText(ATPMessages.getString("MW.LINE.MODIFY"));
    this.btnEditLine.addActionListener((ActionListener)new pnlLineMgn_btnEditLine_actionAdapter(this));
    this.btnDelLine.setMargin(new Insets(2, 2, 2, 2));
    this.btnDelLine.setText(ATPMessages.getString("MW.LINE.DELETE"));
    this.btnDelLine.addActionListener((ActionListener)new pnlLineMgn_btnDelLine_actionAdapter(this));
    this.btnAddLine.setMargin(new Insets(2, 2, 2, 2));
    this.btnAddLine.setText(ATPMessages.getString("MW.LINE.NEW"));
    this.btnAddLine.addActionListener((ActionListener)new pnlLineMgn_btnAddLine_actionAdapter(this));
    this.jPanel5.setLayout(this.gridLayout1);
    this.jPanel4.setLayout(this.flowLayout1);
    add(this.jLabel1, "North");
    this.jPanel1.add(this.btnAddStation, (Object)null);
    this.jPanel1.add(this.btnEditStation, (Object)null);
    this.jPanel1.add(this.btnDelStation, (Object)null);
    this.jPanel3.add(this.jPanel5, "North");
    this.jPanel5.add(this.jLabel3, (Object)null);
    this.jPanel5.add(this.jLabel4, (Object)null);
    this.jPanel5.add(this.btnShowAllStation, (Object)null);
    add(this.jSplitPane1, "Center");
    this.jSplitPane1.add(this.jPanel2, "top");
    this.jPanel2.add(this.jScrollPane1, "Center");
    this.jPanel2.add(this.jLabel2, "North");
    this.jPanel2.add(this.jPanel4, "South");
    this.jPanel4.add(this.btnAddLine, (Object)null);
    this.jPanel4.add(this.btnEditLine, (Object)null);
    this.jPanel4.add(this.btnDelLine, (Object)null);
    this.jSplitPane1.add(this.jPanel3, "bottom");
    this.jPanel3.add(this.jScrollPane2, "Center");
    this.jPanel3.add(this.jPanel1, "South");
    this.jScrollPane2.getViewport().add((Component)this.jTable2, (Object)null);
    this.jScrollPane1.getViewport().add((Component)this.jTable1, (Object)null);
    this.jSplitPane1.setDividerLocation(200);
    this.vLineName.addElement(ATPMessages.getString("MW.LINE.ID"));
    this.vLineName.addElement(ATPMessages.getString("MW.LINE.NAME"));
    this.vStationName.addElement(ATPMessages.getString("MW.STATION.ID"));
    this.vStationName.addElement(ATPMessages.getString("MW.STATION.KM"));
    this.vStationName.addElement(ATPMessages.getString("MW.STATION.E_NAME"));
    this.vStationName.addElement(ATPMessages.getString("MW.STATION.NAME"));
    this.vStationName.addElement(ATPMessages.getString("MW.STATION.DATA"));
    this.vStationName.addElement(ATPMessages.getString("MW.STATION.BELONG"));
    this.vStationName.add("地上設備車站代碼");
    this.jLabel4.setText(ATPMessages.getString("MW.STATION.ALL"));
    this.btnAddStation.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$16179));
    this.btnDelStation.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$16181));
    this.btnEditStation.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$16180));
    this.btnAddLine.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$16176));
    this.btnDelLine.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$16178));
    this.btnEditLine.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$16177));
  }
  
  void jTable1_keyReleased(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyCode() == 38 || paramKeyEvent.getKeyCode() == 40 || paramKeyEvent.getKeyCode() == 10) {
      int i = this.jTable1.getSelectedRow();
      this.vStationMLine = new Vector();
      String str1 = ((String)((Vector)this.vLine.get(i)).get(0)).toString().trim();
      String str2 = "";
      for (byte b = 0; b <= this.vStation.size() - 1; b++) {
        str2 = ((String)((Vector)this.vStation.get(b)).get(5)).toString().trim();
        if (str1.equals(str2))
          this.vStationMLine.addElement(this.vStation.get(b)); 
      } 
      this.jLabel4.setText(((String)((Vector)this.vLine.get(i)).get(2)).toString());
      this.dtm2.setDataVector(this.vStationMLine, this.vStationName);
    } 
  }
  
  void jTable1_mouseClicked(MouseEvent paramMouseEvent) {
    int i = this.jTable1.getSelectedRow();
    this.vStationMLine = new Vector();
    String str1 = ((String)((Vector)this.vLine.get(i)).get(0)).toString().trim();
    String str2 = "";
    for (byte b = 0; b <= this.vStation.size() - 1; b++) {
      str2 = ((String)((Vector)this.vStation.get(b)).get(5)).toString().trim();
      if (str1.equals(str2))
        this.vStationMLine.addElement(this.vStation.get(b)); 
    } 
    this.jLabel4.setText(((String)((Vector)this.vLine.get(i)).get(1)).toString());
    this.dtm2.setDataVector(this.vStationMLine, this.vStationName);
  }
  
  private void _$16160() throws Exception {
    String str = "SELECT LineID, LineCName FROM Line";
    this.vLine = this.connDB.getData(str);
    str = "SELECT * FROM Station";
    this.vStation = this.connDB.getData(str);
    this.dtm1.setDataVector(this.vLine, this.vLineName);
    this.dtm2.setDataVector(this.vStation, this.vStationName);
  }
}
