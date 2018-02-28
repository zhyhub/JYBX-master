package com.bx.jz.jy.jybx.utils;

import android.annotation.SuppressLint;
import android.content.Context;

public class T {

    private static android.widget.Toast toast;

    /**
     * 短时间显示
     */
    @SuppressLint("ShowToast")
    public static void showShort(Context context, String content) {
        if (toast == null) {
            toast = android.widget.Toast.makeText(context, content, android.widget.Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 长时间显示
     */
    @SuppressLint("ShowToast")
    public static void showLong(Context context, String content) {
        if (toast == null) {
            toast = android.widget.Toast.makeText(context, content, android.widget.Toast.LENGTH_LONG);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}