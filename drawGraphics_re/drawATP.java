package drawGraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.util.Vector;

public interface drawATP {
  void paintBody(Graphics paramGraphics) throws Exception;
  
  void paintHeader(Graphics paramGraphics) throws Exception;
  
  void setData(Vector paramVector) throws Exception;
  
  void setDataLineColor(Color paramColor) throws Exception;
  
  void setDrawCurve(boolean paramBoolean) throws Exception;
  
  void setStroke(Stroke paramStroke);
}
