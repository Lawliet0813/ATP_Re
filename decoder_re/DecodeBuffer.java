package decoder;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.DiskTools.CopyDir;
import com.MiTAC.TRA.ATP.Tools.DiskTools.CopyFile;
import com.MiTAC.TRA.ATP.Tools.DiskTools.DeleteDir;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.core.ATPMission;
import com.MiTAC.TRA.ATP.core.ATPMissionFailure;
import com.MiTAC.TRA.ATP.core.ATPMissionGeneral;
import com.MiTAC.TRA.ATP.decoder.DataFeeder;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.JOptionPane;

public class DecodeBuffer extends Thread {
  private static boolean running = false;
  
  private static int bufferSize = 4096;
  
  private static int bufferDepth = 15;
  
  private static Object[][] buffer = new Object[bufferSize][bufferDepth];
  
  private static int pointer = 0;
  
  private static int size = 0;
  
  public static boolean ignoreNoError = true;
  
  public static final String successMsg = ATPMessages.getString("MW.DECODE.STATUS.COMPLETED");
  
  public static final String failureMsg = ATPMessages.getString("MW.DECODE.STATUS.FAILURE");
  
  public static final String giveupMsg = ATPMessages.getString("MW.DECODE.STATUS.GIVEUP");
  
  public static final String decodeMsg = ATPMessages.getString("MW.DECODE.STATUS.DECODING");
  
  public static final String waitingMsg = ATPMessages.getString("MW.DECODE.STATUS.WAIT");
  
  public static final String dataExistMsg = ATPMessages.getString("MW.DECODE.STATUS.EXIST");
  
  public static final String pathNotExistMsg = ATPMessages.getString("MW.DECODE.STATUS.PATH_ERROR");
  
  public static final String invaliedMsg = ATPMessages.getString("MW.DECODE.STATUS.INVALIED");
  
  public static final String ignoreMsg = ATPMessages.getString("MW.DECODE.STATUS.IGNORE");
  
  public static final String replaceMsg = ATPMessages.getString("MW.DECODE.STATUS.REPLACE");
  
  public static final String extendMsg = ATPMessages.getString("MW.DECODE.STATUS.EXTEND");
  
  public static final String noDataMsg = ATPMessages.getString("MW.DECODE.STATUS.NODATA");
  
  DataFeeder decoder;
  
