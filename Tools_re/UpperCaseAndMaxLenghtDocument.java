package Tools;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class UpperCaseAndMaxLenghtDocument extends PlainDocument {
  private int _$2045;
  
  private boolean _$14641 = true;
  
  public UpperCaseAndMaxLenghtDocument(int paramInt) {
    this._$2045 = paramInt;
  }
  
  public UpperCaseAndMaxLenghtDocument() {}
  
  public void insertString(int paramInt, String paramString, AttributeSet paramAttributeSet) throws BadLocationException {
    if (getLength() + paramString.length() > this._$2045 || !this._$14641) {
      Toolkit.getDefaultToolkit().beep();
    } else {
      paramString = paramString.toUpperCase();
      super.insertString(paramInt, paramString, paramAttributeSet);
    } 
  }
  
  public void setUpperCase(boolean paramBoolean) {
    this._$14641 = paramBoolean;
  }
}
