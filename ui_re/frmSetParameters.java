package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.ui.frmSetParameters_btn_actionAdapter;
import com.MiTAC.TRA.ATP.ui.pnlParaFTP;
import com.MiTAC.TRA.ATP.ui.pnlParaGraphic;
import com.MiTAC.TRA.ATP.ui.pnlParaPath;
import com.MiTAC.TRA.ATP.ui.pnlParaPrint;
import com.MiTAC.TRA.ATP.ui.pnlParaSQL;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;

public class frmSetParameters extends JDialog {
  BorderLayout borderLayout1 = new BorderLayout();
  
  BorderLayout borderLayout2 = new BorderLayout();
  
  JButton btnCancel = new JButton();
  
  JButton btnReset = new JButton();
  
  JButton btnSave = new JButton();
  
  boolean changes = false;
  
  boolean closeWithoutAsk = false;
  
  JPanel jPanel1 = new JPanel();
  
  JPanel jPanel2 = new JPanel();
  
  JTree jTree1 = new JTree();
  
  pnlParaFTP pnlFTP;
  
  pnlParaGraphic pnlGraphic;
  
  JPanel pnlMain = new JPanel();
  
  pnlParaPath pnlPath;
  
  pnlParaPrint pnlPrint;
  
  pnlParaSQL pnlSQL;
  
  private static final long serialVersionUID = 1L;
  
  Vector tmp = new Vector();
  
