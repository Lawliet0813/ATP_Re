package decode;

import com.MiTAC.TRA.ATP.decode.MMIVariables;
import java.util.Vector;

public class PacketMMI {
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
  
  /**
   * Decodes MMI packet 103 - Confirmed SR (Staff Responsible) Rules.
   * This packet confirms the SR rules that were echoed back.
   * 
   * @param paramArrayOfbyte the raw packet data
   */
  public static void MMI_CONFIRMED_SR_RULES(byte[] paramArrayOfbyte) {
    MMI_ECHOED_SR_RULES(paramArrayOfbyte);
  }
  
  /**
   * Decodes MMI packet 110 - Confirmed Train Data.
   * This packet confirms the train data that was echoed back.
   * 
   * @param paramArrayOfbyte the raw packet data
   */
  public static void MMI_CONFIRMED_TRAIN_DATA(byte[] paramArrayOfbyte) {
    MMI_ECHOED_TRAIN_DATA(paramArrayOfbyte);
  }
  
  /**
   * Decodes MMI packet 14 - Current Driver Data.
   * 
   * <p>Returns a Vector containing:</p>
   * <ol>
   *   <li>NID_DRIVER - Driver identification (bytes 4-11, 8 bytes)</li>
   *   <li>NID_OPERATION - Operation number (bytes 12-15, 4 bytes)</li>
   *   <li>NID_WORKSHIFT - Work shift identification (bytes 16-23, 8 bytes)</li>
   * </ol>
   * 
   * @param paramArrayOfbyte the raw packet data
   * @return Vector with 3 elements: driver ID, operation number, work shift ID
   */
  public static Vector MMI_CURRENT_DRIVER_DATA(byte[] paramArrayOfbyte) {
    Vector vector = new Vector();
    byte[] arrayOfByte1 = { paramArrayOfbyte[4], paramArrayOfbyte[5], paramArrayOfbyte[6], paramArrayOfbyte[7], paramArrayOfbyte[8], paramArrayOfbyte[9], paramArrayOfbyte[10], paramArrayOfbyte[11] };
    vector.add(MMIVariables.MMI_NID_DRIVER(arrayOfByte1));
    byte[] arrayOfByte2 = { paramArrayOfbyte[12], paramArrayOfbyte[13], paramArrayOfbyte[14], paramArrayOfbyte[15] };
    vector.add(MMIVariables.MMI_NID_OPERATION(arrayOfByte2));
    byte[] arrayOfByte3 = { paramArrayOfbyte[16], paramArrayOfbyte[17], paramArrayOfbyte[18], paramArrayOfbyte[19], paramArrayOfbyte[20], paramArrayOfbyte[21], paramArrayOfbyte[22], paramArrayOfbyte[23] };
    vector.add(MMIVariables.MMI_NID_WORKSHIFT(arrayOfByte3));
    return vector;
  }
  
  /**
   * Decodes MMI packet 11 - Current SR (Staff Responsible) Rules.
   * 
   * <p>Returns a Vector containing:</p>
   * <ol>
   *   <li>V_STFF - Staff Responsible speed (bytes 4-5, 2 bytes)</li>
   *   <li>L_STFF - Staff Responsible length/distance (bytes 6-9, 4 bytes)</li>
   * </ol>
   * 
   * @param paramArrayOfbyte the raw packet data
   * @return Vector with 2 elements: SR speed and SR distance
   */
  public static Vector MMI_CURRENT_SR_RULES(byte[] paramArrayOfbyte) {
    Vector vector = new Vector(2);
    vector.add(new Integer(MMIVariables.MMI_V_STFF(paramArrayOfbyte[4], paramArrayOfbyte[5])));
    vector.add(new Long(MMIVariables.MMI_L_STFF(paramArrayOfbyte[6], paramArrayOfbyte[7], paramArrayOfbyte[8], paramArrayOfbyte[9])));
    vector.trimToSize();
    return vector;
  }
  
