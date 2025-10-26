package core;

import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.util.Arrays;
import java.util.Vector;

public class Station {
  static String[] pchtName;
  
  static Vector pdata;
  
  static String[] pengName;
  
  static int[] pinnerID;
  
  static {
    try {
      ConnectDB connectDB = new ConnectDB();
      pdata = connectDB.getStation();
      pinnerID = new int[pdata.size()];
      pchtName = new String[pdata.size()];
      pengName = new String[pdata.size()];
      for (byte b = 0; b < pdata.size(); b++) {
        Vector vector = pdata.get(b);
        pchtName[b] = vector.get(0).toString();
        pengName[b] = vector.get(1).toString();
        pinnerID[b] = ((Integer)vector.get(2)).intValue();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public static String getStationChtName(int paramInt) {
    String str = "未知";
    int i = Arrays.binarySearch(pinnerID, paramInt);
    return (i < 0) ? str : pchtName[i];
  }
  
  public static String getStationEngName(int paramInt) {
    String str = "unknow";
    int i = Arrays.binarySearch(pinnerID, paramInt);
    return (i < 0) ? str : pengName[i];
  }
}
