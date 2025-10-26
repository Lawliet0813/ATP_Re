// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class StaticSpeedProfile extends DefaultTelegram
{
    private int Q_SCALE;
    private int D_STATIC;
    private int V_STATIC;
    private int Q_FRONT;
    private int N_ITER;
    private int[][] iter;
    private int N_ITER2;
    private int[][] iter2;
    
    public StaticSpeedProfile(final byte[] data) {
        super(data);
        this.Q_SCALE = -1;
        this.D_STATIC = -1;
        this.V_STATIC = -1;
        this.Q_FRONT = -1;
        this.N_ITER = 0;
        this.N_ITER2 = 0;
        this.go();
    }
    
    public void go() {
        try {
            byte[] tmpdata = HexCode.getBit(super.getCode(), 23, 25);
            this.Q_SCALE = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(super.getCode(), 25, 40);
            this.D_STATIC = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            tmpdata = HexCode.getBit(super.getCode(), 40, 47);
            this.V_STATIC = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(super.getCode(), 47, 48);
            this.Q_FRONT = Byte2Number.getUnsigned(tmpdata[0]);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int get_Q_SCALE() {
        return this.Q_SCALE;
    }
    
    public int get_D_STATIC() {
        return this.D_STATIC;
    }
    
    public int get_V_STATIC() {
        return this.V_STATIC;
    }
    
    public int get_Q_FRONT() {
        return this.Q_FRONT;
    }
    
    public String toString() {
        try {
            final StringBuffer tmp = new StringBuffer();
            tmp.append("== P27, Static Speed Profile(SSP). ==");
            tmp.append("\r\n");
            tmp.append(super.toString());
            tmp.append("\r\n");
            tmp.append("Q_SCALE:\t" + this.get_Q_SCALE());
            tmp.append("\r\n");
            tmp.append("D_STATIC:\t" + this.get_D_STATIC());
            tmp.append("\r\n");
            tmp.append("V_STATIC:\t" + this.get_V_STATIC());
            tmp.append("\r\n");
            tmp.append("Q_FRONT:\t" + this.get_Q_FRONT());
            tmp.append("\r\n");
            byte[] tmpdata = HexCode.getBit(super.getCode(), 48, 53);
            int iter = Byte2Number.getUnsigned(tmpdata[0]);
            tmp.append("N_ITER:\t" + iter);
            tmp.append("\r\n");
            int loc = 53;
            for (int x = 0; x < iter; ++x) {
                tmpdata = HexCode.getBit(super.getCode(), loc, loc + 4);
                loc += 4;
                final int nc = Byte2Number.getUnsigned(tmpdata[0]);
                tmp.append("NC_DIFF(" + x + "):\t" + nc);
                tmp.append("\r\n");
                tmpdata = HexCode.getBit(super.getCode(), loc, loc + 7);
                loc += 7;
                final int v = Byte2Number.getUnsigned(tmpdata[0]);
                tmp.append("V_DIFF(" + x + "):\t" + v);
                tmp.append("\r\n");
            }
            tmpdata = HexCode.getBit(super.getCode(), loc, loc + 5);
            loc += 5;
            iter = Byte2Number.getUnsigned(tmpdata[0]);
            tmp.append("N_ITER:\t" + iter);
            tmp.append("\r\n");
            for (int x = 0; x < iter; ++x) {
                tmpdata = HexCode.getBit(super.getCode(), loc, loc + 15);
                loc += 15;
                final int ds = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
                tmp.append("\t");
                tmp.append("D_STATIC(" + x + "):\t" + ds);
                tmp.append("\r\n");
                tmpdata = HexCode.getBit(super.getCode(), loc, loc + 7);
                loc += 7;
                final int vs = Byte2Number.getUnsigned(tmpdata[0]);
                tmp.append("\t");
                tmp.append("V_STATIC(" + x + "):\t" + vs);
                tmp.append("\r\n");
                tmpdata = HexCode.getBit(super.getCode(), loc, loc + 1);
                loc += 11;
                final int qf = Byte2Number.getUnsigned(tmpdata[0]);
                tmp.append("\t");
                tmp.append("Q_FRONT(" + x + "):\t" + qf);
                tmp.append("\r\n");
                tmpdata = HexCode.getBit(super.getCode(), loc, loc + 5);
                loc += 5;
                final int iter2 = Byte2Number.getUnsigned(tmpdata[0]);
                tmp.append("\t");
                tmp.append("N_ITER(" + x + "):\t" + iter2);
                tmp.append("\r\n");
                for (int z = 0; z < iter2; ++z) {
                    tmpdata = HexCode.getBit(super.getCode(), loc, loc + 4);
                    loc += 4;
                    final int nc2 = Byte2Number.getUnsigned(tmpdata[0]);
                    tmp.append("\t");
                    tmp.append("\t");
                    tmp.append("NC_DIFF(" + x + "," + z + "):\t" + nc2);
                    tmp.append("\r\n");
                    tmpdata = HexCode.getBit(super.getCode(), loc, loc + 7);
                    loc += 7;
                    final int vd = Byte2Number.getUnsigned(tmpdata[0]);
                    tmp.append("\t");
                    tmp.append("\t");
                    tmp.append("V_DIFF(" + x + "," + z + "):\t" + vd);
                    tmp.append("\r\n");
                }
            }
            return tmp.toString();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return ">>Exception<<\r\n" + this.getClass() + "\r\n" + super.toString();
        }
    }
}
