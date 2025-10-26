// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class InfillLocationReference extends DefaultTelegram
{
    private int Q_NEWCOUNTRY;
    private int NID_BG;
    
    public InfillLocationReference(final byte[] data) {
        super(data);
        this.Q_NEWCOUNTRY = -1;
        this.NID_BG = -1;
        this.go();
    }
    
    private void go() {
        try {
            byte[] tmpdata = HexCode.getBit(super.getCode(), 23, 24);
            this.Q_NEWCOUNTRY = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(super.getCode(), 24, 38);
            this.NID_BG = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int get_Q_NEWCOUNTRY() {
        return this.Q_NEWCOUNTRY;
    }
    
    public int get_NID_BG() {
        return this.NID_BG;
    }
    
    public String toString() {
        try {
            final StringBuffer tmp = new StringBuffer();
            tmp.append("== P136, Infill Location Reference. ==");
            tmp.append("\r\n");
            tmp.append(super.toString());
            tmp.append("\r\n");
            tmp.append("Q_NEWCOUNTRY:\t" + this.get_Q_NEWCOUNTRY());
            tmp.append("\r\n");
            tmp.append("NID_BG:\t" + this.get_NID_BG());
            tmp.append("\r\n");
            return tmp.toString();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return ">>Exception<<\r\n" + this.getClass() + "\r\n" + super.toString();
        }
    }
}
