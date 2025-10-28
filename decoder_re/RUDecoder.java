package decoder;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.Tools.RecordHandler;
import com.MiTAC.TRA.ATP.decoder.ATPDecoder;
import com.MiTAC.TRA.ATP.decoder.BTMDecoder;
import com.MiTAC.TRA.ATP.decoder.HeadDecoder;
import com.MiTAC.TRA.ATP.decoder.MMIVariables;
import com.MiTAC.TRA.ATP.decoder.VDXDecoder;
import java.util.Vector;

public class RUDecoder {
  private ATPDecoder atpdec = new ATPDecoder();
  
  private HeadDecoder headdec = new HeadDecoder();
  
  private MMIVariables mmivariables = new MMIVariables();
  
  private VDXDecoder vdxdecoder = new VDXDecoder();
  
  private int packetNo;
  
  private byte[] data;
  
  private byte[] header;
  
  private byte[] body;
  
  private int dataLength;
  
  private String description;
  
  private StringBuffer BTMdescrip = new StringBuffer();
  
  private BTMDecoder btmdec = new BTMDecoder();
  
  public Vector getRUDecoder(byte[] byteData) throws Exception {
    try {
      this.data = byteData;
      this.header = substringByte(0, 15);
      this.headdec.setByte(this.header);
      this.packetNo = this.headdec.getPacketNo();
      this.dataLength = Byte2Number.getUnsigned(this.data[15]);
      this.body = substringByte(16, this.dataLength);
      RecordHandler tmp = new RecordHandler();
      tmp.add(this.data);
      tmp.addVector(this.headdec.getData());
      tmp.addVector((Vector)collectionBodyData());
      return (Vector)tmp;
    } catch (Exception ex) {
      ex.printStackTrace();
      System.err.println(HexCode.getHexA_String(this.data));
      return new Vector();
    } 
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public String getBTMdescription() {
    return this.BTMdescrip.toString();
  }
  
  private byte[] substringByte(int start, int length) {
    byte[] tmp = new byte[length];
    for (int x = 0; x < length; x++)
      tmp[x] = this.data[start + x]; 
    return tmp;
  }
  
  private Object collectionBodyData() throws Exception {
    int location, speed, MVBPort = 0;
    RecordHandler rtn = new RecordHandler();
    rtn.add(new Integer(this.packetNo));
    switch (this.packetNo) {
      case 1:
      case 4:
        rtn.addVector(this.atpdec.DecodeATPData(this.body));
        return rtn;
      case 21:
        MVBPort = 21;
        rtn.add(new Boolean(this.vdxdecoder.getBrakePressure(this.body[0])));
        this.description = "MVB_LOG_TYPE_VDX_IN_STATUS_1: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 22:
        MVBPort = 392;
        this.description = "MVB_LOG_TYPE_VDX_OUT_1: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 23:
        MVBPort = 393;
        this.description = "MVB_LOG_TYPE_VDX_OUT_2: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 24:
        MVBPort = 394;
        this.description = "MVB_LOG_TYPE_VDX_OUT_3: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 31:
        MVBPort = 2176;
        this.description = "MVB_LOG_TYPE_DX_IN_STATUS_1: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 32:
        MVBPort = 2177;
        this.description = "MVB_LOG_TYPE_DX_STATUS_1: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 33:
        MVBPort = 2184;
        this.description = "MVB_LOG_TYPE_OUT_STATUS_1: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 41:
        MVBPort = 648;
        this.description = "MVB_LOG_BTM_COMMAND_1: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 42:
        MVBPort = 42;
        this.description = "MVB_LOG_BTM_STATUS_1: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 43:
        MVBPort = 643;
        this.description = "MVB_LOG_BTM_TGM_1.";
        this.btmdec.setData(this.body, 1);
        if (this.btmdec.isDataCollectFinished()) {
          rtn.add(new Integer(this.btmdec.getDetail()[0]));
          rtn.add(new Integer(this.btmdec.getDetail()[1]));
          rtn.add(this.btmdec.getGradient());
          rtn.add(this.btmdec.getCurve());
          this.BTMdescrip.append(this.btmdec.toString());
        } else {
          rtn.add(this.description);
        } 
        return rtn;
      case 44:
        MVBPort = 644;
        this.description = "MVB_LOG_BTM_TGM_2.";
        this.btmdec.setData(this.body, 2);
        if (this.btmdec.isDataCollectFinished()) {
          rtn.add(new Integer(this.btmdec.getDetail()[0]));
          rtn.add(new Integer(this.btmdec.getDetail()[1]));
          rtn.add(this.btmdec.getGradient());
          rtn.add(this.btmdec.getCurve());
          this.BTMdescrip.append(this.btmdec.toString());
        } else {
          rtn.add(this.description);
        } 
        return rtn;
      case 45:
        MVBPort = 645;
        this.description = "MVB_LOG_BTM_TGM_3.";
        this.btmdec.setData(this.body, 3);
        if (this.btmdec.isDataCollectFinished()) {
          rtn.add(new Integer(this.btmdec.getDetail()[0]));
          rtn.add(new Integer(this.btmdec.getDetail()[1]));
          rtn.add(this.btmdec.getGradient());
          rtn.add(this.btmdec.getCurve());
          this.BTMdescrip.append(this.btmdec.toString());
        } else {
          rtn.add(this.description);
        } 
        return rtn;
      case 46:
        MVBPort = 646;
        this.description = "MVB_LOG_BTM_TGM_4.";
        this.btmdec.setData(this.body, 4);
        if (this.btmdec.isDataCollectFinished()) {
          rtn.add(new Integer(this.btmdec.getDetail()[0]));
          rtn.add(new Integer(this.btmdec.getDetail()[1]));
          rtn.add(this.btmdec.getGradient());
          rtn.add(this.btmdec.getCurve());
          this.BTMdescrip.append(this.btmdec.toString());
        } else {
          rtn.add(this.description);
        } 
        return rtn;
      case 47:
        MVBPort = 647;
        this.description = "MVB_LOG_BTM_TGM_5.";
        this.btmdec.setData(this.body, 5);
        if (this.btmdec.isDataCollectFinished()) {
          rtn.add(new Integer(this.btmdec.getDetail()[0]));
          rtn.add(new Integer(this.btmdec.getDetail()[1]));
          rtn.add(this.btmdec.getGradient());
          rtn.add(this.btmdec.getCurve());
          this.BTMdescrip.append(this.btmdec.toString());
        } else {
          rtn.add(this.description);
        } 
        return rtn;
      case 51:
        MVBPort = 256;
        this.description = "MVB_LOG_SDU1: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 52:
        MVBPort = 272;
        this.description = "MVB_LOG_SDU2: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 61:
        MVBPort = 1283;
        this.description = "MVB_LOG_ODO_CONFIG_1: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 62:
        MVBPort = 1285;
        this.description = "MVB_LOG_ODO_MESSAGE_1: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 63:
        MVBPort = 1286;
        this.description = "MVB_LOG_ODO_MESSAGE_2: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 64:
        MVBPort = 1284;
        this.description = "MVB_LOG_ODO_BTM_STATUS_1: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 71:
        MVBPort = 100;
        this.description = "MVB_LOG_PM_LOG_TGM: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 72:
        MVBPort = 1287;
        this.description = "MVB_LOG_PM_APP_LOG_TGM: no packet format to decode.";
        rtn.add(this.description);
        return rtn;
      case 2:
        this.description = "STATUS ATP.";
        rtn.add(new Integer(Byte2Number.getUnsigned(this.body[0])));
        rtn.add(this.description);
        return rtn;
      case 3:
        this.description = "STATUS MMI.";
        rtn.add(new Integer(Byte2Number.getUnsigned(this.body[0])));
        rtn.add(this.description);
        return rtn;
      case 91:
        this.description = "PRS INFO.";
        rtn.add(this.description);
        return rtn;
      case 221:
        this.description = "STATUS COUNTER BOARD.";
        rtn.add(new Integer(Byte2Number.getUnsigned(this.body[0])));
        rtn.add(this.description);
        return rtn;
      case 225:
        this.description = "STATUS DATA DOWNLOAD.";
        rtn.add(new Integer(Byte2Number.getUnsigned(this.body[0])));
        rtn.add(this.description);
        return rtn;
      case 228:
        this.description = "STATUS GPP.";
        rtn.add(new Integer(Byte2Number.getUnsigned(this.body[0])));
        rtn.add(this.description);
        return rtn;
      case 227:
        this.description = "STATUS MVB.";
        rtn.add(new Integer(Byte2Number.getUnsigned(this.body[0])));
        rtn.add(this.description);
        return rtn;
      case 223:
        this.description = "STATUS PRS.";
        rtn.add(new Integer(Byte2Number.getUnsigned(this.body[0])));
        rtn.add(this.description);
        return rtn;
      case 224:
        this.description = "STATUS SPEEDMETER.";
        rtn.add(new Integer(Byte2Number.getUnsigned(this.body[0])));
        rtn.add(this.description);
        return rtn;
      case 222:
        this.description = "STATUS USB.";
        rtn.add(new Integer(Byte2Number.getUnsigned(this.body[0])));
        rtn.add(this.description);
        return rtn;
      case 201:
        this.description = "ATP DOWN: ";
        location = this.mmivariables.MMI_O_TRAIN(this.body[0], this.body[1], this.body[2], 
            this.body[3]);
        // Speed is 4 bytes (body[4]-body[7]) according to ATPRU-LOGF-001 v1.8 spec
        speed = Byte2Number.getSigned(this.body[4], this.body[5], this.body[6], this.body[7]);
        rtn.add(new Integer(speed));
        rtn.add(new Integer(location));
        rtn.add(this.description);
        return rtn;
      case 211:
        this.description = "PERIODIC_SPEED_DISTANCE";
        rtn.add("1");
        rtn.add(this.description);
        return rtn;
      case 216:
        this.description = "MVB LOG TYPE BUTTON EVENT: ";
        rtn.add(new Integer(Byte2Number.getUnsigned(this.body[0])));
        rtn.add(this.description);
        return rtn;
    } 
    this.description = "no handle Record Type:" + this.packetNo;
    rtn.add(this.description);
    return rtn;
  }
}
