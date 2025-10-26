// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decode;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import java.util.Vector;

public class DecodeATP
{
    private Vector _$25799;
    private Vector _$25887;
    private Vector _$22275;
    private Vector _$22272;
    private Vector _$22274;
    private Vector _$22273;
    private Vector _$22271;
    private Vector _$22269;
    
    public Vector getData() {
        return this._$25799;
    }
    
    public Vector getLogDriver() {
        return this._$25887;
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
    
    public void setData(final byte[] array) throws Exception {
        this._$25799 = new Vector();
        this._$25887 = new Vector();
        this._$22269 = new Vector();
        this._$22271 = new Vector();
        this._$22272 = new Vector();
        this._$22273 = new Vector();
        this._$22274 = new Vector();
        this._$22275 = new Vector();
        this._$25799.add("ATP MMI packet NO:" + Byte2Number.getUnsigned(array[1]));
        switch (Byte2Number.getUnsigned(array[1])) {
            case 1: {
                final Vector mmi_DYMANIC = PacketMMI.MMI_DYMANIC(array);
                this._$22271.add(mmi_DYMANIC.get(0));
                this._$22271.add(mmi_DYMANIC.get(2));
                this._$22272.add(mmi_DYMANIC.get(1));
                this._$22272.add(mmi_DYMANIC.get(2));
                this._$22272.add(mmi_DYMANIC.get(3));
                this._$22272.add(mmi_DYMANIC.get(4));
                this._$22272.add(mmi_DYMANIC.get(6));
                this._$22272.add(mmi_DYMANIC.get(7));
                this._$22272.add(mmi_DYMANIC.get(9));
                this._$22272.add(mmi_DYMANIC.get(10));
                this._$22272.add(mmi_DYMANIC.get(11));
                break;
            }
            case 2: {
                this._$22273 = PacketMMI.MMI_STATUS(array);
            }
            case 3: {}
            case 4: {}
            case 6: {
                this._$22269 = PacketMMI.MMI_CURRENT_TRAIN_DATA(array);
            }
            case 8: {
                this._$22275 = PacketMMI.MMI_DRIVER_MESSAGE(array);
                break;
            }
            case 9: {
                this._$22274 = PacketMMI.MMI_FAILURE_REPORT_ATP(array);
            }
            case 11: {}
            case 14: {
                this._$25887 = PacketMMI.MMI_CURRENT_DRIVER_DATA(array);
            }
            case 15: {}
            case 16: {}
            case 17: {}
            case 18: {}
            case 19: {}
            case 100: {}
            case 101: {}
            case 102: {}
            case 103: {}
            case 104: {}
            case 106: {}
            case 107: {}
            case 109: {}
            case 110: {}
            case 111: {}
            case 112: {}
            case 113: {}
            case 114: {}
            case 115: {}
            case 116: {}
            case 117: {}
            case 118: {}
        }
    }
}
