package Tools.SortTable;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class MultiLineHeaderRenderer extends JTextArea implements TableCellRenderer {
  private static final long serialVersionUID = -287948619080247624L;
  
  public MultiLineHeaderRenderer() {
    setBackground(new Color(212, 208, 200));
    setLineWrap(true);
    setBorder(BorderFactory.createRaisedBevelBorder());
    setWrapStyleWord(true);
    setRows(2);
  }
  
  public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
    TableColumnModel tableColumnModel = paramJTable.getColumnModel();
    TableColumn tableColumn = tableColumnModel.getColumn(paramJTable.convertColumnIndexToModel(paramInt2));
    setText(String.valueOf(paramObject));
    setSize(tableColumn.getMaxWidth(), 1);
    return this;
  }
  
  public static void main(String[] paramArrayOfString) {}
}
