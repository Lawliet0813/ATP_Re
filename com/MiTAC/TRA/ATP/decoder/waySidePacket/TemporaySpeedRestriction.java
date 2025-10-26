// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class TemporaySpeedRestriction extends P44
{
    private int NID_TSR;
    private int D_TSR;
    private int L_TSR;
    private int Q_FRONT_DRIVER;
    private int V_TSR;
    private int N_ITER;
    private int[][] iter;
    
    public TemporaySpeedRestriction(final byte[] data) {
        super(data);
        this.NID_TSR = -1;
        this.D_TSR = -1;
        this.L_TSR = -1;
        this.Q_FRONT_DRIVER = -1;
        this.V_TSR = -1;
        this.N_ITER = 0;
        this.go();
    }
    
    private void go() {
        try {
            byte[] tmpdata = HexCode.getBit(super.getCode(), 40, 48);
            this.NID_TSR = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(super.getCode(), 48, 63);
            this.D_TSR = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            tmpdata = HexCode.getBit(super.getCode(), 63, 78);
            this.L_TSR = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            tmpdata = HexCode.getBit(super.getCode(), 78, 80);
            this.Q_FRONT_DRIVER = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(super.getCode(), 80, 87);
            this.V_TSR = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(super.getCode(), 87, 92);
            this.N_ITER = Byte2Number.getUnsigned(tmpdata[0]);
            this.iter = new int[this.get_N_ITER()][3];
            for (int x = 1; x <= this.get_N_ITER(); ++x) {
                int loc = 92 + x - 13;
                final byte[] code = super.getCode();
                final int n = loc;
                loc += 4;
                tmpdata = HexCode.getBit(code, n, loc);
                final int NC_DIFF = Byte2Number.getUnsigned(tmpdata[0]);
                final byte[] code2 = super.getCode();
                final int n2 = loc;
                loc += 2;
                tmpdata = HexCode.getBit(code2, n2, loc);
                final int Q_FRONT_DRIVER = Byte2Number.getUnsigned(tmpdata[0]);
                final byte[] code3 = super.getCode();
                final int n3 = loc;
                loc += 7;
                tmpdata = HexCode.getBit(code3, n3, loc);
                final int unsigned = Byte2Number.getUnsigned(tmpdata[0]);
                this.iter[x - 1] = new int[] { NC_DIFF, Q_FRONT_DRIVER, unsigned };
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int get_NID_TSR() {
        return this.NID_TSR;
    }
    
    public int get_D_TSR() {
        return this.D_TSR;
    }
    
    public int get_L_TSR() {
        return this.L_TSR;
    }
    
    public int get_Q_FRONT_DRIVER() {
        return this.Q_FRONT_DRIVER;
    }
    
    public int get_V_TSR() {
        return this.V_TSR;
    }
    
    public int get_N_ITER() {
        return this.N_ITER;
    }
    
    public int[] get_ITER(final int no) {
        if (no > 0 && no <= this.get_N_ITER()) {
            return this.iter[no];
        }
        return new int[] { -1, -1, -1 };
    }
    
    public String toString() {
        final StringBuffer tmp = new StringBuffer();
        tmp.append("== P44:3, Temporay Speed Restriction. ==");
        tmp.append("\r\n");
        tmp.append(super.toString());
        tmp.append("NID_TSR:\t" + this.get_NID_TSR());
        tmp.append("\r\n");
        tmp.append("D_TSR:\t" + this.get_D_TSR());
        tmp.append("\r\n");
        tmp.append("L_TSR:\t" + this.get_L_TSR());
        tmp.append("\r\n");
        tmp.append("Q_FRONT_DRIVER:\t" + this.get_Q_FRONT_DRIVER());
        tmp.append("\r\n");
        tmp.append("V_TSR:\t" + this.get_V_TSR());
        tmp.append("\r\n");
        tmp.append("N_ITER:\t" + this.get_N_ITER());
        tmp.append("\r\n");
        for (int x = 1; x <= this.get_N_ITER(); ++x) {
            final int[] iter = this.get_ITER(x);
            tmp.append("\t");
            tmp.append("NC_DIFF(" + x + "):\t" + iter[0]);
            tmp.append("\r\n");
            tmp.append("\t");
            tmp.append("Q_FRONT_DRIVER(" + x + "):\t" + iter[1]);
            tmp.append("\r\n");
            tmp.append("\t");
            tmp.append("V_DIFF(" + x + "):\t" + iter[2]);
            tmp.append("\r\n");
        }
        return tmp.toString();
    }
}
