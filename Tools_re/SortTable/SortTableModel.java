package Tools.SortTable;

import javax.swing.table.TableModel;

public interface SortTableModel extends TableModel {
  boolean isSortable(int paramInt);
  
  void sortColumn(int paramInt, boolean paramBoolean);
}
