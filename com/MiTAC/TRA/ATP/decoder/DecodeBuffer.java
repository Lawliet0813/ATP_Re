// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import com.MiTAC.TRA.ATP.Tools.PathHandler;
import java.text.SimpleDateFormat;
import com.MiTAC.TRA.ATP.core.ATPMissionFailure;
import com.MiTAC.TRA.ATP.Tools.DiskTools.CopyFile;
import com.MiTAC.TRA.ATP.Tools.DiskTools.DeleteDir;
import java.util.GregorianCalendar;
import java.util.Vector;
import com.MiTAC.TRA.ATP.Tools.DiskTools.CopyDir;
import com.MiTAC.TRA.ATP.core.ATPMission;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.core.ATPMissionGeneral;
import java.util.Date;
import java.io.File;
import java.awt.Component;
import javax.swing.JOptionPane;
import com.MiTAC.TRA.ATP.ATPMessages;

public class DecodeBuffer extends Thread
{
    private static boolean running;
    private static int bufferSize;
    private static int bufferDepth;
    private static Object[][] buffer;
    private static int pointer;
    private static int size;
    public static boolean ignoreNoError;
    public static final String successMsg;
    public static final String failureMsg;
    public static final String giveupMsg;
    public static final String decodeMsg;
    public static final String waitingMsg;
    public static final String dataExistMsg;
    public static final String pathNotExistMsg;
    public static final String invaliedMsg;
    public static final String ignoreMsg;
    public static final String replaceMsg;
    public static final String extendMsg;
    public static final String noDataMsg;
    DataFeeder decoder;
    
    static {
        DecodeBuffer.running = false;
        DecodeBuffer.bufferSize = 4096;
        DecodeBuffer.bufferDepth = 15;
        DecodeBuffer.buffer = new Object[DecodeBuffer.bufferSize][DecodeBuffer.bufferDepth];
        DecodeBuffer.pointer = 0;
        DecodeBuffer.size = 0;
        DecodeBuffer.ignoreNoError = true;
        successMsg = ATPMessages.getString("MW.DECODE.STATUS.COMPLETED");
        failureMsg = ATPMessages.getString("MW.DECODE.STATUS.FAILURE");
        giveupMsg = ATPMessages.getString("MW.DECODE.STATUS.GIVEUP");
        decodeMsg = ATPMessages.getString("MW.DECODE.STATUS.DECODING");
        waitingMsg = ATPMessages.getString("MW.DECODE.STATUS.WAIT");
        dataExistMsg = ATPMessages.getString("MW.DECODE.STATUS.EXIST");
        pathNotExistMsg = ATPMessages.getString("MW.DECODE.STATUS.PATH_ERROR");
        invaliedMsg = ATPMessages.getString("MW.DECODE.STATUS.INVALIED");
        ignoreMsg = ATPMessages.getString("MW.DECODE.STATUS.IGNORE");
        replaceMsg = ATPMessages.getString("MW.DECODE.STATUS.REPLACE");
        extendMsg = ATPMessages.getString("MW.DECODE.STATUS.EXTEND");
        noDataMsg = ATPMessages.getString("MW.DECODE.STATUS.NODATA");
    }
    
