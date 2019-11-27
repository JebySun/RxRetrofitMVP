package com.jebysun.android.framework;

import com.jebysun.android.appcommon.base.BasePresenter;
import com.jebysun.android.appcommon.http.RequestCallback;

/**
 * 视图和数据之间的主持
 * Presenter不依赖RxJava和Retrofit
 */
public class MainPresenter extends BasePresenter {

    private MainIView view;
    private MainModel model;

    public MainPresenter(MainIView view) {
        this.view = view;
        this.model = new MainModel();
    }

    public void getPage() {
        model.getPage(new RequestCallback<String>() {

            @Override
            public void onSuccess(String s) {
                view.onSuccess(s);
            }

            @Override
            public void onFailure(int code, String errMsg, Throwable throwable) {
                view.onFailure(errMsg);
            }
        });
    }



    public void getGiftList() {
        model.giftList(new RequestCallback<String>() {
            @Override
            public void onSuccess(String msg) {
                view.onSuccess(msg);
            }

            @Override
            public void onFailure(int code, String errMsg, Throwable throwable) {
                view.onFailure(errMsg);
            }
        });
    }


    @Override
    public void dispose() {
        model.dispose();
    }
}
