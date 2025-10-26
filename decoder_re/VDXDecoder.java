package decoder;

import com.MiTAC.TRA.ATP.Tools.HexCode;

public class VDXDecoder {
  public boolean getBrakePressure(byte a) throws Exception {
    if (HexCode.getBit(a, 2, 3) == 0 && HexCode.getBit(a, 3, 4) == 1)
      return false; 
    return true;
  }
}
