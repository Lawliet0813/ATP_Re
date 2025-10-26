package core;

import java.util.Date;
import java.util.Vector;

public interface ATPGeneralData {
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
  
  void setBTM(Vector paramVector);
  
  void setCategory(Vector paramVector);
  
  void setDriverMessage(Vector paramVector);
  
  void setDynamic(Vector paramVector);
  
  void setFailure(Vector paramVector);
  
  void setStatus(Vector paramVector);
  
  void setTimeStamp(Vector paramVector);
  
  void setVDX(Vector paramVector);
}
