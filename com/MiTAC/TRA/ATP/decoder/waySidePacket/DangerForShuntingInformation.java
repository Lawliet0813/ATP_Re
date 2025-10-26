// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class DangerForShuntingInformation extends DefaultTelegram
{
    private int Q_ASPECT;
    
    public DangerForShuntingInformation(final byte[] data) {
        super(data);
        this.Q_ASPECT = -1;
        this.go();
    }
    
    private void go() {
        try {
            final byte[] tmpdata = HexCode.getBit(super.getCode(), 23, 24);
            this.Q_ASPECT = Byte2Number.getUnsigned(tmpdata[0]);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int get_Q_ASPECT() {
        return this.Q_ASPECT;
    }
    
    public String toString() {
        try {
            final StringBuffer tmp = new StringBuffer();
            tmp.append("== p132, Danger for Shunting Information. ==");
            tmp.append("\r\n");
            tmp.append(super.toString());
            tmp.append("\r\n");
            tmp.append("Q_ASPECT:\t" + this.get_Q_ASPECT());
            tmp.append("\r\n");
            return tmp.toString();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return ">>Exception<<\r\n" + this.getClass() + "\r\n" + super.toString();
        }
    }
}
