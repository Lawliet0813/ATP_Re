// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.core;

import java.util.Date;
import java.util.Vector;
import java.io.File;

public class ATPMissionUpdate extends ATPMission implements ATPGeneralData
{
    public ATPMissionUpdate(final File file) {
        super(file);
    }
    
    public Vector getBTM() {
        return null;
    }
    
    public int getCabinFailureTimes() {
        return 0;
    }
    
    public Vector getCategory() {
        return null;
    }
    
    public Vector getDriverMessage() {
        return null;
    }
    
    public Vector getDynamic() {
        return null;
    }
    
    public int getEBTimes() {
        return 0;
    }
    
    public Vector getFailure() {
        return null;
    }
    
    public int getMissionEndLocation() {
        return 0;
    }
    
    public Date getMissionEndTime() {
        return null;
    }
    
    public int getMissionStartLocation() {
        return 0;
    }
    
    public Date getMissionStartTime() {
        return null;
    }
    
    public int getSBTimes() {
        return 0;
    }
    
    public Vector getStatus() {
        return null;
    }
    
    public Vector getTimeStamp() {
        return null;
    }
    
    public Vector getVDX() {
        return null;
    }
    
    public int getWaysideFailureTimes() {
        return 0;
    }
    
    public boolean isDataExistsInDB() {
        return false;
    }
    
    public void setBTM(final Vector vector) {
    }
    
    public void setCategory(final Vector vector) {
    }
    
    public void setDriverMessage(final Vector vector) {
    }
    
    public void setDynamic(final Vector vector) {
    }
    
    public void setFailure(final Vector vector) {
    }
    
    public void setStatus(final Vector vector) {
    }
    
    public void setTimeStamp(final Vector vector) {
    }
    
    public void setVDX(final Vector vector) {
    }
}