  /**
   * Decodes MMI packet 6 - Current Train Data.
   * 
   * <p>Returns a Vector containing comprehensive train configuration data:</p>
   * <ol>
   *   <li>M_DATA_EDIT_ENABLE - Data edit enable flags (bytes 4-5)</li>
   *   <li>NC_TRAIN - Train category (bytes 6-7)</li>
   *   <li>L_TRAIN - Train length (bytes 8-9)</li>
   *   <li>V_MAXTRAIN - Maximum train speed (bytes 10-11)</li>
   *   <li>M_AXLELOAD - Axle load (byte 13)</li>
   *   <li>T_BRAKE_SB - Service brake build-up time</li>
   *   <li>T_BRAKE_EB - Emergency brake build-up time</li>
   *   <li>T_CUTOFFTRACTION - Traction cut-off time</li>
   *   <li>A_MAX_ACC - Maximum acceleration</li>
   *   <li>A_MAX_DEC - Maximum deceleration</li>
   *   <li>NID_ENGINE - Engine identification</li>
   *   <li>M_TRAIN_TYPE - Train type</li>
   *   <li>V_MAXTRAIN_x (4 values) - Maximum speeds for different categories</li>
   * </ol>
   * 
   * <p>Also processes variable-length arrays:</p>
   * <ul>
   *   <li>N_TRACTION - Traction system data</li>
   *   <li>N_DSGUARANT - Distance guaranteed data</li>
   *   <li>N_DEGUARANT - Deceleration guaranteed data</li>
   * </ul>
   * 
   * @param paramArrayOfbyte the raw packet data
   * @return Vector containing all train data elements
   */
  public static Vector MMI_CURRENT_TRAIN_DATA(byte[] paramArrayOfbyte) {
    Vector vector = new Vector();
    vector.add(MMIVariables.MMI_M_DATA_EDIT_ENABLE(paramArrayOfbyte[4], paramArrayOfbyte[5]));
    vector.add(MMIVariables.MMI_NC_TRAIN(paramArrayOfbyte[6], paramArrayOfbyte[7]));
    vector.add(new Integer(MMIVariables.MMI_L_TRAIN(paramArrayOfbyte[8], paramArrayOfbyte[9])));
    vector.add(new Integer(MMIVariables.MMI_V_MAXTRAIN(paramArrayOfbyte[10], paramArrayOfbyte[11])));
    MMIVariables.MMI_M_LOADINGGAUGE();
    vector.add(new Double(MMIVariables.MMI_M_AXLELOAD(paramArrayOfbyte[13])));
    int i = 14;
    byte b = paramArrayOfbyte[i];
    byte[] arrayOfByte1 = new byte[b * 2];
    i++;
    for (byte b1 = 0; b1 <= b - 1; b1++) {
      arrayOfByte1[b1 * 2 + 0] = paramArrayOfbyte[i + b1 * 2 + 0];
      arrayOfByte1[b1 * 2 + 1] = paramArrayOfbyte[i + b1 * 2 + 1];
    } 
    MMIVariables.MMI_N_TRACTION(arrayOfByte1);
    i += b * 2;
    b = paramArrayOfbyte[i];
    byte[] arrayOfByte2 = new byte[b * 4];
    i++;
    for (byte b2 = 0; b2 <= b - 1; b2++) {
      arrayOfByte2[b2 * 4 + 0] = paramArrayOfbyte[i + b2 * 4 + 0];
      arrayOfByte2[b2 * 4 + 1] = paramArrayOfbyte[i + b2 * 4 + 1];
      arrayOfByte2[b2 * 4 + 2] = paramArrayOfbyte[i + b2 * 4 + 2];
      arrayOfByte2[b2 * 4 + 3] = paramArrayOfbyte[i + b2 * 4 + 3];
    } 
    MMIVariables.MMI_N_DSGUARANT(arrayOfByte2);
    i += b * 4;
    b = paramArrayOfbyte[i];
    byte[] arrayOfByte3 = new byte[b * 4];
    i++;
    for (byte b3 = 0; b3 <= b - 1; b3++) {
      arrayOfByte3[b3 * 4 + 0] = paramArrayOfbyte[i + b3 * 4 + 0];
      arrayOfByte3[b3 * 4 + 1] = paramArrayOfbyte[i + b3 * 4 + 1];
      arrayOfByte3[b3 * 4 + 2] = paramArrayOfbyte[i + b3 * 4 + 2];
      arrayOfByte3[b3 * 4 + 3] = paramArrayOfbyte[i + b3 * 4 + 3];
    } 
    MMIVariables.MMI_N_DEGUARANT(arrayOfByte3);
    i += b * 4;
    vector.add(new Integer(MMIVariables.MMI_T_BRAKE_SB(paramArrayOfbyte[i], paramArrayOfbyte[++i])));
    vector.add(new Integer(MMIVariables.MMI_T_BRAKE_EB(paramArrayOfbyte[++i], paramArrayOfbyte[++i])));
    vector.add(new Integer(MMIVariables.MMI_T_CUTOFFTRACTION(paramArrayOfbyte[++i], paramArrayOfbyte[++i])));
    vector.add(new Integer(MMIVariables.MMI_A_MAX_ACC(paramArrayOfbyte[++i], paramArrayOfbyte[++i])));
    vector.add(new Integer(MMIVariables.MMI_A_MAX_DEC(paramArrayOfbyte[++i], paramArrayOfbyte[++i])));
    vector.add(new String(MMIVariables.MMI_NID_ENGINE(paramArrayOfbyte[++i], paramArrayOfbyte[++i], paramArrayOfbyte[++i])));
    vector.add(new Integer(MMIVariables.MMI_M_TRAIN_TYPE(paramArrayOfbyte[++i], paramArrayOfbyte[++i])));
    vector.add(new Integer(MMIVariables.MMI_V_MAXTRAIN_x(paramArrayOfbyte[++i], paramArrayOfbyte[++i])));
    vector.add(new Integer(MMIVariables.MMI_V_MAXTRAIN_x(paramArrayOfbyte[++i], paramArrayOfbyte[++i])));
    vector.add(new Integer(MMIVariables.MMI_V_MAXTRAIN_x(paramArrayOfbyte[++i], paramArrayOfbyte[++i])));
    vector.add(new Integer(MMIVariables.MMI_V_MAXTRAIN_x(paramArrayOfbyte[++i], paramArrayOfbyte[++i])));
    return vector;
  }
  
