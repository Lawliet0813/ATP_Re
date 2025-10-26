// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.core;

import java.util.Arrays;
import com.MiTAC.TRA.ATP.Tools.RecordHandler;
import java.util.Date;
import java.util.Vector;

public class ATPMissionFailure extends ATPMissionGeneral
{
    private Vector _$16210;
    private int _$16392;
    private int _$16394;
    private int _$16393;
    private int _$16395;
    private Vector _$9529;
    private Vector _$16391;
    
    public ATPMissionFailure(final Date date, final String s, final String s2, final String s3, final String s4) {
        super(date, s, s2, s3, s4);
        this._$16392 = 0;
        this._$16393 = 0;
        this._$16394 = 0;
        this._$16395 = 0;
    }
    
    public ATPMissionFailure(final ATPMission atpMission) {
        super(atpMission.getMissionDate(), atpMission.getWorkShift(), atpMission.getTrainRunning(), atpMission.getDriver(), atpMission.getVehicle());
        this._$16392 = 0;
        this._$16393 = 0;
        this._$16394 = 0;
        this._$16395 = 0;
    }
    
    private void _$16402() throws Exception {
        if (this.getEBTimes() != 0 || this.getSBTimes() != 0) {
            final RecordHandler recordHandler = new RecordHandler();
            final RecordHandler recordHandler2 = new RecordHandler();
            final RecordHandler recordHandler3 = new RecordHandler();
            final RecordHandler recordHandler4 = new RecordHandler();
            final Vector vector = new Vector();
            for (int i = 0; i < super.getStatus().size(); ++i) {
                final Vector vector2 = super.getStatus().get(i);
                if ((int)vector2.get(8) > 0 || (int)vector2.get(9) > 0) {
                    recordHandler2.addVector(this._$16400(new int[] { 5 }, vector2));
                    vector.add(this._$16400(new int[] { 6, 7, 8, 9, 10 }, vector2));
                }
            }
            for (int j = 0; j < super.getDynamic().size(); ++j) {
                recordHandler.addVector(this._$16400(new int[] { 5 }, (Vector)super.getDynamic().get(j)));
            }
            for (int k = 0; k < super.getTimeStamp().size(); ++k) {
                recordHandler3.addVector(this._$16400(new int[] { 5 }, (Vector)super.getTimeStamp().get(k)));
            }
            for (int l = 0; l < super.getBTM().size(); ++l) {
                recordHandler4.addVector(this._$16400(new int[] { 5 }, (Vector)super.getBTM().get(l)));
            }
            final Vector<RecordHandler> $16210 = new Vector<RecordHandler>();
            for (int n = 0; n < recordHandler2.size(); ++n) {
                final RecordHandler e = new RecordHandler();
                e.add((Object)this.getDriver());
                e.add((Object)this.getWorkShift());
                e.add((Object)this.getTrainRunning());
                final int binarySearch = Arrays.binarySearch(recordHandler.toArray(), recordHandler2.get(n));
                final Vector $16211 = this._$16400(new int[] { 5, 6, 8, 9, 10, 12, 13, 14 }, super.getDynamic().get((Math.abs(binarySearch) - 2 < 0) ? 0 : (Math.abs(binarySearch) - 2)));
                final int value = $16211.get(3) * 36 / 1000;
                final int value2 = $16211.get(4) * 36 / 1000;
                $16211.setElementAt(new Integer(value), 3);
                $16211.setElementAt(new Integer(value2), 4);
                e.addVector($16211);
                e.addVector((Vector)vector.get(n));
                final int binarySearch2 = Arrays.binarySearch(recordHandler3.toArray(), recordHandler2.get(n));
                final int n2 = (Math.abs(binarySearch2) - 2 < 0) ? 0 : (Math.abs(binarySearch2) - 2);
                final Vector $16212 = this._$16400(new int[] { 6 }, super.getTimeStamp().get(n2));
                $16212.setElementAt(new Integer((int)$16212.get(0) * 36 / 1000), 0);
                e.addVector($16212);
                final int intValue = super.getTimeStamp().get(n2).get(7);
                final int binarySearch3 = Arrays.binarySearch(recordHandler4.toArray(), recordHandler2.get(n));
                final int n3 = (Math.abs(binarySearch3) - 2 < 0) ? 0 : (Math.abs(binarySearch3) - 2);
                e.addVector(this._$16400(new int[] { 6 }, (Vector)super.getBTM().get(n3)));
                e.add((Object)new Integer(intValue - ((Vector<Integer>)super.getBTM().get(n3)).get(7)));
                String s;
                if (((Vector<Integer>)vector.get(n)).get(2) != 0) {
                    s = "EB\u4f5c\u7528";
                }
                else {
                    s = "SB\u4f5c\u7528";
                }
                e.add((Object)s);
                e.add((Object)this);
                $16210.add(e);
            }
            this._$16210 = $16210;
        }
    }
    
