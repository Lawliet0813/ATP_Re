package decoder;

import com.MiTAC.TRA.ATP.decoder.MMIVariables;
import java.util.Vector;

public class PacketMMI {
  public static final int MMI_START_ATP = 0;
  
  public static final int MMI_DYNAMIC = 1;
  
  public static final int MMI_STATUS = 2;
  
  public static final int MMI_SET_TIME_ATP = 3;
  
  public static final int MMI_TRACK_DESCRIPTION = 4;
  
  public static final int MMI_GEO_POSITION = 5;
  
  public static final int MMI_CURRENT_TRAIN_DATA = 6;
  
  public static final int MMI_FORCED_DRIVER_REQUEST = 7;
  
  public static final int MMI_DRIVER_MESSAGE = 8;
  
  public static final int MMI_FAILURE_REPORT_ATP = 9;
  
  public static final int MMI_ECHOED_TRAIN_DATA = 10;
  
  public static final int MMI_CURRENT_SR_RULES = 11;
  
  public static final int MMI_ECHOED_SR_RULES = 12;
  
  public static final int MMI_CURRENT_DRIVER_DATA = 14;
  
  public static final int MMI_TEST_REQUEST = 15;
  
  public static final int MMI_SELECT_STM_REQUEST = 16;
  
  public static final int MMI_STM_DATA_TO_CAB_1 = 17;
  
  public static final int MMI_STM_DATA_TO_CAB_2 = 18;
  
  public static final int MMI_RU_DATA = 19;
  
  public static final int MMI_START_MMI = 100;
  
  public static final int MMI_DRIVER_REQUEST = 101;
  
  public static final int MMI_STATUS_REPORT = 102;
  
  public static final int MMI_CONFIRMED_SR_RULES = 103;
  
  public static final int MMI_NEW_DRIVER_DATA = 104;
  
  public static final int MMI_NEW_SR_RULES = 106;
  
  public static final int MMI_NEW_TRAIN_DATA = 107;
  
  public static final int MMI_SET_TIME_MMI = 109;
  
  public static final int MMI_CONFIRMED_TRAIN_DATA = 110;
  
  public static final int MMI_DRIVER_MESSAGE_ACK = 111;
  
  public static final int MMI_NEW_RBC_DATA = 112;
  
  public static final int MMI_FAILURE_REPORT_MMI = 113;
  
  public static final int MMI_TEST_RESULT = 114;
  
  public static final int MMI_FAILURE_REPORT_ACK = 115;
  
  public static final int MMI_SELECT_STM = 116;
  
  public static final int MMI_STM_DATA_FROM_CAB_1 = 117;
  
  public static final int MMI_STM_DATA_FROM_CAB_2 = 118;
  
  public static final int MMI_RU_DATA_FROM_CAB_1 = 119;
  
  public static final int MMI_RU_DATA_FROM_CAB_2 = 120;
  
  private MMIVariables mmivariable = new MMIVariables();
  
  public int MMI_START_ATP(byte[] bd) {
    return this.mmivariable.MMI_M_START_REQ(bd[3]);
  }
  
  public Vector MMI_DYMANIC(byte[] bd) {
    Vector tmp = new Vector();
    int record = this.mmivariable.MMI_V_TRAIN(bd[3], bd[4]);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_A_TRAIN(bd[5], bd[6]);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_O_TRAIN(bd[7], bd[8], bd[9], bd[10]);
    record = (record >= 1000000000) ? (record - 1000000000) : record;
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_O_BRAKETARGET(bd[11], bd[12], bd[13], bd[14]);
    record = (record >= 1000000000) ? (record - 1000000000) : record;
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_V_TARGET(bd[15], bd[16]);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_T_INTERVENWAR(bd[17], bd[18]);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_V_PERMITTED(bd[19], bd[20]);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_V_RELEASE(bd[21], bd[22]);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_V_INTERVENTION(bd[23], bd[24]);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_M_WARNING((bd[25] & 0xF0) >> 4);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_M_SLIP((bd[25] & 0x8) >> 3);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_M_SLIDE((bd[25] & 0x4) >> 2);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_O_BCSP(bd[26], bd[27], bd[28], bd[29]);
    tmp.add(new Integer(record));
    return tmp;
  }
  
  public Vector MMI_STATUS(byte[] bd) {
    Vector tmp = new Vector();
    int record = this.mmivariable.MMI_M_ADHESION(bd[3]);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_M_MODE((bd[4] & 0xF0) >> 4);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_M_LEVEL(bd[4] & 0xF);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_M_EMERBRAKE((bd[5] & 0xC0) >> 6);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_M_SERVICEBRAKE(bd[5] >> 4 & 0x3);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_M_OVERRIDE_EOA(bd[5] & 0x8);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_M_TRIP(bd[5] & 0x4);
    tmp.add(new Integer(record));
    record = this.mmivariable.MMI_M_ACTIVE_CABIN(bd[5] & 0x3);
    tmp.add(new Integer(record));
    return tmp;
  }
  
