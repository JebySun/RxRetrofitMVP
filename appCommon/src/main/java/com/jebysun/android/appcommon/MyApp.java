package com.jebysun.android.appcommon;

import android.app.Application;

public class MyApp extends Application {

    private static MyApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static MyApp getAppContext() {
        return app;
    }

    public String getStr() {
        return "!!!!!!!MyApp!!!!!!!!";
    }

}
