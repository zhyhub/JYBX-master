package com.bx.jz.jy.jybx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bx.jz.jy.jybx.ConstantPool;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.base.BaseEntity;
import com.bx.jz.jy.jybx.bean.Ingredients;
import com.bx.jz.jy.jybx.bean.MaterialBean;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.utils.OkHttpUtils;
import com.bx.jz.jy.jybx.utils.T;
import com.bx.jz.jy.jybx.view.FullScreenDialog;
import com.bx.jz.jy.jybx.view.RulerView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 添加食材界面
 */

public class EditorsMaterialActivity extends BaseActivity implements TextWatcher {

    private static final String TAG = EditorsMaterialActivity.class.getSimpleName();

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
    @BindView(R.id.rulerView)
    RulerView rulerView;
    @BindView(R.id.rulerView_day)
    RulerView rulerViewDay;
    @BindView(R.id.tv_ke)
    TextView tvKe;
    @BindView(R.id.tv_ge)
    TextView tvGe;
    @BindView(R.id.tv_he)
    TextView tvHe;
    @BindView(R.id.tv_jin)
    TextView tvJin;
    @BindView(R.id.et_name)
    AutoCompleteTextView etName;
    @BindView(R.id.material_img)
    ImageView materialImg;
    @BindView(R.id.complete_img)
    TextView completeImg;

    private int whichBX = 0;//冷藏室 1 ， 变温室  2 ， 冷冻室 3
    private int day;

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private Double materialWeight = 50d;//食材重量
    private Double overDueData = 15d;//过期时间
    private String unit = "克";//单位
    private String materialName = "";
    private String img = "";

    private Ingredients ingredients;
    private long ingredientsId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(this);
        setContentView(R.layout.activity_editors_material);
        ButterKnife.bind(this);
        baseLl.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        completeImg.setVisibility(View.GONE);
        tvTitle.setText("食材编辑");

        etName.addTextChangedListener(this);

        rulerViewDay.setUnit("天");
        rulerViewDay.setMaxScale(30);
        rulerViewDay.setMinScale(0);
        rulerViewDay.setFirstScale(15);
        rulerViewDay.setScaleCount(2);
        rulerViewDay.setScaleGap(200);

        rulerView.setUnit("克");
        rulerView.setMaxScale(100);
        rulerView.setMinScale(0);
        rulerView.setFirstScale(50);

        rulerView.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {
                materialWeight = Double.parseDouble(result);
                L.e(TAG, "rulerView   onEndResult  " + materialWeight);
            }

