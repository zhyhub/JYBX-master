package com.bx.jz.jy.jybx.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bx.jz.jy.jybx.R;
import com.bx.jz.jy.jybx.base.BaseActivity;
import com.bx.jz.jy.jybx.bean.CodeBean;
import com.bx.jz.jy.jybx.fragment.FragmentFour;
import com.bx.jz.jy.jybx.fragment.FragmentOne;
import com.bx.jz.jy.jybx.fragment.FragmentThree;
import com.bx.jz.jy.jybx.fragment.FragmentTwo;
import com.bx.jz.jy.jybx.utils.BaseResult;
import com.bx.jz.jy.jybx.utils.DecorViewUtils;
import com.bx.jz.jy.jybx.utils.JyHttpDSG1;
import com.bx.jz.jy.jybx.utils.L;
import com.bx.jz.jy.jybx.utils.T;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.joyoung.sdk.info.UserConfirmData;
import com.joyoung.sdk.interface_sdk.ChangeDevCallBack;
import com.joyoung.sdk.interface_sdk.CommandCallBack;
import com.joyoung.sdk.interface_sdk.JoyoungLinkCallBack;
import com.joyoung.sdk.interface_sdk.SendCmdCallback;
import com.joyoung.sdk.utils.JoyoungSDK;
import com.joyoung.sdk.utils.encryptdecryptutil.Encrypt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.bx.jz.jy.jybx.ConstantPool.cmd;
import static com.bx.jz.jy.jybx.ConstantPool.devId;
import static com.bx.jz.jy.jybx.ConstantPool.devTypeId;
import static com.bx.jz.jy.jybx.ConstantPool.passwd;
import static com.bx.jz.jy.jybx.ConstantPool.phonenumber;


public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener, EasyPermissions.PermissionCallbacks {

    private static final String TAG = MainActivity.class.getSimpleName();

    BottomNavigationBar mBottomNavigationBar;

    private FragmentOne mFragmentOne;
    private FragmentTwo mFragmentTwo;
    private FragmentThree mFragmentThree;
    private FragmentFour mFragmentFour;
    private ArrayList<Fragment> fragments;

    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.CHANGE_WIFI_STATE};
    private int RC_CAMERA_AND_WIFI = 1;
    private String loginData;
    private Gson gson;
    public static String xxteaKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorViewUtils.getDarkDecorView(this);
        setContentView(R.layout.activity_main);
        gson = new Gson();
        xxteaKey = Encrypt.MD5(phonenumber + passwd).substring(0, 16);

        JyHttpDSG1 jyHttpDSG1 = new JyHttpDSG1();
        jyHttpDSG1.login(phonenumber, passwd, new JyHttpDSG1.JoyoungCallBack<BaseResult<UserConfirmData>>() {
            @Override
            public void Success(BaseResult<UserConfirmData> userConfirmDataBaseResult) {
                L.e(TAG, "登录成功");
                loginData = gson.toJson(userConfirmDataBaseResult);
                JoyoungSDK.getInstance().init(MainActivity.this, loginData, phonenumber, passwd, "1", mCommandCallBack, joyoungLinkCallBack);
            }

            @Override
            public void failed(String msg) {
                L.e(TAG, "登录失败");
            }
        });

        mFragmentOne = new FragmentOne();
        mFragmentTwo = new FragmentTwo();
        mFragmentThree = new FragmentThree();
        mFragmentFour = new FragmentFour();

        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation);
