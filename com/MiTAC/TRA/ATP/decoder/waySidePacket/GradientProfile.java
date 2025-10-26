// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class GradientProfile extends DefaultTelegram
{
    private int Q_SCALE;
    private int[][] iter;
    private int N_ITER;
    
    public GradientProfile(final byte[] data) {
        super(data);
        this.Q_SCALE = -1;
        this.N_ITER = 0;
        this.go();
    }
    
    private void go() {
        try {
            byte[] tmpdata = HexCode.getBit(super.getCode(), 23, 25);
            this.Q_SCALE = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(super.getCode(), 49, 54);
            this.N_ITER = Byte2Number.getUnsigned(tmpdata[0]);
            (this.iter = new int[this.get_N_ITER() + 1][3])[0] = this.getNext(0);
            for (int x = 1; x <= this.get_N_ITER(); ++x) {
                this.iter[x] = this.getNext(x);
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int get_Q_SCALE() {
        return this.Q_SCALE;
    }
    
    public int get_N_ITER() {
        return this.N_ITER;
    }
    
    public int[][] get_GRADIENT() {
        return this.iter;
    }
    
    public int[] get_ITER(final int no) {
        if (no >= 0 && no <= this.get_N_ITER()) {
            return this.iter[no];
        }
        return new int[] { -1, -1, -1 };
    }
    
    private int[] getNext(final int no) {
        try {
            final int[] rtn = new int[3];
            int loc;
            if (no == 0) {
                loc = 25;
            }
            else {
                loc = 54 + (no - 1) * 24;
            }
            int locend = loc + 15;
            byte[] tmpdata = HexCode.getBit(super.getCode(), loc, locend);
            rtn[0] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            loc = locend;
            locend = loc + 1;
            tmpdata = HexCode.getBit(super.getCode(), loc, locend);
            rtn[1] = Byte2Number.getUnsigned(tmpdata[0]);
            loc = locend;
            locend = loc + 8;
            tmpdata = HexCode.getBit(super.getCode(), loc, locend);
            rtn[2] = Byte2Number.getUnsigned(tmpdata[0]);
            return rtn;
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return new int[] { -1, -1, -1 };
        }
    }
    
    public String toString() {
        try {
            final StringBuffer tmp = new StringBuffer();
            tmp.append("== P21, Gradient Profile. ==");
            tmp.append("\r\n");
            tmp.append(super.toString());
            tmp.append("\r\n");
            tmp.append("Q_SCALE:\t" + this.get_Q_SCALE());
            tmp.append("\r\n");
            int[] iter = this.getNext(0);
            tmp.append("D_GRADIENT:\t" + iter[0]);
            tmp.append("\r\n");
            tmp.append("Q_GDIR:\t" + iter[1]);
            tmp.append("\r\n");
            tmp.append("G_A:\t" + iter[2]);
            tmp.append("\r\n");
            tmp.append("N_ITER:\t" + this.get_N_ITER());
            tmp.append("\r\n");
            for (int x = 1; x <= this.get_N_ITER(); ++x) {
                iter = this.getNext(x);
                tmp.append("\t");
                tmp.append("D_GRADIENT(" + x + "):\t" + iter[0]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("Q_GDIR(" + x + "):\t" + iter[1]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("G_A(" + x + "):\t" + iter[2]);
                tmp.append("\r\n");
            }
            return tmp.toString();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return ">>Exception<<\r\n" + this.getClass() + "\r\n" + super.toString();
        }
    }
}
