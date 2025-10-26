package core;

import com.MiTAC.TRA.ATP.Tools.CRC;
import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.StringHandler;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class CreateTrainDataMapping {
  private Vector _$6983 = new Vector();
  
  private Vector _$15906 = new Vector();
  
  private Vector _$15907;
  
  private ConnectDB _$1846 = new ConnectDB();
  
  private CRC _$15893;
  
  private String _$15910 = new String();
  
  private StringBuffer _$15888 = new StringBuffer();
  
  private File _$15880 = new File("D:\\NewTrainData");
  
  private File _$15814 = new File("D:\\NewTrainData\\TrainDataMapping.txt");
  
  private StringBuffer _$15908 = new StringBuffer();
  
  private StringBuffer _$15909;
  
  private int _$1626;
  
  public CreateTrainDataMapping() {
    _$3120();
    _$15911();
  }
  
  private String _$15898() {
    int i = 1;
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    String str1 = simpleDateFormat.format(date);
    if (this._$15814.exists() && str1.equals(this._$15910)) {
      i = this._$1626;
      i++;
    } 
    String str2 = StringHandler.fillLeft(Integer.toString(i), "0", 2);
    StringBuffer stringBuffer = new StringBuffer("ver:" + str1);
    stringBuffer.append(str2);
    return stringBuffer.toString();
  }
  
  private void _$15911() {
    try {
      String str = "SELECT * FROM Train_Category";
      this._$15906 = this._$1846.getData(str);
      for (byte b1 = 0; b1 < this._$15906.size(); b1++) {
        this._$15907 = new Vector();
        this._$15907.addElement(((Vector)this._$15906.get(b1)).get(0).toString());
        this._$15907.addElement(((Vector)this._$15906.get(b1)).get(1).toString());
        this._$6983.addElement(this._$15907);
      } 
      for (byte b2 = 0; b2 < this._$6983.size(); b2++) {
        this._$15909 = new StringBuffer(this._$6983.get(b2).toString().replaceAll(" ", ""));
        this._$15909 = this._$15909.delete(0, 1);
        this._$15909 = this._$15909.delete(this._$15909.length() - 1, this._$15909.length());
        this._$15908.append(this._$15909).append(",\n");
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$3120() {
    try {
      if (this._$15814.exists()) {
        FileReader fileReader = new FileReader(this._$15814);
        for (byte b = 0; b < 14; b++)
          this._$15888.append((char)fileReader.read()); 
        this._$15910 = this._$15888.substring(4, this._$15888.length() - 2);
        this._$1626 = Integer.parseInt(this._$15888.substring(this._$15888.length() - 2, this._$15888.length()));
        fileReader.close();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      InitParameters.start();
      com.MiTAC.TRA.ATP.core.CreateTrainDataMapping createTrainDataMapping = new com.MiTAC.TRA.ATP.core.CreateTrainDataMapping();
      createTrainDataMapping.saveTrainDataMapping();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void saveTrainDataMapping() {
    try {
      if (!this._$15880.exists())
        this._$15880.createNewFile(); 
      if (!this._$15814.exists())
        this._$15814.createNewFile(); 
      this._$15908.insert(0, _$15898() + "\n");
      int i = this._$15908.length();
      char[] arrayOfChar = new char[i + 2];
      for (byte b = 0; b < i; b++)
        arrayOfChar[b] = this._$15908.charAt(b); 
      this._$15893 = new CRC(arrayOfChar, arrayOfChar.length - 2);
      this._$15908.append("crc:").append(this._$15893.getCRC());
      FileWriter fileWriter = new FileWriter(this._$15814);
      fileWriter.write(this._$15908.toString());
      fileWriter.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
