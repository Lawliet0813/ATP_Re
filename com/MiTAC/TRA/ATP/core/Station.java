// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.core;

import java.util.Arrays;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.util.Vector;

public class Station
{
    static String[] pchtName;
    static Vector pdata;
    static String[] pengName;
    static int[] pinnerID;
    
    static {
        try {
            Station.pdata = new ConnectDB().getStation();
            Station.pinnerID = new int[Station.pdata.size()];
            Station.pchtName = new String[Station.pdata.size()];
            Station.pengName = new String[Station.pdata.size()];
            for (int i = 0; i < Station.pdata.size(); ++i) {
                final Vector vector = Station.pdata.get(i);
                Station.pchtName[i] = vector.get(0).toString();
                Station.pengName[i] = vector.get(1).toString();
                Station.pinnerID[i] = (int)vector.get(2);
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static String getStationChtName(final int key) {
        final String s = "\u672a\u77e5";
        final int binarySearch = Arrays.binarySearch(Station.pinnerID, key);
        return (binarySearch < 0) ? s : Station.pchtName[binarySearch];
    }
    
    public static String getStationEngName(final int key) {
        final String s = "unknow";
        final int binarySearch = Arrays.binarySearch(Station.pinnerID, key);
        return (binarySearch < 0) ? s : Station.pengName[binarySearch];
    }
}