  public void run() {
    running = true;
    setPriority(1);
    while (true) {
      while (pointer == size) {
        try {
          System.out.print("==");
          sleep(3000L);
        } catch (Exception ex) {
          JOptionPane.showConfirmDialog(
              null, 
              ex.getMessage(), 
              "Error", 
              0, 
              0);
          ex.printStackTrace();
        } 
      } 
      Object[] data = buffer[pointer];
      checkPath((File)data[0], pointer);
      data[6] = new Date(System.currentTimeMillis());
      if (data[8].equals(waitingMsg)) {
        data[8] = decodeMsg;
        ATPMissionGeneral mson = new ATPMissionGeneral((File)data[0]);
        try {
          ConnectDB cndb = new ConnectDB();
          Vector isDBExists = cndb.getLogDecodeCategory(new Object[] { mson.getMissionDate(), 
                mson.getWorkShift(), 
                mson.getTrainRunning(), 
                mson.getDriver(), 
                mson.getVehicle() });
          if (isDBExists.size() != 0) {
            if (data[13] == ATPMessages.getString("MW.DECODE.FILETYPE.RU")) {
              cndb.DeleteLog((ATPMission)mson);
              doDecode(data, cndb);
              data[8] = replaceMsg;
            } else if (data[13] == ATPMessages.getString("MW.DECODE.FILETYPE.MMI")) {
              String[] list = ((File)data[0]).list();
              if (list.length != 0) {
                String path = ((File)data[0]).getCanonicalPath().replace('\\', '/');
                String[] para = path.split("/");
                File replaceableDir = new File("C:\\DecodeBuffer_Replace\\" + para[para.length - 2] + "\\" + para[para.length - 1]);
                replaceableDir.mkdirs();
                CopyDir cd = new CopyDir((File)data[0], replaceableDir);
                data[0] = replaceableDir;
                Vector category = isDBExists.get(0);
                Date dStart = category.get(24);
                Date dEnd = category.get(25);
                System.out.println("記錄開始時間" + dStart.toLocaleString());
                System.out.println("記錄結束時間" + dEnd.toLocaleString());
                File[] replaceableFile = replaceableDir.listFiles();
                for (int x = 0; x < replaceableFile.length; x++) {
                  String fileName = replaceableFile[x].getName();
                  Date missionDate = (Date)data[1];
                  GregorianCalendar gCalendar = new GregorianCalendar();
                  gCalendar.set(
                      missionDate.getYear() + 1900, 
                      missionDate.getMonth(), 
                      missionDate.getDate(), (
                      new Integer(fileName.substring(0, 2))).intValue(), (
                      new Integer(fileName.substring(2, 4))).intValue(), (
                      new Integer(fileName.substring(4, 6))).intValue());
                  System.out.print("\t >> File Time: " + gCalendar.getTime().toLocaleString());
                  if (dStart.before(gCalendar.getTime()) && dEnd.after(gCalendar.getTime())) {
                    if (replaceableFile[x].delete()) {
                      System.out.println("  match. deleted");
                    } else {
                      System.out.println("  match. delete failure");
                    } 
                  } else {
                    System.out.println("  out of bound. need decode");
                  } 
                } 
                if ((((File)data[0]).list()).length == 0) {
                  System.out.println("判定資料重複");
                  data[8] = dataExistMsg;
                } else {
                  System.out.println("有資料不重複");
                  doDecode(data, cndb);
                  data[8] = extendMsg;
                } 
                DeleteDir.delDir(new File("C:\\DecodeBuffer_Replace\\"));
                System.out.println(" \t DecodeBuffer_Replace deleted.");
              } else {
                data[8] = noDataMsg;
              } 
            } 
          } else {
            doDecode(data, cndb);
          } 
        } catch (Exception ex) {
          ex.printStackTrace();
          data[8] = failureMsg;
          StringBuffer sb = new StringBuffer();
          sb.append(data[0]);
          sb.append("_");
          sb.append(data[1]);
          sb.append("_");
          sb.append(data[2]);
          sb.append("_");
          sb.append(data[3]);
          sb.append("_");
          sb.append(data[4]);
          sb.append("_");
          sb.append(data[5]);
          sb.append("\n\r\t");
          sb.append(ex.getMessage());
          sb.append("\n\r\t");
          StackTraceElement[] ste = ex.getStackTrace();
          for (int x = 0; x < ste.length; x++) {
            sb.append(ste[x].toString());
            sb.append("\n\r\t");
          } 
          sb.append("\n\n");
          data[14] = sb.toString();
        } 
      } 
      data[7] = new Date(System.currentTimeMillis());
      pointer++;
    } 
  }
  
