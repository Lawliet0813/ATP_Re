package Tools.DiskTools;

import com.MiTAC.TRA.ATP.Tools.StringHandler;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class CopyDir {
  private BufferedInputStream _$6598;
  
  private String _$16721;
  
  private String _$6648;
  
  private BufferedInputStream _$6597;
  
  public CopyDir(File paramFile1, File paramFile2) throws IOException, Exception {
    String str1 = paramFile1.getAbsolutePath();
    String str2 = paramFile2.getAbsolutePath();
    File file1 = new File(System.getProperty("java.io.tmpdir"), "atpcmd.txt");
    PrintWriter printWriter1 = new PrintWriter(new FileWriter(file1, false));
    printWriter1.print("DA");
    printWriter1.close();
    File file2 = File.createTempFile("script", ".bat", new File(System.getProperty("java.io.tmpdir")));
    file2.deleteOnExit();
    PrintWriter printWriter2 = new PrintWriter(new FileWriter(file2, false));
    printWriter2.print("xcopy " + str1 + "\\*.* " + str2 + " /S" + " <" + System.getProperty("java.io.tmpdir") + "atpcmd.txt");
    printWriter2.close();
    Process process = Runtime.getRuntime().exec(file2.getAbsolutePath());
    this._$6597 = new BufferedInputStream(process.getInputStream());
    this._$6648 = StringHandler.InputStreamToString(this._$6597);
    this._$6597.close();
    this._$6598 = new BufferedInputStream(process.getErrorStream());
    this._$16721 = StringHandler.InputStreamToString(this._$6598);
    this._$6598.close();
    if (getErrorMessage().length() != 0)
      throw new IOException(getErrorMessage()); 
  }
  
  public String getErrorMessage() throws Exception {
    return this._$16721;
  }
  
  public InputStream getErrorStream() {
    return this._$6598;
  }
  
  public InputStream getInputStream() {
    return this._$6597;
  }
  
  public String getMessage() throws Exception {
    return this._$6648;
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      System.err.println("Starting test...");
      System.err.println("   Coping directory...");
      com.MiTAC.TRA.ATP.Tools.DiskTools.CopyDir copyDir = new com.MiTAC.TRA.ATP.Tools.DiskTools.CopyDir(new File("D:\\LogFiles\\20040901\\01------_02------_03------_-04--"), new File("C:\\test\\"));
      System.err.println("   Directory copied.");
      System.err.println();
      System.err.println("   Runtime message.");
      System.err.println(copyDir.getMessage());
      System.err.println();
      System.err.println("End test.");
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
