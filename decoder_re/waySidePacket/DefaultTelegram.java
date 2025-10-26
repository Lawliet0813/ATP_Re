package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.decoder.waySidePacket.waySidePacketBody;

public class DefaultTelegram extends waySidePacketBody {
  public DefaultTelegram(byte[] data) {
    setCode(data);
  }
  
  public String toString() {
    try {
      StringBuffer tmp = new StringBuffer();
      tmp.append("NID_PACKET:\t" + get_NID_PACKET());
      tmp.append("\r\n");
      tmp.append("Q_DIR:\t" + get_Q_DIR());
      tmp.append("\r\n");
      tmp.append("L_PACKET:\t" + get_L_PACKET());
      return tmp.toString();
    } catch (Exception ex) {
      ex.printStackTrace();
      return ">>Exception<<\r\nDefault packet";
    } 
  }
}
