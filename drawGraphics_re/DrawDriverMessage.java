package drawGraphics;

import com.MiTAC.TRA.ATP.drawGraphics.DrawBase;
import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.drawGraphics.drawATP;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class DrawDriverMessage extends DrawBase implements drawATP {
  private Color _$18217;
  
  private Date[] _$26694;
  
  private Date[] _$26699;
  
  private int[][] _$26696;
  
  private int[][] _$26701;
  
  private boolean _$18218 = true;
  
  private boolean _$26705 = true;
  
  private Color[] _$26704 = new Color[] { new Color(245, 177, 109), new Color(240, 156, 66), new Color(236, 135, 14), (new Color(208, 119, 11)).darker(), (new Color(189, 107, 9)).darker(), (new Color(148, 83, 5)).darker() };
  
  private Vector _$26693;
  
  private Vector _$26698;
  
  private Stroke _$18036 = new BasicStroke();
  
  private int[] _$26695;
  
  private int[] _$26700;
  
  private int[] _$26702;
  
  private int _$26697;
  
  private int _$26703;
  
  public DrawDriverMessage(commonParaSetting paramcommonParaSetting, drawParameters paramdrawParameters, Vector paramVector) {
    super(paramcommonParaSetting, paramdrawParameters, paramVector);
  }
  
  private void _$26653(int paramInt) {
    int[] arrayOfInt1 = { this._$26695[paramInt], this._$26695[paramInt] - 2, this._$26695[paramInt] + 3 };
    int[] arrayOfInt2 = { this._$26697 - 1, this._$26697 - 4, this._$26697 - 4 };
    this.g2.fillPolygon(arrayOfInt1, arrayOfInt2, 3);
  }
  
  private void _$26708(int paramInt) {
    int[] arrayOfInt1 = { this._$26700[paramInt], this._$26700[paramInt] - 3, this._$26700[paramInt] + 3 };
    int[] arrayOfInt2 = { this._$26703, this._$26703 + 4, this._$26703 + 4 };
    this.g2.fillPolygon(arrayOfInt1, arrayOfInt2, 3);
  }
  
  public void paintBody(Graphics paramGraphics) throws Exception {
    super.paintBody(paramGraphics);
    if (this._$18218)
      _$18223(); 
  }
  
  public void paintHeader(Graphics paramGraphics) throws Exception {
    super.paintHeader(paramGraphics);
  }
  
  public void resetScale() {
    this._$26694 = new Date[this._$26693.size()];
    this._$26696 = new int[this._$26693.size()][5];
    this._$26695 = new int[this._$26693.size()];
    this._$26697 = showsoldierRangeToDefence(this.para.MaxNum) - 25;
    for (byte b1 = 0; b1 < this._$26693.size(); b1++) {
      Vector vector = this._$26693.get(b1);
      this._$26694[b1] = vector.get(0);
      this._$26696[b1][0] = ((Integer)vector.get(1) == null) ? 0 : ((Integer)vector.get(1)).intValue();
      this._$26696[b1][1] = 0;
      this._$26696[b1][2] = ((Integer)vector.get(3) == null) ? 0 : ((Integer)vector.get(3)).intValue();
      this._$26696[b1][3] = ((Integer)vector.get(4) == null) ? 0 : ((Integer)vector.get(4)).intValue();
      this._$26696[b1][4] = 0;
      this._$26695[b1] = showsoldierWhereToStand(this._$26694[b1].getTime());
    } 
    this._$26699 = new Date[this._$26698.size()];
    this._$26700 = new int[this._$26698.size()];
    this._$26702 = new int[this._$26698.size()];
    this._$26701 = new int[this._$26698.size()][2];
    this._$26703 = showsoldierRangeToDefence(this.para.MaxNum) - 25;
    for (byte b2 = 0; b2 < this._$26698.size(); b2++) {
      Vector vector = this._$26698.get(b2);
      this._$26699[b2] = vector.get(0);
      this._$26701[b2][0] = ((Integer)vector.get(1) == null) ? 0 : ((Integer)vector.get(1)).intValue();
      this._$26701[b2][1] = ((Integer)vector.get(2) == null) ? 0 : ((Integer)vector.get(2)).intValue();
      int i = Arrays.binarySearch((Object[])this._$26694, this._$26699[b2]);
      if (i < 0)
        i = Math.abs(i) - 2; 
      this._$26702[b2] = -1;
      for (int j = i; j >= 0; j--) {
        if (this._$26696[j][4] == 1) {
          this._$26702[b2] = -1;
          break;
        } 
        if (this._$26701[b2][0] == this._$26696[j][0] && this._$26696[j][2] <= 2) {
          if (this._$26696[j][3] > 60000) {
            this._$26702[b2] = -2;
          } else {
            this._$26702[b2] = showsoldierWhereToStand(this._$26694[j].getTime());
          } 
          this._$26696[j][4] = 1;
          break;
        } 
      } 
      this._$26700[b2] = showsoldierWhereToStand(this._$26699[b2].getTime());
    } 
  }
  
  public void setData(Vector paramVector1, Vector paramVector2) throws Exception {
    this._$26693 = paramVector1;
    this._$26698 = paramVector2;
    resetScale();
  }
  
  public void setData(Vector paramVector) throws Exception {}
  
  public void setDataLineColor(Color paramColor) throws Exception {}
  
  public void setDrawCurve(boolean paramBoolean) {
    this._$18218 = paramBoolean;
  }
  
  public void setStroke(Stroke paramStroke) {}
  
  private void _$18223() {
    if (this._$26694.length == 0) {
      drawBasicMessage();
    } else {
      Stroke stroke = this.g2.getStroke();
      this.g2.setStroke(this._$18036);
      if (isFollowMode()) {
        _$18225();
      } else {
        _$18226();
      } 
      this.g2.setStroke(stroke);
    } 
  }
  
  private void _$18225() {
    for (byte b1 = 0; b1 < this._$26694.length && this._$26695[b1] <= DrawBase.mouseX + 600; b1++) {
      if (this._$26695[b1] >= DrawBase.mouseX - 600) {
        switch (this._$26696[b1][2]) {
          case 0:
            this.g2.setColor(this._$26704[0]);
            break;
          case 1:
            this.g2.setColor(this._$26704[1]);
            break;
          case 2:
            this.g2.setColor(this._$26704[2]);
            break;
          case 3:
            this.g2.setColor(this._$26704[3]);
            break;
          case 4:
            this.g2.setColor(this._$26704[4]);
            break;
          default:
            this.g2.setColor(this._$26704[5]);
            break;
        } 
        this.g2.drawString("" + this._$26696[b1][3], this._$26695[b1], this._$26697 - 5);
        _$26653(b1);
      } 
    } 
    for (byte b2 = 0; b2 < this._$26699.length && this._$26700[b2] <= DrawBase.mouseX; b2++) {
      if (this._$26700[b2] >= DrawBase.mouseX - 300) {
        if (this._$26702[b2] == -1) {
          this.g2.setColor(Color.red);
          this.g2.drawString("?", this._$26700[b2], this._$26703);
        } else {
          this.g2.setColor(Color.orange);
          this.g2.drawLine(this._$26702[b2], this._$26703, this._$26700[b2], this._$26703);
        } 
        this.g2.drawString("" + this._$26701[b2][1], this._$26700[b2], this._$26703 + 14);
        _$26708(b2);
      } 
    } 
  }
  
  private void _$18226() {
    for (byte b1 = 0; b1 < this._$26694.length; b1++) {
      switch (this._$26696[b1][2]) {
        case 0:
          this.g2.setColor(this._$26704[0]);
          break;
        case 1:
          this.g2.setColor(this._$26704[1]);
          break;
        case 2:
          this.g2.setColor(this._$26704[2]);
          break;
        case 3:
          this.g2.setColor(this._$26704[3]);
          break;
        case 4:
          this.g2.setColor(this._$26704[4]);
          break;
        default:
          this.g2.setColor(this._$26704[5]);
          break;
      } 
      if (this._$26696[b1][3] <= 60000) {
        if (b1 > 0) {
          if (this._$26695[b1] - this._$26695[b1 - 1] < 20 && !this._$26705) {
            this.g2.drawString("" + this._$26696[b1][3], this._$26695[b1], this._$26697 - 14);
            this._$26705 = true;
          } else {
            this.g2.drawString("" + this._$26696[b1][3], this._$26695[b1], this._$26697 - 5);
            this._$26705 = false;
          } 
        } else {
          this.g2.drawString("" + this._$26696[b1][3], this._$26695[b1], this._$26697 - 5);
        } 
        if (this._$26696[b1][3] == 550)
          this.g2.drawString("*", this._$26695[b1], this._$26697 + 3); 
        _$26653(b1);
      } 
    } 
    for (byte b2 = 0; b2 < this._$26699.length; b2++) {
      if (this._$26702[b2] == -1) {
        this.g2.setColor(Color.red);
        this.g2.drawString("?", this._$26700[b2], this._$26703);
      } else {
        if (this._$26702[b2] == -2)
          continue; 
        this.g2.setColor(Color.orange);
        this.g2.drawLine(this._$26702[b2], this._$26703, this._$26700[b2], this._$26703);
      } 
      this.g2.drawString("" + this._$26701[b2][1], this._$26700[b2], this._$26703 + 14);
      _$26708(b2);
      continue;
    } 
  }
}
