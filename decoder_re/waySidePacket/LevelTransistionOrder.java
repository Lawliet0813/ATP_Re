package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.DefaultTelegram;

public class LevelTransistionOrder extends DefaultTelegram {
  private int Q_SCALE = -1;
  
  private int D_LEVELTR = -1;
  
  private int M_LEVELTR = -1;
  
  private int L_ACKLEVELTR = -1;
  
  private int N_ITER = 0;
  
  public LevelTransistionOrder(byte[] data) {
    super(data);
    go();
  }
  
  private void go() {
    try {
      byte[] tmpdata = HexCode.getBit(getCode(), 23, 25);
      this.Q_SCALE = Byte2Number.getUnsigned(tmpdata[0]);
      tmpdata = HexCode.getBit(getCode(), 25, 40);
      this.D_LEVELTR = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
      tmpdata = HexCode.getBit(getCode(), 40, 43);
      this.M_LEVELTR = Byte2Number.getUnsigned(tmpdata[0]);
      tmpdata = HexCode.getBit(getCode(), 43, 58);
      this.L_ACKLEVELTR = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
      tmpdata = HexCode.getBit(getCode(), 58, 63);
      this.N_ITER = Byte2Number.getUnsigned(tmpdata[0]);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public int get_Q_SCALE() {
    return this.Q_SCALE;
  }
  
  public int get_D_LEVELTR() {
    return this.D_LEVELTR;
  }
  
  public int get_M_LEVELTR() {
    return this.M_LEVELTR;
  }
  
  public int get_L_ACKLEVELTR() {
    return this.L_ACKLEVELTR;
  }
  
  public int get_N_ITER() {
    return this.N_ITER;
  }
  
  public String toString() {
    try {
      StringBuffer tmp = new StringBuffer();
      tmp.append("== P41, Level Transistion Order. ==");
      tmp.append("\r\n");
      tmp.append(super.toString());
      tmp.append("\r\n");
      tmp.append("Q_SCALE:\t" + get_Q_SCALE());
      tmp.append("\r\n");
      tmp.append("D_LEVELTR:\t" + get_D_LEVELTR());
      tmp.append("\r\n");
      tmp.append("M_LEVELTR:\t" + get_M_LEVELTR());
      tmp.append("\r\n");
      tmp.append("L_ACKLEVELTR:\t" + get_L_ACKLEVELTR());
      tmp.append("\r\n");
      tmp.append("N_ITER:\t" + get_N_ITER());
      tmp.append("\r\n");
      return tmp.toString();
    } catch (Exception ex) {
      ex.printStackTrace();
      return ">>Exception<<\r\n" + getClass() + "\r\n" + super.toString();
    } 
  }
}
