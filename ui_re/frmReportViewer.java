package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.FileInputStream;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

public class frmReportViewer extends JFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  
  private TableModel _$4761;
  
  JasperViewer jsver;
  
  private HashMap _$16836;
  
  private int _$22968;
  
  public static final int showCarReport = 2;
  
  public static final int showDriverBehaviorReport = 1;
  
  public static final int showFilterCarReprot = 4;
  
  public static final int showFilterWaysideReport = 3;
  
  public static final int showWaySideReport = 0;
  
  public frmReportViewer(int paramInt, HashMap paramHashMap, TableModel paramTableModel) {
    this(paramInt, paramHashMap);
    try {
      this._$4761 = paramTableModel;
      init();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public frmReportViewer(int paramInt, HashMap paramHashMap) {
    try {
      this._$16836 = paramHashMap;
      this._$22968 = paramInt;
      setSize(600, 600);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void init() throws Exception {
    JasperReport jasperReport;
    JRTableModelDataSource jRTableModelDataSource1;
    JasperPrint jasperPrint1;
    JRViewer jRViewer1;
    JRTableModelDataSource jRTableModelDataSource2;
    JasperPrint jasperPrint2;
    JRViewer jRViewer2;
    JRTableModelDataSource jRTableModelDataSource3;
    JasperPrint jasperPrint3;
    JRViewer jRViewer3;
    JRTableModelDataSource jRTableModelDataSource4;
    JasperPrint jasperPrint4;
    JRViewer jRViewer4;
    JRTableModelDataSource jRTableModelDataSource5;
    JasperPrint jasperPrint5;
    JRViewer jRViewer5;
    getContentPane().setLayout(this.borderLayout1);
    switch (this._$22968) {
      case 1:
        setTitle(ATPMessages.getString("MW.LA.DRIVER_BEHAVIOR.REPORT"));
        System.err.println("Making driver failure report");
        jRTableModelDataSource1 = new JRTableModelDataSource(this._$4761);
        jasperReport = JasperManager.loadReport(new FileInputStream("C:\\ATPMW\\REPORT\\DriverBehavior.jasper"));
        jasperPrint1 = JasperManager.fillReport(jasperReport, this._$16836, (JRDataSource)jRTableModelDataSource1);
        jRViewer1 = new JRViewer(jasperPrint1);
        getContentPane().add((Component)jRViewer1, "Center");
        break;
      case 2:
        setTitle(ATPMessages.getString("MW.LA.CABIN_FAILURE.REPORT"));
        System.err.println("making cabin failure report");
        jRTableModelDataSource2 = new JRTableModelDataSource(this._$4761);
        jasperReport = JasperManager.loadReport(new FileInputStream("C:\\ATPMW\\REPORT\\Car.jasper"));
        jasperPrint2 = JasperManager.fillReport(jasperReport, this._$16836, (JRDataSource)jRTableModelDataSource2);
        jRViewer2 = new JRViewer(jasperPrint2);
        getContentPane().add((Component)jRViewer2, "Center");
        break;
      case 0:
        setTitle(ATPMessages.getString("MW.LA.WAYSIDE_FAILURE.REPORT"));
        System.err.println("Making way side failure report");
        jRTableModelDataSource3 = new JRTableModelDataSource(this._$4761);
        jasperReport = JasperManager.loadReport(new FileInputStream("C:\\ATPMW\\REPORT\\WaySide.jasper"));
        jasperPrint3 = JasperManager.fillReport(jasperReport, this._$16836, (JRDataSource)jRTableModelDataSource3);
        jRViewer3 = new JRViewer(jasperPrint3);
        getContentPane().add((Component)jRViewer3, "Center");
        break;
      case 3:
        setTitle(ATPMessages.getString("MW.LA.WAYSIDE_FAILURE.REPORT"));
        System.err.println("Making way side failure report");
        jRTableModelDataSource4 = new JRTableModelDataSource(this._$4761);
        jasperReport = JasperManager.loadReport(new FileInputStream("C:\\ATPMW\\REPORT\\WaySideFilter.jasper"));
        jasperPrint4 = JasperManager.fillReport(jasperReport, this._$16836, (JRDataSource)jRTableModelDataSource4);
        jRViewer4 = new JRViewer(jasperPrint4);
        getContentPane().add((Component)jRViewer4, "Center");
        break;
      case 4:
        setTitle(ATPMessages.getString("MW.LA.CABIN_FAILURE.REPORT"));
        System.err.println("making cabin failure report");
        jRTableModelDataSource5 = new JRTableModelDataSource(this._$4761);
        jasperReport = JasperManager.loadReport(new FileInputStream("C:\\ATPMW\\REPORT\\Car.jasper"));
        jasperPrint5 = JasperManager.fillReport(jasperReport, this._$16836, (JRDataSource)jRTableModelDataSource5);
        jRViewer5 = new JRViewer(jasperPrint5);
        getContentPane().add((Component)jRViewer5, "Center");
        break;
    } 
  }
}
