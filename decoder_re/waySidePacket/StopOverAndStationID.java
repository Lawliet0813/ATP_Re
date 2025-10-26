package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.P44;

public class StopOverAndStationID extends P44 {
  private int Q_STATIONID = -1;
  
  private int D_DISPLAY = -1;
  
  private int D_STATIONSTART = -1;
  
  private int L_STATION = -1;
  
  private int V_STATIONEND = -1;
  
  private int Q_DOORCONTROL = -1;
  
  public StopOverAndStationID(byte[] data) {
    super(data);
    go();
  }
  
  private void go() {
    try {
      byte[] tmpdata = HexCode.getBit(getCode(), 40, 49);
      this.Q_STATIONID = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
      tmpdata = HexCode.getBit(getCode(), 49, 62);
      this.D_DISPLAY = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
      tmpdata = HexCode.getBit(getCode(), 62, 75);
      this.D_STATIONSTART = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
      tmpdata = HexCode.getBit(getCode(), 75, 87);
      this.L_STATION = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
      tmpdata = HexCode.getBit(getCode(), 87, 94);
      this.V_STATIONEND = Byte2Number.getUnsigned(tmpdata[0]);
      tmpdata = HexCode.getBit(getCode(), 94, 96);
      this.Q_DOORCONTROL = Byte2Number.getUnsigned(tmpdata[0]);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public int get_Q_STATIONID() {
    return this.Q_STATIONID;
  }
  
  public int get_D_DISPLAY() {
    return this.D_DISPLAY;
  }
  
  public int get_D_STATIONSTART() {
    return this.D_STATIONSTART;
  }
  
  public int get_L_STATION() {
    return this.L_STATION;
  }
  
  public int get_V_STATIONEND() {
    return this.V_STATIONEND;
  }
  
  public int get_Q_DOORCONTROL() {
    return this.Q_DOORCONTROL;
  }
  
  public String toString() {
    StringBuffer tmp = new StringBuffer();
    tmp.append("== P44:2, Stop over and Station ID. ==");
    tmp.append("\r\n");
    tmp.append(super.toString());
    tmp.append("\r\n");
    tmp.append("Q_STATIONID:\t" + get_Q_STATIONID());
    tmp.append("\r\n");
    tmp.append("D_DISPLAY:\t" + get_D_DISPLAY());
    tmp.append("\r\n");
    tmp.append("D_STATIONSTART:\t" + get_D_STATIONSTART());
    tmp.append("\r\n");
    tmp.append("L_STATION:\t" + get_L_STATION());
    tmp.append("\r\n");
    tmp.append("V_STATIONEND:\t" + get_V_STATIONEND());
    tmp.append("\r\n");
    tmp.append("Q_DOORCONTROL:\t" + get_Q_DOORCONTROL());
    tmp.append("\r\n");
    return tmp.toString();
  }
}
