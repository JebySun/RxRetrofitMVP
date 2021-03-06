package com.jebysun.android.moduleroom.demo;

import com.jebysun.android.appcommon.base.BaseModel;
import com.jebysun.android.appcommon.http.RequestCallback;
import com.jebysun.android.appcommon.http.Result;
import com.jebysun.android.appcommon.http.RxHelper;
import com.jebysun.android.appcommon.http.RxRetrofitClient;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;


public class DemoModel extends BaseModel {

    private DemoHttpService mDemoHttpService;

    public DemoModel() {
        mDemoHttpService = RxRetrofitClient.getInstance().createHTTPService(DemoHttpService.class);
    }

    /**
     * 正常网络请求
     * @param callback
     */
    public void giftList(RequestCallback<String> callback) {
        Observable<Result<String>> observable = mDemoHttpService.giftList(null);
        super.doSubscribe(observable, callback);
    }

    /**
     * 特殊网络请求
     * @param callback
     */
    public void getPage(final RequestCallback<String> callback) {
        Disposable disposable = mDemoHttpService.getHTMLPage()
                .compose(RxHelper.<ResponseBody>switchThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody body) throws IOException {
                        String res = body.string();
                        callback.onSuccess(res);
                    }
                }, RxHelper.createThrowableConsumer(callback));

        super.getCompositeDisposable().add(disposable);
    }



}
