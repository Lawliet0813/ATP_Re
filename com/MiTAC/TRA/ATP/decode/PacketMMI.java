// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decode;

import java.util.Vector;

public class PacketMMI
{
    public static final int MMI_CONFIRMED_SR_RULES = 103;
    public static final int MMI_CONFIRMED_TRAIN_DATA = 110;
    public static final int MMI_CURRENT_DRIVER_DATA = 14;
    public static final int MMI_CURRENT_SR_RULES = 11;
    public static final int MMI_CURRENT_TRAIN_DATA = 6;
    public static final int MMI_DRIVER_MESSAGE = 8;
    public static final int MMI_DRIVER_MESSAGE_ACK = 111;
    public static final int MMI_DRIVER_REQUEST = 101;
    public static final int MMI_DYNAMIC = 1;
    public static final int MMI_ECHOED_SR_RULES = 12;
    public static final int MMI_ECHOED_TRAIN_DATA = 10;
    public static final int MMI_FAILURE_REPORT_ACK = 115;
    public static final int MMI_FAILURE_REPORT_ATP = 9;
    public static final int MMI_FAILURE_REPORT_MMI = 113;
    public static final int MMI_FORCED_DRIVER_REQUEST = 7;
    public static final int MMI_GEO_POSITION = 5;
    public static final int MMI_NEW_DRIVER_DATA = 104;
    public static final int MMI_NEW_RBC_DATA = 112;
    public static final int MMI_NEW_SR_RULES = 106;
    public static final int MMI_NEW_TRAIN_DATA = 107;
    public static final int MMI_RU_DATA = 19;
    public static final int MMI_RU_DATA_FROM_CAB_1 = 119;
    public static final int MMI_RU_DATA_FROM_CAB_2 = 120;
    public static final int MMI_SELECT_STM = 116;
    public static final int MMI_SELECT_STM_REQUEST = 16;
    public static final int MMI_SET_TIME_ATP = 3;
    public static final int MMI_SET_TIME_MMI = 109;
    public static final int MMI_START_ATP = 0;
    public static final int MMI_START_MMI = 100;
    public static final int MMI_STATUS = 2;
    public static final int MMI_STATUS_REPORT = 102;
    public static final int MMI_STM_DATA_FROM_CAB_1 = 117;
    public static final int MMI_STM_DATA_FROM_CAB_2 = 118;
    public static final int MMI_STM_DATA_TO_CAB_1 = 17;
    public static final int MMI_STM_DATA_TO_CAB_2 = 18;
    public static final int MMI_TEST_REQUEST = 15;
    public static final int MMI_TEST_RESULT = 114;
    public static final int MMI_TRACK_DESCRIPTION = 4;
    
    public static void MMI_CONFIRMED_SR_RULES(final byte[] array) {
        MMI_ECHOED_SR_RULES(array);
    }
    
    public static void MMI_CONFIRMED_TRAIN_DATA(final byte[] array) {
        MMI_ECHOED_TRAIN_DATA(array);
    }
    
    public static Vector MMI_CURRENT_DRIVER_DATA(final byte[] array) {
        final Vector vector = new Vector();
        vector.add(MMIVariables.MMI_NID_DRIVER(new byte[] { array[4], array[5], array[6], array[7], array[8], array[9], array[10], array[11] }));
        vector.add(MMIVariables.MMI_NID_OPERATION(new byte[] { array[12], array[13], array[14], array[15] }));
        vector.add(MMIVariables.MMI_NID_WORKSHIFT(new byte[] { array[16], array[17], array[18], array[19], array[20], array[21], array[22], array[23] }));
        return vector;
    }
    
    public static Vector MMI_CURRENT_SR_RULES(final byte[] array) {
        final Vector vector = new Vector(2);
        vector.add(new Integer(MMIVariables.MMI_V_STFF(array[4], array[5])));
        vector.add(new Long(MMIVariables.MMI_L_STFF(array[6], array[7], array[8], array[9])));
        vector.trimToSize();
        return vector;
    }
    