//.setActiveColorResource(R.color.color_0e)
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        mBottomNavigationBar.setBarBackgroundColor(R.color.color_0e);
        mBottomNavigationBar.setInActiveColor(R.color.color_70);
        mBottomNavigationBar.setActiveColor(R.color.white);
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.icon_one, R.string.tab_one).setBadgeItem(null).setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.mipmap.icon_two, R.string.tab_two).setBadgeItem(null).setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.mipmap.icon_three, R.string.tab_three).setBadgeItem(null).setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.mipmap.icon_four, R.string.tab_four).setBadgeItem(null).setActiveColorResource(R.color.white))
                .setFirstSelectedPosition(0)
                .initialise();
        fragments = getFragments();
        mBottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();

        if (EasyPermissions.hasPermissions(this, perms)) {

        } else {
            EasyPermissions.requestPermissions(this, "拍照需要摄像头权限",
                    RC_CAMERA_AND_WIFI, perms);
        }
    }

    /**
     * 设置默认的Item
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.ll_content, mFragmentOne);
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(mFragmentOne);
        fragments.add(mFragmentTwo);
        fragments.add(mFragmentThree);
        fragments.add(mFragmentFour);
        return fragments;
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                //当前的fragment
                Fragment from = fm.findFragmentById(R.id.ll_content);
                //点击即将跳转的fragment
                Fragment fragment = fragments.get(position);
                if (fragment.isAdded()) {
                    // 隐藏当前的fragment，显示下一个
                    ft.hide(from).show(fragment);
                } else {
                    // 隐藏当前的fragment，add下一个到Activity中
                    ft.hide(from).add(R.id.ll_content, fragment);
                    if (fragment.isHidden()) {
                        ft.show(fragment);
                        L.d("被隐藏了");
                    }
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        //这儿也要操作隐藏，否则Fragment会重叠
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                // 隐藏当前的fragment
                ft.hide(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private CommandCallBack mCommandCallBack = new CommandCallBack() {
        @Override
        public void connectionLost(String msg) {
            L.e(TAG, "mCommandCallBack  connectionLost" + msg);
        }

        @Override
        public void messageArrived(String topic, String msg) {
            L.e(TAG, "mCommandCallBack  messageArrived " + msg);
        }

        @Override
        public void deliveryComplete(String token) {

        }
    };

    private JoyoungLinkCallBack joyoungLinkCallBack = new JoyoungLinkCallBack() {
        @Override
        public void onSuccess() {
            JoyoungSDK joyoungSDK1 = JoyoungSDK.getInstance();
            joyoungSDK1.changeDev(MainActivity.this, xxteaKey, devTypeId, devId, mCommandCallBack, changeDevCallBack);
        }

        @Override
        public void onError() {

        }
    };

    private ChangeDevCallBack changeDevCallBack = new ChangeDevCallBack() {
        @Override
        public void onSuccess() {
            try {
                JoyoungSDK.getInstance().sendCMD(
                        cmd, devId, true, sendCmdCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError() {

        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    T.showShort(MainActivity.this, "设备未注册");
                    break;
                case 1001:
                    T.showShort(MainActivity.this, "系统错误");
                    break;
                case 1003:
                    T.showShort(MainActivity.this, "请求第三方接口超时");
                    break;
                case 101:
                    T.showShort(MainActivity.this, "key失效，需要同步");
                    break;
                case 102:
                    T.showShort(MainActivity.this, "seq 失效，需要同步");
                    break;
                case 103:
                    T.showShort(MainActivity.this, "设备不在线");
                    break;
            }
        }
    };

    private SendCmdCallback sendCmdCallback = new SendCmdCallback() {
        @Override
        public void onResponse(String s) {
            L.e(TAG, "指令发送成功" + s);
            CodeBean codeBean = gson.fromJson(s, CodeBean.class);

            switch (codeBean.getCode()) {
                case 100:
                    mHandler.sendEmptyMessage(100);
                    break;
                case 1001:
                    mHandler.sendEmptyMessage(1001);
                    break;
                case 1003:
                    mHandler.sendEmptyMessage(1003);
                    break;
                case 101:
                    mHandler.sendEmptyMessage(101);
                    break;
                case 102:
                    mHandler.sendEmptyMessage(102);
                    break;
                case 103:
                    mHandler.sendEmptyMessage(103);
                    break;
            }
        }

        @Override
        public void onFailure(String s, IOException e) {
            L.e(TAG, "指令发送失败" + s);
        }
    };
}
