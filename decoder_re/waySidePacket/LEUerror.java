package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.P44;

public class LEUerror extends P44 {
  private int M_LEUERROR = -1;
  
  public LEUerror(byte[] data) {
    super(data);
    go();
  }
  
  private void go() {
    try {
      byte[] tmpdata = HexCode.getBit(getCode(), 40, 42);
      this.M_LEUERROR = Byte2Number.getUnsigned(tmpdata[0]);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public int get_M_LEUERROR() {
    return this.M_LEUERROR;
  }
  
  public String toString() {
    StringBuffer tmp = new StringBuffer();
    tmp.append("== P44:4, LEU error. ==");
    tmp.append("\r\n");
    tmp.append(super.toString());
    tmp.append("\r\n");
    tmp.append("M_LEUERROR:\t" + get_M_LEUERROR());
    tmp.append("\r\n");
    return tmp.toString();
  }
}
