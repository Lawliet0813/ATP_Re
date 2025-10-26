package Tools;

import java.io.FileInputStream;

public class FileRead {
  private byte[] _$1889;
  
  public FileRead(String paramString) throws Exception {
    setFilePath(paramString);
  }
  
  public byte[] getCodes(int paramInt1, int paramInt2) throws Exception {
    byte[] arrayOfByte = new byte[paramInt2];
    for (byte b = 0; b <= paramInt2 - 1; b++)
      arrayOfByte[b] = this._$1889[paramInt1 + b]; 
    return arrayOfByte;
  }
  
  public byte[] getCodes() {
    return this._$1889;
  }
  
  public void setFilePath(String paramString) throws Exception {
    FileInputStream fileInputStream = new FileInputStream(paramString);
    this._$1889 = new byte[fileInputStream.available()];
    fileInputStream.read(this._$1889);
  }
}
