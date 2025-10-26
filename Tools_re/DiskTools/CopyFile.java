package Tools.DiskTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class CopyFile {
  public CopyFile(File paramFile1, File paramFile2) throws FileNotFoundException, IOException {
    if (paramFile2.isFile())
      paramFile2.getParentFile().mkdirs(); 
    FileInputStream fileInputStream = new FileInputStream(paramFile1);
    FileOutputStream fileOutputStream = new FileOutputStream(paramFile2);
    FileChannel fileChannel = fileInputStream.getChannel();
    fileChannel.transferTo(0L, fileInputStream.available(), fileOutputStream.getChannel());
    fileInputStream.close();
    fileOutputStream.close();
    fileChannel.close();
    fileInputStream = null;
    fileOutputStream = null;
    fileChannel = null;
  }
}
