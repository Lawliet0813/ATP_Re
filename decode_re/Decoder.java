package decode;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.FileRead;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.decode.DecodeATP;
import com.MiTAC.TRA.ATP.decode.DecodeTS;
import com.MiTAC.TRA.ATP.decode.MMIVariables;
import java.util.Vector;

public class Decoder {
  private Vector allPackets;
  
  private Vector atpPackets;
  
  private Vector errorPackets;
  
  private Vector btmPackets;
  
  private Vector logDriverData;
  
  private Vector logDriverMessage;
  
  private Vector logDynamic;
  
  private Vector logFailure;
  
  private Vector logStatus;
  
  private Vector logTS;
  
  private Vector logTrainData;
  
  private Vector tsPackets;
  
  private DecodeATP atpDecoder = new DecodeATP();
  
  private int currentPosition = 0;
  
  public Decoder(String paramString) throws Exception {
    setPath(paramString);
  }
  
  public Vector getATP() {
    return this.atpPackets;
  }
  
  public Vector getAll() {
    return this.allPackets;
  }
  
  public Vector getErr() {
    return this.errorPackets;
  }
  
  public Vector getLogDriverData() {
    return this.logDriverData;
  }
  
  public Vector getLogDriverMessage() {
    return this.logDriverMessage;
  }
  
  public Vector getLogDynamic() {
    return this.logDynamic;
  }
  
  public Vector getLogFailure() {
    return this.logFailure;
  }
  
  public Vector getLogStatus() {
    return this.logStatus;
  }
  
  public Vector getLogTS() {
    return this.logTS;
  }
  
  public Vector getLogTrainData() {
    return this.logTrainData;
  }
  
  public Vector getTS() {
    return this.tsPackets;
  }
  
