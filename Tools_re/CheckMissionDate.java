package Tools;

import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

public class CheckMissionDate {
  GregorianCalendar basicCal = new GregorianCalendar();
  
  ConnectDB conDB = new ConnectDB();
  
  String dataNo;
  
  Date date = new Date();
  
  String ownerName;
  
  Vector workData;
  
  public CheckMissionDate(String paramString1, String paramString2) {
    this.dataNo = paramString1;
    this.ownerName = paramString2;
    _$3737(paramString2);
  }
  
  public CheckMissionDate(String paramString, Date paramDate) {
    this.dataNo = paramString;
    this.date = paramDate;
    _$3737();
  }
  
  public CheckMissionDate() {}
  
  public boolean exists() {
    boolean bool = false;
    if (this.ownerName.equals("running")) {
      for (byte b = 0; b < this.workData.size(); b++) {
        if (((Vector)this.workData.get(b)).get(3).equals(this.dataNo)) {
          bool = true;
          break;
        } 
      } 
    } else {
      for (byte b = 0; b < this.workData.size(); b++) {
        if (((Vector)this.workData.get(b)).get(2).equals(this.dataNo)) {
          bool = true;
          break;
        } 
      } 
    } 
    return bool;
  }
  
  private void _$3737(String paramString) {
    try {
      String str = "";
      str = "SELECT DriverID,RunningDate,WSNo,TRNo,WorkDate FROM Mission WHERE WorkDate >= '" + (new SimpleDateFormat("yyyy/MM/dd")).format(this.date) + "'";
      this.workData = this.conDB.getData(str);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$3737() {
    try {
      String str = "";
      str = "SELECT WSNo,WorkDate FROM Mission WHERE WSNo = '" + this.dataNo + "'";
      this.workData = this.conDB.getData(str);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public boolean isDisagree() {
    boolean bool = false;
    for (byte b = 0; b < this.workData.size(); b++) {
      if (this.date.before(((Vector)this.workData.get(b)).get(1))) {
        bool = true;
        break;
      } 
    } 
    return bool;
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      new InitParameters();
      InitParameters.start();
    } catch (Exception exception) {}
  }
}
