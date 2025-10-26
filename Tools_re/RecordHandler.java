package Tools;

import java.util.Vector;

public class RecordHandler extends Vector {
  public RecordHandler(int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
  }
  
  public RecordHandler(int paramInt) {
    super(paramInt);
  }
  
  public RecordHandler() {}
  
  public void addObject(Object paramObject) throws Exception {
    if (paramObject.getClass().getName() == "java.util.Vector") {
      addVector((Vector)paramObject);
    } else {
      add(paramObject);
    } 
  }
  
  public void addVector(Vector paramVector) throws Exception {
    if (paramVector != null && paramVector.size() != 0)
      for (byte b = 0; b < paramVector.size(); b++)
        add(paramVector.get(b));  
  }
}
