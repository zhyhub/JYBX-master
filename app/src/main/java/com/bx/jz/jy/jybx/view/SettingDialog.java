package com.bx.jz.jy.jybx.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bx.jz.jy.jybx.R;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class SettingDialog extends AlertDialog {

    public SettingDialog(@NonNull Context context) {
        super(context, R.style.SettingDialog);
    }

    protected SettingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.SettingDialog);
    }

    protected SettingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height= ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
