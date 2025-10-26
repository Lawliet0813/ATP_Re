package core;

import com.MiTAC.TRA.ATP.Tools.SortTable.ColumnComparator;
import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.core.ATPMission;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

public class ATPMissionGeneral extends ATPMission {
  private Vector _$3769;
  
  private Vector _$3773;
  
  private Integer _$3785;
  
  private Vector _$3770;
  
  protected ConnectDB conn = new ConnectDB();
  
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
  
  public ATPMissionGeneral(File paramFile) {
    super(paramFile);
  }
  
  public ATPMissionGeneral(Date paramDate, String paramString1, String paramString2, String paramString3, String paramString4) {
    super(paramDate, paramString1, paramString2, paramString3, paramString4);
  }
  
  public ATPMissionGeneral(ATPMission paramATPMission) {
    super(paramATPMission.getMissionDate(), paramATPMission.getWorkShift(), paramATPMission.getTrainRunning(), paramATPMission.getDriver(), paramATPMission.getVehicle());
  }
  
  public Vector getBTM() {
    if (this._$3769 == null && getDataSource() == 2)
      try {
        setBTM(this.conn.getBTM(this));
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$3769;
  }
  
  public Vector getBtnEvent() {
    if (this._$3773 == null && getDataSource() == 2)
      try {
        setDriverMessage(this.conn.getBtnEvent(this));
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$3773;
  }
  
  public int getCabinFailureTimes() {
    if (this._$3785 == null)
      _$3822(); 
    return this._$3785.intValue();
  }
  
  public Vector getCategory() {
    if (this._$3770 == null && getDataSource() == 2)
      try {
        setCategory(this.conn.getCategory(this));
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$3770;
  }
  
  public Vector getCurve() {
    if (this._$3767 == null && getDataSource() == 2)
      try {
        setCurve(this.conn.getCurve(this));
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$3767;
  }
  
  public Vector getDriverMessage() {
    if (this._$3771 == null && getDataSource() == 2)
      try {
        setDriverMessage(this.conn.getDriverMessage(this));
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$3771;
  }
  
  public Vector getDriverMessage_Ack() {
    if (this._$3772 == null && getDataSource() == 2)
      try {
        setDriverMessage_Ack(this.conn.getDriverMessage_Ack(this));
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$3772;
  }
  
  public Vector getDynamic() {
    if (this._$3774 == null && getDataSource() == 2)
      try {
        setDynamic(this.conn.getDynamic(this));
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$3774;
  }
  
  public int getEBTimes() {
    if (this._$3784 == null)
      _$3822(); 
    return this._$3784.intValue();
  }
  
  private void _$3822() {
    Vector vector = getCategory().get(0);
    this._$3784 = new Integer(getStatus().size());
    this._$3783 = new Integer(getStatus().size());
    this._$3785 = vector.get(22);
    this._$3786 = vector.get(21);
  }
  
  public Vector getFailure() {
    if (this._$3775 == null && getDataSource() == 2)
      try {
        setFailure(this.conn.getFailure(this));
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$3775;
  }
  
  public Vector getGradient() {
    if (this._$3768 == null && getDataSource() == 2)
      try {
        setGradient(this.conn.getGradient(this));
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$3768;
  }
  
  public int getMissionEndLocation() {
    if (this._$3782 == null)
      _$3789(); 
    return this._$3782.intValue();
  }
  
  public Date getMissionEndTime() {
    if (this._$3780 == null)
      _$3789(); 
    return this._$3780;
  }
  
  public int getMissionStartLocation() {
    if (this._$3781 == null)
      _$3789(); 
    return this._$3781.intValue();
  }
  
  public Date getMissionStartTime() {
    if (this._$3779 == null)
      _$3789(); 
    return this._$3779;
  }
  
  public int getSBTimes() {
    if (this._$3783 == null)
      _$3822(); 
    return this._$3783.intValue();
  }
  
  private void _$3789() {
    if (getTimeStamp().size() != 0) {
      Collections.sort(getTimeStamp(), (Comparator)new ColumnComparator(7, true));
      this._$3781 = ((Vector)getTimeStamp().get(0)).get(7);
      this._$3782 = ((Vector)getTimeStamp().lastElement()).get(7);
      Collections.sort(getTimeStamp(), (Comparator)new ColumnComparator(5, true));
      for (byte b = 0; b < getTimeStamp().size(); b++) {
        if (((Date)((Vector)getTimeStamp().get(b)).get(5)).after(getMissionDate())) {
          this._$3779 = ((Vector)getTimeStamp().get(b)).get(5);
          break;
        } 
      } 
      this._$3779 = new Date(this._$3779.getTime() - 10000L);
      this._$3780 = ((Vector)getTimeStamp().lastElement()).get(5);
    } 
  }
  
  public Vector getStatus() {
    if (this._$3776 == null && getDataSource() == 2)
      try {
        setStatus(this.conn.getStatus(this));
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$3776;
  }
  
  public Vector getTimeStamp() {
    if (this._$3777 == null && getDataSource() == 2)
      try {
        setTimeStamp(this.conn.getTS(this));
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$3777;
  }
  
  public Vector getTrainData() {
    if (this._$2355 == null && getDataSource() == 2)
      try {
        setTrainData(this.conn.getTrainData(this));
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$2355;
  }
  
  public Vector getVDX() {
    if (this._$3778 == null && getDataSource() == 2)
      try {
        setVDX(this.conn.getVDX(this));
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$3778;
  }
  
  public int getWaysideFailureTimes() {
    if (this._$3786 == null)
      _$3822(); 
    return this._$3786.intValue();
  }
  
  public boolean isDataExistsInDB() {
    return false;
  }
  
  public void setBTM(Vector paramVector) {
    this._$3769 = paramVector;
  }
  
  public void setBtnEvent(Vector paramVector) {
    this._$3773 = paramVector;
  }
  
  public void setCategory(Vector paramVector) {
    this._$3770 = paramVector;
  }
  
  public void setCurve(Vector paramVector) {
    this._$3767 = paramVector;
  }
  
  public void setDriverMessage(Vector paramVector) {
    this._$3771 = paramVector;
  }
  
  public void setDriverMessage_Ack(Vector paramVector) {
    this._$3772 = paramVector;
  }
  
  public void setDynamic(Vector paramVector) {
    this._$3774 = paramVector;
  }
  
  public void setFailure(Vector paramVector) {
    this._$3775 = paramVector;
  }
  
  public void setGradient(Vector paramVector) {
    this._$3768 = paramVector;
  }
  
  public void setStatus(Vector paramVector) {
    this._$3776 = paramVector;
  }
  
  public void setTimeStamp(Vector paramVector) {
    this._$3777 = paramVector;
  }
  
  public void setTrainData(Vector paramVector) {
    this._$2355 = paramVector;
  }
  
  public void setVDX(Vector paramVector) {
    this._$3778 = paramVector;
  }
}
