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
import com.MiTAC.TRA.ATP.ui.frames.frmDriverEdit;
import com.MiTAC.TRA.ATP.ui.frames.frmMain;
import com.MiTAC.TRA.ATP.ui.adapters.pnlDriverMgn_btnAddDriver_actionAdapter;
import com.MiTAC.TRA.ATP.ui.pnlDriverMgn_btnDelDriver_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlDriverMgn_btnEditDriver_actionAdapter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

public class pnlDriverMgn extends JPanel implements RefreshFrame {
  private int _$9598 = 41;
  
  JButton btnAddDriver = new JButton();
  
  JButton btnDelDriver = new JButton();
  
  JButton btnEditDriver = new JButton();
  
  CheckUser checkUser = new CheckUser();
  
  Vector colName = new Vector();
  
  ConnectDB connDB = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  private int _$9600 = 43;
  
  DefaultSortTableModel dtm1 = new DefaultSortTableModel();
  
  private int _$9599 = 42;
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel2 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JSortTable jTable1 = new JSortTable((SortTableModel)this.dtm1);
  
  JLabel lblTitle = new JLabel();
  
  PasswordInputDialog pwdInputDialog;
  
  TitledBorder titledBorder1;
  
  TitledBorder titledBorder2;
  
  Vector vDriInfoName;
  
  Vector vDriverinfo;
  
