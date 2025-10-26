package Tools;

import com.MiTAC.TRA.ATP.Tools.DiskTools.DeleteDir;
import com.MiTAC.TRA.ATP.Tools.RecordHandler;
import com.MiTAC.TRA.ATP.Tools.StringHandler;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbNamedPipe;

public class PathHandler {
  public static Vector getDecodePath(String paramString) throws Exception {
    RecordHandler recordHandler = new RecordHandler();
    paramString = paramString.replace('\\', '/');
    String[] arrayOfString = paramString.split("/");
    String str1 = arrayOfString[arrayOfString.length - 2];
    String str2 = arrayOfString[arrayOfString.length - 1];
    recordHandler.add(getRunningDate(str1));
    recordHandler.addVector(getDriverData(str2));
    return (Vector)recordHandler;
  }
  
  public static Vector getDriverData(String paramString) {
    Vector vector = new Vector();
    String[] arrayOfString = paramString.split("_");
    if (arrayOfString.length < 4)
      throw new ArrayIndexOutOfBoundsException("wrong length"); 
    vector.add(arrayOfString[0].replaceAll("-", "").trim());
    vector.add(arrayOfString[1].replaceAll("-", "").trim());
    vector.add(arrayOfString[2].replaceAll("-", "").trim());
    vector.add(arrayOfString[3].replaceAll("-", "").trim());
    return vector;
  }
  
