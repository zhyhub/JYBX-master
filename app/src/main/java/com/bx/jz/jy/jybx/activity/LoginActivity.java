package com.bx.jz.jy.jybx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.utils.BaseResult;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.JyHttpDSG1;
import com.bx.jz.jy.jybx.utils.T;
import com.jaeger.library.StatusBarUtil;
import com.joyoung.sdk.info.UserConfirmData;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: zhy
 * email: 760982661@qq.com
 * date: 2018/1/23 0023.
 * desc:
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_search)
    AppCompatAutoCompleteTextView etSearch;
    @BindView(R.id.base_ll)
    LinearLayout baseLl;
    @BindView(R.id.complete_img)
    TextView completeImg;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.et_phone)
    TextView etPhone;
    @BindView(R.id.et_code)
    TextView etCode;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        imgBack.setVisibility(View.GONE);
        baseLl.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("登录");
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        startActivity(MainActivity.class, null);
        LoginActivity.this.finish();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(LoginActivity.this, null);
    }

}
