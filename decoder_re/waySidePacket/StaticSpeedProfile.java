package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.DefaultTelegram;

public class StaticSpeedProfile extends DefaultTelegram {
  private int Q_SCALE = -1;
  
  private int D_STATIC = -1;
  
  private int V_STATIC = -1;
  
  private int Q_FRONT = -1;
  
  private int N_ITER = 0;
  
  private int[][] iter;
  
  private int N_ITER2 = 0;
  
  private int[][] iter2;
  
  public StaticSpeedProfile(byte[] data) {
    super(data);
    go();
  }
  
  public void go() {
    try {
      byte[] tmpdata = HexCode.getBit(getCode(), 23, 25);
      this.Q_SCALE = Byte2Number.getUnsigned(tmpdata[0]);
      tmpdata = HexCode.getBit(getCode(), 25, 40);
      this.D_STATIC = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
      tmpdata = HexCode.getBit(getCode(), 40, 47);
      this.V_STATIC = Byte2Number.getUnsigned(tmpdata[0]);
      tmpdata = HexCode.getBit(getCode(), 47, 48);
      this.Q_FRONT = Byte2Number.getUnsigned(tmpdata[0]);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public int get_Q_SCALE() {
    return this.Q_SCALE;
  }
  
  public int get_D_STATIC() {
    return this.D_STATIC;
  }
  
  public int get_V_STATIC() {
    return this.V_STATIC;
  }
  
  public int get_Q_FRONT() {
    return this.Q_FRONT;
  }
  
  public String toString() {
    try {
      StringBuffer tmp = new StringBuffer();
      tmp.append("== P27, Static Speed Profile(SSP). ==");
      tmp.append("\r\n");
      tmp.append(super.toString());
      tmp.append("\r\n");
      tmp.append("Q_SCALE:\t" + get_Q_SCALE());
      tmp.append("\r\n");
      tmp.append("D_STATIC:\t" + get_D_STATIC());
      tmp.append("\r\n");
      tmp.append("V_STATIC:\t" + get_V_STATIC());
      tmp.append("\r\n");
      tmp.append("Q_FRONT:\t" + get_Q_FRONT());
      tmp.append("\r\n");
      byte[] tmpdata = HexCode.getBit(getCode(), 48, 53);
      int iter = Byte2Number.getUnsigned(tmpdata[0]);
      tmp.append("N_ITER:\t" + iter);
      tmp.append("\r\n");
      int loc = 53;
      int x;
      for (x = 0; x < iter; x++) {
        tmpdata = HexCode.getBit(getCode(), loc, loc + 4);
        loc += 4;
        int nc = Byte2Number.getUnsigned(tmpdata[0]);
        tmp.append("NC_DIFF(" + x + "):\t" + nc);
        tmp.append("\r\n");
        tmpdata = HexCode.getBit(getCode(), loc, loc + 7);
        loc += 7;
        int v = Byte2Number.getUnsigned(tmpdata[0]);
        tmp.append("V_DIFF(" + x + "):\t" + v);
        tmp.append("\r\n");
      } 
      tmpdata = HexCode.getBit(getCode(), loc, loc + 5);
      loc += 5;
      iter = Byte2Number.getUnsigned(tmpdata[0]);
      tmp.append("N_ITER:\t" + iter);
      tmp.append("\r\n");
      for (x = 0; x < iter; x++) {
        tmpdata = HexCode.getBit(getCode(), loc, loc + 15);
        loc += 15;
        int ds = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
        tmp.append("\t");
        tmp.append("D_STATIC(" + x + "):\t" + ds);
        tmp.append("\r\n");
        tmpdata = HexCode.getBit(getCode(), loc, loc + 7);
        loc += 7;
        int vs = Byte2Number.getUnsigned(tmpdata[0]);
        tmp.append("\t");
        tmp.append("V_STATIC(" + x + "):\t" + vs);
        tmp.append("\r\n");
        tmpdata = HexCode.getBit(getCode(), loc, loc + 1);
        loc += 11;
        int qf = Byte2Number.getUnsigned(tmpdata[0]);
        tmp.append("\t");
        tmp.append("Q_FRONT(" + x + "):\t" + qf);
        tmp.append("\r\n");
        tmpdata = HexCode.getBit(getCode(), loc, loc + 5);
        loc += 5;
        int iter2 = Byte2Number.getUnsigned(tmpdata[0]);
        tmp.append("\t");
        tmp.append("N_ITER(" + x + "):\t" + iter2);
        tmp.append("\r\n");
        for (int z = 0; z < iter2; z++) {
          tmpdata = HexCode.getBit(getCode(), loc, loc + 4);
          loc += 4;
          int nc = Byte2Number.getUnsigned(tmpdata[0]);
          tmp.append("\t");
          tmp.append("\t");
          tmp.append("NC_DIFF(" + x + "," + z + "):\t" + nc);
          tmp.append("\r\n");
          tmpdata = HexCode.getBit(getCode(), loc, loc + 7);
          loc += 7;
          int vd = Byte2Number.getUnsigned(tmpdata[0]);
          tmp.append("\t");
          tmp.append("\t");
          tmp.append("V_DIFF(" + x + "," + z + "):\t" + vd);
          tmp.append("\r\n");
        } 
      } 
      return tmp.toString();
    } catch (Exception ex) {
      ex.printStackTrace();
      return ">>Exception<<\r\n" + getClass() + "\r\n" + super.toString();
    } 
  }
}
