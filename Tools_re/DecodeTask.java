package Tools;

import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Date;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class DecodeTask {
  private int _$1638;
  
  private DefaultSortTableModel _$16924 = new DefaultSortTableModel();
  
  private boolean _$1702 = false;
  
  private boolean _$1701 = false;
  
  private int _$1637;
  
  private JPanel _$2040;
  
  private String _$2038;
  
  private String _$2032;
  
  private JScrollPane _$1679 = new JScrollPane((Component)this._$28285);
  
  private JSortTable _$28285 = new JSortTable((SortTableModel)this._$16924);
  
  private Vector _$18031;
  
  private Timer _$28284;
  
  public DecodeTask(Vector paramVector, String paramString) {
    this._$18031 = paramVector;
    this._$2032 = paramString;
    this._$1637 = this._$18031.size();
    _$28288();
    this._$2040 = new JPanel(new BorderLayout());
    this._$2040.setSize(480, 230);
    this._$2040.add(this._$1679, "Center");
  }
  
  private void _$28290() {
    TableColumn tableColumn = this._$28285.getColumnModel().getColumn(0);
    tableColumn.setMaxWidth(40);
    tableColumn.setMinWidth(40);
    tableColumn = this._$28285.getColumnModel().getColumn(1);
    tableColumn.setCellRenderer((TableCellRenderer)new Object(this));
    tableColumn.setMaxWidth(70);
    tableColumn.setMinWidth(70);
    tableColumn = this._$28285.getColumnModel().getColumn(2);
    tableColumn.setMaxWidth(70);
    tableColumn.setMinWidth(70);
    tableColumn = this._$28285.getColumnModel().getColumn(3);
    tableColumn.setMaxWidth(70);
    tableColumn.setMinWidth(70);
    tableColumn = this._$28285.getColumnModel().getColumn(4);
    tableColumn.setMaxWidth(70);
    tableColumn.setMinWidth(70);
    tableColumn = this._$28285.getColumnModel().getColumn(5);
    tableColumn.setMaxWidth(75);
    tableColumn.setMinWidth(75);
    tableColumn = this._$28285.getColumnModel().getColumn(6);
    tableColumn.setMinWidth(75);
  }
  
  public int getCurrent() {
    return this._$1638;
  }
  
  public int getLengthOfTask() {
    return this._$1637;
  }
  
  public Object getMessage() {
    return this._$2040;
  }
  
  public String getNote() {
    return this._$2038;
  }
  
  public void go() {
    Object object = new Object(this);
    object.start();
  }
  
  private void _$28288() {
    Vector vector = new Vector();
    vector.add("No");
    vector.add("進度");
    vector.add("日期");
    vector.add("工作班");
    vector.add("車次");
    vector.add("司機員代號");
    vector.add("動力車號碼");
    Vector vector1 = this._$18031;
    for (byte b = 0; b < vector1.size(); b++) {
      Vector vector2 = vector1.get(b);
      vector2.insertElementAt("" + (b + 1), 0);
      vector2.insertElementAt(new Integer(0), 1);
    } 
    this._$16924.setDataVector(vector1, vector);
    _$28290();
  }
  
  public static void main(String[] paramArrayOfString) {
    Vector vector1 = new Vector();
    vector1.add(new Date(2005, 1, 31));
    vector1.add("235");
    vector1.add("2233");
    vector1.add("33456");
    vector1.add("E1023");
    Vector vector2 = new Vector();
    vector2.add(new Date(2005, 2, 1));
    vector2.add("237");
    vector2.add("1003");
    vector2.add("138456");
    vector2.add("M1012");
    Vector vector = new Vector();
    vector.add(vector1);
    vector.add(vector2);
    com.MiTAC.TRA.ATP.Tools.DecodeTask decodeTask = new com.MiTAC.TRA.ATP.Tools.DecodeTask(vector, "C:");
    decodeTask.go();
    JFrame jFrame = new JFrame();
    jFrame.getContentPane().add((JPanel)decodeTask.getMessage());
    jFrame.setSize(((JPanel)decodeTask.getMessage()).size());
    jFrame.setTitle("DecodeTask Test");
    jFrame.show();
  }
}
