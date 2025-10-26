// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.core;

import java.util.Arrays;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.util.Date;
import java.util.Vector;

public class ATPMissionDetail extends ATPMissionGeneral
{
    private Vector _$18383;
    private Vector _$18380;
    private Vector _$18381;
    private Vector _$18382;
    private Vector _$4445;
    private Vector _$4500;
    private Vector _$18394;
    private Vector _$18393;
    private String _$18395;
    private Vector _$18384;
    private Vector _$12664;
    private Vector _$18188;
    private Vector _$18386;
    private Vector _$18389;
    private Vector _$18387;
    private Vector _$18385;
    private Vector _$18391;
    private Vector _$18390;
    private Vector _$18379;
    private Vector _$17042;
    private Vector _$16516;
    private Vector _$18388;
    private Vector _$1968;
    private Date _$18397;
    private Date _$18396;
    private Vector _$18392;
    
    public ATPMissionDetail(final Date date, final String s, final String s2, final String s3, final String s4, final Date date2, final Date date3) {
        super(date, s, s2, s3, s4);
        this._$18379 = new Vector();
        this._$18380 = new Vector();
        this._$18381 = new Vector();
        this._$16516 = new Vector();
        this._$18382 = new Vector();
        this._$4445 = new Vector();
        this._$4500 = new Vector();
        this._$18383 = new Vector();
        this._$1968 = new Vector();
        this._$18384 = new Vector();
        this._$18385 = new Vector();
        this._$12664 = new Vector();
        this._$18188 = new Vector();
        this._$18386 = new Vector();
        this._$18387 = new Vector();
        this._$18388 = new Vector();
        this._$18389 = new Vector();
        this._$18390 = new Vector();
        this._$18391 = new Vector();
        this._$18392 = new Vector();
        this._$17042 = new Vector();
        this._$18393 = new Vector();
        this._$18394 = new Vector();
        this.changeZoom(date2, date3);
    }
    
    public ATPMissionDetail(final Date date, final String s, final String s2, final String s3, final String s4) {
        super(date, s, s2, s3, s4);
        this._$18379 = new Vector();
        this._$18380 = new Vector();
        this._$18381 = new Vector();
        this._$16516 = new Vector();
        this._$18382 = new Vector();
        this._$4445 = new Vector();
        this._$4500 = new Vector();
        this._$18383 = new Vector();
        this._$1968 = new Vector();
        this._$18384 = new Vector();
        this._$18385 = new Vector();
        this._$12664 = new Vector();
        this._$18188 = new Vector();
        this._$18386 = new Vector();
        this._$18387 = new Vector();
        this._$18388 = new Vector();
        this._$18389 = new Vector();
        this._$18390 = new Vector();
        this._$18391 = new Vector();
        this._$18392 = new Vector();
        this._$17042 = new Vector();
        this._$18393 = new Vector();
        this._$18394 = new Vector();
        this.changeZoom(super.getMissionStartTime(), super.getMissionEndTime());
    }
    
    public ATPMissionDetail(final ATPMission atpMission, final Date date, final Date date2) {
        super(atpMission);
        this._$18379 = new Vector();
        this._$18380 = new Vector();
        this._$18381 = new Vector();
        this._$16516 = new Vector();
        this._$18382 = new Vector();
        this._$4445 = new Vector();
        this._$4500 = new Vector();
        this._$18383 = new Vector();
        this._$1968 = new Vector();
        this._$18384 = new Vector();
        this._$18385 = new Vector();
        this._$12664 = new Vector();
        this._$18188 = new Vector();
        this._$18386 = new Vector();
        this._$18387 = new Vector();
        this._$18388 = new Vector();
        this._$18389 = new Vector();
        this._$18390 = new Vector();
        this._$18391 = new Vector();
        this._$18392 = new Vector();
        this._$17042 = new Vector();
        this._$18393 = new Vector();
        this._$18394 = new Vector();
        this.changeZoom(date, date2);
    }
    
    public ATPMissionDetail(final ATPMission atpMission) {
        super(atpMission);
        this._$18379 = new Vector();
        this._$18380 = new Vector();
        this._$18381 = new Vector();
        this._$16516 = new Vector();
        this._$18382 = new Vector();
        this._$4445 = new Vector();
        this._$4500 = new Vector();
        this._$18383 = new Vector();
        this._$1968 = new Vector();
        this._$18384 = new Vector();
        this._$18385 = new Vector();
        this._$12664 = new Vector();
        this._$18188 = new Vector();
        this._$18386 = new Vector();
        this._$18387 = new Vector();
        this._$18388 = new Vector();
        this._$18389 = new Vector();
        this._$18390 = new Vector();
        this._$18391 = new Vector();
        this._$18392 = new Vector();
        this._$17042 = new Vector();
        this._$18393 = new Vector();
        this._$18394 = new Vector();
        this.changeZoom(super.getMissionStartTime(), super.getMissionEndTime());
    }
    
