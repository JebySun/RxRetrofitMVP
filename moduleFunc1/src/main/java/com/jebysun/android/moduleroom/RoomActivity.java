package com.jebysun.android.moduleroom;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jebysun.android.appcommon.MyApp;
import com.jebysun.android.appcommon.contants.RouteContants;
import com.jebysun.android.appcommon.base.BaseActivity;
import com.jebysun.android.appcommon.base.BasePresenter;

@Route(path = RouteContants.ROOM_TEST)
public class RoomActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        MyApp app = (MyApp) getApplication();
        Log.d(TAG, app.getStr());
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

}
