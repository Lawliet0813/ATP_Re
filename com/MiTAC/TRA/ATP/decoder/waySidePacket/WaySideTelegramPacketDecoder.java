// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder.waySidePacket;

import com.MiTAC.TRA.ATP.Tools.HexCode;

public class WaySideTelegramPacketDecoder
{
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
    private boolean decoding;
    private int[] record;
    private int[] fpCurve;
    private int[] fpBalise;
    private int[] fpGradient;
    private int nidBG;
    private int signal;
    private int[][] gradient;
    private int[][] curve;
    private StringBuffer sb;
    
    public WaySideTelegramPacketDecoder(final byte[] bdata) {
        this.decoding = false;
        this.record = new int[41];
        this.fpCurve = new int[1];
        this.fpBalise = new int[1];
        this.fpGradient = new int[1];
        this.nidBG = 0;
        this.signal = -1;
        try {
            this.set(bdata);
            this.go();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void set(final byte[] bdata) {
        this.data = bdata;
        this.decoding = false;
        this.record = new int[41];
        for (int x = 0; x < 41; ++x) {
            this.record[x] = -1;
        }
    }
    
    public void go() throws Exception {
        this.nidBG = 0;
        this.signal = -1;
        this.gradient = new int[][] { { -1, -1, -1 } };
        this.curve = new int[][] { { -1, -1, -1 } };
        this.sb = new StringBuffer();
        this.decoding = true;
        byte[] tmpdata = HexCode.getBit(this.data, 0, 50);
        final Header hd = new Header(this.data);
        int loc = hd.get_L_PACKET();
        this.sb.append(String.valueOf(hd.toString()) + "\r\n");
        while (this.decoding) {
            try {
                tmpdata = HexCode.getBit(this.data, loc, loc + 23, true);
                final waySidePacketBody wspb = new DefaultTelegram(tmpdata);
                tmpdata = HexCode.getBit(this.data, loc, loc + wspb.get_L_PACKET(), true);
                loc += wspb.get_L_PACKET();
                this.nidBG = hd.get_NID_BG();
                this.signal = hd.get_M_MCOUNT();
                switch (wspb.get_NID_PACKET()) {
                    case 5: {
                        continue;
                    }
                    case 12: {
                        continue;
                    }
                    case 16: {
                        continue;
                    }
                    case 27: {
                        continue;
                    }
                    case 41: {
                        continue;
                    }
                    case 66: {
                        continue;
                    }
                    case 132: {
                        continue;
                    }
                    case 136: {
                        continue;
                    }
                    case 254: {
                        final String tmp = "== P254, Default telegram. ==";
                        this.sb.append(String.valueOf(tmp) + "\r\n" + wspb.toString());
                        continue;
                    }
                    case 21: {
                        final GradientProfile gp = new GradientProfile(tmpdata);
                        this.gradient = gp.get_GRADIENT();
                        this.sb.append(gp.toString());
                        continue;
                    }
                    case 44: {
                        final P44 p44 = new P44(tmpdata);
                        switch (p44.get_NID_XDATA()) {
                            case 0: {
                                final Curve crv = new Curve(tmpdata);
                                this.curve = crv.get_Curve();
                                this.sb.append(crv.toString());
                                continue;
                            }
                            case 1: {
                                continue;
                            }
                            case 2: {
                                continue;
                            }
                            case 3: {
                                continue;
                            }
                            case 4: {
                                continue;
                            }
                            case 5: {
                                continue;
                            }
                            default: {
                                this.sb.append("||||||||| unexpected: P44 packet\r\n" + wspb.toString() + "\r\n");
                                continue;
                            }
                        }
                        break;
                    }
                    case 255: {
                        this.sb.append("== end == \r\n\r\n");
                        this.decoding = false;
                        continue;
                    }
                    default: {
                        this.sb.append("||||||||| unexpected packet\r\n" + wspb.toString() + "\r\n");
                        continue;
                    }
                }
            }
            catch (final Exception ex) {
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
