package core;

import com.MiTAC.TRA.ATP.Tools.CRC;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.StringHandler;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class CreateTrainCategoryData {
  private Vector _$15875 = new Vector();
  
  private Vector _$15876 = new Vector();
  
  private Vector _$15877 = new Vector();
  
  private Vector _$15878 = new Vector();
  
  private Vector _$15879 = new Vector();
  
  private BufferedReader _$15894 = null;
  
  private ConnectDB _$1846 = new ConnectDB();
  
  private CRC _$15893;
  
  private FileReader _$2483 = null;
  
  private StringBuffer _$15890 = new StringBuffer();
  
  private StringBuffer _$15888 = new StringBuffer();
  
  private String _$15892 = new String();
  
  private String _$15891 = new String();
  
  private File _$15881 = new File("D:\\NewTrainData\\Category.txt");
  
  private File _$15883 = new File("D:\\NewTrainData\\Category3.txt");
  
  private File _$15882 = new File("D:\\NewTrainData\\Category2.txt");
  
  private File _$15880 = new File("D:\\NewTrainData");
  
  private StringBuffer _$15886 = new StringBuffer();
  
  private StringBuffer _$15887 = new StringBuffer();
  
  private StringBuffer _$15889 = new StringBuffer();
  
  private StringBuffer _$15884 = new StringBuffer();
  
  private StringBuffer _$15885;
  
  private int _$1626;
  
  public CreateTrainCategoryData() {
    _$15895();
    _$15896();
    _$6868();
    _$15897();
  }
  
  private void _$15899(String paramString) {
    try {
      String str1 = paramString;
      String str2 = "";
      int i = str1.length();
      byte[] arrayOfByte1 = new byte[i];
      byte[] arrayOfByte2 = new byte[arrayOfByte1.length];
      for (byte b1 = 0; b1 < arrayOfByte1.length; b1++)
        arrayOfByte1[b1] = (byte)str1.charAt(b1); 
      for (byte b2 = 0; b2 < arrayOfByte1.length; b2++)
        arrayOfByte2[b2] = (byte)(arrayOfByte1[b2] ^ 0xFFFFFFFF); 
      FileOutputStream fileOutputStream = new FileOutputStream(this._$15882);
      for (byte b3 = 0; b3 < arrayOfByte2.length; b3++)
        fileOutputStream.write(arrayOfByte2[b3]); 
      fileOutputStream.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private String _$15898() {
    int i = 1;
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    String str1 = simpleDateFormat.format(date);
    if (this._$15881.exists() && str1.equals(this._$15892)) {
      i = this._$1626;
      i++;
    } 
    String str2 = StringHandler.fillLeft(Integer.toString(i), "0", 2);
    StringBuffer stringBuffer = new StringBuffer("ver:" + str1);
    stringBuffer.append(str2);
    return stringBuffer.toString();
  }
  
  private void _$15897() {
    try {
      int i = this._$15876.size();
      for (byte b1 = 0; b1 < i; b1++) {
        this._$15879 = new Vector();
        this._$15879.addElement(((Vector)this._$15876.get(b1)).get(0).toString());
        this._$15879.addElement(((Vector)this._$15876.get(b1)).get(1).toString());
        this._$15879.addElement(((Vector)this._$15876.get(b1)).get(2).toString());
        this._$15879.addElement(((Vector)this._$15876.get(b1)).get(3).toString());
        if (((Vector)this._$15876.get(b1)).get(4).toString().equals("false")) {
          this._$15879.addElement("0");
        } else {
          this._$15879.addElement("1");
        } 
        this._$15879.addElement(((Vector)this._$15876.get(b1)).get(5).toString());
        this._$15879.addElement(((Vector)this._$15876.get(b1)).get(6).toString());
        this._$15879.addElement(((Vector)this._$15876.get(b1)).get(7).toString());
        this._$15879.addElement(((Vector)this._$15876.get(b1)).get(8).toString());
        this._$15879.addElement(((Vector)this._$15876.get(b1)).get(9).toString());
        this._$15879.addElement(((Vector)this._$15876.get(b1)).get(10).toString());
        this._$15879.addElement(((Vector)this._$15876.get(b1)).get(11).toString());
        this._$15878.addElement(this._$15879);
      } 
      for (byte b2 = 0; b2 < this._$15878.size(); b2++) {
        this._$15887 = new StringBuffer(this._$15878.get(b2).toString().replaceAll(" ", ""));
        this._$15887 = this._$15887.delete(0, 1);
        this._$15887 = this._$15887.delete(this._$15887.length() - 1, this._$15887.length());
        this._$15886.append(this._$15887).append(",\n");
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$15895() {
    try {
      if (this._$15881.exists()) {
        FileReader fileReader = new FileReader(this._$15881);
        for (byte b = 0; b < 14; b++)
          this._$15888.append((char)fileReader.read()); 
        this._$15892 = this._$15888.substring(4, this._$15888.length() - 2);
        this._$1626 = Integer.parseInt(this._$15888.substring(this._$15888.length() - 2, this._$15888.length()));
        fileReader.close();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$15896() {
    try {
      String str = "SELECT * FROM Train_Type";
      this._$15876 = this._$1846.getData(str);
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private String _$15902() throws Exception {
    this._$2483 = new FileReader(this._$15881);
    this._$15894 = new BufferedReader(this._$2483);
    String str;
    while ((str = this._$15894.readLine()) != null)
      this._$15890.append(str); 
    this._$15894.close();
    this._$2483.close();
    return this._$15890.toString();
  }
  
  private void _$15903() throws Exception {
    this._$15892 = _$15902().substring(4, 14).toString();
  }
  
  private String _$15904() {
    this._$15891 = this._$15892.substring(0, 14);
    this._$15891 = this._$15891.substring(this._$15891.length() - 8, this._$15891.length());
    return this._$15891.toString();
  }
  
  private void _$6868() {
    try {
      int i = this._$15876.size();
      for (byte b1 = 0; b1 < i; b1++) {
        this._$15877 = new Vector();
        this._$15877.addElement(((Vector)this._$15876.get(b1)).get(0).toString());
        this._$15877.addElement(((Vector)this._$15876.get(b1)).get(2).toString());
        this._$15877.addElement(((Vector)this._$15876.get(b1)).get(3).toString());
        if (((Vector)this._$15876.get(b1)).get(4).toString().equals("false")) {
          this._$15877.addElement("0");
        } else {
          this._$15877.addElement("1");
        } 
        this._$15877.addElement(((Vector)this._$15876.get(b1)).get(5).toString());
        this._$15877.addElement(((Vector)this._$15876.get(b1)).get(6).toString());
        this._$15877.addElement(((Vector)this._$15876.get(b1)).get(7).toString());
        this._$15877.addElement(((Vector)this._$15876.get(b1)).get(8).toString());
        this._$15877.addElement(((Vector)this._$15876.get(b1)).get(9).toString());
        this._$15877.addElement(((Vector)this._$15876.get(b1)).get(10).toString());
        this._$15877.addElement(((Vector)this._$15876.get(b1)).get(11).toString());
        this._$15875.addElement(this._$15877);
      } 
      for (byte b2 = 0; b2 < this._$15875.size(); b2++) {
        this._$15885 = new StringBuffer(this._$15875.get(b2).toString().replaceAll(" ", ""));
        this._$15885 = this._$15885.delete(0, 1);
        this._$15885 = this._$15885.delete(this._$15885.length() - 1, this._$15885.length());
        this._$15884.append(this._$15885).append(",\n");
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      InitParameters.start();
      com.MiTAC.TRA.ATP.core.CreateTrainCategoryData createTrainCategoryData = new com.MiTAC.TRA.ATP.core.CreateTrainCategoryData();
      createTrainCategoryData.saveTrainCategoryData();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void saveTrainCategoryData() {
    try {
      if (!this._$15880.exists())
        this._$15880.createNewFile(); 
      if (!this._$15881.exists())
        this._$15881.createNewFile(); 
      this._$15884.insert(0, _$15898() + "\n");
      int i = this._$15884.length();
      char[] arrayOfChar1 = new char[i + 2];
      for (byte b1 = 0; b1 < i; b1++)
        arrayOfChar1[b1] = this._$15884.charAt(b1); 
      this._$15893 = new CRC(arrayOfChar1, arrayOfChar1.length - 2);
      this._$15884.append("crc:").append(this._$15893.getCRC());
      FileWriter fileWriter1 = new FileWriter(this._$15881);
      fileWriter1.write(this._$15884.toString());
      fileWriter1.close();
      _$15899(this._$15884.toString());
      this._$15886.insert(0, _$15898() + "\n");
      int j = this._$15886.length();
      char[] arrayOfChar2 = new char[j + 2];
      for (byte b2 = 0; b2 < j; b2++)
        arrayOfChar2[b2] = this._$15886.charAt(b2); 
      this._$15893 = new CRC(arrayOfChar2, arrayOfChar2.length - 2);
      this._$15886.append("crc:").append(this._$15893.getCRC());
      FileWriter fileWriter2 = new FileWriter(this._$15883);
      fileWriter2.write(this._$15886.toString());
      fileWriter2.close();
      _$15902();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
