package Tools.DiskTools;

import com.MiTAC.TRA.ATP.Tools.DiskTools.DiskName;
import com.MiTAC.TRA.ATP.Tools.StringHandler;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;

public class FormatDisk {
  public static final String FORMAT_FAT = "FAT";
  
  public static final String FORMAT_FAT32 = "FAT32";
  
  public static final String FORMAT_NTFS = "NTFS";
  
  private String _$16688;
  
  private BufferedInputStream _$6598;
  
  private String _$6599;
  
  private String _$2040;
  
  private BufferedInputStream _$6597;
  
  private Vector _$2507;
  
  private String _$16763;
  
  public FormatDisk(File paramFile, String paramString1, String paramString2) throws Exception {
    setFormatDisk(paramFile, paramString1, paramString2);
  }
  
  private void _$16767(File paramFile, String paramString1, String paramString2) throws Exception {
    this._$2507 = new Vector();
    this._$16688 = paramString2;
    String str1 = paramFile.getAbsolutePath();
    str1 = str1.substring(0, 2);
    File file1 = new File(System.getProperty("java.io.tmpdir"), "atpcmd.txt");
    PrintWriter printWriter1 = new PrintWriter(new FileWriter(file1, false));
    printWriter1.print("\n");
    printWriter1.close();
    File file2 = File.createTempFile("atpscript", ".bat", new File(System.getProperty("java.io.tmpdir")));
    file2.deleteOnExit();
    PrintWriter printWriter2 = new PrintWriter(new FileWriter(file2, false));
    printWriter2.print("FORMAT " + str1 + " /FS:" + paramString1 + " /V:" + paramString2 + " /X /Q" + " <" + System.getProperty("java.io.tmpdir") + "atpcmd.txt");
    printWriter2.close();
    Process process = Runtime.getRuntime().exec(file2.getAbsolutePath());
    this._$6597 = new BufferedInputStream(process.getInputStream());
    this._$2040 = StringHandler.InputStreamToString(this._$6597);
    this._$6597.close();
    this._$6598 = new BufferedInputStream(process.getErrorStream());
    this._$6599 = StringHandler.InputStreamToString(this._$6598);
    this._$6598.close();
    if (this._$2040.indexOf("不存在") > 0 || this._$6599.length() > 0) {
      System.err.println("1" + this._$2040);
      System.err.println("2" + this._$6599);
      throw new Exception(paramFile.getAbsolutePath() + "\n" + "指定的磁碟機不存在, 請確認磁碟已安裝.\n" + "如果磁碟已安裝請檢查是否正確連接.");
    } 
    String str2 = "";
    int i = this._$2040.indexOf("磁碟空間總計") + 6;
    int j = i + 15;
    str2 = this._$2040.substring(i, j);
    str2 = str2.replaceAll(",", "");
    str2 = str2.replaceAll(" ", "");
    long l2 = Long.parseLong(str2);
    i = this._$2040.indexOf("可用磁碟空間") + 6;
    j = i + 15;
    str2 = this._$2040.substring(i, j);
    str2 = str2.replaceAll(",", "");
    str2 = str2.replaceAll(" ", "");
    long l1 = Long.parseLong(str2);
    i = this._$2040.indexOf("每個配置單元各有") + 8;
    j = i + 15;
    str2 = this._$2040.substring(i, j);
    str2 = str2.replaceAll(",", "");
    str2 = str2.replaceAll(" ", "");
    long l3 = Long.parseLong(str2);
    i = this._$2040.indexOf("磁碟上有") + 4;
    j = i + 15;
    str2 = this._$2040.substring(i, j);
    str2 = str2.replaceAll(",", "");
    str2 = str2.replaceAll(" ", "");
    long l4 = Long.parseLong(str2);
    i = this._$2040.indexOf("磁碟區序列號碼為") + 8;
    j = i + 10;
    str2 = this._$2040.substring(i, j);
    str2 = str2.replaceAll(" ", "");
    this._$16763 = str2;
    File file3 = new File(paramFile, "diskInfo.txt");
    PrintWriter printWriter3 = new PrintWriter(new FileWriter(file3, false));
    printWriter3.println("====================MiTAC Inc.============================\n");
    printWriter3.println("====================TRA - ATP Project=====================\n");
    printWriter3.println("-=-      Author: 許志豪(Hsu Chih-Hao)");
    printWriter3.println("-=-       Label: " + paramString2);
    printWriter3.println("-=-  FileSystem: " + paramString1);
    printWriter3.println("-=- Total Space: " + l2 + " bytes");
    printWriter3.println("-=-  Free Space: " + l1 + " bytes");
    printWriter3.println("-=-        Unit: " + l3 + " bytes");
    printWriter3.println("-=-     Cluster: " + l4);
    printWriter3.println("-=- Disk Serial: " + this._$16763);
    printWriter3.println("==========================================================\n");
    printWriter3.close();
    this._$2507.add(new Long(l2));
    this._$2507.add(new Long(l1));
    this._$2507.add(new Long(l3));
    this._$2507.add(new Long(l4));
  }
  
  public long getAvailableSize() {
    return ((Long)this._$2507.get(1)).longValue();
  }
  
  public long getCluster() {
    return ((Long)this._$2507.get(3)).longValue();
  }
  
  public long getDiskSize() {
    return ((Long)this._$2507.get(0)).longValue();
  }
  
  public String getErrorMessage() {
    return this._$6599;
  }
  
  public Vector getFormatResult() {
    return this._$2507;
  }
  
  public String getLabel() {
    return this._$16688;
  }
  
  public String getMessage() {
    return this._$2040;
  }
  
  public String getSerial() {
    return this._$16763;
  }
  
  public long getUnit() {
    return ((Long)this._$2507.get(2)).longValue();
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      System.err.println("Testing function: \"FormatDisk\"");
      System.err.println("  Formating F:\\...\n  please wait.");
      com.MiTAC.TRA.ATP.Tools.DiskTools.FormatDisk formatDisk = new com.MiTAC.TRA.ATP.Tools.DiskTools.FormatDisk(new File("F:\\"), "FAT32", DiskName.NewName());
      System.err.println(formatDisk.getMessage());
      System.err.println("Disk label : " + formatDisk.getLabel());
      System.err.println("Disk lize : " + formatDisk.getDiskSize());
      System.err.println("Available size : " + formatDisk.getAvailableSize());
      System.err.println("Unit : " + formatDisk.getUnit());
      System.err.println("Cluster : " + formatDisk.getCluster());
      System.err.println("Serial No. : " + formatDisk.getSerial());
      System.err.println("End.");
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void setFormatDisk(File paramFile, String paramString1, String paramString2) throws Exception {
    if (paramFile == null)
      throw new IllegalArgumentException("無效的磁碟"); 
    if (paramFile.equals(new File("c:")))
      throw new IllegalArgumentException("無法格式化 C 磁碟"); 
    if (System.getProperty("os.name").startsWith("Windows")) {
      _$16767(paramFile, paramString1, paramString2);
    } else {
      throw new Exception("本軟體尚不支援此作業系統: " + System.getProperty("os.name"));
    } 
  }
}
