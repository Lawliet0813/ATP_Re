package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.pnlSearchOfMWOperate_btnClean_actionAdapter;
import com.MiTAC.TRA.ATP.ui.pnlSearchOfMWOperate_btnSearch_actionAdapter;
import com.toedter.calendar.JCalendar;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class pnlSearchOfMWOperate extends JPanel implements PropertyChangeListener {
  static final String all = ATPMessages.getString("MW.SEARCH.ALL");
  
  JButton btnClean = new JButton();
  
  JButton btnSearch = new JButton();
  
  GregorianCalendar cal;
  
  JCalendar calendar = new JCalendar();
  
  JCheckBox cbTime = new JCheckBox();
  
  JComboBox cbxFunction = new JComboBox();
  
  JComboBox cbxUserID = new JComboBox();
  
  ConnectDB conDB = new ConnectDB();
  
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
  
  JLabel lblFunction = new JLabel();
  
  JLabel lblUserID = new JLabel();
  
  Vector queryItem = new Vector();
  
  public pnlSearchOfMWOperate() {
    init();
    initComboBox();
  }
  
  void btnClean_actionPerformed(ActionEvent paramActionEvent) {
    clear();
    _$15551();
    firePropertyChange("search", 0, 1);
  }
  
  void btnSearch_actionPerformed(ActionEvent paramActionEvent) {
    _$15551();
    firePropertyChange("search", 0, 1);
  }
  
  public void clear() {
    this.cbTime.setSelected(false);
    this.cbxUserID.setSelectedIndex(0);
    this.cbxFunction.setSelectedIndex(0);
  }
  
  private void _$15551() {
    this.queryItem.clear();
    this.queryItem.add(this.cbTime.isSelected() ? this.calendar.getDate() : null);
    this.queryItem.add(((String)this.cbxUserID.getSelectedItem() == all) ? null : this.cbxUserID.getSelectedItem());
    this.queryItem.add(((String)this.cbxFunction.getSelectedItem() == all) ? null : this.cbxFunction.getSelectedItem());
  }
  
  public String getSearchString() {
    StringBuffer stringBuffer = new StringBuffer(" WHERE ");
    if (this.queryItem.get(0) != null) {
      this.cal = new GregorianCalendar();
      this.cal.setTime(this.queryItem.get(0));
      this.cal.add(5, 1);
      stringBuffer.append(" UseTime between '");
      stringBuffer.append(this.formatter.format(this.queryItem.get(0)));
      stringBuffer.append("' and '");
      stringBuffer.append(this.formatter.format(this.cal.getTime()));
      stringBuffer.append("'");
    } else {
      stringBuffer.append(" UserID not like ''");
    } 
    if (this.queryItem.get(1) != null) {
      stringBuffer.append(" AND");
      stringBuffer.append(" UserID = '" + this.queryItem.get(1) + "'");
    } 
    if (this.queryItem.get(2) != null) {
      stringBuffer.append(" AND");
      stringBuffer.append(" FrameName = '" + this.queryItem.get(2) + "'");
    } 
    return stringBuffer.toString();
  }
  
  public void init() {
    setLayout(new GridBagLayout());
    this.btnClean.setText(ATPMessages.getString("MW.SEARCH.CLEAR"));
    this.btnSearch.setText(ATPMessages.getString("MW.SEARCH.SEARCH"));
    this.cbxUserID.addItem(all);
    this.cbxFunction.addItem(all);
    this.lblUserID.setText(ATPMessages.getString("MW.LOGIN.USERID"));
    this.lblFunction.setText(ATPMessages.getString("MW.SLOG.FUNCTION_NAME"));
    this.cbTime.setText("依日期搜尋");
    this.btnSearch.addActionListener((ActionListener)new pnlSearchOfMWOperate_btnSearch_actionAdapter(this));
    this.btnClean.addActionListener((ActionListener)new pnlSearchOfMWOperate_btnClean_actionAdapter(this));
    add(this.cbTime, new GridBagConstraints(0, 0, 3, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add((Component)this.calendar, new GridBagConstraints(0, 1, 3, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add(this.lblUserID, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add(this.cbxUserID, new GridBagConstraints(1, 2, 2, 1, 1.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
    add(this.lblFunction, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add(this.cbxFunction, new GridBagConstraints(1, 3, 2, 1, 1.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
    add(this.btnClean, new GridBagConstraints(1, 6, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add(this.btnSearch, new GridBagConstraints(2, 6, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
  }
  
  public void initComboBox() {
    try {
      Vector vector = new Vector();
      vector = this.conDB.getData("SELECT DISTINCT UserID FROM MWSystemLog");
      for (byte b1 = 0; b1 < vector.size(); b1++)
        this.cbxUserID.addItem(((Vector)vector.get(b1)).get(0)); 
      vector = new Vector();
      vector = this.conDB.getData("SELECT DISTINCT FrameName FROM MWSystemLog");
      for (byte b2 = 0; b2 < vector.size(); b2++)
        this.cbxFunction.addItem(((Vector)vector.get(b2)).get(0)); 
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      InitParameters.start();
      JFrame jFrame = new JFrame();
      jFrame.setContentPane(new com.MiTAC.TRA.ATP.ui.pnlSearchOfMWOperate());
      jFrame.setSize(350, 500);
      jFrame.setVisible(true);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
}
