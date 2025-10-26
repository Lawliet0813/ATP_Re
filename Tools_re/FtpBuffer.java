package Tools;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.CopyTask;
import com.MiTAC.TRA.ATP.Tools.DiskTools.CopyDir;
import com.MiTAC.TRA.ATP.Tools.DiskTools.DeleteDir;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import com.MiTAC.TRA.ATP.Tools.SwingWorker;
import com.MiTAC.TRA.ATP.connect.ConnectFTP;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import javax.swing.Timer;

public class FtpBuffer extends Thread {
  private static Object[] datawch;
  
  private static ConnectFTP cnftpwch;
  
  private static Timer tx_watcher;
  
  private static SwingWorker ftpWorker;
  
  private static boolean running = false;
  
  private static Vector ftpFileList;
  
  private static final int bufferSize = 4096;
  
  private static Object[][] buffer = new Object[4096][8];
  
  private static int pointer = 0;
  
  private static int size = 0;
  
  private static final String up = ATPMessages.getString("MW.FTP.UD.UPLOAD");
  
  private static final String down = ATPMessages.getString("MW.FTP.UD.DOWNLOAD");
  
  public static final String successMsg = ATPMessages.getString("MW.FTP.STATUS.COMPLETED");
  
  public static final String failureMsg = ATPMessages.getString("MW.FTP.STATUS.FAILURE");
  
  public static final String reConnectMsg = "重連";
  
  public static final String giveupMsg = ATPMessages.getString("MW.FTP.STATUS.GIVEUP");
  
  public static final String transferMsg = ATPMessages.getString("MW.FTP.STATUS.TRANSFER");
  
  public static final String waitingMsg = ATPMessages.getString("MW.FTP.STATUS.WAIT");
  
  public static final String fileExistMsg = ATPMessages.getString("MW.FTP.STATUS.EXIST");
  
  public static final String invaliedMsg = ATPMessages.getString("MW.FTP.STATUS.INVALIED");
  
  public static final String extendMsg = ATPMessages.getString("MW.FTP.STATUS.EXTEND");
  
  public static final String replaceMsg = ATPMessages.getString("MW.FTP.STATUS.REPLACE");
  
  private static final String fromFTP = "FTP";
  
  private ConnectFTP cnftp;
  
