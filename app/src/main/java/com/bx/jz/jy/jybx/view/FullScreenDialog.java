package com.bx.jz.jy.jybx.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;

import com.bx.jz.jy.jybx.R;


/**
 * Created by Administrator on 2017/6/21.
 */

public class FullScreenDialog extends AlertDialog{

    public FullScreenDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
    }

    protected FullScreenDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.MyDialog);
    }

    protected FullScreenDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
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
        layoutParams.gravity=Gravity.BOTTOM;
        layoutParams.width= LayoutParams.MATCH_PARENT;
        layoutParams.height= LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
