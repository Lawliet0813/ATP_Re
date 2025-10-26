package Tools;

import com.MiTAC.TRA.ATP.Tools.InitParameters;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.io.File;
import java.util.Vector;

public class BackupHandler {
  private String rootPath;
  
  private Vector orgiList;
  
  private Vector deleteList;
  
  private Vector deleteList_G;
  
  private Vector backupList;
  
  private File[] deleteFile;
  
  private File[] backupFile;
  
  private File[] unknowFile;
  
  public BackupHandler() {}
  
  public BackupHandler(Vector list) {
    this(InitParameters.MWLogPath, list);
  }
  
  public BackupHandler(String path, Vector list) {
    this.rootPath = path;
    this.orgiList = list;
    setData(this.orgiList);
  }
  
  private void filterBackupList(Vector list) {
    ConnectDB conn = new ConnectDB();
    this.deleteList = new Vector();
    this.deleteList_G = new Vector();
    this.backupList = new Vector();
    for (int i = 0; i < list.size(); i++) {
      try {
        Vector tmp1 = conn.getLogBackupSaveDate(list.get(i));
        if (!tmp1.isEmpty()) {
          this.deleteList.add(tmp1.get(0));
          this.deleteList_G.add(list.get(i));
        } else {
          this.backupList.add(list.get(i));
        } 
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } 
    this.backupFile = transferToFile(this.rootPath, this.backupList);
    this.deleteFile = transferToFile(this.rootPath, this.deleteList_G);
  }
  
  public static File[] transferToFile(String root, Vector list) {
    File[] tmpFile = new File[list.size()];
    for (int x = 0; x < list.size(); x++) {
      String dir = PathHandler.getEncodePath(list.get(x));
      String path = String.valueOf(root.endsWith("\\") ? root : (String.valueOf(root) + "\\")) + dir;
      tmpFile[x] = new File(path);
    } 
    return tmpFile;
  }
  
  public static File[] transferToFile(Vector list) {
    return transferToFile(InitParameters.MWLogPath, list);
  }
  
  public void setData(Vector list) {
    filterBackupList(list);
  }
  
  public Vector getBackupList() {
    return this.backupList;
  }
  
  public Vector getDeleteList() {
    return this.deleteList;
  }
  
  public Vector getDeleteList_G() {
    return this.deleteList_G;
  }
  
  public File[] getBackupFile() {
    return this.backupFile;
  }
  
  public File[] getDeleteFile() {
    return this.deleteFile;
  }
}
