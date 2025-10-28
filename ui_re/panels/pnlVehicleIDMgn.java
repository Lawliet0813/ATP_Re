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
import com.MiTAC.TRA.ATP.ui.dialogs.dlgVehicleIDEdit;
import com.MiTAC.TRA.ATP.ui.dialogs.dlgVehicleTypeEdit;
import com.MiTAC.TRA.ATP.ui.frames.frmMain;
import com.MiTAC.TRA.ATP.ui.adapters.pnlVehicleIDMgn_btnVehicleIDDelete_actionAdapter;
import com.MiTAC.TRA.ATP.ui.pnlVehicleIDMgn_btnVehicleIDEdit_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlVehicleIDMgn_btnVehicleIDNew_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlVehicleIDMgn_btnVehicleTypeDelete_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlVehicleIDMgn_btnVehicleTypeEdit_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlVehicleIDMgn_btnVehicleTypeNew_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlVehicleIDMgn_jButton1_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlVehicleIDMgn_jTable1_keyAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlVehicleIDMgn_jTable1_mouseAdapter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class pnlVehicleIDMgn extends JPanel implements RefreshFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  
  BorderLayout borderLayout2 = new BorderLayout();
  
  BorderLayout borderLayout3 = new BorderLayout();
  
  JButton btnVehicleIDAll = new JButton();
  
  JButton btnVehicleIDDelete = new JButton();
  
  JButton btnVehicleIDEdit = new JButton();
  
  JButton btnVehicleIDNew = new JButton();
  
  JButton btnVehicleTypeDelete = new JButton();
  
  JButton btnVehicleTypeEdit = new JButton();
  
  JButton btnVehicleTypeNew = new JButton();
  
  CheckUser checkUser = new CheckUser();
  
  ConnectDB conn = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  DefaultSortTableModel dtm1 = new DefaultSortTableModel();
  
  DefaultSortTableModel dtm2 = new DefaultSortTableModel();
  
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
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
  
  JLabel lblTitle = new JLabel();
  
  PasswordInputDialog pwdInputDialog;
  
  Vector vehicleIDData = new Vector();
  
  private static int _$7771;
  
  private static int _$7772;
  
  Vector vehicleIDName = new Vector();
  
  private static int _$7770;
  
  Vector vehicleTypeData = new Vector();
  
  private static int _$7768;
  
  private static int _$7769;
  
  Vector vehicleTypeName = new Vector();
  
  private static int _$7767 = 51;
  
  int vehicleTypeSelected = 0;
  
  static {
    _$7768 = 52;
    _$7769 = 53;
    _$7770 = 54;
    _$7771 = 55;
    _$7772 = 56;
  }
  
  public pnlVehicleIDMgn() {
    try {
      init();
      Refresh();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void Refresh() {
    try {
      this.vehicleTypeData = this.conn.getData("select * from vehicletype");
      this.dtm1.setDataVector(this.vehicleTypeData, this.vehicleTypeName);
      this.jTable1.setRowSelectionInterval(this.vehicleTypeSelected, this.vehicleTypeSelected);
      if (this.vehicleTypeData.size() > 0) {
        int i = this.jTable1.getSelectedRow();
        if (i < 0) {
          this.vehicleIDData = this.conn.getData("select * from vehicleid");
        } else {
          String str = (String)this.dtm1.getValueAt(i, 0);
          this.vehicleIDData = this.conn.getData("select * from VehicleID where VehicleType = '" + str + "'");
          this.jLabel3.setText(str + ATPMessages.getString("MW.VEHICLE.ID.SHOWALL"));
        } 
        this.dtm2.setDataVector(this.vehicleIDData, this.vehicleIDName);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnVehicleIDDelete_actionPerformed(ActionEvent paramActionEvent) {
    if (this.jTable2.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.GNL.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      this.pwdInputDialog = new PasswordInputDialog((Frame)getRootPane().getParent(), "密碼確認", true);
      this.pwdInputDialog.show();
      if (!this.pwdInputDialog.isCancel())
        try {
          String str = (String)this.dtm2.getValueAt(this.jTable2.getSelectedRow(), 0);
          if (JOptionPane.showConfirmDialog(getRootPane().getParent(), ATPMessages.getString("MW.VEHICLE.ID.DELETE.Q.CONFIRM") + "\n" + str + ".", ATPMessages.getString("MW.GNL.ASK"), 0, 3) == 0) {
            this.conn.Delete("delete VehicleID where VehicleID ='" + str + "'");
            this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), this.jLabel3.getText(), "動力車頭號碼", "刪除", this.vehicleIDData, this.jTable2.getSelectedRows(), this.vehicleIDName);
            Refresh();
          } 
        } catch (Exception exception) {
          exception.printStackTrace();
        }  
    } 
  }
  
  void btnVehicleIDEdit_actionPerformed(ActionEvent paramActionEvent) {
    if (this.jTable2.getSelectedRow() != -1) {
      String str1 = (String)this.dtm2.getValueAt(this.jTable2.getSelectedRow(), 1);
      String str2 = (String)this.dtm2.getValueAt(this.jTable2.getSelectedRow(), 0);
      dlgVehicleIDEdit dlgVehicleIDEdit = new dlgVehicleIDEdit((Frame)getRootPane().getParent(), str1, str2);
      dlgVehicleIDEdit.show();
      Refresh();
    } else {
      JOptionPane.showMessageDialog(getRootPane().getParent(), ATPMessages.getString("MW.GNL.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnVehicleIDNew_actionPerformed(ActionEvent paramActionEvent) {
    dlgVehicleIDEdit dlgVehicleIDEdit = new dlgVehicleIDEdit((Frame)getRootPane().getParent(), (String)this.dtm1.getValueAt(this.jTable1.getSelectedRow(), 0));
    dlgVehicleIDEdit.show();
    Refresh();
  }
  
  void btnVehicleTypeDelete_actionPerformed(ActionEvent paramActionEvent) {
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.GNL.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      this.pwdInputDialog = new PasswordInputDialog((Frame)getRootPane().getParent(), "密碼確認", true);
      this.pwdInputDialog.show();
      if (!this.pwdInputDialog.isCancel())
        try {
          String str = (String)this.dtm1.getValueAt(this.jTable1.getSelectedRow(), 0);
          if (JOptionPane.showConfirmDialog(getRootPane().getParent(), ATPMessages.getString("MW.VEHICLE.TYPE.DELETE.Q.CONFIRM") + "\n" + str + ".", ATPMessages.getString("MW.GNL.ASK"), 0, 3) == 0) {
            this.conn.Delete("delete VehicleType where VehicleType ='" + str + "'");
            this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), this.jLabel2.getText(), "ATP動力車型", "刪除", this.vehicleTypeData, this.jTable1.getSelectedRows(), this.vehicleTypeName);
            Refresh();
          } 
        } catch (Exception exception) {
          exception.printStackTrace();
        }  
    } 
  }
  
  void btnVehicleTypeEdit_actionPerformed(ActionEvent paramActionEvent) {
    String str = (String)this.dtm1.getValueAt(this.jTable1.getSelectedRow(), 0);
    dlgVehicleTypeEdit dlgVehicleTypeEdit = new dlgVehicleTypeEdit((Frame)getRootPane().getParent(), str);
    dlgVehicleTypeEdit.show();
    Refresh();
  }
  
  void btnVehicleTypeNew_actionPerformed(ActionEvent paramActionEvent) {
    dlgVehicleTypeEdit dlgVehicleTypeEdit = new dlgVehicleTypeEdit((Frame)getRootPane().getParent());
    dlgVehicleTypeEdit.show();
    Refresh();
  }
  
  void init() throws Exception {
    this.lblTitle.setText(ATPMessages.getString("MW.MAIN.MISSION_MGN.VEHICLE_ID_MGN"));
    this.lblTitle.setBorder(BorderFactory.createEtchedBorder());
    this.lblTitle.setFont(new Font("Dialog", 1, 15));
    setLayout(this.borderLayout1);
    this.jSplitPane1.setDividerSize(5);
    this.jSplitPane1.setLastDividerLocation(250);
    this.jPanel1.setLayout(this.borderLayout2);
    this.jPanel2.setLayout(this.borderLayout3);
    this.jLabel2.setText(ATPMessages.getString("MW.VEHICLE.TYPE"));
    this.jLabel3.setText(ATPMessages.getString("MW.VEHICLE.ID"));
    this.btnVehicleTypeNew.setHorizontalTextPosition(11);
    this.btnVehicleTypeNew.setText(ATPMessages.getString("MW.VEHICLE.TYPE.NEW"));
    this.btnVehicleTypeNew.addActionListener((ActionListener)new pnlVehicleIDMgn_btnVehicleTypeNew_actionAdapter(this));
    this.btnVehicleTypeDelete.setText(ATPMessages.getString("MW.VEHICLE.TYPE.DELETE"));
    this.btnVehicleTypeDelete.addActionListener((ActionListener)new pnlVehicleIDMgn_btnVehicleTypeDelete_actionAdapter(this));
    this.btnVehicleTypeEdit.setText(ATPMessages.getString("MW.VEHICLE.TYPE.MODIFY"));
    this.btnVehicleTypeEdit.addActionListener((ActionListener)new pnlVehicleIDMgn_btnVehicleTypeEdit_actionAdapter(this));
    this.btnVehicleIDNew.setText(ATPMessages.getString("MW.VEHICLE.ID.NEW"));
    this.btnVehicleIDNew.addActionListener((ActionListener)new pnlVehicleIDMgn_btnVehicleIDNew_actionAdapter(this));
    this.btnVehicleIDDelete.setText(ATPMessages.getString("MW.VEHICLE.ID.DELETE"));
    this.btnVehicleIDDelete.addActionListener((ActionListener)new pnlVehicleIDMgn_btnVehicleIDDelete_actionAdapter(this));
    this.btnVehicleIDEdit.setText(ATPMessages.getString("MW.VEHICLE.ID.MODIFY"));
    this.btnVehicleIDEdit.addActionListener((ActionListener)new pnlVehicleIDMgn_btnVehicleIDEdit_actionAdapter(this));
    this.jTable1.setBackground(Color.white);
    this.jTable1.setSelectionBackground(new Color(10, 36, 100));
    this.jTable1.addKeyListener((KeyListener)new pnlVehicleIDMgn_jTable1_keyAdapter(this));
    this.jTable1.addMouseListener((MouseListener)new pnlVehicleIDMgn_jTable1_mouseAdapter(this));
    this.jPanel5.setMaximumSize(new Dimension(32767, 32767));
    this.jPanel5.setLayout(this.gridBagLayout1);
    this.btnVehicleIDAll.setText(ATPMessages.getString("MW.VEHICLE.ID.SHOWALL"));
    this.btnVehicleIDAll.addActionListener((ActionListener)new pnlVehicleIDMgn_jButton1_actionAdapter(this));
    add(this.lblTitle, "North");
    add(this.jSplitPane1, "Center");
    this.jSplitPane1.add(this.jPanel1, "left");
    this.jPanel1.add(this.jPanel3, "South");
    this.jPanel3.add(this.btnVehicleTypeNew, (Object)null);
    this.jPanel3.add(this.btnVehicleTypeEdit, (Object)null);
    this.jPanel3.add(this.btnVehicleTypeDelete, (Object)null);
    this.jPanel1.add(this.jLabel2, "North");
    this.jPanel1.add(this.jScrollPane1, "Center");
    this.jScrollPane1.getViewport().add((Component)this.jTable1, (Object)null);
    this.jSplitPane1.add(this.jPanel2, "right");
    this.jPanel2.add(this.jPanel4, "South");
    this.jPanel4.add(this.btnVehicleIDNew, (Object)null);
    this.jPanel4.add(this.btnVehicleIDEdit, (Object)null);
    this.jPanel4.add(this.btnVehicleIDDelete, (Object)null);
    this.jPanel2.add(this.jScrollPane2, "Center");
    this.jPanel2.add(this.jPanel5, "North");
    this.jPanel5.add(this.btnVehicleIDAll, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 46, 0, 2), 7, -10));
    this.jPanel5.add(this.jLabel3, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 5, 0, 0), 0, 0));
    this.jScrollPane2.getViewport().add((Component)this.jTable2, (Object)null);
    this.jSplitPane1.setDividerLocation(300);
    this.vehicleTypeName.add(ATPMessages.getString("MW.VEHICLE.TYPE"));
    this.vehicleTypeName.add(ATPMessages.getString("MW.VEHICLE.ID.FORMAT"));
    this.vehicleIDName.add(ATPMessages.getString("MW.VEHICLE.ID"));
    this.vehicleIDName.add(ATPMessages.getString("MW.VEHICLE.TYPE"));
    this.btnVehicleTypeNew.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$7767));
    this.btnVehicleTypeDelete.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$7768));
    this.btnVehicleTypeEdit.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$7769));
    this.btnVehicleIDNew.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$7770));
    this.btnVehicleIDDelete.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$7771));
    this.btnVehicleIDEdit.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$7772));
  }
  
  void jButton1_actionPerformed(ActionEvent paramActionEvent) {
    try {
      this.jLabel3.setText(ATPMessages.getString("MW.VEHICLE.ID.SHOWALL"));
      this.vehicleIDData = this.conn.getData("select * from VehicleID");
      this.dtm2.setDataVector(this.vehicleIDData, this.vehicleIDName);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void jTable1_keyPressed(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyCode() == 38 || paramKeyEvent.getKeyCode() == 40) {
      this.vehicleTypeSelected = this.jTable1.getSelectedRow();
      Refresh();
    } 
  }
  
  void jTable1_mouseClicked(MouseEvent paramMouseEvent) {
    try {
      this.vehicleTypeSelected = this.jTable1.getSelectedRow();
      Refresh();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
