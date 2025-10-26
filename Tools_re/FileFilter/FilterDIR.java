package Tools.FileFilter;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FilterDIR extends FileFilter {
  public boolean accept(File paramFile) {
    return paramFile.isDirectory();
  }
  
  public String getDescription() {
    return "Choose a Director";
  }
}
