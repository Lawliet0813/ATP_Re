package decoder;

import com.MiTAC.TRA.ATP.decoder.DataFeeder;
import java.io.File;

public class Decoder_tester {
  public Decoder_tester() {
    System.out.println("dd");
  }
  
  public static void main(String[] args) {
    try {
      String path = "K:\\工作資料\\行車紀錄\\問題資料\\20070124_EMC582_嘉義_行車紀錄異常\\20070119\\00000732_002645--_764094--_C582-\\";
      File f = new File(path);
      DataFeeder df = new DataFeeder(f);
      System.out.println(df.getCagetory());
    } catch (Exception exception) {}
  }
}
