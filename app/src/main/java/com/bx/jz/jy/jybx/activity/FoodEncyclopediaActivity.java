package com.bx.jz.jy.jybx.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.fragment.OneTabFragment;
import com.bx.jz.jy.jybx.fragment.TwoTabFragment;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 食材详情界面
 */

public class FoodEncyclopediaActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_search)
    AppCompatAutoCompleteTextView etSearch;
    @BindView(R.id.base_ll)
    LinearLayout baseLl;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private OneTabFragment oneTabFragment;
    private TwoTabFragment twoTabFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(this);
        setContentView(R.layout.activity_food_encyclopedia);
        ButterKnife.bind(this);
        baseLl.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("食材百科");

        initView();

        if(getIntent() != null){
            String knowledgeGraphId = getIntent().getStringExtra("knowledgeGraphId");
            if(!"".equals(knowledgeGraphId)){
                oneTabFragment.getUrlID(knowledgeGraphId);
                twoTabFragment.getUrlID(knowledgeGraphId);
            }
        }
    }

    private void initView() {
        viewpager.setAdapter(new VoucherFragmentPagerAdapter(getSupportFragmentManager()));
        viewpager.setOffscreenPageLimit(2);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabTextColors(getResources().getColor(R.color.color_666), getResources().getColor(R.color.color_0e));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_0e));
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(FoodEncyclopediaActivity.this, null);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        this.finish();
    }

    private class VoucherFragmentPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList = new ArrayList<>();
        String titles[] = new String[]{"营养功效", "饮食宜忌"};

        private VoucherFragmentPagerAdapter(FragmentManager fm) {
            super(fm);

            oneTabFragment = new OneTabFragment();
            twoTabFragment = new TwoTabFragment();

            fragmentList.add(oneTabFragment);
            fragmentList.add(twoTabFragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