    public void run() {
        DecodeBuffer.running = true;
        this.setPriority(1);
        while (true) {
            if (DecodeBuffer.pointer == DecodeBuffer.size) {
                try {
                    System.out.print("==");
                    Thread.sleep(3000L);
                }
                catch (final Exception ex) {
                    JOptionPane.showConfirmDialog(null, ex.getMessage(), "Error", 0, 0);
                    ex.printStackTrace();
                }
            }
            else {
                final Object[] data = DecodeBuffer.buffer[DecodeBuffer.pointer];
                checkPath((File)data[0], DecodeBuffer.pointer);
                data[6] = new Date(System.currentTimeMillis());
                if (data[8].equals(DecodeBuffer.waitingMsg)) {
                    data[8] = DecodeBuffer.decodeMsg;
                    final ATPMissionGeneral mson = new ATPMissionGeneral((File)data[0]);
                    try {
                        final ConnectDB cndb = new ConnectDB();
                        final Vector isDBExists = cndb.getLogDecodeCategory(new Object[] { mson.getMissionDate(), mson.getWorkShift(), mson.getTrainRunning(), mson.getDriver(), mson.getVehicle() });
                        if (isDBExists.size() != 0) {
                            if (data[13] == ATPMessages.getString("MW.DECODE.FILETYPE.RU")) {
                                cndb.DeleteLog((ATPMission)mson);
                                this.doDecode(data, cndb);
                                data[8] = DecodeBuffer.replaceMsg;
                            }
                            else if (data[13] == ATPMessages.getString("MW.DECODE.FILETYPE.MMI")) {
                                final String[] list = ((File)data[0]).list();
                                if (list.length != 0) {
                                    final String path = ((File)data[0]).getCanonicalPath().replace('\\', '/');
                                    final String[] para = path.split("/");
                                    final File replaceableDir = new File("C:\\DecodeBuffer_Replace\\" + para[para.length - 2] + "\\" + para[para.length - 1]);
                                    replaceableDir.mkdirs();
                                    final CopyDir cd = new CopyDir((File)data[0], replaceableDir);
                                    data[0] = replaceableDir;
                                    final Vector category = isDBExists.get(0);
                                    final Date dStart = category.get(24);
                                    final Date dEnd = category.get(25);
                                    System.out.println("\u8a18\u9304\u958b\u59cb\u6642\u9593" + dStart.toLocaleString());
                                    System.out.println("\u8a18\u9304\u7d50\u675f\u6642\u9593" + dEnd.toLocaleString());
                                    final File[] replaceableFile = replaceableDir.listFiles();
                                    for (int x = 0; x < replaceableFile.length; ++x) {
                                        final String fileName = replaceableFile[x].getName();
                                        final Date missionDate = (Date)data[1];
                                        final GregorianCalendar gCalendar = new GregorianCalendar();
                                        gCalendar.set(missionDate.getYear() + 1900, missionDate.getMonth(), missionDate.getDate(), new Integer(fileName.substring(0, 2)), new Integer(fileName.substring(2, 4)), new Integer(fileName.substring(4, 6)));
                                        System.out.print("\t >> File Time: " + gCalendar.getTime().toLocaleString());
                                        if (dStart.before(gCalendar.getTime()) && dEnd.after(gCalendar.getTime())) {
                                            if (replaceableFile[x].delete()) {
                                                System.out.println("  match. deleted");
                                            }
                                            else {
                                                System.out.println("  match. delete failure");
                                            }
                                        }
                                        else {
                                            System.out.println("  out of bound. need decode");
                                        }
                                    }
                                    if (((File)data[0]).list().length == 0) {
                                        System.out.println("\u5224\u5b9a\u8cc7\u6599\u91cd\u8907");
                                        data[8] = DecodeBuffer.dataExistMsg;
                                    }
                                    else {
                                        System.out.println("\u6709\u8cc7\u6599\u4e0d\u91cd\u8907");
                                        this.doDecode(data, cndb);
                                        data[8] = DecodeBuffer.extendMsg;
                                    }
                                    DeleteDir.delDir(new File("C:\\DecodeBuffer_Replace\\"));
                                    System.out.println(" \t DecodeBuffer_Replace deleted.");
                                }
                                else {
                                    data[8] = DecodeBuffer.noDataMsg;
                                }
                            }
                        }
                        else {
                            this.doDecode(data, cndb);
                        }
                    }
                    catch (final Exception ex2) {
                        ex2.printStackTrace();
                        data[8] = DecodeBuffer.failureMsg;
                        final StringBuffer sb = new StringBuffer();
                        sb.append(data[0]);
                        sb.append("_");
                        sb.append(data[1]);
                        sb.append("_");
                        sb.append(data[2]);
                        sb.append("_");
                        sb.append(data[3]);
                        sb.append("_");
                        sb.append(data[4]);
                        sb.append("_");
                        sb.append(data[5]);
                        sb.append("\n\r\t");
                        sb.append(ex2.getMessage());
                        sb.append("\n\r\t");
                        final StackTraceElement[] ste = ex2.getStackTrace();
                        for (int x2 = 0; x2 < ste.length; ++x2) {
                            sb.append(ste[x2].toString());
                            sb.append("\n\r\t");
                        }
                        sb.append("\n\n");
                        data[14] = sb.toString();
                    }
                }
                data[7] = new Date(System.currentTimeMillis());
                ++DecodeBuffer.pointer;
            }
        }
    }
    
