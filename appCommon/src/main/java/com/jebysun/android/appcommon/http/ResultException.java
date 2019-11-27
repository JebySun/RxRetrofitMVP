package com.jebysun.android.appcommon.http;

public class ResultException extends RuntimeException {

    private int code;

    public ResultException(String message) {
        super(message);
    }

    public ResultException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
