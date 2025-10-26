// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.decoder;

import java.util.Vector;
import com.MiTAC.TRA.ATP.Tools.Byte2Number;
import com.MiTAC.TRA.ATP.decoder.waySidePacket.WaySideTelegramPacketDecoder;

public class BTMDecoder
{
    int[] pointer;
    int[][] pnt;
    byte[][][] btmData;
    private WaySideTelegramPacketDecoder wtpd;
    private boolean haveResult;
    
    public BTMDecoder() {
        this.pointer = new int[10];
        this.pnt = new int[10][5];
        this.btmData = new byte[10][5][32];
        this.haveResult = false;
        this.init();
    }
    
    private void init() {
        for (int x = 0; x < this.pointer.length; ++x) {
            this.pointer[x] = -1;
            for (int y = 0; y < this.pnt[x].length; ++y) {
                this.pnt[x][y] = 0;
            }
        }
    }
    
    public void setData(final byte[] bdata, final int telegramNo) {
        final int sequenceNumber = Byte2Number.getUnsigned(bdata[0]);
        int point = -1;
        for (int x = 0; x < this.pointer.length; ++x) {
            if (this.pointer[x] == sequenceNumber) {
                point = x;
            }
        }
        if (point == -1) {
            for (int x = 0; x < this.pointer.length; ++x) {
                if (this.pointer[x] == -1) {
                    point = x;
                    this.pointer[x] = sequenceNumber;
                    break;
                }
            }
        }
        if (point != -1) {
            this.setData(bdata, point, telegramNo);
        }
    }
    
    private void setData(final byte[] bdata, final int point, int telegramNo) {
        this.haveResult = false;
        --telegramNo;
        this.pnt[point][telegramNo] = 1;
        this.btmData[point][telegramNo] = bdata;
        boolean collect5 = true;
        for (int x = 0; x < this.pnt[point].length; ++x) {
            if (this.pnt[point][x] == 0) {
                collect5 = false;
            }
        }
        if (collect5) {
            this.haveResult = true;
            this.pointer[point] = -1;
            for (int x = 0; x < this.pnt[point].length; ++x) {
                this.pnt[point][x] = 0;
            }
            this.decode(this.btmData[point]);
        }
    }
    
    private void decode(final byte[][] bdata) {
        try {
            final byte[] data = new byte[104];
            for (int x = 0; x < bdata.length; ++x) {
                final byte[] tmp = bdata[x];
                int loc = -1;
                switch (x) {
                    case 0: {
                        for (int y = 0; y < 4; ++y) {
                            data[y] = tmp[22 + y];
                        }
                        break;
                    }
                    case 1: {
                        loc = ((loc < 0) ? 4 : loc);
                    }
                    case 2: {
                        loc = ((loc < 0) ? 29 : loc);
                    }
                    case 3: {
                        loc = ((loc < 0) ? 54 : loc);
                    }
                    case 4: {
                        loc = ((loc < 0) ? 79 : loc);
                        for (int y = 0; y < 25; ++y) {
                            data[loc + y] = tmp[1 + y];
                        }
                        break;
                    }
                }
            }
            if (this.wtpd == null) {
                this.wtpd = new WaySideTelegramPacketDecoder(data);
            }
            else {
                this.wtpd.set(data);
                this.wtpd.go();
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String toString() {
        return this.wtpd.getTelegramStream();
    }
    
    public int[] getBTMData() {
        return this.wtpd.get();
    }
    
    public int getNID_BG() {
        return this.wtpd.getNID_BG();
    }
    
    public Vector getGradient() {
        final int[][] gradient = this.wtpd.getGradient();
        final Vector rtn = new Vector();
        for (int x = 0; x < gradient.length; ++x) {
            final int[] zz = gradient[x];
            rtn.add(new Integer(zz[0]));
            rtn.add(new Integer(zz[1]));
            rtn.add(new Integer(zz[2]));
        }
        while (rtn.size() < 27) {
            rtn.add(new Integer(-1));
        }
        return rtn;
    }
    
    public Vector getCurve() {
        final int[][] curve = this.wtpd.getCurve();
        final Vector rtn = new Vector();
        for (int x = 0; x < curve.length; ++x) {
            final int[] zz = curve[x];
            rtn.add(new Integer(zz[0]));
            rtn.add(new Integer(zz[1]));
            rtn.add(new Integer(zz[2]));
        }
        while (rtn.size() < 27) {
            rtn.add(new Integer(-1));
        }
        return rtn;
    }
    
    public int[] getDetail() {
        return new int[] { this.wtpd.getNID_BG(), this.wtpd.getMCOUNT() };
    }
    
    public boolean isDataCollectFinished() {
        return this.haveResult;
    }
}