    private void doDecode(final Object[] data, final ConnectDB cndb) throws Exception {
        final int fileNumber = ((File)data[0]).listFiles().length;
        File[] fileSplit;
        if (fileNumber > 8) {
            int slipNo = fileNumber / 8;
            slipNo += ((fileNumber % 8 != 0) ? 1 : 0);
            fileSplit = new File[slipNo];
            final File[] list = ((File)data[0]).listFiles();
            final String path = ((File)data[0]).getCanonicalPath().replace('\\', '/');
            final String[] para = path.split("/");
            File dir = new File("");
            for (int y = 0; y < fileNumber; ++y) {
                if (y % 8 == 0) {
                    dir = new File("C:\\DecodeBuffer\\" + y / 8 + "\\" + para[para.length - 2] + "\\" + para[para.length - 1]);
                    dir.mkdirs();
                    fileSplit[y / 8] = dir;
                }
                final File from = list[y];
                final File to = new File(String.valueOf(dir.getAbsolutePath()) + "\\" + list[y].getName());
                final CopyFile copyFile = new CopyFile(from, to);
            }
        }
        else {
            fileSplit = new File[] { (File)data[0] };
        }
        final Vector preLog = cndb.getLogDecodeCategory(new Object[] { data[1], data[2], data[3], data[4], data[5] });
        int ebTime = 0;
        int sbTime = 0;
        int cfTime = 0;
        int wfTime = 0;
        if (preLog.size() != 0) {
            final Vector tmp = preLog.get(0);
            ebTime = Integer.parseInt(tmp.get(19).toString());
            sbTime = Integer.parseInt(tmp.get(20).toString());
            cfTime = Integer.parseInt(tmp.get(21).toString());
            wfTime = Integer.parseInt(tmp.get(22).toString());
        }
        boolean isolation = false;
        for (int x = 0; x < fileSplit.length; ++x) {
            this.decoder = new DataFeeder(fileSplit[x]);
            ebTime += this.decoder.getEBTimes();
            sbTime += this.decoder.getSBTimes();
            cfTime += this.decoder.getCabinFailureTimes();
            wfTime += this.decoder.getWaysideFailureTimes();
            isolation = (isolation || this.decoder.getIsolation());
        }
        if (sbTime + ebTime + cfTime + wfTime == 0 && !isolation && DecodeBuffer.ignoreNoError) {
            data[8] = String.valueOf(DecodeBuffer.successMsg) + "." + DecodeBuffer.ignoreMsg;
        }
        else {
            for (int x = 0; x < fileSplit.length; ++x) {
                this.decoder = new DataFeeder(fileSplit[x]);
                cndb.InsertMissionLog(this.decoder.getMission());
            }
            Date start = this.decoder.getMission().getMissionStartTime();
            Date end = this.decoder.getMission().getMissionEndTime();
            if (preLog.size() != 0) {
                final Vector tmp2 = preLog.get(0);
                final Date ds = tmp2.get(24);
                final Date de = tmp2.get(25);
                if (ds.before(start)) {
                    start = ds;
                }
                if (de.after(end)) {
                    end = de;
                }
            }
            final String[] fileName = ((File)data[0]).list();
            for (int x2 = 0; x2 < fileName.length; ++x2) {
                final GregorianCalendar gCalendar = new GregorianCalendar();
                gCalendar.set(this.decoder.getMission().getMissionDate().getYear() + 1900, this.decoder.getMission().getMissionDate().getMonth(), this.decoder.getMission().getMissionDate().getDate(), new Integer(fileName[x2].substring(0, 2)), new Integer(fileName[x2].substring(2, 4)), new Integer(fileName[x2].substring(4, 6)));
                if (gCalendar.getTime().before(start)) {
                    start = gCalendar.getTime();
                }
                if (gCalendar.getTime().after(end)) {
                    end = gCalendar.getTime();
                }
            }
            final ATPMissionFailure t = new ATPMissionFailure((ATPMission)this.decoder.getMission());
            t.getFilterBehaviorFailure();
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            final String updateString = "update logcategory set ebtimes='" + t.getBehavior_ebTimes_filter() + "'," + " sbtimes='" + t.getBehavior_sbTimes_filter() + "'," + " waysidefailuretimes='" + wfTime + "'," + " cabinfailuretimes='" + cfTime + "'," + " isolation='" + (isolation ? 1 : 0) + "'," + " StartTime='" + sdf2.format(start) + "'," + " EndTime='" + sdf2.format(end) + "' " + "where missiondate='" + sdf.format(this.decoder.getMission().getMissionDate()) + "'" + " and wsno='" + this.decoder.getMission().getWorkShift() + "'" + " and trno='" + this.decoder.getMission().getTrainRunning() + "'" + " and did='" + this.decoder.getMission().getDriver() + "'" + " and vid='" + this.decoder.getMission().getVehicle() + "'";
            cndb.Update(updateString);
            if (fileSplit.length > 1) {
                DeleteDir.delDir(new File("C:\\DecodeBuffer\\"));
            }
            data[8] = DecodeBuffer.successMsg;
        }
        data[9] = new Integer(sbTime);
        data[10] = new Integer(ebTime);
        data[11] = new Integer(cfTime);
        data[12] = new Integer(wfTime);
    }
    
