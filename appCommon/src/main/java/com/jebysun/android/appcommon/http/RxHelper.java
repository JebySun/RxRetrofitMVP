package com.jebysun.android.appcommon.http;

import android.util.Log;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class RxHelper {

    /**
     * IO线程和UI线程之间的切换
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> switchThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * data数据结果判断
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<Result<T>, Result<T>> resultData() {
        return new ObservableTransformer<Result<T>, Result<T>>() {
            @Override
            public ObservableSource<Result<T>> apply(Observable<Result<T>> upstream) {
                return upstream.map(new Function<Result<T>, Result<T>>() {
                        @Override
                        public Result<T> apply(Result<T> result) {
                            int code = result.getCode();
                            String msg = result.getMsg();
                            if (code != ResultCode.SUCCESS) {
                                throw new ResultException(code, msg);
                            }
                            return result;
                        }
                    });
            }
        };
    }

    public static <T> Consumer<Result<T>> createConsumer(final RequestCallback<T> callback) {
        return new Consumer<Result<T>>() {
            @Override
            public void accept(Result<T> result) {
                callback.onSuccess(result.getData());
            }
        };
    }

    public static Consumer<Throwable> createThrowableConsumer(final RequestCallback callback) {
       return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Log.e("API请求异常", throwable.toString());
                throwable.printStackTrace();

                handleException(throwable, callback);
            }
        };
    }

    private static void handleException(Throwable throwable, RequestCallback callback) {
        if (callback == null) {
            return;
        }
        if (throwable instanceof ResultException) {
            ResultException exception = (ResultException) throwable;
            callback.onFailure(exception.getCode(), exception.getMessage(), throwable);
            return;
        }
        if (throwable instanceof UnknownHostException) {
            callback.onFailure(-100, "无法连接到服务器", throwable);
        }
        else if (throwable instanceof TimeoutException || throwable instanceof SocketTimeoutException) {
            callback.onFailure(-110, "网络超时", throwable);
        }
        else if (throwable instanceof HttpException) {
            callback.onFailure(-110, "网络响应异常" + throwable.getMessage(), throwable);
        }
        // 其他异常
        else {
            callback.onFailure(0, throwable.getMessage(), throwable);
        }
    }

}
