package com.bx.jz.jy.jybx.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.broadcom.cooee.Cooee;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.service.ListenSocketService;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.utils.Settings;
import com.bx.jz.jy.jybx.utils.T;
import com.bx.jz.jy.jybx.view.LoadingDialog;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 手动配网
 */

public class AddBySelfActivity extends BaseActivity {

    private static final String TAG = AddBySelfActivity.class.getSimpleName();

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
    @BindView(R.id.tv_open_wifi)
    EditText tvOpenWifi;
    @BindView(R.id.img_cancel)
    ImageView imgCancel;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.img_eye)
    ImageView imgEye;
    @BindView(R.id.btn_deploy)
    AppCompatButton btnDeploy;
    @BindView(R.id.complete_img)
    TextView completeImg;
    @BindView(R.id.connect_mac)
    TextView connectMac;

    private boolean eyeStatus = false;
    private int mLocalIp;
    private boolean mDone = false;
    private Thread mThread;
    private Intent intent;
    private MsgReceiver msgReceiver;
    private String mac;
    private Dialog loadingDialog;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1015) {
                if(mac != null && !"".equals(mac)){
                    connectMac.setText("已连接设备的mac地址：" + mac);
                    btnDeploy.setText("配置成功");
                    loadingDialog.cancel();
                }
            }
        }
    };

    /**
     * 广播接收器
     */
    public class MsgReceiver extends BroadcastReceiver {

        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            //拿到进度，更新UI
            mac = intent.getStringExtra("wifimac");
            T.showLong(AddBySelfActivity.this, "已连接设备的mac地址");
            mHandler.sendEmptyMessage(1015);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(AddBySelfActivity.this);
        setContentView(R.layout.add_by_self_activity);
        ButterKnife.bind(this);
        intent = new Intent(this, ListenSocketService.class);
        loadingDialog = LoadingDialog.createDialog(AddBySelfActivity.this, "正在为您配网...");
        baseLl.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("连接网络");
        updateWifiInfo();
    }

    private void updateWifiInfo() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connManager != null;
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Log.d(TAG, "connected: " + networkInfo.isConnected());
        if (!networkInfo.isConnected()) {
            Log.d(TAG, getString(R.string.connect_wifi));
            showErrorDialog();
            return;
        }

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifiManager != null;
        WifiInfo info = wifiManager.getConnectionInfo();
        mLocalIp = info.getIpAddress();
        Log.d(TAG, String.format("ip: 0x%x", mLocalIp));

        String ssid = info.getSSID();
        if (ssid.startsWith("\"")) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        tvOpenWifi.setText(ssid);
        Log.d(TAG, "ssid: " + ssid);
    }

    private void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddBySelfActivity.this);
        builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.connect_wifi);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });
        builder.create().show();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(AddBySelfActivity.this, null);
    }

    @Override
    public View[] filterViewByIds() {
        return new View[]{etPwd};
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        return new int[]{R.id.et_pwd};
    }

    @OnClick({R.id.img_back, R.id.tv_open_wifi, R.id.img_cancel, R.id.img_eye, R.id.btn_deploy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.tv_open_wifi:
                break;
            case R.id.img_cancel:
                tvOpenWifi.setText("");
                break;
            case R.id.img_eye:
                if (!eyeStatus) {
                    imgEye.setImageResource(R.mipmap.eye_off);
                    eyeStatus = true;
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    imgEye.setImageResource(R.mipmap.eye_on);
                    eyeStatus = false;
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.btn_deploy:
                if ("".equals(tvOpenWifi.getText().toString())) {
                    T.showShort(AddBySelfActivity.this, "请输入WIFI名称");
                    return;
                }

                if ("".equals(etPwd.getText().toString())) {
                    T.showShort(AddBySelfActivity.this, "请输入WIFI密码");
                    return;
                }
                loadingDialog.show();
                send();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateWifiInfo();

        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.signway.wifimac.RECEIVER");
        registerReceiver(msgReceiver, intentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(intent);
    }

    private void send() {
        T.showShort(AddBySelfActivity.this, "mDone == " + mDone);
        if (!mDone) {
            mDone = true;
            final String SSId = tvOpenWifi.getText().toString();
            final String password = etPwd.getText().toString();

            SharedPreferences sp = Settings.getPrefs(AddBySelfActivity.this);
            String packetInterval = sp.getString("packet_interval", getString(R.string.default_packet_interval));
            int interval = Integer.parseInt(packetInterval);
            Cooee.SetPacketInterval(interval); /* default 8ms */

            if (mThread == null) {
                mThread = new Thread() {
                    public void run() {
                        while (mDone) {
                            if (loadingDialog != null && loadingDialog.isShowing()) {
                                Cooee.send(SSId, password, mLocalIp);
                            } else {
                                stopService(intent);
                                return;
                            }
                            L.e(TAG, "ssid  : " + SSId + "  password  : " + password);
                        }
                    }
                };
            }
            mThread.start();
            startService(intent);
        } else {
            mDone = false;
            mThread = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingDialog.cancel();
        unregisterReceiver(msgReceiver);
    }
}
