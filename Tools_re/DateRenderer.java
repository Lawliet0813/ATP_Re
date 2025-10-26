package Tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableCellRenderer;

public class DateRenderer extends DefaultTableCellRenderer {
  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  public void setValue(Object paramObject) {
    if (paramObject == null) {
      setText("");
    } else if (paramObject.getClass().isInstance("String")) {
      setText(paramObject.toString());
    } else {
      setText(this.dateFormat.format(paramObject));
    } 
  }
}
