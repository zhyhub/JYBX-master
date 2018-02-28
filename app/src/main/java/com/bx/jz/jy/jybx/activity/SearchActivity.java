package com.bx.jz.jy.jybx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索界面
 */

public class SearchActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_search)
    AppCompatAutoCompleteTextView etSearch;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.base_ll)
    LinearLayout baseLl;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.ll_record)
    LinearLayout llRecord;

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(SearchActivity.this, null);
    }

    @Override
    public View[] filterViewByIds() {
        return new View[]{etSearch};
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        return new int[]{R.id.et_search};
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(this);
        setContentView(R.layout.activity_search_layout);
        ButterKnife.bind(this);
        baseLl.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);
    }

    @OnClick({R.id.img_back, R.id.img_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.img_delete:
                break;
        }
    }
}
