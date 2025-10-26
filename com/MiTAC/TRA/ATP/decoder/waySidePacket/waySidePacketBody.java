// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public abstract class waySidePacketBody
{
    protected byte[] code;
    private int NID_PACKET;
    private int Q_DIR;
    private int L_PACKET;
    
    public waySidePacketBody() {
        this.NID_PACKET = -1;
        this.Q_DIR = -1;
        this.L_PACKET = -1;
    }
    
    public void setCode(final byte[] data) {
        this.code = data;
        this.go();
    }
    
    private void go() {
        try {
            byte dir = HexCode.getBit(this.code, 0, 8)[0];
            this.NID_PACKET = Byte2Number.getUnsigned(dir);
            if (this.get_NID_PACKET() == 255) {
                this.Q_DIR = -1;
                this.L_PACKET = 8;
            }
            else {
                dir = HexCode.getBit(this.code, 8, 10)[0];
                this.Q_DIR = Byte2Number.getUnsigned(dir);
                final byte[] len = HexCode.getBit(this.code, 10, 23);
                this.L_PACKET = Byte2Number.getUnsigned(len[0], len[1]);
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public byte[] getCode() {
        return this.code;
    }
    
    public int get_NID_PACKET() {
        return this.NID_PACKET;
    }
    
    public int get_Q_DIR() {
        return this.Q_DIR;
    }
    
    public int get_L_PACKET() {
        return this.L_PACKET;
    }
    
    public String toString() {
        return HexCode.decodeToChar(this.getCode());
    }
}
