package Tools;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CheckUser;
import com.MiTAC.TRA.ATP.Tools.PasswordInputDialog_btnCancel_actionAdapter;
import com.MiTAC.TRA.ATP.Tools.PasswordInputDialog_btnCommit_actionAdapter;
import com.MiTAC.TRA.ATP.Tools.PasswordInputDialog_jPasswordField1_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmMain;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class PasswordInputDialog extends JDialog {
  JButton btnCancel = new JButton();
  
  JButton btnCommit = new JButton();
  
  private boolean _$4256;
  
  CheckUser checkUser = new CheckUser();
  
  JLabel jLabel1 = new JLabel();
  
  JPasswordField jPasswordField1 = new JPasswordField();
  
  XYLayout xYLayout1 = new XYLayout();
  
  public PasswordInputDialog(Dialog paramDialog, String paramString, boolean paramBoolean) {
    super(paramDialog, paramString, paramBoolean);
    try {
      _$12828();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public PasswordInputDialog(Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    try {
      _$12828();
      setTitle(ATPMessages.getString("MW.PWD.PWD_CONFIRM"));
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public PasswordInputDialog() {
    try {
      _$12828();
      setTitle(ATPMessages.getString("MW.PWD.PWD_CONFIRM"));
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnCancel_actionPerformed(ActionEvent paramActionEvent) {
    this._$4256 = true;
    dispose();
  }
  
  void btnCommit_actionPerformed(ActionEvent paramActionEvent) {
    if (this.jPasswordField1.getText().length() == 0) {
      JOptionPane.showMessageDialog(this, "密碼欄位不可是空白的!", ATPMessages.getString("MW.GNL.ERROR"), 0);
      this.jPasswordField1.requestFocusInWindow();
    } else if ((new CheckUser(frmMain.getUserID(), getPassword())).isPasswordRight()) {
      getPassword();
      dispose();
    } else {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.PD.PASSWORD.ERROR"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      this.jPasswordField1.setText("");
      this.jPasswordField1.requestFocusInWindow();
    } 
  }
  
  public String getPassword() {
    return this.jPasswordField1.getText();
  }
  
  public boolean isCancel() {
    return this._$4256;
  }
  
  void jPasswordField1_actionPerformed(ActionEvent paramActionEvent) {
    ((Component)paramActionEvent.getSource()).transferFocus();
  }
  
  private void _$12828() throws Exception {
    setFont(new Font("Dialog", 0, 12));
    this.jLabel1.setFont(new Font("Dialog", 0, 12));
    this.jLabel1.setText(ATPMessages.getString("MW.PD.PASSWORD_REQUEST"));
    getContentPane().setLayout((LayoutManager)this.xYLayout1);
    this.jPasswordField1.setText("");
    this.jPasswordField1.addActionListener((ActionListener)new PasswordInputDialog_jPasswordField1_actionAdapter(this));
    this.btnCommit.setFont(new Font("Dialog", 0, 12));
    this.btnCommit.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.btnCommit.addActionListener((ActionListener)new PasswordInputDialog_btnCommit_actionAdapter(this));
    this.btnCancel.setFont(new Font("Dialog", 0, 12));
    this.btnCancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.btnCancel.addActionListener((ActionListener)new PasswordInputDialog_btnCancel_actionAdapter(this));
    getContentPane().add(this.jLabel1, new XYConstraints(50, 5, 100, 25));
    getContentPane().add(this.jPasswordField1, new XYConstraints(50, 30, 150, 25));
    getContentPane().add(this.btnCommit, new XYConstraints(50, 65, 60, -1));
    getContentPane().add(this.btnCancel, new XYConstraints(130, 65, 60, -1));
    setSize(new Dimension(270, 125));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
  
  public static void main(String[] paramArrayOfString) {
    (new com.MiTAC.TRA.ATP.Tools.PasswordInputDialog()).show();
  }
}
