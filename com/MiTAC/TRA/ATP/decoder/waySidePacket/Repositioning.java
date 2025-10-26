// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class Repositioning extends DefaultTelegram
{
    private int Q_SCALE;
    private int L_SECTION;
    
    public Repositioning(final byte[] data) {
        super(data);
        this.Q_SCALE = -1;
        this.L_SECTION = -1;
        this.go();
    }
    
    public void go() {
        try {
            byte[] tmpdata = HexCode.getBit(super.getCode(), 23, 25);
            this.Q_SCALE = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(super.getCode(), 25, 40);
            this.L_SECTION = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int get_Q_SCALE() {
        return this.Q_SCALE;
    }
    
    public int get_L_SECTION() {
        return this.L_SECTION;
    }
    
    public String toString() {
        final StringBuffer tmp = new StringBuffer();
        tmp.append("== P16, Repositioning. ==");
        tmp.append("\r\n");
        tmp.append(super.toString());
        tmp.append("\r\n");
        tmp.append("Q_SCALE:\t" + this.get_Q_SCALE());
        tmp.append("\r\n");
        tmp.append("L_SECTION:\t" + this.get_L_SECTION());
        tmp.append("\r\n");
        return tmp.toString();
    }
}
