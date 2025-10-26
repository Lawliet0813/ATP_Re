// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import java.io.IOException;
import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.Tools.HexCode;
import com.MiTAC.TRA.ATP.Tools.WriteToFile;

public class WaySideTelegramPacketDecoder
{
    public static final int noLinking = 5;
    public static final int noMovementAuthoruty = 12;
    public static final int noRespositioning = 16;
    public static final int noGradientProfile = 21;
    public static final int noStaticSpeedProfile = 27;
    public static final int noLevelTransistionOrder = 41;
    public static final int xData = 44;
    public static final int noCurve = 0;
    public static final int noAuxiliary4bitData = 1;
    public static final int noStopOverAndStationID = 2;
    public static final int noTemporaySpeedRestriction = 3;
    public static final int noLEUError = 4;
    public static final int noWheelWearCompensation = 5;
    public static final int noTemporarySpeedRestricionRevocation = 66;
    public static final int noDangerForShuntingInformation = 132;
    public static final int noInfillLocationReference = 136;
    public static final int noDefaultTelegram = 254;
    public static final int noEndOfInformation = 255;
    private byte[] data;
    private int location;
    private boolean decoding;
    private int[] record;
    private int[] fpCurve;
    private int[] fpBalise;
    private int[] fpGradient;
    private WriteToFile wtf;
    
    public WaySideTelegramPacketDecoder(final byte[] bdata) {
        this.location = 0;
        this.decoding = false;
        this.record = new int[41];
        this.fpCurve = new int[1];
        this.fpBalise = new int[1];
        this.fpGradient = new int[1];
        try {
            this.wtf = new WriteToFile("c:\\waySideLog.Log");
            this.set(bdata);
            this.go();
        }
        catch (final Exception ex) {}
    }
    
    public void set(final byte[] bdata) {
        this.data = bdata;
        this.location = 0;
        this.decoding = false;
        this.record = new int[41];
        for (int x = 0; x < 41; ++x) {
            this.record[x] = -1;
        }
    }
    
    public void go() throws Exception {
        this.decoding = true;
        this.location += this.Header(this.location);
        while (this.decoding) {
            final int packetno = Byte2Number.getUnsigned(HexCode.getBit(this.data, this.location, this.location + 8)[0]);
            this.wtf.writeLine("--== Decoding packet " + packetno + " ==--");
            switch (packetno) {
                case 5: {
                    this.location += this.Linking(this.location);
                    continue;
                }
                case 12:
                case 16:
                case 27:
                case 41:
                case 66:
                case 132:
                case 136:
                case 254: {
                    this.getPackeDir(this.location);
                    this.location += this.getPacketLength(this.location);
                    continue;
                }
                case 21: {
                    this.getPackeDir(this.location);
                    this.location += this.GradientProfile(this.location);
                    continue;
                }
                case 44: {
                    final int ident = Byte2Number.getUnsigned(HexCode.getBit(this.data, this.location + 32, this.location + 40)[0]);
                    switch (ident) {
                        case 0: {
                            this.Curve(this.location);
                        }
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5: {
                            this.getPackeDir(this.location);
                            this.location += this.getPacketLength(this.location);
                            continue;
                        }
                        default: {
                            this.decoding = false;
                            throw new Exception("44:" + ident + " not defined.");
                        }
                    }
                    break;
                }
                case 255: {
                    this.decoding = false;
                    continue;
                }
                default: {
                    this.decoding = false;
                    throw new Exception(String.valueOf(packetno) + " not defined.");
                }
            }
        }
        this.wtf.exists();
    }
    
    public int[] get() {
        return this.record;
    }
    
    public int getNID_BG() {
        return this.record[3];
    }
    
