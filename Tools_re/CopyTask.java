package Tools;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.Tools.DiskTools.CopyDir;
import com.MiTAC.TRA.ATP.Tools.DiskTools.DeleteDir;
import com.MiTAC.TRA.ATP.Tools.DiskTools.DiskName;
import com.MiTAC.TRA.ATP.Tools.DiskTools.DiskStatus;
import com.MiTAC.TRA.ATP.Tools.FtpBuffer;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import com.MiTAC.TRA.ATP.Tools.UnknowProgressMonitor;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.decoder.DecodeBuffer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CopyTask {
  public static final int CopyFromFTPtoHD = 3;
  
  public static final int CopyFromHDtoFTP = 4;
  
  public static final int CopyFromHDtoMO = 2;
  
  public static final int CopyFromUSBtoAUTO = 5;
  
  public static final int CopyFromUSBtoHD = 1;
  
  private String _$10090;
  
  public static final int FileTyep_Both = 3;
  
  public static final int FileType_ETC = 4;
  
  public static final int FileType_MMI = 2;
  
  public static final int FileType_None = 5;
  
  public static final int FileType_RU = 1;
  
  private String _$10097 = "null";
  
  private boolean _$10085 = false;
  
  private ConnectDB _$2819 = new ConnectDB();
  
  private int _$10091;
  
  private com.MiTAC.TRA.ATP.Tools.CopyTask _$7857;
  
  private int _$10083 = 0;
  
  private boolean _$6913 = true;
  
  private boolean _$10084 = false;
  
  private String _$2790;
  
  private boolean _$10086 = false;
  
  private int _$10082 = 100;
  
  private Vector _$2731;
  
  private String _$10087 = ATPMessages.getString("MW.MONITOR.PREPARING");
  
  private String _$10088 = "C:\\TEMP_ATP_FILE\\";
  
  private String _$10089;
  
  private UnknowProgressMonitor _$7886;
  
  private static String[] _$10079;
  
  static {
    try {
      ConnectDB connectDB = new ConnectDB();
      Vector vector = connectDB.getData("SELECT VehicleID FROM VehicleID");
      _$10079 = new String[vector.size()];
      for (byte b = 0; b < vector.size(); b++)
        _$10079[b] = ((Vector)vector.get(b)).get(0); 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public CopyTask(Vector paramVector, String paramString1, String paramString2, int paramInt, boolean paramBoolean) {
    this(paramVector, paramString1, paramString2, paramInt);
    this._$6913 = paramBoolean;
  }
  
  public CopyTask(Vector paramVector, String paramString1, String paramString2, int paramInt) {
    this._$2731 = paramVector;
    this._$10091 = paramInt;
    if (paramInt == 3) {
      this._$2790 = "FTP";
    } else {
      paramString1 = paramString1.endsWith("\\") ? paramString1 : (paramString1 + "\\");
      this._$2790 = paramString1;
    } 
    if (paramInt == 4) {
      this._$10089 = "FTP";
    } else {
      paramString2 = paramString2.endsWith("\\") ? paramString2 : (paramString2 + "\\");
      this._$10089 = paramString2;
    } 
    this._$7857 = this;
  }
  
  private void _$10121() throws Exception {
    if (this._$2790.equals(this._$10089))
      throw new IOException(ATPMessages.getString("MW.LT.ERROR.SAMEPATH") + "\n" + ATPMessages.getString("MW.LT.CHANGE_PATH")); 
    this._$10082 = this._$2731.size() * 2 + 6;
    this._$10083++;
    this._$10087 = ATPMessages.getString("MW.MONITOR.PROCESSING");
    Vector vector = new Vector();
    _$10139(this._$2731, this._$2790, this._$10088);
    _$10139(this._$2731, this._$10088, this._$10089);
    this._$10083++;
    this._$10087 = ATPMessages.getString("MW.DECODE.BUFFER.ADD");
    for (byte b1 = 0; b1 < this._$2731.size(); b1++) {
      Vector vector1 = this._$2731.get(b1);
      String str = this._$10088 + PathHandler.getEncodePath(vector1);
      DecodeBuffer.addTask(new File(str));
    } 
    this._$10083++;
    this._$10087 = ATPMessages.getString("MW.LT.TOFTP");
    _$10155(this._$2731, this._$10088);
    this._$10083++;
    this._$10087 = ATPMessages.getString("MW.LT.LOG");
    for (byte b2 = 0; b2 < this._$2731.size(); b2++)
      vector.add(this._$2731.get(b2)); 
    if (vector.size() != 0)
      this._$2819.UpdateMission(vector, 1); 
    this._$2819.InsertLogUploadLibrary(this._$2731, this._$2790, this._$10089);
    this._$10083 = this._$10082;
    this._$10084 = true;
  }
  
  private void _$10122() throws Exception {
    this._$10082 = this._$2731.size() + 5;
    if (this._$2790.equals(this._$10089))
      throw new IOException(ATPMessages.getString("MW.LT.ERROR.SAMEPATH") + "\n" + ATPMessages.getString("MW.LT.CHANGE_PATH")); 
    this._$10083++;
    String[][] arrayOfString = _$10128(this._$2731, this._$2790, this._$10089);
    this._$10083++;
    this._$10087 = ATPMessages.getString("MW.LT.LOG");
    if (arrayOfString.length != 0) {
      this._$2819.InsertLogBackupLibrary(this._$2731, arrayOfString);
      this._$2819.UpdateMission_Backup(this._$2731, 1);
    } 
    this._$10083 = this._$10082;
    this._$10084 = true;
  }
  
  private void _$10120() throws Exception {
    this._$10082 = this._$2731.size() + 6;
    String[] arrayOfString = new String[this._$2731.size()];
    Vector vector = new Vector();
    this._$10083++;
    this._$10087 = ATPMessages.getString("MW.MONITOR.PROCESSING");
    for (byte b1 = 0; b1 < this._$2731.size(); b1++) {
      String str = PathHandler.getEncodePath(this._$2731.get(b1));
      str = this._$10088 + str;
      DeleteDir.delDir(new File(str));
      arrayOfString[b1] = str;
    } 
    _$10139(this._$2731, this._$10088);
    this._$10083++;
    this._$10087 = ATPMessages.getString("MW.LT.LOG");
    for (byte b2 = 0; b2 < this._$2731.size(); b2++)
      vector.add(this._$2731.get(b2)); 
    if (vector.size() != 0)
      this._$2819.UpdateMission(vector, 1); 
    this._$10083++;
    this._$10087 = ATPMessages.getString("MW.LT.LOG");
    this._$2819.InsertLogUploadLibrary(this._$2731, this._$2790, this._$10089);
    this._$10083++;
    this._$10087 = ATPMessages.getString("MW.DECODE.BUFFER.ADD");
    for (byte b3 = 0; b3 < arrayOfString.length; b3++) {
      Vector vector1 = PathHandler.getDecodePath(arrayOfString[b3]);
      String str = this._$10089 + PathHandler.getEncodePath(vector1);
      DecodeBuffer.addTask(new File(str));
    } 
    this._$10083 = this._$10082;
    this._$10084 = true;
  }
  
  private void _$10119() throws Exception {
    this._$10082 = this._$2731.size() + 2;
    this._$10083++;
    _$10155(this._$2731, this._$2790);
    this._$10083++;
    this._$10087 = ATPMessages.getString("MW.LT.LOG");
    this._$2819.InsertLogUploadLibrary(this._$2731, this._$2790, this._$10089);
    this._$10083 = this._$10082;
    this._$10084 = true;
  }
  
  private void _$10123() throws Exception {
    this._$10082 = this._$2731.size() + 5;
    if (this._$2790.equals(this._$10089))
      throw new IOException(ATPMessages.getString("MW.LT.ERROR.SAMEPATH") + "\n" + ATPMessages.getString("MW.LT.CHANGE_PATH")); 
    this._$10083++;
    this._$10087 = ATPMessages.getString("MW.LT.CHECK_DISK_SPACE");
    Vector vector = new Vector();
    String str = DiskStatus.getDiskLabel();
    this._$10083++;
    this._$10087 = ATPMessages.getString("MW.MONITOR.PROCESSING");
    for (byte b1 = 0; b1 < this._$2731.size(); b1++) {
      String str1 = PathHandler.getEncodePath(this._$2731.get(b1));
      str1 = this._$10088 + str1;
      DeleteDir.delDir(new File(str1));
    } 
    _$10139(this._$2731, this._$2790, this._$10088);
    _$10139(this._$2731, this._$10088, this._$10089);
    this._$10083++;
    this._$10087 = ATPMessages.getString("MW.LT.LOG");
    for (byte b2 = 0; b2 < this._$2731.size(); b2++)
      vector.add(this._$2731.get(b2)); 
    switch (this._$10091) {
      case 1:
        if (vector.size() != 0)
          this._$2819.UpdateMission(vector, 1); 
        this._$2819.InsertLogUploadLibrary(this._$2731, this._$2790, this._$10089);
        break;
    } 
    this._$10083++;
    this._$10087 = ATPMessages.getString("MW.DECODE.BUFFER.ADD");
    if (this._$6913)
      for (byte b = 0; b < this._$2731.size(); b++) {
        Vector vector1 = this._$2731.get(b);
        String str1 = this._$10088 + PathHandler.getEncodePath(vector1);
        DecodeBuffer.addTask(new File(str1));
      }  
    this._$10083 = this._$10082;
    this._$10084 = true;
  }
  
  private boolean _$10134(String paramString1, String paramString2) throws Exception {
    boolean bool = false;
    int i = checkFileType(new File(paramString1));
    int j = checkFileType(new File(paramString2));
    try {
      if ((j == 2 || j == 1) && i == 1) {
        DeleteDir.delDir(new File(paramString2));
      } else if (j == 1) {
        bool = true;
      } 
    } catch (FileNotFoundException fileNotFoundException) {
      fileNotFoundException.printStackTrace();
    } catch (Exception exception) {
      throw exception;
    } 
    return bool;
  }
  
  public static int checkFileType(File paramFile) throws Exception {
    if (paramFile.exists()) {
      String[] arrayOfString = paramFile.list();
      if (arrayOfString.length == 0)
        return 5; 
      boolean bool1 = false;
      boolean bool2 = false;
      boolean bool3 = false;
      for (byte b = 0; b < arrayOfString.length; b++) {
        if (arrayOfString[b].endsWith("RU") || arrayOfString[b].endsWith("ru")) {
          bool1 = true;
        } else if (arrayOfString[b].endsWith("MMI") || arrayOfString[b].endsWith("mmi")) {
          bool2 = true;
        } else {
          bool3 = true;
        } 
      } 
      return bool1 ? 1 : (bool2 ? 2 : (bool3 ? 4 : 5));
    } 
    return 5;
  }
  
  private String[] _$10155(Vector paramVector, String paramString) {
    String[] arrayOfString = new String[paramVector.size()];
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector = paramVector.get(b);
      String str1 = PathHandler.getEncodeSubDate(vector);
      String str2 = PathHandler.getEncodeSubPath(vector);
      this._$10083++;
      this._$10087 = ATPMessages.getString("MW.GNL.COPY") + ": " + str1 + "\\" + str2;
      FtpBuffer.addList(new String[] { paramString, str1, str2 });
      arrayOfString[b] = paramString + "\\" + str1 + "\\" + str2;
    } 
    return arrayOfString;
  }
  
  private String[][] _$10128(Vector paramVector, String paramString1, String paramString2) {
    do {
    
    } while (!_$10130(paramString2));
    String[][] arrayOfString = new String[paramVector.size()][2];
    this._$10097 = DiskStatus.getDiskLabel();
    File file = new File(paramString2);
    if (!file.exists())
      file.mkdir(); 
    for (byte b = 0; b < paramVector.size(); b++) {
      String str1 = PathHandler.getEncodePath(paramVector.get(b));
      String str2 = paramString1 + str1;
      String str3 = file.getAbsolutePath() + str1;
      try {
        this._$10087 = ATPMessages.getString("MW.GNL.COPY") + ": " + str1;
        if (!_$10134(str2, str3))
          CopyDir copyDir = new CopyDir(new File(str2), new File(str3)); 
        arrayOfString[b][0] = str3;
        arrayOfString[b][1] = this._$10097;
        this._$10083++;
      } catch (IOException iOException) {
        iOException.printStackTrace();
        JOptionPane.showMessageDialog(null, iOException.getMessage() + ", " + ATPMessages.getString("MW.LB.MO.CHANGE"), ATPMessages.getString("MW.LB.MO.CHANGE"), 2);
        do {
        
        } while (!_$10130(paramString2));
        b--;
        try {
          DiskStatus.getDiskStatus(new File(InitParameters.MOPath));
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
        this._$10097 = DiskStatus.getDiskLabel();
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } 
    return arrayOfString;
  }
  
  private String[] _$10139(Vector paramVector, String paramString) {
    String[] arrayOfString = new String[paramVector.size()];
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector = paramVector.get(b);
      String str1 = PathHandler.getEncodeSubDate(vector);
      String str2 = PathHandler.getEncodeSubPath(vector);
      this._$10083++;
      this._$10087 = ATPMessages.getString("MW.GNL.COPY") + ": " + str1 + "\\" + str2;
      FtpBuffer.addList(new String[] { str1, str2 });
      arrayOfString[b] = this._$10089 + str1 + "\\" + str2;
    } 
    return arrayOfString;
  }
  
  private String[][] _$10139(Vector paramVector, String paramString1, String paramString2) {
    String[][] arrayOfString = new String[paramVector.size()][2];
    File file = new File(paramString2);
    if (!file.exists())
      file.mkdir(); 
    for (byte b = 0; b < paramVector.size(); b++) {
      String str1 = PathHandler.getEncodePath(paramVector.get(b));
      String str2 = paramString1 + str1;
      String str3 = file.getAbsolutePath() + str1;
      this._$10087 = ATPMessages.getString("MW.GNL.CHECK") + ": " + str1;
      try {
        this._$10087 = ATPMessages.getString("MW.GNL.COPY") + ": " + str1;
        if (!_$10134(str2, str3))
          CopyDir copyDir = new CopyDir(new File(str2), new File(str3)); 
        arrayOfString[b][0] = str3;
        this._$10083++;
      } catch (IOException iOException) {
        iOException.printStackTrace();
        if (iOException.getMessage().startsWith("找不到檔案")) {
          JOptionPane.showMessageDialog(null, "隨身碟有無法刪除的資料夾, 請在檔案傳輸完成後試著格式化司機員隨身碟.");
        } else {
          JOptionPane.showMessageDialog(null, iOException.getMessage() + "\nfrom:" + str2 + "\nto:" + str3);
        } 
        str3 = iOException.getMessage();
      } catch (Exception exception) {
        exception.printStackTrace();
        str3 = exception.getMessage();
      } 
    } 
    return arrayOfString;
  }
  
  private JPanel _$10110(Vector paramVector) {
    byte b1;
    byte b2;
    JPanel jPanel = new JPanel();
    jPanel.setLayout(new BorderLayout());
    JLabel jLabel1 = new JLabel();
    JTextArea jTextArea = new JTextArea();
    jTextArea.setEditable(false);
    JScrollPane jScrollPane = new JScrollPane(jTextArea);
    jScrollPane.setPreferredSize(new Dimension(500, 200));
    JLabel jLabel2 = new JLabel(ATPMessages.getString("MW.LT.TRANSFER.EXIST.RESENDORNOT"));
    jPanel.add(jLabel1, "North");
    jPanel.add(jScrollPane, "Center");
    jPanel.add(jLabel2, "South");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    StringBuffer stringBuffer = new StringBuffer();
    switch (this._$10091) {
      case 1:
      case 3:
      case 5:
        jLabel1.setText(ATPMessages.getString("MW.LT.TRANSFER.SENDED") + ":");
        stringBuffer.append(_$10115(ATPMessages.getString("MW.GNL.DATE")) + "\t" + _$10115(ATPMessages.getString("MW.WS.ID")) + "\t" + _$10115(ATPMessages.getString("MW.TR.ID")) + "\t" + _$10115(ATPMessages.getString("MW.DRIVER.ID")) + "\t" + _$10115(ATPMessages.getString("MW.VEHICLE.ID")) + "\t" + _$10115(ATPMessages.getString("MW.LT.DATA_SOURCE")) + "\t" + _$10115(ATPMessages.getString("MW.LT.DATA_DESTINATION")) + "\t" + _$10115(ATPMessages.getString("MW.LI.TRANSPORT.DATE")) + "\n");
        for (b1 = 0; b1 < paramVector.size(); b1++) {
          try {
            Vector vector = paramVector.get(b1);
            stringBuffer.append(_$10115(simpleDateFormat.format(vector.get(0))) + "\t" + _$10115(vector.get(1).toString()) + "\t" + _$10115(vector.get(2).toString()) + "\t" + _$10115(vector.get(3).toString()) + "\t" + _$10115(vector.get(4).toString()) + "\t" + _$10115(vector.get(5).toString()) + "\t" + _$10115(vector.get(6).toString()) + "\t" + _$10115(simpleDateFormat.format(vector.get(7))) + "\n");
          } catch (Exception exception) {
            exception.printStackTrace();
          } 
        } 
        break;
      case 2:
        jLabel1.setText(ATPMessages.getString("MW.LB.MO.SENDED"));
        stringBuffer.append(_$10115(ATPMessages.getString("MW.GNL.DATE")) + "\t" + _$10115(ATPMessages.getString("MW.WS.ID")) + "\t" + _$10115(ATPMessages.getString("MW.TR.ID")) + "\t" + _$10115(ATPMessages.getString("MW.DRIVER.ID")) + "\t" + _$10115(ATPMessages.getString("MW.VEHICLE.ID")) + "\t" + _$10115(ATPMessages.getString("MW.LB.MO.LABEL")) + "\t" + _$10115(ATPMessages.getString("MW.LI.BACKUP.DATE")) + "\n");
        for (b2 = 0; b2 < paramVector.size(); b2++) {
          try {
            Vector vector = paramVector.get(b2);
            stringBuffer.append(_$10115(simpleDateFormat.format(vector.get(0))) + "\t" + _$10115(vector.get(1).toString()) + "\t" + _$10115(vector.get(2).toString()) + "\t" + _$10115(vector.get(3).toString()) + "\t" + _$10115(vector.get(4).toString()) + "\t" + _$10115(vector.get(5).toString()) + "\t" + _$10115(simpleDateFormat.format(vector.get(6))) + "\n");
          } catch (Exception exception) {
            exception.printStackTrace();
          } 
        } 
        break;
    } 
    jTextArea.setText(stringBuffer.toString());
    return jPanel;
  }
  
  private String _$10115(String paramString) {
    StringBuffer stringBuffer = new StringBuffer(paramString);
    for (int i = paramString.length(); i < 10; i++)
      stringBuffer.append(" "); 
    return stringBuffer.toString();
  }
  
  public int getCurrent() {
    return this._$10083;
  }
  
  public int getLengthOfTask() {
    return this._$10082;
  }
  
  public String getMOLabel() {
    return this._$10097;
  }
  
  public String getMessage() {
    return this._$10087;
  }
  
  public void go() throws Exception {
    this._$10083 = 0;
    this._$10084 = false;
    this._$10085 = false;
    this._$10087 = "";
    switch (this._$10091) {
      case 4:
        _$10119();
        return;
      case 3:
        _$10120();
        return;
      case 5:
        _$10121();
        return;
      case 2:
        _$10122();
        return;
    } 
    _$10123();
  }
  
  public boolean isCanceled() {
    return this._$10085;
  }
  
  public boolean isDone() {
    return this._$10084;
  }
  
  private boolean _$10130(String paramString) {
    boolean bool = false;
    try {
      DiskStatus.getDiskStatus(new File(InitParameters.MOPath));
      String str = DiskStatus.getDiskLabel();
      System.out.println(str);
      if (DiskName.checkDiskName(str)) {
        System.out.println("found label");
        if (DiskName.check2YearsOld(str)) {
          int i = JOptionPane.showConfirmDialog(null, "MO光碟已經超過兩年保用期限, 是否隔式化MO光碟?", ATPMessages.getString("MW.GNL.ASK"), 0);
          if (i == 0) {
            try {
              int j = JOptionPane.showConfirmDialog(null, ATPMessages.getString("MW.LB.MO.FORMAT.Q.CONFIRM"), ATPMessages.getString("MW.GNL.ASK"), 0);
              if (j == 0) {
                this._$7886 = new UnknowProgressMonitor(null, ATPMessages.getString("MW.LB.MO.FORMATTING"), "", 0, 100);
                Object object = new Object(this);
                object.start();
                this._$7886.show();
                bool = true;
              } 
            } catch (Exception exception) {
              exception.printStackTrace();
              JOptionPane.showMessageDialog(null, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
            } 
          } else {
            return true;
          } 
        } else {
          bool = true;
        } 
      } else {
        System.out.println("no label");
        int i = JOptionPane.showConfirmDialog(null, ATPMessages.getString("MW.LB.MO.NOT_ATP_FORMAT") + "\n" + ATPMessages.getString("MW.LB.MO.FORMAT.Q.CONFIRM"), ATPMessages.getString("MW.GNL.ASK"), 0);
        if (i == 0)
          try {
            int j = JOptionPane.showConfirmDialog(null, ATPMessages.getString("MW.LB.MO.FORMAT.Q.CONFIRM"), ATPMessages.getString("MW.GNL.ASK"), 0);
            if (j == 0) {
              this._$7886 = new UnknowProgressMonitor(null, ATPMessages.getString("MW.LB.MO.FORMATTING"), "", 0, 100);
              Object object = new Object(this);
              object.start();
              this._$7886.show();
              bool = true;
            } 
          } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(null, exception.getMessage(), ATPMessages.getString("MW.GNL.ERROR"), 0);
          }  
      } 
    } catch (IOException iOException) {
      iOException.printStackTrace();
      JOptionPane.showMessageDialog(null, iOException.getMessage(), "", 0);
    } catch (Exception exception) {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(null, exception.getMessage(), "", 0);
    } finally {
      return bool;
    } 
    while (true);
  }
  
  public void stop() {
    this._$10085 = true;
    this._$10087 = null;
  }
}
