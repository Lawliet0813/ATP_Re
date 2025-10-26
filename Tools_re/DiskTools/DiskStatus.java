package Tools.DiskTools;

import com.MiTAC.TRA.ATP.Tools.StringHandler;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

public class DiskStatus {
  private static long _$6683;
  
  private static long _$6685;
  
  private static long _$6684;
  
  private static Date _$6686;
  
  private static BufferedInputStream _$6598;
  
  private static String _$6599;
  
  private static String _$6618 = "";
  
  private static String _$2040;
  
  private static BufferedInputStream _$6597;
  
  static {
    _$6683 = -1L;
    _$6684 = -1L;
    _$6685 = -1L;
  }
  
  public static long getDiskFree() {
    return _$6683;
  }
  
  public static String getDiskLabel() {
    return _$6618;
  }
  
  public static final Vector getDiskStatus(File paramFile) throws IllegalArgumentException, IllegalStateException, IOException, Exception {
    _$6618 = "";
    _$6683 = -1L;
    _$6684 = -1L;
    _$6685 = -1L;
    _$6686 = null;
    if (paramFile == null)
      throw new IllegalArgumentException("file == null"); 
    if (System.getProperty("os.name").startsWith("Windows"))
      return Locale.getDefault().equals(Locale.TAIWAN) ? _$6687(paramFile) : _$6688(paramFile); 
    throw new IllegalStateException("本系統尚不支援此作業系統:" + System.getProperty("os.name"));
  }
  
  public static long getDiskUsed() {
    return _$6684;
  }
  
  private static final Vector _$6687(File paramFile) throws IllegalStateException, IOException, Exception {
    Vector vector = new Vector();
    File file = File.createTempFile("script", ".bat", new File(System.getProperty("java.io.tmpdir")));
    file.deleteOnExit();
    PrintWriter printWriter = new PrintWriter(new FileWriter(file, false));
    printWriter.print("dir " + paramFile.getAbsolutePath());
    printWriter.close();
    Process process = Runtime.getRuntime().exec(file.getAbsolutePath());
    _$6597 = new BufferedInputStream(process.getInputStream());
    _$6598 = new BufferedInputStream(process.getErrorStream());
    String str1 = StringHandler.InputStreamToString(_$6597);
    _$6597.close();
    String str2 = StringHandler.InputStreamToString(_$6598);
    _$6598.close();
    if (str2.length() != 0) {
      if (str2.startsWith("找不到檔案")) {
        vector.add("無標籤");
        vector.add(new Long(0L));
        vector.add(new Long(0L));
        return vector;
      } 
      throw new IOException(str2);
    } 
    if (str1.indexOf("中的磁碟是") == -1) {
      _$6618 = "無標籤";
      _$6686 = null;
    } else {
      int k = str1.indexOf("中的磁碟是") + 6;
      int m = k + 11;
      String str = str1.substring(k, m);
      str = str.replaceAll(",", "");
      str = str.replaceAll(" ", "");
      _$6618 = str;
    } 
    int i = str1.indexOf("位元組可用") - 15;
    int j = i + 15;
    String str3 = str1.substring(i, j);
    str3 = str3.replaceAll(",", "");
    str3 = str3.replaceAll(" ", "");
    _$6683 = Long.parseLong(str3);
    i = str1.indexOf("個檔案") + 3;
    j = i + 16;
    str3 = str1.substring(i, j);
    str3 = str3.replaceAll(",", "");
    str3 = str3.replaceAll(" ", "");
    _$6684 = Long.parseLong(str3);
    vector.add(_$6618);
    vector.add(new Long(_$6683));
    vector.add(new Long(_$6684));
    return vector;
  }
  