  public Vector MMI_CURRENT_DRIVER_DATA(byte[] bd) {
    Vector rtn = new Vector();
    byte[] driverid = { bd[3], bd[4], bd[5], bd[6], bd[7], bd[8], bd[9], bd[10] };
    rtn.add(this.mmivariable.MMI_NID_DRIVER(driverid));
    byte[] oper = { bd[11], bd[12], bd[13], bd[14] };
    rtn.add(this.mmivariable.MMI_NID_OPERATION(oper));
    byte[] ws = { bd[15], bd[16], bd[17], bd[18], bd[19], bd[20], bd[21], bd[22] };
    rtn.add(this.mmivariable.MMI_NID_WORKSHIFT(ws));
    return rtn;
  }
  
  public String MMI_FORCED_DRIVER_REQUEST(byte[] bd) {
    return this.mmivariable.MMI_M_FORCED_REQUEST(bd[3], bd[4]);
  }
  
  public Vector MMI_CURRENT_TRAIN_DATA(byte[] bd) {
    Vector trainData = new Vector();
    trainData.add(this.mmivariable.MMI_M_DATA_EDIT_ENABLE(bd[3], bd[4]));
    trainData.add(this.mmivariable.MMI_NC_TRAIN(bd[5], bd[6]));
    trainData.add(new Integer(this.mmivariable.MMI_L_TRAIN(bd[7], bd[8])));
    trainData.add(new Integer(this.mmivariable.MMI_V_MAXTRAIN(bd[9], bd[10])));
    this.mmivariable.MMI_M_LOADINGGAUGE();
    trainData.add(new Double(this.mmivariable.MMI_M_AXLELOAD(bd[12])));
    int pos = 13;
    int len = bd[pos];
    byte[] traction = new byte[len * 2];
    pos++;
    for (int i = 0; i <= len - 1; i++) {
      traction[i * 2 + 0] = bd[pos + i * 2 + 0];
      traction[i * 2 + 1] = bd[pos + i * 2 + 1];
    } 
    this.mmivariable.MMI_N_TRACTION(traction);
    pos += len * 2;
    len = bd[pos];
    byte[] dsguarant = new byte[len * 4];
    pos++;
    for (int j = 0; j <= len - 1; j++) {
      dsguarant[j * 4 + 0] = bd[pos + j * 4 + 0];
      dsguarant[j * 4 + 1] = bd[pos + j * 4 + 1];
      dsguarant[j * 4 + 2] = bd[pos + j * 4 + 2];
      dsguarant[j * 4 + 3] = bd[pos + j * 4 + 3];
    } 
    this.mmivariable.MMI_N_DSGUARANT(dsguarant);
    pos += len * 4;
    len = bd[pos];
    byte[] deguarant = new byte[len * 4];
    pos++;
    for (int k = 0; k < len; k++) {
      deguarant[k * 4 + 0] = bd[pos + k * 4 + 0];
      deguarant[k * 4 + 1] = bd[pos + k * 4 + 1];
      deguarant[k * 4 + 2] = bd[pos + k * 4 + 2];
      deguarant[k * 4 + 3] = bd[pos + k * 4 + 3];
    } 
    this.mmivariable.MMI_N_DEGUARANT(deguarant);
    pos += len * 4;
    trainData.add(new Integer(this.mmivariable.MMI_T_BRAKE_SB(bd[pos], bd[++pos])));
    trainData.add(new Integer(this.mmivariable.MMI_T_BRAKE_EB(bd[++pos], bd[++pos])));
    trainData.add(new Integer(this.mmivariable
          .MMI_T_CUTOFFTRACTION(bd[++pos], bd[++pos])));
    trainData.add(new Integer(this.mmivariable.MMI_A_MAX_ACC(bd[++pos], bd[++pos])));
    trainData.add(new Integer(this.mmivariable.MMI_A_MAX_DEC(bd[++pos], bd[++pos])));
    trainData.add(new String(this.mmivariable
          .MMI_NID_ENGINE(bd[++pos], bd[++pos], bd[++pos])));
    trainData.add(new Integer(this.mmivariable.MMI_M_TRAIN_TYPE(bd[++pos], bd[++pos])));
    trainData.add(new Integer(this.mmivariable.MMI_V_MAXTRAIN_x(bd[++pos], bd[++pos])));
    trainData.add(new Integer(this.mmivariable.MMI_V_MAXTRAIN_x(bd[++pos], bd[++pos])));
    trainData.add(new Integer(this.mmivariable.MMI_V_MAXTRAIN_x(bd[++pos], bd[++pos])));
    trainData.add(new Integer(this.mmivariable.MMI_V_MAXTRAIN_x(bd[++pos], bd[++pos])));
    return trainData;
  }
  
