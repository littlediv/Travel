package net.xinhong.travel.utils;

import android.util.Log;

/**
 * Created by mac on 2017/1/5.
 */
public class LogUtils {

    private static boolean isDebug = true;


    public static void e(Object obj, String value) {
        if (isDebug)
            Log.e(obj.getClass().getSimpleName(), "Error: " + value );
    }

    public static void d(Object obj, String value) {
        if (isDebug)
            Log.d(obj.getClass().getSimpleName(), "Debug: " + value );
    }

    public static void i(Object obj, String value) {
        if (isDebug)
            Log.i(obj.getClass().getSimpleName(), "Info: " + value );
    }

    public static void v(Object obj, String value) {
        if (isDebug)
            Log.v(obj.getClass().getSimpleName(), "Verbose: " + value );
    }

    public static void w(Object obj, String value) {
        if (isDebug)
            Log.w(obj.getClass().getSimpleName(), "Warm: " + value );
    }
}