    private void _$16433() throws Exception {
        if (super.getCabinFailureTimes() != 0) {
            final RecordHandler recordHandler = new RecordHandler();
            final RecordHandler recordHandler2 = new RecordHandler();
            final Vector vector = new Vector();
            final int intValue = this.getCategory().get(0).get(14);
            for (int i = 0; i < this.getFailure().size(); ++i) {
                final Vector vector2 = this.getFailure().get(i);
                if ((int)vector2.get(6) != 8 && (int)vector2.get(6) != 15 && (int)vector2.get(6) != 16 && (int)vector2.get(6) != 21 && (int)vector2.get(6) != 34) {
                    recordHandler.addVector(this._$16400(new int[] { 5 }, vector2));
                    vector.add(this._$16400(new int[] { 5, 6, 9, 11 }, vector2));
                }
            }
            for (int j = 0; j < this.getTimeStamp().size(); ++j) {
                recordHandler2.addVector(this._$16400(new int[] { 5 }, (Vector)this.getTimeStamp().get(j)));
            }
            final Vector $9529 = new Vector<RecordHandler>();
            for (int k = 0; k < recordHandler.size(); ++k) {
                final RecordHandler e = new RecordHandler();
                e.add((Object)this.getDriver());
                e.add((Object)this.getTrainRunning());
                e.add((Object)this.getVehicle());
                e.add((Object)new Integer(intValue));
                e.addVector((Vector)vector.get(k));
                final int binarySearch = Arrays.binarySearch(recordHandler2.toArray(), recordHandler.get(k));
                e.add((Object)new Integer((int)this._$16400(new int[] { 6 }, (Vector)super.getTimeStamp().get((binarySearch >= 0) ? binarySearch : ((Math.abs(binarySearch) > recordHandler2.size()) ? (Math.abs(binarySearch) - 2) : (Math.abs(binarySearch) - 1)))).get(0) * 36 / 1000));
                $9529.add(e);
            }
            for (int l = 0; l < $9529.size(); ++l) {
                final int intValue2 = ((Vector<Integer>)$9529.get(l)).get(5);
                final int intValue3 = ((Vector<Integer>)$9529.get(l)).get(6);
                if (intValue2 == 9) {
                    if (intValue3 == 63) {
                        ((Vector)$9529.get(l)).setElementAt(new Integer(0), 6);
                        ((Vector<String>)$9529.get(l)).setElementAt("\u64cd\u4f5c\u9762\u76e4:\u5c1a\u672a\u6309\u78ba\u8a8d\u9215", 7);
                    }
                    else if (intValue3 == 6223) {
                        ((Vector)$9529.get(l)).setElementAt(new Integer(0), 6);
                        ((Vector<String>)$9529.get(l)).setElementAt("\u64cd\u4f5c\u9762\u76e4:\u5df2\u6309\u904e\u78ba\u8a8d\u9215", 7);
                    }
                }
            }
            this._$9529 = $9529;
        }
    }
    