  /**
   * Decodes MMI packet 8 - Driver Message.
   * 
   * <p>Returns a Vector containing:</p>
   * <ol>
   *   <li>I_TEXT - Text message index (byte 4)</li>
   *   <li>Q_TEXT_CLASS - Text class qualifier (byte 5, bit 7)</li>
   *   <li>Q_TEXT_CRITERIA - Text criteria (byte 5, bits 4-6)</li>
   *   <li>Q_TEXT - Text identifier (bytes 6-7)</li>
   * </ol>
   * 
   * @param paramArrayOfbyte the raw packet data
   * @return Vector with 4 elements describing the message
   */
  public static Vector MMI_DRIVER_MESSAGE(byte[] paramArrayOfbyte) {
    Vector vector = new Vector();
    vector.add(new Integer(MMIVariables.MMI_I_TEXT(paramArrayOfbyte[4])));
    vector.add(new Integer(MMIVariables.MMI_Q_TEXT_CLASS(paramArrayOfbyte[5] >> 7 & 0x1)));
    vector.add(new Integer(MMIVariables.MMI_Q_TEXT_CRITERIA(paramArrayOfbyte[5] >> 4 & 0x7)));
    vector.add(new Integer(MMIVariables.MMI_Q_TEXT(paramArrayOfbyte[6], paramArrayOfbyte[7])));
    vector.trimToSize();
    return vector;
  }
  
