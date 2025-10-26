package Tools.SortTable;

import com.MiTAC.TRA.ATP.Tools.SortTable.ColumnComparator;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class DefaultSortTableModel extends DefaultTableModel implements SortTableModel {
  private boolean _$13017;
  
  public DefaultSortTableModel(Vector paramVector1, Vector paramVector2) {
    super(paramVector1, paramVector2);
  }
  
  public DefaultSortTableModel(Vector paramVector, int paramInt) {
    super(paramVector, paramInt);
  }
  
  public DefaultSortTableModel(Object[] paramArrayOfObject, int paramInt) {
    super(paramArrayOfObject, paramInt);
  }
  
  public DefaultSortTableModel(Object[][] paramArrayOfObject, Object[] paramArrayOfObject1) {
    super(paramArrayOfObject, paramArrayOfObject1);
  }
  
  public DefaultSortTableModel(int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
  }
  
  public DefaultSortTableModel() {}
  
  public Class getColumnClass(int paramInt) {
    Object object = getValueAt(0, paramInt);
    return (object == null) ? Object.class : object.getClass();
  }
  
  public boolean isCellEditable(int paramInt1, int paramInt2) {
    return false;
  }
  
  public boolean isSortable(int paramInt) {
    return true;
  }
  
  public void setCellEditable(boolean paramBoolean) {
    this._$13017 = paramBoolean;
  }
  
  public void sortColumn(int paramInt, boolean paramBoolean) {
    Collections.sort(getDataVector(), (Comparator)new ColumnComparator(paramInt, paramBoolean));
  }
}
