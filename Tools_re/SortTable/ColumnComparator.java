package Tools.SortTable;

import java.util.Comparator;
import java.util.Vector;

public class ColumnComparator implements Comparator {
  protected boolean ascending;
  
  protected int index;
  
  public ColumnComparator(int paramInt, boolean paramBoolean) {
    this.index = paramInt;
    this.ascending = paramBoolean;
  }
  
  public int compare(Object paramObject1, Object paramObject2) {
    if (paramObject1 instanceof Vector && paramObject2 instanceof Vector) {
      Vector vector1 = (Vector)paramObject1;
      Vector vector2 = (Vector)paramObject2;
      Comparable comparable1 = (Comparable)vector1.elementAt(this.index);
      Comparable comparable2 = (Comparable)vector2.elementAt(this.index);
      if (comparable1 instanceof Comparable && comparable2 instanceof Comparable) {
        Comparable comparable = comparable1;
        Comparable comparable3 = comparable2;
        int i = comparable.compareTo(comparable3);
        int j = comparable3.compareTo(comparable);
        return this.ascending ? i : j;
      } 
    } 
    return 1;
  }
}
