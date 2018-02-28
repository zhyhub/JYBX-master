package com.bx.jz.jy.jybx.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.T;
import com.tuesda.walker.circlerefresh.CircleRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhy on 2018/2/9 0009.
 * email: 760982661@qq.com
 * dec:
 */

public class CookBookActivity extends AppCompatActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.collect)
    ImageView collect;
    @BindView(R.id.share1)
    ImageView share1;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.refresh_layout)
    CircleRefreshLayout refreshLayout;
    @BindView(R.id.stop_refresh)
    Button stopRefresh;

    private String[] str = {
            "The",
            "Canvas",
            "class",
            "holds",
            "the",
            "draw",
            "calls",
            ".",
            "To",
            "draw",
            "something,",
            "you",
            "need",
            "4 basic",
            "components",
            "Bitmap",
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(CookBookActivity.this);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition transition = TransitionInflater.from(this).inflateTransition(android.R.transition.explode);
        getWindow().setEnterTransition(transition);
        setContentView(R.layout.activity_cook_book);
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, str);
        list.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(
                new CircleRefreshLayout.OnCircleRefreshListener() {
                    @Override
                    public void refreshing() {
                        // do something when refresh starts
                    }

                    @Override
                    public void completeRefresh() {
                        // do something when refresh complete
                    }
                });
    }

    @OnClick({R.id.img_back, R.id.collect, R.id.share1,R.id.stop_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                CookBookActivity.this.finish();
                break;
            case R.id.collect:
                T.showShort(CookBookActivity.this, "点击了收藏");
                break;
            case R.id.share1:
                T.showShort(CookBookActivity.this, "点击了分享");
                break;
            case R.id.stop_refresh:
                refreshLayout.finishRefreshing();
                break;
        }
    }
}
