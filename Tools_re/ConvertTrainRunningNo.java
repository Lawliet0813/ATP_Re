package Tools;

public class ConvertTrainRunningNo {
  String strTrainRunningNo;
  
  int trainRunningNo;
  
  private String _$28356() {
    int i = 0;
    boolean bool = false;
    null = "";
    null = null + String.valueOf(this.trainRunningNo % 10000);
    i = this.trainRunningNo / 10000;
    char c = (char)i;
    null = null + c;
    return null.replaceAll(" ", "");
  }
  
  private int _$28358() {
    int i = 0;
    int j = 0;
    String str = "";
    for (byte b = 0; b < this.strTrainRunningNo.length(); b++) {
      char c = this.strTrainRunningNo.charAt(b);
      if (Character.isDigit(c)) {
        str = str + String.valueOf(this.strTrainRunningNo.charAt(b));
      } else if (Character.isLetter(c)) {
        j += this.strTrainRunningNo.charAt(b) * 10000;
      } 
      i = j + Integer.parseInt(str);
    } 
    return i;
  }
  
  private void _$28359() {}
  
  public int getTrainRunningNoOfInt(String paramString) {
    this.strTrainRunningNo = paramString;
    return _$28358();
  }
  
  public String getTrainRunningNoOfStr(String paramString) {
    this.trainRunningNo = Integer.parseInt(paramString);
    return _$28356();
  }
  
  public void setTrainRunningNo(int paramInt) {
    this.trainRunningNo = paramInt;
  }
  
  public void setTrainRunningNo(String paramString) {
    this.strTrainRunningNo = paramString;
  }
}
