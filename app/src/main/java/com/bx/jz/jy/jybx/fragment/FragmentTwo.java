package com.bx.jz.jy.jybx.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bx.jz.jy.jybx.ConstantPool;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.activity.AddMaterialActivity;
import com.bx.jz.jy.jybx.activity.FoodEncyclopediaActivity;
import com.bx.jz.jy.jybx.activity.PolygonsActivity;
import com.bx.jz.jy.jybx.activity.SearchActivity;
import com.bx.jz.jy.jybx.base.BaseEntity;
import com.bx.jz.jy.jybx.base.BaseListEntity;
import com.bx.jz.jy.jybx.bean.Ingredients;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.utils.OkHttpUtils;
import com.bx.jz.jy.jybx.utils.T;
import com.bx.jz.jy.jybx.view.LinearLayoutManagerWrapper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.grantland.widget.AutofitTextView;
import okhttp3.Call;


public class FragmentTwo extends Fragment {

    private static final String TAG = "FragmentTwo";

    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.durability_period)
    LinearLayout durabilityPeriod;
    @BindView(R.id.all_goods)
    LinearLayout allGoods;
    @BindView(R.id.RecyclerView)
    SwipeMenuRecyclerView RecyclerView;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout SwipeRefreshLayout;
    @BindView(R.id.tv_all_goods)
    TextView tvAllGoods;
    @BindView(R.id.auto)
    AutofitTextView auto;
    @BindView(R.id.trophic_analysis)
    TextView trophicAnalysis;
    @BindView(R.id.view_50)
    View view50;
    Unbinder unbinder;

    private static final int PAGE_SIZE = 10;
    private List<Ingredients> ingredientsList = new ArrayList<>();
    private BaseQuickAdapter<Ingredients, BaseViewHolder> mAdapter;

    private String Order = "desc";
    private int temp = 1;
    private boolean isRefresh = true;
    private boolean isDesc = false;
    private View notDataView;
    private View errorView;
    private boolean isAll = false;
    private int subordinatePosition = 0;
    private boolean isStart = false;
    private int day;

    String[] arrayList = {"所有", "冷藏", "变温", "冷冻"};
    private Animation myAnimation_Translate;
    private boolean isShow = false;

    @SuppressLint("UseSparseArrays")
    private Map<Integer, String> map = new HashMap<>();

    @Override
    public void onResume() {
        super.onResume();
        if (!isStart) {
            temp = 1;
            isRefresh = true;
            ingredientsList.clear();
            mAdapter.notifyDataSetChanged();
            SwipeRefreshLayout.setRefreshing(true);
            getGoodList(temp, Order);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_two_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        SwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);
        // 设置下拉进度的主题颜色
        SwipeRefreshLayout.setColorSchemeResources(R.color.color_0e, R.color.color_0e, R.color.color_0e);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                temp = 1;
                isRefresh = true;
                isStart = false;
                getGoodList(temp, Order);
            }
        });
        initView();
        return view;
    }

    // 创建菜单
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_80);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem addItem = new SwipeMenuItem(getActivity())
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                    .setBackground(R.mipmap.gary)
                    .setWidth(width)
                    .setHeight(height)
                    .setText("编辑")
                    .setTextColor(Color.WHITE);
            rightMenu.addMenuItem(addItem);

            SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                    .setBackgroundColor(Color.RED)
                    .setWidth(width)
                    .setHeight(height)
                    .setText("删除")
                    .setTextColor(Color.WHITE);
            rightMenu.addMenuItem(deleteItem);
        }
    };

    // 菜单点击监听。
    SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                switch (menuPosition) {
                    case 0:
                        T.showShort(getActivity(), "点击了 " + adapterPosition + "的编辑");
                        Intent intent = new Intent(getActivity(), AddMaterialActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Ingredients", mAdapter.getData().get(adapterPosition));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 1:
                        Ingredients Ingredients = mAdapter.getData().get(adapterPosition);
                        deleteFoods(Ingredients.getIngredientsId(), adapterPosition);
                        break;
                }
            }
        }
    };

    private void deleteFoods(final int ingredientsId, final int adapterPosition) {
        OkHttpUtils.getInstance().postForMapAsynchronization(ConstantPool.DELETEFOODS, deleteRequest(ingredientsId), new OkHttpUtils.RequestCallBack<BaseEntity>() {
            @Override
            public void onError(Call call, Exception e) {
                T.showShort(getActivity(), e.getMessage());
                L.e(TAG, "  deleteFoods  onError  " + e.getMessage());
            }

            @Override
            public void onResponse(BaseEntity response) {
                L.e(TAG, "  deleteFoods  onResponse  " + "  ...  " + adapterPosition + "   000000  " + mAdapter.getData().size());
                if (response.getCode().equals("1")) {
                    mAdapter.getData().remove(adapterPosition);
                    mAdapter.notifyItemRemoved(adapterPosition);
                    T.showShort(getActivity(), "删除成功");
                }
            }
        });
    }

    private Map<String, Object> deleteRequest(int ingredientsId) {
        Map<String, Object> object = new HashMap<>();
        object.put("ingredients.refrigeratorId", 1);
        object.put("ingredients.ingredientsId", ingredientsId);
        return object;
    }

    private void initView() {

        RecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getActivity()));
        RecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        RecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);

        notDataView = getLayoutInflater().inflate(R.layout.empty_list_view, (ViewGroup) RecyclerView.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 1;
                ingredientsList.clear();
                SwipeRefreshLayout.setRefreshing(true);
                getGoodList(temp, Order);
            }
        });
        errorView = getLayoutInflater().inflate(R.layout.error_list_view, (ViewGroup) RecyclerView.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 1;
                ingredientsList.clear();
                SwipeRefreshLayout.setRefreshing(true);
                getGoodList(temp, Order);
            }
        });

        mAdapter = new BaseQuickAdapter<Ingredients, BaseViewHolder>(R.layout.item_goods_layout, ingredientsList) {

            @Override
            protected void convert(final BaseViewHolder helper, final Ingredients item) {

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.mipmap.placeholder)
                        .error(R.mipmap.placeholder)
                        .priority(Priority.HIGH)
                        .transform(new CircleCrop());

                ImageView imageView = helper.getView(R.id.img_goods);
                ImageView freshness = helper.getView(R.id.freshness);
                final View pitchOn = helper.getView(R.id.pitch_on_view);
                LinearLayout llOnClick = helper.getView(R.id.ll_onClick);
                helper.setText(R.id.tv_goods_name, item.getIngredientsName());

                if(item.getImgUrl() != null){
                    Glide.with(getActivity())
                            .load(item.getImgUrl())
                            .apply(options)
                            .into(imageView);
                }

                if (item.getSubordinatePosition() != null) {
                    switch (item.getSubordinatePosition()) {//所属位置(1=冷藏，2=变温，3=冷冻)
                        case 1:
                            helper.setText(R.id.tv_goods_type, "冷藏");
                            break;
                        case 2:
                            helper.setText(R.id.tv_goods_type, "变温");
                            break;
                        case 3:
                            helper.setText(R.id.tv_goods_type, "冷冻");
                            break;
                    }
                }
                switch (item.getFreshness()) {
                    case 1:
                        freshness.setVisibility(View.VISIBLE);
                        freshness.setImageResource(R.mipmap.freshness_1);
                        break;
                    case 2:
                        freshness.setVisibility(View.VISIBLE);
                        freshness.setImageResource(R.mipmap.freshness_2);
                        break;
                    case 3:
                        freshness.setVisibility(View.VISIBLE);
                        freshness.setImageResource(R.mipmap.freshness_3);
                        break;
                }
                if (item.getShelfLifeRemaining() != null && item.getShelfLifeRemaining() != 0) {
                    day = (int) (item.getShelfLifeRemaining() / 60 / 60 / 1000 / 24);
                    helper.setText(R.id.tv_goods_date, String.valueOf(day + "天"));
                }
                helper.setText(R.id.tv_goods_weight, (item.getFoodComponent() == null ? 1 : item.getFoodComponent()) + (item.getComponentUnit() == null ? "个" : item.getComponentUnit()));

                if (!item.isClick()) {
                    pitchOn.setBackgroundResource(R.color.color_ee);
                } else {
                    pitchOn.setBackgroundResource(R.color.color_0e);
                }
                llOnClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!item.isClick()) {
                            pitchOn.setBackgroundResource(R.color.color_0e);
                            item.setClick(true);
                            map.put(helper.getPosition(), item.getIngredientsName());
                        } else {
                            pitchOn.setBackgroundResource(R.color.color_ee);
                            item.setClick(false);
                            map.remove(helper.getPosition());
                        }
                        L.e(TAG, map.toString());
                        if (map.size() != 0) {
                            if (!isShow) {
                                view50.setVisibility(View.VISIBLE);
                                myAnimation_Translate = new TranslateAnimation(
                                        Animation.RELATIVE_TO_PARENT, 0,
                                        Animation.RELATIVE_TO_PARENT, 0,
                                        Animation.RELATIVE_TO_PARENT, 1,
                                        Animation.RELATIVE_TO_PARENT, 0);
                                myAnimation_Translate.setDuration(500);
                                myAnimation_Translate.setRepeatMode(Animation.REVERSE);
                                myAnimation_Translate.setInterpolator(AnimationUtils
                                        .loadInterpolator(getActivity(),
                                                android.R.anim.accelerate_decelerate_interpolator));
                                view50.startAnimation(myAnimation_Translate);
                                isShow = true;
                            }
                        } else {
                            myAnimation_Translate = new TranslateAnimation(
                                    Animation.RELATIVE_TO_PARENT, 0,
                                    Animation.RELATIVE_TO_PARENT, 0,
                                    Animation.RELATIVE_TO_PARENT, 0,
                                    Animation.RELATIVE_TO_PARENT, 1);
                            myAnimation_Translate.setDuration(500);
                            myAnimation_Translate.setInterpolator(AnimationUtils
                                    .loadInterpolator(getActivity(),
                                            android.R.anim.accelerate_decelerate_interpolator));
                            view50.startAnimation(myAnimation_Translate);
                            view50.setVisibility(View.GONE);
                            isShow = false;
                        }
                    }
                });

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.showShort(getActivity(), "点击了头像   " + item.getIngredientsName());
                        Intent intent = new Intent(getActivity(), FoodEncyclopediaActivity.class);
                        intent.putExtra("knowledgeGraphId",item.getKnowledgeGraphId());
                        getActivity().startActivity(intent);
                        isStart = true;
                    }
                });
            }
        };
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                getGoodList(temp, Order);
            }
        });

        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.isFirstOnly(false);

        RecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DecorViewUtils.getDarkDecorView(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @OnClick({R.id.durability_period, R.id.all_goods, R.id.img_search, R.id.img_add, R.id.trophic_analysis})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.durability_period://保质期剩余
                temp = 1;
                isRefresh = true;
                if (!isDesc) {
                    Order = "asc";
                    SwipeRefreshLayout.setRefreshing(true);
                    getGoodList(temp, Order);
                    isDesc = true;
                    auto.setTextColor(getResources().getColor(R.color.color_0e));
                } else {
                    Order = "desc";
                    SwipeRefreshLayout.setRefreshing(true);
                    getGoodList(temp, Order);
                    isDesc = false;
                    auto.setTextColor(getResources().getColor(R.color.color_333));
                }
                break;
            case R.id.all_goods:

                final LayoutInflater dialogInflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList) {

                    @NonNull
                    @Override
                    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

                        assert dialogInflater != null;
                        convertView = dialogInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                        TextView tvSimple = convertView.findViewById(android.R.id.text1);
                        String display = this.getItem(position);
                        tvSimple.setText(display);

                        if (tvAllGoods.getText().toString().equals(tvSimple.getText().toString())) {
                            tvSimple.setTextColor(getResources().getColor(R.color.color_0e));
                        }
                        return convertView;
                    }
                };

                DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        temp = 1;
                        isRefresh = true;
                        if (which == 0) {
                            isAll = false;
                            SwipeRefreshLayout.setRefreshing(true);
                            getGoodList(temp, Order);
                        } else {
                            isAll = true;
                            subordinatePosition = which;
                            isRefresh = true;
                            SwipeRefreshLayout.setRefreshing(true);
                            getGoodList(temp, Order);
                        }
                        tvAllGoods.setText(arrayList[which]);
                        tvAllGoods.setTextColor(getResources().getColor(R.color.color_0e));
                    }
                };
                new AlertDialog.Builder(getActivity()).setAdapter(adapter, clickListener).create().show();
                break;
            case R.id.img_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                isStart = true;
                break;
            case R.id.img_add:
                startActivity(new Intent(getActivity(), AddMaterialActivity.class));
                isStart = false;
                break;
            case R.id.trophic_analysis://营养分析
                startActivity(new Intent(getActivity(), PolygonsActivity.class));
                isStart = true;
                break;
        }
    }

    private void getGoodList(int page, String order) {
        if (view50 != null && map != null && isRefresh) {
            view50.setVisibility(View.GONE);
            map.clear();
            isShow = false;
        }
        OkHttpUtils.getInstance().postForMapAsynchronization(ConstantPool.GOODSLIST, GoodsRequest(page, order), new OkHttpUtils.RequestCallBack<BaseListEntity<Ingredients>>() {
            @Override
            public void onError(Call call, Exception e) {
                T.showLong(getActivity(), e.getMessage());
                if (!isRefresh) {
                    mAdapter.loadMoreFail();
                }
                mAdapter.setEmptyView(errorView);
                if (SwipeRefreshLayout != null) {
                    SwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onResponse(BaseListEntity<Ingredients> response) {
                if (response != null && response.getList() != null) {
                    if (response.getList().size() == 0 && isRefresh) {
                        setData(true, null);
                        mAdapter.setEmptyView(notDataView);
                    } else {
                        temp++;
                        setData(isRefresh, response.getList());
                    }
                    if (SwipeRefreshLayout != null) {
                        SwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });
    }

    private Map<String, Object> GoodsRequest(int page, String order) {
        Map<String, Object> object = new HashMap<>();
        object.put("ingredients.refrigeratorId", 1);
        if (isAll) {
            object.put("ingredients.subordinatePosition", subordinatePosition);
        }
        object.put("pageNo", page);
        object.put("pageNum", 10);
        object.put("order", order);//降序：desc；升序：asc
        return object;
    }

    private void setData(boolean isRefresh, List<Ingredients> data) {
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }
}
