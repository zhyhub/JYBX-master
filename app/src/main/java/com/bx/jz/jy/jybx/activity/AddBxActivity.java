package com.bx.jz.jy.jybx.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.utils.T;
import com.bx.jz.jy.jybx.view.FullScreenDialog;
import com.google.zxing.Result;
import com.google.zxing.client.android.AutoScannerView;
import com.google.zxing.client.android.BaseCaptureActivity;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 扫描二维码
 */

public class AddBxActivity extends BaseCaptureActivity {

    private static final String TAG = AddBxActivity.class.getSimpleName();
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl)
    Toolbar rl;
    @BindView(R.id.preview_view)
    SurfaceView surfaceView;
    @BindView(R.id.autoscanner_view)
    AutoScannerView autoScannerView;
    @BindView(R.id.add_by_self)
    LinearLayout addBySelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.setColorNoTranslucent(AddBxActivity.this, Color.BLACK);
        DecorViewUtils.getWhiteDecorView(this);

        setContentView(R.layout.add_bx_activity);
        ButterKnife.bind(this);
        surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        autoScannerView = (AutoScannerView) findViewById(R.id.autoscanner_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoScannerView.setCameraManager(cameraManager);
    }

    @Override
    public SurfaceView getSurfaceView() {
        return (surfaceView == null) ? (SurfaceView) findViewById(R.id.preview_view) : surfaceView;
    }

    @Override
    public void dealDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        L.i(TAG, "dealDecode ~~~~~ " + rawResult.getText() + " " + barcode + " " + scaleFactor);
        playBeepSoundAndVibrate(true, false);
        T.showShort(this, rawResult.getText());
//        对此次扫描结果不满意可以调用
        reScan();
    }

    @OnClick({R.id.img_back, R.id.add_by_self})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.add_by_self:
                startActivity(new Intent(this, AddBySelfActivity.class));
                break;
        }
    }

    public void getDialog() {
        final FullScreenDialog dialog = new FullScreenDialog(this);
        LayoutInflater inflater = getLayoutInflater();
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.dialog_add_bx_complete, null);
        TextView content = layout.findViewById(R.id.complete_content);
        TextView title = layout.findViewById(R.id.complete_title);
        title.setText("添加成功");
        content.setText("您好，您已成功添加设备");

        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
                AddBxActivity.this.finish();
            }
        }, 1500);

        dialog.show();
        dialog.setCancelable(false);
        dialog.setContentView(layout);
    }

}