  public frmSetParameters(Frame paramFrame) {
    super(paramFrame, ATPMessages.getString("MW.PARA.SETTING"), true);
    try {
      this.pnlPath = new pnlParaPath();
      this.pnlFTP = new pnlParaFTP();
      this.pnlGraphic = new pnlParaGraphic();
      this.pnlSQL = new pnlParaSQL();
      this.pnlPrint = new pnlParaPrint();
      init();
      this.pnlMain.add((Component)this.pnlPath, "Center");
      this.jTree1.addMouseListener((MouseListener)new Object(this));
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnCancel_actionPerformed(ActionEvent paramActionEvent) {
    dispose();
  }
  
  void btnConfirm_actionPerformed(ActionEvent paramActionEvent) {
    try {
      _$11112();
      if (this.changes && JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.GNL.SAVE.Q.CONFIRM"), ATPMessages.getString("MW.GNL.SAVE"), 0, 3) == 0) {
        InitParameters.saveSetting();
        JOptionPane.showMessageDialog(this, ATPMessages.getString("MW.PARA.SAVE.RELOGIN"), ATPMessages.getString("MW.GNL.INFO"), 2);
        this.changes = false;
      } 
      this.closeWithoutAsk = true;
      dispose();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnReset_actionPerformed(ActionEvent paramActionEvent) {
    try {
      InitParameters.start();
      this.pnlFTP.callbackValue();
      this.pnlGraphic.callbackValue();
      this.pnlPath.callbackValue();
      this.pnlSQL.callbackValue();
      this.pnlPrint.callbackValue();
      repaint();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void dispose() {
    try {
      if (!this.closeWithoutAsk && JOptionPane.showConfirmDialog(this, ATPMessages.getString("MW.PARA.Q.SAVE_AND_EXIT"), ATPMessages.getString("MW.GNL.SAVE"), 0, 3) == 0) {
        _$11112();
        InitParameters.saveSetting();
      } 
      super.dispose();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void init() throws Exception {
    setDefaultCloseOperation(2);
    setSize(510, 480);
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
    setResizable(false);
    this.btnCancel.setText(ATPMessages.getString("MW.GNL.CANCEL"));
    this.btnCancel.setActionCommand("cancel");
    this.btnCancel.addActionListener((ActionListener)new frmSetParameters_btn_actionAdapter(this));
    this.btnSave.setText(ATPMessages.getString("MW.GNL.SAVE"));
    this.btnSave.addActionListener((ActionListener)new frmSetParameters_btn_actionAdapter(this));
    this.btnSave.setActionCommand("confirm");
    this.jPanel2.setLayout(this.borderLayout2);
    this.jPanel1.setBorder(BorderFactory.createEtchedBorder());
    this.btnReset.setText(ATPMessages.getString("MW.PARA.REVERSE_DEFAULT"));
    this.btnReset.addActionListener((ActionListener)new frmSetParameters_btn_actionAdapter(this));
    this.btnReset.setActionCommand("reverse");
    this.tmp.add(ATPMessages.getString("MW.PARA.PATH.SETTING"));
    this.tmp.add(ATPMessages.getString("MW.PARA.DB.SETTING"));
    this.tmp.add(ATPMessages.getString("MW.PARA.FTP.SETTING"));
    this.tmp.add(ATPMessages.getString("MW.PARA.GRAPHIC.SETTING"));
    this.tmp.add(ATPMessages.getString("MW.PARA.PRINT.SETTING"));
    this.jTree1 = new JTree(this.tmp);
    getContentPane().setLayout(this.borderLayout1);
    this.jTree1.setAutoscrolls(false);
    this.jTree1.setSelectionRow(0);
    this.jTree1.setBorder(BorderFactory.createLoweredBevelBorder());
    this.jTree1.setToolTipText("");
    getContentPane().add(this.jTree1, "West");
    this.jPanel1.add(this.btnSave, (Object)null);
    this.jPanel1.add(this.btnCancel, (Object)null);
    this.jPanel1.add(this.btnReset, (Object)null);
    this.jPanel2.add(this.pnlMain, "Center");
    getContentPane().add(this.jPanel2, "Center");
    this.jPanel2.add(this.jPanel1, "South");
  }
  
  private void _$11112() {
    Object[] arrayOfObject1 = this.pnlPath.getValues();
    Object[] arrayOfObject2 = this.pnlSQL.getValues();
    Object[] arrayOfObject3 = this.pnlFTP.getValues();
    Object[] arrayOfObject4 = this.pnlGraphic.getValues();
    Object[] arrayOfObject5 = this.pnlPrint.getValues();
    System.out.println("");
    System.out.println("ATPMWbin.ini");
    System.out.println("===CHANGES=====================================");
    if (!InitParameters.USBPath.equals(arrayOfObject1[0])) {
      this.changes = true;
      System.out.print("USB PATH : ");
      System.out.print(InitParameters.USBPath + " <|> ");
      InitParameters.USBPath = (String)arrayOfObject1[0];
      System.out.println((String)arrayOfObject1[0] + " <|> " + InitParameters.USBPath);
      System.out.print("USB LOG PATH: ");
      System.out.print(InitParameters.USBLogPath + " <|> ");
      InitParameters.USBLogPath = (String)arrayOfObject1[1];
      System.out.println((String)arrayOfObject1[1] + " <|> " + InitParameters.USBLogPath);
      System.out.print("USB MAINTAIN PATH: ");
      System.out.print(InitParameters.USBMaintainPath + " <|> ");
      InitParameters.USBMaintainPath = (String)arrayOfObject1[2];
      System.out.println((String)arrayOfObject1[2] + " <|> " + InitParameters.USBMaintainPath);
      System.out.print("USB RU LOG PATH:");
      System.out.print(InitParameters.USBRULogPath + " <|> ");
      InitParameters.USBRULogPath = (String)arrayOfObject1[3];
      System.out.println((String)arrayOfObject1[3] + " <|> " + InitParameters.USBRULogPath);
    } 
    if (!InitParameters.MOPath.equals(arrayOfObject1[4])) {
      this.changes = true;
      System.out.print("MO PATH: ");
      System.out.print(InitParameters.MOPath + " <|> ");
      InitParameters.MOPath = (String)arrayOfObject1[4];
      System.out.println((String)arrayOfObject1[4] + " <|> " + InitParameters.MOPath);
    } 
    if (!InitParameters.MWLogPath.equals(arrayOfObject1[5])) {
      this.changes = true;
      System.out.print("MW LOG PATH: ");
      System.out.print(InitParameters.MWLogPath + " <|> ");
      InitParameters.MWLogPath = (String)arrayOfObject1[5];
      System.out.println((String)arrayOfObject1[5] + " <|> " + InitParameters.MWLogPath);
    } 
    if (!InitParameters.MWPath.equals(arrayOfObject1[6])) {
      this.changes = true;
      System.out.print("MW LOG TO SMB OR LOCAL: ");
      System.out.print(InitParameters.MWPath + " <|> ");
      InitParameters.MWPath = (String)arrayOfObject1[6];
      System.out.println((String)arrayOfObject1[6] + " <|> " + InitParameters.MWPath);
    } 
    if (!InitParameters.MWSMBPath.equals(arrayOfObject1[7])) {
      this.changes = true;
      System.out.print("MW SMB PATH: ");
      System.out.print(InitParameters.MWSMBPath + " <|> ");
      InitParameters.MWSMBPath = (String)arrayOfObject1[7];
      System.out.println((String)arrayOfObject1[7] + " <|> " + InitParameters.MWSMBPath);
    } 
    if (!InitParameters.MWSMBUser.equals(arrayOfObject1[8])) {
      this.changes = true;
      System.out.print("MW SMB USER: ");
      System.out.print(InitParameters.MWSMBUser + " <|> ");
      InitParameters.MWSMBUser = (String)arrayOfObject1[8];
      System.out.println((String)arrayOfObject1[8] + " <|> " + InitParameters.MWSMBUser);
    } 
    if (!InitParameters.MWSMBPWD.equals(arrayOfObject1[9])) {
      this.changes = true;
      System.out.print("MW SMB PWD: ");
      System.out.print(InitParameters.MWSMBPWD + " <|> ");
      InitParameters.MWSMBPWD = (String)arrayOfObject1[9];
      System.out.println((String)arrayOfObject1[9] + " <|> " + InitParameters.MWSMBPWD);
    } 
    if (!InitParameters.SQLName.equals(arrayOfObject2[0])) {
      this.changes = true;
      System.out.print("SQL NAME: ");
      System.out.print(InitParameters.SQLName + " <|> ");
      InitParameters.SQLName = (String)arrayOfObject2[0];
      System.out.println((String)arrayOfObject2[0] + " <|> " + InitParameters.SQLName);
    } 
    if (!InitParameters.SQLUserName.equals(arrayOfObject2[1])) {
      this.changes = true;
      System.out.print("SQL USER: ");
      System.out.print(InitParameters.SQLUserName + " <|> ");
      InitParameters.SQLUserName = (String)arrayOfObject2[1];
      System.out.println((String)arrayOfObject2[1] + " <|> " + InitParameters.SQLUserName);
    } 
    if (!InitParameters.SQLUserPWD.equals(arrayOfObject2[2])) {
      this.changes = true;
      System.out.print("SQL USER PWD: ");
      System.out.print(InitParameters.SQLUserPWD + " <|> ");
      InitParameters.SQLUserPWD = (String)arrayOfObject2[2];
      System.out.println((String)arrayOfObject2[2] + " <|> " + InitParameters.SQLUserPWD);
    } 
    if (!InitParameters.FTPHostIP.equals(arrayOfObject3[0])) {
      this.changes = true;
      System.out.print("FTP HOST: ");
      System.out.print(InitParameters.FTPHostIP + " <|> ");
      InitParameters.FTPHostIP = (String)arrayOfObject3[0];
      System.out.println((String)arrayOfObject3[0] + " <|> " + InitParameters.FTPHostIP);
    } 
    if (InitParameters.FTPPort != ((Integer)arrayOfObject3[1]).intValue()) {
      this.changes = true;
      System.out.print("FTP PORT: ");
      System.out.print(InitParameters.FTPPort + " <|> ");
      InitParameters.FTPPort = ((Integer)arrayOfObject3[1]).intValue();
      System.out.println(((Integer)arrayOfObject3[1]).intValue() + " <|> " + InitParameters.FTPPort);
    } 
    if (!InitParameters.FTPUserName.equals(arrayOfObject3[2])) {
      this.changes = true;
      System.out.print("FTP USER: ");
      System.out.print(InitParameters.FTPUserName + " <|> ");
      InitParameters.FTPUserName = (String)arrayOfObject3[2];
      System.out.println((String)arrayOfObject3[2] + " <|> " + InitParameters.FTPUserName);
    } 
    if (!InitParameters.FTPUserPWD.equals(arrayOfObject3[3])) {
      this.changes = true;
      System.out.print("FTP USER PWD: ");
      System.out.print(InitParameters.FTPUserPWD + " <|> ");
      InitParameters.FTPUserPWD = (String)arrayOfObject3[3];
      System.out.println((String)arrayOfObject3[3] + " <|> " + InitParameters.FTPUserPWD);
    } 
    if (InitParameters.DpiX != ((Double)arrayOfObject4[0]).doubleValue()) {
      this.changes = true;
      System.out.print("DPIX: ");
      System.out.print(InitParameters.DpiX + " <|> ");
      InitParameters.DpiX = ((Double)arrayOfObject4[0]).doubleValue();
      System.out.println(((Double)arrayOfObject4[0]).doubleValue() + " <|> " + InitParameters.DpiX);
    } 
    if (InitParameters.DpiXD != ((Double)arrayOfObject4[1]).doubleValue()) {
      this.changes = true;
      System.out.print("DPIX_D: ");
      System.out.print(InitParameters.DpiXD + " <|> ");
      InitParameters.DpiXD = ((Double)arrayOfObject4[1]).doubleValue();
      System.out.println(((Double)arrayOfObject4[1]).doubleValue() + " <|> " + InitParameters.DpiXD);
    } 
    if (InitParameters.IntervalX != ((Integer)arrayOfObject4[2]).intValue()) {
      this.changes = true;
      System.out.print("INTERVALX: ");
      System.out.print(InitParameters.IntervalX + " <|> ");
      InitParameters.IntervalX = ((Integer)arrayOfObject4[2]).intValue();
      System.out.println(((Integer)arrayOfObject4[2]).intValue() + " <|> " + InitParameters.IntervalX);
    } 
    if (InitParameters.BrightLine != ((Integer)arrayOfObject4[3]).intValue()) {
      this.changes = true;
      System.out.print("BRIGHT LINE: ");
      System.out.print(InitParameters.BrightLine + " <|> ");
      InitParameters.BrightLine = ((Integer)arrayOfObject4[3]).intValue();
      System.out.println(((Integer)arrayOfObject4[3]).intValue() + " <|> " + InitParameters.BrightLine);
    } 
    if (InitParameters.IntervalXD != ((Integer)arrayOfObject4[4]).intValue()) {
      this.changes = true;
      System.out.print("INTERVAL_D: ");
      System.out.print(InitParameters.IntervalXD + " <|> ");
      InitParameters.IntervalXD = ((Integer)arrayOfObject4[4]).intValue();
      System.out.println(((Integer)arrayOfObject4[4]).intValue() + " <|> " + InitParameters.IntervalXD);
    } 
    if (InitParameters.BrightLineD != ((Integer)arrayOfObject4[5]).intValue()) {
      this.changes = true;
      System.out.print("BRIGHT LINE_D: ");
      System.out.print(InitParameters.BrightLineD + " <|> ");
      InitParameters.BrightLineD = ((Integer)arrayOfObject4[5]).intValue();
      System.out.println(((Integer)arrayOfObject4[5]).intValue() + " <|> " + InitParameters.BrightLineD);
    } 
    if (InitParameters.SpeedDpi != ((Double)arrayOfObject4[6]).doubleValue()) {
      this.changes = true;
      System.out.print("SPEED DPI: ");
      System.out.print(InitParameters.SpeedDpi + " <|> ");
      InitParameters.SpeedDpi = ((Double)arrayOfObject4[6]).doubleValue();
      System.out.println(((Double)arrayOfObject4[6]).doubleValue() + " <|> " + InitParameters.SpeedDpi);
    } 
    if (InitParameters.SpeedMax != ((Integer)arrayOfObject4[7]).intValue()) {
      this.changes = true;
      System.out.print("SPEED MAX: ");
      System.out.print(InitParameters.SpeedMax + " <|> ");
      InitParameters.SpeedMax = ((Integer)arrayOfObject4[7]).intValue();
      System.out.println(((Integer)arrayOfObject4[7]).intValue() + " <|> " + InitParameters.SpeedMax);
    } 
    if (InitParameters.SpeedMin != ((Integer)arrayOfObject4[8]).intValue()) {
      this.changes = true;
      System.out.print("SPEED MIN: ");
      System.out.print(InitParameters.SpeedMin + " <|> ");
      InitParameters.SpeedMin = ((Integer)arrayOfObject4[8]).intValue();
      System.out.println(((Integer)arrayOfObject4[8]).intValue() + " <|> " + InitParameters.SpeedMin);
    } 
    if (InitParameters.SpeedInterval != ((Integer)arrayOfObject4[9]).intValue()) {
      this.changes = true;
      System.out.print("SPEED INTERVAL: ");
      System.out.print(InitParameters.SpeedInterval + " <|> ");
      InitParameters.SpeedInterval = ((Integer)arrayOfObject4[9]).intValue();
      System.out.println(((Integer)arrayOfObject4[9]).intValue() + " <|> " + InitParameters.SpeedInterval);
    } 
    if (!InitParameters.SpeedMsg.equals(arrayOfObject4[10])) {
      this.changes = true;
      System.out.print("SPEED MSG: ");
      System.out.print(InitParameters.SpeedMsg + " <|> ");
      InitParameters.SpeedMsg = (String)arrayOfObject4[10];
      System.out.println((String)arrayOfObject4[10] + " <|> " + InitParameters.SpeedMsg);
    } 
    if (InitParameters.WarnDpi != ((Double)arrayOfObject4[11]).doubleValue()) {
      this.changes = true;
      System.out.print("WARN DPI: ");
      System.out.print(InitParameters.WarnDpi + " <|> ");
      InitParameters.WarnDpi = ((Double)arrayOfObject4[11]).doubleValue();
      System.out.println(((Double)arrayOfObject4[11]).doubleValue() + " <|> " + InitParameters.WarnDpi);
    } 
    if (InitParameters.WarnMax != ((Integer)arrayOfObject4[12]).intValue()) {
      this.changes = true;
      System.out.print("WARN MAX: ");
      System.out.print(InitParameters.WarnMax + " <|> ");
      InitParameters.WarnMax = ((Integer)arrayOfObject4[12]).intValue();
      System.out.println(((Integer)arrayOfObject4[12]).intValue() + " <|> " + InitParameters.WarnMax);
    } 
    if (InitParameters.WarnMin != ((Integer)arrayOfObject4[13]).intValue()) {
      this.changes = true;
      System.out.print("WARN MIN: ");
      System.out.print(InitParameters.WarnMin + " <|> ");
      InitParameters.WarnMin = ((Integer)arrayOfObject4[13]).intValue();
      System.out.println(((Integer)arrayOfObject4[13]).intValue() + " <|> " + InitParameters.WarnMin);
    } 
    if (InitParameters.WarnInterval != ((Integer)arrayOfObject4[14]).intValue()) {
      this.changes = true;
      System.out.print("WARN INTERVAL: ");
      System.out.print(InitParameters.WarnInterval + " <|> ");
      InitParameters.WarnInterval = ((Integer)arrayOfObject4[14]).intValue();
      System.out.println(((Integer)arrayOfObject4[14]).intValue() + " <|> " + InitParameters.WarnInterval);
    } 
    if (!InitParameters.WarnMsg.equals(arrayOfObject4[15])) {
      this.changes = true;
      System.out.print("WARN MSG: ");
      System.out.print(InitParameters.WarnMsg + " <|> ");
      InitParameters.WarnMsg = (String)arrayOfObject4[15];
      System.out.println((String)arrayOfObject4[15] + " <|> " + InitParameters.WarnMsg);
    } 
    if (InitParameters.BTDpi != ((Double)arrayOfObject4[16]).doubleValue()) {
      this.changes = true;
      System.out.print("BT DPI: ");
      System.out.print(InitParameters.BTDpi + " <|> ");
      InitParameters.BTDpi = ((Double)arrayOfObject4[16]).doubleValue();
      System.out.println(((Double)arrayOfObject4[16]).doubleValue() + " <|> " + InitParameters.BTDpi);
    } 
    if (InitParameters.BTMax != ((Integer)arrayOfObject4[17]).intValue()) {
      this.changes = true;
      System.out.print("BT MAX: ");
      System.out.print(InitParameters.BTMax + " <|> ");
      InitParameters.BTMax = ((Integer)arrayOfObject4[17]).intValue();
      System.out.println(((Integer)arrayOfObject4[17]).intValue() + " <|> " + InitParameters.BTMax);
    } 
    if (InitParameters.BTMin != ((Integer)arrayOfObject4[18]).intValue()) {
      this.changes = true;
      System.out.print("BT MIN: ");
      System.out.print(InitParameters.BTMin + " <|> ");
      InitParameters.BTMin = ((Integer)arrayOfObject4[18]).intValue();
      System.out.println(((Integer)arrayOfObject4[18]).intValue() + " <|> " + InitParameters.BTMin);
    } 
    if (InitParameters.BTInterval != ((Integer)arrayOfObject4[19]).intValue()) {
      this.changes = true;
      System.out.print("BT INTERVAL: ");
      System.out.print(InitParameters.BTInterval + " <|> ");
      InitParameters.BTInterval = ((Integer)arrayOfObject4[19]).intValue();
      System.out.println(((Integer)arrayOfObject4[19]).intValue() + " <|> " + InitParameters.BTInterval);
    } 
    if (!InitParameters.BTMsg.equals(arrayOfObject4[20])) {
      this.changes = true;
      System.out.print("BT MSG: ");
      System.out.print(InitParameters.BTMsg + " <|> ");
      InitParameters.BTMsg = (String)arrayOfObject4[20];
      System.out.println((String)arrayOfObject4[20] + " <|> " + InitParameters.BTMsg);
    } 
    if (InitParameters.SlipDpi != ((Double)arrayOfObject4[21]).doubleValue()) {
      this.changes = true;
      System.out.print("SLIP DPI: ");
      System.out.print(InitParameters.SlipDpi + " <|> ");
      InitParameters.SlipDpi = ((Double)arrayOfObject4[21]).doubleValue();
      System.out.println(((Double)arrayOfObject4[21]).doubleValue() + " <|> " + InitParameters.SlipDpi);
    } 
    if (InitParameters.SlipMax != ((Integer)arrayOfObject4[22]).intValue()) {
      this.changes = true;
      System.out.print("SLIP MAX: ");
      System.out.print(InitParameters.SlipMax + " <|> ");
      InitParameters.SlipMax = ((Integer)arrayOfObject4[22]).intValue();
      System.out.println(((Integer)arrayOfObject4[22]).intValue() + " <|> " + InitParameters.SlipMax);
    } 
    if (InitParameters.SlipMin != ((Integer)arrayOfObject4[23]).intValue()) {
      this.changes = true;
      System.out.print("SLIP MIN: ");
      System.out.print(InitParameters.SlipMin + " <|> ");
      InitParameters.SlipMin = ((Integer)arrayOfObject4[23]).intValue();
      System.out.println(((Integer)arrayOfObject4[23]).intValue() + " <|> " + InitParameters.SlipMin);
    } 
    if (InitParameters.SlipInterval != ((Integer)arrayOfObject4[24]).intValue()) {
      this.changes = true;
      System.out.print("SLIP INTERVAL: ");
      System.out.print(InitParameters.SlipInterval + " <|> ");
      InitParameters.SlipInterval = ((Integer)arrayOfObject4[24]).intValue();
      System.out.println(((Integer)arrayOfObject4[24]).intValue() + " <|> " + InitParameters.SlipInterval);
    } 
    if (!InitParameters.SlipMsg.equals(arrayOfObject4[25])) {
      this.changes = true;
      System.out.print("SLIP MSG: ");
      System.out.print(InitParameters.SlipMsg + " <|> ");
      InitParameters.SlipMsg = (String)arrayOfObject4[25];
      System.out.println((String)arrayOfObject4[25] + " <|> " + InitParameters.SlipMsg);
    } 
    if (InitParameters.EBDpi != ((Double)arrayOfObject4[26]).doubleValue()) {
      this.changes = true;
      System.out.print("EB DPI: ");
      System.out.print(InitParameters.EBDpi + " <|> ");
      InitParameters.EBDpi = ((Double)arrayOfObject4[26]).doubleValue();
      System.out.println(((Double)arrayOfObject4[26]).doubleValue() + " <|> " + InitParameters.EBDpi);
    } 
    if (InitParameters.EBMax != ((Integer)arrayOfObject4[27]).intValue()) {
      this.changes = true;
      System.out.print("EB MAX: ");
      System.out.print(InitParameters.EBMax + " <|> ");
      InitParameters.EBMax = ((Integer)arrayOfObject4[27]).intValue();
      System.out.println(((Integer)arrayOfObject4[27]).intValue() + " <|> " + InitParameters.EBMax);
    } 
    if (InitParameters.EBMin != ((Integer)arrayOfObject4[28]).intValue()) {
      this.changes = true;
      System.out.print("EB MIN: ");
      System.out.print(InitParameters.EBMin + " <|> ");
      InitParameters.EBMin = ((Integer)arrayOfObject4[28]).intValue();
      System.out.println(((Integer)arrayOfObject4[28]).intValue() + " <|> " + InitParameters.EBMin);
    } 
    if (InitParameters.EBInterval != ((Integer)arrayOfObject4[29]).intValue()) {
      this.changes = true;
      System.out.print("EB INTERVAL: ");
      System.out.print(InitParameters.EBInterval + " <|> ");
      InitParameters.EBInterval = ((Integer)arrayOfObject4[29]).intValue();
      System.out.println(((Integer)arrayOfObject4[29]).intValue() + " <|> " + InitParameters.EBInterval);
    } 
    if (!InitParameters.EBMsg.equals(arrayOfObject4[30])) {
      this.changes = true;
      System.out.print("EB MSG: ");
      System.out.print(InitParameters.EBMsg + " <|> ");
      InitParameters.EBMsg = (String)arrayOfObject4[30];
      System.out.println((String)arrayOfObject4[30] + " <|> " + InitParameters.EBMsg);
    } 
    if (InitParameters.ModeDpi != ((Double)arrayOfObject4[31]).doubleValue()) {
      this.changes = true;
      System.out.print("MODE DPI: ");
      System.out.print(InitParameters.ModeDpi + " <|> ");
      InitParameters.ModeDpi = ((Double)arrayOfObject4[31]).doubleValue();
      System.out.println(((Double)arrayOfObject4[31]).doubleValue() + " <|> " + InitParameters.ModeDpi);
    } 
    if (InitParameters.ModeMax != ((Integer)arrayOfObject4[32]).intValue()) {
      this.changes = true;
      System.out.print("MODE MAX: ");
      System.out.print(InitParameters.ModeMax + " <|> ");
      InitParameters.ModeMax = ((Integer)arrayOfObject4[32]).intValue();
      System.out.println(((Integer)arrayOfObject4[32]).intValue() + " <|> " + InitParameters.ModeMax);
    } 
    if (InitParameters.ModeMin != ((Integer)arrayOfObject4[33]).intValue()) {
      this.changes = true;
      System.out.print("MODE MIN: ");
      System.out.print(InitParameters.ModeMin + " <|> ");
      InitParameters.ModeMin = ((Integer)arrayOfObject4[33]).intValue();
      System.out.println(((Integer)arrayOfObject4[33]).intValue() + " <|> " + InitParameters.ModeMin);
    } 
    if (InitParameters.ModeInterval != ((Integer)arrayOfObject4[34]).intValue()) {
      this.changes = true;
      System.out.print("MODE INTERVAL: ");
      System.out.print(InitParameters.ModeInterval + " <|> ");
      InitParameters.ModeInterval = ((Integer)arrayOfObject4[34]).intValue();
      System.out.println(((Integer)arrayOfObject4[34]).intValue() + " <|> " + InitParameters.ModeInterval);
    } 
    if (!InitParameters.ModeMsg.equals(arrayOfObject4[35])) {
      this.changes = true;
      System.out.print("MODE MSG: ");
      System.out.print(InitParameters.ModeMsg + " <|> ");
      InitParameters.ModeMsg = (String)arrayOfObject4[35];
      System.out.println((String)arrayOfObject4[35] + " <|> " + InitParameters.ModeMsg);
    } 
    if (InitParameters.BackGround != (Color)arrayOfObject4[36]) {
      this.changes = true;
      System.out.print("BACKGROUND COLOR: ");
      System.out.print(InitParameters.BackGround + " <|> ");
      InitParameters.BackGround = (Color)arrayOfObject4[36];
      System.out.println((Color)arrayOfObject4[36] + " <|> " + InitParameters.BackGround);
    } 
    if (InitParameters.MainLine != (Color)arrayOfObject4[37]) {
      this.changes = true;
      System.out.print("MAIN LINE COLOR: ");
      System.out.print(InitParameters.MainLine + " <|> ");
      InitParameters.MainLine = (Color)arrayOfObject4[37];
      System.out.println((Color)arrayOfObject4[37] + " <|> " + InitParameters.MainLine);
    } 
    if (InitParameters.BaseLine != (Color)arrayOfObject4[38]) {
      this.changes = true;
      System.out.print("BASE LINE: ");
      System.out.print(InitParameters.BaseLine + " <|> ");
      InitParameters.BaseLine = (Color)arrayOfObject4[38];
      System.out.println((Color)arrayOfObject4[38] + " <|> " + InitParameters.BaseLine);
    } 
    if (InitParameters.GridLine != (Color)arrayOfObject4[39]) {
      this.changes = true;
      System.out.print("GRID LINE COLOR: ");
      System.out.print(InitParameters.GridLine + " <|> ");
      InitParameters.GridLine = (Color)arrayOfObject4[39];
      System.out.println((Color)arrayOfObject4[39] + " <|> " + InitParameters.GridLine);
    } 
    if (InitParameters.Char != (Color)arrayOfObject4[40]) {
      this.changes = true;
      System.out.print("CHAR COLOR: ");
      System.out.print(InitParameters.Char + " <|> ");
      InitParameters.Char = (Color)arrayOfObject4[40];
      System.out.println((Color)arrayOfObject4[40] + " <|> " + InitParameters.Char);
    } 
    if (InitParameters.Mouse != (Color)arrayOfObject4[41]) {
      this.changes = true;
      System.out.print("MOUSE COLOR: ");
      System.out.print(InitParameters.Mouse + " <|> ");
      InitParameters.Mouse = (Color)arrayOfObject4[41];
      System.out.println((Color)arrayOfObject4[41] + " <|> " + InitParameters.Mouse);
    } 
    if (InitParameters.SpeedColor != (Color)arrayOfObject4[42]) {
      this.changes = true;
      System.out.print("SPEED COLOR: ");
      System.out.print(InitParameters.SpeedColor + " <|> ");
      InitParameters.SpeedColor = (Color)arrayOfObject4[42];
      System.out.println((Color)arrayOfObject4[42] + " <|> " + InitParameters.SpeedColor);
    } 
    if (InitParameters.PmSpeedColor != (Color)arrayOfObject4[43]) {
      this.changes = true;
      System.out.print("PERMITTED SPEED COLOR: ");
      System.out.print(InitParameters.PmSpeedColor + " <|> ");
      InitParameters.PmSpeedColor = (Color)arrayOfObject4[43];
      System.out.println((Color)arrayOfObject4[43] + " <|> " + InitParameters.PmSpeedColor);
    } 
    if (InitParameters.RsSpeedColor != (Color)arrayOfObject4[44]) {
      this.changes = true;
      System.out.print("RELEASE SPEED COLOR: ");
      System.out.print(InitParameters.RsSpeedColor + " <|> ");
      InitParameters.RsSpeedColor = (Color)arrayOfObject4[44];
      System.out.println((Color)arrayOfObject4[44] + " <|> " + InitParameters.RsSpeedColor);
    } 
    if (InitParameters.TgSpeedColor != (Color)arrayOfObject4[45]) {
      this.changes = true;
      System.out.print("TARGET SPEED COLOR: ");
      System.out.print(InitParameters.TgSpeedColor + " <|> ");
      InitParameters.TgSpeedColor = (Color)arrayOfObject4[45];
      System.out.println((Color)arrayOfObject4[45] + " <|> " + InitParameters.TgSpeedColor);
    } 
    if (InitParameters.AhSpeedColor != (Color)arrayOfObject4[46]) {
      this.changes = true;
      System.out.print("ADHESION COLOR: ");
      System.out.print(InitParameters.AhSpeedColor + " <|> ");
      InitParameters.AhSpeedColor = (Color)arrayOfObject4[46];
      System.out.println((Color)arrayOfObject4[46] + " <|> " + InitParameters.AhSpeedColor);
    } 
    if (InitParameters.ModeColor != (Color)arrayOfObject4[47]) {
      this.changes = true;
      System.out.print("MODE COLOR: ");
      System.out.print(InitParameters.ModeColor + " <|> ");
      InitParameters.ModeColor = (Color)arrayOfObject4[47];
      System.out.println((Color)arrayOfObject4[47] + " <|> " + InitParameters.ModeColor);
    } 
    if (InitParameters.SlipColor != (Color)arrayOfObject4[48]) {
      this.changes = true;
      System.out.print("SLIP COLOR: ");
      System.out.print(InitParameters.SlipColor + " <|> ");
      InitParameters.SlipColor = (Color)arrayOfObject4[48];
      System.out.println((Color)arrayOfObject4[48] + " <|> " + InitParameters.SlipColor);
    } 
    if (InitParameters.SlideColor != (Color)arrayOfObject4[49]) {
      this.changes = true;
      System.out.print("SLIDE COLOR: ");
      System.out.print(InitParameters.SlideColor + " <|> ");
      InitParameters.SlideColor = (Color)arrayOfObject4[49];
      System.out.println((Color)arrayOfObject4[49] + " <|> " + InitParameters.SlideColor);
    } 
    if (InitParameters.WarnColor != (Color)arrayOfObject4[50]) {
      this.changes = true;
      System.out.print("WARN COLOR: ");
      System.out.print(InitParameters.WarnColor + " <|> ");
      InitParameters.WarnColor = (Color)arrayOfObject4[50];
      System.out.println((Color)arrayOfObject4[50] + " <|> " + InitParameters.WarnColor);
    } 
    if (InitParameters.BTColor != (Color)arrayOfObject4[51]) {
      this.changes = true;
      System.out.print("BRAKE TARGET COLOR: ");
      System.out.print(InitParameters.BTColor + " <|> ");
      InitParameters.BTColor = (Color)arrayOfObject4[51];
      System.out.println((Color)arrayOfObject4[51] + " <|> " + InitParameters.BTColor);
    } 
    if (InitParameters.EBColor != (Color)arrayOfObject4[52]) {
      this.changes = true;
      System.out.print("EB COLOR: ");
      System.out.print(InitParameters.EBColor + " <|> ");
      InitParameters.EBColor = (Color)arrayOfObject4[52];
      System.out.println((Color)arrayOfObject4[52] + " <|> " + InitParameters.EBColor);
    } 
    if (InitParameters.SBColor != (Color)arrayOfObject4[53]) {
      this.changes = true;
      System.out.print("SB COLOR: ");
      System.out.print(InitParameters.SBColor + " <|> ");
      InitParameters.SBColor = (Color)arrayOfObject4[53];
      System.out.println((Color)arrayOfObject4[53] + " <|> " + InitParameters.SBColor);
    } 
    if (InitParameters.DrawByDist != ((((Integer)arrayOfObject4[54]).intValue() == 1))) {
      this.changes = true;
      System.out.print("DRAW TYPE: ");
      System.out.print(InitParameters.DrawByDist + " <|> ");
      InitParameters.DrawByDist = (((Integer)arrayOfObject4[54]).intValue() == 1);
      System.out.println(((((Integer)arrayOfObject4[54]).intValue() == 1) ? 1 : 0) + " <|> " + InitParameters.DrawByDist);
    } 
    if (InitParameters.PrintBackGround != (Color)arrayOfObject5[36]) {
      this.changes = true;
      System.out.print("Print BACKGROUND COLOR: ");
      System.out.print(InitParameters.PrintBackGround + " <|> ");
      InitParameters.PrintBackGround = (Color)arrayOfObject5[36];
      System.out.println((Color)arrayOfObject5[36] + " <|> " + InitParameters.PrintBackGround);
    } 
    if (InitParameters.PrintMainLine != (Color)arrayOfObject5[37]) {
      this.changes = true;
      System.out.print("Print MAIN LINE COLOR: ");
      System.out.print(InitParameters.PrintMainLine + " <|> ");
      InitParameters.PrintMainLine = (Color)arrayOfObject5[37];
      System.out.println((Color)arrayOfObject5[37] + " <|> " + InitParameters.PrintMainLine);
    } 
    if (InitParameters.PrintBaseLine != (Color)arrayOfObject5[38]) {
      this.changes = true;
      System.out.print("Print BASE LINE: ");
      System.out.print(InitParameters.PrintBaseLine + " <|> ");
      InitParameters.PrintBaseLine = (Color)arrayOfObject5[38];
      System.out.println((Color)arrayOfObject5[38] + " <|> " + InitParameters.PrintBaseLine);
    } 
    if (InitParameters.PrintGridLine != (Color)arrayOfObject5[39]) {
      this.changes = true;
      System.out.print("Print GRID LINE COLOR: ");
      System.out.print(InitParameters.PrintGridLine + " <|> ");
      InitParameters.PrintGridLine = (Color)arrayOfObject5[39];
      System.out.println((Color)arrayOfObject5[39] + " <|> " + InitParameters.PrintGridLine);
    } 
    if (InitParameters.PrintChar != (Color)arrayOfObject5[40]) {
      this.changes = true;
      System.out.print("Print CHAR COLOR: ");
      System.out.print(InitParameters.PrintChar + " <|> ");
      InitParameters.PrintChar = (Color)arrayOfObject5[40];
      System.out.println((Color)arrayOfObject5[40] + " <|> " + InitParameters.PrintChar);
    } 
    if (InitParameters.PrintMouse != (Color)arrayOfObject5[41]) {
      this.changes = true;
      System.out.print("Print MOUSE COLOR: ");
      System.out.print(InitParameters.PrintMouse + " <|> ");
      InitParameters.PrintMouse = (Color)arrayOfObject5[41];
      System.out.println((Color)arrayOfObject5[41] + " <|> " + InitParameters.PrintMouse);
    } 
    if (InitParameters.PrintSpeedColor != (Color)arrayOfObject5[42]) {
      this.changes = true;
      System.out.print("Print SPEED COLOR: ");
      System.out.print(InitParameters.PrintSpeedColor + " <|> ");
      InitParameters.PrintSpeedColor = (Color)arrayOfObject5[42];
      System.out.println((Color)arrayOfObject5[42] + " <|> " + InitParameters.PrintSpeedColor);
    } 
    if (InitParameters.PrintPmSpeedColor != (Color)arrayOfObject5[43]) {
      this.changes = true;
      System.out.print("Print PERMITTED SPEED COLOR: ");
      System.out.print(InitParameters.PrintPmSpeedColor + " <|> ");
      InitParameters.PrintPmSpeedColor = (Color)arrayOfObject5[43];
      System.out.println((Color)arrayOfObject5[43] + " <|> " + InitParameters.PrintPmSpeedColor);
    } 
    if (InitParameters.PrintRsSpeedColor != (Color)arrayOfObject5[44]) {
      this.changes = true;
      System.out.print("Print RELEASE SPEED COLOR: ");
      System.out.print(InitParameters.PrintRsSpeedColor + " <|> ");
      InitParameters.PrintRsSpeedColor = (Color)arrayOfObject5[44];
      System.out.println((Color)arrayOfObject5[44] + " <|> " + InitParameters.PrintRsSpeedColor);
    } 
    if (InitParameters.PrintTgSpeedColor != (Color)arrayOfObject5[45]) {
      this.changes = true;
      System.out.print("Print TARGET SPEED COLOR: ");
      System.out.print(InitParameters.PrintTgSpeedColor + " <|> ");
      InitParameters.PrintTgSpeedColor = (Color)arrayOfObject5[45];
      System.out.println((Color)arrayOfObject5[45] + " <|> " + InitParameters.PrintTgSpeedColor);
    } 
    if (InitParameters.PrintAhSpeedColor != (Color)arrayOfObject5[46]) {
      this.changes = true;
      System.out.print("Print ADHESION COLOR: ");
      System.out.print(InitParameters.PrintAhSpeedColor + " <|> ");
      InitParameters.PrintAhSpeedColor = (Color)arrayOfObject5[46];
      System.out.println((Color)arrayOfObject5[46] + " <|> " + InitParameters.PrintAhSpeedColor);
    } 
    if (InitParameters.PrintModeColor != (Color)arrayOfObject5[47]) {
      this.changes = true;
      System.out.print("Print MODE COLOR: ");
      System.out.print(InitParameters.PrintModeColor + " <|> ");
      InitParameters.PrintModeColor = (Color)arrayOfObject5[47];
      System.out.println((Color)arrayOfObject5[47] + " <|> " + InitParameters.PrintModeColor);
    } 
    if (InitParameters.PrintSlipColor != (Color)arrayOfObject5[48]) {
      this.changes = true;
      System.out.print("Print SLIP COLOR: ");
      System.out.print(InitParameters.PrintSlipColor + " <|> ");
      InitParameters.PrintSlipColor = (Color)arrayOfObject5[48];
      System.out.println((Color)arrayOfObject5[48] + " <|> " + InitParameters.PrintSlipColor);
    } 
    if (InitParameters.PrintSlideColor != (Color)arrayOfObject5[49]) {
      this.changes = true;
      System.out.print("Print SLIDE COLOR: ");
      System.out.print(InitParameters.PrintSlideColor + " <|> ");
      InitParameters.PrintSlideColor = (Color)arrayOfObject5[49];
      System.out.println((Color)arrayOfObject5[49] + " <|> " + InitParameters.PrintSlideColor);
    } 
    if (InitParameters.PrintWarnColor != (Color)arrayOfObject5[50]) {
      this.changes = true;
      System.out.print("Print WARN COLOR: ");
      System.out.print(InitParameters.PrintWarnColor + " <|> ");
      InitParameters.PrintWarnColor = (Color)arrayOfObject5[50];
      System.out.println((Color)arrayOfObject5[50] + " <|> " + InitParameters.PrintWarnColor);
    } 
    if (InitParameters.PrintBTColor != (Color)arrayOfObject5[51]) {
      this.changes = true;
      System.out.print("Print BRAKE TARGET COLOR: ");
      System.out.print(InitParameters.PrintBTColor + " <|> ");
      InitParameters.PrintBTColor = (Color)arrayOfObject5[51];
      System.out.println((Color)arrayOfObject5[51] + " <|> " + InitParameters.PrintBTColor);
    } 
    if (InitParameters.PrintEBColor != (Color)arrayOfObject5[52]) {
      this.changes = true;
      System.out.print("Print EB COLOR: ");
      System.out.print(InitParameters.PrintEBColor + " <|> ");
      InitParameters.EBColor = (Color)arrayOfObject5[52];
      System.out.println((Color)arrayOfObject5[52] + " <|> " + InitParameters.PrintEBColor);
    } 
    if (InitParameters.PrintSBColor != (Color)arrayOfObject5[53]) {
      this.changes = true;
      System.out.print("Print SB COLOR: ");
      System.out.print(InitParameters.PrintSBColor + " <|> ");
      InitParameters.PrintSBColor = (Color)arrayOfObject5[53];
      System.out.println((Color)arrayOfObject5[53] + " <|> " + InitParameters.PrintSBColor);
    } 
    System.out.println("===END CHANGES=================================");
  }
}
