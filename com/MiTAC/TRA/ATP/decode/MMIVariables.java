// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decode;

import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.Tools.Byte2Number;

public class MMIVariables
{
    public static int MMI_A_DEGUARANTEED(final byte b, final byte b2) {
        return Byte2Number.getUnsigned(b, b2);
    }
    
    public static int MMI_A_DSGUARANTEED(final byte b, final byte b2) {
        return Byte2Number.getUnsigned(b, b2);
    }
    
    public static int MMI_A_MAX_ACC(final byte b, final byte b2) {
        return Byte2Number.getUnsigned(b, b2);
    }
    
    public static int MMI_A_MAX_DEC(final byte b, final byte b2) {
        return Byte2Number.getUnsigned(b, b2);
    }
    
    public static int MMI_A_TRAIN(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static int MMI_G_GRADIENT(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static int MMI_G_GRADIENT_CURR(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static int MMI_I_FAILURE_NUMBER(final byte b, final byte b2) {
        return Byte2Number.getUnsigned(b, b2);
    }
    
    public static int MMI_I_TEXT(final byte b) {
        return Byte2Number.getUnsigned(b);
    }
    
    public static int MMI_I_UNIT(int n) {
        return ++n;
    }
    
    public static int MMI_L_PACKET(final byte b, final byte b2) {
        return Byte2Number.getUnsigned(b, b2);
    }
    
    public static long MMI_L_STFF(final byte b, final byte b2, final byte b3, final byte b4) {
        return Byte2Number.getUnsigned(b, b2, b3, b4);
    }
    
    public static long MMI_L_TRACKCOND(final byte b, final byte b2, final byte b3, final byte b4) {
        return Byte2Number.getUnsigned(b, b2, b3, b4);
    }
    
    public static int MMI_L_TRAIN(final byte b, final byte b2) {
        return Byte2Number.getUnsigned(b, b2);
    }
    
    public static int MMI_M_ABSOLUTPOS(final byte b, final byte b2, final byte b3) {
        return Byte2Number.getSigned(b, b2, b3);
    }
    
    public static int MMI_M_ACTIVE_CABIN(final int n) {
        return n;
    }
    
    public static int MMI_M_ADHESION(final byte b) {
        return Byte2Number.getUnsigned(b);
    }
    
    public static double MMI_M_AXLELOAD(final byte b) {
        final double n = Byte2Number.getUnsigned(b);
        if (0.0 <= n && n <= 80.0) {
            return n * 0.5;
        }
        if (n == 126.0) {
            return 41.0;
        }
        return 100.0;
    }
    
    public static String MMI_M_DATA_EDIT_ENABLE(final byte b, final byte b2) {
        String s = "";
        for (int i = 0; i <= 15; ++i) {
            if (((b << 8 & b2) >> i & 0x1) == 0x1) {
                s += "1";
            }
            else {
                s += "0";
            }
        }
        return s;
    }
    
    public static int MMI_M_EMERBRAKE(final int n) {
        return n;
    }
    
    public static String MMI_M_FORCED_REQUEST(final byte b, final byte b2) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 8; ++i) {
            sb.insert(0, b2 >> i & 0x1);
        }
        for (int j = 0; j < 8; ++j) {
            sb.insert(0, b >> j & 0x1);
        }
        return sb.toString();
    }
    
    public static String MMI_M_IF_VER(final byte b, final byte b2, final byte b3) {
        return Byte2Number.getSigned(b) + "." + Byte2Number.getSigned(b2) + "." + Byte2Number.getSigned(b3);
    }
    
    public static int MMI_M_LEVEL(final int n) {
        return n;
    }
    
    public static void MMI_M_LOADINGGAUGE() {
    }
    
    public static int MMI_M_MMI_STATUS(final byte b) {
        return Byte2Number.getUnsigned(b);
    }
    
    public static int MMI_M_MODE(final int n) {
        return n;
    }
    
    public static int MMI_M_OVERRIDE_EOA(final int n) {
        return n;
    }
    
    public static int MMI_M_PACKET(final byte b) {
        return Byte2Number.getUnsigned(b);
    }
    
    public static int MMI_M_RELATIVPOS(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static int MMI_M_REQUEST(final byte b) {
        return Byte2Number.getUnsigned(b);
    }
    
    public static void MMI_M_RU_DATA() {
    }
    
    public static int MMI_M_SERVICEBRAKE(final int n) {
        return n;
    }
    
    public static int MMI_M_SLIDE(final int n) {
        return n;
    }
    
    public static int MMI_M_SLIP(final int n) {
        return n;
    }
    
    public static int MMI_M_START_INFO(final byte b) {
        return Byte2Number.getUnsigned(b);
    }
    
    public static int MMI_M_START_REQ(final byte b) {
        return Byte2Number.getUnsigned(b);
    }
    
    public static int MMI_M_START_STATUS(final byte b) {
        return Byte2Number.getUnsigned(b);
    }
    
    public static void MMI_M_STM_DATA() {
    }
    
    public static String MMI_M_SW_VER(final byte b, final byte b2, final byte b3) {
        return Byte2Number.getSigned(b) + "." + Byte2Number.getSigned(b2) + "." + Byte2Number.getSigned(b3);
    }
    
    public static int MMI_M_TEST_(final byte b, final byte b2) {
        return Byte2Number.getUnsigned(b, b2);
    }
    
    public static int MMI_M_TRACKCOND(final byte b) {
        return Byte2Number.getUnsigned(b);
    }
    
    public static void MMI_M_TRACTION() {
    }
    
    public static int MMI_M_TRAIN_TYPE(final byte b, final byte b2) {
        return Byte2Number.getUnsigned(b, b2);
    }
    
    public static int MMI_M_TRIP(final int n) {
        return n;
    }
    
    public static int MMI_M_TYPE(final byte b) {
        return Byte2Number.getUnsigned(b);
    }
    
    public static void MMI_M_VOLTAGE() {
    }
    
    public static int MMI_M_WARNING(final int n) {
        return n;
    }
    
    public static String MMI_NC_TRAIN(final byte b, final byte b2) {
        return HexCode.getBinaryInteger(new byte[] { b, b2 });
    }
    
    public static String MMI_NID_DRIVER(final byte[] array) {
        return HexCode.decodeToChar(array).trim();
    }
    
    public static String MMI_NID_ENGINE(final byte b, final byte b2, final byte b3) {
        new StringBuffer().append(HexCode.decodeToChar(new byte[] { b })).append(Byte2Number.getUnsigned(b2, b3)).toString();
        return " ";
    }
    
    public static String MMI_NID_OPERATION(final byte[] array) {
        return "" + HexCode.getHexA_String(array[0]) + HexCode.getHexA_String(array[1]) + HexCode.decodeToChar(new byte[] { array[2], array[3] });
    }
    
    public static String MMI_NID_RADIO(final byte[] array) {
        return HexCode.decodeToChar(array).trim();
    }
    
    public static int MMI_NID_RBC(final byte b, final byte b2, final byte b3) {
        return Byte2Number.getUnsigned(b, b2, b3);
    }
    
    public static void MMI_NID_STM() {
    }
    
    public static String MMI_NID_WORKSHIFT(final byte[] array) {
        return HexCode.decodeToChar(array).trim();
    }
    
    public static void MMI_N_DEGUARANT(final byte[] array) {
        for (int i = 0; i <= array.length / 4 - 1; ++i) {
            MMI_V_DEC_LIMIT_SE(array[4 * i + 0], array[4 * i + 1]);
            MMI_A_DEGUARANTEED(array[4 * i + 2], array[4 * i + 3]);
        }
    }
    
    public static void MMI_N_DSGUARANT(final byte[] array) {
        for (int i = 0; i <= array.length / 4 - 1; ++i) {
            MMI_V_DEC_LIMIT_SE(array[4 * i + 0], array[4 * i + 1]);
            MMI_A_DSGUARANTEED(array[4 * i + 2], array[4 * i + 3]);
        }
    }
    
    public static void MMI_N_GRADIENT(final byte[] array) {
        for (int i = 0; i <= array.length / 6 - 1; ++i) {
            MMI_G_GRADIENT(array[6 * i + 0], array[6 * i + 1]);
            MMI_O_GRADIENT(array[6 * i + 2], array[6 * i + 3], array[6 * i + 4], array[6 * i + 5]);
        }
    }
    
    public static void MMI_N_MRSP(final byte[] array) {
        for (int i = 0; i <= array.length / 6 - 1; ++i) {
            MMI_V_MRSP(array[6 * i + 0], array[6 * i + 1]);
            MMI_O_MRSP(array[6 * i + 2], array[6 * i + 3], array[6 * i + 4], array[6 * i + 5]);
        }
    }
    
    public static void MMI_N_TRACKC(final byte[] array) {
        for (int i = 0; i <= array.length / 9 - 1; ++i) {
            MMI_O_TRACKCOND(array[9 * i + 0], array[9 * i + 1], array[9 * i + 2], array[9 * i + 3]);
            MMI_M_TRACKCOND(array[9 * i + 4]);
            MMI_L_TRACKCOND(array[9 * i + 5], array[9 * i + 6], array[9 * i + 7], array[9 * i + 8]);
        }
    }
    
    public static void MMI_N_TRACTION(final byte[] array) {
        MMI_M_VOLTAGE();
    }
    
    public static int MMI_O_BCSP(final byte b, final byte b2, final byte b3, final byte b4) {
        return Byte2Number.getSigned(b, b2, b3, b4);
    }
    
    public static int MMI_O_BRAKETARGET(final byte b, final byte b2, final byte b3, final byte b4) {
        return Byte2Number.getSigned(b, b2, b3, b4);
    }
    
    public static int MMI_O_GRADIENT(final byte b, final byte b2, final byte b3, final byte b4) {
        return Byte2Number.getSigned(b, b2, b3, b4);
    }
    
    public static int MMI_O_MRSP(final byte b, final byte b2, final byte b3, final byte b4) {
        return Byte2Number.getSigned(b, b2, b3, b4);
    }
    
    public static int MMI_O_TRACKCOND(final byte b, final byte b2, final byte b3, final byte b4) {
        return Byte2Number.getSigned(b, b2, b3, b4);
    }
    
    public static int MMI_O_TRAIN(final byte b, final byte b2, final byte b3, final byte b4) {
        return Byte2Number.getSigned(b, b2, b3, b4);
    }
    
    public static int MMI_Q_ACK(final int n) {
        return n;
    }
    
    public static int MMI_Q_FAILURE_CLASS(final byte b) {
        return Byte2Number.getUnsigned(b);
    }
    
    public static int MMI_Q_FAILURE_CRITERIA(final int n) {
        return n;
    }
    
    public static int MMI_Q_FAILURE_SEVERITY(final int n) {
        return n;
    }
    
    public static int MMI_Q_TEXT(final byte b, final byte b2) {
        return Byte2Number.getUnsigned(b, b2);
    }
    
    public static int MMI_Q_TEXT_CLASS(final int n) {
        return n;
    }
    
    public static int MMI_Q_TEXT_CRITERIA(final int n) {
        return n;
    }
    
    public static int MMI_T_BRAKE_EB(final byte b, final byte b2) {
        return Byte2Number.getUnsigned(b, b2);
    }
    
    public static int MMI_T_BRAKE_SB(final byte b, final byte b2) {
        return Byte2Number.getUnsigned(b, b2);
    }
    
    public static int MMI_T_CUTOFFTRACTION(final byte b, final byte b2) {
        return Byte2Number.getUnsigned(b, b2);
    }
    
    public static int MMI_T_INTERVENWAR(final byte b, final byte b2) {
        return Byte2Number.getUnsigned(b, b2);
    }
    
    public static long MMI_T_UTC(final byte b, final byte b2, final byte b3, final byte b4) {
        return Byte2Number.getUnsigned(b, b2, b3, b4);
    }
    
    public static int MMI_V_DEC_LIMIT_SE(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static int MMI_V_INTERVENTION(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static int MMI_V_MAXTRAIN(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static int MMI_V_MAXTRAIN_x(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static int MMI_V_MRSP(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static int MMI_V_MRSP_CURR(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static int MMI_V_PERMITTED(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static int MMI_V_RELEASE(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static int MMI_V_STFF(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static int MMI_V_TARGET(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static int MMI_V_TRAIN(final byte b, final byte b2) {
        return Byte2Number.getSigned(b, b2);
    }
    
    public static String MMI_X_TEXT(final byte[] array) {
        return HexCode.decodeToChar(array).trim();
    }
}
