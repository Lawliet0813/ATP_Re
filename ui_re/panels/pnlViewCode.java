package ui.panels;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.FileFilter.FilterATP;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.Tools.UnknowProgressMonitor;
import com.MiTAC.TRA.ATP.decode.LogDataCollector;
import com.MiTAC.TRA.ATP.ui.decode_btnCommit_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlViewCode_Table_mouseAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlViewCode_btnOpen_actionAdapter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class pnlViewCode extends JPanel {
  LogDataCollector Logdata;
  
  BorderLayout borderLayout1 = new BorderLayout();
  
  BorderLayout borderLayout10 = new BorderLayout();
  
  BorderLayout borderLayout2 = new BorderLayout();
  
  BorderLayout borderLayout3 = new BorderLayout();
  
  BorderLayout borderLayout9 = new BorderLayout();
  
  JButton btnCommit = new JButton();
  
  JButton btnOpen = new JButton();
  
  ButtonGroup buttonGroup1 = new ButtonGroup();
  
  byte[] datas;
  
  String defaultPath = "C:\\";
  
  DefaultTableColumnModel dtcm = new DefaultTableColumnModel();
  
  DefaultSortTableModel dtmATP = new DefaultSortTableModel();
  
  DefaultSortTableModel dtmAll = new DefaultSortTableModel();
  
  FlowLayout flowLayout1 = new FlowLayout();
  
  FlowLayout flowLayout2 = new FlowLayout();
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel14 = new JPanel();
  
  JPanel jPanel15 = new JPanel();
  
  JPanel jPanel18 = new JPanel();
  
  JPanel jPanel2 = new JPanel();
  
  JPanel jPanel3 = new JPanel();
  
  JPanel jPanel4 = new JPanel();
  
  JPanel jPanel5 = new JPanel();
  
  JPanel jPanel6 = new JPanel();
  
  JPanel jPanel7 = new JPanel();
  
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  
  JFormattedTextField jTextField1 = new JFormattedTextField();
  
  JFileChooser jfc = new JFileChooser(this.defaultPath);
  
  private com.MiTAC.TRA.ATP.ui.pnlViewCode _$22018;
  
  JScrollPane scrollALL = new JScrollPane();
  
  JScrollPane scrollATP = new JScrollPane();
  
  JScrollPane scrollNoHandle = new JScrollPane();
  
  JScrollPane scrollTS = new JScrollPane();
  
  JSortTable tableALL;
  
  JSortTable tableATP;
  
  JSortTable tableNoHandle;
  
  JSortTable tableTS;
  
  private Timer _$15396;
  
  JTextField txtFilePath = new JTextField();
  
  JTextField txtShowValue = new JTextField();
  
  private UnknowProgressMonitor _$7950;
  
  Vector vectorTSname = new Vector();
  
  Vector vectorcommonName = new Vector();
  
  public pnlViewCode(String paramString) {
    try {
      this._$22018 = this;
      init();
      this.defaultPath = paramString;
      this.jfc.setCurrentDirectory(new File(this.defaultPath + "\\"));
      this.txtFilePath.setText(this.defaultPath);
      _$22042(this.defaultPath);
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public pnlViewCode() {
    try {
      this._$22018 = this;
      init();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void Table_mouseClicked(MouseEvent paramMouseEvent) {
    this.jTextField1.setText(((JTable)paramMouseEvent.getSource()).getValueAt(((JTable)paramMouseEvent.getSource()).getSelectedRow(), 7).toString());
  }
  
  void btnCommit_actionPerformed(ActionEvent paramActionEvent) {
    try {
      _$22042(this.txtFilePath.getText().toString());
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnOpen_actionPerformed(ActionEvent paramActionEvent) {
    this.jfc.setFileFilter((FileFilter)new FilterATP());
    int i = this.jfc.showOpenDialog(null);
    if (i == 0)
      this.txtFilePath.setText(this.jfc.getSelectedFile().getPath()); 
    _$22042(this.txtFilePath.getText().toString());
  }
  
  private void _$22042(String paramString) {
    try {
      this._$15396 = new Timer(100, (ActionListener)new Object(this));
      this._$7950 = new UnknowProgressMonitor(this, ATPMessages.getString("MW.LV.DECODING") + paramString + "", "", 0, 100);
      this._$7950.setMillisToDecideToPopup(0);
      this._$15396.start();
      this._$7950.setNote(ATPMessages.getString("MW.LV.DECODING"));
      this.Logdata = new LogDataCollector(paramString);
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void init() throws Exception {
    this.jPanel1.setLayout(this.borderLayout1);
    setLayout(new BorderLayout());
    this.btnOpen.setActionCommand(ATPMessages.getString("MW.LV.OPENFILE"));
    this.btnOpen.setText(ATPMessages.getString("MW.LV.OPENFILE"));
    this.btnOpen.addActionListener((ActionListener)new pnlViewCode_btnOpen_actionAdapter(this));
    this.btnCommit.setText(ATPMessages.getString("MW.LV.DECODE"));
    this.btnCommit.addActionListener((ActionListener)new decode_btnCommit_actionAdapter(this));
    this.txtFilePath.setPreferredSize(new Dimension(200, 23));
    this.txtFilePath.setText("jTextField1");
    this.txtFilePath.setMinimumSize(new Dimension(6, 23));
    this.txtFilePath.setPreferredSize(new Dimension(400, 23));
    this.txtFilePath.setText("");
    this.jPanel3.setLayout(new BorderLayout());
    this.jPanel4.setLayout(new BorderLayout());
    this.jPanel14.setLayout(this.borderLayout2);
    this.jPanel15.setLayout(this.borderLayout9);
    this.jTabbedPane1.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
    this.jPanel2.setLayout(this.flowLayout2);
    this.jPanel18.setLayout(this.borderLayout10);
    this.jPanel5.setBorder(BorderFactory.createLineBorder(Color.black));
    this.txtShowValue.setText(ATPMessages.getString("MW.LV.SELECT_TO_SHOW_DETAIL"));
    this.txtShowValue.setVisible(false);
    this.jPanel7.setLayout(this.borderLayout3);
    this.jTextField1.setText(ATPMessages.getString("MW.LV.SELECT_TO_SHOW_DETAIL"));
    this.jPanel2.add(this.jPanel6, (Object)null);
    this.jPanel5.add(this.txtFilePath, (Object)null);
    this.jPanel5.add(this.txtFilePath, (Object)null);
    this.jPanel5.add(this.btnCommit, (Object)null);
    this.jPanel2.add(this.btnOpen, (Object)null);
    this.jPanel2.add(this.jPanel5, (Object)null);
    add(this.jPanel1, "Center");
    this.jTabbedPane1.add(this.jPanel18, ATPMessages.getString("MW.LV.ALL"));
    this.jPanel18.add(this.scrollALL, "Center");
    this.jPanel1.add(this.jTabbedPane1, "Center");
    this.jTabbedPane1.add(this.jPanel3, ATPMessages.getString("MW.LV.TS"));
    this.jPanel3.add(this.scrollTS, "Center");
    this.vectorTSname.add(ATPMessages.getString("MW.LV.TIME"));
    this.vectorTSname.add(ATPMessages.getString("MW.LV.SPEED"));
    this.vectorTSname.add(ATPMessages.getString("MW.LV.LOCATION"));
    this.vectorcommonName.add(ATPMessages.getString("MW.LV.LOCATION"));
    this.vectorcommonName.add(ATPMessages.getString("MW.LV.TIME"));
    this.vectorcommonName.add(ATPMessages.getString("MW.LV.SPEED"));
    this.vectorcommonName.add(ATPMessages.getString("MW.LV.POSITION"));
    this.vectorcommonName.add(ATPMessages.getString("MW.LV.MVB"));
    this.vectorcommonName.add(ATPMessages.getString("MW.LV.TYPE"));
    this.vectorcommonName.add(ATPMessages.getString("MW.LV.LENGTH"));
    this.vectorcommonName.add(ATPMessages.getString("MW.LV.HEXCODE"));
    this.vectorcommonName.add(ATPMessages.getString("MW.LV.DESCRIPTION"));
    TableColumn tableColumn1 = new TableColumn(0);
    tableColumn1.setHeaderValue(this.vectorcommonName.get(0));
    tableColumn1.setMaxWidth(50);
    TableColumn tableColumn2 = new TableColumn(1, 120);
    tableColumn2.setHeaderValue(this.vectorcommonName.get(1));
    tableColumn2.setMaxWidth(105);
    TableColumn tableColumn3 = new TableColumn(2);
    tableColumn3.setHeaderValue(this.vectorcommonName.get(2));
    tableColumn3.setMaxWidth(40);
    TableColumn tableColumn4 = new TableColumn(3);
    tableColumn4.setHeaderValue(this.vectorcommonName.get(3));
    tableColumn4.setMaxWidth(70);
    TableColumn tableColumn5 = new TableColumn(4);
    tableColumn5.setHeaderValue(this.vectorcommonName.get(4));
    tableColumn5.setMaxWidth(45);
    TableColumn tableColumn6 = new TableColumn(5);
    tableColumn6.setHeaderValue(this.vectorcommonName.get(5));
    tableColumn6.setMaxWidth(45);
    TableColumn tableColumn7 = new TableColumn(6);
    tableColumn7.setHeaderValue(this.vectorcommonName.get(6));
    tableColumn7.setMaxWidth(40);
    TableColumn tableColumn8 = new TableColumn(7);
    tableColumn8.setHeaderValue(this.vectorcommonName.get(7));
    TableColumn tableColumn9 = new TableColumn(8);
    tableColumn9.setHeaderValue(this.vectorcommonName.get(8));
    tableColumn9.setMinWidth(70);
    this.dtcm.addColumn(tableColumn1);
    this.dtcm.addColumn(tableColumn2);
    this.dtcm.addColumn(tableColumn3);
    this.dtcm.addColumn(tableColumn4);
    this.dtcm.addColumn(tableColumn5);
    this.dtcm.addColumn(tableColumn6);
    this.dtcm.addColumn(tableColumn7);
    this.dtcm.addColumn(tableColumn8);
    this.dtcm.addColumn(tableColumn9);
    this.jTabbedPane1.add(this.jPanel4, ATPMessages.getString("MW.LV.ATP"));
    this.jTabbedPane1.add(this.jPanel14, ATPMessages.getString("MW.LV.NO_HANDLE"));
    add(this.jPanel15, "North");
    this.jPanel15.add(this.jPanel2, "Center");
    add(this.jPanel7, "South");
    this.jPanel7.add(this.txtShowValue, "Center");
    this.jPanel4.add(this.scrollATP, "Center");
    this.jPanel14.add(this.scrollNoHandle, "Center");
    this.jPanel1.add(this.jTextField1, "South");
    this.jTabbedPane1.setSelectedIndex(0);
  }
  
  private void _$22052() {
    this.dtmAll.setDataVector(this.Logdata.getAllEventdata(), this.vectorcommonName);
    this.tableALL = new JSortTable((SortTableModel)this.dtmAll, this.dtcm);
    this.tableALL.addMouseListener((MouseListener)new pnlViewCode_Table_mouseAdapter(this));
    this.scrollALL.getViewport().add((Component)this.tableALL, (Object)null);
    this.tableTS = new JSortTable(this.Logdata.getLogTS(), this.vectorTSname);
    this.scrollTS.getViewport().add((Component)this.tableTS, (Object)null);
    this.dtmATP.setDataVector(this.Logdata.getATPEventdata(), this.vectorcommonName);
    this.tableATP = new JSortTable((SortTableModel)this.dtmATP, this.dtcm);
    this.tableATP.addMouseListener((MouseListener)new pnlViewCode_Table_mouseAdapter(this));
    this.scrollATP.getViewport().add((Component)this.tableATP, (Object)null);
    this.tableNoHandle = new JSortTable(this.Logdata.getErrorEventdata(), this.vectorcommonName);
    this.tableNoHandle.addMouseListener((MouseListener)new pnlViewCode_Table_mouseAdapter(this));
    this.scrollNoHandle.getViewport().add((Component)this.tableNoHandle, (Object)null);
  }
}
