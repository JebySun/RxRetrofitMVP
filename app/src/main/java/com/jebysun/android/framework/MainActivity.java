package com.jebysun.android.framework;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.jebysun.android.moduleroom.RoomActivity;
import com.jebysun.android.moduleuser.UserActivity;
import com.jebysun.android.appcommon.base.BaseActivity;
import com.jebysun.android.appcommon.base.BasePresenter;

public class MainActivity extends BaseActivity implements MainIView {

    private TextView tvInfo;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.tv_info);

        presenter.getPage();
//        presenter.getGiftList();

        gotoRoomModule();

    }

    @Override
    public BasePresenter initPresenter() {
        presenter = new MainPresenter(this);
        return presenter;
    }

    @Override
    public void onSuccess(String msg) {
        tvInfo.setText(msg);
    }


    private void gotoUserModule() {
        startActivity(new Intent(this, UserActivity.class));
    }

    private void gotoRoomModule() {
        startActivity(new Intent(this, RoomActivity.class));
    }
}
