package Tools.SortTable;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
  private static com.MiTAC.TRA.ATP.Tools.SortTable.MultiLineCellRenderer _$14097 = null;
  
  public static com.MiTAC.TRA.ATP.Tools.SortTable.MultiLineCellRenderer getInstance() {
    if (_$14097 == null)
      _$14097 = new com.MiTAC.TRA.ATP.Tools.SortTable.MultiLineCellRenderer(); 
    return _$14097;
  }
  
  public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
    setLineWrap(true);
    setWrapStyleWord(true);
    setOpaque(true);
    if (paramInt1 % 2 == 1) {
      setBackground(paramJTable.getSelectionForeground());
    } else {
      setBackground(paramJTable.getSelectionForeground());
    } 
    if (paramBoolean1)
      setBackground(paramJTable.getSelectionForeground()); 
    Font font = paramJTable.getFont();
    setFont(new Font(font.getName(), 0, 14));
    setText((paramObject == null) ? "" : paramObject.toString());
    setSize(paramJTable.getColumnModel().getColumn(paramJTable.convertColumnIndexToModel(paramInt2)).getWidth(), 60);
    int i = (int)getPreferredSize().getHeight();
    if (i > paramJTable.getRowHeight(paramInt1))
      paramJTable.setRowHeight(paramInt1, i); 
    return this;
  }
  
  public static void main(String[] paramArrayOfString) {}
}
