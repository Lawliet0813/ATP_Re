package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.P44;

public class WheelWearCompensation extends P44 {
  private int D_MEASURE = -1;
  
  private int M_ENDNIDBG = -1;
  
  public WheelWearCompensation(byte[] data) {
    super(data);
    go();
  }
  
  private void go() {
    try {
      byte[] tmpdata = HexCode.getBit(getCode(), 40, 56);
      this.D_MEASURE = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
      tmpdata = HexCode.getBit(getCode(), 56, 70);
      this.M_ENDNIDBG = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public int get_D_MEASURE() {
    return this.D_MEASURE;
  }
  
  public int get_M_ENDIDBG() {
    return this.M_ENDNIDBG;
  }
  
  public String toString() {
    StringBuffer tmp = new StringBuffer();
    tmp.append("== P44:5, Wheel Wear Compensation. ==");
    tmp.append("\r\n");
    tmp.append(super.toString());
    tmp.append("\r\n");
    tmp.append("D_MEASURE:\t" + get_D_MEASURE());
    tmp.append("\r\n");
    tmp.append("M_ENDNIDBG:\t" + get_M_ENDIDBG());
    tmp.append("\r\n");
    return tmp.toString();
  }
}