    public void changeZoom(final Date when, final Date when2) {
        this._$18396 = (when.before(when2) ? when : when2);
        this._$18397 = (when2.after(when) ? when2 : when);
        this._$18396 = (this._$18396.after(super.getMissionStartTime()) ? this._$18396 : super.getMissionStartTime());
        this._$18397 = (this._$18397.before(super.getMissionEndTime()) ? this._$18397 : super.getMissionEndTime());
        this._$18403();
        this._$18404();
    }
    
    public void changeZoom(final Date date) {
        this.changeZoom(date, this._$18397);
    }
    
    private void _$18403() {
        this._$18379.removeAllElements();
        this._$18380.removeAllElements();
        this._$18381.removeAllElements();
        this._$16516.removeAllElements();
        this._$18382.removeAllElements();
        this._$4445.removeAllElements();
        this._$4500.removeAllElements();
        this._$18383.removeAllElements();
        this._$1968.removeAllElements();
        this._$18384.removeAllElements();
        this._$18385.removeAllElements();
        this._$12664.removeAllElements();
        this._$18188.removeAllElements();
        this._$18386.removeAllElements();
        this._$18387.removeAllElements();
        this._$18388.removeAllElements();
        this._$18389.removeAllElements();
        this._$18390.removeAllElements();
        this._$18391.removeAllElements();
        this._$18392.removeAllElements();
        this._$17042.removeAllElements();
        this._$18393.removeAllElements();
        this._$18394.removeAllElements();
    }
    
    public Vector getAcceleration() {
        return this._$18380;
    }
    
    public Vector getAdhesion() {
        return this._$18381;
    }
    
    public Vector getBTM() {
        return this._$4445;
    }
    
    public Vector getBrakeTarget() {
        return this._$18382;
    }
    
    public Vector getCurve() {
        return this._$4500;
    }
    
    public Vector getDriverMessage_Ack_filter() {
        return this._$18394;
    }
    
    public Vector getDriverMessage_filter() {
        return this._$18393;
    }
    
    public String getDriverName() {
        if (this._$18395 == null) {
            this._$18395 = "";
            final ConnectDB connectDB = new ConnectDB();
            try {
                final String $18395 = connectDB.getData("SELECT Name FROM Driver_info WHERE Driver_ID = '" + this.getDriver() + "'").get(0).get(0);
                if ($18395.length() != 0) {
                    this._$18395 = $18395;
                }
                else {
                    this._$18395 = "-";
                }
            }
            catch (final Exception ex) {
                this._$18395 = "x";
            }
        }
        return this._$18395;
    }
    
    public Vector getEmerBrake() {
        return this._$18384;
    }
    
    public Vector getGradient() {
        return this._$18383;
    }
    
    public Vector getLocation() {
        return this._$12664;
    }
    
    public Vector getMode() {
        return this._$18188;
    }
    
    public Vector getPermittedSpeed() {
        return this._$18386;
    }
    
    public Vector getPipePressure() {
        return this._$18389;
    }
    
    public Vector getReleaseSpeed() {
        return this._$18387;
    }
    
    public Vector getServiceBrake() {
        return this._$18385;
    }
    
    public Vector getSlide() {
        return this._$18391;
    }
    
    public Vector getSlip() {
        return this._$18390;
    }
    
    public Vector getSpeed() {
        return this._$18379;
    }
    
    public Vector getStopStation() {
        return this._$17042;
    }
    
    public Vector getTarget() {
        return this._$16516;
    }
    
    public Vector getTargetSpeed() {
        return this._$18388;
    }
    
    public Vector getTrainData() {
        return this._$1968;
    }
    
    public Date getUserDefineEndTime() {
        return this._$18397;
    }
    
    public Date getUserDefineStartTime() {
        return this._$18396;
    }
    
