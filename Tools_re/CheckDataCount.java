package Tools;

public class CheckDataCount {
  private int _$17169;
  
  private int _$17168;
  
  private boolean _$17172;
  
  private int _$17170;
  
  private int _$17171;
  
  public CheckDataCount(int paramInt1, int paramInt2, int paramInt3) {
    this._$17170 = paramInt1;
    this._$17168 = paramInt2;
    this._$17169 = paramInt3;
    _$869();
  }
  
  public CheckDataCount() {}
  
  private void _$869() {
    this._$17171 = this._$17168 + this._$17169;
  }
  
  public int getSum() {
    return this._$17171;
  }
  
  public boolean isMoreThanCount() {
    if (this._$17171 > this._$17170) {
      this._$17172 = true;
    } else {
      this._$17172 = false;
    } 
    return this._$17172;
  }
  
  public static void main(String[] paramArrayOfString) {}
  
  public void setCountOfDownload(int paramInt) {
    this._$17169 = paramInt;
  }
  
  public void setCountOfUSB(int paramInt) {
    this._$17168 = paramInt;
  }
  
  public void setMaxcount(int paramInt) {
    this._$17170 = paramInt;
  }
}
