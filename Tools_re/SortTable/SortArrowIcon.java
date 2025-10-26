package Tools.SortTable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

public class SortArrowIcon implements Icon {
  public static final int ASCENDING = 2;
  
  public static final int DECENDING = 1;
  
  public static final int NONE = 0;
  
  protected int direction;
  
  protected int iconHeight = 8;
  
  protected int iconWidth = 8;
  
  public SortArrowIcon(int paramInt) {
    this.direction = paramInt;
  }
  
  public int getIconHeight() {
    return this.iconHeight;
  }
  
  public int getIconWidth() {
    return this.iconWidth;
  }
  
  public void paintIcon(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2) {
    Color color1 = paramComponent.getBackground();
    Color color2 = color1.brighter();
    Color color3 = color1.darker();
    int i = this.iconWidth;
    int j = this.iconHeight;
    int k = i / 2;
    if (this.direction == 2) {
      paramGraphics.setColor(color3);
      paramGraphics.drawLine(paramInt1, paramInt2, paramInt1 + i, paramInt2);
      paramGraphics.drawLine(paramInt1, paramInt2, paramInt1 + k, paramInt2 + j);
      paramGraphics.setColor(color2);
      paramGraphics.drawLine(paramInt1 + i, paramInt2, paramInt1 + k, paramInt2 + j);
    } 
    if (this.direction == 1) {
      paramGraphics.setColor(color3);
      paramGraphics.drawLine(paramInt1 + k, paramInt2, paramInt1, paramInt2 + j);
      paramGraphics.setColor(color2);
      paramGraphics.drawLine(paramInt1, paramInt2 + j, paramInt1 + i, paramInt2 + j);
      paramGraphics.drawLine(paramInt1 + k, paramInt2, paramInt1 + i, paramInt2 + j);
    } 
  }
}
