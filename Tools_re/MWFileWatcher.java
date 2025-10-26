package Tools;

import com.MiTAC.TRA.ATP.Tools.PathHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class MWFileWatcher {
  private static String fileName = "ATPMW_FILE_LIST.lst";
  
  private static String filePath;
  
  private static Date refreshTime = new Date(System.currentTimeMillis());
  
  private static String msg;
  
  private static Vector list;
  
  private static boolean refresh = false;
  
  private static boolean firstread = true;
  
  public static void setMWFilePath(String path) {
    filePath = path.endsWith("\\") ? path : (String.valueOf(path) + "\\");
  }
  
  public static Vector getList() {
    return list;
  }
  
  public static String getDescription() {
    return msg;
  }
  
  public static Date getRefreshDate() {
    return refreshTime;
  }
  
  public static boolean isRefresh() {
    return refresh;
  }
  
  public static void refresh() {
    if (!refresh) {
      msg = "refreshing by user, regen later.";
      refresh = true;
      getFileList();
      refresh = false;
      System.out.print("getFileList ");
      msg = "refresh time: " + getRefreshDate();
    } 
  }
  
  public static synchronized void run() {
    msg = "getting File List";
    if (firstread) {
      readFileList();
      firstread = false;
      msg = "need refresh";
      System.out.print("1st read... readFileList ");
    } else if (refresh) {
      readFileList();
      System.out.print("refreshing... readFileList ");
      msg = "need refresh";
    } else {
      msg = "refreshing, regen later.";
      refresh = true;
      getFileList();
      refresh = false;
      System.out.print("getFileList ");
      msg = "refresh time: " + getRefreshDate();
    } 
    System.out.println("tick");
  }
  
  private static void getFileList() {
    try {
      Vector tmpList = PathHandler.getPathList(filePath);
      list = tmpList;
      saveFileList();
      refreshTime = new Date(System.currentTimeMillis());
    } catch (Exception ex) {
      ex.printStackTrace();
      msg = ex.getMessage();
    } 
  }
  
  private static void readFileList() {
    try {
      StringBuffer sb = new StringBuffer();
      File file = new File(String.valueOf(filePath) + fileName);
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      sb.append(br.readLine());
      br.close();
      fr.close();
      Vector tmpList = new Vector();
      String[] listLV1 = sb.toString().split(";");
      for (int x = 0; x < listLV1.length; x++) {
        String[] listLV2 = listLV1[x].split(",");
        if (listLV2.length != 5) {
          System.err.print(String.valueOf(listLV2.length) + "  ");
          for (int y = 0; y < listLV2.length; y++)
            System.err.print(String.valueOf(listLV2[y]) + " "); 
          System.err.println();
        } else {
          Vector tmpList2 = new Vector();
          tmpList2.add(PathHandler.getRunningDate(listLV2[0]));
          tmpList2.add(listLV2[1].trim());
          tmpList2.add(listLV2[2].trim());
          tmpList2.add(listLV2[3].trim());
          tmpList2.add(listLV2[4].trim());
          tmpList.add(tmpList2);
        } 
      } 
      list = tmpList;
    } catch (FileNotFoundException fex) {
      System.out.println("FILE NOT FOUND!");
      System.out.println("getting file list");
      getFileList();
      System.out.println("file list ok");
    } catch (IOException iex) {
      iex.printStackTrace();
      msg = iex.getMessage();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private static void saveFileList() {
    try {
      if (list != null) {
        StringBuffer sb = new StringBuffer();
        for (int x = 0; x < list.size(); x++) {
          Vector tmp = list.get(x);
          sb.append(PathHandler.getEncodeSubDate(tmp));
          sb.append(", ");
          sb.append(tmp.get(1));
          sb.append(", ");
          sb.append(tmp.get(2));
          sb.append(", ");
          sb.append(tmp.get(3));
          sb.append(", ");
          sb.append(tmp.get(4));
          sb.append(";");
        } 
        File listFile = new File(String.valueOf(filePath) + fileName);
        FileWriter fw = new FileWriter(listFile);
        fw.write(sb.toString());
        fw.close();
      } 
    } catch (IOException ex) {
      ex.printStackTrace();
      msg = ex.getMessage();
    } 
  }
  
  public static void main(String[] args) {
    Object object = new Object();
    long delay = 1000L;
    Timer t = new Timer();
    t.scheduleAtFixedRate((TimerTask)object, 0L, delay);
  }
}
