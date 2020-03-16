package com.jebysun.android.appcommon;

import android.app.Application;

import com.jebysun.android.androidcommon.util.AppContextUtil;


public class MyApp extends Application {

    private static MyApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        AppContextUtil.init(this);
    }

    public static MyApp getAppContext() {
        return app;
    }

    public String getStr() {
        return "!!!!!!!MyApp!!!!!!!!";
    }

}
