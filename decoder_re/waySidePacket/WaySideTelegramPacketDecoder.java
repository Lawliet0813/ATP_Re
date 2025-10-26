package decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.Curve;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.DefaultTelegram;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.GradientProfile;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.Header;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.P44;

public class WaySideTelegramPacketDecoder {
  public static final int Linking = 5;
  
  public static final int MovementAuthority = 12;
  
  public static final int Repositioning = 16;
  
  public static final int GradientProfile = 21;
  
  public static final int StaticSpeedProfile = 27;
  
  public static final int LevelTransistionOrder = 41;
  
  public static final int P44 = 44;
  
  public static final int P44_Curve = 0;
  
  public static final int P44_Auxiliary4bitData = 1;
  
  public static final int P44_StopOverAndStationID = 2;
  
  public static final int P44_TemporaySpeedRestriction = 3;
  
  public static final int P44_LEUError = 4;
  
  public static final int P44_WheelWearCompensation = 5;
  
  public static final int TemporarySpeedRestrictionRevocation = 66;
  
  public static final int DangerForShuntingInformation = 132;
  
  public static final int InfillLocationReference = 136;
  
  public static final int DefaultTelegram = 254;
  
  public static final int EndOfInformation = 255;
  
  private byte[] data;
  
  private boolean decoding = false;
  
  private int[] record = new int[41];
  
  private int[] fpCurve = new int[1];
  
  private int[] fpBalise = new int[1];
  
  private int[] fpGradient = new int[1];
  
  private int nidBG = 0;
  
  private int signal = -1;
  
  private int[][] gradient;
  
  private int[][] curve;
  
  private StringBuffer sb;
  
  public WaySideTelegramPacketDecoder(byte[] bdata) {
    try {
      set(bdata);
      go();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public void set(byte[] bdata) {
    this.data = bdata;
    this.decoding = false;
    this.record = new int[41];
    for (int x = 0; x < 41; x++)
      this.record[x] = -1; 
  }
  
  public void go() throws Exception {
    this.nidBG = 0;
    this.signal = -1;
    this.gradient = new int[][] { { -1, -1, -1 } };
    this.curve = new int[][] { { -1, -1, -1 } };
    this.sb = new StringBuffer();
    this.decoding = true;
    byte[] tmpdata = HexCode.getBit(this.data, 0, 50);
    Header hd = new Header(this.data);
    int loc = hd.get_L_PACKET();
    this.sb.append(String.valueOf(hd.toString()) + "\r\n");
    while (this.decoding) {
      try {
        String tmp;
        GradientProfile gp;
        P44 p44;
        Curve crv;
        tmpdata = HexCode.getBit(this.data, loc, loc + 23, true);
        DefaultTelegram defaultTelegram = new DefaultTelegram(tmpdata);
        tmpdata = HexCode.getBit(this.data, loc, loc + defaultTelegram.get_L_PACKET(), true);
        loc += defaultTelegram.get_L_PACKET();
        this.nidBG = hd.get_NID_BG();
        this.signal = hd.get_M_MCOUNT();
        switch (defaultTelegram.get_NID_PACKET()) {
          case 5:
          case 12:
          case 16:
          case 27:
          case 41:
          case 66:
          case 132:
          case 136:
            continue;
          case 254:
            tmp = "== P254, Default telegram. ==";
            this.sb.append(String.valueOf(tmp) + "\r\n" + defaultTelegram.toString());
            continue;
          case 21:
            gp = new GradientProfile(tmpdata);
            this.gradient = gp.get_GRADIENT();
            this.sb.append(gp.toString());
            continue;
          case 44:
            p44 = new P44(tmpdata);
            switch (p44.get_NID_XDATA()) {
              case 0:
                crv = new Curve(tmpdata);
                this.curve = crv.get_Curve();
                this.sb.append(crv.toString());
                continue;
              case 1:
              case 2:
              case 3:
              case 4:
              case 5:
                continue;
            } 
            this.sb.append("||||||||| unexpected: P44 packet\r\n" + 
                defaultTelegram.toString() + "\r\n");
            continue;
          case 255:
            this.sb.append("== end == \r\n\r\n");
            this.decoding = false;
            continue;
        } 
        this.sb.append("||||||||| unexpected packet\r\n" + defaultTelegram.toString() + "\r\n");
      } catch (Exception ex) {
        this.sb.append("\r\n|||ERROR unexpected \r\n" + ex.getMessage() + "\r\n" + "packet Length = " + this.data.length + "\r\n");
        this.decoding = false;
      } 
    } 
  }
  
  public int[] get() {
    return this.record;
  }
  
  public String getTelegramStream() {
    return this.sb.toString();
  }
  
  public int getNID_BG() {
    return this.nidBG;
  }
  
  public int getMCOUNT() {
    return this.signal;
  }
  
  public int[][] getGradient() {
    return this.gradient;
  }
  
  public int[][] getCurve() {
    return this.curve;
  }
}