  private static final Vector _$6688(File paramFile) throws IllegalStateException, IOException, Exception {
    Vector vector = new Vector();
    File file = File.createTempFile("script", ".bat", new File(System.getProperty("java.io.tmpdir")));
    file.deleteOnExit();
    PrintWriter printWriter = new PrintWriter(new FileWriter(file, false));
    printWriter.print("dir " + paramFile.getAbsolutePath());
    printWriter.close();
    Process process = Runtime.getRuntime().exec(file.getAbsolutePath());
    _$6597 = new BufferedInputStream(process.getInputStream());
    _$6598 = new BufferedInputStream(process.getErrorStream());
    String str1 = StringHandler.InputStreamToString(_$6597);
    _$6597.close();
    String str2 = StringHandler.InputStreamToString(_$6598);
    _$6598.close();
    if (str2.length() != 0) {
      if (str2.startsWith("找不到檔案")) {
        vector.add("No label.");
        vector.add(new Long(0L));
        vector.add(new Long(0L));
        return vector;
      } 
      throw new IOException(str2);
    } 
    if (str1.indexOf("has no label") != -1) {
      _$6618 = "No label.";
      _$6686 = null;
    } else {
      int k = str1.indexOf("is") + 3;
      int m = k + 12;
      String str = str1.substring(k, m);
      str = str.replaceAll(",", "");
      str = str.replaceAll(" ", "");
      _$6618 = str;
    } 
    int i = str1.indexOf("bytes free") - 15;
    int j = i + 15;
    String str3 = str1.substring(i, j);
    str3 = str3.replaceAll(",", "");
    str3 = str3.replaceAll(" ", "");
    _$6683 = Long.parseLong(str3);
    i = str1.indexOf("File(s)") + 7;
    j = i + 16;
    str3 = str1.substring(i, j);
    str3 = str3.replaceAll(",", "");
    str3 = str3.replaceAll(" ", "");
    _$6684 = Long.parseLong(str3);
    vector.add(_$6618);
    vector.add(new Long(_$6683));
    vector.add(new Long(_$6684));
    return vector;
  }
  
  private static final Vector _$6690(File paramFile) throws IllegalStateException, IOException, Exception {
    Vector vector = new Vector();
    File file = File.createTempFile("script", ".bat", new File(System.getProperty("java.io.tmpdir")));
    file.deleteOnExit();
    PrintWriter printWriter = new PrintWriter(new FileWriter(file, false));
    printWriter.print("chkdsk " + paramFile.getAbsolutePath().substring(0, 2));
    printWriter.close();
    Process process = Runtime.getRuntime().exec(file.getAbsolutePath());
    _$6597 = new BufferedInputStream(process.getInputStream());
    _$6598 = new BufferedInputStream(process.getErrorStream());
    String str1 = StringHandler.InputStreamToString(_$6597);
    _$6597.close();
    String str2 = StringHandler.InputStreamToString(_$6598);
    _$6598.close();
    if (str2.length() != 0)
      throw new IOException(str2); 
    if (str1.indexOf("磁碟區建立") == -1) {
      _$6618 = "無標籤";
      _$6686 = null;
    } else {
      int k = str1.indexOf("磁碟區建立") - 12;
      int m = k + 12;
      String str4 = str1.substring(k, m);
      str4 = str4.replaceAll(",", "");
      str4 = str4.replaceAll(" ", "");
      _$6618 = str4;
      k = str1.indexOf("磁碟區建立") + 5;
      m = k + 19;
      str4 = str1.substring(k, m);
      str4 = str4.replaceAll(" ", "");
      String str5 = (str4.indexOf("上午") != -1) ? "上午" : "下午";
      String str6 = str4.substring(0, str4.indexOf(str5));
      String str7 = str4.substring(str4.indexOf(str5) + 2, str4.length());
      String[] arrayOfString = str6.split("/");
      int n = (new Integer(arrayOfString[0])).intValue();
      n -= 1900;
      int i1 = (new Integer(arrayOfString[1])).intValue();
      i1--;
      int i2 = (new Integer(arrayOfString[2])).intValue();
      arrayOfString = str7.split(":");
      int i3 = (new Integer(arrayOfString[0])).intValue();
      int i4 = (new Integer(arrayOfString[1])).intValue();
      _$6686 = new Date(n, i1, i2, i3, i4, 0);
    } 
    int i = str1.indexOf("磁碟空間總計") + 6;
    int j = i + 14;
    String str3 = str1.substring(i, j);
    str3 = str3.replaceAll(",", "");
    str3 = str3.replaceAll(" ", "");
    _$6685 = Long.parseLong(str3);
    i = str1.indexOf("可用磁碟空間") + 6;
    j = i + 14;
    str3 = str1.substring(i, j);
    str3 = str3.replaceAll(",", "");
    str3 = str3.replaceAll(" ", "");
    _$6683 = Long.parseLong(str3);
    _$6684 = _$6685 - _$6683;
    vector.add(_$6618);
    vector.add(new Long(_$6683));
    return vector;
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      System.err.println("Testing function: \"DiskStatus\"");
      System.err.println("Checking disk status: H:\\");
      getDiskStatus(new File("H:\\"));
      System.err.println(" Result:");
      System.err.println("   Label: " + getDiskLabel());
      System.err.println("   Free space: " + getDiskFree());
      System.err.println("   Used space: " + getDiskUsed());
      System.err.println(" End");
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
