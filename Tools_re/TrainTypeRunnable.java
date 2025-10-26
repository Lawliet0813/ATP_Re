package Tools;

import com.MiTAC.TRA.ATP.Tools.CRC;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.StringHandler;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TrainTypeRunnable {
  ConnectDB conDB = new ConnectDB();
  
  private DateFormat _$1805 = new SimpleDateFormat("yyyyMMdd");
  
  private Calendar _$17202 = new GregorianCalendar();
  
  private Calendar _$17201 = new GregorianCalendar();
  
  private boolean _$1639 = false;
  
  String fileTableA = InitParameters.USBPath;
  
  private boolean _$16799 = false;
  
  private FileReader _$17231;
  
  private FileReader _$17230;
  
  private FileReader _$17229;
  
  private FileReader _$17228;
  
  private File _$17227 = new File("c:\\ATPMW\\UpdateTrainData\\train_type2.txt");
  
  private File _$17226 = new File(InitParameters.USBPath + "train_type2.txt");
  
  private String _$17206;
  
  private String _$17205;
  
  private File _$17225 = new File("c:\\ATPMW\\UpdateTrainData\\train_type.txt");
  
  private File _$17224 = new File(InitParameters.USBPath + "train_type.txt");
  
  private int _$17204;
  
  private StringBuffer _$17208 = new StringBuffer();
  
  private int _$17203;
  
  private StringBuffer _$17207 = new StringBuffer();
  
  public TrainTypeRunnable() {
    run();
  }
  
  private void _$17213() {
    try {
      this._$17229 = new FileReader(this._$17225);
      FileWriter fileWriter1 = new FileWriter(this._$17224);
      int i;
      while ((i = this._$17229.read()) != -1)
        fileWriter1.write(i); 
      this._$17229.close();
      fileWriter1.close();
      this._$17231 = new FileReader(this._$17227);
      FileWriter fileWriter2 = new FileWriter(this._$17226);
      int j;
      while ((j = this._$17231.read()) != -1)
        fileWriter2.write(j); 
      this._$17231.close();
      fileWriter2.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$17081(String paramString1, String paramString2) throws IOException {
    String str1 = paramString1;
    String str2 = "";
    int i = str1.length();
    byte[] arrayOfByte1 = new byte[i];
    byte[] arrayOfByte2 = new byte[arrayOfByte1.length];
    for (byte b1 = 0; b1 < arrayOfByte1.length; b1++)
      arrayOfByte1[b1] = (byte)str1.charAt(b1); 
    for (byte b2 = 0; b2 < arrayOfByte1.length; b2++)
      arrayOfByte2[b2] = (byte)(arrayOfByte1[b2] ^ 0xFFFFFFFF); 
    FileOutputStream fileOutputStream = new FileOutputStream(this.fileTableA + paramString2);
    for (byte b3 = 0; b3 < arrayOfByte2.length; b3++)
      fileOutputStream.write(arrayOfByte2[b3]); 
    fileOutputStream.close();
  }
  
  private String _$17080(String paramString) {
    String str1 = "";
    String str2 = paramString;
    try {
      int i = str2.length();
      char[] arrayOfChar = new char[i + 2];
      for (byte b = 0; b < i; b++)
        arrayOfChar[b] = str2.charAt(b); 
      CRC cRC = new CRC(arrayOfChar, arrayOfChar.length - 2);
      str1 = str1 + "crc:" + cRC.getCRC();
      str2 = str2 + str1;
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
    } 
    return str2;
  }
  
  private int _$17220(String paramString) {
    String str = paramString;
    int i = 0;
    if (str.substring(0, 3).equals("ver")) {
      i = Integer.parseInt(str.substring(4, 14));
    } else {
      i = 0;
    } 
    return i;
  }
  
  private String _$17216(int paramInt) {
    int i = 1;
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    String str1 = simpleDateFormat.format(date);
    String str2 = String.valueOf(paramInt);
    if (paramInt == 0) {
      i = 1;
    } else {
      String str = str2.substring(0, 8);
      int j = Integer.parseInt(str2.substring(8, str2.length()));
      if (!str1.equals(str)) {
        i = 1;
      } else {
        i = j;
        i++;
      } 
    } 
    String str3 = StringHandler.fillLeft(Integer.toString(i), "0", 2);
    StringBuffer stringBuffer = new StringBuffer("ver:" + str1);
    stringBuffer.append(str3);
    this._$16799 = true;
    return stringBuffer.toString();
  }
  
  public boolean isDone() {
    return this._$1639;
  }
  
  public boolean isNewData() {
    return this._$16799;
  }
  
  private String _$17036(String paramString) throws Exception {
    boolean bool = false;
    String str = "";
    File file = new File(paramString);
    FileInputStream fileInputStream = new FileInputStream(file);
    str = StringHandler.InputStreamToString(fileInputStream);
    str = str.substring(0, str.length() - 8);
    str = str.replaceAll("\n", "");
    fileInputStream.close();
    return str;
  }
  
  private String _$17221(String paramString) {
    null = paramString;
    return null.substring(14, null.length());
  }
  
  public void run() {
    try {
      if (this._$17224.exists()) {
        this._$17228 = new FileReader(this._$17224);
        for (byte b1 = 0; b1 < 14; b1++)
          this._$17207.append((char)this._$17228.read()); 
        this._$17201.setTime(this._$1805.parse(this._$17207.substring(4, this._$17207.length() - 2)));
        this._$17203 = Integer.parseInt(this._$17207.substring(this._$17207.length() - 2, this._$17207.length()));
        this._$17228.close();
        this._$17229 = new FileReader(this._$17225);
        for (byte b2 = 0; b2 < 14; b2++)
          this._$17208.append((char)this._$17229.read()); 
        this._$17202.setTime(this._$1805.parse(this._$17208.substring(4, this._$17208.length() - 2)));
        this._$17204 = Integer.parseInt(this._$17208.substring(this._$17208.length() - 2, this._$17208.length()));
        this._$17229.close();
        if (this._$1805.parse(this._$17207.substring(4, this._$17207.length() - 2)).before(this._$1805.parse(this._$17208.substring(4, this._$17208.length() - 2)))) {
          _$17213();
          this._$1639 = true;
        } 
        if (this._$1805.parse(this._$17207.substring(4, this._$17207.length() - 2)).equals(this._$1805.parse(this._$17208.substring(4, this._$17208.length() - 2)))) {
          if (this._$17204 > this._$17203) {
            _$17213();
            this._$1639 = true;
          } else {
            this._$1639 = true;
          } 
        } else {
          this._$1639 = true;
        } 
      } else {
        _$17213();
        this._$1639 = true;
        System.out.println("run to traintype");
      } 
    } catch (IOException iOException) {
      System.err.println(iOException.getMessage());
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