  public Vector MMI_ECHOED_TRAIN_DATA(byte[] bd) {
    Vector rtn = new Vector();
    rtn.add(new Integer(this.mmivariable.MMI_V_MAXTRAIN_x((byte)(bd[3] ^ 0xFFFFFFFF), (byte)(bd[4] ^ 0xFFFFFFFF))));
    rtn.add(new Integer(this.mmivariable.MMI_V_MAXTRAIN_x((byte)(bd[5] ^ 0xFFFFFFFF), (byte)(bd[6] ^ 0xFFFFFFFF))));
    rtn.add(new Integer(this.mmivariable.MMI_V_MAXTRAIN_x((byte)(bd[7] ^ 0xFFFFFFFF), (byte)(bd[8] ^ 0xFFFFFFFF))));
    rtn.add(new Integer(this.mmivariable.MMI_V_MAXTRAIN_x((byte)(bd[9] ^ 0xFFFFFFFF), (byte)(bd[10] ^ 0xFFFFFFFF))));
    rtn.add(new Integer(this.mmivariable.MMI_M_TRAIN_TYPE((byte)(bd[11] ^ 0xFFFFFFFF), (byte)(bd[12] ^ 0xFFFFFFFF))));
    rtn.add(this.mmivariable.MMI_NID_ENGINE((byte)(bd[13] ^ 0xFFFFFFFF), (byte)(bd[14] ^ 0xFFFFFFFF), (byte)(bd[15] ^ 0xFFFFFFFF)));
    rtn.add(new Integer(this.mmivariable.MMI_A_MAX_DEC((byte)(bd[16] ^ 0xFFFFFFFF), (byte)(bd[17] ^ 0xFFFFFFFF))));
    rtn.add(new Integer(this.mmivariable.MMI_A_MAX_ACC((byte)(bd[18] ^ 0xFFFFFFFF), (byte)(bd[19] ^ 0xFFFFFFFF))));
    rtn.add(new Integer(this.mmivariable.MMI_T_CUTOFFTRACTION((byte)(bd[20] ^ 0xFFFFFFFF), (byte)(bd[21] ^ 0xFFFFFFFF))));
    rtn.add(new Integer(this.mmivariable.MMI_T_BRAKE_EB((byte)(bd[22] ^ 0xFFFFFFFF), (byte)(bd[23] ^ 0xFFFFFFFF))));
    rtn.add(new Integer(this.mmivariable.MMI_T_BRAKE_SB((byte)(bd[24] ^ 0xFFFFFFFF), (byte)(bd[25] ^ 0xFFFFFFFF))));
    int pos = 26;
    int len = bd[pos] ^ 0xFFFFFFFF;
    byte[] degu = new byte[len * 4];
    pos++;
    for (int i = 0; i <= len - 1; i++) {
      degu[i * 4 + 0] = (byte)(bd[pos + i * 4 + 0] ^ 0xFFFFFFFF);
      degu[i * 4 + 1] = (byte)(bd[pos + i * 4 + 1] ^ 0xFFFFFFFF);
      degu[i * 4 + 2] = (byte)(bd[pos + i * 4 + 2] ^ 0xFFFFFFFF);
      degu[i * 4 + 3] = (byte)(bd[pos + i * 4 + 3] ^ 0xFFFFFFFF);
    } 
    this.mmivariable.MMI_N_DEGUARANT(degu);
    pos += len * 4;
    len = bd[pos] ^ 0xFFFFFFFF;
    byte[] dsgu = new byte[len * 4];
    pos++;
    for (int j = 0; j <= len - 1; j++) {
      dsgu[j * 4 + 0] = (byte)(bd[pos + j * 4 + 0] ^ 0xFFFFFFFF);
      dsgu[j * 4 + 1] = (byte)(bd[pos + j * 4 + 1] ^ 0xFFFFFFFF);
      dsgu[j * 4 + 2] = (byte)(bd[pos + j * 4 + 2] ^ 0xFFFFFFFF);
      dsgu[j * 4 + 3] = (byte)(bd[pos + j * 4 + 3] ^ 0xFFFFFFFF);
    } 
    this.mmivariable.MMI_N_DSGUARANT(dsgu);
    pos += len * 4;
    len = bd[pos] ^ 0xFFFFFFFF;
    byte[] trac = new byte[len * 2];
    pos++;
    for (int k = 0; k <= len - 1; k++) {
      trac[k * 2 + 0] = (byte)(bd[pos + k * 2 + 0] ^ 0xFFFFFFFF);
      trac[k * 2 + 1] = (byte)(bd[pos + k * 2 + 1] ^ 0xFFFFFFFF);
    } 
    this.mmivariable.MMI_N_TRACTION(trac);
    pos += len * 2;
    rtn.add(new Double(this.mmivariable.MMI_M_AXLELOAD((byte)(bd[pos] ^ 0xFFFFFFFF))));
    pos++;
    this.mmivariable.MMI_M_LOADINGGAUGE();
    rtn.add(new Integer(this.mmivariable.MMI_V_MAXTRAIN((byte)(bd[++pos] ^ 0xFFFFFFFF), (byte)(bd[++pos] ^ 0xFFFFFFFF))));
    rtn.add(new Integer(this.mmivariable.MMI_L_TRAIN((byte)(bd[++pos] ^ 0xFFFFFFFF), (byte)(bd[++pos] ^ 0xFFFFFFFF))));
    rtn.add(this.mmivariable.MMI_NC_TRAIN((byte)(bd[++pos] ^ 0xFFFFFFFF), (byte)(bd[++pos] ^ 0xFFFFFFFF)));
    rtn.add(this.mmivariable.MMI_M_DATA_EDIT_ENABLE((byte)(bd[++pos] ^ 0xFFFFFFFF), (byte)(bd[++pos] ^ 0xFFFFFFFF)));
    return rtn;
  }
  
