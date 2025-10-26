package Tools.DiskTools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbNamedPipe;

public class CopySmbDir {
  private String _$1667 = "smb://";
  
  private String _$3840 = "";
  
  private String _$3879 = "guest";
  
  public CopySmbDir(SmbFile paramSmbFile, File paramFile) throws SmbException, IOException {
    NtlmPasswordAuthentication ntlmPasswordAuthentication = new NtlmPasswordAuthentication("", this._$3879, this._$3840);
    SmbNamedPipe smbNamedPipe = new SmbNamedPipe(this._$1667, 1, ntlmPasswordAuthentication);
    getFloder(paramFile, (SmbFile)smbNamedPipe);
  }
  
  public CopySmbDir(File paramFile, SmbFile paramSmbFile) throws SmbException, IOException, Exception {
    NtlmPasswordAuthentication ntlmPasswordAuthentication = new NtlmPasswordAuthentication("", this._$3879, this._$3840);
    SmbNamedPipe smbNamedPipe = new SmbNamedPipe(this._$1667, 2, ntlmPasswordAuthentication);
    putFloder(paramFile, (SmbFile)smbNamedPipe);
  }
  
  public CopySmbDir() {}
  
  public void getFile(File paramFile, SmbFile paramSmbFile) throws SmbException, IOException {
    BufferedInputStream bufferedInputStream = new BufferedInputStream(paramSmbFile.getInputStream());
    String str = paramFile.getAbsolutePath() + "\\" + paramSmbFile.getName();
    File file = new File(str);
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    for (int i = bufferedInputStream.read(); i != -1; i = bufferedInputStream.read())
      fileOutputStream.write(i); 
    fileOutputStream.close();
  }
  
  public void getFloder(File paramFile, SmbFile paramSmbFile) throws SmbException, IOException {
    if (paramSmbFile.isDirectory()) {
      String str = paramFile.getAbsolutePath() + "\\" + paramSmbFile.getName();
      str = str.replace('/', '\\');
      File file = new File(str);
      if (!file.exists())
        file.mkdir(); 
      SmbFile[] arrayOfSmbFile = paramSmbFile.listFiles();
      for (byte b = 0; b < arrayOfSmbFile.length; b++)
        getFloder(file, arrayOfSmbFile[b]); 
    } else {
      getFile(paramFile, paramSmbFile);
    } 
  }
  
  public static boolean isFileExist(String paramString1, String paramString2, String paramString3) throws SmbException, IOException, Exception {
    NtlmPasswordAuthentication ntlmPasswordAuthentication = new NtlmPasswordAuthentication("", paramString2, paramString3);
    SmbNamedPipe smbNamedPipe = new SmbNamedPipe(paramString1, 1, ntlmPasswordAuthentication);
    smbNamedPipe.canRead();
    smbNamedPipe.exists();
    smbNamedPipe.canWrite();
    smbNamedPipe.connect();
    return true;
  }
  
  public void putFile(File paramFile, SmbFile paramSmbFile) throws SmbException, IOException, Exception {
    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(paramFile));
    String str = paramSmbFile.getURL() + "/" + paramFile.getName();
    SmbFile smbFile = new SmbFile(str);
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(smbFile.getOutputStream());
    for (int i = bufferedInputStream.read(); i != -1; i = bufferedInputStream.read())
      bufferedOutputStream.write(i); 
    bufferedInputStream.close();
    bufferedOutputStream.close();
  }
  
  public void putFloder(File paramFile, SmbFile paramSmbFile) throws SmbException, IOException, Exception {
    if (paramFile.isDirectory()) {
      String str = paramSmbFile.getURL() + "/" + paramFile.getName();
      SmbFile smbFile = new SmbFile(str);
      smbFile.mkdir();
      File[] arrayOfFile = paramFile.listFiles();
      for (byte b = 0; b < arrayOfFile.length; b++)
        putFloder(arrayOfFile[b], smbFile); 
    } else {
      putFile(paramFile, paramSmbFile);
    } 
  }
}
