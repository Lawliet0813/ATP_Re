package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.frmLineEdit_btnCommit_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmLineEdit_btnRollBack_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmMain;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class frmLineEdit extends JDialog {
  ConnectDB Connect = new ConnectDB();
  
  JButton btnCommit = new JButton();
  
  JButton btnRollBack = new JButton();
  
  int cmboIndex = 0;
  
  CreatMWSystemLog creatLog;
  
  boolean editLineStat = true;
  
  int indexNo;
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel5 = new JLabel();
  
  JPanel jPanel = new JPanel();
  
  String lineID;
  
  JTextField txtLinename = new JTextField();
  
  JTextField txtLineno = new JTextField();
  
  Vector vLinetemp = new Vector();
  
  Vector vecLine;
  
  public frmLineEdit(int paramInt, Vector paramVector, Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    try {
      init();
      this.vecLine = paramVector;
      this.indexNo = paramInt;
      this.txtLineno.setText(((Vector)this.vecLine.get(this.indexNo)).get(0));
      this.txtLinename.setText(((Vector)this.vecLine.get(this.indexNo)).get(1));
      this.lineID = ((Vector)this.vecLine.get(this.indexNo)).get(0);
      this.editLineStat = false;
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnCommit_actionPerformed(ActionEvent paramActionEvent) {
    try {
      String str1 = "UPDATE Line SET LineID='" + this.txtLineno.getText().toUpperCase() + "',LineCName='" + this.txtLinename.getText() + "' WHERE LineID='" + this.lineID + "'";
      this.Connect.Update(str1);
      String str2 = this.jLabel5.getText() + ":" + this.txtLineno.getText().toUpperCase() + "," + this.jLabel2.getText() + ":" + this.txtLinename.getText();
      this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "列車路線", "修改", str2);
      dispose();
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnRollBack_actionPerformed(ActionEvent paramActionEvent) {
    int i = JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.LINE.MODIFY.Q.CANCEL"), ATPMessages.getString("MW.GNL.ASK"), 0, 3);
    if (i == 0)
      dispose(); 
  }
  
  void init() throws Exception {
    getContentPane().setLayout(new BorderLayout());
    this.jPanel.setLayout(new GridBagLayout());
    this.jLabel2.setFont(new Font("", 0, 14));
    this.jLabel2.setText(ATPMessages.getString("MW.LINE.NAME"));
    this.btnCommit.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.btnCommit.addActionListener((ActionListener)new frmLineEdit_btnCommit_actionAdapter(this));
    this.btnRollBack.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.btnRollBack.addActionListener((ActionListener)new frmLineEdit_btnRollBack_actionAdapter(this));
    this.jLabel5.setText(ATPMessages.getString("MW.LINE.ID"));
    this.jLabel5.setFont(new Font("", 0, 14));
    this.txtLineno.setText("");
    this.txtLinename.setVisible(true);
    this.txtLinename.setText("");
    this.jPanel.add(this.jLabel5, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(10, 30, 4, 4), 0, 0));
    this.jPanel.add(this.txtLineno, new GridBagConstraints(2, 1, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.jPanel.add(this.jLabel2, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.jPanel.add(this.txtLinename, new GridBagConstraints(2, 2, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.jPanel.add(this.btnCommit, new GridBagConstraints(3, 3, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.jPanel.add(this.btnRollBack, new GridBagConstraints(4, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 4, 4), 0, 0));
    getContentPane().add(this.jPanel, "Center");
    pack();
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
    setResizable(false);
  }
}