            @Override
            public void onScrollResult(String result) {

            }
        });

        rulerViewDay.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {
                overDueData = Double.parseDouble(result);
                L.e(TAG, "rulerViewDay  onEndResult  " + overDueData);
            }

            @Override
            public void onScrollResult(String result) {

            }
        });

        if (getIntent() != null) {
            ingredients = (Ingredients) getIntent().getSerializableExtra("Ingredients");
            if(null != ingredients){
                ingredientsId = ingredients.getIngredientsId();
                day = getIntent().getIntExtra("day", 0);
                whichBX = getIntent().getIntExtra("whichBX", 0);
                L.e(TAG, String.valueOf(whichBX));
                if (ingredients.getIngredientsName() != null && !"".equals(ingredients.getIngredientsName())) {
                    etName.setText(ingredients.getIngredientsName());
                    materialName = ingredients.getIngredientsName();
                }
                if (ingredients.getImgUrl() != null && !"".equals(ingredients.getImgUrl())) {
                    Glide.with(EditorsMaterialActivity.this)
                            .load(ingredients.getImgUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                            .into(materialImg);
                    img = ingredients.getImgUrl();
                }
                if (ingredients.getFoodComponent() != null && !"".equals(ingredients.getFoodComponent())) {
                    rulerView.setFirstScale(Float.parseFloat(ingredients.getFoodComponent()));
                    materialWeight = Double.valueOf(ingredients.getFoodComponent());
                }
                if (ingredients.getComponentUnit() != null && !"".equals(ingredients.getComponentUnit())) {
                    rulerView.setUnit(ingredients.getComponentUnit());

                    switch (ingredients.getComponentUnit()) {
                        case "克":
                            setO();
                            tvKe.setTextColor(getResources().getColor(R.color.theme_other));
                            rulerView.setUnit("克");
                            unit = "克";
                            break;
                        case "个":
                            setO();
                            tvGe.setTextColor(getResources().getColor(R.color.theme_other));
                            rulerView.setUnit("个");
                            unit = "个";
                            break;
                        case "盒":
                            setO();
                            tvHe.setTextColor(getResources().getColor(R.color.theme_other));
                            rulerView.setUnit("盒");
                            unit = "盒";
                            break;
                        case "斤":
                            setO();
                            tvJin.setTextColor(getResources().getColor(R.color.theme_other));
                            rulerView.setUnit("斤");
                            unit = "斤";
                            break;
                    }
                }
                if (ingredients.getShelfLifeRemaining() != null && ingredients.getShelfLifeRemaining() != 0) {
                    rulerViewDay.setFirstScale(ingredients.getShelfLifeRemaining() / 60 / 60 / 1000 / 24);
                    overDueData = (double) (ingredients.getShelfLifeRemaining() / 60 / 60 / 1000 / 24);
                }
            }
        }
    }

    private void getMaterialList(String s) {
        OkHttpUtils.getInstance().postForMapAsynchronization(ConstantPool.SIMILAR, materialRequest(s), new OkHttpUtils.RequestCallBack<MaterialBean>() {
            @Override
            public void onError(Call call, Exception e) {
                T.showLong(EditorsMaterialActivity.this, e.getMessage());
                L.e(TAG, "getMaterialList  " + e.getMessage());
            }

            @Override
            public void onResponse(final MaterialBean response) {
                if (response != null && response.getCode() == 1) {
                    arrayList.clear();
                    if (response.getResult() != null && response.getResult().size() != 0) {
                        for (int i = 0; i < response.getResult().size(); i++) {
                            arrayList.add(response.getResult().get(i).getName());
                        }
                    }
                    if (arrayList != null && arrayList.size() != 0) {
                        arrayAdapter = new ArrayAdapter<String>(EditorsMaterialActivity.this, android.R.layout.simple_list_item_1, arrayList);
                        etName.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();
                        etName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                img = response.getResult().get(position).getImg();
                                String name = response.getResult().get(position).getName();
                                L.e(TAG, "setOnItemClickListener  img " + img + "  name  " + name);

                                if (!"".equals(img)) {
                                    Glide.with(EditorsMaterialActivity.this)
                                            .load(img).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                            .into(materialImg);
                                }
                                arrayAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
    }

    private Map<String, Object> materialRequest(String s) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", s);
        return map;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(EditorsMaterialActivity.this, null);
    }

    public void getDialog() {
        final FullScreenDialog dialog = new FullScreenDialog(this);
        final LayoutInflater inflater = getLayoutInflater();
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.dialog_add_edit, null);
        TextView content = layout.findViewById(R.id.content);
        content.setText("添加成功");

        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
                EditorsMaterialActivity.this.setResult(RESULT_OK);
                EditorsMaterialActivity.this.finish();
            }
        }, 1500);

        dialog.show();
        dialog.setCancelable(false);
        dialog.setContentView(layout);
    }

    @OnClick({R.id.img_back, R.id.tv_ke, R.id.tv_ge, R.id.tv_he, R.id.tv_jin, R.id.complete_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.tv_ke:
                setO();
                tvKe.setTextColor(getResources().getColor(R.color.theme_other));
                rulerView.setUnit("克");
                unit = "克";
                break;
            case R.id.tv_ge:
                setO();
                tvGe.setTextColor(getResources().getColor(R.color.theme_other));
                rulerView.setUnit("个");
                unit = "个";
                break;
            case R.id.tv_he:
                setO();
                tvHe.setTextColor(getResources().getColor(R.color.theme_other));
                rulerView.setUnit("盒");
                unit = "盒";
                break;
            case R.id.tv_jin:
                setO();
                tvJin.setTextColor(getResources().getColor(R.color.theme_other));
                rulerView.setUnit("斤");
                unit = "斤";
                break;
            case R.id.complete_img:
                if ("".equals(materialName)) {
                    T.showShort(EditorsMaterialActivity.this, "请填写食材名称");
                    return;
                }
                if (materialWeight == null || materialWeight == 0) {
                    T.showShort(EditorsMaterialActivity.this, "请选择食材重量");
                    return;
                }
                if (overDueData == null || overDueData == 0) {
                    T.showShort(EditorsMaterialActivity.this, "请选择食材保质期");
                    return;
                }
                completeEditorsMaterial();
                break;
        }
    }

    private void completeEditorsMaterial() {
        OkHttpUtils.getInstance().postForMapAsynchronization(ConstantPool.saveOrUpdate, completeRequest(), new OkHttpUtils.RequestCallBack<BaseEntity>() {
            @Override
            public void onError(Call call, Exception e) {
                T.showLong(EditorsMaterialActivity.this, e.getMessage());
                L.e(TAG, "completeEditorsMaterial  " + e.getMessage());
            }

            @Override
            public void onResponse(BaseEntity response) {
                if (response != null && response.getCode().equals("1")) {
                    getDialog();
                }
            }
        });
    }

    private Map<String, Object> completeRequest() {
        Map<String, Object> map = new HashMap<>();
        map.put("ingredients.refrigeratorId", 1);
        map.put("ingredients.ingredientsName", materialName);
        map.put("ingredients.userId", 12345);
        map.put("ingredients.imgUrl", img);
        map.put("ingredients.shelfLifeTime", overDueData);
        map.put("ingredients.addWay", 2);
        map.put("ingredients.subordinatePosition", whichBX);
        map.put("ingredients.foodComponent", materialWeight);
        map.put("ingredients.componentUnit", unit);
        map.put("ingredients.ingredientsId", ingredientsId);
        return map;
    }

    private void setO() {
        tvGe.setTextColor(getResources().getColor(R.color.color_df));
        tvKe.setTextColor(getResources().getColor(R.color.color_df));
        tvHe.setTextColor(getResources().getColor(R.color.color_df));
        tvJin.setTextColor(getResources().getColor(R.color.color_df));
    }

    @Override
    public View[] filterViewByIds() {
        return new View[]{etName};
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        return new int[]{R.id.et_name};
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!s.toString().equals("")) {
            completeImg.setVisibility(View.VISIBLE);
            completeImg.setText("确认");
            materialName = s.toString();
            L.e(TAG, "afterTextChanged  name " + s);
            getMaterialList(s.toString());
        } else {
            completeImg.setVisibility(View.GONE);
        }
    }
}
