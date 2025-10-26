package drawGraphics;

public class drawParameters implements Cloneable {
  public int MaxNum = 130;
  
  public int MinNum = 0;
  
  public int UpperBound = 0;
  
  public double dpiY = 1.0D;
  
  public boolean drawBody = true;
  
  public boolean drawValues = true;
  
  public int intervalY = 20;
  
  public String message = "";
  
  public int sequence = 0;
  
  public int basePointY() {
    return (int)(this.UpperBound + (this.MaxNum - this.MinNum) / this.dpiY);
  }
  
  public Object clone() {
    com.MiTAC.TRA.ATP.drawGraphics.drawParameters drawParameters1 = new com.MiTAC.TRA.ATP.drawGraphics.drawParameters();
    drawParameters1.dpiY = this.dpiY;
    drawParameters1.drawBody = this.drawBody;
    drawParameters1.drawValues = this.drawValues;
    drawParameters1.intervalY = this.intervalY;
    drawParameters1.MaxNum = this.MaxNum;
    drawParameters1.message = this.message;
    drawParameters1.MinNum = this.MinNum;
    drawParameters1.sequence = this.sequence;
    drawParameters1.UpperBound = this.UpperBound;
    return drawParameters1;
  }
}
