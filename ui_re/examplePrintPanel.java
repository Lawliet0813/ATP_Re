package ui;

import com.MiTAC.Tools.StrokeAndColor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

class examplePrintPanel extends JPanel implements ActionListener {
  static final int CHOOSE_BACKGROUND_COLOR = 1;
  
  static final int CHOOSE_CHAR_COLOR = 4;
  
  static final int CHOOSE_LINE_COLOR = 5;
  
  static final int CHOOSE_LINE_COLOR_AND_STROKE = 2;
  
  static final int CHOOSE_SQUARE_COLOR = 3;
  
  Color backgroundColor = Color.black;
  
  JButton btnStyle = new JButton("...");
  
  Color initColor = new Color(0, 255, 0, 135);
  
  Stroke initStroke;
  
  Color newBackgroundColor = Color.black;
  
  Color newColor = new Color(0, 255, 0, 135);
  
  Stroke newStroke = this.initStroke;
  
  private static final long serialVersionUID = 1L;
  
  int task = 2;
  
  public examplePrintPanel(Color paramColor1, Color paramColor2, int paramInt) {
    int[] arrayOfInt = { 3, 4, 5 };
    this.task = paramInt;
    if (Arrays.binarySearch(arrayOfInt, paramInt) < 0)
      paramInt = 3; 
    this.backgroundColor = paramColor1;
    this.newBackgroundColor = this.backgroundColor;
    this.initColor = paramColor2;
    this.newColor = this.initColor;
    _$3120();
  }
  
  public examplePrintPanel(Color paramColor) {
    this.task = 1;
    this.backgroundColor = paramColor;
    this.newBackgroundColor = this.backgroundColor;
    _$3120();
  }
  
  public examplePrintPanel(Stroke paramStroke, Color paramColor1, Color paramColor2) {
    this.task = 2;
    this.initColor = paramColor1;
    this.initStroke = paramStroke;
    this.backgroundColor = paramColor2;
    this.newBackgroundColor = this.backgroundColor;
    this.newColor = this.initColor;
    this.newStroke = this.initStroke;
    _$3120();
  }
  
  public examplePrintPanel(StrokeAndColor paramStrokeAndColor, Color paramColor) {
    this(paramStrokeAndColor.getStroke(), paramStrokeAndColor.getColor(), paramColor);
  }
  
  public examplePrintPanel() {
    _$3120();
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    Color color = JColorChooser.showDialog(this, "select color", this.initColor);
    switch (this.task) {
      case 1:
        this.newBackgroundColor = color;
        firePropertyChange(getName(), this.backgroundColor, this.newBackgroundColor);
        if (this.backgroundColor != this.newBackgroundColor)
          repaint(); 
        break;
      case 2:
      case 3:
      case 4:
      case 5:
        this.newColor = color;
        firePropertyChange(getName(), this.initColor, this.newColor);
        if (this.initColor != this.newColor)
          repaint(); 
        break;
    } 
  }
  
  public void callbackSetting() {
    this.newColor = this.initColor;
    this.newBackgroundColor = this.backgroundColor;
    repaint();
  }
  
  public Color getColor() {
    return (this.task == 1) ? this.newBackgroundColor : this.newColor;
  }
  
  public Stroke getStroke() {
    return this.newStroke;
  }
  
  private void _$3120() {
    setPreferredSize(new Dimension(63, 17));
    setOpaque(true);
    this.btnStyle.addActionListener(this);
    this.btnStyle.setPreferredSize(new Dimension(13, 17));
    setLayout(new BorderLayout());
    add(this.btnStyle, "East");
  }
  
  public void paintComponent(Graphics paramGraphics) {
    super.paintComponent(paramGraphics);
    Graphics2D graphics2D = (Graphics2D)paramGraphics;
    graphics2D.setColor(this.newBackgroundColor);
    graphics2D.fillRect(0, 0, 100, 22);
    if (this.task == 2)
      graphics2D.setStroke(this.newStroke); 
    if (this.task != 1)
      graphics2D.setColor(this.newColor); 
    if (this.task == 4)
      graphics2D.drawString("文字", 0, 12); 
    if ((((this.task != 1) ? 1 : 0) ^ ((this.task != 4) ? 1 : 0) ^ ((this.task != 3) ? 1 : 0)) != 0)
      graphics2D.drawLine(0, 10, 100, 10); 
    if (this.task == 3)
      graphics2D.fillRect(0, 10, 100, 10); 
  }
  
  public void setBackGroundColor(Color paramColor) {
    this.newBackgroundColor = paramColor;
    repaint();
  }
}
