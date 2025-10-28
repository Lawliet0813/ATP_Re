package connect;

import com.MITAC.Tools.ATPUnZIP;
import com.MITAC.Tools.ATPZIP;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import com.MiTAC.Tools.ATPFTP;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class ConnectFTP {
  private static String ftpHostIp = "";
  
  private static String ftpLogPath;
  
  private static int ftpPort;
  
  private static String ftpUsername = "";
  
  private static String ftpPassword = "";
  
  private static final int BUFFER_SIZE_LARGE = 58;
  
  private static final int BUFFER_SIZE_MEDIUM = 44;
  
  private static final int BUFFER_SIZE_SMALL = 29;
  
  ATPFTP ftp;
  
  static {
    ftpPort = 21;
    ftpLogPath = "";
  }
  
  public ConnectFTP(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4) {
    ftpHostIp = paramString1;
    ftpPort = paramInt;
    ftpUsername = paramString2;
    ftpPassword = paramString3;
    ftpLogPath = paramString4;
  }
  
  public ConnectFTP() throws Exception {
    ftpHostIp = InitParameters.FTPHostIP;
    ftpPort = InitParameters.FTPPort;
    ftpUsername = InitParameters.FTPUserName;
    ftpPassword = InitParameters.FTPUserPWD;
    ftpLogPath = InitParameters.FTPLogPath;
  }
  
  public boolean closeServer() {
    try {
      if (this.ftp.isConnected())
        this.ftp.disconnect(); 
      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } 
  }
  
  private boolean deleteRecursively(File paramFile) {
    if (paramFile.isDirectory()) {
      File[] arrayOfFile = paramFile.listFiles();
      for (byte b = 0; b < arrayOfFile.length; b++)
        deleteRecursively(arrayOfFile[b]); 
    } 
    System.out.print("deleting: " + paramFile.toString());
    System.out.println(" - " + paramFile.delete());
    return true;
  }
  
  public boolean ftpconnect() throws Exception {
    try {
      this.ftp = new ATPFTP(ftpHostIp, ftpPort, ftpUsername, ftpPassword, "");
      this.ftp.connect(ftpHostIp, ftpPort);
      if (!this.ftp.login(ftpUsername, ftpPassword))
        throw new Exception("使用者名稱/密碼錯誤."); 
      this.ftp.setFileType(2);
      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
      throw new Exception("資訊中心FTP連線失敗\n訊息: " + exception.getMessage());
    } 
  }
  
  public Vector getATPDirList() throws Exception {
    this.ftp.changeWorkingDirectory(ftpLogPath);
    BufferedInputStream bufferedInputStream = new BufferedInputStream(this.ftp.retrieveFileStream("ftpFileList.log"));
    System.out.print(this.ftp.getReplyString());
    File file = new File("C:\\ATPMW\\FTPLSIT.log");
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
    byte[] arrayOfByte = new byte[2048];
    int i;
    while (-1 != (i = bufferedInputStream.read(arrayOfByte, 0, arrayOfByte.length)))
      bufferedOutputStream.write(arrayOfByte, 0, i); 
    bufferedInputStream.close();
    bufferedOutputStream.close();
    this.ftp.completePendingCommand();
    System.out.print(this.ftp.getReplyString());
    try {
      Vector vector = new Vector();
      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      StringBuffer stringBuffer = new StringBuffer();
      stringBuffer.append(bufferedReader.readLine());
      bufferedReader.close();
      fileReader.close();
      String[] arrayOfString = stringBuffer.toString().split(";");
      for (byte b = 0; b < arrayOfString.length; b++) {
        String[] arrayOfString1 = arrayOfString[b].split(",");
        Vector vector1 = new Vector();
        vector1.add(PathHandler.getRunningDate(arrayOfString1[0]));
        vector1.add(arrayOfString1[1]);
        vector1.add(arrayOfString1[2]);
        vector1.add(arrayOfString1[3]);
        vector1.add(arrayOfString1[4]);
        vector.add(vector1);
      } 
      this.ftp.changeToParentDirectory();
      return vector;
    } catch (FileNotFoundException fileNotFoundException) {
      return null;
    } catch (IOException iOException) {
      return null;
    } 
  }
  
  public int getDirSize(String paramString1, String paramString2) throws Exception {
    int i = 0;
    this.ftp.changeWorkingDirectory(ftpLogPath);
    this.ftp.changeWorkingDirectory(paramString1);
    this.ftp.changeWorkingDirectory(paramString2);
    i = this.ftp.getSize();
    this.ftp.changeToParentDirectory();
    this.ftp.changeToParentDirectory();
    this.ftp.changeToParentDirectory();
    return i;
  }
  
  public int getDirSize(Vector paramVector) throws Exception {
    int i = 0;
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector = paramVector.get(b);
      String str1 = PathHandler.getEncodeSubDate(vector);
      String str2 = PathHandler.getEncodeSubPath(vector);
      i += getDirSize(str1, str2);
    } 
    return i;
  }
  
  public Vector getList() throws Exception {
    Vector vector = new Vector();
    String[] arrayOfString = this.ftp.getFile();
    for (byte b = 0; b < arrayOfString.length; b++)
      vector.add(arrayOfString[b]); 
    return vector;
  }
  
  public Vector getList(String paramString) throws Exception {
    this.ftp.changeWorkingDirectory(paramString);
    Vector vector = getList();
    this.ftp.changeToParentDirectory();
    return vector;
  }
  
  public boolean isConnected() {
    return this.ftp.isConnected();
  }
  
  public boolean isExist(String paramString1, String paramString2) throws Exception {
    paramString2 = paramString2 + "_-1.zip";
    this.ftp.changeWorkingDirectory(ftpLogPath);
    this.ftp.changeWorkingDirectory(paramString1);
    String[] arrayOfString = this.ftp.getFile();
    this.ftp.changeToParentDirectory();
    this.ftp.changeToParentDirectory();
    Arrays.sort((Object[])arrayOfString);
    int i = Arrays.binarySearch((Object[])arrayOfString, paramString2);
    return (i >= 0);
  }
  
  private boolean fileExistsInList(String paramString) throws Exception {
    boolean bool = false;
    Vector vector = getList();
    for (byte b = 0; b < vector.size() && !bool; b++)
      bool = ((String)vector.get(b)).equals(paramString); 
    return bool;
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      String str1 = "SA80MW01";
      byte b = 21;
      String str2 = "M05555";
      String str3 = "5555";
      com.MiTAC.TRA.ATP.connect.ConnectFTP connectFTP = new com.MiTAC.TRA.ATP.connect.ConnectFTP(str1, 21, str2, str3, "LogFiles");
      if (connectFTP.testConnectFTP(str1, 21, str2, str3)) {
        connectFTP.ftpconnect();
        System.err.println("FTP Connected.");
        Vector vector = connectFTP.getATPDirList();
        System.err.println(vector);
        System.err.println(connectFTP.getDirSize(connectFTP.getATPDirList()));
        String str4 = PathHandler.getEncodeSubDate(vector.get(0));
        String str5 = PathHandler.getEncodeSubPath(vector.get(0));
        if (vector.size() != 0) {
          System.err.println("FUNCTION TEST : \"mget\"");
          connectFTP.mget(str4, str5);
          System.err.println("    mget SUCESSFUL.");
        } else {
          System.err.println("FTP server is empty. skip function test \"mget\"");
        } 
        System.err.println("FUNCTION TEST : \"mput\"");
        connectFTP.mput("D:\\logdata\\", "20050128", "SingleGp_000513EF_EMU500SG_-----");
        System.err.println("    result:" + connectFTP.getATPDirList());
        System.err.println("FUNCTION TEST : \"rmdir\"");
        connectFTP.rmdir("20050128", "SingleGp_000513EF_EMU500SG_-----");
        System.err.println("    result:" + connectFTP.getATPDirList());
      } else {
        System.err.println("Failed to connect \"" + str1 + "\".");
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public boolean mget(String paramString1, String paramString2) throws Exception {
    paramString2 = paramString2 + ".zip";
    String[][] arrayOfString = { { paramString1, paramString2 } };
    mget(arrayOfString, new File(InitParameters.MWLogPath + "\\"));
    return true;
  }
  
  public boolean mget(String[][] paramArrayOfString, File paramFile) throws Exception {
    if (!paramFile.exists()) {
      System.out.println("Creating directiory: " + paramFile);
      paramFile.mkdirs();
    } 
    System.out.println(paramArrayOfString[0][0] + "/" + paramArrayOfString[0][1]);
    File[] arrayOfFile = this.ftp.mget(paramArrayOfString);
    ATPUnZIP aTPUnZIP = new ATPUnZIP(arrayOfFile, paramFile);
    aTPUnZIP.start();
    deleteRecursively(new File("C:\\ftptest\\"));
    return true;
  }
  
  public boolean mput(String paramString1, String paramString2, String paramString3) throws Exception {
    if (!paramString1.endsWith("\\"))
      paramString1 = paramString1 + "\\"; 
    File file = new File(paramString1 + paramString2 + "\\" + paramString3 + "\\");
    String str = "LogFiles";
    mput(file, str);
    return true;
  }
  
  public boolean mput(File paramFile, String paramString) throws Exception {
    File file = new File("c:\\ftptmp\\");
    ATPZIP aTPZIP = new ATPZIP(paramFile, file);
    aTPZIP.start();
    File[] arrayOfFile = aTPZIP.getZIPList();
    this.ftp.mput(arrayOfFile, paramString);
    deleteRecursively(file);
    return true;
  }
  
  public boolean rmdir(String paramString1, String paramString2) throws IOException, Exception {
    String str = ftpLogPath + "\\" + paramString1 + "\\" + paramString2 + "_-1.zip";
    this.ftp.delete(str);
    return true;
  }
  
  public boolean rmdir(String paramString) throws Exception {
    this.ftp.rmdir(paramString);
    return true;
  }
  
  public boolean testConnectFTP(String paramString1, int paramInt, String paramString2, String paramString3) throws Exception {
    ftpHostIp = paramString1;
    ftpPort = paramInt;
    ftpUsername = paramString2;
    ftpPassword = paramString3;
    boolean bool = ftpconnect();
    this.ftp.disconnect();
    return bool;
  }
}
