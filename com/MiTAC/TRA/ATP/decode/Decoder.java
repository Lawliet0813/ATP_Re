// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decode;

import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.FileRead;
import java.util.Vector;

public class Decoder
{
    private Vector _$25801;
    private Vector _$25799;
    private Vector _$25800;
    private Vector _$25802;
    private Vector _$22268;
    private Vector _$22275;
    private Vector _$22272;
    private Vector _$22274;
    private Vector _$22273;
    private Vector _$22271;
    private Vector _$22269;
    private Vector _$25798;
    private DecodeATP _$25797;
    private int _$4027;
    
    public Decoder(final String path) throws Exception {
        this._$4027 = 0;
        this._$25797 = new DecodeATP();
        this.setPath(path);
    }
    
    public Vector getATP() {
        return this._$25799;
    }
    
    public Vector getAll() {
        return this._$25801;
    }
    
    public Vector getErr() {
        return this._$25800;
    }
    
    public Vector getLogDriverData() {
        return this._$22268;
    }
    
    public Vector getLogDriverMessage() {
        return this._$22275;
    }
    
    public Vector getLogDynamic() {
        return this._$22272;
    }
    
    public Vector getLogFailure() {
        return this._$22274;
    }
    
    public Vector getLogStatus() {
        return this._$22273;
    }
    
    public Vector getLogTS() {
        return this._$22271;
    }
    
    public Vector getLogTrainData() {
        return this._$22269;
    }
    
    public Vector getTS() {
        return this._$25798;
    }
    