    public Vector getWarning() {
        return this._$18392;
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
    
    private void _$18404() {
        int size = super.getTimeStamp().size();
        int index = 0;
        Date $18396 = this._$18396;
        for (int i = 0; i < super.getTimeStamp().size(); ++i) {
            final Vector vector = super.getTimeStamp().get(i);
            final Date date = (Date)vector.get(5);
            if (date.after(this._$18396) && date.before(this._$18397)) {
                size = ((i < size) ? i : size);
                index = ((i > index) ? i : index);
                final Vector $18397 = this._$16400(new int[] { 5, 6 }, vector);
                if (date.getTime() - $18396.getTime() > 10000L) {
                    final Vector<Date> e = new Vector<Date>();
                    e.add(new Date($18396.getTime() + 1000L));
                    e.add((Date)new Integer(0));
                    this._$18379.add(e);
                    final Vector<Date> e2 = new Vector<Date>();
                    e2.add(new Date(date.getTime() - 1000L));
                    e2.add((Date)new Integer(0));
                    this._$18379.add(e2);
                }
                this._$18379.add($18397);
                final Vector $18398 = this._$16400(new int[] { 5, 7 }, vector);
                if (date.getTime() - $18396.getTime() > 10000L) {
                    final Vector<Date> e3 = new Vector<Date>();
                    e3.add(new Date($18396.getTime() + 1000L));
                    e3.add((Date)new Integer(0));
                    this._$12664.add(e3);
                    final Vector<Date> e4 = new Vector<Date>();
                    e4.add(new Date(date.getTime() - 1000L));
                    e4.add((Date)new Integer(0));
                    this._$12664.add(e4);
                }
                this._$12664.add($18398);
                $18396 = date;
            }
        }
        if (super.getTimeStamp().size() != 0) {
            final int index2 = (size - 1 < 0) ? 0 : (size - 1);
            final Vector vector2 = super.getTimeStamp().get(index2);
            this._$18379.insertElementAt(this._$16400(new int[] { 5, 6 }, vector2), 0);
            this._$12664.insertElementAt(this._$16400(new int[] { 5, 7 }, vector2), 0);
            final Vector vector3 = super.getTimeStamp().get(index);
            final Date date2 = this._$18379.lastElement().get(0);
            final Date date3 = (Date)vector3.get(0);
            if (date2.getTime() != date3.getTime()) {
                this._$18379.add(this._$16400(new int[] { 5, 6 }, vector3));
            }
            if (this._$12664.lastElement().get(0).getTime() != date3.getTime()) {
                this._$12664.add(this._$16400(new int[] { 5, 7 }, vector3));
            }
            System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
            System.out.println("\u539f\u59cb\u5927\u5c0f: TimeStamp size > " + (index - index2));
            System.out.println("  \u8abf\u6574\u5f8c: speed > " + this._$18379.size());
            System.out.println("       : location > " + this._$12664.size());
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        }
        int size2 = super.getDynamic().size();
        int index3 = 0;
        int n = -100;
        int n2 = -100;
        int n3 = -100;
        int n4 = -100;
        int n5 = -100;
        int n6 = -100;
        int n7 = -100;
        int n8 = -100;
        for (int j = 0; j < super.getDynamic().size(); ++j) {
            final Vector vector4 = super.getDynamic().get(j);
            final Date date4 = (Date)vector4.get(5);
            if (date4.after(this._$18396) && date4.before(this._$18397)) {
                size2 = ((j < size2) ? j : size2);
                index3 = ((j > index3) ? j : index3);
                final Vector $18399 = this._$16400(new int[] { 5, 6 }, vector4);
                final int intValue = $18399.get(1);
                if (n != intValue) {
                    this._$18380.add($18399);
                    n = intValue;
                }
                final Vector $18400 = this._$16400(new int[] { 5, 7 }, vector4);
                final int intValue2 = $18400.get(1);
                if (n2 != intValue2) {
                    this._$16516.add($18400);
                    this._$18382.add(this._$16400(new int[] { 5, 8 }, vector4));
                    n2 = intValue2;
                }
                final Vector $18401 = this._$16400(new int[] { 5, 9 }, vector4);
                final int intValue3 = $18401.get(1);
                if (n3 != intValue3) {
                    this._$18388.add($18401);
                    n3 = intValue3;
                }
                final Vector $18402 = this._$16400(new int[] { 5, 10 }, vector4);
                final int intValue4 = $18402.get(1);
                if (n4 != intValue4) {
                    this._$18386.add($18402);
                    n4 = intValue4;
                }
                final Vector $18403 = this._$16400(new int[] { 5, 11 }, vector4);
                final int intValue5 = $18403.get(1);
                if (n5 != intValue5) {
                    this._$18387.add($18403);
                    n5 = intValue5;
                }
                final Vector $18404 = this._$16400(new int[] { 5, 12 }, vector4);
                final int intValue6 = $18404.get(1);
                if (n6 != intValue6) {
                    this._$18392.add($18404);
                    n6 = intValue6;
                }
                final Vector $18405 = this._$16400(new int[] { 5, 13 }, vector4);
                final int intValue7 = $18405.get(1);
                if (n7 != intValue7) {
                    this._$18390.add($18405);
                    n7 = intValue7;
                }
                final Vector $18406 = this._$16400(new int[] { 5, 14 }, vector4);
                final int intValue8 = $18406.get(1);
                if (n8 != intValue8) {
                    this._$18391.add($18406);
                    n8 = intValue8;
                }
            }
        }
        if (super.getDynamic().size() != 0) {
            final int index4 = (size2 - 1 < 0) ? 0 : (size2 - 1);
            final Vector vector5 = super.getDynamic().get(index4);
            this._$18380.insertElementAt(this._$16400(new int[] { 5, 6 }, vector5), 0);
            this._$16516.insertElementAt(this._$16400(new int[] { 5, 7 }, vector5), 0);
            this._$18382.insertElementAt(this._$16400(new int[] { 5, 8 }, vector5), 0);
            this._$18388.insertElementAt(this._$16400(new int[] { 5, 9 }, vector5), 0);
            this._$18386.insertElementAt(this._$16400(new int[] { 5, 10 }, vector5), 0);
            this._$18387.insertElementAt(this._$16400(new int[] { 5, 11 }, vector5), 0);
            this._$18392.insertElementAt(this._$16400(new int[] { 5, 12 }, vector5), 0);
            this._$18390.insertElementAt(this._$16400(new int[] { 5, 13 }, vector5), 0);
            this._$18391.insertElementAt(this._$16400(new int[] { 5, 14 }, vector5), 0);
            final Vector vector6 = super.getDynamic().get(index3);
            final Date date5 = (Date)vector6.get(0);
            if (this._$18380.lastElement().get(0).getTime() != date5.getTime()) {
                this._$18380.add(this._$16400(new int[] { 5, 6 }, vector6));
            }
            if (this._$16516.lastElement().get(0).getTime() != date5.getTime()) {
                this._$16516.add(this._$16400(new int[] { 5, 7 }, vector6));
            }
            if (this._$18382.lastElement().get(0).getTime() != date5.getTime()) {
                this._$18382.add(this._$16400(new int[] { 5, 8 }, vector6));
            }
            if (this._$18388.lastElement().get(0).getTime() != date5.getTime()) {
                this._$18388.add(this._$16400(new int[] { 5, 9 }, vector6));
            }
            if (this._$18386.lastElement().get(0).getTime() != date5.getTime()) {
                this._$18386.add(this._$16400(new int[] { 5, 10 }, vector6));
            }
            if (this._$18387.lastElement().get(0).getTime() != date5.getTime()) {
                this._$18387.add(this._$16400(new int[] { 5, 11 }, vector6));
            }
            if (this._$18392.lastElement().get(0).getTime() != date5.getTime()) {
                this._$18392.add(this._$16400(new int[] { 5, 12 }, vector6));
            }
            if (this._$18390.lastElement().get(0).getTime() != date5.getTime()) {
                this._$18390.add(this._$16400(new int[] { 5, 13 }, vector6));
            }
            if (this._$18391.lastElement().get(0).getTime() != date5.getTime()) {
                this._$18391.add(this._$16400(new int[] { 5, 14 }, vector6));
            }
            System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
            System.out.println("\u539f\u59cb\u5927\u5c0f: Dynamic size > " + (index3 - index4));
            System.out.println("  \u8abf\u6574\u5f8c: acceleration > " + this._$18380.size());
            System.out.println("       : target > " + this._$16516.size());
            System.out.println("       : brakeTarget > " + this._$18382.size());
            System.out.println("       : targetSpeed > " + this._$18388.size());
            System.out.println("       : permittedSpeed > " + this._$18386.size());
            System.out.println("       : releaseSpeed > " + this._$18387.size());
            System.out.println("       : warn > " + this._$18392.size());
            System.out.println("       : slip > " + this._$18390.size());
            System.out.println("       : slide > " + this._$18391.size());
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        }
        int size3 = super.getStatus().size();
        int index5 = 0;
        int n9 = -100;
        int n10 = -100;
        int n11 = -100;
        int n12 = -100;
        for (int k = 0; k < super.getStatus().size(); ++k) {
            final Vector vector7 = super.getStatus().get(k);
            final Date date6 = (Date)vector7.get(5);
            if (date6.before(this._$18396)) {
                size3 = k;
            }
            if (date6.before(this._$18397)) {
                index5 = k;
            }
            if (date6.after(this._$18396) && date6.before(this._$18397)) {
                size3 = ((k < size3) ? k : size3);
                index5 = ((k > index5) ? k : index5);
                final Vector $18407 = this._$16400(new int[] { 5, 6 }, vector7);
                final int intValue9 = $18407.get(1);
                if (n9 != intValue9) {
                    this._$18381.add($18407);
                    n9 = intValue9;
                }
                final Vector $18408 = this._$16400(new int[] { 5, 7 }, vector7);
                final int intValue10 = $18408.get(1);
                if (n10 != intValue10) {
                    this._$18188.add($18408);
                    n10 = intValue10;
                }
                final Vector $18409 = this._$16400(new int[] { 5, 8 }, vector7);
                final int intValue11 = $18409.get(1);
                if (n11 != intValue11) {
                    this._$18384.add($18409);
                    n11 = intValue11;
                }
                final Vector $18410 = this._$16400(new int[] { 5, 9 }, vector7);
                final int intValue12 = $18410.get(1);
                if (n12 != intValue12) {
                    this._$18385.add($18410);
                    n12 = intValue12;
                }
            }
        }
        if (super.getStatus().size() != 0) {
            final int index6 = (size3 - 1 < 0) ? 0 : (size3 - 1);
            final Vector vector8 = super.getStatus().get(index6);
            this._$18381.insertElementAt(this._$16400(new int[] { 5, 6 }, vector8), 0);
            this._$18188.insertElementAt(this._$16400(new int[] { 5, 7 }, vector8), 0);
            this._$18384.insertElementAt(this._$16400(new int[] { 5, 8 }, vector8), 0);
            this._$18385.insertElementAt(this._$16400(new int[] { 5, 9 }, vector8), 0);
            final Vector vector9 = super.getStatus().get(index5);
            final Date date7 = this._$18381.lastElement().get(0);
            final Date date8 = (Date)vector9.get(0);
            if (date7.getTime() != date8.getTime()) {
                this._$18381.add(this._$16400(new int[] { 5, 6 }, vector9));
            }
            if (this._$18188.lastElement().get(0).getTime() != date8.getTime()) {
                this._$18188.add(this._$16400(new int[] { 5, 7 }, vector9));
            }
            if (this._$18384.lastElement().get(0).getTime() != date8.getTime()) {
                this._$18384.add(this._$16400(new int[] { 5, 8 }, vector9));
            }
            if (this._$18385.lastElement().get(0).getTime() != date8.getTime()) {
                this._$18385.add(this._$16400(new int[] { 5, 9 }, vector9));
            }
            System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
            System.out.println("\u539f\u59cb\u5927\u5c0f: Status size > " + (index5 - index6));
            System.out.println("  \u8abf\u6574\u5f8c: adhesion > " + this._$18381.size());
            System.out.println("       : mode > " + this._$18188.size());
            System.out.println("       : emerBrake > " + this._$18384.size());
            System.out.println("       : serviceBrake > " + this._$18385.size());
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        }
        for (int l = 0; l < super.getBTM().size(); ++l) {
            final Vector e5 = super.getBTM().get(l);
            final Date date9 = (Date)e5.get(5);
            if (date9.after(this._$18396) && date9.before(this._$18397)) {
                this._$4445.add(e5);
            }
        }
        for (int index7 = 0; index7 < super.getDriverMessage().size(); ++index7) {
            final Vector vector10 = super.getDriverMessage().get(index7);
            final Date date10 = (Date)vector10.get(5);
            if (date10.after(this._$18396) && date10.before(this._$18397) && this._$16400(new int[] { 5, 9 }, vector10).get(1) >= 60000) {
                this._$17042.add(this._$16400(new int[] { 5, 9 }, vector10));
            }
        }
        for (int index8 = 0; index8 < super.getDriverMessage().size(); ++index8) {
            final Vector vector11 = super.getDriverMessage().get(index8);
            final Date date11 = (Date)vector11.get(5);
            if (date11.after(this._$18396) && date11.before(this._$18397)) {
                this._$18393.add(this._$16400(new int[] { 5, 6, 7, 8, 9 }, vector11));
            }
        }
        for (int index9 = 0; index9 < super.getDriverMessage_Ack().size(); ++index9) {
            final Vector vector12 = super.getDriverMessage_Ack().get(index9);
            final Date date12 = (Date)vector12.get(5);
            if (date12.after(this._$18396) && date12.before(this._$18397)) {
                this._$18394.add(this._$16400(new int[] { 5, 6, 7 }, vector12));
            }
        }
        int size4 = super.getVDX().size();
        int index10 = 0;
        int n13 = -100;
        for (int index11 = 0; index11 < super.getVDX().size(); ++index11) {
            final Vector vector13 = super.getVDX().get(index11);
            final Date date13 = (Date)vector13.get(5);
            if (date13.after(this._$18396) && date13.before(this._$18397)) {
                size4 = ((index11 < size4) ? index11 : size4);
                index10 = ((index11 > index10) ? index11 : index10);
                final Vector $18411 = this._$16400(new int[] { 5, 6 }, vector13);
                final int intValue13 = $18411.get(1);
                if (n13 != intValue13) {
                    this._$18389.add($18411);
                    n13 = intValue13;
                }
            }
        }
        if (super.getVDX().size() != 0) {
            size4 = ((size4 - 1 < 0) ? 0 : (size4 - 1));
            this._$18389.insertElementAt(this._$16400(new int[] { 5, 6 }, (Vector)super.getVDX().get(size4)), 0);
            final Vector vector14 = super.getVDX().get(index10);
            if (this._$18389.lastElement().get(0).getTime() != ((Date)vector14.get(0)).getTime()) {
                this._$18389.add(this._$16400(new int[] { 5, 6 }, vector14));
            }
        }
        System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        System.out.println("\u539f\u59cb\u5927\u5c0f: VDX size > " + (index10 - size4));
        System.out.println("  \u8abf\u6574\u5f8c: pipePressure > " + this._$18389.size());
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        for (int index12 = 0; index12 < super.getTrainData().size(); ++index12) {
            final Vector e6 = super.getTrainData().get(index12);
            final Date date14 = (Date)e6.get(5);
            if (date14.after(this._$18396) && date14.before(this._$18397)) {
                this._$1968.add(e6);
            }
        }
        try {
            this._$18383 = new Vector();
            final Vector gradientToDraw = super.conn.getGradientToDraw((ATPMission)this);
            for (int index13 = 0; index13 < gradientToDraw.size(); ++index13) {
                final Vector e7 = gradientToDraw.get(index13);
                final Date date15 = (Date)e7.get(0);
                if (date15.after(this._$18396) && date15.before(this._$18397)) {
                    this._$18383.add(e7);
                }
            }
            this._$4500 = new Vector();
            final Vector curveToDraw = super.conn.getCurveToDraw((ATPMission)this);
            for (int index14 = 0; index14 < curveToDraw.size(); ++index14) {
                final Vector e8 = curveToDraw.get(index14);
                final Date date16 = (Date)e8.get(0);
                if (date16.after(this._$18396) && date16.before(this._$18397)) {
                    this._$4500.add(e8);
                }
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        this._$18451(this._$18386);
        this._$18451(this._$18387);
        this._$18451(this._$18388);
        this._$18451(this._$18379);
        this._$18452(this._$16516);
        this._$18452(this._$18382);
    }
    
    private Vector _$18451(final Vector vector) {
        for (int i = 0; i < vector.size(); ++i) {
            final Vector vector2 = vector.get(i);
            int intValue = (int)vector2.get(1);
            if (intValue != 0) {
                if (intValue < 0) {
                    intValue = -1;
                }
                else {
                    intValue = intValue * 36 / 1000;
                    if (intValue == 0) {
                        intValue = 1;
                    }
                }
            }
            vector2.remove(1);
            vector2.add(new Integer(intValue));
        }
        return vector;
    }
    
    private Vector _$18452(final Vector vector) {
        for (int i = 0; i < vector.size(); ++i) {
            final Vector vector2 = vector.get(i);
            final int intValue = (int)vector2.get(1);
            int value;
            if (intValue < 0) {
                value = -1;
            }
            else {
                value = intValue / 100;
            }
            vector2.remove(1);
            vector2.add(new Integer(value));
        }
        return vector;
    }
}
