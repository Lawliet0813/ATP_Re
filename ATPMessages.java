import com.MiTAC.TRA.ATP.ATPMessages;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ATPMessages {
  private static final String _$193 = "ATPMessages";
  
  private static ResourceBundle _$194;
  
  public static boolean showChinese = true;
  
  static {
    _$194 = ResourceBundle.getBundle("ATPMessages", showChinese ? Locale.TAIWAN : Locale.US);
  }
  
  public static void changeLocale(String paramString) {
    if (paramString.equals("us") || paramString.equals("US")) {
      showChinese = false;
    } else {
      showChinese = true;
    } 
    _$194 = ResourceBundle.getBundle("ATPMessages", showChinese ? Locale.TAIWAN : Locale.US);
  }
  
  public static String getString(String paramString) {
    try {
      return _$194.getString(paramString);
    } catch (MissingResourceException missingResourceException) {
      return '!' + paramString + '!';
    } 
  }
}
