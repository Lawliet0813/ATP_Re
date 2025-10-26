package Tools;

import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.util.Vector;

public class CheckUserPassword {
  private ConnectDB _$1181 = new ConnectDB();
  
  private int _$11341;
  
  private String _$10385;
  
  private boolean _$12115;
  
  private Vector _$10207;
  
  private String _$1177;
  
  private String _$12114;
  
  public CheckUserPassword(String paramString1, String paramString2) {
    this._$11341 = Integer.parseInt(paramString2);
    this._$1177 = paramString1;
    userDataInit();
  }
  
  public CheckUserPassword() {
    userDataInit();
  }
  
  public String getPriority() {
    return this._$10385;
  }
  
  public String getUserID() {
    return this._$1177;
  }
  
  public String getUserName() {
    return this._$12114;
  }
  
  public boolean isPasswordRight() {
    for (byte b = 0; b < this._$10207.size(); b++) {
      int i = Integer.parseInt(((Vector)this._$10207.get(b)).get(2));
      if (i == this._$11341) {
        this._$12115 = true;
        break;
      } 
    } 
    return this._$12115;
  }
  
  public void userDataInit() {
    try {
      String str = "SELECT User_ID, UserName, Password, Priority FROM MWUser WHERE User_ID ='" + this._$1177 + "'";
      this._$10207 = this._$1181.getData(str);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