    private void _$16438() throws Exception {
        if (super.getWaysideFailureTimes() != 0) {
            final RecordHandler recordHandler = new RecordHandler();
            final RecordHandler recordHandler2 = new RecordHandler();
            final RecordHandler recordHandler3 = new RecordHandler();
            final Vector vector = new Vector();
            for (int i = 0; i < super.getFailure().size(); ++i) {
                final Vector vector2 = super.getFailure().get(i);
                if ((int)vector2.get(6) == 8 || (int)vector2.get(6) == 15 || (int)vector2.get(6) == 16 || (int)vector2.get(6) == 21 || (int)vector2.get(6) == 34) {
                    recordHandler.addVector(this._$16400(new int[] { 5 }, vector2));
                    vector.add(this._$16400(new int[] { 5, 6, 9, 11 }, vector2));
                }
            }
            for (int j = 0; j < super.getBTM().size(); ++j) {
                recordHandler3.addVector(this._$16400(new int[] { 5 }, (Vector)super.getBTM().get(j)));
            }
            for (int k = 0; k < super.getTimeStamp().size(); ++k) {
                recordHandler2.addVector(this._$16400(new int[] { 5 }, (Vector)super.getTimeStamp().get(k)));
            }
            final Vector<RecordHandler> $16391 = new Vector<RecordHandler>();
            for (int l = 0; l < recordHandler.size(); ++l) {
                final RecordHandler e = new RecordHandler();
                e.add((Object)this.getDriver());
                e.add((Object)this.getTrainRunning());
                e.add((Object)this.getVehicle());
                e.addVector((Vector)vector.get(l));
                final int binarySearch = Arrays.binarySearch(recordHandler2.toArray(), recordHandler.get(l));
                e.add((Object)new Integer((int)this._$16400(new int[] { 6 }, (Vector)super.getTimeStamp().get((binarySearch >= 0) ? binarySearch : ((Math.abs(binarySearch) > recordHandler2.size()) ? (Math.abs(binarySearch) - 2) : (Math.abs(binarySearch) - 1)))).get(0) * 36 / 1000));
                final int binarySearch2 = Arrays.binarySearch(recordHandler3.toArray(), recordHandler.get(l));
                final int n = (binarySearch2 >= 0) ? binarySearch2 : ((Math.abs(binarySearch2) > recordHandler3.size()) ? (Math.abs(binarySearch2) - 2) : (Math.abs(binarySearch2) - 1));
                final int[] array = { 6 };
                e.addVector(new Vector());
                $16391.add(e);
            }
            this._$16391 = $16391;
        }
    }
    
    private Vector _$16424(final Vector vector) {
        if (vector == null) {
            return new Vector();
        }
        final Vector vector2 = new Vector();
        final Vector vector3 = new Vector();
        final Vector e = new Vector();
        e.add(new Integer(0));
        e.add(new Integer(0));
        vector3.add(e);
        int n = 0;
        for (int i = 0; i < vector.size(); ++i) {
            final Vector<Integer> e2 = new Vector<Integer>();
            final Vector vector4 = vector.get(i);
            final int intValue = (int)vector4.get(5);
            final int intValue2 = (int)vector4.get(6);
            e2.add(new Integer(intValue));
            e2.add(new Integer(intValue2));
            for (int j = 0; j < vector3.size(); ++j) {
                final Vector vector5 = vector3.get(j);
                final int intValue3 = (int)vector5.get(0);
                final int intValue4 = (int)vector5.get(1);
                if (intValue3 == intValue && intValue4 == intValue2) {
                    n = 1;
                    break;
                }
            }
            if (n != 0) {
                n = 0;
            }
            else {
                vector3.add(e2);
                vector2.add(vector.get(i));
            }
        }
        return vector2;
    }
    
