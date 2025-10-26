// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

public class DefaultTelegram extends waySidePacketBody
{
    public DefaultTelegram(final byte[] data) {
        super.setCode(data);
    }
    
    public String toString() {
        try {
            final StringBuffer tmp = new StringBuffer();
            tmp.append("NID_PACKET:\t" + super.get_NID_PACKET());
            tmp.append("\r\n");
            tmp.append("Q_DIR:\t" + super.get_Q_DIR());
            tmp.append("\r\n");
            tmp.append("L_PACKET:\t" + super.get_L_PACKET());
            return tmp.toString();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return ">>Exception<<\r\nDefault packet";
        }
    }
}