    public void setPath(final String s) throws Exception {
        this._$25798 = new Vector();
        this._$25799 = new Vector();
        this._$25800 = new Vector();
        this._$25801 = new Vector();
        this._$22268 = new Vector();
        this._$22269 = new Vector();
        this._$22271 = new Vector();
        this._$22272 = new Vector();
        this._$22273 = new Vector();
        this._$22274 = new Vector();
        this._$22275 = new Vector();
        this._$25802 = new Vector();
        final FileRead fileRead = new FileRead(s);
        final byte[] codes = fileRead.getCodes();
        final Vector vector = new Vector();
        while (this._$4027 <= codes.length - 1) {
            final byte[] codes2 = fileRead.getCodes(this._$4027 + 1, 14);
            final String s2 = DecodeTS.getTS(codes2).get(0);
            final Integer e = DecodeTS.getTS(codes2).get(1);
            final Integer e2 = DecodeTS.getTS(codes2).get(2);
            this._$22271.add(DecodeTS.getTS(codes2));
            int value = 0;
            Vector<Vector<Integer>> vector2 = null;
            final byte[] array = new byte[Byte2Number.getUnsigned(codes[this._$4027 + 15])];
            final byte[] codes3 = fileRead.getCodes(this._$4027 + 15, Byte2Number.getUnsigned(codes[this._$4027 + 15]) + 1);
            String e3 = null;
            switch (Byte2Number.getUnsigned(codes[this._$4027])) {
                case 1:
                case 4: {
                    this._$25797.setData(codes3);
                    final Vector logDynamic = this._$25797.getLogDynamic();
                    if (logDynamic.size() != 0) {
                        logDynamic.insertElementAt(s2, 0);
                        this._$22272.add(logDynamic);
                    }
                    final Vector logStatus = this._$25797.getLogStatus();
                    if (logStatus.size() != 0) {
                        logStatus.insertElementAt(s2, 0);
                        this._$22273.add(logStatus);
                    }
                    final Vector logDriver = this._$25797.getLogDriver();
                    if (logDriver.size() != 0) {
                        logDriver.insertElementAt(s2, 0);
                        this._$22268.add(logDriver);
                    }
                    final Vector logTrainData = this._$25797.getLogTrainData();
                    if (logTrainData.size() != 0) {
                        logTrainData.insertElementAt(s2, 0);
                        this._$22269.add(logTrainData);
                    }
                    final Vector logFailure = this._$25797.getLogFailure();
                    if (logFailure.size() != 0) {
                        logFailure.insertElementAt(s2, 0);
                        this._$22274.add(logFailure);
                    }
                    final Vector logDriverMessage = this._$25797.getLogDriverMessage();
                    if (logDriverMessage.size() != 0) {
                        logDriverMessage.insertElementAt(s2, 0);
                        this._$22275.add(logDriverMessage);
                    }
                    e3 = "" + this._$25797.getData();
                    vector2 = this._$25799;
                    break;
                }
                case 21: {
                    value = 642;
                    e3 = "MVB_LOG_TYPE_VDX_IN_STATUS_1: no packet format to decode.";
                    break;
                }
                case 22: {
                    value = 392;
                    e3 = "MVB_LOG_TYPE_VDX_OUT_1: no packet format to decode.";
                    break;
                }
                case 23: {
                    value = 393;
                    e3 = "MVB_LOG_TYPE_VDX_OUT_2: no packet format to decode.";
                    break;
                }
                case 24: {
                    value = 394;
                    e3 = "MVB_LOG_TYPE_VDX_OUT_3: no packet format to decode.";
                    break;
                }
                case 31: {
                    value = 2176;
                    e3 = "MVB_LOG_TYPE_DX_IN_STATUS_1: no packet format to decode.";
                    break;
                }
                case 32: {
                    value = 2177;
                    e3 = "MVB_LOG_TYPE_DX_STATUS_1: no packet format to decode.";
                    break;
                }
                case 33: {
                    value = 2184;
                    e3 = "MVB_LOG_TYPE_OUT_STATUS_1: no packet format to decode.";
                    break;
                }
                case 41: {
                    value = 648;
                    e3 = "MVB_LOG_BTM_COMMAND_1: no packet format to decode.";
                    break;
                }
                case 42: {
                    value = 42;
                    e3 = "MVB_LOG_BTM_STATUS_1: no packet format to decode.";
                    break;
                }
                case 43: {
                    value = 643;
                    e3 = "MVB_LOG_BTM_TGM_1: no packet format to decode.";
                    break;
                }
                case 44: {
                    value = 644;
                    e3 = "MVB_LOG_BTM_TGM_2: no packet format to decode.";
                    break;
                }
                case 45: {
                    value = 645;
                    e3 = "MVB_LOG_BTM_TGM_3: no packet format to decode.";
                    break;
                }
                case 46: {
                    value = 646;
                    e3 = "MVB_LOG_BTM_TGM_4: no packet format to decode.";
                    break;
                }
                case 47: {
                    value = 647;
                    e3 = "MVB_LOG_BTM_TGM_5: no packet format to decode.";
                    break;
                }
                case 51: {
                    value = 256;
                    e3 = "MVB_LOG_SDU1: no packet format to decode.";
                    break;
                }
                case 52: {
                    value = 272;
                    e3 = "MVB_LOG_SDU2: no packet format to decode.";
                    break;
                }
                case 61: {
                    value = 1283;
                    e3 = "MVB_LOG_ODO_CONFIG_1: no packet format to decode.";
                    break;
                }
                case 62: {
                    value = 1285;
                    e3 = "MVB_LOG_ODO_MESSAGE_1: no packet format to decode.";
                    break;
                }
                case 63: {
                    value = 1286;
                    e3 = "MVB_LOG_ODO_MESSAGE_2: no packet format to decode.";
                    break;
                }
                case 64: {
                    value = 1284;
                    e3 = "MVB_LOG_ODO_BTM_STATUS_1: no packet format to decode.";
                    break;
                }
                case 71: {
                    value = 100;
                    e3 = "MVB_LOG_PM_LOG_TGM: no packet format to decode.";
                    break;
                }
                case 72: {
                    value = 1287;
                    e3 = "MVB_LOG_PM_APP_LOG_TGM: no packet format to decode.";
                    break;
                }
                case 2: {
                    e3 = "STATUS ATP: no packet format to decode.";
                    break;
                }
                case 3: {
                    e3 = "STATUS MMI: no packet format to decode.";
                    break;
                }
                case 91: {
                    e3 = "PRS INFO: no packet format to decode.";
                    break;
                }
                case 221: {
                    e3 = "STATUS COUNTER BOARD: no packet format to decode.";
                    break;
                }
                case 225: {
                    e3 = "STATUS DATA DOWNLOAD: no packet format to decode.";
                    break;
                }
                case 228: {
                    e3 = "STATUS GPP: no packet format to decode.";
                    break;
                }
                case 227: {
                    e3 = "STATUS MVB: no packet format to decode.";
                    break;
                }
                case 223: {
                    e3 = "STATUS PRS: no packet format to decode.";
                    break;
                }
                case 224: {
                    e3 = "STATUS SPEEDMETER: no packet format to decode.";
                    break;
                }
                case 222: {
                    e3 = "STATUS USB: no packet format to decode.";
                    break;
                }
                case 201: {
                    final Vector<String> e4 = new Vector<String>();
                    final int mmi_O_TRAIN = MMIVariables.MMI_O_TRAIN(codes[this._$4027 + 16], codes[this._$4027 + 17], codes[this._$4027 + 18], codes[this._$4027 + 19]);
                    final int mmi_V_TRAIN = MMIVariables.MMI_V_TRAIN(codes[this._$4027 + 22], codes[this._$4027 + 23]);
                    e4.add(s2);
                    e4.add((String)new Integer(mmi_V_TRAIN));
                    e4.add((String)new Integer(mmi_O_TRAIN));
                    this._$22271.add(e4);
                }
                case 211: {
                    e3 = "PERIODIC_SPEED_DISTANCE";
                    break;
                }
                case 216: {
                    e3 = "MVB LOG TYPE BUTTON EVENT: ";
                    break;
                }
                default: {
                    e3 = "no handle Record Type:" + Byte2Number.getUnsigned(codes[this._$4027]) + " at " + this._$4027 + " ";
                    vector2 = this._$25800;
                    break;
                }
            }
            final Vector<Integer> vector3 = new Vector<Integer>();
            vector3.add(new Integer(this._$4027));
            vector3.add((Integer)s2);
            vector3.add(e);
            vector3.add(e2);
            vector3.add(new Integer(value));
            vector3.add(new Integer(Byte2Number.getUnsigned(codes[this._$4027])));
            vector3.add(new Integer(Byte2Number.getUnsigned(codes[this._$4027 + 15])));
            vector3.add((Integer)HexCode.getHexA_String(codes3));
            vector3.add((Integer)e3);
            if (vector2 != null) {
                vector2.add(vector3);
            }
            this._$25801.add(vector3);
            this._$4027 += 15;
            this._$4027 += Byte2Number.getUnsigned(codes[this._$4027]);
            ++this._$4027;
        }
    }
}
