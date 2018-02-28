package com.bx.jz.jy.jybx.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.bean.Album;
import com.bx.jz.jy.jybx.bean.MySection;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class SectionAdapter extends BaseSectionQuickAdapter<MySection, BaseViewHolder> {

    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List<MySection> data, Context context) {
        super(layoutResId, sectionHeadResId, data);
        this.context = context;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MySection item) {
        helper.setText(R.id.header, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        RequestOptions options = new RequestOptions()
                .centerCrop().placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).priority(Priority.HIGH);

        Album album = item.t;
        RelativeLayout rl = helper.getView(R.id.choose_rl);
        Glide.with(context).load(album.getImg())
                .apply(options)
                .into((ImageView) helper.getView(R.id.iv));

        if(item.isChoose()){
            rl.setVisibility(View.VISIBLE);
        }else {
            rl.setVisibility(View.GONE);
        }
    }
}
