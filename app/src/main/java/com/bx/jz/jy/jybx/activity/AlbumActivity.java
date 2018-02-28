package com.bx.jz.jy.jybx.activity;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bx.jz.jy.jybx.ConstantPool;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.adapter.SectionAdapter;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.bean.Album;
import com.bx.jz.jy.jybx.bean.AlbumBean;
import com.bx.jz.jy.jybx.bean.MySection;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.utils.OkHttpUtils;
import com.bx.jz.jy.jybx.utils.T;
import com.bx.jz.jy.jybx.view.FullScreenDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 相册
 */
public class AlbumActivity extends BaseActivity {

    private static final String TAG = AlbumActivity.class.getSimpleName();
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
    @BindView(R.id.album_recycle)
    RecyclerView albumRecycle;
    @BindView(R.id.album_swipe)
    SwipeRefreshLayout albumSwipe;
    @BindView(R.id.complete_img)
    TextView complete;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.share_pop)
    LinearLayout sharePop;


    private List<MySection> mySections = new ArrayList<>();
    private SectionAdapter sectionAdapter;
    private static final int PAGE_SIZE = 5;
    private int temp = 1;
    private boolean isRefresh = true;
    private View notDataView;
    private View errorView;
    private int anInt = 0;
    private boolean isBlowUp = true;
    private boolean isCanClick = false;

    private List<String> imgList = new ArrayList<>();
    private List<String> shareList = new ArrayList<>();
    SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]{SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ};

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(AlbumActivity.this, null);
    }

    private void getAlbumList(final int page) {
        OkHttpUtils.getInstance().postForMapAsynchronization(ConstantPool.ALBUM, albumRequest(page), new OkHttpUtils.RequestCallBack<AlbumBean>() {
            @Override
            public void onError(Call call, Exception e) {
                T.showShort(AlbumActivity.this, e.getMessage());
                L.e(TAG, e.getMessage());
                if (!isRefresh) {
                    sectionAdapter.loadMoreFail();
                }
                sectionAdapter.setEmptyView(errorView);
                if (albumSwipe != null) {
                    albumSwipe.setRefreshing(false);
                }
            }

            @Override
            public void onResponse(AlbumBean response) {
                mySections = new ArrayList<>();
                for (int i = 0; i < response.getLists().size(); i++) {
                    imgList.add(response.getLists().get(i).getGroupTime());
                    mySections.add(new MySection(true, response.getLists().get(i).getGroupTime(), false));
                    for (int j = 0; j < response.getLists().get(i).getUrl().size(); j++) {
                        imgList.add(response.getLists().get(i).getUrl().get(j));
                        mySections.add(new MySection(new Album(response.getLists().get(i).getUrl().get(j))));
                    }
                }
                temp++;
                L.e(TAG, TAG + "    " + temp);
                setData(isRefresh, mySections);
                if (albumSwipe != null) {
                    albumSwipe.setRefreshing(false);
                }
            }
        });
    }

    private Map<String, Object> albumRequest(int page) {
        Map<String, Object> object = new HashMap<>();
        object.put("img.pid", 1);
        object.put("img.menuId", 1);
        object.put("pageNo", page);
        object.put("pageNum", 5);
        return object;
    }

    @Override
    protected void onResume() {
        super.onResume();
        temp = 1;
        mySections.clear();
        albumSwipe.setRefreshing(true);
        getAlbumList(temp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(AlbumActivity.this);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);
        baseLl.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("相册");
        complete.setVisibility(View.VISIBLE);
        complete.setText("选择");

        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }

        albumSwipe.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));
        albumSwipe.setColorSchemeResources(R.color.color_0e, R.color.color_0e, R.color.color_0e, R.color.color_0e);
        albumSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                temp = 1;
                isRefresh = true;
                mySections = new ArrayList<>();
                imgList = new ArrayList<>();
                shareList = new ArrayList<>();
                getAlbumList(temp);
            }
        });

        albumRecycle.setLayoutManager(new GridLayoutManager(this, 4));

        sectionAdapter = new SectionAdapter(R.layout.item_album, R.layout.section_album, mySections, AlbumActivity.this);

        sectionAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                getAlbumList(temp);
            }
        }, albumRecycle);

        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MySection mySection = sectionAdapter.getData().get(position);
                if (mySection.isHeader) {
                    T.showShort(AlbumActivity.this, mySection.header + "  " + position);
                } else {
                    T.showShort(AlbumActivity.this, " " + position);
                    if (isBlowUp) {
                        ShowImg(AlbumActivity.this, imgList.get(position));
                    } else {
                        if (mySection.isChoose()) {
                            shareList.remove(imgList.get(position));
                            mySection.setChoose(false);
                            sectionAdapter.notifyDataSetChanged();
                        } else {
                            shareList.add(imgList.get(position));
                            mySection.setChoose(true);
                            sectionAdapter.notifyDataSetChanged();
                        }
                        L.e(TAG, " shareList  " + shareList.toString());
                        if (shareList.size() == 0) {
                            delete.setImageResource(R.mipmap.delete);
                            share.setImageResource(R.mipmap.share);
                            isCanClick = false;
                        } else {
                            delete.setImageResource(R.mipmap.delete_blue);
                            share.setImageResource(R.mipmap.share_blue);
                            isCanClick = true;
                        }
                    }
                }
            }
        });

        sectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                T.showShort(AlbumActivity.this, "onItemChildClick " + position);
            }
        });
        notDataView = getLayoutInflater().inflate(R.layout.empty_list_view, (ViewGroup) albumRecycle.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 1;
                mySections.clear();
                albumSwipe.setRefreshing(true);
                getAlbumList(temp);
            }
        });
        errorView = getLayoutInflater().inflate(R.layout.error_list_view, (ViewGroup) albumRecycle.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 1;
                mySections.clear();
                albumSwipe.setRefreshing(true);
                getAlbumList(temp);
            }
        });

        albumRecycle.setAdapter(sectionAdapter);
    }

    private void setData(boolean isRefresh, List<MySection> data) {
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            sectionAdapter.setNewData(data);
        } else {
            if (size > 0) {
                sectionAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            sectionAdapter.loadMoreEnd(isRefresh);
        } else {
            sectionAdapter.loadMoreComplete();
        }
    }

    @OnClick({R.id.img_back, R.id.complete_img, R.id.delete, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.complete_img:
                shareList.clear();
                for (int i = 0; i < sectionAdapter.getData().size(); i++) {
                    sectionAdapter.getData().get(i).setChoose(false);
                }
                sectionAdapter.notifyDataSetChanged();

                Animation myAnimation_Translate;
                if (isBlowUp) {
                    complete.setText("取消");
                    sharePop.setVisibility(View.VISIBLE);
                    myAnimation_Translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_PARENT, 0,
                            Animation.RELATIVE_TO_PARENT, 0,
                            Animation.RELATIVE_TO_PARENT, 1,
                            Animation.RELATIVE_TO_PARENT, 0);
                    myAnimation_Translate.setDuration(500);
                    myAnimation_Translate.setRepeatMode(Animation.REVERSE);
                    myAnimation_Translate.setInterpolator(AnimationUtils
                            .loadInterpolator(AlbumActivity.this,
                                    android.R.anim.accelerate_decelerate_interpolator));
                    sharePop.startAnimation(myAnimation_Translate);
                    isBlowUp = false;
                } else {
                    myAnimation_Translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_PARENT, 0,
                            Animation.RELATIVE_TO_PARENT, 0,
                            Animation.RELATIVE_TO_PARENT, 0,
                            Animation.RELATIVE_TO_PARENT, 1);
                    myAnimation_Translate.setDuration(1000);
                    myAnimation_Translate.setRepeatMode(Animation.REVERSE);
                    myAnimation_Translate.setInterpolator(AnimationUtils
                            .loadInterpolator(AlbumActivity.this,
                                    android.R.anim.accelerate_decelerate_interpolator));
                    sharePop.startAnimation(myAnimation_Translate);
                    sharePop.setVisibility(View.GONE);
                    isBlowUp = true;
                    complete.setText("选择");
                    delete.setImageResource(R.mipmap.delete);
                    share.setImageResource(R.mipmap.share);
                }
                break;
            case R.id.delete:
                if (isCanClick) {
                    T.showShort(AlbumActivity.this, "点击了删除");
                }
                break;
            case R.id.share:
                if (isCanClick) {
                    T.showShort(AlbumActivity.this, "点击了分享");
                    UMImage image = new UMImage(AlbumActivity.this, shareList.get(0));
                    new ShareAction(AlbumActivity.this).withText("hello").withMedia(image).share();
                }
                break;
        }
    }

    private void ShowImg(Context context, String imgUrl) {
        final FullScreenDialog dialog = new FullScreenDialog(context);
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.full_image_layout, null);
        ImageView img = layout.findViewById(R.id.full_img);
        Glide.with(context)
                .load(imgUrl)
                .into(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
        dialog.setCancelable(false);
        dialog.setContentView(layout);
    }

    //新浪和QQ的授权
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(AlbumActivity.this).onActivityResult(requestCode, resultCode, data);
//    }
}
