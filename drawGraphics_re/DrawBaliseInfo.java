package drawGraphics;

import com.MiTAC.TRA.ATP.ATPMessages;
import com.MiTAC.TRA.ATP.core.ATPMissionDetail;
import com.MiTAC.TRA.ATP.drawGraphics.DrawBase;
import com.MiTAC.TRA.ATP.drawGraphics.commonParaSetting;
import com.MiTAC.TRA.ATP.drawGraphics.drawATP;
import com.MiTAC.TRA.ATP.drawGraphics.drawParameters;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class DrawBaliseInfo extends DrawBase implements drawATP {
  private Vector _$26624;
  
  private Vector _$26622;
  
  private Vector _$26623;
  
  private Vector _$26626;
  
  private int[] _$26628;
  
  private int _$26629;
  
  private Vector _$26625;
  
  private Vector _$26620;
  
  private Color _$18217;
  
  private boolean _$18218 = true;
  
  private final int _$5541 = 0;
  
  private ATPMissionDetail _$18234;
  
  private final int _$26670 = 7;
  
  private final int _$5533 = 6;
  
  private final int _$26672 = 9;
  
  private final int _$26669 = 5;
  
  private final int _$26668 = 4;
  
  private final int _$26667 = 3;
  
  private SimpleDateFormat _$3906 = new SimpleDateFormat("HH:mm:ss");
  
  private boolean _$26627 = true;
  
  private final String[] _$26665 = new String[] { "G", "Y", "Yf", "RYf", "RY", "Rf", "R", "Off", "YY", "R&O" };
  
  private Vector _$26621;
  
  private Stroke _$18036 = new BasicStroke();
  
  private Vector _$4436;
  
  private final int _$5539 = 1;
  
  private final int _$26666 = 2;
  
  private final int _$26671 = 8;
  
  public DrawBaliseInfo(commonParaSetting paramcommonParaSetting, drawParameters paramdrawParameters, Vector paramVector) {
    super(paramcommonParaSetting, paramdrawParameters, paramVector);
  }
  
  public void drawStopInformation(Date paramDate) {
    if (this._$26624.size() != 0) {
      int i = Arrays.binarySearch(this._$4436.toArray(), paramDate);
      i = (i >= 0) ? i : ((Math.abs(i) - 2 < 0) ? 0 : (Math.abs(i) - 2));
      int j = ((Integer)this._$26624.get(i)).intValue();
      int k = Arrays.binarySearch(this._$26620.toArray(), paramDate);
      k = (k >= 0) ? k : ((Math.abs(k) - 2 < 0) ? 0 : (Math.abs(k) - 2));
      int m = ((Integer)this._$26625.get(k)).intValue();
      int n = m - j;
      this.g2.setColor(Color.CYAN);
      long l = ((Date)this._$4436.get(i)).getTime();
      int i1 = showsoldierWhereToStand(l);
      int i2 = arrangesoldierPosition(paramDate.getTime());
      int i3 = showsoldierRangeToDefence(this.para.MaxNum) + 12;
      int i4 = showsoldierRangeToDefence(this.para.MinNum) + 2;
      this.g2.drawLine(i1, i3, i2, i3);
      this.g2.drawLine(i1, i3 + 3, i1, i3 - 3);
      this.g2.drawLine(i2, i3 - 3, i2, i4);
      this.g2.drawLine(i2 + 3, i4 + 3, i2, i4);
      this.g2.drawLine(i2 - 3, i4 + 3, i2, i4);
      int i5 = Arrays.binarySearch(this._$26621.toArray(), paramDate);
      int i6 = (i5 >= 0) ? i5 : (Math.abs(i5) - 1);
      if (i6 < this._$26621.size()) {
        k = Arrays.binarySearch(this._$26620.toArray(), this._$26621.get(i6));
        int i7 = ((Integer)this._$26625.get(k)).intValue();
        int i8 = i7 - m;
        i1 = i2;
        i2 = arrangesoldierPosition(((Date)this._$26621.get(i6)).getTime());
        this.g2.setColor(Color.RED);
        this.g2.drawLine(i1, i3, i2, i3);
        this.g2.drawLine(i1, i3 + 3, i1, i3 - 3);
        this.g2.drawLine(i2, i3 - 3, i2, i4);
        this.g2.drawLine(i2 + 3, i4 + 3, i2, i4);
        this.g2.drawLine(i2 - 3, i4 + 3, i2, i4);
        long l1 = ((Date)this._$26621.get(i6)).getTime() - paramDate.getTime();
        this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.TRAIN.STOP") + (i8 / 100) + " m, " + (l1 / 1000L) + ATPMessages.getString("MW.BEHAVIOR.TRAIN.STOP.SECOND") + " @ " + this._$3906.format(this._$26621.get(i6)), i1 + 1, i3 - 1);
      } 
    } 
  }
  
  private void _$26653(int paramInt) {
    int[] arrayOfInt1 = { this._$26628[paramInt], this._$26628[paramInt] - 2, this._$26628[paramInt] + 4 };
    int[] arrayOfInt2 = { this._$26629 - 2, this._$26629 + 2, this._$26629 + 2 };
    this.g2.fillPolygon(arrayOfInt1, arrayOfInt2, 3);
  }
  
  private void _$26653(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (paramInt2 < 16380) {
      int i = this._$26628[paramInt1];
      int j = this._$26629 + 5;
      double d = 1.5707963267948966D;
      this.g2.translate(i, j);
      this.g2.rotate(d);
      _$26658(paramInt1, paramInt2, paramInt3);
      this.g2.drawString((paramInt4 < 0 || paramInt4 > 9) ? ("" + paramInt4) : this._$26665[paramInt4], -55, 0);
      this.g2.rotate(-d);
      _$26664(paramInt4);
      this.g2.translate(-i, -j);
      _$26653(paramInt1);
    } 
  }
  
  private void _$26653(int paramInt1, int paramInt2) {
    _$26653(paramInt1);
    double d = 0.7853981633974483D;
    int i = this._$26628[paramInt1];
    int j = this._$26629 + 15;
    this.g2.translate(i, j);
    this.g2.rotate(d);
    if (paramInt2 <= 16380) {
      this.g2.drawString("" + paramInt2, 0, 0);
    } else {
      this.g2.drawString("" + paramInt2, -50, -20);
    } 
    this.g2.rotate(-d);
    this.g2.translate(-i, -j);
  }
  
  private Vector _$26631() {
    Vector vector = new Vector();
    for (byte b = 0; b < this._$18234.getBTM().size() - 1; b++) {
      Vector vector1 = this._$18234.getBTM().get(b);
      int i = ((Integer)vector1.get(6)).intValue();
      long l1 = ((Date)vector1.get(5)).getTime();
      Vector vector2 = this._$18234.getBTM().get(b + 1);
      int j = ((Integer)vector2.get(6)).intValue();
      long l2 = ((Date)vector2.get(5)).getTime();
      if (i < 16380 && j > 16380 && l2 - l1 < 4000L) {
        vector.add(vector1);
        vector.add(vector2);
        b++;
      } else if (j < 16380 && i > 16380 && l2 - l1 < 4000L) {
        b++;
      } 
    } 
    return vector;
  }
  
  public Object getMousePointStatus() throws Exception {
    int i = Arrays.binarySearch(this._$4436.toArray(), new Date(soloderReport()));
    if (i < 0)
      try {
        return this._$26622.get(Math.abs(i) - 2);
      } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        return this._$26622.get(this._$26622.size() - 1);
      } catch (Exception exception) {
        return ATPMessages.getString("MW.GNL.ERROR");
      }  
    return this._$26622.get(i);
  }
  
  public void paintBody(Graphics paramGraphics) throws Exception {
    super.paintBody(paramGraphics);
    this.g2 = (Graphics2D)paramGraphics;
    _$18223();
    _$26640();
  }
  
  public void paintHeader(Graphics paramGraphics) throws Exception {
    super.paintHeader(paramGraphics);
    this.g2 = (Graphics2D)paramGraphics;
  }
  
  private void _$26658(int paramInt1, int paramInt2, int paramInt3) {
    this.g2.setColor(Color.green);
    if (paramInt2 == paramInt3) {
      this.g2.drawString("*", 25, 0);
      this.g2.drawString("" + paramInt2, 0, 0);
    } else if (paramInt3 == 0) {
      this.g2.setColor(Color.red);
      this.g2.drawString("" + paramInt2, 0, 0);
      this.g2.setColor(Color.pink);
      this.g2.drawLine(-2, 0, -2, -10);
      this.g2.drawString("DBn/F", 0, -10);
      this.g2.setColor(Color.green);
    } else {
      this.g2.setColor(this.g2.getColor().darker().darker());
      this.g2.drawString("" + paramInt2, 0, 0);
      byte b1 = 10;
      byte b2 = (paramInt1 - b1 > 0) ? (paramInt1 - b1) : 0;
      int i = (paramInt1 + b1 < this._$26622.size()) ? (paramInt1 + b1) : this._$26622.size();
      byte b = -1;
      for (byte b3 = b2; b3 < i; b3++) {
        int j = ((Integer)this._$26622.get(b3)).intValue();
        if (j == paramInt3) {
          b = b3;
          break;
        } 
      } 
      if (b == -1) {
        this.g2.setColor(Color.pink);
        this.g2.drawLine(-2, 0, -2, -10);
        this.g2.drawString(" " + paramInt3, 0, -10);
        this.g2.setColor(Color.green);
      } else {
        this.g2.drawLine(-2, 0, -2, -Math.abs(this._$26628[b] - this._$26628[paramInt1]));
      } 
    } 
  }
  
  public void resetScale() {
    Vector vector = _$26631();
    this._$4436 = new Vector();
    this._$26622 = new Vector();
    this._$26623 = new Vector();
    this._$26624 = new Vector();
    this._$26626 = new Vector();
    this._$26628 = new int[vector.size()];
    this._$26629 = showsoldierRangeToDefence(this.para.MinNum);
    for (byte b1 = 0; b1 < vector.size(); b1++) {
      Vector vector1 = vector.get(b1);
      this._$4436.add(vector1.get(5));
      this._$26622.add(vector1.get(6));
      this._$26623.add(vector1.get(9));
      this._$26624.add(vector1.get(7));
      int j = ((Integer)vector1.get(8)).intValue();
      if (((Integer)vector1.get(6)).intValue() == 2025)
        System.out.println(vector1); 
      int k = 0;
      for (byte b = 0; b < 10; b++) {
        if (((Integer)vector1.get(10 + b)).intValue() != 0) {
          k = -1;
          break;
        } 
        k += ((Integer)vector1.get(10 + b)).intValue();
      } 
      if (k == 0) {
        this._$26626.add(new Integer(-(j + 10)));
        this._$26628[b1] = showsoldierWhereToStand(((Date)this._$4436.get(b1)).getTime());
      } else {
        if (j > 9) {
          this._$26626.add(new Integer(-j));
        } else {
          try {
            this._$26626.add(vector1.get(10 + j));
          } catch (Exception exception) {
            this._$26626.add(new Integer(-j));
            exception.printStackTrace();
          } 
        } 
        this._$26628[b1] = showsoldierWhereToStand(((Date)this._$4436.get(b1)).getTime());
      } 
    } 
    this._$26620 = new Vector();
    this._$26621 = new Vector();
    this._$26625 = new Vector();
    int i = 0;
    for (byte b2 = 0; b2 < this._$18234.getTimeStamp().size(); b2++) {
      Vector vector1 = this._$18234.getTimeStamp().get(b2);
      this._$26620.add(vector1.get(5));
      this._$26625.add(vector1.get(7));
      boolean bool = false;
      if (i && ((Integer)vector1.get(6)).intValue() == 0)
        bool = true; 
      if (bool)
        this._$26621.add(vector1.get(5)); 
      i = ((Integer)vector1.get(6)).intValue();
    } 
  }
  
  public void setData(ATPMissionDetail paramATPMissionDetail) {
    this._$18234 = paramATPMissionDetail;
    resetScale();
  }
  
  public void setData(Vector paramVector) {}
  
  public void setDataLineColor(Color paramColor) throws Exception {
    this._$18217 = paramColor;
  }
  
  public void setDrawCurve(boolean paramBoolean) {
    this._$18218 = paramBoolean;
  }
  
  public void setDrawType(int paramInt) {
    super.setDrawType(paramInt);
    if (paramInt != DrawBase.drawType)
      resetScale(); 
  }
  
  public void setStroke(Stroke paramStroke) {
    this._$18036 = paramStroke;
  }
  
  private void _$26664(int paramInt) {
    int[] arrayOfInt1 = { -28, -23, -18, -13 };
    Color[] arrayOfColor = { Color.green, Color.yellow, Color.red, Color.yellow };
    int[] arrayOfInt2 = { 0, 0, 0, 0 };
    switch (paramInt) {
      case 0:
        arrayOfInt2 = new int[] { 1, 0, 0, 0 };
        break;
      case 1:
        arrayOfInt2 = new int[] { 0, 1, 0, 0 };
        break;
      case 2:
        arrayOfInt2 = new int[] { 0, 2, 0, 0 };
        break;
      case 3:
        arrayOfInt2 = new int[] { 0, 0, 1, 2 };
        break;
      case 4:
        arrayOfInt2 = new int[] { 0, 0, 1, 1 };
        break;
      case 5:
        arrayOfInt2 = new int[] { 0, 0, 2, 0 };
        break;
      case 6:
        arrayOfInt2 = new int[] { 0, 0, 1, 0 };
        break;
      case 7:
        arrayOfInt2 = new int[] { 0, 0, 0, 0 };
        break;
      case 8:
        arrayOfInt2 = new int[] { 0, 1, 0, 1 };
        break;
      case 9:
        break;
      default:
        arrayOfInt2 = new int[] { 3, 3, 3, 3 };
        break;
    } 
    this.g2.setColor(Color.gray.darker().darker().darker());
    this.g2.fillRect(0, arrayOfInt1[0] - 2, 6, 25);
    this.g2.drawLine(1, arrayOfInt1[0] - 3, 4, arrayOfInt1[0] - 3);
    for (byte b = 0; b < arrayOfInt2.length; b++) {
      switch (arrayOfInt2[b]) {
        case 0:
          break;
        case 1:
        case 2:
        case 3:
          this.g2.setColor(Color.blue.darker().darker());
          this.g2.fillOval(0, arrayOfInt1[b] - 1, 5, 6);
          this.g2.setColor(arrayOfColor[b].brighter());
          this.g2.fillOval(0, arrayOfInt1[b], 4, 4);
          this.g2.setColor(Color.blue.darker());
          this.g2.drawLine(0, arrayOfInt1[b] - 1, 3, arrayOfInt1[b] - 1);
          this.g2.setColor(Color.blue.darker().darker());
          this.g2.drawLine(2, arrayOfInt1[b], 4, arrayOfInt1[b]);
          break;
        default:
          this.g2.setColor(Color.green.darker().darker());
          this.g2.fillOval(0, arrayOfInt1[b] - 1, 5, 5);
          this.g2.setColor(Color.red);
          this.g2.drawString("x", 0, arrayOfInt1[b]);
          break;
      } 
    } 
    this.g2.setColor(Color.green);
  }
  
  private void _$18223() throws Exception {
    if (this._$4436.size() == 0) {
      drawBasicMessage();
    } else {
      this.g2.setColor(this._$18217);
      if (isFollowMode()) {
        _$18225();
      } else {
        _$18226();
      } 
    } 
  }
  
  private void _$18225() {
    for (byte b = 0; b < this._$26622.size() && this._$26628[b] <= DrawBase.mouseX + 600; b++) {
      if (this._$26628[b] >= DrawBase.mouseX - 600)
        if (this._$26627) {
          if (this._$26626.get(b) == null) {
            _$26653(b, (new Integer(this._$26622.get(b).toString())).intValue());
          } else {
            _$26653(b, (new Integer(this._$26622.get(b).toString())).intValue(), (new Integer(this._$26623.get(b).toString())).intValue(), (new Integer(this._$26626.get(b).toString())).intValue());
          } 
        } else {
          _$26653(b, (new Integer(this._$26622.get(b).toString())).intValue());
        }  
    } 
  }
  
  private void _$18226() {
    for (byte b = 0; b < this._$26622.size(); b++) {
      if (this._$26627) {
        _$26653(b, (new Integer(this._$26622.get(b).toString())).intValue(), (new Integer(this._$26623.get(b).toString())).intValue(), (new Integer(this._$26626.get(b).toString())).intValue());
      } else {
        _$26653(b, (new Integer(this._$26622.get(b).toString())).intValue());
      } 
    } 
  }
  
  private void _$26640() throws Exception {
    if (this._$26624.size() != 0) {
      int i = Arrays.binarySearch(this._$4436.toArray(), new Date(soloderReport()));
      i = (i >= 0) ? i : ((Math.abs(i) - 2 < 0) ? 0 : (Math.abs(i) - 3));
      int j = ((Integer)this._$26624.get(i)).intValue();
      int k = Arrays.binarySearch(this._$26620.toArray(), new Date(soloderReport()));
      k = (k >= 0) ? k : ((Math.abs(k) - 2 < 0) ? 0 : (Math.abs(k) - 2));
      int m = ((Integer)this._$26625.get(k)).intValue();
      int n = m - j;
      long l = ((Date)this._$4436.get(i)).getTime();
      int i1 = showsoldierWhereToStand(l);
      int i2 = arrangesoldierPosition(soloderReport());
      int i3 = showsoldierRangeToDefence(this.para.MaxNum) - 11;
      int i4 = showsoldierRangeToDefence(this.para.MinNum) - 2;
      this.g2.drawLine(i1, i3, i2, i3);
      this.g2.drawLine(i1, i3 - 5, i1, i3 + 5);
      this.g2.drawLine(i2, i3 + 3, i2, i4);
      this.g2.drawLine(i2 - 5, i4 - 5, i2, i4);
      this.g2.drawLine(i2 + 5, i4 - 5, i2, i4);
      this.g2.setColor(new Color(Color.blue.getRed(), Color.blue.getGreen(), Color.blue.getBlue(), 220));
      if (!this.outOfView) {
        this.g2.fillRect(i2 - 14, i3 - 24, 210, 24);
        this.g2.setColor(Color.ORANGE);
        this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.DISTANCE_TO_BALISE") + ": " + (n / 100) + "m", i2 - 10, i3 - 3);
        this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.BALISE_ID") + ": " + this._$26622.get(i), i2 - 10, i3 - 14);
        this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.TRAIN_POSITION") + ": " + (m / 100), i2 - 10 + 100, i3 - 3);
        this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.BALISE_POSITION") + ": " + (j / 100), i2 - 10 + 100, i3 - 14);
      } else {
        this.g2.fillRect(i2 - 14 - 250, i3 - 24, 210, 24);
        this.g2.setColor(Color.ORANGE);
        this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.DISTANCE_TO_BALISE") + ": " + (n / 100) + "m", i2 - 10 - 250, i3 - 3);
        this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.BALISE_ID") + ": " + this._$26622.get(i), i2 - 10 - 250, i3 - 14);
        this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.TRAIN_POSITION") + ": " + (m / 100), i2 - 10 + 100 - 250, i3 - 3);
        this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.BALISE_POSITION") + ": " + (j / 100), i2 - 10 + 100 - 250, i3 - 14);
      } 
      this.g2.drawLine(i1, i3, i2, i3);
      this.g2.drawLine(i1, i3 - 5, i1, i3 + 5);
      this.g2.drawLine(i2, i3 + 3, i2, i4);
      this.g2.drawLine(i2 - 3, i4 - 3, i2, i4);
      this.g2.drawLine(i2 + 3, i4 - 3, i2, i4);
      int i5 = Arrays.binarySearch(this._$26621.toArray(), new Date(soloderReport()));
      int i6 = (i5 >= 0) ? i5 : (Math.abs(i5) - 1);
      if (i6 < this._$26621.size()) {
        k = Arrays.binarySearch(this._$26620.toArray(), this._$26621.get(i6));
        int i7 = ((Integer)this._$26625.get(k)).intValue();
        int i8 = i7 - m;
        i1 = i2;
        i2 = arrangesoldierPosition(((Date)this._$26621.get(i6)).getTime());
        this.g2.setColor(Color.RED);
        this.g2.drawLine(i1, i3, i2, i3);
        this.g2.drawLine(i1, i3 + 3, i1, i3 - 3);
        this.g2.drawLine(i2, i3 - 3, i2, i4);
        this.g2.drawLine(i2 - 3, i4 - 3, i2, i4);
        this.g2.drawLine(i2 + 3, i4 - 3, i2, i4);
        long l1 = ((Date)this._$26621.get(i6)).getTime() - soloderReport();
        if (!this.outOfView) {
          this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.TRAIN.STOP") + (i8 / 100) + " m, " + (l1 / 1000L) + ATPMessages.getString("MW.BEHAVIOR.TRAIN.STOP.SECOND") + " @ " + this._$3906.format(this._$26621.get(i6)), i1 + 5, i3 + 10);
        } else {
          this.g2.drawString(ATPMessages.getString("MW.BEHAVIOR.TRAIN.STOP") + (i8 / 100) + " m, " + (l1 / 1000L) + ATPMessages.getString("MW.BEHAVIOR.TRAIN.STOP.SECOND") + " @ " + this._$3906.format(this._$26621.get(i6)), i1 - 250, i3 + 10);
        } 
      } 
    } 
  }
}
