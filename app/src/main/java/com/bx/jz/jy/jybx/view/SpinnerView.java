package com.bx.jz.jy.jybx.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.adapter.MySpinnerAdapter;
import com.bx.jz.jy.jybx.utils.L;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/23 0023.
 */

public class SpinnerView extends LinearLayout {
    private Context context;
    private LinearLayout layout;
    private ListView listView;
    private MySpinnerAdapter adapter;
    private PopupWindow popupWindow;
    private TextView mSpinnerText;
    private LinearLayout mLinearLayout;
    private ArrayList<String> listData = new ArrayList<>();

    public SpinnerView(Context context) {
        super(context);
        this.context = context;
    }

    public SpinnerView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.include_my_spinnerview, this);
        mSpinnerText = (TextView) findViewById(R.id.text_spinner);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_ai_mode);
    }

    public SpinnerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setMyData() {
        ArrayList<String> persons = new ArrayList<String>();
        persons.add("智能模式");
        persons.add("假日模式");
        persons.add("速冷模式");
        persons.add("速冻模式");
        persons.add("去味");
        this.listData = persons;
        L.e("SPINNER   " , mSpinnerText.getText().toString());
        adapter = new MySpinnerAdapter(context, listData,mSpinnerText.getText().toString());    // 默认设置下拉框的标题为数据的第一个
//        mSpinnerText.setText((CharSequence) adapter.getItem(0));    // 点击右侧按钮，弹出下拉框
        showWindow(mLinearLayout);
    }

    public void showWindow(View v) {
        // 找到布局文件
        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.mypinner_dropdown, null);
        // 实例化listView
        listView = (ListView) layout.findViewById(R.id.listView);
        // 设置listView的适配器
        listView.setAdapter(adapter);
        // 实例化一个PopuWindow对象
        popupWindow = new PopupWindow(v);
        // 设置弹框的宽度为布局文件的宽
        popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
        // 高度设置的300
        popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击弹框外部，弹框消失
        popupWindow.setOutsideTouchable(true);
        // 设置焦点
        popupWindow.setFocusable(true);
        // 设置所在布局
        popupWindow.setContentView(layout);
        // 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度
        popupWindow.showAsDropDown(v,  0,4, Gravity.BOTTOM);
        // listView的item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if(arg2 == 4){
                    if(mSpinnerText.getText().toString().contains("去味")){
                        mSpinnerText.setText(mSpinnerText.getText().toString().replace("/去味",""));
                    }else {
                        mSpinnerText.setText(mSpinnerText.getText().toString()+"/"+listData.get(arg2));// 设置所选的item作为下拉框的标题
                    }
                }else {
                    mSpinnerText.setText(listData.get(arg2));// 设置所选的item作为下拉框的标题
                }
                // 弹框消失
                popupWindow.dismiss();
                popupWindow = null;
            }
        });
    }

}