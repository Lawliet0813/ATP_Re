package ui.frames;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.RecordHandler;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import com.MiTAC.TRA.ATP.Tools.UnknowProgressMonitor;
import com.MiTAC.TRA.ATP.core.ATPMission;
import com.MiTAC.TRA.ATP.core.ATPMissionFailure;
import com.MiTAC.TRA.ATP.ui.frmErrorList_btnShowDetail_actionAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.frmErrorList_jButton1_actionAdapter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class frmErrorList extends JFrame {
  JButton btnMakeReport = new JButton();
  
  JButton btnShowAllDetail = new JButton();
  
  int clickRow;
  
  ATPMission currentMission;
  
  errorlistTableModel dstm = new errorlistTableModel(this, null);
  
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
  JLabel jLabel4 = new JLabel();
  
  JLabel jLabel5 = new JLabel();
  
  JPanel jPanel1 = new JPanel();
  
  JSortTable jstb = new JSortTable((SortTableModel)this.dstm);
  
  JLabel lblDID = new JLabel();
  
  JLabel lblDate = new JLabel();
  
  JLabel lblTRNo = new JLabel();
  
  JLabel lblVID = new JLabel();
  
  JLabel lblWSNo = new JLabel();
  
  Vector list;
  
  ATPMissionFailure[] missionList;
  
  HashMap parameters;
  
  JScrollPane scp = new JScrollPane();
  
  com.MiTAC.TRA.ATP.ui.frmErrorList thisFrame;
  
  TitledBorder titledBorder1 = new TitledBorder("");
  
  UnknowProgressMonitor unpm;
  
  public frmErrorList(ATPMission paramATPMission, ATPMissionFailure[] paramArrayOfATPMissionFailure, HashMap paramHashMap) {
    this.parameters = paramHashMap;
    try {
      this.currentMission = paramATPMission;
      _$3120();
      this.missionList = paramArrayOfATPMissionFailure;
      Vector vector = new Vector();
      vector.add(ATPMessages.getString("MW.DRIVER.ID"));
      vector.add(ATPMessages.getString("MW.WS.ID"));
      vector.add(ATPMessages.getString("MW.TR.ID"));
      vector.add(ATPMessages.getString("MW.GNL.TIME"));
      vector.add(ATPMessages.getString("MW.GNL.ACCELERATION"));
      vector.add(ATPMessages.getString("MW.GNL.TARGET_DISTANCE"));
      vector.add(ATPMessages.getString("MW.GNL.TARGET_SPEED"));
      vector.add(ATPMessages.getString("MW.GNL.PERMITTED_SPEED"));
      vector.add(ATPMessages.getString("MW.GNL.WARN"));
      vector.add(ATPMessages.getString("MW.GNL.SLIP"));
      vector.add(ATPMessages.getString("MW.GNL.SLIDE"));
      vector.add(ATPMessages.getString("MW.GNL.ADHESION"));
      vector.add(ATPMessages.getString("MW.GNL.ATP_MODE"));
      vector.add(ATPMessages.getString("MW.GNL.EB"));
      vector.add(ATPMessages.getString("MW.GNL.SB"));
      vector.add(ATPMessages.getString("MW.GNL.EOA"));
      vector.add(ATPMessages.getString("MW.GNL.SPEED"));
      vector.add(ATPMessages.getString("MW.GNL.BALISE"));
      vector.add(ATPMessages.getString("MW.GNL.DIST_TO_BALISE"));
      vector.add(ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.FAILURE.TYPE"));
      vector.add(ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.FAILURE.VIEW"));
      RecordHandler recordHandler = new RecordHandler();
      for (byte b = 0; b < paramArrayOfATPMissionFailure.length; b++) {
        ATPMissionFailure aTPMissionFailure = paramArrayOfATPMissionFailure[b];
        Vector vector1 = new Vector();
        if (aTPMissionFailure.getBehaviorFailure() != null) {
          for (byte b1 = 0; b1 < aTPMissionFailure.getBehaviorFailure().size(); b1++)
            vector1.add(aTPMissionFailure.getBehaviorFailure().get(b1)); 
          recordHandler.addVector(vector1);
        } 
      } 
      this.dstm.setDataVector((Vector)recordHandler, vector);
      TableColumn tableColumn1 = this.jstb.getColumnModel().getColumn(20);
      errorlistcelleditor errorlistcelleditor = new errorlistcelleditor(this);
      tableColumn1.setCellEditor((TableCellEditor)errorlistcelleditor);
      this.dstm.setCellEditable(true);
      tableColumn1.setCellRenderer((TableCellRenderer)new Object(this));
      TableColumn tableColumn2 = this.jstb.getColumnModel().getColumn(3);
      tableColumn2.setCellRenderer((TableCellRenderer)new Object(this));
      TableColumn tableColumn3 = this.jstb.getColumnModel().getColumn(20);
      this.jstb.setAutoResizeMode(4);
      if (recordHandler.size() == 0) {
        JOptionPane.showMessageDialog(null, ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.NO_FAILURE"), ATPMessages.getString("MW.GNL.INFO"), 1);
        this.unpm = new UnknowProgressMonitor(this.thisFrame, ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.DRAW_GRAPHIC.DRAWING"), ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.ANALYZE"), 0, 100);
        Object object = new Object(this);
        object.start();
        this.unpm.show();
        dispose();
      } else {
        show();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void btnShowAllDetail_actionPerformed(ActionEvent paramActionEvent) {
    this.unpm = new UnknowProgressMonitor(this.thisFrame, ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.DRAW_GRAPHIC.DRAWING"), ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.ANALYZE"), 0, 100);
    Object object = new Object(this);
    object.start();
    this.unpm.show();
  }
  
  private void _$3120() throws Exception {
    getContentPane().setLayout(new BorderLayout());
    setSize(600, 400);
    this.btnShowAllDetail.addActionListener((ActionListener)new frmErrorList_btnShowDetail_actionAdapter(this));
    this.lblDID.setPreferredSize(new Dimension(53, 17));
    this.lblVID.setPreferredSize(new Dimension(57, 17));
    this.lblVID.setRequestFocusEnabled(true);
    this.lblWSNo.setPreferredSize(new Dimension(53, 17));
    this.lblTRNo.setPreferredSize(new Dimension(53, 17));
    this.lblDate.setPreferredSize(new Dimension(214, 17));
    this.btnMakeReport.setText(ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.REPORT.MAKE"));
    this.btnMakeReport.addActionListener((ActionListener)new frmErrorList_jButton1_actionAdapter(this));
    this.scp.getViewport().add((Component)this.jstb);
    this.btnShowAllDetail.setText(ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.DRAW_GRAPHIC"));
    this.jLabel1.setText(ATPMessages.getString("MW.LOG.RUNNINGDATE"));
    this.jLabel2.setText(ATPMessages.getString("MW.WS.ID"));
    this.jPanel1.setLayout(this.gridBagLayout1);
    this.jLabel3.setText(ATPMessages.getString("MW.TR.ID"));
    this.jLabel4.setText(ATPMessages.getString("MW.DRIVER.ID"));
    this.jLabel5.setText(ATPMessages.getString("MW.VEHICLE.ID"));
    this.lblDate.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblDate.setText(ATPMessages.getString("MW.LOG.RUNNINGDATE"));
    this.lblWSNo.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblWSNo.setText(ATPMessages.getString("MW.WS.ID"));
    this.lblTRNo.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblTRNo.setText(ATPMessages.getString("MW.TR.ID"));
    this.lblVID.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblVID.setText(ATPMessages.getString("MW.VEHICLE.ID"));
    this.lblDID.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblDID.setText(ATPMessages.getString("MW.DRIVER.ID"));
    getContentPane().add(this.scp, "Center");
    getContentPane().add(this.jPanel1, "North");
    this.jPanel1.add(this.jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(4, 38, 0, 11), 0, -2));
    this.jPanel1.add(this.jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(7, 38, 6, 0), 0, -2));
    this.jPanel1.add(this.lblDate, new GridBagConstraints(1, 0, 3, 1, 0.0D, 0.0D, 17, 0, new Insets(4, 6, 0, 0), 0, 0));
    this.jPanel1.add(this.lblWSNo, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(6, 6, 0, 0), 0, 0));
    this.jPanel1.add(this.lblTRNo, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 6, 0, 0), 0, 0));
    this.jPanel1.add(this.jLabel5, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(6, 29, 0, 22), 0, 0));
    this.jPanel1.add(this.jLabel4, new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 29, 0, 0), 0, 0));
    this.jPanel1.add(this.lblVID, new GridBagConstraints(3, 1, 1, 1, 1.0D, 0.0D, 17, 0, new Insets(6, 0, 0, 0), -4, 0));
    this.jPanel1.add(this.lblDID, new GridBagConstraints(3, 2, 1, 1, 1.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    this.jPanel1.add(this.jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 38, 0, 11), 0, -2));
    this.jPanel1.add(this.btnShowAllDetail, new GridBagConstraints(5, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 74), 0, 0));
    this.jPanel1.add(this.btnMakeReport, new GridBagConstraints(4, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
    this.jLabel1.setVisible(false);
    this.jLabel2.setVisible(false);
    this.jLabel3.setVisible(false);
    this.jLabel4.setVisible(false);
    this.jLabel5.setVisible(false);
    this.lblDate.setVisible(false);
    this.lblWSNo.setVisible(false);
    this.lblTRNo.setVisible(false);
    this.lblDID.setVisible(false);
    this.lblVID.setVisible(false);
  }
  
  void jButton1_actionPerformed(ActionEvent paramActionEvent) {
    this.unpm = new UnknowProgressMonitor(this.thisFrame, ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.REPORT.MAKE"), "", 0, 100);
    Object object = new Object(this);
    object.start();
    this.unpm.show();
  }
}
