package ui.panels;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.core.ATPMission;
import com.MiTAC.TRA.ATP.core.ATPMissionDetail;
import com.MiTAC.TRA.ATP.core.ATPMissionFailure;
import com.MiTAC.TRA.ATP.drawGraphics.DrawBaliseInfo;
import com.MiTAC.TRA.ATP.drawGraphics.DrawBase;
import com.MiTAC.TRA.ATP.drawGraphics.DrawBreakOff;
import com.MiTAC.TRA.ATP.drawGraphics.DrawContinuous;
import com.MiTAC.TRA.ATP.drawGraphics.DrawCurve;
import com.MiTAC.TRA.ATP.drawGraphics.DrawDescription;
import com.MiTAC.TRA.ATP.drawGraphics.DrawDriverMessage;
import com.MiTAC.TRA.ATP.drawGraphics.DrawFailureMsg;
import com.MiTAC.TRA.ATP.drawGraphics.DrawGradient;
import com.MiTAC.TRA.ATP.drawGraphics.DrawHistogram;
import com.MiTAC.TRA.ATP.drawGraphics.DrawMass;
import com.MiTAC.TRA.ATP.drawGraphics.DrawRightAngle;
import com.MiTAC.TRA.ATP.drawGraphics.DrawTrainData;
import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import com.MiTAC.TRA.ATP.ui.utils.PrintablePanel;
import com.MiTAC.TRA.ATP.ui.adapters.pnlDriverBehavior_btn_actionAdapter;
import com.MiTAC.TRA.ATP.ui.pnlDriverBehavior_drawType_changeAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlDriverBehavior_jPanel1_mouseAdapter;
import com.MiTAC.TRA.ATP.ui.adapters.pnlDriverBehavior_mouseMotionAdapter;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.print.PrintService;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class pnlDriverBehavior extends JPanel implements ChangeListener, ItemListener {
  protected bodyPanel Body = new bodyPanel(this);
  
  protected headerPanel Hd = new headerPanel(this);
  
  Vector accdatax;
  
  Vector accdatay;
  
  Vector acctmp;
  
  int bodyHeight = 0;
  
  BorderLayout borderLayout2 = new BorderLayout();
  
  JButton btnOrgiZoom = new JButton();
  
  JButton btnPrintLarge = new JButton();
  
  JButton btnPrintSmall = new JButton();
  
  JButton btnSaveLarge = new JButton();
  
  JButton btnSaveSmall = new JButton();
  
  JButton btnZoom = new JButton();
  
  JButton btnZoomIn = new JButton();
  
  ButtonGroup buttonGroup1 = new ButtonGroup();
  
  Vector category;
  
  public static final String checkWindowOpenfile = "C:\\ATPMW\\windowsChecking";
  
  JCheckBox chxAdhesion = new JCheckBox();
  
  JCheckBox chxBalise = new JCheckBox();
  
  JCheckBox chxBrakeTarget = new JCheckBox();
  
  JCheckBox chxCabinFailure = new JCheckBox();
  
  JCheckBox chxCurve = new JCheckBox();
  
  JCheckBox chxDescription = new JCheckBox();
  
  JCheckBox chxDriverMessage = new JCheckBox();
  
  JCheckBox chxEB = new JCheckBox();
  
  JCheckBox chxGradient = new JCheckBox();
  
  JCheckBox chxGroup1 = new JCheckBox();
  
  JCheckBox chxGroup2 = new JCheckBox();
  
  JCheckBox chxGroup3 = new JCheckBox();
  
  JCheckBox chxMode = new JCheckBox();
  
  JCheckBox chxMouse = new JCheckBox();
  
  JCheckBox chxPmSpeed = new JCheckBox();
  
  JCheckBox chxPrintMode = new JCheckBox();
  
  JCheckBox chxRsSpeed = new JCheckBox();
  
  JCheckBox chxSB = new JCheckBox();
  
  JCheckBox chxSimpleMode = new JCheckBox();
  
  JCheckBox chxSlide = new JCheckBox();
  
  JCheckBox chxSlip = new JCheckBox();
  
  JCheckBox chxTgSpeed = new JCheckBox();
  
  JCheckBox chxTrainData = new JCheckBox();
  
  JCheckBox chxWarn = new JCheckBox();
  
  JCheckBox chxWaysideFailure = new JCheckBox();
  
  private DrawRightAngle _$26032;
  
  private DrawBaliseInfo _$26034;
  
  private DrawMass _$26033;
  
  private DrawFailureMsg _$26036;
  
  private DrawCurve _$26039;
  
  private DrawDescription _$2586;
  
  private DrawDriverMessage _$26037;
  
  private DrawRightAngle _$8363;
  
  private DrawGradient _$26040;
  
  private DrawHistogram _$17927;
  
  private double _$8451;
  
  private double _$8450;
  
  private double[] _$18000 = new double[20];
  
  private DrawHistogram _$26031;
  
  private DrawBreakOff _$17918;
  
  private boolean _$26000 = true;
  
  private boolean _$26012 = false;
  
  private boolean _$26001 = true;
  
  private boolean _$26014 = true;
  
  private boolean _$18218 = true;
  
  private boolean _$26003 = false;
  
  private boolean _$26015 = true;
  
  private boolean _$26008 = true;
  
  private boolean _$26004 = true;
  
  private boolean _$26007 = true;
  
  private boolean _$26017 = true;
  
  private boolean _$26018 = true;
  
  private DrawBase[] _$26043 = new DrawBase[20];
  
  private boolean _$26011 = true;
  
  private boolean _$26002 = true;
  
  private drawParameters[] _$26044 = new drawParameters[20];
  
  private boolean _$25998 = true;
  
  private boolean _$25997 = true;
  
  private boolean _$26009 = true;
  
  private boolean _$26006 = true;
  
  private boolean _$26005 = true;
  
  private boolean _$25999 = true;
  
  private boolean _$26016 = true;
  
  private boolean _$26010 = true;
  
  private boolean _$26013 = true;
  
  private DrawBreakOff _$17919;
  
  private DrawRightAngle _$17926;
  
  private DrawRightAngle _$17925;
  
  private DrawRightAngle _$17924;
  
  private DrawContinuous _$17917;
  
  private DrawTrainData _$26038;
  
  private DrawBreakOff _$17920;
  
  private DrawHistogram _$26030;
  
  private DrawFailureMsg _$26035;
  
  private Vector _$26022 = new Vector();
  
  JLabel lblAMAcc = new JLabel();
  
  JLabel lblAMDec = new JLabel();
  
  JLabel lblDID = new JLabel();
  
  JLabel lblDIDTag = new JLabel();
  
  JLabel lblEndTime = new JLabel();
  
  JLabel lblEndTimeTag = new JLabel(ATPMessages.getString("MW.WS.END_TIME"));
  
  JLabel lblFileName = new JLabel();
  
  JLabel lblFileNameTag = new JLabel(ATPMessages.getString("MW.BEHAVIOR.FILE.NAME") + ": ");
  
  JLabel lblFileSaveResult = new JLabel(ATPMessages.getString("MW.BEHAVIOR.FILE.EXCEPT"));
  
  JLabel lblFileSaveType = new JLabel(ATPMessages.getString("MW.BEHAVIOR.SAVE"));
  
  JLabel lblLen = new JLabel();
  
  JLabel lblMTType = new JLabel();
  
  JLabel lblMissionDate = new JLabel();
  
  JLabel lblMissionDateTag = new JLabel();
  
  JLabel lblPrintType = new JLabel(ATPMessages.getString("MW.BEHAVIOR.PRINT"));
  
  JLabel lblStartTime = new JLabel();
  
  JLabel lblStartTimeTag = new JLabel(ATPMessages.getString("MW.WS.START_TIME"));
  
  JLabel lblTCut = new JLabel();
  
  JLabel lblTEB = new JLabel();
  
  JLabel lblTRNo = new JLabel();
  
  JLabel lblTRNoTag = new JLabel();
  
  JLabel lblTSB = new JLabel();
  
  JLabel lblUserDefineTime1 = new JLabel();
  
  JLabel lblUserDefineTime1Tag = new JLabel(ATPMessages.getString("MW.BEHAVIOR.SET_TIME1"));
  
  JLabel lblUserDefineTime2 = new JLabel();
  
  JLabel lblUserDefineTime2Tag = new JLabel(ATPMessages.getString("MW.BEHAVIOR.SET_TIME2"));
  
  JLabel lblVID = new JLabel();
  
  JLabel lblVIDTag = new JLabel();
  
  JLabel lblVMax = new JLabel();
  
  JLabel lblVMax10 = new JLabel();
  
  JLabel lblVMax15 = new JLabel();
  
  JLabel lblVMax20 = new JLabel();
  
  JLabel lblVMax5 = new JLabel();
  
  JLabel lblWSNo = new JLabel();
  
  JLabel lblWSNoTag = new JLabel();
  
  private messagePanel _$26029 = new messagePanel(this);
  
  private String _$2038 = "";
  
  private ATPMissionDetail _$26045;
  
  PrintablePanel pnlBody = new PrintablePanel();
  
  JPanel pnlHeader = new JPanel();
  
  public static final int printLarge = 1;
  
  public static final int printSmall = 0;
  
  public int printType = 0;
  
  JRadioButton rdoDrawByDist = new JRadioButton();
  
  JRadioButton rdoDrawByTime = new JRadioButton();
  
  protected commonParaSetting scps = new commonParaSetting();
  
  JScrollPane scroll;
  
  TitledBorder titledBorder1;
  
  TitledBorder titledBorder2;
  
  TitledBorder titledBorder3;
  
  TitledBorder titledBorder4;
  
  JTextArea txtUserDescription = new JTextArea();
  
  private Vector _$26023 = new Vector();
  
  private JSlider _$26041 = new JSlider(1, 20, 11);
  
  private JSlider _$26042 = new JSlider(1, 20, 11);
  
  public pnlDriverBehavior(ATPMission paramATPMission, long paramLong) {
    try {
      _$3120();
      this._$26045 = new ATPMissionDetail(paramATPMission);
      this.category = this._$26045.getCategory().get(0);
      this.pnlBody.setMessage(this._$26045, this._$2038, this);
      long l1 = 180000L;
      long l2 = paramLong - l1;
      long l3 = paramLong + l1;
      zoomChange(l2, l3);
      this._$26022.add(_$26110(this._$17917.showsoldierWhereToStand(paramLong), 0).clone());
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
      this.lblFileName.setText(simpleDateFormat.format(this._$26045.getMissionDate()) + "_" + this._$26045.getWorkShift() + "_" + this._$26045.getTrainRunning() + "_" + this._$26045.getDriver() + "_" + this._$26045.getVehicle() + "_");
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public pnlDriverBehavior(ATPMission paramATPMission, long paramLong1, long paramLong2, String paramString) {
    try {
      _$3120();
      this._$26045 = new ATPMissionDetail(paramATPMission);
      this.category = this._$26045.getCategory().get(0);
      this._$2038 = paramString;
      this.pnlBody.setMessage(this._$26045, this._$2038, this);
      zoomChange(paramLong1, paramLong2);
      this._$26022.add(_$26110(this._$17917.showsoldierWhereToStand(paramLong1), 0).clone());
      this._$26022.add(_$26110(this._$17917.showsoldierWhereToStand(paramLong2), 0).clone());
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
      this.lblFileName.setText(simpleDateFormat.format(this._$26045.getMissionDate()) + "_" + this._$26045.getWorkShift() + "_" + this._$26045.getTrainRunning() + "_" + this._$26045.getDriver() + "_" + this._$26045.getVehicle() + "_");
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public pnlDriverBehavior(ATPMission paramATPMission) {
    try {
      _$3120();
      this._$26045 = new ATPMissionDetail(paramATPMission);
      this.category = this._$26045.getCategory().get(0);
      this.pnlBody.setMessage(this._$26045, this._$2038, this);
      zoomChange(this._$26045.getMissionStartTime().getTime(), this._$26045.getMissionEndTime().getTime());
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
      this.lblFileName.setText(simpleDateFormat.format(this._$26045.getMissionDate()) + "_" + this._$26045.getWorkShift() + "_" + this._$26045.getTrainRunning() + "_" + this._$26045.getDriver() + "_" + this._$26045.getVehicle() + "_");
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private int _$26168(int paramInt1, int paramInt2) {
    int i;
    Dimension dimension = getSize();
    int j = this.scroll.getHorizontalScrollBar().getValue();
    int k = (int)(dimension.getWidth() - this._$26029.getWidth() - this.Hd.getWidth());
    int m = j + k;
    int n = paramInt1 - j;
    if (n > k / 2) {
      i = j;
    } else {
      i = m - this._$2586.getWidth();
    } 
    return i;
  }
  
  private int[] _$26158(int paramInt1, int paramInt2) {
    int[] arrayOfInt = new int[2];
    Dimension dimension = getSize();
    int i = this.scroll.getHorizontalScrollBar().getValue();
    int j = this.scroll.getVerticalScrollBar().getValue();
    int k = paramInt1 - i;
    int m = paramInt2 - j;
    short s = 0;
    byte b = 0;
    if (dimension.getWidth() - k - 350.0D < 0.0D)
      s = -170; 
    if (m - 10 < 0)
      b = 85; 
    arrayOfInt[0] = s;
    arrayOfInt[1] = b;
    return arrayOfInt;
  }
  
  void btnOrgiZoom_actionPerformed(ActionEvent paramActionEvent) {
    this._$26022.clear();
    zoomChange(this._$26045.getMissionStartTime().getTime(), this._$26045.getMissionEndTime().getTime());
  }
  
  void btnPrint_actionPerformed(ActionEvent paramActionEvent) {
    if (paramActionEvent.getActionCommand() == this.btnPrintLarge.getActionCommand()) {
      this.printType = 1;
    } else {
      this.printType = 0;
    } 
    PrinterJob printerJob = PrinterJob.getPrinterJob();
    PrintService printService = printerJob.getPrintService();
    if (printService == null) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.BEHAVIOR.NO_PRINTER"), ATPMessages.getString("MW.GNL.ERROR"), 0);
      return;
    } 
    try {
      PageFormat pageFormat = printerJob.defaultPage();
      Paper paper = new Paper();
      double d = 40.0D;
      paper.setSize(595.2D, 841.6D);
      paper.setImageableArea(d, d, paper.getWidth() - d * 2.0D, paper.getHeight() - d * 2.0D);
      if (this.printType == 1) {
        this.scroll.getHorizontalScrollBar().setValue(0);
      } else {
        this.scroll.setViewportView((Component)this.Body);
      } 
      pageFormat.setOrientation(0);
      pageFormat.setPaper(paper);
      printerJob.setPrintable((Printable)this.pnlBody, pageFormat);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    if (printerJob.printDialog())
      try {
        printerJob.print();
      } catch (PrinterException printerException) {
        printerException.printStackTrace();
        JOptionPane.showMessageDialog(this, printerException.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
      }  
  }
  
  void btnSaveLarge_actionPerformed(ActionEvent paramActionEvent) {
    _$26206(0);
  }
  
  void btnSaveSmall_actionPerformed(ActionEvent paramActionEvent) {
    _$26206(1);
  }
  
  void btnZoomIn_actionPerformed(ActionEvent paramActionEvent) {
    if (this._$26022.size() == 0) {
      JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.BEHAVIOR.ZOOM.NEED_A_POINT_TO_ZOOM"), ATPMessages.getString("MW.Error"), 0);
    } else {
      Date date1;
      Date date2;
      if (this._$26022.size() == 1) {
        Vector vector = this._$26022.get(0);
        date1 = vector.get(1);
        date2 = this._$26045.getUserDefineEndTime();
      } else {
        Vector vector1 = this._$26022.get(0);
        Vector vector2 = this._$26022.get(1);
        date1 = vector1.get(1);
        date2 = vector2.get(1);
        if (date1.after(date2)) {
          date1 = vector2.get(1);
          date2 = vector1.get(1);
        } 
      } 
      if (date1.equals(date2) || date2.getTime() - date1.getTime() < 90000L) {
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.BEHAVIOR.ZOOM.POINTS_TOO_CLOSE"), ATPMessages.getString("MW.Error"), 0);
      } else {
        this._$26022.clear();
        zoomChange(date1.getTime(), date2.getTime());
      } 
    } 
  }
  
  void btnZoom_actionPerformed(ActionEvent paramActionEvent) {
    long l = 600000L;
    zoomChange(this._$26045.getUserDefineStartTime().getTime() + l, this._$26045.getUserDefineEndTime().getTime() - l);
  }
  
  private void _$26155(Graphics paramGraphics) {
    for (byte b = 0; b < this._$26022.size(); b++) {
      Vector vector = this._$26022.get(b);
      int i = ((int[])vector.get(0))[0];
      if (b == 0 || b == 1) {
        paramGraphics.setColor(Color.PINK);
        int[] arrayOfInt1 = { i, i, i - 24 };
        int[] arrayOfInt2 = { 0, 24, 12 };
        paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, 3);
        paramGraphics.setColor(Color.BLACK);
        paramGraphics.drawString("" + (b + 1), i - 6, 14);
      } 
      int j = _$26158(i, 0)[0];
      byte b1 = 14;
      int k = i;
      paramGraphics.setColor(InitParameters.SpeedColor);
      paramGraphics.drawString(vector.get(2), i + j, 14);
      byte b2 = 0;
      if (this._$25998) {
        b1 += 13;
        paramGraphics.setColor(InitParameters.PmSpeedColor);
        paramGraphics.drawString(vector.get(3), k + j, b1);
        b2++;
      } 
      if (this._$25997) {
        b1 += 13;
        paramGraphics.setColor(InitParameters.RsSpeedColor);
        paramGraphics.drawString(vector.get(4), k + j, b1);
        b2++;
      } 
      if (this._$25999) {
        b1 += 13;
        paramGraphics.setColor(InitParameters.TgSpeedColor);
        paramGraphics.drawString(vector.get(5), k + j, b1);
        b2++;
      } 
      if (this._$26000) {
        b1 += 13;
        paramGraphics.setColor(InitParameters.AhSpeedColor);
        paramGraphics.drawString(vector.get(6), k + j, b1);
        b2++;
      } 
      if (this._$26010) {
        if (b2 == 4) {
          k = i + 90;
          b1 = 14;
        } else {
          b1 += 13;
        } 
        paramGraphics.setColor(InitParameters.WarnColor);
        paramGraphics.drawString(vector.get(7), k + j, b1);
        b2++;
      } 
      if (this._$26008) {
        if (b2 == 4) {
          k = i + 90;
          b1 = 14;
        } else {
          b1 += 13;
        } 
        paramGraphics.setColor(InitParameters.EBColor);
        paramGraphics.drawString(vector.get(8), k + j, b1);
        b2++;
      } 
      if (this._$26009) {
        if (b2 == 4) {
          k = i + 90;
          b1 = 14;
        } else {
          b1 += 13;
        } 
        paramGraphics.setColor(InitParameters.SBColor);
        paramGraphics.drawString(vector.get(9), k + j, b1);
        b2++;
      } 
      if (this._$26011) {
        if (b2 == 4) {
          k = i + 90;
          b1 = 14;
        } else {
          b1 += 13;
        } 
        paramGraphics.setColor(InitParameters.ModeColor);
        paramGraphics.drawString(vector.get(10), k + j, b1);
        b2++;
      } 
      if (b2 == 4) {
        k = i + 90;
        b1 = 14;
      } else {
        b1 += 13;
      } 
      paramGraphics.setColor(InitParameters.ACCColor);
      paramGraphics.drawString(vector.get(11), k + j, b1);
      b2++;
      Color color = InitParameters.MarkedColor;
      paramGraphics.setColor(color);
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
      if (b2 > 4) {
        paramGraphics.fillRect(i + j, 0, 170, 80);
        paramGraphics.setColor(InitParameters.Char);
        paramGraphics.drawString(simpleDateFormat.format((Date)vector.get(1)), i + j, 79);
        paramGraphics.setColor(color.brighter());
        paramGraphics.drawLine(i, 67, i, 1000);
      } else if (b2 == 4) {
        paramGraphics.fillRect(i + j, 0, 85, 80);
        paramGraphics.setColor(InitParameters.Char);
        paramGraphics.drawString(simpleDateFormat.format((Date)vector.get(1)), i + j, 79);
        paramGraphics.setColor(color.brighter());
        paramGraphics.drawLine(i, 67, i, 1000);
      } else {
        paramGraphics.fillRect(i + j, 0, 85, 80 - 13 * (4 - b2));
        paramGraphics.setColor(InitParameters.Char);
        paramGraphics.drawString(simpleDateFormat.format((Date)vector.get(1)), i + j, 79 - 13 * (4 - b2));
        paramGraphics.setColor(color.brighter());
        paramGraphics.drawLine(i, 67 - 13 * (4 - b2), i, 1000);
      } 
      paramGraphics.setColor(color);
      paramGraphics.fillRect(i - 2, 0, 5, 1000);
      this._$26034.drawStopInformation((Date)vector.get(1));
    } 
  }
  
  public void drawNoteMessage(Graphics paramGraphics) {
    paramGraphics.setColor(Color.red);
    paramGraphics.drawString(this._$2038, 12, 12);
  }
  
  void drawType_stateChanged(ChangeEvent paramChangeEvent) {
    boolean bool = true;
    if (this.rdoDrawByDist.isSelected())
      bool = false; 
    for (byte b = 0; b < this._$26043.length; b++)
      this._$26043[b].setDrawType(bool); 
    this.Body.setPreferredSize(new Dimension(this._$26043[0].showBattleLingDepth() + 70, 30));
    this.scroll.setViewportView((Component)this.Body);
    repaint();
  }
  
  private void _$26156(Graphics paramGraphics) {
    try {
      if (this._$26023 != null && this._$26023.size() != 0) {
        int i = ((int[])this._$26023.get(0))[0] + 10;
        int j = ((int[])this._$26023.get(0))[1] - 77;
        if (this._$26002) {
          int[] arrayOfInt = _$26158(i, j);
          int k = arrayOfInt[0];
          int m = arrayOfInt[1];
          byte b1 = 14;
          byte b2 = 0;
          byte b3 = 0;
          paramGraphics.setColor(InitParameters.SpeedColor);
          paramGraphics.drawString(this._$26023.get(2), i + b2 + k, j + b1 + m);
          if (this._$25998 && this._$26023.get(3) != null) {
            b1 += 13;
            paramGraphics.setColor(InitParameters.PmSpeedColor);
            paramGraphics.drawString(this._$26023.get(3), i + b2 + k, j + b1 + m);
            b3++;
          } 
          if (this._$25997 && this._$26023.get(4) != null) {
            b1 += 13;
            paramGraphics.setColor(InitParameters.RsSpeedColor);
            paramGraphics.drawString(this._$26023.get(4), i + b2 + k, j + b1 + m);
            b3++;
          } 
          if (this._$25999 && this._$26023.get(5) != null) {
            b1 += 13;
            paramGraphics.setColor(InitParameters.TgSpeedColor);
            paramGraphics.drawString(this._$26023.get(5), i + b2 + k, j + b1 + m);
            b3++;
          } 
          if (this._$26000 && this._$26023.get(6) != null) {
            b1 += 13;
            paramGraphics.setColor(InitParameters.AhSpeedColor);
            paramGraphics.drawString(this._$26023.get(6), i + b2 + k, j + b1 + m);
            b3++;
          } 
          if (this._$26010 && this._$26023.get(7) != null) {
            if (b3 == 4) {
              b1 = 14;
              b2 = 90;
            } else {
              b1 += 13;
            } 
            paramGraphics.setColor(InitParameters.WarnColor);
            paramGraphics.drawString(this._$26023.get(7), i + b2 + k, j + b1 + m);
            b3++;
          } 
          if (this._$26008 && this._$26023.get(8) != null) {
            if (b3 == 4) {
              b1 = 14;
              b2 = 90;
            } else {
              b1 += 13;
            } 
            paramGraphics.setColor(InitParameters.EBColor);
            paramGraphics.drawString(this._$26023.get(8), i + b2 + k, j + b1 + m);
            b3++;
          } 
          if (this._$26009 && this._$26023.get(9) != null) {
            if (b3 == 4) {
              b1 = 14;
              b2 = 90;
            } else {
              b1 += 13;
            } 
            paramGraphics.setColor(InitParameters.SBColor);
            paramGraphics.drawString(this._$26023.get(9), i + b2 + k, j + b1 + m);
            b3++;
          } 
          if (this._$26011 && this._$26023.get(10) != null) {
            if (b3 == 4) {
              b1 = 14;
              b2 = 90;
            } else {
              b1 += 13;
            } 
            paramGraphics.setColor(InitParameters.ModeColor);
            paramGraphics.drawString(this._$26023.get(10), i + b2 + k, j + b1 + m);
            b3++;
          } 
          if (b3 == 4) {
            b1 = 14;
            b2 = 90;
          } else {
            b1 += 13;
          } 
          paramGraphics.setColor(InitParameters.ACCColor);
          paramGraphics.drawString(this._$26023.get(11), i + b2 + k, j + b1 + m);
          b3++;
          paramGraphics.setColor(InitParameters.MarkedColor);
          if (b3 > 4) {
            paramGraphics.fillRect(i + k, j + m, 170, 67);
          } else if (b3 == 4) {
            paramGraphics.fillRect(i + k, j + m, 85, 67);
          } else {
            paramGraphics.fillRect(i + k, j + m, 85, 67 - 13 * (4 - b3));
          } 
        } 
      } 
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      arrayIndexOutOfBoundsException.printStackTrace();
    } 
  }
  
  private void _$26115() {
    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
    this.lblMissionDate.setText(simpleDateFormat1.format(this._$26045.getMissionDate()));
    this.lblWSNo.setText(this._$26045.getWorkShift());
    this.lblTRNo.setText(this._$26045.getTrainRunning());
    this.lblDID.setText(this._$26045.getDriver() + "(" + this._$26045.getDriverName() + ")");
    this.lblVID.setText(this._$26045.getVehicle());
    this.lblLen.setText(this.category.get(6).toString());
    this.lblVMax.setText(this.category.get(7).toString());
    this.lblTSB.setText(this.category.get(9).toString());
    this.lblTEB.setText(this.category.get(10).toString());
    this.lblTCut.setText(this.category.get(11).toString());
    this.lblAMAcc.setText(this.category.get(12).toString());
    this.lblAMDec.setText(this.category.get(13).toString());
    this.lblMTType.setText(this.category.get(14).toString());
    this.lblVMax5.setText(this.category.get(15).toString());
    this.lblVMax10.setText(this.category.get(16).toString());
    this.lblVMax15.setText(this.category.get(17).toString());
    this.lblVMax20.setText(this.category.get(18).toString());
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
    this.lblUserDefineTime1.setText(simpleDateFormat2.format(this._$26045.getUserDefineStartTime()));
    this.lblUserDefineTime2.setText(simpleDateFormat2.format(this._$26045.getUserDefineEndTime()));
    this.lblStartTime.setText(simpleDateFormat2.format(this._$26045.getMissionStartTime()));
    this.lblEndTime.setText(simpleDateFormat2.format(this._$26045.getMissionEndTime()));
    this.acctmp = this._$26045.getAcceleration();
    this.accdatax = new Vector();
    this.accdatay = new Vector();
    for (byte b = 0; b < this.acctmp.size(); b++) {
      Vector vector = this.acctmp.get(b);
      this.accdatax.add(vector.get(0));
      this.accdatay.add(vector.get(1));
    } 
  }
  
  private int _$26215(Graphics2D paramGraphics2D, int paramInt) {
    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
    char c1 = Character.MIN_VALUE;
    int i = 20;
    char c2 = '\024';
    char c3 = '';
    char c4 = 'Ĭ';
    char c5 = 'Ų';
    byte b = 16;
    paramGraphics2D.setBackground(Color.WHITE);
    paramGraphics2D.setColor(Color.BLACK);
    Font font = paramGraphics2D.getFont();
    paramGraphics2D.setFont(new Font("Default", 1, 16));
    paramGraphics2D.drawString(ATPMessages.getString("MW.TRA"), c1, i);
    paramGraphics2D.setFont(new Font("Default", 1, 14));
    paramGraphics2D.drawString(ATPMessages.getString("MW.ATP"), c1 + 140, i);
    paramGraphics2D.drawString(ATPMessages.getString("MW.BEHAVIOR.SPEED_MONITOR"), c1 + 300, i);
    paramGraphics2D.setFont(new Font("Default", 1, 10));
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    paramGraphics2D.drawString(ATPMessages.getString("MW.BEHAVIOR.PRINT_DATE") + ": " + simpleDateFormat2.format(date), paramInt - 240, i);
    paramGraphics2D.setFont(font);
    i += b;
    c1 = c2;
    paramGraphics2D.drawString(ATPMessages.getString("MW.DRIVER.ID"), c1, i);
    c1 = c3;
    paramGraphics2D.drawString(this._$26045.getDriver() + "(" + this._$26045.getDriverName() + ")", c1, i);
    i += b;
    c1 = c2;
    paramGraphics2D.drawString(ATPMessages.getString("MW.GNL.DATE") + "(yyyy/MM/dd)", c1, i);
    c1 = c3;
    paramGraphics2D.drawString(simpleDateFormat1.format(this._$26045.getMissionDate()), c1, i);
    c1 = c4;
    paramGraphics2D.drawString(ATPMessages.getString("MW.WS.START_TIME"), c1, i);
    c1 = c5;
    paramGraphics2D.drawString(simpleDateFormat2.format(this._$26045.getMissionStartTime()), c1, i);
    i += b;
    c1 = c2;
    paramGraphics2D.drawString(ATPMessages.getString("MW.WS.ID"), c1, i);
    c1 = c3;
    paramGraphics2D.drawString(this._$26045.getWorkShift(), c1, i);
    c1 = c4;
    paramGraphics2D.drawString(ATPMessages.getString("MW.WS.END_TIME"), c1, i);
    c1 = c5;
    paramGraphics2D.drawString(simpleDateFormat2.format(this._$26045.getMissionEndTime()), c1, i);
    i += b;
    c1 = c2;
    paramGraphics2D.drawString(ATPMessages.getString("MW.TR.ID"), c1, i);
    c1 = c3;
    paramGraphics2D.drawString(this._$26045.getTrainRunning(), c1, i);
    i += b;
    c1 = c2;
    paramGraphics2D.drawString(ATPMessages.getString("MW.VEHICLE.ID"), c1, i);
    c1 = c3;
    paramGraphics2D.drawString(this._$26045.getVehicle(), c1, i);
    return i;
  }
  
  private void _$3120() throws Exception {
    Thread.currentThread().setPriority(1);
    this.titledBorder1 = new TitledBorder("");
    this.titledBorder2 = new TitledBorder("");
    this.titledBorder3 = new TitledBorder("");
    this.titledBorder4 = new TitledBorder("");
    setLayout(new BorderLayout());
    this.txtUserDescription.setSize(new Dimension(this.lblFileName.getHeight(), 100));
    this.lblMissionDateTag.setHorizontalAlignment(10);
    this.lblMissionDateTag.setText(ATPMessages.getString("MW.LOG.RUNNINGDATE"));
    this.lblMissionDate.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblWSNoTag.setText(ATPMessages.getString("MW.WS.ID"));
    this.lblWSNo.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblTRNoTag.setText(ATPMessages.getString("MW.TR.ID"));
    this.lblTRNo.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblDIDTag.setText(ATPMessages.getString("MW.DRIVER.ID"));
    this.lblDID.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblVIDTag.setText(ATPMessages.getString("MW.VEHICLE.ID"));
    this.lblVID.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblStartTime.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblStartTime.setPreferredSize(new Dimension(55, 17));
    this.lblUserDefineTime1.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblUserDefineTime1.setPreferredSize(new Dimension(55, 17));
    this.lblUserDefineTime2.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblUserDefineTime2.setPreferredSize(new Dimension(55, 17));
    this.lblEndTime.setBorder(BorderFactory.createLineBorder(Color.black));
    this.lblEndTime.setPreferredSize(new Dimension(55, 17));
    this.rdoDrawByTime.setSelected(!InitParameters.DrawByDist);
    this.rdoDrawByTime.setText(ATPMessages.getString("MW.BEHAVIOR.TIME_EXTEND"));
    this.rdoDrawByTime.addChangeListener((ChangeListener)new pnlDriverBehavior_drawType_changeAdapter(this));
    this.rdoDrawByDist.setSelected(InitParameters.DrawByDist);
    this.rdoDrawByDist.setText(ATPMessages.getString("MW.BEHAVIOR.LOCATION_EXTEND"));
    this.rdoDrawByDist.addChangeListener((ChangeListener)new pnlDriverBehavior_drawType_changeAdapter(this));
    this.chxPmSpeed.setSelected(this._$25998);
    this.chxPmSpeed.addItemListener(this);
    this.chxTgSpeed.setSelected(this._$25999);
    this.chxTgSpeed.addItemListener(this);
    this.chxRsSpeed.setSelected(this._$25997);
    this.chxRsSpeed.addItemListener(this);
    this.chxAdhesion.setSelected(this._$26000);
    this.chxAdhesion.addItemListener(this);
    this.chxMode.setSelected(this._$26011);
    this.chxMode.addItemListener(this);
    this.chxWarn.setSelected(this._$26010);
    this.chxWarn.addItemListener(this);
    this.chxSlip.setSelected(this._$26005);
    this.chxSlip.addItemListener(this);
    this.chxSlide.setSelected(this._$26006);
    this.chxSlide.addItemListener(this);
    this.chxEB.setSelected(this._$26008);
    this.chxEB.addItemListener(this);
    this.chxSB.setSelected(this._$26009);
    this.chxSB.addItemListener(this);
    this.chxBrakeTarget.setSelected(this._$26012);
    this.chxBrakeTarget.addItemListener(this);
    this.chxBalise.setSelected(this._$26001);
    this.chxBalise.addItemListener(this);
    this.chxWaysideFailure.setSelected(this._$26013);
    this.chxWaysideFailure.addItemListener(this);
    this.chxCabinFailure.setSelected(this._$26014);
    this.chxCabinFailure.addItemListener(this);
    this.chxDriverMessage.setSelected(this._$26015);
    this.chxDriverMessage.addItemListener(this);
    this.chxMouse.setSelected(this._$26002);
    this.chxMouse.addItemListener(this);
    this.chxDescription.setSelected(this._$26003);
    this.chxDescription.addItemListener(this);
    this.chxTrainData.setSelected(this._$26016);
    this.chxTrainData.addItemListener(this);
    this.chxGradient.setSelected(this._$26018);
    this.chxGradient.addItemListener(this);
    this.chxCurve.setSelected(this._$18218);
    this.chxCurve.addItemListener(this);
    this.chxGroup1.setSelected(this._$26004);
    this.chxGroup1.addItemListener(this);
    this.chxGroup2.setSelected(this._$26007);
    this.chxGroup2.addItemListener(this);
    this.chxGroup3.setSelected(this._$26017);
    this.chxGroup3.addItemListener(this);
    this.chxSimpleMode.setText(ATPMessages.getString("MW.BEHAVIOR.SIMPLE_MODE"));
    this.chxSimpleMode.addItemListener(this);
    this.chxPrintMode.setText(ATPMessages.getString("MW.BEHAVIOR.PRINT_MODE"));
    this.chxPrintMode.addItemListener(this);
    this.btnPrintLarge.setText(ATPMessages.getString("MW.BEHAVIOR.PRINT.LARGE"));
    this.btnPrintLarge.addActionListener((ActionListener)new pnlDriverBehavior_btn_actionAdapter(this));
    this.btnPrintSmall.setText(ATPMessages.getString("MW.BEHAVIOR.PRINT.SMALL"));
    this.btnPrintSmall.addActionListener((ActionListener)new pnlDriverBehavior_btn_actionAdapter(this));
    this.btnZoom.setText("zoom test");
    this.btnZoom.addActionListener((ActionListener)new pnlDriverBehavior_btn_actionAdapter(this));
    this.btnOrgiZoom.setText(ATPMessages.getString("MW.BEHAVIOR.ZOOM.ORGI"));
    this.btnOrgiZoom.addActionListener((ActionListener)new pnlDriverBehavior_btn_actionAdapter(this));
    this.btnZoomIn.setText(ATPMessages.getString("MW.BEHAVIOR.ZOOM.IN"));
    this.btnZoomIn.addActionListener((ActionListener)new pnlDriverBehavior_btn_actionAdapter(this));
    this.btnSaveLarge.setText(ATPMessages.getString("MW.BEHAVIOR.SAVE.ALL"));
    this.btnSaveLarge.addActionListener((ActionListener)new pnlDriverBehavior_btn_actionAdapter(this));
    this.btnSaveSmall.setText(ATPMessages.getString("MW.BEHAVIOR.SAVE.FRAME"));
    this.btnSaveSmall.addActionListener((ActionListener)new pnlDriverBehavior_btn_actionAdapter(this));
    this.pnlBody.setLayout(this.borderLayout2);
    this.pnlHeader.setBorder(this.titledBorder2);
    this.pnlHeader.setLayout(new GridBagLayout());
    this.Hd.setPreferredSize(new Dimension(this.scps.headerWidth, 31));
    this.pnlBody.add((Component)this.Hd, "West");
    this.Body.addMouseMotionListener((MouseMotionListener)new pnlDriverBehavior_mouseMotionAdapter(this));
    this.Body.addMouseListener((MouseListener)new pnlDriverBehavior_jPanel1_mouseAdapter(this));
    this.scroll = new JScrollPane((Component)this.Body);
    this.scroll.setBorder((Border)null);
    this.pnlBody.add(this.scroll, "Center");
    this._$26029.setPreferredSize(new Dimension(130, 200));
    this._$26029.add(this.chxPmSpeed, null);
    this._$26029.add(this.chxTgSpeed, null);
    this._$26029.add(this.chxRsSpeed, null);
    this._$26029.add(this.chxAdhesion, null);
    this._$26029.add(this.chxWarn, null);
    this._$26029.add(this.chxMode, null);
    this._$26029.add(this.chxSlip, null);
    this._$26029.add(this.chxSlide, null);
    this._$26029.add(this.chxEB, null);
    this._$26029.add(this.chxSB, null);
    this._$26029.add(this.chxBrakeTarget, null);
    this._$26029.add(this.chxBalise, null);
    this._$26029.add(this.chxWaysideFailure, null);
    this._$26029.add(this.chxCabinFailure, null);
    this._$26029.add(this.chxMouse, null);
    this._$26029.add(this.chxDescription, null);
    this._$26029.add(this.chxDriverMessage, null);
    this._$26029.add(this.chxTrainData, null);
    this._$26029.add(this.chxGradient, null);
    this._$26029.add(this.chxCurve, null);
    this._$26029.add(this._$26042);
    this._$26042.addChangeListener(this);
    this._$26029.add(this._$26041);
    this._$26041.addChangeListener(this);
    this._$26029.add(this.chxGroup1);
    this._$26029.add(this.chxGroup2);
    this._$26029.add(this.chxGroup3);
    this.chxPmSpeed.setOpaque(false);
    this.chxTgSpeed.setOpaque(false);
    this.chxRsSpeed.setOpaque(false);
    this.chxAdhesion.setOpaque(false);
    this.chxWarn.setOpaque(false);
    this.chxMode.setOpaque(false);
    this.chxSlip.setOpaque(false);
    this.chxSlide.setOpaque(false);
    this.chxEB.setOpaque(false);
    this.chxSB.setOpaque(false);
    this.chxBrakeTarget.setOpaque(false);
    this.chxBalise.setOpaque(false);
    this.chxWaysideFailure.setOpaque(false);
    this.chxCabinFailure.setOpaque(false);
    this.chxMouse.setOpaque(false);
    this.chxDescription.setOpaque(false);
    this.chxDriverMessage.setOpaque(false);
    this.chxTrainData.setOpaque(false);
    this.chxGradient.setOpaque(false);
    this.chxCurve.setOpaque(false);
    this.chxGroup3.setOpaque(false);
    this.chxGroup2.setOpaque(false);
    this.chxGroup1.setOpaque(false);
    this.pnlBody.add((Component)this._$26029, "East");
    this.pnlHeader.add(this.lblMissionDateTag, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    this.pnlHeader.add(this.lblDIDTag, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-5, 1, 0, 1), 0, 0));
    this.pnlHeader.add(this.lblVIDTag, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-5, 1, -5, 1), 0, 0));
    this.pnlHeader.add(this.lblMissionDate, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(-3, 1, 0, 1), 0, 0));
    this.pnlHeader.add(this.lblDID, new GridBagConstraints(1, 1, 3, 1, 0.0D, 0.0D, 17, 2, new Insets(-5, 1, 0, 1), 100, 0));
    this.pnlHeader.add(this.lblVID, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(-5, 1, -5, 1), 0, 0));
    this.pnlHeader.add(this.lblWSNoTag, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-5, 1, 0, 1), 0, 0));
    this.pnlHeader.add(this.lblTRNoTag, new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-5, 1, -5, 1), 0, 0));
    this.pnlHeader.add(this.lblWSNo, new GridBagConstraints(3, 0, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(-5, 1, 0, 1), 0, 0));
    this.pnlHeader.add(this.lblTRNo, new GridBagConstraints(3, 2, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(-5, 1, -5, 1), 0, 0));
    this.pnlHeader.add(this.lblStartTimeTag, new GridBagConstraints(4, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 5, 0, 1), 0, 0));
    this.pnlHeader.add(this.lblEndTimeTag, new GridBagConstraints(4, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-5, 5, 0, 1), 0, 0));
    this.pnlHeader.add(this.lblFileNameTag, new GridBagConstraints(4, 2, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 5, -5, 1), 0, 0));
    this.pnlHeader.add(this.lblStartTime, new GridBagConstraints(5, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    this.pnlHeader.add(this.lblEndTime, new GridBagConstraints(5, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-5, 1, 0, 1), 0, 0));
    this.pnlHeader.add(this.lblFileName, new GridBagConstraints(5, 2, 4, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 5, -5, 1), 0, 0));
    this.pnlHeader.add(this.lblUserDefineTime1Tag, new GridBagConstraints(6, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-3, 5, 0, 1), 0, 0));
    this.pnlHeader.add(this.lblUserDefineTime2Tag, new GridBagConstraints(6, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-5, 5, 0, 1), 0, 0));
    this.pnlHeader.add(this.lblUserDefineTime1, new GridBagConstraints(7, 0, 1, 1, 1.0D, 0.0D, 17, 0, new Insets(-3, 1, 0, 1), 0, 0));
    this.pnlHeader.add(this.lblUserDefineTime2, new GridBagConstraints(7, 1, 1, 1, 1.0D, 0.0D, 17, 0, new Insets(-5, 1, 0, 1), 0, 0));
    this.pnlHeader.add(this.chxSimpleMode, new GridBagConstraints(8, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(-3, 5, 0, 0), 0, 0));
    this.pnlHeader.add(this.chxPrintMode, new GridBagConstraints(8, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(-5, 5, 0, 0), 0, 0));
    this.pnlHeader.add(this.btnZoomIn, new GridBagConstraints(9, 0, 1, 2, 0.0D, 0.0D, 10, 3, new Insets(-3, 0, 0, 0), 0, 0));
    this.pnlHeader.add(this.txtUserDescription, new GridBagConstraints(9, 2, 4, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 1, -5, 1), 0, 0));
    this.pnlHeader.add(this.btnOrgiZoom, new GridBagConstraints(10, 0, 1, 2, 0.0D, 0.0D, 10, 3, new Insets(-3, 0, 0, 0), 0, 0));
    this.pnlHeader.add(this.lblPrintType, new GridBagConstraints(11, 0, 2, 1, 0.0D, 0.0D, 10, 0, new Insets(-3, 0, 0, 0), 0, 0));
    this.pnlHeader.add(this.btnPrintLarge, new GridBagConstraints(11, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(-3, 0, 0, 0), 0, 0));
    this.pnlHeader.add(this.btnPrintSmall, new GridBagConstraints(12, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(-3, 0, 0, 0), 0, 0));
    this.pnlHeader.add(this.lblFileSaveType, new GridBagConstraints(13, 0, 2, 1, 0.0D, 0.0D, 10, 0, new Insets(-3, 0, 0, 0), 0, 0));
    this.pnlHeader.add(this.btnSaveLarge, new GridBagConstraints(13, 1, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(-5, 0, 0, 0), 0, 0));
    this.pnlHeader.add(this.btnSaveSmall, new GridBagConstraints(14, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(-5, 0, 0, 0), 0, 0));
    this.pnlHeader.add(this.lblFileSaveResult, new GridBagConstraints(13, 2, 2, 1, 0.0D, 0.0D, 10, 0, new Insets(-5, 0, -5, 0), 0, 0));
    this.buttonGroup1.add(this.rdoDrawByTime);
    this.buttonGroup1.add(this.rdoDrawByDist);
    add(this.pnlHeader, "North");
    add((Component)this.pnlBody, "Center");
    this.rdoDrawByDist.setVisible(false);
  }
  
  private void _$26116() throws Exception {
    this.scps = new commonParaSetting();
    boolean bool = this.chxPrintMode.isSelected();
    this.scps.drawByDist = InitParameters.DrawByDist;
    this.scps.dpiX = InitParameters.DpiX;
    this.scps.intervalX = InitParameters.IntervalX;
    this.scps.dpiDistX = InitParameters.DpiXD;
    this.scps.intervalDistX = InitParameters.IntervalXD;
    this.scps.headerHeight = InitParameters.HeaderHeight;
    this.scps.headerWidth = InitParameters.HeaderWidth;
    if (bool) {
      this.scps.backgroundColor = InitParameters.PrintBackGround;
      this.scps.baseLineColor = InitParameters.PrintBaseLine;
      this.scps.baseLineColor_light = InitParameters.PrintGridLine;
      this.scps.mainLineColor = InitParameters.PrintMainLine;
      this.scps.charColor = InitParameters.PrintChar;
      this.scps.stationColor = InitParameters.PrintStation;
      this.scps.mouse = InitParameters.PrintMouse;
    } else {
      this.scps.backgroundColor = InitParameters.BackGround;
      this.scps.baseLineColor = InitParameters.BaseLine;
      this.scps.baseLineColor_light = InitParameters.GridLine;
      this.scps.mainLineColor = InitParameters.MainLine;
      this.scps.charColor = InitParameters.Char;
      this.scps.stationColor = InitParameters.Station;
      this.scps.mouse = InitParameters.Mouse;
    } 
    this.scps.startLocation = this._$26045.getMissionStartLocation();
    this.scps.endLocation = this._$26045.getMissionEndLocation();
    this.scps.startTime = this._$26045.getUserDefineStartTime().getTime();
    this.scps.endTime = this._$26045.getUserDefineEndTime().getTime();
    this.scps.drawByDist = InitParameters.DrawByDist;
    this._$8450 = this.scps.dpiX;
    this._$8451 = this.scps.dpiDistX;
    drawParameters drawParameters1 = new drawParameters();
    drawParameters1.message = ATPMessages.getString("MW.GNL.SPEED");
    drawParameters1.MaxNum = InitParameters.SpeedMax;
    drawParameters1.MinNum = InitParameters.SpeedMin;
    drawParameters1.intervalY = InitParameters.SpeedInterval;
    drawParameters1.dpiY = InitParameters.SpeedDpi;
    drawParameters1.drawBody = InitParameters.SpeedBody;
    drawParameters1.drawValues = InitParameters.SpeedValue;
    drawParameters1.drawBody = false;
    drawParameters1.drawValues = false;
    this._$17917 = new DrawContinuous(this.scps, drawParameters1, this._$26045.getLocation());
    this._$17917.setData(this._$26045.getSpeed());
    this._$17917.setDataLineColor(InitParameters.SpeedColor);
    this._$17917.setDrawCurve(true);
    this._$26044[0] = drawParameters1;
    this._$26043[0] = (DrawBase)this._$17917;
    drawParameters drawParameters2 = (drawParameters)drawParameters1.clone();
    this._$17918 = new DrawBreakOff(this.scps, drawParameters2, this._$26045.getLocation());
    this._$17918.setData(this._$26045.getPermittedSpeed());
    this._$17918.setDataLineColor(InitParameters.PmSpeedColor);
    this._$17918.setStroke(new BasicStroke(1.0F, 1, 1, 0.0F, new float[] { 12.0F, 3.0F }, 0.0F));
    this._$17918.setDrawCurve(this._$25998);
    this._$26044[1] = drawParameters2;
    this._$26043[1] = (DrawBase)this._$17918;
    drawParameters drawParameters3 = (drawParameters)drawParameters1.clone();
    this._$17919 = new DrawBreakOff(this.scps, drawParameters3, this._$26045.getLocation());
    this._$17919.setData(this._$26045.getReleaseSpeed());
    this._$17919.setDataLineColor(InitParameters.RsSpeedColor);
    this._$17919.setStroke(new BasicStroke(1.0F, 1, 1, 0.0F, new float[] { 3.0F, 3.0F, 6.0F, 3.0F }, 0.0F));
    this._$17919.setDrawCurve(this._$25997);
    this._$26044[2] = drawParameters3;
    this._$26043[2] = (DrawBase)this._$17919;
    drawParameters drawParameters4 = (drawParameters)drawParameters1.clone();
    this._$17920 = new DrawBreakOff(this.scps, drawParameters4, this._$26045.getLocation());
    this._$17920.setData(this._$26045.getTargetSpeed());
    this._$17920.setDataLineColor(InitParameters.TgSpeedColor);
    this._$17920.setStroke(new BasicStroke(1.0F, 1, 1, 0.0F, new float[] { 6.0F, 6.0F }, 0.0F));
    this._$17920.setDrawCurve(this._$25999);
    this._$26044[3] = drawParameters4;
    this._$26043[3] = (DrawBase)this._$17920;
    drawParameters drawParameters5 = (drawParameters)drawParameters1.clone();
    drawParameters5.drawBody = true;
    drawParameters5.drawValues = true;
    this._$26032 = new DrawRightAngle(this.scps, drawParameters5, this._$26045.getLocation());
    this._$26032.setData(this._$26045.getAdhesion());
    this._$26032.setDataLineColor(InitParameters.AhSpeedColor);
    this._$26032.setDrawCurve(this._$26000);
    this._$26044[4] = drawParameters5;
    this._$26043[4] = (DrawBase)this._$26032;
    drawParameters drawParameters6 = (drawParameters)drawParameters1.clone();
    this._$26038 = new DrawTrainData(this.scps, drawParameters6, this._$26045.getLocation());
    this._$26038.setData(this._$26045.getTrainData());
    this._$26038.setDrawCurve(this._$26016);
    this._$26044[17] = drawParameters6;
    this._$26043[17] = (DrawBase)this._$26038;
    drawParameters drawParameters7 = new drawParameters();
    drawParameters7.message = "坡度/彎道";
    drawParameters7.MaxNum = 20;
    drawParameters7.MinNum = -20;
    drawParameters7.intervalY = 10;
    drawParameters7.dpiY = 1.0D;
    drawParameters7.drawBody = false;
    drawParameters7.drawValues = false;
    this._$26039 = new DrawCurve(this.scps, drawParameters7, this._$26045.getLocation());
    this._$26039.setData(this._$26045.getCurve());
    this._$26044[18] = drawParameters7;
    this._$26043[18] = (DrawBase)this._$26039;
    drawParameters drawParameters8 = (drawParameters)drawParameters7.clone();
    drawParameters8.drawBody = false;
    drawParameters8.drawValues = false;
    this._$26040 = new DrawGradient(this.scps, drawParameters8, this._$26045.getLocation());
    this._$26040.setData(this._$26045.getGradient());
    this._$26044[19] = drawParameters8;
    this._$26043[19] = (DrawBase)this._$26040;
    drawParameters drawParameters9 = (drawParameters)drawParameters1.clone();
    drawParameters5.drawBody = true;
    drawParameters5.drawValues = true;
    this._$26037 = new DrawDriverMessage(this.scps, drawParameters9, this._$26045.getLocation());
    this._$26037.setData(this._$26045.getDriverMessage_filter(), this._$26045.getDriverMessage_Ack_filter());
    this._$26037.setDrawCurve(this._$26015);
    this._$26044[16] = drawParameters9;
    this._$26043[16] = (DrawBase)this._$26037;
    drawParameters drawParameters10 = new drawParameters();
    drawParameters10.message = ATPMessages.getString("MW.GNL.TARGET_DISTANCE");
    drawParameters10.MaxNum = InitParameters.BTMax;
    drawParameters10.MinNum = InitParameters.BTMin;
    drawParameters10.dpiY = InitParameters.BTDpi;
    drawParameters10.intervalY = InitParameters.BTInterval;
    drawParameters10.drawBody = InitParameters.BTBody;
    drawParameters10.drawValues = InitParameters.BTValue;
    this._$26033 = new DrawMass(this.scps, drawParameters10, this._$26045.getLocation());
    this._$26033.setData(this._$26045.getTarget(), this._$26045.getBrakeTarget());
    this._$26033.setDataLineColor(InitParameters.BTColor);
    this._$26033.setDrawCurve(this._$26012);
    this._$26044[5] = drawParameters10;
    this._$26043[5] = (DrawBase)this._$26033;
    this.Body.setPreferredSize(new Dimension(this._$17917.showBattleLingDepth() + 70, 300));
    drawParameters drawParameters11 = new drawParameters();
    drawParameters11.message = ATPMessages.getString("MW.GNL.BALISE");
    drawParameters11.MaxNum = 1;
    drawParameters11.MinNum = 0;
    drawParameters11.dpiY = 1.0D;
    drawParameters11.intervalY = 10;
    drawParameters11.drawBody = true;
    drawParameters11.drawValues = false;
    this._$26034 = new DrawBaliseInfo(this.scps, drawParameters11, this._$26045.getLocation());
    this._$26034.setData(this._$26045);
    this._$26034.setDataLineColor(Color.GREEN);
    this._$26034.setDrawCurve(this._$26001);
    this._$26044[6] = drawParameters11;
    this._$26043[6] = (DrawBase)this._$26034;
    drawParameters drawParameters12 = (drawParameters)drawParameters11.clone();
    drawParameters12.drawBody = false;
    ATPMissionFailure aTPMissionFailure = new ATPMissionFailure((ATPMission)this._$26045);
    this._$26035 = new DrawFailureMsg(this.scps, drawParameters12, this._$26045.getLocation(), 0);
    this._$26035.setData(aTPMissionFailure.getWaysideFailure());
    this._$26035.setDataLineColor(Color.pink);
    this._$26035.setDrawCurve(this._$26013);
    this._$26044[14] = drawParameters12;
    this._$26043[14] = (DrawBase)this._$26035;
    drawParameters drawParameters13 = (drawParameters)drawParameters11.clone();
    drawParameters13.drawBody = false;
    this._$26036 = new DrawFailureMsg(this.scps, drawParameters13, this._$26045.getLocation(), 1);
    this._$26036.setData(aTPMissionFailure.getCabinFailure());
    this._$26036.setDataLineColor(Color.yellow);
    this._$26036.setDrawCurve(this._$26014);
    this._$26044[15] = drawParameters13;
    this._$26043[15] = (DrawBase)this._$26036;
    this.Body.setPreferredSize(new Dimension(this._$17917.showBattleLingDepth() + 70, 300));
    drawParameters drawParameters14 = new drawParameters();
    drawParameters14.message = ATPMessages.getString("MW.GNL.WARN");
    drawParameters14.MaxNum = InitParameters.WarnMax;
    drawParameters14.MinNum = InitParameters.WarnMin;
    drawParameters14.dpiY = InitParameters.WarnDpi;
    drawParameters14.intervalY = InitParameters.WarnInterval;
    drawParameters14.drawBody = InitParameters.WarnBody;
    drawParameters14.drawValues = InitParameters.WarnValue;
    this._$26030 = new DrawHistogram(this.scps, drawParameters14, this._$26045.getLocation());
    this._$26030.setData(this._$26045.getWarning());
    this._$26030.setDataLineColor(InitParameters.WarnColor);
    this._$26030.setDrawCurve(this._$26010);
    this._$26044[7] = drawParameters14;
    this._$26043[7] = (DrawBase)this._$26030;
    drawParameters drawParameters15 = new drawParameters();
    drawParameters15.message = ATPMessages.getString("MW.GNL.SLIP") + "/" + ATPMessages.getString("MW.GNL.SLIDE");
    drawParameters15.MaxNum = InitParameters.SlipMax;
    drawParameters15.MinNum = InitParameters.SlipMin;
    drawParameters15.dpiY = InitParameters.SlipDpi;
    drawParameters15.intervalY = InitParameters.SlipInterval;
    drawParameters15.drawBody = InitParameters.SlipBody;
    drawParameters15.drawValues = InitParameters.SlipValue;
    this._$17924 = new DrawRightAngle(this.scps, drawParameters15, this._$26045.getLocation());
    this._$17924.setData(this._$26045.getSlip());
    this._$17924.setDataLineColor(InitParameters.SlipColor);
    this._$17924.setDrawBody(true);
    this._$17924.setDrawCurve(this._$26005);
    this._$26044[8] = drawParameters15;
    this._$26043[8] = (DrawBase)this._$17924;
    drawParameters drawParameters16 = (drawParameters)drawParameters15.clone();
    this._$17925 = new DrawRightAngle(this.scps, drawParameters16, this._$26045.getLocation());
    this._$17925.setData(this._$26045.getSlide());
    this._$17925.setDataLineColor(InitParameters.SlideColor);
    this._$17925.setDrawBody(false);
    this._$17925.setDrawCurve(this._$26006);
    this._$26044[9] = drawParameters16;
    this._$26043[9] = (DrawBase)this._$17925;
    drawParameters drawParameters17 = new drawParameters();
    drawParameters17.message = ATPMessages.getString("MW.GNL.ATP_MODE");
    drawParameters17.MaxNum = InitParameters.ModeMax;
    drawParameters17.MinNum = InitParameters.ModeMin;
    drawParameters17.dpiY = InitParameters.ModeDpi;
    drawParameters17.intervalY = InitParameters.ModeInterval;
    drawParameters17.drawBody = InitParameters.ModeBody;
    drawParameters17.drawValues = InitParameters.ModeValue;
    this._$17927 = new DrawHistogram(this.scps, drawParameters17, this._$26045.getLocation());
    this._$17927.setData(this._$26045.getMode());
    this._$17927.setDataLineColor(InitParameters.ModeColor);
    this._$17927.setDrawCurve(this._$26011);
    this._$26044[10] = drawParameters17;
    this._$26043[10] = (DrawBase)this._$17927;
    drawParameters drawParameters18 = new drawParameters();
    drawParameters18.message = ATPMessages.getString("MW.GNL.BRAKE") + "/" + ATPMessages.getString("MW.GNL.PIPE_PRESSURE");
    drawParameters18.MaxNum = InitParameters.EBMax;
    drawParameters18.MinNum = InitParameters.EBMin;
    drawParameters18.dpiY = InitParameters.EBDpi;
    drawParameters18.intervalY = InitParameters.EBInterval;
    drawParameters18.drawBody = InitParameters.EBBody;
    drawParameters18.drawValues = InitParameters.EBValue;
    drawParameters18.drawBody = false;
    drawParameters18.drawValues = false;
    drawParameters drawParameters19 = (drawParameters)drawParameters18.clone();
    drawParameters19.drawBody = true;
    drawParameters19.drawValues = true;
    this._$26031 = new DrawHistogram(this.scps, drawParameters19, this._$26045.getLocation());
    this._$26031.setData(this._$26045.getPipePressure());
    this._$26031.setDataLineColor(Color.lightGray);
    this._$26031.setDrawBody(true);
    this._$26044[11] = drawParameters19;
    this._$26043[11] = (DrawBase)this._$26031;
    this._$8363 = new DrawRightAngle(this.scps, drawParameters18, this._$26045.getLocation());
    this._$8363.setData(this._$26045.getEmerBrake());
    this._$8363.setDataLineColor(InitParameters.EBColor);
    this._$8363.setDrawCurve(this._$26008);
    this._$26044[12] = drawParameters18;
    this._$26043[12] = (DrawBase)this._$8363;
    drawParameters drawParameters20 = (drawParameters)drawParameters18.clone();
    this._$17926 = new DrawRightAngle(this.scps, drawParameters20, this._$26045.getLocation());
    this._$17926.setData(this._$26045.getServiceBrake());
    this._$17926.setDataLineColor(InitParameters.SBColor);
    this._$17926.setDrawCurve(this._$26009);
    this._$26044[13] = drawParameters20;
    this._$26043[13] = (DrawBase)this._$17926;
    for (byte b = 0; b < this._$26044.length; b++)
      this._$18000[b] = (this._$26044[b]).dpiY; 
    _$26144();
    this._$2586 = new DrawDescription();
  }
  
  public static boolean isWindowsOpen() {
    try {
      File file = new File("C:\\ATPMW\\windowsChecking");
      if (!file.exists()) {
        FileWriter fileWriter1 = new FileWriter("C:\\ATPMW\\windowsChecking");
        fileWriter1.write("" + System.currentTimeMillis());
        fileWriter1.close();
        return false;
      } 
      FileReader fileReader = new FileReader("C:\\ATPMW\\windowsChecking");
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      long l = (new Long(bufferedReader.readLine())).longValue();
      FileWriter fileWriter = new FileWriter("C:\\ATPMW\\windowsChecking");
      fileWriter.write("" + System.currentTimeMillis());
      fileWriter.close();
      fileReader.close();
      bufferedReader.close();
      return (System.currentTimeMillis() - l < 180000L);
    } catch (IOException iOException) {
      iOException.printStackTrace();
      return false;
    } 
  }
  
  public void itemStateChanged(ItemEvent paramItemEvent) {
    JCheckBox jCheckBox = (JCheckBox)paramItemEvent.getSource();
    if (jCheckBox.equals(this.chxGroup1)) {
      this._$26004 = jCheckBox.isSelected();
      this.chxSlip.setEnabled(this._$26004);
      this._$2586.setDrawSlip(this._$26004);
      this.chxSlide.setEnabled(this._$26004);
      this._$2586.setDrawSlide(this._$26004);
    } else if (jCheckBox.equals(this.chxGroup2)) {
      this._$26007 = jCheckBox.isSelected();
      this.chxEB.setEnabled(this._$26007);
      this._$2586.setDrawEB(this._$26007);
      this.chxSB.setEnabled(this._$26007);
      this._$2586.setDrawSB(this._$26007);
    } else if (jCheckBox.equals(this.chxPmSpeed)) {
      this._$25998 = this.chxPmSpeed.isSelected();
      this._$17918.setDrawCurve(this._$25998);
    } else if (jCheckBox.equals(this.chxTgSpeed)) {
      this._$25999 = jCheckBox.isSelected();
      this._$17920.setDrawCurve(this._$25999);
    } else if (jCheckBox.equals(this.chxRsSpeed)) {
      this._$25997 = jCheckBox.isSelected();
      this._$17919.setDrawCurve(this._$25997);
    } else if (jCheckBox.equals(this.chxAdhesion)) {
      this._$26000 = jCheckBox.isSelected();
      this._$26032.setDrawCurve(this._$26000);
    } else if (jCheckBox.equals(this.chxWarn)) {
      this._$26010 = jCheckBox.isSelected();
      this._$26030.setDrawCurve(this._$26010);
      this._$2586.setDrawWarn(this._$26010);
    } else if (jCheckBox.equals(this.chxMode)) {
      this._$26011 = jCheckBox.isSelected();
      this._$17927.setDrawCurve(this._$26011);
      this._$2586.setDrawMode(this._$26011);
    } else if (jCheckBox.equals(this.chxSlip)) {
      this._$26005 = jCheckBox.isSelected();
      this._$17924.setDrawCurve(this._$26005);
      this._$2586.setDrawSlip(this._$26005);
      if (!this._$26005 && !this._$26006)
        this.chxGroup1.setSelected(false); 
    } else if (jCheckBox.equals(this.chxSlide)) {
      this._$26006 = jCheckBox.isSelected();
      this._$17925.setDrawCurve(this._$26006);
      this._$2586.setDrawSlide(this._$26006);
      if (!this._$26005 && !this._$26006)
        this.chxGroup1.setSelected(false); 
    } else if (jCheckBox.equals(this.chxEB)) {
      this._$26008 = jCheckBox.isSelected();
      this._$8363.setDrawCurve(this._$26008);
      this._$2586.setDrawEB(this._$26008);
      if (!this._$26008 && !this._$26009)
        this.chxGroup2.setSelected(false); 
    } else if (jCheckBox.equals(this.chxSB)) {
      this._$26009 = jCheckBox.isSelected();
      this._$17926.setDrawCurve(this._$26009);
      this._$2586.setDrawSB(this._$26009);
      if (!this._$26008 && !this._$26009)
        this.chxGroup2.setSelected(false); 
    } else if (jCheckBox.equals(this.chxBrakeTarget)) {
      this._$26012 = jCheckBox.isSelected();
      this._$26033.setDrawCurve(this._$26012);
      this._$2586.setDrawBT(this._$26012);
    } else if (jCheckBox.equals(this.chxBalise)) {
      this._$26001 = jCheckBox.isSelected();
      this._$26034.setDrawCurve(this._$26001);
      this._$26013 = jCheckBox.isSelected();
      this._$26035.setDrawCurve(this._$26013);
      this.chxWaysideFailure.setEnabled(jCheckBox.isSelected());
      this._$26014 = jCheckBox.isSelected();
      this._$26036.setDrawCurve(this._$26014);
      this.chxCabinFailure.setEnabled(jCheckBox.isSelected());
    } else if (jCheckBox.equals(this.chxWaysideFailure)) {
      this._$26013 = jCheckBox.isSelected();
      this._$26035.setDrawCurve(this._$26013);
    } else if (jCheckBox.equals(this.chxCabinFailure)) {
      this._$26014 = jCheckBox.isSelected();
      this._$26036.setDrawCurve(this._$26014);
    } else if (jCheckBox.equals(this.chxMouse)) {
      this._$26002 = jCheckBox.isSelected();
    } else if (jCheckBox.equals(this.chxDescription)) {
      this._$26003 = jCheckBox.isSelected();
    } else if (jCheckBox.equals(this.chxDriverMessage)) {
      this._$26015 = jCheckBox.isSelected();
      this._$26037.setDrawCurve(this._$26015);
      this._$2586.setDrawDriverMessage(this._$26015);
    } else if (jCheckBox.equals(this.chxTrainData)) {
      this._$26016 = jCheckBox.isSelected();
      this._$26038.setDrawCurve(this._$26016);
    } else if (jCheckBox.equals(this.chxGradient)) {
      this._$26018 = jCheckBox.isSelected();
      this._$26040.setDrawCurve(this._$26018);
      if (!this._$26018 && !this._$18218)
        this.chxGroup3.setSelected(false); 
    } else if (jCheckBox.equals(this.chxCurve)) {
      this._$18218 = jCheckBox.isSelected();
      this._$26039.setDrawCurve(this._$18218);
      if (!this._$26018 && !this._$18218)
        this.chxGroup3.setSelected(false); 
    } else if (jCheckBox.equals(this.chxGroup3)) {
      this._$26017 = jCheckBox.isSelected();
      this.chxGradient.setEnabled(this._$26017);
      this.chxCurve.setEnabled(this._$26017);
    } else if (jCheckBox.equals(this.chxSimpleMode)) {
      boolean bool = (paramItemEvent.getStateChange() == 1) ? true : false;
      this.chxAdhesion.setSelected(!bool);
      this.chxBalise.setSelected(!bool);
      this.chxWaysideFailure.setSelected(!bool);
      this.chxCabinFailure.setSelected(!bool);
      if (bool)
        this.chxBrakeTarget.setSelected(!bool); 
      this.chxGroup1.setSelected(!bool);
      this.chxGroup2.setSelected(!bool);
      this.chxMode.setSelected(!bool);
      this.chxPmSpeed.setSelected(!bool);
      this.chxRsSpeed.setSelected(!bool);
      this.chxTgSpeed.setSelected(!bool);
      this.chxWarn.setSelected(!bool);
      this.chxMouse.setSelected(!bool);
      if (bool)
        this.chxDescription.setSelected(!bool); 
      this.chxDriverMessage.setSelected(!bool);
      this.chxTrainData.setSelected(!bool);
      this.chxGroup3.setSelected(!bool);
      this.chxGradient.setSelected(!bool);
      this.chxCurve.setSelected(!bool);
      if (bool) {
        this._$26042.setValue(5);
      } else {
        this._$26042.setValue(10);
      } 
    } else if (jCheckBox.equals(this.chxPrintMode)) {
      int i = this._$26042.getValue();
      try {
        _$26116();
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
      this._$26042.setValue(i);
      if (i <= 10) {
        for (byte b = 0; b < 5; b++) {
          (this._$26044[b]).dpiY = this._$18000[b] * i / 10.0D;
          this._$26043[b].resetScale();
        } 
      } else {
        for (byte b = 0; b < 5; b++) {
          (this._$26044[b]).dpiY = this._$18000[b] * (i - 10);
          this._$26043[b].resetScale();
        } 
      } 
    } 
    _$26144();
    repaint();
  }
  
  void jPanel1_mouseClicked(MouseEvent paramMouseEvent) {
    try {
      if (paramMouseEvent.getButton() == 1) {
        this._$26022.add(this._$26023.clone());
      } else if (paramMouseEvent.getButton() == 3) {
        this._$26022.clear();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    repaint();
  }
  
  private void _$26144() {
    byte b1 = 30;
    this.bodyHeight = 50;
    (this._$26044[0]).UpperBound = this.bodyHeight;
    (this._$26044[1]).UpperBound = this.bodyHeight;
    (this._$26044[2]).UpperBound = this.bodyHeight;
    (this._$26044[3]).UpperBound = this.bodyHeight;
    (this._$26044[4]).UpperBound = this.bodyHeight;
    (this._$26044[16]).UpperBound = this.bodyHeight;
    (this._$26044[17]).UpperBound = this.bodyHeight;
    this.bodyHeight += this._$26043[0].showBattleLineRange() + b1;
    if (this._$26001) {
      this.bodyHeight += 40;
      (this._$26044[6]).UpperBound = this.bodyHeight;
      (this._$26044[14]).UpperBound = this.bodyHeight;
      (this._$26044[15]).UpperBound = this.bodyHeight;
      this.bodyHeight += this._$26043[6].showBattleLineRange() + b1;
      this.bodyHeight += 20;
    } 
    if (this._$26017) {
      (this._$26044[18]).UpperBound = this.bodyHeight;
      (this._$26044[19]).UpperBound = this.bodyHeight;
      this.bodyHeight += this._$26043[18].showBattleLineRange() + b1;
    } 
    if (this._$26012) {
      (this._$26044[5]).UpperBound = this.bodyHeight;
      this.bodyHeight += this._$26043[5].showBattleLineRange() + b1;
    } 
    if (this._$26010) {
      (this._$26044[7]).UpperBound = this.bodyHeight;
      this.bodyHeight += this._$26043[7].showBattleLineRange() + b1;
    } 
    if (this._$26004) {
      (this._$26044[8]).UpperBound = this.bodyHeight;
      (this._$26044[9]).UpperBound = this.bodyHeight;
      this.bodyHeight += this._$26043[8].showBattleLineRange() + b1;
    } 
    if (this._$26011) {
      (this._$26044[10]).UpperBound = this.bodyHeight;
      this.bodyHeight += this._$26043[10].showBattleLineRange() + b1;
    } 
    if (this._$26007) {
      (this._$26044[11]).UpperBound = this.bodyHeight;
      (this._$26044[12]).UpperBound = this.bodyHeight;
      (this._$26044[13]).UpperBound = this.bodyHeight;
    } 
    for (byte b2 = 0; b2 < this._$26043.length; b2++)
      this._$26043[b2].resetScale(); 
  }
  
  void mouseMoved(MouseEvent paramMouseEvent) {
    try {
      _$26110(paramMouseEvent.getX(), paramMouseEvent.getY());
      this._$2586.setLocation(_$26168(paramMouseEvent.getX(), paramMouseEvent.getY()), paramMouseEvent.getY());
      int i = _$26158(this._$26034.showsoldierWhereToStand(this._$26034.soloderReport()), 0)[0];
      boolean bool = (i == 0) ? false : true;
      for (byte b = 0; b < this._$26043.length; b++)
        this._$26043[b].isMessageOutOfView(bool); 
      repaint();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$26206(int paramInt) {
    String str1 = "C:\\ATP_Graphics\\";
    String str2 = this.txtUserDescription.getText();
    str2 = str2.replace('\\', ' ');
    str2 = str2.replace('/', ' ');
    str2 = str2.replace(':', ' ');
    str2 = str2.replace('*', ' ');
    str2 = str2.replace('?', ' ');
    str2 = str2.replace('"', ' ');
    str2 = str2.replace('<', ' ');
    str2 = str2.replace('>', ' ');
    str2 = str2.replace('|', ' ');
    this.txtUserDescription.setText(str2);
    if (paramInt == 0) {
      BufferedImage bufferedImage = new BufferedImage(this.Body.getWidth() + this.Hd.getWidth() + this._$26029.getWidth(), this.Body.getHeight() + 100, 6);
      Graphics2D graphics2D = bufferedImage.createGraphics();
      int i = _$26215(graphics2D, bufferedImage.getWidth());
      graphics2D.translate(0, i);
      this.Hd.paintAll(graphics2D);
      graphics2D.translate(this.Hd.getWidth(), 0);
      this.Body.paintAll(graphics2D);
      graphics2D.translate(this.Body.getWidth(), 0);
      this._$26029.printAll(graphics2D);
      graphics2D.dispose();
      File file1 = new File(str1);
      if (!file1.exists())
        file1.mkdir(); 
      File file2 = new File(str1 + "\\" + this.lblFileName.getText() + "_" + this.txtUserDescription.getText() + ".png");
      try {
        ImageIO.write(bufferedImage, "png", file2);
        this.lblFileSaveResult.setText(ATPMessages.getString("MW.BEHAVIOR.SAVE.OK"));
      } catch (Exception exception) {
        exception.printStackTrace();
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.BEHAVIOR.SAVE.FAILURE") + "\n" + exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
      } 
    } else if (paramInt == 1) {
      BufferedImage bufferedImage = new BufferedImage(this.pnlBody.getWidth(), this.pnlBody.getHeight() + 100, 6);
      Graphics2D graphics2D = bufferedImage.createGraphics();
      int i = _$26215(graphics2D, bufferedImage.getWidth());
      graphics2D.translate(0, i);
      this.pnlBody.paintAll(graphics2D);
      graphics2D.dispose();
      File file1 = new File(str1);
      if (!file1.exists())
        file1.mkdir(); 
      File file2 = new File(str1 + "\\" + "s" + this.lblFileName.getText() + "_" + this.txtUserDescription.getText() + ".png");
      try {
        ImageIO.write(bufferedImage, "png", file2);
        this.lblFileSaveResult.setText(ATPMessages.getString("MW.BEHAVIOR.SAVE.OK"));
      } catch (Exception exception) {
        exception.printStackTrace();
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.BEHAVIOR.SAVE.FAILURE") + "\n" + exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
      } 
    } 
  }
  
  public void stateChanged(ChangeEvent paramChangeEvent) {
    JSlider jSlider = (JSlider)paramChangeEvent.getSource();
    if (!jSlider.getValueIsAdjusting()) {
      int i = jSlider.getValue();
      if (jSlider.equals(this._$26042)) {
        if (i <= 10) {
          for (byte b = 0; b < 5; b++) {
            (this._$26044[b]).dpiY = this._$18000[b] * i / 10.0D;
            this._$26043[b].resetScale();
          } 
        } else {
          for (byte b = 0; b < 5; b++) {
            (this._$26044[b]).dpiY = this._$18000[b] * (i - 10);
            this._$26043[b].resetScale();
          } 
        } 
      } else {
        if (this.rdoDrawByDist.isSelected()) {
          if (i <= 10) {
            this.scps.dpiDistX = this._$8451 * i / 10.0D;
          } else {
            this.scps.dpiDistX = this._$8451 * (i - 10);
          } 
          for (byte b = 0; b < this._$26043.length; b++)
            this._$26043[b].setScaleData(); 
        } else {
          if (i <= 10) {
            this.scps.dpiX = this._$8450 * i / 10.0D;
          } else {
            this.scps.dpiX = this._$8450 * (i - 10);
          } 
          for (byte b = 0; b < this._$26043.length; b++)
            this._$26043[b].setScaleData(); 
        } 
        this.Body.setPreferredSize(new Dimension(this._$26043[0].showBattleLingDepth() + 70, 30));
        this.scroll.setViewportView((Component)this.Body);
      } 
      _$26144();
      repaint();
    } 
  }
  
  private Vector _$26110(int paramInt1, int paramInt2) throws Exception {
    this._$17917.setMouseXY(paramInt1, paramInt2);
    this._$17918.setMouseXY(paramInt1, paramInt2);
    this._$17919.setMouseXY(paramInt1, paramInt2);
    this._$17920.setMouseXY(paramInt1, paramInt2);
    this._$26032.setMouseXY(paramInt1, paramInt2);
    this._$26030.setMouseXY(paramInt1, paramInt2);
    this._$8363.setMouseXY(paramInt1, paramInt2);
    this._$17926.setMouseXY(paramInt1, paramInt2);
    this._$17927.setMouseXY(paramInt1, paramInt2);
    this._$26033.setMouseXY(paramInt1, paramInt2);
    int[] arrayOfInt = { paramInt1, paramInt2 };
    this._$26023.clear();
    this._$26023.add(arrayOfInt);
    this._$26023.add(new Date(this._$17917.soloderReport()));
    this._$26023.add(ATPMessages.getString("MW.GNL.SPEED") + this._$17917.getMousePointStatus());
    this._$26023.add(ATPMessages.getString("MW.GNL.PERMITTED_SPEED") + this._$17918.getMousePointStatus().toString());
    this._$26023.add(ATPMessages.getString("MW.GNL.RELEASE_SPEED") + this._$17919.getMousePointStatus().toString());
    this._$26023.add(ATPMessages.getString("MW.GNL.TARGET_SPEED") + this._$17920.getMousePointStatus().toString());
    this._$26023.add(ATPMessages.getString("MW.GNL.ADHESION") + this._$26032.getMousePointStatus().toString());
    this._$26023.add(ATPMessages.getString("MW.GNL.WARN") + this._$26030.getMousePointStatus().toString());
    this._$26023.add(ATPMessages.getString("MW.GNL.EB") + this._$8363.getMousePointStatus().toString());
    this._$26023.add(ATPMessages.getString("MW.GNL.SB") + this._$17926.getMousePointStatus().toString());
    this._$26023.add(ATPMessages.getString("MW.GNL.ATP_MODE") + this._$17927.getMousePointStatus().toString());
    int i = Arrays.binarySearch(this.accdatax.toArray(), new Date(this._$17918.soloderReport()));
    int j = 0;
    if (i < 0) {
      if (i == -1 || i <= -this.accdatay.size()) {
        j = 0;
      } else {
        j = ((Integer)this.accdatay.get(Math.abs(i) - 2)).intValue();
      } 
    } else {
      j = ((Integer)this.accdatay.get(i)).intValue();
    } 
    float f = j / 100.0F;
    this._$26023.add(ATPMessages.getString("MW.GNL.ACCELERATION") + ":" + f + "m/s^2");
    return this._$26023;
  }
  
  protected void zoomChange(long paramLong1, long paramLong2) {
    try {
      this._$26045.changeZoom(new Date(paramLong1), new Date(paramLong2));
      _$26115();
      _$26116();
      this.scroll.setViewportView((Component)this.Body);
      repaint();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
