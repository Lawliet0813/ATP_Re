// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.core;

import java.util.Vector;
import com.MiTAC.TRA.ATP.Tools.PathHandler;
import java.util.Date;
import java.io.File;

public class ATPMission
{
    public static final int SourceFromDataBase = 2;
    public static final int SourceFromFile = 1;
    private int _$2931;
    private String _$2935;
    private File _$2246;
    private Date _$2932;
    private String _$2934;
    private String _$2936;
    private String _$2933;
    
    public ATPMission(final Date missionDate, final String workShiftNumber, final String trainRunningNumber, final String driverID, final String vehicleID) {
        this._$2931 = 1;
        this._$2932 = new Date();
        this._$2933 = "notInit";
        this._$2934 = "notInit";
        this._$2935 = "notInit";
        this._$2936 = "notInit";
        this._$2931 = 2;
        this.setMissionDate(missionDate);
        this.setWorkShiftNumber(workShiftNumber);
        this.setTrainRunningNumber(trainRunningNumber);
        this.setDriverID(driverID);
        this.setVehicleID(vehicleID);
    }
    
    public ATPMission(final File $2246) {
        this._$2931 = 1;
        this._$2932 = new Date();
        this._$2933 = "notInit";
        this._$2934 = "notInit";
        this._$2935 = "notInit";
        this._$2936 = "notInit";
        this._$2931 = 1;
        this._$2246 = $2246;
        try {
            final Vector decodePath = PathHandler.getDecodePath($2246.getAbsolutePath());
            this.setMissionDate((Date)decodePath.get(0));
            this.setWorkShiftNumber((String)decodePath.get(1));
            this.setTrainRunningNumber((String)decodePath.get(2));
            this.setDriverID((String)decodePath.get(3));
            this.setVehicleID((String)decodePath.get(4));
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Vector getATPMissionData() {
        final Vector vector = new Vector();
        vector.add(this._$2932);
        vector.add(this._$2933);
        vector.add(this._$2934);
        vector.add(this._$2935);
        vector.add(this._$2936);
        return vector;
    }
    
    public int getDataSource() {
        return this._$2931;
    }
    
    public String getDriver() {
        return this._$2935;
    }
    
    public File getFile() {
        if (this.getDataSource() == 2) {
            return null;
        }
        return this._$2246;
    }
    
    public Date getMissionDate() {
        return this._$2932;
    }
    
    public String getPath() {
        final Vector vector = new Vector();
        vector.add(this._$2932);
        vector.add(this._$2933);
        vector.add(this._$2934);
        vector.add(this._$2935);
        vector.add(this._$2936);
        return PathHandler.getEncodePath(vector);
    }
    
    public String getTrainRunning() {
        return this._$2934;
    }
    
    public String getVehicle() {
        return this._$2936;
    }
    
    public String getWorkShift() {
        return this._$2933;
    }
    
    public void setDriverID(final String $2935) {
        this._$2935 = $2935;
    }
    
    public void setMissionDate(final Date $2932) {
        this._$2932 = $2932;
    }
    
    public void setTrainRunningNumber(final String $2934) {
        this._$2934 = $2934;
    }
    
    public void setVehicleID(final String $2936) {
        this._$2936 = $2936;
    }
    
    public void setWorkShiftNumber(final String $2933) {
        this._$2933 = $2933;
    }
}
