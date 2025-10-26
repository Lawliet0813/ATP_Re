// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class LevelTransistionOrder extends DefaultTelegram
{
    private int Q_SCALE;
    private int D_LEVELTR;
    private int M_LEVELTR;
    private int L_ACKLEVELTR;
    private int N_ITER;
    
    public LevelTransistionOrder(final byte[] data) {
        super(data);
        this.Q_SCALE = -1;
        this.D_LEVELTR = -1;
        this.M_LEVELTR = -1;
        this.L_ACKLEVELTR = -1;
        this.N_ITER = 0;
        this.go();
    }
    
    private void go() {
        try {
            byte[] tmpdata = HexCode.getBit(super.getCode(), 23, 25);
            this.Q_SCALE = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(super.getCode(), 25, 40);
            this.D_LEVELTR = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            tmpdata = HexCode.getBit(super.getCode(), 40, 43);
            this.M_LEVELTR = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(super.getCode(), 43, 58);
            this.L_ACKLEVELTR = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            tmpdata = HexCode.getBit(super.getCode(), 58, 63);
            this.N_ITER = Byte2Number.getUnsigned(tmpdata[0]);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int get_Q_SCALE() {
        return this.Q_SCALE;
    }
    
    public int get_D_LEVELTR() {
        return this.D_LEVELTR;
    }
    
    public int get_M_LEVELTR() {
        return this.M_LEVELTR;
    }
    
    public int get_L_ACKLEVELTR() {
        return this.L_ACKLEVELTR;
    }
    
    public int get_N_ITER() {
        return this.N_ITER;
    }
    
    public String toString() {
        try {
            final StringBuffer tmp = new StringBuffer();
            tmp.append("== P41, Level Transistion Order. ==");
            tmp.append("\r\n");
            tmp.append(super.toString());
            tmp.append("\r\n");
            tmp.append("Q_SCALE:\t" + this.get_Q_SCALE());
            tmp.append("\r\n");
            tmp.append("D_LEVELTR:\t" + this.get_D_LEVELTR());
            tmp.append("\r\n");
            tmp.append("M_LEVELTR:\t" + this.get_M_LEVELTR());
            tmp.append("\r\n");
            tmp.append("L_ACKLEVELTR:\t" + this.get_L_ACKLEVELTR());
            tmp.append("\r\n");
            tmp.append("N_ITER:\t" + this.get_N_ITER());
            tmp.append("\r\n");
            return tmp.toString();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return ">>Exception<<\r\n" + this.getClass() + "\r\n" + super.toString();
        }
    }
}
