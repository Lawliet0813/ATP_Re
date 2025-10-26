package decoder;

import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.FileRead;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import com.MiTAC.TRA.ATP.Tools.RecordHandler;
import com.MiTAC.TRA.ATP.Tools.WriteToFile;
import com.MiTAC.TRA.ATP.core.ATPMissionGeneral;
import com.MiTAC.TRA.ATP.decoder.RUDecoder;
import java.io.File;
import java.util.Date;
import java.util.Vector;

public class DataFeeder {
  WriteToFile wtf = new WriteToFile("c:\\debug.txt");
  
  WriteToFile statusATP = new WriteToFile("C:\\ATPMW\\ATP_STATUS.txt");
  
  private boolean atpdown = false;
  
  private int msgLength = 0;
  
  private int loc = 0;
  
  private Vector collector = new Vector();
  
  private int length;
  
  private int current = 0;
  
  private boolean done;
  
  private boolean cancel;
  
  private File root;
  
  private File file;
  
  private String WSNo;
  
  private String TRNo;
  
  private String VID;
  
  private String Driver;
  
  private Date RunningDate;
  
  private Vector gradient = new Vector();
  
  private Vector curve = new Vector();
  
  private Vector btm = new Vector();
  
  private Vector driverData = new Vector();
  
  private Vector trainData = new Vector();
  
  private RecordHandler category = new RecordHandler();
  
  private Vector dynamic = new Vector();
  
  private Vector status = new Vector();
  
  private Vector failure = new Vector();
  
  private Vector driverMessage = new Vector();
  
  private Vector driverMessage_Ack = new Vector();
  
  private Vector btnEvent = new Vector();
  
  private Vector timeStamp = new Vector();
  
  private Vector vdx = new Vector();
  
  private ATPMissionGeneral thisMission;
  
  private RUDecoder rudecoder = new RUDecoder();
  
  private int ebTimes = 0;
  
  private int sbTimes = 0;
  
  private int cabinFailureTimes = 0;
  
  private int waysideFailureTimes = 0;
  
  public DataFeeder(File mission) throws Exception {
    this.thisMission = new ATPMissionGeneral(mission);
    this.root = this.thisMission.getFile();
    go();
    this.thisMission.setCategory(getCagetory());
    this.thisMission.setVDX(getVDX());
    this.thisMission.setDynamic(getDynamic());
    this.thisMission.setFailure(getFailure());
    this.thisMission.setStatus(getStatus());
    this.thisMission.setTimeStamp(getTS());
    this.thisMission.setDriverMessage(getDriverMessage());
    this.thisMission.setDriverMessage_Ack(getDriverMessage_Ack());
    this.thisMission.setBtnEvent(getBtnEvent());
    this.thisMission.setBTM(getBTM());
    this.thisMission.setTrainData(getTrainData());
    this.thisMission.setGradient(getGradient());
    this.thisMission.setCurve(getCurve());
    this.wtf.close();
    this.statusATP.close();
  }
  
