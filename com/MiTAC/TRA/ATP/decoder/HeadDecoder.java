// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import java.util.Vector;
import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import java.util.Date;

public class HeadDecoder
{
    private Date ts;
    private int speed;
    private int location;
    private int packetNo;
    private MMIVariables mmivariables;
    
    public HeadDecoder() {
        this.mmivariables = new MMIVariables();
    }
    
    public void setByte(final byte[] tsb) throws Exception {
        this.packetNo = Byte2Number.getUnsigned(tsb[0]);
        this.ts = this.getTime(tsb[1], tsb[2], tsb[3], tsb[4], tsb[5], tsb[6]);
        this.location = new Integer(this.mmivariables.MMI_O_TRAIN(tsb[7], tsb[8], tsb[9], tsb[10]));
        this.location = ((this.location >= 1000000000) ? (this.location - 1000000000) : this.location);
        // Speed Stamp is 4 bytes (bytes 11-14) according to ATPRU-LOGF-001 v1.8 spec
        this.speed = Byte2Number.getSigned(tsb[11], tsb[12], tsb[13], tsb[14]);
    }
    
    public Vector getData() {
        final Vector tmp = new Vector();
        tmp.add(this.ts);
        tmp.add(new Integer(this.speed));
        tmp.add(new Integer(this.location));
        return tmp;
    }
    
    public int getPacketNo() {
        return this.packetNo;
    }
    
    public Date getTS() {
        return this.ts;
    }
    
    public int getSpeed() {
        return this.speed;
    }
    
    public int getLocation() {
        return this.location;
    }
    
    private Date getTime(final byte yy, final byte mm, final byte dd, final byte hh, final byte MM, final byte ss) throws Exception {
        final int year = 2000 + Byte2Number.getUnsigned(yy);
        final int month = Byte2Number.getUnsigned(mm);
        final int day = Byte2Number.getUnsigned(dd);
        final int hour = Byte2Number.getUnsigned(hh);
        final int minute = Byte2Number.getUnsigned(MM);
        final int second = Byte2Number.getUnsigned(ss);
        final Date rtn = new Date(year - 1900, month - 1, day, hour, minute, second);
        return rtn;
    }
}
