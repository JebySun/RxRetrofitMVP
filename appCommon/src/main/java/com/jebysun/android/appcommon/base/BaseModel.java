package com.jebysun.android.appcommon.base;

import com.jebysun.android.appcommon.http.RequestCallback;
import com.jebysun.android.appcommon.http.Result;
import com.jebysun.android.appcommon.http.RxHelper;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BaseModel {

    private CompositeDisposable compositeDisposable;

    public BaseModel() {
        compositeDisposable = new CompositeDisposable();
    }

    public void dispose() {
        compositeDisposable.dispose();
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    /**
     * 处理Observable之后的API请求，线程切换，异常判断。
     * @param observable
     * @param callback
     * @param <T>
     */
    public <T> void doSubscribe(Observable<Result<T>> observable, RequestCallback<T> callback) {
        Disposable disposable = observable
                .compose(RxHelper.<Result<T>>switchThread())
                .compose(RxHelper.<T>resultData())
                .subscribe(RxHelper.createConsumer(callback), RxHelper.createThrowableConsumer(callback));
        compositeDisposable.add(disposable);
    }


}
