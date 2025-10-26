// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.RecordHandler;
import java.util.Vector;

public class ATPDecoder
{
    private byte[] data;
    private int packetNO;
    private String description;
    private PacketMMI packetMMI;
    
    public ATPDecoder() {
        this.packetMMI = new PacketMMI();
    }
    
    public Vector DecodeATPData(final byte[] byteData) throws Exception {
        this.data = byteData;
        final RecordHandler rtn = new RecordHandler();
        this.packetNO = Byte2Number.getUnsigned(this.data[0]);
        rtn.add((Object)new Integer(this.packetNO));
        rtn.addObject(this.packetdata());
        rtn.add((Object)("ATP PacketNo " + this.packetNO));
        return (Vector)rtn;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    private Object packetdata() {
        switch (this.packetNO) {
            case 0: {
                return new Integer(this.packetMMI.MMI_START_ATP(this.data));
            }
            case 1: {
                return this.packetMMI.MMI_DYMANIC(this.data);
            }
            case 2: {
                return this.packetMMI.MMI_STATUS(this.data);
            }
            case 3: {
                return new Long(this.packetMMI.MMI_SET_TIME_ATP(this.data));
            }
            case 4: {
                return "TBD.";
            }
            case 5: {
                return this.packetMMI.MMI_GEO_POSITION(this.data);
            }
            case 6: {
                return "TBD.";
            }
            case 7: {
                return this.packetMMI.MMI_FORCED_DRIVER_REQUEST(this.data);
            }
            case 8: {
                return this.packetMMI.MMI_DRIVER_MESSAGE(this.data);
            }
            case 9: {
                return this.packetMMI.MMI_FAILURE_REPORT_ATP(this.data);
            }
            case 10: {
                return this.packetMMI.MMI_ECHOED_TRAIN_DATA(this.data);
            }
            case 11: {
                return this.packetMMI.MMI_CURRENT_SR_RULES(this.data);
            }
            case 12: {
                return "TBD.";
            }
            case 14: {
                return this.packetMMI.MMI_CURRENT_DRIVER_DATA(this.data);
            }
            case 15: {
                return new Integer(this.packetMMI.MMI_TEST_REQUEST(this.data));
            }
            case 16: {
                return "TBD.";
            }
            case 17: {
                return "TBD.";
            }
            case 18: {
                return "TBD.";
            }
            case 19: {
                return "TBD.";
            }
            case 100: {
                return this.packetMMI.MMI_START_MMI(this.data);
            }
            case 101: {
                return new Integer(this.packetMMI.MMI_DRIVER_REQUEST(this.data));
            }
            case 102: {
                return new Integer(this.packetMMI.MMI_STATUS_REPORT(this.data));
            }
            case 103: {
                return "TBD.";
            }
            case 104: {
                return "TBD.";
            }
            case 106: {
                return "TBD.";
            }
            case 107: {
                return "TBD.";
            }
            case 109: {
                return "TBD.";
            }
            case 110: {
                return this.packetMMI.MMI_CONFIRMED_TRAIN_DATA(this.data);
            }
            case 111: {
                return this.packetMMI.MMI_DRIVER_MESSAGE_ACK(this.data);
            }
            case 112: {
                return "TBD.";
            }
            case 113: {
                return new Integer(this.packetMMI.MMI_FAILURE_REPORT_MMI(this.data));
            }
            case 114: {
                return "TBD.";
            }
            case 115: {
                return "TBD.";
            }
            case 116: {
                return "TBD.";
            }
            case 117: {
                return "TBD.";
            }
            case 118: {
                return "TBD.";
            }
            case 119: {
                return "TBD.";
            }
            case 120: {
                return "TBD.";
            }
            default: {
                return "Unknow Package.";
            }
        }
    }
}
