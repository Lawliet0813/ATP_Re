// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.core;

import java.util.Date;
import java.util.Vector;

public interface ATPGeneralData
{
    Vector getBTM();
    
    int getCabinFailureTimes();
    
    Vector getCategory();
    
    Vector getDriverMessage();
    
    Vector getDynamic();
    
    int getEBTimes();
    
    Vector getFailure();
    
    int getMissionEndLocation();
    
    Date getMissionEndTime();
    
    int getMissionStartLocation();
    
    Date getMissionStartTime();
    
    int getSBTimes();
    
    Vector getStatus();
    
    Vector getTimeStamp();
    
    Vector getVDX();
    
    int getWaysideFailureTimes();
    
    boolean isDataExistsInDB();
    
    void setBTM(final Vector p0);
    
    void setCategory(final Vector p0);
    
    void setDriverMessage(final Vector p0);
    
    void setDynamic(final Vector p0);
    
    void setFailure(final Vector p0);
    
    void setStatus(final Vector p0);
    
    void setTimeStamp(final Vector p0);
    
    void setVDX(final Vector p0);
}