    public static boolean isRunning() {
        return DecodeBuffer.running;
    }
    
    public static synchronized void addTask(final File path) {
        System.out.println("PATH ADD: " + path.getAbsolutePath());
        if (DecodeBuffer.size == DecodeBuffer.buffer.length) {
            clearDecodeFinished();
            if (DecodeBuffer.size == DecodeBuffer.buffer.length) {
                System.out.println("Index Out Of Bounds Exception");
            }
        }
        try {
            final Vector data = PathHandler.getDecodePath(path.getPath());
            DecodeBuffer.buffer[DecodeBuffer.size][0] = path;
            DecodeBuffer.buffer[DecodeBuffer.size][1] = data.get(0);
            DecodeBuffer.buffer[DecodeBuffer.size][2] = data.get(1);
            DecodeBuffer.buffer[DecodeBuffer.size][3] = data.get(2);
            DecodeBuffer.buffer[DecodeBuffer.size][4] = data.get(3);
            DecodeBuffer.buffer[DecodeBuffer.size][5] = data.get(4);
            DecodeBuffer.buffer[DecodeBuffer.size][6] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][7] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][8] = DecodeBuffer.waitingMsg;
            DecodeBuffer.buffer[DecodeBuffer.size][9] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][10] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][11] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][12] = null;
            final String[] tmp = path.list();
            DecodeBuffer.buffer[DecodeBuffer.size][13] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][14] = null;
            ++DecodeBuffer.size;
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            DecodeBuffer.buffer[DecodeBuffer.size][0] = path;
            DecodeBuffer.buffer[DecodeBuffer.size][1] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][2] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][3] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][4] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][5] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][6] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][7] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][8] = DecodeBuffer.invaliedMsg;
            DecodeBuffer.buffer[DecodeBuffer.size][9] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][10] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][11] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][12] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][13] = null;
            DecodeBuffer.buffer[DecodeBuffer.size][14] = null;
            ex.printStackTrace();
            final StringBuffer sb = new StringBuffer();
            sb.append(DecodeBuffer.buffer[DecodeBuffer.size][0]);
            sb.append("_");
            sb.append(DecodeBuffer.buffer[DecodeBuffer.size][1]);
            sb.append("_");
            sb.append(DecodeBuffer.buffer[DecodeBuffer.size][2]);
            sb.append("_");
            sb.append(DecodeBuffer.buffer[DecodeBuffer.size][3]);
            sb.append("_");
            sb.append(DecodeBuffer.buffer[DecodeBuffer.size][4]);
            sb.append("_");
            sb.append(DecodeBuffer.buffer[DecodeBuffer.size][5]);
            sb.append("\n\r\t");
            sb.append(ex.getMessage());
            sb.append("\n\r\t");
            final StackTraceElement[] ste = ex.getStackTrace();
            for (int x = 0; x < ste.length; ++x) {
                sb.append(ste[x].toString());
                sb.append("\n\r\t");
            }
            sb.append("\n\n");
            DecodeBuffer.buffer[DecodeBuffer.size][14] = sb.toString();
            ++DecodeBuffer.size;
        }
    }
    
    private static synchronized void checkPath(final File path, final int pointer) {
        if (path.exists()) {
            try {
                final Vector data = PathHandler.getDecodePath(path.getPath());
                DecodeBuffer.buffer[pointer][0] = path;
                DecodeBuffer.buffer[pointer][1] = data.get(0);
                DecodeBuffer.buffer[pointer][2] = data.get(1);
                DecodeBuffer.buffer[pointer][3] = data.get(2);
                DecodeBuffer.buffer[pointer][4] = data.get(3);
                DecodeBuffer.buffer[pointer][5] = data.get(4);
                DecodeBuffer.buffer[pointer][6] = null;
                DecodeBuffer.buffer[pointer][7] = null;
                DecodeBuffer.buffer[pointer][8] = DecodeBuffer.waitingMsg;
                DecodeBuffer.buffer[pointer][9] = null;
                DecodeBuffer.buffer[pointer][10] = null;
                DecodeBuffer.buffer[pointer][11] = null;
                DecodeBuffer.buffer[pointer][12] = null;
                final String[] tmp = path.list();
                if (tmp.length == 0) {
                    DecodeBuffer.buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.EMPTY");
                }
                boolean typeRU = false;
                boolean typeMMI = false;
                boolean typeOther = false;
                for (int x = 0; x < tmp.length; ++x) {
                    if (tmp[x].endsWith("RU") || tmp[x].endsWith("ru")) {
                        typeRU = true;
                    }
                    else if (tmp[x].endsWith("MMI") || tmp[x].endsWith("mmi")) {
                        typeMMI = true;
                    }
                    else {
                        typeOther = true;
                    }
                }
                if (typeRU && typeMMI && typeOther) {
                    DecodeBuffer.buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.OTHER");
                }
                else if (typeRU && typeOther) {
                    DecodeBuffer.buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.RU_OTHER");
                }
                else if (typeMMI && typeOther) {
                    DecodeBuffer.buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.MMI_OTHER");
                }
                else if (typeRU && typeMMI) {
                    DecodeBuffer.buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.MIX");
                }
                else if (typeRU) {
                    DecodeBuffer.buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.RU");
                }
                else if (typeMMI) {
                    DecodeBuffer.buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.MMI");
                }
                else {
                    DecodeBuffer.buffer[pointer][13] = ATPMessages.getString("MW.DECODE.FILETYPE.UNKNOW");
                }
                DecodeBuffer.buffer[pointer][14] = null;
            }
            catch (final Exception ex) {
                ex.printStackTrace();
                DecodeBuffer.buffer[pointer][0] = path;
                DecodeBuffer.buffer[pointer][1] = null;
                DecodeBuffer.buffer[pointer][2] = null;
                DecodeBuffer.buffer[pointer][3] = null;
                DecodeBuffer.buffer[pointer][4] = null;
                DecodeBuffer.buffer[pointer][5] = null;
                DecodeBuffer.buffer[pointer][6] = null;
                DecodeBuffer.buffer[pointer][7] = null;
                DecodeBuffer.buffer[pointer][8] = DecodeBuffer.invaliedMsg;
                DecodeBuffer.buffer[pointer][9] = null;
                DecodeBuffer.buffer[pointer][10] = null;
                DecodeBuffer.buffer[pointer][11] = null;
                DecodeBuffer.buffer[pointer][12] = null;
                DecodeBuffer.buffer[pointer][13] = null;
                DecodeBuffer.buffer[pointer][14] = null;
                final StringBuffer sb = new StringBuffer();
                sb.append(DecodeBuffer.buffer[DecodeBuffer.size][0]);
                sb.append("_");
                sb.append(DecodeBuffer.buffer[DecodeBuffer.size][1]);
                sb.append("_");
                sb.append(DecodeBuffer.buffer[DecodeBuffer.size][2]);
                sb.append("_");
                sb.append(DecodeBuffer.buffer[DecodeBuffer.size][3]);
                sb.append("_");
                sb.append(DecodeBuffer.buffer[DecodeBuffer.size][4]);
                sb.append("_");
                sb.append(DecodeBuffer.buffer[DecodeBuffer.size][5]);
                sb.append("\n\r\t");
                sb.append(ex.getMessage());
                sb.append("\n\r\t");
                final StackTraceElement[] ste = ex.getStackTrace();
                for (int x2 = 0; x2 < ste.length; ++x2) {
                    sb.append(ste[x2].toString());
                    sb.append("\n\r\t");
                }
                sb.append("\n\n");
                DecodeBuffer.buffer[DecodeBuffer.size][14] = sb.toString();
            }
        }
        else {
            System.err.println(path.getAbsolutePath());
            DecodeBuffer.buffer[pointer][0] = path;
            DecodeBuffer.buffer[pointer][1] = null;
            DecodeBuffer.buffer[pointer][2] = null;
            DecodeBuffer.buffer[pointer][3] = null;
            DecodeBuffer.buffer[pointer][4] = null;
            DecodeBuffer.buffer[pointer][5] = null;
            DecodeBuffer.buffer[pointer][6] = null;
            DecodeBuffer.buffer[pointer][7] = null;
            DecodeBuffer.buffer[pointer][8] = DecodeBuffer.pathNotExistMsg;
            DecodeBuffer.buffer[pointer][9] = null;
            DecodeBuffer.buffer[pointer][10] = null;
            DecodeBuffer.buffer[pointer][11] = null;
            DecodeBuffer.buffer[pointer][12] = null;
            DecodeBuffer.buffer[pointer][13] = null;
            DecodeBuffer.buffer[pointer][14] = null;
        }
    }
    
    public static Object[][] getBufferStatus() {
        return DecodeBuffer.buffer;
    }
    
    public static Object[][] getBufferStatus2() {
        final Object[][] newBuffer = new Object[DecodeBuffer.size][17];
        for (int x = 0; x < DecodeBuffer.size; ++x) {
            Vector path = new Vector();
            try {
                path = PathHandler.getDecodePath(((File)DecodeBuffer.buffer[x][0]).getAbsolutePath());
            }
            catch (final Exception ex) {
                path = new Vector();
                path.add(null);
                path.add(null);
                path.add(null);
                path.add(null);
                path.add(null);
            }
            newBuffer[x][0] = new Integer(x + 1);
            newBuffer[x][1] = DecodeBuffer.buffer[x][0];
            newBuffer[x][2] = path.get(0);
            newBuffer[x][3] = path.get(1);
            newBuffer[x][4] = path.get(2);
            newBuffer[x][5] = path.get(3);
            newBuffer[x][6] = path.get(4);
            if (DecodeBuffer.buffer[x][6] == null) {
                newBuffer[x][7] = null;
            }
            else {
                newBuffer[x][7] = DecodeBuffer.buffer[x][6];
            }
            if (DecodeBuffer.buffer[x][7] == null) {
                newBuffer[x][8] = null;
            }
            else {
                newBuffer[x][8] = DecodeBuffer.buffer[x][7];
            }
            if (DecodeBuffer.buffer[x][6] == null) {
                newBuffer[x][9] = null;
            }
            else if (DecodeBuffer.buffer[x][7] == null) {
                newBuffer[x][9] = new Long(System.currentTimeMillis() - ((Date)DecodeBuffer.buffer[x][6]).getTime());
            }
            else {
                newBuffer[x][9] = new Long(((Date)DecodeBuffer.buffer[x][7]).getTime() - ((Date)DecodeBuffer.buffer[x][6]).getTime());
            }
            newBuffer[x][10] = DecodeBuffer.buffer[x][8];
            newBuffer[x][11] = DecodeBuffer.buffer[x][9];
            newBuffer[x][12] = DecodeBuffer.buffer[x][10];
            newBuffer[x][13] = DecodeBuffer.buffer[x][11];
            newBuffer[x][14] = DecodeBuffer.buffer[x][12];
            newBuffer[x][15] = DecodeBuffer.buffer[x][13];
            newBuffer[x][16] = DecodeBuffer.buffer[x][14];
        }
        return newBuffer;
    }
    
    public static int getBufferSize() {
        return DecodeBuffer.size;
    }
    
    public static int getBufferFinished() {
        return DecodeBuffer.pointer;
    }
    
    public static int getBufferWaiting() {
        return DecodeBuffer.size - DecodeBuffer.pointer;
    }
    
    public static synchronized void clearDecodeFinished() {
        final Object[][] newBuffer = new Object[DecodeBuffer.bufferSize][DecodeBuffer.bufferDepth];
        int counter = 0;
        final int bufferLength = DecodeBuffer.size;
        final int bufferPoint = DecodeBuffer.pointer;
        for (int x = 0; x < bufferLength; ++x) {
            if (x < bufferPoint) {
                if (!DecodeBuffer.buffer[x][8].toString().startsWith(DecodeBuffer.successMsg)) {
                    newBuffer[counter++] = DecodeBuffer.buffer[x];
                }
                else {
                    --DecodeBuffer.pointer;
                    --DecodeBuffer.size;
                }
            }
            else {
                newBuffer[counter++] = DecodeBuffer.buffer[x];
            }
        }
        DecodeBuffer.buffer = newBuffer;
    }
    
    public static synchronized void clearFailure() {
        final Object[][] newBuffer = new Object[DecodeBuffer.bufferSize][DecodeBuffer.bufferDepth];
        int counter = 0;
        final int bufferLength = DecodeBuffer.size;
        final int bufferPoint = DecodeBuffer.pointer;
        for (int x = 0; x < bufferLength; ++x) {
            if (x < bufferPoint) {
                if (!DecodeBuffer.buffer[x][8].equals(DecodeBuffer.failureMsg)) {
                    newBuffer[counter++] = DecodeBuffer.buffer[x];
                }
                else {
                    --DecodeBuffer.pointer;
                    --DecodeBuffer.size;
                }
            }
            else {
                newBuffer[counter++] = DecodeBuffer.buffer[x];
            }
        }
        DecodeBuffer.buffer = newBuffer;
    }
    
    public static synchronized void cleanExists() {
        final Object[][] newBuffer = new Object[DecodeBuffer.bufferSize][DecodeBuffer.bufferDepth];
        int counter = 0;
        final int bufferLength = DecodeBuffer.size;
        final int bufferPoint = DecodeBuffer.pointer;
        for (int x = 0; x < bufferLength; ++x) {
            if (x < bufferPoint) {
                if (!DecodeBuffer.buffer[x][8].equals(DecodeBuffer.dataExistMsg)) {
                    newBuffer[counter++] = DecodeBuffer.buffer[x];
                }
                else {
                    --DecodeBuffer.pointer;
                    --DecodeBuffer.size;
                }
            }
            else {
                newBuffer[counter++] = DecodeBuffer.buffer[x];
            }
        }
        DecodeBuffer.buffer = newBuffer;
    }
}