  /**
   * Decodes MMI packet 111 - Driver Message Acknowledgement.
   * Processes the acknowledgement of a driver message.
   * 
   * @param paramArrayOfbyte the raw packet data containing I_TEXT (byte 4) and Q_ACK (byte 5, bits 4-7)
   */
  public static void MMI_DRIVER_MESSAGE_ACK(byte[] paramArrayOfbyte) {
    MMIVariables.MMI_I_TEXT(paramArrayOfbyte[4]);
    MMIVariables.MMI_Q_ACK((paramArrayOfbyte[5] & 0xF0) >> 4);
  }
  
  /**
   * Decodes MMI packet 101 - Driver Request.
   * 
   * @param paramArrayOfbyte the raw packet data
   * @return M_REQUEST - The type of request made by the driver (byte 4)
   */
  public static int MMI_DRIVER_REQUEST(byte[] paramArrayOfbyte) {
    return MMIVariables.MMI_M_REQUEST(paramArrayOfbyte[4]);
  }
  
  public static Vector MMI_DYMANIC(byte[] paramArrayOfbyte) {
    Vector vector = new Vector();
    int i = MMIVariables.MMI_V_TRAIN(paramArrayOfbyte[4], paramArrayOfbyte[5]);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_A_TRAIN(paramArrayOfbyte[6], paramArrayOfbyte[7]);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_O_TRAIN(paramArrayOfbyte[8], paramArrayOfbyte[9], paramArrayOfbyte[10], paramArrayOfbyte[11]);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_O_BRAKETARGET(paramArrayOfbyte[12], paramArrayOfbyte[13], paramArrayOfbyte[14], paramArrayOfbyte[15]);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_V_TARGET(paramArrayOfbyte[16], paramArrayOfbyte[17]);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_T_INTERVENWAR(paramArrayOfbyte[18], paramArrayOfbyte[19]);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_V_PERMITTED(paramArrayOfbyte[20], paramArrayOfbyte[21]);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_V_RELEASE(paramArrayOfbyte[22], paramArrayOfbyte[23]);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_V_INTERVENTION(paramArrayOfbyte[24], paramArrayOfbyte[25]);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_M_WARNING((paramArrayOfbyte[26] & 0xF0) >> 4);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_M_SLIP((paramArrayOfbyte[26] & 0x8) >> 3);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_M_SLIDE((paramArrayOfbyte[26] & 0x4) >> 2);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_O_BCSP(paramArrayOfbyte[27], paramArrayOfbyte[28], paramArrayOfbyte[29], paramArrayOfbyte[30]);
    vector.add(new Integer(i));
    return vector;
  }
  
  public static void MMI_ECHOED_SR_RULES(byte[] paramArrayOfbyte) {
    MMIVariables.MMI_L_STFF((byte)(paramArrayOfbyte[4] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[5] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[6] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[7] ^ 0xFFFFFFFF));
    MMIVariables.MMI_V_STFF((byte)(paramArrayOfbyte[8] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[9] ^ 0xFFFFFFFF));
  }
  
