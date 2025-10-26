// 
// Decompiled by Procyon v0.6.0
// 

package com.MiTAC.TRA.ATP;

import java.util.MissingResourceException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ATPMessages
{
    private static final String _$193 = "ATPMessages";
    private static ResourceBundle _$194;
    public static boolean showChinese;
    
    static {
        ATPMessages.showChinese = true;
        ATPMessages._$194 = ResourceBundle.getBundle("ATPMessages", ATPMessages.showChinese ? Locale.TAIWAN : Locale.US);
    }
    
    private ATPMessages() {
    }
    
    public static void changeLocale(final String s) {
        if (s.equals("us") || s.equals("US")) {
            ATPMessages.showChinese = false;
        }
        else {
            ATPMessages.showChinese = true;
        }
        ATPMessages._$194 = ResourceBundle.getBundle("ATPMessages", ATPMessages.showChinese ? Locale.TAIWAN : Locale.US);
    }
    
    public static String getString(final String s) {
        try {
            return ATPMessages._$194.getString(s);
        }
        catch (final MissingResourceException ex) {
            return '!' + s + '!';
        }
    }
}
