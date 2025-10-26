package decode;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.decode.PacketMMI;
import java.util.Vector;

public class DecodeATP {
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
  
  public void setData(byte[] paramArrayOfbyte) throws Exception {
    Vector vector;
    this._$25799 = new Vector();
    this._$25887 = new Vector();
    this._$22269 = new Vector();
    this._$22271 = new Vector();
    this._$22272 = new Vector();
    this._$22273 = new Vector();
    this._$22274 = new Vector();
    this._$22275 = new Vector();
    this._$25799.add("ATP MMI packet NO:" + Byte2Number.getUnsigned(paramArrayOfbyte[1]));
    switch (Byte2Number.getUnsigned(paramArrayOfbyte[1])) {
      case 1:
        vector = PacketMMI.MMI_DYMANIC(paramArrayOfbyte);
        this._$22271.add(vector.get(0));
        this._$22271.add(vector.get(2));
        this._$22272.add(vector.get(1));
        this._$22272.add(vector.get(2));
        this._$22272.add(vector.get(3));
        this._$22272.add(vector.get(4));
        this._$22272.add(vector.get(6));
        this._$22272.add(vector.get(7));
        this._$22272.add(vector.get(9));
        this._$22272.add(vector.get(10));
        this._$22272.add(vector.get(11));
        break;
      case 2:
        this._$22273 = PacketMMI.MMI_STATUS(paramArrayOfbyte);
        break;
      case 6:
        this._$22269 = PacketMMI.MMI_CURRENT_TRAIN_DATA(paramArrayOfbyte);
        break;
      case 8:
        this._$22275 = PacketMMI.MMI_DRIVER_MESSAGE(paramArrayOfbyte);
        break;
      case 9:
        this._$22274 = PacketMMI.MMI_FAILURE_REPORT_ATP(paramArrayOfbyte);
        break;
      case 14:
        this._$25887 = PacketMMI.MMI_CURRENT_DRIVER_DATA(paramArrayOfbyte);
        break;
    } 
  }
}
