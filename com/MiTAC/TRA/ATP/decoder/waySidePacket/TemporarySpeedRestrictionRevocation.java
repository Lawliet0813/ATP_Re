// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class TemporarySpeedRestrictionRevocation extends DefaultTelegram
{
    private int NID_TSR;
    
    public TemporarySpeedRestrictionRevocation(final byte[] data) {
        super(data);
        this.NID_TSR = -1;
        this.go();
    }
    
    private void go() {
        try {
            final byte[] tmpdata = HexCode.getBit(super.getCode(), 23, 31);
            this.NID_TSR = Byte2Number.getUnsigned(tmpdata[0]);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int get_NID_TSR() {
        return this.NID_TSR;
    }
    
    public String toString() {
        try {
            final StringBuffer tmp = new StringBuffer();
            tmp.append("== P66, Temporary Speed Restriction Revocation. ==");
            tmp.append("\r\n");
            tmp.append(super.toString());
            tmp.append("\r\n");
            tmp.append("NID_TSR:\t" + this.get_NID_TSR());
            tmp.append("\r\n");
            return tmp.toString();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return ">>Exception<<\r\n" + this.getClass() + "\r\n" + super.toString();
        }
    }
}
