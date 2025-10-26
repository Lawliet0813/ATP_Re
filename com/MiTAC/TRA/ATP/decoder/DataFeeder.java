// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.FileRead;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import com.MiTAC.TRA.ATP.core.ATPMissionGeneral;
import com.MiTAC.TRA.ATP.Tools.RecordHandler;
import java.util.Date;
import java.io.File;
import java.util.Vector;
import com.MiTAC.TRA.ATP.Tools.WriteToFile;

public class DataFeeder
{
    WriteToFile wtf;
    WriteToFile statusATP;
    private boolean atpdown;
    private int msgLength;
    private int loc;
    private Vector collector;
    private int length;
    private int current;
    private boolean done;
    private boolean cancel;
    private File root;
    private File file;
    private String WSNo;
    private String TRNo;
    private String VID;
    private String Driver;
    private Date RunningDate;
    private Vector gradient;
    private Vector curve;
    private Vector btm;
    private Vector driverData;
    private Vector trainData;
    private RecordHandler category;
    private Vector dynamic;
    private Vector status;
    private Vector failure;
    private Vector driverMessage;
    private Vector driverMessage_Ack;
    private Vector btnEvent;
    private Vector timeStamp;
    private Vector vdx;
    private ATPMissionGeneral thisMission;
    private RUDecoder rudecoder;
    private int ebTimes;
    private int sbTimes;
    private int cabinFailureTimes;
    private int waysideFailureTimes;
    
    public DataFeeder(final File mission) throws Exception {
        this.wtf = new WriteToFile("c:\\debug.txt");
        this.statusATP = new WriteToFile("C:\\ATPMW\\ATP_STATUS.txt");
        this.atpdown = false;
        this.msgLength = 0;
        this.loc = 0;
        this.collector = new Vector();
        this.current = 0;
        this.gradient = new Vector();
        this.curve = new Vector();
        this.btm = new Vector();
        this.driverData = new Vector();
        this.trainData = new Vector();
        this.category = new RecordHandler();
        this.dynamic = new Vector();
        this.status = new Vector();
        this.failure = new Vector();
        this.driverMessage = new Vector();
        this.driverMessage_Ack = new Vector();
        this.btnEvent = new Vector();
        this.timeStamp = new Vector();
        this.vdx = new Vector();
        this.rudecoder = new RUDecoder();
        this.ebTimes = 0;
        this.sbTimes = 0;
        this.cabinFailureTimes = 0;
        this.waysideFailureTimes = 0;
        this.thisMission = new ATPMissionGeneral(mission);
        this.root = this.thisMission.getFile();
        this.go();
        this.thisMission.setCategory(this.getCagetory());
        this.thisMission.setVDX(this.getVDX());
        this.thisMission.setDynamic(this.getDynamic());
        this.thisMission.setFailure(this.getFailure());
        this.thisMission.setStatus(this.getStatus());
        this.thisMission.setTimeStamp(this.getTS());
        this.thisMission.setDriverMessage(this.getDriverMessage());
        this.thisMission.setDriverMessage_Ack(this.getDriverMessage_Ack());
        this.thisMission.setBtnEvent(this.getBtnEvent());
        this.thisMission.setBTM(this.getBTM());
        this.thisMission.setTrainData(this.getTrainData());
        this.thisMission.setGradient(this.getGradient());
        this.thisMission.setCurve(this.getCurve());
        this.wtf.close();
        this.statusATP.close();
    }
    
