package com.bx.jz.jy.jybx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.bean.Ingredients;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class AddMaterialActivity extends BaseActivity {

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
    @BindView(R.id.lengCang)
    TextView lengCang;
    @BindView(R.id.bianWen)
    TextView bianWen;
    @BindView(R.id.lengDong)
    TextView lengDong;

    private Ingredients ingredients;

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(AddMaterialActivity.this, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(this);
        setContentView(R.layout.activity_add_material);
        ButterKnife.bind(this);
        baseLl.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("食材添加");

        if (getIntent() != null) {
            ingredients = (Ingredients) getIntent().getSerializableExtra("Ingredients");
            if(null != ingredients){
                int whichBX = ingredients.getSubordinatePosition();
                switch (whichBX) {
                    case 1:
                        lengCang.setTextColor(getResources().getColor(R.color.theme_other));
                        break;
                    case 2:
                        bianWen.setTextColor(getResources().getColor(R.color.theme_other));
                        break;
                    case 3:
                        lengDong.setTextColor(getResources().getColor(R.color.theme_other));
                        break;
                }
            }
        }
    }

    @OnClick({R.id.img_back, R.id.lengCang, R.id.bianWen, R.id.lengDong})
    public void onViewClicked(View view) {
        Intent intent = new Intent(AddMaterialActivity.this, EditorsMaterialActivity.class);
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.lengCang:
                toZero();
                lengCang.setTextColor(getResources().getColor(R.color.theme_other));
                if(ingredients != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Ingredients",ingredients);
                    bundle.putInt("whichBX",1);
                    intent.putExtras(bundle);
                }else {
                    intent.putExtra("whichBX", 1);
                }
                startActivityForResult(intent, 1015);
                break;
            case R.id.bianWen:
                toZero();
                bianWen.setTextColor(getResources().getColor(R.color.theme_other));
                if(ingredients != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Ingredients",ingredients);
                    bundle.putInt("whichBX",2);
                    intent.putExtras(bundle);
                }else {
                    intent.putExtra("whichBX", 2);
                }
                startActivityForResult(intent, 1015);
                break;
            case R.id.lengDong:
                toZero();
                lengDong.setTextColor(getResources().getColor(R.color.theme_other));
                if(ingredients != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Ingredients",ingredients);
                    bundle.putInt("whichBX",3);
                    intent.putExtras(bundle);
                }else {
                    intent.putExtra("whichBX", 3);
                }
                startActivityForResult(intent, 1015);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1015) {
                AddMaterialActivity.this.finish();
            }
        }
    }

    private void toZero() {
        lengCang.setTextColor(getResources().getColor(R.color.color_666));
        bianWen.setTextColor(getResources().getColor(R.color.color_666));
        lengDong.setTextColor(getResources().getColor(R.color.color_666));
    }
}
