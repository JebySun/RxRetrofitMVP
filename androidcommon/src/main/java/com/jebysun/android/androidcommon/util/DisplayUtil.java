package com.jebysun.android.androidcommon.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;

public class DisplayUtil {

    /**
     * 改变状态栏文字颜色（白色，黑色）， 只支持Android 6.0+系统
     *
     * @param activity
     * @param dark     true黑色, false白色
     */
    public static void setLightStatusBar(Activity activity, boolean dark) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

}
