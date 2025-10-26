package Tools;

import com.MiTAC.TRA.ATP.Tools.WorkDatetimeTransfer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class WorkDateTransfer extends WorkDatetimeTransfer {
  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  private Date _$17233;
  
  private long _$17234;
  
  public WorkDateTransfer(long paramLong) {
    this._$17234 = paramLong;
  }
  
  public WorkDateTransfer(Date paramDate) {
    this._$17233 = paramDate;
  }
  
  public WorkDateTransfer() {}
  
  public Date getDate() {
    try {
      this._$17233 = this.dateFormat.parse(this.dateFormat.format(new Date(this._$17234)));
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return this._$17233;
  }
  
  public long getWorkDateMillis() {
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(this._$17233);
    return gregorianCalendar.getTimeInMillis() / 1000L;
  }
}
