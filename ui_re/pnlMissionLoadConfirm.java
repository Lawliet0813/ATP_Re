package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.DateRenderer;
import com.MiTAC.TRA.ATP.Tools.SortTable.MultiLineHeaderRenderer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class pnlMissionLoadConfirm extends JPanel {
  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  DefaultTableModel dtm = new DefaultTableModel();
  
  JLabel lblBottom = new JLabel();
  
  JLabel lblHead = new JLabel();
  
  Vector missionData = new Vector();
  
  Vector missionDataName = new Vector();
  
  JScrollPane scroll = new JScrollPane();
  
  String status = new String();
  
  JTable table = new JTable(this.dtm);
  
  public pnlMissionLoadConfirm(Vector paramVector, String paramString) {
    try {
      this.missionData = paramVector;
      this.status = paramString;
      init();
      _$23761();
      _$23762();
      _$23763();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private Vector _$3982() {
    if (this.status.equals("confirm")) {
      this.missionDataName.add(ATPMessages.getString("MW.DRIVER.ID"));
      this.missionDataName.add(ATPMessages.getString("MW.DRIVER.NAME"));
      this.missionDataName.add(ATPMessages.getString("MW.WS.WORKDATE"));
      this.missionDataName.add(ATPMessages.getString("MW.WS.ID"));
      this.missionDataName.add(ATPMessages.getString("MW.TR.ID"));
      this.missionDataName.add(ATPMessages.getString("MW.WS.TR_WORKDATE"));
      this.missionDataName.add(ATPMessages.getString("MW.TT.ID"));
      this.missionDataName.add(ATPMessages.getString("MW.TD.ID"));
    } else {
      this.missionDataName.add(ATPMessages.getString("MW.DRIVER.ID"));
      this.missionDataName.add(ATPMessages.getString("MW.WS.WORKDATE"));
      this.missionDataName.add(ATPMessages.getString("MW.WS.ID"));
      this.missionDataName.add(ATPMessages.getString("MW.TR.ID"));
      this.missionDataName.add(ATPMessages.getString("MW.WS.TR_WORKDATE"));
      this.missionDataName.add(ATPMessages.getString("MW.TT.ID"));
      this.missionDataName.add(ATPMessages.getString("MW.TD.ID"));
    } 
    return this.missionDataName;
  }
  
  private Vector _$1631(Vector paramVector) {
    Vector vector = paramVector;
    Vector vector1 = new Vector();
    Vector vector2 = new Vector();
    if (this.status.equals("confirm"))
      return vector; 
    for (byte b = 0; b < vector.size(); b++) {
      vector1.addElement(((Vector)vector.get(b)).get(0));
      vector1.addElement(this.dateFormat.format(((Vector)vector.get(b)).get(2)));
      vector1.addElement(((Vector)vector.get(b)).get(3));
      vector1.addElement(((Vector)vector.get(b)).get(4));
      vector1.addElement(this.dateFormat.format(((Vector)vector.get(b)).get(5)));
      vector1.addElement(((Vector)vector.get(b)).get(6).toString());
      vector1.addElement(((Vector)vector.get(b)).get(7).toString());
      vector2.addElement(vector1);
      vector1 = new Vector();
    } 
    return vector2;
  }
  
  void init() throws Exception {
    setLayout(new BorderLayout());
    this.scroll.getViewport().add(this.table);
    setSize(new Dimension(500, 500));
    add(this.lblHead, "North");
    add(this.scroll, "Center");
    add(this.lblBottom, "South");
    setPreferredSize(new Dimension(600, 400));
    this.table.getTableHeader().setDefaultRenderer((TableCellRenderer)new MultiLineHeaderRenderer());
    this.dtm.setDataVector(_$1631(this.missionData), _$3982());
  }
  
  public static void main(String[] paramArrayOfString) {
    (new com.MiTAC.TRA.ATP.ui.pnlMissionLoadConfirm(new Vector(), "confirm")).show();
  }
  
  private void _$23763() {
    if (this.status.equals("confirm")) {
      TableColumn tableColumn1 = this.table.getColumnModel().getColumn(2);
      tableColumn1.setCellRenderer((TableCellRenderer)new DateRenderer());
      TableColumn tableColumn2 = this.table.getColumnModel().getColumn(5);
      tableColumn2.setCellRenderer((TableCellRenderer)new DateRenderer());
    } else {
      TableColumn tableColumn1 = this.table.getColumnModel().getColumn(1);
      tableColumn1.setCellRenderer((TableCellRenderer)new DateRenderer());
      TableColumn tableColumn2 = this.table.getColumnModel().getColumn(4);
      tableColumn2.setCellRenderer((TableCellRenderer)new DateRenderer());
    } 
  }
  
  private void _$23762() {
    if (this.status.equals("confirm")) {
      this.table.getColumnModel().getColumn(0).setMaxWidth(160);
      this.table.getColumnModel().getColumn(1).setMaxWidth(70);
      this.table.getColumnModel().getColumn(2).setMaxWidth(70);
      this.table.getColumnModel().getColumn(3).setPreferredWidth(50);
      this.table.getColumnModel().getColumn(4).setPreferredWidth(50);
      this.table.getColumnModel().getColumn(5).setMaxWidth(65);
      this.table.getColumnModel().getColumn(6).setMaxWidth(50);
      this.table.getColumnModel().getColumn(7).setMaxWidth(60);
    } else {
      this.table.getColumnModel().getColumn(0).setMaxWidth(160);
      this.table.getColumnModel().getColumn(1).setMaxWidth(70);
      this.table.getColumnModel().getColumn(2).setMaxWidth(70);
      this.table.getColumnModel().getColumn(3).setPreferredWidth(50);
      this.table.getColumnModel().getColumn(4).setPreferredWidth(50);
      this.table.getColumnModel().getColumn(5).setMaxWidth(65);
      this.table.getColumnModel().getColumn(6).setMaxWidth(50);
    } 
  }
  
  private void _$23761() {
    if (this.status.equals("confirm")) {
      this.lblHead.setText(ATPMessages.getString("MW.MD.DOWNLOAD_MISSION.Q.CONFIMR"));
      this.lblBottom.setText(ATPMessages.getString("MW.MD.DOWNLOAD_MISSION.Q.CONFIMR"));
    } else {
      this.lblHead.setText(ATPMessages.getString("MW.MD.DATA_EXIST"));
      this.lblBottom.setText(ATPMessages.getString("MW.MD.DATA_EXIST.Q.OVERWIRTE"));
    } 
  }
}
