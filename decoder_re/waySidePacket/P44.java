package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.DefaultTelegram;

public class P44 extends DefaultTelegram {
  private int NID_XUSER = -1;
  
  private int NID_XDATA = -1;
  
  public P44(byte[] data) {
    super(data);
    go();
  }
  
  private void go() {
    try {
      byte[] tmpdata = HexCode.getBit(getCode(), 23, 32);
      this.NID_XUSER = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
      tmpdata = HexCode.getBit(getCode(), 32, 40);
      this.NID_XDATA = Byte2Number.getUnsigned(tmpdata[0]);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public int get_NID_XUSER() {
    return this.NID_XUSER;
  }
  
  public int get_NID_XDATA() {
    return this.NID_XDATA;
  }
  
  public String toString() {
    StringBuffer tmp = new StringBuffer();
    tmp.append(super.toString());
    tmp.append("\r\n");
    tmp.append("NID_XUSER:\t" + get_NID_XUSER());
    tmp.append("\r\n");
    tmp.append("NID_XDATA:\t" + get_NID_XDATA());
    tmp.append("\r\n");
    return tmp.toString();
  }
}
