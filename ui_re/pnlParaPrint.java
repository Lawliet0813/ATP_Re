package ui;

import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.drawGraphics.DrawBreakOff;
import com.MiTAC.TRA.ATP.drawGraphics.DrawContinuous;
import com.MiTAC.TRA.ATP.drawGraphics.DrawHistogram;
import com.MiTAC.TRA.ATP.drawGraphics.DrawMass;
import com.MiTAC.TRA.ATP.drawGraphics.DrawRightAngle;
import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import com.MiTAC.TRA.ATP.ui.examplePrintPanel;
import com.MiTAC.TRA.ATP.ui.pnlParaPrint_jTabbedPane1_changeAdapter;
import com.MiTAC.TRA.ATP.ui.pnlParaPrint_rdoDrawByTime_changeAdapter;
import com.MiTAC.TRA.ATP.ui.pnlParaPrint_resetValue_keyAdapter;
import com.MiTAC.TRA.ATP.ui.pnlParaPrint_txtParaField_focusAdapter;
import com.MiTAC.TRA.ATP.ui.pnlPrintBD_pnlExample_mouseMotionAdapter;
import com.MiTAC.TRA.ATP.ui.resetPrintColor_propertyChangeListener;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class pnlParaPrint extends JPanel {
  private Color _$17717;
  
  private Color _$17702;
  
  private Color _$17704;
  
  pnlBody BD = new pnlBody(this, null);
  
  private Color _$17703;
  
  private int _$17699;
  
  private int _$17701;
  
  private Color _$17729;
  
  private double _$17724;
  
  private int _$17727;
  
  private int _$17725;
  
  private int _$17726;
  
  private String _$17728;
  
  private Color _$17706;
  
  private double _$17696;
  
  private double _$17697;
  
  protected static final int DRAW_BY_DIST = 1;
  
  protected static final int DRAW_BY_TIME = 2;
  
  private int _$17695 = 2;
  
  private Color _$17742;
  
  private double _$17737;
  
  private int _$17740;
  
  private int _$17738;
  
  private int _$17739;
  
  private String _$17741;
  
  private Color _$17705;
  
  pnlHeader HD = new pnlHeader(this, null);
  
  private int _$17698;
  
  private int _$17700;
  
  private Color _$17749;
  
  private double _$17744;
  
  private int _$17747;
  
  private int _$17745;
  
  private int _$17746;
  
  private String _$17748;
  
  private Color _$17707;
  
  private Color _$17714;
  
  private Color _$17715;
  
  private Color _$17743;
  
  private Color _$17736;
  
  private Color _$17735;
  
  private double _$17730;
  
  private int _$17733;
  
  private int _$17731;
  
  private int _$17732;
  
  private String _$17734;
  
  private Color _$17713;
  
  private double _$17708;
  
  private int _$17711;
  
  private int _$17709;
  
  private int _$17710;
  
  private String _$17712;
  
  private Color _$17716;
  
  private int _$17805 = 0;
  
  private Color _$17723;
  
  private double _$17718;
  
  private int _$17721;
  
  private int _$17719;
  
  private int _$17720;
  
  private String _$17722;
  
  private Vector _$17812;
  
  private Vector _$17807;
  
  BorderLayout borderLayout1 = new BorderLayout();
  
  BorderLayout borderLayout2 = new BorderLayout();
  
  BorderLayout borderLayout3 = new BorderLayout();
  
  private Vector _$17813;
  
  ButtonGroup buttonGroup1 = new ButtonGroup();
  
  boolean changes = false;
  
  DrawContinuous dad;
  
  DrawMass dbt;
  
  DrawRightAngle deb;
  
  DrawHistogram dmode;
  
  DrawBreakOff dpspd;
  
  DrawBreakOff drspd;
  
  DrawRightAngle dsb;
  
  DrawRightAngle dslide;
  
  DrawRightAngle dslip;
  
  DrawContinuous dspd;
  
  DrawBreakOff dtspd;
  
  DrawHistogram dwrn;
  
  private Vector _$17816;
  
  examplePrintPanel[] exGroup = new examplePrintPanel[18];
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel11 = new JLabel();
  
  JLabel jLabel110 = new JLabel();
  
  JLabel jLabel111 = new JLabel();
  
  JLabel jLabel1110 = new JLabel();
  
  JLabel jLabel1111 = new JLabel();
  
  JLabel jLabel11110 = new JLabel();
  
  JLabel jLabel11111 = new JLabel();
  
  JLabel jLabel11112 = new JLabel();
  
  JLabel jLabel11113 = new JLabel();
  
  JLabel jLabel11115 = new JLabel();
  
  JLabel jLabel11116 = new JLabel();
  
  JLabel jLabel1112 = new JLabel();
  
  JLabel jLabel1113 = new JLabel();
  
  JLabel jLabel1115 = new JLabel();
  
  JLabel jLabel1116 = new JLabel();
  
  JLabel jLabel1117 = new JLabel();
  
  JLabel jLabel1118 = new JLabel();
  
  JLabel jLabel112 = new JLabel();
  
  JLabel jLabel113 = new JLabel();
  
  JLabel jLabel114 = new JLabel();
  
  JLabel jLabel115 = new JLabel();
  
  JLabel jLabel116 = new JLabel();
  
  JLabel jLabel117 = new JLabel();
  
  JLabel jLabel118 = new JLabel();
  
  JLabel jLabel17 = new JLabel();
  
  JLabel jLabel19 = new JLabel();
  
  JLabel jLabel20 = new JLabel();
  
  JLabel jLabel21 = new JLabel();
  
  JLabel jLabel210 = new JLabel();
  
  JLabel jLabel211 = new JLabel();
  
  JLabel jLabel2110 = new JLabel();
  
  JLabel jLabel2111 = new JLabel();
  
  JLabel jLabel21113 = new JLabel();
  
  JLabel jLabel21114 = new JLabel();
  
  JLabel jLabel2112 = new JLabel();
  
  JLabel jLabel2113 = new JLabel();
  
  JLabel jLabel2115 = new JLabel();
  
  JLabel jLabel2119 = new JLabel();
  
  JLabel jLabel212 = new JLabel();
  
  JLabel jLabel213 = new JLabel();
  
  JLabel jLabel214 = new JLabel();
  
  JLabel jLabel215 = new JLabel();
  
  JLabel jLabel216 = new JLabel();
  
  JLabel jLabel218 = new JLabel();
  
  JLabel jLabel219 = new JLabel();
  
  JLabel jLabel22 = new JLabel();
  
  JLabel jLabel23 = new JLabel();
  
  JLabel jLabel24 = new JLabel();
  
  JLabel jLabel25 = new JLabel();
  
  JLabel jLabel26 = new JLabel();
  
  JLabel jLabel27 = new JLabel();
  
  JLabel jLabel28 = new JLabel();
  
  JTabbedPane jTabbedPane1;
  
  JLabel lblAdhesionColor = new JLabel();
  
  JLabel lblBOC = new JLabel();
  
  JLabel lblBTColor = new JLabel();
  
  JLabel lblBTIncrease = new JLabel();
  
  JLabel lblBTlIncrease = new JLabel();
  
  JLabel lblBackgroundColor = new JLabel();
  
  JLabel lblBaseLineColor = new JLabel();
  
  JLabel lblBorderLineColor = new JLabel();
  
  JLabel lblCharColor = new JLabel();
  
  JLabel lblEBColor = new JLabel();
  
  JLabel lblEBIncrease = new JLabel();
  
  JLabel lblGridLineColor = new JLabel();
  
  JLabel lblModeColor = new JLabel();
  
  JLabel lblModeIncrease = new JLabel();
  
  JLabel lblMouseColor = new JLabel();
  
  JLabel lblPermittedSpeedColor = new JLabel();
  
  JLabel lblReleaseSpeedColor = new JLabel();
  
  JLabel lblSBColor = new JLabel();
  
  JLabel lblSlideColor = new JLabel();
  
  JLabel lblSlipColor = new JLabel();
  
  JLabel lblSlipIncrease = new JLabel();
  
  JLabel lblSpeedColor = new JLabel();
  
  JLabel lblSpeedIncrease = new JLabel();
  
  JLabel lblTargetSpeedColor = new JLabel();
  
  JLabel lblUsualIncrease = new JLabel();
  
  JLabel lblWarnIncrease = new JLabel();
  
  private Vector _$17818;
  
  private MouseEvent _$17806;
  
  private Color _$17772;
  
  private Color _$17757;
  
  private Color _$17759;
  
  private Color _$17758;
  
  private int _$17754;
  
  private int _$17756;
  
  private Color _$17784;
  
  private double _$17779;
  
  private int _$17782;
  
  private int _$17780;
  
  private int _$17781;
  
  private String _$17783;
  
  private Color _$17761;
  
  private double _$17751;
  
  private double _$17752;
  
  private int _$17750 = this._$17695;
  
  private Color _$17797;
  
  private double _$17792;
  
  private int _$17795;
  
  private int _$17793;
  
  private int _$17794;
  
  private String _$17796;
  
  private Color _$17760;
  
  private int _$17753;
  
  private int _$17755;
  
  private Color _$17804;
  
  private double _$17799;
  
  private int _$17802;
  
  private int _$17800;
  
  private int _$17801;
  
  private String _$17803;
  
  private Color _$17762;
  
  private Color _$17769;
  
  private Color _$17770;
  
  private Color _$17798;
  
  private Color _$17791;
  
  private Color _$17790;
  
  private double _$17785;
  
  private int _$17788;
  
  private int _$17786;
  
  private int _$17787;
  
  private String _$17789;
  
  private Color _$17768;
  
  private double _$17763;
  
  private int _$17766;
  
  private int _$17764;
  
  private int _$17765;
  
  private String _$17767;
  
  private Color _$17771;
  
  private Color _$17778;
  
  private double _$17773;
  
  private int _$17776;
  
  private int _$17774;
  
  private int _$17775;
  
  private String _$17777;
  
  private Vector _$17810;
  
  examplePrintPanel pnlAdhesionChooser;
  
  examplePrintPanel pnlBackgroundChooser;
  
  examplePrintPanel pnlBaseLineChooser;
  
  examplePrintPanel pnlBorderLineChooser;
  
  JPanel pnlBrake = new JPanel();
  
  examplePrintPanel pnlBrakeTargetChooser;
  
  examplePrintPanel pnlCharChooser;
  
  examplePrintPanel pnlEBChooser;
  
  JPanel pnlExample = new JPanel();
  
  examplePrintPanel pnlGridLineChooser;
  
  JPanel pnlMode = new JPanel();
  
  examplePrintPanel pnlModeChooser;
  
  examplePrintPanel pnlMouseChooser;
  
  examplePrintPanel pnlPermittedSPDChooser;
  
  examplePrintPanel pnlReleaseSPDChooser;
  
  examplePrintPanel pnlSBChooser;
  
  JPanel pnlSlide = new JPanel();
  
  examplePrintPanel pnlSlideChooser;
  
  examplePrintPanel pnlSlipChooser;
  
  JPanel pnlSpeed = new JPanel();
  
  examplePrintPanel pnlSpeedChooser;
  
  JPanel pnlTarget = new JPanel();
  
  examplePrintPanel pnlTargetSPDChooser;
  
  JPanel pnlUsual = new JPanel();
  
  JPanel pnlWarn = new JPanel();
  
  examplePrintPanel pnlWarnChooser;
  
  JRadioButton rdoDrawByDist = new JRadioButton();
  
  JRadioButton rdoDrawByTime = new JRadioButton();
  
  private Vector _$17809;
  
  private Vector _$17817;
  
  commonParaSetting scps = new commonParaSetting();
  
  private static final long serialVersionUID = 1L;
  
  private Vector _$17815;
  
  private Vector _$17814;
  
  drawParameters spara;
  
  private Vector _$17808;
  
  private Vector _$17811;
  
  TitledBorder titledBorder1;
  
  TitledBorder titledBorder2;
  
  TitledBorder titledBorder3;
  
  JTextField txtBTDpi = new JTextField();
  
  JTextField txtBTInterval = new JTextField();
  
  JTextField txtBTMax = new JTextField();
  
  JTextField txtBTMin = new JTextField();
  
  JTextField txtBTMsg = new JTextField();
  
  JTextField txtBrightLine = new JTextField();
  
  JTextField txtBrightLineD = new JTextField();
  
  JTextField txtDpiX = new JTextField();
  
  JTextField txtDpiXD = new JTextField();
  
  JTextField txtEBDpi = new JTextField();
  
  JTextField txtEBInterval = new JTextField();
  
  JTextField txtEBMax = new JTextField();
  
  JTextField txtEBMin = new JTextField();
  
  JTextField txtEBMsg = new JTextField();
  
  JTextField txtIntervalX = new JTextField();
  
  JTextField txtIntervalXD = new JTextField();
  
  JTextField txtModeDpi = new JTextField();
  
  JTextField txtModeInterval = new JTextField();
  
  JTextField txtModeMax = new JTextField();
  
  JTextField txtModeMin = new JTextField();
  
  JTextField txtModeMsg = new JTextField();
  
  JTextField txtSlipDpi = new JTextField();
  
  JTextField txtSlipInterval = new JTextField();
  
  JTextField txtSlipMax = new JTextField();
  
  JTextField txtSlipMin = new JTextField();
  
  JTextField txtSlipMsg = new JTextField();
  
  JTextField txtSpeedDpi = new JTextField();
  
  JTextField txtSpeedInterval = new JTextField();
  
  JTextField txtSpeedMax = new JTextField();
  
  JTextField txtSpeedMin = new JTextField();
  
  JTextField txtSpeedMsg = new JTextField();
  
  JTextField txtWarnDpi = new JTextField();
  
  JTextField txtWarnInterval = new JTextField();
  
  JTextField txtWarnMax = new JTextField();
  
  JTextField txtWarnMin = new JTextField();
  
  JTextField txtWarnMsg = new JTextField();
  
  private Vector _$17819;
  
  XYLayout xYLayout1 = new XYLayout();
  
  XYLayout xYLayout2 = new XYLayout();
  
  XYLayout xYLayout3 = new XYLayout();
  
  XYLayout xYLayout4 = new XYLayout();
  
  XYLayout xYLayout5 = new XYLayout();
  
  XYLayout xYLayout6 = new XYLayout();
  
  XYLayout xYLayout7 = new XYLayout();
  
  XYLayout xYLayout8 = new XYLayout();
  
  public pnlParaPrint() {
    try {
      initValue();
      init();
      _$17987();
      _$17988();
      this.pnlExample.setBackground(this._$17702);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  protected void callbackValue() {
    initValue();
    for (byte b = 0; b < this.exGroup.length; b++)
      this.exGroup[b].callbackSetting(); 
    this.pnlExample.setBackground(this._$17702);
    _$17990();
  }
  
  public Object[] getValues() {
    Object[] arrayOfObject = new Object[55];
    arrayOfObject[0] = new Double(this._$17751);
    arrayOfObject[1] = new Double(this._$17752);
    arrayOfObject[2] = new Integer(this._$17753);
    arrayOfObject[3] = new Integer(this._$17754);
    arrayOfObject[4] = new Integer(this._$17755);
    arrayOfObject[5] = new Integer(this._$17756);
    arrayOfObject[6] = new Double(this._$17763);
    arrayOfObject[7] = new Integer(this._$17764);
    arrayOfObject[8] = new Integer(this._$17765);
    arrayOfObject[9] = new Integer(this._$17766);
    arrayOfObject[10] = this._$17767;
    arrayOfObject[11] = new Double(this._$17773);
    arrayOfObject[12] = new Integer(this._$17774);
    arrayOfObject[13] = new Integer(this._$17775);
    arrayOfObject[14] = new Integer(this._$17776);
    arrayOfObject[15] = this._$17777;
    arrayOfObject[16] = new Double(this._$17779);
    arrayOfObject[17] = new Integer(this._$17780);
    arrayOfObject[18] = new Integer(this._$17781);
    arrayOfObject[19] = new Integer(this._$17782);
    arrayOfObject[20] = this._$17783;
    arrayOfObject[21] = new Double(this._$17785);
    arrayOfObject[22] = new Integer(this._$17786);
    arrayOfObject[23] = new Integer(this._$17787);
    arrayOfObject[24] = new Integer(this._$17788);
    arrayOfObject[25] = this._$17789;
    arrayOfObject[26] = new Double(this._$17792);
    arrayOfObject[27] = new Integer(this._$17793);
    arrayOfObject[28] = new Integer(this._$17794);
    arrayOfObject[29] = new Integer(this._$17795);
    arrayOfObject[30] = this._$17796;
    arrayOfObject[31] = new Double(this._$17799);
    arrayOfObject[32] = new Integer(this._$17800);
    arrayOfObject[33] = new Integer(this._$17801);
    arrayOfObject[34] = new Integer(this._$17802);
    arrayOfObject[35] = this._$17803;
    arrayOfObject[36] = this._$17757;
    arrayOfObject[37] = this._$17758;
    arrayOfObject[38] = this._$17759;
    arrayOfObject[39] = this._$17760;
    arrayOfObject[40] = this._$17761;
    arrayOfObject[41] = this._$17762;
    arrayOfObject[42] = this._$17768;
    arrayOfObject[43] = this._$17769;
    arrayOfObject[44] = this._$17770;
    arrayOfObject[45] = this._$17771;
    arrayOfObject[46] = this._$17772;
    arrayOfObject[47] = this._$17804;
    arrayOfObject[48] = this._$17790;
    arrayOfObject[49] = this._$17791;
    arrayOfObject[50] = this._$17778;
    arrayOfObject[51] = this._$17784;
    arrayOfObject[52] = this._$17797;
    arrayOfObject[53] = this._$17798;
    arrayOfObject[54] = new Integer(this._$17750);
    return arrayOfObject;
  }
  
  void init() throws Exception {
    this.titledBorder1 = new TitledBorder("");
    this.titledBorder2 = new TitledBorder("");
    this.titledBorder3 = new TitledBorder("");
    setLayout((LayoutManager)this.xYLayout1);
    this.jLabel17.setFont(new Font("Dialog", 1, 14));
    this.jLabel17.setForeground(Color.black);
    this.jLabel17.setText("顏色設定");
    this.pnlExample.setBorder(BorderFactory.createEtchedBorder());
    this.BD.addMouseMotionListener((MouseMotionListener)new pnlPrintBD_pnlExample_mouseMotionAdapter(this));
    this.pnlExample.setLayout(this.borderLayout1);
    this.jTabbedPane1 = new JTabbedPane();
    this.jTabbedPane1.setTabPlacement(1);
    this.jTabbedPane1.setAlignmentY(0.5F);
    this.jTabbedPane1.setBorder((Border)null);
    this.jTabbedPane1.setVerifyInputWhenFocusTarget(true);
    this.jTabbedPane1.addChangeListener((ChangeListener)new pnlParaPrint_jTabbedPane1_changeAdapter(this));
    this.pnlSpeed.setLayout((LayoutManager)this.xYLayout2);
    this.jLabel1.setText("最大速度");
    this.lblPermittedSpeedColor.setText("限制速度");
    this.lblReleaseSpeedColor.setText("放行速度");
    this.lblTargetSpeedColor.setText("目標速度");
    this.pnlBrake.setLayout((LayoutManager)this.xYLayout3);
    this.lblEBColor.setText("緊急緊軔");
    this.lblSBColor.setToolTipText("");
    this.lblSBColor.setText("常用緊軔");
    this.pnlSlide.setLayout((LayoutManager)this.xYLayout4);
    this.pnlTarget.setLayout((LayoutManager)this.xYLayout5);
    this.pnlWarn.setLayout((LayoutManager)this.xYLayout6);
    this.jLabel11.setText("警告");
    this.pnlMode.setLayout((LayoutManager)this.xYLayout7);
    this.lblModeColor.setText("模式");
    this.pnlUsual.setLayout((LayoutManager)this.xYLayout8);
    this.lblBaseLineColor.setText("底線");
    this.lblGridLineColor.setText("格線");
    this.lblBackgroundColor.setText("背景");
    this.lblMouseColor.setText("滑鼠游標");
    this.lblCharColor.setText("文字");
    this.lblSpeedColor.setText("速度");
    this.jLabel19.setText("最低速度");
    this.jLabel110.setText("每點解析度");
    this.jLabel111.setText("間隔");
    this.txtSpeedMax.setNextFocusableComponent(this.txtSpeedMin);
    this.txtSpeedMax.setPreferredSize(new Dimension(57, 23));
    this.txtSpeedMax.setMargin(new Insets(1, 1, 1, 1));
    this.txtSpeedMax.setHorizontalAlignment(4);
    this.txtSpeedMax.setToolTipText("");
    this.txtSpeedMin.setHorizontalAlignment(4);
    this.txtSpeedMin.setMargin(new Insets(1, 1, 1, 1));
    this.txtSpeedMin.setNextFocusableComponent(this.txtSpeedDpi);
    this.txtSpeedMin.setPreferredSize(new Dimension(57, 23));
    this.txtSpeedMin.setToolTipText("");
    this.txtSpeedDpi.setHorizontalAlignment(4);
    this.txtSpeedDpi.setMargin(new Insets(1, 1, 1, 1));
    this.txtSpeedDpi.setNextFocusableComponent(this.txtSpeedInterval);
    this.txtSpeedDpi.setPreferredSize(new Dimension(57, 23));
    this.txtSpeedDpi.setToolTipText("速度解析度");
    this.txtSpeedInterval.setHorizontalAlignment(4);
    this.txtSpeedInterval.setMargin(new Insets(1, 1, 1, 1));
    this.txtSpeedInterval.setNextFocusableComponent(this.txtSpeedMsg);
    this.txtSpeedInterval.setPreferredSize(new Dimension(57, 23));
    this.txtSpeedInterval.setToolTipText("");
    this.txtSpeedMsg.setPreferredSize(new Dimension(57, 23));
    this.txtSpeedMsg.setMargin(new Insets(1, 1, 1, 1));
    this.txtSpeedMsg.setHorizontalAlignment(4);
    this.txtSpeedMsg.setToolTipText("");
    this.jLabel20.setOpaque(true);
    this.jLabel20.setText("公里/小時");
    this.jLabel21.setText("公里/小時");
    this.jLabel22.setText("公里/小時(A)");
    this.jLabel23.setText("點(B)劃線");
    this.lblSpeedIncrease.setText("注:");
    this.xYLayout1.setWidth(404);
    this.xYLayout1.setHeight(418);
    this.jLabel24.setText("秒");
    this.jLabel24.setOpaque(true);
    this.jLabel25.setText("解析度");
    this.jLabel27.setOpaque(true);
    this.jLabel27.setText("點 劃線");
    this.jLabel112.setText("標題");
    this.jLabel113.setText("間隔");
    this.jLabel114.setToolTipText("");
    this.jLabel114.setText("最小值");
    this.jLabel28.setRequestFocusEnabled(true);
    this.jLabel28.setText("最大值");
    this.jLabel115.setText("每點解析度");
    this.txtWarnMax.setHorizontalAlignment(4);
    this.txtWarnMax.setMargin(new Insets(1, 1, 1, 1));
    this.txtWarnMax.setNextFocusableComponent(this.txtWarnMin);
    this.txtWarnMax.setPreferredSize(new Dimension(57, 23));
    this.txtWarnMax.setToolTipText("");
    this.txtWarnMin.setNextFocusableComponent(this.txtWarnDpi);
    this.txtWarnMin.setPreferredSize(new Dimension(57, 23));
    this.txtWarnMin.setMargin(new Insets(1, 1, 1, 1));
    this.txtWarnMin.setHorizontalAlignment(4);
    this.txtWarnMin.setToolTipText("");
    this.txtWarnDpi.setNextFocusableComponent(this.txtWarnInterval);
    this.txtWarnDpi.setPreferredSize(new Dimension(57, 23));
    this.txtWarnDpi.setMargin(new Insets(1, 1, 1, 1));
    this.txtWarnDpi.setHorizontalAlignment(4);
    this.txtWarnDpi.setToolTipText("");
    this.txtWarnInterval.setNextFocusableComponent(this.txtWarnMsg);
    this.txtWarnInterval.setPreferredSize(new Dimension(57, 23));
    this.txtWarnInterval.setMargin(new Insets(1, 1, 1, 1));
    this.txtWarnInterval.setHorizontalAlignment(4);
    this.txtWarnInterval.setToolTipText("");
    this.txtWarnMsg.setHorizontalAlignment(4);
    this.txtWarnMsg.setMargin(new Insets(1, 1, 1, 1));
    this.txtWarnMsg.setPreferredSize(new Dimension(57, 23));
    this.txtWarnMsg.setToolTipText("");
    this.jLabel116.setText("標題");
    this.lblBorderLineColor.setText("邊線");
    this.jLabel117.setText("間隔");
    this.jLabel118.setText("標題");
    this.lblBTColor.setToolTipText("");
    this.lblBTColor.setText("目標距離");
    this.txtBTMax.setNextFocusableComponent(this.txtBTMin);
    this.txtBTMax.setPreferredSize(new Dimension(57, 23));
    this.txtBTMax.setMargin(new Insets(1, 1, 1, 1));
    this.txtBTMax.setHorizontalAlignment(4);
    this.txtBTMax.setToolTipText("");
    this.txtBTMin.setHorizontalAlignment(4);
    this.txtBTMin.setMargin(new Insets(1, 1, 1, 1));
    this.txtBTMin.setNextFocusableComponent(this.txtBTDpi);
    this.txtBTMin.setPreferredSize(new Dimension(57, 23));
    this.txtBTMin.setToolTipText("");
    this.txtBTDpi.setHorizontalAlignment(4);
    this.txtBTDpi.setMargin(new Insets(1, 1, 1, 1));
    this.txtBTDpi.setNextFocusableComponent(this.txtBTInterval);
    this.txtBTDpi.setPreferredSize(new Dimension(57, 23));
    this.txtBTDpi.setToolTipText("");
    this.txtBTInterval.setHorizontalAlignment(4);
    this.txtBTInterval.setMargin(new Insets(1, 1, 1, 1));
    this.txtBTInterval.setNextFocusableComponent(this.txtBTMsg);
    this.txtBTInterval.setPreferredSize(new Dimension(57, 23));
    this.txtBTInterval.setToolTipText("");
    this.txtBTMsg.setHorizontalAlignment(4);
    this.txtBTMsg.setMargin(new Insets(1, 1, 1, 1));
    this.txtBTMsg.setPreferredSize(new Dimension(57, 23));
    this.txtBTMsg.setToolTipText("");
    this.jLabel1110.setText("每點解析度");
    this.jLabel210.setRequestFocusEnabled(true);
    this.jLabel210.setText("最大值");
    this.jLabel1111.setToolTipText("");
    this.jLabel1111.setText("最小值");
    this.jLabel1112.setText("間隔");
    this.jLabel1113.setText("標題");
    this.lblSlideColor.setText("滑行");
    this.txtSlipMax.setBackground(Color.white);
    this.txtSlipMax.setNextFocusableComponent(this.txtSlipMin);
    this.txtSlipMax.setOpaque(true);
    this.txtSlipMax.setToolTipText("");
    this.txtSlipMin.setHorizontalAlignment(4);
    this.txtSlipMin.setMargin(new Insets(1, 1, 1, 1));
    this.txtSlipMin.setNextFocusableComponent(this.txtSlipDpi);
    this.txtSlipMin.setPreferredSize(new Dimension(57, 23));
    this.txtSlipMin.setToolTipText("");
    this.txtSlipDpi.setHorizontalAlignment(4);
    this.txtSlipDpi.setMargin(new Insets(1, 1, 1, 1));
    this.txtSlipDpi.setNextFocusableComponent(this.txtSlipInterval);
    this.txtSlipDpi.setPreferredSize(new Dimension(57, 23));
    this.txtSlipDpi.setToolTipText("");
    this.txtSlipInterval.setToolTipText("");
    this.txtSlipInterval.setNextFocusableComponent(this.txtSlipMsg);
    this.txtSlipInterval.setPreferredSize(new Dimension(57, 23));
    this.txtSlipInterval.setMargin(new Insets(1, 1, 1, 1));
    this.txtSlipInterval.setHorizontalAlignment(4);
    this.txtSlipMsg.setHorizontalAlignment(4);
    this.txtSlipMsg.setMargin(new Insets(1, 1, 1, 1));
    this.txtSlipMsg.setPreferredSize(new Dimension(57, 23));
    this.txtSlipMsg.setToolTipText("");
    this.jLabel1115.setText("每點解析度");
    this.jLabel211.setRequestFocusEnabled(true);
    this.jLabel211.setText("最大值");
    this.txtSlipMax.setHorizontalAlignment(4);
    this.txtSlipMax.setMargin(new Insets(1, 1, 1, 1));
    this.txtSlipMax.setPreferredSize(new Dimension(57, 23));
    this.jLabel1116.setToolTipText("");
    this.jLabel1116.setText("最小值");
    this.jLabel1117.setText("間隔");
    this.jLabel1118.setText("標題");
    this.txtEBMax.setHorizontalAlignment(4);
    this.txtEBMax.setNextFocusableComponent(this.txtEBMin);
    this.txtEBMax.setMargin(new Insets(1, 1, 1, 1));
    this.txtEBMax.setPreferredSize(new Dimension(57, 23));
    this.txtEBMax.setToolTipText("");
    this.txtEBMin.setHorizontalAlignment(4);
    this.txtEBMin.setNextFocusableComponent(this.txtEBDpi);
    this.txtEBMin.setMargin(new Insets(1, 1, 1, 1));
    this.txtEBMin.setPreferredSize(new Dimension(57, 23));
    this.txtEBMin.setToolTipText("");
    this.txtEBDpi.setHorizontalAlignment(4);
    this.txtEBDpi.setNextFocusableComponent(this.txtEBInterval);
    this.txtEBDpi.setMargin(new Insets(1, 1, 1, 1));
    this.txtEBDpi.setPreferredSize(new Dimension(57, 23));
    this.txtEBDpi.setToolTipText("");
    this.txtEBInterval.setHorizontalAlignment(4);
    this.txtEBInterval.setNextFocusableComponent(this.txtEBMsg);
    this.txtEBInterval.setMargin(new Insets(1, 1, 1, 1));
    this.txtEBInterval.setPreferredSize(new Dimension(57, 23));
    this.txtEBInterval.setToolTipText("");
    this.txtEBMsg.setHorizontalAlignment(4);
    this.txtEBMsg.setMargin(new Insets(1, 1, 1, 1));
    this.txtEBMsg.setPreferredSize(new Dimension(57, 23));
    this.txtEBMsg.setToolTipText("");
    this.jLabel11110.setText("每點解析度");
    this.jLabel212.setRequestFocusEnabled(true);
    this.jLabel212.setText("最大值");
    this.jLabel11111.setToolTipText("");
    this.jLabel11111.setText("最小值");
    this.jLabel11112.setText("間隔");
    this.jLabel11113.setText("標題");
    this.txtModeMax.setHorizontalAlignment(4);
    this.txtModeMax.setMargin(new Insets(1, 1, 1, 1));
    this.txtModeMax.setNextFocusableComponent(this.txtModeMin);
    this.txtModeMax.setPreferredSize(new Dimension(57, 23));
    this.txtModeMax.setToolTipText("");
    this.txtModeMin.setHorizontalAlignment(4);
    this.txtModeMin.setMargin(new Insets(1, 1, 1, 1));
    this.txtModeMin.setNextFocusableComponent(this.txtModeDpi);
    this.txtModeMin.setPreferredSize(new Dimension(57, 23));
    this.txtModeMin.setToolTipText("");
    this.txtModeDpi.setHorizontalAlignment(4);
    this.txtModeDpi.setMargin(new Insets(1, 1, 1, 1));
    this.txtModeDpi.setMinimumSize(new Dimension(6, 23));
    this.txtModeDpi.setNextFocusableComponent(this.txtModeInterval);
    this.txtModeDpi.setPreferredSize(new Dimension(57, 23));
    this.txtModeDpi.setToolTipText("");
    this.txtModeInterval.setToolTipText("");
    this.txtModeInterval.setNextFocusableComponent(this.txtModeMsg);
    this.txtModeInterval.setPreferredSize(new Dimension(57, 23));
    this.txtModeInterval.setMargin(new Insets(1, 1, 1, 1));
    this.txtModeInterval.setHorizontalAlignment(4);
    this.txtModeMsg.setHorizontalAlignment(4);
    this.txtModeMsg.setMargin(new Insets(1, 1, 1, 1));
    this.txtModeMsg.setPreferredSize(new Dimension(57, 23));
    this.txtModeMsg.setToolTipText("");
    this.txtDpiXD.setPreferredSize(new Dimension(57, 23));
    this.txtDpiXD.setNextFocusableComponent(this.txtIntervalXD);
    this.txtDpiXD.setMinimumSize(new Dimension(6, 23));
    this.txtDpiXD.setMargin(new Insets(1, 1, 1, 1));
    this.txtDpiXD.setHorizontalAlignment(4);
    this.txtDpiXD.setToolTipText("");
    this.txtIntervalXD.setHorizontalAlignment(4);
    this.txtIntervalXD.setNextFocusableComponent(this.txtBrightLineD);
    this.txtIntervalXD.setMargin(new Insets(1, 1, 1, 1));
    this.txtIntervalXD.setPreferredSize(new Dimension(57, 23));
    this.txtIntervalXD.setToolTipText("");
    this.txtBrightLineD.setHorizontalAlignment(4);
    this.txtBrightLineD.setMargin(new Insets(1, 1, 1, 1));
    this.txtBrightLineD.setPreferredSize(new Dimension(57, 23));
    this.txtBrightLineD.setToolTipText("");
    this.txtDpiX.setHorizontalAlignment(4);
    this.txtDpiX.setMargin(new Insets(1, 1, 1, 1));
    this.txtDpiX.setMinimumSize(new Dimension(6, 23));
    this.txtDpiX.setNextFocusableComponent(this.txtIntervalX);
    this.txtDpiX.setPreferredSize(new Dimension(57, 23));
    this.txtDpiX.setToolTipText("");
    this.txtIntervalX.setPreferredSize(new Dimension(57, 23));
    this.txtIntervalX.setMargin(new Insets(1, 1, 1, 1));
    this.txtIntervalX.setHorizontalAlignment(4);
    this.txtIntervalX.setNextFocusableComponent(this.txtBrightLine);
    this.txtIntervalX.setToolTipText("");
    this.txtBrightLine.setPreferredSize(new Dimension(57, 23));
    this.txtBrightLine.setMargin(new Insets(1, 1, 1, 1));
    this.txtBrightLine.setHorizontalAlignment(4);
    this.txtBrightLine.setToolTipText("");
    this.jLabel26.setText("底線每");
    this.jLabel11115.setText("每點解析度");
    this.jLabel213.setRequestFocusEnabled(true);
    this.jLabel213.setText("最大值");
    this.jLabel11116.setText("最小值");
    this.lblSlipColor.setText("空轉");
    this.lblAdhesionColor.setText("黏著力");
    this.jLabel218.setText("公尺");
    this.jLabel219.setText("公尺");
    this.jLabel219.setOpaque(true);
    this.jLabel2110.setText("公尺");
    this.jLabel2111.setText("點 劃線");
    this.jLabel2112.setText("無");
    this.jLabel2113.setText("空轉/滑行");
    this.jLabel2113.setOpaque(true);
    this.jLabel2115.setText("點 劃線");
    this.jLabel2119.setText("點 劃線");
    this.jLabel21113.setText("點 劃線");
    this.lblUsualIncrease.setText("* 格線區間為20秒");
    this.lblUsualIncrease.setOpaque(true);
    this.lblWarnIncrease.setOpaque(true);
    this.lblWarnIncrease.setText("* 格線區間為20秒");
    this.lblBTIncrease.setOpaque(true);
    this.lblBTIncrease.setText("* 格線區間為20秒");
    this.lblSlipIncrease.setOpaque(true);
    this.lblSlipIncrease.setText("* 格線區間為20秒");
    this.lblEBIncrease.setOpaque(true);
    this.lblEBIncrease.setText("* 格線區間為20秒");
    this.lblModeIncrease.setOpaque(true);
    this.lblModeIncrease.setText("* 格線區間為20秒");
    this.jLabel21114.setText("點 劃線");
    this.jLabel215.setOpaque(true);
    this.jLabel215.setText("公尺");
    this.rdoDrawByTime.setOpaque(false);
    this.rdoDrawByTime.setText("時間展開");
    this.rdoDrawByTime.addChangeListener((ChangeListener)new pnlParaPrint_rdoDrawByTime_changeAdapter(this));
    this.rdoDrawByDist.setOpaque(false);
    this.rdoDrawByDist.setToolTipText("");
    this.rdoDrawByDist.setText("距離展開");
    this.rdoDrawByDist.addChangeListener((ChangeListener)new pnlParaPrint_rdoDrawByTime_changeAdapter(this));
    this.jLabel214.setText("格線每");
    this.jLabel216.setText("格 區隔");
    this.jLabel216.setOpaque(true);
    this.pnlSpeed.add(this.lblSpeedColor, new XYConstraints(8, 6, -1, -1));
    this.pnlSpeedChooser = new examplePrintPanel(this._$17757, this._$17768, 5);
    this.pnlSpeedChooser.setName("SPEED");
    this.exGroup[0] = this.pnlSpeedChooser;
    this.pnlSpeed.add((Component)this.pnlSpeedChooser, new XYConstraints(55, 6, -1, -1));
    this.pnlSpeedChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlSpeed.add(this.lblReleaseSpeedColor, new XYConstraints(8, 26, -1, -1));
    this.pnlReleaseSPDChooser = new examplePrintPanel(this._$17757, this._$17770, 5);
    this.pnlReleaseSPDChooser.setName("RELEASE_SPEED");
    this.exGroup[1] = this.pnlReleaseSPDChooser;
    this.pnlSpeed.add((Component)this.pnlReleaseSPDChooser, new XYConstraints(55, 26, -1, -1));
    this.pnlReleaseSPDChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlSpeed.add(this.lblPermittedSpeedColor, new XYConstraints(8, 46, -1, -1));
    this.pnlPermittedSPDChooser = new examplePrintPanel(this._$17757, this._$17769, 5);
    this.pnlPermittedSPDChooser.setName("PERMITTED_SPEED");
    this.exGroup[2] = this.pnlPermittedSPDChooser;
    this.pnlSpeed.add((Component)this.pnlPermittedSPDChooser, new XYConstraints(55, 46, -1, -1));
    this.pnlPermittedSPDChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlSpeed.add(this.lblTargetSpeedColor, new XYConstraints(8, 66, -1, -1));
    this.pnlTargetSPDChooser = new examplePrintPanel(this._$17757, this._$17771, 5);
    this.pnlTargetSPDChooser.setName("TARGET_SPEED");
    this.exGroup[3] = this.pnlTargetSPDChooser;
    this.pnlSpeed.add((Component)this.pnlTargetSPDChooser, new XYConstraints(55, 66, -1, -1));
    this.pnlTargetSPDChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlSpeed.add(this.lblAdhesionColor, new XYConstraints(8, 86, -1, -1));
    this.pnlAdhesionChooser = new examplePrintPanel(this._$17757, this._$17772, 5);
    this.pnlAdhesionChooser.setName("ADHESION");
    this.exGroup[4] = this.pnlAdhesionChooser;
    this.pnlSpeed.add((Component)this.pnlAdhesionChooser, new XYConstraints(55, 86, -1, -1));
    this.pnlAdhesionChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlSpeed.add(this.jLabel1, new XYConstraints(138, 6, -1, -1));
    this.pnlSpeed.add(this.jLabel19, new XYConstraints(138, 26, -1, 17));
    this.pnlSpeed.add(this.jLabel110, new XYConstraints(138, 46, -1, 17));
    this.pnlSpeed.add(this.jLabel111, new XYConstraints(138, 66, -1, 17));
    this.pnlSpeed.add(this.txtSpeedInterval, new XYConstraints(202, 66, -1, 17));
    this.pnlSpeed.add(this.txtSpeedDpi, new XYConstraints(202, 46, -1, 17));
    this.pnlSpeed.add(this.txtSpeedMax, new XYConstraints(202, 6, -1, 17));
    this.pnlSpeed.add(this.txtSpeedMin, new XYConstraints(202, 26, -1, 17));
    this.pnlSpeed.add(this.jLabel20, new XYConstraints(262, 6, -1, -1));
    this.pnlSpeed.add(this.jLabel21, new XYConstraints(262, 26, -1, -1));
    this.pnlSpeed.add(this.jLabel22, new XYConstraints(262, 46, -1, -1));
    this.pnlSpeed.add(this.jLabel23, new XYConstraints(262, 66, -1, -1));
    this.pnlSpeed.add(this.jLabel112, new XYConstraints(138, 86, -1, -1));
    this.pnlSpeed.add(this.txtSpeedMsg, new XYConstraints(202, 86, -1, 17));
    this.pnlSpeed.add(this.lblSpeedIncrease, new XYConstraints(242, 100, -1, -1));
    this.pnlMode.add(this.lblModeColor, new XYConstraints(8, 6, -1, -1));
    this.pnlModeChooser = new examplePrintPanel(this._$17757, this._$17804, 3);
    this.pnlModeChooser.setName("MODE");
    this.exGroup[5] = this.pnlModeChooser;
    this.pnlMode.add((Component)this.pnlModeChooser, new XYConstraints(55, 6, -1, -1));
    this.pnlModeChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlMode.add(this.jLabel11112, new XYConstraints(138, 66, -1, -1));
    this.pnlMode.add(this.jLabel11113, new XYConstraints(138, 86, -1, -1));
    this.pnlMode.add(this.txtModeDpi, new XYConstraints(202, 46, -1, 17));
    this.pnlMode.add(this.txtModeMin, new XYConstraints(202, 26, -1, 17));
    this.pnlMode.add(this.jLabel11115, new XYConstraints(138, 46, -1, -1));
    this.pnlMode.add(this.txtModeMsg, new XYConstraints(202, 86, -1, 17));
    this.pnlMode.add(this.txtModeInterval, new XYConstraints(202, 66, -1, 17));
    this.pnlMode.add(this.jLabel213, new XYConstraints(138, 6, -1, -1));
    this.pnlMode.add(this.txtModeMax, new XYConstraints(202, 6, -1, 17));
    this.pnlMode.add(this.jLabel11116, new XYConstraints(138, 26, -1, -1));
    this.pnlMode.add(this.jLabel21113, new XYConstraints(262, 66, -1, -1));
    this.pnlMode.add(this.lblModeIncrease, new XYConstraints(242, 100, -1, -1));
    this.pnlWarn.add(this.jLabel11, new XYConstraints(8, 6, -1, -1));
    this.pnlWarnChooser = new examplePrintPanel(this._$17757, this._$17778, 3);
    this.pnlWarnChooser.setName("WARN");
    this.exGroup[6] = this.pnlWarnChooser;
    this.pnlWarn.add((Component)this.pnlWarnChooser, new XYConstraints(55, 6, -1, -1));
    this.pnlWarnChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlWarn.add(this.jLabel28, new XYConstraints(138, 6, -1, -1));
    this.pnlWarn.add(this.jLabel114, new XYConstraints(138, 26, -1, -1));
    this.pnlWarn.add(this.jLabel115, new XYConstraints(138, 46, -1, -1));
    this.pnlWarn.add(this.jLabel113, new XYConstraints(138, 66, -1, -1));
    this.pnlWarn.add(this.jLabel116, new XYConstraints(138, 86, -1, -1));
    this.pnlWarn.add(this.txtWarnMsg, new XYConstraints(202, 86, -1, 17));
    this.pnlWarn.add(this.txtWarnInterval, new XYConstraints(202, 66, -1, 17));
    this.pnlWarn.add(this.txtWarnDpi, new XYConstraints(202, 46, -1, 17));
    this.pnlWarn.add(this.txtWarnMin, new XYConstraints(202, 26, -1, 17));
    this.pnlWarn.add(this.txtWarnMax, new XYConstraints(202, 6, -1, 17));
    this.pnlWarn.add(this.lblWarnIncrease, new XYConstraints(242, 100, -1, -1));
    this.pnlWarn.add(this.jLabel21114, new XYConstraints(262, 66, -1, -1));
    this.pnlTarget.add(this.lblBTColor, new XYConstraints(8, 6, -1, -1));
    this.pnlBrakeTargetChooser = new examplePrintPanel(this._$17757, this._$17784, 5);
    this.pnlBrakeTargetChooser.setName("BRAKETARGET");
    this.exGroup[7] = this.pnlBrakeTargetChooser;
    this.pnlTarget.add((Component)this.pnlBrakeTargetChooser, new XYConstraints(55, 6, -1, -1));
    this.pnlBrakeTargetChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlTarget.add(this.jLabel210, new XYConstraints(138, 6, -1, -1));
    this.pnlTarget.add(this.jLabel1111, new XYConstraints(138, 26, -1, -1));
    this.pnlTarget.add(this.jLabel1110, new XYConstraints(138, 46, -1, -1));
    this.pnlTarget.add(this.jLabel117, new XYConstraints(138, 66, -1, -1));
    this.pnlTarget.add(this.jLabel118, new XYConstraints(138, 86, -1, -1));
    this.pnlTarget.add(this.txtBTMsg, new XYConstraints(202, 86, -1, 17));
    this.pnlTarget.add(this.txtBTDpi, new XYConstraints(202, 46, -1, 17));
    this.pnlTarget.add(this.txtBTMin, new XYConstraints(202, 26, -1, 17));
    this.pnlTarget.add(this.txtBTMax, new XYConstraints(202, 6, -1, 17));
    this.pnlTarget.add(this.txtBTInterval, new XYConstraints(202, 66, -1, 17));
    this.pnlTarget.add(this.jLabel219, new XYConstraints(262, 6, -1, -1));
    this.pnlTarget.add(this.jLabel218, new XYConstraints(262, 26, -1, -1));
    this.pnlTarget.add(this.jLabel2110, new XYConstraints(262, 46, -1, -1));
    this.pnlTarget.add(this.jLabel2111, new XYConstraints(262, 66, -1, -1));
    this.pnlTarget.add(this.lblBTIncrease, new XYConstraints(242, 100, -1, -1));
    this.pnlSlide.add(this.lblSlideColor, new XYConstraints(8, 6, -1, -1));
    this.pnlSlipChooser = new examplePrintPanel(this._$17757, this._$17790, 5);
    this.pnlSlipChooser.setName("SLIP");
    this.exGroup[8] = this.pnlSlipChooser;
    this.pnlSlide.add((Component)this.pnlSlipChooser, new XYConstraints(55, 6, -1, -1));
    this.pnlSlipChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlSlide.add(this.lblSlipColor, new XYConstraints(8, 26, -1, -1));
    this.pnlSlideChooser = new examplePrintPanel(this._$17757, this._$17791, 5);
    this.pnlSlideChooser.setName("SLIDE");
    this.exGroup[9] = this.pnlSlideChooser;
    this.pnlSlide.add((Component)this.pnlSlideChooser, new XYConstraints(55, 26, -1, -1));
    this.pnlSlideChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlSlide.add(this.jLabel1112, new XYConstraints(138, 66, -1, -1));
    this.pnlSlide.add(this.jLabel1113, new XYConstraints(138, 86, -1, -1));
    this.pnlSlide.add(this.txtSlipDpi, new XYConstraints(202, 46, -1, 17));
    this.pnlSlide.add(this.txtSlipMin, new XYConstraints(202, 26, -1, 17));
    this.pnlSlide.add(this.jLabel1115, new XYConstraints(138, 46, -1, -1));
    this.pnlSlide.add(this.txtSlipMsg, new XYConstraints(202, 86, -1, 17));
    this.pnlSlide.add(this.txtSlipInterval, new XYConstraints(202, 66, -1, 17));
    this.pnlSlide.add(this.jLabel211, new XYConstraints(138, 6, -1, -1));
    this.pnlSlide.add(this.txtSlipMax, new XYConstraints(202, 6, -1, 17));
    this.pnlSlide.add(this.jLabel1116, new XYConstraints(138, 26, -1, -1));
    this.pnlSlide.add(this.jLabel2112, new XYConstraints(262, 26, -1, -1));
    this.pnlSlide.add(this.jLabel2115, new XYConstraints(262, 66, -1, -1));
    this.pnlSlide.add(this.jLabel2113, new XYConstraints(262, 6, -1, -1));
    this.pnlSlide.add(this.lblSlipIncrease, new XYConstraints(242, 100, -1, -1));
    this.pnlBrake.add(this.lblEBColor, new XYConstraints(8, 6, -1, -1));
    this.pnlEBChooser = new examplePrintPanel(this._$17757, this._$17797, 5);
    this.pnlEBChooser.setName("EB");
    this.exGroup[10] = this.pnlEBChooser;
    this.pnlBrake.add((Component)this.pnlEBChooser, new XYConstraints(55, 6, -1, -1));
    this.pnlEBChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlBrake.add(this.lblSBColor, new XYConstraints(8, 26, -1, -1));
    this.pnlSBChooser = new examplePrintPanel(this._$17757, this._$17798, 5);
    this.pnlSBChooser.setName("SB");
    this.exGroup[11] = this.pnlSBChooser;
    this.pnlBrake.add((Component)this.pnlSBChooser, new XYConstraints(55, 26, -1, -1));
    this.pnlSBChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlBrake.add(this.jLabel1117, new XYConstraints(138, 66, -1, -1));
    this.pnlBrake.add(this.jLabel1118, new XYConstraints(138, 86, -1, -1));
    this.pnlBrake.add(this.txtEBDpi, new XYConstraints(202, 46, -1, 17));
    this.pnlBrake.add(this.txtEBMin, new XYConstraints(202, 26, -1, 17));
    this.pnlBrake.add(this.jLabel11110, new XYConstraints(138, 46, -1, -1));
    this.pnlBrake.add(this.txtEBMsg, new XYConstraints(202, 86, -1, 17));
    this.pnlBrake.add(this.txtEBInterval, new XYConstraints(202, 66, -1, 17));
    this.pnlBrake.add(this.jLabel212, new XYConstraints(138, 6, -1, -1));
    this.pnlBrake.add(this.txtEBMax, new XYConstraints(202, 6, -1, 17));
    this.pnlBrake.add(this.jLabel11111, new XYConstraints(138, 26, -1, -1));
    this.pnlBrake.add(this.jLabel2119, new XYConstraints(262, 66, -1, -1));
    this.pnlBrake.add(this.lblEBIncrease, new XYConstraints(242, 100, -1, -1));
    add(this.jLabel17, new XYConstraints(33, 11, -1, -1));
    this.pnlUsual.add(this.jLabel215, new XYConstraints(262, 46, -1, -1));
    this.pnlUsual.add(this.lblMouseColor, new XYConstraints(8, 6, -1, -1));
    this.pnlMouseChooser = new examplePrintPanel(this._$17757, this._$17762, 5);
    this.pnlMouseChooser.setName("MOUSE");
    this.exGroup[12] = this.pnlMouseChooser;
    this.pnlUsual.add((Component)this.pnlMouseChooser, new XYConstraints(55, 6, -1, -1));
    this.pnlMouseChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlUsual.add(this.lblBackgroundColor, new XYConstraints(8, 26, -1, -1));
    this.pnlBackgroundChooser = new examplePrintPanel(this._$17757);
    this.pnlBackgroundChooser.setName("BKGCOLOR");
    this.exGroup[13] = this.pnlBackgroundChooser;
    this.pnlUsual.add((Component)this.pnlBackgroundChooser, new XYConstraints(55, 26, -1, -1));
    this.pnlBackgroundChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlUsual.add(this.lblBorderLineColor, new XYConstraints(8, 46, -1, -1));
    this.pnlBorderLineChooser = new examplePrintPanel(this._$17757, this._$17758, 5);
    this.pnlBorderLineChooser.setName("BORDERLINE");
    this.exGroup[14] = this.pnlBorderLineChooser;
    this.pnlUsual.add((Component)this.pnlBorderLineChooser, new XYConstraints(55, 46, -1, -1));
    this.pnlBorderLineChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlUsual.add(this.lblBaseLineColor, new XYConstraints(8, 66, -1, -1));
    this.pnlBaseLineChooser = new examplePrintPanel(this._$17757, this._$17759, 5);
    this.pnlBaseLineChooser.setName("BASELINE");
    this.exGroup[15] = this.pnlBaseLineChooser;
    this.pnlUsual.add((Component)this.pnlBaseLineChooser, new XYConstraints(55, 66, -1, -1));
    this.pnlBaseLineChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlUsual.add(this.lblGridLineColor, new XYConstraints(8, 86, -1, -1));
    this.pnlGridLineChooser = new examplePrintPanel(this._$17757, this._$17760, 5);
    this.pnlGridLineChooser.setName("GRIDLINE");
    this.exGroup[16] = this.pnlGridLineChooser;
    this.pnlUsual.add((Component)this.pnlGridLineChooser, new XYConstraints(55, 86, -1, -1));
    this.pnlGridLineChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlUsual.add(this.lblCharColor, new XYConstraints(138, 6, -1, -1));
    this.pnlCharChooser = new examplePrintPanel(this._$17757, this._$17761, 4);
    this.pnlCharChooser.setName("CHAR");
    this.exGroup[17] = this.pnlCharChooser;
    this.pnlUsual.add((Component)this.pnlCharChooser, new XYConstraints(185, 6, -1, -1));
    this.pnlCharChooser.addPropertyChangeListener((PropertyChangeListener)new resetPrintColor_propertyChangeListener(this));
    this.pnlUsual.add(this.jLabel25, new XYConstraints(138, 46, -1, -1));
    this.pnlUsual.add(this.txtDpiX, new XYConstraints(202, 46, -1, 17));
    this.pnlUsual.add(this.jLabel26, new XYConstraints(138, 66, -1, -1));
    this.pnlUsual.add(this.jLabel27, new XYConstraints(262, 66, -1, -1));
    this.pnlUsual.add(this.jLabel24, new XYConstraints(262, 46, -1, -1));
    this.pnlUsual.add(this.txtDpiXD, new XYConstraints(202, 46, -1, 17));
    this.pnlUsual.add(this.rdoDrawByTime, new XYConstraints(138, 26, -1, -1));
    this.pnlUsual.add(this.rdoDrawByDist, new XYConstraints(227, 26, -1, -1));
    this.pnlUsual.add(this.jLabel214, new XYConstraints(138, 86, -1, -1));
    this.pnlUsual.add(this.jLabel216, new XYConstraints(262, 86, -1, -1));
    this.pnlUsual.add(this.lblUsualIncrease, new XYConstraints(242, 100, -1, -1));
    this.pnlUsual.add(this.txtIntervalX, new XYConstraints(202, 66, -1, 17));
    this.pnlUsual.add(this.txtBrightLine, new XYConstraints(202, 86, -1, 17));
    this.pnlUsual.add(this.txtBrightLineD, new XYConstraints(202, 86, -1, 17));
    this.pnlUsual.add(this.txtIntervalXD, new XYConstraints(202, 66, -1, 17));
    add(this.pnlExample, new XYConstraints(7, 174, 387, 238));
    add(this.jTabbedPane1, new XYConstraints(7, 32, 387, 142));
    this.jTabbedPane1.add(this.pnlUsual, "通用設定");
    this.jTabbedPane1.add(this.pnlWarn, "警告");
    this.jTabbedPane1.add(this.pnlTarget, "目標距離");
    this.jTabbedPane1.add(this.pnlSlide, "空轉/滑行");
    this.jTabbedPane1.add(this.pnlBrake, "EB/SB");
    this.jTabbedPane1.add(this.pnlMode, "駕駛模式");
    this.jTabbedPane1.add(this.pnlSpeed, "速度");
    this.txtSpeedMax.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtSpeedMin.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtSpeedDpi.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtSpeedInterval.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtSpeedMsg.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtWarnMax.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtWarnMin.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtWarnDpi.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtWarnInterval.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtWarnMsg.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtBTMax.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtBTMin.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtBTDpi.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtBTInterval.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtBTMsg.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtSlipMax.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtSlipMin.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtSlipDpi.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtSlipInterval.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtSlipMsg.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtModeMax.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtModeMin.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtModeDpi.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtModeInterval.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtModeMsg.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtEBMax.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtEBMin.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtEBDpi.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtEBInterval.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtEBMsg.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtDpiX.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtIntervalX.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtBrightLine.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtDpiXD.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtIntervalXD.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtBrightLineD.addKeyListener((KeyListener)new pnlParaPrint_resetValue_keyAdapter(this));
    this.txtSpeedMax.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtSpeedMin.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtSpeedDpi.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtSpeedInterval.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtSpeedMsg.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtWarnMax.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtWarnMin.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtWarnDpi.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtWarnInterval.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtWarnMsg.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtBTMax.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtBTMin.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtBTDpi.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtBTInterval.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtBTMsg.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtSlipMax.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtSlipMin.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtSlipDpi.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtSlipInterval.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtSlipMsg.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtModeMax.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtModeMin.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtModeDpi.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtModeInterval.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtModeMsg.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtEBMax.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtEBMin.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtEBDpi.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtEBInterval.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtEBMsg.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtDpiX.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtIntervalX.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtBrightLine.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtDpiXD.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtIntervalXD.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.txtBrightLineD.addFocusListener((FocusListener)new pnlParaPrint_txtParaField_focusAdapter(this));
    this.HD.setPreferredSize(new Dimension(60, 31));
    this.pnlExample.add((Component)this.HD, "West");
    this.pnlExample.add((Component)this.BD, "Center");
    this.buttonGroup1.add(this.rdoDrawByTime);
    this.buttonGroup1.add(this.rdoDrawByDist);
    this.scps = new commonParaSetting();
    this.scps.dpiX = this._$17696;
    this.scps.dpiDistX = this._$17697;
    this.scps.intervalX = this._$17698;
    this.scps.intervalDistX = this._$17700;
    this.scps.BrightLine = this._$17699;
    this.scps.baseLineColor = this._$17704;
    this.scps.baseLineColor_light = this._$17705;
    this.scps.charColor = this._$17706;
    this.scps.backgroundColor = this._$17702;
    this.scps.mainLineColor = this._$17703;
    this.scps.mouse = this._$17707;
    this.scps.startTime = 1089001194L;
    this.scps.endTime = 1089232010L;
    this.scps.startLocation = 0;
    this.scps.endLocation = 2000;
  }
  
  private void _$17987() {
    this._$17807 = new Vector();
    Vector vector1 = new Vector();
    vector1.add(new Date(this.scps.startTime));
    vector1.add(new Integer(this.scps.startLocation));
    this._$17807.add(vector1);
    for (byte b1 = 0; b1 < 10; b1++) {
      Vector vector = new Vector();
      vector.add(new Date(this.scps.startTime + (this.scps.endTime - this.scps.startTime) / 10L * b1));
      vector.add(new Integer(this.scps.startLocation));
      this._$17807.add(vector);
    } 
    Vector vector2 = new Vector();
    vector2.add(new Date(this.scps.endTime));
    vector2.add(new Integer(this.scps.endLocation));
    this._$17807.add(vector2);
    this._$17808 = new Vector();
    for (byte b2 = 0; b2 < 10; b2++) {
      Vector vector = new Vector();
      vector.add(new Date(this.scps.startTime + (this.scps.endTime - this.scps.startTime) / 10L * b2));
      vector.add(new Integer((int)(Math.random() * this._$17709)));
      this._$17808.add(vector);
    } 
    this._$17809 = new Vector();
    for (byte b3 = 0; b3 < 10; b3++) {
      Vector vector = new Vector();
      vector.add(new Date(this.scps.startTime + (this.scps.endTime - this.scps.startTime) / 10L * b3));
      vector.add(new Integer((int)(Math.random() * this._$17709)));
      this._$17809.add(vector);
    } 
    this._$17810 = new Vector();
    for (byte b4 = 0; b4 < 10; b4++) {
      Vector vector = new Vector();
      vector.add(new Date(this.scps.startTime + (this.scps.endTime - this.scps.startTime) / 10L * b4));
      vector.add(new Integer((int)(Math.random() * this._$17709)));
      this._$17810.add(vector);
    } 
    this._$17811 = new Vector();
    for (byte b5 = 0; b5 < 10; b5++) {
      Vector vector = new Vector();
      vector.add(new Date(this.scps.startTime + (this.scps.endTime - this.scps.startTime) / 10L * b5));
      vector.add(new Integer((int)(Math.random() * this._$17709)));
      this._$17811.add(vector);
    } 
    this._$17812 = new Vector();
    for (byte b6 = 0; b6 < 10; b6++) {
      Vector vector = new Vector();
      vector.add(new Date(this.scps.startTime + (this.scps.endTime - this.scps.startTime) / 10L * b6));
      vector.add(new Integer((int)(Math.random() * this._$17709)));
      this._$17812.add(vector);
    } 
    this._$17813 = new Vector();
    int i = 0;
    for (byte b7 = 0; b7 < 10; b7++) {
      Vector vector = new Vector();
      long l = this.scps.startTime + (this.scps.endTime - this.scps.startTime) / 10L * b7;
      i += (int)(Math.random() * (this._$17725 / 10));
      vector.add(new Date(l));
      vector.add(new Integer(i));
      this._$17813.add(vector.clone());
      vector.clear();
      vector.add(new Date(l));
      vector.add(new Integer(this._$17725));
      this._$17813.add(vector.clone());
    } 
    this._$17814 = new Vector();
    for (byte b8 = 0; b8 < 10; b8++) {
      Vector vector = new Vector();
      vector.add(new Date(this.scps.startTime + (this.scps.endTime - this.scps.startTime) / 10L * b8));
      vector.add(new Integer((int)(Math.random() * (this._$17731 + 1))));
      this._$17814.add(vector);
    } 
    this._$17815 = new Vector();
    for (byte b9 = 0; b9 < 10; b9++) {
      Vector vector = new Vector();
      vector.add(new Date(this.scps.startTime + (this.scps.endTime - this.scps.startTime) / 10L * b9));
      vector.add(new Integer((int)(Math.random() * (this._$17731 + 1))));
      this._$17815.add(vector);
    } 
    this._$17816 = new Vector();
    for (byte b10 = 0; b10 < 10; b10++) {
      Vector vector = new Vector();
      vector.add(new Date(this.scps.startTime + (this.scps.endTime - this.scps.startTime) / 10L * b10));
      vector.add(new Integer((int)(Math.random() * (this._$17738 + 1))));
      this._$17816.add(vector);
    } 
    this._$17817 = new Vector();
    for (byte b11 = 0; b11 < 10; b11++) {
      Vector vector = new Vector();
      vector.add(new Date(this.scps.startTime + (this.scps.endTime - this.scps.startTime) / 10L * b11));
      vector.add(new Integer((int)(Math.random() * (this._$17738 + 1))));
      this._$17817.add(vector);
    } 
    this._$17818 = new Vector();
    for (byte b12 = 0; b12 < 10; b12++) {
      Vector vector = new Vector();
      vector.add(new Date(this.scps.startTime + (this.scps.endTime - this.scps.startTime) / 10L * b12));
      vector.add(new Integer((int)(Math.random() * this._$17745)));
      this._$17818.add(vector);
    } 
    this._$17819 = new Vector();
    for (byte b13 = 0; b13 < 10; b13++) {
      Vector vector = new Vector();
      vector.add(new Date(this.scps.startTime + (this.scps.endTime - this.scps.startTime) / 10L * b13));
      vector.add(new Integer((int)(Math.random() * this._$17719)));
      this._$17819.add(vector);
    } 
  }
  
  protected void initValue() {
    if (InitParameters.DrawByDist) {
      this.rdoDrawByDist.setSelected(true);
      this._$17695 = 1;
    } else {
      this.rdoDrawByTime.setSelected(true);
      this._$17695 = 2;
    } 
    this._$17696 = InitParameters.DpiX;
    this._$17698 = InitParameters.IntervalX;
    this._$17699 = InitParameters.BrightLine;
    this._$17697 = InitParameters.DpiXD;
    this._$17700 = InitParameters.IntervalXD;
    this._$17701 = InitParameters.BrightLineD;
    this._$17702 = InitParameters.PrintBackGround;
    this._$17703 = InitParameters.PrintMainLine;
    this._$17704 = InitParameters.PrintBaseLine;
    this._$17705 = InitParameters.PrintGridLine;
    this._$17706 = InitParameters.PrintChar;
    this._$17707 = InitParameters.PrintMouse;
    this._$17709 = InitParameters.SpeedMax;
    this._$17710 = InitParameters.SpeedMin;
    this._$17708 = InitParameters.SpeedDpi;
    this._$17711 = InitParameters.SpeedInterval;
    this._$17712 = InitParameters.SpeedMsg;
    this._$17713 = InitParameters.PrintSpeedColor;
    this._$17714 = InitParameters.PrintPmSpeedColor;
    this._$17715 = InitParameters.PrintRsSpeedColor;
    this._$17716 = InitParameters.PrintTgSpeedColor;
    this._$17717 = InitParameters.PrintAhSpeedColor;
    this._$17719 = InitParameters.WarnMax;
    this._$17720 = InitParameters.WarnMin;
    this._$17718 = InitParameters.WarnDpi;
    this._$17721 = InitParameters.WarnInterval;
    this._$17722 = InitParameters.WarnMsg;
    this._$17723 = InitParameters.PrintWarnColor;
    this._$17725 = InitParameters.BTMax;
    this._$17726 = InitParameters.BTMin;
    this._$17724 = InitParameters.BTDpi;
    this._$17727 = InitParameters.BTInterval;
    this._$17728 = InitParameters.BTMsg;
    this._$17729 = InitParameters.PrintBTColor;
    this._$17731 = InitParameters.SlipMax;
    this._$17732 = InitParameters.SlipMin;
    this._$17730 = InitParameters.SlipDpi;
    this._$17733 = InitParameters.SlipInterval;
    this._$17734 = InitParameters.SlipMsg;
    this._$17735 = InitParameters.PrintSlipColor;
    this._$17736 = InitParameters.PrintSlideColor;
    this._$17738 = InitParameters.EBMax;
    this._$17739 = InitParameters.EBMin;
    this._$17737 = InitParameters.EBDpi;
    this._$17740 = InitParameters.EBInterval;
    this._$17741 = InitParameters.EBMsg;
    this._$17742 = InitParameters.PrintEBColor;
    this._$17743 = InitParameters.PrintSBColor;
    this._$17745 = InitParameters.ModeMax;
    this._$17746 = InitParameters.ModeMin;
    this._$17744 = InitParameters.ModeDpi;
    this._$17747 = InitParameters.ModeInterval;
    this._$17748 = InitParameters.ModeMsg;
    this._$17749 = InitParameters.PrintModeColor;
    this.txtDpiX.setText("" + this._$17696);
    this.txtIntervalX.setText("" + this._$17698);
    this.txtBrightLine.setText("" + this._$17699);
    this.txtDpiXD.setText("" + this._$17697);
    this.txtIntervalXD.setText("" + this._$17700);
    this.txtBrightLineD.setText("" + this._$17701);
    this.txtSpeedMax.setText("" + this._$17709);
    this.txtSpeedMin.setText("" + this._$17710);
    this.txtSpeedDpi.setText("" + this._$17708);
    this.txtSpeedInterval.setText("" + this._$17711);
    this.txtSpeedMsg.setText(this._$17712);
    this.txtWarnMax.setText("" + this._$17719);
    this.txtWarnMin.setText("" + this._$17720);
    this.txtWarnDpi.setText("" + this._$17718);
    this.txtWarnInterval.setText("" + this._$17721);
    this.txtWarnMsg.setText(this._$17722);
    this.txtBTMax.setText("" + this._$17725);
    this.txtBTMin.setText("" + this._$17726);
    this.txtBTDpi.setText("" + this._$17724);
    this.txtBTInterval.setText("" + this._$17727);
    this.txtBTMsg.setText(this._$17728);
    this.txtSlipMax.setText("" + this._$17731);
    this.txtSlipMin.setText("" + this._$17732);
    this.txtSlipDpi.setText("" + this._$17730);
    this.txtSlipInterval.setText("" + this._$17733);
    this.txtSlipMsg.setText(this._$17734);
    this.txtEBMax.setText("" + this._$17738);
    this.txtEBMin.setText("" + this._$17739);
    this.txtEBDpi.setText("" + this._$17737);
    this.txtEBInterval.setText("" + this._$17740);
    this.txtEBMsg.setText(this._$17741);
    this.txtModeMax.setText("" + this._$17745);
    this.txtModeMin.setText("" + this._$17746);
    this.txtModeDpi.setText("" + this._$17744);
    this.txtModeInterval.setText("" + this._$17747);
    this.txtModeMsg.setText(this._$17748);
    this._$17750 = this._$17695;
    this._$17751 = this._$17696;
    this._$17753 = this._$17698;
    this._$17754 = this._$17699;
    this._$17752 = this._$17697;
    this._$17755 = this._$17700;
    this._$17756 = this._$17701;
    this._$17757 = this._$17702;
    this._$17758 = this._$17703;
    this._$17759 = this._$17704;
    this._$17760 = this._$17705;
    this._$17761 = this._$17706;
    this._$17762 = this._$17707;
    this._$17764 = this._$17709;
    this._$17765 = this._$17710;
    this._$17763 = this._$17708;
    this._$17766 = this._$17711;
    this._$17767 = this._$17712;
    this._$17768 = this._$17713;
    this._$17769 = this._$17714;
    this._$17770 = this._$17715;
    this._$17771 = this._$17716;
    this._$17772 = this._$17717;
    this._$17774 = this._$17719;
    this._$17775 = this._$17720;
    this._$17773 = this._$17718;
    this._$17776 = this._$17721;
    this._$17777 = this._$17722;
    this._$17778 = this._$17723;
    this._$17780 = this._$17725;
    this._$17781 = this._$17726;
    this._$17779 = this._$17724;
    this._$17782 = this._$17727;
    this._$17783 = this._$17728;
    this._$17784 = this._$17729;
    this._$17786 = this._$17731;
    this._$17787 = this._$17732;
    this._$17785 = this._$17730;
    this._$17788 = this._$17733;
    this._$17789 = this._$17734;
    this._$17790 = this._$17735;
    this._$17791 = this._$17736;
    this._$17793 = this._$17738;
    this._$17794 = this._$17739;
    this._$17792 = this._$17737;
    this._$17795 = this._$17740;
    this._$17796 = this._$17741;
    this._$17797 = this._$17742;
    this._$17798 = this._$17743;
    this._$17800 = this._$17745;
    this._$17801 = this._$17746;
    this._$17799 = this._$17744;
    this._$17802 = this._$17747;
    this._$17803 = this._$17748;
    this._$17804 = this._$17749;
  }
  
  public boolean isChanged() {
    return this.changes;
  }
  
  void jTabbedPane1_stateChanged(ChangeEvent paramChangeEvent) {
    this._$17805 = this.jTabbedPane1.getSelectedIndex();
    _$17990();
    this.pnlExample.repaint();
  }
  
  private void _$8475() {
    this._$17695 = this.rdoDrawByDist.isSelected() ? 1 : 2;
    this._$17751 = (new Double(this.txtDpiX.getText())).doubleValue();
    this._$17753 = (new Integer(this.txtIntervalX.getText())).intValue();
    this._$17754 = (new Integer(this.txtBrightLine.getText())).intValue();
    this._$17752 = (new Double(this.txtDpiXD.getText())).doubleValue();
    this._$17755 = (new Integer(this.txtIntervalXD.getText())).intValue();
    this._$17756 = (new Integer(this.txtBrightLineD.getText())).intValue();
    this._$17764 = (new Integer(this.txtSpeedMax.getText())).intValue();
    this._$17765 = (new Integer(this.txtSpeedMin.getText())).intValue();
    this._$17763 = (new Double(this.txtSpeedDpi.getText())).doubleValue();
    this._$17766 = (new Integer(this.txtSpeedInterval.getText())).intValue();
    this._$17767 = this.txtSpeedMsg.getText();
    this._$17774 = (new Integer(this.txtWarnMax.getText())).intValue();
    this._$17775 = (new Integer(this.txtWarnMin.getText())).intValue();
    this._$17773 = (new Double(this.txtWarnDpi.getText())).doubleValue();
    this._$17776 = (new Integer(this.txtWarnInterval.getText())).intValue();
    this._$17777 = this.txtWarnMsg.getText();
    this._$17780 = (new Integer(this.txtBTMax.getText())).intValue();
    this._$17781 = (new Integer(this.txtBTMin.getText())).intValue();
    this._$17779 = (new Double(this.txtBTDpi.getText())).doubleValue();
    this._$17782 = (new Integer(this.txtBTInterval.getText())).intValue();
    this._$17783 = this.txtBTMsg.getText();
    this._$17786 = (new Integer(this.txtSlipMax.getText())).intValue();
    this._$17787 = (new Integer(this.txtSlipMin.getText())).intValue();
    this._$17785 = (new Double(this.txtSlipDpi.getText())).doubleValue();
    this._$17788 = (new Integer(this.txtSlipInterval.getText())).intValue();
    this._$17789 = this.txtSlipMsg.getText();
    this._$17800 = (new Integer(this.txtModeMax.getText())).intValue();
    this._$17801 = (new Integer(this.txtModeMin.getText())).intValue();
    this._$17799 = (new Double(this.txtModeDpi.getText())).doubleValue();
    this._$17802 = (new Integer(this.txtModeInterval.getText())).intValue();
    this._$17803 = this.txtModeMsg.getText();
    this._$17793 = (new Integer(this.txtEBMax.getText())).intValue();
    this._$17794 = (new Integer(this.txtEBMin.getText())).intValue();
    this._$17792 = (new Double(this.txtEBDpi.getText())).doubleValue();
    this._$17795 = (new Integer(this.txtEBInterval.getText())).intValue();
    this._$17796 = this.txtEBMsg.getText();
    this.lblUsualIncrease.setText("注: X軸格線區間為" + (this._$17751 * this._$17753) + "秒");
    this.lblWarnIncrease.setText("注: Y軸格線區間為" + (this._$17773 * this._$17776));
    this.lblBTIncrease.setText("注: Y軸格線區間為" + (this._$17779 * this._$17782));
    this.lblSlipIncrease.setText("注: Y軸格線區間為" + (this._$17785 * this._$17788));
    this.lblModeIncrease.setText("注: Y軸格線區間為" + (this._$17799 * this._$17802));
    this.lblSpeedIncrease.setText("注: Y軸格線區間為" + (this._$17763 * this._$17766));
    _$17990();
  }
  
  private void _$18014(Graphics paramGraphics) {
    DrawHistogram drawHistogram2;
    DrawMass drawMass;
    DrawRightAngle drawRightAngle;
    DrawHistogram drawHistogram1;
    DrawContinuous drawContinuous = this.dspd;
    switch (this._$17805) {
      case 0:
      case 6:
        drawContinuous = this.dspd;
        break;
      case 1:
        drawHistogram2 = this.dwrn;
        break;
      case 2:
        drawMass = this.dbt;
        break;
      case 3:
        drawRightAngle = this.dslip;
        break;
      case 4:
        drawRightAngle = this.deb;
        break;
      case 5:
        drawHistogram1 = this.dmode;
        break;
    } 
    if (this._$17806 != null)
      drawHistogram1.setMouseXY(this._$17806.getX(), this._$17806.getY()); 
  }
  
  void pnlExample_mouseMoved(MouseEvent paramMouseEvent) {
    this._$17806 = paramMouseEvent;
    repaint();
  }
  
  void rdoDrawByTime_stateChanged(ChangeEvent paramChangeEvent) {
    _$17988();
  }
  
  private void _$17988() {
    _$8475();
    if (this._$17695 == 1) {
      this.jLabel215.setVisible(true);
      this.txtDpiXD.setVisible(true);
      this.txtIntervalXD.setVisible(true);
      this.txtBrightLineD.setVisible(true);
      this.jLabel24.setVisible(false);
      this.txtDpiX.setVisible(false);
      this.txtIntervalX.setVisible(false);
      this.txtBrightLine.setVisible(false);
    } else {
      this.jLabel24.setVisible(true);
      this.txtDpiX.setVisible(true);
      this.txtIntervalX.setVisible(true);
      this.txtBrightLine.setVisible(true);
      this.jLabel215.setVisible(false);
      this.txtDpiXD.setVisible(false);
      this.txtIntervalXD.setVisible(false);
      this.txtBrightLineD.setVisible(false);
    } 
    _$17990();
  }
  
  private void _$17990() {
    try {
      drawParameters drawParameters1;
      this.scps.drawByDist = this.rdoDrawByDist.isSelected();
      this.scps.dpiX = this._$17751;
      this.scps.dpiDistX = this._$17752;
      this.scps.intervalX = this._$17753;
      this.scps.intervalDistX = this._$17755;
      this.scps.BrightLine = this._$17754;
      this.scps.baseLineColor = this._$17759;
      this.scps.baseLineColor_light = this._$17760;
      this.scps.charColor = this._$17761;
      this.scps.backgroundColor = this._$17757;
      this.scps.mainLineColor = this._$17758;
      this.scps.mouse = this._$17762;
      switch (this._$17805) {
        case 1:
          this.spara = new drawParameters();
          this.spara.message = this._$17777;
          this.spara.MaxNum = this._$17774;
          this.spara.MinNum = this._$17775;
          this.spara.dpiY = this._$17773;
          this.spara.intervalY = this._$17776;
          this.spara.drawBody = true;
          this.spara.drawValues = true;
          this.spara.UpperBound = 30;
          this.dwrn = new DrawHistogram(this.scps, this.spara, this._$17807);
          this.dwrn.setData(this._$17819);
          this.dwrn.setDataLineColor(this._$17778);
          this.dwrn.setDrawBody(true);
          this.dwrn.setDrawValue(true);
          return;
        case 2:
          this.spara = new drawParameters();
          this.spara.message = this._$17783;
          this.spara.MaxNum = this._$17780;
          this.spara.MinNum = this._$17781;
          this.spara.dpiY = this._$17779;
          this.spara.intervalY = this._$17782;
          this.spara.drawBody = true;
          this.spara.drawValues = true;
          this.spara.UpperBound = 30;
          this.dbt = new DrawMass(this.scps, this.spara, this._$17807);
          this.dbt.setData(this._$17813, this._$17813);
          this.dbt.setDataLineColor(this._$17784);
          this.dbt.setDrawBody(true);
          this.dbt.setDrawValue(true);
          return;
        case 3:
          this.spara = new drawParameters();
          this.spara.message = this._$17789;
          this.spara.MaxNum = this._$17786;
          this.spara.MinNum = this._$17787;
          this.spara.dpiY = this._$17785;
          this.spara.intervalY = this._$17788;
          this.spara.drawBody = true;
          this.spara.drawValues = true;
          this.spara.UpperBound = 30;
          this.dslip = new DrawRightAngle(this.scps, this.spara, this._$17807);
          this.dslip.setData(this._$17814);
          this.dslip.setDataLineColor(this._$17790);
          this.dslip.setDrawBody(false);
          this.dslip.setDrawValue(false);
          this.dslide = new DrawRightAngle(this.scps, this.spara, this._$17807);
          this.dslide.setData(this._$17815);
          this.dslide.setDataLineColor(this._$17791);
          this.dslide.setDrawBody(true);
          this.dslide.setDrawValue(true);
          return;
        case 4:
          this.spara = new drawParameters();
          this.spara.message = this._$17796;
          this.spara.MaxNum = this._$17793;
          this.spara.MinNum = this._$17794;
          this.spara.dpiY = this._$17792;
          this.spara.intervalY = this._$17795;
          this.spara.drawBody = false;
          this.spara.drawValues = false;
          this.spara.UpperBound = 30;
          this.deb = new DrawRightAngle(this.scps, this.spara, this._$17807);
          this.deb.setData(this._$17816);
          this.deb.setDataLineColor(this._$17797);
          drawParameters1 = (drawParameters)this.spara.clone();
          drawParameters1.drawBody = true;
          drawParameters1.drawValues = true;
          this.dsb = new DrawRightAngle(this.scps, drawParameters1, this._$17807);
          this.dsb.setData(this._$17817);
          this.dsb.setDataLineColor(this._$17798);
          return;
        case 5:
          this.spara = new drawParameters();
          this.spara.message = this._$17803;
          this.spara.MaxNum = this._$17800;
          this.spara.MinNum = this._$17801;
          this.spara.dpiY = this._$17799;
          this.spara.intervalY = this._$17802;
          this.spara.drawBody = true;
          this.spara.drawValues = true;
          this.spara.UpperBound = 30;
          this.dmode = new DrawHistogram(this.scps, this.spara, this._$17807);
          this.dmode.setData(this._$17818);
          this.dmode.setDataLineColor(this._$17804);
          this.dmode.setDrawBody(true);
          this.dmode.setDrawValue(true);
          return;
      } 
      this.spara = new drawParameters();
      this.spara.message = this._$17767;
      this.spara.MaxNum = this._$17764;
      this.spara.MinNum = this._$17765;
      this.spara.dpiY = this._$17763;
      this.spara.intervalY = this._$17766;
      this.spara.drawBody = false;
      this.spara.drawValues = false;
      this.spara.UpperBound = 30;
      drawParameters drawParameters2 = (drawParameters)this.spara.clone();
      drawParameters2.drawBody = true;
      drawParameters2.drawValues = true;
      this.dad = new DrawContinuous(this.scps, drawParameters2, this._$17807);
      this.dad.setData(this._$17812);
      this.dad.setDataLineColor(this._$17772);
      this.dpspd = new DrawBreakOff(this.scps, this.spara, this._$17807);
      this.dpspd.setData(this._$17810);
      this.dpspd.setDataLineColor(this._$17769);
      this.dpspd.setDrawBody(false);
      this.dpspd.setDrawValue(false);
      this.dtspd = new DrawBreakOff(this.scps, this.spara, this._$17807);
      this.dtspd.setData(this._$17811);
      this.dtspd.setDataLineColor(this._$17771);
      this.dtspd.setDrawBody(false);
      this.dtspd.setDrawValue(false);
      this.drspd = new DrawBreakOff(this.scps, this.spara, this._$17807);
      this.drspd.setData(this._$17809);
      this.drspd.setDataLineColor(this._$17770);
      this.drspd.setDrawBody(false);
      this.drspd.setDrawValue(false);
      this.dspd = new DrawContinuous(this.scps, this.spara, this._$17807);
      this.dspd.setData(this._$17808);
      this.dspd.setDataLineColor(this._$17768);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void resetColor(PropertyChangeEvent paramPropertyChangeEvent) {
    String str = paramPropertyChangeEvent.getPropertyName();
    System.out.println("NAME " + paramPropertyChangeEvent.getPropertyName() + "\nOLDVALUE " + paramPropertyChangeEvent.getOldValue() + "\nNEWVALUE " + paramPropertyChangeEvent.getNewValue());
    Object object = paramPropertyChangeEvent.getNewValue();
    if (str == this.pnlSpeedChooser.getName()) {
      this._$17768 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlPermittedSPDChooser.getName()) {
      this._$17769 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlReleaseSPDChooser.getName()) {
      this._$17770 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlTargetSPDChooser.getName()) {
      this._$17771 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlAdhesionChooser.getName()) {
      this._$17772 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlModeChooser.getName()) {
      this._$17804 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlEBChooser.getName()) {
      this._$17797 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlSBChooser.getName()) {
      this._$17798 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlSlipChooser.getName()) {
      this._$17790 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlSlideChooser.getName()) {
      this._$17791 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlBrakeTargetChooser.getName()) {
      this._$17784 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlWarnChooser.getName()) {
      this._$17778 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlBaseLineChooser.getName()) {
      this._$17759 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlGridLineChooser.getName()) {
      this._$17760 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlBorderLineChooser.getName()) {
      this._$17758 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlBackgroundChooser.getName()) {
      this._$17757 = (Color)object;
      _$18009(this._$17757);
      this.changes = true;
    } 
    if (str == this.pnlMouseChooser.getName()) {
      this._$17762 = (Color)object;
      this.changes = true;
    } 
    if (str == this.pnlCharChooser.getName()) {
      this._$17761 = (Color)object;
      this.changes = true;
    } 
    System.out.println("get change String >> " + str);
    _$17990();
  }
  
  private void _$18009(Color paramColor) {
    this.pnlExample.setBackground(paramColor);
    for (byte b = 0; b < this.exGroup.length; b++)
      this.exGroup[b].setBackGroundColor(paramColor); 
  }
  
  void txtNewValue_keyPressed(KeyEvent paramKeyEvent) {
    this.changes = true;
    if (paramKeyEvent.getKeyCode() == 10) {
      ((Component)paramKeyEvent.getSource()).transferFocus();
      _$8475();
    } 
  }
  
  void txtParaField_focusLost(FocusEvent paramFocusEvent) {
    _$8475();
  }
}
