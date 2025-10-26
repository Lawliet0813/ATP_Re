// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import com.MiTAC.TRA.ATP.Tools.HexCode;
import java.util.Vector;
import com.MiTAC.TRA.ATP.Tools.Byte2Number;

public class MMIVariables
{
    public int MMI_A_DEGUARANTEED(final byte a, final byte b) {
        return Byte2Number.getUnsigned(a, b);
    }
    
    public int MMI_A_DSGUARANTEED(final byte a, final byte b) {
        return Byte2Number.getUnsigned(a, b);
    }
    
    public int MMI_A_MAX_ACC(final byte a, final byte b) {
        return Byte2Number.getUnsigned(a, b);
    }
    
    public int MMI_A_MAX_DEC(final byte a, final byte b) {
        return Byte2Number.getUnsigned(a, b);
    }
    
    public int MMI_A_TRAIN(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public int MMI_G_GRADIENT(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public int MMI_G_GRADIENT_CURR(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public int MMI_I_FAILURE_NUMBER(final byte a, final byte b) {
        return Byte2Number.getUnsigned(a, b);
    }
    
    public int MMI_I_TEXT(final byte a) {
        return Byte2Number.getUnsigned(a);
    }
    
    public int MMI_I_UNIT(int unit) {
        return ++unit;
    }
    
    public int MMI_L_PACKET(final byte a, final byte b) {
        return Byte2Number.getUnsigned(a, b);
    }
    
    public long MMI_L_STFF(final byte a, final byte b, final byte c, final byte d) {
        return Byte2Number.getUnsigned(a, b, c, d);
    }
    
    public long MMI_L_TRACKCOND(final byte a, final byte b, final byte c, final byte d) {
        return Byte2Number.getUnsigned(a, b, c, d);
    }
    
    public int MMI_L_TRAIN(final byte a, final byte b) {
        return Byte2Number.getUnsigned(a, b);
    }
    
    public int MMI_M_ABSOLUTPOS(final byte a, final byte b, final byte c) {
        return Byte2Number.getSigned(a, b, c);
    }
    
    public int MMI_M_ACTIVE_CABIN(final int active) {
        return active;
    }
    
    public int MMI_M_ADHESION(final byte a) {
        return Byte2Number.getUnsigned(a);
    }
    
    public double MMI_M_AXLELOAD(final byte a) {
        double ton = Byte2Number.getUnsigned(a);
        if (0.0 <= ton && ton <= 80.0) {
            ton *= 0.5;
            return ton;
        }
        if (ton == 126.0) {
            return 41.0;
        }
        if (ton == 127.0) {
            return 0.0;
        }
        return -1.0;
    }
    
    public String MMI_M_DATA_EDIT_ENABLE(final byte a, final byte b) {
        final StringBuffer enable = new StringBuffer();
        byte data = a;
        for (int i = 0; i <= 15; ++i) {
            if (i == 8) {
                data = b;
            }
            enable.append((data & 0x80) >> 7);
            data <<= 1;
        }
        return enable.toString();
    }
    
    public int MMI_M_EMERBRAKE(final int eb) {
        return eb;
    }
    
    public String MMI_M_FORCED_REQUEST(final byte a, final byte b) {
        final StringBuffer rtn = new StringBuffer();
        for (int i = 0; i < 8; ++i) {
            rtn.insert(0, b >> i & 0x1);
        }
        for (int i = 0; i < 8; ++i) {
            rtn.insert(0, a >> i & 0x1);
        }
        return rtn.toString();
    }
    
    public String MMI_M_IF_VER(final byte a, final byte b, final byte c) {
        final int x = Byte2Number.getUnsigned(a);
        final int y = Byte2Number.getUnsigned(b);
        final int z = Byte2Number.getUnsigned(c);
        final String ver = String.valueOf(x) + "." + y + "." + z;
        return ver;
    }
    
    public int MMI_M_LEVEL(final int level) {
        return level;
    }
    
    public void MMI_M_LOADINGGAUGE() {
    }
    
    public int MMI_M_MMI_STATUS(final byte a) {
        return Byte2Number.getUnsigned(a);
    }
    
    public int MMI_M_MODE(final int mode) {
        return mode;
    }
    
    public int MMI_M_OVERRIDE_EOA(final int eoa) {
        return eoa;
    }
    
    public int MMI_M_PACKET(final byte a) {
        return Byte2Number.getUnsigned(a);
    }
    
    public int MMI_M_RELATIVPOS(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public int MMI_M_REQUEST(final byte a) {
        return Byte2Number.getUnsigned(a);
    }
    
    public byte[] MMI_M_RU_DATA(final byte[] arg0) {
        return arg0;
    }
    
    public int MMI_M_SERVICEBRAKE(final int sb) {
        return sb;
    }
    
    public int MMI_M_SLIDE(final int slide) {
        return slide;
    }
    
    public int MMI_M_SLIP(final int slip) {
        return slip;
    }
    
    public int MMI_M_START_REQ(final byte a) {
        return Byte2Number.getUnsigned(a);
    }
    
    public int MMI_M_START_INFO(final byte a) {
        return Byte2Number.getUnsigned(a);
    }
    
    public int MMI_M_START_STATUS(final byte a) {
        return Byte2Number.getUnsigned(a);
    }
    
    public byte[] MMI_M_STM_DATA(final byte[] arg0) {
        return arg0;
    }
    
    public String MMI_M_SW_VER(final byte a, final byte b, final byte c) {
        final int x = Byte2Number.getSigned(a);
        final int y = Byte2Number.getSigned(b);
        final int z = Byte2Number.getSigned(c);
        return String.valueOf(x) + "." + y + "." + z;
    }
    
    public int MMI_M_TEST_(final byte a, final byte b) {
        return Byte2Number.getUnsigned(a, b);
    }
    
    public int MMI_M_TRACKCOND(final byte a) {
        return Byte2Number.getUnsigned(a);
    }
    
    public void MMI_M_TRACTION(final byte a) {
    }
    
    public int MMI_M_TRAIN_TYPE(final byte a, final byte b) {
        return Byte2Number.getUnsigned(a, b);
    }
    
    public int MMI_M_TRIP(final int trip) {
        return trip;
    }
    
    public int MMI_M_TYPE(final byte a) {
        return Byte2Number.getUnsigned(a);
    }
    
    void MMI_M_VOLTAGE() {
    }
    
    public int MMI_M_WARNING(final int warning) {
        return warning;
    }
    
    public Vector MMI_N_MRSP(final byte[] mrsp) {
        final Vector rtn = new Vector();
        for (int i = 0; i < mrsp.length / 6; ++i) {
            final Vector tmp = new Vector();
            tmp.add(new Integer(this.MMI_V_MRSP(mrsp[6 * i + 0], mrsp[6 * i + 1])));
            tmp.add(new Integer(this.MMI_O_MRSP(mrsp[6 * i + 2], mrsp[6 * i + 3], mrsp[6 * i + 4], mrsp[6 * i + 5])));
            rtn.add(tmp);
        }
        return rtn;
    }
    
    public Vector MMI_N_DEGUARANT(final byte[] degu) {
        final Vector rtn = new Vector();
        for (int i = 0; i <= degu.length / 4 - 1; ++i) {
            final Vector tmp = new Vector();
            tmp.add(new Integer(this.MMI_V_DEC_LIMIT_SE(degu[4 * i + 0], degu[4 * i + 1])));
            tmp.add(new Integer(this.MMI_A_DEGUARANTEED(degu[4 * i + 2], degu[4 * i + 3])));
            rtn.add(tmp);
        }
        return rtn;
    }
    
    public Vector MMI_N_DSGUARANT(final byte[] dsgu) {
        final Vector rtn = new Vector();
        for (int i = 0; i <= dsgu.length / 4 - 1; ++i) {
            final Vector tmp = new Vector();
            tmp.add(new Integer(this.MMI_V_DEC_LIMIT_SE(dsgu[4 * i + 0], dsgu[4 * i + 1])));
            tmp.add(new Integer(this.MMI_A_DSGUARANTEED(dsgu[4 * i + 2], dsgu[4 * i + 3])));
            rtn.add(tmp);
        }
        return rtn;
    }
    
    public Vector MMI_N_GRADIENT(final byte[] grad) {
        final Vector rtn = new Vector();
        for (int i = 0; i <= grad.length / 6 - 1; ++i) {
            final Vector tmp = new Vector();
            tmp.add(new Integer(this.MMI_G_GRADIENT(grad[6 * i + 0], grad[6 * i + 1])));
            tmp.add(new Integer(this.MMI_O_GRADIENT(grad[6 * i + 2], grad[6 * i + 3], grad[6 * i + 4], grad[6 * i + 5])));
            rtn.add(tmp);
        }
        return rtn;
    }
    
    public Vector MMI_N_TRACKC(final byte[] track) {
        final Vector rtn = new Vector();
        for (int i = 0; i <= track.length / 9 - 1; ++i) {
            final Vector tmp = new Vector();
            tmp.add(new Integer(this.MMI_O_TRACKCOND(track[9 * i + 0], track[9 * i + 1], track[9 * i + 2], track[9 * i + 3])));
            tmp.add(new Integer(this.MMI_M_TRACKCOND(track[9 * i + 4])));
            tmp.add(new Long(this.MMI_L_TRACKCOND(track[9 * i + 5], track[9 * i + 6], track[9 * i + 7], track[9 * i + 8])));
            rtn.add(tmp);
        }
        return rtn;
    }
    
    public void MMI_N_TRACTION(final byte[] traction) {
    }
    
    public String MMI_NC_TRAIN(final byte a, final byte b) {
        final byte[] tmp = { a, b };
        return HexCode.getBinaryInteger(tmp);
    }
    
    public String MMI_NID_DRIVER(final byte[] driver) {
        return HexCode.decodeToChar(driver).trim();
    }
    
    public String MMI_NID_ENGINE(final byte a, final byte b, final byte c) {
        final byte[] first = { a };
        final StringBuffer eng = new StringBuffer();
        eng.append(HexCode.decodeToChar(first));
        eng.append(Byte2Number.getUnsigned(b, c));
        return eng.toString();
    }
    
    public String MMI_NID_OPERATION(final byte[] oper) {
        String rtn = new StringBuffer().append(HexCode.getHexA_String(oper[0])).toString();
        rtn = String.valueOf(rtn) + HexCode.getHexA_String(oper[1]);
        final byte[] a = { oper[2], oper[3] };
        rtn = String.valueOf(rtn) + HexCode.decodeToChar(a);
        return rtn;
    }
    
    public String MMI_NID_RADIO(final byte[] radio) {
        return HexCode.decodeToChar(radio).trim();
    }
    
    public int MMI_NID_RBC(final byte a, final byte b, final byte c) {
        return Byte2Number.getUnsigned(a, b, c);
    }
    
    public void MMI_NID_STM(final byte arg0, final byte arg1) {
    }
    
    public String MMI_NID_WORKSHIFT(final byte[] ws) {
        return HexCode.decodeToChar(ws).trim();
    }
    
    public int MMI_O_BCSP(final byte a, final byte b, final byte c, final byte d) {
        return Byte2Number.getSigned(a, b, c, d);
    }
    
    public int MMI_O_BRAKETARGET(final byte a, final byte b, final byte c, final byte d) {
        return Byte2Number.getSigned(a, b, c, d);
    }
    
    public int MMI_O_GRADIENT(final byte a, final byte b, final byte c, final byte d) {
        return Byte2Number.getSigned(a, b, c, d);
    }
    
    public int MMI_O_MRSP(final byte a, final byte b, final byte c, final byte d) {
        return Byte2Number.getSigned(a, b, c, d);
    }
    
    public int MMI_O_TRACKCOND(final byte a, final byte b, final byte c, final byte d) {
        return Byte2Number.getSigned(a, b, c, d);
    }
    
    public int MMI_O_TRAIN(final byte a, final byte b, final byte c, final byte d) {
        return Byte2Number.getSigned(a, b, c, d);
    }
    
    public int MMI_Q_ACK(final int ack) {
        return ack;
    }
    
    public int MMI_Q_FAILURE_CLASS(final byte a) {
        return Byte2Number.getUnsigned(a);
    }
    
    public int MMI_Q_FAILURE_CRITERIA(final int criteria) {
        return criteria;
    }
    
    public int MMI_Q_FAILURE_SEVERITY(final int severity) {
        return severity;
    }
    
    public int MMI_Q_TEXT(final byte a, final byte b) {
        return Byte2Number.getUnsigned(a, b);
    }
    
    public int MMI_Q_TEXT_CLASS(final int cls) {
        return cls;
    }
    
    public int MMI_Q_TEXT_CRITERIA(final int criteria) {
        return criteria;
    }
    
    public int MMI_T_BRAKE_EB(final byte a, final byte b) {
        final int eb = Byte2Number.getUnsigned(a, b);
        return eb;
    }
    
    public int MMI_T_BRAKE_SB(final byte a, final byte b) {
        return Byte2Number.getUnsigned(a, b);
    }
    
    public int MMI_T_CUTOFFTRACTION(final byte a, final byte b) {
        return Byte2Number.getUnsigned(a, b);
    }
    
    public int MMI_T_INTERVENWAR(final byte a, final byte b) {
        return Byte2Number.getUnsigned(a, b);
    }
    
    public long MMI_T_UTC(final byte a, final byte b, final byte c, final byte d) {
        return Byte2Number.getUnsigned(a, b, c, d);
    }
    
    public int MMI_V_DEC_LIMIT_SE(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public int MMI_V_INTERVENTION(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public int MMI_V_MAXTRAIN(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public int MMI_V_MAXTRAIN_x(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public int MMI_V_PERMITTED(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public int MMI_V_RELEASE(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public int MMI_V_MRSP(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public int MMI_V_MRSP_CURR(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public int MMI_V_STFF(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public int MMI_V_TARGET(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public int MMI_V_TRAIN(final byte a, final byte b) {
        return Byte2Number.getSigned(a, b);
    }
    
    public String MMI_X_TEXT(final byte[] text_) {
        return HexCode.decodeToChar(text_).trim();
    }
    
    public void main(final String[] args) {
        System.err.print(HexCode.getHexA_String((byte)20));
        System.err.print(" " + HexCode.getHexA_String((byte)40) + " ");
        System.err.println(this.MMI_NC_TRAIN((byte)20, (byte)40));
        System.err.println(this.MMI_NID_ENGINE((byte)70, (byte)40, (byte)(-1)));
    }
}