    public static Vector MMI_CURRENT_TRAIN_DATA(final byte[] array) {
        final Vector vector = new Vector();
        vector.add(MMIVariables.MMI_M_DATA_EDIT_ENABLE(array[4], array[5]));
        vector.add(MMIVariables.MMI_NC_TRAIN(array[6], array[7]));
        vector.add(new Integer(MMIVariables.MMI_L_TRAIN(array[8], array[9])));
        vector.add(new Integer(MMIVariables.MMI_V_MAXTRAIN(array[10], array[11])));
        MMIVariables.MMI_M_LOADINGGAUGE();
        vector.add(new Double(MMIVariables.MMI_M_AXLELOAD(array[13])));
        int n = 14;
        final byte b = array[n];
        final byte[] array2 = new byte[b * 2];
        ++n;
        for (int i = 0; i <= b - 1; ++i) {
            array2[i * 2 + 0] = array[n + i * 2 + 0];
            array2[i * 2 + 1] = array[n + i * 2 + 1];
        }
        MMIVariables.MMI_N_TRACTION(array2);
        int n2 = n + b * 2;
        final byte b2 = array[n2];
        final byte[] array3 = new byte[b2 * 4];
        ++n2;
        for (int j = 0; j <= b2 - 1; ++j) {
            array3[j * 4 + 0] = array[n2 + j * 4 + 0];
            array3[j * 4 + 1] = array[n2 + j * 4 + 1];
            array3[j * 4 + 2] = array[n2 + j * 4 + 2];
            array3[j * 4 + 3] = array[n2 + j * 4 + 3];
        }
        MMIVariables.MMI_N_DSGUARANT(array3);
        int n3 = n2 + b2 * 4;
        final byte b3 = array[n3];
        final byte[] array4 = new byte[b3 * 4];
        ++n3;
        for (int k = 0; k <= b3 - 1; ++k) {
            array4[k * 4 + 0] = array[n3 + k * 4 + 0];
            array4[k * 4 + 1] = array[n3 + k * 4 + 1];
            array4[k * 4 + 2] = array[n3 + k * 4 + 2];
            array4[k * 4 + 3] = array[n3 + k * 4 + 3];
        }
        MMIVariables.MMI_N_DEGUARANT(array4);
        int n4 = n3 + b3 * 4;
        vector.add(new Integer(MMIVariables.MMI_T_BRAKE_SB(array[n4], array[++n4])));
        vector.add(new Integer(MMIVariables.MMI_T_BRAKE_EB(array[++n4], array[++n4])));
        vector.add(new Integer(MMIVariables.MMI_T_CUTOFFTRACTION(array[++n4], array[++n4])));
        vector.add(new Integer(MMIVariables.MMI_A_MAX_ACC(array[++n4], array[++n4])));
        vector.add(new Integer(MMIVariables.MMI_A_MAX_DEC(array[++n4], array[++n4])));
        vector.add(new String(MMIVariables.MMI_NID_ENGINE(array[++n4], array[++n4], array[++n4])));
        vector.add(new Integer(MMIVariables.MMI_M_TRAIN_TYPE(array[++n4], array[++n4])));
        vector.add(new Integer(MMIVariables.MMI_V_MAXTRAIN_x(array[++n4], array[++n4])));
        vector.add(new Integer(MMIVariables.MMI_V_MAXTRAIN_x(array[++n4], array[++n4])));
        vector.add(new Integer(MMIVariables.MMI_V_MAXTRAIN_x(array[++n4], array[++n4])));
        vector.add(new Integer(MMIVariables.MMI_V_MAXTRAIN_x(array[++n4], array[++n4])));
        return vector;
    }
    
    public static Vector MMI_DRIVER_MESSAGE(final byte[] array) {
        final Vector vector = new Vector();
        vector.add(new Integer(MMIVariables.MMI_I_TEXT(array[4])));
        vector.add(new Integer(MMIVariables.MMI_Q_TEXT_CLASS(array[5] >> 7 & 0x1)));
        vector.add(new Integer(MMIVariables.MMI_Q_TEXT_CRITERIA(array[5] >> 4 & 0x7)));
        vector.add(new Integer(MMIVariables.MMI_Q_TEXT(array[6], array[7])));
        vector.trimToSize();
        return vector;
    }
    
    public static void MMI_DRIVER_MESSAGE_ACK(final byte[] array) {
        MMIVariables.MMI_I_TEXT(array[4]);
        MMIVariables.MMI_Q_ACK((array[5] & 0xF0) >> 4);
    }
    
    public static int MMI_DRIVER_REQUEST(final byte[] array) {
        return MMIVariables.MMI_M_REQUEST(array[4]);
    }
    
