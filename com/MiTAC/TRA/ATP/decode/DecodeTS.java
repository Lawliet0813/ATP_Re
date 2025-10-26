// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decode;

import com.MiTAC.TRA.ATP.Tools.StringHandler;
import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import java.util.Vector;

public class DecodeTS
{
    public static Vector getTS(final byte[] array) throws Exception {
        final Vector vector = new Vector();
        vector.add(_$1911(array[0], array[1], array[2], array[3], array[4], array[5]));
        vector.add(new Integer(MMIVariables.MMI_V_TRAIN(array[12], array[13])));
        vector.add(new Integer(MMIVariables.MMI_O_TRAIN(array[6], array[7], array[8], array[9])));
        return vector;
    }
    
    private static String _$1911(final byte b, final byte b2, final byte b3, final byte b4, final byte b5, final byte b6) throws Exception {
        return 2000 + Byte2Number.getUnsigned(b) + "/" + StringHandler.fillLeft("" + Byte2Number.getUnsigned(b2), "0", 2) + "/" + StringHandler.fillLeft("" + Byte2Number.getUnsigned(b3), "0", 2) + " " + StringHandler.fillLeft("" + Byte2Number.getUnsigned(b4), "0", 2) + ":" + StringHandler.fillLeft("" + Byte2Number.getUnsigned(b5), "0", 2) + ":" + StringHandler.fillLeft("" + Byte2Number.getUnsigned(b6), "0", 2);
    }
}