  public void run() {
    try {
      running = true;
      setPriority(1);
      this.cnftp = new ConnectFTP();
      try {
        this.cnftp.ftpconnect();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      tx_watcher = new Timer(500, (ActionListener)new Object(this));
      ftpWorker = (SwingWorker)new Object(this);
      ftpWorker.start();
    } catch (InterruptedException itex) {
      itex.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private static synchronized void doFTPUpload(Object[] data, ConnectFTP cnftp) {
    datawch = data;
    cnftpwch = cnftp;
    if (!tx_watcher.isRunning())
      tx_watcher.start(); 
    try {
      System.out.print("\t\tPUT.");
      System.out.print(" " + data[0] + " " + data[1] + " " + data[2] + " " + data[3]);
      data[4] = new Date(System.currentTimeMillis());
      if (cnftp.isExist((String)data[2], (String)data[3])) {
        String tmp_FTP_Path = "C:\\TEMP_FTP_PUT\\";
        cnftp.mget(new String[][] { { (String)data[2], String.valueOf(data[3]) + "_-1.zip" },  }, new File(tmp_FTP_Path));
        String dir = String.valueOf(data[2]) + "\\" + (String)data[3];
        String srcPath = String.valueOf(data[1]) + dir;
        int srcType = CopyTask.checkFileType(new File(srcPath));
        String ftpPath = String.valueOf(tmp_FTP_Path) + dir;
        int ftpType = CopyTask.checkFileType(new File(ftpPath));
        try {
          if ((ftpType == 2 || ftpType == 1) && srcType == 1) {
            cnftp.rmdir((String)data[2], (String)data[3]);
            cnftp.mput((String)data[1], (String)data[2], (String)data[3]);
            data[6] = replaceMsg;
          } else if (ftpType == 2 && srcType == 2) {
            cnftp.rmdir((String)data[2], (String)data[3]);
            CopyDir cd = new CopyDir(new File(srcPath), new File(ftpPath));
            data[1] = tmp_FTP_Path;
            cnftp.mput((String)data[1], (String)data[2], (String)data[3]);
            data[6] = extendMsg;
          } else {
            data[6] = fileExistMsg;
          } 
        } catch (FileNotFoundException fex) {
          fex.printStackTrace();
        } catch (Exception ex) {
          throw ex;
        } 
        DeleteDir.delDir(new File(tmp_FTP_Path));
      } else {
        cnftp.mput((String)data[1], (String)data[2], (String)data[3]);
        data[6] = successMsg;
      } 
      data[5] = new Date(System.currentTimeMillis());
    } catch (Exception ex) {
      data[5] = new Date(System.currentTimeMillis());
      if (data[7] == null) {
        data[6] = failureMsg;
        addList(new String[] { (String)data[1], (String)data[2], (String)data[3] }, 1);
      } else {
        String[] cmd = ((String)data[7]).split(",");
        int retryTimes = (new Integer(cmd[0])).intValue();
        retryTimes++;
        if (retryTimes >= 3) {
          data[6] = giveupMsg;
        } else {
          data[6] = String.valueOf(failureMsg) + "(" + retryTimes + ")";
          addList(new String[] { (String)data[1], (String)data[2], (String)data[3] }, retryTimes);
        } 
      } 
      StringBuffer sb = new StringBuffer();
      sb.append((data[7] == null) ? "0" : (String)data[7]);
      sb.append(",");
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
      data[7] = sb.toString();
      ex.printStackTrace();
    } finally {
      if (tx_watcher.isRunning())
        tx_watcher.stop(); 
    } 
  }
  
  private static synchronized void doFTPDownload(Object[] data, ConnectFTP cnftp) {
    try {
      System.out.print("\t\tGET.");
      System.out.print(
          " date " + data[2] + 
          ", dir " + data[3]);
      data[4] = new Date(System.currentTimeMillis());
      StringBuffer filePath = new StringBuffer();
      filePath.append(
          InitParameters.MWLogPath.endsWith("\\") ? 
          InitParameters.MWLogPath : (
          String.valueOf(InitParameters.MWLogPath) + "\\"));
      filePath.append(data[2] + "\\");
      filePath.append(data[3] + "\\");
      String path = PathHandler.getEncodePath(PathHandler.getDecodePath(filePath.toString()));
      path = InitParameters.MWLogPath.endsWith("\\") ? (
        String.valueOf(InitParameters.MWLogPath) + path) : (
        String.valueOf(InitParameters.MWLogPath) + "\\" + path);
      File file = new File(path);
      if (file.exists()) {
        String tmpFile = "C:\\TEMP_FTP_GET\\";
        cnftp.mget(new String[][] { { (String)data[2], data[3] + ".zip" },  }, new File(tmpFile));
        String dir = String.valueOf(data[2]) + "\\" + (String)data[3];
        int lclType = CopyTask.checkFileType(file);
        String ftpPath = String.valueOf(tmpFile) + dir.substring(0, dir.length() - 3);
        int ftpType = CopyTask.checkFileType(new File(ftpPath));
        System.out.println(ftpPath);
        System.out.println("FTP TYPE: " + ftpType + " ~~ " + "LOCAL TYPE: " + lclType);
        if (ftpType == 2 && lclType == 2) {
          System.out.println("資料擴展");
          CopyDir cd = new CopyDir(new File(ftpPath), file);
          data[6] = extendMsg;
        } else if (ftpType == 1 && (lclType == 2 || lclType == 1)) {
          System.out.println("資料取代");
          DeleteDir.delDir(file);
          CopyDir cd = new CopyDir(new File(ftpPath), file);
          data[6] = replaceMsg;
        } else {
          data[6] = fileExistMsg;
        } 
      } else {
        cnftp.mget((String)data[2], (String)data[3]);
        data[6] = successMsg;
      } 
      data[5] = new Date(System.currentTimeMillis());
    } catch (Exception ex) {
      data[5] = new Date(System.currentTimeMillis());
      if (data[7] == null) {
        data[6] = failureMsg;
        addList(new String[] { (String)data[2], (String)data[3] }, 1);
      } else {
        String[] cmd = ((String)data[7]).split(",");
        int retryTimes = (new Integer(cmd[0])).intValue();
        retryTimes++;
        if (retryTimes >= 3) {
          data[6] = giveupMsg;
        } else {
          data[6] = String.valueOf(failureMsg) + "(" + retryTimes + ")";
          addList(new String[] { (String)data[2], (String)data[3] }, retryTimes);
        } 
      } 
      StringBuffer sb = new StringBuffer();
      sb.append((data[7] == null) ? "0" : (String)data[7]);
      sb.append(",");
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
      data[7] = sb.toString();
      ex.printStackTrace();
    } 
  }
  
  public static boolean isRunning() {
    return running;
  }
  
  public static synchronized void refreshFTPFileList() throws Exception {
    try {
      ConnectFTP ftp = new ConnectFTP();
      ftp.ftpconnect();
      ftpFileList = ftp.getATPDirList();
      ftp.closeServer();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public static Object[][] getBufferStatus() {
    return buffer;
  }
  
  public static Object[][] getBufferStatus2() {
    Object[][] newBuffer = new Object[size][12];
    for (int x = 0; x < size; x++) {
      Vector path = new Vector();
      try {
        path = PathHandler.getDecodePath("x:\\" + buffer[x][2] + "\\" + buffer[x][3]);
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      newBuffer[x][0] = new Integer(x + 1);
      newBuffer[x][1] = buffer[x][0];
      newBuffer[x][2] = buffer[x][1];
      newBuffer[x][3] = path.get(0);
      newBuffer[x][4] = path.get(1);
      newBuffer[x][5] = path.get(2);
      newBuffer[x][6] = path.get(3);
      newBuffer[x][7] = path.get(4);
      if (buffer[x][4] == null) {
        newBuffer[x][8] = null;
      } else {
        newBuffer[x][8] = buffer[x][4];
      } 
      if (buffer[x][5] == null) {
        newBuffer[x][9] = null;
      } else {
        newBuffer[x][9] = buffer[x][5];
      } 
      if (buffer[x][4] == null) {
        newBuffer[x][10] = null;
      } else if (buffer[x][5] == null) {
        newBuffer[x][10] = new Long(System.currentTimeMillis() - ((Date)buffer[x][4]).getTime());
      } else {
        newBuffer[x][10] = new Long(((Date)buffer[x][5]).getTime() - ((Date)buffer[x][4]).getTime());
      } 
      newBuffer[x][11] = buffer[x][6];
    } 
    return newBuffer;
  }
  
  public static Vector getFTPFileList() {
    return ftpFileList;
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
  
  public static int getUploadNumber() {
    return 0;
  }
  
  public static int getDownloadNumber() {
    return 0;
  }
  
  public static synchronized void clearFTPFinished() {
    Object[][] newBuffer = new Object[4096][8];
    int counter = 0;
    int bufferSize = size;
    int bufferPoint = pointer;
    for (int x = 0; x < bufferSize; x++) {
      if (x < bufferPoint) {
        if (buffer[x][6].equals(successMsg) || 
          buffer[x][6].equals(fileExistMsg)) {
          pointer--;
          size--;
        } else {
          newBuffer[counter++] = buffer[x];
        } 
      } else {
        newBuffer[counter++] = buffer[x];
      } 
    } 
    buffer = newBuffer;
  }
  
  public static synchronized void clearFTPFailure() {
    Object[][] newBuffer = new Object[4096][8];
    int counter = 0;
    int bufferSize = size;
    int bufferPoint = pointer;
    for (int x = 0; x < bufferSize; x++) {
      if (x < bufferPoint) {
        if (!buffer[x][6].toString().startsWith(failureMsg) || 
          !buffer[x][6].toString().startsWith(giveupMsg)) {
          pointer--;
          size--;
        } else {
          newBuffer[counter++] = buffer[x];
        } 
      } else {
        newBuffer[counter++] = buffer[x];
      } 
    } 
    buffer = newBuffer;
  }
  
  public static synchronized void resendFTPFailured() {
    Object[][] newBuffer = new Object[4096][8];
    Object[][] failureList = new Object[4096][8];
    int counter = 0;
    int bufferSize = size;
    int bufferPoint = pointer;
    int failureCounter = 0;
    int x;
    for (x = 0; x < bufferSize; x++) {
      if (x < bufferPoint) {
        if (!buffer[x][6].equals(failureMsg)) {
          newBuffer[counter++] = buffer[x];
        } else {
          pointer--;
          size--;
          buffer[x][6] = waitingMsg;
          failureList[failureCounter++] = buffer[x];
        } 
      } else {
        newBuffer[counter++] = buffer[x];
      } 
    } 
    for (x = 0; x < failureList.length; x++) {
      newBuffer[counter++] = failureList[x];
      size++;
    } 
    buffer = newBuffer;
  }
  
  public static synchronized void removeSelected(int[] list) {
    Object[][] newBuffer = new Object[4096][8];
    int counter = 0;
    int bufferSize = size;
    int listCounter = 0;
    int removeNumber = list[listCounter];
    Arrays.sort(list);
    for (int x = 0; x < bufferSize; x++) {
      if (x == removeNumber) {
        pointer--;
        size--;
        listCounter++;
        if (listCounter < list.length)
          removeNumber = list[listCounter]; 
      } else {
        newBuffer[counter++] = buffer[x];
      } 
    } 
  }
  
  public static synchronized void addList(String[] newList, String updown) {
    if (updown.equals(up)) {
      addList(newList, updown, 0);
    } else if (updown.equals(down)) {
      addList(newList, updown, 0);
    } else {
      System.out.println("got unknow status");
    } 
  }
  
  public static synchronized void addList(String[] newList) {
    if (newList.length == 2) {
      addList(newList, down, 0);
    } else if (newList.length == 3) {
      addList(newList, up, 0);
    } else {
      System.err.println("got unknow status");
    } 
  }
  
  public static synchronized void addList(String[] newList, int redo) {
    if (newList.length == 2) {
      addList(newList, down, redo);
    } else if (newList.length == 3) {
      addList(newList, up, redo);
    } else {
      System.err.println("got unknow status");
    } 
  }
  
  public static synchronized void addList(String[] newList, String updown, int redo) {
    if (size == buffer.length) {
      clearFTPFinished();
      if (size == buffer.length)
        throw new IndexOutOfBoundsException(); 
    } 
    if (updown.equals(up)) {
      buffer[size][0] = up;
      buffer[size][1] = newList[0];
      buffer[size][2] = newList[1];
      buffer[size][3] = newList[2];
      buffer[size][4] = null;
      buffer[size][5] = null;
      if (redo == 0) {
        buffer[size][6] = waitingMsg;
      } else {
        buffer[size][6] = String.valueOf(waitingMsg) + "(" + redo + ")";
        buffer[size][7] = redo;
      } 
      size++;
    } else if (updown.equals(down)) {
      buffer[size][0] = down;
      buffer[size][1] = "FTP";
      buffer[size][2] = newList[0];
      buffer[size][3] = newList[1];
      buffer[size][4] = null;
      buffer[size][5] = null;
      if (redo == 0) {
        buffer[size][6] = waitingMsg;
      } else {
        buffer[size][6] = String.valueOf(waitingMsg) + "(" + redo + ")";
        buffer[size][7] = redo;
      } 
      size++;
    } else {
      System.err.print("UNKNOW data[");
      for (int x = 0; x < (buffer[size]).length; x++)
        System.err.print(buffer[size][x] + " "); 
      System.err.println("]");
    } 
  }
  
  public static void printBufferStatus() {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < 7; y++)
        System.out.println("[" + buffer[x][y] + ","); 
      System.out.println("]");
    } 
  }
}