    public int Header(final int start) throws IOException {
        this.wtf.writeLine("--== Decoding Header packet ==--");
        try {
            int loc = start;
            int locend = 0;
            this.wtf.writeLine(HexCode.getBinaryInteger(this.data));
            locend = loc + 1;
            this.wtf.writeLine("Q_UPDOWN=" + HexCode.getBit(this.data, loc, locend)[0]);
            loc = locend;
            locend = loc + 7;
            this.wtf.writeLine("M_VERSION=" + HexCode.getBinaryInteger(HexCode.getBit(this.data, loc, locend)).substring(1, 8));
            loc = locend;
            locend = loc + 1;
            this.wtf.writeLine("Q_MEDIA=" + HexCode.getBit(this.data, loc, locend)[0]);
            loc = locend;
            locend = loc + 3;
            int tmp = HexCode.getBit(this.data, loc, locend)[0];
            this.record[1] = tmp;
            this.wtf.writeLine("N_PIG=" + tmp);
            loc = locend;
            locend = loc + 3;
            tmp = HexCode.getBit(this.data, loc, locend)[0];
            this.record[2] = tmp;
            this.wtf.writeLine("N_TOTAL=" + tmp);
            loc = locend;
            locend = loc + 2;
            this.wtf.writeLine("M_DUP=" + HexCode.getBit(this.data, loc, locend)[0]);
            loc = locend;
            locend = loc + 8;
            this.wtf.writeLine(HexCode.getBinaryInteger(HexCode.getBit(this.data, loc, locend)[0]));
            this.wtf.writeLine("M_MCOUNT=" + Byte2Number.getUnsigned(HexCode.getBit(this.data, loc, locend)[0]));
            loc = locend;
            locend = loc + 10;
            this.wtf.writeLine(HexCode.getBinaryInteger(HexCode.getBit(this.data, loc, locend)));
            this.wtf.writeLine("NID_C=" + Byte2Number.getUnsigned(HexCode.getBit(this.data, loc, locend)[0], HexCode.getBit(this.data, loc, locend)[1]));
            loc = locend;
            locend = loc + 14;
            tmp = Byte2Number.getUnsigned(HexCode.getBit(this.data, loc, locend)[0], HexCode.getBit(this.data, loc, locend)[1]);
            this.record[3] = tmp;
            this.wtf.writeLine("NID_BG=" + tmp);
            loc = locend;
            locend = loc + 1;
            tmp = HexCode.getBit(this.data, loc, locend)[0];
            this.record[4] = tmp;
            this.wtf.writeLine("Q_LINK=" + tmp);
        }
        catch (final Exception ex) {}
        return 50;
    }
    
    public int Linking(final int start) throws Exception {
        this.wtf.writeLine("--== Decoding Linking packet ==--");
        this.getPackeDir(this.location);
        int loc = start + 23;
        int locend = loc + 2;
        byte[] tmpdata = HexCode.getBit(this.data, loc, locend);
        final int Q_Scale = Byte2Number.getUnsigned(tmpdata[0]);
        this.wtf.writeLine("Q_Scale= " + Q_Scale);
        loc = locend;
        locend = loc + 15;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        int D_Link = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
        this.wtf.writeLine("D_LINK= " + D_Link);
        loc = locend;
        locend = loc + 1;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        int Q_NewCountry = Byte2Number.getUnsigned(tmpdata[0]);
        this.wtf.writeLine("Q_NewCountry= " + Q_NewCountry);
        loc = locend;
        locend = loc + 14;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        int NID_BG = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
        this.wtf.writeLine("NID_BG= " + NID_BG);
        loc = locend;
        locend = loc + 1;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        int Q_LinkOrienation = Byte2Number.getUnsigned(tmpdata[0]);
        this.wtf.writeLine("Q_LinkOrientation= " + Q_LinkOrienation);
        loc = locend;
        locend = loc + 2;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        int Q_LinkReation = Byte2Number.getUnsigned(tmpdata[0]);
        this.wtf.writeLine("Q_LinkReation= " + Q_LinkReation);
        loc = locend;
        locend = loc + 6;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        int Q_LinkACC = Byte2Number.getUnsigned(tmpdata[0]);
        this.wtf.writeLine("Q_LinkACC= " + Q_LinkACC);
        loc = locend;
        locend = loc + 5;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        final int N_ITER = Byte2Number.getUnsigned(tmpdata[0]);
        this.wtf.writeLine("N_ITER= " + N_ITER);
        for (int x = 0; x < N_ITER; ++x) {
            loc = locend;
            locend = loc + 15;
            tmpdata = HexCode.getBit(this.data, loc, locend);
            D_Link = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            this.wtf.writeLine("D_LINK= " + D_Link);
            loc = locend;
            locend = loc + 1;
            tmpdata = HexCode.getBit(this.data, loc, locend);
            Q_NewCountry = Byte2Number.getUnsigned(tmpdata[0]);
            this.wtf.writeLine("Q_NewCountry= " + Q_NewCountry);
            loc = locend;
            locend = loc + 14;
            tmpdata = HexCode.getBit(this.data, loc, locend);
            NID_BG = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            this.wtf.writeLine("NID_BG= " + NID_BG);
            loc = locend;
            locend = loc + 1;
            tmpdata = HexCode.getBit(this.data, loc, locend);
            Q_LinkOrienation = Byte2Number.getUnsigned(tmpdata[0]);
            this.wtf.writeLine("Q_LinkOrientation= " + Q_LinkOrienation);
            loc = locend;
            locend = loc + 2;
            tmpdata = HexCode.getBit(this.data, loc, locend);
            Q_LinkReation = Byte2Number.getUnsigned(tmpdata[0]);
            this.wtf.writeLine("Q_LinkReation= " + Q_LinkReation);
            loc = locend;
            locend = loc + 6;
            tmpdata = HexCode.getBit(this.data, loc, locend);
            Q_LinkACC = Byte2Number.getUnsigned(tmpdata[0]);
            this.wtf.writeLine("Q_LinkACC= " + Q_LinkACC);
        }
        return this.getPacketLength(start);
    }
    