  public void go() {
    try {
      this.current = 0;
      if (this.root.isDirectory()) {
        File[] fList = this.root.listFiles();
        this.length = fList.length;
        for (int i = 0; i < fList.length; i++) {
          if (fList[i].getName().endsWith(".MMI") || 
            fList[i].getName().endsWith(".RU")) {
            this.file = fList[i];
            decode(this.file.getPath());
            this.current++;
          } 
        } 
      } else if (this.root.getName().endsWith(".MMI") || 
        this.root.getName().endsWith(".RU")) {
        this.length = 1;
        this.file = this.root;
        decode(this.file.getPath());
      } else {
        this.length = 0;
        throw new Exception(
            "Not a ATP file, please select a ATP Log file.");
      } 
      separateData();
      collectTrainCategory();
      this.done = true;
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public boolean isDone() {
    return this.done;
  }
  
  public boolean isCanceled() {
    return this.cancel;
  }
  
  public int length() {
    return this.length;
  }
  
  public int current() {
    return this.current;
  }
  
  private void collectTrainCategory() throws Exception {
    RecordHandler tmp;
    Date codeRunningDate;
    String codeWSNo, codeTRNo, codeDriver;
    Vector tmptrainData = PathHandler.getDecodePath(this.file.getParentFile().getAbsolutePath());
    if (tmptrainData.size() != 5)
      throw new ArrayIndexOutOfBoundsException("wrong length"); 
    Date pathRunningDate = tmptrainData.get(0);
    String pathWSNo = (String)tmptrainData.get(1);
    String pathTRNo = (String)tmptrainData.get(2);
    String pathDriver = (String)tmptrainData.get(3);
    String pathVID = (String)tmptrainData.get(4);
    if (getDriverData().size() > 0) {
      tmp = getDriverData().get(0);
      codeWSNo = (String)tmp.get(8);
      codeTRNo = (String)tmp.get(7);
      codeDriver = (String)tmp.get(6);
      codeRunningDate = (Date)tmp.get(1);
    } else {
      codeWSNo = "wsno";
      codeTRNo = "trno";
      codeDriver = "driverId";
      codeRunningDate = pathRunningDate;
    } 
    if (getTrainData().size() > 0) {
      RecordHandler recordHandler = getTrainData().get(0);
      tmp = new RecordHandler();
      tmp.add(recordHandler.get(0));
      tmp.add(recordHandler.get(1));
      tmp.add(recordHandler.get(2));
      tmp.add(recordHandler.get(3));
      tmp.add(recordHandler.get(4));
      tmp.add(recordHandler.get(5));
      tmp.add(recordHandler.get(21));
      tmp.add(recordHandler.get(20));
      tmp.add(recordHandler.get(19));
      tmp.add(recordHandler.get(18));
      tmp.add(recordHandler.get(17));
      tmp.add(recordHandler.get(16));
      tmp.add(recordHandler.get(15));
      tmp.add(recordHandler.get(14));
      tmp.add(recordHandler.get(13));
      tmp.add(recordHandler.get(12));
      tmp.add(recordHandler.get(11));
      tmp.add(recordHandler.get(10));
      tmp.add(recordHandler.get(9));
      tmp.add(recordHandler.get(8));
      tmp.add(recordHandler.get(7));
      tmp.add(recordHandler.get(6));
    } else {
      tmp = new RecordHandler();
      tmp.add("miss traindata");
      tmp.add("1980/12/17 01:01:01");
      tmp.add(new Integer(0));
      tmp.add(new Integer(10000000));
      tmp.add(new Integer(4));
      tmp.add(new Integer(6));
      tmp.add("1");
      tmp.add("tmp");
      tmp.add(new Integer(0));
      tmp.add(new Integer(0));
      tmp.add(new Double(0.0D));
      tmp.add(new Integer(0));
      tmp.add(new Integer(0));
      tmp.add(new Integer(0));
      tmp.add(new Integer(0));
      tmp.add(new Integer(0));
      tmp.add("");
      tmp.add(new Integer(0));
      tmp.add(new Integer(0));
      tmp.add(new Integer(0));
      tmp.add(new Integer(0));
      tmp.add(new Integer(0));
    } 
    Object object = tmp.get(5);
    if (pathWSNo.equals(codeWSNo) && 
      pathTRNo.equals(codeTRNo) && 
      pathDriver.equals(codeDriver) && 
      pathVID.equals(object) && 
      codeRunningDate.equals(pathRunningDate)) {
      this.WSNo = codeWSNo;
      this.TRNo = codeTRNo;
      this.Driver = codeDriver;
      this.VID = (String)object;
      this.RunningDate = pathRunningDate;
    } else {
      this.WSNo = pathWSNo;
      this.TRNo = pathTRNo;
      this.Driver = pathDriver;
      this.VID = String.valueOf(pathVID) + "_Er";
      this.RunningDate = pathRunningDate;
    } 
    this.category = (RecordHandler)tmp.clone();
    this.category.remove(5);
    this.category.remove(4);
    this.category.remove(3);
    this.category.remove(2);
    this.category.remove(0);
    this.category.insertElementAt(this.WSNo, 1);
    this.category.insertElementAt(this.TRNo, 2);
    this.category.insertElementAt(this.Driver, 3);
    this.category.add(new Integer(getEBTimes()));
    this.category.add(new Integer(getSBTimes()));
    this.category.add(new Integer(getCabinFailureTimes()));
    this.category.add(new Integer(getWaysideFailureTimes()));
    this.category.add(new Integer(getIsolation() ? 1 : 0));
  }
  
  private void decode(String filepath) {
    try {
      this.msgLength = 0;
      this.loc = 0;
      FileRead fr = new FileRead(filepath);
      byte[] originalData = fr.getCodes();
      this.wtf.writeLine("\n");
      this.wtf
        .writeLine("===File======================================================================");
      this.wtf.writeLine(filepath);
      this.wtf
        .writeLine("-----------------------------------------------------------------------------");
      while (this.loc < originalData.length) {
        this.msgLength = 16 + 
          Byte2Number.getUnsigned(originalData[this.loc + 15]);
        if (this.loc + this.msgLength > originalData.length)
          this.msgLength = originalData.length - this.loc; 
        byte[] data = fr.getCodes(this.loc, this.msgLength);
        this.collector.add(this.rudecoder.getRUDecoder(data));
        this.wtf.writeLine("loc:" + this.loc + ".\tlength:" + this.msgLength);
        this.wtf.writeLine("Oirg data: " + HexCode.getHexA_String(data));
        this.loc += this.msgLength;
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private void separateData() throws Exception {
    for (int i = 0; i < this.collector.size(); i++) {
      this.atpdown = false;
      Vector tmp = (Vector)((Vector)this.collector.get(i)).clone();
      if (tmp.size() != 0)
        if (this.thisMission.getMissionDate().getDate() == ((Date)tmp.get(1)).getDate()) {
          int atpType, mode, type = ((Integer)tmp.get(4)).intValue();
          switch (type) {
            case 1:
            case 4:
              separateATPdata(tmp);
              break;
            case 2:
              this.statusATP.writeLine("[STATUS ATP] " + tmp);
              atpType = ((Integer)tmp.get(5)).intValue();
              tmp.remove(6);
              tmp.remove(5);
              tmp.remove(4);
              tmp.remove(3);
              tmp.remove(2);
              tmp.remove(0);
              tmp.insertElementAt(this.thisMission.getMissionDate(), 0);
              tmp.insertElementAt(this.thisMission.getWorkShift(), 1);
              tmp.insertElementAt(this.thisMission.getTrainRunning(), 2);
              tmp.insertElementAt(this.thisMission.getDriver(), 3);
              tmp.insertElementAt(this.thisMission.getVehicle(), 4);
              mode = -2;
              if (atpType == 15) {
                mode = -2;
              } else if (atpType == 16) {
                mode = -1;
              } 
              tmp.add(new Integer(0));
              tmp.add(new Integer(mode));
              tmp.add(new Integer(0));
              tmp.add(new Integer(0));
              tmp.add(new Integer(0));
              tmp.add(new Integer(0));
              this.status.add(tmp);
              break;
            case 3:
              break;
            case 21:
              tmp.remove(6);
              tmp.remove(4);
              tmp.remove(3);
              tmp.remove(2);
              tmp.remove(0);
              tmp.insertElementAt(this.thisMission.getMissionDate(), 0);
              tmp.insertElementAt(this.thisMission.getWorkShift(), 1);
              tmp.insertElementAt(this.thisMission.getTrainRunning(), 2);
              tmp.insertElementAt(this.thisMission.getDriver(), 3);
              tmp.insertElementAt(this.thisMission.getVehicle(), 4);
              this.vdx.add(tmp);
              break;
            case 22:
            case 23:
            case 24:
            case 31:
            case 32:
            case 33:
            case 41:
            case 42:
              break;
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
              if (tmp.get(5).getClass() != "".getClass()) {
                Vector curve = (Vector)tmp.get(8);
                Vector gradient = (Vector)tmp.get(7);
                int nid_bg = ((Integer)tmp.get(5)).intValue();
                tmp.remove(8);
                tmp.remove(7);
                tmp.remove(4);
                Integer x = (Integer)tmp.get(3);
                tmp.remove(3);
                tmp.remove(2);
                tmp.remove(0);
                tmp.insertElementAt(this.thisMission.getMissionDate(), 0);
                tmp.insertElementAt(this.thisMission.getWorkShift(), 1);
                tmp.insertElementAt(this.thisMission.getTrainRunning(), 2);
                tmp.insertElementAt(this.thisMission.getDriver(), 3);
                tmp.insertElementAt(this.thisMission.getVehicle(), 4);
                tmp.add(x);
                this.btm.add(tmp);
                gradient.insertElementAt(this.thisMission.getMissionDate(), 0);
                gradient.insertElementAt(this.thisMission.getWorkShift(), 1);
                gradient.insertElementAt(this.thisMission.getTrainRunning(), 2);
                gradient.insertElementAt(this.thisMission.getDriver(), 3);
                gradient.insertElementAt(this.thisMission.getVehicle(), 4);
                gradient.insertElementAt(new Integer(nid_bg), 5);
                if ((new Integer(gradient.get(6).toString())).intValue() >= 0)
                  this.gradient.add(gradient); 
                curve.insertElementAt(this.thisMission.getMissionDate(), 0);
                curve.insertElementAt(this.thisMission.getWorkShift(), 1);
                curve.insertElementAt(this.thisMission.getTrainRunning(), 2);
                curve.insertElementAt(this.thisMission.getDriver(), 3);
                curve.insertElementAt(this.thisMission.getVehicle(), 4);
                curve.insertElementAt(new Integer(nid_bg), 5);
                if ((new Integer(curve.get(6).toString())).intValue() >= 0)
                  this.curve.add(curve); 
              } 
              break;
            case 51:
            case 52:
            case 61:
            case 62:
            case 63:
            case 64:
            case 71:
            case 72:
            case 91:
              break;
            case 201:
              this.atpdown = true;
              eventRising((Vector)tmp.clone());
              tmp.remove(7);
              tmp.remove(6);
              tmp.remove(5);
              tmp.remove(4);
              tmp.remove(3);
              tmp.remove(2);
              tmp.remove(0);
              tmp.insertElementAt(this.thisMission.getMissionDate(), 0);
              tmp.insertElementAt(this.thisMission.getWorkShift(), 1);
              tmp.insertElementAt(this.thisMission.getTrainRunning(), 2);
              tmp.insertElementAt(this.thisMission.getDriver(), 3);
              tmp.insertElementAt(this.thisMission.getVehicle(), 4);
              tmp.add(new Integer(0));
              tmp.add(new Integer(-1));
              tmp.add(new Integer(0));
              tmp.add(new Integer(0));
              tmp.add(new Integer(0));
              tmp.add(new Integer(0));
              this.status.add(tmp);
              if (this.dynamic.size() > 0) {
                Vector tmpdyn = (Vector)((Vector)this.dynamic.lastElement()).clone();
                tmpdyn.setElementAt(new Integer(-1), 9);
                tmpdyn.setElementAt(new Integer(-1), 10);
                tmpdyn.setElementAt(new Integer(-1), 11);
                tmpdyn.setElementAt(new Integer(0), 12);
                tmpdyn.setElementAt(new Integer(0), 13);
                tmpdyn.setElementAt(new Integer(0), 14);
                this.dynamic.add(tmpdyn);
              } 
              break;
            case 211:
              this.statusATP.writeLine("[PERIODIC SPEED DISTANCE] " + tmp);
              break;
            case 216:
              tmp.remove(5);
              tmp.remove(4);
              tmp.remove(3);
              tmp.remove(2);
              tmp.remove(0);
              tmp.insertElementAt(this.thisMission.getMissionDate(), 0);
              tmp.insertElementAt(this.thisMission.getWorkShift(), 1);
              tmp.insertElementAt(this.thisMission.getTrainRunning(), 2);
              tmp.insertElementAt(this.thisMission.getDriver(), 3);
              tmp.insertElementAt(this.thisMission.getVehicle(), 4);
              this.btnEvent.add(tmp);
              break;
            case 221:
            case 222:
            case 223:
            case 224:
            case 225:
            case 227:
            case 228:
              break;
            default:
              System.err.println("Err :  (" + type + ")unknow package.");
              break;
          } 
          if (type != 91)
            eventRising(this.collector.get(i)); 
        }  
    } 
  }
  
  private void eventRising(Vector arg0, boolean arg1) {
    Vector tmpTS = new Vector();
    tmpTS.add(this.thisMission.getMissionDate());
    tmpTS.add(this.thisMission.getWorkShift());
    tmpTS.add(this.thisMission.getTrainRunning());
    tmpTS.add(this.thisMission.getDriver());
    tmpTS.add(this.thisMission.getVehicle());
    if (this.atpdown) {
      tmpTS.add(arg0.get(1));
      tmpTS.add(arg0.get(5));
      tmpTS.add(arg0.get(6));
    } else {
      tmpTS.add(arg0.get(1));
      tmpTS.add(arg0.get(2));
      tmpTS.add(arg0.get(3));
    } 
    if (this.timeStamp.size() > 0) {
      Vector compare = this.timeStamp.get(this.timeStamp.size() - 1);
      if (((Date)tmpTS.get(5)).equals(compare.get(5))) {
        if (((Integer)tmpTS.get(7)).intValue() > ((Integer)compare.get(7)).intValue()) {
          this.timeStamp.remove(this.timeStamp.size() - 1);
          this.timeStamp.add(tmpTS);
        } 
      } else {
        this.timeStamp.add(tmpTS);
      } 
    } else {
      this.timeStamp.add(tmpTS);
    } 
  }
  
  private void eventRising(Vector arg0) {
    Vector tmpTS = new Vector();
    tmpTS.add(this.thisMission.getMissionDate());
    tmpTS.add(this.thisMission.getWorkShift());
    tmpTS.add(this.thisMission.getTrainRunning());
    tmpTS.add(this.thisMission.getDriver());
    tmpTS.add(this.thisMission.getVehicle());
    if (this.atpdown) {
      tmpTS.add(arg0.get(1));
      tmpTS.add(arg0.get(5));
      tmpTS.add(arg0.get(6));
    } else {
      tmpTS.add(arg0.get(1));
      tmpTS.add(arg0.get(2));
      tmpTS.add(arg0.get(3));
    } 
    if (this.timeStamp.size() > 0) {
      Vector compare = this.timeStamp.get(this.timeStamp.size() - 1);
      if (((Date)tmpTS.get(5)).equals(compare.get(5))) {
        if (((Integer)tmpTS.get(7)).intValue() > ((Integer)compare.get(7)).intValue()) {
          this.timeStamp.remove(this.timeStamp.size() - 1);
          this.timeStamp.add(tmpTS);
        } 
      } else if ((new Integer(compare.get(6).toString())).intValue() == 0 && (new Integer(tmpTS.get(6).toString())).intValue() != 0) {
        Vector tmpTS0 = new Vector();
        tmpTS0.add(tmpTS.get(0));
        tmpTS0.add(tmpTS.get(1));
        tmpTS0.add(tmpTS.get(2));
        tmpTS0.add(tmpTS.get(3));
        tmpTS0.add(tmpTS.get(4));
        tmpTS0.add(new Date(((Date)tmpTS.get(5)).getTime() - 1000L));
        tmpTS0.add(compare.get(6));
        tmpTS0.add(compare.get(7));
        this.timeStamp.add(tmpTS0);
        this.timeStamp.add(tmpTS);
      } else if ((new Integer(compare.get(6).toString())).intValue() != 0 && (new Integer(tmpTS.get(6).toString())).intValue() == 0) {
        this.timeStamp.add(tmpTS);
      } else if (((Date)tmpTS.get(5)).getTime() - ((Date)compare.get(5)).getTime() >= 3000L) {
        if (!compare.get(6).equals(tmpTS.get(6)) || !compare.get(7).equals(tmpTS.get(7)))
          this.timeStamp.add(tmpTS); 
      } 
    } else {
      this.timeStamp.add(tmpTS);
    } 
  }
  
  private void separateATPdata(Vector arg0) {
    Vector tmp = (Vector)arg0.clone();
    switch (((Integer)arg0.get(5)).intValue()) {
      case 1:
        tmp.remove(18);
        tmp.remove(14);
        tmp.remove(11);
        tmp.remove(6);
        tmp.remove(5);
        tmp.remove(4);
        tmp.remove(3);
        tmp.remove(2);
        tmp.remove(0);
        tmp.insertElementAt(this.thisMission.getMissionDate(), 0);
        tmp.insertElementAt(this.thisMission.getWorkShift(), 1);
        tmp.insertElementAt(this.thisMission.getTrainRunning(), 2);
        tmp.insertElementAt(this.thisMission.getDriver(), 3);
        tmp.insertElementAt(this.thisMission.getVehicle(), 4);
        this.dynamic.add(tmp);
        break;
      case 2:
        eventRising((Vector)tmp.clone(), true);
        tmp.remove(12);
        tmp.remove(8);
        tmp.remove(5);
        tmp.remove(4);
        tmp.remove(3);
        tmp.remove(2);
        tmp.remove(0);
        tmp.insertElementAt(this.thisMission.getMissionDate(), 0);
        tmp.insertElementAt(this.thisMission.getWorkShift(), 1);
        tmp.insertElementAt(this.thisMission.getTrainRunning(), 2);
        tmp.insertElementAt(this.thisMission.getDriver(), 3);
        tmp.insertElementAt(this.thisMission.getVehicle(), 4);
        this.status.add(tmp);
        caculateEBSBTimes(tmp);
        break;
      case 8:
        tmp.remove(5);
        tmp.remove(4);
        tmp.remove(3);
        tmp.remove(2);
        tmp.remove(0);
        tmp.insertElementAt(this.thisMission.getMissionDate(), 0);
        tmp.insertElementAt(this.thisMission.getWorkShift(), 1);
        tmp.insertElementAt(this.thisMission.getTrainRunning(), 2);
        tmp.insertElementAt(this.thisMission.getDriver(), 3);
        tmp.insertElementAt(this.thisMission.getVehicle(), 4);
        this.driverMessage.add(tmp);
        break;
      case 9:
        eventRising((Vector)tmp.clone(), true);
        tmp.remove(5);
        tmp.remove(4);
        tmp.remove(3);
        tmp.remove(2);
        tmp.remove(0);
        tmp.insertElementAt(this.thisMission.getMissionDate(), 0);
        tmp.insertElementAt(this.thisMission.getWorkShift(), 1);
        tmp.insertElementAt(this.thisMission.getTrainRunning(), 2);
        tmp.insertElementAt(this.thisMission.getDriver(), 3);
        tmp.insertElementAt(this.thisMission.getVehicle(), 4);
        this.failure.add(tmp);
        caculateFailure(tmp);
        break;
      case 10:
        tmp.remove(5);
        tmp.remove(4);
        tmp.remove(3);
        tmp.remove(2);
        tmp.remove(0);
        tmp.insertElementAt(this.thisMission.getMissionDate(), 0);
        tmp.insertElementAt(this.thisMission.getWorkShift(), 1);
        tmp.insertElementAt(this.thisMission.getTrainRunning(), 2);
        tmp.insertElementAt(this.thisMission.getDriver(), 3);
        tmp.insertElementAt(this.thisMission.getVehicle(), 4);
        this.trainData.add(tmp);
        break;
      case 14:
        this.driverData.add(arg0);
        break;
      case 110:
        tmp.remove(5);
        tmp.remove(4);
        tmp.remove(3);
        tmp.remove(2);
        tmp.remove(0);
        tmp.insertElementAt(this.thisMission.getMissionDate(), 0);
        tmp.insertElementAt(this.thisMission.getWorkShift(), 1);
        tmp.insertElementAt(this.thisMission.getTrainRunning(), 2);
        tmp.insertElementAt(this.thisMission.getDriver(), 3);
        tmp.insertElementAt(this.thisMission.getVehicle(), 4);
        this.trainData.add(tmp);
        break;
      case 111:
        tmp.remove(5);
        tmp.remove(4);
        tmp.remove(3);
        tmp.remove(2);
        tmp.remove(0);
        tmp.insertElementAt(this.thisMission.getMissionDate(), 0);
        tmp.insertElementAt(this.thisMission.getWorkShift(), 1);
        tmp.insertElementAt(this.thisMission.getTrainRunning(), 2);
        tmp.insertElementAt(this.thisMission.getDriver(), 3);
        tmp.insertElementAt(this.thisMission.getVehicle(), 4);
        this.driverMessage_Ack.add(tmp);
        break;
    } 
  }
  
  private void caculateEBSBTimes(Vector data) {
    if (((Integer)data.get(8)).intValue() != 0)
      this.ebTimes++; 
    if (((Integer)data.get(9)).intValue() != 0)
      this.sbTimes++; 
  }
  
  private void caculateFailure(Vector data) {
    this.thisMission.getFailure();
    if (((Integer)data.get(6)).intValue() == 8 || (
      (Integer)data.get(6)).intValue() == 15 || (
      (Integer)data.get(6)).intValue() == 16 || (
      (Integer)data.get(6)).intValue() == 21 || (
      (Integer)data.get(6)).intValue() == 34) {
      this.waysideFailureTimes++;
    } else {
      this.cabinFailureTimes++;
    } 
  }
  
  public boolean getIsolation() {
    int status = 0;
    long timeS = System.currentTimeMillis(), timeE = System.currentTimeMillis();
    int downCount = 0;
    for (int x = 0; x < getStatus().size(); x++) {
      Vector tmp = getStatus().get(x);
      int tmpStatus = ((Integer)tmp.get(7)).intValue();
      if (status == -1 && 
        tmpStatus == -2) {
        timeE = ((Date)tmp.get(5)).getTime();
        if (timeE - timeS > 3000L)
          return true; 
        status = 0;
        downCount = 0;
      } 
      if (downCount > 3)
        return true; 
      if (tmpStatus == -1 && status != -1) {
        timeS = ((Date)tmp.get(5)).getTime();
        status = -1;
      } else if (tmpStatus == -1 && status == -1) {
        downCount++;
      } 
    } 
    return false;
  }
  
  public int getEBTimes() {
    return this.ebTimes;
  }
  
  public int getSBTimes() {
    return this.sbTimes;
  }
  
  public int getWaysideFailureTimes() {
    return this.waysideFailureTimes;
  }
  
  public int getCabinFailureTimes() {
    return this.cabinFailureTimes;
  }
  
  public Vector getBTM() {
    return this.btm;
  }
  
  public Vector getGradient() {
    return this.gradient;
  }
  
  public Vector getCurve() {
    return this.curve;
  }
  
  public Vector getCagetory() {
    return (Vector)this.category;
  }
  
  public Vector getDriverData() {
    return this.driverData;
  }
  
  public Vector getDriverMessage() {
    return this.driverMessage;
  }
  
  public Vector getDriverMessage_Ack() {
    return this.driverMessage_Ack;
  }
  
  public Vector getBtnEvent() {
    return this.btnEvent;
  }
  
  public Vector getDynamic() {
    return this.dynamic;
  }
  
  public Vector getFailure() {
    return this.failure;
  }
  
  public Vector getOriginalData() {
    return this.collector;
  }
  
  public Vector getStatus() {
    return this.status;
  }
  
  public Vector getTS() {
    return this.timeStamp;
  }
  
  public Vector getTrainData() {
    return this.trainData;
  }
  
  public Vector getVDX() {
    return this.vdx;
  }
  
  public ATPMissionGeneral getMission() {
    return this.thisMission;
  }
}
