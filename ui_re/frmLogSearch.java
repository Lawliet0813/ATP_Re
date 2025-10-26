package ui;

import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.frmLogSearch_btnConfirm_actionAdapter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class frmLogSearch extends JFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  
  JButton btnConfirm = new JButton();
  
  DefaultTableModel dtm = new DefaultTableModel();
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
  JLabel jLabel4 = new JLabel();
  
  JPanel jPanel1 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JTable jTable1 = new JTable(this.dtm);
  
  JTextField txtDIDNo = new JTextField();
  
  JTextField txtRunningDate = new JTextField();
  
  JTextField txtTRNo = new JTextField();
  
  JTextField txtWSNo = new JTextField();
  
  public frmLogSearch() {
    try {
      jbInit();
      this.txtWSNo.setText("");
      this.txtWSNo.setPreferredSize(new Dimension(80, 27));
      this.txtTRNo.setText("");
      this.txtTRNo.setPreferredSize(new Dimension(80, 27));
      this.txtDIDNo.setText("");
      this.txtDIDNo.setPreferredSize(new Dimension(80, 27));
      this.txtRunningDate.setText("");
      this.txtRunningDate.setPreferredSize(new Dimension(80, 27));
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnConfirm_actionPerformed(ActionEvent paramActionEvent) {
    try {
      ConnectDB connectDB = new ConnectDB();
      Vector vector = new Vector();
      vector.add("執行日期");
      vector.add("工作班號");
      vector.add("車次");
      vector.add("司機員編號");
      vector.add("MO光碟片編號");
      vector.add("備份日期");
      _$29844();
      String str = _$15545();
      Vector vector1 = connectDB.getData(str);
      this.dtm.setDataVector(vector1, vector);
      repaint();
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), "錯誤", 2);
    } 
  }
  
  void jbInit() throws Exception {
    getContentPane().setLayout(this.borderLayout1);
    this.jLabel1.setText("工作班號:");
    this.txtWSNo.setText("jTextField1");
    this.jLabel2.setText("車次:");
    this.jLabel3.setText("司機員ID");
    this.txtTRNo.setText("jTextField2");
    this.txtDIDNo.setText("jTextField3");
    this.jLabel4.setText("執行日期:");
    this.txtRunningDate.setText("jTextField1");
    this.btnConfirm.setText("搜尋");
    this.btnConfirm.addActionListener((ActionListener)new frmLogSearch_btnConfirm_actionAdapter(this));
    setDefaultCloseOperation(1);
    setLocale(Locale.getDefault());
    setState(0);
    setTitle("行車記錄檔檢索");
    getContentPane().add(this.jPanel1, "North");
    this.jPanel1.add(this.jLabel1, (Object)null);
    this.jPanel1.add(this.txtWSNo, (Object)null);
    this.jPanel1.add(this.jLabel2, (Object)null);
    this.jPanel1.add(this.txtTRNo, (Object)null);
    this.jPanel1.add(this.jLabel3, (Object)null);
    this.jPanel1.add(this.txtDIDNo, (Object)null);
    this.jPanel1.add(this.jLabel4, (Object)null);
    this.jPanel1.add(this.txtRunningDate, (Object)null);
    this.jPanel1.add(this.btnConfirm, (Object)null);
    getContentPane().add(this.jScrollPane1, "Center");
    this.jScrollPane1.getViewport().add(this.jTable1, (Object)null);
    setSize(800, 600);
  }
  
  private String _$15545() {
    String str = "select RunningDate, WSNo, TRNo, DriverID ,DiskNo, BackupDate From LogBackupLibrary Where";
    if (this.txtWSNo.getText().length() != 0) {
      str = str + " WSNo = '" + this.txtWSNo.getText() + "'";
    } else {
      str = str + " WSNo is not null";
    } 
    if (this.txtTRNo.getText().length() != 0)
      str = str + " and TRNo = '" + this.txtTRNo.getText() + "'"; 
    if (this.txtRunningDate.getText().length() != 0)
      str = str + " and RunningDate = '" + this.txtRunningDate.getText() + "'"; 
    if (this.txtDIDNo.getText().length() != 0)
      str = str + " and DriverID = '" + this.txtDIDNo.getText() + "'"; 
    System.err.println(str);
    return str;
  }
  
  private void _$29844() throws Exception {
    if (this.txtWSNo.getText().length() == 0 && this.txtTRNo.getText().length() == 0 && this.txtDIDNo.getText().length() == 0 && this.txtRunningDate.getText().length() == 0)
      throw new Exception("請輸入搜尋條件"); 
  }
}
