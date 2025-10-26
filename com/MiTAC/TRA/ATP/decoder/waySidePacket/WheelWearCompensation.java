// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class WheelWearCompensation extends P44
{
    private int D_MEASURE;
    private int M_ENDNIDBG;
    
    public WheelWearCompensation(final byte[] data) {
        super(data);
        this.D_MEASURE = -1;
        this.M_ENDNIDBG = -1;
        this.go();
    }
    
    private void go() {
        try {
            byte[] tmpdata = HexCode.getBit(super.getCode(), 40, 56);
            this.D_MEASURE = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            tmpdata = HexCode.getBit(super.getCode(), 56, 70);
            this.M_ENDNIDBG = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int get_D_MEASURE() {
        return this.D_MEASURE;
    }
    
    public int get_M_ENDIDBG() {
        return this.M_ENDNIDBG;
    }
    
    public String toString() {
        final StringBuffer tmp = new StringBuffer();
        tmp.append("== P44:5, Wheel Wear Compensation. ==");
        tmp.append("\r\n");
        tmp.append(super.toString());
        tmp.append("\r\n");
        tmp.append("D_MEASURE:\t" + this.get_D_MEASURE());
        tmp.append("\r\n");
        tmp.append("M_ENDNIDBG:\t" + this.get_M_ENDIDBG());
        tmp.append("\r\n");
        return tmp.toString();
    }
}