    public static Vector MMI_DYMANIC(final byte[] array) {
        final Vector vector = new Vector();
        vector.add(new Integer(MMIVariables.MMI_V_TRAIN(array[4], array[5])));
        vector.add(new Integer(MMIVariables.MMI_A_TRAIN(array[6], array[7])));
        vector.add(new Integer(MMIVariables.MMI_O_TRAIN(array[8], array[9], array[10], array[11])));
        vector.add(new Integer(MMIVariables.MMI_O_BRAKETARGET(array[12], array[13], array[14], array[15])));
        vector.add(new Integer(MMIVariables.MMI_V_TARGET(array[16], array[17])));
        vector.add(new Integer(MMIVariables.MMI_T_INTERVENWAR(array[18], array[19])));
        vector.add(new Integer(MMIVariables.MMI_V_PERMITTED(array[20], array[21])));
        vector.add(new Integer(MMIVariables.MMI_V_RELEASE(array[22], array[23])));
        vector.add(new Integer(MMIVariables.MMI_V_INTERVENTION(array[24], array[25])));
        vector.add(new Integer(MMIVariables.MMI_M_WARNING((array[26] & 0xF0) >> 4)));
        vector.add(new Integer(MMIVariables.MMI_M_SLIP((array[26] & 0x8) >> 3)));
        vector.add(new Integer(MMIVariables.MMI_M_SLIDE((array[26] & 0x4) >> 2)));
        vector.add(new Integer(MMIVariables.MMI_O_BCSP(array[27], array[28], array[29], array[30])));
        return vector;
    }
    
    public static void MMI_ECHOED_SR_RULES(final byte[] array) {
        MMIVariables.MMI_L_STFF((byte)~array[4], (byte)~array[5], (byte)~array[6], (byte)~array[7]);
        MMIVariables.MMI_V_STFF((byte)~array[8], (byte)~array[9]);
    }
    
    public static void MMI_ECHOED_TRAIN_DATA(final byte[] array) {
        MMIVariables.MMI_V_MAXTRAIN_x((byte)~array[4], (byte)~array[5]);
        MMIVariables.MMI_V_MAXTRAIN_x((byte)~array[6], (byte)~array[7]);
        MMIVariables.MMI_V_MAXTRAIN_x((byte)~array[8], (byte)~array[9]);
        MMIVariables.MMI_V_MAXTRAIN_x((byte)~array[10], (byte)~array[11]);
        MMIVariables.MMI_M_TRAIN_TYPE((byte)~array[12], (byte)~array[13]);
        MMIVariables.MMI_NID_ENGINE((byte)~array[14], (byte)~array[15], (byte)~array[16]);
        MMIVariables.MMI_A_MAX_DEC((byte)~array[17], (byte)~array[18]);
        MMIVariables.MMI_A_MAX_ACC((byte)~array[19], (byte)~array[20]);
        MMIVariables.MMI_T_CUTOFFTRACTION((byte)~array[21], (byte)~array[22]);
        MMIVariables.MMI_T_BRAKE_EB((byte)~array[23], (byte)~array[24]);
        MMIVariables.MMI_T_BRAKE_SB((byte)~array[25], (byte)~array[26]);
        int n = 27;
        final int n2 = ~array[n];
        final byte[] array2 = new byte[n2 * 4];
        ++n;
        for (int i = 0; i <= n2 - 1; ++i) {
            array2[i * 4 + 0] = (byte)~array[n + i * 4 + 0];
            array2[i * 4 + 1] = (byte)~array[n + i * 4 + 1];
            array2[i * 4 + 2] = (byte)~array[n + i * 4 + 2];
            array2[i * 4 + 3] = (byte)~array[n + i * 4 + 3];
        }
        MMIVariables.MMI_N_DEGUARANT(array2);
        int n3 = n + n2 * 4;
        final int n4 = ~array[n3];
        final byte[] array3 = new byte[n4 * 4];
        ++n3;
        for (int j = 0; j <= n4 - 1; ++j) {
            array3[j * 4 + 0] = (byte)~array[n3 + j * 4 + 0];
            array3[j * 4 + 1] = (byte)~array[n3 + j * 4 + 1];
            array3[j * 4 + 2] = (byte)~array[n3 + j * 4 + 2];
            array3[j * 4 + 3] = (byte)~array[n3 + j * 4 + 3];
        }
        MMIVariables.MMI_N_DSGUARANT(array3);
        int n5 = n3 + n4 * 4;
        final int n6 = ~array[n5];
        final byte[] array4 = new byte[n6 * 2];
        ++n5;
        for (int k = 0; k <= n6 - 1; ++k) {
            array4[k * 2 + 0] = (byte)~array[n5 + k * 2 + 0];
            array4[k * 2 + 1] = (byte)~array[n5 + k * 2 + 1];
        }
        MMIVariables.MMI_N_TRACTION(array4);
        int n7 = n5 + n6 * 2;
        MMIVariables.MMI_M_AXLELOAD((byte)~array[n7]);
        ++n7;
        MMIVariables.MMI_M_LOADINGGAUGE();
        MMIVariables.MMI_V_MAXTRAIN((byte)~array[++n7], (byte)~array[++n7]);
        MMIVariables.MMI_L_TRAIN((byte)~array[++n7], (byte)~array[++n7]);
        MMIVariables.MMI_NC_TRAIN((byte)~array[++n7], (byte)~array[++n7]);
        MMIVariables.MMI_M_DATA_EDIT_ENABLE((byte)~array[++n7], (byte)~array[++n7]);
    }
    
