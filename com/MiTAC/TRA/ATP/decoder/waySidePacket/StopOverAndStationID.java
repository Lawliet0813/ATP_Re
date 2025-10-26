// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;

public class StopOverAndStationID extends P44
{
    private int Q_STATIONID;
    private int D_DISPLAY;
    private int D_STATIONSTART;
    private int L_STATION;
    private int V_STATIONEND;
    private int Q_DOORCONTROL;
    
    public StopOverAndStationID(final byte[] data) {
        super(data);
        this.Q_STATIONID = -1;
        this.D_DISPLAY = -1;
        this.D_STATIONSTART = -1;
        this.L_STATION = -1;
        this.V_STATIONEND = -1;
        this.Q_DOORCONTROL = -1;
        this.go();
    }
    
    private void go() {
        try {
            byte[] tmpdata = HexCode.getBit(super.getCode(), 40, 49);
            this.Q_STATIONID = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            tmpdata = HexCode.getBit(super.getCode(), 49, 62);
            this.D_DISPLAY = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            tmpdata = HexCode.getBit(super.getCode(), 62, 75);
            this.D_STATIONSTART = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            tmpdata = HexCode.getBit(super.getCode(), 75, 87);
            this.L_STATION = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            tmpdata = HexCode.getBit(super.getCode(), 87, 94);
            this.V_STATIONEND = Byte2Number.getUnsigned(tmpdata[0]);
            tmpdata = HexCode.getBit(super.getCode(), 94, 96);
            this.Q_DOORCONTROL = Byte2Number.getUnsigned(tmpdata[0]);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int get_Q_STATIONID() {
        return this.Q_STATIONID;
    }
    
    public int get_D_DISPLAY() {
        return this.D_DISPLAY;
    }
    
    public int get_D_STATIONSTART() {
        return this.D_STATIONSTART;
    }
    
    public int get_L_STATION() {
        return this.L_STATION;
    }
    
    public int get_V_STATIONEND() {
        return this.V_STATIONEND;
    }
    
    public int get_Q_DOORCONTROL() {
        return this.Q_DOORCONTROL;
    }
    
    public String toString() {
        final StringBuffer tmp = new StringBuffer();
        tmp.append("== P44:2, Stop over and Station ID. ==");
        tmp.append("\r\n");
        tmp.append(super.toString());
        tmp.append("\r\n");
        tmp.append("Q_STATIONID:\t" + this.get_Q_STATIONID());
        tmp.append("\r\n");
        tmp.append("D_DISPLAY:\t" + this.get_D_DISPLAY());
        tmp.append("\r\n");
        tmp.append("D_STATIONSTART:\t" + this.get_D_STATIONSTART());
        tmp.append("\r\n");
        tmp.append("L_STATION:\t" + this.get_L_STATION());
        tmp.append("\r\n");
        tmp.append("V_STATIONEND:\t" + this.get_V_STATIONEND());
        tmp.append("\r\n");
        tmp.append("Q_DOORCONTROL:\t" + this.get_Q_DOORCONTROL());
        tmp.append("\r\n");
        return tmp.toString();
    }
}