  public static String getEncodePath(Vector paramVector) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("\\" + getEncodeSubDate(paramVector) + "\\");
    stringBuffer.append(getEncodeSubPath(paramVector));
    return stringBuffer.toString();
  }
  
  public static String getEncodeSubDate(Vector paramVector) {
    Date date = paramVector.get(0);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    return simpleDateFormat.format(date);
  }
  
  public static String getEncodeSubPath(Vector paramVector) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(StringHandler.fillRight(paramVector.get(1), "-", 8) + "_" + StringHandler.fillRight(paramVector.get(2), "-", 8) + "_" + StringHandler.fillRight(paramVector.get(3), "-", 8) + "_" + getEncodeVID(paramVector.get(4)));
    return stringBuffer.toString();
  }
  
  public static String getEncodeVID(String paramString) {
    StringBuffer stringBuffer = new StringBuffer();
    if (paramString.length() != 0) {
      if (Character.isLetter(paramString.charAt(0)) || paramString.charAt(0) == '-') {
        stringBuffer.append(StringHandler.fillRight(paramString, "-", 5));
      } else {
        stringBuffer.append("-");
        stringBuffer.append(StringHandler.fillRight(paramString, "-", 4));
      } 
    } else {
      stringBuffer.append(StringHandler.fillRight(paramString, "-", 5));
    } 
    return stringBuffer.toString();
  }
  
  public static Vector getPathList(String paramString) throws Exception {
    Vector vector = new Vector();
    File[] arrayOfFile = (new File(paramString)).listFiles();
    StringBuffer stringBuffer = new StringBuffer();
    for (byte b = 0; b < arrayOfFile.length; b++) {
      if (arrayOfFile[b].isDirectory()) {
        String str = arrayOfFile[b].getName();
        File[] arrayOfFile1 = (new File(paramString + "\\" + str)).listFiles();
        try {
          Date date = getRunningDate(str);
          if (arrayOfFile1.length == 0)
            DeleteDir.delDir(new File(paramString + "\\" + str)); 
          for (byte b1 = 0; b1 < arrayOfFile1.length; b1++) {
            if (arrayOfFile1[b1].isDirectory())
              try {
                Vector vector1 = new Vector();
                String str1 = arrayOfFile1[b1].getName();
                Vector vector2 = getDriverData(str1);
                vector1.add(date);
                vector1.add(vector2.get(0));
                vector1.add(vector2.get(1));
                vector1.add(vector2.get(2));
                vector1.add(vector2.get(3));
                vector.add(vector1);
              } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                stringBuffer.append(arrayOfFile1[b1].getAbsolutePath());
                stringBuffer.append("\n");
                for (byte b2 = 0; b2 < (arrayIndexOutOfBoundsException.getStackTrace()).length; b2++) {
                  stringBuffer.append("\t");
                  stringBuffer.append(arrayIndexOutOfBoundsException.getStackTrace()[b2]);
                  stringBuffer.append("\n");
                } 
                stringBuffer.append("\n");
              }  
          } 
        } catch (NumberFormatException numberFormatException) {
          numberFormatException.printStackTrace();
          stringBuffer.append(arrayOfFile[b].getAbsolutePath());
          stringBuffer.append("\n");
          for (byte b1 = 0; b1 < (numberFormatException.getStackTrace()).length; b1++) {
            stringBuffer.append("\t");
            stringBuffer.append(numberFormatException.getStackTrace()[b1]);
            stringBuffer.append("\n");
          } 
          stringBuffer.append("\n");
        } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
          stringIndexOutOfBoundsException.printStackTrace();
          stringBuffer.append(arrayOfFile[b].getAbsolutePath());
          stringBuffer.append("\n");
          for (byte b1 = 0; b1 < (stringIndexOutOfBoundsException.getStackTrace()).length; b1++) {
            stringBuffer.append("\t");
            stringBuffer.append(stringIndexOutOfBoundsException.getStackTrace()[b1]);
            stringBuffer.append("\n");
          } 
          stringBuffer.append("\n");
        } 
      } 
    } 
    if (stringBuffer.length() != 0) {
      String str = "C:\\ATPMW\\ERROR_LOG\\";
      File file = new File(str);
      if (!file.exists())
        file.mkdirs(); 
      FileWriter fileWriter = new FileWriter(str + "MWDISK_ERROR_" + System.currentTimeMillis() + ".log");
      fileWriter.write(stringBuffer.toString());
      fileWriter.close();
    } 
    return vector;
  }
  
  public static Date getRunningDate(String paramString) {
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    int i = (new Integer(paramString.substring(0, 4))).intValue();
    int j = (new Integer(paramString.substring(4, 6))).intValue();
    int k = (new Integer(paramString.substring(6, 8))).intValue();
    gregorianCalendar.set(i, j - 1, k, 0, 0, 0);
    return gregorianCalendar.getTime();
  }
  
  public static String getSmbEncodePath(Vector paramVector) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("/" + getEncodeSubDate(paramVector) + "/");
    stringBuffer.append(getEncodeSubPath(paramVector) + "/");
    return stringBuffer.toString();
  }
  
  public static Vector getSmbPathList(String paramString1, String paramString2, String paramString3) throws Exception {
    Vector vector = new Vector();
    NtlmPasswordAuthentication ntlmPasswordAuthentication = new NtlmPasswordAuthentication("", paramString2, paramString3);
    SmbNamedPipe smbNamedPipe = new SmbNamedPipe(paramString1, 1, ntlmPasswordAuthentication);
    SmbFile[] arrayOfSmbFile = smbNamedPipe.listFiles();
    for (byte b = 0; b < arrayOfSmbFile.length; b++) {
      if (arrayOfSmbFile[b].isDirectory()) {
        String str = arrayOfSmbFile[b].getName();
        Date date = getRunningDate(str);
        SmbFile[] arrayOfSmbFile1 = arrayOfSmbFile[b].listFiles();
        for (byte b1 = 0; b1 < arrayOfSmbFile1.length; b1++) {
          if (arrayOfSmbFile1[b1].isDirectory()) {
            String str1 = arrayOfSmbFile1[b1].getName();
            Vector vector1 = getDriverData(str1);
            vector.add(date);
            vector.add(vector1.get(0));
            vector.add(vector1.get(1));
            vector.add(vector1.get(2));
            vector.add(((String)vector1.get(3)).replaceAll("/", ""));
          } 
        } 
      } 
    } 
    return vector;
  }
  
  public static boolean isDirExists(String paramString) {
    return (new File(paramString)).exists();
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      System.out.println(getSmbPathList("smb://192.168.152.156/MWDisk/", "administrator", "wing"));
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
