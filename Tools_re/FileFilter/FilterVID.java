package Tools.FileFilter;

import java.util.Arrays;
import java.util.Vector;

public class FilterVID {
  public Vector removeEmptyVID(Vector paramVector) {
    if (paramVector == null)
      return null; 
    Vector vector = new Vector();
    for (byte b = 0; b < paramVector.size(); b++) {
      Vector vector1 = paramVector.get(b);
      if (!vector1.get(4).equals(""))
        vector.add(vector1); 
    } 
    return vector;
  }
  
  public Vector removeNotBelong(Vector paramVector1, Vector paramVector2) {
    Vector vector = new Vector();
    Object[] arrayOfObject = new Object[paramVector2.size()];
    for (byte b1 = 0; b1 < paramVector2.size(); b1++)
      arrayOfObject[b1] = ((Vector)paramVector2.get(b1)).get(0); 
    Arrays.sort(arrayOfObject);
    for (byte b2 = 0; b2 < paramVector1.size(); b2++) {
      Vector vector1 = paramVector1.get(b2);
      String str = ((String)vector1.get(4)).split("_")[0];
      str = str.replaceAll("-", "");
      if (Arrays.binarySearch(arrayOfObject, str) >= 0)
        vector.add(vector1); 
    } 
    return vector;
  }
}
