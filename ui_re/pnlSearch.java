package ui;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.SortTable.ColumnComparator;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.ui.pnlSearch_btn_actionAdapter;
import com.MiTAC.Tools.GridExportToCSV;
import com.toedter.calendar.JCalendar;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.table.TableModel;

public class pnlSearch extends JPanel implements PropertyChangeListener {
  Vector DID;
  
  Vector FUNC;
  
  Vector TRNo;
  
  Vector UID;
  
  Vector VID;
  
  Vector WSNo;
  
  static final String all = ATPMessages.getString("MW.SEARCH.ALL");
  
  JButton btnSave = new JButton();
  
  JButton btnclean = new JButton();
  
  JButton btnsearch = new JButton();
  
  JCalendar cal = new JCalendar();
  
  JComboBox cbxDID = new JComboBox();
  
  JComboBox cbxFunction = new JComboBox();
  
  JComboBox cbxTRNo = new JComboBox();
  
  JComboBox cbxUserID = new JComboBox();
  
  JComboBox cbxVID = new JComboBox();
  
  JComboBox cbxWSNo = new JComboBox();
  
  JCheckBox chxSearchDate = new JCheckBox();
  
  Vector data;
  
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
  
  JLabel lblDID = new JLabel();
  
  JLabel lblFunction = new JLabel();
  
  JLabel lblTRNO = new JLabel();
  
  JLabel lblUserID = new JLabel();
  
  JLabel lblVID = new JLabel();
  
  JLabel lblWSNO = new JLabel();
  
  Vector queryItem = new Vector();
  
  Vector runningDate;
  
  public static final int showDID = 4;
  
  public static final int showDate = 1;
  
  public static final int showFUNC = 7;
  
  public static final int showTRNo = 3;
  
  public static final int showUID = 6;
  
  public static final int showVID = 5;
  
  public static final int showWSNo = 2;
  
  public pnlSearch(Vector paramVector, boolean paramBoolean) {
    this(paramVector);
    this.chxSearchDate.setVisible(paramBoolean);
  }
  
