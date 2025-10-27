package ui.utils;

import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;

class myTableModel extends DefaultSortTableModel {
  public boolean isCellEditable(int paramInt1, int paramInt2) {
    try {
      return (paramInt2 == 5) ? (!getValueAt(paramInt1, paramInt2).toString().equals("1")) : false;
    } catch (NullPointerException nullPointerException) {
      return true;
    } 
  }
}
