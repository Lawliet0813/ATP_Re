package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.P44;

public class Auxiliary4bitdata extends P44 {
  String a4bit = "0000";
  
  public Auxiliary4bitdata(byte[] data) {
    super(data);
    go();
  }
  
  private void go() {
    try {
      byte[] tmpdata = HexCode.getBit(getCode(), 40, 44);
      this.a4bit = HexCode.decodeToChar(tmpdata).substring(0, 4);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public String get_M_AUXILIARY_4_BIT_OUTPUT() {
    return this.a4bit;
  }
  
  public String toString() {
    try {
      StringBuffer tmp = new StringBuffer();
      tmp.append("== P44:1, Auxiliary 4-bit data.==");
      tmp.append("\r\n");
      tmp.append(super.toString());
      tmp.append("\r\n");
      tmp.append("M_AUXILIARY_4-bit_OUTPUT:\t" + get_M_AUXILIARY_4_BIT_OUTPUT());
      tmp.append("\r\n");
      return tmp.toString();
    } catch (Exception ex) {
      ex.printStackTrace();
      return ">>Exception<<\r\n" + getClass() + "\r\n" + super.toString();
    } 
  }
}
