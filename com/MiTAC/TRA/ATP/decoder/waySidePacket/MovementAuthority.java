// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class MovementAuthority extends DefaultTelegram
{
    private int Q_SCALE;
    private int V_MAIN;
    private int V_LOA;
    private int T_LOA;
    private int N_ITER;
    private int Q_SECTIONTIMER;
    private int T_SECTIONTIMER;
    private int D_SECTIONTIMERSTOPLOC;
    private int Q_ENDTIMER;
    private int Q_DANGERPOINT;
    private int D_DP;
    private int V_RELEASEDP;
    private int Q_OVERLAP;
    private int[][] iter;
    
    public MovementAuthority(final byte[] data) {
        super(data);
        this.Q_SCALE = -1;
        this.V_MAIN = -1;
        this.V_LOA = -1;
        this.T_LOA = -1;
        this.N_ITER = 0;
        this.Q_SECTIONTIMER = -1;
        this.T_SECTIONTIMER = -1;
        this.D_SECTIONTIMERSTOPLOC = -1;
        this.Q_ENDTIMER = -1;
        this.Q_DANGERPOINT = -1;
        this.D_DP = -1;
        this.V_RELEASEDP = -1;
        this.Q_OVERLAP = -1;
        this.go();
    }
    
    private void go() {
        try {
            int loc = 23;
            final byte[] code = super.getCode();
            final int n = loc;
            loc += 2;
            byte[] tmpdata = HexCode.getBit(code, n, loc);
            this.Q_SCALE = Byte2Number.getUnsigned(tmpdata[0]);
            final byte[] code2 = super.getCode();
            final int n2 = loc;
            loc += 7;
            tmpdata = HexCode.getBit(code2, n2, loc);
            this.V_MAIN = Byte2Number.getUnsigned(tmpdata[0]);
            final byte[] code3 = super.getCode();
            final int n3 = loc;
            loc += 7;
            tmpdata = HexCode.getBit(code3, n3, loc);
            this.V_LOA = Byte2Number.getUnsigned(tmpdata[0]);
            final byte[] code4 = super.getCode();
            final int n4 = loc;
            loc += 10;
            tmpdata = HexCode.getBit(code4, n4, loc);
            this.T_LOA = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            final byte[] code5 = super.getCode();
            final int n5 = loc;
            loc += 5;
            tmpdata = HexCode.getBit(code5, n5, loc);
            this.N_ITER = Byte2Number.getUnsigned(tmpdata[0]);
            this.iter = new int[this.get_N_ITER()][5];
            for (int x = 1; x <= this.get_N_ITER(); ++x) {
                loc += (x - 1) * 107;
                final int[] rtn = new int[13];
                final byte[] code6 = super.getCode();
                final int n6 = loc;
                loc += 15;
                tmpdata = HexCode.getBit(code6, n6, loc);
                rtn[0] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
                tmpdata = HexCode.getBit(super.getCode(), loc, ++loc);
                rtn[1] = Byte2Number.getUnsigned(tmpdata[0]);
                final byte[] code7 = super.getCode();
                final int n7 = loc;
                loc += 10;
                tmpdata = HexCode.getBit(code7, n7, loc);
                rtn[2] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
                final byte[] code8 = super.getCode();
                final int n8 = loc;
                loc += 15;
                tmpdata = HexCode.getBit(code8, n8, loc);
                rtn[3] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
                final byte[] code9 = super.getCode();
                final int n9 = loc;
                loc += 15;
                tmpdata = HexCode.getBit(code9, n9, loc);
                rtn[4] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
                tmpdata = HexCode.getBit(super.getCode(), loc, ++loc);
                rtn[5] = Byte2Number.getUnsigned(tmpdata[0]);
                final byte[] code10 = super.getCode();
                final int n10 = loc;
                loc += 10;
                tmpdata = HexCode.getBit(code10, n10, loc);
                rtn[6] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
                final byte[] code11 = super.getCode();
                final int n11 = loc;
                loc += 15;
                tmpdata = HexCode.getBit(code11, n11, loc);
                rtn[7] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
                tmpdata = HexCode.getBit(super.getCode(), loc, ++loc);
                rtn[8] = Byte2Number.getUnsigned(tmpdata[0]);
                tmpdata = HexCode.getBit(super.getCode(), loc, ++loc);
                rtn[9] = Byte2Number.getUnsigned(tmpdata[0]);
                final byte[] code12 = super.getCode();
                final int n12 = loc;
                loc += 15;
                tmpdata = HexCode.getBit(code12, n12, loc);
                rtn[10] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
                final byte[] code13 = super.getCode();
                final int n13 = loc;
                loc += 7;
                tmpdata = HexCode.getBit(code13, n13, loc);
                rtn[11] = Byte2Number.getUnsigned(tmpdata[0]);
                tmpdata = HexCode.getBit(super.getCode(), loc, ++loc);
                rtn[12] = Byte2Number.getUnsigned(tmpdata[0]);
                this.iter[x - 1] = rtn;
            }
        }
        catch (final Exception ex) {}
    }
    
    public int get_Q_SCALE() {
        return this.Q_SCALE;
    }
    
    public int get_V_MAIN() {
        return this.V_MAIN;
    }
    
    public int get_V_LOA() {
        return this.V_LOA;
    }
    
    public int get_T_LOA() {
        return this.T_LOA;
    }
    
    private int get_N_ITER() {
        return this.N_ITER;
    }
    
    public int[] get_ITER(final int no) {
        if (no > 0 && no <= this.get_N_ITER()) {
            return this.iter[no - 1];
        }
        return new int[] { -1, -1, -1, -1, -1 };
    }
    
    public int get_Q_SECTIONTIMER() {
        return this.Q_SECTIONTIMER;
    }
    
    public int get_T_SECTIONTIMER() {
        return this.T_SECTIONTIMER;
    }
    
    public int get_D_SECTIONTIMERSTOPLOC() {
        return this.D_SECTIONTIMERSTOPLOC;
    }
    
    public int get_Q_ENDTIMER() {
        return this.Q_ENDTIMER;
    }
    
    public int get_Q_DANGERPOINT() {
        return this.Q_DANGERPOINT;
    }
    
    public int get_D_DP() {
        return this.D_DP;
    }
    
    public int get_V_RELEASEDP() {
        return this.V_RELEASEDP;
    }
    
    public int get_Q_OVERLAP() {
        return this.Q_OVERLAP;
    }
    
    public String toString() {
        try {
            final StringBuffer tmp = new StringBuffer();
            tmp.append("== P12, MovementAuthority. ==");
            tmp.append("\r\n");
            tmp.append(super.toString());
            tmp.append("\r\n");
            tmp.append("Q_SCALE:\t" + this.get_Q_SCALE());
            tmp.append("\r\n");
            tmp.append("V_MAIN:\t" + this.get_V_MAIN());
            tmp.append("\r\n");
            tmp.append("V_LOA:\t" + this.get_V_LOA());
            tmp.append("\r\n");
            tmp.append("T_LOA:\t" + this.get_T_LOA());
            tmp.append("\r\n");
            tmp.append("N_ITER:\t" + this.get_N_ITER());
            tmp.append("\r\n");
            for (int x = 1; x <= this.get_N_ITER(); ++x) {
                final int[] tmpdata = this.get_ITER(x);
                tmp.append("\t");
                tmp.append("L_SECTION:\t" + tmpdata[0]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("Q_SECTIONTIMER:\t" + tmpdata[1]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("T_SECTIONTIMER:\t" + tmpdata[2]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("D_SECTIONTIMERSTOPLOC:\t" + tmpdata[3]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("L_ENDSECTION:\t" + tmpdata[4]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("Q_SECTIONTIMER:\t" + tmpdata[5]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("T_SECTIONTIMER:\t" + tmpdata[6]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("D_SECTIONTIMERSTOPLOC:\t" + tmpdata[7]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("Q_ENDTIMER:\t" + tmpdata[8]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("Q_DANGERPOINT:\t" + tmpdata[9]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("D_DP:\t" + tmpdata[10]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("V_RELEASEDP:\t" + tmpdata[11]);
                tmp.append("\r\n");
                tmp.append("\t");
                tmp.append("Q_OVERLAP:\t" + tmpdata[12]);
                tmp.append("\r\n");
            }
            return tmp.toString();
        }
        catch (final Exception ex) {
            return ">>Exception<<\r\n" + this.getClass() + "\r\n" + super.toString();
        }
    }
}
