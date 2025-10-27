package ui.panels;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.ui.adapters.pnlParaSQL_btnTestCon_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlParaSQL_txtField_focusAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlParaSQL_txtField_keyAdapter;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class pnlParaSQL extends JPanel {
  private String _$17679;
  
  private String _$17681;
  
  private String _$17680;
  
  JButton btnTestCon = new JButton();
  
  boolean changes = false;
  
  JLabel lblDB = new JLabel();
  
  JLabel lblDBPwd = new JLabel();
  
  JLabel lblDBTitle = new JLabel();
  
  JLabel lblDBUser = new JLabel();
  
  private String _$17682;
  
  private String _$17684;
  
  private String _$17683;
  
  private static final long serialVersionUID = 1L;
  
  JTextField txtDBName = new JTextField();
  
  JPasswordField txtDBPwd = new JPasswordField();
  
  JTextField txtDBUser = new JTextField();
  
  XYLayout xYLayout1 = new XYLayout();
  
  public pnlParaSQL() {
    try {
      init();
      callbackValue();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnTestCon_actionPerformed(ActionEvent paramActionEvent) {
    Connection connection = null;
    try {
      Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
      connection = DriverManager.getConnection("jdbc:microsoft:sqlserver://" + this._$17682 + ":1433", this._$17683, this._$17684);
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.PARA.DB.TEST_CONN.OK"), ATPMessages.getString("MW.GNL.INFO"), 1);
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.PARA.DB.TEST_CONN.FAILURE"), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } finally {
      try {
        if (!connection.isClosed())
          connection.close(); 
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } 
  }
  
  protected void callbackValue() {
    this._$17679 = InitParameters.SQLName;
    this._$17680 = InitParameters.SQLUserName;
    this._$17681 = InitParameters.SQLUserPWD;
    this.txtDBName.setText(this._$17679);
    this.txtDBUser.setText(this._$17680);
    this.txtDBPwd.setText(this._$17681);
    this._$17682 = this._$17679;
    this._$17683 = this._$17680;
    this._$17684 = this._$17681;
  }
  
  public Object[] getValues() {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this._$17682;
    arrayOfObject[1] = this._$17683;
    arrayOfObject[2] = this._$17684;
    return arrayOfObject;
  }
  
  void init() throws Exception {
    this.btnTestCon.setText(ATPMessages.getString("MW.PARA.DB.TEST_CONN"));
    this.btnTestCon.addActionListener((ActionListener)new pnlParaSQL_btnTestCon_actionAdapter(this));
    this.lblDBTitle.setText(ATPMessages.getString("MW.PARA.DB.SETTING"));
    this.lblDBTitle.setFont(new Font("Dialog", 1, 14));
    this.lblDBUser.setText(ATPMessages.getString("MW.PARA.DB.USER"));
    this.lblDB.setText(ATPMessages.getString("MW.PARA.DB.DB"));
    this.lblDBPwd.setText(ATPMessages.getString("MW.PARA.DB.PWD"));
    this.txtDBName.setNextFocusableComponent(this.txtDBUser);
    this.txtDBName.setText("jTextField1");
    this.txtDBUser.setNextFocusableComponent(this.txtDBPwd);
    this.txtDBUser.setText("jTextField2");
    this.txtDBPwd.setNextFocusableComponent(this.btnTestCon);
    this.txtDBPwd.setText("jPasswordField1");
    this.txtDBName.addFocusListener((FocusListener)new pnlParaSQL_txtField_focusAdapter(this));
    this.txtDBUser.addFocusListener((FocusListener)new pnlParaSQL_txtField_focusAdapter(this));
    this.txtDBPwd.addFocusListener((FocusListener)new pnlParaSQL_txtField_focusAdapter(this));
    this.txtDBName.addKeyListener((KeyListener)new pnlParaSQL_txtField_keyAdapter(this));
    this.txtDBUser.addKeyListener((KeyListener)new pnlParaSQL_txtField_keyAdapter(this));
    this.txtDBPwd.addKeyListener((KeyListener)new pnlParaSQL_txtField_keyAdapter(this));
    setLayout((LayoutManager)this.xYLayout1);
    setPreferredSize(new Dimension(404, 418));
    add(this.lblDBTitle, new XYConstraints(33, 11, -1, -1));
    add(this.txtDBName, new XYConstraints(133, 55, 99, -1));
    add(this.txtDBPwd, new XYConstraints(133, 135, 99, -1));
    add(this.txtDBUser, new XYConstraints(133, 95, 99, -1));
    add(this.lblDB, new XYConstraints(44, 55, -1, -1));
    add(this.lblDBUser, new XYConstraints(44, 95, -1, -1));
    add(this.lblDBPwd, new XYConstraints(44, 135, -1, -1));
    add(this.btnTestCon, new XYConstraints(46, 179, -1, -1));
  }
  
  public boolean isChanged() {
    return this.changes;
  }
  
  private void _$8475() {
    this.changes = true;
    this._$17682 = this.txtDBName.getText();
    this._$17683 = this.txtDBUser.getText();
    this._$17684 = this.txtDBPwd.getText();
  }
  
  void txtField_focusLost(FocusEvent paramFocusEvent) {
    _$8475();
  }
  
  void txtField_keyPressed(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyCode() == 10) {
      _$8475();
      ((Component)paramKeyEvent.getSource()).transferFocus();
    } 
  }
}
