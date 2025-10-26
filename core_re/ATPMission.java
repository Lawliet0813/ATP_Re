package core;

import com.MiTAC.TRA.ATP.Tools.PathHandler;
import java.io.File;
import java.util.Date;
import java.util.Vector;

public class ATPMission {
  public static final int SourceFromDataBase = 2;
  
  public static final int SourceFromFile = 1;
  
  private int _$2931 = 1;
  
  private String _$2935 = "notInit";
  
  private File _$2246;
  
  private Date _$2932 = new Date();
  
  private String _$2934 = "notInit";
  
  private String _$2936 = "notInit";
  
  private String _$2933 = "notInit";
  
  public ATPMission(Date paramDate, String paramString1, String paramString2, String paramString3, String paramString4) {
    this._$2931 = 2;
    setMissionDate(paramDate);
    setWorkShiftNumber(paramString1);
    setTrainRunningNumber(paramString2);
    setDriverID(paramString3);
    setVehicleID(paramString4);
  }
  
  public ATPMission(File paramFile) {
    this._$2931 = 1;
    this._$2246 = paramFile;
    try {
      Vector vector = PathHandler.getDecodePath(paramFile.getAbsolutePath());
      setMissionDate(vector.get(0));
      setWorkShiftNumber((String)vector.get(1));
      setTrainRunningNumber((String)vector.get(2));
      setDriverID((String)vector.get(3));
      setVehicleID((String)vector.get(4));
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public Vector getATPMissionData() {
    Vector vector = new Vector();
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
    return (getDataSource() == 2) ? null : this._$2246;
  }
  
  public Date getMissionDate() {
    return this._$2932;
  }
  
  public String getPath() {
    Vector vector = new Vector();
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
  
  public void setDriverID(String paramString) {
    this._$2935 = paramString;
  }
  
  public void setMissionDate(Date paramDate) {
    this._$2932 = paramDate;
  }
  
  public void setTrainRunningNumber(String paramString) {
    this._$2934 = paramString;
  }
  
  public void setVehicleID(String paramString) {
    this._$2936 = paramString;
  }
  
  public void setWorkShiftNumber(String paramString) {
    this._$2933 = paramString;
  }
}
