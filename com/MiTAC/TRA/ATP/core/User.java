// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP.core;

public class User
{
    private String _$29792;
    private String _$29793;
    private String _$29794;
    private boolean _$29795;
    
    public User(final String s, final String s2) throws Exception {
        this._$29795 = false;
        if (!validUserAndPWD(s, s2)) {
            throw new Exception("\u8acb\u8f38\u5165\u6b63\u78ba\u7684\u4f7f\u7528\u8005\u540d\u7a31\u53ca\u5bc6\u78bc!");
        }
        this._$29797();
    }
    
    public User() {
        this._$29795 = false;
    }
    
    public boolean checkPWD(final String s) {
        return validUserAndPWD(this._$29792, s);
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
    
    private void _$29797() {
    }
    
    public void setUser(final String $29792) {
        this._$29792 = $29792;
    }
    
    public static boolean validUserAndPWD(final String s, final String s2) {
        return false;
    }
}
