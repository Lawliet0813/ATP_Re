package Tools.DiskTools;

import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.ShowTimeNow;
import java.util.Arrays;
import java.util.Date;

public class DiskName {
  private static final String[] _$16710 = new String[] { 
      "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
      "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", 
      "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", 
      "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", 
      "g", "h", "i", "j", "k", "m", "n", "p", "q", "r", 
      "s", "t", "u", "v", "w", "x", "y", "z", "_", "-" };
  
  private static final String _$16709 = "非ATP專案所屬MO光碟片, 欲在本系統使用請先格式化";
  
  public static String NewName() throws Exception {
    null = "";
    System.err.println(InitParameters.MWName);
    null = InitParameters.MWName.substring(0, 5);
    String str1 = _$16714((ShowTimeNow.getYear() - 2000) % 60);
    String str2 = _$16714(ShowTimeNow.getMonth());
    String str3 = _$16714(ShowTimeNow.getDay());
    String str4 = _$16714(ShowTimeNow.getHour());
    String str5 = _$16714(ShowTimeNow.getMinute());
    String str6 = _$16714(ShowTimeNow.getSecond());
    return null + str1 + str2 + str3 + str4 + str5 + str6;
  }
  
  public static boolean check2YearsOld(String paramString) throws Exception {
    try {
      if (!checkDiskName(paramString))
        return true; 
    } catch (Exception exception) {
      return true;
    } 
    int i = (ShowTimeNow.getYear() - 2000) % 60;
    int j = ShowTimeNow.getMonth();
    int k = ShowTimeNow.getDay();
    int m = _$3759(paramString);
    int n = _$3756(paramString);
    int i1 = _$3753(paramString);
    Date date1 = new Date(System.currentTimeMillis());
    Date date2 = getDate(paramString);
    return (i - m < 2) ? false : ((i - m == 2) ? ((j < n) ? false : ((j == n) ? (!(k < i1)) : true)) : true);
  }
  
  public static boolean checkDiskName(String paramString) throws Exception {
    if (paramString.length() < 11)
      return false; 
    try {
      getDate(paramString);
      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } 
  }
  
  private static String _$16714(String paramString) throws Exception {
    int i = (new Integer(paramString)).intValue();
    return _$16714(i);
  }
  
  private static String _$16714(int paramInt) throws Exception {
    return _$16710[paramInt];
  }
  
  public static Date getDate(String paramString) throws Exception {
    int i = _$3759(paramString);
    int j = _$3756(paramString);
    int k = _$3753(paramString);
    int m = _$4435(paramString);
    int n = _$4437(paramString);
    int i1 = _$4437(paramString);
    return new Date(100 + i, j, k, m, n, i1);
  }
  
  private static int _$3753(String paramString) throws Exception {
    String str = paramString.substring(7, 8);
    int i = Arrays.binarySearch((Object[])_$16710, str);
    if (i == -1 || i > 31)
      throw new Exception("非ATP專案所屬MO光碟片, 欲在本系統使用請先格式化"); 
    return i;
  }
  
  private static int _$4435(String paramString) throws Exception {
    String str = paramString.substring(8, 9);
    int i = Arrays.binarySearch((Object[])_$16710, str);
    if (i == -1 || i > 24)
      throw new Exception("非ATP專案所屬MO光碟片, 欲在本系統使用請先格式化"); 
    return i;
  }
  
  private static int _$4437(String paramString) throws Exception {
    String str = paramString.substring(9, 10);
    int i = Arrays.binarySearch((Object[])_$16710, str);
    if (i == -1 || i > 60)
      throw new Exception("非ATP專案所屬MO光碟片, 欲在本系統使用請先格式化"); 
    return i;
  }
  
  private static int _$3756(String paramString) throws Exception {
    String str = paramString.substring(6, 7);
    int i = Arrays.binarySearch((Object[])_$16710, str);
    if (i == -1 || i > 12)
      throw new Exception("非ATP專案所屬MO光碟片, 欲在本系統使用請先格式化"); 
    return i;
  }
  
  private static int _$4438(String paramString) throws Exception {
    String str = paramString.substring(10, 11);
    int i = Arrays.binarySearch((Object[])_$16710, str);
    if (i == -1 || i > 60)
      throw new Exception("非ATP專案所屬MO光碟片, 欲在本系統使用請先格式化"); 
    return i;
  }
  
  private static int _$3759(String paramString) throws Exception {
    String str = paramString.substring(5, 6);
    int i = Arrays.binarySearch((Object[])_$16710, str);
    if (i == -1)
      throw new Exception("非ATP專案所屬MO光碟片, 欲在本系統使用請先格式化"); 
    return i;
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      String str = NewName();
      System.err.println("Generating a new label: " + str);
      System.err.println("Decode Label creation time: " + getDate(str));
      System.err.println("  is this label older then 2 year's? " + check2YearsOld(str));
      System.err.println();
      System.err.println("Testing an old Disk Label:  \"MW001233G4b\"");
      System.err.println("Decode Label creation time: " + getDate("MW001233G4b"));
      System.err.println("  is this label older then 2 year's? " + check2YearsOld("MW001233G4b"));
      System.err.println();
      System.err.println("Testing an unlegal label: \"mw001------\"");
      System.err.println(checkDiskName("mw001------"));
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
