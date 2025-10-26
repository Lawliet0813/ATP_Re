package ui;

import com.MiTAC.TRA.ATP.Tools.CopyTask;
import com.MiTAC.TRA.ATP.Tools.CreatMWSystemLog;
import com.MiTAC.TRA.ATP.Tools.DiskTools.CopyDir;
import com.MiTAC.TRA.ATP.Tools.DiskTools.CopyFile;
import com.MiTAC.TRA.ATP.Tools.DiskTools.DiskStatus;
import com.MiTAC.TRA.ATP.Tools.UnknowProgressMonitor;
import com.MiTAC.TRA.ATP.ui.frmBackup_btnCancel_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmBackup_btnConfirm_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmBackup_btnOpenFile_actionAdapter;
import com.MiTAC.TRA.ATP.ui.frmMain;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class frmBackup extends JFrame {
  JButton btnCancel = new JButton("取消");
  
  JButton btnConfirm = new JButton("確認");
  
  JButton btnOpenFile = new JButton("開啟", new ImageIcon("C:\\eclipse\\workspace\\ATPMW\\src\\com\\MiTAC\\TRA\\ATP\\images\\Open16.gif"));
  
  CopyDir copyDir;
  
  CopyFile copyFile;
  
  CopyTask copyTask;
  
  CreatMWSystemLog creatLog;
  
  JFileChooser fc;
  
  File fromDir = new File("C:\\ATPMW\\");
  
  JLabel lbBackPath = new JLabel();
  
  long length = 0L;
  
  private static final String _$7624 = "\n";
  
  UnknowProgressMonitor progressMonitorFormat;
  
  JTextField tfBackPath = new JTextField();
  
  File toDir;
  
  public frmBackup() {
    init();
  }
  
  void btnCancel_actionPerformed(ActionEvent paramActionEvent) {
    dispose();
  }
  
  void btnConfirm_actionPerformed(ActionEvent paramActionEvent) {
    try {
      this.toDir = new File(this.tfBackPath.getText().toString() + "\\ATPMW");
      if (this.tfBackPath.getText().length() == 0) {
        int i = JOptionPane.showConfirmDialog(this, "您尚未選擇任何的備份路徑，請重新選擇備份路徑。", "確認", -1);
      } else if (_$7643(new File(this.tfBackPath.getText().toString())) > _$7636(this.fromDir)) {
        if (this.toDir.exists()) {
          int i = JOptionPane.showConfirmDialog(this, "目的地磁碟中已經有相同的資料夾存在,\n是否要取代該資料夾？", "確認", 0);
          if (i == 0) {
            this.progressMonitorFormat = new UnknowProgressMonitor(this, "資料備份中...", "", 0, 0);
            Object object = new Object(this);
            object.start();
            this.progressMonitorFormat.show();
          } else {
            i = JOptionPane.showConfirmDialog(this, "請更換新的備份路徑!!!", "確認", -1);
          } 
        } else {
          this.progressMonitorFormat = new UnknowProgressMonitor(this, "資料備份中...", "", 0, 0);
          Object object = new Object(this);
          object.start();
          this.progressMonitorFormat.show();
          int i = JOptionPane.showConfirmDialog(getParent(), "已完成資料備份。", "確認", -1);
        } 
      } else {
        int i = JOptionPane.showConfirmDialog(this, "目的地磁碟空間不足，請更換新的磁碟作備份。", "確認", -1);
        if (i == 0)
          dispose(); 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  void btnOpenFile_actionPerformed(ActionEvent paramActionEvent) {
    this.fc = new JFileChooser();
    this.fc.setFileSelectionMode(1);
    int i = this.fc.showOpenDialog(getParent());
    if (i == 0)
      this.tfBackPath.setText(this.fc.getSelectedFile().getAbsolutePath()); 
  }
  
  private void _$7634() {
    try {
      this.copyDir = new CopyDir(this.fromDir, this.toDir);
      CreatMWSystemLog creatMWSystemLog = new CreatMWSystemLog(frmMain.getUserID(), "管理電腦", "資料庫備份", "確認", "資料庫備份");
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private int _$7641(File paramFile) {
    try {
      File[] arrayOfFile = paramFile.listFiles();
      for (byte b = 0; b < arrayOfFile.length; b++) {
        if (arrayOfFile[b].isDirectory()) {
          _$7641(arrayOfFile[b]);
        } else {
          File file = new File(arrayOfFile[b].toString());
          this.length += file.length();
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return (int)this.length;
  }
  
  private int _$7636(File paramFile) {
    long l = 0L;
    try {
      File file = new File(paramFile.toString());
      if (file.isDirectory()) {
        l += _$7641(paramFile);
      } else {
        File file1 = new File(file.toString());
        l += file1.length();
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return (int)l / 1024 / 1024;
  }
  
  private int _$7643(File paramFile) {
    float f = 0.0F;
    try {
      File file = File.createTempFile("temp", ".txt", paramFile);
      DiskStatus.getDiskStatus(paramFile);
      f = (float)(DiskStatus.getDiskFree() / 1024L / 1024L);
      System.out.println("target space:" + f);
      file.delete();
    } catch (FileNotFoundException fileNotFoundException) {
      fileNotFoundException.printStackTrace();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return (int)f;
  }
  
  public void init() {
    getContentPane().setLayout(new GridBagLayout());
    setTitle("備份資料路徑選擇!!");
    setSize(300, 200);
    setResizable(false);
    this.lbBackPath.setText("備份資料存放路徑: ");
    getContentPane().add(this.lbBackPath, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 0, 0), 0, 0));
    getContentPane().add(this.tfBackPath, new GridBagConstraints(0, 1, 1, 1, 1.0D, 0.0D, 17, 0, new Insets(5, 5, 0, 0), 250, 0));
    getContentPane().add(this.btnOpenFile, new GridBagConstraints(1, 1, 1, 1, 1.0D, 0.0D, 17, 0, new Insets(5, 5, 0, 0), 50, 0));
    getContentPane().add(this.btnConfirm, new GridBagConstraints(0, 2, 1, 1, 1.0D, 0.0D, 17, 0, new Insets(5, 5, 0, 0), 0, 0));
    getContentPane().add(this.btnCancel, new GridBagConstraints(1, 2, 1, 1, 1.0D, 0.0D, 17, 0, new Insets(5, 5, 0, 0), 0, 0));
    this.btnOpenFile.addActionListener((ActionListener)new frmBackup_btnOpenFile_actionAdapter(this));
    this.btnConfirm.addActionListener((ActionListener)new frmBackup_btnConfirm_actionAdapter(this));
    this.btnCancel.addActionListener((ActionListener)new frmBackup_btnCancel_actionAdapter(this));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
  }
}