  public void setPath(String paramString) throws Exception {
    this.tsPackets = new Vector();
    this.atpPackets = new Vector();
    this.errorPackets = new Vector();
    this.allPackets = new Vector();
    this.logDriverData = new Vector();
    this.logTrainData = new Vector();
    this.logTS = new Vector();
    this.logDynamic = new Vector();
    this.logStatus = new Vector();
    this.logFailure = new Vector();
    this.logDriverMessage = new Vector();
    this.btmPackets = new Vector();
    FileRead fileRead = new FileRead(paramString);
    byte[] arrayOfByte = fileRead.getCodes();
    Vector vector = new Vector();
    while (this.currentPosition <= arrayOfByte.length - 1) {
      Vector vector1;
      Vector vector2;
      Vector vector3;
      Vector vector4;
      Vector vector5;
      Vector vector6;
      Vector vector7;
      int j;
      int k;
      int i = 0;
      byte[] arrayOfByte1 = fileRead.getCodes(this.currentPosition + 1, 14);
      String str2 = DecodeTS.getTS(arrayOfByte1).get(0);
      Integer integer1 = DecodeTS.getTS(arrayOfByte1).get(1);
      Integer integer2 = DecodeTS.getTS(arrayOfByte1).get(2);
      this.logTS.add(DecodeTS.getTS(arrayOfByte1));
      char c = Character.MIN_VALUE;
      String str1 = "";
      vector = null;
      byte[] arrayOfByte2 = new byte[Byte2Number.getUnsigned(arrayOfByte[this.currentPosition + 15])];
      arrayOfByte2 = fileRead.getCodes(this.currentPosition + 15, Byte2Number.getUnsigned(arrayOfByte[this.currentPosition + 15]) + 1);
      switch (Byte2Number.getUnsigned(arrayOfByte[this.currentPosition])) {
        case 1:
        case 4:
          this.atpDecoder.setData(arrayOfByte2);
          vector1 = this.atpDecoder.getLogDynamic();
          if (vector1.size() != 0) {
            vector1.insertElementAt(str2, 0);
            this.logDynamic.add(vector1);
          } 
          vector2 = this.atpDecoder.getLogStatus();
          if (vector2.size() != 0) {
            vector2.insertElementAt(str2, 0);
            this.logStatus.add(vector2);
          } 
          vector3 = this.atpDecoder.getLogDriver();
          if (vector3.size() != 0) {
            vector3.insertElementAt(str2, 0);
            this.logDriverData.add(vector3);
          } 
          vector4 = this.atpDecoder.getLogTrainData();
          if (vector4.size() != 0) {
            vector4.insertElementAt(str2, 0);
            this.logTrainData.add(vector4);
          } 
          vector5 = this.atpDecoder.getLogFailure();
          if (vector5.size() != 0) {
            vector5.insertElementAt(str2, 0);
            this.logFailure.add(vector5);
          } 
          vector6 = this.atpDecoder.getLogDriverMessage();
          if (vector6.size() != 0) {
            vector6.insertElementAt(str2, 0);
            this.logDriverMessage.add(vector6);
          } 
          str1 = "" + this.atpDecoder.getData();
          vector = this.atpPackets;
          break;
        case 21:
          c = 'ʂ';
          str1 = "MVB_LOG_TYPE_VDX_IN_STATUS_1: no packet format to decode.";
          break;
        case 22:
          c = 'ƈ';
          str1 = "MVB_LOG_TYPE_VDX_OUT_1: no packet format to decode.";
          break;
        case 23:
          c = 'Ɖ';
          str1 = "MVB_LOG_TYPE_VDX_OUT_2: no packet format to decode.";
          break;
        case 24:
          c = 'Ɗ';
          str1 = "MVB_LOG_TYPE_VDX_OUT_3: no packet format to decode.";
          break;
        case 31:
          c = 'ࢀ';
          str1 = "MVB_LOG_TYPE_DX_IN_STATUS_1: no packet format to decode.";
          break;
        case 32:
          c = 'ࢁ';
          str1 = "MVB_LOG_TYPE_DX_STATUS_1: no packet format to decode.";
          break;
        case 33:
          c = '࢈';
          str1 = "MVB_LOG_TYPE_OUT_STATUS_1: no packet format to decode.";
          break;
        case 41:
          c = 'ʈ';
          str1 = "MVB_LOG_BTM_COMMAND_1: no packet format to decode.";
          break;
        case 42:
          c = '*';
          str1 = "MVB_LOG_BTM_STATUS_1: no packet format to decode.";
          break;
        case 43:
          c = 'ʃ';
          str1 = "MVB_LOG_BTM_TGM_1: no packet format to decode.";
          break;
        case 44:
          c = 'ʄ';
          str1 = "MVB_LOG_BTM_TGM_2: no packet format to decode.";
          break;
        case 45:
          c = 'ʅ';
          str1 = "MVB_LOG_BTM_TGM_3: no packet format to decode.";
          break;
        case 46:
          c = 'ʆ';
          str1 = "MVB_LOG_BTM_TGM_4: no packet format to decode.";
          break;
        case 47:
          c = 'ʇ';
          str1 = "MVB_LOG_BTM_TGM_5: no packet format to decode.";
          break;
        case 51:
          c = 'Ā';
          str1 = "MVB_LOG_SDU1: no packet format to decode.";
          break;
        case 52:
          c = 'Đ';
          str1 = "MVB_LOG_SDU2: no packet format to decode.";
          break;
        case 61:
          c = 'ԃ';
          str1 = "MVB_LOG_ODO_CONFIG_1: no packet format to decode.";
          break;
        case 62:
          c = 'ԅ';
          str1 = "MVB_LOG_ODO_MESSAGE_1: no packet format to decode.";
          break;
        case 63:
          c = 'Ԇ';
          str1 = "MVB_LOG_ODO_MESSAGE_2: no packet format to decode.";
          break;
        case 64:
          c = 'Ԅ';
          str1 = "MVB_LOG_ODO_BTM_STATUS_1: no packet format to decode.";
          break;
        case 71:
          c = 'd';
          str1 = "MVB_LOG_PM_LOG_TGM: no packet format to decode.";
          break;
        case 72:
          c = 'ԇ';
          str1 = "MVB_LOG_PM_APP_LOG_TGM: no packet format to decode.";
          break;
        case 2:
          str1 = "STATUS ATP: no packet format to decode.";
          break;
        case 3:
          str1 = "STATUS MMI: no packet format to decode.";
          break;
        case 91:
          str1 = "PRS INFO: no packet format to decode.";
          break;
        case 221:
          str1 = "STATUS COUNTER BOARD: no packet format to decode.";
          break;
        case 225:
          str1 = "STATUS DATA DOWNLOAD: no packet format to decode.";
          break;
        case 228:
          str1 = "STATUS GPP: no packet format to decode.";
          break;
        case 227:
          str1 = "STATUS MVB: no packet format to decode.";
          break;
        case 223:
          str1 = "STATUS PRS: no packet format to decode.";
          break;
        case 224:
          str1 = "STATUS SPEEDMETER: no packet format to decode.";
          break;
        case 222:
          str1 = "STATUS USB: no packet format to decode.";
          break;
        case 201:
          str1 = "ATP DOWN: ";
          vector7 = new Vector();
          j = MMIVariables.MMI_O_TRAIN(arrayOfByte[this.currentPosition + 16], arrayOfByte[this.currentPosition + 17], arrayOfByte[this.currentPosition + 18], arrayOfByte[this.currentPosition + 19]);
          k = MMIVariables.MMI_V_TRAIN(arrayOfByte[this.currentPosition + 22], arrayOfByte[this.currentPosition + 23]);
          vector7.add(str2);
          vector7.add(new Integer(k));
          vector7.add(new Integer(j));
          this.logTS.add(vector7);
        case 211:
          str1 = "PERIODIC_SPEED_DISTANCE";
          break;
        case 216:
          str1 = "MVB LOG TYPE BUTTON EVENT: ";
          break;
        default:
          str1 = "no handle Record Type:" + Byte2Number.getUnsigned(arrayOfByte[this.currentPosition]) + " at " + this.currentPosition + " ";
          vector = this.errorPackets;
          break;
      } 
      Vector vector8 = new Vector();
      vector8.add(new Integer(this.currentPosition));
      vector8.add(str2);
      vector8.add(integer1);
      vector8.add(integer2);
      vector8.add(new Integer(c));
      vector8.add(new Integer(Byte2Number.getUnsigned(arrayOfByte[this.currentPosition])));
      vector8.add(new Integer(Byte2Number.getUnsigned(arrayOfByte[this.currentPosition + 15])));
      vector8.add(HexCode.getHexA_String(arrayOfByte2));
      vector8.add(str1);
      if (vector != null)
        vector.add(vector8); 
      this.allPackets.add(vector8);
      this.currentPosition += 15;
      i = Byte2Number.getUnsigned(arrayOfByte[this.currentPosition]);
      this.currentPosition += i;
      this.currentPosition++;
    } 
  }
}
