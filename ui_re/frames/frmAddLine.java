package ui.frames;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.Tools.UpperCaseAndMaxLenghtDocument;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.adapters.frmAddLine_cancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.frmAddLine_commit_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frames.frmMain;
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
import javax.swing.text.Document;

public class frmAddLine extends JDialog {
  JButton btnCancel = new JButton();
  
  JButton btnCommit = new JButton();
  
  ConnectDB conDB = new ConnectDB();
  
  CreatMWSystemLog creatLog;
  
  Document doc = (Document)new UpperCaseAndMaxLenghtDocument(8);
  
  int index;
  
  JPanel jPanel1 = new JPanel();
  
  JLabel lbLinename = new JLabel();
  
  JLabel lbLineno = new JLabel();
  
  boolean lineInsert = true;
  
  boolean lineUpdate;
  
  JTextField txtLinename = new JTextField();
  
  JTextField txtLineno = new JTextField();
  
  Vector vecLine;
  
  public frmAddLine() {
    this(null, ATPMessages.getString("MW.LINE.NEW"), true);
  }
  
  public frmAddLine(Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    try {
      init();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void cancel_actionPerformed(ActionEvent paramActionEvent) {
    dispose();
  }
  
  void commit_actionPerformed(ActionEvent paramActionEvent) {
    try {
      String str1 = "INSERT INTO Line(LineID,LineCName) VALUES('" + this.txtLineno.getText().toUpperCase() + "','" + this.txtLinename.getText() + "')";
      this.conDB.Insert(str1);
      String str2 = this.lbLineno.getText() + ":" + this.txtLineno.getText().toUpperCase() + "," + this.lbLinename.getText() + ":" + this.txtLinename.getText();
      this.creatLog = new CreatMWSystemLog(frmMain.getUserID(), getTitle(), "列車路線", "新增", str2);
      dispose();
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void init() throws Exception {
    getContentPane().setLayout(new BorderLayout());
    this.jPanel1.setLayout(new GridBagLayout());
    this.lbLinename.setFont(new Font("Dialog", 0, 14));
    this.lbLinename.setText(ATPMessages.getString("MW.LINE.NAME"));
    this.btnCancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.btnCancel.addActionListener((ActionListener)new frmAddLine_cancel_actionAdapter(this));
    this.btnCommit.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.btnCommit.addActionListener((ActionListener)new frmAddLine_commit_actionAdapter(this));
    this.lbLineno.setFont(new Font("Dialog", 0, 14));
    this.lbLineno.setRequestFocusEnabled(true);
    this.lbLineno.setText(ATPMessages.getString("MW.LINE.ID"));
    this.txtLineno.setDocument(this.doc);
    getContentPane().add(this.jPanel1, "Center");
    this.jPanel1.add(this.lbLineno, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(10, 30, 4, 4), 0, 0));
    this.jPanel1.add(this.txtLineno, new GridBagConstraints(2, 1, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.jPanel1.add(this.lbLinename, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.jPanel1.add(this.txtLinename, new GridBagConstraints(2, 2, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 4, 4), 0, 0));
    this.jPanel1.add(this.btnCommit, new GridBagConstraints(3, 3, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 30, 4, 4), 0, 0));
    this.jPanel1.add(this.btnCancel, new GridBagConstraints(4, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 4, 4), 0, 0));
    pack();
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
}
