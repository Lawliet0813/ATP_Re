package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.DefaultTelegram;

public class InfillLocationReference extends DefaultTelegram {
  private int Q_NEWCOUNTRY = -1;
  
  private int NID_BG = -1;
  
  public InfillLocationReference(byte[] data) {
    super(data);
    go();
  }
  
  private void go() {
    try {
      byte[] tmpdata = HexCode.getBit(getCode(), 23, 24);
      this.Q_NEWCOUNTRY = Byte2Number.getUnsigned(tmpdata[0]);
      tmpdata = HexCode.getBit(getCode(), 24, 38);
      this.NID_BG = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public int get_Q_NEWCOUNTRY() {
    return this.Q_NEWCOUNTRY;
  }
  
  public int get_NID_BG() {
    return this.NID_BG;
  }
  
  public String toString() {
    try {
      StringBuffer tmp = new StringBuffer();
      tmp.append("== P136, Infill Location Reference. ==");
      tmp.append("\r\n");
      tmp.append(super.toString());
      tmp.append("\r\n");
      tmp.append("Q_NEWCOUNTRY:\t" + get_Q_NEWCOUNTRY());
      tmp.append("\r\n");
      tmp.append("NID_BG:\t" + get_NID_BG());
      tmp.append("\r\n");
      return tmp.toString();
    } catch (Exception ex) {
      ex.printStackTrace();
      return ">>Exception<<\r\n" + getClass() + "\r\n" + super.toString();
    } 
  }
}
