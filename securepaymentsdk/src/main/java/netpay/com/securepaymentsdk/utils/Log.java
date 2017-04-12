package netpay.com.securepaymentsdk.utils;

public final class Log {

    private static final String NO_MSG = "NULL";
    private Log(){}

    public static final boolean SHOW_LOG = false;

    public static void i(String tag, String string) {
        if (SHOW_LOG) {
            if(string == null){
                android.util.Log.i(tag, NO_MSG);
            }else{
                android.util.Log.i(tag, string);
            }
        }
    }
    public static void e(String tag, String string) {
        if (SHOW_LOG) {
            if(string == null){
                android.util.Log.e(tag, NO_MSG);
            }else{
                android.util.Log.e(tag, string);
            }
        }
    }
    public static void d(String tag, String string) {
        if (SHOW_LOG) {
            if(string == null){
                android.util.Log.d(tag, NO_MSG);
            }else{
                android.util.Log.d(tag, string);
            }
        }
    }
    public static void v(String tag, String string) {
        if (SHOW_LOG) {
            if(string == null){
                android.util.Log.v(tag, NO_MSG);
            }else{
                android.util.Log.v(tag, string);
            }
        }
    }
    public static void w(String tag, String string) {
        if (SHOW_LOG) {
            if(string == null){
                android.util.Log.w(tag, NO_MSG);
            }else{
                android.util.Log.w(tag, string);
            }
        }
    }
    public static void joc(String tag, String string) {
        if (SHOW_LOG) {
            if(string == null){
                android.util.Log.e(tag, NO_MSG);
            }else{
                android.util.Log.e(tag, string);
            }
        }
    }

}