  public void MMI_TRACK_DESCRIPTION(byte[] bd) {}
  
  public Vector MMI_DRIVER_MESSAGE(byte[] bd) {
    Vector rtn = new Vector();
    rtn.add(new Integer(this.mmivariable.MMI_I_TEXT(bd[3])));
    rtn.add(new Integer(this.mmivariable.MMI_Q_TEXT_CLASS(bd[4] >> 7 & 0x1)));
    rtn.add(new Integer(this.mmivariable.MMI_Q_TEXT_CRITERIA(bd[4] >> 4 & 0x7)));
    rtn.add(new Integer(this.mmivariable.MMI_Q_TEXT(bd[5], bd[6])));
    return rtn;
  }
  
  public Vector MMI_FAILURE_REPORT_ATP(byte[] bd) {
    Vector rtn = new Vector();
    int finfo = this.mmivariable.MMI_Q_FAILURE_CLASS(bd[3]);
    rtn.add(new Integer(finfo));
    finfo = this.mmivariable.MMI_I_UNIT((bd[4] & 0xF0) >> 4);
    rtn.add(new Integer(finfo));
    finfo = this.mmivariable.MMI_Q_FAILURE_SEVERITY(bd[4] & 0xF);
    rtn.add(new Integer(finfo));
    finfo = this.mmivariable.MMI_I_FAILURE_NUMBER(bd[5], bd[6]);
    rtn.add(new Integer(finfo));
    finfo = this.mmivariable.MMI_Q_FAILURE_CRITERIA((bd[7] & 0xF0) >> 4);
    rtn.add(new Integer(finfo));
    return rtn;
  }
  
  public int MMI_TEST_REQUEST(byte[] bd) {
    return this.mmivariable.MMI_M_TEST_(bd[3], bd[4]);
  }
  
  public long MMI_SET_TIME_ATP(byte[] bd) {
    return this.mmivariable.MMI_T_UTC(bd[3], bd[4], bd[5], bd[6]);
  }
  
  public Vector MMI_CURRENT_SR_RULES(byte[] bd) {
    Vector SR = new Vector(2);
    SR.add(new Integer(this.mmivariable.MMI_V_STFF(bd[3], bd[4])));
    SR.add(new Long(this.mmivariable.MMI_L_STFF(bd[5], bd[6], bd[7], bd[8])));
    return SR;
  }
  
  public Vector MMI_ECHOED_SR_RULES(byte[] bd) {
    Vector rule = new Vector();
    rule.add(new Long(this.mmivariable

          
          .MMI_L_STFF((byte)(bd[3] ^ 0xFFFFFFFF), (byte)(bd[4] ^ 0xFFFFFFFF), (byte)(bd[5] ^ 0xFFFFFFFF), (byte)(bd[6] ^ 0xFFFFFFFF))));
    rule.add(new Integer(this.mmivariable.MMI_V_STFF((byte)(bd[7] ^ 0xFFFFFFFF), (byte)(bd[8] ^ 0xFFFFFFFF))));
    return rule;
  }
  
