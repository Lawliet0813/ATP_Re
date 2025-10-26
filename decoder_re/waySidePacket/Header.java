package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.util.Arrays;
import java.util.Vector;

public class Header {
  private static Vector signalAspect;
  
  private static int[] signalNo;
  
  private static String[] signalColor;
  
  private int L_PACKET = -1;
  
  private int Q_UPDOWN = -1;
  
  private String M_VERSION = "0010000";
  
  private int Q_MEDIA = -1;
  
  private int N_PIG = -1;
  
  private int N_TOTAL = -1;
  
  private int M_DUP = -1;
  
  private int M_MCOUNT = -1;
  
  private int NID_C = -1;
  
  private int NID_BG = -1;
  
  private int Q_LINK = -1;
  
  private String signal = "NOT FOUND.";
  
  private byte[] code;
  
  static {
    try {
      if (signalColor == null) {
        ConnectDB cndb = new ConnectDB();
        Vector data = cndb.getData("SELECT signalAspectColor FROM SignalAspect_Def ORDER BY signalAspectNo");
        signalColor = new String[data.size()];
        for (int x = 0; x < data.size(); x++)
          signalColor[x] = data.get(x).toString(); 
        Vector signal = cndb.getData("SELECT NID_BG FROM signalAspect order by 1");
        signalNo = new int[signal.size()];
        for (int i = 0; i < signal.size(); i++)
          signalNo[i] = (new Integer(((Vector)signal.get(i)).get(0).toString())).intValue(); 
        signalAspect = cndb.getData("SELECT * FROM SignalAspect order by NID_BG");
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public Header(byte[] data) {
    setCode(data);
    go();
  }
  
  private void go() {
    try {
      byte[] tmpdata = HexCode.getBit(getCode(), 0, 1);
      this.Q_UPDOWN = Byte2Number.getUnsigned(tmpdata[0]);
      tmpdata = HexCode.getBit(getCode(), 1, 8);
      this.M_VERSION = HexCode.getBinaryInteger(tmpdata).substring(1, 8);
      tmpdata = HexCode.getBit(getCode(), 8, 9);
      this.Q_MEDIA = Byte2Number.getUnsigned(tmpdata[0]);
      tmpdata = HexCode.getBit(getCode(), 9, 12);
      this.N_PIG = Byte2Number.getUnsigned(tmpdata[0]);
      tmpdata = HexCode.getBit(getCode(), 12, 15);
      this.N_TOTAL = Byte2Number.getUnsigned(tmpdata[0]);
      tmpdata = HexCode.getBit(getCode(), 15, 17);
      this.M_DUP = Byte2Number.getUnsigned(tmpdata[0]);
      tmpdata = HexCode.getBit(getCode(), 17, 25);
      this.M_MCOUNT = Byte2Number.getUnsigned(tmpdata[0]);
      tmpdata = HexCode.getBit(getCode(), 25, 35);
      this.NID_C = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
      tmpdata = HexCode.getBit(getCode(), 35, 49);
      this.NID_BG = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
      tmpdata = HexCode.getBit(getCode(), 49, 50);
      this.Q_LINK = Byte2Number.getUnsigned(tmpdata[0]);
      int no = 0;
      int mcount = get_M_MCOUNT();
      if (mcount < 10) {
        int plac = Arrays.binarySearch(signalNo, get_NID_BG());
        this.signal = String.valueOf(this.signal) + plac;
        if (plac > 0) {
          Vector tmp = signalAspect.get(plac);
          no = ((Integer)tmp.get(mcount + 2)).intValue();
          if (no >= 0 && no < 10) {
            this.signal = signalColor[no];
          } else {
            this.signal = String.valueOf(this.signal) + "[" + no + "]";
          } 
        } 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public void setCode(byte[] data) {
    this.code = data;
  }
  
  public byte[] getCode() {
    return this.code;
  }
  
  public int get_L_PACKET() {
    return 50;
  }
  
  public int get_Q_UPDOWN() {
    return this.Q_UPDOWN;
  }
  
  public String get_M_VERSION() {
    return this.M_VERSION;
  }
  
  public int get_Q_MEDIA() {
    return this.Q_MEDIA;
  }
  
  public int get_N_PIG() {
    return this.N_PIG;
  }
  
  public int get_N_TOTAL() {
    return this.N_TOTAL;
  }
  
  public int get_M_DUP() {
    return this.M_DUP;
  }
  
  public int get_M_MCOUNT() {
    return this.M_MCOUNT;
  }
  
  public int get_NID_C() {
    return this.NID_C;
  }
  
  public int get_NID_BG() {
    return this.NID_BG;
  }
  
  public int get_Q_LINK() {
    return this.Q_LINK;
  }
  
  public String getSignalAspect() {
    return this.signal;
  }
  
  public String toString() {
    StringBuffer tmp = new StringBuffer();
    tmp.append("== HEADER ==");
    tmp.append("\tSignal Aspect:\t" + getSignalAspect() + ".");
    tmp.append("\r\n");
    tmp.append("Q_UPDOWN:\t" + get_Q_UPDOWN());
    tmp.append("\r\n");
    tmp.append("M_VERSION:\t" + get_M_VERSION());
    tmp.append("\r\n");
    tmp.append("Q_MEDIA:\t" + get_Q_MEDIA());
    tmp.append("\r\n");
    tmp.append("N_PIG:\t" + get_N_PIG());
    tmp.append("\r\n");
    tmp.append("N_TOTAL:\t" + get_N_TOTAL());
    tmp.append("\r\n");
    tmp.append("M_DUP:\t" + get_M_DUP());
    tmp.append("\r\n");
    tmp.append("M_MCOUNT:\t" + get_M_MCOUNT());
    tmp.append("\r\n");
    tmp.append("NID_C:\t" + get_NID_C());
    tmp.append("\r\n");
    tmp.append("NID_BG:\t" + get_NID_BG());
    tmp.append("\r\n");
    tmp.append("Q_LINK:\t" + get_Q_LINK());
    tmp.append("\r\n");
    return tmp.toString();
  }
}
