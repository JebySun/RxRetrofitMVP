package com.jebysun.android.androidcommon.util;

import android.app.Application;
import android.content.Context;

public class AppContextUtil {

    private static Application mApplication;

    public static void init(Context context) {
        init((Application) context.getApplicationContext());
    }

    public static void init(Application application) {
        mApplication = application;
    }

    public static Application getApplication() {
        if (mApplication != null) {
            return mApplication;
        }
        throw new NullPointerException("请确保先调用init()初始化");
    }

}
