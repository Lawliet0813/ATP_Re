package ui.panels;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.RefreshFrame;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMWOperateLog_jTable1_focusAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMWOperateLog_jTable1_mouseAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlMWOperateLog_search_propertyChangeAdapter;
import com.MiTAC.TRA.ATP.ui.panels.pnlSearch;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class pnlMWOperateLog extends JPanel implements RefreshFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  
  GregorianCalendar calendar = new GregorianCalendar();
  
  ConnectDB conDB = new ConnectDB();
  
  DefaultSortTableModel dtm1 = new DefaultSortTableModel();
  
  JPanel jPanel1 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JScrollPane jScrollPane2 = new JScrollPane();
  
  JSplitPane jSplitPane1 = new JSplitPane();
  
  JSortTable jTable1 = new JSortTable((SortTableModel)this.dtm1);
  
  JLabel lbDescription = new JLabel();
  
  JLabel lblTitle = new JLabel();
  
  Vector logData = new Vector();
  
  Vector logDataName = new Vector();
  
  pnlSearch pnlsearch = new pnlSearch(true);
  
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
  
  JTextArea taDescription = new JTextArea("");
  
  public pnlMWOperateLog() {
    try {
      _$3120();
      _$17368();
      _$1631();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void Refresh() {
    try {
      String str1 = "SELECT UserID,UseTime,ComputerName,FrameName,Status,description FROM MWSystemLog";
      Date date = this.pnlsearch.getSearchList().get(0);
      StringBuffer stringBuffer = new StringBuffer();
      if (date != null) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.add(5, 1);
        stringBuffer.append(" UseTime between '");
        stringBuffer.append("" + simpleDateFormat.format(date));
        stringBuffer.append("' AND '");
        stringBuffer.append(simpleDateFormat.format(gregorianCalendar.getTime()));
        stringBuffer.append("'");
      } else {
        stringBuffer.append(" UseTime not like ''");
      } 
      String str2 = this.pnlsearch.getSearchString();
      int i = str2.indexOf(" AND");
      if (i > 0) {
        str2 = str2.substring(i, str2.length());
      } else {
        str2 = "";
      } 
      str1 = str1 + " WHERE " + stringBuffer.toString() + str2;
      this.logData = this.conDB.getData(str1);
      if (this.logData.size() == 0) {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.SEARCH.NOT_FOUND"), ATPMessages.getString("MW.GNL.INFO"), 1);
        this.dtm1.setDataVector(this.logData, this.logDataName);
        this.taDescription.setText("");
        this.jTable1.getColumnModel().getColumn(0).setPreferredWidth(80);
        this.jTable1.getColumnModel().getColumn(1).setPreferredWidth(120);
        this.jTable1.getColumnModel().getColumn(2).setPreferredWidth(85);
        this.jTable1.getColumnModel().getColumn(3).setPreferredWidth(85);
        this.jTable1.getColumnModel().getColumn(4).setPreferredWidth(70);
        this.jTable1.getColumnModel().getColumn(5).setPreferredWidth(1000);
      } else {
        this.dtm1.setDataVector(this.logData, this.logDataName);
        this.jTable1.setRowSelectionInterval(0, 0);
        this.taDescription.setText(((Vector)this.logData.get(this.jTable1.getSelectedRow())).get(5).toString());
        this.jTable1.getColumnModel().getColumn(0).setPreferredWidth(80);
        this.jTable1.getColumnModel().getColumn(1).setPreferredWidth(120);
        this.jTable1.getColumnModel().getColumn(2).setPreferredWidth(85);
        this.jTable1.getColumnModel().getColumn(3).setPreferredWidth(85);
        this.jTable1.getColumnModel().getColumn(4).setPreferredWidth(70);
        this.jTable1.getColumnModel().getColumn(5).setPreferredWidth(1000);
        TableColumn tableColumn = this.jTable1.getColumnModel().getColumn(1);
        tableColumn.setCellRenderer((TableCellRenderer)new Object(this));
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$17368() throws Exception {
    this.calendar.setTimeInMillis(System.currentTimeMillis());
    this.calendar.add(2, -2);
    String str = "DELETE FROM MWSystemLog WHERE UseTime < '" + this.simpleDateFormat.format(this.calendar.getTime()) + "'";
    this.conDB.Delete(str);
  }
  
  private void _$1631() {
    try {
      String str = "SELECT UserID,UseTime,ComputerName,FrameName,Status,description FROM MWSystemLog";
      this.logData = this.conDB.getData(str);
      this.dtm1.setDataVector(this.logData, this.logDataName);
      this.jTable1.setRowSelectionInterval(0, 0);
      this.taDescription.setText(((Vector)this.logData.get(this.jTable1.getSelectedRow())).get(5).toString());
      this.jTable1.getColumnModel().getColumn(0).setPreferredWidth(80);
      this.jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
      this.jTable1.getColumnModel().getColumn(2).setPreferredWidth(85);
      this.jTable1.getColumnModel().getColumn(3).setPreferredWidth(85);
      this.jTable1.getColumnModel().getColumn(4).setPreferredWidth(70);
      this.jTable1.getColumnModel().getColumn(5).setPreferredWidth(1000);
      TableColumn tableColumn = this.jTable1.getColumnModel().getColumn(1);
      tableColumn.setCellRenderer((TableCellRenderer)new Object(this));
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$3120() throws Exception {
    this.lblTitle.setBorder(BorderFactory.createEtchedBorder());
    setLayout(this.borderLayout1);
    this.jPanel1.setLayout(new BorderLayout());
    this.lblTitle.setText(ATPMessages.getString("MW.MAIN.HISTORY.MW_OPERATION"));
    this.lbDescription.setText(ATPMessages.getString("MW.SLOG.DESCRIPTION"));
    this.taDescription.setLineWrap(true);
    this.taDescription.setWrapStyleWord(true);
    this.jSplitPane1.setOrientation(0);
    this.jPanel1.add(this.lbDescription, "North");
    this.jPanel1.add(this.taDescription, "Center");
    this.jTable1.addMouseListener((MouseListener)new pnlMWOperateLog_jTable1_mouseAdapter(this));
    this.jTable1.addFocusListener((FocusListener)new pnlMWOperateLog_jTable1_focusAdapter(this));
    this.jScrollPane2.add(this.taDescription);
    this.jPanel1.add(this.jScrollPane2);
    this.jSplitPane1.add(this.jScrollPane1, "top");
    this.jSplitPane1.add(this.jPanel1, "bottom");
    add(this.lblTitle, "North");
    add(this.jSplitPane1, "Center");
    add((Component)this.pnlsearch, "East");
    this.jTable1.setAutoResizeMode(0);
    this.jScrollPane1.getViewport().add((Component)this.jTable1, (Object)null);
    this.jScrollPane2.getViewport().add(this.taDescription, (Object)null);
    this.jSplitPane1.setDividerLocation(450);
    this.pnlsearch.addPropertyChangeListener((PropertyChangeListener)new pnlMWOperateLog_search_propertyChangeAdapter(this));
    this.pnlsearch.hideOption(4, false);
    this.pnlsearch.hideOption(3, false);
    this.pnlsearch.hideOption(5, false);
    this.pnlsearch.hideOption(2, false);
    this.logDataName.add(ATPMessages.getString("MW.LOGIN.USERID"));
    this.logDataName.add(ATPMessages.getString("MW.SLOG.OPERATE_TIME"));
    this.logDataName.add(ATPMessages.getString("MW.SLOG.COMPUTER_NAME"));
    this.logDataName.add(ATPMessages.getString("MW.SLOG.FUNCTION_NAME"));
    this.logDataName.add(ATPMessages.getString("MW.SLOG.STATUS"));
    this.logDataName.add(ATPMessages.getString("MW.SLOG.DESCRIPTION"));
  }
  
  void jTable1_focusGained(FocusEvent paramFocusEvent) {
    this.taDescription.setText(((Vector)this.logData.get(this.jTable1.getSelectedRow())).get(5).toString());
  }
  
  void jTable1_mouseClicked(MouseEvent paramMouseEvent) {
    this.taDescription.setText(((Vector)this.logData.get(this.jTable1.getSelectedRow())).get(5).toString());
  }
  
  void search_propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    if (paramPropertyChangeEvent.getPropertyName() == "search") {
      Refresh();
    } else if (paramPropertyChangeEvent.getPropertyName().equals("save")) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");
      this.pnlsearch.doExport((TableModel)this.dtm1, this.lblTitle.getText() + "_" + simpleDateFormat.format(new Date(System.currentTimeMillis())), true);
    } 
  }
}
