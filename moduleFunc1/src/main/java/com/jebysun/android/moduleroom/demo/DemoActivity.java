package com.jebysun.android.moduleroom.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jebysun.android.appcommon.InputDialog;
import com.jebysun.android.androidcommon.util.DisplayUtil;
import com.jebysun.android.appcommon.Titlebar;
import com.jebysun.android.appcommon.base.BaseActivity;
import com.jebysun.android.appcommon.base.BasePresenter;
import com.jebysun.android.appcommon.contants.RouteContants;
import com.jebysun.android.moduleroom.R;

@Route(path = RouteContants.ROOM_DEMO)
public class DemoActivity extends BaseActivity implements DemoIView {

    private Titlebar titlebar;
    private TextView tvInfo;
    private DemoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayUtil.setLightStatusBar(this, true);
        setContentView(R.layout.activity_demo);
        titlebar = findViewById(R.id.titlebar);
        tvInfo = findViewById(R.id.tv_info);

        findViewById(R.id.iv_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                gotoUserModule();
                showInputDialog();
            }
        });

        presenter.getPage();
//        presenter.getGiftList();

        titlebar.setOnClickBackListener(new Titlebar.OnClickBackListener() {
            @Override
            public void onBack() {
                ARouter.getInstance().build(RouteContants.USER_TEST).navigation();
            }
        });

    }

    @Override
    public BasePresenter initPresenter() {
        presenter = new DemoPresenter(this);
        return presenter;
    }

    private void showInputDialog() {
        InputDialog dialog = InputDialog.newInstance();
        dialog.show(getSupportFragmentManager(),"dialog");
    }



    @Override
    public void onSuccess(String msg) {
        tvInfo.setText(msg);
    }


    private void gotoUserModule() {
        ARouter.getInstance().build(RouteContants.USER_TEST).navigation();
    }

    private void gotoRoomModule() {
        ARouter.getInstance().build(RouteContants.ROOM_TEST).navigation();
    }
}
