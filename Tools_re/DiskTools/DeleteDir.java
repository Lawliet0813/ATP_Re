package Tools.DiskTools;

import com.MiTAC.TRA.ATP.Tools.DiskTools.CopyDir;
import com.MiTAC.TRA.ATP.Tools.StringHandler;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class DeleteDir {
  private static BufferedInputStream _$6598;
  
  private static String _$6599;
  
  private static String _$2040;
  
  private static BufferedInputStream _$6597;
  
  public static final void delDir(File paramFile) throws IllegalArgumentException, IllegalStateException, IOException, Exception {
    if (System.getProperty("os.name").startsWith("Windows")) {
      _$11455(paramFile);
    } else {
      throw new IllegalStateException("本系統尚不支援此作業系統: " + System.getProperty("os.name"));
    } 
  }
  
  private static final void _$11455(File paramFile) throws FileNotFoundException, IllegalStateException, IOException, Exception {
    System.err.println("deleting file: " + paramFile);
    String str = paramFile.getAbsolutePath();
    File file = File.createTempFile("script", ".bat", new File(System.getProperty("java.io.tmpdir")));
    file.deleteOnExit();
    PrintWriter printWriter = new PrintWriter(new FileWriter(file, false));
    printWriter.print("rmdir " + str + " /S /Q");
    printWriter.close();
    Process process = Runtime.getRuntime().exec(file.getAbsolutePath());
    _$6597 = new BufferedInputStream(process.getInputStream());
    _$6598 = new BufferedInputStream(process.getErrorStream());
    _$2040 = StringHandler.InputStreamToString(_$6597);
    _$6597.close();
    _$6599 = StringHandler.InputStreamToString(_$6598);
    _$6598.close();
    if (_$6599.length() == 0 || _$6599.startsWith("系統找不到指定的路徑。") || _$6599.startsWith("系統找不到指定的檔案。"));
    System.err.println(" file deleted: " + paramFile);
  }
  
  public static String getErrorMessage() {
    return _$6599;
  }
  
  public static InputStream getErrorStream() {
    return _$6598;
  }
  
  public static InputStream getInputStream() {
    return _$6597;
  }
  
  public static String getMessage() {
    return _$2040;
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      System.err.println("Creating test files...");
      CopyDir copyDir = new CopyDir(new File("D:\\LogFiles\\20040901\\01------_02------_03------_-04--"), new File("C:\\test\\"));
      System.err.println("Testing files created.");
      System.err.println();
      System.err.println("Deleting test directory.");
      delDir(new File("c:\\test\\"));
      System.err.println("Result: \n" + getMessage());
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
