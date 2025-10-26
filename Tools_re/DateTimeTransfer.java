package Tools;

public class DateTimeTransfer {
  private String _$28263;
  
  private long _$28262;
  
  private String _$1578;
  
  private String _$4436;
  
  public String getDateTime(long paramLong) {
    this._$28263 = String.valueOf(paramLong / 1440L) + "-" + String.valueOf(paramLong % 1440L / 60L) + ":" + String.valueOf(paramLong % 1440L % 60L);
    return this._$28263;
  }
  
  public long getSecond(String paramString) {
    this._$4436 = paramString;
    String[] arrayOfString = new String[3];
    arrayOfString[0] = this._$4436.substring(0, 1);
    arrayOfString[1] = this._$4436.substring(2, 4);
    arrayOfString[2] = this._$4436.substring(5, this._$4436.length());
    this._$28262 = (1440 * Integer.parseInt(arrayOfString[0]) + 60 * Integer.parseInt(arrayOfString[1]) + Integer.parseInt(arrayOfString[2]));
    return this._$28262;
  }
  
  public void setDateTime(String paramString) {
    this._$1578 = paramString;
  }
  
  public void setTime(String paramString) {
    this._$4436 = paramString;
  }
}
