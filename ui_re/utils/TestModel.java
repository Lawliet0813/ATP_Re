package ui.utils;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;

class TestModel extends DefaultSortTableModel {
  public boolean isCellEditable(int paramInt1, int paramInt2) {
    return getColumnName(paramInt2).equals(ATPMessages.getString("MW.WS.TR_WORKDATE"));
  }
}
