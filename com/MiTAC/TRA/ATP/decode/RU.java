// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decode;

import java.util.Vector;

public class RU
{
    public static final int MMI_STATUS_REPORT_CHANGED = 81;
    public static final int MVB_LOG_BTM_COMMAND_1 = 41;
    public static final int MVB_LOG_BTM_STATUS_1 = 42;
    public static final int MVB_LOG_BTM_TGM_1 = 43;
    public static final int MVB_LOG_BTM_TGM_2 = 44;
    public static final int MVB_LOG_BTM_TGM_3 = 45;
    public static final int MVB_LOG_BTM_TGM_4 = 46;
    public static final int MVB_LOG_BTM_TGM_5 = 47;
    public static final int MVB_LOG_ODO_BTM_STATUS_1 = 64;
    public static final int MVB_LOG_ODO_CONFIG_1 = 61;
    public static final int MVB_LOG_ODO_MESSAGE_1 = 62;
    public static final int MVB_LOG_ODO_MESSAGE_2 = 63;
    public static final int MVB_LOG_PM_APP_LOG_TGM = 72;
    public static final int MVB_LOG_PM_LOG_TGM = 71;
    public static final int MVB_LOG_SDU1 = 51;
    public static final int MVB_LOG_SDU2 = 52;
    public static final int MVB_LOG_TYPE_ATP_MMI_CH1 = 1;
    public static final int MVB_LOG_TYPE_ATP_MMI_CH2 = 4;
    public static final int MVB_LOG_TYPE_DX_IN_STATUS_1 = 31;
    public static final int MVB_LOG_TYPE_DX_OUT_STATUS_1 = 33;
    public static final int MVB_LOG_TYPE_DX_STATUS_1 = 32;
    public static final int MVB_LOG_TYPE_VDX_IN_STATUS_1 = 21;
    public static final int MVB_LOG_TYPE_VDX_OUT_1 = 22;
    public static final int MVB_LOG_TYPE_VDX_OUT_2 = 23;
    public static final int MVB_LOG_TYPE_VDX_OUT_3 = 24;
    public static final int T_ATP_DOWN_DIS_SPEED_TIME = 201;
    public static final int T_BUTTON_EVENT = 216;
    public static final int T_PERIODIC_SPEED_DISTANCE = 211;
    public static final int T_PRS_INFO = 91;
    public static final int T_STATUS_ATP = 2;
    public static final int T_STATUS_COUNTER_BOARD = 221;
    public static final int T_STATUS_DATA_DOWNLOAD = 225;
    public static final int T_STATUS_GPP = 228;
    public static final int T_STATUS_MMI = 3;
    public static final int T_STATUS_MVB = 227;
    public static final int T_STATUS_PRS = 223;
    public static final int T_STATUS_SPEEDMETER = 224;
    public static final int T_STATUS_USB = 222;
    public static final int port_MVB_LOG_BTM_COMMAND_1 = 648;
    public static final int port_MVB_LOG_BTM_STATUS_1 = 642;
    public static final int port_MVB_LOG_BTM_TGM_1 = 643;
    public static final int port_MVB_LOG_BTM_TGM_2 = 644;
    public static final int port_MVB_LOG_BTM_TGM_3 = 645;
    public static final int port_MVB_LOG_BTM_TGM_4 = 646;
    public static final int port_MVB_LOG_BTM_TGM_5 = 647;
    public static final int port_MVB_LOG_ODO_BTM_STATUS_1 = 1284;
    public static final int port_MVB_LOG_ODO_CONFIG_1 = 1283;
    public static final int port_MVB_LOG_ODO_MESSAGE_1 = 1285;
    public static final int port_MVB_LOG_ODO_MESSAGE_2 = 1286;
    public static final int port_MVB_LOG_PM_APP_LOG_TGM = 1287;
    public static final int port_MVB_LOG_PM_LOG_TGM = 100;
    public static final int port_MVB_LOG_SDU1 = 256;
    public static final int port_MVB_LOG_SDU2 = 272;
    public static final int port_MVB_LOG_TYPE_DX_IN_STATUS_1 = 2176;
    public static final int port_MVB_LOG_TYPE_DX_OUT_STATUS_1 = 2184;
    public static final int port_MVB_LOG_TYPE_DX_STATUS_1 = 2177;
    public static final int port_MVB_LOG_TYPE_VDX_IN_STATUS_1 = 384;
    public static final int port_MVB_LOG_TYPE_VDX_OUT_1 = 392;
    public static final int port_MVB_LOG_TYPE_VDX_OUT_2 = 393;
    public static final int port_MVB_LOG_TYPE_VDX_OUT_3 = 394;
    
    public static Vector T_ATP_DOWN_DIS_SPEED_TIME(final byte[] array) {
        final Vector vector = new Vector(2);
        final int mmi_O_TRAIN = MMIVariables.MMI_O_TRAIN(array[0], array[1], array[2], array[3]);
        // Speed is 4 bytes (array[4]-array[7]) according to ATPRU-LOGF-001 v1.8 spec
        final int mmi_V_TRAIN = com.MiTAC.TRA.ATP.Tools.Byte2Number.getSigned(array[4], array[5], array[6], array[7]);
        vector.add(new Integer(mmi_O_TRAIN));
        vector.add(new Integer(mmi_V_TRAIN));
        return vector;
    }
    
    public static int T_BUTTON_EVENT(final byte b) {
        return b;
    }
    
    public static int T_STATUS_COUNTER_BOARD(final byte b) {
        return b;
    }
    
    public static int T_STATUS_DATA_DOWNLOAD(final byte b) {
        return b;
    }
    
    public static int T_STATUS_GPP(final byte b) {
        return b;
    }
    
    public static int T_STATUS_MVB(final byte b) {
        return b;
    }
    
    public static int T_STATUS_PRS(final byte b) {
        return b;
    }
    
    public static int T_STATUS_SPEEDMETER(final byte b) {
        return b;
    }
    
    public static int T_STATUS_USB(final byte b) {
        return b;
    }
}
