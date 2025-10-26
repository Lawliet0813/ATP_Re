package decoder;

import com.MiTAC.TRA.ATP.decoder.MMIVariables;
import java.util.Vector;

public class RU {
  public static final int MVB_LOG_TYPE_ATP_MMI_CH1 = 1;
  
  public static final int T_STATUS_ATP = 2;
  
  public static final int T_STATUS_MMI = 3;
  
  public static final int MVB_LOG_TYPE_ATP_MMI_CH2 = 4;
  
  public static final int MVB_LOG_TYPE_VDX_IN_STATUS_1 = 21;
  
  public static final int port_MVB_LOG_TYPE_VDX_IN_STATUS_1 = 384;
  
  public static final int MVB_LOG_TYPE_VDX_OUT_1 = 22;
  
  public static final int port_MVB_LOG_TYPE_VDX_OUT_1 = 392;
  
  public static final int MVB_LOG_TYPE_VDX_OUT_2 = 23;
  
  public static final int port_MVB_LOG_TYPE_VDX_OUT_2 = 393;
  
  public static final int MVB_LOG_TYPE_VDX_OUT_3 = 24;
  
  public static final int port_MVB_LOG_TYPE_VDX_OUT_3 = 394;
  
  public static final int MVB_LOG_TYPE_DX_IN_STATUS_1 = 31;
  
  public static final int port_MVB_LOG_TYPE_DX_IN_STATUS_1 = 2176;
  
  public static final int MVB_LOG_TYPE_DX_STATUS_1 = 32;
  
  public static final int port_MVB_LOG_TYPE_DX_STATUS_1 = 2177;
  
  public static final int MVB_LOG_TYPE_DX_OUT_STATUS_1 = 33;
  
  public static final int port_MVB_LOG_TYPE_DX_OUT_STATUS_1 = 2184;
  
  public static final int MVB_LOG_BTM_COMMAND_1 = 41;
  
  public static final int port_MVB_LOG_BTM_COMMAND_1 = 648;
  
  public static final int MVB_LOG_BTM_STATUS_1 = 42;
  
  public static final int port_MVB_LOG_BTM_STATUS_1 = 642;
  
  public static final int MVB_LOG_BTM_TGM_1 = 43;
  
  public static final int port_MVB_LOG_BTM_TGM_1 = 643;
  
  public static final int MVB_LOG_BTM_TGM_2 = 44;
  
  public static final int port_MVB_LOG_BTM_TGM_2 = 644;
  
  public static final int MVB_LOG_BTM_TGM_3 = 45;
  
  public static final int port_MVB_LOG_BTM_TGM_3 = 645;
  
  public static final int MVB_LOG_BTM_TGM_4 = 46;
  
  public static final int port_MVB_LOG_BTM_TGM_4 = 646;
  
  public static final int MVB_LOG_BTM_TGM_5 = 47;
  
  public static final int port_MVB_LOG_BTM_TGM_5 = 647;
  
  public static final int MVB_LOG_SDU1 = 51;
  
  public static final int port_MVB_LOG_SDU1 = 256;
  
  public static final int MVB_LOG_SDU2 = 52;
  
  public static final int port_MVB_LOG_SDU2 = 272;
  
  public static final int MVB_LOG_ODO_CONFIG_1 = 61;
  
  public static final int port_MVB_LOG_ODO_CONFIG_1 = 1283;
  
  public static final int MVB_LOG_ODO_MESSAGE_1 = 62;
  
  public static final int port_MVB_LOG_ODO_MESSAGE_1 = 1285;
  
  public static final int MVB_LOG_ODO_MESSAGE_2 = 63;
  
  public static final int port_MVB_LOG_ODO_MESSAGE_2 = 1286;
  
  public static final int MVB_LOG_ODO_BTM_STATUS_1 = 64;
  
  public static final int port_MVB_LOG_ODO_BTM_STATUS_1 = 1284;
  
  public static final int MVB_LOG_PM_LOG_TGM = 71;
  
  public static final int port_MVB_LOG_PM_LOG_TGM = 100;
  
  public static final int MVB_LOG_PM_APP_LOG_TGM = 72;
  
  public static final int port_MVB_LOG_PM_APP_LOG_TGM = 1287;
  
  public static final int MMI_STATUS_REPORT_CHANGED = 81;
  
  public static final int T_PRS_INFO = 91;
  
  public static final int T_ATP_DOWN_DIS_SPEED_TIME = 201;
  
  public static final int T_PERIODIC_SPEED_DISTANCE = 211;
  
  public static final int T_BUTTON_EVENT = 216;
  
  public static final int T_STATUS_COUNTER_BOARD = 221;
  
  public static final int T_STATUS_USB = 222;
  
  public static final int T_STATUS_PRS = 223;
  
  public static final int T_STATUS_SPEEDMETER = 224;
  
  public static final int T_STATUS_DATA_DOWNLOAD = 225;
  
  public static final int T_STATUS_MVB = 227;
  
  public static final int T_STATUS_GPP = 228;
  
  private MMIVariables mmivairalbes = new MMIVariables();
  
  public Vector T_ATP_DOWN_DIS_SPEED_TIME(byte[] data) {
    Vector rtn = new Vector(2);
    int loc = this.mmivairalbes.MMI_O_TRAIN(data[0], data[1], data[2], data[3]);
    int spd = this.mmivairalbes.MMI_V_TRAIN(data[6], data[7]);
    rtn.add(new Integer(spd));
    rtn.add(new Integer(loc));
    return rtn;
  }
  
  public int T_BUTTON_EVENT(byte data) {
    return data;
  }
  
  public int T_STATUS_COUNTER_BOARD(byte data) {
    return data;
  }
  
  public int T_STATUS_USB(byte data) {
    return data;
  }
  
  public int T_STATUS_PRS(byte data) {
    return data;
  }
  
  public int T_STATUS_SPEEDMETER(byte data) {
    return data;
  }
  
  public int T_STATUS_DATA_DOWNLOAD(byte data) {
    return data;
  }
  
  public int T_STATUS_MVB(byte data) {
    return data;
  }
  
  public int T_STATUS_GPP(byte data) {
    return data;
  }
}