  public static void MMI_ECHOED_TRAIN_DATA(byte[] paramArrayOfbyte) {
    MMIVariables.MMI_V_MAXTRAIN_x((byte)(paramArrayOfbyte[4] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[5] ^ 0xFFFFFFFF));
    MMIVariables.MMI_V_MAXTRAIN_x((byte)(paramArrayOfbyte[6] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[7] ^ 0xFFFFFFFF));
    MMIVariables.MMI_V_MAXTRAIN_x((byte)(paramArrayOfbyte[8] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[9] ^ 0xFFFFFFFF));
    MMIVariables.MMI_V_MAXTRAIN_x((byte)(paramArrayOfbyte[10] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[11] ^ 0xFFFFFFFF));
    MMIVariables.MMI_M_TRAIN_TYPE((byte)(paramArrayOfbyte[12] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[13] ^ 0xFFFFFFFF));
    MMIVariables.MMI_NID_ENGINE((byte)(paramArrayOfbyte[14] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[15] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[16] ^ 0xFFFFFFFF));
    MMIVariables.MMI_A_MAX_DEC((byte)(paramArrayOfbyte[17] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[18] ^ 0xFFFFFFFF));
    MMIVariables.MMI_A_MAX_ACC((byte)(paramArrayOfbyte[19] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[20] ^ 0xFFFFFFFF));
    MMIVariables.MMI_T_CUTOFFTRACTION((byte)(paramArrayOfbyte[21] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[22] ^ 0xFFFFFFFF));
    MMIVariables.MMI_T_BRAKE_EB((byte)(paramArrayOfbyte[23] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[24] ^ 0xFFFFFFFF));
    MMIVariables.MMI_T_BRAKE_SB((byte)(paramArrayOfbyte[25] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[26] ^ 0xFFFFFFFF));
    int i = 27;
    int j = paramArrayOfbyte[i] ^ 0xFFFFFFFF;
    byte[] arrayOfByte1 = new byte[j * 4];
    i++;
    for (byte b1 = 0; b1 <= j - 1; b1++) {
      arrayOfByte1[b1 * 4 + 0] = (byte)(paramArrayOfbyte[i + b1 * 4 + 0] ^ 0xFFFFFFFF);
      arrayOfByte1[b1 * 4 + 1] = (byte)(paramArrayOfbyte[i + b1 * 4 + 1] ^ 0xFFFFFFFF);
      arrayOfByte1[b1 * 4 + 2] = (byte)(paramArrayOfbyte[i + b1 * 4 + 2] ^ 0xFFFFFFFF);
      arrayOfByte1[b1 * 4 + 3] = (byte)(paramArrayOfbyte[i + b1 * 4 + 3] ^ 0xFFFFFFFF);
    } 
    MMIVariables.MMI_N_DEGUARANT(arrayOfByte1);
    i += j * 4;
    j = paramArrayOfbyte[i] ^ 0xFFFFFFFF;
    byte[] arrayOfByte2 = new byte[j * 4];
    i++;
    for (byte b2 = 0; b2 <= j - 1; b2++) {
      arrayOfByte2[b2 * 4 + 0] = (byte)(paramArrayOfbyte[i + b2 * 4 + 0] ^ 0xFFFFFFFF);
      arrayOfByte2[b2 * 4 + 1] = (byte)(paramArrayOfbyte[i + b2 * 4 + 1] ^ 0xFFFFFFFF);
      arrayOfByte2[b2 * 4 + 2] = (byte)(paramArrayOfbyte[i + b2 * 4 + 2] ^ 0xFFFFFFFF);
      arrayOfByte2[b2 * 4 + 3] = (byte)(paramArrayOfbyte[i + b2 * 4 + 3] ^ 0xFFFFFFFF);
    } 
    MMIVariables.MMI_N_DSGUARANT(arrayOfByte2);
    i += j * 4;
    j = paramArrayOfbyte[i] ^ 0xFFFFFFFF;
    byte[] arrayOfByte3 = new byte[j * 2];
    i++;
    for (byte b3 = 0; b3 <= j - 1; b3++) {
      arrayOfByte3[b3 * 2 + 0] = (byte)(paramArrayOfbyte[i + b3 * 2 + 0] ^ 0xFFFFFFFF);
      arrayOfByte3[b3 * 2 + 1] = (byte)(paramArrayOfbyte[i + b3 * 2 + 1] ^ 0xFFFFFFFF);
    } 
    MMIVariables.MMI_N_TRACTION(arrayOfByte3);
    i += j * 2;
    MMIVariables.MMI_M_AXLELOAD((byte)(paramArrayOfbyte[i] ^ 0xFFFFFFFF));
    i++;
    MMIVariables.MMI_M_LOADINGGAUGE();
    MMIVariables.MMI_V_MAXTRAIN((byte)(paramArrayOfbyte[++i] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[++i] ^ 0xFFFFFFFF));
    MMIVariables.MMI_L_TRAIN((byte)(paramArrayOfbyte[++i] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[++i] ^ 0xFFFFFFFF));
    MMIVariables.MMI_NC_TRAIN((byte)(paramArrayOfbyte[++i] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[++i] ^ 0xFFFFFFFF));
    MMIVariables.MMI_M_DATA_EDIT_ENABLE((byte)(paramArrayOfbyte[++i] ^ 0xFFFFFFFF), (byte)(paramArrayOfbyte[++i] ^ 0xFFFFFFFF));
  }
  
