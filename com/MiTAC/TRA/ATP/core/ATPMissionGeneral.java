// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.core;

import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import com.MiTAC.TRA.ATP.Tools.SortTable.ColumnComparator;
import java.io.File;
import java.util.Date;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import java.util.Vector;

public class ATPMissionGeneral extends ATPMission
{
    private Vector _$3769;
    private Vector _$3773;
    private Integer _$3785;
    private Vector _$3770;
    protected ConnectDB conn;
    private Vector _$3767;
    private Vector _$3771;
    private Vector _$3772;
    private Vector _$3774;
    private Integer _$3784;
    private Integer _$3782;
    private Date _$3780;
    private Vector _$3775;
    private Vector _$3768;
    private Integer _$3783;
    private Integer _$3781;
    private Date _$3779;
    private Vector _$3776;
    private Vector _$3777;
    private Vector _$2355;
    private Vector _$3778;
    private Integer _$3786;
    
    public ATPMissionGeneral(final File file) {
        super(file);
        this.conn = new ConnectDB();
    }
    
    public ATPMissionGeneral(final Date date, final String s, final String s2, final String s3, final String s4) {
        super(date, s, s2, s3, s4);
        this.conn = new ConnectDB();
    }
    
    public ATPMissionGeneral(final ATPMission atpMission) {
        super(atpMission.getMissionDate(), atpMission.getWorkShift(), atpMission.getTrainRunning(), atpMission.getDriver(), atpMission.getVehicle());
        this.conn = new ConnectDB();
    }
    
