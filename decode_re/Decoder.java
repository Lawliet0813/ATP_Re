package decode;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.FileRead;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.decode.DecodeATP;
import com.MiTAC.TRA.ATP.decode.DecodeTS;
import com.MiTAC.TRA.ATP.decode.MMIVariables;
import java.util.Vector;

public class Decoder {
  private Vector _$25801;
  
  private Vector _$25799;
  
  private Vector _$25800;
  
  private Vector _$25802;
  
  private Vector _$22268;
  
  private Vector _$22275;
  
  private Vector _$22272;
  
  private Vector _$22274;
  
  private Vector _$22273;
  
  private Vector _$22271;
  
  private Vector _$22269;
  
  private Vector _$25798;
  
  private DecodeATP _$25797 = new DecodeATP();
  
  private int _$4027 = 0;
  
  public Decoder(String paramString) throws Exception {
    setPath(paramString);
  }
  
  public Vector getATP() {
    return this._$25799;
  }
  
  public Vector getAll() {
    return this._$25801;
  }
  
  public Vector getErr() {
    return this._$25800;
  }
  
  public Vector getLogDriverData() {
    return this._$22268;
  }
  
  public Vector getLogDriverMessage() {
    return this._$22275;
  }
  
  public Vector getLogDynamic() {
    return this._$22272;
  }
  
  public Vector getLogFailure() {
    return this._$22274;
  }
  
  public Vector getLogStatus() {
    return this._$22273;
  }
  
  public Vector getLogTS() {
    return this._$22271;
  }
  
  public Vector getLogTrainData() {
    return this._$22269;
  }
  
  public Vector getTS() {
    return this._$25798;
  }
  
  public void setPath(String paramString) throws Exception {
    this._$25798 = new Vector();
    this._$25799 = new Vector();
    this._$25800 = new Vector();
    this._$25801 = new Vector();
    this._$22268 = new Vector();
    this._$22269 = new Vector();
    this._$22271 = new Vector();
    this._$22272 = new Vector();
    this._$22273 = new Vector();
    this._$22274 = new Vector();
    this._$22275 = new Vector();
    this._$25802 = new Vector();
    FileRead fileRead = new FileRead(paramString);
    byte[] arrayOfByte = fileRead.getCodes();
    Vector vector = new Vector();
    while (this._$4027 <= arrayOfByte.length - 1) {
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
      byte[] arrayOfByte1 = fileRead.getCodes(this._$4027 + 1, 14);
      String str2 = DecodeTS.getTS(arrayOfByte1).get(0);
      Integer integer1 = DecodeTS.getTS(arrayOfByte1).get(1);
      Integer integer2 = DecodeTS.getTS(arrayOfByte1).get(2);
      this._$22271.add(DecodeTS.getTS(arrayOfByte1));
      char c = Character.MIN_VALUE;
      String str1 = "";
      vector = null;
      byte[] arrayOfByte2 = new byte[Byte2Number.getUnsigned(arrayOfByte[this._$4027 + 15])];
      arrayOfByte2 = fileRead.getCodes(this._$4027 + 15, Byte2Number.getUnsigned(arrayOfByte[this._$4027 + 15]) + 1);
      switch (Byte2Number.getUnsigned(arrayOfByte[this._$4027])) {
        case 1:
        case 4:
          this._$25797.setData(arrayOfByte2);
          vector1 = this._$25797.getLogDynamic();
          if (vector1.size() != 0) {
            vector1.insertElementAt(str2, 0);
            this._$22272.add(vector1);
          } 
          vector2 = this._$25797.getLogStatus();
          if (vector2.size() != 0) {
            vector2.insertElementAt(str2, 0);
            this._$22273.add(vector2);
          } 
          vector3 = this._$25797.getLogDriver();
          if (vector3.size() != 0) {
            vector3.insertElementAt(str2, 0);
            this._$22268.add(vector3);
          } 
          vector4 = this._$25797.getLogTrainData();
          if (vector4.size() != 0) {
            vector4.insertElementAt(str2, 0);
            this._$22269.add(vector4);
          } 
          vector5 = this._$25797.getLogFailure();
          if (vector5.size() != 0) {
            vector5.insertElementAt(str2, 0);
            this._$22274.add(vector5);
          } 
          vector6 = this._$25797.getLogDriverMessage();
          if (vector6.size() != 0) {
            vector6.insertElementAt(str2, 0);
            this._$22275.add(vector6);
          } 
          str1 = "" + this._$25797.getData();
          vector = this._$25799;
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
          j = MMIVariables.MMI_O_TRAIN(arrayOfByte[this._$4027 + 16], arrayOfByte[this._$4027 + 17], arrayOfByte[this._$4027 + 18], arrayOfByte[this._$4027 + 19]);
          k = MMIVariables.MMI_V_TRAIN(arrayOfByte[this._$4027 + 22], arrayOfByte[this._$4027 + 23]);
          vector7.add(str2);
          vector7.add(new Integer(k));
          vector7.add(new Integer(j));
          this._$22271.add(vector7);
        case 211:
          str1 = "PERIODIC_SPEED_DISTANCE";
          break;
        case 216:
          str1 = "MVB LOG TYPE BUTTON EVENT: ";
          break;
        default:
          str1 = "no handle Record Type:" + Byte2Number.getUnsigned(arrayOfByte[this._$4027]) + " at " + this._$4027 + " ";
          vector = this._$25800;
          break;
      } 
      Vector vector8 = new Vector();
      vector8.add(new Integer(this._$4027));
      vector8.add(str2);
      vector8.add(integer1);
      vector8.add(integer2);
      vector8.add(new Integer(c));
      vector8.add(new Integer(Byte2Number.getUnsigned(arrayOfByte[this._$4027])));
      vector8.add(new Integer(Byte2Number.getUnsigned(arrayOfByte[this._$4027 + 15])));
      vector8.add(HexCode.getHexA_String(arrayOfByte2));
      vector8.add(str1);
      if (vector != null)
        vector.add(vector8); 
      this._$25801.add(vector8);
      this._$4027 += 15;
      i = Byte2Number.getUnsigned(arrayOfByte[this._$4027]);
      this._$4027 += i;
      this._$4027++;
    } 
  }
}
