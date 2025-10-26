package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.ui.setTime_jButton1_actionAdapter;
import com.MiTAC.TRA.ATP.ui.setTime_jButton2_actionAdapter;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class dlgsetTime extends JDialog {
  JComboBox arrivalHr = new JComboBox();
  
  JComboBox arrivalMin = new JComboBox();
  
  JComboBox arrivalSec = new JComboBox();
  
  String arrivetime;
  
  JButton jButton1 = new JButton();
  
  JButton jButton2 = new JButton();
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
  JLabel jLabel4 = new JLabel();
  
  JLabel jLabel5 = new JLabel();
  
  JLabel jLabel6 = new JLabel();
  
  JComboBox leaveHr = new JComboBox();
  
  JComboBox leaveMin = new JComboBox();
  
  JComboBox leaveSec = new JComboBox();
  
  String leavetime;
  
  JPanel panel1 = new JPanel();
  
  XYLayout xYLayout1 = new XYLayout();
  
  public dlgsetTime(Frame paramFrame, String paramString, boolean paramBoolean) {
    super(paramFrame, paramString, paramBoolean);
    try {
      _$12828();
      pack();
      _$29951();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$29951() {
    for (byte b1 = 0; b1 < 24; b1++) {
      this.arrivalHr.addItem(String.valueOf(b1));
      this.leaveHr.addItem(String.valueOf(b1));
    } 
    for (byte b2 = 0; b2 < 60; b2++) {
      this.arrivalMin.addItem(String.valueOf(b2));
      this.leaveMin.addItem(String.valueOf(b2));
    } 
    for (byte b3 = 0; b3 < 60; b3++) {
      this.arrivalSec.addItem(String.valueOf(b3));
      this.leaveSec.addItem(String.valueOf(b3));
    } 
  }
  
  public String getarriveTime() {
    return this.arrivetime;
  }
  
  public String getleaveTime() {
    return this.leavetime;
  }
  
  void jButton1_actionPerformed(ActionEvent paramActionEvent) {
    this.arrivetime = this.arrivalHr.getSelectedItem().toString() + ":" + this.arrivalMin.getSelectedItem().toString() + ":" + this.arrivalSec.getSelectedItem();
    this.leavetime = this.leaveHr.getSelectedItem().toString() + ":" + this.leaveMin.getSelectedItem().toString() + ":" + this.leaveSec.getSelectedItem().toString();
    dispose();
  }
  
  void jButton2_actionPerformed(ActionEvent paramActionEvent) {
    dispose();
  }
  
  private void _$12828() throws Exception {
    this.panel1.setLayout((LayoutManager)this.xYLayout1);
    this.jLabel1.setFont(new Font("Dialog", 0, 14));
    this.jLabel1.setText(ATPMessages.getString("MW.ArriveTime"));
    this.jLabel2.setText(ATPMessages.getString("MW.LeaveTime"));
    this.jLabel2.setFont(new Font("Dialog", 0, 14));
    this.jButton1.setText(ATPMessages.getString("MW.Confirm"));
    this.jButton1.addActionListener((ActionListener)new setTime_jButton1_actionAdapter(this));
    this.jButton2.addActionListener((ActionListener)new setTime_jButton2_actionAdapter(this));
    this.jButton2.setText(ATPMessages.getString("MW.Cancel"));
    this.jButton2.addActionListener((ActionListener)new setTime_jButton2_actionAdapter(this));
    this.jLabel3.setHorizontalAlignment(0);
    this.jLabel3.setText("：");
    this.jLabel4.setText("：");
    this.jLabel4.setHorizontalAlignment(0);
    this.jLabel5.setHorizontalAlignment(0);
    this.jLabel5.setText("：");
    this.jLabel6.setText("：");
    this.jLabel6.setHorizontalAlignment(0);
    getContentPane().add(this.panel1);
    this.panel1.add(this.jLabel1, new XYConstraints(30, 40, -1, -1));
    this.panel1.add(this.jLabel2, new XYConstraints(30, 70, -1, -1));
    this.panel1.add(this.jButton1, new XYConstraints(200, 130, -1, -1));
    this.panel1.add(this.arrivalHr, new XYConstraints(100, 40, 50, -1));
    this.panel1.add(this.jButton2, new XYConstraints(200, 100, -1, -1));
    this.panel1.add(this.arrivalMin, new XYConstraints(165, 40, 50, -1));
    this.panel1.add(this.jLabel3, new XYConstraints(150, 40, 15, 20));
    this.panel1.add(this.jLabel4, new XYConstraints(215, 40, 15, 20));
    this.panel1.add(this.arrivalSec, new XYConstraints(230, 40, 50, -1));
    this.panel1.add(this.leaveHr, new XYConstraints(100, 70, 50, -1));
    this.panel1.add(this.jLabel6, new XYConstraints(150, 70, 15, 20));
    this.panel1.add(this.leaveMin, new XYConstraints(165, 70, 50, -1));
    this.panel1.add(this.jLabel5, new XYConstraints(215, 70, 15, 20));
    this.panel1.add(this.leaveSec, new XYConstraints(230, 70, 50, -1));
    setSize(new Dimension(300, 200));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
}