  private void doDecode(Object[] data, ConnectDB cndb) throws Exception {
    File[] fileSplit;
    int fileNumber = (((File)data[0]).listFiles()).length;
    if (fileNumber > 8) {
      int slipNo = fileNumber / 8;
      slipNo += (fileNumber % 8 == 0) ? 0 : 1;
      fileSplit = new File[slipNo];
      File[] list = ((File)data[0]).listFiles();
      String path = ((File)data[0]).getCanonicalPath().replace('\\', '/');
      String[] para = path.split("/");
      File dir = new File("");
      for (int y = 0; y < fileNumber; y++) {
        if (y % 8 == 0) {
          dir = new File("C:\\DecodeBuffer\\" + (y / 8) + "\\" + para[para.length - 2] + "\\" + para[para.length - 1]);
          dir.mkdirs();
          fileSplit[y / 8] = dir;
        } 
        File from = list[y];
        File to = new File(String.valueOf(dir.getAbsolutePath()) + "\\" + list[y].getName());
        CopyFile copyFile = new CopyFile(from, to);
      } 
    } else {
      fileSplit = new File[1];
      fileSplit[0] = (File)data[0];
    } 
    Vector preLog = cndb.getLogDecodeCategory(new Object[] { data[1], data[2], data[3], data[4], data[5] });
    int ebTime = 0;
    int sbTime = 0;
    int cfTime = 0;
    int wfTime = 0;
    if (preLog.size() != 0) {
      Vector tmp = preLog.get(0);
      ebTime = Integer.parseInt(tmp.get(19).toString());
      sbTime = Integer.parseInt(tmp.get(20).toString());
      cfTime = Integer.parseInt(tmp.get(21).toString());
      wfTime = Integer.parseInt(tmp.get(22).toString());
    } 
    boolean isolation = false;
    int x;
    for (x = 0; x < fileSplit.length; x++) {
      this.decoder = new DataFeeder(fileSplit[x]);
      ebTime += this.decoder.getEBTimes();
      sbTime += this.decoder.getSBTimes();
      cfTime += this.decoder.getCabinFailureTimes();
      wfTime += this.decoder.getWaysideFailureTimes();
      isolation = !(!isolation && !this.decoder.getIsolation());
    } 
    if (sbTime + ebTime + cfTime + wfTime == 0 && !isolation && ignoreNoError) {
      data[8] = String.valueOf(successMsg) + "." + ignoreMsg;
    } else {
      for (x = 0; x < fileSplit.length; x++) {
        this.decoder = new DataFeeder(fileSplit[x]);
        cndb.InsertMissionLog(this.decoder.getMission());
      } 
      Date start = this.decoder.getMission().getMissionStartTime();
      Date end = this.decoder.getMission().getMissionEndTime();
      if (preLog.size() != 0) {
        Vector tmp = preLog.get(0);
        Date ds = tmp.get(24);
        Date de = tmp.get(25);
        if (ds.before(start))
          start = ds; 
        if (de.after(end))
          end = de; 
      } 
      String[] fileName = ((File)data[0]).list();
      for (int i = 0; i < fileName.length; i++) {
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.set(
            this.decoder.getMission().getMissionDate().getYear() + 1900, 
            this.decoder.getMission().getMissionDate().getMonth(), 
            this.decoder.getMission().getMissionDate().getDate(), (
            new Integer(fileName[i].substring(0, 2))).intValue(), (
            new Integer(fileName[i].substring(2, 4))).intValue(), (
            new Integer(fileName[i].substring(4, 6))).intValue());
        if (gCalendar.getTime().before(start))
          start = gCalendar.getTime(); 
        if (gCalendar.getTime().after(end))
          end = gCalendar.getTime(); 
      } 
      ATPMissionFailure t = new ATPMissionFailure((ATPMission)this.decoder.getMission());
      t.getFilterBehaviorFailure();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
      SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      String updateString = 
        "update logcategory set ebtimes='" + 
        t.getBehavior_ebTimes_filter() + "'," + 
        " sbtimes='" + t.getBehavior_sbTimes_filter() + "'," + 

        
        " waysidefailuretimes='" + wfTime + "'," + 
        " cabinfailuretimes='" + cfTime + "'," + 
        " isolation='" + (isolation ? 1 : 0) + "'," + 
        " StartTime='" + sdf2.format(start) + "'," + 
        " EndTime='" + sdf2.format(end) + "' " + 
        "where missiondate='" + sdf.format(this.decoder.getMission().getMissionDate()) + "'" + 
        " and wsno='" + this.decoder.getMission().getWorkShift() + "'" + 
        " and trno='" + this.decoder.getMission().getTrainRunning() + "'" + 
        " and did='" + this.decoder.getMission().getDriver() + "'" + 
        " and vid='" + this.decoder.getMission().getVehicle() + "'";
      cndb.Update(updateString);
      if (fileSplit.length > 1)
        DeleteDir.delDir(new File("C:\\DecodeBuffer\\")); 
      data[8] = successMsg;
    } 
    data[9] = new Integer(sbTime);
    data[10] = new Integer(ebTime);
    data[11] = new Integer(cfTime);
    data[12] = new Integer(wfTime);
  }
  
  public static boolean isRunning() {
    return running;
  }
  
  public static synchronized void addTask(File path) {
    System.out.println("PATH ADD: " + path.getAbsolutePath());
    if (size == buffer.length) {
      clearDecodeFinished();
      if (size == buffer.length)
        System.out.println("Index Out Of Bounds Exception"); 
    } 
    try {
      Vector data = PathHandler.getDecodePath(path.getPath());
      buffer[size][0] = path;
      buffer[size][1] = data.get(0);
      buffer[size][2] = data.get(1);
      buffer[size][3] = data.get(2);
      buffer[size][4] = data.get(3);
      buffer[size][5] = data.get(4);
      buffer[size][6] = null;
      buffer[size][7] = null;
      buffer[size][8] = waitingMsg;
      buffer[size][9] = null;
      buffer[size][10] = null;
      buffer[size][11] = null;
      buffer[size][12] = null;
      String[] tmp = path.list();
      buffer[size][13] = null;
      buffer[size][14] = null;
      size++;
    } catch (Exception ex) {
      ex.printStackTrace();
      buffer[size][0] = path;
      buffer[size][1] = null;
      buffer[size][2] = null;
      buffer[size][3] = null;
      buffer[size][4] = null;
      buffer[size][5] = null;
      buffer[size][6] = null;
      buffer[size][7] = null;
      buffer[size][8] = invaliedMsg;
      buffer[size][9] = null;
      buffer[size][10] = null;
      buffer[size][11] = null;
      buffer[size][12] = null;
      buffer[size][13] = null;
      buffer[size][14] = null;
      ex.printStackTrace();
      StringBuffer sb = new StringBuffer();
      sb.append(buffer[size][0]);
      sb.append("_");
      sb.append(buffer[size][1]);
      sb.append("_");
      sb.append(buffer[size][2]);
      sb.append("_");
      sb.append(buffer[size][3]);
      sb.append("_");
      sb.append(buffer[size][4]);
      sb.append("_");
      sb.append(buffer[size][5]);
      sb.append("\n\r\t");
      sb.append(ex.getMessage());
      sb.append("\n\r\t");
      StackTraceElement[] ste = ex.getStackTrace();
      for (int x = 0; x < ste.length; x++) {
        sb.append(ste[x].toString());
        sb.append("\n\r\t");
      } 
      sb.append("\n\n");
      buffer[size][14] = sb.toString();
      size++;
    } 
  }
  
