package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.DefaultTelegram;

public class Linking extends DefaultTelegram {
  private int location;
  
  private int start;
  
  private int Q_SCALE = -1;
  
  private int N_ITER = 0;
  
  public Linking(byte[] data) {
    super(data);
    go();
  }
  
  public void go() {
    try {
      byte[] tmpdata = HexCode.getBit(getCode(), 23, 25);
      this.Q_SCALE = Byte2Number.getUnsigned(tmpdata[0]);
      tmpdata = HexCode.getBit(getCode(), 64, 69);
      this.N_ITER = Byte2Number.getUnsigned(tmpdata[0]);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public int get_Q_SCALE() {
    return this.Q_SCALE;
  }
  
  public int get_N_ITER() {
    return this.N_ITER;
  }
  
  public int[] getNext(int no) throws Exception {
    if (no == 0) {
      loc = 25;
    } else {
      loc = 69 + no * 39;
    } 
    int[] nextBG = new int[6];
    int locend = loc + 15;
    byte[] tmpdata = HexCode.getBit(getCode(), loc, locend);
    nextBG[0] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
    int loc = locend;
    locend = loc + 1;
    tmpdata = HexCode.getBit(getCode(), loc, locend);
    nextBG[1] = Byte2Number.getUnsigned(tmpdata[0]);
    loc = locend;
    locend = loc + 14;
    tmpdata = HexCode.getBit(getCode(), loc, locend);
    nextBG[2] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
    loc = locend;
    locend = loc + 1;
    tmpdata = HexCode.getBit(getCode(), loc, locend);
    nextBG[3] = Byte2Number.getUnsigned(tmpdata[0]);
    loc = locend;
    locend = loc + 2;
    tmpdata = HexCode.getBit(getCode(), loc, locend);
    nextBG[4] = Byte2Number.getUnsigned(tmpdata[0]);
    loc = locend;
    locend = loc + 6;
    tmpdata = HexCode.getBit(getCode(), loc, locend);
    nextBG[5] = Byte2Number.getUnsigned(tmpdata[0]);
    return nextBG;
  }
  
  public String toString() {
    try {
      StringBuffer tmp = new StringBuffer();
      tmp.append("== P5, Linking. ==");
      tmp.append("\r\n");
      tmp.append(super.toString());
      tmp.append("\r\n");
      tmp.append("Q_SCALE:\t" + get_Q_SCALE());
      tmp.append("\r\n");
      int[] iter = getNext(0);
      tmp.append("D_LINK:\t" + iter[0]);
      tmp.append("\r\n");
      tmp.append("D_Q_NEWCOUNTRY:\t" + iter[1]);
      tmp.append("\r\n");
      tmp.append("NID_BG:\t" + iter[2]);
      tmp.append("\r\n");
      tmp.append("Q_LINKORIENTATION:\t" + iter[3]);
      tmp.append("\r\n");
      tmp.append("D_LINKREACTION:\t" + iter[4]);
      tmp.append("\r\n");
      tmp.append("D_LINKACC:\t" + iter[5]);
      tmp.append("\r\n");
      tmp.append("N_ITER:\t" + get_N_ITER());
      tmp.append("\r\n");
      for (int x = 0; x < get_N_ITER(); x++) {
        iter = getNext(x);
        tmp.append("\t");
        tmp.append("D_LINK(" + x + '\001' + "):\t" + iter[0]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("D_Q_NEWCOUNTRY(" + x + '\001' + "):\t" + iter[1]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("NID_BG(" + x + '\001' + "):\t" + iter[2]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("Q_LINKORIENTATION(" + x + '\001' + "):\t" + iter[3]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("D_LINKREACTION(" + x + '\001' + "):\t" + iter[4]);
        tmp.append("\r\n");
        tmp.append("\t");
        tmp.append("D_LINKACC(" + x + '\001' + "):\t" + iter[5]);
        tmp.append("\r\n");
      } 
      return tmp.toString();
    } catch (Exception ex) {
      ex.printStackTrace();
      return ">>Exception<<\r\n" + getClass() + "\r\n" + super.toString();
    } 
  }
}