    public int MovementAuthority(final int start) throws Exception {
        return this.getPacketLength(start);
    }
    
    public int Repositioning(final int start) throws Exception {
        return this.getPacketLength(start);
    }
    
    public int GradientProfile(final int start) throws Exception {
        this.getPackeDir(start);
        int loc = start + 23;
        int locend = loc + 2;
        byte[] tmpdata = HexCode.getBit(this.data, loc, locend);
        this.wtf.writeLine("Q_SCALE=" + Byte2Number.getUnsigned(tmpdata[0]));
        loc = locend;
        locend = loc + 15;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        this.record[5] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
        this.wtf.writeLine("D_GRADIENT=" + Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]));
        loc = locend;
        locend = loc + 1;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        this.record[6] = Byte2Number.getUnsigned(tmpdata[0]);
        this.wtf.writeLine("Q_GDIR=" + Byte2Number.getUnsigned(tmpdata[0]));
        loc = locend;
        locend = loc + 8;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        this.record[7] = Byte2Number.getUnsigned(tmpdata[0]);
        this.wtf.writeLine("G_A=" + Byte2Number.getUnsigned(tmpdata[0]));
        loc = locend;
        locend = loc + 5;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        this.wtf.writeLine("N_ITER=" + Byte2Number.getUnsigned(tmpdata[0]));
        if (Byte2Number.getUnsigned(tmpdata[0]) != 0) {
            this.wtf.writeLine("Number(" + Byte2Number.getUnsigned(tmpdata[0]) + ") of iterions of data set following this variable in a packet.");
            loc = locend;
            locend = loc + 15;
            tmpdata = HexCode.getBit(this.data, loc, locend);
            this.record[8] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
            this.wtf.writeLine("D_GRADIENT=" + Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]));
            loc = locend;
            locend = loc + 1;
            tmpdata = HexCode.getBit(this.data, loc, locend);
            this.record[9] = Byte2Number.getUnsigned(tmpdata[0]);
            this.wtf.writeLine("Q_GDIR=" + Byte2Number.getUnsigned(tmpdata[0]));
            loc = locend;
            locend = loc + 8;
            tmpdata = HexCode.getBit(this.data, loc, locend);
            this.record[10] = Byte2Number.getUnsigned(tmpdata[0]);
            this.wtf.writeLine("G_A=" + Byte2Number.getUnsigned(tmpdata[0]));
        }
        return this.getPacketLength(start);
    }
    
    public int SpeedProfile(final byte[] data, final int start) throws Exception {
        return this.getPacketLength(start);
    }
    
    public int LevelTransistionOrder(final int start) throws Exception {
        return this.getPacketLength(start);
    }
    
    public int Curve(final int start) throws Exception {
        this.getPackeDir(start);
        int loc = start + 23;
        int locend = loc + 9;
        byte[] tmpdata = HexCode.getBit(this.data, loc, locend);
        this.wtf.writeLine("NID_XUSER=" + Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]));
        loc = locend;
        locend = loc + 8;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        this.wtf.writeLine("NID_XDATA=" + Byte2Number.getUnsigned(tmpdata[0]));
        loc = locend;
        locend = loc + 13;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        this.record[11] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
        this.wtf.writeLine("D_CURVEDISTANCE=" + Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]));
        loc = locend;
        locend = loc + 12;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        this.record[12] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
        this.wtf.writeLine("L_CURVELENGHT=" + Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]));
        loc = locend;
        locend = loc + 5;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        this.record[13] = Byte2Number.getUnsigned(tmpdata[0]);
        this.wtf.writeLine("M_CURVERADIUS=" + Byte2Number.getUnsigned(tmpdata[0]));
        loc = locend;
        locend = loc + 5;
        tmpdata = HexCode.getBit(this.data, loc, locend);
        final int intr = Byte2Number.getUnsigned(tmpdata[0]);
        this.wtf.writeLine("N_ITER=" + intr);
        if (Byte2Number.getUnsigned(tmpdata[0]) != 0) {
            this.wtf.writeLine("Number(" + Byte2Number.getUnsigned(tmpdata[0]) + ") of iterions of data set following this variable in a packet.");
        }
        for (int x = 1; x <= 9; ++x) {
            if (x <= intr) {
                loc = locend;
                locend = loc + 13;
                tmpdata = HexCode.getBit(this.data, loc, locend);
                this.record[13 + (x - 1) * 3 + 1] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
                this.wtf.writeLine("D_CURVEDISTANCE=" + Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]));
                loc = locend;
                locend = loc + 12;
                tmpdata = HexCode.getBit(this.data, loc, locend);
                this.record[13 + (x - 1) * 3 + 2] = Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]);
                this.wtf.writeLine("L_CURVELENGHT=" + Byte2Number.getUnsigned(tmpdata[0], tmpdata[1]));
                loc = locend;
                locend = loc + 5;
                tmpdata = HexCode.getBit(this.data, loc, locend);
                this.record[13 + (x - 1) * 3 + 3] = Byte2Number.getUnsigned(tmpdata[0]);
                this.wtf.writeLine("M_CURVERADIUS=" + Byte2Number.getUnsigned(tmpdata[0]));
            }
        }
        return this.getPacketLength(start);
    }
    
    public int Auxiliary4bitData(final int start) throws Exception {
        return this.getPacketLength(start);
    }
    
    public int StopOverAndStationID(final int start) throws Exception {
        return this.getPacketLength(start);
    }
    
    public int TemporaySpeedRestriction(final int start) throws Exception {
        return this.getPacketLength(start);
    }
    
    public int LEUError(final int start) throws Exception {
        return this.getPacketLength(start);
    }
    
    public int WheelWearCompenstation(final int start) throws Exception {
        return this.getPacketLength(start);
    }
    
    public int TemporarySpeedRestrictionRevocation(final int start) throws Exception {
        return this.getPacketLength(start);
    }
    
    public int DangerForShuntingInformation(final int start) throws Exception {
        return this.getPacketLength(start);
    }
    
    public int InfillLocationReference(final int start) throws Exception {
        return this.getPacketLength(start);
    }
    
    public int DefaultTelegram(final int start) throws Exception {
        return this.getPacketLength(start);
    }
    
    public int EndOfInformation(final int start) throws IOException {
        this.wtf.writeLine("End flag.");
        return 8;
    }
    
    private void getPackeDir(final int start) throws Exception {
        final int loc = start + 8;
        final int locend = loc + 2;
        final byte code = HexCode.getBit(this.data, loc, locend)[0];
        this.wtf.writeLine(". Direcotion: " + ((code == 0) ? "Reverse" : "Normal"));
        this.record[0] = Byte2Number.getUnsigned(code);
    }
    
    private int getPacketLength(final int start) throws Exception {
        int packetlen = 0;
        final int loc = start + 10;
        final int locend = loc + 13;
        final byte[] code = HexCode.getBit(this.data, loc, locend);
        this.wtf.writeLine(HexCode.getBinaryInteger(code));
        packetlen = Byte2Number.getUnsigned(code[0], code[1]);
        this.wtf.writeLine("length of this packet: " + packetlen);
        return packetlen;
    }
    
    public static void main(final String[] args) {
        final byte[] data = { -112, 2, 1, -18, -56, -127, -61, 16, 36, -96, 0, 127, -32, 0, 60, 31, -1 };
        final WaySideTelegramPacketDecoder wtpd = new WaySideTelegramPacketDecoder(data);
        for (int x = 0; x < wtpd.get().length; ++x) {
            System.err.print(String.valueOf(wtpd.get()[x]) + " ");
        }
    }
}
