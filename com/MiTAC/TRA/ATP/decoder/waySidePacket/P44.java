// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class P44 extends DefaultTelegram
{
    private int NID_XUSER;
    private int NID_XDATA;
    
    public P44(final byte[] data) {
        super(data);
        this.NID_XUSER = -1;
        this.NID_XDATA = -1;
        this.go();
    }
    
    private void go() {
        try {
            byte[] tmpdata = HexCode.getBit(super.getCode(), 23, 32);
            this.NID_XUSER = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            tmpdata = HexCode.getBit(super.getCode(), 32, 40);
            this.NID_XDATA = Byte2Number.getUnsigned(tmpdata[0]);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int get_NID_XUSER() {
        return this.NID_XUSER;
    }
    
    public int get_NID_XDATA() {
        return this.NID_XDATA;
    }
    
    public String toString() {
        final StringBuffer tmp = new StringBuffer();
        tmp.append(super.toString());
        tmp.append("\r\n");
        tmp.append("NID_XUSER:\t" + this.get_NID_XUSER());
        tmp.append("\r\n");
        tmp.append("NID_XDATA:\t" + this.get_NID_XDATA());
        tmp.append("\r\n");
        return tmp.toString();
    }
}
