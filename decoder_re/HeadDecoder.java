package decoder;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.decoder.MMIVariables;
import java.util.Date;
import java.util.Vector;

public class HeadDecoder {
  private Date ts;
  
  private int speed;
  
  private int location;
  
  private int packetNo;
  
  private MMIVariables mmivariables = new MMIVariables();
  
  public void setByte(byte[] tsb) throws Exception {
    this.packetNo = Byte2Number.getUnsigned(tsb[0]);
    this.ts = getTime(tsb[1], tsb[2], tsb[3], tsb[4], tsb[5], tsb[6]);
    this.location = (
      new Integer(this.mmivariables.MMI_O_TRAIN(tsb[7], tsb[8], tsb[9], tsb[10]))).intValue();
    this.location = (this.location >= 1000000000) ? (this.location - 1000000000) : this.location;
    this.speed = (
      new Integer(this.mmivariables.MMI_V_TRAIN(tsb[13], tsb[14]))).intValue();
  }
  
  public Vector getData() {
    Vector tmp = new Vector();
    tmp.add(this.ts);
    tmp.add(new Integer(this.speed));
    tmp.add(new Integer(this.location));
    return tmp;
  }
  
  public int getPacketNo() {
    return this.packetNo;
  }
  
  public Date getTS() {
    return this.ts;
  }
  
  public int getSpeed() {
    return this.speed;
  }
  
  public int getLocation() {
    return this.location;
  }
  
  private Date getTime(byte yy, byte mm, byte dd, byte hh, byte MM, byte ss) throws Exception {
    int year = 2000 + Byte2Number.getUnsigned(yy);
    int month = Byte2Number.getUnsigned(mm);
    int day = Byte2Number.getUnsigned(dd);
    int hour = Byte2Number.getUnsigned(hh);
    int minute = Byte2Number.getUnsigned(MM);
    int second = Byte2Number.getUnsigned(ss);
    Date rtn = new Date(year - 1900, month - 1, day, hour, minute, second);
    return rtn;
  }
}