  public static void MMI_FAILURE_REPORT_ACK(byte[] paramArrayOfbyte) {
    MMIVariables.MMI_I_FAILURE_NUMBER(paramArrayOfbyte[4], paramArrayOfbyte[5]);
  }
  
  public static Vector MMI_FAILURE_REPORT_ATP(byte[] paramArrayOfbyte) {
    Vector vector = new Vector();
    int i = MMIVariables.MMI_Q_FAILURE_CLASS(paramArrayOfbyte[4]);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_I_UNIT((paramArrayOfbyte[5] & 0xF0) >> 4);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_Q_FAILURE_SEVERITY(paramArrayOfbyte[5] & 0xF);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_I_FAILURE_NUMBER(paramArrayOfbyte[6], paramArrayOfbyte[7]);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_Q_FAILURE_CRITERIA((paramArrayOfbyte[8] & 0xF0) >> 4);
    vector.add(new Integer(i));
    vector.trimToSize();
    return vector;
  }
  
  public static void MMI_FAILURE_REPORT_MMI(byte[] paramArrayOfbyte) {
    MMIVariables.MMI_I_FAILURE_NUMBER(paramArrayOfbyte[4], paramArrayOfbyte[5]);
  }
  
  public static String MMI_FORCED_DRIVER_REQUEST(byte[] paramArrayOfbyte) {
    return MMIVariables.MMI_M_FORCED_REQUEST(paramArrayOfbyte[3], paramArrayOfbyte[4]);
  }
  
  public static Vector MMI_GEO_POSITION(byte[] paramArrayOfbyte) {
    Vector vector = new Vector(2);
    vector.add(new Integer(MMIVariables.MMI_M_ABSOLUTPOS(paramArrayOfbyte[4], paramArrayOfbyte[5], paramArrayOfbyte[6])));
    vector.add(new Integer(MMIVariables.MMI_M_RELATIVPOS(paramArrayOfbyte[7], paramArrayOfbyte[8])));
    vector.trimToSize();
    return vector;
  }
  
  public static void MMI_NEW_DRIVER_DATA(byte[] paramArrayOfbyte) {
    MMI_CURRENT_DRIVER_DATA(paramArrayOfbyte);
  }
  
  public static void MMI_NEW_RBC_DATA(byte[] paramArrayOfbyte) {
    MMIVariables.MMI_NID_RBC(paramArrayOfbyte[4], paramArrayOfbyte[5], paramArrayOfbyte[6]);
    byte[] arrayOfByte = new byte[8];
    for (byte b = 0; b <= 7; b++)
      arrayOfByte[b] = paramArrayOfbyte[7 + b]; 
    MMIVariables.MMI_NID_RADIO(arrayOfByte);
  }
  
  public static void MMI_NEW_SR_RULES(byte[] paramArrayOfbyte) {
    MMI_CURRENT_SR_RULES(paramArrayOfbyte);
  }
  
  public static void MMI_NEW_TRAIN_DATA(byte[] paramArrayOfbyte) {
    MMI_CURRENT_TRAIN_DATA(paramArrayOfbyte);
  }
  
  public static void MMI_RU_DATA(byte[] paramArrayOfbyte) {}
  