    private Vector _$16415(final Vector vector) {
        if (vector == null) {
            return null;
        }
        final Vector vector2 = new Vector();
        long time = 0L;
        long time2 = 0L;
        long time3 = 0L;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        for (int i = 0; i < vector.size(); ++i) {
            final Vector vector3 = vector.get(i);
            if (((Date)vector3.get(3)).getTime() - time > 300000L) {
                n = 0;
                n2 = 0;
            }
            final int intValue = (int)vector3.get(13);
            final int intValue2 = (int)vector3.get(14);
            if (intValue != 0) {
                ++this._$16392;
            }
            if (n2 != 2 && intValue == 2 && time3 != ((Date)vector3.get(3)).getTime()) {
                ++this._$16394;
                vector2.add(vector3);
                n3 = 1;
                time3 = ((Date)vector3.get(3)).getTime();
            }
            if (intValue2 == 2) {
                ++this._$16393;
            }
            if (n != 2 && intValue2 == 2 && n3 == 0 && time2 != ((Date)vector3.get(3)).getTime()) {
                ++this._$16395;
                vector2.add(vector3);
                time2 = ((Date)vector3.get(3)).getTime();
            }
            n = intValue2;
            n2 = intValue;
            time = ((Date)vector3.get(3)).getTime();
            n3 = 0;
        }
        return vector2;
    }
    
    private Vector _$16432(final Vector vector) {
        final Vector vector2 = new Vector();
        final Vector vector3 = new Vector();
        final Vector e = new Vector();
        e.add(new Integer(0));
        e.add(new Integer(0));
        vector3.add(e);
        int n = 0;
        for (int i = 0; i < vector.size(); ++i) {
            final Vector<Integer> e2 = new Vector<Integer>();
            final Vector vector4 = vector.get(i);
            final int intValue = (int)vector4.get(4);
            final int intValue2 = (int)vector4.get(5);
            e2.add(new Integer(intValue));
            e2.add(new Integer(intValue2));
            for (int j = 0; j < vector3.size(); ++j) {
                final Vector vector5 = vector3.get(j);
                final int intValue3 = (int)vector5.get(0);
                final int intValue4 = (int)vector5.get(1);
                if (intValue3 == intValue && intValue4 == intValue2) {
                    n = 1;
                    break;
                }
            }
            if (n != 0) {
                n = 0;
            }
            else {
                vector3.add(e2);
                vector2.add(vector.get(i));
            }
        }
        return vector2;
    }
    
    public Vector getBehaviorFailure() {
        if (this._$16210 == null) {
            try {
                this._$16402();
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$16210;
    }
    
    public int getBehavior_ebTimes() {
        return this._$16392;
    }
    
    public int getBehavior_ebTimes_filter() {
        return this._$16394;
    }
    
    public int getBehavior_sbTimes() {
        return this._$16393;
    }
    
    public int getBehavior_sbTimes_filter() {
        return this._$16395;
    }
    
    public Vector getCabinFailure() {
        if (this._$9529 == null) {
            try {
                this._$16433();
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$9529;
    }
    
    public Vector getFilterBehaviorFailure() {
        return this._$16210 = this._$16415(this.getBehaviorFailure());
    }
    
    public Vector getFilterCabinFailure() {
        return this._$16424(this.getCabinFailure());
    }
    
    public Vector getFilterWaysideFailure() {
        return this._$16432(this.getWaysideFailure());
    }
    
    public Vector getWaysideFailure() {
        if (this._$16391 == null) {
            try {
                this._$16438();
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$16391;
    }
    
    private Vector _$16400(final int[] a, final Vector vector) {
        final Vector vector2 = (Vector)vector.clone();
        for (int i = vector.size() - 1; i >= 0; --i) {
            if (Arrays.binarySearch(a, i) < 0) {
                vector2.remove(i);
            }
        }
        return vector2;
    }
}
