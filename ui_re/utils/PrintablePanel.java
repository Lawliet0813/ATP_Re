package ui.utils;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.core.ATPMission;
import com.MiTAC.TRA.ATP.core.ATPMissionDetail;
import com.MiTAC.TRA.ATP.ui.panels.pnlDriverBehavior;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class PrintablePanel extends JPanel implements Printable {
  pnlDriverBehavior adaptee;
  
  private long _$2585 = 0L;
  
  ATPMissionDetail mission;
  
  String note = "";
  
  int pages = 0;
  
  private long _$9961 = 0L;
  
  private static final long serialVersionUID = 1L;
  
  private boolean _$26238(Component paramComponent) {
    if (!(paramComponent instanceof JComponent))
      return false; 
    JComponent jComponent = (JComponent)paramComponent;
    boolean bool = jComponent.isDoubleBuffered();
    jComponent.setDoubleBuffered(false);
    return bool;
  }
  
  public int print(Graphics paramGraphics, PageFormat paramPageFormat, int paramInt) throws PrinterException {
    if (paramInt > this.pages)
      return 1; 
    if (this.adaptee.printType == 1) {
      long l1 = this._$2585 + (1080000 * paramInt);
      long l2 = this._$2585 + (1080000 * (paramInt + 1));
      this.adaptee.zoomChange(l1, l2);
      this.pages = (int)(this._$9961 / 1080000L);
    } else {
      this.pages = 0;
    } 
    Graphics2D graphics2D = (Graphics2D)paramGraphics;
    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
    graphics2D.translate(paramPageFormat.getImageableX(), paramPageFormat.getImageableY());
    char c = Character.MIN_VALUE;
    byte b = 20;
    Font font = graphics2D.getFont();
    graphics2D.setFont(new Font("Default", 1, 16));
    graphics2D.drawString(ATPMessages.getString("MW.TRA"), c, b);
    graphics2D.setFont(new Font("Default", 1, 14));
    graphics2D.drawString(ATPMessages.getString("MW.ATP"), c + 140, b);
    graphics2D.drawString(ATPMessages.getString("MW.BEHAVIOR.SPEED_MONITOR"), c + 300, b);
    graphics2D.setFont(new Font("Default", 1, 10));
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    graphics2D.drawString(ATPMessages.getString("MW.BEHAVIOR.PRINT_DATE") + ": " + simpleDateFormat2.format(date), (int)paramPageFormat.getWidth() - 240, b);
    graphics2D.drawString("Page: " + (paramInt + 1) + " / " + (this.pages + 1), (int)paramPageFormat.getWidth() - 240, b + 12);
    graphics2D.setFont(font);
    b += 16;
    c = '\024';
    graphics2D.drawString(ATPMessages.getString("MW.DRIVER.ID"), c, b);
    c = '';
    ATPMissionDetail aTPMissionDetail = new ATPMissionDetail((ATPMission)this.mission);
    graphics2D.drawString(this.mission.getDriver() + "(" + aTPMissionDetail.getDriverName() + ")", c, b);
    b += 16;
    c = '\024';
    graphics2D.drawString(ATPMessages.getString("MW.GNL.DATE") + "(yyyy/MM/dd)", c, b);
    c = '';
    graphics2D.drawString(simpleDateFormat1.format(this.mission.getMissionDate()), c, b);
    b += 16;
    c = '\024';
    graphics2D.drawString(ATPMessages.getString("MW.WS.ID"), c, b);
    c = '';
    graphics2D.drawString(this.mission.getWorkShift(), c, b);
    b += 16;
    c = '\024';
    graphics2D.drawString(ATPMessages.getString("MW.TR.ID"), c, b);
    c = '';
    graphics2D.drawString(this.mission.getTrainRunning(), c, b);
    b += 16;
    c = '\024';
    graphics2D.drawString(ATPMessages.getString("MW.VEHICLE.ID"), c, b);
    c = '';
    graphics2D.drawString(this.mission.getVehicle(), c, b);
    if (this.note.length() != 0) {
      b += 16;
      c = '\024';
      graphics2D.drawString(ATPMessages.getString("MW.LA.ACCIDENT.TYPE"), c, b);
      c = '';
      graphics2D.drawString(this.note, c, b);
    } 
    graphics2D.translate(0, b + 5);
    Component[] arrayOfComponent = getComponents();
    boolean bool = _$26238(arrayOfComponent[0]);
    double d1 = (paramPageFormat.getImageableHeight() - b) / (this.adaptee.bodyHeight + 45);
    double d2 = (paramPageFormat.getImageableWidth() - 80.0D) / this.adaptee.scroll.getViewport().getWidth();
    double d3 = (d2 < d1) ? d2 : d1;
    graphics2D.scale(d3, d3);
    arrayOfComponent[0].paint(graphics2D);
    _$26244(arrayOfComponent[0], bool);
    graphics2D.translate(arrayOfComponent[0].getWidth(), 0);
    if (this.adaptee.printType == 1) {
      bool = _$26238(((JScrollPane)arrayOfComponent[1]).getComponent(0));
      ((JScrollPane)arrayOfComponent[1]).getComponent(0).paint(graphics2D);
      _$26244(((JScrollPane)arrayOfComponent[1]).getComponent(0), bool);
    } else {
      bool = _$26238(arrayOfComponent[1]);
      arrayOfComponent[1].paint(graphics2D);
      _$26244(arrayOfComponent[1], bool);
    } 
    return 0;
  }
  
  private void _$26244(Component paramComponent, boolean paramBoolean) {
    if (paramComponent instanceof JComponent)
      ((JComponent)paramComponent).setDoubleBuffered(paramBoolean); 
  }
  
  public void setMessage(ATPMissionDetail paramATPMissionDetail, String paramString, pnlDriverBehavior parampnlDriverBehavior) {
    this.mission = paramATPMissionDetail;
    this.note = paramString;
    this.adaptee = parampnlDriverBehavior;
    this._$2585 = this.mission.getMissionStartTime().getTime() / 60000L * 60000L - 60000L + 1000L;
    this._$9961 = this.mission.getMissionEndTime().getTime() - this.mission.getMissionStartTime().getTime();
  }
}