  private static synchronized void checkPath(File path, int pointer) {
    if (path.exists()) {
      try {
        Vector data = PathHandler.getDecodePath(path.getPath());
        buffer[pointer][0] = path;
        buffer[pointer][1] = data.get(0);
        buffer[pointer][2] = data.get(1);
        buffer[pointer][3] = data.get(2);
        buffer[pointer][4] = data.get(3);
        buffer[pointer][5] = data.get(4);
        buffer[pointer][6] = null;
        buffer[pointer][7] = null;
        buffer[pointer][8] = waitingMsg;
        buffer[pointer][9] = null;
        buffer[pointer][10] = null;
        buffer[pointer][11] = null;
        buffer[pointer][12] = null;
        String[] tmp = path.list();
        if (tmp.length == 0)
          buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.EMPTY"); 
        boolean typeRU = false;
        boolean typeMMI = false;
        boolean typeOther = false;
        for (int x = 0; x < tmp.length; x++) {
          if (tmp[x].endsWith("RU") || tmp[x].endsWith("ru")) {
            typeRU = true;
          } else if (tmp[x].endsWith("MMI") || tmp[x].endsWith("mmi")) {
            typeMMI = true;
          } else {
            typeOther = true;
          } 
        } 
        if (typeRU && typeMMI && typeOther) {
          buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.OTHER");
        } else if (typeRU && typeOther) {
          buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.RU_OTHER");
        } else if (typeMMI && typeOther) {
          buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.MMI_OTHER");
        } else if (typeRU && typeMMI) {
          buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.MIX");
        } else if (typeRU) {
          buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.RU");
        } else if (typeMMI) {
          buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.MMI");
        } else {
          buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.UNKNOW");
        } 
        buffer[pointer][14] = null;
      } catch (Exception ex) {
        ex.printStackTrace();
        buffer[pointer][0] = path;
        buffer[pointer][1] = null;
        buffer[pointer][2] = null;
        buffer[pointer][3] = null;
        buffer[pointer][4] = null;
        buffer[pointer][5] = null;
        buffer[pointer][6] = null;
        buffer[pointer][7] = null;
        buffer[pointer][8] = invaliedMsg;
        buffer[pointer][9] = null;
        buffer[pointer][10] = null;
        buffer[pointer][11] = null;
        buffer[pointer][12] = null;
        buffer[pointer][13] = null;
        buffer[pointer][14] = null;
        StringBuffer sb = new StringBuffer();
        sb.append(buffer[size][0]);
        sb.append("_");
        sb.append(buffer[size][1]);
        sb.append("_");
        sb.append(buffer[size][2]);
        sb.append("_");
        sb.append(buffer[size][3]);
        sb.append("_");
        sb.append(buffer[size][4]);
        sb.append("_");
        sb.append(buffer[size][5]);
        sb.append("\n\r\t");
        sb.append(ex.getMessage());
        sb.append("\n\r\t");
        StackTraceElement[] ste = ex.getStackTrace();
        for (int x = 0; x < ste.length; x++) {
          sb.append(ste[x].toString());
          sb.append("\n\r\t");
        } 
        sb.append("\n\n");
        buffer[size][14] = sb.toString();
      } 
    } else {
      System.err.println(path.getAbsolutePath());
      buffer[pointer][0] = path;
      buffer[pointer][1] = null;
      buffer[pointer][2] = null;
      buffer[pointer][3] = null;
      buffer[pointer][4] = null;
      buffer[pointer][5] = null;
      buffer[pointer][6] = null;
      buffer[pointer][7] = null;
      buffer[pointer][8] = pathNotExistMsg;
      buffer[pointer][9] = null;
      buffer[pointer][10] = null;
      buffer[pointer][11] = null;
      buffer[pointer][12] = null;
      buffer[pointer][13] = null;
      buffer[pointer][14] = null;
    } 
  }
  
