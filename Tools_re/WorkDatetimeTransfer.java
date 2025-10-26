package Tools;

import com.MiTAC.TRA.ATP.Tools.StringHandler;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WorkDatetimeTransfer {
  private int _$15160;
  
  private int _$15163;
  
  private int _$15161;
  
  private int _$15162;
  
  public WorkDatetimeTransfer(int paramInt) {
    setTime(paramInt);
  }
  
  public WorkDatetimeTransfer(int paramInt1, int paramInt2, int paramInt3) {
    setTime(paramInt1, paramInt2, paramInt3);
  }
  
  public WorkDatetimeTransfer() {}
  
  public long getDateTimeLong() {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.add(10, 0 - calendar.getTime().getHours());
    calendar.add(12, 0 - calendar.getTime().getMinutes());
    calendar.add(13, 0 - calendar.getTime().getSeconds());
    calendar.add(13, getTime());
    return calendar.getTimeInMillis();
  }
  
  public String getDateTimeString() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.add(10, 0 - calendar.getTime().getHours());
    calendar.add(12, 0 - calendar.getTime().getMinutes());
    calendar.add(13, 0 - calendar.getTime().getSeconds());
    calendar.add(13, getTime());
    return simpleDateFormat.format(calendar.getTime()).toString();
  }
  
  public int getDay() {
    return this._$15160;
  }
  
  public String getFormattedString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(getDay());
    stringBuffer.append("-");
    stringBuffer.append(StringHandler.fillLeft("" + getHour(), "0", 2));
    stringBuffer.append(":");
    stringBuffer.append(StringHandler.fillLeft("" + getMinutes(), "0", 2));
    return stringBuffer.toString();
  }
  
  public int getHour() {
    return this._$15161;
  }
  
  public int getMinutes() {
    return this._$15162;
  }
  
  public int getTime() {
    return this._$15163;
  }
  
  public String getTimeString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(StringHandler.fillLeft("" + getHour(), "0", 2));
    stringBuffer.append(":");
    stringBuffer.append(StringHandler.fillLeft("" + getMinutes(), "0", 2));
    return stringBuffer.toString();
  }
  
  public static void main(String[] paramArrayOfString) {
    com.MiTAC.TRA.ATP.Tools.WorkDatetimeTransfer workDatetimeTransfer1 = new com.MiTAC.TRA.ATP.Tools.WorkDatetimeTransfer(1, 22, 33);
    Calendar calendar1 = Calendar.getInstance();
    calendar1.add(10, 0 - calendar1.getTime().getHours());
    calendar1.add(12, 0 - calendar1.getTime().getMinutes());
    calendar1.add(13, 0 - calendar1.getTime().getSeconds());
    System.err.println("b'>')~=====  WorkDatetimeTransfer  =====");
    System.err.println("b'>')~");
    System.err.println("b'>')~Today : " + calendar1.getTime());
    System.err.println("b'>')~");
    System.err.println("b'>')~Test parameters:");
    System.err.println("b'>')~   set Day = 1");
    System.err.println("b'>')~   set Hour = 22");
    System.err.println("b'>')~   set Minute = 33");
    System.err.println("b'>')~   Result:");
    System.err.println("b'>')~     to seconds:");
    System.err.println("b'>')~       Day : " + workDatetimeTransfer1.getDay());
    System.err.println("b'>')~       Hour : " + workDatetimeTransfer1.getHour());
    System.err.println("b'>')~       Minute : " + workDatetimeTransfer1.getMinutes());
    System.err.println("b'>')~       total seconds : " + workDatetimeTransfer1.getTime());
    Calendar calendar2 = (Calendar)calendar1.clone();
    calendar2.add(13, workDatetimeTransfer1.getTime());
    System.err.println("b'>')~         => Work time : " + calendar2.getTime());
    com.MiTAC.TRA.ATP.Tools.WorkDatetimeTransfer workDatetimeTransfer2 = new com.MiTAC.TRA.ATP.Tools.WorkDatetimeTransfer(workDatetimeTransfer1.getTime());
    System.err.println("b'>')~     to part:");
    System.err.println("b'>')~       Day : " + workDatetimeTransfer2.getDay());
    System.err.println("b'>')~       Hour : " + workDatetimeTransfer2.getHour());
    System.err.println("b'>')~       Minute : " + workDatetimeTransfer2.getMinutes());
    System.err.println("b'>')~       total seconds : " + workDatetimeTransfer2.getTime());
    calendar2 = (Calendar)calendar1.clone();
    calendar2.add(13, workDatetimeTransfer2.getTime());
    System.err.println("b'>')~         => Work time : " + calendar2.getTime());
  }
  
  public void setTime(int paramInt) {
    this._$15163 = paramInt;
    transferToFormate();
  }
  
  public void setTime(int paramInt1, int paramInt2, int paramInt3) {
    this._$15160 = paramInt1;
    this._$15161 = paramInt2;
    this._$15162 = paramInt3;
    this._$15163 = transferToSeconds();
  }
  
  protected void transferToFormate() {
    this._$15160 = this._$15163 / 86400;
    int i = this._$15163 % 86400;
    this._$15161 = i / 3600;
    i %= 3600;
    this._$15162 = i / 60;
  }
  
  protected int transferToSeconds() {
    int i = 0;
    i += this._$15162 * 60;
    i += this._$15161 * 60 * 60;
    i += this._$15160 * 24 * 60 * 60;
    return i;
  }
}
