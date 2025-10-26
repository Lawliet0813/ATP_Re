package Tools.DataBackup;

import com.MiTAC.TRA.ATP.Tools.DataBackup.TableList;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class ExportTableToText {
  private ConnectDB _$10443 = new ConnectDB();
  
  private String _$10445 = "@##@";
  
  private String _$10444 = " @#@ ";
  
  private Vector _$10446;
  
  public ExportTableToText() {
    try {
      Vector vector = new Vector();
      _$10447();
      for (byte b = 0; b < TableList.list.length; b++) {
        String str = TableList.list[b];
        System.out.println("Exprot table: " + str);
        vector.clear();
        vector = this._$10443.getData("SELECT * FROM " + str);
        _$10450(vector, str);
      } 
    } catch (SQLException sQLException) {
      sQLException.printStackTrace();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$10447() {
    File file = new File("c:\\ATPMW\\DB\\DBBackup\\");
    if (!file.exists())
      file.mkdir(); 
  }
  
  private Vector _$10455() {
    try {
      FileReader fileReader = new FileReader("C:\\ATPMW\\DB\\TableNameList.txt");
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      StringBuffer stringBuffer1 = new StringBuffer();
      StringBuffer stringBuffer2 = new StringBuffer();
      this._$10446 = new Vector();
      int i;
      while ((i = bufferedReader.read()) != -1)
        stringBuffer2.append((char)i); 
      for (byte b = 0; b < stringBuffer2.length(); b++) {
        if (stringBuffer2.charAt(b) != ',') {
          stringBuffer1.append(stringBuffer2.charAt(b));
        } else {
          this._$10446.addElement(stringBuffer1.toString().replaceAll(" ", ""));
          stringBuffer1 = new StringBuffer();
        } 
      } 
      fileReader.close();
      bufferedReader.close();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return this._$10446;
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      com.MiTAC.TRA.ATP.Tools.DataBackup.ExportTableToText exportTableToText = new com.MiTAC.TRA.ATP.Tools.DataBackup.ExportTableToText();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void _$10450(Vector paramVector, String paramString) throws Exception {
    File file = new File("c:\\ATPMW\\DB\\DBBackup\\" + paramString + ".dlog");
    file.createNewFile();
    if (paramVector.size() != 0) {
      StringBuffer stringBuffer = new StringBuffer();
      for (byte b = 0; b < paramVector.size(); b++) {
        Vector vector = paramVector.get(b);
        for (byte b1 = 0; b1 < vector.size(); b1++) {
          try {
            if (vector.get(b1).getClass().toString().equals(Date.class.toString())) {
              stringBuffer.append(((Date)vector.get(b1)).getTime() + "");
            } else {
              stringBuffer.append(vector.get(b1).toString());
            } 
          } catch (NullPointerException nullPointerException) {
            stringBuffer.append("");
          } 
          if (b1 == vector.size() - 1) {
            stringBuffer.append(this._$10445);
          } else {
            stringBuffer.append(this._$10444);
          } 
        } 
      } 
      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(stringBuffer.toString());
      fileWriter.close();
    } 
  }
}