  public static Object[][] getBufferStatus() {
    return buffer;
  }
  
  public static Object[][] getBufferStatus2() {
    Object[][] newBuffer = new Object[size][17];
    for (int x = 0; x < size; x++) {
      Vector path = new Vector();
      try {
        path = PathHandler.getDecodePath(((File)buffer[x][0]).getAbsolutePath());
      } catch (Exception ex) {
        path = new Vector();
        path.add(null);
        path.add(null);
        path.add(null);
        path.add(null);
        path.add(null);
      } 
      newBuffer[x][0] = new Integer(x + 1);
      newBuffer[x][1] = buffer[x][0];
      newBuffer[x][2] = path.get(0);
      newBuffer[x][3] = path.get(1);
      newBuffer[x][4] = path.get(2);
      newBuffer[x][5] = path.get(3);
      newBuffer[x][6] = path.get(4);
      if (buffer[x][6] == null) {
        newBuffer[x][7] = null;
      } else {
        newBuffer[x][7] = buffer[x][6];
      } 
      if (buffer[x][7] == null) {
        newBuffer[x][8] = null;
      } else {
        newBuffer[x][8] = buffer[x][7];
      } 
      if (buffer[x][6] == null) {
        newBuffer[x][9] = null;
      } else if (buffer[x][7] == null) {
        newBuffer[x][9] = new Long(System.currentTimeMillis() - ((Date)buffer[x][6]).getTime());
      } else {
        newBuffer[x][9] = new Long(((Date)buffer[x][7]).getTime() - ((Date)buffer[x][6]).getTime());
      } 
      newBuffer[x][10] = buffer[x][8];
      newBuffer[x][11] = buffer[x][9];
      newBuffer[x][12] = buffer[x][10];
      newBuffer[x][13] = buffer[x][11];
      newBuffer[x][14] = buffer[x][12];
      newBuffer[x][15] = buffer[x][13];
      newBuffer[x][16] = buffer[x][14];
    } 
    return newBuffer;
  }
  
  public static int getBufferSize() {
    return size;
  }
  
  public static int getBufferFinished() {
    return pointer;
  }
  
  public static int getBufferWaiting() {
    return size - pointer;
  }
  
  public static synchronized void clearDecodeFinished() {
    Object[][] newBuffer = new Object[bufferSize][bufferDepth];
    int counter = 0;
    int bufferLength = size;
    int bufferPoint = pointer;
    for (int x = 0; x < bufferLength; x++) {
      if (x < bufferPoint) {
        if (!buffer[x][8].toString().startsWith(successMsg)) {
          newBuffer[counter++] = buffer[x];
        } else {
          pointer--;
          size--;
        } 
      } else {
        newBuffer[counter++] = buffer[x];
      } 
    } 
    buffer = newBuffer;
  }
  
  public static synchronized void clearFailure() {
    Object[][] newBuffer = new Object[bufferSize][bufferDepth];
    int counter = 0;
    int bufferLength = size;
    int bufferPoint = pointer;
    for (int x = 0; x < bufferLength; x++) {
      if (x < bufferPoint) {
        if (!buffer[x][8].equals(failureMsg)) {
          newBuffer[counter++] = buffer[x];
        } else {
          pointer--;
          size--;
        } 
      } else {
        newBuffer[counter++] = buffer[x];
      } 
    } 
    buffer = newBuffer;
  }
  
  public static synchronized void cleanExists() {
    Object[][] newBuffer = new Object[bufferSize][bufferDepth];
    int counter = 0;
    int bufferLength = size;
    int bufferPoint = pointer;
    for (int x = 0; x < bufferLength; x++) {
      if (x < bufferPoint) {
        if (!buffer[x][8].equals(dataExistMsg)) {
          newBuffer[counter++] = buffer[x];
        } else {
          pointer--;
          size--;
        } 
      } else {
        newBuffer[counter++] = buffer[x];
      } 
    } 
    buffer = newBuffer;
  }
}
