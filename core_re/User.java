package core;

public class User {
  private String _$29792;
  
  private String _$29793;
  
  private String _$29794;
  
  private boolean _$29795 = false;
  
  public User(String paramString1, String paramString2) throws Exception {
    if (!validUserAndPWD(paramString1, paramString2))
      throw new Exception("請輸入正確的使用者名稱及密碼!"); 
    _$29797();
  }
  
  public User() {}
  
  public boolean checkPWD(String paramString) {
    return validUserAndPWD(this._$29792, paramString);
  }
  
  public String getPriority() {
    return this._$29793;
  }
  
  public String getTitle() {
    return this._$29794;
  }
  
  public String getUser() {
    return this._$29792;
  }
  
  private void _$29797() {}
  
  public void setUser(String paramString) {
    this._$29792 = paramString;
  }
  
  public static boolean validUserAndPWD(String paramString1, String paramString2) {
    return false;
  }
}
