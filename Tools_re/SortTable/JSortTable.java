package Tools.SortTable;

import com.MiTAC.TRA.ATP.Tools.SortTable.DefaultSortTableModel;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortHeaderRenderer;
import com.MiTAC.TRA.ATP.Tools.SortTable.SortTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class JSortTable extends JTable implements MouseListener {
  protected boolean sortedColumnAscending = true;
  
  protected int sortedColumnIndex = -1;
  
  public JSortTable(SortTableModel paramSortTableModel, TableColumnModel paramTableColumnModel, ListSelectionModel paramListSelectionModel) {
    super((TableModel)paramSortTableModel, paramTableColumnModel, paramListSelectionModel);
    initSortHeader();
  }
  
  public JSortTable(SortTableModel paramSortTableModel, TableColumnModel paramTableColumnModel) {
    this(paramSortTableModel, paramTableColumnModel, null);
  }
  
  public JSortTable(SortTableModel paramSortTableModel) {
    super((TableModel)paramSortTableModel);
    initSortHeader();
  }
  
  public JSortTable(Vector paramVector1, Vector paramVector2) {
    this((SortTableModel)new DefaultSortTableModel(paramVector1, paramVector2));
  }
  
  public JSortTable(Object[][] paramArrayOfObject, Object[] paramArrayOfObject1) {
    this((SortTableModel)new DefaultSortTableModel(paramArrayOfObject, paramArrayOfObject1));
  }
  
  public JSortTable(int paramInt1, int paramInt2) {
    this((SortTableModel)new DefaultSortTableModel(paramInt1, paramInt2));
  }
  
  public JSortTable() {
    this((SortTableModel)new DefaultSortTableModel());
  }
  
  public int getSortedColumnIndex() {
    return this.sortedColumnIndex;
  }
  
  protected void initSortHeader() {
    JTableHeader jTableHeader = getTableHeader();
    jTableHeader.setDefaultRenderer((TableCellRenderer)new SortHeaderRenderer());
    jTableHeader.addMouseListener(this);
  }
  
  public boolean isSortedColumnAscending() {
    return this.sortedColumnAscending;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mousePressed(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent) {
    TableColumnModel tableColumnModel = getColumnModel();
    int i = tableColumnModel.getColumnIndexAtX(paramMouseEvent.getX());
    int j = tableColumnModel.getColumn(i).getModelIndex();
    SortTableModel sortTableModel = (SortTableModel)getModel();
    if (sortTableModel.isSortable(j)) {
      if (this.sortedColumnIndex == i)
        this.sortedColumnAscending = !this.sortedColumnAscending; 
      this.sortedColumnIndex = i;
      sortTableModel.sortColumn(j, this.sortedColumnAscending);
    } 
  }
}
