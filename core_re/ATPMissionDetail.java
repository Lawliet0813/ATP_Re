package core;

import com.MiTAC.TRA.ATP.connect.ConnectDB;
import com.MiTAC.TRA.ATP.core.ATPMission;
import com.MiTAC.TRA.ATP.core.ATPMissionGeneral;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class ATPMissionDetail extends ATPMissionGeneral {
  private Vector _$18383 = new Vector();
  
  private Vector _$18380 = new Vector();
  
  private Vector _$18381 = new Vector();
  
  private Vector _$18382 = new Vector();
  
  private Vector _$4445 = new Vector();
  
  private Vector _$4500 = new Vector();
  
  private Vector _$18394 = new Vector();
  
  private Vector _$18393 = new Vector();
  
  private String _$18395;
  
  private Vector _$18384 = new Vector();
  
  private Vector _$12664 = new Vector();
  
  private Vector _$18188 = new Vector();
  
  private Vector _$18386 = new Vector();
  
  private Vector _$18389 = new Vector();
  
  private Vector _$18387 = new Vector();
  
  private Vector _$18385 = new Vector();
  
  private Vector _$18391 = new Vector();
  
  private Vector _$18390 = new Vector();
  
  private Vector _$18379 = new Vector();
  
  private Vector _$17042 = new Vector();
  
  private Vector _$16516 = new Vector();
  
  private Vector _$18388 = new Vector();
  
  private Vector _$1968 = new Vector();
  
  private Date _$18397;
  
  private Date _$18396;
  
  private Vector _$18392 = new Vector();
  
  public ATPMissionDetail(Date paramDate1, String paramString1, String paramString2, String paramString3, String paramString4, Date paramDate2, Date paramDate3) {
    super(paramDate1, paramString1, paramString2, paramString3, paramString4);
    changeZoom(paramDate2, paramDate3);
  }
  
  public ATPMissionDetail(Date paramDate, String paramString1, String paramString2, String paramString3, String paramString4) {
    super(paramDate, paramString1, paramString2, paramString3, paramString4);
    changeZoom(getMissionStartTime(), getMissionEndTime());
  }
  
  public ATPMissionDetail(ATPMission paramATPMission, Date paramDate1, Date paramDate2) {
    super(paramATPMission);
    changeZoom(paramDate1, paramDate2);
  }
  
  public ATPMissionDetail(ATPMission paramATPMission) {
    super(paramATPMission);
    changeZoom(getMissionStartTime(), getMissionEndTime());
  }
  
  public void changeZoom(Date paramDate1, Date paramDate2) {
    this._$18396 = paramDate1.before(paramDate2) ? paramDate1 : paramDate2;
    this._$18397 = paramDate2.after(paramDate1) ? paramDate2 : paramDate1;
    this._$18396 = this._$18396.after(getMissionStartTime()) ? this._$18396 : getMissionStartTime();
    this._$18397 = this._$18397.before(getMissionEndTime()) ? this._$18397 : getMissionEndTime();
    _$18403();
    _$18404();
  }
  
  public void changeZoom(Date paramDate) {
    changeZoom(paramDate, this._$18397);
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
      ConnectDB connectDB = new ConnectDB();
      try {
        String str = ((Vector)connectDB.getData("SELECT Name FROM Driver_info WHERE Driver_ID = '" + getDriver() + "'").get(0)).get(0);
        if (str.length() != 0) {
          this._$18395 = str;
        } else {
          this._$18395 = "-";
        } 
      } catch (Exception exception) {
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
  
  private Vector _$16400(int[] paramArrayOfint, Vector paramVector) {
    Vector vector = (Vector)paramVector.clone();
    for (int i = paramVector.size() - 1; i >= 0; i--) {
      if (Arrays.binarySearch(paramArrayOfint, i) < 0)
        vector.remove(i); 
    } 
    return vector;
  }
  
  private void _$18404() {
    int i = getTimeStamp().size();
    byte b1 = 0;
    byte b2 = -100;
    byte b3 = -100;
    Date date = this._$18396;
    for (byte b4 = 0; b4 < getTimeStamp().size(); b4++) {
      Vector vector = getTimeStamp().get(b4);
      Date date1 = vector.get(5);
      if (date1.after(this._$18396) && date1.before(this._$18397)) {
        i = (b4 < i) ? b4 : i;
        b1 = (b4 > b1) ? b4 : b1;
        Vector vector1 = _$16400(new int[] { 5, 6 }, vector);
        if (date1.getTime() - date.getTime() > 10000L) {
          Vector vector3 = new Vector();
          vector3.add(new Date(date.getTime() + 1000L));
          vector3.add(new Integer(0));
          this._$18379.add(vector3);
          Vector vector4 = new Vector();
          vector4.add(new Date(date1.getTime() - 1000L));
          vector4.add(new Integer(0));
          this._$18379.add(vector4);
        } 
        this._$18379.add(vector1);
        Vector vector2 = _$16400(new int[] { 5, 7 }, vector);
        if (date1.getTime() - date.getTime() > 10000L) {
          Vector vector3 = new Vector();
          vector3.add(new Date(date.getTime() + 1000L));
          vector3.add(new Integer(0));
          this._$12664.add(vector3);
          Vector vector4 = new Vector();
          vector4.add(new Date(date1.getTime() - 1000L));
          vector4.add(new Integer(0));
          this._$12664.add(vector4);
        } 
        this._$12664.add(vector2);
        date = date1;
      } 
    } 
    if (getTimeStamp().size() != 0) {
      i = (i - 1 < 0) ? 0 : (i - 1);
      Vector vector = getTimeStamp().get(i);
      this._$18379.insertElementAt(_$16400(new int[] { 5, 6 }, vector), 0);
      this._$12664.insertElementAt(_$16400(new int[] { 5, 7 }, vector), 0);
      vector = getTimeStamp().get(b1);
      Date date1 = ((Vector)this._$18379.lastElement()).get(0);
      Date date2 = vector.get(0);
      if (date1.getTime() != date2.getTime())
        this._$18379.add(_$16400(new int[] { 5, 6 }, vector)); 
      date1 = ((Vector)this._$12664.lastElement()).get(0);
      if (date1.getTime() != date2.getTime())
        this._$12664.add(_$16400(new int[] { 5, 7 }, vector)); 
      System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
      System.out.println("原始大小: TimeStamp size > " + (b1 - i));
      System.out.println("  調整後: speed > " + this._$18379.size());
      System.out.println("       : location > " + this._$12664.size());
      System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    } 
    i = getDynamic().size();
    b1 = 0;
    int j = -100;
    int k = -100;
    int m = -100;
    int n = -100;
    int i1 = -100;
    int i2 = -100;
    int i3 = -100;
    int i4 = -100;
    for (byte b5 = 0; b5 < getDynamic().size(); b5++) {
      Vector vector = getDynamic().get(b5);
      Date date1 = vector.get(5);
      if (date1.after(this._$18396) && date1.before(this._$18397)) {
        i = (b5 < i) ? b5 : i;
        b1 = (b5 > b1) ? b5 : b1;
        Vector vector1 = _$16400(new int[] { 5, 6 }, vector);
        int i10 = ((Integer)vector1.get(1)).intValue();
        if (j != i10) {
          this._$18380.add(vector1);
          j = i10;
        } 
        Vector vector2 = _$16400(new int[] { 5, 7 }, vector);
        int i11 = ((Integer)vector2.get(1)).intValue();
        if (k != i11) {
          this._$16516.add(vector2);
          Vector vector9 = _$16400(new int[] { 5, 8 }, vector);
          this._$18382.add(vector9);
          k = i11;
        } 
        Vector vector3 = _$16400(new int[] { 5, 9 }, vector);
        int i12 = ((Integer)vector3.get(1)).intValue();
        if (m != i12) {
          this._$18388.add(vector3);
          m = i12;
        } 
        Vector vector4 = _$16400(new int[] { 5, 10 }, vector);
        int i13 = ((Integer)vector4.get(1)).intValue();
        if (n != i13) {
          this._$18386.add(vector4);
          n = i13;
        } 
        Vector vector5 = _$16400(new int[] { 5, 11 }, vector);
        int i14 = ((Integer)vector5.get(1)).intValue();
        if (i1 != i14) {
          this._$18387.add(vector5);
          i1 = i14;
        } 
        Vector vector6 = _$16400(new int[] { 5, 12 }, vector);
        int i15 = ((Integer)vector6.get(1)).intValue();
        if (i2 != i15) {
          this._$18392.add(vector6);
          i2 = i15;
        } 
        Vector vector7 = _$16400(new int[] { 5, 13 }, vector);
        int i16 = ((Integer)vector7.get(1)).intValue();
        if (i3 != i16) {
          this._$18390.add(vector7);
          i3 = i16;
        } 
        Vector vector8 = _$16400(new int[] { 5, 14 }, vector);
        int i17 = ((Integer)vector8.get(1)).intValue();
        if (i4 != i17) {
          this._$18391.add(vector8);
          i4 = i17;
        } 
      } 
    } 
    if (getDynamic().size() != 0) {
      i = (i - 1 < 0) ? 0 : (i - 1);
      Vector vector = getDynamic().get(i);
      this._$18380.insertElementAt(_$16400(new int[] { 5, 6 }, vector), 0);
      this._$16516.insertElementAt(_$16400(new int[] { 5, 7 }, vector), 0);
      this._$18382.insertElementAt(_$16400(new int[] { 5, 8 }, vector), 0);
      this._$18388.insertElementAt(_$16400(new int[] { 5, 9 }, vector), 0);
      this._$18386.insertElementAt(_$16400(new int[] { 5, 10 }, vector), 0);
      this._$18387.insertElementAt(_$16400(new int[] { 5, 11 }, vector), 0);
      this._$18392.insertElementAt(_$16400(new int[] { 5, 12 }, vector), 0);
      this._$18390.insertElementAt(_$16400(new int[] { 5, 13 }, vector), 0);
      this._$18391.insertElementAt(_$16400(new int[] { 5, 14 }, vector), 0);
      vector = getDynamic().get(b1);
      Date date2 = vector.get(0);
      Date date1 = ((Vector)this._$18380.lastElement()).get(0);
      if (date1.getTime() != date2.getTime())
        this._$18380.add(_$16400(new int[] { 5, 6 }, vector)); 
      date1 = ((Vector)this._$16516.lastElement()).get(0);
      if (date1.getTime() != date2.getTime())
        this._$16516.add(_$16400(new int[] { 5, 7 }, vector)); 
      date1 = ((Vector)this._$18382.lastElement()).get(0);
      if (date1.getTime() != date2.getTime())
        this._$18382.add(_$16400(new int[] { 5, 8 }, vector)); 
      date1 = ((Vector)this._$18388.lastElement()).get(0);
      if (date1.getTime() != date2.getTime())
        this._$18388.add(_$16400(new int[] { 5, 9 }, vector)); 
      date1 = ((Vector)this._$18386.lastElement()).get(0);
      if (date1.getTime() != date2.getTime())
        this._$18386.add(_$16400(new int[] { 5, 10 }, vector)); 
      date1 = ((Vector)this._$18387.lastElement()).get(0);
      if (date1.getTime() != date2.getTime())
        this._$18387.add(_$16400(new int[] { 5, 11 }, vector)); 
      date1 = ((Vector)this._$18392.lastElement()).get(0);
      if (date1.getTime() != date2.getTime())
        this._$18392.add(_$16400(new int[] { 5, 12 }, vector)); 
      date1 = ((Vector)this._$18390.lastElement()).get(0);
      if (date1.getTime() != date2.getTime())
        this._$18390.add(_$16400(new int[] { 5, 13 }, vector)); 
      date1 = ((Vector)this._$18391.lastElement()).get(0);
      if (date1.getTime() != date2.getTime())
        this._$18391.add(_$16400(new int[] { 5, 14 }, vector)); 
      System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
      System.out.println("原始大小: Dynamic size > " + (b1 - i));
      System.out.println("  調整後: acceleration > " + this._$18380.size());
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
    i = getStatus().size();
    b1 = 0;
    int i5 = -100;
    int i6 = -100;
    int i7 = -100;
    int i8 = -100;
    for (byte b6 = 0; b6 < getStatus().size(); b6++) {
      Vector vector = getStatus().get(b6);
      Date date1 = vector.get(5);
      if (date1.before(this._$18396))
        i = b6; 
      if (date1.before(this._$18397))
        b1 = b6; 
      if (date1.after(this._$18396) && date1.before(this._$18397)) {
        i = (b6 < i) ? b6 : i;
        b1 = (b6 > b1) ? b6 : b1;
        Vector vector1 = _$16400(new int[] { 5, 6 }, vector);
        int i10 = ((Integer)vector1.get(1)).intValue();
        if (i5 != i10) {
          this._$18381.add(vector1);
          i5 = i10;
        } 
        Vector vector2 = _$16400(new int[] { 5, 7 }, vector);
        int i11 = ((Integer)vector2.get(1)).intValue();
        if (i6 != i11) {
          this._$18188.add(vector2);
          i6 = i11;
        } 
        Vector vector3 = _$16400(new int[] { 5, 8 }, vector);
        int i12 = ((Integer)vector3.get(1)).intValue();
        if (i7 != i12) {
          this._$18384.add(vector3);
          i7 = i12;
        } 
        Vector vector4 = _$16400(new int[] { 5, 9 }, vector);
        int i13 = ((Integer)vector4.get(1)).intValue();
        if (i8 != i13) {
          this._$18385.add(vector4);
          i8 = i13;
        } 
      } 
    } 
    if (getStatus().size() != 0) {
      i = (i - 1 < 0) ? 0 : (i - 1);
      Vector vector = getStatus().get(i);
      this._$18381.insertElementAt(_$16400(new int[] { 5, 6 }, vector), 0);
      this._$18188.insertElementAt(_$16400(new int[] { 5, 7 }, vector), 0);
      this._$18384.insertElementAt(_$16400(new int[] { 5, 8 }, vector), 0);
      this._$18385.insertElementAt(_$16400(new int[] { 5, 9 }, vector), 0);
      vector = getStatus().get(b1);
      Date date1 = ((Vector)this._$18381.lastElement()).get(0);
      Date date2 = vector.get(0);
      if (date1.getTime() != date2.getTime())
        this._$18381.add(_$16400(new int[] { 5, 6 }, vector)); 
      date1 = ((Vector)this._$18188.lastElement()).get(0);
      if (date1.getTime() != date2.getTime())
        this._$18188.add(_$16400(new int[] { 5, 7 }, vector)); 
      date1 = ((Vector)this._$18384.lastElement()).get(0);
      if (date1.getTime() != date2.getTime())
        this._$18384.add(_$16400(new int[] { 5, 8 }, vector)); 
      date1 = ((Vector)this._$18385.lastElement()).get(0);
      if (date1.getTime() != date2.getTime())
        this._$18385.add(_$16400(new int[] { 5, 9 }, vector)); 
      System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
      System.out.println("原始大小: Status size > " + (b1 - i));
      System.out.println("  調整後: adhesion > " + this._$18381.size());
      System.out.println("       : mode > " + this._$18188.size());
      System.out.println("       : emerBrake > " + this._$18384.size());
      System.out.println("       : serviceBrake > " + this._$18385.size());
      System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    } 
    for (byte b7 = 0; b7 < super.getBTM().size(); b7++) {
      Vector vector = super.getBTM().get(b7);
      Date date1 = vector.get(5);
      if (date1.after(this._$18396) && date1.before(this._$18397))
        this._$4445.add(vector); 
    } 
    for (byte b8 = 0; b8 < getDriverMessage().size(); b8++) {
      Vector vector = getDriverMessage().get(b8);
      Date date1 = vector.get(5);
      if (date1.after(this._$18396) && date1.before(this._$18397) && ((Integer)_$16400(new int[] { 5, 9 }, vector).get(1)).intValue() >= 60000)
        this._$17042.add(_$16400(new int[] { 5, 9 }, vector)); 
    } 
    for (byte b9 = 0; b9 < getDriverMessage().size(); b9++) {
      Vector vector = getDriverMessage().get(b9);
      Date date1 = vector.get(5);
      if (date1.after(this._$18396) && date1.before(this._$18397))
        this._$18393.add(_$16400(new int[] { 5, 6, 7, 8, 9 }, vector)); 
    } 
    for (byte b10 = 0; b10 < getDriverMessage_Ack().size(); b10++) {
      Vector vector = getDriverMessage_Ack().get(b10);
      Date date1 = vector.get(5);
      if (date1.after(this._$18396) && date1.before(this._$18397))
        this._$18394.add(_$16400(new int[] { 5, 6, 7 }, vector)); 
    } 
    i = getVDX().size();
    b1 = 0;
    int i9 = -100;
    for (byte b11 = 0; b11 < getVDX().size(); b11++) {
      Vector vector = getVDX().get(b11);
      Date date1 = vector.get(5);
      if (date1.after(this._$18396) && date1.before(this._$18397)) {
        i = (b11 < i) ? b11 : i;
        b1 = (b11 > b1) ? b11 : b1;
        Vector vector1 = _$16400(new int[] { 5, 6 }, vector);
        int i10 = ((Integer)vector1.get(1)).intValue();
        if (i9 != i10) {
          this._$18389.add(vector1);
          i9 = i10;
        } 
      } 
    } 
    if (getVDX().size() != 0) {
      i = (i - 1 < 0) ? 0 : (i - 1);
      Vector vector = getVDX().get(i);
      this._$18389.insertElementAt(_$16400(new int[] { 5, 6 }, vector), 0);
      vector = getVDX().get(b1);
      Date date1 = ((Vector)this._$18389.lastElement()).get(0);
      Date date2 = vector.get(0);
      if (date1.getTime() != date2.getTime())
        this._$18389.add(_$16400(new int[] { 5, 6 }, vector)); 
    } 
    System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
    System.out.println("原始大小: VDX size > " + (b1 - i));
    System.out.println("  調整後: pipePressure > " + this._$18389.size());
    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    for (byte b12 = 0; b12 < super.getTrainData().size(); b12++) {
      Vector vector = super.getTrainData().get(b12);
      Date date1 = vector.get(5);
      if (date1.after(this._$18396) && date1.before(this._$18397))
        this._$1968.add(vector); 
    } 
    try {
      this._$18383 = new Vector();
      Vector vector1 = this.conn.getGradientToDraw((ATPMission)this);
      for (byte b13 = 0; b13 < vector1.size(); b13++) {
        Vector vector = vector1.get(b13);
        Date date1 = vector.get(0);
        if (date1.after(this._$18396) && date1.before(this._$18397))
          this._$18383.add(vector); 
      } 
      this._$4500 = new Vector();
      Vector vector2 = this.conn.getCurveToDraw((ATPMission)this);
      for (byte b14 = 0; b14 < vector2.size(); b14++) {
        Vector vector = vector2.get(b14);
        Date date1 = vector.get(0);
        if (date1.after(this._$18396) && date1.before(this._$18397))
          this._$4500.add(vector); 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    _$18451(this._$18386);
    _$18451(this._$18387);
    _$18451(this._$18388);
    _$18451(this._$18379);
    _$18452(this._$16516);
    _$18452(this._$18382);
  }
  
  private Vector _$18451(Vector paramVector) {
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector = paramVector.get(b);
      int i = ((Integer)vector.get(1)).intValue();
      if (i != 0)
        if (i < 0) {
          i = -1;
        } else {
          i = i * 36 / 1000;
          if (i == 0)
            i = 1; 
        }  
      vector.remove(1);
      vector.add(new Integer(i));
    } 
    return paramVector;
  }
  
  private Vector _$18452(Vector paramVector) {
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector = paramVector.get(b);
      int i = ((Integer)vector.get(1)).intValue();
      if (i < 0) {
        i = -1;
      } else {
        i /= 100;
      } 
      vector.remove(1);
      vector.add(new Integer(i));
    } 
    return paramVector;
  }
}
