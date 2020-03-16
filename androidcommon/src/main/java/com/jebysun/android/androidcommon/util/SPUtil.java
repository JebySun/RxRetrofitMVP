package com.jebysun.android.androidcommon.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences数据存储工具
 * SharedPreferences原生不支持存取double，这里拓展了对double类型数据的支持。
 */
public class SPUtil {

    private static SharedPreferences sharedPrefer;

    private static void init() {
        Context context = AppContextUtil.getApplication();
        sharedPrefer = context.getSharedPreferences("xxx", Context.MODE_PRIVATE);
    }

    /**
     * 存double
     * @param key
     * @param value
     */
    public static void putDouble(String key, double value) {
        init();
        SharedPreferences.Editor editor = sharedPrefer.edit();
        editor.putLong(key, Double.doubleToRawLongBits(value)).apply();
    }

    /**
     * 读double
     * @param key
     * @param defaultValue
     * @return
     */
    public static double getDouble(String key, double defaultValue) {
        init();
        if (!sharedPrefer.contains(key)) {
            return defaultValue;
        }
        return Double.longBitsToDouble(sharedPrefer.getLong(key, 0L));
    }

}
