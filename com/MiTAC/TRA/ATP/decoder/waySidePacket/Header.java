// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import java.util.Arrays;
import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.util.Vector;

public class Header
{
    private static Vector signalAspect;
    private static int[] signalNo;
    private static String[] signalColor;
    private int L_PACKET;
    private int Q_UPDOWN;
    private String M_VERSION;
    private int Q_MEDIA;
    private int N_PIG;
    private int N_TOTAL;
    private int M_DUP;
    private int M_MCOUNT;
    private int NID_C;
    private int NID_BG;
    private int Q_LINK;
    private String signal;
    private byte[] code;
    
    static {
        try {
            if (Header.signalColor == null) {
                final ConnectDB cndb = new ConnectDB();
                final Vector data = cndb.getData("SELECT signalAspectColor FROM SignalAspect_Def ORDER BY signalAspectNo");
                Header.signalColor = new String[data.size()];
                for (int x = 0; x < data.size(); ++x) {
                    Header.signalColor[x] = data.get(x).toString();
                }
                final Vector signal = cndb.getData("SELECT NID_BG FROM signalAspect order by 1");
                Header.signalNo = new int[signal.size()];
                for (int x2 = 0; x2 < signal.size(); ++x2) {
                    Header.signalNo[x2] = new Integer(signal.get(x2).get(0).toString());
                }
                Header.signalAspect = cndb.getData("SELECT * FROM SignalAspect order by NID_BG");
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Header(final byte[] data) {
        this.L_PACKET = -1;
        this.Q_UPDOWN = -1;
        this.M_VERSION = "0010000";
        this.Q_MEDIA = -1;
        this.N_PIG = -1;
        this.N_TOTAL = -1;
        this.M_DUP = -1;
        this.M_MCOUNT = -1;
        this.NID_C = -1;
        this.NID_BG = -1;
        this.Q_LINK = -1;
        this.signal = "NOT FOUND.";
        this.setCode(data);
        this.go();
    }
    
    private void go() {
        try {
            byte[] tmpdata = HexCode.getBit(this.getCode(), 0, 1);
            this.Q_UPDOWN = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(this.getCode(), 1, 8);
            this.M_VERSION = HexCode.getBinaryInteger(tmpdata).substring(1, 8);
            tmpdata = HexCode.getBit(this.getCode(), 8, 9);
            this.Q_MEDIA = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(this.getCode(), 9, 12);
            this.N_PIG = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(this.getCode(), 12, 15);
            this.N_TOTAL = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(this.getCode(), 15, 17);
            this.M_DUP = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(this.getCode(), 17, 25);
            this.M_MCOUNT = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(this.getCode(), 25, 35);
            this.NID_C = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            tmpdata = HexCode.getBit(this.getCode(), 35, 49);
            this.NID_BG = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            tmpdata = HexCode.getBit(this.getCode(), 49, 50);
            this.Q_LINK = Byte2Number.getUnsigned(tmpdata[0]);
            int no = 0;
            final int mcount = this.get_M_MCOUNT();
            if (mcount < 10) {
                final int plac = Arrays.binarySearch(Header.signalNo, this.get_NID_BG());
                this.signal = String.valueOf(this.signal) + plac;
                if (plac > 0) {
                    final Vector tmp = Header.signalAspect.get(plac);
                    no = tmp.get(mcount + 2);
                    if (no >= 0 && no < 10) {
                        this.signal = Header.signalColor[no];
                    }
                    else {
                        this.signal = String.valueOf(this.signal) + "[" + no + "]";
                    }
                }
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void setCode(final byte[] data) {
        this.code = data;
    }
    
    public byte[] getCode() {
        return this.code;
    }
    
    public int get_L_PACKET() {
        return 50;
    }
    
    public int get_Q_UPDOWN() {
        return this.Q_UPDOWN;
    }
    
    public String get_M_VERSION() {
        return this.M_VERSION;
    }
    
    public int get_Q_MEDIA() {
        return this.Q_MEDIA;
    }
    
    public int get_N_PIG() {
        return this.N_PIG;
    }
    
    public int get_N_TOTAL() {
        return this.N_TOTAL;
    }
    
    public int get_M_DUP() {
        return this.M_DUP;
    }
    
    public int get_M_MCOUNT() {
        return this.M_MCOUNT;
    }
    
    public int get_NID_C() {
        return this.NID_C;
    }
    
    public int get_NID_BG() {
        return this.NID_BG;
    }
    
    public int get_Q_LINK() {
        return this.Q_LINK;
    }
    
    public String getSignalAspect() {
        return this.signal;
    }
    
    public String toString() {
        final StringBuffer tmp = new StringBuffer();
        tmp.append("== HEADER ==");
        tmp.append("\tSignal Aspect:\t" + this.getSignalAspect() + ".");
        tmp.append("\r\n");
        tmp.append("Q_UPDOWN:\t" + this.get_Q_UPDOWN());
        tmp.append("\r\n");
        tmp.append("M_VERSION:\t" + this.get_M_VERSION());
        tmp.append("\r\n");
        tmp.append("Q_MEDIA:\t" + this.get_Q_MEDIA());
        tmp.append("\r\n");
        tmp.append("N_PIG:\t" + this.get_N_PIG());
        tmp.append("\r\n");
        tmp.append("N_TOTAL:\t" + this.get_N_TOTAL());
        tmp.append("\r\n");
        tmp.append("M_DUP:\t" + this.get_M_DUP());
        tmp.append("\r\n");
        tmp.append("M_MCOUNT:\t" + this.get_M_MCOUNT());
        tmp.append("\r\n");
        tmp.append("NID_C:\t" + this.get_NID_C());
        tmp.append("\r\n");
        tmp.append("NID_BG:\t" + this.get_NID_BG());
        tmp.append("\r\n");
        tmp.append("Q_LINK:\t" + this.get_Q_LINK());
        tmp.append("\r\n");
        return tmp.toString();
    }
}
