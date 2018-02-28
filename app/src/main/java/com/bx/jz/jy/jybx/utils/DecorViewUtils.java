package com.bx.jz.jy.jybx.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class DecorViewUtils {

    public DecorViewUtils() {
    }

    public static void getWhiteDecorView(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    public static void getDarkDecorView(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
