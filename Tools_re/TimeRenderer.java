package Tools;

import com.MiTAC.TRA.ATP.Tools.WorkDatetimeTransfer;
import javax.swing.table.DefaultTableCellRenderer;

public class TimeRenderer extends DefaultTableCellRenderer {
  WorkDatetimeTransfer formatter;
  
  public void setValue(Object paramObject) {
    if (paramObject == null) {
      setText("");
    } else {
      int i = (new Integer(String.valueOf(paramObject))).intValue();
      this.formatter = new WorkDatetimeTransfer(i);
      setText((paramObject == null) ? "" : this.formatter.getFormattedString());
    } 
  }
}
