package decode;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class MMIVariables {
  public static int MMI_A_DEGUARANTEED(byte paramByte1, byte paramByte2) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2);
  }
  
  public static int MMI_A_DSGUARANTEED(byte paramByte1, byte paramByte2) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2);
  }
  
  public static int MMI_A_MAX_ACC(byte paramByte1, byte paramByte2) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2);
  }
  
  public static int MMI_A_MAX_DEC(byte paramByte1, byte paramByte2) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2);
  }
  
  public static int MMI_A_TRAIN(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static int MMI_G_GRADIENT(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static int MMI_G_GRADIENT_CURR(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static int MMI_I_FAILURE_NUMBER(byte paramByte1, byte paramByte2) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2);
  }
  
  public static int MMI_I_TEXT(byte paramByte) {
    return Byte2Number.getUnsigned(paramByte);
  }
  
  public static int MMI_I_UNIT(int paramInt) {
    return ++paramInt;
  }
  
  public static int MMI_L_PACKET(byte paramByte1, byte paramByte2) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2);
  }
  
  public static long MMI_L_STFF(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2, paramByte3, paramByte4);
  }
  
  public static long MMI_L_TRACKCOND(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2, paramByte3, paramByte4);
  }
  
  public static int MMI_L_TRAIN(byte paramByte1, byte paramByte2) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2);
  }
  
  public static int MMI_M_ABSOLUTPOS(byte paramByte1, byte paramByte2, byte paramByte3) {
    return Byte2Number.getSigned(paramByte1, paramByte2, paramByte3);
  }
  
  public static int MMI_M_ACTIVE_CABIN(int paramInt) {
    return paramInt;
  }
  
  public static int MMI_M_ADHESION(byte paramByte) {
    return Byte2Number.getUnsigned(paramByte);
  }
  
  public static double MMI_M_AXLELOAD(byte paramByte) {
    double d = Byte2Number.getUnsigned(paramByte);
    if (0.0D <= d && d <= 80.0D) {
      d *= 0.5D;
      return d;
    } 
    return (d == 126.0D) ? 41.0D : 100.0D;
  }
  
  public static String MMI_M_DATA_EDIT_ENABLE(byte paramByte1, byte paramByte2) {
    String str = "";
    for (byte b = 0; b <= 15; b++) {
      if (((paramByte1 << 8 & paramByte2) >> b & 0x1) == 1) {
        str = str + "1";
      } else {
        str = str + "0";
      } 
    } 
    return str;
  }
  
  public static int MMI_M_EMERBRAKE(int paramInt) {
    return paramInt;
  }
  
  public static String MMI_M_FORCED_REQUEST(byte paramByte1, byte paramByte2) {
    StringBuffer stringBuffer = new StringBuffer();
    for (byte b1 = 0; b1 < 8; b1++)
      stringBuffer.insert(0, paramByte2 >> b1 & 0x1); 
    for (byte b2 = 0; b2 < 8; b2++)
      stringBuffer.insert(0, paramByte1 >> b2 & 0x1); 
    return stringBuffer.toString();
  }
  
  public static String MMI_M_IF_VER(byte paramByte1, byte paramByte2, byte paramByte3) {
    int i = Byte2Number.getSigned(paramByte1);
    int j = Byte2Number.getSigned(paramByte2);
    int k = Byte2Number.getSigned(paramByte3);
    return i + "." + j + "." + k;
  }
  
  public static int MMI_M_LEVEL(int paramInt) {
    return paramInt;
  }
  
  public static void MMI_M_LOADINGGAUGE() {}
  
  public static int MMI_M_MMI_STATUS(byte paramByte) {
    return Byte2Number.getUnsigned(paramByte);
  }
  
  public static int MMI_M_MODE(int paramInt) {
    return paramInt;
  }
  
  public static int MMI_M_OVERRIDE_EOA(int paramInt) {
    return paramInt;
  }
  
  public static int MMI_M_PACKET(byte paramByte) {
    return Byte2Number.getUnsigned(paramByte);
  }
  
  public static int MMI_M_RELATIVPOS(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static int MMI_M_REQUEST(byte paramByte) {
    return Byte2Number.getUnsigned(paramByte);
  }
  
  public static void MMI_M_RU_DATA() {}
  
  public static int MMI_M_SERVICEBRAKE(int paramInt) {
    return paramInt;
  }
  
  public static int MMI_M_SLIDE(int paramInt) {
    return paramInt;
  }
  
  public static int MMI_M_SLIP(int paramInt) {
    return paramInt;
  }
  
  public static int MMI_M_START_INFO(byte paramByte) {
    return Byte2Number.getUnsigned(paramByte);
  }
  
  public static int MMI_M_START_REQ(byte paramByte) {
    return Byte2Number.getUnsigned(paramByte);
  }
  
  public static int MMI_M_START_STATUS(byte paramByte) {
    return Byte2Number.getUnsigned(paramByte);
  }
  
  public static void MMI_M_STM_DATA() {}
  
  public static String MMI_M_SW_VER(byte paramByte1, byte paramByte2, byte paramByte3) {
    int i = Byte2Number.getSigned(paramByte1);
    int j = Byte2Number.getSigned(paramByte2);
    int k = Byte2Number.getSigned(paramByte3);
    return i + "." + j + "." + k;
  }
  
  public static int MMI_M_TEST_(byte paramByte1, byte paramByte2) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2);
  }
  
  public static int MMI_M_TRACKCOND(byte paramByte) {
    return Byte2Number.getUnsigned(paramByte);
  }
  
  public static void MMI_M_TRACTION() {}
  
  public static int MMI_M_TRAIN_TYPE(byte paramByte1, byte paramByte2) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2);
  }
  
  public static int MMI_M_TRIP(int paramInt) {
    return paramInt;
  }
  
  public static int MMI_M_TYPE(byte paramByte) {
    return Byte2Number.getUnsigned(paramByte);
  }
  
  public static void MMI_M_VOLTAGE() {}
  
  public static int MMI_M_WARNING(int paramInt) {
    return paramInt;
  }
  
  public static String MMI_NC_TRAIN(byte paramByte1, byte paramByte2) {
    byte[] arrayOfByte = new byte[2];
    arrayOfByte[0] = paramByte1;
    arrayOfByte[1] = paramByte2;
    return HexCode.getBinaryInteger(arrayOfByte);
  }
  
  public static String MMI_NID_DRIVER(byte[] paramArrayOfbyte) {
    return HexCode.decodeToChar(paramArrayOfbyte).trim();
  }
  
  public static String MMI_NID_ENGINE(byte paramByte1, byte paramByte2, byte paramByte3) {
    byte[] arrayOfByte = new byte[1];
    arrayOfByte[0] = paramByte1;
    String str = HexCode.decodeToChar(arrayOfByte);
    str = str + Byte2Number.getUnsigned(paramByte2, paramByte3);
    return " ";
  }
  
  public static String MMI_NID_OPERATION(byte[] paramArrayOfbyte) {
    null = "" + HexCode.getHexA_String(paramArrayOfbyte[0]);
    null = null + HexCode.getHexA_String(paramArrayOfbyte[1]);
    byte[] arrayOfByte = new byte[2];
    arrayOfByte[0] = paramArrayOfbyte[2];
    arrayOfByte[1] = paramArrayOfbyte[3];
    return null + HexCode.decodeToChar(arrayOfByte);
  }
  
  public static String MMI_NID_RADIO(byte[] paramArrayOfbyte) {
    return HexCode.decodeToChar(paramArrayOfbyte).trim();
  }
  
  public static int MMI_NID_RBC(byte paramByte1, byte paramByte2, byte paramByte3) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2, paramByte3);
  }
  
  public static void MMI_NID_STM() {}
  
  public static String MMI_NID_WORKSHIFT(byte[] paramArrayOfbyte) {
    return HexCode.decodeToChar(paramArrayOfbyte).trim();
  }
  
  public static void MMI_N_DEGUARANT(byte[] paramArrayOfbyte) {
    for (byte b = 0; b <= paramArrayOfbyte.length / 4 - 1; b++) {
      MMI_V_DEC_LIMIT_SE(paramArrayOfbyte[4 * b + 0], paramArrayOfbyte[4 * b + 1]);
      MMI_A_DEGUARANTEED(paramArrayOfbyte[4 * b + 2], paramArrayOfbyte[4 * b + 3]);
    } 
  }
  
  public static void MMI_N_DSGUARANT(byte[] paramArrayOfbyte) {
    for (byte b = 0; b <= paramArrayOfbyte.length / 4 - 1; b++) {
      MMI_V_DEC_LIMIT_SE(paramArrayOfbyte[4 * b + 0], paramArrayOfbyte[4 * b + 1]);
      MMI_A_DSGUARANTEED(paramArrayOfbyte[4 * b + 2], paramArrayOfbyte[4 * b + 3]);
    } 
  }
  
  public static void MMI_N_GRADIENT(byte[] paramArrayOfbyte) {
    for (byte b = 0; b <= paramArrayOfbyte.length / 6 - 1; b++) {
      MMI_G_GRADIENT(paramArrayOfbyte[6 * b + 0], paramArrayOfbyte[6 * b + 1]);
      MMI_O_GRADIENT(paramArrayOfbyte[6 * b + 2], paramArrayOfbyte[6 * b + 3], paramArrayOfbyte[6 * b + 4], paramArrayOfbyte[6 * b + 5]);
    } 
  }
  
  public static void MMI_N_MRSP(byte[] paramArrayOfbyte) {
    for (byte b = 0; b <= paramArrayOfbyte.length / 6 - 1; b++) {
      MMI_V_MRSP(paramArrayOfbyte[6 * b + 0], paramArrayOfbyte[6 * b + 1]);
      MMI_O_MRSP(paramArrayOfbyte[6 * b + 2], paramArrayOfbyte[6 * b + 3], paramArrayOfbyte[6 * b + 4], paramArrayOfbyte[6 * b + 5]);
    } 
  }
  
  public static void MMI_N_TRACKC(byte[] paramArrayOfbyte) {
    for (byte b = 0; b <= paramArrayOfbyte.length / 9 - 1; b++) {
      MMI_O_TRACKCOND(paramArrayOfbyte[9 * b + 0], paramArrayOfbyte[9 * b + 1], paramArrayOfbyte[9 * b + 2], paramArrayOfbyte[9 * b + 3]);
      MMI_M_TRACKCOND(paramArrayOfbyte[9 * b + 4]);
      MMI_L_TRACKCOND(paramArrayOfbyte[9 * b + 5], paramArrayOfbyte[9 * b + 6], paramArrayOfbyte[9 * b + 7], paramArrayOfbyte[9 * b + 8]);
    } 
  }
  
  public static void MMI_N_TRACTION(byte[] paramArrayOfbyte) {
    MMI_M_VOLTAGE();
  }
  
  public static int MMI_O_BCSP(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
    return Byte2Number.getSigned(paramByte1, paramByte2, paramByte3, paramByte4);
  }
  
  public static int MMI_O_BRAKETARGET(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
    return Byte2Number.getSigned(paramByte1, paramByte2, paramByte3, paramByte4);
  }
  
  public static int MMI_O_GRADIENT(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
    return Byte2Number.getSigned(paramByte1, paramByte2, paramByte3, paramByte4);
  }
  
  public static int MMI_O_MRSP(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
    return Byte2Number.getSigned(paramByte1, paramByte2, paramByte3, paramByte4);
  }
  
  public static int MMI_O_TRACKCOND(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
    return Byte2Number.getSigned(paramByte1, paramByte2, paramByte3, paramByte4);
  }
  
  public static int MMI_O_TRAIN(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
    return Byte2Number.getSigned(paramByte1, paramByte2, paramByte3, paramByte4);
  }
  
  public static int MMI_Q_ACK(int paramInt) {
    return paramInt;
  }
  
  public static int MMI_Q_FAILURE_CLASS(byte paramByte) {
    return Byte2Number.getUnsigned(paramByte);
  }
  
  public static int MMI_Q_FAILURE_CRITERIA(int paramInt) {
    return paramInt;
  }
  
  public static int MMI_Q_FAILURE_SEVERITY(int paramInt) {
    return paramInt;
  }
  
  public static int MMI_Q_TEXT(byte paramByte1, byte paramByte2) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2);
  }
  
  public static int MMI_Q_TEXT_CLASS(int paramInt) {
    return paramInt;
  }
  
  public static int MMI_Q_TEXT_CRITERIA(int paramInt) {
    return paramInt;
  }
  
  public static int MMI_T_BRAKE_EB(byte paramByte1, byte paramByte2) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2);
  }
  
  public static int MMI_T_BRAKE_SB(byte paramByte1, byte paramByte2) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2);
  }
  
  public static int MMI_T_CUTOFFTRACTION(byte paramByte1, byte paramByte2) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2);
  }
  
  public static int MMI_T_INTERVENWAR(byte paramByte1, byte paramByte2) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2);
  }
  
  public static long MMI_T_UTC(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
    return Byte2Number.getUnsigned(paramByte1, paramByte2, paramByte3, paramByte4);
  }
  
  public static int MMI_V_DEC_LIMIT_SE(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static int MMI_V_INTERVENTION(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static int MMI_V_MAXTRAIN(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static int MMI_V_MAXTRAIN_x(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static int MMI_V_MRSP(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static int MMI_V_MRSP_CURR(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static int MMI_V_PERMITTED(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static int MMI_V_RELEASE(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static int MMI_V_STFF(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static int MMI_V_TARGET(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static int MMI_V_TRAIN(byte paramByte1, byte paramByte2) {
    return Byte2Number.getSigned(paramByte1, paramByte2);
  }
  
  public static String MMI_X_TEXT(byte[] paramArrayOfbyte) {
    return HexCode.decodeToChar(paramArrayOfbyte).trim();
  }
}