  public static void MMI_RU_DATA_FROM_CAB_1(byte[] paramArrayOfbyte) {}
  
  public static void MMI_RU_DATA_FROM_CAB_2(byte[] paramArrayOfbyte) {}
  
  public static void MMI_SELECT_STM(byte[] paramArrayOfbyte) {
    MMIVariables.MMI_NID_STM();
  }
  
  public static void MMI_SELECT_STM_REQUEST(byte[] paramArrayOfbyte) {
    MMIVariables.MMI_NID_STM();
  }
  
  public static long MMI_SET_TIME_ATP(byte[] paramArrayOfbyte) {
    return MMIVariables.MMI_T_UTC(paramArrayOfbyte[4], paramArrayOfbyte[5], paramArrayOfbyte[6], paramArrayOfbyte[7]);
  }
  
  public static void MMI_SET_TIME_MMI(byte[] paramArrayOfbyte) {
    MMIVariables.MMI_T_UTC(paramArrayOfbyte[4], paramArrayOfbyte[5], paramArrayOfbyte[6], paramArrayOfbyte[7]);
  }
  
  public static int MMI_START_ATP(byte[] paramArrayOfbyte) {
    return MMIVariables.MMI_M_START_REQ(paramArrayOfbyte[4]);
  }
  
  public static Vector MMI_START_MMI(byte[] paramArrayOfbyte) {
    Vector vector = new Vector(5);
    vector.add(new Integer(MMIVariables.MMI_M_START_INFO(paramArrayOfbyte[4])));
    vector.add(new Integer(MMIVariables.MMI_M_START_STATUS(paramArrayOfbyte[5])));
    vector.add(new Integer(MMIVariables.MMI_M_TYPE(paramArrayOfbyte[6])));
    vector.add(MMIVariables.MMI_M_IF_VER(paramArrayOfbyte[7], paramArrayOfbyte[8], paramArrayOfbyte[9]));
    vector.add(MMIVariables.MMI_M_SW_VER(paramArrayOfbyte[10], paramArrayOfbyte[11], paramArrayOfbyte[12]));
    vector.trimToSize();
    return vector;
  }
  
  public static Vector MMI_STATUS(byte[] paramArrayOfbyte) {
    Vector vector = new Vector();
    int i = MMIVariables.MMI_M_ADHESION(paramArrayOfbyte[4]);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_M_MODE((paramArrayOfbyte[5] & 0xF0) >> 4);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_M_LEVEL(paramArrayOfbyte[5] & 0xF);
    i = MMIVariables.MMI_M_EMERBRAKE((paramArrayOfbyte[6] & 0xC0) >> 6);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_M_SERVICEBRAKE(paramArrayOfbyte[6] >> 4 & 0x3);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_M_OVERRIDE_EOA(paramArrayOfbyte[6] & 0x8);
    vector.add(new Integer(i));
    i = MMIVariables.MMI_M_TRIP(paramArrayOfbyte[6] & 0x4);
    i = MMIVariables.MMI_M_ACTIVE_CABIN(paramArrayOfbyte[6] & 0x3);
    vector.add(new Integer(i));
    vector.trimToSize();
    return vector;
  }
  
  public static int MMI_STATUS_REPORT(byte[] paramArrayOfbyte) {
    return MMIVariables.MMI_M_MMI_STATUS(paramArrayOfbyte[4]);
  }
  
  public static void MMI_STM_DATA_FROM_CAB_1(byte[] paramArrayOfbyte) {}
  
  public static void MMI_STM_DATA_FROM_CAB_2(byte[] paramArrayOfbyte) {}
  
  public static void MMI_STM_DATA_TO_CAB_1(byte[] paramArrayOfbyte) {}
  
  public static void MMI_STM_DATA_TO_CAB_2(byte[] paramArrayOfbyte) {}
  
