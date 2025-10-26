package decode;

import com.MiTAC.TRA.ATP.Tools.PathHandler;
import com.MiTAC.TRA.ATP.Tools.RecordHandler;
import com.MiTAC.TRA.ATP.decode.Decoder;
import java.util.Date;
import java.util.Vector;

public class LogDataCollector {
  private RecordHandler _$22276;
  
  private RecordHandler _$22277;
  
  private RecordHandler _$22278;
  
  private RecordHandler _$22270;
  
  private RecordHandler _$22268;
  
  private RecordHandler _$22275;
  
  private RecordHandler _$22272;
  
  private RecordHandler _$22274;
  
  private RecordHandler _$22273;
  
  private RecordHandler _$22271;
  
  private RecordHandler _$22269;
  
  private String _$22279;
  
  private boolean _$16641;
  
  private boolean _$1639;
  
  public LogDataCollector(String paramString) throws Exception {
    setPath(paramString);
  }
  
  public LogDataCollector() throws Exception {}
  
  private Vector _$22285(String paramString) throws Exception {
    boolean bool = true;
    String str1 = "";
    Vector vector = new Vector(2);
    if (paramString.endsWith("\\")) {
      paramString = paramString.substring(paramString.length() - 27, paramString.length() - 1);
    } else {
      paramString = paramString.substring(paramString.length() - 26, paramString.length());
    } 
    Vector vector1 = PathHandler.getDecodePath(this._$22279);
    Date date = vector1.get(0);
    String str2 = (String)vector1.get(1);
    String str3 = (String)vector1.get(2);
    String str4 = (String)vector1.get(3);
    String str5 = (String)vector1.get(4);
    String str6 = "";
    String str7 = "";
    String str8 = "";
    if (this._$22268.size() == 0) {
      Vector vector2 = new Vector();
      vector2.add(date);
      vector2.add(str4);
      vector2.add(str3);
      vector2.add(str2);
      vector2.add(str5);
      this._$22268.add(vector2);
    } else {
      Vector vector2 = (Vector)this._$22268.get(0);
      str6 = vector2.get(1);
      str7 = vector2.get(3);
      str8 = vector2.get(2);
    } 
    if (!str4.equals(str6)) {
      bool = false;
      str1 = str1 + "Driver ID(" + str4 + ") in path is different from Driver ID(" + str6 + ")  in log.";
    } 
    if (!str2.equals(str7)) {
      bool = false;
      str1 = str1 + "\nWorkShift Number(" + str2 + ")  in path is different from WorkShift Number(" + str7 + ")  in log.";
    } 
    if (!str3.equals(str8)) {
      bool = false;
      str1 = str1 + "\nTrainRunning Number(" + str3 + ")  in path is different from TrainRunning Number(" + str8 + ")  in log.";
    } 
    vector.add(new Boolean(bool));
    vector.add(str1);
    return vector;
  }
  
  public void clear() {
    this._$22268.clear();
    this._$22268.trimToSize();
    this._$22269.clear();
    this._$22269.trimToSize();
    this._$22270.clear();
    this._$22270.trimToSize();
    this._$22271.clear();
    this._$22271.trimToSize();
    this._$22272.clear();
    this._$22272.trimToSize();
    this._$22273.clear();
    this._$22273.trimToSize();
    this._$22274.clear();
    this._$22274.trimToSize();
    this._$22275.clear();
    this._$22276.clear();
    this._$22276.trimToSize();
    this._$22277.clear();
    this._$22277.trimToSize();
    this._$22278.clear();
    this._$22278.trimToSize();
    System.gc();
  }
  
  private void _$22283(String paramString) throws Exception {
    Decoder decoder = new Decoder(paramString);
    this._$22268.addVector(decoder.getLogDriverData());
    this._$22269.addVector(decoder.getLogTrainData());
    this._$22271.addVector(decoder.getLogTS());
    this._$22272.addVector(decoder.getLogDynamic());
    this._$22273.addVector(decoder.getLogStatus());
    this._$22274.addVector(decoder.getLogFailure());
    this._$22275.addVector(decoder.getLogDriverMessage());
    this._$22276.addVector(decoder.getAll());
    this._$22277.addVector(decoder.getATP());
    this._$22278.addVector(decoder.getErr());
  }
  
  public Vector getATPEventdata() {
    return (Vector)this._$22277;
  }
  
  public Vector getAllEventdata() {
    return (Vector)this._$22276;
  }
  
  public Vector getErrorEventdata() {
    return (Vector)this._$22278;
  }
  
  public Vector getLogCategory() {
    return (Vector)this._$22270;
  }
  
  public Vector getLogDriverMessage() {
    return (Vector)this._$22275;
  }
  
  public Vector getLogDynamic() {
    return (Vector)this._$22272;
  }
  
  public Vector getLogFailure() {
    return (Vector)this._$22274;
  }
  
  public Vector getLogStatus() {
    return (Vector)this._$22273;
  }
  
  public Vector getLogTS() {
    return (Vector)this._$22271;
  }
  
  public boolean isCanceled() {
    return this._$16641;
  }
  
  public boolean isDone() {
    return this._$1639;
  }
  
  public void setPath(String paramString) {
    this._$22279 = paramString;
    Object object = new Object(this);
    object.start();
  }
}