  public pnlSearch(boolean paramBoolean) {
    this();
    try {
      if (paramBoolean) {
        this.WSNo = new Vector();
        this.TRNo = new Vector();
        this.DID = new Vector();
        this.VID = new Vector();
        this.UID = new Vector();
        this.FUNC = new Vector();
        ConnectDB connectDB = new ConnectDB();
        Vector vector = connectDB.getData("SELECT DISTINCT(WSNo) FROM WorkShift");
        for (byte b1 = 0; b1 < vector.size(); b1++)
          this.WSNo.add(((Vector)vector.get(b1)).get(0)); 
        vector = connectDB.getData("SELECT DISTINCT(TRNo) FROM TrainRunning");
        for (byte b2 = 0; b2 < vector.size(); b2++)
          this.TRNo.add(((Vector)vector.get(b2)).get(0)); 
        vector = connectDB.getData("SELECT DISTINCT(Driver_ID) FROM Driver_Info");
        for (byte b3 = 0; b3 < vector.size(); b3++)
          this.DID.add(((Vector)vector.get(b3)).get(0)); 
        vector = connectDB.getData("SELECT DISTINCT(VehicleID) FROM VehicleID");
        for (byte b4 = 0; b4 < vector.size(); b4++)
          this.VID.add(((Vector)vector.get(b4)).get(0)); 
        vector = connectDB.getData("SELECT DISTINCT UserID FROM MWSystemLog");
        for (byte b5 = 0; b5 < vector.size(); b5++)
          this.UID.add(((Vector)vector.get(b5)).get(0)); 
        vector = connectDB.getData("SELECT DISTINCT FrameName FROM MWSystemLog");
        for (byte b6 = 0; b6 < vector.size(); b6++)
          this.FUNC.add(((Vector)vector.get(b6)).get(0)); 
        _$15532();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public pnlSearch(Vector paramVector) {
    this();
    setData(paramVector);
  }
  
  public pnlSearch() {
    try {
      init();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public Vector SearchData(Vector paramVector) {
    Vector vector = new Vector();
    Collections.sort(paramVector, (Comparator)new ColumnComparator(0, true));
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    Vector vector1 = new Vector();
    vector1.add(this.queryItem.get(0));
    vector1.add(this.queryItem.get(1));
    vector1.add(this.queryItem.get(2));
    vector1.add(this.queryItem.get(3));
    vector1.add(this.queryItem.get(4));
    if (vector1.get(0) != null && vector1.get(0).getClass() != String.class) {
      String str = simpleDateFormat.format((Date)vector1.get(0));
      vector1.remove(0);
      vector1.insertElementAt((E)str, 0);
    } 
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector2 = new Vector();
      Vector vector3 = (Vector)((Vector)paramVector.get(b)).clone();
      String str = simpleDateFormat.format(vector3.get(0));
      vector3.remove(0);
      vector3.insertElementAt(str, 0);
      for (byte b1 = 0; b1 < vector1.size(); b1++) {
        if (vector1.get(b1) == null) {
          vector2.add(vector3.get(b1));
        } else {
          vector2.add(vector1.get(b1));
        } 
      } 
      if (vector3.equals(vector2)) {
        vector.add(paramVector.get(b));
      } else {
        vector2.clear();
        for (byte b2 = 0; b2 < vector1.size(); b2++) {
          if (vector1.get(b2) == null) {
            vector2.add(vector3.get(b2));
          } else if (b2 == 1) {
            String str1;
            for (str1 = (String)vector1.get(b2); str1.length() < 8; str1 = "0" + str1);
            vector2.add(str1);
          } else if (b2 == 2) {
            String str1;
            for (str1 = (String)vector1.get(b2); str1.length() < 6; str1 = "0" + str1);
            vector2.add(str1);
          } else {
            vector2.add((String)vector1.get(b2));
          } 
        } 
        if (vector3.equals(vector2))
          vector.add(paramVector.get(b)); 
      } 
    } 
    return vector;
  }
  
  public Vector SearchDataWithCRCcode(Vector paramVector) {
    Vector vector = new Vector();
    Collections.sort(paramVector, (Comparator)new ColumnComparator(0, true));
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    Vector vector1 = new Vector();
    vector1.add(this.queryItem.get(0));
    vector1.add(this.queryItem.get(1));
    vector1.add(this.queryItem.get(2));
    vector1.add(this.queryItem.get(3));
    vector1.add(this.queryItem.get(4));
    if (vector1.get(0) != null && vector1.get(0).getClass() != String.class) {
      String str = simpleDateFormat.format((Date)vector1.get(0));
      vector1.remove(0);
      vector1.insertElementAt((E)str, 0);
    } 
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector2 = new Vector();
      Vector vector3 = (Vector)((Vector)paramVector.get(b)).clone();
      String str1 = simpleDateFormat.format(vector3.get(0));
      vector3.remove(0);
      vector3.insertElementAt(str1, 0);
      String str2 = vector3.get(4);
      str2 = str2.split("_")[0];
      str2 = str2.replaceAll("-", "");
      vector3.remove(4);
      vector3.insertElementAt(str2, 4);
      for (byte b1 = 0; b1 < vector1.size(); b1++) {
        if (vector1.get(b1) == null) {
          vector2.add(vector3.get(b1));
        } else {
          vector2.add(vector1.get(b1));
        } 
      } 
      if (vector3.equals(vector2)) {
        vector.add(paramVector.get(b));
      } else {
        vector2.clear();
        for (byte b2 = 0; b2 < vector1.size(); b2++) {
          if (vector1.get(b2) == null) {
            vector2.add(vector3.get(b2));
          } else if (b2 == 1) {
            String str;
            for (str = (String)vector1.get(b2); str.length() < 8; str = "0" + str);
            vector2.add(str);
          } else if (b2 == 2) {
            String str;
            for (str = (String)vector1.get(b2); str.length() < 6; str = "0" + str);
            vector2.add(str);
          } else {
            vector2.add((String)vector1.get(b2));
          } 
        } 
        if (vector3.equals(vector2))
          vector.add(paramVector.get(b)); 
      } 
    } 
    return vector;
  }
  
  void btnSave_actionPerformed(ActionEvent paramActionEvent) {
    firePropertyChange("save", 0, 1);
  }
  
  void btnclean_actionPerformed(ActionEvent paramActionEvent) {
    clear();
    _$15551();
    firePropertyChange("search", 0, 1);
  }
  
  void btnsearch_actionPerformed(ActionEvent paramActionEvent) {
    _$15551();
    firePropertyChange("search", 0, 1);
  }
  
  public void clear() {
    this.chxSearchDate.setSelected(false);
    this.cbxWSNo.setSelectedIndex(0);
    this.cbxTRNo.setSelectedIndex(0);
    this.cbxDID.setSelectedIndex(0);
    this.cbxVID.setSelectedIndex(0);
    this.cbxUserID.setSelectedIndex(0);
    this.cbxFunction.setSelectedIndex(0);
  }
  
  private void _$15551() {
    this.queryItem.clear();
    this.queryItem.add(this.chxSearchDate.isSelected() ? this.cal.getDate() : null);
    this.queryItem.add(((String)this.cbxWSNo.getSelectedItem() == all) ? null : this.cbxWSNo.getSelectedItem());
    this.queryItem.add(((String)this.cbxTRNo.getSelectedItem() == all) ? null : this.cbxTRNo.getSelectedItem());
    this.queryItem.add(((String)this.cbxDID.getSelectedItem() == all) ? null : this.cbxDID.getSelectedItem());
    this.queryItem.add(((String)this.cbxVID.getSelectedItem() == all) ? null : this.cbxVID.getSelectedItem());
    this.queryItem.add(((String)this.cbxUserID.getSelectedItem() == all) ? null : this.cbxUserID.getSelectedItem());
    this.queryItem.add(((String)this.cbxFunction.getSelectedItem() == all) ? null : this.cbxFunction.getSelectedItem());
  }
  
  public void doExport(TableModel paramTableModel, String paramString, boolean paramBoolean) {
    GridExportToCSV gridExportToCSV = new GridExportToCSV(paramTableModel);
    String str = "";
    if (paramBoolean) {
      if (this.queryItem.get(0) != null) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        str = str + "Date：" + simpleDateFormat.format(this.queryItem.get(0)) + ",";
      } 
      if (this.queryItem.get(1) != null)
        str = str + "WSNO：" + this.queryItem.get(1) + ","; 
      if (this.queryItem.get(2) != null)
        str = str + "TRNO：" + this.queryItem.get(2) + ","; 
      if (this.queryItem.get(3) != null)
        str = str + "DID：" + this.queryItem.get(3) + ","; 
      if (this.queryItem.get(4) != null)
        str = str + "VID：" + this.queryItem.get(4) + ","; 
      if (this.queryItem.get(5) != null)
        str = str + "UID：" + this.queryItem.get(5) + ","; 
      if (this.queryItem.get(6) != null)
        str = str + "FUNC：" + this.queryItem.get(6) + ","; 
      paramString = paramString + ((str.length() == 0) ? "" : ("(" + str + ")"));
    } 
    gridExportToCSV.setDescription(paramString);
    gridExportToCSV.doExport(getRootPane());
  }
  
  public Vector getSearchList() {
    return this.queryItem;
  }
  
  public String getSearchString() {
    StringBuffer stringBuffer = new StringBuffer(" WHERE");
    if (this.queryItem.get(0) != null) {
      stringBuffer.append(" MissionDate = '");
      stringBuffer.append("" + this.formatter.format(this.queryItem.get(0)));
      stringBuffer.append("'");
    } else {
      stringBuffer.append(" MissionDate not like ''");
    } 
    if (this.queryItem.get(1) != null) {
      stringBuffer.append(" AND");
      stringBuffer.append(" WSNo like '%" + this.queryItem.get(1) + "%'");
    } 
    if (this.queryItem.get(2) != null) {
      stringBuffer.append(" AND");
      stringBuffer.append(" TRNo like '%" + this.queryItem.get(2) + "%'");
    } 
    if (this.queryItem.get(3) != null) {
      stringBuffer.append(" AND");
      stringBuffer.append(" DID like '" + this.queryItem.get(3) + "'");
    } 
    if (this.queryItem.get(4) != null) {
      stringBuffer.append(" AND");
      stringBuffer.append(" VID like '" + this.queryItem.get(4) + "'");
    } 
    if (this.queryItem.get(5) != null) {
      stringBuffer.append(" AND");
      stringBuffer.append(" UserID = '" + this.queryItem.get(5) + "'");
    } 
    if (this.queryItem.get(6) != null) {
      stringBuffer.append(" AND");
      stringBuffer.append(" FrameName = '" + this.queryItem.get(6) + "'");
    } 
    return stringBuffer.toString();
  }
  
  public void hideOption(int paramInt, boolean paramBoolean) {
    switch (paramInt) {
      case 1:
        this.chxSearchDate.setVisible(paramBoolean);
        break;
      case 2:
        this.lblWSNO.setVisible(paramBoolean);
        this.cbxWSNo.setVisible(paramBoolean);
        break;
      case 3:
        this.lblTRNO.setVisible(paramBoolean);
        this.cbxTRNo.setVisible(paramBoolean);
        break;
      case 4:
        this.lblDID.setVisible(paramBoolean);
        this.cbxDID.setVisible(paramBoolean);
        break;
      case 5:
        this.lblVID.setVisible(paramBoolean);
        this.cbxVID.setVisible(paramBoolean);
        break;
      case 6:
        this.lblUserID.setVisible(paramBoolean);
        this.cbxUserID.setVisible(paramBoolean);
        break;
      case 7:
        this.lblFunction.setVisible(paramBoolean);
        this.cbxFunction.setVisible(paramBoolean);
        break;
    } 
  }
  
  void init() throws Exception {
    setLayout(new GridBagLayout());
    this.lblWSNO.setText(ATPMessages.getString("MW.WS.ID"));
    this.lblTRNO.setText(ATPMessages.getString("MW.TR.ID"));
    this.lblDID.setText(ATPMessages.getString("MW.DRIVER.ID"));
    this.lblVID.setText(ATPMessages.getString("MW.VEHICLE.ID"));
    this.lblUserID.setText(ATPMessages.getString("MW.LOGIN.USERID"));
    this.lblFunction.setText(ATPMessages.getString("MW.SLOG.FUNCTION_NAME"));
    this.chxSearchDate.setSelected(false);
    this.cal.setBorder(null);
    this.cal.setDebugGraphicsOptions(0);
    this.cbxWSNo.setEditable(true);
    this.cbxTRNo.setEditable(true);
    this.cbxDID.setEditable(true);
    this.cbxVID.setEditable(true);
    this.cbxUserID.setEditable(true);
    this.cbxFunction.setEditable(true);
    this.queryItem.add(null);
    this.queryItem.add(null);
    this.queryItem.add(null);
    this.queryItem.add(null);
    this.queryItem.add(null);
    this.queryItem.add(null);
    this.queryItem.add(null);
    this.chxSearchDate.setToolTipText("");
    this.chxSearchDate.setText(ATPMessages.getString("MW.SEARCH.SEARCH_DATE"));
    this.btnsearch.setText(ATPMessages.getString("MW.SEARCH.SEARCH"));
    this.btnsearch.addActionListener((ActionListener)new pnlSearch_btn_actionAdapter(this));
    this.btnclean.setText(ATPMessages.getString("MW.SEARCH.CLEAR"));
    this.btnclean.addActionListener((ActionListener)new pnlSearch_btn_actionAdapter(this));
    this.btnSave.setText(ATPMessages.getString("MW.SEARCH.SAVERESULT"));
    this.btnSave.addActionListener((ActionListener)new pnlSearch_btn_actionAdapter(this));
    add(this.chxSearchDate, new GridBagConstraints(0, 0, 4, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add((Component)this.cal, new GridBagConstraints(0, 1, 4, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add(this.lblWSNO, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add(this.cbxWSNo, new GridBagConstraints(1, 2, 3, 1, 1.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
    add(this.lblTRNO, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add(this.cbxTRNo, new GridBagConstraints(1, 3, 3, 1, 1.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
    add(this.lblDID, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add(this.cbxDID, new GridBagConstraints(1, 4, 3, 1, 1.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
    add(this.lblVID, new GridBagConstraints(0, 5, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add(this.cbxVID, new GridBagConstraints(1, 5, 3, 1, 1.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
    add(this.lblUserID, new GridBagConstraints(0, 6, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add(this.cbxUserID, new GridBagConstraints(1, 6, 3, 1, 1.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
    add(this.lblFunction, new GridBagConstraints(0, 7, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add(this.cbxFunction, new GridBagConstraints(1, 7, 3, 1, 1.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
    add(this.btnclean, new GridBagConstraints(1, 8, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add(this.btnsearch, new GridBagConstraints(2, 8, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    add(this.btnSave, new GridBagConstraints(3, 8, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
  }
  
  private void _$15532() {
    this.cbxWSNo.removeAllItems();
    this.cbxTRNo.removeAllItems();
    this.cbxDID.removeAllItems();
    this.cbxVID.removeAllItems();
    this.cbxUserID.removeAllItems();
    this.cbxFunction.removeAllItems();
    this.cbxWSNo.addItem(all);
    this.cbxTRNo.addItem(all);
    this.cbxDID.addItem(all);
    this.cbxVID.addItem(all);
    this.cbxUserID.addItem(all);
    this.cbxFunction.addItem(all);
    for (byte b1 = 0; b1 < this.WSNo.size(); b1++)
      this.cbxWSNo.addItem(this.WSNo.get(b1)); 
    for (byte b2 = 0; b2 < this.TRNo.size(); b2++)
      this.cbxTRNo.addItem(this.TRNo.get(b2)); 
    for (byte b3 = 0; b3 < this.DID.size(); b3++)
      this.cbxDID.addItem(this.DID.get(b3)); 
    for (byte b4 = 0; b4 < this.VID.size(); b4++)
      this.cbxVID.addItem(this.VID.get(b4)); 
    for (byte b5 = 0; b5 < this.UID.size(); b5++)
      this.cbxUserID.addItem(this.UID.get(b5)); 
    for (byte b6 = 0; b6 < this.FUNC.size(); b6++)
      this.cbxFunction.addItem(this.FUNC.get(b6)); 
    this.cbxDID.setRenderer((ListCellRenderer)new Object(this));
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
  
  public void setData(Vector paramVector) {
    this.runningDate = new Vector();
    this.WSNo = new Vector();
    this.TRNo = new Vector();
    this.DID = new Vector();
    this.VID = new Vector();
    this.UID = new Vector();
    this.FUNC = new Vector();
    for (byte b = 0; b < paramVector.size(); b++) {
      String str1 = ((String)((Vector)paramVector.get(b)).get(1)).trim();
      String str2 = ((String)((Vector)paramVector.get(b)).get(2)).trim();
      String str3 = ((String)((Vector)paramVector.get(b)).get(3)).trim();
      String str4 = ((String)((Vector)paramVector.get(b)).get(4)).trim();
      String str5 = ((String)((Vector)paramVector.get(b)).get(5)).trim();
      String str6 = ((String)((Vector)paramVector.get(b)).get(6)).trim();
      if (Arrays.binarySearch(this.WSNo.toArray(), str1) < 0)
        this.WSNo.add(str1); 
      if (Arrays.binarySearch(this.TRNo.toArray(), str2) < 0)
        this.TRNo.add(str2); 
      if (Arrays.binarySearch(this.DID.toArray(), str3) < 0)
        this.DID.add(str3); 
      if (Arrays.binarySearch(this.VID.toArray(), str4) < 0)
        this.VID.add(str4); 
      if (Arrays.binarySearch(this.UID.toArray(), str5) < 0)
        this.UID.add(str5); 
      if (Arrays.binarySearch(this.FUNC.toArray(), str6) < 0)
        this.FUNC.add(str6); 
    } 
  }
}
