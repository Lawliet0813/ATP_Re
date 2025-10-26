package Tools;

import java.io.InputStream;
import java.util.Vector;

public class StringHandler {
  public static final String InputStreamToString(InputStream paramInputStream) throws Exception {
    String str = "";
    Vector vector = new Vector();
    for (byte b = 0;; b++) {
      byte[] arrayOfByte;
      int i = paramInputStream.read();
      if (i == -1) {
        arrayOfByte = new byte[vector.size()];
        for (byte b1 = 0; b1 < vector.size(); b1++)
          arrayOfByte[b1] = ((Integer)vector.get(b1)).byteValue(); 
        return new String(arrayOfByte);
      } 
      vector.add(new Integer(arrayOfByte));
    } 
  }
  
  public static String fillLeft(String paramString1, String paramString2, int paramInt) {
    String str = paramString1;
    for (int i = str.length(); i < paramInt; i++)
      str = paramString2 + str; 
    return str;
  }
  
  public static String fillRight(String paramString1, String paramString2, int paramInt) {
    String str = paramString1;
    for (int i = str.length(); i < paramInt; i++)
      str = str + paramString2; 
    return str;
  }
}
