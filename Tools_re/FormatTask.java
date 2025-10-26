package Tools;

import com.MiTAC.TRA.ATP.Tools.DiskTools.DiskName;
import com.MiTAC.TRA.ATP.Tools.DiskTools.FormatDisk;
import java.io.File;
import java.util.Vector;
import javax.swing.JOptionPane;

public class FormatTask {
  private boolean _$16641 = false;
  
  private int _$1638 = 0;
  
  private String _$16691;
  
  private boolean _$1639 = false;
  
  private String _$6618;
  
  private int _$1637;
  
  private Vector _$3885;
  
  private String _$16643;
  
  public FormatTask(String paramString) throws Exception {
    this._$16691 = paramString;
    this._$6618 = DiskName.NewName();
    this._$1637 = 1;
  }
  
  public int getCurrent() {
    return this._$1638;
  }
  
  public int getLengthOfTask() {
    return this._$1637;
  }
  
  public String getMessage() {
    return this._$16643;
  }
  
  public String getResultMessage() {
    return "格式化完成\nDisk label: " + this._$6618 + "\n" + "Total disk space: " + (Long)this._$3885.get(0) + " bytes\n" + "Available on disk: " + (Long)this._$3885.get(1) + " bytes\n" + "Each allocation unit: " + (Long)this._$3885.get(2) + " bytes\n" + "Available allocation units: " + (Long)this._$3885.get(3);
  }
  
  public void go() {
    try {
      FormatDisk formatDisk = new FormatDisk(new File(this._$16691), "FAT32", this._$6618);
      this._$3885 = formatDisk.getFormatResult();
      this._$1639 = true;
    } catch (Exception exception) {
      this._$1639 = true;
      exception.printStackTrace();
      JOptionPane.showMessageDialog(null, exception.getMessage(), "錯誤", 0);
    } 
  }
  
  public boolean isDone() {
    return this._$1639;
  }
  
  public void stop() {
    this._$16641 = true;
    this._$16643 = null;
  }
}