  public static int MMI_TEST_REQUEST(byte[] paramArrayOfbyte) {
    return MMIVariables.MMI_M_TEST_(paramArrayOfbyte[4], paramArrayOfbyte[5]);
  }
  
  public static void MMI_TEST_RESULT(byte[] paramArrayOfbyte) {
    MMIVariables.MMI_M_TEST_(paramArrayOfbyte[4], paramArrayOfbyte[5]);
  }
  
  public static void MMI_TRACK_DESCRIPTION(byte[] paramArrayOfbyte) {
    MMIVariables.MMI_V_MRSP_CURR(paramArrayOfbyte[4], paramArrayOfbyte[5]);
    int i = 6;
    byte b = paramArrayOfbyte[6];
    byte[] arrayOfByte1 = new byte[b * 6];
    i++;
    for (byte b1 = 0; b1 <= b - 1; b1++) {
      arrayOfByte1[b1 + 0] = paramArrayOfbyte[i + 6 * b1 + 0];
      arrayOfByte1[b1 + 1] = paramArrayOfbyte[i + 6 * b1 + 1];
      arrayOfByte1[b1 + 2] = paramArrayOfbyte[i + 6 * b1 + 2];
      arrayOfByte1[b1 + 3] = paramArrayOfbyte[i + 6 * b1 + 3];
      arrayOfByte1[b1 + 4] = paramArrayOfbyte[i + 6 * b1 + 4];
      arrayOfByte1[b1 + 5] = paramArrayOfbyte[i + 6 * b1 + 5];
    } 
    MMIVariables.MMI_N_MRSP(arrayOfByte1);
    i += b * 6;
    MMIVariables.MMI_G_GRADIENT_CURR(paramArrayOfbyte[i], paramArrayOfbyte[++i]);
    b = paramArrayOfbyte[++i];
    byte[] arrayOfByte2 = new byte[b * 6];
    i++;
    for (byte b2 = 0; b2 <= b - 1; b2++) {
      arrayOfByte2[b2 + 0] = paramArrayOfbyte[i + 6 * b2 + 0];
      arrayOfByte2[b2 + 1] = paramArrayOfbyte[i + 6 * b2 + 1];
      arrayOfByte2[b2 + 2] = paramArrayOfbyte[i + 6 * b2 + 2];
      arrayOfByte2[b2 + 3] = paramArrayOfbyte[i + 6 * b2 + 3];
      arrayOfByte2[b2 + 4] = paramArrayOfbyte[i + 6 * b2 + 4];
      arrayOfByte2[b2 + 5] = paramArrayOfbyte[i + 6 * b2 + 5];
    } 
    MMIVariables.MMI_N_GRADIENT(arrayOfByte2);
    i += b * 6;
    b = paramArrayOfbyte[i];
    byte[] arrayOfByte3 = new byte[b * 9];
    i++;
    for (byte b3 = 0; b3 <= b - 1; b3++) {
      arrayOfByte3[b3 + 0] = paramArrayOfbyte[i + 9 * b3 + 0];
      arrayOfByte3[b3 + 1] = paramArrayOfbyte[i + 9 * b3 + 1];
      arrayOfByte3[b3 + 2] = paramArrayOfbyte[i + 9 * b3 + 2];
      arrayOfByte3[b3 + 3] = paramArrayOfbyte[i + 9 * b3 + 3];
      arrayOfByte3[b3 + 4] = paramArrayOfbyte[i + 9 * b3 + 4];
      arrayOfByte3[b3 + 5] = paramArrayOfbyte[i + 9 * b3 + 5];
      arrayOfByte3[b3 + 6] = paramArrayOfbyte[i + 9 * b3 + 6];
      arrayOfByte3[b3 + 7] = paramArrayOfbyte[i + 9 * b3 + 7];
      arrayOfByte3[b3 + 8] = paramArrayOfbyte[i + 9 * b3 + 8];
    } 
    MMIVariables.MMI_N_TRACKC(arrayOfByte3);
  }
}