    public Vector getBTM() {
        if (this._$3769 == null && this.getDataSource() == 2) {
            try {
                this.setBTM(this.conn.getBTM((ATPMission)this));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$3769;
    }
    
    public Vector getBtnEvent() {
        if (this._$3773 == null && this.getDataSource() == 2) {
            try {
                this.setDriverMessage(this.conn.getBtnEvent((ATPMission)this));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$3773;
    }
    
    public int getCabinFailureTimes() {
        if (this._$3785 == null) {
            this._$3822();
        }
        return this._$3785;
    }
    
    public Vector getCategory() {
        if (this._$3770 == null && this.getDataSource() == 2) {
            try {
                this.setCategory(this.conn.getCategory((ATPMission)this));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$3770;
    }
    
    public Vector getCurve() {
        if (this._$3767 == null && this.getDataSource() == 2) {
            try {
                this.setCurve(this.conn.getCurve((ATPMission)this));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$3767;
    }
    
    public Vector getDriverMessage() {
        if (this._$3771 == null && this.getDataSource() == 2) {
            try {
                this.setDriverMessage(this.conn.getDriverMessage((ATPMission)this));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$3771;
    }
    
    public Vector getDriverMessage_Ack() {
        if (this._$3772 == null && this.getDataSource() == 2) {
            try {
                this.setDriverMessage_Ack(this.conn.getDriverMessage_Ack((ATPMission)this));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$3772;
    }
    
    public Vector getDynamic() {
        if (this._$3774 == null && this.getDataSource() == 2) {
            try {
                this.setDynamic(this.conn.getDynamic((ATPMission)this));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$3774;
    }
    
    public int getEBTimes() {
        if (this._$3784 == null) {
            this._$3822();
        }
        return this._$3784;
    }
    
    private void _$3822() {
        final Vector vector = this.getCategory().get(0);
        this._$3784 = new Integer(this.getStatus().size());
        this._$3783 = new Integer(this.getStatus().size());
        this._$3785 = (Integer)vector.get(22);
        this._$3786 = (Integer)vector.get(21);
    }
    
    public Vector getFailure() {
        if (this._$3775 == null && this.getDataSource() == 2) {
            try {
                this.setFailure(this.conn.getFailure((ATPMission)this));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$3775;
    }
    
    public Vector getGradient() {
        if (this._$3768 == null && this.getDataSource() == 2) {
            try {
                this.setGradient(this.conn.getGradient((ATPMission)this));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$3768;
    }
    
    public int getMissionEndLocation() {
        if (this._$3782 == null) {
            this._$3789();
        }
        return this._$3782;
    }
    
    public Date getMissionEndTime() {
        if (this._$3780 == null) {
            this._$3789();
        }
        return this._$3780;
    }
    
    public int getMissionStartLocation() {
        if (this._$3781 == null) {
            this._$3789();
        }
        return this._$3781;
    }
    
    public Date getMissionStartTime() {
        if (this._$3779 == null) {
            this._$3789();
        }
        return this._$3779;
    }
    
    public int getSBTimes() {
        if (this._$3783 == null) {
            this._$3822();
        }
        return this._$3783;
    }
    
    private void _$3789() {
        if (this.getTimeStamp().size() != 0) {
            Collections.sort((List<Object>)this.getTimeStamp(), (Comparator<? super Object>)new ColumnComparator(7, true));
            this._$3781 = this.getTimeStamp().get(0).get(7);
            this._$3782 = this.getTimeStamp().lastElement().get(7);
            Collections.sort((List<Object>)this.getTimeStamp(), (Comparator<? super Object>)new ColumnComparator(5, true));
            for (int i = 0; i < this.getTimeStamp().size(); ++i) {
                if (((Vector<Date>)this.getTimeStamp().get(i)).get(5).after(this.getMissionDate())) {
                    this._$3779 = ((Vector<Date>)this.getTimeStamp().get(i)).get(5);
                    break;
                }
            }
            this._$3779 = new Date(this._$3779.getTime() - 10000L);
            this._$3780 = this.getTimeStamp().lastElement().get(5);
        }
    }
    
    public Vector getStatus() {
        if (this._$3776 == null && this.getDataSource() == 2) {
            try {
                this.setStatus(this.conn.getStatus((ATPMission)this));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$3776;
    }
    
    public Vector getTimeStamp() {
        if (this._$3777 == null && this.getDataSource() == 2) {
            try {
                this.setTimeStamp(this.conn.getTS((ATPMission)this));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$3777;
    }
    
    public Vector getTrainData() {
        if (this._$2355 == null && this.getDataSource() == 2) {
            try {
                this.setTrainData(this.conn.getTrainData((ATPMission)this));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$2355;
    }
    
    public Vector getVDX() {
        if (this._$3778 == null && this.getDataSource() == 2) {
            try {
                this.setVDX(this.conn.getVDX((ATPMission)this));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return this._$3778;
    }
    
    public int getWaysideFailureTimes() {
        if (this._$3786 == null) {
            this._$3822();
        }
        return this._$3786;
    }
    
    public boolean isDataExistsInDB() {
        return false;
    }
    
    public void setBTM(final Vector $3769) {
        this._$3769 = $3769;
    }
    
    public void setBtnEvent(final Vector $3773) {
        this._$3773 = $3773;
    }
    
    public void setCategory(final Vector $3770) {
        this._$3770 = $3770;
    }
    
    public void setCurve(final Vector $3767) {
        this._$3767 = $3767;
    }
    
    public void setDriverMessage(final Vector $3771) {
        this._$3771 = $3771;
    }
    
    public void setDriverMessage_Ack(final Vector $3772) {
        this._$3772 = $3772;
    }
    
    public void setDynamic(final Vector $3774) {
        this._$3774 = $3774;
    }
    
    public void setFailure(final Vector $3775) {
        this._$3775 = $3775;
    }
    
    public void setGradient(final Vector $3768) {
        this._$3768 = $3768;
    }
    
    public void setStatus(final Vector $3776) {
        this._$3776 = $3776;
    }
    
    public void setTimeStamp(final Vector $3777) {
        this._$3777 = $3777;
    }
    
    public void setTrainData(final Vector $2355) {
        this._$2355 = $2355;
    }
    
    public void setVDX(final Vector $3778) {
        this._$3778 = $3778;
    }
}