  public Vector MMI_GEO_POSITION(byte[] bd) {
    Vector position = new Vector(2);
    position.add(new Integer(this.mmivariable.MMI_M_ABSOLUTPOS(bd[3], bd[4], bd[5])));
    position.add(new Integer(this.mmivariable.MMI_M_RELATIVPOS(bd[6], bd[7])));
    return position;
  }
  
  public void MMI_SELECT_STM_REQUEST(byte[] bd) {}
  
  public void MMI_STM_DATA_TO_CAB_1(byte[] bd) {}
  
  public void MMI_STM_DATA_TO_CAB_2(byte[] bd) {}
  
  public void MMI_RU_DATA(byte[] bd) {}
  
  public Vector MMI_START_MMI(byte[] bd) {
    Vector info = new Vector(5);
    info.add(new Integer(this.mmivariable.MMI_M_START_INFO(bd[3])));
    info.add(new Integer(this.mmivariable.MMI_M_START_STATUS(bd[4])));
    info.add(new Integer(this.mmivariable.MMI_M_TYPE(bd[5])));
    info.add(this.mmivariable.MMI_M_IF_VER(bd[6], bd[7], bd[8]));
    info.add(this.mmivariable.MMI_M_SW_VER(bd[9], bd[10], bd[11]));
    return info;
  }
  
  public int MMI_STATUS_REPORT(byte[] bd) {
    return this.mmivariable.MMI_M_MMI_STATUS(bd[3]);
  }
  
  public int MMI_DRIVER_REQUEST(byte[] bd) {
    return this.mmivariable.MMI_M_REQUEST(bd[3]);
  }
  
  public Vector MMI_NEW_DRIVER_DATA(byte[] bd) {
    return MMI_CURRENT_DRIVER_DATA(bd);
  }
  
  public Vector MMI_NEW_TRAIN_DATA(byte[] bd) {
    return MMI_CURRENT_TRAIN_DATA(bd);
  }
  
  public Vector MMI_CONFIRMED_TRAIN_DATA(byte[] bd) {
    return MMI_ECHOED_TRAIN_DATA(bd);
  }
  
  public int MMI_FAILURE_REPORT_MMI(byte[] bd) {
    return this.mmivariable.MMI_I_FAILURE_NUMBER(bd[3], bd[4]);
  }
  
  public Vector MMI_DRIVER_MESSAGE_ACK(byte[] bd) {
    Vector tmp = new Vector();
    tmp.add(new Integer(this.mmivariable.MMI_I_TEXT(bd[3])));
    tmp.add(new Integer(this.mmivariable.MMI_Q_ACK((bd[4] & 0xF0) >> 4)));
    return tmp;
  }
  
  public int MMI_FAILURE_REPORT_ACK(byte[] bd) {
    return this.mmivariable.MMI_I_FAILURE_NUMBER(bd[3], bd[4]);
  }
  
  public long MMI_SET_TIME_MMI(byte[] bd) {
    return this.mmivariable.MMI_T_UTC(bd[3], bd[4], bd[5], bd[6]);
  }
  
  public int MMI_TEST_RESULT(byte[] bd) {
    return this.mmivariable.MMI_M_TEST_(bd[3], bd[4]);
  }
  
  public Vector MMI_NEW_SR_RULES(byte[] bd) {
    return MMI_CURRENT_SR_RULES(bd);
  }
  
  public Vector MMI_CONFIRMED_SR_RULES(byte[] bd) {
    return MMI_ECHOED_SR_RULES(bd);
  }
  
  public Vector MMI_NEW_RBC_DATA(byte[] bd) {
    Vector tmp = new Vector();
    tmp.add(new Integer(this.mmivariable.MMI_NID_RBC(bd[3], bd[4], bd[5])));
    byte[] radio = new byte[7];
    for (int i = 0; i <= 7; i++)
      radio[i] = bd[7 + i]; 
    tmp.add(this.mmivariable.MMI_NID_RADIO(radio));
    return tmp;
  }
  
  public void MMI_SELECT_STM(byte[] db) {
    this.mmivariable.MMI_NID_STM(db[0], db[1]);
  }
  
  public void MMI_STM_DATA_FROM_CAB_1(byte[] db) {}
  
  public void MMI_STM_DATA_FROM_CAB_2(byte[] db) {}
  
  public void MMI_RU_DATA_FROM_CAB_1(byte[] db) {}
  
  public void MMI_RU_DATA_FROM_CAB_2(byte[] db) {}
}
