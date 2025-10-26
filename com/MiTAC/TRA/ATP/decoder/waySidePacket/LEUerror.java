// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class LEUerror extends P44
{
    private int M_LEUERROR;
    
    public LEUerror(final byte[] data) {
        super(data);
        this.M_LEUERROR = -1;
        this.go();
    }
    
    private void go() {
        try {
            final byte[] tmpdata = HexCode.getBit(super.getCode(), 40, 42);
            this.M_LEUERROR = Byte2Number.getUnsigned(tmpdata[0]);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int get_M_LEUERROR() {
        return this.M_LEUERROR;
    }
    
    public String toString() {
        final StringBuffer tmp = new StringBuffer();
        tmp.append("== P44:4, LEU error. ==");
        tmp.append("\r\n");
        tmp.append(super.toString());
        tmp.append("\r\n");
        tmp.append("M_LEUERROR:\t" + this.get_M_LEUERROR());
        tmp.append("\r\n");
        return tmp.toString();
    }
}
