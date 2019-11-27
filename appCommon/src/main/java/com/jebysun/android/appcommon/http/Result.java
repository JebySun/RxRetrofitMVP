package com.jebysun.android.appcommon.http;

/**
 * Created by JebySun on 2018/4/10.
 * email:jebysun@126.com
 */

public class Result<T> {

    /**
     * 返回结果码
     */
    private int code;
    /**
     * 返回结果描述
     */
    private String msg;
    /**
     * 返回结果业务数据，可为null
     */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
