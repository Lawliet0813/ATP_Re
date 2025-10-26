package decode;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.StringHandler;
import com.MiTAC.TRA.ATP.decode.MMIVariables;
import java.util.Vector;

public class DecodeTS {
  public static Vector getTS(byte[] paramArrayOfbyte) throws Exception {
    Vector vector = new Vector();
    vector.add(_$1911(paramArrayOfbyte[0], paramArrayOfbyte[1], paramArrayOfbyte[2], paramArrayOfbyte[3], paramArrayOfbyte[4], paramArrayOfbyte[5]));
    vector.add(new Integer(MMIVariables.MMI_V_TRAIN(paramArrayOfbyte[12], paramArrayOfbyte[13])));
    vector.add(new Integer(MMIVariables.MMI_O_TRAIN(paramArrayOfbyte[6], paramArrayOfbyte[7], paramArrayOfbyte[8], paramArrayOfbyte[9])));
    return vector;
  }
  
  private static String _$1911(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte paramByte5, byte paramByte6) throws Exception {
    int i = 2000 + Byte2Number.getUnsigned(paramByte1);
    int j = Byte2Number.getUnsigned(paramByte2);
    int k = Byte2Number.getUnsigned(paramByte3);
    int m = Byte2Number.getUnsigned(paramByte4);
    int n = Byte2Number.getUnsigned(paramByte5);
    int i1 = Byte2Number.getUnsigned(paramByte6);
    return i + "/" + StringHandler.fillLeft("" + j, "0", 2) + "/" + StringHandler.fillLeft("" + k, "0", 2) + " " + StringHandler.fillLeft("" + m, "0", 2) + ":" + StringHandler.fillLeft("" + n, "0", 2) + ":" + StringHandler.fillLeft("" + i1, "0", 2);
  }
}
