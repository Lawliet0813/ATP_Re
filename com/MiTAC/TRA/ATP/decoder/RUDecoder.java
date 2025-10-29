// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.Tools.RecordHandler;
import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import java.util.Vector;

public class RUDecoder
{
    private ATPDecoder atpdec;
    private HeadDecoder headdec;
    private MMIVariables mmivariables;
    private VDXDecoder vdxdecoder;
    private int packetNo;
    private byte[] data;
    private byte[] header;
    private byte[] body;
    private int dataLength;
    private String description;
    private StringBuffer BTMdescrip;
    private BTMDecoder btmdec;
    
    public RUDecoder() {
        this.atpdec = new ATPDecoder();
        this.headdec = new HeadDecoder();
        this.mmivariables = new MMIVariables();
        this.vdxdecoder = new VDXDecoder();
        this.BTMdescrip = new StringBuffer();
        this.btmdec = new BTMDecoder();
    }
    
    public Vector getRUDecoder(final byte[] byteData) throws Exception {
        try {
            this.data = byteData;
            this.header = this.substringByte(0, 15);
            this.headdec.setByte(this.header);
            this.packetNo = this.headdec.getPacketNo();
            this.dataLength = Byte2Number.getUnsigned(this.data[15]);
            this.body = this.substringByte(16, this.dataLength);
            final RecordHandler tmp = new RecordHandler();
            tmp.add((Object)this.data);
            tmp.addVector(this.headdec.getData());
            tmp.addVector((Vector)this.collectionBodyData());
            return (Vector)tmp;
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            System.err.println(HexCode.getHexA_String(this.data));
            return new Vector();
        }
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getBTMdescription() {
        return this.BTMdescrip.toString();
    }
    
    private byte[] substringByte(final int start, final int length) {
        final byte[] tmp = new byte[length];
        for (int x = 0; x < length; ++x) {
            tmp[x] = this.data[start + x];
        }
        return tmp;
    }
    
    private Object collectionBodyData() throws Exception {
        int MVBPort = 0;
        final RecordHandler rtn = new RecordHandler();
        rtn.add((Object)new Integer(this.packetNo));
        switch (this.packetNo) {
            case 1:
            case 4: {
                rtn.addVector(this.atpdec.DecodeATPData(this.body));
                return rtn;
            }
            case 21: {
                MVBPort = 21;
                rtn.add((Object)new Boolean(this.vdxdecoder.getBrakePressure(this.body[0])));
                rtn.add((Object)(this.description = "MVB_LOG_TYPE_VDX_IN_STATUS_1: no packet format to decode."));
                return rtn;
            }
            case 22: {
                MVBPort = 392;
                rtn.add((Object)(this.description = "MVB_LOG_TYPE_VDX_OUT_1: no packet format to decode."));
                return rtn;
            }
            case 23: {
                MVBPort = 393;
                rtn.add((Object)(this.description = "MVB_LOG_TYPE_VDX_OUT_2: no packet format to decode."));
                return rtn;
            }
            case 24: {
                MVBPort = 394;
                rtn.add((Object)(this.description = "MVB_LOG_TYPE_VDX_OUT_3: no packet format to decode."));
                return rtn;
            }
            case 31: {
                MVBPort = 2176;
                rtn.add((Object)(this.description = "MVB_LOG_TYPE_DX_IN_STATUS_1: no packet format to decode."));
                return rtn;
            }
            case 32: {
                MVBPort = 2177;
                rtn.add((Object)(this.description = "MVB_LOG_TYPE_DX_STATUS_1: no packet format to decode."));
                return rtn;
            }
            case 33: {
                MVBPort = 2184;
                rtn.add((Object)(this.description = "MVB_LOG_TYPE_OUT_STATUS_1: no packet format to decode."));
                return rtn;
            }
            case 41: {
                MVBPort = 648;
                rtn.add((Object)(this.description = "MVB_LOG_BTM_COMMAND_1: no packet format to decode."));
                return rtn;
            }
            case 42: {
                MVBPort = 42;
                rtn.add((Object)(this.description = "MVB_LOG_BTM_STATUS_1: no packet format to decode."));
                return rtn;
            }
            case 43: {
                MVBPort = 643;
                this.description = "MVB_LOG_BTM_TGM_1.";
                this.btmdec.setData(this.body, 1);
                if (this.btmdec.isDataCollectFinished()) {
                    rtn.add((Object)new Integer(this.btmdec.getDetail()[0]));
                    rtn.add((Object)new Integer(this.btmdec.getDetail()[1]));
                    rtn.add((Object)this.btmdec.getGradient());
                    rtn.add((Object)this.btmdec.getCurve());
                    this.BTMdescrip.append(this.btmdec.toString());
                }
                else {
                    rtn.add((Object)this.description);
                }
                return rtn;
            }
            case 44: {
                MVBPort = 644;
                this.description = "MVB_LOG_BTM_TGM_2.";
                this.btmdec.setData(this.body, 2);
                if (this.btmdec.isDataCollectFinished()) {
                    rtn.add((Object)new Integer(this.btmdec.getDetail()[0]));
                    rtn.add((Object)new Integer(this.btmdec.getDetail()[1]));
                    rtn.add((Object)this.btmdec.getGradient());
                    rtn.add((Object)this.btmdec.getCurve());
                    this.BTMdescrip.append(this.btmdec.toString());
                }
                else {
                    rtn.add((Object)this.description);
                }
                return rtn;
            }
            case 45: {
                MVBPort = 645;
                this.description = "MVB_LOG_BTM_TGM_3.";
                this.btmdec.setData(this.body, 3);
                if (this.btmdec.isDataCollectFinished()) {
                    rtn.add((Object)new Integer(this.btmdec.getDetail()[0]));
                    rtn.add((Object)new Integer(this.btmdec.getDetail()[1]));
                    rtn.add((Object)this.btmdec.getGradient());
                    rtn.add((Object)this.btmdec.getCurve());
                    this.BTMdescrip.append(this.btmdec.toString());
                }
                else {
                    rtn.add((Object)this.description);
                }
                return rtn;
            }
            case 46: {
                MVBPort = 646;
                this.description = "MVB_LOG_BTM_TGM_4.";
                this.btmdec.setData(this.body, 4);
                if (this.btmdec.isDataCollectFinished()) {
                    rtn.add((Object)new Integer(this.btmdec.getDetail()[0]));
                    rtn.add((Object)new Integer(this.btmdec.getDetail()[1]));
                    rtn.add((Object)this.btmdec.getGradient());
                    rtn.add((Object)this.btmdec.getCurve());
                    this.BTMdescrip.append(this.btmdec.toString());
                }
                else {
                    rtn.add((Object)this.description);
                }
                return rtn;
            }
            case 47: {
                MVBPort = 647;
                this.description = "MVB_LOG_BTM_TGM_5.";
                this.btmdec.setData(this.body, 5);
                if (this.btmdec.isDataCollectFinished()) {
                    rtn.add((Object)new Integer(this.btmdec.getDetail()[0]));
                    rtn.add((Object)new Integer(this.btmdec.getDetail()[1]));
                    rtn.add((Object)this.btmdec.getGradient());
                    rtn.add((Object)this.btmdec.getCurve());
                    this.BTMdescrip.append(this.btmdec.toString());
                }
                else {
                    rtn.add((Object)this.description);
                }
                return rtn;
            }
            case 51: {
                MVBPort = 256;
                rtn.add((Object)(this.description = "MVB_LOG_SDU1: no packet format to decode."));
                return rtn;
            }
            case 52: {
                MVBPort = 272;
                rtn.add((Object)(this.description = "MVB_LOG_SDU2: no packet format to decode."));
                return rtn;
            }
            case 61: {
                MVBPort = 1283;
                rtn.add((Object)(this.description = "MVB_LOG_ODO_CONFIG_1: no packet format to decode."));
                return rtn;
            }
            case 62: {
                MVBPort = 1285;
                rtn.add((Object)(this.description = "MVB_LOG_ODO_MESSAGE_1: no packet format to decode."));
                return rtn;
            }
            case 63: {
                MVBPort = 1286;
                rtn.add((Object)(this.description = "MVB_LOG_ODO_MESSAGE_2: no packet format to decode."));
                return rtn;
            }
            case 64: {
                MVBPort = 1284;
                rtn.add((Object)(this.description = "MVB_LOG_ODO_BTM_STATUS_1: no packet format to decode."));
                return rtn;
            }
            case 71: {
                MVBPort = 100;
                rtn.add((Object)(this.description = "MVB_LOG_PM_LOG_TGM: no packet format to decode."));
                return rtn;
            }
            case 72: {
                MVBPort = 1287;
                rtn.add((Object)(this.description = "MVB_LOG_PM_APP_LOG_TGM: no packet format to decode."));
                return rtn;
            }
            case 2: {
                this.description = "STATUS ATP.";
                rtn.add((Object)new Integer(Byte2Number.getUnsigned(this.body[0])));
                rtn.add((Object)this.description);
                return rtn;
            }
            case 3: {
                this.description = "STATUS MMI.";
                rtn.add((Object)new Integer(Byte2Number.getUnsigned(this.body[0])));
                rtn.add((Object)this.description);
                return rtn;
            }
            case 91: {
                rtn.add((Object)(this.description = "PRS INFO."));
                return rtn;
            }
            case 221: {
                this.description = "STATUS COUNTER BOARD.";
                rtn.add((Object)new Integer(Byte2Number.getUnsigned(this.body[0])));
                rtn.add((Object)this.description);
                return rtn;
            }
            case 225: {
                this.description = "STATUS DATA DOWNLOAD.";
                rtn.add((Object)new Integer(Byte2Number.getUnsigned(this.body[0])));
                rtn.add((Object)this.description);
                return rtn;
            }
            case 228: {
                this.description = "STATUS GPP.";
                rtn.add((Object)new Integer(Byte2Number.getUnsigned(this.body[0])));
                rtn.add((Object)this.description);
                return rtn;
            }
            case 227: {
                this.description = "STATUS MVB.";
                rtn.add((Object)new Integer(Byte2Number.getUnsigned(this.body[0])));
                rtn.add((Object)this.description);
                return rtn;
            }
            case 223: {
                this.description = "STATUS PRS.";
                rtn.add((Object)new Integer(Byte2Number.getUnsigned(this.body[0])));
                rtn.add((Object)this.description);
                return rtn;
            }
            case 224: {
                this.description = "STATUS SPEEDMETER.";
                rtn.add((Object)new Integer(Byte2Number.getUnsigned(this.body[0])));
                rtn.add((Object)this.description);
                return rtn;
            }
            case 222: {
                this.description = "STATUS USB.";
                rtn.add((Object)new Integer(Byte2Number.getUnsigned(this.body[0])));
                rtn.add((Object)this.description);
                return rtn;
            }
            case 201: {
                this.description = "ATP DOWN: ";
                final int location = this.mmivariables.MMI_O_TRAIN(this.body[0], this.body[1], this.body[2], this.body[3]);
                // Speed is 4 bytes (body[4]-body[7]) according to ATPRU-LOGF-001 v1.8 spec
                final int speed = Byte2Number.getSigned(this.body[4], this.body[5], this.body[6], this.body[7]);
                rtn.add((Object)new Integer(speed));
                rtn.add((Object)new Integer(location));
                rtn.add((Object)this.description);
                return rtn;
            }
            case 211: {
                this.description = "PERIODIC_SPEED_DISTANCE";
                rtn.add((Object)"1");
                rtn.add((Object)this.description);
                return rtn;
            }
            case 216: {
                this.description = "MVB LOG TYPE BUTTON EVENT: ";
                rtn.add((Object)new Integer(Byte2Number.getUnsigned(this.body[0])));
                rtn.add((Object)this.description);
                return rtn;
            }
            default: {
                rtn.add((Object)(this.description = "no handle Record Type:" + this.packetNo));
                return rtn;
            }
        }
    }
}
