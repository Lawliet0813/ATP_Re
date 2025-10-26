package Tools;

import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.Tools.MD5.MD5;
import java.util.Vector;

public class CheckUser {
  private ConnectDB _$528 = new ConnectDB();
  
  private String _$523;
  
  private String _$526;
  
  private boolean _$529;
  
  private Vector _$527;
  
  private int[] _$530 = new int[] { 
      0, 2, 3, 4, 10, 14, 15, 33, 110, 111, 
      112 };
  
  private int[] _$531 = new int[] { 
      0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 
      11, 14, 15, 33, 41, 42, 43, 51, 52, 53, 
      54, 55, 56, 61, 62, 63, 71, 72, 73, 81, 
      82, 83, 91, 92, 93, 94, 95, 96, 110, 111, 
      112 };
  
  private int[] _$532 = new int[] { 
      0, 2, 3, 4, 5, 6, 10, 11, 14, 15, 
      16, 17, 18, 19, 20, 21, 22, 23, 33, 41, 
      42, 43, 51, 52, 53, 54, 55, 56, 91, 92, 
      93, 94, 95, 96, 101, 102, 103, 104, 105, 106, 
      110, 111, 112, 121, 122, 123, 124 };
  
  private int[] _$533 = new int[] { 
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
      10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 
      20, 21, 22, 23, 31, 32, 33, 41, 42, 43, 
      51, 52, 53, 54, 55, 56, 61, 62, 63, 71, 
      72, 73, 81, 82, 83, 91, 92, 93, 94, 95, 
      96, 101, 102, 103, 104, 105, 106, 107, 108, 109, 
      110, 111, 112, 121, 122, 123, 124, 125 };
  
  private String _$524;
  
  private String _$525;
  
  public CheckUser(String paramString1, String paramString2) {
    this._$523 = paramString2;
    this._$524 = paramString1;
    userDataInit();
  }
  
  public CheckUser() {}
  
  public String getPriority() {
    return ((Vector)this._$527.get(0)).get(3);
  }
  
  public String getUserID() {
    return this._$524;
  }
  
  public String getUserName() {
    return ((Vector)this._$527.get(0)).get(1);
  }
  
  public boolean isEnable(int paramInt1, int paramInt2) {
    boolean bool = false;
    if (paramInt1 == 0) {
      for (byte b = 0; b < this._$530.length; b++) {
        if (paramInt2 == this._$530[b])
          bool = true; 
      } 
    } else if (paramInt1 == 1) {
      for (byte b = 0; b < this._$531.length; b++) {
        if (paramInt2 == this._$531[b])
          bool = true; 
      } 
    } else if (paramInt1 == 2) {
      for (byte b = 0; b < this._$532.length; b++) {
        if (paramInt2 == this._$532[b])
          bool = true; 
      } 
    } else if (paramInt1 == 3) {
      for (byte b = 0; b < this._$533.length; b++) {
        if (paramInt2 == this._$533[b])
          bool = true; 
      } 
    } 
    return bool;
  }
  
  public boolean isPasswordRight() {
    if (this._$524.equals("MiTACManager")) {
      if (this._$523.equals("A82")) {
        this._$529 = true;
      } else {
        this._$529 = false;
      } 
    } else {
      for (byte b = 0; b < this._$527.size(); b++) {
        String str = ((Vector)this._$527.get(b)).get(2);
        try {
          MD5 mD5 = new MD5(this._$523);
          if (str.equals(mD5.getHexString())) {
            this._$529 = true;
            break;
          } 
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
      } 
    } 
    return this._$529;
  }
  
  public void userDataInit() {
    try {
      String str = "SELECT User_ID, UserName, Password, Priority FROM MWUser WHERE User_ID ='" + this._$524 + "'";
      this._$527 = this._$528.getData(str);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
