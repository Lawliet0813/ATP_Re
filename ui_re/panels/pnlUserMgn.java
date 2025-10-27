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
import com.MiTAC.TRA.ATP.ui.frames.frmMain;
import com.MiTAC.TRA.ATP.ui.frames.frmUserEdit;
import com.MiTAC.TRA.ATP.ui.adapters.pnlUserMgn_btnAddUser_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlUserMgn_btnDelUser_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlUserMgn_btnEditUser_actionAdapter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class pnlUserMgn extends JPanel implements RefreshFrame {
  private static int _$12826 = 31;
  
  BorderLayout borderLayout1 = new BorderLayout();
  
  BorderLayout borderLayout2 = new BorderLayout();
  
  JButton btnAddUser = new JButton();
  
  JButton btnDelUser = new JButton();
  
  JButton btnEditUser = new JButton();
  
  ButtonGroup buttonGroup1 = new ButtonGroup();
  
  CheckUser checkUser = new CheckUser();
  
  ConnectDB connDB = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  private static int _$12827 = 32;
  
  DefaultSortTableModel dtm1 = new DefaultSortTableModel();
  
  private static int _$10386 = 33;
  
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel2 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JSortTable jTable1 = new JSortTable((SortTableModel)this.dtm1);
  
  JLabel lblTitle = new JLabel();
  
  PasswordInputDialog pwdInputDialog;
  
  Vector vPassword;
  
  Vector vUserName = new Vector();
  
  Vector vUsers;
  
  public pnlUserMgn() {
    try {
      jbInit();
      Refresh();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void Refresh() {
    try {
      if (frmMain.getPriority() == 3) {
        String str = "SELECT MWUser.User_ID, MWUser.UserName, MWUserClass.UserClass FROM MWUser JOIN MWUserClass ON MWUser.Priority = MWUserClass.Priority";
        this.vUsers = this.connDB.getData(str);
        this.dtm1.setDataVector(this.vUsers, this.vUserName);
        str = "SELECT Password FROM MWUser";
        this.vPassword = this.connDB.getData(str);
      } else {
        String str = "SELECT MWUser.User_ID, MWUser.UserName, MWUserClass.UserClass FROM MWUser JOIN MWUserClass ON MWUser.Priority = MWUserClass.Priority WHERE User_ID = '" + frmMain.getUserID() + "'";
        this.vUsers = this.connDB.getData(str);
        this.dtm1.setDataVector(this.vUsers, this.vUserName);
        str = "SELECT Password FROM MWUser WHERE User_ID = '" + frmMain.getUserID() + "'";
        this.vPassword = this.connDB.getData(str);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnAddUser_actionPerformed(ActionEvent paramActionEvent) {
    frmUserEdit frmUserEdit = new frmUserEdit(this.vPassword, (Frame)getRootPane().getParent(), "", true);
    frmUserEdit.show();
    Refresh();
  }
  
  void btnDelUser_actionPerformed(ActionEvent paramActionEvent) {
    StringBuffer stringBuffer = new StringBuffer();
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.GNL.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else if (this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 0).equals(frmMain.getUserID())) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.USER.DELETE.ERROR"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      this.pwdInputDialog = new PasswordInputDialog((Frame)getRootPane().getParent(), "", true);
      this.pwdInputDialog.show();
      if (!this.pwdInputDialog.isCancel())
        try {
          Vector vector = new Vector();
          StringBuffer stringBuffer1 = new StringBuffer();
          for (byte b = 0; b < (this.jTable1.getSelectedRows()).length; b++) {
            int j = this.jTable1.getSelectedRows()[b];
            stringBuffer1.append(((Vector)this.vUsers.get(j)).get(0).toString() + "[" + ((Vector)this.vUsers.get(j)).get(1).toString() + "]");
            stringBuffer1.append(".\n");
            vector.addElement(((Vector)this.vUsers.get(j)).get(0));
            vector.addElement(((Vector)this.vUsers.get(j)).get(1));
          } 
          int i = JOptionPane.showConfirmDialog(getRootPane().getParent(), ATPMessages.getString("MW.USER.Q.DELETE_CONFIRM") + "\n" + stringBuffer1, ATPMessages.getString("MW.GNL.ASK"), 0, 3);
          if (i == 0) {
            for (byte b1 = 0; b1 < (this.jTable1.getSelectedRows()).length; b1++) {
              String str = "DELETE FROM MWUser WHERE (User_ID='" + this.jTable1.getValueAt(this.jTable1.getSelectedRows()[b1], 0).toString() + "')";
              this.connDB.Delete(str);
              stringBuffer.append("使用者編號:");
              stringBuffer.append(this.jTable1.getValueAt(this.jTable1.getSelectedRows()[b1], 0).toString());
              stringBuffer.append(",使用者姓名:");
              stringBuffer.append(this.jTable1.getValueAt(this.jTable1.getSelectedRows()[b1], 1).toString());
              stringBuffer.append(",使用者級別:");
              stringBuffer.append(this.jTable1.getValueAt(this.jTable1.getSelectedRows()[b1], 2).toString());
              stringBuffer.append("<>");
            } 
            this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), this.lblTitle.getText(), null, "刪除", stringBuffer.toString());
            Refresh();
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.GNL.DELETE_OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
          } 
        } catch (Exception exception) {
          exception.printStackTrace();
        }  
    } 
  }
  
  void btnEditUser_actionPerformed(ActionEvent paramActionEvent) {
    Vector vector1 = new Vector();
    Vector vector2 = new Vector();
    int i = 0;
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.GNL.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      i = this.jTable1.getSelectedRow();
      vector1 = this.vUsers.get(i);
      vector2 = this.vPassword.get(i);
      frmUserEdit frmUserEdit = new frmUserEdit(vector1, vector2, (Frame)getRootPane().getParent(), "", true);
      frmUserEdit.show();
      Refresh();
    } 
  }
  
  void jbInit() throws Exception {
    this.lblTitle.setFont(new Font("Dialog", 1, 15));
    this.lblTitle.setBorder(BorderFactory.createEtchedBorder());
    this.lblTitle.setDoubleBuffered(false);
    this.lblTitle.setText(ATPMessages.getString("MW.MAIN.SYSTEM.USER_MGN"));
    setLayout(this.borderLayout1);
    this.jPanel1.setLayout(this.borderLayout2);
    this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel2.setLayout(this.gridBagLayout1);
    this.btnAddUser.setText(ATPMessages.getString("MW.GNL.NEW"));
    this.btnAddUser.addActionListener((ActionListener)new pnlUserMgn_btnAddUser_actionAdapter(this));
    this.btnDelUser.setText(ATPMessages.getString("MW.GNL.DELETE"));
    this.btnDelUser.addActionListener((ActionListener)new pnlUserMgn_btnDelUser_actionAdapter(this));
    this.btnEditUser.setText(ATPMessages.getString("MW.GNL.MODIFY"));
    this.btnEditUser.addActionListener((ActionListener)new pnlUserMgn_btnEditUser_actionAdapter(this));
    add(this.lblTitle, "North");
    add(this.jPanel1, "South");
    this.jPanel1.add(this.jPanel2, "North");
    this.jPanel2.add(this.btnEditUser, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 6, 0, 7), 0, 0));
    this.jPanel2.add(this.btnDelUser, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 6, 0, 0), 0, 0));
    this.jPanel2.add(this.btnAddUser, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 340, 0, 0), 0, 0));
    add(this.jScrollPane1, "Center");
    this.jScrollPane1.getViewport().add((Component)this.jTable1, (Object)null);
    this.vUserName.addElement(ATPMessages.getString("MW.LOGIN.USERID"));
    this.vUserName.addElement(ATPMessages.getString("MW.LOGIN.USERNAME"));
    this.vUserName.addElement(ATPMessages.getString("MW.LOGIN.USERPRIORITY"));
    this.btnAddUser.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$12826));
    this.btnDelUser.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$12827));
    this.btnEditUser.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), _$10386));
  }
}
