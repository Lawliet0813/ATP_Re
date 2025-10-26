package core;

import com.MiTAC.TRA.ATP.core.ATPGeneralData;
import com.MiTAC.TRA.ATP.core.ATPMission;
import java.io.File;
import java.util.Date;
import java.util.Vector;

public class ATPMissionUpdate extends ATPMission implements ATPGeneralData {
  public ATPMissionUpdate(File paramFile) {
    super(paramFile);
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
  
  public void setBTM(Vector paramVector) {}
  
  public void setCategory(Vector paramVector) {}
  
  public void setDriverMessage(Vector paramVector) {}
  
  public void setDynamic(Vector paramVector) {}
  
  public void setFailure(Vector paramVector) {}
  
  public void setStatus(Vector paramVector) {}
  
  public void setTimeStamp(Vector paramVector) {}
  
  public void setVDX(Vector paramVector) {}
}
