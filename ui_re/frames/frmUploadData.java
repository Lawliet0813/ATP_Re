package ui.frames;

import com.MiTAC.TRA.ATP.ui.frames.frmLogin;
import com.MiTAC.TRA.ATP.ui.adapters.frmUploadData_jButton2_actionAdapter;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class frmUploadData extends JFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  
  JComboBox cmboSection = new JComboBox();
  
  JButton jButton1 = new JButton();
  
  JButton jButton2 = new JButton();
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JPanel jPanel1 = new JPanel();
  
  XYLayout xYLayout1 = new XYLayout();
  
  public frmUploadData() {
    try {
      jbInit();
      this.cmboSection.addItem("嘉義機務分段");
      this.cmboSection.addItem("高雄機務段");
      this.cmboSection.addItem("台北機務段");
      this.cmboSection.addItem("花蓮機務段");
      this.cmboSection.addItem("新竹機務分段");
      this.cmboSection.addItem("台中機務分段");
      this.cmboSection.addItem("七堵機務段");
      this.cmboSection.addItem("台東機務分段");
      this.cmboSection.addItem("樹林機務分段");
      this.cmboSection.addItem("宜蘭機務分段");
      this.cmboSection.addItem("苗栗機務分駐所");
      this.cmboSection.addItem("彰化機務段");
      this.cmboSection.addItem("台北機場");
      this.cmboSection.addItem("花蓮機場");
      this.cmboSection.addItem("資訊中心");
      this.cmboSection.addItem("機務處");
      this.cmboSection.addItem("基隆機務分駐所");
      this.cmboSection.addItem("二水機務分駐所");
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void jButton2_actionPerformed(ActionEvent paramActionEvent) {
    frmLogin frmLogin = new frmLogin();
    frmLogin.show();
    dispose();
  }
  
  void jbInit() throws Exception {
    setTitle("上傳行車料");
    setSize(350, 250);
    this.jLabel1.setFont(new Font("Dialog", 1, 15));
    this.jLabel1.setBorder(BorderFactory.createEtchedBorder());
    this.jLabel1.setText("上傳行車資料");
    getContentPane().setLayout(this.borderLayout1);
    this.jPanel1.setLayout((LayoutManager)this.xYLayout1);
    this.jLabel2.setText("請選擇所屬機務段");
    this.jButton1.setToolTipText("");
    this.jButton1.setText("確認");
    this.jButton2.setText("取消");
    this.jButton2.addActionListener((ActionListener)new frmUploadData_jButton2_actionAdapter(this));
    getContentPane().add(this.jLabel1, "North");
    getContentPane().add(this.jPanel1, "Center");
    this.jPanel1.add(this.jLabel2, new XYConstraints(73, 46, -1, -1));
    this.jPanel1.add(this.cmboSection, new XYConstraints(86, 63, 147, -1));
    this.jPanel1.add(this.jButton1, new XYConstraints(165, 138, -1, -1));
    this.jPanel1.add(this.jButton2, new XYConstraints(239, 138, -1, -1));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
}
