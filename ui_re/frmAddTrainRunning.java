package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.frmAddTrainRunning_cancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmAddTrainRunning_commit_actionAdapter;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class frmAddTrainRunning extends JDialog {
  JButton cancel = new JButton();
  
  JButton commit = new JButton();
  
  ConnectDB connDB = new ConnectDB();
  
  String index;
  
  boolean insert = false;
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JTextField jTextField1 = new JTextField();
  
  JTextField jTextField2 = new JTextField();
  
  boolean update = false;
  
  XYLayout xYLayout1 = new XYLayout();
  
  public frmAddTrainRunning(int paramInt, Vector paramVector, Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    this.update = true;
    this.index = ((Vector)paramVector.get(paramInt)).get(0);
    try {
      jbInit();
      pack();
      this.jTextField1.setText(((Vector)paramVector.get(paramInt)).get(0));
      this.jTextField2.setText(((Vector)paramVector.get(paramInt)).get(1));
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public frmAddTrainRunning(Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    this.insert = true;
    try {
      jbInit();
      pack();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void cancel_actionPerformed(ActionEvent paramActionEvent) {
    dispose();
  }
  
  void commit_actionPerformed(ActionEvent paramActionEvent) {
    try {
      if (this.insert) {
        String str = "INSERT INTO TrainRunning VALUES('" + this.jTextField1.getText() + "','" + this.jTextField2.getText() + "')";
        this.connDB.Insert(str);
      } 
      if (this.update) {
        String str = "UPDATE TrainRunning SET TRNo='" + this.jTextField1.getText() + "',TRName='" + this.jTextField2.getText() + "' WHERE TRNo='" + this.index + "'";
        this.connDB.Update(str);
      } 
      dispose();
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.Error"), 0);
    } 
  }
  
  void jbInit() throws Exception {
    this.jLabel1.setFont(new Font("Dialog", 0, 14));
    this.jLabel1.setText(ATPMessages.getString("MW.TrainRunningNumber"));
    getContentPane().setLayout((LayoutManager)this.xYLayout1);
    this.jLabel2.setFont(new Font("Dialog", 0, 14));
    this.jLabel2.setText(ATPMessages.getString("MW.TrainRunningName"));
    this.commit.setText(ATPMessages.getString("MW.Confirm"));
    this.commit.addActionListener((ActionListener)new frmAddTrainRunning_commit_actionAdapter(this));
    this.cancel.setText(ATPMessages.getString("MW.Cancel"));
    this.cancel.addActionListener((ActionListener)new frmAddTrainRunning_cancel_actionAdapter(this));
    this.jTextField1.setText("");
    this.jTextField2.setText("");
    getContentPane().add(this.jLabel1, new XYConstraints(30, 10, 60, 20));
    getContentPane().add(this.jLabel2, new XYConstraints(30, 50, 60, 20));
    getContentPane().add(this.jTextField1, new XYConstraints(100, 5, 80, 30));
    getContentPane().add(this.jTextField2, new XYConstraints(100, 45, 80, 30));
    getContentPane().add(this.commit, new XYConstraints(150, 100, -1, -1));
    getContentPane().add(this.cancel, new XYConstraints(220, 100, -1, -1));
    setSize(new Dimension(300, 200));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
}
