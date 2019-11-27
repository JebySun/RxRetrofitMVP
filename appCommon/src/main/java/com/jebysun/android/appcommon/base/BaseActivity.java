package com.jebysun.android.appcommon.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    public final String TAG = getClass().getName();

    private BasePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = initPresenter();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.dispose();
        }
        super.onDestroy();
    }


    ///////////////////////////////////////////////////////

    public abstract BasePresenter initPresenter();

    @Override
    public void onFailure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}

