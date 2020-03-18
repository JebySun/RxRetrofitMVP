package com.jebysun.android.androidcommon.util;

import android.widget.Toast;

public class ToastUtil {

    private static Toast toast;

    public static void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(AppContextUtil.getApplication(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

}
