package Tools;

import java.awt.Component;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

public class UnknowProgressMonitor {
  private long _$16373;
  
  private Object[] _$16372 = null;
  
  private JDialog _$2033;
  
  private int _$16376;
  
  private int _$2045;
  
  private Object _$2040;
  
  private int _$16374 = 500;
  
  private int _$16375 = 2000;
  
  private int _$2044;
  
  private JProgressBar _$16369;
  
  private String _$2038;
  
  private JLabel _$16370;
  
  private JOptionPane _$16368;
  
  private Component _$16371;
  
  private int _$16377;
  
  private com.MiTAC.TRA.ATP.Tools.UnknowProgressMonitor _$2032;
  
  private int _$15543;
  
  private UnknowProgressMonitor(Component paramComponent, Object paramObject, String paramString, int paramInt1, int paramInt2, com.MiTAC.TRA.ATP.Tools.UnknowProgressMonitor paramUnknowProgressMonitor) {
    this._$2044 = paramInt1;
    this._$2045 = paramInt2;
    this._$16371 = paramComponent;
    this._$16372 = new Object[1];
    this._$16372[0] = UIManager.getString("OptionPane.cancelButtonText");
    this._$16377 = (paramInt2 - paramInt1) / 100;
    if (this._$16377 < 1)
      this._$16377 = 1; 
    this._$15543 = paramInt1;
    this._$2040 = paramObject;
    this._$2038 = paramString;
    if (paramUnknowProgressMonitor != null) {
      this._$2032 = (paramUnknowProgressMonitor._$2032 != null) ? paramUnknowProgressMonitor._$2032 : paramUnknowProgressMonitor;
      this._$16373 = this._$2032._$16373;
      this._$2033 = this._$2032._$2033;
    } else {
      this._$16373 = System.currentTimeMillis();
    } 
  }
  
  public UnknowProgressMonitor(Component paramComponent, Object paramObject, String paramString, int paramInt1, int paramInt2) {
    this(paramComponent, paramObject, paramString, paramInt1, paramInt2, null);
    this._$16369 = new JProgressBar();
    this._$16369.setIndeterminate(true);
    if (paramString != null)
      this._$16370 = new JLabel(paramString); 
    this._$16368 = (JOptionPane)new ProgressOptionPane(this, new Object[] { paramObject, this._$16369, this._$16370 });
    this._$2033 = this._$16368.createDialog(paramComponent, UIManager.getString("ProgressMonitor.progressText"));
    this._$2033.setModal(true);
  }
  
  public void close() {
    if (this._$2033 != null) {
      this._$2033.setVisible(false);
      this._$2033.dispose();
      this._$2033 = null;
      this._$16368 = null;
      this._$16369 = null;
    } 
  }
  
  public int getMillisToDecideToPopup() {
    return this._$16374;
  }
  
  public int getMillisToPopup() {
    return this._$16375;
  }
  
  public String getNote() {
    return this._$2038;
  }
  
  public boolean isCanceled() {
    if (this._$16368 == null)
      return false; 
    Object object = this._$16368.getValue();
    return (object != null && this._$16372.length == 1 && object.equals(this._$16372[0]));
  }
  
  public void setMillisToDecideToPopup(int paramInt) {
    this._$16374 = paramInt;
  }
  
  public void setMillisToPopup(int paramInt) {
    this._$16375 = paramInt;
  }
  
  public void setNote(String paramString) {
    this._$2038 = paramString;
    if (this._$16370 != null)
      this._$16370.setText(paramString); 
  }
  
  public void setPorgessFinished() {
    this._$16369.setIgnoreRepaint(false);
    this._$16369.setValue(100);
    this._$16369.repaint();
    this._$16368.setEnabled(false);
    JOptionPane.showMessageDialog(this._$16371, "test", "note", 1);
    close();
  }
  
  public void show() {
    this._$2033.show();
  }
}
