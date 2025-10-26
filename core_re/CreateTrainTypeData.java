package core;

import com.MiTAC.TRA.ATP.Tools.CRC;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.StringHandler;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class CreateTrainTypeData {
  private Vector _$15914 = new Vector();
  
  private Vector _$15915 = new Vector();
  
  private Vector _$15916 = new Vector();
  
  private Vector _$15917 = new Vector();
  
  private Vector _$15918 = new Vector();
  
  private Vector _$15919 = new Vector();
  
  private ConnectDB _$1846 = new ConnectDB();
  
  private CRC _$15893;
  
  private NumberFormat _$6855;
  
  private String _$15910 = new String();
  
  private StringBuffer _$15888 = new StringBuffer();
  
  private File _$15880 = new File("D:\\NewTrainData");
  
  private File _$15924 = new File("D:\\NewTrainData\\train_type.txt");
  
  private File _$15926 = new File("D:\\NewTrainData\\train_type3.txt");
  
  private File _$15925 = new File("D:\\NewTrainData\\train_type2.txt");
  
  private StringBuffer _$15922 = new StringBuffer();
  
  private StringBuffer _$15923 = new StringBuffer();
  
  private StringBuffer _$15920 = new StringBuffer();
  
  private StringBuffer _$15921;
  
  private int _$1626;
  
  public CreateTrainTypeData() {
    _$3120();
    this._$6855 = NumberFormat.getInstance();
    this._$6855.setMaximumFractionDigits(3);
    _$6867();
    _$15927();
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
      FileOutputStream fileOutputStream = new FileOutputStream(this._$15925);
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
    if (this._$15924.exists() && str1.equals(this._$15910)) {
      i = this._$1626;
      i++;
    } 
    String str2 = StringHandler.fillLeft(Integer.toString(i), "0", 2);
    StringBuffer stringBuffer = new StringBuffer("ver:" + str1);
    stringBuffer.append(str2);
    return stringBuffer.toString();
  }
  
  private void _$15927() {
    try {
      String str = "SELECT * FROM Train_Data";
      this._$15915 = this._$1846.getData(str);
      int i = this._$15915.size();
      for (byte b1 = 0; b1 < i; b1++) {
        this._$15919 = new Vector();
        this._$15919.addElement(((Vector)this._$15915.get(b1)).get(0));
        this._$15919.addElement(((Vector)this._$15915.get(b1)).get(1));
        this._$15919.addElement(((Vector)this._$15915.get(b1)).get(2));
        this._$15919.addElement(((Vector)this._$15915.get(b1)).get(3));
        this._$15919.addElement(((Vector)this._$15915.get(b1)).get(4));
        this._$15919.addElement(((Vector)this._$15915.get(b1)).get(5));
        this._$15919.addElement(this._$6855.format(((Vector)this._$15915.get(b1)).get(6)));
        this._$15919.addElement(this._$6855.format(((Vector)this._$15915.get(b1)).get(7)));
        this._$15919.addElement(this._$6855.format(((Vector)this._$15915.get(b1)).get(8)));
        this._$15919.addElement(this._$6855.format(((Vector)this._$15915.get(b1)).get(9)));
        this._$15919.addElement(this._$6855.format(((Vector)this._$15915.get(b1)).get(10)));
        this._$15919.addElement(this._$6855.format(((Vector)this._$15915.get(b1)).get(11)));
        this._$15917.addElement(this._$15919);
      } 
      for (byte b2 = 0; b2 < this._$15917.size(); b2++) {
        this._$15923 = new StringBuffer(this._$15917.get(b2).toString().replaceAll(" ", ""));
        this._$15923 = this._$15923.delete(0, 1);
        this._$15923 = this._$15923.delete(this._$15923.length() - 1, this._$15923.length());
        this._$15922.append(this._$15923).append(",\n");
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$6867() {
    try {
      String str = "SELECT Train_Category.Train_Type AS Train_Type , Train_Data.* FROM Train_Data left join Train_Category ON Train_Category.Train_SN = Train_Data.Train_SN ";
      this._$15915 = this._$1846.getData(str);
      int i = this._$15915.size();
      for (byte b1 = 0; b1 < i; b1++) {
        this._$15916 = new Vector();
        this._$15916.addElement(((Vector)this._$15915.get(b1)).get(1));
        this._$15916.addElement(((Vector)this._$15915.get(b1)).get(0));
        this._$15916.addElement(((Vector)this._$15915.get(b1)).get(2));
        this._$15916.addElement(((Vector)this._$15915.get(b1)).get(3));
        this._$15916.addElement(((Vector)this._$15915.get(b1)).get(4));
        this._$15916.addElement(((Vector)this._$15915.get(b1)).get(5));
        this._$15916.addElement(((Vector)this._$15915.get(b1)).get(6));
        this._$15916.addElement(((Vector)this._$15915.get(b1)).get(7));
        this._$15916.addElement(((Vector)this._$15915.get(b1)).get(8));
        this._$15916.addElement(((Vector)this._$15915.get(b1)).get(9));
        this._$15916.addElement(((Vector)this._$15915.get(b1)).get(10));
        this._$15916.addElement(((Vector)this._$15915.get(b1)).get(11));
        this._$15916.addElement(((Vector)this._$15915.get(b1)).get(12));
        this._$15914.addElement(this._$15916);
      } 
      for (byte b2 = 0; b2 < this._$15914.size(); b2++) {
        this._$15921 = new StringBuffer(this._$15914.get(b2).toString().replaceAll(" ", ""));
        this._$15921 = this._$15921.delete(0, 1);
        this._$15921 = this._$15921.delete(this._$15921.length() - 1, this._$15921.length());
        this._$15920.append(this._$15921).append(",\n");
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$3120() {
    if (this._$15924.exists())
      try {
        FileReader fileReader = new FileReader(this._$15924);
        for (byte b = 0; b < 14; b++)
          this._$15888.append((char)fileReader.read()); 
        this._$15910 = this._$15888.substring(4, this._$15888.length() - 2);
        this._$1626 = Integer.parseInt(this._$15888.substring(this._$15888.length() - 2, this._$15888.length()));
        fileReader.close();
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      InitParameters.start();
      com.MiTAC.TRA.ATP.core.CreateTrainTypeData createTrainTypeData = new com.MiTAC.TRA.ATP.core.CreateTrainTypeData();
      createTrainTypeData.saveTrainTypeData();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void saveTrainTypeData() {
    try {
      if (!this._$15880.exists())
        this._$15880.createNewFile(); 
      if (!this._$15924.exists())
        this._$15924.createNewFile(); 
      this._$15920.insert(0, _$15898() + "\n");
      int i = this._$15920.length();
      char[] arrayOfChar1 = new char[i + 2];
      for (byte b1 = 0; b1 < i; b1++)
        arrayOfChar1[b1] = this._$15920.charAt(b1); 
      this._$15893 = new CRC(arrayOfChar1, arrayOfChar1.length - 2);
      this._$15920.append("crc:").append(this._$15893.getCRC());
      FileWriter fileWriter1 = new FileWriter(this._$15924);
      fileWriter1.write(this._$15920.toString());
      fileWriter1.close();
      _$15899(this._$15920.toString());
      this._$15922.insert(0, _$15898() + "\n");
      int j = this._$15922.length();
      char[] arrayOfChar2 = new char[j + 2];
      for (byte b2 = 0; b2 < j; b2++)
        arrayOfChar2[b2] = this._$15922.charAt(b2); 
      this._$15893 = new CRC(arrayOfChar2, arrayOfChar2.length - 2);
      this._$15922.append("crc:").append(this._$15893.getCRC());
      FileWriter fileWriter2 = new FileWriter(this._$15926);
      fileWriter2.write(this._$15922.toString());
      fileWriter2.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
