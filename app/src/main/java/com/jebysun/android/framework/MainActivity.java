package com.jebysun.android.framework;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jebysun.android.appcommon.base.BasePresenter;
import com.jebysun.android.appcommon.contants.RouteContants;
import com.jebysun.android.appcommon.base.BaseActivity;

public class MainActivity extends BaseActivity {
    // 开屏页最少显示多少秒
    private static final int SPLASH_TIME_IN_SECOND = 2;

    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        startCountdown();
    }


    private void startCountdown() {
        mCountDownTimer = new CountDownTimer(1000 * SPLASH_TIME_IN_SECOND, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startNextActivity();
            }
        };
        mCountDownTimer.start();
    }

    private void startNextActivity() {
        gotoModule();
    }


    @Override
    public void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        super.onDestroy();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    private void gotoModule() {
        ARouter.getInstance().build(RouteContants.ROOM_DEMO).navigation();
        finish();
    }

}