  public pnlDriverMgn() {
    try {
      init();
      dataInit();
    } catch (SQLException sQLException) {
      JOptionPane.showMessageDialog(this, sQLException.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  public void Refresh() {}
  
  void btnAddDriver_actionPerformed(ActionEvent paramActionEvent) {
    try {
      frmDriverEdit frmDriverEdit = new frmDriverEdit((Frame)getRootPane().getParent(), ATPMessages.getString("MW.DRIVER.NEW"), true);
      frmDriverEdit.show();
      dataInit();
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      JOptionPane.showMessageDialog(this, sQLException.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnDelDriver_actionPerformed(ActionEvent paramActionEvent) {
    if (this.jTable1.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.GNL.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } else {
      this.pwdInputDialog = new PasswordInputDialog((Frame)getRootPane().getParent(), "", true);
      this.pwdInputDialog.show();
      if (!this.pwdInputDialog.isCancel())
        try {
          StringBuffer stringBuffer = new StringBuffer();
          Vector vector = new Vector();
          for (byte b = 0; b < (this.jTable1.getSelectedRows()).length; b++) {
            int j = this.jTable1.getSelectedRows()[b];
            stringBuffer.append(((Vector)this.vDriverinfo.get(j)).get(0).toString());
            stringBuffer.append("[");
            stringBuffer.append(((Vector)this.vDriverinfo.get(j)).get(1).toString());
            stringBuffer.append("],");
            vector.addElement(((Vector)this.vDriverinfo.get(j)).get(0));
          } 
          int i = JOptionPane.showConfirmDialog(getRootPane().getParent(), ATPMessages.getString("MW.DRIVER.DELETE.Q.CONFIRM") + "\n" + stringBuffer, ATPMessages.getString("MW.GNL.ASK"), 0, 3);
          if (i == 0) {
            for (byte b1 = 0; b1 < (this.jTable1.getSelectedRows()).length; b1++) {
              String str = "DELETE FROM Driver_INFO WHERE (Driver_ID='" + this.jTable1.getValueAt(this.jTable1.getSelectedRows()[b1], 0).toString() + "')";
              this.connDB.Delete(str);
            } 
            this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), "刪除司機員資料", "司機員", "刪除", this.vDriverinfo, this.jTable1.getSelectedRows(), this.colName);
            dataInit();
            JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.DRIVER.DELETE.OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
          } 
        } catch (SQLException sQLException) {
          JOptionPane.showMessageDialog(this, sQLException.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
        } catch (Exception exception) {
          JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
          exception.printStackTrace();
        }  
    } 
  }
  
  void btnEditDriver_actionPerformed(ActionEvent paramActionEvent) {
    int i = this.jTable1.getSelectedRow();
    try {
      if (this.jTable1.getSelectedRow() == -1) {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.GNL.NO_SELECTED"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      } else {
        frmDriverEdit frmDriverEdit = new frmDriverEdit(i, this.vDriverinfo, (Frame)getRootPane().getParent(), ATPMessages.getString("MW.DRIVER.MODIFY"), true);
        frmDriverEdit.show();
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
    String str = "SELECT Driver_ID,Name,Title,Hphone,Mphone,Email_Address FROM Driver_INFO";
    this.vDriverinfo = this.connDB.getData(str);
    this.dtm1.setDataVector(this.vDriverinfo, this.colName);
    switch (frmMain.getPriority()) {
    
    } 
  }
  
  void init() throws Exception {
    this.titledBorder1 = new TitledBorder("");
    this.titledBorder2 = new TitledBorder("");
    this.lblTitle.setFont(new Font("Dialog", 1, 15));
    this.lblTitle.setBorder(BorderFactory.createEtchedBorder());
    this.lblTitle.setText(ATPMessages.getString("MW.MAIN.MISSION_MGN.DIRVER_MGN"));
    setLayout(new BorderLayout());
    this.jTable1.setEnabled(true);
    this.jTable1.setBorder(BorderFactory.createLineBorder(Color.black));
    this.jTable1.setSelectionMode(0);
    this.jPanel1.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel1.setLayout(new BorderLayout());
    this.btnAddDriver.setText(ATPMessages.getString("MW.DRIVER.NEW"));
    this.btnAddDriver.addActionListener((ActionListener)new pnlDriverMgn_btnAddDriver_actionAdapter(this));
    this.btnDelDriver.setText(ATPMessages.getString("MW.DRIVER.DELETE"));
    this.btnDelDriver.addActionListener((ActionListener)new pnlDriverMgn_btnDelDriver_actionAdapter(this));
    this.btnEditDriver.setText(ATPMessages.getString("MW.DRIVER.MODIFY"));
    this.btnEditDriver.addActionListener((ActionListener)new pnlDriverMgn_btnEditDriver_actionAdapter(this));
    this.jPanel2.setLayout(new GridBagLayout());
    this.jScrollPane1.setBorder(BorderFactory.createLoweredBevelBorder());
    add(this.lblTitle, "North");
    add(this.jPanel1, "South");
    this.jPanel1.add(this.jPanel2, "North");
    this.jPanel2.add(this.btnEditDriver, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, -3, 0, 9), 0, 0));
    this.jPanel2.add(this.btnDelDriver, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 15), 0, 0));
    this.jPanel2.add(this.btnAddDriver, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 619, 0, 12), 0, 0));
    add(this.jScrollPane1, "Center");
    this.jScrollPane1.getViewport().add((Component)this.jTable1, (Object)null);
    MaskFormatter maskFormatter = new MaskFormatter("(###)###-#####");
    maskFormatter.setPlaceholderCharacter('_');
    this.colName.add(ATPMessages.getString("MW.DRIVER.ID"));
    this.colName.add(ATPMessages.getString("MW.DRIVER.NAME"));
    this.colName.add(ATPMessages.getString("MW.DRIVER.TITLE"));
    this.colName.add(ATPMessages.getString("MW.DRIVER.PHONE_HOME"));
    this.colName.add(ATPMessages.getString("MW.DRIVER.PHONE_MOBILE"));
    this.colName.add(ATPMessages.getString("MW.DRIVER.EMAIL"));
    this.btnAddDriver.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), this._$9598));
    this.btnDelDriver.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), this._$9600));
    this.btnEditDriver.setEnabled(this.checkUser.isEnable(frmMain.getPriority(), this._$9599));
  }
}
