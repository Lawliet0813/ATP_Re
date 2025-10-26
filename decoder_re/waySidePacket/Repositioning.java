package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.DefaultTelegram;

public class Repositioning extends DefaultTelegram {
  private int Q_SCALE = -1;
  
  private int L_SECTION = -1;
  
  public Repositioning(byte[] data) {
    super(data);
    go();
  }
  
  public void go() {
    try {
      byte[] tmpdata = HexCode.getBit(getCode(), 23, 25);
      this.Q_SCALE = Byte2Number.getUnsigned(tmpdata[0]);
      tmpdata = HexCode.getBit(getCode(), 25, 40);
      this.L_SECTION = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public int get_Q_SCALE() {
    return this.Q_SCALE;
  }
  
  public int get_L_SECTION() {
    return this.L_SECTION;
  }
  
  public String toString() {
    StringBuffer tmp = new StringBuffer();
    tmp.append("== P16, Repositioning. ==");
    tmp.append("\r\n");
    tmp.append(super.toString());
    tmp.append("\r\n");
    tmp.append("Q_SCALE:\t" + get_Q_SCALE());
    tmp.append("\r\n");
    tmp.append("L_SECTION:\t" + get_L_SECTION());
    tmp.append("\r\n");
    return tmp.toString();
  }
}
