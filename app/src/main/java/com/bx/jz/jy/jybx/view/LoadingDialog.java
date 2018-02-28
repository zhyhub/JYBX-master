package com.bx.jz.jy.jybx.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bx.jz.jy.jybx.R;

/**
 * author: zhy
 * email: 760982661@qq.com
 * date: 2018/1/18 0018.
 * desc:
 */

public class LoadingDialog {

    public static Dialog createDialog(Context context,String content) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        TextView textView = v.findViewById(R.id.tv_content);
        textView.setText(content);
        Dialog loadingDialog = new Dialog(context);// 创建自定义样式dialog
        loadingDialog.setCancelable(true);// 可以用“返回键”取消
        loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;
    }
}
