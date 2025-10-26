package Tools.FileFilter;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FilterRU extends FileFilter {
  public boolean accept(File paramFile) {
    return paramFile.isDirectory() ? true : paramFile.getPath().endsWith(".RU");
  }
  
  public String getDescription() {
    return "ATP files(*.RU)";
  }
}