    public void go() {
        try {
            this.current = 0;
            if (this.root.isDirectory()) {
                final File[] fList = this.root.listFiles();
                this.length = fList.length;
                for (int i = 0; i < fList.length; ++i) {
                    if (fList[i].getName().endsWith(".MMI") || fList[i].getName().endsWith(".RU")) {
                        this.file = fList[i];
                        this.decode(this.file.getPath());
                        ++this.current;
                    }
                }
            }
            else {
                if (!this.root.getName().endsWith(".MMI") && !this.root.getName().endsWith(".RU")) {
                    this.length = 0;
                    throw new Exception("Not a ATP file, please select a ATP Log file.");
                }
                this.length = 1;
                this.file = this.root;
                this.decode(this.file.getPath());
            }
            this.separateData();
            this.collectTrainCategory();
            this.done = true;
        }
        catch (final Exception ex) {
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
        final Vector tmptrainData = PathHandler.getDecodePath(this.file.getParentFile().getAbsolutePath());
        if (tmptrainData.size() != 5) {
            throw new ArrayIndexOutOfBoundsException("wrong length");
        }
        final Date pathRunningDate = tmptrainData.get(0);
        final String pathWSNo = tmptrainData.get(1);
        final String pathTRNo = tmptrainData.get(2);
        final String pathDriver = tmptrainData.get(3);
        final String pathVID = tmptrainData.get(4);
        String codeWSNo;
        String codeTRNo;
        String codeDriver;
        Date codeRunningDate;
        if (this.getDriverData().size() > 0) {
            final RecordHandler tmp = this.getDriverData().get(0);
            codeWSNo = (String)tmp.get(8);
            codeTRNo = (String)tmp.get(7);
            codeDriver = (String)tmp.get(6);
            codeRunningDate = (Date)tmp.get(1);
        }
        else {
            codeWSNo = "wsno";
            codeTRNo = "trno";
            codeDriver = "driverId";
            codeRunningDate = pathRunningDate;
        }
        RecordHandler tmp;
        if (this.getTrainData().size() > 0) {
            final Vector newData = (Vector)this.getTrainData().get(0);
            tmp = new RecordHandler();
            tmp.add(newData.get(0));
            tmp.add(newData.get(1));
            tmp.add(newData.get(2));
            tmp.add(newData.get(3));
            tmp.add(newData.get(4));
            tmp.add(newData.get(5));
            tmp.add(newData.get(21));
            tmp.add(newData.get(20));
            tmp.add(newData.get(19));
            tmp.add(newData.get(18));
            tmp.add(newData.get(17));
            tmp.add(newData.get(16));
            tmp.add(newData.get(15));
            tmp.add(newData.get(14));
            tmp.add(newData.get(13));
            tmp.add(newData.get(12));
            tmp.add(newData.get(11));
            tmp.add(newData.get(10));
            tmp.add(newData.get(9));
            tmp.add(newData.get(8));
            tmp.add(newData.get(7));
            tmp.add(newData.get(6));
        }
        else {
            tmp = new RecordHandler();
            tmp.add((Object)"miss traindata");
            tmp.add((Object)"1980/12/17 01:01:01");
            tmp.add((Object)new Integer(0));
            tmp.add((Object)new Integer(10000000));
            tmp.add((Object)new Integer(4));
            tmp.add((Object)new Integer(6));
            tmp.add((Object)"1");
            tmp.add((Object)"tmp");
            tmp.add((Object)new Integer(0));
            tmp.add((Object)new Integer(0));
            tmp.add((Object)new Double(0.0));
            tmp.add((Object)new Integer(0));
            tmp.add((Object)new Integer(0));
            tmp.add((Object)new Integer(0));
            tmp.add((Object)new Integer(0));
            tmp.add((Object)new Integer(0));
            tmp.add((Object)"");
            tmp.add((Object)new Integer(0));
            tmp.add((Object)new Integer(0));
            tmp.add((Object)new Integer(0));
            tmp.add((Object)new Integer(0));
            tmp.add((Object)new Integer(0));
        }
        final String codeVID = new StringBuffer().append(tmp.get(5)).toString();
        if (pathWSNo.equals(codeWSNo) && pathTRNo.equals(codeTRNo) && pathDriver.equals(codeDriver) && pathVID.equals(codeVID) && codeRunningDate.equals(pathRunningDate)) {
            this.WSNo = codeWSNo;
            this.TRNo = codeTRNo;
            this.Driver = codeDriver;
            this.VID = codeVID;
            this.RunningDate = pathRunningDate;
        }
        else {
            this.WSNo = pathWSNo;
            this.TRNo = pathTRNo;
            this.Driver = pathDriver;
            this.VID = String.valueOf(pathVID) + "_Er";
            this.RunningDate = pathRunningDate;
        }
        (this.category = (RecordHandler)tmp.clone()).remove(5);
        this.category.remove(4);
        this.category.remove(3);
        this.category.remove(2);
        this.category.remove(0);
        this.category.insertElementAt((Object)this.WSNo, 1);
        this.category.insertElementAt((Object)this.TRNo, 2);
        this.category.insertElementAt((Object)this.Driver, 3);
        this.category.add((Object)new Integer(this.getEBTimes()));
        this.category.add((Object)new Integer(this.getSBTimes()));
        this.category.add((Object)new Integer(this.getCabinFailureTimes()));
        this.category.add((Object)new Integer(this.getWaysideFailureTimes()));
        this.category.add((Object)new Integer(this.getIsolation() ? 1 : 0));
    }
    
    private void decode(final String filepath) {
        try {
            this.msgLength = 0;
            this.loc = 0;
            final FileRead fr = new FileRead(filepath);
            final byte[] originalData = fr.getCodes();
            this.wtf.writeLine("\n");
            this.wtf.writeLine("===File======================================================================");
            this.wtf.writeLine(filepath);
            this.wtf.writeLine("-----------------------------------------------------------------------------");
            while (this.loc < originalData.length) {
                this.msgLength = 16 + Byte2Number.getUnsigned(originalData[this.loc + 15]);
                if (this.loc + this.msgLength > originalData.length) {
                    this.msgLength = originalData.length - this.loc;
                }
                final byte[] data = fr.getCodes(this.loc, this.msgLength);
                this.collector.add(this.rudecoder.getRUDecoder(data));
                this.wtf.writeLine("loc:" + this.loc + ".\tlength:" + this.msgLength);
                this.wtf.writeLine("Oirg data: " + HexCode.getHexA_String(data));
                this.loc += this.msgLength;
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void separateData() throws Exception {
        for (int i = 0; i < this.collector.size(); ++i) {
            this.atpdown = false;
            final Vector tmp = (Vector)this.collector.get(i).clone();
            if (tmp.size() != 0) {
                if (this.thisMission.getMissionDate().getDate() == tmp.get(1).getDate()) {
                    final int type = tmp.get(4);
                    switch (type) {
                        case 1:
                        case 4: {
                            this.separateATPdata(tmp);
                            break;
                        }
                        case 2: {
                            this.statusATP.writeLine("[STATUS ATP] " + tmp);
                            final int atpType = tmp.get(5);
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
                            int mode = -2;
                            if (atpType == 15) {
                                mode = -2;
                            }
                            else if (atpType == 16) {
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
                        }
                        case 3: {
                            break;
                        }
                        case 21: {
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
                        }
                        case 22:
                        case 23:
                        case 24:
                        case 31:
                        case 32:
                        case 33:
                        case 41:
                        case 42: {
                            break;
                        }
                        case 43:
                        case 44:
                        case 45:
                        case 46:
                        case 47: {
                            if (tmp.get(5).getClass() == "".getClass()) {
                                break;
                            }
                            final Vector curve = tmp.get(8);
                            final Vector gradient = tmp.get(7);
                            final int nid_bg = tmp.get(5);
                            tmp.remove(8);
                            tmp.remove(7);
                            tmp.remove(4);
                            final Integer x = tmp.get(3);
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
                            if (new Integer(gradient.get(6).toString()) >= 0) {
                                this.gradient.add(gradient);
                            }
                            curve.insertElementAt(this.thisMission.getMissionDate(), 0);
                            curve.insertElementAt(this.thisMission.getWorkShift(), 1);
                            curve.insertElementAt(this.thisMission.getTrainRunning(), 2);
                            curve.insertElementAt(this.thisMission.getDriver(), 3);
                            curve.insertElementAt(this.thisMission.getVehicle(), 4);
                            curve.insertElementAt(new Integer(nid_bg), 5);
                            if (new Integer(curve.get(6).toString()) >= 0) {
                                this.curve.add(curve);
                                break;
                            }
                            break;
                        }
                        case 51:
                        case 52:
                        case 61:
                        case 62:
                        case 63:
                        case 64:
                        case 71:
                        case 72:
                        case 91: {
                            break;
                        }
                        case 201: {
                            this.atpdown = true;
                            this.eventRising((Vector)tmp.clone());
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
                                final Vector tmpdyn = (Vector)this.dynamic.lastElement().clone();
                                tmpdyn.setElementAt(new Integer(-1), 9);
                                tmpdyn.setElementAt(new Integer(-1), 10);
                                tmpdyn.setElementAt(new Integer(-1), 11);
                                tmpdyn.setElementAt(new Integer(0), 12);
                                tmpdyn.setElementAt(new Integer(0), 13);
                                tmpdyn.setElementAt(new Integer(0), 14);
                                this.dynamic.add(tmpdyn);
                                break;
                            }
                            break;
                        }
                        case 211: {
                            this.statusATP.writeLine("[PERIODIC SPEED DISTANCE] " + tmp);
                            break;
                        }
                        case 216: {
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
                        }
                        case 221:
                        case 222:
                        case 223:
                        case 224:
                        case 225:
                        case 227:
                        case 228: {
                            break;
                        }
                        default: {
                            System.err.println("Err :  (" + type + ")unknow package.");
                            break;
                        }
                    }
                    if (type != 91) {
                        this.eventRising(this.collector.get(i));
                    }
                }
            }
        }
    }
    
    private void eventRising(final Vector arg0, final boolean arg1) {
        final Vector tmpTS = new Vector();
        tmpTS.add(this.thisMission.getMissionDate());
        tmpTS.add(this.thisMission.getWorkShift());
        tmpTS.add(this.thisMission.getTrainRunning());
        tmpTS.add(this.thisMission.getDriver());
        tmpTS.add(this.thisMission.getVehicle());
        if (this.atpdown) {
            tmpTS.add(arg0.get(1));
            tmpTS.add(arg0.get(5));
            tmpTS.add(arg0.get(6));
        }
        else {
            tmpTS.add(arg0.get(1));
            tmpTS.add(arg0.get(2));
            tmpTS.add(arg0.get(3));
        }
        if (this.timeStamp.size() > 0) {
            final Vector compare = this.timeStamp.get(this.timeStamp.size() - 1);
            if (tmpTS.get(5).equals(compare.get(5))) {
                if (tmpTS.get(7) > compare.get(7)) {
                    this.timeStamp.remove(this.timeStamp.size() - 1);
                    this.timeStamp.add(tmpTS);
                }
            }
            else {
                this.timeStamp.add(tmpTS);
            }
        }
        else {
            this.timeStamp.add(tmpTS);
        }
    }
    
    private void eventRising(final Vector arg0) {
        final Vector tmpTS = new Vector();
        tmpTS.add(this.thisMission.getMissionDate());
        tmpTS.add(this.thisMission.getWorkShift());
        tmpTS.add(this.thisMission.getTrainRunning());
        tmpTS.add(this.thisMission.getDriver());
        tmpTS.add(this.thisMission.getVehicle());
        if (this.atpdown) {
            tmpTS.add(arg0.get(1));
            tmpTS.add(arg0.get(5));
            tmpTS.add(arg0.get(6));
        }
        else {
            tmpTS.add(arg0.get(1));
            tmpTS.add(arg0.get(2));
            tmpTS.add(arg0.get(3));
        }
        if (this.timeStamp.size() > 0) {
            final Vector compare = this.timeStamp.get(this.timeStamp.size() - 1);
            if (tmpTS.get(5).equals(compare.get(5))) {
                if (tmpTS.get(7) > compare.get(7)) {
                    this.timeStamp.remove(this.timeStamp.size() - 1);
                    this.timeStamp.add(tmpTS);
                }
            }
            else if (new Integer(compare.get(6).toString()) == 0 && new Integer(tmpTS.get(6).toString()) != 0) {
                final Vector tmpTS2 = new Vector();
                tmpTS2.add(tmpTS.get(0));
                tmpTS2.add(tmpTS.get(1));
                tmpTS2.add(tmpTS.get(2));
                tmpTS2.add(tmpTS.get(3));
                tmpTS2.add(tmpTS.get(4));
                tmpTS2.add(new Date(tmpTS.get(5).getTime() - 1000L));
                tmpTS2.add(compare.get(6));
                tmpTS2.add(compare.get(7));
                this.timeStamp.add(tmpTS2);
                this.timeStamp.add(tmpTS);
            }
            else if (new Integer(compare.get(6).toString()) != 0 && new Integer(tmpTS.get(6).toString()) == 0) {
                this.timeStamp.add(tmpTS);
            }
            else if (tmpTS.get(5).getTime() - compare.get(5).getTime() >= 3000L && (!compare.get(6).equals(tmpTS.get(6)) || !compare.get(7).equals(tmpTS.get(7)))) {
                this.timeStamp.add(tmpTS);
            }
        }
        else {
            this.timeStamp.add(tmpTS);
        }
    }
    
    private void separateATPdata(final Vector arg0) {
        final Vector tmp = (Vector)arg0.clone();
        switch (arg0.get(5)) {
            case 1: {
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
            }
            case 2: {
                this.eventRising((Vector)tmp.clone(), true);
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
                this.caculateEBSBTimes(tmp);
            }
            case 3:
            case 4:
            case 5: {}
            case 6: {}
            case 8: {
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
            }
            case 9: {
                this.eventRising((Vector)tmp.clone(), true);
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
                this.caculateFailure(tmp);
                break;
            }
            case 10: {
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
            }
            case 14: {
                this.driverData.add(arg0);
            }
            case 15:
            case 16:
            case 17:
            case 18:
            case 19: {}
            case 110: {
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
            }
            case 111: {
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
    }
    
    private void caculateEBSBTimes(final Vector data) {
        if (data.get(8) != 0) {
            ++this.ebTimes;
        }
        if (data.get(9) != 0) {
            ++this.sbTimes;
        }
    }
    
    private void caculateFailure(final Vector data) {
        this.thisMission.getFailure();
        if (data.get(6) == 8 || data.get(6) == 15 || data.get(6) == 16 || data.get(6) == 21 || data.get(6) == 34) {
            ++this.waysideFailureTimes;
        }
        else {
            ++this.cabinFailureTimes;
        }
    }
    
    public boolean getIsolation() {
        int status = 0;
        long timeS = System.currentTimeMillis();
        long timeE = System.currentTimeMillis();
        int downCount = 0;
        for (int x = 0; x < this.getStatus().size(); ++x) {
            final Vector tmp = this.getStatus().get(x);
            final int tmpStatus = tmp.get(7);
            if (status == -1 && tmpStatus == -2) {
                timeE = tmp.get(5).getTime();
                if (timeE - timeS > 3000L) {
                    return true;
                }
                status = 0;
                downCount = 0;
            }
            if (downCount > 3) {
                return true;
            }
            if (tmpStatus == -1 && status != -1) {
                timeS = tmp.get(5).getTime();
                status = -1;
            }
            else if (tmpStatus == -1 && status == -1) {
                ++downCount;
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
