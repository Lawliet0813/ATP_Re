package Tools.FileFilter;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FilterMMI extends FileFilter {
  public boolean accept(File paramFile) {
    return paramFile.isDirectory() ? true : paramFile.getPath().endsWith(".MMI");
  }
  
  public String getDescription() {
    return "ATP files(*.MMI)";
  }
}
