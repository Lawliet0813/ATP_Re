// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class Curve extends P44
{
    private int N_ITER;
    private int[][] iter;
    
    public Curve(final byte[] data) {
        super(data);
        this.N_ITER = 0;
        this.go();
    }
    
    private void go() {
        try {
            byte[] tmpdata = HexCode.getBit(super.getCode(), 70, 75);
            this.N_ITER = Byte2Number.getUnsigned(tmpdata[0]);
            this.iter = new int[this.get_N_ITER() + 1][3];
            int loc = 40;
            final byte[] code = super.getCode();
            final int n = loc;
            loc += 13;
            tmpdata = HexCode.getBit(code, n, loc);
            int D_CURVEDISTANCE = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            final byte[] code2 = super.getCode();
            final int n2 = loc;
            loc += 12;
            tmpdata = HexCode.getBit(code2, n2, loc);
            int L_CURVELENGTH = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            final byte[] code3 = super.getCode();
            final int n3 = loc;
            loc += 5;
            tmpdata = HexCode.getBit(code3, n3, loc);
            int M_CURVERADIUS = Byte2Number.getUnsigned(tmpdata[0]);
            this.iter[0] = new int[] { D_CURVEDISTANCE, L_CURVELENGTH, M_CURVERADIUS };
            for (int x = 1; x <= this.get_N_ITER(); ++x) {
                loc = 75 + (x - 1) * 30;
                final byte[] code4 = super.getCode();
                final int n4 = loc;
                loc += 13;
                tmpdata = HexCode.getBit(code4, n4, loc);
                D_CURVEDISTANCE = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
                final byte[] code5 = super.getCode();
                final int n5 = loc;
                loc += 12;
                tmpdata = HexCode.getBit(code5, n5, loc);
                L_CURVELENGTH = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
                final byte[] code6 = super.getCode();
                final int n6 = loc;
                loc += 5;
                tmpdata = HexCode.getBit(code6, n6, loc);
                M_CURVERADIUS = Byte2Number.getUnsigned(tmpdata[0]);
                this.iter[x] = new int[] { D_CURVEDISTANCE, L_CURVELENGTH, M_CURVERADIUS };
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int get_N_ITER() {
        return this.N_ITER;
    }
    
    public int[][] get_Curve() {
        return this.iter;
    }
    
    private int[] get_Curve(final int x) {
        if (x >= 0 && x <= this.get_N_ITER()) {
            return this.iter[x];
        }
        return new int[] { -1, -1, -1 };
    }
    
    public String toString() {
        final StringBuffer tmp = new StringBuffer();
        tmp.append("== P44:0, Curve ==");
        tmp.append("\r\n");
        tmp.append(super.toString());
        tmp.append("\r\n");
        int[] iter = this.get_Curve(0);
        tmp.append("D_CURVEDISTANCE:\t" + iter[0]);
        tmp.append("\r\n");
        tmp.append("L_CURVELENGTH:\t" + iter[1]);
        tmp.append("\r\n");
        tmp.append("M_CURVERADIUS:\t" + iter[2]);
        tmp.append("\r\n");
        tmp.append("N_ITER:\t" + this.get_N_ITER());
        tmp.append("\r\n");
        for (int x = 1; x <= this.get_N_ITER(); ++x) {
            iter = this.get_Curve(x);
            tmp.append("\t");
            tmp.append("D_CURVEDISTANCE(" + x + "):\t" + iter[0]);
            tmp.append("\r\n");
            tmp.append("\t");
            tmp.append("L_CURVELENGTH(" + x + "):\t" + iter[1]);
            tmp.append("\r\n");
            tmp.append("\t");
            tmp.append("M_CURVERADIUS(" + x + "):\t" + iter[2]);
            tmp.append("\r\n");
        }
        return tmp.toString();
    }
}
