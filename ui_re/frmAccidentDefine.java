package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.UnknowProgressMonitor;
import com.MiTAC.TRA.ATP.core.ATPMissionGeneral;
import com.MiTAC.TRA.ATP.ui.frmAccidentDefine_btnCancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmAccidentDefine_btnConfirm_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmAccidentDefine_lstAccidentType_listSelectionAdapter;
import com.MiTAC.TRA.ATP.ui.frmAccidentDefine_scrollEndTime_adjustmentAdapter;
import com.MiTAC.TRA.ATP.ui.frmAccidentDefine_scrollStartTime_adjustmentAdapter;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class frmAccidentDefine extends JDialog {
  BorderLayout borderLayout1 = new BorderLayout();
  
  JButton btnCancel = new JButton();
  
  JButton btnConfirm = new JButton();
  
  ButtonGroup buttonGroup1 = new ButtonGroup();
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel10 = new JLabel();
  
  JLabel jLabel11 = new JLabel();
  
  JLabel jLabel12 = new JLabel();
  
  JLabel jLabel13 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel4 = new JLabel();
  
  JLabel jLabel6 = new JLabel();
  
  JLabel jLabel7 = new JLabel();
  
  JLabel jLabel8 = new JLabel();
  
  JLabel jLabel9 = new JLabel();
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel3 = new JPanel();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  
  JLabel lblDID = new JLabel();
  
  JLabel lblEndTime = new JLabel();
  
  JLabel lblMissionDate = new JLabel();
  
  JLabel lblScrollEndTime = new JLabel();
  
  JLabel lblScrollStartTime = new JLabel();
  
  JLabel lblStartTime = new JLabel();
  
  JLabel lblTRNo = new JLabel();
  
  JLabel lblVID = new JLabel();
  
  JLabel lblWSNo = new JLabel();
  
  JList lstAccidenType = new JList();
  
  JList lstAccidentType;
  
  String message;
  
  JScrollBar scrollEndTime = new JScrollBar();
  
  JScrollBar scrollStartTime = new JScrollBar();
  
  SimpleDateFormat sdfMission = new SimpleDateFormat("yyyy/MM/dd");
  
  SimpleDateFormat sdfRunning = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  
  long startTime;
  
  com.MiTAC.TRA.ATP.ui.frmAccidentDefine thisFrame;
  
  ATPMissionGeneral thisMission;
  
  UnknowProgressMonitor unpm;
  
  XYLayout xYLayout1 = new XYLayout();
  
  XYLayout xYLayout2 = new XYLayout();
  
  public frmAccidentDefine(Frame paramFrame, String paramString, boolean paramBoolean, ATPMissionGeneral paramATPMissionGeneral) {
    super(paramFrame, paramString, paramBoolean);
    try {
      this.thisFrame = this;
      this.thisMission = paramATPMissionGeneral;
      init();
      pack();
      this.lblMissionDate.setText(this.sdfMission.format(paramATPMissionGeneral.getMissionDate()));
      this.lblWSNo.setText(paramATPMissionGeneral.getWorkShift());
      this.lblTRNo.setText(paramATPMissionGeneral.getTrainRunning());
      this.lblDID.setText(paramATPMissionGeneral.getDriver());
      this.lblVID.setText(paramATPMissionGeneral.getVehicle());
      this.lblStartTime.setText(this.sdfRunning.format(paramATPMissionGeneral.getMissionStartTime()));
      this.lblEndTime.setText(this.sdfRunning.format(paramATPMissionGeneral.getMissionEndTime()));
      this.startTime = paramATPMissionGeneral.getMissionStartTime().getTime();
      int i = (int)(paramATPMissionGeneral.getMissionEndTime().getTime() - paramATPMissionGeneral.getMissionStartTime().getTime()) / 1000;
      this.scrollStartTime.setMinimum(0);
      this.scrollStartTime.setMaximum(i);
      this.scrollStartTime.setValue(0);
      this.scrollEndTime.setMinimum(0);
      this.scrollEndTime.setMaximum(i);
      this.scrollEndTime.setValue(i);
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
      _$22932();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnCancel_actionPerformed(ActionEvent paramActionEvent) {
    dispose();
  }
  
  void btnConfirm_actionPerformed(ActionEvent paramActionEvent) {
    this.unpm = new UnknowProgressMonitor(this, ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.DRAW_GRAPHIC.DRAWING"), "", 0, 100);
    Object object = new Object(this);
    object.start();
  }
  
  void init() throws Exception {
    String[] arrayOfString = { 
        "衝撞", "傾覆", "火災", "列車出軌", "車輛出軌", "列車邊撞", "車輛邊撞", "列車分離", "進入錯線", "車輛溜逸", 
        "止衝檔衝擊", "路牌錯誤", "機車故障", "電車故障", "動力車故障", "客車故障", "貨車故障", "電車線設備故障", "路線故障", "列車障礙", 
        "列車妨礙", "平車或電搖車障礙", "車輛遺留", "轉轍器擠壞", "車輛衝擊", "無閉塞行車", "辦理閉塞違章", "閉塞裝置故障", "號誌故障", "號誌機外停車", 
        "列車延誤", "死傷", "其他" };
    this.lstAccidentType = new JList(arrayOfString);
    this.lstAccidentType.setSelectedIndex(0);
    this.lstAccidentType.addListSelectionListener((ListSelectionListener)new frmAccidentDefine_lstAccidentType_listSelectionAdapter(this));
    this.message = arrayOfString[0];
    this.jLabel1.setBorder(BorderFactory.createEtchedBorder());
    this.jLabel1.setText(ATPMessages.getString("MW.LA.ACCIDENT.ANALYZE"));
    getContentPane().setLayout(this.borderLayout1);
    this.jLabel2.setText(ATPMessages.getString("MW.GNL.DATE"));
    this.lblMissionDate.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblMissionDate.setText("jLabel3");
    this.jLabel4.setText(ATPMessages.getString("MW.WS.ID"));
    this.lblWSNo.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblWSNo.setPreferredSize(new Dimension(38, 21));
    this.lblWSNo.setText("jLabel5");
    this.jLabel6.setText(ATPMessages.getString("MW.TR.ID"));
    this.lblTRNo.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblTRNo.setText("jLabel7");
    this.jLabel8.setText(ATPMessages.getString("MW.DRIVER.ID"));
    this.lblDID.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblDID.setText("jLabel9");
    this.jPanel1.setLayout((LayoutManager)this.xYLayout1);
    this.btnConfirm.setText(ATPMessages.getString("MW.GNL.CONFIRM"));
    this.btnConfirm.addActionListener((ActionListener)new frmAccidentDefine_btnConfirm_actionAdapter(this));
    this.btnCancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.btnCancel.addActionListener((ActionListener)new frmAccidentDefine_btnCancel_actionAdapter(this));
    this.jLabel7.setText(ATPMessages.getString("MW.LA.ACCIDENT.TR_START_TIME"));
    this.jLabel9.setText(ATPMessages.getString("MW.LA.ACCIDENT.TR_END_TIME"));
    this.lblStartTime.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblStartTime.setText("jLabel10");
    this.lblEndTime.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblEndTime.setText("jLabel11");
    this.jPanel3.setLayout((LayoutManager)this.xYLayout2);
    this.jPanel3.setBorder(BorderFactory.createLoweredBevelBorder());
    this.lblVID.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblVID.setDebugGraphicsOptions(0);
    this.lblVID.setText("jLabel10");
    this.jLabel10.setText(ATPMessages.getString("MW.VEHICLE.ID"));
    this.jLabel11.setText(ATPMessages.getString("MW.LA.ACCIDENT.SET_START_TIME"));
    this.jLabel12.setText(ATPMessages.getString("MW.LA.ACCIDENT.SET_END_TIME"));
    this.lblScrollStartTime.setText("jLabel11");
    this.lblScrollStartTime.setForeground(Color.black);
    this.lblScrollStartTime.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblScrollStartTime.setHorizontalAlignment(0);
    this.lblScrollEndTime.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblScrollEndTime.setHorizontalAlignment(0);
    this.lblScrollEndTime.setText("jLabel11");
    this.scrollStartTime.setOrientation(0);
    this.scrollStartTime.addAdjustmentListener((AdjustmentListener)new frmAccidentDefine_scrollStartTime_adjustmentAdapter(this));
    this.scrollEndTime.setOrientation(0);
    this.scrollEndTime.addAdjustmentListener((AdjustmentListener)new frmAccidentDefine_scrollEndTime_adjustmentAdapter(this));
    this.jLabel13.setText(ATPMessages.getString("MW.LA.ACCIDENT.TYPE"));
    this.jScrollPane1.setToolTipText(ATPMessages.getString("MW.LA.ACCIDENT.TYPE.TOOLTIP"));
    getContentPane().add(this.jLabel1, "North");
    getContentPane().add(this.jPanel1, "South");
    this.jPanel3.add(this.jScrollPane1, new XYConstraints(3, 20, 116, 112));
    this.jPanel3.add(this.jLabel11, new XYConstraints(127, 13, 94, -1));
    this.jPanel3.add(this.scrollStartTime, new XYConstraints(122, 33, 261, 18));
    this.jPanel3.add(this.scrollEndTime, new XYConstraints(122, 93, 261, 18));
    this.jPanel3.add(this.lblScrollEndTime, new XYConstraints(127, 114, 255, 18));
    this.jPanel3.add(this.jLabel12, new XYConstraints(127, 73, 97, -1));
    this.jPanel3.add(this.jLabel13, new XYConstraints(3, 3, 94, -1));
    this.jPanel3.add(this.lblScrollStartTime, new XYConstraints(128, 54, 255, -1));
    this.jPanel1.add(this.jLabel2, new XYConstraints(4, 7, -1, 23));
    this.jPanel1.add(this.lblStartTime, new XYConstraints(81, 77, 115, 20));
    this.jPanel1.add(this.jLabel7, new XYConstraints(4, 76, -1, 23));
    this.jPanel1.add(this.jLabel4, new XYConstraints(4, 30, -1, 23));
    this.jPanel1.add(this.jLabel6, new XYConstraints(4, 53, -1, 23));
    this.jPanel1.add(this.lblMissionDate, new XYConstraints(43, 8, 233, 20));
    this.jPanel1.add(this.jLabel9, new XYConstraints(196, 77, -1, 20));
    this.jPanel1.add(this.lblEndTime, new XYConstraints(276, 77, 115, 20));
    this.jPanel1.add(this.jLabel10, new XYConstraints(196, 31, -1, 20));
    this.jPanel1.add(this.jLabel8, new XYConstraints(196, 54, -1, 20));
    this.jPanel1.add(this.lblWSNo, new XYConstraints(81, 31, 72, 20));
    this.jPanel1.add(this.lblTRNo, new XYConstraints(81, 54, 72, 20));
    this.jPanel1.add(this.lblVID, new XYConstraints(276, 31, 72, 20));
    this.jPanel1.add(this.lblDID, new XYConstraints(276, 54, 72, 20));
    this.jPanel1.add(this.btnConfirm, new XYConstraints(217, 240, -1, -1));
    this.jPanel1.add(this.btnCancel, new XYConstraints(288, 240, -1, -1));
    this.jScrollPane1.getViewport().add(this.lstAccidentType, (Object)null);
    this.jPanel1.add(this.jPanel3, new XYConstraints(4, 99, 394, 141));
  }
  
  void lstAccidentType_valueChanged(ListSelectionEvent paramListSelectionEvent) {
    this.message = this.lstAccidentType.getSelectedValue().toString();
  }
  
  private void _$22932() {
    this.lblScrollStartTime.setText(this.sdfRunning.format(new Date(this.startTime + (this.scrollStartTime.getValue() * 1000))));
    this.lblScrollEndTime.setText(this.sdfRunning.format(new Date(this.startTime + (this.scrollEndTime.getValue() * 1000))));
  }
  
  void scrollEndTime_adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent) {
    if (this.scrollEndTime.getValue() - this.scrollStartTime.getValue() < 300) {
      if (this.scrollStartTime.getValue() - 300 > this.scrollStartTime.getMaximum()) {
        this.scrollStartTime.setValue(this.scrollStartTime.getMinimum());
      } else {
        this.scrollStartTime.setValue(this.scrollEndTime.getValue() - 301);
      } 
    } else {
      _$22932();
    } 
  }
  
  void scrollStartTime_adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent) {
    if (this.scrollEndTime.getValue() - this.scrollStartTime.getValue() < 300) {
      if (this.scrollEndTime.getValue() + 300 > this.scrollEndTime.getMaximum()) {
        this.scrollEndTime.setValue(this.scrollEndTime.getMaximum());
      } else {
        this.scrollEndTime.setValue(this.scrollStartTime.getValue() + 301);
      } 
    } else {
      _$22932();
    } 
  }
}
