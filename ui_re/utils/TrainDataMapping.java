package ui.utils;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.SortTable.MultiLineCellRenderer;
import com.MiTAC.TRA.ATP.Tools.SortTable.MultiLineHeaderRenderer;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.adapters.TrainDataMapping_cancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.TrainDataMapping_confirm_actionAdapter;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class TrainDataMapping extends JDialog {
  BorderLayout borderLayout1 = new BorderLayout();
  
  BorderLayout borderLayout2 = new BorderLayout();
  
  JButton cancel = new JButton();
  
  JButton confirm = new JButton();
  
  ConnectDB connDB = new ConnectDB();
  
  DefaultTableModel dtm1 = new DefaultTableModel();
  
  DefaultTableModel dtm2 = new DefaultTableModel();
  
  private boolean _$21425;
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
  JLabel jLabel4 = new JLabel();
  
  JLabel jLabel5 = new JLabel();
  
  JLabel jLabel6 = new JLabel();
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel2 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JScrollPane jScrollPane2 = new JScrollPane();
  
  JTable jTable1 = new JTable(this.dtm1);
  
  JTable jTable2 = new JTable(this.dtm2);
  
  private Vector _$25399 = new Vector();
  
  private Vector _$25400 = new Vector();
  
  XYLayout xYLayout1 = new XYLayout();
  
  public TrainDataMapping(String paramString1, String paramString2, String paramString3, String paramString4, Frame paramFrame, String paramString5, boolean paramBoolean) {
    super(paramFrame, paramString5, paramBoolean);
    try {
      jbInit();
      pack();
      _$17039(paramString1);
      _$15799(paramString2);
      this.jLabel4.setText(paramString3);
      this.jLabel6.setText(paramString4);
      this.jLabel5.setVisible(false);
      this.jLabel6.setVisible(false);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public TrainDataMapping() {
    try {
      jbInit();
      this.jLabel5.setVisible(false);
      this.jLabel6.setVisible(false);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void cancel_actionPerformed(ActionEvent paramActionEvent) {
    dispose();
  }
  
  void confirm_actionPerformed(ActionEvent paramActionEvent) {
    this._$21425 = true;
    dispose();
  }
  
  public boolean isTrue() {
    return this._$21425;
  }
  
  void jbInit() throws Exception {
    getContentPane().setLayout((LayoutManager)this.xYLayout1);
    this.jPanel1.setBorder(BorderFactory.createLineBorder(Color.black));
    this.jPanel1.setLayout(this.borderLayout1);
    this.jPanel2.setBorder(BorderFactory.createLineBorder(Color.black));
    this.jPanel2.setLayout(this.borderLayout2);
    this.jLabel1.setBorder(BorderFactory.createLineBorder(Color.black));
    this.jLabel1.setText(ATPMessages.getString("MW.TT"));
    this.jLabel1.setFont(new Font(ATPMessages.getString("MW.TT"), 0, 16));
    this.jLabel1.setOpaque(true);
    this.jLabel1.setBackground(Color.YELLOW);
    this.jLabel2.setBorder(BorderFactory.createLineBorder(Color.black));
    this.jLabel2.setText(ATPMessages.getString("MW.TD"));
    this.jLabel2.setFont(new Font(ATPMessages.getString("MW.TD"), 0, 16));
    this.jLabel2.setOpaque(true);
    this.jLabel2.setBackground(Color.YELLOW);
    this.confirm.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.confirm.setFont(new Font(ATPMessages.getString("MW.GNL.CONFIRM"), 0, 16));
    this.confirm.setMargin(new Insets(2, 2, 2, 2));
    this.confirm.addActionListener((ActionListener)new TrainDataMapping_confirm_actionAdapter(this));
    this.cancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.cancel.setFont(new Font(ATPMessages.getString("MW.GNL.CANCEL"), 0, 16));
    this.cancel.setMargin(new Insets(2, 2, 2, 2));
    this.cancel.addActionListener((ActionListener)new TrainDataMapping_cancel_actionAdapter(this));
    this.jLabel3.setBorder(BorderFactory.createLineBorder(Color.black));
    this.jLabel3.setText(ATPMessages.getString("MW.WS.WORKDATE"));
    this.jLabel3.setFont(new Font(ATPMessages.getString("MW.WS.WORKDATE"), 0, 16));
    this.jLabel3.setOpaque(true);
    this.jLabel3.setBackground(Color.YELLOW);
    this.jLabel4.setBackground(Color.white);
    this.jLabel4.setOpaque(true);
    this.jLabel4.setText("");
    this.jLabel4.setFont(new Font("", 1, 16));
    this.jLabel5.setBorder(BorderFactory.createLineBorder(Color.black));
    this.jLabel5.setText(ATPMessages.getString("MW.VEHICLE.ID"));
    this.jLabel6.setBackground(Color.white);
    this.jLabel6.setOpaque(true);
    this.jLabel6.setText("");
    getContentPane().add(this.jPanel1, new XYConstraints(10, 50, 880, 100));
    this.jPanel1.add(this.jScrollPane1, "Center");
    this.jTable1.setAutoResizeMode(0);
    this.jTable1.setDefaultRenderer(String.class, (TableCellRenderer)new MultiLineCellRenderer());
    this.jTable1.getTableHeader().setDefaultRenderer((TableCellRenderer)new MultiLineHeaderRenderer());
    this.jTable1.setFont(new Font("新細明體", 0, 16));
    this.jScrollPane1.setFont(new Font("新細明體", 0, 14));
    this.jScrollPane1.getViewport().add(this.jTable1, (Object)null);
    getContentPane().add(this.jLabel1, new XYConstraints(10, 20, -1, -1));
    getContentPane().add(this.jLabel2, new XYConstraints(10, 170, -1, -1));
    getContentPane().add(this.jPanel2, new XYConstraints(10, 200, 880, 100));
    this.jPanel2.add(this.jScrollPane2, "Center");
    this.jTable2.setAutoResizeMode(0);
    this.jTable2.setDefaultRenderer(String.class, (TableCellRenderer)new MultiLineCellRenderer());
    this.jTable2.getTableHeader().setDefaultRenderer((TableCellRenderer)new MultiLineHeaderRenderer());
    getContentPane().add(this.confirm, new XYConstraints(780, 350, 46, -1));
    getContentPane().add(this.cancel, new XYConstraints(840, 350, 46, -1));
    getContentPane().add(this.jLabel3, new XYConstraints(10, 320, 130, -1));
    getContentPane().add(this.jLabel4, new XYConstraints(150, 320, -1, -1));
    getContentPane().add(this.jLabel5, new XYConstraints(10, 250, 60, 20));
    getContentPane().add(this.jLabel6, new XYConstraints(80, 250, 100, 20));
    this.jTable2.setFont(new Font("新細明體", 0, 16));
    this.jScrollPane2.setFont(new Font("新細明體", 0, 14));
    this.jScrollPane2.getViewport().add(this.jTable2, (Object)null);
    this._$25399.addElement(ATPMessages.getString("MW.TT.ID"));
    this._$25399.addElement(ATPMessages.getString("MW.TT.NAME"));
    this._$25399.addElement(ATPMessages.getString("MW.TT.MAX_SPEED(Km/hr)"));
    this._$25399.addElement(ATPMessages.getString("MW.TT.OVER_SPEED_AT_TSR(Km/hr)"));
    this._$25399.addElement(ATPMessages.getString("MW.TT.ALLOW_OVER_SPEED_AT_TSR"));
    this._$25399.addElement(ATPMessages.getString("MW.TT.OVER_SPEED_AT_CURVES(Km/hr)"));
    this._$25399.addElement(ATPMessages.getString("MW.TT.GRADIENTS0(Km/hr)"));
    this._$25399.addElement(ATPMessages.getString("MW.TT.GRADIENTS5(Km/hr)"));
    this._$25399.addElement(ATPMessages.getString("MW.TT.GRADIENTS10(Km/hr)"));
    this._$25399.addElement(ATPMessages.getString("MW.TT.GRADIENTS15(Km/hr)"));
    this._$25399.addElement(ATPMessages.getString("MW.TT.GRADIENTS20(Km/hr)"));
    this._$25400.add(ATPMessages.getString("MW.TD.ID"));
    this._$25400.add(ATPMessages.getString("MW.TD.NAME"));
    this._$25400.add(ATPMessages.getString("MW.TD.LENGTH(m)"));
    this._$25400.add(ATPMessages.getString("MW.TD.WEIGHT_EMPTY(ton)"));
    this._$25400.add(ATPMessages.getString("MW.TD.WEIGHT_FULL(ton)"));
    this._$25400.add(ATPMessages.getString("MW.TD.MAX_ACCELERATION(m/s/s)"));
    this._$25400.add(ATPMessages.getString("MW.TD.SB_DELAY_TIME(s)"));
    this._$25400.add(ATPMessages.getString("MW.TD.SB_DECELERATION(m/s/s)"));
    this._$25400.add(ATPMessages.getString("MW.TD.EB_DELAY_TIME(s)"));
    this._$25400.add(ATPMessages.getString("MW.TD.EB_DECELERATION(m/s/s)"));
    this._$25400.add(ATPMessages.getString("MW.TD.MAX_DECELERATION(m/s/s)"));
    setSize(new Dimension(900, 500));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
  
  private void _$17039(String paramString) {
    try {
      String str = "SELECT Train_Type,Train_Type_Name,MaxSpeed,OverSpeed_at_TSR,AllowOverSpeed_at_TSR,OverSpeed_at_Curves,Gradient0,Gradient5,Gradient10,Gradient15,Gradient20 FROM Train_Type WHERE Train_Type ='" + paramString + "'";
      Vector vector = this.connDB.getData(str);
      this.dtm1.setDataVector(vector, this._$25399);
      this.jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
      this.jTable1.getColumnModel().getColumn(1).setPreferredWidth(260);
      this.jTable1.getColumnModel().getColumn(2).setPreferredWidth(50);
      this.jTable1.getColumnModel().getColumn(3).setPreferredWidth(50);
      this.jTable1.getColumnModel().getColumn(4).setPreferredWidth(50);
      this.jTable1.getColumnModel().getColumn(5).setPreferredWidth(50);
      this.jTable1.getColumnModel().getColumn(6).setPreferredWidth(50);
      this.jTable1.getColumnModel().getColumn(7).setPreferredWidth(50);
      this.jTable1.getColumnModel().getColumn(8).setPreferredWidth(50);
      this.jTable1.getColumnModel().getColumn(9).setPreferredWidth(50);
      this.jTable1.getColumnModel().getColumn(10).setPreferredWidth(50);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$15799(String paramString) {
    try {
      String str = "SELECT Train_SN,Train_Kind,Length,Weight_Empty,Weight_Full,Max_Acceleration,SB_delay_time,SB_deceleration,EB_delay_time,EB_deceleration,Max_Deceleration FROM Train_Data WHERE Train_SN ='" + paramString + "'";
      Vector vector = this.connDB.getData(str);
      this.dtm2.setDataVector(vector, this._$25400);
      this.jTable2.getColumnModel().getColumn(0).setPreferredWidth(50);
      this.jTable2.getColumnModel().getColumn(1).setPreferredWidth(390);
      this.jTable2.getColumnModel().getColumn(2).setPreferredWidth(45);
      this.jTable2.getColumnModel().getColumn(3).setPreferredWidth(45);
      this.jTable2.getColumnModel().getColumn(4).setPreferredWidth(45);
      this.jTable2.getColumnModel().getColumn(5).setPreferredWidth(45);
      this.jTable2.getColumnModel().getColumn(6).setPreferredWidth(45);
      this.jTable2.getColumnModel().getColumn(7).setPreferredWidth(45);
      this.jTable2.getColumnModel().getColumn(8).setPreferredWidth(45);
      this.jTable2.getColumnModel().getColumn(9).setPreferredWidth(45);
      this.jTable2.getColumnModel().getColumn(0).setPreferredWidth(45);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
