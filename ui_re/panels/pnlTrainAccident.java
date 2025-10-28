package ui.panels;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.drawGraphics.DrawContinuous;
import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class pnlTrainAccident extends JPanel {
  bodyPanel Body = new bodyPanel(this);
  
  headerPanel Hd = new headerPanel(this);
  
  BorderLayout borderLayout1 = new BorderLayout();
  
  private Timestamp _$1264;
  
  private Vector _$30050 = new Vector();
  
  private DrawContinuous _$17917;
  
  JButton jButton1 = new JButton();
  
  JLabel jLabel1 = new JLabel();
  
  JLabel jLabel2 = new JLabel();
  
  JLabel jLabel3 = new JLabel();
  
  JLabel jLabel4 = new JLabel();
  
  JLabel jLabel5 = new JLabel();
  
  JPanel jPanel1 = new JPanel();
  
  JLabel lblAccidentType = new JLabel();
  
  JLabel lblDID = new JLabel();
  
  JLabel lblMissionDate = new JLabel();
  
  JLabel lblTRNo = new JLabel();
  
  JLabel lblWSNo = new JLabel();
  
  private commonParaSetting _$17915 = new commonParaSetting();
  
  private drawParameters _$17916 = new drawParameters();
  
  private Vector _$16412 = new Vector();
  
  private Vector _$16834 = new Vector();
  
  private Vector _$4436 = new Vector();
  
  public pnlTrainAccident(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
    try {
      jbInit();
      this.lblAccidentType.setText(paramString1);
      this.lblMissionDate.setText(paramString2);
      this.lblWSNo.setText(paramString3);
      this.lblTRNo.setText(paramString4);
      this.lblDID.setText(paramString5);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$30055(ResultSet paramResultSet) throws Exception {
    this._$16412.clear();
    this._$30050.clear();
    this._$4436.clear();
    this._$16412.addElement(new Long(0L));
    this._$30050.addElement(new Long(0L));
    this._$4436.addElement(new Long(0L));
    try {
      paramResultSet.next();
      do {
        this._$16412.addElement(new Long(paramResultSet.getLong(1)));
        this._$30050.addElement(new Long(paramResultSet.getLong(2) / 1000L));
        this._$1264 = paramResultSet.getTimestamp(3);
        this._$4436.addElement(new Long(this._$1264.getTime()));
      } while (paramResultSet.next());
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
    } 
  }
  
  private void _$30058(ResultSet paramResultSet) throws Exception {
    this._$16834.clear();
    paramResultSet.next();
    do {
      Vector vector = new Vector();
      vector.addElement(new String(paramResultSet.getString(1)));
      vector.addElement(new Float(paramResultSet.getFloat(2)));
      vector.addElement(new String(paramResultSet.getString(3)));
      this._$16834.addElement(vector);
    } while (paramResultSet.next());
  }
  
  void jbInit() throws Exception {
    setLayout(this.borderLayout1);
    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    Connection connection = DriverManager.getConnection("jdbc:odbc:mw", "mmw", "mmw");
    Statement statement1 = connection.createStatement();
    ResultSet resultSet1 = statement1.executeQuery("sp_getDrawBasicInfo");
    _$30055(resultSet1);
    Statement statement2 = connection.createStatement();
    ResultSet resultSet2 = statement2.executeQuery("SELECT stationid,km, stationcname from station where linebelong ='A'");
    _$30058(resultSet2);
    connection.close();
    this.Hd.setPreferredSize(new Dimension(5000, 31));
    this.jLabel1.setText(ATPMessages.getString("MW.MissionDate"));
    this.lblMissionDate.setBorder(BorderFactory.createEtchedBorder());
    this.lblMissionDate.setText("jLabel2");
    this.jLabel2.setText(ATPMessages.getString("MW.WorkShiftNumber"));
    this.lblWSNo.setBorder(BorderFactory.createEtchedBorder());
    this.lblWSNo.setDebugGraphicsOptions(0);
    this.lblWSNo.setText("jLabel3");
    this.jLabel4.setBorder((Border)null);
    this.jLabel4.setOpaque(false);
    this.jLabel4.setText(ATPMessages.getString("MW.TrainRunningNumber"));
    this.lblTRNo.setBorder(BorderFactory.createEtchedBorder());
    this.lblTRNo.setText("jLabel3");
    this.jLabel3.setText(ATPMessages.getString("MW.DriverID"));
    this.lblDID.setBorder(BorderFactory.createEtchedBorder());
    this.lblDID.setText("jLabel5");
    this.jButton1.setText(ATPMessages.getString("MW.EventReport"));
    this.jLabel5.setText(ATPMessages.getString("MW.EventType"));
    this.lblAccidentType.setBorder(BorderFactory.createEtchedBorder());
    this.lblAccidentType.setText("jLabel6");
    add((Component)this.Hd, "West");
    JScrollPane jScrollPane = new JScrollPane((Component)this.Body);
    jScrollPane.setBorder((Border)null);
    add(jScrollPane, "Center");
    add(this.jPanel1, "North");
    this.jPanel1.add(this.jLabel5, (Object)null);
    this.jPanel1.add(this.lblAccidentType, (Object)null);
    this.jPanel1.add(this.jLabel1, (Object)null);
    this.jPanel1.add(this.lblMissionDate, (Object)null);
    this.jPanel1.add(this.jLabel2, (Object)null);
    this.jPanel1.add(this.lblWSNo, (Object)null);
    this.jPanel1.add(this.jLabel4, (Object)null);
    this.jPanel1.add(this.lblTRNo, (Object)null);
    this.jPanel1.add(this.jLabel3, (Object)null);
    this.jPanel1.add(this.lblDID, (Object)null);
    this.jPanel1.add(this.jButton1, (Object)null);
    System.err.println("Start init");
    this._$17915.dpiX = 20.0D;
    this._$17916.message = ATPMessages.getString("MW.Speed");
    this._$17916.MaxNum = 180;
    this._$17916.dpiY = 1.0D;
    this.Body.setPreferredSize(new Dimension(5000, 150));
    System.err.println("Finish init");
  }
}
