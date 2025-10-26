// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.HexCode;

public class Auxiliary4bitdata extends P44
{
    String a4bit;
    
    public Auxiliary4bitdata(final byte[] data) {
        super(data);
        this.a4bit = "0000";
        this.go();
    }
    
    private void go() {
        try {
            final byte[] tmpdata = HexCode.getBit(super.getCode(), 40, 44);
            this.a4bit = HexCode.decodeToChar(tmpdata).substring(0, 4);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String get_M_AUXILIARY_4_BIT_OUTPUT() {
        return this.a4bit;
    }
    
    public String toString() {
        try {
            final StringBuffer tmp = new StringBuffer();
            tmp.append("== P44:1, Auxiliary 4-bit data.==");
            tmp.append("\r\n");
            tmp.append(super.toString());
            tmp.append("\r\n");
            tmp.append("M_AUXILIARY_4-bit_OUTPUT:\t" + this.get_M_AUXILIARY_4_BIT_OUTPUT());
            tmp.append("\r\n");
            return tmp.toString();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return ">>Exception<<\r\n" + this.getClass() + "\r\n" + super.toString();
        }
    }
}
