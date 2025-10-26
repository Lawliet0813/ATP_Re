package core;

import com.MiTAC.TRA.ATP.Tools.RecordHandler;
import com.MiTAC.TRA.ATP.core.ATPMission;
import com.MiTAC.TRA.ATP.core.ATPMissionGeneral;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class ATPMissionFailure extends ATPMissionGeneral {
  private Vector _$16210;
  
  private int _$16392 = 0;
  
  private int _$16394 = 0;
  
  private int _$16393 = 0;
  
  private int _$16395 = 0;
  
  private Vector _$9529;
  
  private Vector _$16391;
  
  public ATPMissionFailure(Date paramDate, String paramString1, String paramString2, String paramString3, String paramString4) {
    super(paramDate, paramString1, paramString2, paramString3, paramString4);
  }
  
  public ATPMissionFailure(ATPMission paramATPMission) {
    super(paramATPMission.getMissionDate(), paramATPMission.getWorkShift(), paramATPMission.getTrainRunning(), paramATPMission.getDriver(), paramATPMission.getVehicle());
  }
  
  private void _$16402() throws Exception {
    if (getEBTimes() != 0 || getSBTimes() != 0) {
      RecordHandler recordHandler1 = new RecordHandler();
      RecordHandler recordHandler2 = new RecordHandler();
      RecordHandler recordHandler3 = new RecordHandler();
      RecordHandler recordHandler4 = new RecordHandler();
      Vector vector = new Vector();
      for (byte b1 = 0; b1 < getStatus().size(); b1++) {
        Vector vector2 = getStatus().get(b1);
        if (((Integer)vector2.get(8)).intValue() > 0 || ((Integer)vector2.get(9)).intValue() > 0) {
          recordHandler2.addVector(_$16400(new int[] { 5 }, vector2));
          vector.add(_$16400(new int[] { 6, 7, 8, 9, 10 }, vector2));
        } 
      } 
      for (byte b2 = 0; b2 < getDynamic().size(); b2++) {
        Vector vector2 = getDynamic().get(b2);
        recordHandler1.addVector(_$16400(new int[] { 5 }, vector2));
      } 
      for (byte b3 = 0; b3 < getTimeStamp().size(); b3++) {
        Vector vector2 = getTimeStamp().get(b3);
        recordHandler3.addVector(_$16400(new int[] { 5 }, vector2));
      } 
      for (byte b4 = 0; b4 < getBTM().size(); b4++) {
        Vector vector2 = getBTM().get(b4);
        recordHandler4.addVector(_$16400(new int[] { 5 }, vector2));
      } 
      Vector vector1 = new Vector();
      for (byte b5 = 0; b5 < recordHandler2.size(); b5++) {
        RecordHandler recordHandler = new RecordHandler();
        recordHandler.add(getDriver());
        recordHandler.add(getWorkShift());
        recordHandler.add(getTrainRunning());
        int k = Arrays.binarySearch(recordHandler1.toArray(), recordHandler2.get(b5));
        k = (Math.abs(k) - 2 < 0) ? 0 : (Math.abs(k) - 2);
        Vector vector2 = _$16400(new int[] { 5, 6, 8, 9, 10, 12, 13, 14 }, getDynamic().get(k));
        int m = ((Integer)vector2.get(3)).intValue() * 36 / 1000;
        int n = ((Integer)vector2.get(4)).intValue() * 36 / 1000;
        vector2.setElementAt(new Integer(m), 3);
        vector2.setElementAt(new Integer(n), 4);
        recordHandler.addVector(vector2);
        recordHandler.addVector(vector.get(b5));
        k = Arrays.binarySearch(recordHandler3.toArray(), recordHandler2.get(b5));
        k = (Math.abs(k) - 2 < 0) ? 0 : (Math.abs(k) - 2);
        Vector vector3 = _$16400(new int[] { 6 }, getTimeStamp().get(k));
        int i1 = ((Integer)vector3.get(0)).intValue() * 36 / 1000;
        vector3.setElementAt(new Integer(i1), 0);
        recordHandler.addVector(vector3);
        int i = ((Integer)((Vector)getTimeStamp().get(k)).get(7)).intValue();
        k = Arrays.binarySearch(recordHandler4.toArray(), recordHandler2.get(b5));
        k = (Math.abs(k) - 2 < 0) ? 0 : (Math.abs(k) - 2);
        recordHandler.addVector(_$16400(new int[] { 6 }, getBTM().get(k)));
        int j = ((Integer)((Vector)getBTM().get(k)).get(7)).intValue();
        recordHandler.add(new Integer(i - j));
        String str = "";
        if (((Integer)((Vector)vector.get(b5)).get(2)).intValue() != 0) {
          str = "EB作用";
        } else {
          str = "SB作用";
        } 
        recordHandler.add(str);
        recordHandler.add(this);
        vector1.add(recordHandler);
      } 
      this._$16210 = vector1;
    } 
  }
  
  private void _$16433() throws Exception {
    if (getCabinFailureTimes() != 0) {
      RecordHandler recordHandler1 = new RecordHandler();
      RecordHandler recordHandler2 = new RecordHandler();
      Vector vector = new Vector();
      int i = ((Integer)((Vector)getCategory().get(0)).get(14)).intValue();
      for (byte b1 = 0; b1 < getFailure().size(); b1++) {
        Vector vector2 = getFailure().get(b1);
        if (((Integer)vector2.get(6)).intValue() != 8 && ((Integer)vector2.get(6)).intValue() != 15 && ((Integer)vector2.get(6)).intValue() != 16 && ((Integer)vector2.get(6)).intValue() != 21 && ((Integer)vector2.get(6)).intValue() != 34) {
          int[] arrayOfInt = { 5 };
          recordHandler1.addVector(_$16400(arrayOfInt, vector2));
          arrayOfInt = new int[] { 5, 6, 9, 11 };
          vector.add(_$16400(arrayOfInt, vector2));
        } 
      } 
      for (byte b2 = 0; b2 < getTimeStamp().size(); b2++) {
        Vector vector2 = getTimeStamp().get(b2);
        int[] arrayOfInt = { 5 };
        recordHandler2.addVector(_$16400(arrayOfInt, vector2));
      } 
      Vector vector1 = new Vector();
      for (byte b3 = 0; b3 < recordHandler1.size(); b3++) {
        RecordHandler recordHandler = new RecordHandler();
        recordHandler.add(getDriver());
        recordHandler.add(getTrainRunning());
        recordHandler.add(getVehicle());
        recordHandler.add(new Integer(i));
        recordHandler.addVector(vector.get(b3));
        int j = Arrays.binarySearch(recordHandler2.toArray(), recordHandler1.get(b3));
        j = (j >= 0) ? j : ((Math.abs(j) > recordHandler2.size()) ? (Math.abs(j) - 2) : (Math.abs(j) - 1));
        int[] arrayOfInt = { 6 };
        Vector vector2 = _$16400(arrayOfInt, getTimeStamp().get(j));
        recordHandler.add(new Integer(((Integer)vector2.get(0)).intValue() * 36 / 1000));
        vector1.add(recordHandler);
      } 
      for (byte b4 = 0; b4 < vector1.size(); b4++) {
        int j = ((Integer)((Vector)vector1.get(b4)).get(5)).intValue();
        int k = ((Integer)((Vector)vector1.get(b4)).get(6)).intValue();
        if (j == 9)
          if (k == 63) {
            ((Vector)vector1.get(b4)).setElementAt(new Integer(0), 6);
            ((Vector)vector1.get(b4)).setElementAt("操作面盤:尚未按確認鈕", 7);
          } else if (k == 6223) {
            ((Vector)vector1.get(b4)).setElementAt(new Integer(0), 6);
            ((Vector)vector1.get(b4)).setElementAt("操作面盤:已按過確認鈕", 7);
          }  
      } 
      this._$9529 = vector1;
    } 
  }
  
  private void _$16438() throws Exception {
    if (getWaysideFailureTimes() != 0) {
      RecordHandler recordHandler1 = new RecordHandler();
      RecordHandler recordHandler2 = new RecordHandler();
      RecordHandler recordHandler3 = new RecordHandler();
      Vector vector = new Vector();
      for (byte b1 = 0; b1 < getFailure().size(); b1++) {
        Vector vector2 = getFailure().get(b1);
        if (((Integer)vector2.get(6)).intValue() == 8 || ((Integer)vector2.get(6)).intValue() == 15 || ((Integer)vector2.get(6)).intValue() == 16 || ((Integer)vector2.get(6)).intValue() == 21 || ((Integer)vector2.get(6)).intValue() == 34) {
          int[] arrayOfInt = { 5 };
          recordHandler1.addVector(_$16400(arrayOfInt, vector2));
          arrayOfInt = new int[] { 5, 6, 9, 11 };
          vector.add(_$16400(arrayOfInt, vector2));
        } 
      } 
      for (byte b2 = 0; b2 < getBTM().size(); b2++) {
        Vector vector2 = getBTM().get(b2);
        int[] arrayOfInt = { 5 };
        recordHandler3.addVector(_$16400(arrayOfInt, vector2));
      } 
      for (byte b3 = 0; b3 < getTimeStamp().size(); b3++) {
        Vector vector2 = getTimeStamp().get(b3);
        int[] arrayOfInt = { 5 };
        recordHandler2.addVector(_$16400(arrayOfInt, vector2));
      } 
      Vector vector1 = new Vector();
      for (byte b4 = 0; b4 < recordHandler1.size(); b4++) {
        RecordHandler recordHandler = new RecordHandler();
        recordHandler.add(getDriver());
        recordHandler.add(getTrainRunning());
        recordHandler.add(getVehicle());
        recordHandler.addVector(vector.get(b4));
        int i = Arrays.binarySearch(recordHandler2.toArray(), recordHandler1.get(b4));
        i = (i >= 0) ? i : ((Math.abs(i) > recordHandler2.size()) ? (Math.abs(i) - 2) : (Math.abs(i) - 1));
        int[] arrayOfInt = { 6 };
        Vector vector2 = _$16400(arrayOfInt, getTimeStamp().get(i));
        recordHandler.add(new Integer(((Integer)vector2.get(0)).intValue() * 36 / 1000));
        i = Arrays.binarySearch(recordHandler3.toArray(), recordHandler1.get(b4));
        i = (i >= 0) ? i : ((Math.abs(i) > recordHandler3.size()) ? (Math.abs(i) - 2) : (Math.abs(i) - 1));
        arrayOfInt = new int[] { 6 };
        recordHandler.addVector(new Vector());
        vector1.add(recordHandler);
      } 
      this._$16391 = vector1;
    } 
  }
  
  private Vector _$16424(Vector paramVector) {
    if (paramVector == null)
      return new Vector(); 
    Vector vector = new Vector();
    Vector vector1 = new Vector();
    Vector vector2 = new Vector();
    vector2.add(new Integer(0));
    vector2.add(new Integer(0));
    vector1.add(vector2);
    boolean bool = false;
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector3 = new Vector();
      Vector vector4 = paramVector.get(b);
      int i = ((Integer)vector4.get(5)).intValue();
      int j = ((Integer)vector4.get(6)).intValue();
      vector3.add(new Integer(i));
      vector3.add(new Integer(j));
      int k = 0;
      int m = 0;
      for (byte b1 = 0; b1 < vector1.size(); b1++) {
        Vector vector5 = vector1.get(b1);
        k = ((Integer)vector5.get(0)).intValue();
        m = ((Integer)vector5.get(1)).intValue();
        if (k == i && m == j) {
          bool = true;
          break;
        } 
      } 
      if (bool) {
        bool = false;
      } else {
        vector1.add(vector3);
        vector.add(paramVector.get(b));
      } 
    } 
    return vector;
  }
  
  private Vector _$16415(Vector paramVector) {
    if (paramVector == null)
      return null; 
    Vector vector = new Vector();
    long l1 = 0L;
    long l2 = 0L;
    long l3 = 0L;
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    boolean bool = false;
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector1 = paramVector.get(b);
      if (((Date)vector1.get(3)).getTime() - l1 > 300000L) {
        k = 0;
        m = 0;
      } 
      j = ((Integer)vector1.get(13)).intValue();
      i = ((Integer)vector1.get(14)).intValue();
      if (j != 0)
        this._$16392++; 
      if (m != 2 && j == 2 && l3 != ((Date)vector1.get(3)).getTime()) {
        this._$16394++;
        vector.add(vector1);
        bool = true;
        l3 = ((Date)vector1.get(3)).getTime();
      } 
      if (i == 2)
        this._$16393++; 
      if (k != 2 && i == 2 && !bool && l2 != ((Date)vector1.get(3)).getTime()) {
        this._$16395++;
        vector.add(vector1);
        l2 = ((Date)vector1.get(3)).getTime();
      } 
      k = i;
      m = j;
      l1 = ((Date)vector1.get(3)).getTime();
      bool = false;
    } 
    return vector;
  }
  
  private Vector _$16432(Vector paramVector) {
    Vector vector = new Vector();
    Vector vector1 = new Vector();
    Vector vector2 = new Vector();
    vector2.add(new Integer(0));
    vector2.add(new Integer(0));
    vector1.add(vector2);
    boolean bool = false;
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector3 = new Vector();
      Vector vector4 = paramVector.get(b);
      int i = ((Integer)vector4.get(4)).intValue();
      int j = ((Integer)vector4.get(5)).intValue();
      vector3.add(new Integer(i));
      vector3.add(new Integer(j));
      int k = 0;
      int m = 0;
      for (byte b1 = 0; b1 < vector1.size(); b1++) {
        Vector vector5 = vector1.get(b1);
        k = ((Integer)vector5.get(0)).intValue();
        m = ((Integer)vector5.get(1)).intValue();
        if (k == i && m == j) {
          bool = true;
          break;
        } 
      } 
      if (bool) {
        bool = false;
      } else {
        vector1.add(vector3);
        vector.add(paramVector.get(b));
      } 
    } 
    return vector;
  }
  
  public Vector getBehaviorFailure() {
    if (this._$16210 == null)
      try {
        _$16402();
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$16210;
  }
  
  public int getBehavior_ebTimes() {
    return this._$16392;
  }
  
  public int getBehavior_ebTimes_filter() {
    return this._$16394;
  }
  
  public int getBehavior_sbTimes() {
    return this._$16393;
  }
  
  public int getBehavior_sbTimes_filter() {
    return this._$16395;
  }
  
  public Vector getCabinFailure() {
    if (this._$9529 == null)
      try {
        _$16433();
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$9529;
  }
  
  public Vector getFilterBehaviorFailure() {
    return this._$16210 = _$16415(getBehaviorFailure());
  }
  
  public Vector getFilterCabinFailure() {
    return _$16424(getCabinFailure());
  }
  
  public Vector getFilterWaysideFailure() {
    return _$16432(getWaysideFailure());
  }
  
  public Vector getWaysideFailure() {
    if (this._$16391 == null)
      try {
        _$16438();
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this._$16391;
  }
  
  private Vector _$16400(int[] paramArrayOfint, Vector paramVector) {
    Vector vector = (Vector)paramVector.clone();
    for (int i = paramVector.size() - 1; i >= 0; i--) {
      if (Arrays.binarySearch(paramArrayOfint, i) < 0)
        vector.remove(i); 
    } 
    return vector;
  }
}