    public static void MMI_FAILURE_REPORT_ACK(final byte[] array) {
        MMIVariables.MMI_I_FAILURE_NUMBER(array[4], array[5]);
    }
    
    public static Vector MMI_FAILURE_REPORT_ATP(final byte[] array) {
        final Vector vector = new Vector();
        vector.add(new Integer(MMIVariables.MMI_Q_FAILURE_CLASS(array[4])));
        vector.add(new Integer(MMIVariables.MMI_I_UNIT((array[5] & 0xF0) >> 4)));
        vector.add(new Integer(MMIVariables.MMI_Q_FAILURE_SEVERITY(array[5] & 0xF)));
        vector.add(new Integer(MMIVariables.MMI_I_FAILURE_NUMBER(array[6], array[7])));
        vector.add(new Integer(MMIVariables.MMI_Q_FAILURE_CRITERIA((array[8] & 0xF0) >> 4)));
        vector.trimToSize();
        return vector;
    }
    
    public static void MMI_FAILURE_REPORT_MMI(final byte[] array) {
        MMIVariables.MMI_I_FAILURE_NUMBER(array[4], array[5]);
    }
    
    public static String MMI_FORCED_DRIVER_REQUEST(final byte[] array) {
        return MMIVariables.MMI_M_FORCED_REQUEST(array[3], array[4]);
    }
    
    public static Vector MMI_GEO_POSITION(final byte[] array) {
        final Vector vector = new Vector(2);
        vector.add(new Integer(MMIVariables.MMI_M_ABSOLUTPOS(array[4], array[5], array[6])));
        vector.add(new Integer(MMIVariables.MMI_M_RELATIVPOS(array[7], array[8])));
        vector.trimToSize();
        return vector;
    }
    
    public static void MMI_NEW_DRIVER_DATA(final byte[] array) {
        MMI_CURRENT_DRIVER_DATA(array);
    }
    
    public static void MMI_NEW_RBC_DATA(final byte[] array) {
        MMIVariables.MMI_NID_RBC(array[4], array[5], array[6]);
        final byte[] array2 = new byte[8];
        for (int i = 0; i <= 7; ++i) {
            array2[i] = array[7 + i];
        }
        MMIVariables.MMI_NID_RADIO(array2);
    }
    
    public static void MMI_NEW_SR_RULES(final byte[] array) {
        MMI_CURRENT_SR_RULES(array);
    }
    
    public static void MMI_NEW_TRAIN_DATA(final byte[] array) {
        MMI_CURRENT_TRAIN_DATA(array);
    }
    
    public static void MMI_RU_DATA(final byte[] array) {
    }
    
    public static void MMI_RU_DATA_FROM_CAB_1(final byte[] array) {
    }
    
    public static void MMI_RU_DATA_FROM_CAB_2(final byte[] array) {
    }
    
    public static void MMI_SELECT_STM(final byte[] array) {
        MMIVariables.MMI_NID_STM();
    }
    
    public static void MMI_SELECT_STM_REQUEST(final byte[] array) {
        MMIVariables.MMI_NID_STM();
    }
    
    public static long MMI_SET_TIME_ATP(final byte[] array) {
        return MMIVariables.MMI_T_UTC(array[4], array[5], array[6], array[7]);
    }
    
    public static void MMI_SET_TIME_MMI(final byte[] array) {
        MMIVariables.MMI_T_UTC(array[4], array[5], array[6], array[7]);
    }
    
    public static int MMI_START_ATP(final byte[] array) {
        return MMIVariables.MMI_M_START_REQ(array[4]);
    }
    
    public static Vector MMI_START_MMI(final byte[] array) {
        final Vector vector = new Vector(5);
        vector.add(new Integer(MMIVariables.MMI_M_START_INFO(array[4])));
        vector.add(new Integer(MMIVariables.MMI_M_START_STATUS(array[5])));
        vector.add(new Integer(MMIVariables.MMI_M_TYPE(array[6])));
        vector.add(MMIVariables.MMI_M_IF_VER(array[7], array[8], array[9]));
        vector.add(MMIVariables.MMI_M_SW_VER(array[10], array[11], array[12]));
        vector.trimToSize();
        return vector;
    }
    
