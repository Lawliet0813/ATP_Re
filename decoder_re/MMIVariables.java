package decoder;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import java.util.Vector;

public class MMIVariables {
  public int MMI_A_DEGUARANTEED(byte a, byte b) {
    return Byte2Number.getUnsigned(a, b);
  }
  
  public int MMI_A_DSGUARANTEED(byte a, byte b) {
    return Byte2Number.getUnsigned(a, b);
  }
  
  public int MMI_A_MAX_ACC(byte a, byte b) {
    return Byte2Number.getUnsigned(a, b);
  }
  
  public int MMI_A_MAX_DEC(byte a, byte b) {
    return Byte2Number.getUnsigned(a, b);
  }
  
  public int MMI_A_TRAIN(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public int MMI_G_GRADIENT(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public int MMI_G_GRADIENT_CURR(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public int MMI_I_FAILURE_NUMBER(byte a, byte b) {
    return Byte2Number.getUnsigned(a, b);
  }
  
  public int MMI_I_TEXT(byte a) {
    return Byte2Number.getUnsigned(a);
  }
  
  public int MMI_I_UNIT(int unit) {
    unit++;
    return unit;
  }
  
  public int MMI_L_PACKET(byte a, byte b) {
    return Byte2Number.getUnsigned(a, b);
  }
  
  public long MMI_L_STFF(byte a, byte b, byte c, byte d) {
    return Byte2Number.getUnsigned(a, b, c, d);
  }
  
  public long MMI_L_TRACKCOND(byte a, byte b, byte c, byte d) {
    return Byte2Number.getUnsigned(a, b, c, d);
  }
  
  public int MMI_L_TRAIN(byte a, byte b) {
    return Byte2Number.getUnsigned(a, b);
  }
  
  public int MMI_M_ABSOLUTPOS(byte a, byte b, byte c) {
    return Byte2Number.getSigned(a, b, c);
  }
  
  public int MMI_M_ACTIVE_CABIN(int active) {
    return active;
  }
  
  public int MMI_M_ADHESION(byte a) {
    return Byte2Number.getUnsigned(a);
  }
  
  public double MMI_M_AXLELOAD(byte a) {
    double ton = Byte2Number.getUnsigned(a);
    if (0.0D <= ton && ton <= 80.0D) {
      ton *= 0.5D;
      return ton;
    } 
    if (ton == 126.0D)
      return 41.0D; 
    if (ton == 127.0D)
      return 0.0D; 
    return -1.0D;
  }
  
  public String MMI_M_DATA_EDIT_ENABLE(byte a, byte b) {
    StringBuffer enable = new StringBuffer();
    byte data = a;
    for (int i = 0; i <= 15; i++) {
      if (i == 8)
        data = b; 
      enable.append((data & 0x80) >> 7);
      data = (byte)(data << 1);
    } 
    return enable.toString();
  }
  
  public int MMI_M_EMERBRAKE(int eb) {
    return eb;
  }
  
  public String MMI_M_FORCED_REQUEST(byte a, byte b) {
    StringBuffer rtn = new StringBuffer();
    int i;
    for (i = 0; i < 8; i++)
      rtn.insert(0, b >> i & 0x1); 
    for (i = 0; i < 8; i++)
      rtn.insert(0, a >> i & 0x1); 
    return rtn.toString();
  }
  
  public String MMI_M_IF_VER(byte a, byte b, byte c) {
    int x = Byte2Number.getUnsigned(a);
    int y = Byte2Number.getUnsigned(b);
    int z = Byte2Number.getUnsigned(c);
    String ver = String.valueOf(x) + "." + y + "." + z;
    return ver;
  }
  
  public int MMI_M_LEVEL(int level) {
    return level;
  }
  
  public void MMI_M_LOADINGGAUGE() {}
  
  public int MMI_M_MMI_STATUS(byte a) {
    return Byte2Number.getUnsigned(a);
  }
  
  public int MMI_M_MODE(int mode) {
    return mode;
  }
  
  public int MMI_M_OVERRIDE_EOA(int eoa) {
    return eoa;
  }
  
  public int MMI_M_PACKET(byte a) {
    return Byte2Number.getUnsigned(a);
  }
  
  public int MMI_M_RELATIVPOS(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public int MMI_M_REQUEST(byte a) {
    return Byte2Number.getUnsigned(a);
  }
  
  public byte[] MMI_M_RU_DATA(byte[] arg0) {
    return arg0;
  }
  
  public int MMI_M_SERVICEBRAKE(int sb) {
    return sb;
  }
  
  public int MMI_M_SLIDE(int slide) {
    return slide;
  }
  
  public int MMI_M_SLIP(int slip) {
    return slip;
  }
  
  public int MMI_M_START_REQ(byte a) {
    return Byte2Number.getUnsigned(a);
  }
  
  public int MMI_M_START_INFO(byte a) {
    return Byte2Number.getUnsigned(a);
  }
  
  public int MMI_M_START_STATUS(byte a) {
    return Byte2Number.getUnsigned(a);
  }
  
  public byte[] MMI_M_STM_DATA(byte[] arg0) {
    return arg0;
  }
  
  public String MMI_M_SW_VER(byte a, byte b, byte c) {
    int x = Byte2Number.getSigned(a);
    int y = Byte2Number.getSigned(b);
    int z = Byte2Number.getSigned(c);
    return String.valueOf(x) + "." + y + "." + z;
  }
  
  public int MMI_M_TEST_(byte a, byte b) {
    return Byte2Number.getUnsigned(a, b);
  }
  
  public int MMI_M_TRACKCOND(byte a) {
    return Byte2Number.getUnsigned(a);
  }
  
  public void MMI_M_TRACTION(byte a) {}
  
  public int MMI_M_TRAIN_TYPE(byte a, byte b) {
    return Byte2Number.getUnsigned(a, b);
  }
  
  public int MMI_M_TRIP(int trip) {
    return trip;
  }
  
  public int MMI_M_TYPE(byte a) {
    return Byte2Number.getUnsigned(a);
  }
  
  void MMI_M_VOLTAGE() {}
  
  public int MMI_M_WARNING(int warning) {
    return warning;
  }
  
  public Vector MMI_N_MRSP(byte[] mrsp) {
    Vector rtn = new Vector();
    for (int i = 0; i < mrsp.length / 6; i++) {
      Vector tmp = new Vector();
      tmp.add(new Integer(MMI_V_MRSP(mrsp[6 * i + 0], mrsp[6 * i + 1])));
      tmp.add(new Integer(
            MMI_O_MRSP(mrsp[6 * i + 2], mrsp[6 * i + 3], mrsp[6 * i + 4], mrsp[6 * i + 5])));
      rtn.add(tmp);
    } 
    return rtn;
  }
  
  public Vector MMI_N_DEGUARANT(byte[] degu) {
    Vector rtn = new Vector();
    for (int i = 0; i <= degu.length / 4 - 1; i++) {
      Vector tmp = new Vector();
      tmp.add(new Integer(MMI_V_DEC_LIMIT_SE(degu[4 * i + 0], degu[4 * i + 1])));
      tmp.add(new Integer(MMI_A_DEGUARANTEED(degu[4 * i + 2], degu[4 * i + 3])));
      rtn.add(tmp);
    } 
    return rtn;
  }
  
  public Vector MMI_N_DSGUARANT(byte[] dsgu) {
    Vector rtn = new Vector();
    for (int i = 0; i <= dsgu.length / 4 - 1; i++) {
      Vector tmp = new Vector();
      tmp.add(new Integer(MMI_V_DEC_LIMIT_SE(dsgu[4 * i + 0], dsgu[4 * i + 1])));
      tmp.add(new Integer(MMI_A_DSGUARANTEED(dsgu[4 * i + 2], dsgu[4 * i + 3])));
      rtn.add(tmp);
    } 
    return rtn;
  }
  
  public Vector MMI_N_GRADIENT(byte[] grad) {
    Vector rtn = new Vector();
    for (int i = 0; i <= grad.length / 6 - 1; i++) {
      Vector tmp = new Vector();
      tmp.add(new Integer(MMI_G_GRADIENT(grad[6 * i + 0], grad[6 * i + 1])));
      tmp.add(new Integer(MMI_O_GRADIENT(grad[6 * i + 2], grad[6 * i + 3], grad[6 * i + 4], grad[6 * i + 5])));
      rtn.add(tmp);
    } 
    return rtn;
  }
  
  public Vector MMI_N_TRACKC(byte[] track) {
    Vector rtn = new Vector();
    for (int i = 0; i <= track.length / 9 - 1; i++) {
      Vector tmp = new Vector();
      tmp.add(new Integer(MMI_O_TRACKCOND(track[9 * i + 0], track[9 * i + 1], track[9 * i + 2], track[9 * i + 3])));
      tmp.add(new Integer(MMI_M_TRACKCOND(track[9 * i + 4])));
      tmp.add(new Long(MMI_L_TRACKCOND(track[9 * i + 5], track[9 * i + 6], track[9 * i + 7], track[9 * i + 8])));
      rtn.add(tmp);
    } 
    return rtn;
  }
  
  public void MMI_N_TRACTION(byte[] traction) {}
  
  public String MMI_NC_TRAIN(byte a, byte b) {
    byte[] tmp = new byte[2];
    tmp[0] = a;
    tmp[1] = b;
    return HexCode.getBinaryInteger(tmp);
  }
  
  public String MMI_NID_DRIVER(byte[] driver) {
    return HexCode.decodeToChar(driver).trim();
  }
  
  public String MMI_NID_ENGINE(byte a, byte b, byte c) {
    byte[] first = new byte[1];
    first[0] = a;
    StringBuffer eng = new StringBuffer();
    eng.append(HexCode.decodeToChar(first));
    eng.append(Byte2Number.getUnsigned(b, c));
    return eng.toString();
  }
  
  public String MMI_NID_OPERATION(byte[] oper) {
    String rtn = HexCode.getHexA_String(oper[0]);
    rtn = String.valueOf(rtn) + HexCode.getHexA_String(oper[1]);
    byte[] a = new byte[2];
    a[0] = oper[2];
    a[1] = oper[3];
    rtn = String.valueOf(rtn) + HexCode.decodeToChar(a);
    return rtn;
  }
  
  public String MMI_NID_RADIO(byte[] radio) {
    return HexCode.decodeToChar(radio).trim();
  }
  
  public int MMI_NID_RBC(byte a, byte b, byte c) {
    return Byte2Number.getUnsigned(a, b, c);
  }
  
  public void MMI_NID_STM(byte arg0, byte arg1) {}
  
  public String MMI_NID_WORKSHIFT(byte[] ws) {
    return HexCode.decodeToChar(ws).trim();
  }
  
  public int MMI_O_BCSP(byte a, byte b, byte c, byte d) {
    return Byte2Number.getSigned(a, b, c, d);
  }
  
  public int MMI_O_BRAKETARGET(byte a, byte b, byte c, byte d) {
    return Byte2Number.getSigned(a, b, c, d);
  }
  
  public int MMI_O_GRADIENT(byte a, byte b, byte c, byte d) {
    return Byte2Number.getSigned(a, b, c, d);
  }
  
  public int MMI_O_MRSP(byte a, byte b, byte c, byte d) {
    return Byte2Number.getSigned(a, b, c, d);
  }
  
  public int MMI_O_TRACKCOND(byte a, byte b, byte c, byte d) {
    return Byte2Number.getSigned(a, b, c, d);
  }
  
  public int MMI_O_TRAIN(byte a, byte b, byte c, byte d) {
    return Byte2Number.getSigned(a, b, c, d);
  }
  
  public int MMI_Q_ACK(int ack) {
    return ack;
  }
  
  public int MMI_Q_FAILURE_CLASS(byte a) {
    return Byte2Number.getUnsigned(a);
  }
  
  public int MMI_Q_FAILURE_CRITERIA(int criteria) {
    return criteria;
  }
  
  public int MMI_Q_FAILURE_SEVERITY(int severity) {
    return severity;
  }
  
  public int MMI_Q_TEXT(byte a, byte b) {
    return Byte2Number.getUnsigned(a, b);
  }
  
  public int MMI_Q_TEXT_CLASS(int cls) {
    return cls;
  }
  
  public int MMI_Q_TEXT_CRITERIA(int criteria) {
    return criteria;
  }
  
  public int MMI_T_BRAKE_EB(byte a, byte b) {
    int eb = Byte2Number.getUnsigned(a, b);
    return eb;
  }
  
  public int MMI_T_BRAKE_SB(byte a, byte b) {
    return Byte2Number.getUnsigned(a, b);
  }
  
  public int MMI_T_CUTOFFTRACTION(byte a, byte b) {
    return Byte2Number.getUnsigned(a, b);
  }
  
  public int MMI_T_INTERVENWAR(byte a, byte b) {
    return Byte2Number.getUnsigned(a, b);
  }
  
  public long MMI_T_UTC(byte a, byte b, byte c, byte d) {
    return Byte2Number.getUnsigned(a, b, c, d);
  }
  
  public int MMI_V_DEC_LIMIT_SE(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public int MMI_V_INTERVENTION(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public int MMI_V_MAXTRAIN(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public int MMI_V_MAXTRAIN_x(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public int MMI_V_PERMITTED(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public int MMI_V_RELEASE(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public int MMI_V_MRSP(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public int MMI_V_MRSP_CURR(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public int MMI_V_STFF(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public int MMI_V_TARGET(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public int MMI_V_TRAIN(byte a, byte b) {
    return Byte2Number.getSigned(a, b);
  }
  
  public String MMI_X_TEXT(byte[] text_) {
    return HexCode.decodeToChar(text_).trim();
  }
  
  public void main(String[] args) {
    System.err.print(HexCode.getHexA_String((byte)20));
    System.err.print(" " + HexCode.getHexA_String((byte)40) + " ");
    System.err.println(MMI_NC_TRAIN((byte)20, (byte)40));
    System.err.println(MMI_NID_ENGINE((byte)70, (byte)40, (byte)-1));
  }
}
