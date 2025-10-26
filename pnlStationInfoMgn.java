package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.frmAddStation;
import com.MiTAC.TRA.ATP.ui.frmError;
import com.MiTAC.TRA.ATP.ui.pnlStationInfoMgn_btnDelStation_actionAdapter;
import com.MiTAC.TRA.ATP.ui.pnlStationInfoMgn_btnEditStation_actionAdapter;
import com.MiTAC.TRA.ATP.ui.pnlStationInfoMgn_btnNewStation_actionAdapter;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class pnlStationInfoMgn extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  
  BorderLayout borderLayout2 = new BorderLayout();
  
  JButton btnDelStation = new JButton();
  
  JButton btnEditStation = new JButton();
  
  JButton btnNewStation = new JButton();
  
  ConnectDB conn = new ConnectDB();
  
  DefaultTableModel dtm = new DefaultTableModel();
  
  JLabel jLabel1 = new JLabel();
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel2 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JTable jTable1 = new JTable();
  
  Vector vLine;
  
  Vector vLineName;
  
  Vector vStation;
  
  Vector vStationName;
  
  public pnlStationInfoMgn() {
    try {
      jbInit();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnDelStation_actionPerformed(ActionEvent paramActionEvent) {
    try {
      Vector vector = new Vector();
      for (byte b = 0; b < (this.jTable1.getSelectedRows()).length; b++) {
        int j = this.jTable1.getSelectedRows()[b];
        vector.addElement(((Vector)this.vStation.get(j)).get(0));
      } 
      int i = JOptionPane.showConfirmDialog(getRootPane().getParent(), ATPMessages.getString("MW.STATION.DELETE.Q.CONFIRM") + vector, ATPMessages.getString("MW.GNL.ASK"), 0, 1);
      if (i == 0) {
        String str = this.conn.Delete(vector);
        frmError frmError = new frmError(str, (Frame)getRootPane().getParent(), ATPMessages.getString("MW.GNL.INFO"), true);
        frmError.show();
        _$16160();
      } 
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnEditStation_actionPerformed(ActionEvent paramActionEvent) {
    try {
      int i = 0;
      if (this.jTable1.getSelectedRow() == -1) {
        i = 0;
      } else {
        i = this.jTable1.getSelectedRow();
      } 
      System.err.println(i);
      Vector vector = this.dtm.getDataVector().get(i);
      frmAddStation frmAddStation = new frmAddStation(this.vLine, vector, (Frame)getRootPane().getParent(), "", true);
      frmAddStation.show();
      _$16160();
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void btnNewStation_actionPerformed(ActionEvent paramActionEvent) {
    try {
      frmAddStation frmAddStation = new frmAddStation(this.vLine, (Frame)getRootPane().getParent(), "", true);
      frmAddStation.show();
      _$16160();
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(this, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
    } 
  }
  
  void jbInit() throws Exception {
    this.jLabel1.setFont(new Font("Dialog", 1, 15));
    this.jLabel1.setBorder(BorderFactory.createEtchedBorder());
    this.jLabel1.setText(ATPMessages.getString("MW.MAIN.TRAIN_AND_STATION_MGN.LINE_STATION_MGN"));
    setLayout(this.borderLayout1);
    this.jPanel1.setLayout(this.borderLayout2);
    this.btnNewStation.setText(ATPMessages.getString("MW.STATION.NEW"));
    this.btnNewStation.addActionListener((ActionListener)new pnlStationInfoMgn_btnNewStation_actionAdapter(this));
    this.btnDelStation.setText(ATPMessages.getString("MW.STATION.DELETE"));
    this.btnDelStation.addActionListener((ActionListener)new pnlStationInfoMgn_btnDelStation_actionAdapter(this));
    this.btnEditStation.setText(ATPMessages.getString("MW.STATION.MODIFY"));
    this.btnEditStation.addActionListener((ActionListener)new pnlStationInfoMgn_btnEditStation_actionAdapter(this));
    add(this.jLabel1, "North");
    add(this.jPanel1, "Center");
    this.jPanel1.add(this.jPanel2, "South");
    this.jPanel2.add(this.btnNewStation, (Object)null);
    this.jPanel2.add(this.btnEditStation, (Object)null);
    this.jPanel2.add(this.btnDelStation, (Object)null);
    this.jPanel1.add(this.jScrollPane1, "Center");
    this.jScrollPane1.getViewport().add(this.jTable1, (Object)null);
    this.jTable1.setModel(this.dtm);
    _$16160();
  }
  
  private void _$16160() throws Exception {
    String str = "SELECT * FROM Station";
    this.vStation = this.conn.getData(str);
    this.vStationName = new Vector();
    this.vStationName.add(ATPMessages.getString("MW.STATION.ID"));
    this.vStationName.add(ATPMessages.getString("MW.STATION.KM"));
    this.vStationName.add(ATPMessages.getString("MW.STATION.E_NAME"));
    this.vStationName.add(ATPMessages.getString("MW.STATION.NAME"));
    this.vStationName.add(ATPMessages.getString("MW.STATION.DATA"));
    this.vStationName.add(ATPMessages.getString("MW.STATION.BELONG"));
    this.vStationName.add("地上設備車站代碼");
    str = "SELECT * FROM Line";
    this.vLine = this.conn.getData(str);
    this.vLineName = new Vector();
    this.vLineName.add(ATPMessages.getString("MW.LINE.ID"));
    this.vLineName.add(ATPMessages.getString("MW.LINE.E_NEAME"));
    this.vLineName.add(ATPMessages.getString("MW.LINE.NAME"));
    this.dtm.setDataVector(this.vStation, this.vStationName);
  }
}