    public static Vector MMI_STATUS(final byte[] array) {
        final Vector vector = new Vector();
        vector.add(new Integer(MMIVariables.MMI_M_ADHESION(array[4])));
        vector.add(new Integer(MMIVariables.MMI_M_MODE((array[5] & 0xF0) >> 4)));
        MMIVariables.MMI_M_LEVEL(array[5] & 0xF);
        vector.add(new Integer(MMIVariables.MMI_M_EMERBRAKE((array[6] & 0xC0) >> 6)));
        vector.add(new Integer(MMIVariables.MMI_M_SERVICEBRAKE(array[6] >> 4 & 0x3)));
        vector.add(new Integer(MMIVariables.MMI_M_OVERRIDE_EOA(array[6] & 0x8)));
        MMIVariables.MMI_M_TRIP(array[6] & 0x4);
        vector.add(new Integer(MMIVariables.MMI_M_ACTIVE_CABIN(array[6] & 0x3)));
        vector.trimToSize();
        return vector;
    }
    
    public static int MMI_STATUS_REPORT(final byte[] array) {
        return MMIVariables.MMI_M_MMI_STATUS(array[4]);
    }
    
    public static void MMI_STM_DATA_FROM_CAB_1(final byte[] array) {
    }
    
    public static void MMI_STM_DATA_FROM_CAB_2(final byte[] array) {
    }
    
    public static void MMI_STM_DATA_TO_CAB_1(final byte[] array) {
    }
    
    public static void MMI_STM_DATA_TO_CAB_2(final byte[] array) {
    }
    
    public static int MMI_TEST_REQUEST(final byte[] array) {
        return MMIVariables.MMI_M_TEST_(array[4], array[5]);
    }
    
    public static void MMI_TEST_RESULT(final byte[] array) {
        MMIVariables.MMI_M_TEST_(array[4], array[5]);
    }
    
    public static void MMI_TRACK_DESCRIPTION(final byte[] array) {
        MMIVariables.MMI_V_MRSP_CURR(array[4], array[5]);
        int n = 6;
        final byte b = array[6];
        final byte[] array2 = new byte[b * 6];
        ++n;
        for (int i = 0; i <= b - 1; ++i) {
            array2[i + 0] = array[n + 6 * i + 0];
            array2[i + 1] = array[n + 6 * i + 1];
            array2[i + 2] = array[n + 6 * i + 2];
            array2[i + 3] = array[n + 6 * i + 3];
            array2[i + 4] = array[n + 6 * i + 4];
            array2[i + 5] = array[n + 6 * i + 5];
        }
        MMIVariables.MMI_N_MRSP(array2);
        int n2 = n + b * 6;
        MMIVariables.MMI_G_GRADIENT_CURR(array[n2], array[++n2]);
        ++n2;
        final byte b2 = array[n2];
        final byte[] array3 = new byte[b2 * 6];
        ++n2;
        for (int j = 0; j <= b2 - 1; ++j) {
            array3[j + 0] = array[n2 + 6 * j + 0];
            array3[j + 1] = array[n2 + 6 * j + 1];
            array3[j + 2] = array[n2 + 6 * j + 2];
            array3[j + 3] = array[n2 + 6 * j + 3];
            array3[j + 4] = array[n2 + 6 * j + 4];
            array3[j + 5] = array[n2 + 6 * j + 5];
        }
        MMIVariables.MMI_N_GRADIENT(array3);
        int n3 = n2 + b2 * 6;
        final byte b3 = array[n3];
        final byte[] array4 = new byte[b3 * 9];
        ++n3;
        for (int k = 0; k <= b3 - 1; ++k) {
            array4[k + 0] = array[n3 + 9 * k + 0];
            array4[k + 1] = array[n3 + 9 * k + 1];
            array4[k + 2] = array[n3 + 9 * k + 2];
            array4[k + 3] = array[n3 + 9 * k + 3];
            array4[k + 4] = array[n3 + 9 * k + 4];
            array4[k + 5] = array[n3 + 9 * k + 5];
            array4[k + 6] = array[n3 + 9 * k + 6];
            array4[k + 7] = array[n3 + 9 * k + 7];
            array4[k + 8] = array[n3 + 9 * k + 8];
        }
        MMIVariables.MMI_N_TRACKC(array4);
    }
}
