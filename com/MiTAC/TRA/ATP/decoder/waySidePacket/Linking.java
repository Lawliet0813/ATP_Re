// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class Linking extends DefaultTelegram
{
    private int location;
    private int start;
    private int Q_SCALE;
    private int N_ITER;
    
    public Linking(final byte[] data) {
        super(data);
        this.Q_SCALE = -1;
        this.N_ITER = 0;
        this.go();
    }
    
    public void go() {
        try {
            byte[] tmpdata = HexCode.getBit(super.getCode(), 23, 25);
            this.Q_SCALE = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(super.getCode(), 64, 69);
            this.N_ITER = Byte2Number.getUnsigned(tmpdata[0]);
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
    
    public int[] getNext(final int no) throws Exception {
        int loc;
        if (no == 0) {
            loc = 25;
        }
        else {
            loc = 69 + no * 39;
        }
        final int[] nextBG = new int[6];
        int locend = loc + 15;
        byte[] tmpdata = HexCode.getBit(super.getCode(), loc, locend);
        nextBG[0] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
        loc = locend;
        locend = loc + 1;
        tmpdata = HexCode.getBit(super.getCode(), loc, locend);
        nextBG[1] = Byte2Number.getUnsigned(tmpdata[0]);
        loc = locend;
        locend = loc + 14;
        tmpdata = HexCode.getBit(super.getCode(), loc, locend);
        nextBG[2] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
        loc = locend;
        locend = loc + 1;
        tmpdata = HexCode.getBit(super.getCode(), loc, locend);
        nextBG[3] = Byte2Number.getUnsigned(tmpdata[0]);
        loc = locend;
        locend = loc + 2;
        tmpdata = HexCode.getBit(super.getCode(), loc, locend);
        nextBG[4] = Byte2Number.getUnsigned(tmpdata[0]);
        loc = locend;
        locend = loc + 6;
        tmpdata = HexCode.getBit(super.getCode(), loc, locend);
        nextBG[5] = Byte2Number.getUnsigned(tmpdata[0]);
        return nextBG;
    }
    
    public String toString() {
        try {
            final StringBuffer tmp = new StringBuffer();
            tmp.append("== P5, Linking. ==");
            tmp.append("\r\n");
            tmp.append(super.toString());
            tmp.append("\r\n");
            tmp.append("Q_SCALE:\t" + this.get_Q_SCALE());
            tmp.append("\r\n");
            int[] iter = this.getNext(0);
            tmp.append("D_LINK:\t" + iter[0]);
            tmp.append("\r\n");
            tmp.append("D_Q_NEWCOUNTRY:\t" + iter[1]);
            tmp.append("\r\n");
            tmp.append("NID_BG:\t" + iter[2]);
            tmp.append("\r\n");
            tmp.append("Q_LINKORIENTATION:\t" + iter[3]);
            tmp.append("\r\n");
            tmp.append("D_LINKREACTION:\t" + iter[4]);
            tmp.append("\r\n");
            tmp.append("D_LINKACC:\t" + iter[5]);
            tmp.append("\r\n");
            tmp.append("N_ITER:\t" + this.get_N_ITER());
            tmp.append("\r\n");
            for (int x = 0; x < this.get_N_ITER(); ++x) {
                iter = this.getNext(x);
                tmp.append("\t");
                tmp.append("D_LINK(" + x + 1 + "):\t" + iter[0]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("D_Q_NEWCOUNTRY(" + x + 1 + "):\t" + iter[1]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("NID_BG(" + x + 1 + "):\t" + iter[2]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("Q_LINKORIENTATION(" + x + 1 + "):\t" + iter[3]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("D_LINKREACTION(" + x + 1 + "):\t" + iter[4]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("D_LINKACC(" + x + 1 + "):\t" + iter[5]);
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
