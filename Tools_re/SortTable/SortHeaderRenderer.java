package Tools.SortTable;

import com.MiTAC.TRA.ATP.Tools.SortTable.JSortTable;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortArrowIcon;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class SortHeaderRenderer extends DefaultTableCellRenderer {
  public static final Icon ASCENDING;
  
  public static final Icon DECENDING;
  
  public static final Icon NONSORTED = (Icon)new SortArrowIcon(0);
  
  static {
    ASCENDING = (Icon)new SortArrowIcon(2);
    DECENDING = (Icon)new SortArrowIcon(1);
  }
  
  public SortHeaderRenderer() {
    setHorizontalTextPosition(2);
    setHorizontalAlignment(0);
  }
  
  public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
    int i = -1;
    boolean bool = true;
    if (paramJTable instanceof JSortTable) {
      JSortTable jSortTable = (JSortTable)paramJTable;
      i = jSortTable.getSortedColumnIndex();
      bool = jSortTable.isSortedColumnAscending();
    } 
    if (paramJTable != null) {
      JTableHeader jTableHeader = paramJTable.getTableHeader();
      if (jTableHeader != null) {
        setForeground(jTableHeader.getForeground());
        setBackground(jTableHeader.getBackground());
        setFont(jTableHeader.getFont());
      } 
    } 
    Icon icon = bool ? ASCENDING : DECENDING;
    setIcon((paramInt2 == i) ? icon : NONSORTED);
    setText((paramObject == null) ? "" : paramObject.toString());
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    return this;
  }
}
